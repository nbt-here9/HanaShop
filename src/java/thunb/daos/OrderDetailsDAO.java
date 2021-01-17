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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import thunb.cart.CartItemObject;
import thunb.dtos.OrderDetailsDTO;
import thunb.utilities.DBHelpers;

/**
 *
 * @author Banh Bao
 */
public class OrderDetailsDAO {

    private static Connection cn;
    private static PreparedStatement pst;
    private static ResultSet rs;

    public OrderDetailsDAO() {
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

    public List<OrderDetailsDTO> getOrderDetailsList(String orderID) throws SQLException, NamingException {
        List<OrderDetailsDTO> orderDetailsList = null;
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "select productID,quantity,total "
                        + "from OrderDetails "
                        + "where orderID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, orderID);
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (orderDetailsList == null) {
                        orderDetailsList = new ArrayList<>();
                    }
                    int productID = rs.getInt("productID");
                    int quantity = rs.getInt("quantity");
                    long total = rs.getLong("total");
                    orderDetailsList.add(new OrderDetailsDTO(orderID, productID, quantity, total));
                }
            }
        } finally {
            closeConnection();
        }
        return orderDetailsList;
    }

    public boolean addOrderDetails(String orderID, Map<Integer, CartItemObject> items) throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "insert into OrderDetails(orderID,productID,quantity,total) values(?,?,?,?)";
                cn.setAutoCommit(false);
                pst = cn.prepareStatement(sql);
                for (Map.Entry<Integer, CartItemObject> entry : items.entrySet()) {
                    pst.setString(1, orderID);
                    pst.setInt(2, entry.getKey());
                    pst.setInt(3, entry.getValue().getQuantity());
                    pst.setLong(4, entry.getValue().total());
                    pst.addBatch();
                    pst.clearParameters();
                }
                int[] executeBatch = pst.executeBatch();
                for (int i : executeBatch) {
                    if (i == PreparedStatement.EXECUTE_FAILED) {
                        return false;
                    }
                }
                cn.commit();
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
