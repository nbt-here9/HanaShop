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
public class ProductErrors implements Serializable{
    private String emptyProductName;
    private String emptyDescription;
    private String emptyCategory;
    private String emptyStatus;
    private String invalidPrice;
    private String invalidQuantity;
    private String invalidFileType;


    public ProductErrors() {
    }

    /**
     * @return the emptyProductName
     */
    public String getEmptyProductName() {
        return emptyProductName;
    }

    /**
     * @param emptyProductName the emptyProductName to set
     */
    public void setEmptyProductName(String emptyProductName) {
        this.emptyProductName = emptyProductName;
    }

    /**
     * @return the emptyDescription
     */
    public String getEmptyDescription() {
        return emptyDescription;
    }

    /**
     * @param emptyDescription the emptyDescription to set
     */
    public void setEmptyDescription(String emptyDescription) {
        this.emptyDescription = emptyDescription;
    }

    /**
     * @return the emptyCategory
     */
    public String getEmptyCategory() {
        return emptyCategory;
    }

    /**
     * @param emptyCategory the emptyCategory to set
     */
    public void setEmptyCategory(String emptyCategory) {
        this.emptyCategory = emptyCategory;
    }

    /**
     * @return the emptyStatus
     */
    public String getEmptyStatus() {
        return emptyStatus;
    }

    /**
     * @param emptyStatus the emptyStatus to set
     */
    public void setEmptyStatus(String emptyStatus) {
        this.emptyStatus = emptyStatus;
    }

    /**
     * @return the invalidPrice
     */
    public String getInvalidPrice() {
        return invalidPrice;
    }

    /**
     * @param invalidPrice the invalidPrice to set
     */
    public void setInvalidPrice(String invalidPrice) {
        this.invalidPrice = invalidPrice;
    }

    /**
     * @return the invalidQuantity
     */
    public String getInvalidQuantity() {
        return invalidQuantity;
    }

    /**
     * @param invalidQuantity the invalidQuantity to set
     */
    public void setInvalidQuantity(String invalidQuantity) {
        this.invalidQuantity = invalidQuantity;
    }

    /**
     * @return the invalidFileType
     */
    public String getInvalidFileType() {
        return invalidFileType;
    }

    /**
     * @param invalidFileType the invalidFileType to set
     */
    public void setInvalidFileType(String invalidFileType) {
        this.invalidFileType = invalidFileType;
    }

    
}
