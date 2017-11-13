/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.view;

import com.aribanilia.vaadin.framework.AbstractScreen;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

@SpringView
public class LandingView extends AbstractScreen implements View {

    @Override
    protected void initComponents() {
        setContent(new Label("This is Landing View"));
    }

    @Override
    public void setModeNew() {

    }

    @Override
    public void setModeUpdate() {

    }

    @Override
    public void setModeView() {

    }
}