/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope(scopeName = "prototype")
public class ComponentComment extends VerticalLayout {

    private static Logger logger = LoggerFactory.getLogger(ComponentComment.class);

    public static final int POST_WIDTH = 450;
    private static final String POST_STYLE_NAME = "post";
    private static final String LAYOUT_NAME = "post-layout-name";
    @Autowired
    private MessagesLoader msgs;
    @Autowired
    private ApplicationContext applicationContext;

    private VerticalLayout wrapper;
    protected Label name;

    protected Label timestamp;

    private Label postMessage;
    private long postId;

    protected CustomLayout layout = new CustomLayout("post");

    private Users user;


    public ComponentComment() {
        wrapper = new VerticalLayout();
        wrapper.setMargin(true);
        this.setSizeUndefined();
        this.setWidth(POST_WIDTH, Unit.PIXELS);
        this.setStyleName(POST_STYLE_NAME);

        this.name = new Label();
 
        this.timestamp = new Label();
        this.postMessage = new Label();

    }

    @PostConstruct
    public void postConstruct() {
        layout.addComponent(this.getNameLayout(), "name");
        addPostInfo();
        addPostMessage();

        wrapper.addComponent(layout);
        this.addComponent(wrapper);

    }

 
    private void addPostInfo() {
        layout.addComponent(timestamp, "timestamp");
    }



    private void addPostMessage() {
        VerticalLayout base = new VerticalLayout();
        base.addComponent(postMessage);
        layout.addComponent(base, "message");
    }




    protected final void setMessage(String postMessage) {
        this.postMessage.setReadOnly(false);
        this.postMessage.setValue(postMessage);
        this.postMessage.setReadOnly(true);
    }

    private com.vaadin.ui.Component getNameLayout() {
        name.setStyleName(LAYOUT_NAME);
        name.setWidthUndefined();
        name.setHeight(25, Unit.PIXELS);
        return name;
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
    public void setParameters(Users user, Date dateSent, String comment) {
        this.timestamp.setValue(getTimeStamp(dateSent));
        this.setMessage(comment);

        this.user = user;
        this.name.setValue(user.getFullname());

    }
}
