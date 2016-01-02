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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Frantisek Kolenak
 */
@Entity
public class RatedPosts extends BaseEntity {
    
    private Users user;
    
    private Post post;
    
    private int rateType = RateType.LIKE;
    
    public RatedPosts(){}
    
    public RatedPosts(Post post, Users user, int rateType){
        this.post = post;
        this.user = user;
        this.rateType = rateType;
    }

    @ManyToOne
    @Cascade({CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    @ManyToOne
    @Cascade({CascadeType.MERGE})
    @JoinColumn(name = "post_id", nullable = false)
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    @Column(name = "rateType", nullable = false)    
    public int getRateType() {
        return rateType;
    }

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
