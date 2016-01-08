/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Following;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import cz.zcu.pia.social.network.backend.services.interfaces.FollowingInterface;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Following DAO
 *
 * @author Frantisek Kolenak
 */
@Component
public class FollowingDAO extends GenericDAO<Following> implements FollowingInterface {

    private static final Logger logger = LoggerFactory.getLogger(FollowingDAO.class);

    @Override
    public void removeFollow(Users follower, Users feeder) {
        Session session = getCurrentSessionWithTransaction();
        try {
            Query query = session.createQuery("delete from " + this.genericType.getName() + " f WHERE f.follower.id = :followerId AND f.feeder.id = :feederId ORDER BY f.followingSince DESC");
            query.setLong("followerId", follower.getId()).setLong("feederId", feeder.getId());
            query.executeUpdate();
            this.currentTransaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.currentTransaction.rollback();
        } finally {
            closeSession(session);
        }

    }

    @Override
    public Following getFollower(Users follower, Users feeder) {
        Session session = getCurrentSession();
        try {
            Query query = session.createQuery("from " + this.genericType.getName() + " f WHERE f.follower.id = :followerId AND f.feeder.id = :feederId");
            query.setLong("followerId", follower.getId()).setLong("feederId", feeder.getId());
            return (Following) query.uniqueResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeSession(session);
        }
    }

    @Override
    public List<Following> getUserFeeders(Users logedInUser) {
        Session session = getCurrentSession();
        try {
            Query query = session.createQuery("from " + this.genericType.getName() + " f WHERE f.follower.id = :followerId");
            query.setLong("followerId", logedInUser.getId());
            return (List<Following>) query.list();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();
        } finally {
            closeSession(session);
        }
    }

}
