/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Solution for storing informations about logged in user.
 * @author Fanda
 *
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS) 
public class SecurityHelper {
    	private final Logger logger = LoggerFactory.getLogger(SecurityHelper.class);
        
        private Object user = null;
        /**
	 * Checks if user is logged in
	 * @return is logged in
	 */
	public boolean isAuthenticated(){
		if(this.getLogedInUser() == null){
			return false;
		} else {
			return true;
		}	
	}
	/**
	 * Gets logged in user informations.
	 * @return logged in user informations 
	 */
	public Object getLogedInUser(){
		return this.user;
	}
	/**
	 * Sets informations about logged in user.
	 * @param user Informations to set
	 */
	public void setLogedInUser(Object user){
		this.user = user;
	}
	
}
