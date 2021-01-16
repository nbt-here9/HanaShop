/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import thunb.dtos.ProductStatusDTO;
import thunb.utilities.DBHelpers;

/**
 *
 * @author Banh Bao
 */
public class ProductStatusDAO implements Serializable {

    private static Connection cn;
    private static PreparedStatement pst;
    private static ResultSet rs;

    private List<ProductStatusDTO> productStatusList;

    public List<ProductStatusDTO> getProductStatusList() {
        return productStatusList;
    }

    public ProductStatusDAO() {
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

    public int getProductStatus() throws NamingException, SQLException {
        int total = 0;
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT statusID, statusName FROM ProductStatus";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (this.productStatusList == null) {
                        this.productStatusList = new ArrayList<>();
                    }
                    int statusID = rs.getInt("statusID");
                    String statusName = rs.getString("statusName");
                    this.productStatusList.add(new ProductStatusDTO(statusID, statusName));
                    total++;
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public int getStatusIDFromName(String statusName) throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT statusID FROM ProductStatus "
                        + "WHERE statusName = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, statusName);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("statusID");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }

}
