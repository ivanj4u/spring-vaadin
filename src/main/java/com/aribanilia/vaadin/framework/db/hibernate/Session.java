/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.hibernate;

import org.hibernate.exception.LockAcquisitionException;

public interface Session extends Database {
    void save(Object o);
    void saveOrUpdate(Object o);
    void update(Object o);
    Object get(Class c, Object id);
    Object getAndLock(Class c, Object id) throws LockAcquisitionException;
    Object getAndLockNoWait(Class c, Object id) throws LockAcquisitionException;
    void delete(Object o);
    Transaction beginTransaction();
    Transaction getCurrentTransaction();
    Query createQuery(String hql);
    Query createSQLQuery(String sql);
    Criteria createCriteria(Class c);
    Query createQueryCache(String hql);
    Query createSQLQueryCache(String sql);
    Criteria createCriteriaCache(Class c);
    void close();
    boolean isOpen();
}
