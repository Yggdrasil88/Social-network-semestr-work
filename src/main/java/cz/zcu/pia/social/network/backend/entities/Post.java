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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Entity for post
 * @author Frantisek Kolenak
 */
@Entity
@Table(name = "fkolenak_post")
public class Post extends BaseEntity {
    /**
     * User that posted 
     */
    private Users user;
    /**
     * Message of the post
     */
    private String message;
    /**
     * Like count
     */
    private int likeCount = 0;
    /**
     * Hate count
     */
    private int hateCount = 0;
    /**
     * Visibility
     */
    private int visibility = Visibility.PUBLIC;
    /**
     * Date sent
     */
    private Date dateSent = new Date();
    /**
     * Number of comments
     */
    private int numberOfComments = 0;

    
    /**
     * Constructor
     */
    public Post(){}
    /**
     * Constructor
     * @param user User that posted 
     * @param message Post message
     * @param visibility Post visibility
     */
    public Post(Users user, String message, int visibility){
        this.user = user;
        this.message = message;
        this.visibility = visibility;
    }
    /**
     * Constructor with default visibility
     * @param user User that posted 
     * @param message Post message
     */
    public Post(Users user, String message){
        this.user = user;
        this.message = message;
    }
    /**
     * Gest User that posted 
     * @return  User that posted 
     */
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }
    /**
     * Sets User that posted 
     * @param user User that posted 
     */
    public void setUser(Users user) {
        this.user = user;
    }
    /**
     * Gets date sent
     * @return date sent
     */
    @Column(name = "dateSent", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateSent() {
        return dateSent;
    }
    /**
     * Sets date sent
     * @param dateSent date sent 
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
    /**
     * Gets message
     * @return post message
     */
    @Column(name = "message", nullable = false, length = 1000)
    public String getMessage() {
        return message;
    }
    /**
     * Sets message
     * @param message message 
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Gets like count
     * @return like count
     */
    @Column(name = "like_count", nullable = false)
    public int getLikeCount() {
        return likeCount;
    }
    /**
     * Sets like count
     * @param likeCount like countt 
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    /**
     * Gets hate count
     * @return hate count 
     */
    @Column(name = "hate_count", nullable = false)
    public int getHateCount() {
        return hateCount;
    }
    /**
     * Sets hate count
     * @param hateCount hate count 
     */
    public void setHateCount(int hateCount) {
        this.hateCount = hateCount;
    }
    /**
     * Gets visibility
     * @return visibility
     */
    @Column(name = "visibility", nullable = false)
    public int getVisibility() {
        return visibility;
    }
    /**
     * Gets number of comments
     * @return   number of comments
     */
    @Column(nullable = false)
    public int getNumberOfComments() {
        return numberOfComments;
    }
    /**
     * Sets number of comments
     * @param numberOfComments   number of comments
     */
    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }
    /**
     * Sets visibility
     * @param visibility  visibility
     */
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
