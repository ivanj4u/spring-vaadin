/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.plugin;

import com.aribanilia.vaadin.framework.db.HibernateUtil;
import com.aribanilia.vaadin.framework.db.hibernate.Session;
import com.aribanilia.vaadin.framework.db.hibernate.SessionFactory;

public class SessionFactoryPlugin implements SessionFactory {
    @Override
    public Session openSession() throws Exception {
        SessionPlugin session = new SessionPlugin();
        session.setSession(HibernateUtil.getSessionFactory().openSession());
        return session;
    }
}
