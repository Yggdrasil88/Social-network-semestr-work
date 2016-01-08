/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile.friends;

import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import cz.zcu.pia.social.network.backend.entities.Following;
import cz.zcu.pia.social.network.backend.entities.Friends;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.services.impl.FollowingService;
import cz.zcu.pia.social.network.backend.services.services.impl.FriendsService;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Friends 
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentFriends extends VerticalLayout {

    private static final Logger logger = LoggerFactory.getLogger(ComponentFriends.class);
    /**
     * Messages helper
     */
    @Autowired
    private MessagesLoader msgs;
    /**
     * Application context
     */
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * Component layout
     */
    private final CustomLayout layout = new CustomLayout("friends");
    /**
     * Filter
     */
    private final ComboBox filter;
    /**
     * Table of friends
     */
    private final Table table;
    /**
     * Button for managing friend request
     */
    private Button manageFriendRequestsButton;
    /**
     * Friends Service
     */
    @Autowired
    private FriendsService friendsService;
    /**
     * Security Helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * Following service
     */
    @Autowired
    private FollowingService followingService;
    /**
     * Users Service
     */
    @Autowired
    private UsersService usersService;
    /**
     * Constructor
     */
    public ComponentFriends() {
        filter = new ComboBox();
        filter.setNullSelectionAllowed(false);
        table = new Table();
        table.setWidth(400, Unit.PIXELS);
        table.setPageLength(10);
    }
    /**
     * Post construct
     */
    @PostConstruct
    public void postConstruct() {
        this.addComponent(layout);
        manageFriendRequestsButton = new Button(msgs.getMessage("component.friends.request"));
        manageFriendRequestsButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                manageFriendsRequestsButtonFunction();
            }
        });
        setFilterComboBox();
        setTable();
        layout.addComponent(manageFriendRequestsButton, "button");
        layout.addComponent(filter, "filter");
        layout.addComponent(table, "table");
        if (securityHelper.isAuthenticated()) {
            reload();
        }
    }
    /**
     * Function for manage friends requests button
     */
    private void manageFriendsRequestsButtonFunction() {
        Window subWindow = new Window(msgs.getMessage("post.comments"));
        ComponentManageFriendRequest manageFriendRequest = applicationContext.getBean(ComponentManageFriendRequest.class);
        manageFriendRequest.setParentReference(this);
        manageFriendRequest.setSizeFull();
        subWindow.setModal(true);
        subWindow.center();
        subWindow.setWidth(400, Unit.PIXELS);
        subWindow.setHeight(400, Unit.PIXELS);
        subWindow.setResizable(true);
        subWindow.setContent(manageFriendRequest);
        UI.getCurrent().addWindow(subWindow);

    }
    /**
     * Sets filter combobox
     */
    private void setFilterComboBox() {
        filter.addItem(msgs.getMessage("view.profile.tab.friends"));
        filter.addItem(msgs.getMessage("component.friends.following"));
        filter.setValue(msgs.getMessage("view.profile.tab.friends"));

        filter.addValueChangeListener(new ComboBox.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                reload();
            }
        });
    }
    /**
     * Sets table to default 
     */
    private void setTable() {
        table.addContainerProperty(msgs.getMessage("name"), String.class, "");
        table.addContainerProperty(msgs.getMessage("surname"), String.class, "");
        table.addContainerProperty(msgs.getMessage("since"), String.class, "");
        table.addContainerProperty("", Button.class, "");
    }
    /**
     * Reloads table
     */
    public void reload() {
        table.removeAllItems();
        if (filter.getValue().equals(msgs.getMessage("view.profile.tab.friends"))) {
            List<Friends> friendsList = friendsService.getFriend(securityHelper.getLogedInUser());
            addFriendsToTable(friendsList);
        } else {
            List<Following> followingList = followingService.getUserFeeders(securityHelper.getLogedInUser());
            addFollowingToTable(followingList);
        }
    }
    /**
     * Adds friends to table
     * @param friendsList  friends List
     */
    private void addFriendsToTable(List<Friends> friendsList) {
        for (Friends f : friendsList) {
            Button removeFriend = new Button(msgs.getMessage("profile.remove.friend"));
            removeFriend.setData(f);
            removeFriend.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Friends f = (Friends) event.getButton().getData();
                    friendsService.delete(f);
                    table.removeItem(f.getId());
                }
            });
            if (f.getFriend().getId().equals(securityHelper.getLogedInUser().getId())) {
                table.addItem(new Object[]{f.getUser().getName(), f.getUser().getSurname(), getTimeStamp(f.getFriendsSince()), removeFriend}, f.getId());
            } else {
                table.addItem(new Object[]{f.getFriend().getName(), f.getFriend().getSurname(), getTimeStamp(f.getFriendsSince()), removeFriend}, f.getId());
            }
        }
    }

    /**
     * Adds following to table
     * @param followingList  following List
     */
    private void addFollowingToTable(List<Following> followingList) {
        for (Following f : followingList) {
            Button removeFollow = new Button(msgs.getMessage("component.friends.remove.following"));
            removeFollow.setData(f);
            removeFollow.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Following f = (Following) event.getButton().getData();
                    table.removeItem(f.getId());
                    Users u = f.getFeeder();
                    u.setTotalFollowers(u.getTotalFollowers()-1);
                    usersService.update(u);
                    followingService.delete(f);
                }
            });
            table.addItem(new Object[]{f.getFeeder().getName(), f.getFeeder().getSurname(), getTimeStamp(f.getFollowingSince()), removeFollow}, f.getId());
        }
    }

    /**
     * Gets time stamp
     *
     * @param date date to get time stamp
     * @return
     */
    protected final String getTimeStamp(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm dd.MM.yyyy");//dd/MM/yyyy
        return sdfDate.format(date);
    }
}
