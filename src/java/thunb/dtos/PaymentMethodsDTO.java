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
public class PaymentMethodsDTO implements Serializable{
    private int methodType;
    private String methodName;

    public PaymentMethodsDTO() {
    }

    public PaymentMethodsDTO(int methodType, String methodName) {
        this.methodType = methodType;
        this.methodName = methodName;
    }

    /**
     * @return the methodType
     */
    public int getMethodType() {
        return methodType;
    }

    /**
     * @param methodType the methodType to set
     */
    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    
    
}
