/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.model;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.util.VConstants;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = LandingPage.VIEW_NAME)
public class LandingPage extends VerticalLayout implements View {
    private TblUser user;
    public static final String VIEW_NAME = "landing";
    private static final Logger logger = LoggerFactory.getLogger(LandingPage.class);

    @Autowired
    public LandingPage() {
        setSizeFull();
        setMargin(true);
        setSpacing(true);

        /**
         * Header
         * Left     : Application Name
         * Right    : Username
         */
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidth("100%");
        headerLayout.setSpacing(true);
        addComponent(headerLayout);

        Label lblUsername = new Label(VConstants.APPLICATION_NAME);
        headerLayout.addComponent(lblUsername);
        headerLayout.setComponentAlignment(lblUsername, Alignment.MIDDLE_LEFT);
        
        TblUser user = (TblUser) VaadinSession.getCurrent().getAttribute("user");
        Button btnLogout = new Button(user.getUsername(), clickEvent -> {
            VaadinSession.getCurrent().setAttribute("user", null);
            getUI().getNavigator().navigateTo(LoginPage.VIEW_NAME);
        });
        headerLayout.addComponent(btnLogout);
        headerLayout.setComponentAlignment(btnLogout, Alignment.MIDDLE_RIGHT);

        // Bottom Panel
        Panel botPanel = new Panel("BOTTOM WELCOME");
        botPanel.setSizeUndefined();
        addComponent(botPanel);
    }
}
