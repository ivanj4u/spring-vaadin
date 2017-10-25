package com.aribanilia.vaadin.model;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.loader.MenuLoader;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringUI
@PreserveOnRefresh
@Theme(ValoTheme.THEME_NAME)
@Title("Vaadin Spring")
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        // Initial Configuration
        setMenuInstance();

        Responsive.makeResponsive(this);
        Navigator navigator = new Navigator(this, this);
        // Initializer Login View
        LoginPage login = new LoginPage();
        navigator.addView(LoginPage.VIEW_NAME, login);
        navigator.setErrorView(LoginPage.class);
        updateContent();
    }

    private void setMenuInstance() {
        MenuLoader loader = null;
        if (VaadinSession.getCurrent().getAttribute(MenuLoader.class) == null)
            loader = new MenuLoader();
        VaadinSession.getCurrent().setAttribute(MenuLoader.class, loader);
    }

    private void updateContent() {
        if (VaadinSession.getCurrent().getAttribute(TblUser.class) != null) {
            // Authenticated user
            removeStyleName("loginview");
            getNavigator().navigateTo(getNavigator().getState());
        } else {
            getNavigator().navigateTo(LoginPage.VIEW_NAME);
            addStyleName("loginview");
        }
    }
}
