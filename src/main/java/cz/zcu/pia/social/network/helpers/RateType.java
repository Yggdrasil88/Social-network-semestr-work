/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Rate type
 * @author Frantisek Kolenak
 */
@Component
public class RateType {
    public static final int LIKE = 0, HATE =  1;
    
    
    
    @Autowired
    private MessagesLoader msgs;
    
    public String getLikeValue(){
        return msgs.getMessage("like");
    }
    public String getHateValue(){
        return msgs.getMessage("hate");
    }
    
    
}
