/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

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
public class Following extends BaseEntity{

    private Users follower;
    
    private Users feeder;
    
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "follower_id", nullable = false)
    public Users getFollower() {
        return follower;
    }

    public void setFollower(Users follower) {
        this.follower = follower;
    }
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "feeder_id", nullable = false)    public Users getFeeder() {
        return feeder;
    }

    public void setFeeder(Users feeder) {
        this.feeder = feeder;
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
