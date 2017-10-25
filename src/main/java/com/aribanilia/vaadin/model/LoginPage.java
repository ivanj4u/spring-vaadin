package com.aribanilia.vaadin.model;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.framework.HibernateUtil;
import com.aribanilia.vaadin.framework.LoginUtil;
import com.aribanilia.vaadin.loader.MenuLoader;
import com.aribanilia.vaadin.loader.ParamLoader;
import com.aribanilia.vaadin.util.Constants;
import com.aribanilia.vaadin.util.ValidationHelper;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@SpringView(name = LoginPage.VIEW_NAME)
public class LoginPage extends VerticalLayout implements View {
    private TextField txtUsername;
    private PasswordField txtPassword;

    public static final String VIEW_NAME = "login";
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage() {
        setSizeFull();
        setMargin(false);
        setSpacing(true);
        Responsive.makeResponsive(this);

        Component loginForm = buildLoginForm();
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

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setMargin(false);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
        return loginPanel;
    }

    private Component buildFields() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addStyleName("fields");

        txtUsername = new TextField("Username");
        txtUsername.setIcon(FontAwesome.USER);
        txtUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        txtPassword = new PasswordField("Password");
        txtPassword.setIcon(FontAwesome.LOCK);
        txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button btnLogin = new Button("Login", event -> {
            if (ValidationHelper.validateRequired(txtUsername)
                    && ValidationHelper.validateRequired(txtPassword)) {
                attemptLogin(txtUsername.getValue(), txtPassword.getValue());
            }
        });
        btnLogin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        btnLogin.focus();

        layout.addComponents(txtUsername, txtPassword, btnLogin);
        layout.setComponentAlignment(btnLogin, Alignment.BOTTOM_LEFT);

        return layout;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Selamat Datang");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

//        Label title = new Label("QuickTickets Dashboard");
//        title.setSizeUndefined();
//        title.addStyleName(ValoTheme.LABEL_H3);
//        title.addStyleName(ValoTheme.LABEL_LIGHT);
//        labels.addComponent(title);
        return labels;
    }

    private void attemptLogin(String username, String password) {
        try {
            Session session = null;
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                TblUser user = session.get(TblUser.class, username);
                if (user != null) {
                    // Cek Password
                    if (user.getPassword().equals(password)) {
                        if (checkLogin(session, user, true)) {
                            // Set Authorized Menu
                            VaadinSession.getCurrent().getAttribute(MenuLoader.class).setAuthorizedMenu(session, user);
                            // Set User pada Session
                            VaadinSession.getCurrent().setAttribute(TblUser.class, user);
                            LandingPage landing = new LandingPage();
                            getUI().getNavigator().addView(LandingPage.VIEW_NAME, landing);
                            getUI().getNavigator().navigateTo(LandingPage.VIEW_NAME);
                        } else {
                            logger.error("Status User : " + user.getUsername() + " tidak benar!");
                            failedLogin("Status User : " + user.getUsername() + " tidak benar!");
                        }
                    } else {
                        // Cek maksimal salah password
                        checkLogin(session, user, false);
                        failedLogin("Login Gagal!. Username atau Password yang anda masukkan salah!");
                    }
                } else {
                    logger.error("User : " + user.getUsername() + " tidak ditemukan!");
                    failedLogin("User : " + user.getUsername() + " tidak ditemukan!");
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
            failedLogin("Login Gagal!. Silahkan hubungi administrator!");
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
            failedLogin("Peringatan, Profil anda diblokir atau tidak aktif. Silahkan hubungi administrator!");
            valid = false;
        }
        Transaction trx = null;
        try {
            trx = session.beginTransaction();
            String sessionid = VaadinSession.getCurrent().getSession().getId();
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
        }
        return valid;
    }

    private void failedLogin(String message) {
        txtUsername.setValue("");
        txtPassword.setValue("");
        Notification.show(message, Notification.Type.ERROR_MESSAGE);
    }

}
