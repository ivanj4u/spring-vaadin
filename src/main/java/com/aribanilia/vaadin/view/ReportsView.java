package com.aribanilia.vaadin.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView
public class ReportsView extends VerticalLayout implements View {

    public ReportsView() {
        addComponent(new Label("Reports View"));
    }
}
