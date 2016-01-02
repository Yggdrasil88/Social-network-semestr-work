/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.RatedPosts;
import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.services.impl.PostService;
import cz.zcu.pia.social.network.backend.services.services.impl.PostTagsService;
import cz.zcu.pia.social.network.backend.services.services.impl.RatedPostsService;
import cz.zcu.pia.social.network.frontend.components.profile.profile.ComponentProfilePost;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.RateType;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
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
@Scope("prototype")
public class ComponentPost extends VerticalLayout {

    private static Logger logger = LoggerFactory.getLogger(ComponentPost.class);

    public static final int POST_WIDTH = 450;
    private static final String POST_STYLE_NAME = "post";
    private static final String LAYOUT_NAME = "post-button-name";
    @Autowired
    private MessagesLoader msgs;
    @Autowired
    private RatedPostsService ratedPostsService;
    @Autowired
    private PostService postService;
    @Autowired
    protected SecurityHelper securityHelper;
    @Autowired
    private PostTagsService postTagsService;
    @Autowired
    private ApplicationContext applicationContext;

    private VerticalLayout wrapper;
    protected Button name;
    protected Button likes;
    protected Button disagrees;
    private Button tags;
    private Button comments;

    protected Label timestamp;

    private Label postMessage;
    private long postId;
    private int numberOfLikes;
    private int numberOfDisagrees;
    private int numberOfComments;

    protected CustomLayout layout = new CustomLayout("post");

    public ComponentPost() {
        wrapper = new VerticalLayout();
        wrapper.setMargin(true);
        this.setSizeUndefined();
        this.setWidth(POST_WIDTH, Unit.PIXELS);
        this.setStyleName(POST_STYLE_NAME);

        this.name = new Button();
        this.likes = new Button();
        this.likes.setStyleName("button-label-simple");
        this.disagrees = new Button();
        this.disagrees.setStyleName("button-label-simple");
        this.comments = new Button();
        this.comments.setStyleName("button-label-simple");
        this.comments.addStyleName("padding-left-none");
        this.timestamp = new Label();
        this.postMessage = new Label();
        this.tags = new Button();
        this.tags.setStyleName("button-label-simple");
    }

    public ComponentPost(long postId, String name, Date date, int numberOflikes, int numberOfdisagrees, String postMessage, int numberOfComments) {
        this();
        this.numberOfLikes = numberOflikes;
        this.numberOfDisagrees = numberOfdisagrees;
        this.numberOfComments = numberOfComments;

        this.name.setCaption(name);
        this.timestamp.setValue(getTimeStamp(date));
        setMessage(postMessage);

        this.postMessage.setWidth(100, Unit.PERCENTAGE);
        this.postMessage.setHeightUndefined();

        this.postId = postId;
    }

    @PostConstruct
    public void postConstruct() {
        addClickListeners();
        layout.addComponent(this.getNameLayout(), "name");
        addPostInfo();
        addPostMessage();
        addBottom();

        wrapper.addComponent(layout);
        this.addComponent(wrapper);
        enableRates();

    }

    protected void enableRates() {
        if (!this.securityHelper.isAuthenticated()) {
            likes.setEnabled(false);
            disagrees.setEnabled(false);
        }
    }

    private void addPostInfo() {

        Label likeIMG = new Label();
        likeIMG.setIcon(new ThemeResource("./images/like.png"));
        likeIMG.setSizeUndefined();
        layout.addComponent(likeIMG, "picture-like");

        layout.addComponent(likes, "like");
        likes.setCaption(getLikesCaption());

        Label dislikeIMG = new Label();
        dislikeIMG.setIcon(new ThemeResource("./images/dislike.png"));
        dislikeIMG.setSizeUndefined();
        layout.addComponent(dislikeIMG, "picture-disagree");
        layout.addComponent(disagrees, "disagree");
        disagrees.setCaption(getDisagreeCaption());

        layout.addComponent(timestamp, "timestamp");

    }

    private String getLikesCaption() {
        return msgs.getMessage("post.likes") + " (" + this.numberOfLikes + ")";
    }

    private String getDisagreeCaption() {
        return msgs.getMessage("post.disagrees") + " (" + this.numberOfDisagrees + ")";
    }

    private void addPostMessage() {
        VerticalLayout base = new VerticalLayout();
        base.addComponent(postMessage);
        layout.addComponent(base, "message");
    }

    private String getCommentsCaption() {
        return msgs.getMessage("post.comments") + " (" + this.numberOfComments + ")";
    }

    private void addBottom() {

        comments.setCaption(getCommentsCaption());
        layout.addComponent(comments, "comments");

        tags.setCaption(msgs.getMessage("post.tags"));
        layout.addComponent(tags, "tags");

    }

    protected final void setMessage(String postMessage) {
        this.postMessage.setReadOnly(false);
        this.postMessage.setValue(postMessage);
        this.postMessage.setReadOnly(true);
    }

