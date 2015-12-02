/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Frantisek Kolenak
 */
@Entity
public class PostTags extends BaseEntity {

    private List<Tag> tags;

    private Post post;

    @OneToMany
    @OrderBy("name ASC")
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "post_id", nullable = false)
    public Post getPost() {
        return post;
    }

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
