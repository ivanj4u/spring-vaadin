package com.aribanilia.vaadin.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView
public class ScheduleView extends VerticalLayout implements View {

    public ScheduleView() {
        addComponent(new Label("Schedule View"));
    }
}
