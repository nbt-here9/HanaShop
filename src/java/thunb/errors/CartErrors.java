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
public class CartErrors implements Serializable{
    private int productID;
    private String outOfStockErr;
    private int quantityLeft;
    private String statusChangedErr;

    public CartErrors() {
    }

    public CartErrors(int productID, String outOfStockErr, int quantityLeft, String statusChangedErr) {
        this.productID = productID;
        this.outOfStockErr = outOfStockErr;
        this.quantityLeft = quantityLeft;
        this.statusChangedErr = statusChangedErr;
    }

    /**
     * @return the productID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * @param productID the productID to set
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * @return the outOfStockErr
     */
    public String getOutOfStockErr() {
        return outOfStockErr;
    }

    /**
     * @param outOfStockErr the outOfStockErr to set
     */
    public void setOutOfStockErr(String outOfStockErr) {
        this.outOfStockErr = outOfStockErr;
    }

    /**
     * @return the quantityLeft
     */
    public int getQuantityLeft() {
        return quantityLeft;
    }

    /**
     * @param quantityLeft the quantityLeft to set
     */
    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    /**
     * @return the statusChangedErr
     */
    public String getStatusChangedErr() {
        return statusChangedErr;
    }

    /**
     * @param statusChangedErr the statusChangedErr to set
     */
    public void setStatusChangedErr(String statusChangedErr) {
        this.statusChangedErr = statusChangedErr;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
