/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.login;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.frontend.views.ViewHome;
import cz.zcu.pia.social.network.frontend.views.ViewRegister;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import javax.annotation.PostConstruct;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Component for login
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentLogin extends VerticalLayout {

    private static final String STYLE_BUTTON_LABEL = "button-label";
    private static final String INFO_REGISTER = "view.login.info.register";

    private static final String INFO_USERNAME = "username";
    private static final String INFO_PASSWORD = "password";
    private static final String INFO_LOG_IN_BUTTON = "login.login";
    private static final String ERROR_NOT_CORRECT_LOGIN = "login.not-correct-login";
    /**
     * Messages helper
     */
    @Autowired
    private MessagesLoader msgs;
    /**
     * Users service
     */
    @Autowired
    private UsersService usersService;
    /**
     * Security helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * Nickname field
     */
    private final TextField nickname = new TextField();
    /**
     * Password field
     */
    private final PasswordField password = new PasswordField();
    /**
     * Constructor
     */
    public ComponentLogin() {
        this.setSizeUndefined();
        nickname.setRequired(true);
        password.setRequired(true);

        nickname.setNullRepresentation("");
        password.setNullRepresentation("");
    }

    /**
     * PostConstruct
     */
    @PostConstruct
    public void postConstruct() {

        CustomLayout content = new CustomLayout("login");
        content.setSizeUndefined();
        this.addComponent(content);

        nickname.setCaption(msgs.getMessage(INFO_USERNAME));
        password.setCaption(msgs.getMessage(INFO_PASSWORD));
        Button login = createLoginButton();

        Button infoRegister = createRegisterButton();
        content.addComponent(infoRegister, "register-button");

        content.addComponent(nickname, "username");
        content.addComponent(password, "password");
        content.addComponent(login, "okbutton");

    }
    /**
     * Creates register button
     * @return register button
     */
    private Button createRegisterButton() {
        Button register = new Button(msgs.getMessage(INFO_REGISTER));
        register.setStyleName(STYLE_BUTTON_LABEL);
        register.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {

                ((MyUI) UI.getCurrent().getUI()).getNavigator()
                    .navigateTo(ViewRegister.NAME);
            }
        });

        return register;
    }
    /**
     * Creates login button
     * @return login button
     */
    private Button createLoginButton() {
        Button login = new Button(msgs.getMessage(INFO_LOG_IN_BUTTON));
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (securityHelper.isAuthenticated()) {
                    ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewHome.NAME);
                    return;
                }
                Users user = usersService.getUserByUsername(nickname.getValue());
                if (user == null) {
                    Notification.show(msgs.getMessage(ERROR_NOT_CORRECT_LOGIN), Notification.Type.ERROR_MESSAGE);
                    return;
                }
                StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
                if (passwordEncryptor.checkPassword(password.getValue(), user.getPassword())) {
                    securityHelper.setLogedInUser(user);
                    ((MyUI) UI.getCurrent().getUI()).getHeader().setUsersFullName(user.getName() + " " + user.getSurname());
                    ((MyUI) UI.getCurrent().getUI()).getHeader().setLoginButtonCaption();
                    ((MyUI) UI.getCurrent().getUI()).getHeader().setSelectedMenuItem(msgs.getMessage("header.home"));
                    ((MyUI) UI.getCurrent().getUI()).getHeader().addLogedInMenu();
                    password.setValue("");
                    nickname.setValue("");
                    
                    ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewHome.NAME);
                    return;
                } else {
                    Notification.show(msgs.getMessage(ERROR_NOT_CORRECT_LOGIN), Notification.Type.ERROR_MESSAGE);
                    return;
                }

            }
        });

        return login;
    }
}
