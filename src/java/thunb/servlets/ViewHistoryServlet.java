/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thunb.daos.OrderDetailsDAO;
import thunb.daos.OrdersDAO;
import thunb.daos.ProductsDAO;
import thunb.dtos.HistoryDTO;
import thunb.dtos.OrderDetailsDTO;
import thunb.dtos.OrdersDTO;
import thunb.dtos.UsersDTO;
import thunb.utilities.ConstantsKey;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "ViewHistoryServlet", urlPatterns = {"/ViewHistoryServlet"})
public class ViewHistoryServlet extends HttpServlet {

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

        String url = ConstantsKey.HISTORY_PAGE;
        String valid = null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                if (loginUser != null && loginUser.getRoleID() == ConstantsKey.USER_ROLE) {
                    List<HistoryDTO> listHistory = null;
                    HistoryDTO history = null;

                    OrdersDAO dao = new OrdersDAO();
                    boolean rs = dao.getOrdersByUsername(loginUser.getUsername());
                    if (rs) {
                        List<OrdersDTO> listOrders = dao.getListOrders();
                        for (OrdersDTO order : listOrders) {
                            OrderDetailsDAO orderDetailDAO = new OrderDetailsDAO();
                            List<OrderDetailsDTO> listDetail = orderDetailDAO.getOrderDetailsList(order.getOrderID());
                            if (listDetail != null) {
                                if (listHistory == null) {
                                    listHistory = new ArrayList<>();
                                }
                                ProductsDAO proDAO = new ProductsDAO();
                                List<String> listProName = new ArrayList<>();
                                for (OrderDetailsDTO detail : listDetail) {
                                    String proName = proDAO.getNameByID(detail.getProductID());
                                    listProName.add(proName);
                                }
                                String orderID = order.getOrderID();
                                String username = order.getUsername();
                                String customerName = order.getCustomerName();
                                String customerAddress = order.getCustomerAddress();
                                String customerPhone = order.getCustomerPhone();
                                Timestamp orderDate = order.getOrderDate();
                                int paymentMethod = order.getPaymentMethod();
                                long total = order.getTotal();
                                history = new HistoryDTO(listDetail, listProName, orderID, username, customerName, customerAddress, customerPhone, orderDate, paymentMethod, total);
                                listHistory.add(history);
                            }
                        }
                    }

                    if (listHistory != null) {

                        String txtSearchValue = (String) request.getParameter("txtSearchValue");
                        String txtFromDate = (String) request.getParameter("txtFromDate");
                        String txtToDate = (String) request.getParameter("txtToDate");

                        List<HistoryDTO> searchResult = new ArrayList<>();
                        if ((txtSearchValue != null && !txtSearchValue.trim().isEmpty())
                                || (txtFromDate != null && !txtFromDate.trim().isEmpty()
                                && txtToDate != null && !txtToDate.trim().isEmpty())) {

                            //Search by product name
                            if (txtSearchValue != null && !txtSearchValue.trim().isEmpty()) {
                                txtSearchValue = txtSearchValue.trim();
                                for (HistoryDTO order : listHistory) {
                                    for (String proName : order.getProductNameList()) {
                                        if (proName.contains(txtSearchValue)) {
                                            searchResult.add(order);
                                        }
                                    }
                                }
                            }
                            //Search by shopping date
                            if (txtFromDate != null && !txtFromDate.trim().isEmpty()
                                    && txtToDate != null && !txtToDate.trim().isEmpty()) {

                                Timestamp fromDate, toDate;
                                txtFromDate = txtFromDate.trim();
                                txtToDate = txtToDate.trim();
                                try {
                                    LocalDateTime fromLDT = LocalDateTime.parse(txtFromDate);
                                    LocalDateTime toLDT = LocalDateTime.parse(txtToDate);

                                    fromDate = Timestamp.valueOf(fromLDT);
                                    toDate = Timestamp.valueOf(toLDT);
                                    if (fromDate.after(toDate)) {
                                        valid = "FromDate Must be before ToDate";
                                    }

                                    //
                                    if (!searchResult.isEmpty()) {
                                        for (int i = 0; i < searchResult.size(); i++) {
                                            Timestamp creatDate = searchResult.get(i).getOrderDate();
                                           if (creatDate.before(fromDate) || creatDate.after(toDate)) {
                                                searchResult.remove(searchResult.get(i));
                                            }
                                        }

                                    } else {
                                        
                                        for (int i = 0; i < listHistory.size(); i++) {
                                            Timestamp creatDate = listHistory.get(i).getOrderDate();
                                           if (creatDate.after(fromDate) && creatDate.before(toDate)) {
                                                searchResult.add(listHistory.get(i));
                                            }
                                        }
                                    }

                                } catch (DateTimeParseException ex) {
                                    valid = "Invalid FromTo Date";
                                }

                            }
                        }

                        if (valid == null) {
                            if (searchResult.isEmpty()) {
                                session.setAttribute("HISTORY", listHistory);
                                request.setAttribute("NO_RESULT", true);
                            } else {
                                session.setAttribute("HISTORY", searchResult);
                                request.setAttribute("NO_RESULT", false);
                            }
                        } else {
                            request.setAttribute("ERROR", valid);
                        }
                    }

                }
            }
        } catch (NamingException ex) {
            log("ViewHistoryServlet_NamingException:" + ex.getMessage());
        } catch (SQLException ex) {
            log("ViewHistoryServlet_SQLException:" + ex.getMessage());
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
