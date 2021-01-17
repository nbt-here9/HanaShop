/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thunb.utilities.ConstantsKey;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

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

        String url = ConstantsKey.ERROR_PAGE;

        try {
            String action = request.getParameter("Action");
//            request.setAttribute("NEXT_PAGE", action);

            //New request
            if (action == null || "Home".equals(action)) { //OKAYYY
                HttpSession session = request.getSession(true);
                if (session != null) {
                    session.setAttribute("LOAD_ALL", true);
                    session.setAttribute("LOAD_CATEGORY_AND_STATUS", true);
                }
                url = ConstantsKey.LOAD_DATA_SERVLET;
            }

            if (action != null) {

                if ("LoadData".equals(action)) {
                    url = ConstantsKey.LOAD_DATA_SERVLET;
                } else if ("CheckAndLoad".equals(action)) {
                    url = ConstantsKey.LOAD_FOR_CHECKOUT_SERVLET;
                } //All user action
                if ("Sign in".equals(action)) { //OKAYYY
                    url = ConstantsKey.LOGIN_PAGE;
                } else if ("Login".equals(action)) {
                    url = ConstantsKey.LOGIN_SERVLET;
                } else if ("Sign out".equals(action)) {
                    url = ConstantsKey.LOGOUT_SERVLET;
                } //Shopping
                else if ("Add To Cart".equals(action)) {
                    url = ConstantsKey.ADD_TO_CART_SERVLET;
                } else if (action.contains("Your cart")) {
                    url = ConstantsKey.VIEW_CART_PAGE;
                } else if ("Remove From Cart".equals(action)) {
                    url = ConstantsKey.REMOVE_FROM_CART_SERVLET;
                } else if ("Update Cart".equals(action)) {
                    url = ConstantsKey.UPDATE_CART_SERVLET;
                } else if ("Check out".equals(action)) {
                    url = ConstantsKey.CHECK_OUT_PAGE;
                } else if ("Proceed".equals(action)) {
                    url = ConstantsKey.CHECK_OUT_SERVLET;
                } //Admin action
                else if ("Edit".equals(action)) {
                    url = ConstantsKey.LOAD_PRODUCT_SERVLET;
                } else if ("CreateProduct".equals(action)) {
                    url = ConstantsKey.CREATE_PRODUCT_PAGE;
                } else if ("Create Category".equals(action)) {
                    url = ConstantsKey.CREATE_CATEGORY_SERVLET;
                } else if ("Remove".equals(action)) {
                    url = ConstantsKey.UPDATE_STATUS_PRODUCT_SERVLET;
                }
                

            }

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
