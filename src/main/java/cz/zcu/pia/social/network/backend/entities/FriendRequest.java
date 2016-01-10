/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Entity representing friend request
 * @author Frantisek Kolenak
 */
@Entity
@Table(name = "fkolenak_friend_request")
public class FriendRequest extends BaseEntity {
    /**
     * Sender of the request
     */
    private Users userSender;
    /**
     * Reciever of the request
     */
    private Users userReciever;
    /**
     * Says if the request was denied
     */
    private boolean denyed = false;

    /**
     * Date the request was sent
     */
    private Date dateSent = new Date();
    /**
     * Constructor just for hibernate
     */
    public FriendRequest(){}
    /**
     * Constructor
     * @param userSender Sender of the request
     * @param userReciever Reciever of the request
     */
    public FriendRequest(Users userSender, Users userReciever) {
        this.userSender = userSender;
        this.userReciever = userReciever;
    }
    /**
     * Gets  Sender of the request
     * @return  Sender of the request
     */
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "sender_id", nullable = false)
    public Users getUserSender() {
        return userSender;
    }
    /**
     * Sets  Sender of the request
     * @param userSender   Sender of the request
     */
    public void setUserSender(Users userSender) {
        this.userSender = userSender;
    }
    /**
     * Gets Reciever of the request
     * @return Reciever of the request
     */
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "reciever_id", nullable = false)
    public Users getUserReciever() {
        return userReciever;
    }
    /**
     * Sets Reciever of the request
     * @param userReciever 
     */
    public void setUserReciever(Users userReciever) {
        this.userReciever = userReciever;
    }
    /**
     * Says if the request was denied
     * @return is denied
     */
    @Column(name = "denyed", nullable = false)
    public boolean isDenyed() {
        return denyed;
    }
    /**
     * Sets Says if the request was denied
     * @param denyed is denied
     */
    public void setDenyed(boolean denyed) {
        this.denyed = denyed;
    }
    /**
     * Gets Date the request was sent
     * @return Date the request was sent
     */
    @Column(name = "dateSent", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateSent() {
        return dateSent;
    }
    /**
     * Sets Date the request was sent
     * @param dateSent  Date the request was sent
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.userSender);
        hash = 71 * hash + Objects.hashCode(this.userReciever);
        hash = 71 * hash + (this.denyed ? 1 : 0);
        hash = 71 * hash + Objects.hashCode(this.dateSent);
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
        final FriendRequest other = (FriendRequest) obj;
        if (!Objects.equals(this.userSender, other.userSender)) {
            return false;
        }
        if (!Objects.equals(this.userReciever, other.userReciever)) {
            return false;
        }
        if (this.denyed != other.denyed) {
            return false;
        }
        if (!Objects.equals(this.dateSent, other.dateSent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FriendRequest{" + "userSender=" + userSender + ", userReciever=" + userReciever + ", denyed=" + denyed + ", timestamp=" + dateSent + '}';
    }

}