    private com.vaadin.ui.Component getNameLayout() {
        name.setStyleName(LAYOUT_NAME);
        name.addStyleName("button-label-simple");
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

    private void addClickListeners() {
        this.likes.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                RatedPosts rt = ratedPostsService.getRateType(postId, securityHelper.getLogedInUser().getUsername());
                Post post = postService.getPostById(postId);
                if (post == null) {
                    Notification.show("Something went wrong. Post ID:" + postId + " not found.", LAYOUT_NAME, Notification.Type.ERROR_MESSAGE);
                    return;
                }
                if (rt == null) {
                    ratedPostsService.persist(new RatedPosts(post, securityHelper.getLogedInUser(), RateType.LIKE));
                    post.setLikeCount(post.getLikeCount() + 1);

                } else if (rt.getRateType() == RateType.LIKE) {
                    ratedPostsService.delete(rt);
                    post.setLikeCount(post.getLikeCount() - 1);
                } else if (rt.getRateType() == RateType.HATE) {
                    rt.setRateType(RateType.LIKE);
                    ratedPostsService.update(rt);
                    post.setLikeCount(post.getLikeCount() + 1);
                    post.setHateCount(post.getHateCount() - 1);
                }
                numberOfLikes = post.getLikeCount();
                numberOfDisagrees = post.getHateCount();
                postService.update(post);
                updateHateLike();

            }
        });
        this.disagrees.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                RatedPosts rt = ratedPostsService.getRateType(postId, securityHelper.getLogedInUser().getUsername());
                Post post = postService.getPostById(postId);
                if (post == null) {
                    Notification.show("Something went wrong. Post ID:" + postId + " not found.", LAYOUT_NAME, Notification.Type.ERROR_MESSAGE);
                    return;
                }
                if (rt == null) {
                    ratedPostsService.persist(new RatedPosts(post, securityHelper.getLogedInUser(), RateType.HATE));
                    post.setHateCount(post.getHateCount() + 1);

                } else if (rt.getRateType() == RateType.LIKE) {
                    rt.setRateType(RateType.HATE);
                    ratedPostsService.update(rt);
                    post.setLikeCount(post.getLikeCount() - 1);
                    post.setHateCount(post.getHateCount() + 1);
                } else if (rt.getRateType() == RateType.HATE) {
                    ratedPostsService.delete(rt);
                    post.setHateCount(post.getHateCount() - 1);
                }
                numberOfLikes = post.getLikeCount();
                numberOfDisagrees = post.getHateCount();
                postService.update(post);
                updateHateLike();
            }
        });
        this.tags.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Window subWindow = new Window(msgs.getMessage("post.tags"));

                subWindow.setModal(true);
                subWindow.center();
                subWindow.setWidth(400, Unit.PIXELS);
                subWindow.setHeight(110, Unit.PIXELS);
                subWindow.setResizable(false);
                Panel panel = new Panel();

                panel.setSizeFull();
                HorizontalLayout tagsWrapper = new HorizontalLayout();
                tagsWrapper.setStyleName("margin-left-big");
                panel.setContent(tagsWrapper);
                tagsWrapper.setSpacing(true);
                tagsWrapper.setMargin(true);
                tagsWrapper.setSizeUndefined();

                for (Tag t : postTagsService.getPostTags(postId)) {
                    CustomLayout tag = new CustomLayout("tag");
                    Button tagLabel = new Button(t.getTagName());
                    tag.addComponent(tagLabel, "button");

                    tagsWrapper.addComponent(tag);
                }

                subWindow.setContent(panel);
                UI.getCurrent().addWindow(subWindow);
            }
        });
        this.comments.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Window subWindow = new Window(msgs.getMessage("post.comments"));

                subWindow.setModal(true);
                subWindow.center();
                subWindow.setWidth(400, Unit.PIXELS);
                subWindow.setHeight(600, Unit.PIXELS);
                subWindow.setResizable(true);

                ComponentPostComments componentPostComments = applicationContext.getBean(ComponentPostComments.class, postId);
                subWindow.setContent(componentPostComments);
                UI.getCurrent().addWindow(subWindow);
            }
        });
        this.name.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                Window subWindow = new Window(msgs.getMessage("header.profile") + "- " + name.getCaption());

                subWindow.setModal(true);
                subWindow.center();
                subWindow.setWidth(400, Unit.PIXELS);
                subWindow.setHeight(350, Unit.PIXELS);
                subWindow.setResizable(true);
                
                Users user = postService.getPostById(postId).getUser();
                ComponentProfilePost profilePost = applicationContext.getBean(ComponentProfilePost.class,user);
                
                subWindow.setContent(profilePost);
                UI.getCurrent().addWindow(subWindow);
            }
        });
    }

    private void updateHateLike() {
        likes.setCaption(getLikesCaption());
        disagrees.setCaption(getDisagreeCaption());
    }

}
