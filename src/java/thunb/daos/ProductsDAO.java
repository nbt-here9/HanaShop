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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import thunb.cart.CartItemObject;
import thunb.dtos.OrderDetailsDTO;
import thunb.dtos.ProductsDTO;
import thunb.utilities.ConstantsKey;
import thunb.utilities.DBHelpers;

/**
 *
 * @author Banh Bao
 */
public class ProductsDAO implements Serializable {

    private static Connection cn;
    private static PreparedStatement pst;
    private static ResultSet rs;

    public ProductsDAO() {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
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

    private ProductsDTO selectedProduct;
    private int minPrice;
    private int maxPrice;
    private List<ProductsDTO> productList;

    public ProductsDTO getSelectedProduct() {
        return selectedProduct;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public List<ProductsDTO> getProductList() {
        return productList;
    }

    public boolean loadSelectedProduct(int productID)
            throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT productName, description, image, price, "
                        + "quantity, createDate, categoryID, statusID "
                        + "FROM Products WHERE productID = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, productID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String productName = rs.getNString("productName");
                    String description = rs.getNString("description");
                    String image = rs.getString("image");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    this.selectedProduct = new ProductsDTO(productID,
                            productName, description, image, price, quantity,
                            categoryID, createDate, statusID);
                    return true;
                }
            }
        } finally {
            closeConnection();
        }

        return false;
    }

    public String getNameByID(int productID)
            throws NamingException, SQLException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT productName FROM Products "
                        + "WHERE productID = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, productID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getNString("productName");
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public int getPriceByID(int productID)
            throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT price FROM Products "
                        + "WHERE productID = ? AND statusID = 1 "
                        + "AND quantity > 0";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, productID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("price");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }

    public int getQuantityByID(int productID)
            throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT quantity FROM Products "
                        + "WHERE productID = ? AND statusID = 1";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, productID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }

    public int getNumberOfProductPage(String searchValue, int minPrice,
            int maxPrice, String categoryID, boolean getAll)
            throws NamingException, SQLException {
        int total = 0;
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql;
                if (!getAll) {
                    sql = "SELECT count(productID) AS numOfProducts "
                            + "FROM Products "
                            + "WHERE quantity > 0 AND statusID=1 "
                            + "AND (productName LIKE ? OR description like ?) "
                            + "AND price>=? AND price<=? AND categoryID like ? ";
                    pst = cn.prepareStatement(sql);
                    pst.setNString(1, "%" + searchValue + "%");
                    pst.setNString(2, "%" + searchValue + "%");
                    pst.setInt(3, minPrice);
                    pst.setInt(4, maxPrice);
                    pst.setString(5, categoryID);
                } else {
                    sql = "SELECT count(productID) AS numOfProducts "
                            + "FROM Products "
                            + "WHERE productName LIKE ? OR description LIKE ? ";
                    pst = cn.prepareStatement(sql);
                    pst.setNString(1, "%" + searchValue + "%");
                    pst.setNString(2, "%" + searchValue + "%");
                }
                rs = pst.executeQuery();
                if (rs.next()) {
                    total = rs.getInt("numOfProducts");
                }
            }
        } finally {
            closeConnection();
        }
        return (int) Math.ceil(total / (float) ConstantsKey.PRODUCT_PER_PAGE);
    }

    public boolean getMinMaxPrice()
            throws NamingException, SQLException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT MIN(price) AS minPrice, "
                        + "MAX(price) AS maxPrice "
                        + "FROM Products";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    minPrice = rs.getInt("minPrice");
                    maxPrice = rs.getInt("maxPrice");
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

