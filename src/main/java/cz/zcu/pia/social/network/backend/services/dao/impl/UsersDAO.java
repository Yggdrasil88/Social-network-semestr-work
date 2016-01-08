/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Users DAO
 *
 * @author Frantisek Kolenak
 */
@Component
public class UsersDAO extends GenericDAO<Users> {
    
    private static final Logger logger = LoggerFactory.getLogger(UsersDAO.class);

    /**
     * Get user by his username
     *
     * @param username users username
     * @return User
     */
    public Users getUserByUsername(String username) {
        Session session = getCurrentSession();
        try {
            String querry = "FROM " + this.genericType.getName()
                + " AS users WHERE users.username = :username";
            
            return (Users) session.createQuery(querry).setString("username", username).uniqueResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            session.close();
        }
        
        
    }
    
}
