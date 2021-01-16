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
import thunb.dtos.CategoriesDTO;
import thunb.utilities.DBHelpers;

/**
 *
 * @author Banh Bao
 */
public class CategoriesDAO implements Serializable {

    private static Connection cn;
    private static PreparedStatement pst;
    private static ResultSet rs;

    public CategoriesDAO() {
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

    private List<CategoriesDTO> categoriesDTOList;

    public List<CategoriesDTO> getCategoriesDTOList() {
        return categoriesDTOList;
    }

    public int getCategoriesList() throws SQLException, NamingException {
        int result = 0;
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT categoryID, categoryName "
                        + "FROM Categories ";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();

                while (rs.next()) {
                    if (this.categoriesDTOList == null) {
                        this.categoriesDTOList = new ArrayList<>();
                    }
                    int categoryID = rs.getInt("categoryID");
                    String categoryName = rs.getNString("categoryName");
                    this.categoriesDTOList.add(new CategoriesDTO(categoryID, categoryName));
                    result++;
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getCategoryIDByName(String categoryName)
            throws NamingException, SQLException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT categoryID FROM Categories "
                        + "WHERE categoryName = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, categoryName);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("categoryID");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }

    public boolean createCategory(String categoryName) throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Categories(categoryName) VALUES (?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, categoryName);
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
}
