package com.aribanilia.vaadin.view;

import com.aribanilia.vaadin.framework.impl.AbstractScreen;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

@SpringView
public class DashboardView extends AbstractScreen implements View {

    @Override
    protected void initComponents() {
        setContent(new Label("Dashboard View"));
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
