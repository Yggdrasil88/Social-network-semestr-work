/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.login;

import com.ejt.vaadin.loginform.LoginForm.LoginEvent;
import com.ejt.vaadin.loginform.LoginForm.LoginListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.frontend.views.ViewHome;
import cz.zcu.pia.social.network.frontend.views.ViewRegister;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentLogin extends VerticalLayout {

    private static final String STYLE_BUTTON_LABEL = "button-label";
    private static final String INFO_REGISTER = "view.login.info.register";

    private static final String INFO_USERNAME = "login.username";
    private static final String INFO_PASSWORD = "login.password";
    private static final String INFO_LOG_IN_BUTTON = "login.login";

    @Autowired
    private MessagesLoader msgs;

    private TextField nickname = new TextField();
    private PasswordField password = new PasswordField();

    public ComponentLogin() {
        this.setSizeUndefined();
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
    
    private Button createRegisterButton(){
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
    
        private Button createLoginButton(){
        Button login = new Button(msgs.getMessage(INFO_LOG_IN_BUTTON));
        login.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((MyUI) UI.getCurrent().getUI()).getNavigator()
                    .navigateTo(ViewHome.NAME);
            }
        });
        
        return login;
    }
}
