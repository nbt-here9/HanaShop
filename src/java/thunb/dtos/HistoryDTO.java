/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dtos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Banh Bao
 */
public class HistoryDTO extends OrdersDTO {
    private List<OrderDetailsDTO> detailList;

    public HistoryDTO() {
    }

    public HistoryDTO(List<OrderDetailsDTO> detailList, String orderID, String username, String customerName, String customerAddress, String customerPhone, Timestamp orderDate, int paymentMethod, int paymentStatus, long total) {
        super(orderID, username, customerName, customerAddress, customerPhone, orderDate, paymentMethod, paymentStatus, total);
        this.detailList = detailList;
    }

    public HistoryDTO(Object object, Object object0, String orderID, String username, String customerName, String customerAddress, String customerPhone, Timestamp orderDate, int paymentMethod, int paymentStatus, long total, Object object1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
