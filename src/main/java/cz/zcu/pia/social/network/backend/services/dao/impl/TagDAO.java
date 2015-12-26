/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.dao.impl;

import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.dao.GenericDAO;
import cz.zcu.pia.social.network.frontend.components.posts.ComponentPostAdd;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
public class TagDAO extends GenericDAO<Tag> {

    private final Logger logger = LoggerFactory.getLogger(TagDAO.class);

    public int saveTags(List<Tag> tagList) {
        if (tagList == null || tagList.size() == 0) {
            return 0;
        }
        Session session = this.getCurrentSession();
        try {
            Transaction tx = session.beginTransaction();
            for (Tag tag : tagList) {
                session.save(tag);
            }
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

    public List<Tag> getTagsByName(List<Tag> tagsWithoutId) {
        if (tagsWithoutId == null || tagsWithoutId.isEmpty()) {
            return null;
        }
        Session session = this.getCurrentSession();
        try {
            String[] namesList = new String[tagsWithoutId.size()];
            int i = 0;
            logger.debug("START");
            for (Tag t : tagsWithoutId) {
                namesList[i] = t.getTagName();
                logger.debug("NAME:" + namesList[i]);

            }
            logger.debug("END");
            String querry = "FROM " + this.genericType.getName()
                + " AS tags WHERE tags.tagName in (:tagName)";
            List<Tag> ta = (List<Tag>) session.createQuery(querry).setParameterList("tagName", namesList).list();
            logger.debug("START");
            for (Tag t : ta) {
                logger.debug("NAME:" + t.getTagName());
            }
            logger.debug("END");
            return ta;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }

        // return null;
    }
}
