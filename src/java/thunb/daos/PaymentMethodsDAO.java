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
import javax.naming.NamingException;
import thunb.dtos.PaymentMethodsDTO;
import thunb.utilities.DBHelpers;

/**
 *
 * @author Banh Bao
 */
public class PaymentMethodsDAO {

    private static Connection cn;
    private static PreparedStatement pst;
    private static ResultSet rs;

    public PaymentMethodsDAO() {
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

    private List<PaymentMethodsDTO> methodList;

    public List<PaymentMethodsDTO> getMethodList() {
        return methodList;
    }

    public int getPaymentMethodsList() throws SQLException, NamingException {
        int total = 0;
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "select methodType,methodName from PaymentMethods";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (this.methodList == null) {
                        this.methodList = new ArrayList<>();
                    }
                    int methodType = rs.getInt("methodType");
                    String methodName = rs.getString("methodName");
                    this.methodList.add(new PaymentMethodsDTO(methodType, methodName));
                    total++;
                }
            }
        } finally {
            closeConnection();
        }
        return total;
    }

    public String getMethodNameByType(int methodType) throws SQLException, NamingException {
        try {
            cn = DBHelpers.makeConnection();
            if (cn != null) {
                String sql = "select methodName from PaymentMethods where methodType=?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, methodType);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getString("methodName");
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }
}
