/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework;

import com.aribanilia.vaadin.entity.TblMenu;
import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.loader.MenuLoader;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class MenuComponent extends CustomComponent {
    private MenuBar.MenuItem settingsItem;
    private HashMap<String, MenuItemComponent> hMenu = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(MenuComponent.class);
    private static final String ID = "menu";
    private static final String STYLE_VISIBLE = "valo-menu-visible";

    public MenuComponent() {
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
        return VaadinSession.getCurrent().getAttribute(TblUser.class);
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final TblUser user = getCurrentUser();
        settingsItem = settings.addItem("",
                new ThemeResource("img/profile-pic-300px.jpg"), null);
        settingsItem.addItem("Edit Profile", (MenuBar.Command) selectedItem -> {
//                ProfilePreferencesWindow.open(user, false);
            Notification.show("Edit Profile Clicked");
        });
        settingsItem.addItem("Preferences", (MenuBar.Command) selectedItem -> {
//                ProfilePreferencesWindow.open(user, true);
            Notification.show("Preferences Clicked");
        });
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", (MenuBar.Command) selectedItem -> {
            VaadinSession.getCurrent().setAttribute(TblUser.class, null);
            getUI().getNavigator().navigateTo(LoginPage.VIEW_NAME);
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
        menuItemsLayout.addStyleName(ValoTheme.MENU_ITEM);
        try {
            hMenu.clear();
            MenuLoader menuLoader = VaadinSession.getCurrent().getAttribute(MenuLoader.class);
            for (final TblMenu view : menuLoader.getAuthorizedMenu()) {
                AbstractScreen screen = menuLoader.getScreen(view.getMenuId());
                if (screen == null) {
                    logger.error("Screen Error : " + view.getMenuClass());
                    continue;
                }
                Button menu = new Button();
                menu.setPrimaryStyleName(ValoTheme.MENU_ITEM);
                menu.setCaption(view.getMenuName().substring(0, 1).toUpperCase()
                        + view.getMenuName().substring(1));
                if (view.getHaveChild().equals("1")) {
                    MenuItemComponent itemComponent = new MenuItemComponent(view.getMenuId());
                    hMenu.put(view.getMenuId(), itemComponent);
                } else {
                    MenuItemComponent itemComponent = hMenu.get(view.getParentId());

                    menu.addClickListener(event -> {
                        getUI().getNavigator().navigateTo(MainPage.VIEW_NAME + "/" + view.getMenuId());
                    });
                }
                menuItemsLayout.addComponent(menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return menuItemsLayout;

    }

    @Override
    public void attach() {
        super.attach();
    }

    private class MenuItemComponent extends CssLayout {
        private boolean isExpanded;

        public MenuItemComponent(String id) {
            super();
            setId(id);
            addStyleName(ValoTheme.MENU_ITEM);
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean visible) {
            isExpanded = visible;
        }
    }
}
