/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Banh Bao
 */
public class HistoryDTO implements Serializable {
    private Timestamp orderDate;
    private int paymentMethod;
    private int quantity;
    private long total;
    
}
