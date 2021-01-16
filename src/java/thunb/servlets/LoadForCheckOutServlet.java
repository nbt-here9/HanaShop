/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thunb.cart.CartItemObject;
import thunb.cart.CartObject;
import thunb.daos.PaymentMethodsDAO;
import thunb.daos.ProductsDAO;
import thunb.dtos.UsersDTO;
import thunb.errors.CartErrors;
import thunb.utilities.ConstantsKey;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "LoadForCheckOutServlet", urlPatterns = {"/LoadForCheckOutServlet"})
public class LoadForCheckOutServlet extends HttpServlet {

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
            boolean isNotAdmin = true;
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session != null) {
                UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                if (loginUser != null && loginUser.getRoleID() == 1) {
                    isNotAdmin = false;
                }
                
                boolean loadPrice = (boolean) session.getAttribute("LOAD_PRICE");
                //Except admin
                if (!isNotAdmin) {
                    request.setAttribute("ERR_MESS", "You can not access this function!!!");
                } else {

                    CartObject cart = (CartObject) session.getAttribute("CART");
                    if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {

                        //Cart Item Price Update
                        ProductsDAO dao = new ProductsDAO();
                        for (Map.Entry<Integer, CartItemObject> en : cart.getItems().entrySet()) {
                            int newPrice = dao.getPriceByID(en.getKey());
                            if (newPrice >= 0) {
                                en.getValue().setPrice(newPrice);
                            } else {
                                cart.updateCart(en.getKey(), 0);
                            }
                        }

                        // Check Stock Quantity
                        boolean flag = true;
                        Map<Integer, CartItemObject> items = cart.getItems();
                        for (Map.Entry<Integer, CartItemObject> entry : items.entrySet()) {
                            Integer key = entry.getKey();
                            CartItemObject value = entry.getValue();
                            int quantity = dao.getQuantityByID(key);
                            if (quantity < value.getQuantity()) {
                                CartErrors err = new CartErrors();
                                err.setProductID(key);
                                err.setQuantityLeft(quantity);
                                err.setOutOfStockErr(entry.getValue().getItemName() + "is not Enough Quantity In Stock");
                                request.setAttribute("ERROR", err);
                                flag = false;
                                break;
                            }
                        }
                        
                        
                        
                        //Load Payment
                        PaymentMethodsDAO paymentMethodDAO = new PaymentMethodsDAO();
                        int total = paymentMethodDAO.getPaymentMethodsList();
                        if (total > 0) {
                            request.setAttribute("PAYMENT_LIST", paymentMethodDAO.getMethodList());
                        }

                        if (flag) {
                            String action = (String) request.getAttribute("Action");
                            if (action != null) {
                                 url += "?Action=" + action;
                            }
                        } else {
                            url = ConstantsKey.VIEW_CART_PAGE;
                        }

                    }
                }

                session.setAttribute("LOAD", false);
                session.setAttribute("LOAD_CATEGORY_AND_STATUS", false);
            }

        } catch (SQLException ex) {
            log("LoadForCheckOutServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("LoadForCheckOutServlet_NamingException:" + ex.getMessage());
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