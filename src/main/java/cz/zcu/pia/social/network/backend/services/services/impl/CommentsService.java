/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.services.impl;

import cz.zcu.pia.social.network.backend.entities.Comments;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAOInterface;
import cz.zcu.pia.social.network.backend.services.dao.impl.CommentsDAO;
import cz.zcu.pia.social.network.backend.services.interfaces.CommentsInterface;
import cz.zcu.pia.social.network.backend.services.services.AbstractService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class CommentsService extends AbstractService<Comments> implements CommentsInterface {

    /**
     * DAO
     */
    @Autowired
    private CommentsDAO dao;

    /**
     * Gets dao
     */
    @Override
    protected GenericDAOInterface<Comments> getDao() {
        return dao;
    }

    @Override
    public List<Comments> getCommentsForPost(Long postId) {
        return dao.getCommentsForPost(postId);
    }
}
