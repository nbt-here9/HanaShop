/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.errors;

import java.io.Serializable;

/**
 *
 * @author Banh Bao
 */
public class CheckOutErrors implements Serializable{
    private String emptyNameErr;
    private String emptyAddressErr;
    private String invalidPhoneNumErr;

    public CheckOutErrors() {
    }

    /**
     * @return the emptyNameErr
     */
    public String getEmptyNameErr() {
        return emptyNameErr;
    }

    /**
     * @param emptyNameErr the emptyNameErr to set
     */
    public void setEmptyNameErr(String emptyNameErr) {
        this.emptyNameErr = emptyNameErr;
    }

    /**
     * @return the emptyAddressErr
     */
    public String getEmptyAddressErr() {
        return emptyAddressErr;
    }

    /**
     * @param emptyAddressErr the emptyAddressErr to set
     */
    public void setEmptyAddressErr(String emptyAddressErr) {
        this.emptyAddressErr = emptyAddressErr;
    }

    /**
     * @return the invalidPhoneNumErr
     */
    public String getInvalidPhoneNumErr() {
        return invalidPhoneNumErr;
    }

    /**
     * @param invalidPhoneNumErr the invalidPhoneNumErr to set
     */
    public void setInvalidPhoneNumErr(String invalidPhoneNumErr) {
        this.invalidPhoneNumErr = invalidPhoneNumErr;
    }
    
    
    
}
