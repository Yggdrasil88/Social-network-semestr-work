/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.Tag;
import java.util.List;

/**
 *
 * @author Frantisek Kolenak
 */
public interface PostTagsInterface {

    /**
     * Gets posts tags
     *
     * @param postId post id
     * @return posts tags
     */
    public List<Tag> getPostTags(long postId);

    /**
     * Gets posts by tag
     *
     * @param tagName
     * @return
     */
    public List<Post> getPostsByTag(String tagName);

}
