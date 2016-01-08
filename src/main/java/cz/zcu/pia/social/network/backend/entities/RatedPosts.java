/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import cz.zcu.pia.social.network.helpers.RateType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Rated posts
 * @author Frantisek Kolenak
 */
@Entity
public class RatedPosts extends BaseEntity {
    /**
     * User who rated
     */
    private Users user;
    /**
     * Post that was rated
     */
    private Post post;
    /**
     * Rate type
     */
    private int rateType = RateType.LIKE;
    /**
     * Constructor
     */
    public RatedPosts(){}
    /**
     * 
     * @param post Post that was rated
     * @param user User who rated
     * @param rateType rate type
     */
    public RatedPosts(Post post, Users user, int rateType){
        this.post = post;
        this.user = user;
        this.rateType = rateType;
    }
    /**
     * Gets User who rated
     * @return User who rated
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }
    /**
     * Sets User who rated
     * @param user User who rated
     */
    public void setUser(Users user) {
        this.user = user;
    }
    /**
     * Gets Post that was rated
     * @return Post that was rated
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({CascadeType.MERGE})
    @JoinColumn(name = "post_id", nullable = false)
    public Post getPost() {
        return post;
    }
    /**
     * Sets Post that was rated
     * @param post Post that was rated
     */
    public void setPost(Post post) {
        this.post = post;
    }
    /**
     * Gets rate type
     * @return rate type
     */
    @Column(name = "rateType", nullable = false)    
    public int getRateType() {
        return rateType;
    }
    /**
     * Sets rate type
     * @param rateType rate type 
     */
    public void setRateType(int rateType) {
        this.rateType = rateType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.user);
        hash = 37 * hash + Objects.hashCode(this.post);
        hash = 37 * hash + Objects.hashCode(this.rateType);
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
        final RatedPosts other = (RatedPosts) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.post, other.post)) {
            return false;
        }
        if (this.rateType != other.rateType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RatedPosts{" + "user=" + user + ", post=" + post + ", rateType=" + rateType + '}';
    }
    
    
}
