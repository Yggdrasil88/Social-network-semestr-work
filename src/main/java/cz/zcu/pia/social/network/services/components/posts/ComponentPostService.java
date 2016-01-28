/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.services.components.posts;

import com.vaadin.ui.Notification;
import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.RatedPosts;
import cz.zcu.pia.social.network.backend.services.services.impl.PostService;
import cz.zcu.pia.social.network.backend.services.services.impl.RatedPostsService;
import cz.zcu.pia.social.network.helpers.RateType;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class ComponentPostService {

    /**
     * Rated Posts Service
     */
    @Autowired
    private RatedPostsService ratedPostsService;
    /**
     * Post Service
     */
    @Autowired
    private PostService postService;
    /**
     * Security Helper
     */
    @Autowired
    protected SecurityHelper securityHelper;

    public Post updateDisagreeRating(Long postId) {

        RatedPosts rt = ratedPostsService.getRateType(postId, securityHelper.getLogedInUser().getUsername());
        Post post = postService.getPostById(postId);
        if (post == null) {
            Notification.show("Something went wrong. Post ID:" + postId + " not found.", Notification.Type.ERROR_MESSAGE);
            return null;
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
        postService.update(post);

        return post;
    }

    public Post updateLikeRating(Long postId) {
        RatedPosts rt = ratedPostsService.getRateType(postId, securityHelper.getLogedInUser().getUsername());
        Post post = postService.getPostById(postId);
        if (post == null) {
            Notification.show("Something went wrong. Post ID:" + postId + " not found.", Notification.Type.ERROR_MESSAGE);
            return null;
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

        postService.update(post);
        return post;
    }

}
