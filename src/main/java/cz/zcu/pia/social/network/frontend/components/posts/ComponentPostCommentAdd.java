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
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope(scopeName = "prototype")
public class ComponentPostCommentAdd extends VerticalLayout {

    private static Logger logger = LoggerFactory.getLogger(ComponentPostCommentAdd.class);
    
    @Autowired
    protected MessagesLoader msgs;
    @Autowired
    private Visibility visibility;

    protected CustomLayout layout = new CustomLayout("addPost");

    protected TextArea message = new TextArea();
    private Button confirmButton;
    private List<Button> taglist = new ArrayList();
    private Panel panel;
    
    private ComponentPostComments parentReference;

    @Autowired
    protected PostService postService;
    @Autowired
    private PostTagsService postTagsService;
    @Autowired
    private TagService tagService;
    @Autowired
    protected SecurityHelper securityHelper;
    private Window subWindow;
    
    
    @Autowired
    private CommentsService commentsService;
    private Long postId;
    

    public ComponentPostCommentAdd() {
        this.setSpacing(true);

        message.setWidth(100, Unit.PERCENTAGE);
        message.setHeight(100, Unit.PIXELS);
        message.setMaxLength(255);

       
        
    }

    
    
    

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
    }



    public void setPostId(Long postId) {
        this.postId = postId;
    }

    void setParentReference(ComponentPostComments parentReference) {
        this.parentReference = parentReference;
    }
    
    
}
