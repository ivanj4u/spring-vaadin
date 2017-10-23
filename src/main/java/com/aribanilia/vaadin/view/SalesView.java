package com.aribanilia.vaadin.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView
public class SalesView extends VerticalLayout implements View {

    public SalesView() {
        addComponent(new Label("View Screen"));
    }
}
