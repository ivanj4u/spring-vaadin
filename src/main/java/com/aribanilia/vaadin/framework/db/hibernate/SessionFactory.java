/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.hibernate;

public interface SessionFactory {
    Session openSession() throws Exception;
}
