/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework;

public interface PriviledgeModel {

    int MODE_NEW = 0;
    int MODE_UPDATE = 1;
    int MODE_VIEW = 2;

    boolean setMode(int mode);

    boolean isAuthorizedToView();

    boolean isAuthorizedToUpdate();

    boolean isAuthorizedToDelete();

    boolean isAuthorizedToAdd();
}
