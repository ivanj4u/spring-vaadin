/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.loader.MenuLoader;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@PreserveOnRefresh
@Theme(ValoTheme.THEME_NAME)
@Title("Vaadin Spring")
public class VaadinUI extends UI {
    @Autowired
    private SpringViewProvider viewProvider;

    private MainPage mainPage;

    private static final Logger logger = LoggerFactory.getLogger(VaadinUI.class);

    @Override
    protected void init(VaadinRequest request) {
        this.mainPage = new MainPage();
        if (getSession().getAttribute(MenuLoader.class) == null) {
            getSession().setAttribute(MenuLoader.class, new MenuLoader());
        }
        setErrorHandler(event -> {
            Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
            logger.error("Error during request", t);
        });

        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(LoginPage.class);
        setNavigator(navigator);

        updateContent();
    }

    private void updateContent() {
        if (getSession().getAttribute(TblUser.class) != null) {
            setContent(mainPage);
        } else {
            getNavigator().navigateTo(LoginPage.VIEW_NAME);
        }
    }
}
