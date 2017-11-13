/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.framework.db.hibernate.Criteria;
import com.aribanilia.vaadin.framework.db.hibernate.Session;
import com.aribanilia.vaadin.framework.db.hibernate.Transaction;
import com.aribanilia.vaadin.framework.db.plugin.PersistentPlugin;

import java.util.Date;

public class TestHibernate {

    public static void main(String[] args) {
        try {
            Session session = PersistentPlugin.getSessionFactory().openSession();
            Transaction trx = session.beginTransaction();
            Criteria criteria = session.createCriteria(TblUser.class);
            criteria.addRestriction(criteria.getRestriction().eq("username", "angko"));
            TblUser user = (TblUser) criteria.uniqueResult();
            System.out.println("OLD EMAIL : " + user.getEmail());
            user.setEmail("angko.j4u@gmail.com");
            System.out.println("NEW EMAIL : " + user.getEmail());
            user.setUpdateBy("System");
            user.setUpdateDate(new Date());
            session.save(user);
            trx.commit();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
