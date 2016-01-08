/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Friends;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import cz.zcu.pia.social.network.backend.services.interfaces.FriendsInterface;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Friends DAO
 *
 * @author Frantisek Kolenak
 */
@Component
public class FriendsDAO extends GenericDAO<Friends> implements FriendsInterface {

    private static final Logger logger = LoggerFactory.getLogger(FriendsDAO.class);

    @Override
    public Friends getFriend(Users logedInUser, Users user) {
        Session session = getCurrentSession();
        try {
            Query query = session.createQuery("from " + this.genericType.getName() + " friend WHERE (friend.user.id = :logedInUser AND friend.friend.id = :user) OR ( friend.user.id = :user AND friend.friend.id = :logedInUser) ORDER BY friend.friendsSince DESC");
            query.setLong("logedInUser", logedInUser.getId()).setLong("user", user.getId());
            return (Friends) query.uniqueResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeSession(session);
        }
    }

    @Override
    public List<Friends> getFriend(Users logedInUser) {
        Session session = getCurrentSession();
        try {
            Query query = session.createQuery("from " + this.genericType.getName() + " friend WHERE (friend.user.id = :logedInUser) OR ( friend.friend.id = :logedInUser)");
            query.setLong("logedInUser", logedInUser.getId());
            return (List<Friends>) query.list();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList();
        } finally {
            closeSession(session);
        }
    }

}
