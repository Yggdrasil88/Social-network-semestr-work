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
 * Entity for comments
 * @author Frantisek Kolenak
 */
@Entity
@Table(name = "fkolenak_comments")
public class Comments extends BaseEntity {
    /**
     * User that posted comment
     */
    private Users user;
    /**
     * Commented post
     */
    private Post post;
    /**
     * Comment message
     */
    private String comment;
    /**
     * When the comment was sent
     */
    private Date dateSent = new Date();
    /**
     * Constructor for hibernate
     */
    public Comments(){}
    /**
     * Constructor
     * @param user User that posted comment
     * @param post Commented post
     * @param comment Comment message
     */
    public Comments(Users user, Post post, String comment){
        this.user = user;
        this.post = post;
        this.comment = comment;
    }
    /**
     * Gets User that posted comment
     * @return User that posted comment
     */
    @ManyToOne
    @Cascade({CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }
    /**
     * Sets User that posted comment
     * @param user User that posted comment 
     */
    public void setUser(Users user) {
        this.user = user;
    }
    /**
     * Gets Commented post
     * @return Commented post
     */
    @ManyToOne
    @Cascade({CascadeType.MERGE})
    @JoinColumn(name = "post_id", nullable = false)
    public Post getPost() {
        return post;
    }
    /**
     * Sets Commented post
     * @param post Commented post
     */
    public void setPost(Post post) {
        this.post = post;
    }
    /**
     * Get Comment message
     * @return Comment message
     */
    @Column(name = "comment", nullable = false, length = 1000)
    public String getComment() {
        return comment;
    }
    /**
     * Set Comment message
     * @param comment Comment message
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * Get When the comment was sent
     * @return When the comment was sent 
     */
    @Column(name = "dateSent", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateSent() {
        return dateSent;
    }
    /**
     * Sets When the comment was sent
     * @param dateSent When the comment was sent
     */
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.user);
        hash = 79 * hash + Objects.hashCode(this.post);
        hash = 79 * hash + Objects.hashCode(this.comment);
        hash = 79 * hash + Objects.hashCode(this.dateSent);
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
        final Comments other = (Comments) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.post, other.post)) {
            return false;
        }
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        if (!Objects.equals(this.dateSent, other.dateSent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comments{" + "user=" + user + ", post=" + post + ", comment=" + comment + ", dateSent=" + dateSent + '}';
    }

}
