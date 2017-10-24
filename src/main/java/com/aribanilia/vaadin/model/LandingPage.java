/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.model;

import com.aribanilia.vaadin.entity.TblMenu;
import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.loader.MenuLoader;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = LandingPage.VIEW_NAME)
public class LandingPage extends CustomComponent implements View {
    private TblUser user;
    public static final String VIEW_NAME = "landing";
    private static final Logger logger = LoggerFactory.getLogger(LandingPage.class);

    public static final String ID = "dashboard-menu";
    public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private Label notificationsBadge;
    private Label reportsBadge;
    private MenuBar.MenuItem settingsItem;

    @Autowired
    public LandingPage() {
        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("QuickTickets <strong>Dashboard</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);
        return logoWrapper;
    }

    private TblUser getCurrentUser() {
        return (TblUser) VaadinSession.getCurrent()
                .getAttribute(TblUser.class.getName());
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final TblUser user = getCurrentUser();
        settingsItem = settings.addItem("",
                new ThemeResource("img/profile-pic-300px.jpg"), null);
        settingsItem.addItem("Edit Profile", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
//                ProfilePreferencesWindow.open(user, false);
                Notification.show("Edit Profile Clicked");
            }
        });
        settingsItem.addItem("Preferences", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
//                ProfilePreferencesWindow.open(user, true);
                Notification.show("Preferences Clicked");
            }
        });
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                VaadinSession.getCurrent().setAttribute(TblUser.class.getName(), null);
                getUI().getNavigator().navigateTo(LoginPage.VIEW_NAME);
            }
        });
        return settings;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", event -> {
            if (getCompositionRoot().getStyleName()
                    .contains(STYLE_VISIBLE)) {
                getCompositionRoot().removeStyleName(STYLE_VISIBLE);
            } else {
                getCompositionRoot().addStyleName(STYLE_VISIBLE);
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        MenuLoader loader = (MenuLoader) VaadinSession.getCurrent().getAttribute(MenuLoader.class.getName());
        try {
            for (final TblMenu view : loader.getAuthorizedMenu()) {
                AbstractScreen screen = loader.getScreen(view.getMenuId());
                if (screen == null) {
                    logger.error("Screen Error : " + view.getMenuClass());
                    continue;
                }
                getUI().getNavigator().addView(view.getMenuId(), screen);
                Component menuItemComponent = new ValoMenuItemButton(view);
                menuItemsLayout.addComponent(menuItemComponent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return menuItemsLayout;

    }

    private Component buildBadgeWrapper(final Component menuItemButton,
                                        final Component badgeLabel) {
        CssLayout dashboardWrapper = new CssLayout(menuItemButton);
        dashboardWrapper.addStyleName("badgewrapper");
        dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
        badgeLabel.setWidthUndefined();
        badgeLabel.setVisible(false);
        dashboardWrapper.addComponent(badgeLabel);
        return dashboardWrapper;
    }

    @Override
    public void attach() {
        super.attach();
    }

    public final class ValoMenuItemButton extends Button {
        private final TblMenu view;

        public ValoMenuItemButton(final TblMenu view) {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setCaption(view.getMenuName().substring(0, 1).toUpperCase()
                    + view.getMenuName().substring(1));
            addClickListener(event -> {
                UI.getCurrent().getNavigator()
                        .navigateTo(view.getMenuId());
            });

        }

    }
}
