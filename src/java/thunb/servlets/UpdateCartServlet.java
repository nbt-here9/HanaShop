/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thunb.cart.CartObject;
import thunb.daos.ProductsDAO;
import thunb.dtos.UsersDTO;
import thunb.errors.CartErrors;
import thunb.utilities.ConstantsKey;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {

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

        String url = ConstantsKey.VIEW_CART_PAGE;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {

                UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                if (loginUser == null || loginUser.getRoleID() == 0) {
                    
                    CartObject cart = (CartObject) session.getAttribute("CART");
                    if (cart != null) {
                        
                        String txtProductID = request.getParameter("txtProductID");
                        
                        if (txtProductID != null 
                                && !txtProductID.trim().isEmpty() 
                                && txtProductID.matches("\\d+")) {
                            
                            int productID = Integer.parseInt(txtProductID);
                            ProductsDAO dao = new ProductsDAO();
                            int inStock = dao.getQuantityByID(productID);
                            String txtQuantity = request.getParameter("txtQuantity");
                            
                            if (txtQuantity != null 
                                    && !txtQuantity.trim().isEmpty() 
                                    && txtQuantity.matches("\\d+")) {
                                int quantity = Integer.parseInt(txtQuantity);
                                if (quantity > 0) {
                                    if (quantity <= inStock) {
                                        cart.updateCart(productID, quantity);
                                        session.setAttribute("CART", cart);
                                    } else {
                                        CartErrors err = new CartErrors();
                                        err.setProductID(productID);
                                        err.setQuantityLeft(inStock);
                                        err.setOutOfStockErr("Not Enough Quantity In Stock!");
                                        request.setAttribute("ERROR", err);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            log("UpdateCartServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("UpdateCartServlet_NamingException:" + ex.getMessage());
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
