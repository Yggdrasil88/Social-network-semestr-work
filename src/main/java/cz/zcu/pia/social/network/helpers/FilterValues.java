/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.helpers;

/**
 *
 * @author Frantisek Kolenak
 */
public enum FilterValues {
    FILTER_NAME("filter.name"),FILTER_TAG("filter.tag"),FILTER_FROM("filter.from");
    
    private String value;
    
    FilterValues(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
}
