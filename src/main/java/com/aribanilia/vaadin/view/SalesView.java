package com.aribanilia.vaadin.view;

import com.aribanilia.vaadin.model.AbstractScreen;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class SalesView extends AbstractScreen {

    @Override
    protected void initComponents() {
        setContent(new Label("Sales View"));
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
