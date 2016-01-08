/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Tags DAO
 * @author Frantisek Kolenak
 */
@Component
public class TagDAO extends GenericDAO<Tag> {

    private final Logger logger = LoggerFactory.getLogger(TagDAO.class);
    /**
     * Save tags in list
     * @param tagList tag list
     * @return result (<=0 is bad)
     */
    public int saveTags(List<Tag> tagList) {
        if (tagList == null || tagList.isEmpty()) {
            return 0;
        }
        Session session = this.getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            for (Tag tag : tagList) {
                session.save(tag);
            }
            //session.load
            tx.commit();
            return 1;
        } catch (ConstraintViolationException e) {
            return -11;
        } catch (Exception e) {
            return -1;
        } finally {
            session.close();
        }

    }

    /**
     * Gets all tags in database in the list
     * @param nameList tags name List
     * @return  all tags in database in the list
     */
    public List<Tag> getTagsByName(List<String> nameList) {
        if (nameList == null || nameList.isEmpty()) {
            return new ArrayList<Tag>();
        }
        Session session = this.getCurrentSession();
        try {
            
            String querry = "FROM " + this.genericType.getName()
                + " AS tags WHERE tags.tagName in (:tagName)";
            List<Tag> ta = (List<Tag>) session.createQuery(querry).setParameterList("tagName", nameList).list();
           
            return ta;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<Tag>();
        } finally {
            session.close();
        }

    }
}
