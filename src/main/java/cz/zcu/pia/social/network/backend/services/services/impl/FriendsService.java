/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.services.impl;

import cz.zcu.pia.social.network.backend.entities.FriendRequest;
import cz.zcu.pia.social.network.backend.entities.Friends;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAOInterface;
import cz.zcu.pia.social.network.backend.services.dao.impl.FriendRequestDAO;
import cz.zcu.pia.social.network.backend.services.dao.impl.FriendsDAO;
import cz.zcu.pia.social.network.backend.services.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class FriendsService extends AbstractService<Friends> {

    /**
     * DAO
     */
    @Autowired
    private FriendsDAO dao;

    /**
     * Gets dao
     */
    @Override
    protected GenericDAOInterface<Friends> getDao() {
        return dao;
    }
}