/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.plugin;

import com.aribanilia.vaadin.framework.db.hibernate.Query;

import java.util.Collection;
import java.util.List;

public class QueryPlugin implements Query {
    private org.hibernate.Session session;
    private org.hibernate.Query query;

    @Override
    public Query setParameter(String param, Object value) {
        query.setParameter(param, value);
        return this;
    }

    @Override
    public Query setParameter(int index, Object value) {
        query.setParameter(index, value);
        return this;
    }

    @Override
    public Query setParameterList(String param, Collection c) {
        query.setParameterList(param, c);
        return this;
    }

    @Override
    public Query setParameterList(String param, Object[] o) {
        query.setParameterList(param, o);
        return this;
    }

    @Override
    public Query setQueryString(String hql) {
        this.query = session.createQuery(hql);
        return this;
    }

    @Override
    public Query setSQLQueryString(String sql) {
        this.query = session.createSQLQuery(sql);
        return this;
    }

    @Override
    public Object uniqueResult() {
        return query.uniqueResult();
    }

    @Override
    public Query setMaxResult(int max) {
        query.setMaxResults(max);
        return this;
    }

    @Override
    public List list() {
        return query.list();
    }

    @Override
    public int executeUpdate() {
        return query.executeUpdate();
    }

    @Override
    public void setSession(Object session) {
        this.session = (org.hibernate.Session) session;
    }
}
