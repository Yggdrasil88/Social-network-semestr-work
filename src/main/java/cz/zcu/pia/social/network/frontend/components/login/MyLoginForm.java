/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.login;

import com.ejt.vaadin.loginform.DefaultVerticalLoginForm;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class MyLoginForm extends DefaultVerticalLoginForm {

    private static final String INFO_USERNAME = "login.username";
    private static final String INFO_PASSWORD = "login.password";
    private static final String INFO_LOG_IN_BUTTON = "login.login";

    @Autowired
    private MessagesLoader msgs;

    @Override
    protected VerticalLayout createContent(TextField userNameField, PasswordField passwordField, Button loginButton) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);

        userNameField.setCaption(msgs.getMessage(INFO_USERNAME));
        passwordField.setCaption(msgs.getMessage(INFO_PASSWORD));
        loginButton.setCaption(msgs.getMessage(INFO_LOG_IN_BUTTON));

        layout.addComponent(userNameField);
        layout.addComponent(passwordField);
        layout.addComponent(loginButton);
        return layout;
    }
}
