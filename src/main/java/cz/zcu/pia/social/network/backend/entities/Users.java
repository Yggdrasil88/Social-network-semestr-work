/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * User
 * @author Frantisek Kolenak
 */
@Entity
public class Users extends BaseEntity {
    /**
     * Users name
     */
    private String name;
    /**
     * Users surname
     */
    private String surname;
    /**
     * Users username
     */
    private String username;
    /**
     * Users password
     */
    private String password;
    /**
     * Users image name
     */
    private String userImageName;
    /**
     * Users post count
     */
    private int totalPosts = 0;
    /**
     * Users followers count
     */
    private int totalFollowers = 0;
    /**
     * Gets Users name
     * @return Users name
     */
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }
    /**
     * Sets Users name
     * @param name Users name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets Users surname
     * @return Users surname
     */
    @Column(name = "surname", nullable = false, length = 100)
    public String getSurname() {
        return surname;
    }
    /**
     * Sets Users surname
     * @param surname Users surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    /**
     * Users username
     * @return Users username
     */
    @Column(name = "user_name", nullable = false, length = 40, unique = true)
    public String getUsername() {
        return username;
    }
    /**
     * Sets Users username
     * @param username Users username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Gets Users password
     * @return Users password
     */
    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    /**
     * Sets Users password
     * @param password Users password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Gets Users post count
     * @return Users post count
     */
    @Column(name = "total_posts", nullable = false)
    public int getTotalPosts() {
        return totalPosts;
    }
    /**
     * Sets Users post count
     * @param totalPosts Users post count
     */
    public void setTotalPosts(int totalPosts) {
        this.totalPosts = totalPosts;
    }
    /**
     * Gets Users followers count
     * @return Users followers count
     */
    @Column(name = "total_followers", nullable = false)
    public int getTotalFollowers() {
        return totalFollowers;
    }
    /**
     * Sets Users followers count
     * @param totalFollowers Users followers count
     */
    public void setTotalFollowers(int totalFollowers) {
        this.totalFollowers = totalFollowers;
    }
    /**
     * Gets Users image name
     * @return Users image name
     */
    public String getUserImageName() {
        return userImageName;
    }
    /**
     * Sets Users image name
     * @param userImageName Users image name
     */
    public void setUserImageName(String userImageName) {
        this.userImageName = userImageName;
    }
    
    /**
     * Gets users fullname
     * @return fullname
     */
    @Transient
    public String getFullname(){
        return this.getName() + " " + this.getSurname();
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.surname);
        hash = 53 * hash + Objects.hashCode(this.username);
        hash = 53 * hash + Objects.hashCode(this.password);
        hash = 53 * hash + this.totalPosts;
        hash = 53 * hash + this.totalFollowers;
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
        final Users other = (Users) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (this.totalPosts != other.totalPosts) {
            return false;
        }
        if (this.totalFollowers != other.totalFollowers) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", surname=" + surname
            + ", username=" + username
            + ", totalPosts=" + totalPosts
            + ", totalFollowers=" + totalFollowers + '}';
    }

}
