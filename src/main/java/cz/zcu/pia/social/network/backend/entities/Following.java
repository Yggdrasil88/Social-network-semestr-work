/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Entity represents one user following other
 * @author Frantisek Kolenak
 */
@Entity
public class Following extends BaseEntity{
    /**
     * Who follows
     */
    private Users follower;
    /**
     * Feeder of posts
     */
    private Users feeder;
    /**
     * Date since user is following
     */
    private Date followingSince = new Date();
    /**
     * Constructor 
     */
    public Following(){}
    /**
     * Constructor
     * @param follower Who follows
     * @param feeder Feeder of posts
     */
    public Following(Users follower, Users feeder){
        this.follower = follower;
        this.feeder = feeder;
    }
    /**
     * Get Who follows
     * @return Who follows
     */
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "follower_id", nullable = false)
    public Users getFollower() {
        return follower;
    }
    /**
     * Sets Who follows
     * @param follower Who follows
     */
    public void setFollower(Users follower) {
        this.follower = follower;
    }

    /**
     * Gets Feeder of posts
     * @return Feeder of posts
     */
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "feeder_id", nullable = false)    public Users getFeeder() {
        return feeder;
    }

    /**
     * Sets Feeder of posts
     * @param feeder Feeder of posts
     */
    public void setFeeder(Users feeder) {
        this.feeder = feeder;
    }
    
    /**
     * Gets Date since user is following
     * @return Date since user is following
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFollowingSince() {
        return followingSince;
    }
    /**
     * Set Date since user is following
     * @param followingSince Date since user is following
     */
    public void setFollowingSince(Date followingSince) {
        this.followingSince = followingSince;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.follower);
        hash = 61 * hash + Objects.hashCode(this.feeder);
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
        final Following other = (Following) obj;
        if (!Objects.equals(this.follower, other.follower)) {
            return false;
        }
        if (!Objects.equals(this.feeder, other.feeder)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Following{" + "follower=" + follower + ", feeder=" + feeder + '}';
    }
}
