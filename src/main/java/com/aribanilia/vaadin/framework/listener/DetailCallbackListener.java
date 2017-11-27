/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework.listener;

public interface DetailCallbackListener {

    void onAfterAdded(Object pojo);
    void onAfterUpdated(Object pojo);
    void onAfterViewed();
    void onCancel();
}
