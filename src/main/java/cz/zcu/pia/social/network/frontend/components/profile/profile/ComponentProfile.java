/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile.profile;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.frontend.views.ViewHome;
import cz.zcu.pia.social.network.frontend.views.ViewProfile;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentProfile extends VerticalLayout {

    private final static int LABEL_WIDTH = 150;
    private final static int COMPONENT_WIDTH = ViewProfile.COMPONENT_WIDTH;

    @Autowired
    private MessagesLoader msgs;
    private final CustomLayout layout = new CustomLayout("profile");

    private Label fullname;
    private Label username;
    private Label numberOfposts;
    private Label numberOfFollowers;
    private Button editButton;

    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityHelper securityHelper;
    @Autowired
    private ComponentEditProfile editProfile;
    
    @Autowired
    private ApplicationContext appContext;

    
    public ComponentProfile() {
        this.setSizeUndefined();
        this.setWidth(COMPONENT_WIDTH, Unit.PIXELS);
        this.setStyleName("text-align-center");
        this.setMargin(true);
        this.addComponent(layout);

        layout.setSizeUndefined();
    }

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

        Label picture = new Label("IF  picture is required, picture here");
        picture.setWidth(100, Unit.PIXELS);
        picture.setHeight(100, Unit.PIXELS);
        layout.addComponent(picture, "picture");
        
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

    private void buttonEditFunction(Button.ClickEvent event) {
        swapComponents();
    }
    
    public void swapComponents(){
        if (layout.isVisible()) {
            layout.setVisible(false);
            editProfile.setVisible(true);
        } else {
            editProfile.setVisible(false);
            layout.setVisible(true);
        }
    }

    private void setLabels(Users user) {

        username = new Label(user.getUsername());
        numberOfposts = new Label("" + user.getTotalPosts());
        numberOfFollowers = new Label("" + user.getTotalFollowers());

    }

    public void reload(Users user) {
       fullname.setValue(user.getName() + " " + user.getSurname());
       username.setValue(user.getUsername());
    }
}
