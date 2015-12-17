/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
public class UsersDAO extends GenericDAO<Users> {

    public Users getUserByUsername(String username) {
        Session session = getCurrentSession();
        try {
            String querry = "FROM " + this.genericType.getName()
                + " AS users WHERE users.username = :username";

            return (Users) session.createQuery(querry).setString("username", username).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;

    }

}
