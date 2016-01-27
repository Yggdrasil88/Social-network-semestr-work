/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile.profile;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.frontend.views.ViewHome;
import cz.zcu.pia.social.network.frontend.views.ViewProfile;
import cz.zcu.pia.social.network.helpers.Constants;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.io.File;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Component profile
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentProfile extends VerticalLayout {

    private static Logger logger = LoggerFactory.getLogger(ComponentProfile.class);

    private final static int LABEL_WIDTH = 150;
    private final static int COMPONENT_WIDTH = ViewProfile.COMPONENT_WIDTH;
    /**
     * Messages helper
     */
    @Autowired
    private MessagesLoader msgs;
    /**
     * Component layout
     */
    private final CustomLayout layout = new CustomLayout("profile");
    /**
     * Fullname layout
     */
    private Label fullname;
    /**
     * Username  layout
     */
    private Label username;
    /**
     * number of posts  layout
     */
    private Label numberOfposts;
    /**
     * number of followers  layout
     */
    private Label numberOfFollowers;
    /**
     * edit button
     */
    private Button editButton;
    /**
     * User profile picture
     */
    private final Embedded image = new Embedded();
    /**
     * Users Service
     */
    @Autowired
    private UsersService usersService;
    /**
     * Security Helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * parent reference
     */
    @Autowired
    private ComponentEditProfile editProfile;

    /**
     * Application Context
     */
    @Autowired
    private ApplicationContext appContext;

    /**
     * Constructor
     */
    public ComponentProfile() {
        this.setSizeUndefined();
        this.setWidth(COMPONENT_WIDTH, Unit.PIXELS);
        this.setStyleName("text-align-center");
        this.setMargin(true);
        this.addComponent(layout);

        layout.setSizeUndefined();
        image.setVisible(false);
        image.setWidth(150, Unit.PIXELS);
        image.setHeight(150, Unit.PIXELS);
    }
    /**
     * Post Construct
     */
    @PostConstruct
    public void postConstruct() {
        if (!securityHelper.isAuthenticated()) {
            ((MyUI) UI.getCurrent().getUI()).getNavigator()
                .navigateTo(ViewHome.NAME);
            return;
        }
        Users user = usersService.getUserByUsername(securityHelper.getLogedInUser().getUsername());
        if (user == null) {
            return;
        }

        this.addComponent(editProfile);
        editProfile.setUser(user);
        editProfile.setVisible(false);
        editProfile.setParentReference(this);

        setLabels(user);
        editButton = new Button(msgs.getMessage("edit"));
        editButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                buttonEditFunction(event);
            }
        });

        if (securityHelper.getLogedInUser().getUserImageName() != null) {
            reloadImage();

        } else {
            ImageUploader receiver = appContext.getBean(ImageUploader.class);
            receiver.setParentReference(this);
            Upload upload = new Upload(msgs.getMessage("upload.image"), receiver);
            upload.setButtonCaption(msgs.getMessage("upload"));
            upload.addSucceededListener(receiver);

            upload.addFailedListener(new Upload.FailedListener() {

                @Override
                public void uploadFailed(Upload.FailedEvent event) {
                    Notification.show(msgs.getMessage("upload.not.ok"));
                }
            });
            layout.addComponent(upload, "picture");

        }

        fullname = new Label(user.getName() + " " + user.getSurname());
        layout.addComponent(fullname, "name");

        Label usernameLabel = new Label(msgs.getMessage("username") + ":");
        Label postsLabel = new Label(msgs.getMessage("number-of-posts") + ":");
        Label followersLabel = new Label(msgs.getMessage("number-of-followers") + ":");

        usernameLabel.setWidth(LABEL_WIDTH, Unit.PIXELS);
        postsLabel.setWidth(LABEL_WIDTH, Unit.PIXELS);
        followersLabel.setWidth(LABEL_WIDTH, Unit.PIXELS);

        layout.addComponent(usernameLabel, "usernameLabel");
        layout.addComponent(postsLabel, "numberPostsLabel");
        layout.addComponent(followersLabel, "numberFollowersLabel");

        layout.addComponent(username, "username");
        layout.addComponent(numberOfposts, "numberPosts");
        layout.addComponent(numberOfFollowers, "numberFollowers");
        layout.addComponent(editButton, "button");
    }
    /**
     * Edit button function
     * @param event  click event
     */
    private void buttonEditFunction(Button.ClickEvent event) {
        swapComponents();
    }
    /**
     * Swaps component profile and component edit profile
     */
    public void swapComponents() {
        if (layout.isVisible()) {
            layout.setVisible(false);
            editProfile.setVisible(true);
        } else {
            editProfile.setVisible(false);
            layout.setVisible(true);
        }
    }
    /**
     * Sets labels
     * @param user user 
     */
    private void setLabels(Users user) {

        username = new Label(user.getUsername());
        numberOfposts = new Label("" + user.getTotalPosts());
        numberOfFollowers = new Label("" + user.getTotalFollowers());

    }
    /**
     * Reloads users informations
     * @param user user
     */
    public void reload(Users user) {
        fullname.setValue(user.getName() + " " + user.getSurname());
        username.setValue(user.getUsername());

    }
    /**
     * Reload user profile image
     */
    public void reloadImage() {
        image.setVisible(true);
        
        image.setSource(new FileResource(new File(Constants.BASE_PATH_RESIZED + securityHelper.getLogedInUser().getUserImageName())));
        layout.addComponent(image, "picture");
    }

}
