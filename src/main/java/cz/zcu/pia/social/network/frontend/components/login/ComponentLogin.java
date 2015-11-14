/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.login;

import com.ejt.vaadin.loginform.LoginForm.LoginEvent;
import com.ejt.vaadin.loginform.LoginForm.LoginListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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

    @Autowired
    private MessagesLoader msgs;

    @Autowired
    private MyLoginForm loginForm;

    public ComponentLogin() {
        this.setSizeUndefined();
    }

    /**
     * PostConstruct
     */
    @PostConstruct
    public void postConstruct() {
        HorizontalLayout top = new HorizontalLayout();
        Button infoRegister = new Button(msgs.getMessage(INFO_REGISTER));
        infoRegister.setCaptionAsHtml(true);

        infoRegister.setStyleName(STYLE_BUTTON_LABEL);
        top.addComponent(infoRegister);
        infoRegister.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((MyUI) UI.getCurrent().getUI()).getNavigator()
                    .navigateTo(ViewRegister.NAME);
            }
        });

        this.addComponent(top);

        loginForm.addLoginListener(new LoginListener() {
            @Override
            public void onLogin(LoginEvent event) {
                ((MyUI) getUI()).getHeader().setUsersFullName(event.getUserName());

                ((MyUI) UI.getCurrent().getUI()).getNavigator()
                    .navigateTo(ViewHome.NAME);

            }
        });

        this.addComponent(loginForm);
    }
}