//    public int loadProduct(int page, boolean getAll)
//            throws SQLException, NamingException {
//        int total = 0;
//        try {
//            cn = DBHelpers.makeConnection();
//            if (cn != null) {
//                String sql;
//                String productActive = " ";
//                if (!getAll) {
//                    productActive = "WHERE statusID = 1 AND quantity > 0 ";
//                }
//
//                sql = "SELECT productID, productName, image, description, "
//                        + "price, quantity, createDate, categoryID "
//                        + "FROM Products "
//                        + productActive
//                        + "ORDER BY createDate DESC "
//                        + "OFFSET ? ROWS "
//                        + "FETCH NEXT ? ROWS ONLY ";
//
//                pst = cn.prepareStatement(sql);
//                pst.setInt(1, page - 1);
//                pst.setInt(2, ConstantsKey.PRODUCT_PER_PAGE);
//                rs = pst.executeQuery();
//                while (rs.next()) {
//                    if (this.productList == null) {
//                        this.productList = new ArrayList<>();
//                    }
//                    int productID = rs.getInt("productID");
//                    String productName = rs.getNString("productName");
//                    String image = rs.getString("image");
//                    String description = rs.getNString("description");
//                    int price = rs.getInt("price");
//                    int quantity = rs.getInt("quantity");
//                    Timestamp createDate = rs.getTimestamp("createDate");
//                    int categoryID = rs.getInt("categoryID");
//                    int statusID = rs.getInt("statusID");
//                    this.productList.add(new ProductsDTO(productID, productName,
//                            image, description, price, quantity, categoryID,
//                            createDate, statusID));
//                    total++;
//                }
//            }
//        } finally {
//            closeConnection();
//        }
//        return total;
//    }
    public int searchProduct(String searchValue, int minPrice, int maxPrice,
            String incategoryID, int page, boolean getAll)
            throws SQLException, NamingException {
        int total = 0;
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String productActive = " ";
                if (!getAll) {
                    productActive = " AND statusID = 1 ";
                }
//                if (!getAll) {
                String sql = "SELECT productID, productName, image, "
                        + "description, price, quantity, createDate, "
                        + "categoryID, statusID "
                        + "FROM Products "
                        + "WHERE (productName LIKE ? OR description LIKE ?) "
                        + "AND price >= ? AND price <= ? "
                        + "AND categoryID LIKE ? "
                        + productActive
                        + "AND quantity > 0 "
                        + "ORDER BY createDate DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                pst = cn.prepareStatement(sql);
                pst.setNString(1, "%" + searchValue + "%");
                pst.setNString(2, "%" + searchValue + "%");
                pst.setInt(3, minPrice);
                pst.setInt(4, maxPrice);
                pst.setString(5, incategoryID);
                pst.setInt(6, (page - 1) * ConstantsKey.PRODUCT_PER_PAGE);
                pst.setInt(7, ConstantsKey.PRODUCT_PER_PAGE);
//                } else { 
//                    String sql = "SELECT productID, productName, image, description, price, quantity, createDate, categoryID, statusID "
//                            + "FROM Products "
//                            + "WHERE (productName LIKE ? OR description LIKE ?) "
//                            + "AND price >= ? AND price <= ? "
//                            + "AND categoryID LIKE ? "
//                            + "AND quantity > 0 "
//                            + "ORDER BY createDate DESC "
//                            + "OFFSET ? ROWS "
//                            + "FETCH NEXT ? ROWS ONLY";
//                    pst = cn.prepareStatement(sql);
//                    pst.setNString(1, "%" + searchValue + "%");
//                    pst.setNString(2, "%" + searchValue + "%");
//                    pst.setInt(3, minPrice);
//                    pst.setInt(4, maxPrice);
//                    pst.setString(5, incategoryID);
//                    pst.setInt(6, page - 1);
//                    pst.setInt(7, ConstantsKey.PRODUCT_PER_PAGE);
//                }
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (this.productList == null) {
                        this.productList = new ArrayList<>();
                    }
                    int productID = rs.getInt("productID");
                    String productName = rs.getNString("productName");
                    String image = rs.getString("image");
                    String description = rs.getNString("description");
                    int price = rs.getInt("price");
                    int quantity = rs.getInt("quantity");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    this.productList.add(new ProductsDTO(productID, productName, description, image, price, quantity, categoryID, createDate, statusID));
                    total++;
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public boolean createProduct(String productName, String image,
            String description, int price, int quantity, Timestamp createDate, int categoryID) throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Products( productName ,image ,"
                        + "description ,price ,quantity ,createDate ,categoryID "
                        + ",statusID) VALUES  (?,?,?,?,?,?,?,1)";
                pst = cn.prepareStatement(sql);
                pst.setNString(1, productName);
                pst.setString(2, image);
                pst.setNString(3, description);
                pst.setInt(4, price);
                pst.setInt(5, quantity);
                pst.setTimestamp(6, createDate);
                pst.setInt(7, categoryID);
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

    public boolean updateProduct(int productID, String productName, String image, String description, int price, int quantity, Timestamp createDate, int categoryID, int statusID)
            throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "UPDATE Products SET productName = ?, image = ?, "
                        + "description  = ?, price = ?, quantity = ?, "
                        + "createDate = ?, categoryID = ?, statusID = ? "
                        + "WHERE productID=?";
                pst = cn.prepareStatement(sql);

                pst.setNString(1, productName);
                pst.setString(2, image);
                pst.setNString(3, description);
                pst.setInt(4, price);
                pst.setInt(5, quantity);
                pst.setTimestamp(6, createDate);
                pst.setInt(7, categoryID);
                pst.setInt(8, statusID);
                pst.setInt(9, productID);
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

    public boolean updateStatusProduct(int productID, int statusID) throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "UPDATE Products SET statusID = ? "
                        + "WHERE productID = ? ";
                pst = cn.prepareStatement(sql);

                pst.setInt(1, statusID);
                pst.setInt(2, productID);
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

    public int getLatestProductID() throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "select MAX(productID) as latestProductID from Products";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("latestProductID");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }

    public boolean decreaseQuantityByID(List<OrderDetailsDTO> items)
            throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                String sql = "update Products set quantity=quantity-? where productID=?";
                pst = cn.prepareStatement(sql);
                for (OrderDetailsDTO detail : items) {
                    pst.setInt(1, detail.getQuantity());
                    pst.setInt(2, detail.getProductID());
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

    public boolean decreaseQuantityByID(Map<Integer, CartItemObject> items)
            throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                String sql = "update Products set quantity=quantity-? where productID=?";
                pst = cn.prepareStatement(sql);
                for (Map.Entry<Integer, CartItemObject> entry : items.entrySet()) {
                    pst.setInt(1, entry.getValue().getQuantity());
                    pst.setInt(2, entry.getKey());
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
