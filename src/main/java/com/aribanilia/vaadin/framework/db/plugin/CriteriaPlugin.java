/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.plugin;

import com.aribanilia.vaadin.framework.db.hibernate.Criteria;
import com.aribanilia.vaadin.framework.db.hibernate.Restriction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import java.util.List;

public class CriteriaPlugin implements Criteria {
    private org.hibernate.Criteria criteria;
    private org.hibernate.Session session;
    private RestrictionPlugin restrictionPlugin;

    @Override
    public Criteria addRestriction(Object restriction) {
        criteria.add((Criterion) restriction);
        return this;
    }

    @Override
    public Criteria addOrderBy(String propertyName) {
        criteria.addOrder(Order.asc(propertyName));
        return this;
    }

    @Override
    public Criteria addOrderByDesc(String propertyName) {
        criteria.addOrder(Order.desc(propertyName));
        return this;
    }

    @Override
    public Criteria create(Class c) {
        criteria = session.createCriteria(c);
        return this;
    }

    @Override
    public Restriction getRestriction() {
        if (restrictionPlugin == null)
            restrictionPlugin = new RestrictionPlugin();

        return restrictionPlugin;
    }

    @Override
    public Object uniqueResult() {
        return criteria.uniqueResult();
    }

    @Override
    public List list() {
        return criteria.list();
    }

    @Override
    public Criteria setProjection(String propertyName) {
        criteria.setProjection(Projections.property(propertyName));
        return this;
    }

    @Override
    public void setSession(Object session) {
        this.session = (org.hibernate.Session) session;
    }
}
