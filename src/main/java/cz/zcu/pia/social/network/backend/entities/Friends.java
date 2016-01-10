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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Entity telling which users are friends
 * @author Frantisek Kolenak
 */
@Entity
@Table(name = "fkolenak_friends")
public class Friends extends BaseEntity {
    /**
     * One of the users that is friend with the other one
     */
    private Users user;
    /**
     * The other friend
     */
    private Users friend;
    /**
     * Friends since
     */
    private Date friendsSince = new Date();
    /**
     * Constructor
     */
    public Friends(){}
    /**
     * Constructor
     * @param user1 user1 in the relationship
     * @param user2 user2  in the relationship
     */
    public Friends(Users user1, Users user2){
        this.user = user1;
        this.friend = user2;
    }
    
    /**
     * Gets user1 in the relationship
     * @return  user1 in the relationship
     */
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }
    /**
     * Sets user1 in the relationship
     * @param user user1 in the relationship
     */
    public void setUser(Users user) {
        this.user = user;
    }
    /**
     * Gets user2 in the relationship
     * @return user2 in the relationship
     */
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "friend_id", nullable = false)
    public Users getFriend() {
        return friend;
    }
    /**
     * Sets user2 in the relationship
     * @param friend user2 in the relationship
     */
    public void setFriend(Users friend) {
        this.friend = friend;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.user);
        hash = 29 * hash + Objects.hashCode(this.friend);
        return hash;
    }
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFriendsSince() {
        return friendsSince;
    }

    public void setFriendsSince(Date friendsSince) {
        this.friendsSince = friendsSince;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Friends other = (Friends) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.friend, other.friend)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Friends{" + "user=" + user + ", friend=" + friend + '}';
    }

    
    
    

}
