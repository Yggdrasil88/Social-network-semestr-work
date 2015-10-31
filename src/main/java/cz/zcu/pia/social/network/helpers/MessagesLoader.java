/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.MessageSource;

/**
 * Class for loading messages
 *
 * @author Frantisek Kolenak
 *
 */
@Component
public class MessagesLoader {

    public String defaultValue = "Missing: ";
    /**
     * Message source
     */
    @Autowired
    private transient MessageSource messageSource;

    /**
     * Get message
     *
     * @param id id
     * @return message
     */
    public String getMessage(String id) {
        return messageSource.getMessage(id, null, defaultValue + " " + id, null);
    }
}
