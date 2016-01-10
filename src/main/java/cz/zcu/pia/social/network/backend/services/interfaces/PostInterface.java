/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.Post;
import java.util.List;

/**
 *
 * @author Frantisek Kolenak
 */
public interface PostInterface {

    /**
     * Gets public posts
     *
     * @return public posts
     */
    public List<Post> getPublicPosts();

    /**
     * Gets post by id
     *
     * @param postId postId
     * @return post
     */
    public Post getPostById(long postId);

    /**
     * Gets friends posts
     *
     * @param userId user
     * @return friends posts
     */
    public List<Post> getFriendsPosts(Long userId);

    /**
     * Gets posts of people user follows
     *
     * @param userId user id
     * @return posts of people user follows
     */
    public List<Post> getFollowingPosts(Long userId);

    /**
     * Gets posts by username
     *
     * @param username username
     * @return posts by username
     */
    public List<Post> getPostsByUsername(String username);
    /**
     * Gets posts by user id
     * @param userId user id
     * @return  posts by user id
     */
    public List<Post> getUserPosts(Long userId);
    }
