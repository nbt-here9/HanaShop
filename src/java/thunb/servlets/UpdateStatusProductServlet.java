/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thunb.daos.LogsDAO;
import thunb.daos.ProductsDAO;
import thunb.dtos.UsersDTO;
import thunb.utilities.ConstantsKey;
import thunb.utilities.Utilities;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "UpdateStatusProductServlet", urlPatterns = {"/UpdateStatusProductServlet"})
public class UpdateStatusProductServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String url = ConstantsKey.LOAD_DATA_SERVLET;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                if (loginUser != null && loginUser.getRoleID() == ConstantsKey.ADMIN_ROLE) {
                    String txtProductID = null;
                    txtProductID = (String) request.getParameter("txtProductID");

                    if (txtProductID != null || !txtProductID.trim().isEmpty()
                            || txtProductID.matches("\\d+")) {
                        
                        int productID = Integer.parseInt(txtProductID);
                        ProductsDAO dao = new ProductsDAO();
                        boolean rs = dao.updateStatusProduct(productID, 0);
                        if (rs) {
                            Timestamp createDate = Utilities.getCurrentTime();
                            LogsDAO logsDAO = new LogsDAO();
                            logsDAO.addLogs(productID, loginUser.getUsername(), createDate, "Delete");
                            
                            request.setAttribute("REMOVE_SUCCESS", "Delete Successfully! (Meaning update the status of the Food to Inactive)");

                            session.setAttribute("LOAD_ALL", true);
                            session.setAttribute("LOAD_CATEGORY_AND_STATUS", true);
                        }
                    }
                }

            }
        } catch (SQLException ex) {
            log("UpdateStatusProductServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("UpdateStatusProductServlet_NamingException:" + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
