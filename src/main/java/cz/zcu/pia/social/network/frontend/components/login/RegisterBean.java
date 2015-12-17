/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.login;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Frantisek Kolenak
 */
public class RegisterBean {

    /**
     * Name
     */
    @NotNull
    @javax.validation.constraints.Size(min = 1, max = 20)
    private String name;
    /**
     * surname
     */
    @NotNull
    @javax.validation.constraints.Size(min = 1, max = 20)
    private String surname;
    /**
     * username
     */
    @NotNull
    @javax.validation.constraints.Size(min = 1, max = 20)
    private String username;
    /**
     * password
     */
    @NotNull
    @javax.validation.constraints.Size(min = 1, max = 25)
    private String password;
    /**
     * password
     */
    @NotNull
    @javax.validation.constraints.Size(min = 1, max = 25)
    private String passwordRepeat;

    /**
     * validation
     */
    @NotNull
    @javax.validation.constraints.Size(min = 1, max = 10)
    private String validation;

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
