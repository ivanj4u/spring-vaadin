/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.hibernate;

public interface Transaction extends Database {
    void begin();
    void commit();
    void rollback();
    boolean isActive();
    void released();
}
