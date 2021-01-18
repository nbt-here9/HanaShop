/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dtos;

import java.io.Serializable;


/**
 *
 * @author Banh Bao
 */
public class UsersDTO implements Serializable {

    private String username;
    private String password;
    private String googleID;
    private String name;
    private String address;
    private String phone;
    private int roleID;

    public UsersDTO() {
    }

   

    public UsersDTO(String username, String password, String googleID, String name, String address, String phone, int roleID) {
        this.username = username;
        this.password = password;
        this.googleID = googleID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.roleID = roleID;
    }
    
    public UsersDTO(String username, String googleID, String name, String address, String phone, int roleID) {
        this.username = username;
        this.googleID = googleID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.roleID = roleID;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the googleID
     */
    public String getGoogleID() {
        return googleID;
    }

    /**
     * @param googleID the googleID to set
     */
    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the roleID
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * @param roleID the roleID to set
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

}
