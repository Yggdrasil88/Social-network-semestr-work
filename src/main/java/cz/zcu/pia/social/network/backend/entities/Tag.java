/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Tag
 * @author Frantisek Kolenak
 */
@Entity
public class Tag extends BaseEntity{
    /**
     * Tag name
     */
    private String tagName;
    /**
     * Constructor
     */
    public Tag(){
    }
    /**
     * Constructor
     * @param tagName Tag name 
     */
    public Tag(String tagName){
        this.tagName = tagName;
    }
    /**
     * Gets Tag name
     * @return Tag name
     */
    @Column(name = "name", nullable = false, unique = true)
    public String getTagName() {
        return tagName;
    }
    /**
     * Sets Tag name
     * @param tagName Tag name
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.tagName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tag other = (Tag) obj;
        if (!Objects.equals(this.tagName, other.tagName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tag{" + "tagName=" + tagName + '}';
    }
    
    
    
}
