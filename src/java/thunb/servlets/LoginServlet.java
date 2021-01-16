/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import thunb.daos.UsersDAO;
import thunb.dtos.UsersDTO;
import thunb.utilities.ConstantsKey;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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

        boolean success = false;
        String url = ConstantsKey.LOGIN_PAGE;

        try {
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");

            if (username != null && password != null
                    && (!username.trim().isEmpty() || !password.trim().isEmpty())) {
                UsersDAO dao = new UsersDAO();
                boolean rs = dao.checkLogin(username, password);
                if (rs) {
                    UsersDTO loginUser = dao.getLoginUser();
                    HttpSession session = request.getSession(true);
                    session.setAttribute("LOGIN_USER", loginUser);
                    
                    url = ConstantsKey.LOAD_DATA_SERVLET;
                    success = true;
                }
            }

        } catch (SQLException ex) {
            log("LoginServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet_NamingException: " + ex.getMessage());
        } finally {
            if (success) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                request.setAttribute("LOGIN_ERROR", "User not found!");
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
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
