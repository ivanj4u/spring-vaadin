package com.aribanilia.vaadin.view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView
public class TransactionsView extends VerticalLayout implements View {

    public TransactionsView() {
        addComponent(new Label("Transaction View"));
    }
}
