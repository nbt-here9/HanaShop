/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.dtos;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Banh Bao
 */
public class HistoryDTO extends OrdersDTO {
    private List<OrderDetailsDTO> detailList;
    private List<String> productNameList;

    public HistoryDTO() {
    }

    public HistoryDTO(List<OrderDetailsDTO> detailList, List<String> productNameList, String orderID, String username, String customerName, String customerAddress, String customerPhone, Timestamp orderDate, int paymentMethod, long total) {
        super(orderID, username, customerName, customerAddress, customerPhone, orderDate, paymentMethod, total);
        this.detailList = detailList;
        this.productNameList = productNameList;
    }

    

    /**
     * @return the detailList
     */
    public List<OrderDetailsDTO> getDetailList() {
        return detailList;
    }

    /**
     * @param detailList the detailList to set
     */
    public void setDetailList(List<OrderDetailsDTO> detailList) {
        this.detailList = detailList;
    }

    /**
     * @return the productNameList
     */
    public List<String> getProductNameList() {
        return productNameList;
    }

    /**
     * @param productNameList the productNameList to set
     */
    public void setProductNameList(List<String> productNameList) {
        this.productNameList = productNameList;
    }

    
}
