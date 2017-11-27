/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.framework.impl.AbstractScreen;
import com.aribanilia.vaadin.loader.MenuLoader;
import com.aribanilia.vaadin.view.LandingView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = MainPage.VIEW_NAME)
public class MainPage extends HorizontalLayout implements View {
    private Panel content;
    private LandingView landingView;

    public static final String VIEW_NAME = "main";
    private static final Logger logger = LoggerFactory.getLogger(MainPage.class);

    TblUser user = VaadinSession.getCurrent().getAttribute(TblUser.class);

    @PostConstruct
    public void init() {
        setSizeFull();
        addStyleName("mainview");
        setSpacing(false);

        this.content = new Panel();
        this.landingView = new LandingView();
        content.addStyleName("view-content");
        content.setSizeFull();

        final MenuComponent menuComponent = new MenuComponent();
        addComponent(menuComponent);
        addComponent(content);
        setExpandRatio(content, 1.0f);
    }

    private boolean validateUserSession() {
        try {
            String sessionId = VaadinSession.getCurrent().getSession().getId();
            if (!LoginUtil.sessionCheck(user.getUsername(), sessionId)) {
                Notification.show("Anda telah keluar", "Anda Telah Keluar/Login dari Komputer Lain!", Notification.Type.HUMANIZED_MESSAGE);
                VaadinSession.getCurrent().close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (!validateUserSession()) {
            return;
        }
        if (event.getParameters() == null || event.getParameters().isEmpty()) {
            landingView.show();
            content.setContent(landingView);
        } else {
            try {
                MenuLoader menuLoader = VaadinSession.getCurrent().getAttribute(MenuLoader.class);
                AbstractScreen screen = menuLoader.getScreen(event.getParameters());
                screen.show();
                content.setContent(screen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
