/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import cz.zcu.pia.social.network.helpers.Constants;
import cz.zcu.pia.social.network.helpers.RateType;
import cz.zcu.pia.social.network.helpers.Visibility;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
public class PostDAO extends GenericDAO<Post> {

    private final Logger logger = LoggerFactory.getLogger(PostDAO.class);

    public List<Post> getPublicPosts(int page) {
        Session session = this.getCurrentSession();
        try {
            int pageSize = Constants.PAGE_LENGTH;
            
             Query selectQuery = session.createQuery("from " + this.genericType.getName() + " post WHERE post.visibility = :visibility ORDER BY post.dateSent DESC");
             selectQuery.setInteger("visibility", Visibility.PUBLIC);
             //selectQuery.setFirstResult((page - 1) * pageSize);

             //selectQuery.setMaxResults(pageSize * page);

             List<Post> lastPage = selectQuery.list();
             
            /*
            Query query = session.createQuery("from " + this.genericType.getName() + " post WHERE post.visibility = :visibility ORDER BY post.dateSent");
            query.setInteger("visibility", Visibility.PUBLIC);
            logger.debug("The returned list " +  query.list().size());
            if(query.list().size() == 0){
                return new ArrayList();
            }
            ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);
            
            
            resultScroll.first();
            resultScroll.scroll(page * pageSize);
            List<Post> posts = new ArrayList<>();
            int i = 0;
            while (pageSize > i++) {
                posts.add((Post) resultScroll.get(0));
                if (!resultScroll.next()) {
                    break;
                }
            }*/
            return lastPage;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return new ArrayList();

    }

    public int getPublicPostsPages() {
        Session session = this.getCurrentSession();
        try {
            int pageSize = Constants.PAGE_LENGTH;

            String countQ = "Select count (post.id) from " + this.genericType.getName() + " post WHERE post.visibility = :visibility";

            Query countQuery = session.createQuery(countQ).setInteger("visibility", Visibility.PUBLIC);

            Long countResults = (Long) countQuery.uniqueResult();

            return (int) ((countResults / pageSize) + 1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return -1;
    }

    public Post getPostById(long postId) {
        Session session = this.getCurrentSession();
        try {
          Query query = session.createQuery("from " + this.genericType.getName() + " post where post.id = :postId");
          return (Post) query.setLong("postId", postId).uniqueResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            session.close();
        }
    }

}
