/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile.friends;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.backend.entities.FriendRequest;
import cz.zcu.pia.social.network.backend.entities.Friends;
import cz.zcu.pia.social.network.backend.services.services.impl.FriendRequestService;
import cz.zcu.pia.social.network.backend.services.services.impl.FriendsService;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Component for managing friend requests
 * @author Frantisek Kolenak
 */
@Component
@Scope(scopeName = "prototype")
public class ComponentManageFriendRequest extends VerticalLayout {

    private static final Logger logger = LoggerFactory.getLogger(ComponentManageFriendRequest.class);
    /**
     * Messages helper
     */
    @Autowired
    private MessagesLoader msgs;
    /**
     * Security helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * Friends Service
     */
    @Autowired
    private FriendsService friendsService;
    /**
     * Friend Request Service
     */
    @Autowired
    private FriendRequestService friendRequestService;
    /**
     * parent reference to ComponentFriends
     */
    private ComponentFriends parentReference;
    /**
     * table with friend requests
     */
    private final Table table;
    /**
     * Constructor 
     */
    public ComponentManageFriendRequest() {
        table = new Table();
        table.setSizeFull();
        this.setMargin(true);
        this.addComponent(table);
    }
    /**
     * PostConstruct
     */
    @PostConstruct
    public void postConstruct() {
        setTable();
        if (securityHelper.isAuthenticated()) {
            addContent();
        }
    }
    /**
     * Sets table to default
     */
    private void setTable() {
        table.addContainerProperty(msgs.getMessage("name"), String.class, "");
        table.addContainerProperty(msgs.getMessage("surname"), String.class, "");
        table.addContainerProperty(msgs.getMessage("since"), String.class, "");
        table.addContainerProperty("", HorizontalLayout.class, "");
    }
    /**
     * Adds content
     */
    private void addContent() {
        Long userId = securityHelper.getLogedInUser().getId();
        List<FriendRequest> friendRequestList = friendRequestService.getFriendRequests(userId);
        for (FriendRequest fr : friendRequestList) {
            Button accept = new Button(msgs.getMessage("request.accept"));
            accept.setData(fr);
            accept.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    FriendRequest fr = (FriendRequest) event.getButton().getData();
                    Friends friends = new Friends(fr.getUserSender(),fr.getUserReciever());
                    friendsService.persist(friends);
                    friendRequestService.delete(fr);
                    table.removeItem(fr.getId());
                    parentReference.reload();
                }
            });
            Button deny = new Button(msgs.getMessage("request.deny"));
            HorizontalLayout wrapper = new HorizontalLayout();
            wrapper.setSpacing(true);
            wrapper.addComponent(accept);
            wrapper.addComponent(deny);
            if (fr.isDenyed()) {
                deny.setEnabled(false);
            } else {
                deny.setData(fr);
                deny.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        FriendRequest fr = (FriendRequest) event.getButton().getData();
                        fr.setDenyed(true);
                        friendRequestService.update(fr);
                        event.getButton().setEnabled(false);
                    }
                });
            }
            table.addItem(new Object[]{fr.getUserSender().getName(), fr.getUserSender().getSurname(), getTimeStamp(fr.getDateSent()), wrapper}, fr.getId());
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
    /**
     * Sets parent reference
     * @param parentReference 
     */
    public void setParentReference(ComponentFriends parentReference) {
        this.parentReference = parentReference;
    }

}
