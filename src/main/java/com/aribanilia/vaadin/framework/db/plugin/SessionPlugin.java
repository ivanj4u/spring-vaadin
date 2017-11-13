/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.plugin;

import com.aribanilia.vaadin.entity.AuditTrail;
import com.aribanilia.vaadin.framework.db.hibernate.Criteria;
import com.aribanilia.vaadin.framework.db.hibernate.Query;
import com.aribanilia.vaadin.framework.db.hibernate.Session;
import com.aribanilia.vaadin.framework.db.hibernate.Transaction;
import org.hibernate.LockMode;
import org.hibernate.exception.LockAcquisitionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SessionPlugin implements Session {
    private org.hibernate.Session session;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private TransactionPlugin trxPlugin = null;
    private List<Transaction> listOfTrx = new ArrayList<>();

    @Override
    public void save(Object o) {
        if ((o instanceof AuditTrail)) {
            ((AuditTrail) o).setVersi(System.currentTimeMillis());
        }
        session.save(o);
    }

    @Override
    public void saveOrUpdate(Object o) {
        if ((o instanceof AuditTrail)) {
            ((AuditTrail) o).setVersi(System.currentTimeMillis());
        }
        session.saveOrUpdate(o);
    }

    @Override
    public void update(Object o) {
        if ((o instanceof AuditTrail)) {
            ((AuditTrail) o).setVersi(System.currentTimeMillis());
        }
        session.update(o);
    }

    @Override
    public Object get(Class c, Object id) {
        return session.get(c, (Serializable) id);
    }

    @Override
    public Object getAndLock(Class c, Object id) throws LockAcquisitionException {
        if (trxPlugin == null) {
            log.error("Trying to lock at session scope is prohibited, System will shutdown for now...");
            System.exit(1);
        }
        if (!trxPlugin.isActive()) {
            log.error("Trying to lock at session scope is prohibited, System will shutdown for now...");
            System.exit(1);
        }
        Object o;
        try {
            o = session.get(c, (Serializable) id, LockMode.UPGRADE);
        } catch (LockAcquisitionException e) {
            throw e;
        }
        return o;
    }

    @Override
    public Object getAndLockNoWait(Class c, Object id) throws LockAcquisitionException {
        if (trxPlugin == null) {
            log.error("Trying to lock at session scope is prohibited, System will shutdown for now...");
            System.exit(1);
        }
        if (!trxPlugin.isActive()) {
            log.error("Trying to lock at session scope is prohibited, System will shutdown for now...");
            System.exit(1);
        }
        Object o;
        try {
            o = session.get(c, (Serializable) id, LockMode.UPGRADE_NOWAIT);
        } catch (LockAcquisitionException e) {
            throw e;
        }
        return o;
    }

    @Override
    public void delete(Object o) {
        session.delete(o);
    }

    @Override
    public Transaction beginTransaction() {
        trxPlugin = new TransactionPlugin();
        trxPlugin.setSession(session);
        trxPlugin.begin();
        listOfTrx.add(trxPlugin);
        return trxPlugin;
    }

    @Override
    public Transaction getCurrentTransaction() {
        return trxPlugin;
    }

    @Override
    public Query createQuery(String hql) {
        QueryPlugin queryPlugin = new QueryPlugin();
        queryPlugin.setSession(session);
        queryPlugin.setQueryString(hql);
        return queryPlugin;
    }

    @Override
    public Query createSQLQuery(String sql) {
        QueryPlugin queryPlugin = new QueryPlugin();
        queryPlugin.setSession(session);
        queryPlugin.setSQLQueryString(sql);
        return queryPlugin;
    }

    @Override
    public Criteria createCriteria(Class c) {
        CriteriaPlugin criteriaPlugin = new CriteriaPlugin();
        criteriaPlugin.setSession(session);
        criteriaPlugin.create(c);
        return criteriaPlugin;
    }

    @Override
    public Query createQueryCache(String hql) {
        return createQuery(hql);
    }

    @Override
    public Query createSQLQueryCache(String sql) {
        return createSQLQuery(sql);
    }

    @Override
    public Criteria createCriteriaCache(Class c) {
        return createCriteria(c);
    }

    @Override
    public void close() {
        if (session.isOpen()) {
            // released trx before closing resource!
            for (Transaction trx : listOfTrx) {
                trx.released();
            }
            session.close();
        }
    }

    @Override
    public boolean isOpen() {
        return session.isOpen();
    }

    @Override
    public void setSession(Object session) {
        this.session = (org.hibernate.Session) session;
    }
}
