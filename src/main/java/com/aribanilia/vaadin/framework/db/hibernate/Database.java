/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.db.hibernate;

import java.io.Serializable;

public interface Database extends Serializable {
    void setSession(Object session);
}
