/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.plugin;

import com.aribanilia.vaadin.framework.db.hibernate.Transaction;
import com.aribanilia.vaadin.framework.exception.UnsafeSessionCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionPlugin implements Transaction {
    private org.hibernate.Session session;
    private org.hibernate.Transaction trx;
    private boolean isReleased = false;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Override
    public void begin() {
        trx = session.getTransaction();
        trx.setTimeout(600);
        trx.begin();
    }

    @Override
    public void commit() {
        if (trx.getStatus().toString().equals("ACTIVE")) {
            trx.commit();
            isReleased = true;
        }
    }

    @Override
    public void rollback() {
        if (trx.getStatus().toString().equals("ACTIVE")) {
            trx.rollback();
            isReleased = true;
        }
    }

    @Override
    public boolean isActive() {
        return (trx != null && (trx.getStatus().toString().equals("ACTIVE")));
    }

    @Override
    public void released() {
        if (isActive()) {
            if (!isReleased) {
                Exception e = new UnsafeSessionCloseException("TRANSACTION BELUM DIRELEASE, PASTI ADA DATA YANG TIDAK KONSISTEN! BELUM PERNAH DISLIDING PALA LU, COBA CEK TRANSAKSI LU");
                log.error(e.getMessage(), e);
                rollback();
            }
        }
    }

    @Override
    public void setSession(Object session) {
        this.session = (org.hibernate.Session) session;
    }
}
