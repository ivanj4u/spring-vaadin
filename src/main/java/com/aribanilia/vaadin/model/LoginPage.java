package com.aribanilia.vaadin.model;

import com.aribanilia.vaadin.entity.TblUser;
import com.aribanilia.vaadin.service.UserServices;
import com.aribanilia.vaadin.util.VConstants;
import com.aribanilia.vaadin.util.VaadinValidation;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = LoginPage.VIEW_NAME)
public class LoginPage extends VerticalLayout implements View {

    public static final String VIEW_NAME = "login";
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    @Autowired
    public LoginPage(UserServices servicesUser) {
        Panel panel = new Panel("Login");
        panel.setSizeUndefined();
        addComponent(panel);

        FormLayout content = new FormLayout();
        TextField txtUsername = new TextField("Username");
        content.addComponent(txtUsername);
        PasswordField txtPassword = new PasswordField("Password");
        content.addComponent(txtPassword);

        Button btnLogin = new Button("Login", event -> {
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

        content.addComponent(btnLogin);
        content.setSizeUndefined();
        content.setMargin(true);

        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
    }

}
