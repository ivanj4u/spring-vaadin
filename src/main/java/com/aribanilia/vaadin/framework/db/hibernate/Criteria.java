/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.hibernate;

import java.util.List;

public interface Criteria extends Database {
    Criteria addRestriction(Object restriction);
    Criteria addOrderBy(String propertyName);
    Criteria addOrderByDesc(String propertyName);
    Criteria create(Class c);
    Restriction getRestriction();
    Object uniqueResult();
    List list();
    Criteria setProjection(String propertyName);
}
