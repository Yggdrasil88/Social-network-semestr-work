/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ComponentPost extends VerticalLayout {
    public static final int POST_WIDTH = 450;
    private static final String POST_STYLE_NAME = "post";
    private static final String LAYOUT_NAME = "post-layout-name";
    @Autowired
    private MessagesLoader msgs;
    private VerticalLayout wrapper;
    private Label name;
    private Label likes;
    private Label disagrees;
    private Label timestamp;

    private Label postMessage;
    private long postId;
    private int numberOfLikes;
    private int numberOfDisagrees;
    private int numberOfComments;
    
    private CustomLayout layout = new CustomLayout("post");

    public ComponentPost() {
        wrapper = new VerticalLayout();
        wrapper.setMargin(true);
        this.setSizeUndefined();
        this.setWidth(POST_WIDTH, Unit.PIXELS);
        this.setStyleName(POST_STYLE_NAME);
        
        this.name = new Label();
        this.likes = new Label();
        this.disagrees = new Label();
        this.timestamp = new Label();
        this.postMessage = new Label();
        
    }

    public ComponentPost(long postId, String name, Date date, int numberOflikes, int numberOfdisagrees, String postMessage, int numberOfComments) {
        this();
        this.numberOfLikes = numberOflikes;
        this.numberOfDisagrees = numberOfdisagrees;
        this.numberOfComments = numberOfComments;
        
        this.name.setValue(name);
        this.timestamp.setValue(getTimeStamp(date));
        this.postMessage.setValue(postMessage);
        this.postMessage.setReadOnly(true);
        
        this.postMessage.setWidth(100, Unit.PERCENTAGE);
        this.postMessage.setHeightUndefined();
       
        this.postId = postId;
    }

    @PostConstruct
    public void postConstruct() {
        
        layout.addComponent(this.getNameLayout(), "name");
        getPostInfo();
        getPostMessage();
        getBottom();
     
        
        wrapper.addComponent(layout);
        this.addComponent(wrapper);
    }

    private void getPostInfo() {



        Label likeIMG = new Label();
        likeIMG.setIcon(new ThemeResource("./images/like.png"));
        likeIMG.setSizeUndefined();
        layout.addComponent(likeIMG, "picture-like");
        layout.addComponent(likes, "like");
        likes.setValue(msgs.getMessage("post.likes") + " (" + this.numberOfLikes+")");

 
        Label dislikeIMG = new Label();
        dislikeIMG.setIcon(new ThemeResource("./images/dislike.png"));
        dislikeIMG.setSizeUndefined();
        layout.addComponent(dislikeIMG, "picture-disagree");
        layout.addComponent(disagrees, "disagree");
        disagrees.setValue(msgs.getMessage("post.disagrees") + " (" + this.numberOfDisagrees+")");

        layout.addComponent(timestamp, "timestamp");

    }

    private void getPostMessage() {
        VerticalLayout base = new VerticalLayout();
        base.addComponent(postMessage);
        layout.addComponent(base,"message");        
    }

    private void getBottom() {
        
        Label comments = new Label(msgs.getMessage("post.comments") + " (" + this.numberOfComments+")");
        layout.addComponent(comments, "comments");
        
        Label tags = new Label(msgs.getMessage("post.tags"));
        layout.addComponent(tags, "tags");
      
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
     * @param date date to get time spamp
     * @return
     */
    private String getTimeStamp(Date date) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm dd.MM.yyyy");//dd/MM/yyyy
        return sdfDate.format(date);
    }

}
