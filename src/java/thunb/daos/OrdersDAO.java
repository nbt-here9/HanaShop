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
import thunb.dtos.HistoryDTO;
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

    public boolean createOrder(String orderID, String username, String customerName,
            String customerAddress, String phone, Timestamp orderDate, long total, int paymentMethod)
            throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
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
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public HistoryDTO getOrderByID(String orderID, String username) throws NamingException, SQLException {
        HistoryDTO order = null;
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "select customerName,customerAddress,customerPhone,orderDate,paymentMethod,paymentStatus,total "
                        + "from Orders where orderID=? and username=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, orderID);
                pst.setString(2, username);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String customerName = rs.getNString("customerName");
                    String customerAddress = rs.getNString("customerAddress");
                    String customerPhone = rs.getString("customerPhone");
                    Timestamp orderDate = rs.getTimestamp("orderDate");
                    int paymentMethod = rs.getInt("paymentMethod");
                    int paymentStatus = rs.getInt("paymentStatus");
                    long total = rs.getLong("total");
                    order = new HistoryDTO(null, null, orderID, username, customerName, customerAddress, customerPhone, orderDate, paymentMethod, paymentStatus, total, null);
                }
            }
        } finally {
            closeConnection();
        }
        return order;
    }

}
