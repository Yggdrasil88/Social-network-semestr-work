/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.RatedPosts;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Rated posts
 * @author Frantisek Kolenak
 */
@Component
public class RatedPostsDAO extends GenericDAO<RatedPosts>{
    
    Logger logger = LoggerFactory.getLogger(RatedPostsDAO.class);
    /**
     * Gets users ratings of post 
     * @param postId postId
     * @param username username
     * @return users ratings of post 
     */
    public RatedPosts getRateType(Long postId, String username){
        Session session = this.getCurrentSession();
        try{
            Query query = session.createQuery("from " + this.genericType.getName() + " rp WHERE rp.post.id = :postId AND rp.user.username = :username");
            return (RatedPosts) query.setLong("postId", postId).setString("username", username).uniqueResult();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return null;
        } finally{
            session.close();
        }
    }
    /**
     * Gets users ratings of post 
     * @param post post
     * @param user user
     * @return users ratings of post 
     */
    public RatedPosts getRateType(Post post, Users user) {
        return getRateType(post.getId(), user.getUsername());
    }
    
}
