/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.login;

import javax.validation.constraints.NotNull;

/**
 * Register bean
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
    /**
     * Gets validation value
     * @return 
     */
    public String getValidation() {
        return validation;
    }
    /**
     * Sets validation value
     * @param validation validation value
     */
    public void setValidation(String validation) {
        this.validation = validation;
    }
    /**
     * Gets name value
     * @return name value
     */
    public String getName() {
        return name;
    }
    /**
     * Sets name value
     * @param name name value
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets surname value
     * @return surname value
     */
    public String getSurname() {
        return surname;
    }
    /**
     * Sets surname value
     * @param surname surname value
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    /**
     * Gets username value
     * @return username value
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets username value
     * @param userName username value
     */
    public void setUsername(String userName) {
        this.username = userName;
    }
    /**
     * Gets password value
     * @return password value
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets password value
     * @param password password value
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Gets password value
     * @return password value
     */
    public String getPasswordRepeat() {
        return passwordRepeat;
    }
    /**
     * Sets password value
     * @param passwordRepeat password value 
     */
    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
