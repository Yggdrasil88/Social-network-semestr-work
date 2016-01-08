/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.services.impl;

import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAOInterface;
import cz.zcu.pia.social.network.backend.services.dao.impl.UsersDAO;
import cz.zcu.pia.social.network.backend.services.interfaces.UsersInterface;
import cz.zcu.pia.social.network.backend.services.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class UsersService extends AbstractService<Users> implements UsersInterface{

    /**
     * DAO
     */
    @Autowired
    private UsersDAO dao;

    /**
     * Gets dao
     * @return dao
     */
    @Override
    protected GenericDAOInterface<Users> getDao() {
        return dao;
    }
    @Override
    public Users getUserByUsername(String username) {
        return dao.getUserByUsername(username);
    }
}