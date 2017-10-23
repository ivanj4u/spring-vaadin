package com.aribanilia.vaadin.model;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.service.UserServices;
import com.aribanilia.vaadin.util.VConstants;
import com.aribanilia.vaadin.util.VaadinValidation;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = LoginPage.VIEW_NAME)
public class LoginPage extends VerticalLayout implements View {

    private UserServices servicesUser;

    public static final String VIEW_NAME = "login";
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    @Autowired
    public LoginPage(UserServices servicesUser) {
        this.servicesUser = servicesUser;
        setSizeFull();
        setMargin(false);
        setSpacing(true);

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

        final TextField txtUsername = new TextField("Username");
        txtUsername.setIcon(FontAwesome.USER);
        txtUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField txtPassword = new PasswordField("Password");
        txtPassword.setIcon(FontAwesome.LOCK);
        txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button btnLogin = new Button("Login", event -> {
            if (VaadinValidation.validateRequired(txtUsername)
                    && VaadinValidation.validateRequired(txtPassword)) {
                TblUser user = servicesUser.login(txtUsername.getValue(), txtPassword.getValue());
                if (user != null) {
                    if (VConstants.STATUS_USER.ACTIVE.equals(user.getStatus())) {
                        VaadinSession.getCurrent().setAttribute("user", user);
                        LandingPage landing = new LandingPage();
                        getUI().getNavigator().addView(LandingPage.VIEW_NAME, landing);
                        getUI().getNavigator().navigateTo(LandingPage.VIEW_NAME);
                    } else {
                        logger.error("Status User : " + user.getUsername() + " tidak benar!");
                        Notification.show("Status User : " + user.getUsername() + " tidak benar!", Notification.Type.ERROR_MESSAGE);
                    }
                } else {
                    logger.error("User : " + user.getUsername() + " tidak ditemukan!");
                    Notification.show("User : " + user.getUsername() + " tidak ditemukan!", Notification.Type.ERROR_MESSAGE);
                }
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

}
