/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Comments;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Comments DAO
 * @author Frantisek Kolenak
 */
@Component
public class CommentsDAO extends GenericDAO<Comments> {

    private static final Logger logger = LoggerFactory.getLogger(CommentsDAO.class);

    /**
     * Gets list of comments of post by ID
     * @param postId post id
     * @return posts comments
     */
    public List<Comments> getCommentsForPost(Long postId) {
        Session session = getCurrentSession();
        try {
            Query query = session.createQuery("from " + this.genericType.getName() + " comment where comment.post.id = :postId ORDER BY comment.dateSent DESC");
            return query.setLong("postId", postId).list();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();
        } finally {
            closeSession(session);
        }
    
    }

}
