/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author Frantisek Kolenak
 */
@Component
public enum RateType {
    LIKE, HATE;
    
    @Autowired
    private MessagesLoader msgs;
    
    public String getLikeValue(){
        return msgs.getMessage("like");
    }
    public String getHateValue(){
        return msgs.getMessage("hate");
    }
    
    
}
