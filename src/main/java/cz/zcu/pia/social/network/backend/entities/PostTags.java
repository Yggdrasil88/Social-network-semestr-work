/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Posts tags
 * @author Frantisek Kolenak
 */
@Entity
@Table(name = "fkolenak_post_tags")
public class PostTags extends BaseEntity {
    /**
     * Posts tags
     */
    private List<Tag> tags;
    /**
     * Post 
     */
    private Post post;
    /**
     * Constructor
     */
    public PostTags(){}
    /**
     * Constructor
     * @param tags post tags
     * @param post post
     */
    public PostTags(List<Tag> tags, Post post ){
        this.tags = tags;
        this.post = post;
    }
    /**
     * Gets post tags
     * @return post tags
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    @Column(unique = false)
    public List<Tag> getTags() {
        return tags;
    }
    /**
     * Sets post tags
     * @param tags post tags
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    /**
     * Gets post tags
     * @return post tags
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "post_id", nullable = false)
    public Post getPost() {
        return post;
    }
    /**
     * Sets post tags
     * @param post post tags
     */
    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.tags);
        hash = 67 * hash + Objects.hashCode(this.post);
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
        final PostTags other = (PostTags) obj;
        if (!Objects.equals(this.tags, other.tags)) {
            return false;
        }
        if (!Objects.equals(this.post, other.post)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PostTags{" + "tags=" + tags + ", post=" + post + '}';
    }

}
