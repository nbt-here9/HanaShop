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
public class ProductStatusDTO implements Serializable {

    private int statusID;
    private String statusName;

    public ProductStatusDTO() {
    }

    public ProductStatusDTO(int statusID, String statusName) {
        this.statusID = statusID;
        this.statusName = statusName;
    }

    /**
     * @return the statusID
     */
    public int getStatusID() {
        return statusID;
    }

    /**
     * @param statusID the statusID to set
     */
    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    /**
     * @return the statusName
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

}
