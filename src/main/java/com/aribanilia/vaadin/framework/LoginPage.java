/*
 * Copyright (c) 2017.
 */

package com.aribanilia.vaadin.framework;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.framework.db.hibernate.Session;
import com.aribanilia.vaadin.framework.db.hibernate.Transaction;
import com.aribanilia.vaadin.framework.db.plugin.PersistentPlugin;
import com.aribanilia.vaadin.loader.MenuLoader;
import com.aribanilia.vaadin.loader.ParamLoader;
import com.aribanilia.vaadin.util.ValidationHelper;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Date;

@UIScope
@SpringView(name = LoginPage.VIEW_NAME)
public class LoginPage extends VerticalLayout implements View {
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Button btnLogin;

    public static final String VIEW_NAME = "login";
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    @PostConstruct
    public void init() {
        setSizeFull();
        setMargin(false);
        setSpacing(true);
        Responsive.makeResponsive(this);

        Component loginForm = initComponent();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        Notification notification = new Notification(
                "Selamat Datang ke Aplikasi Vaadin Spring");
        notification
                .setDescription("<span>Untuk mendapatkan <b>Username</b> & <b>Password</b> dapat menghubungi : <a href=\"https://twitter.com/ivan_j4u\">Ivan Aribanilia</a> .</span>" +
                        "<span> Terima kasih </span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("tray dark small closable login-help");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(20000);
        notification.show(Page.getCurrent());
    }

    private Component initComponent() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setMargin(false);
        loginPanel.addStyleName(ValoTheme.PANEL_WELL);
        Responsive.makeResponsive(loginPanel);

        loginPanel.addComponent(initDetail());
        return loginPanel;
    }

    private Component initDetail() {
        VerticalLayout layout = new VerticalLayout();

        Label title = new Label("Simple Vaadin Application");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        layout.addComponent(title, 0);

        layout.addComponent(txtUsername = new TextField("Username"));
        txtUsername.setIcon(FontAwesome.USER);
        txtUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        layout.addComponent(txtPassword = new PasswordField("Password"));
        txtPassword.setIcon(FontAwesome.LOCK);
        txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        layout.addComponent(btnLogin = new Button("Login"));
        btnLogin.addClickListener(event -> {
            if (ValidationHelper.validateRequired(txtUsername)
                    && ValidationHelper.validateRequired(txtPassword)) {
                try {
                    attemptLogin(txtUsername.getValue(), txtPassword.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        });
        btnLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        btnLogin.focus();

        return layout;
    }

    private void attemptLogin(String username, String password) {
        try {
            Session session = null;
            try {
                session = PersistentPlugin.getSessionFactory().openSession();
                TblUser user = (TblUser) session.get(TblUser.class, username);
                if (user != null) {
                    // Cek Password
                    if (user.getPassword().equals(password)) {
                        if (checkLogin(session, user, true)) {
                            // Set Authorized Menu
                            getSession().getAttribute(MenuLoader.class).setAuthorizedMenu(session, user);
                            // Set User pada Session
                            getSession().setAttribute(TblUser.class, user);
                            // Reset Field
                            resetField();
                            // Navigate to MainPage
                            getUI().getNavigator().navigateTo(MainPage.VIEW_NAME);
                        } else {
                            logger.error("Status User : " + user.getUsername() + " tidak benar!");
                            showFailedLogin("Status User : " + user.getUsername() + " tidak benar!");
                        }
                    } else {
                        // Cek maksimal salah password
                        checkLogin(session, user, false);
                        showFailedLogin("Login Gagal!. Username atau Password yang anda masukkan salah!");
                    }
                } else {
                    logger.error("User : " + txtUsername.getValue() + " tidak ditemukan!");
                    showFailedLogin("User : " + txtUsername.getValue() + " tidak ditemukan!");
                }
            } catch (Exception e) {
                if (session != null && session.isOpen())
                    session.close();
                e.printStackTrace();
                logger.error(e.getMessage());
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            showFailedLogin("Login Gagal!. Silahkan hubungi administrator!");
        }
    }

    private boolean checkLogin(Session session, TblUser user, boolean valid) {
        Date now = new Date();
        boolean b = user.getStartTime().getTime() <= now.getTime();
        b = b && now.getTime() <= user.getEndTime().getTime();
        b = b && (user.getLoginFailCount() != null ? user.getLoginFailCount() : 0)
                <= Integer.parseInt(ParamLoader.getParam("MAX.LOGIN.FAIL"));
        b = b && user.getStatus() != null && user.getStatus().equals("1");
        if (!b) {
            showFailedLogin("Peringatan, Profil anda diblokir atau tidak aktif. Silahkan hubungi administrator!");
            valid = false;
        }
        Transaction trx = null;
        try {
            trx = session.beginTransaction();
            String sessionid = getSession().getSession().getId();
            if (valid) {
                user.setLoginFailCount(0);
                user.setLastLogin(now);
                LoginUtil.sessionLogin(session, user.getUsername(), sessionid, getUI().getPage().getWebBrowser().getAddress());
            } else {
                user.setLoginFailCount(user.getLoginFailCount() + 1);
            }
            if (now.getTime() > user.getEndTime().getTime() && user.getStatus().equals("1")) {
                // set status nonaktif
                user.setStatus("0");
            }
            session.update(user);
            trx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trx != null)
                trx.rollback();
            throw e;
        }
        return valid;
    }

    private void showFailedLogin(String message) {
        resetField();
        Notification.show(message, Notification.Type.ERROR_MESSAGE);
    }

    private void resetField() {
        txtUsername.setValue("");
        txtPassword.setValue("");
    }

}
