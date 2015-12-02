/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.services.impl;

import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.RatedPosts;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAOInterface;
import cz.zcu.pia.social.network.backend.services.dao.impl.PostDAO;
import cz.zcu.pia.social.network.backend.services.dao.impl.RatedPostsDAO;
import cz.zcu.pia.social.network.backend.services.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class RatedPostsService extends AbstractService<RatedPosts> {

    /**
     * DAO
     */
    @Autowired
    private RatedPostsDAO dao;

    /**
     * Gets dao
     */
    @Override
    protected GenericDAOInterface<RatedPosts> getDao() {
        return dao;
    }
}