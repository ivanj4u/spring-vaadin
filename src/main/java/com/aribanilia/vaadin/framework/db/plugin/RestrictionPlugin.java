/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.plugin;

import com.aribanilia.vaadin.framework.db.hibernate.Restriction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

public class RestrictionPlugin implements Restriction {
    @Override
    public Object eq(String propertyName, Object value) {
        return Restrictions.eq(propertyName, value);
    }

    @Override
    public Object eqProperty(String propertyName1, String propertyName2) {
        return Restrictions.eqProperty(propertyName1, propertyName2);
    }

    @Override
    public Object ne(String propertyName, Object value) {
        return Restrictions.ne(propertyName, value);
    }

    @Override
    public Object neProperty(String propertyName1, String propertyName2) {
        return Restrictions.neProperty(propertyName1, propertyName2);
    }

    @Override
    public Object like(String propertyName, Object value) {
        return Restrictions.like(propertyName, value);
    }

    @Override
    public Object between(String propertyName, Object value1, Object value2) {
        return Restrictions.between(propertyName, value1, value2);
    }

    @Override
    public Object lt(String propertyName, Object value) {
        return Restrictions.lt(propertyName, value);
    }

    @Override
    public Object ltProperty(String propertyName1, String propertyName2) {
        return Restrictions.ltProperty(propertyName1, propertyName2);
    }

    @Override
    public Object le(String propertyName, Object value) {
        return Restrictions.le(propertyName, value);
    }

    @Override
    public Object leProperty(String propertyName1, String propertyName2) {
        return Restrictions.leProperty(propertyName1, propertyName2);
    }

    @Override
    public Object gt(String propertyName, Object value) {
        return Restrictions.gt(propertyName, value);
    }

    @Override
    public Object gtProperty(String propertyName1, String propertyName2) {
        return Restrictions.gtProperty(propertyName1, propertyName2);
    }

    @Override
    public Object ge(String propertyName, Object value) {
        return Restrictions.ge(propertyName, value);
    }

    @Override
    public Object geProperty(String propertyName1, String propertyName2) {
        return Restrictions.geProperty(propertyName1, propertyName2);
    }

    @Override
    public Object or(Object criteria1, Object criteria2) {
        return Restrictions.or((Criterion) criteria1, (Criterion) criteria2);
    }

    @Override
    public Object and(Object criteria1, Object criteria2) {
        return Restrictions.and((Criterion) criteria1, (Criterion) criteria2);
    }

    @Override
    public Object isNull(String propertyName) {
        return Restrictions.isNull(propertyName);
    }

    @Override
    public Object isNotNull(String propertyName) {
        return Restrictions.isNotNull(propertyName);
    }

    @Override
    public Object in(String propertyName, Collection values) {
        return Restrictions.in(propertyName, values);
    }
}
