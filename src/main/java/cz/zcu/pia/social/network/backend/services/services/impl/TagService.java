/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.services.impl;

import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAOInterface;
import cz.zcu.pia.social.network.backend.services.dao.impl.TagDAO;
import cz.zcu.pia.social.network.backend.services.interfaces.TagInterface;
import cz.zcu.pia.social.network.backend.services.services.AbstractService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class TagService extends AbstractService<Tag> implements TagInterface {

    /**
     * DAO
     */
    @Autowired
    private TagDAO dao;

    /**
     * Gets dao
     */
    @Override
    protected GenericDAOInterface<Tag> getDao() {
        return dao;
    }
    @Override
    public int saveTags(List<Tag> tagList) {
        return dao.saveTags(tagList);
    }
    @Override
    public List<Tag> getTagsByName(List<String> nameList){
        return dao.getTagsByName(nameList);
    }

}