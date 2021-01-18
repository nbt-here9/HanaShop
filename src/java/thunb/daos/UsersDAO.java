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
import javax.naming.NamingException;
import thunb.dtos.UsersDTO;
import thunb.utilities.DBHelpers;

/**
 *
 * @author Banh Bao
 */
public class UsersDAO {

    private static Connection cn;
    private static PreparedStatement pst;
    private static ResultSet rs;

    public UsersDAO() {
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

    private UsersDTO loginUser;

    public UsersDTO getLoginUser() {
        return loginUser;
    }

    public boolean checkLogin(String username, String password) throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "SELECT username, googleID, name, address,phone, "
                        + "roleID FROM Users WHERE username=? AND password=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String googleID = rs.getString("googleID");
                    String name = rs.getNString("name");
                    String address = rs.getNString("address");
                    String phone = rs.getString("phone");
                    int roleID = rs.getInt("roleID");
                    this.loginUser = new UsersDTO(username, password, googleID, name, address, phone, roleID);
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

   public boolean checkLogin(String googleID) throws SQLException, NamingException {
        try{
            cn = DBHelpers.makeConnection();
            if(cn!=null){
                String sql = "select username,googleID,name,address,phone,roleID " +
                        "from Users " +
                        "where googleID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1,googleID);
                rs = pst.executeQuery();
                if(rs.next()){
                    String username = rs.getString("username");
                    String name = rs.getNString("name");
                    String address = rs.getNString("address");
                    String phone = rs.getString("phone");
                    int roleID = rs.getInt("roleID");
                    this.loginUser = new UsersDTO(username, googleID, name, address, phone, roleID);
                    return true;
                }
            }
        }finally{
            closeConnection();
        }
        return false;
    }
}
