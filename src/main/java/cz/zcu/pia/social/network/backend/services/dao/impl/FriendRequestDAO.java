/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.FriendRequest;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
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
public class FriendRequestDAO extends GenericDAO<FriendRequest> {

    private static final Logger logger = LoggerFactory.getLogger(FriendRequestDAO.class);

    public FriendRequest getFriendRequest(Users logedInUser, Users user) {
        Session session = getCurrentSession();
        try {
            Query query = session.createQuery("from " + this.genericType.getName() + " request WHERE (request.userSender.id = :logedInUser AND request.userReciever.id = :user) OR ( request.userSender.id = :user AND request.userReciever.id = :logedInUser)");
            query.setLong("logedInUser", logedInUser.getId()).setLong("user", user.getId());
            return (FriendRequest) query.uniqueResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeSession(session);
        }
    }

}
