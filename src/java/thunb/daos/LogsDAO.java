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
public class LogsDAO {

    private static Connection cn;
    private static PreparedStatement pst;
    private static ResultSet rs;

    public LogsDAO() {
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

    public boolean addLogs(int productID, String username, Timestamp upTime, String action) throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "INSERT INTO Logs(productID, username, timeUp, action) values(?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, productID);
                pst.setString(2, username);
                pst.setTimestamp(3, upTime);
                pst.setNString(4, action);
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
