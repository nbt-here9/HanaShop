/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import thunb.daos.OrderDetailsDAO;
import thunb.daos.OrdersDAO;
import thunb.daos.ProductsDAO;
import thunb.dtos.UsersDTO;
import thunb.errors.CheckOutErrors;
import thunb.utilities.ConstantsKey;
import thunb.utilities.Utilities;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

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
        String url = ConstantsKey.CHECK_OUT_PAGE;
        boolean redirect = false;
        try {

            boolean valid = true;
            CheckOutErrors errors = new CheckOutErrors();
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");

                if (cart != null) {
                    String txtName = request.getParameter("txtName");
                    String txtAddress = request.getParameter("txtAddress");
                    String txtPhone = request.getParameter("txtPhone");

                    UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                    String loginUsername = null;
                    String loginName = "";
                    if (loginUser != null) {
                        loginUsername = loginUser.getUsername();
                        txtName = loginUser.getName();
                        txtAddress = loginUser.getAddress();
                        txtPhone = loginUser.getPhone();
                    }

                    //Valid info
                    if (txtName == null || txtName.trim().isEmpty()) {
                        valid = false;
                        errors.setEmptyNameErr("Customer Name must not be empty.");
                    }
                    if (txtAddress == null || txtAddress.trim().isEmpty()) {
                        valid = false;
                        errors.setEmptyAddressErr("Customer Address must not be empty.");
                    }
                    if (txtPhone == null || txtPhone.trim().isEmpty() || !txtPhone.matches("(03|07|08|09|01[2|6|8|9])+([0-9]{8})\\b")) {
                        valid = false;
                        errors.setInvalidPhoneNumErr("Not a valid telephone number in Vietnam.");
                    }

                    if (!valid) {
                        request.setAttribute("ERRORS", errors);
                    } //Check out
                    else {
                        String txtPaymentMethod = request.getParameter("txtPaymentMethod");
                        if (txtPaymentMethod != null && !txtPaymentMethod.trim().isEmpty() && txtPaymentMethod.matches("\\d+")) {
                            int paymentMethod = Integer.parseInt(txtPaymentMethod);

                            OrdersDAO ordersDAO = new OrdersDAO();
                            ProductsDAO productsDAO = new ProductsDAO();

                            String newOrderID = Utilities.OrderIDGenerate();
                            Timestamp orderDate = Utilities.getCurrentTime();

                            boolean newOrder = ordersDAO.createOrder(newOrderID, loginUsername, txtName, txtAddress,
                                    txtPhone, orderDate, cart.total(), paymentMethod);
                            if (newOrder) {
                                OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                                Map<Integer, CartItemObject> items = cart.getItems();
                                boolean rs = orderDetailsDAO.addOrderDetails(newOrderID, items);
                                if (!rs) {
                                    request.setAttribute("CHECKOUT_FAILED", "FAILED");
//                                //delete order
                                } else {

                                    boolean decreaseStock = productsDAO.decreaseQuantityByID(items);
                                    if (decreaseStock) {
                                        request.setAttribute("CHECKOUT_SUCCESS", newOrderID);

                                        // session.removeAttribute("CART");
                                    }

                                }
                            }
                        }
                    }
                }
                session.setAttribute("CHECK_AND_LOAD", false);
            }

        } catch (NumberFormatException ex) {
            log("CheckOutServlet_NumberFormatException:" + ex.getMessage());
        } catch (SQLException ex) {
            log("CheckOutServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("CheckOutServlet_NamingException:" + ex.getMessage());
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
