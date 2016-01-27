/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.Users;

/**
 * Users interface
 * @author Frantisek Kolenak
 */
public interface UsersInterface {

    /**
     * Get user by his username
     *
     * @param username users username
     * @return User
     */
    public Users getUserByUsername(String username);
}
