/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.services.impl;

import cz.zcu.pia.social.network.backend.entities.Following;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAOInterface;
import cz.zcu.pia.social.network.backend.services.dao.impl.FollowingDAO;
import cz.zcu.pia.social.network.backend.services.interfaces.FollowingInterface;
import cz.zcu.pia.social.network.backend.services.services.AbstractService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class FollowingService extends AbstractService<Following> implements FollowingInterface {

    /**
     * DAO
     */
    @Autowired
    private FollowingDAO dao;

    /**
     * Gets dao
     */
    @Override
    protected GenericDAOInterface<Following> getDao() {
        return dao;
    }

    @Override
    public void removeFollow(Users logedInUser, Users user) {
        dao.removeFollow(logedInUser, user);
    }

    @Override
    public Following getFollower(Users logedInUser, Users user) {
        return dao.getFollower(logedInUser, user);

    }

    @Override
    public List<Following> getUserFeeders(Users logedInUser) {
        return dao.getUserFeeders(logedInUser);

    }
}
