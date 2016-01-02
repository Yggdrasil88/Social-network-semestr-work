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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Frantisek Kolenak
 */
@Entity
public class FriendRequest extends BaseEntity {

    private Users userSender;

    private Users userReciever;

    private boolean denyed = false;


    private Date dateSent = new Date();

    public FriendRequest(){}
    
    public FriendRequest(Users userSender, Users userReciever) {
        this.userSender = userSender;
        this.userReciever = userReciever;
    }
    
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "sender_id", nullable = false)
    public Users getUserSender() {
        return userSender;
    }

    public void setUserSender(Users userSender) {
        this.userSender = userSender;
    }
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "reciever_id", nullable = false)
    public Users getUserReciever() {
        return userReciever;
    }

    public void setUserReciever(Users userReciever) {
        this.userReciever = userReciever;
    }
    @Column(name = "denyed", nullable = false)
    public boolean isDenyed() {
        return denyed;
    }

    public void setDenyed(boolean denyed) {
        this.denyed = denyed;
    }
    @Column(name = "dateSent", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateSent() {
        return dateSent;
    }

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
