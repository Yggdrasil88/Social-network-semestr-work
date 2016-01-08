/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile.profile;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.backend.entities.Following;
import cz.zcu.pia.social.network.backend.entities.FriendRequest;
import cz.zcu.pia.social.network.backend.entities.Friends;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.services.impl.FollowingService;
import cz.zcu.pia.social.network.backend.services.services.impl.FriendRequestService;
import cz.zcu.pia.social.network.backend.services.services.impl.FriendsService;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.helpers.Constants;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.io.File;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Component profile, this is after clicking on post on user name
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentProfilePost extends VerticalLayout {

    private static Logger logger = LoggerFactory.getLogger(ComponentProfilePost.class);

    private final static int LABEL_WIDTH = 150;
    /**
     * Users profile picture
     */
    private final Embedded image = new Embedded();
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
     * Fullname
     */
    private Label fullname;
    /**
     * Username
     */
    private Label username;
    /**
     * number of posts
     */
    private Label numberOfposts;
    /**
     * number of followers
     */
    private Label numberOfFollowers;

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
     * Friend Request Service
     */
    @Autowired
    private FriendRequestService friendRequestService;
    /**
     * Following Service
     */
    @Autowired
    private FollowingService followingService;
    /**
     * Friends Service
     */
    @Autowired
    private FriendsService friendsService;

    /**
     * user that wrote the post
     */
    private final Users user;
    /**
     * Constructor
     * @param user user
     */
    public ComponentProfilePost(Users user) {
        this.setSizeUndefined();
        this.setWidth(100, Unit.PERCENTAGE);
        this.setStyleName("text-align-center");
        this.setMargin(true);
        this.addComponent(layout);
        image.setVisible(false);
        layout.setSizeUndefined();
        this.user = user;
    }   
    /**
     * Post Construct
     */
    @PostConstruct
    public void postConstruct() {

        setLabels(user);
        if (user.getUserImageName() != null) {
            image.setVisible(true);
            image.setSource(new FileResource(new File(Constants.BASE_PATH_RESIZED + user.getUserImageName())));
            layout.addComponent(image, "picture");
        }
        fullname = new Label(user.getFullname());
        layout.addComponent(fullname, "name");

        Label usernameLabel = new Label(msgs.getMessage("username") + ":");
        Label postsLabel = new Label(msgs.getMessage("number-of-posts") + ":");
        Label followersLabel = new Label(msgs.getMessage("number-of-followers") + ":");

        usernameLabel.setWidth(LABEL_WIDTH, Sizeable.Unit.PIXELS);
        postsLabel.setWidth(LABEL_WIDTH, Sizeable.Unit.PIXELS);
        followersLabel.setWidth(LABEL_WIDTH, Sizeable.Unit.PIXELS);

        layout.addComponent(usernameLabel, "usernameLabel");
        layout.addComponent(postsLabel, "numberPostsLabel");
        layout.addComponent(followersLabel, "numberFollowersLabel");

        layout.addComponent(username, "username");
        layout.addComponent(numberOfposts, "numberPosts");
        layout.addComponent(numberOfFollowers, "numberFollowers");
        layout.addComponent(createButtons(), "button");
    }
    /**
     * Sets labels
     * @param user 
     */
    private void setLabels(Users user) {

        username = new Label(user.getUsername());
        numberOfposts = new Label("" + user.getTotalPosts());
        numberOfFollowers = new Label("" + user.getTotalFollowers());

    }
    /**
     * Reload user info
     * @param user  user
     */
    public void reload(Users user) {
        fullname.setValue(user.getName() + " " + user.getSurname());
        username.setValue(user.getUsername());
    }
    /**
     * Create buttons add friend and add follower
     * @return wrapper with buttons
     */
    private com.vaadin.ui.Component createButtons() {
        HorizontalLayout wrapper = new HorizontalLayout();
        wrapper.setSpacing(true);
        if (!securityHelper.isAuthenticated() || securityHelper.getLogedInUser().equals(this.user)) {
            return wrapper;
        }
        Button addFriend = getAddFriendButton();

        Button addFollower = getFollowerButton();

        wrapper.addComponent(addFriend);
        wrapper.addComponent(addFollower);
        return wrapper;
    }
    /**
     * Gets add friend button
     * @return add friend button
     */
    private Button getAddFriendButton() {
        Button addFriend;

        Friends friends = friendsService.getFriend(securityHelper.getLogedInUser(), user);
        if (friends == null) {
            addFriend = new Button(msgs.getMessage("profile.add.friend"));

        } else {

            addFriend = new Button(msgs.getMessage("profile.remove.friend"));
            addFriend.setData(friends);
        }

        addFriend.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (event.getButton().getCaption().equals(msgs.getMessage("profile.add.friend"))) {
                    FriendRequest request = friendRequestService.getFriendRequest(securityHelper.getLogedInUser(), user);
                    if (request != null && !request.isDenyed()) {
                        Notification.show(msgs.getMessage("request.sent"), Notification.Type.HUMANIZED_MESSAGE);
                        return;
                    }
                    FriendRequest fr = new FriendRequest(securityHelper.getLogedInUser(), user);
                    friendRequestService.persist(fr);
                    event.getButton().setEnabled(false);
                } else {
                    Friends friends = (Friends) event.getButton().getData();
                    friendsService.delete(friends);
                }
            }
        });
        return addFriend;
    }
    /**
     * Gets follower button
     * @return follower button
     */
    private Button getFollowerButton() {
        Button addFollower;

        if (followingService.getFollower(securityHelper.getLogedInUser(), user) == null) {
            addFollower = new Button(msgs.getMessage("profile.add.follower"));
        } else {
            addFollower = new Button(msgs.getMessage("unfollow"));

        }
        addFollower.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (event.getButton().getCaption().equals(msgs.getMessage("profile.add.follower"))) {
                    Following f = new Following(securityHelper.getLogedInUser(), user);
                    followingService.persist(f);

                    user.setTotalFollowers(user.getTotalFollowers() + 1);
                    usersService.update(user);
                    event.getButton().setCaption(msgs.getMessage("unfollow"));
                } else {
                    followingService.removeFollow(securityHelper.getLogedInUser(), user);

                    user.setTotalFollowers(user.getTotalFollowers() - 1);
                    usersService.update(user);
                    event.getButton().setCaption(msgs.getMessage("profile.add.follower"));
                }
            }
        });
        return addFollower;
    }
}
