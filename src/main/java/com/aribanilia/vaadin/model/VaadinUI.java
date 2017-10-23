package com.aribanilia.vaadin.model;

import com.aribanilia.vaadin.service.UserServices;
import com.aribanilia.vaadin.util.VConstants;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

    @Autowired private UserServices servicesUser;
    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        // Initializer All UI
        navigator = new Navigator(this, this);

        LoginPage login = new LoginPage(servicesUser);
        navigator.addView(LoginPage.VIEW_NAME, login);
        navigator.setErrorView(LoginPage.class);

        router();
    }

    private void router() {
        if (getSession().getAttribute("user") != null) {
            getNavigator().navigateTo(LandingPage.VIEW_NAME);
        } else {
            getNavigator().navigateTo(LoginPage.VIEW_NAME);
        }
    }
}
