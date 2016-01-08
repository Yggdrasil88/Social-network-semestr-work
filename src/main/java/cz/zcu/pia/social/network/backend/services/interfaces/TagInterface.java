/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.Tag;
import java.util.List;

/**
 *
 * @author Frantisek Kolenak
 */
public interface TagInterface {

    /**
     * Save tags in list
     *
     * @param tagList tag list
     * @return result (<=0 is bad)
     */
    public int saveTags(List<Tag> tagList);

    /**
     * Gets all tags in database in the list
     *
     * @param nameList tags name List
     * @return all tags in database in the list
     */
    public List<Tag> getTagsByName(List<String> nameList);

}
