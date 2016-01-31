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
import com.vaadin.ui.Upload;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.frontend.components.login.ComponentRegister;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Component for editing profile
 *
 * @author Frantisek Kolenak
 */
@Component
public class ComponentEditProfile extends ComponentRegister {

    private static final String INFO_UPDATED = "component.profile-edit.changes-saved";
    /**
     * Parent reference
     */
    private ComponentProfile parentReference;
    /**
     * User
     */
    private Users user;
    /**
     * Application context
     */
    @Autowired
    private ApplicationContext appContext;
    /**
     * Image uploader
     */
    private ImageUploader receiver;

    /**
     * Sets user
     *
     * @param user user
     */
    public void setUser(Users user) {
        this.user = user;
        this.setValues();
    }

    @Override
    @PostConstruct
    public void postConstruct() {
        receiver = appContext.getBean(ImageUploader.class);
        Upload upload = new Upload(msgs.getMessage("upload.image"), receiver);
        upload.setButtonCaption(msgs.getMessage("upload"));
        upload.addSucceededListener(receiver);

        upload.addFailedListener(new Upload.FailedListener() {

            @Override
            public void uploadFailed(Upload.FailedEvent event) {
                Notification.show(msgs.getMessage("upload.not.ok"));
            }
        });
        this.layout.addComponent(upload, "upload");
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

    /**
     * Sets values to the fields
     */
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
        usersService.update(user);

        ((MyUI) UI.getCurrent()).getHeader().setUsersFullName(user);

        Notification.show(msgs.getMessage(INFO_UPDATED), Notification.Type.ERROR_MESSAGE);
        resetValues();
        parentReference.swapComponents();
        parentReference.reload(user);
    }

    /**
     * Sets parent reference
     *
     * @param parentReference parent reference
     */
    public void setParentReference(ComponentProfile parentReference) {
        this.parentReference = parentReference;
        receiver.setParentReference(parentReference);

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
