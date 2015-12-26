/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import cz.zcu.pia.social.network.helpers.Visibility;
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
public class Post extends BaseEntity {

    private Users user;
    private String message;
    private int likeCount = 0;
    private int hateCount = 0;
    //TODO add default value
    private int visibility = Visibility.PUBLIC;
    private Date dateSent = new Date();

    public Post(){}
    public Post(Users user, String message, int visibility){
        this.user = user;
        this.message = message;
        this.visibility = visibility;
    }
    
    public Post(Users user, String message){
        this.user = user;
        this.message = message;
    }
    
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Column(name = "dateSent", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    @Column(name = "message", nullable = false, length = 100)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "like_count", nullable = false)
    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Column(name = "hate_count", nullable = false)
    public int getHateCount() {
        return hateCount;
    }

    public void setHateCount(int hateCount) {
        this.hateCount = hateCount;
    }

    @Column(name = "visibility", nullable = false)
    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        if (visibility == Visibility.FRIENDS || visibility == Visibility.PUBLIC) {
            this.visibility = visibility;
        } else {
            this.visibility = Visibility.PUBLIC;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.message);
        hash = 17 * hash + this.likeCount;
        hash = 17 * hash + this.hateCount;
        hash = 17 * hash + Objects.hashCode(this.visibility);
        hash = 17 * hash + Objects.hashCode(this.dateSent);
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
        final Post other = (Post) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (this.likeCount != other.likeCount) {
            return false;
        }
        if (this.hateCount != other.hateCount) {
            return false;
        }
        if (!Objects.equals(this.visibility, other.visibility)) {
            return false;
        }
        if (!Objects.equals(this.dateSent, other.dateSent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Post{" + "message=" + message + ", likeCount=" + likeCount + ", hateCount=" + hateCount + ", visibility=" + visibility + ", timestamp=" + dateSent + '}';
    }

}
