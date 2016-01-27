/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import cz.zcu.pia.social.network.backend.services.interfaces.PostInterface;
import cz.zcu.pia.social.network.helpers.Constants;
import cz.zcu.pia.social.network.helpers.Visibility;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Post DAO
 *
 * @author Frantisek Kolenak
 */
@Component
public class PostDAO extends GenericDAO<Post> implements PostInterface {

    private static final Logger logger = LoggerFactory.getLogger(PostDAO.class);

    @Override
    public List<Post> getPublicPosts() {
        Session session = this.getCurrentSession();
        try {

            Query selectQuery = session.createQuery("from " + this.genericType.getName() + " post WHERE post.visibility = :visibility ORDER BY post.dateSent DESC");
            selectQuery.setInteger("visibility", Visibility.PUBLIC);

            selectQuery.setMaxResults(Constants.MAX_RESULTS);
            List<Post> results = selectQuery.list();

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();

        } finally {
            session.close();
        }

    }

    @Override
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

    @Override
    public List<Post> getFriendsPosts(Long userId) {
        Session session = this.getCurrentSession();
        try {
            String qery = "select post from " + this.genericType.getName() + " post, Friends as f WHERE ((f.user.id = :userId AND post.user.id = f.friend.id ) OR (f.friend.id = :userId AND post.user.id = f.user.id)) AND post.visibility = :visibility ORDER BY post.dateSent DESC";
            Query selectQuery = session.createQuery(qery);
            selectQuery.setLong("userId", userId).setInteger("visibility", Visibility.FRIENDS);

            selectQuery.setMaxResults(Constants.MAX_RESULTS);
            List<Post> results = selectQuery.list();

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();

        } finally {
            session.close();
        }
    }

    @Override
    public List<Post> getFollowingPosts(Long userId) {
        Session session = this.getCurrentSession();
        try {
            String qery = "select post from " + this.genericType.getName() + " post, Following as f WHERE (f.follower.id = :userId AND post.user.id = f.feeder.id ) AND post.visibility = :visibility ORDER BY post.dateSent DESC";
            Query selectQuery = session.createQuery(qery);
            selectQuery.setLong("userId", userId).setInteger("visibility", Visibility.PUBLIC);

            selectQuery.setMaxResults(Constants.MAX_RESULTS);
            List<Post> results = selectQuery.list();

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();

        } finally {
            session.close();
        }
    }

    @Override
    public List<Post> getPostsByUsername(String username) {
        Session session = this.getCurrentSession();
        try {
            String qery = "from " + this.genericType.getName() + " post WHERE post.user.username = :username AND post.visibility = :visibility ORDER BY post.dateSent DESC";
            Query selectQuery = session.createQuery(qery);
            selectQuery.setString("username", username).setInteger("visibility", Visibility.PUBLIC);

            selectQuery.setMaxResults(Constants.MAX_RESULTS);
            List<Post> results = selectQuery.list();

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();

        } finally {
            session.close();
        }
    }

    @Override
    public List<Post> getUserPosts(Long userId) {
        Session session = this.getCurrentSession();
        try {
            String qery = "from " + this.genericType.getName() + " post WHERE post.user.id = :userId ) ORDER BY post.dateSent DESC";
            Query selectQuery = session.createQuery(qery);
            selectQuery.setLong("userId", userId);

            selectQuery.setMaxResults(Constants.MAX_RESULTS);
            List<Post> results = selectQuery.list();

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();

        } finally {
            session.close();
        }
    }

    @Override
    public List<Tag> getPostTags(long postId) {
        Session session = getCurrentSession();
        try {
            Query query = session.createQuery("select tags from " + this.genericType.getName() + " post INNER JOIN post.tags tags where post.id = :postId");
            query.setLong("postId", postId);
            return query.list();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();
        }
    }

    @Override
    public List<Post> getPostsByTag(String tagName) {
        Session session = this.getCurrentSession();
        try {
            String qery = "select post from " + this.genericType.getName() + " post INNER JOIN post.tags as tags WHERE tags.tagName= :tagName AND post.visibility=:visibility ORDER BY post.dateSent DESC";
            Query selectQuery = session.createQuery(qery);
            selectQuery.setString("tagName", tagName).setInteger("visibility", Visibility.PUBLIC);

            selectQuery.setMaxResults(Constants.MAX_RESULTS);
            List<Post> results = selectQuery.list();

            return results;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();

        } finally {
            session.close();
        }
    }
}
