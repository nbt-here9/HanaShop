/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.utilities;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Banh Bao
 */
public class DBHelpers implements Serializable {
    public static Connection makeConnection() 
            throws SQLException, NamingException {
        
        Context context = new InitialContext();
        Context tomCatContext = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomCatContext.lookup("HanaShopDB");
        Connection con = ds.getConnection();
        return con;
    }
}
