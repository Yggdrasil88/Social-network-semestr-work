/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import cz.zcu.pia.social.network.backend.entities.Comments;
import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.services.services.impl.CommentsService;
import cz.zcu.pia.social.network.backend.services.services.impl.PostService;
import cz.zcu.pia.social.network.backend.services.services.impl.PostTagsService;
import cz.zcu.pia.social.network.backend.services.services.impl.TagService;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import cz.zcu.pia.social.network.helpers.Visibility;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Component for adding post comments
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope(scopeName = "prototype")
public class ComponentPostCommentAdd extends VerticalLayout {

    private static Logger logger = LoggerFactory.getLogger(ComponentPostCommentAdd.class);
    /**
     * Messages helper
     */
    @Autowired
    protected MessagesLoader msgs;
    /**
     * Component layout
     */
    protected CustomLayout layout = new CustomLayout("addPost");
    /**
     * Message text area
     */
    protected TextArea message = new TextArea();
    /**
     * Confirm button
     */
    private Button confirmButton;
    /**
     * Tag list
     */
    private List<Button> taglist = new ArrayList();
    /**
     * Parent reference
     */
    private ComponentPostComments parentReference;
    /**
     * Post service
     */
    @Autowired
    protected PostService postService;
    /**
     * Security helper
     */
    @Autowired
    protected SecurityHelper securityHelper;

    /**
     * Comments service
     */
    @Autowired
    private CommentsService commentsService;
    /**
     * Post id
     */
    private Long postId;
    /**
     * Constructor
     */
    public ComponentPostCommentAdd() {
        this.setSpacing(true);

        message.setWidth(100, Unit.PERCENTAGE);
        message.setHeight(100, Unit.PIXELS);
        message.setMaxLength(1000);

    }
    /**
     * Post Construct
     */
    @PostConstruct
    public void postConstruct() {

        confirmButton = new Button(msgs.getMessage("post.add.new"));
        confirmButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                postButtonFunction(event);
            }

        });
        this.addComponent(layout);
        layout.addComponent(message, "message");
        layout.addComponent(confirmButton, "okButton");
    }
    /**
     * Button function
     * @param event click event
     */
    protected void postButtonFunction(Button.ClickEvent event) {
        if (message.isEmpty()) {
            Notification.show(msgs.getMessage("post.add.empty-msg"), Notification.Type.ERROR_MESSAGE);
            return;
        }
        logger.debug("PostId: {}", postId);
        Post post = this.postService.getPostById(postId);
        Comments comment = new Comments(securityHelper.getLogedInUser(), post, message.getValue());
        post.setNumberOfComments(post.getNumberOfComments() + 1);
        commentsService.persist(comment);
        postService.update(post);
        parentReference.reload();
        message.setValue("");
    }
    /**
     * Sets post id
     * @param postId post id 
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    /**
     * Sets parent reference
     * @param parentReference  parent reference
     */
    void setParentReference(ComponentPostComments parentReference) {
        this.parentReference = parentReference;
    }

}
