/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.hibernate;

import java.util.Collection;

public interface Restriction {
    Object eq(String propertyName, Object value);
    Object eqProperty(String propertyName1, String propertyName2);
    Object ne(String propertyName, Object value);
    Object neProperty(String propertyName1, String propertyName2);
    Object like(String propertyName, Object value);
    Object between(String propertyName, Object value1, Object value2);
    Object lt(String propertyName, Object value);
    Object ltProperty(String propertyName1, String propertyName2);
    Object le(String propertyName, Object value);
    Object leProperty(String propertyName1, String propertyName2);
    Object gt(String propertyName, Object value);
    Object gtProperty(String propertyName1, String propertyName2);
    Object ge(String propertyName, Object value);
    Object geProperty(String propertyName1, String propertyName2);
    Object or(Object criteria1, Object criteria2);
    Object and(Object criteria1, Object criteria2);
    Object isNull(String propertyName);
    Object isNotNull(String propertyName);
    Object in(String propertyName, Collection values);

}
