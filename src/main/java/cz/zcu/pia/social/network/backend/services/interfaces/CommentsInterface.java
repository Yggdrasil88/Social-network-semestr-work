/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.Comments;
import java.util.List;

/**
 * CommentsInterface
 * @author Frantisek Kolenak
 */
public interface CommentsInterface {

    /**
     * Gets list of comments of post by ID
     *
     * @param postId post id
     * @return posts comments
     */
    public List<Comments> getCommentsForPost(Long postId);

}
