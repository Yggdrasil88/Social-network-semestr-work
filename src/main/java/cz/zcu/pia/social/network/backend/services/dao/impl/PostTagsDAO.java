/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.PostTags;
import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
public class PostTagsDAO extends GenericDAO<PostTags>{
    private Logger logger = LoggerFactory.getLogger(PostTagsDAO.class);
    
    public List<Tag> getPostTags(long postId) {
        Session session = getCurrentSession();
        try {
            Query query = session.createQuery("select tags from " + this.genericType.getName() + " postTags JOIN postTags.tags tags where postTags.post.id = :postId");
            query.setLong("postId", postId);
            return query.list();
            
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            return new ArrayList();
        }
        
        
        
    }
    
}
