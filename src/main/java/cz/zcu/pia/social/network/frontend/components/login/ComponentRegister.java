/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.login;

import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.frontend.views.ViewLogin;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.jasypt.util.password.StrongPasswordEncryptor;
/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentRegister extends FormLayout {

    public static final String TURRING_TEST = "dva";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_REPEAT = "component.register.password-repeat";
    public static final String VALIDATION = "component.register.validation";
    public static final String CONFIRM = "component.register.confirm";

    public static final String ERROR_EMPTY = "component.register.error-empty";
    public static final String REQUIRED_ERROR = "component.register.error-required";
    public static final String ERROR_USERNAME_USED = "component.register.error-username-used";
    public static final String ERROR_PASSWORDS_DOESNT_MATCH = "component.register.error-passwords-doesnt-match";
    public static final String ERROR_VALIDATION = "component.register.error-validation";
    public static final String ERROR_RANGE = "component.register.error-range";
    public static final String REQUIRED_ERROR_NOT_EMPTY = "component.register.error-empty-cannot";
    
    public static final String INFO_REGISTERED = "component.register.info-registered";

    private FieldGroup group;
    private RegisterBean bean = new RegisterBean();
    @Autowired
    private MessagesLoader msgs;

    @Autowired
    private UsersService usersService;

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
        name.focus();
    }

    private void confirmButtonFunction(Button.ClickEvent event) {
        try {
            if (!group.isModified()) {
                return;
            }
            group.commit();
            if (!isValid()) {
                return;
            }
            proccesValues();
        } catch (Validator.EmptyValueException ex) {
            Notification.show(msgs.getMessage(ERROR_EMPTY), Notification.Type.ERROR_MESSAGE);
        } catch (Validator.InvalidValueException ex) {
            Notification.show(msgs.getMessage(ERROR_RANGE), Notification.Type.ERROR_MESSAGE);

        } catch (FieldGroup.CommitException e) {
            HashMap<Field<?>, Validator.InvalidValueException> map = (HashMap<Field<?>, Validator.InvalidValueException>) e.getInvalidFields();
            
            
            for (Validator.InvalidValueException ee : map.values()) {
                if (ee.getMessage().equals(msgs.getMessage(REQUIRED_ERROR))) {
                    Notification.show(msgs.getMessage(REQUIRED_ERROR_NOT_EMPTY), Notification.Type.ERROR_MESSAGE);
                    return;
                }

            }

        }
    }

    private boolean isValid() {
        if (!bean.getValidation().equals(TURRING_TEST)) {
            Notification.show(msgs.getMessage(ERROR_VALIDATION), Notification.Type.ERROR_MESSAGE);
            return false;
        }
        if (!bean.getPassword().equals(bean.getPasswordRepeat())) {
            Notification.show(msgs.getMessage(ERROR_PASSWORDS_DOESNT_MATCH), Notification.Type.ERROR_MESSAGE);
            return false;
        }
        if (usersService.getUserByUsername(bean.getUsername()) != null) {
            Notification.show(msgs.getMessage(ERROR_USERNAME_USED), Notification.Type.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void proccesValues() {
        Users user = new Users();
        user.setName(bean.getName());
        user.setSurname(bean.getSurname());
        user.setUsername(bean.getSurname());
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(bean.getPassword());
        user.setPassword(encryptedPassword);
        
        usersService.persist(user);
        Notification.show(msgs.getMessage(INFO_REGISTERED), Notification.Type.ERROR_MESSAGE);
        resetValues();
        UI.getCurrent().getNavigator().navigateTo(ViewLogin.NAME);
    }
    
    private void resetValues(){
        bean.setName("");
        bean.setPassword("");
        bean.setPasswordRepeat("");
        bean.setSurname("");
        bean.setUsername("");
        bean.setValidation("");
        
    }

    private void createFieldGroup() {
        BeanItem<RegisterBean> item = new BeanItem<>(bean);

        group = new FieldGroup(item);
        group.setItemDataSource(new BeanItem<RegisterBean>(bean));

        name = group.buildAndBind(msgs.getMessage(NAME), "name", TextField.class);
        surname = group.buildAndBind(msgs.getMessage(SURNAME), "surname", TextField.class);
        username = group.buildAndBind(msgs.getMessage(USERNAME), "username", TextField.class);
        password = group.buildAndBind(msgs.getMessage(PASSWORD), "password", PasswordField.class);
        passwordRepeat = group.buildAndBind(msgs.getMessage(PASSWORD_REPEAT), "passwordRepeat", PasswordField.class);
        validation = group.buildAndBind(msgs.getMessage(VALIDATION), "validation", TextField.class);
    }

    private void basicFieldSettings() {
        name.setNullRepresentation("");
        surname.setNullRepresentation("");
        username.setNullRepresentation("");
        password.setNullRepresentation("");
        passwordRepeat.setNullRepresentation("");
        validation.setNullRepresentation("");

        name.setRequired(true);
        surname.setRequired(true);
        username.setRequired(true);
        password.setRequired(true);
        passwordRepeat.setRequired(true);
        validation.setRequired(true);

        name.setMaxLength(20);
        surname.setMaxLength(20);
        username.setMaxLength(20);
        password.setMaxLength(25);
        passwordRepeat.setMaxLength(25);
        validation.setMaxLength(10);

        name.setRequiredError(msgs.getMessage(REQUIRED_ERROR));
        surname.setRequiredError(msgs.getMessage(REQUIRED_ERROR));
        username.setRequiredError(msgs.getMessage(REQUIRED_ERROR));
        password.setRequiredError(msgs.getMessage(REQUIRED_ERROR));
        passwordRepeat.setRequiredError(msgs.getMessage(REQUIRED_ERROR));
        validation.setRequiredError(msgs.getMessage(REQUIRED_ERROR));
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
