/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base for entities
 * @author Frantisek Kolenak
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    
    private Long id;
    
    /**
     * Entity ID
     * @return entity ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    /**
     * Set entity ID
     * @param id  if to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract String toString();
}
