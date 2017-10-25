package com.aribanilia.vaadin.framework;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

public class HibernateUtil {

    public static SessionFactory getSessionFactory() {
        final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory;
    }

}
