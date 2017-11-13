/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {

    public static SessionFactory getSessionFactory() {
        String path = System.getProperty("user.dir").replaceAll("\\\\", "\\/")
                + "/cfg/hibernate.cfg.xml";
        File file = new File(path);
        final SessionFactory sessionFactory = new Configuration().configure(file).buildSessionFactory();
        return sessionFactory;
    }

}
