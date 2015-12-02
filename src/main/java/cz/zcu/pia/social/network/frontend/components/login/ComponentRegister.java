/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.login;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ComponentRegister extends FormLayout {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_REPEAT = "component.register.password-repeat";
    public static final String VALIDATION = "component.register.validation";
    public static final String CONFIRM = "component.register.confirm";

    public static final String ERROR_EMPTY = "component.register.error-empty";

    private FieldGroup group;
    @Autowired
    private MessagesLoader msgs;

    private TextField name;
    private TextField surname;
    private TextField username;
    private PasswordField password;
    private PasswordField passwordRepeat;
    private TextField validation;
    private Button confirm;

    public ComponentRegister() {
        this.setSpacing(true);
        this.setSpacing(true);
        this.setSizeUndefined();
    }

    @PostConstruct
    public void postConstruct() {

        createFieldGroup();

        basicFieldSettings();
        addValidators();
        
        CustomLayout layout = new CustomLayout("register");
        
        layout.addComponent(name, "name");
        layout.addComponent(surname, "surname");
        layout.addComponent(username, "username");
        layout.addComponent(password, "password");
        layout.addComponent(passwordRepeat, "password2");
        layout.addComponent(validation, "validation");
        

        confirm = new Button(msgs.getMessage(CONFIRM));
        layout.addComponent(confirm, "okbutton");

        confirm.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                confirmButtonFunction(event);
            }

        });
        
        this.addComponent(layout);
    }

    private void confirmButtonFunction(Button.ClickEvent event) {
        try {
            group.commit();
        } catch (FieldGroup.CommitException ex) {
            Notification.show(msgs.getMessage(ERROR_EMPTY), Notification.Type.ERROR_MESSAGE);
        }
    }

    private void createFieldGroup() {
        BeanItem<RegisterBean> item = new BeanItem<>(new RegisterBean());
        group = new FieldGroup(item);
              
        name = group.buildAndBind(msgs.getMessage(NAME), "name", TextField.class);
        surname = group.buildAndBind(msgs.getMessage(SURNAME), "surname", TextField.class);
        username = group.buildAndBind(msgs.getMessage(USERNAME), "username", TextField.class);
        password = group.buildAndBind(msgs.getMessage(PASSWORD), "password", PasswordField.class);
        passwordRepeat =  group.buildAndBind(msgs.getMessage(PASSWORD_REPEAT), "passwordRepeat", PasswordField.class);
        validation = group.buildAndBind(msgs.getMessage(VALIDATION), "validation", TextField.class);
    }

    private void basicFieldSettings() {
        name.setNullRepresentation("");
        surname.setNullRepresentation("");
        username.setNullRepresentation("");
        password.setNullRepresentation("");
        passwordRepeat.setNullRepresentation("");
        validation.setNullRepresentation("");
    }

    private void addValidators() {
        name.addValidator(new BeanValidator(RegisterBean.class, "name"));
        surname.addValidator(new BeanValidator(RegisterBean.class, "surname"));
        username.addValidator(new BeanValidator(RegisterBean.class, "username"));
        password.addValidator(new BeanValidator(RegisterBean.class, "password"));
        passwordRepeat.addValidator(new BeanValidator(RegisterBean.class, "passwordRepeat"));
        validation.addValidator(new BeanValidator(RegisterBean.class, "validation"));
    }
}
