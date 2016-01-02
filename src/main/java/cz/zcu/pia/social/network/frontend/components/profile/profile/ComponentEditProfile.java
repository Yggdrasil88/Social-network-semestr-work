/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile.profile;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.frontend.components.login.ComponentRegister;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
public class ComponentEditProfile extends ComponentRegister {

    private static final String INFO_UPDATED = "component.profile-edit.changes-saved";
    private ComponentProfile parentReference;
    private Users user;

    @Autowired
    private SecurityHelper securityHelper;

    public void setUser(Users user) {
        this.user = user;
        this.setValues();
    }

    @Override
    @PostConstruct
    public void postConstruct() {
        super.postConstruct();
        this.password.setVisible(false);
        this.passwordRepeat.setVisible(false);
        this.validation.setVisible(false);

    }

    @Override
    protected HorizontalLayout getRegisterButton() {
        HorizontalLayout wrapper = new HorizontalLayout();

        confirm = new Button(msgs.getMessage("save"));
        confirm.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        confirm.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                confirmButtonFunction(event);
            }

        });
        Button swapButton = new Button(msgs.getMessage("back"));
        swapButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                resetValues();
                parentReference.swapComponents();
            }
        });

        wrapper.addComponent(swapButton);
        wrapper.addComponent(confirm);

        wrapper.setSpacing(true);
        return wrapper;
    }

    private void setValues() {
        bean.setName(user.getName());
        bean.setSurname(this.user.getSurname());
        bean.setUsername(this.user.getUsername());
        bean.setPassword("valid");
        bean.setPasswordRepeat("valid");
        bean.setValidation(TURRING_TEST);
    }

    @Override
    protected void proccesValues() {
        user.setName(bean.getName());
        user.setSurname(bean.getSurname());
        user.setUsername(bean.getUsername());
        
        ( (MyUI)UI.getCurrent()).getHeader().setUsersFullName(user);
        
        usersService.update(user);
        Notification.show(msgs.getMessage(INFO_UPDATED), Notification.Type.ERROR_MESSAGE);
        resetValues();
        parentReference.swapComponents();
        parentReference.reload(user);
    }

    public void setParentReference(ComponentProfile parentReference) {
        this.parentReference = parentReference;
    }

    @Override
    protected boolean isValid() {
        if (!bean.getValidation().equals(TURRING_TEST)) {
            Notification.show(msgs.getMessage(ERROR_VALIDATION), Notification.Type.ERROR_MESSAGE);
            return false;
        }
        if (!bean.getPassword().equals(bean.getPasswordRepeat())) {
            Notification.show(msgs.getMessage(ERROR_PASSWORDS_DOESNT_MATCH), Notification.Type.ERROR_MESSAGE);
            return false;
        }
        if (!bean.getUsername().equals(user.getUsername())) {
            if (usersService.getUserByUsername(bean.getUsername()) != null) {
                Notification.show(msgs.getMessage(ERROR_USERNAME_USED), Notification.Type.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }
}
