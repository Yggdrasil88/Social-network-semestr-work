/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.services.impl;

import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAOInterface;
import cz.zcu.pia.social.network.backend.services.dao.impl.PostDAO;
import cz.zcu.pia.social.network.backend.services.interfaces.PostInterface;
import cz.zcu.pia.social.network.backend.services.services.AbstractService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class PostService extends AbstractService<Post> implements PostInterface {

    /**
     * DAO
     */
    @Autowired
    private PostDAO dao;

    /**
     * Gets dao
     */
    @Override
    protected GenericDAOInterface<Post> getDao() {
        return dao;
    }

    @Override
    public List<Post> getPublicPosts() {
        return dao.getPublicPosts();
    }

    @Override
    public Post getPostById(long postId) {
        return dao.getPostById(postId);
    }

    @Override
    public List<Post> getFriendsPosts(Long userId) {
        return dao.getFriendsPosts(userId);
    }

    @Override
    public List<Post> getFollowingPosts(Long userId) {
        return dao.getFollowingPosts(userId);
    }

    @Override
    public List<Post> getPostsByUsername(String username) {
        return dao.getPostsByUsername(username);
    }

    @Override
    public List<Post> getUserPosts(Long userId) {
        return dao.getUserPosts(userId);

    }

}
