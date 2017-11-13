/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.hibernate;

import java.util.Collection;
import java.util.List;

public interface Query extends Database {
    Query setParameter(String param, Object value);
    Query setParameter(int index, Object value);
    Query setParameterList(String param, Collection c);
    Query setParameterList(String param, Object[] o);
    Query setQueryString(String hql);
    Query setSQLQueryString(String sql);
    Object uniqueResult();
    Query setMaxResult(int max);
    List list();
    int executeUpdate();
}
