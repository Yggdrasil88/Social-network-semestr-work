/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.backend.entities.Comments;
import cz.zcu.pia.social.network.backend.services.services.impl.CommentsService;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *Component post comment
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentPostComments extends VerticalLayout {

    private static Logger logger = LoggerFactory.getLogger(ComponentPostComments.class);
    /**
     * Comments service
     */
    @Autowired
    private CommentsService commentsService;
    /**
     *  Security Helper
     */
    @Autowired
    private SecurityHelper securityHelper;

    /**
     * New comments
     */
    private VerticalLayout newComment;
    /**
     * Comments
     */
    private VerticalLayout comments;
    /**
     * Post id
     */
    private Long postId;
    /**
     * Application Context
     */
    @Autowired
    private ApplicationContext appContext;
    /**
     * Constructor
     */
    public ComponentPostComments() {
        newComment = new VerticalLayout();
        comments = new VerticalLayout();
        comments.setSpacing(true);
        this.addComponent(newComment);
        this.addComponent(comments);
        this.setMargin(true);
        this.setSpacing(true);

    }
    /**
     * Constructor that sets post id
     * @param postId post id
     */
    public ComponentPostComments(Long postId) {
        this();

        this.postId = postId;
    }
    /**
     * Post construct
     */
    @PostConstruct
    public void postConstruct() {

        ComponentPostCommentAdd postCommentAdd = appContext.getBean(ComponentPostCommentAdd.class);
        postCommentAdd.setPostId(this.postId);
        postCommentAdd.setParentReference(this);
        
        if (securityHelper.isAuthenticated()) {
            newComment.addComponent(postCommentAdd);
        }
        reload();
    }
    /**
     * Reload comments
     */
    public void reload() {
        comments.removeAllComponents();
        List<Comments> commentsList = commentsService.getCommentsForPost(this.postId);
        for(Comments c : commentsList){
            ComponentComment comment = appContext.getBean(ComponentComment.class);
            comment.setWidth(100, Unit.PERCENTAGE);
            comment.setParameters(c.getUser(),c.getDateSent(),c.getComment());
            comments.addComponent(comment);
        }
    }
}
