/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import thunb.utilities.DBHelpers;

/**
 *
 * @author Banh Bao
 */
public class OrdersDAO {

    private static Connection cn;
    private static PreparedStatement pst;
    private static ResultSet rs;

    public OrdersDAO() {
        this.cn = null;
        this.pst = null;
        this.rs = null;
    }

    private static void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (cn != null) {
            cn.close();
        }
    }
    
    public boolean createOrder(String orderID,String username,String customerName,
            String customerAddress,String phone, Timestamp orderDate,long total,int paymentMethod) 
            throws SQLException, NamingException{
        try{
            cn= DBHelpers.makeConnection();
            if(cn!=null){
                String sql = "INSERT INTO Orders (orderID,username,customerName,"
                        + "customerAddress,customerPhone,orderDate,total,paymentMethod) "
                        + "VALUES (?,?,?,?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, orderID);
                pst.setString(2, username);
                pst.setNString(3, customerName);
                pst.setNString(4, customerAddress);
                pst.setString(5, phone);
                pst.setTimestamp(6, orderDate);
                pst.setLong(7, total);
                pst.setInt(8, paymentMethod);
                int result = pst.executeUpdate();
                if(result>0)return true;
            }   
        }finally{
            closeConnection();
        }
        return false;
    }
    
//    
    
    
    
    
    
}
