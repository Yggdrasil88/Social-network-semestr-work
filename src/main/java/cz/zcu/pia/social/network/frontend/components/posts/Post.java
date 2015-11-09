/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
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
public class Post extends VerticalLayout {
    private static final int POST_WIDTH = 450;
    private static final String POST_STYLE_NAME = "post";
    private static final String LAYOUT_NAME = "post-layout-name";
    @Autowired
    private MessagesLoader msgs;
    private VerticalLayout wrapper;
    private Label name;
    private Label likes;
    private Label disagrees;
    private Label timestamp;

    private TextArea postMessage;
    private long postId;
    private int numberOfLikes;
    private int numberOfDisagrees;
    private int numberOfComments;

    public Post() {
        wrapper = new VerticalLayout();
        wrapper.setMargin(true);
        this.setSizeFull();
        this.setWidth(POST_WIDTH, Unit.PIXELS);
        this.setStyleName(POST_STYLE_NAME);
        
        this.name = new Label();
        this.likes = new Label();
        this.disagrees = new Label();
        this.timestamp = new Label();
        this.postMessage = new TextArea();
    }

    public Post(long postId, String name, Date date, int numberOflikes, int numberOfdisagrees, String postMessage, int numberOfComments) {
        this();
        this.numberOfLikes = numberOflikes;
        this.numberOfDisagrees = numberOfdisagrees;
        this.numberOfComments = numberOfComments;
        
        this.name.setValue(name);
        this.timestamp.setValue(getTimeStamp(date));
        this.postMessage.setValue(postMessage);
        this.postMessage.setReadOnly(true);
        
        this.postMessage.setSizeFull();

        this.postId = postId;
    }

    @PostConstruct
    public void postConstruct() {

        wrapper.addComponent(this.getNameLayout());
        //this.setComponentAlignment(name, Alignment.MIDDLE_LEFT);

        wrapper.addComponent(getPostInfo());

        wrapper.addComponent(getPostMessage());

        wrapper.addComponent(getComments());
        
        this.addComponent(wrapper);
    }

    private com.vaadin.ui.Component getPostInfo() {
        HorizontalLayout base = new HorizontalLayout();
        base.setHeight(20, Unit.PIXELS);
        base.setSizeFull();

        HorizontalLayout like = new HorizontalLayout();
        like.setHeight(20, Unit.PIXELS);
        Label likeIMG = new Label();
        likeIMG.setIcon(new ThemeResource("./images/like.png"));
        likeIMG.setSizeUndefined();
        like.addComponent(likeIMG);
        like.addComponent(likes);
        likes.setValue(msgs.getMessage("post.likes") + " (" + this.numberOfLikes+")");

        HorizontalLayout disagree = new HorizontalLayout();
        
        disagree.setHeight(20, Unit.PIXELS);
        Label dislikeIMG = new Label();
        dislikeIMG.setIcon(new ThemeResource("./images/dislike.png"));
        dislikeIMG.setSizeUndefined();
        disagree.addComponent(dislikeIMG);
        disagree.addComponent(disagrees);
        disagrees.setValue(msgs.getMessage("post.disagrees") + " (" + this.numberOfDisagrees+")");

        VerticalLayout tp = new VerticalLayout();
        tp.setHeight(20, Unit.PIXELS);
        tp.addComponent(timestamp);

        base.addComponent(like);
        base.addComponent(disagree);
        base.addComponent(tp);

        base.setComponentAlignment(like, Alignment.MIDDLE_LEFT);
        base.setComponentAlignment(disagree, Alignment.MIDDLE_LEFT);
        tp.setComponentAlignment(timestamp, Alignment.MIDDLE_RIGHT);
        
        base.setMargin(new MarginInfo(false, false, true, false));
        /*
         base.setExpandRatio(likes, 2);
         base.setExpandRatio(disagrees, 2);
         base.setExpandRatio(timestamp, 2);*/

        return base;
    }

    private com.vaadin.ui.Component getPostMessage() {
        VerticalLayout base = new VerticalLayout();
        base.addComponent(postMessage);
        return base;
    }

    private com.vaadin.ui.Component getComments() {
        VerticalLayout base = new VerticalLayout();
        Label comments = new Label(msgs.getMessage("post.comments") + " (" + this.numberOfComments+")");

        base.addComponent(comments);

        return base;
    }

    private com.vaadin.ui.Component getNameLayout() {
        //HorizontalLayout base = new HorizontalLayout();
        name.setStyleName(LAYOUT_NAME);
        // base.addComponent(name);
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
