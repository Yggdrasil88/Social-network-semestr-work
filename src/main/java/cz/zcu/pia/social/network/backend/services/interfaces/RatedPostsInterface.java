/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.RatedPosts;
import cz.zcu.pia.social.network.backend.entities.Users;

/**
 *
 * @author Frantisek Kolenak
 */
public interface RatedPostsInterface {

    /**
     * Gets users ratings of post
     *
     * @param postId postId
     * @param username username
     * @return users ratings of post
     */
    public RatedPosts getRateType(Long postId, String username);

    /**
     * Gets users ratings of post
     *
     * @param post post
     * @param user user
     * @return users ratings of post
     */
    public RatedPosts getRateType(Post post, Users user);

}
