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
import thunb.daos.CategoriesDAO;
import thunb.daos.ProductStatusDAO;
import thunb.daos.ProductsDAO;
import thunb.dtos.UsersDTO;
import thunb.utilities.ConstantsKey;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "LoadDataServlet", urlPatterns = {"/LoadDataServlet"})
public class LoadDataServlet extends HttpServlet {

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
            //Check role
            boolean isAdmin = false;
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsersDTO userCurrent = (UsersDTO) session.getAttribute("LOGIN_USER");
                if (userCurrent != null) {
                    if (userCurrent.getRoleID() == ConstantsKey.ADMIN_ROLE) {
                        isAdmin = true;
                    }
                }

                //Check flag
                boolean loadAll = (boolean) session.getAttribute("LOAD_ALL");
                boolean loadCateAndStatus = (boolean) session.getAttribute("LOAD_CATEGORY_AND_STATUS");

                //Load Category And Status
                //Load Category list
                if (loadCateAndStatus) {
                    CategoriesDAO categoriesDAO = new CategoriesDAO();
                    ProductStatusDAO productStatusDAO = new ProductStatusDAO();
                    int totalCategory = categoriesDAO.getCategoriesList();
                    if (totalCategory > 0) {
                        request.setAttribute("CATEGORY_LIST", categoriesDAO.getCategoriesDTOList());
                    }
                    //Load Products Status
                    int numOfStatus = productStatusDAO.getProductStatus();
                    if (numOfStatus > 0) {
                        request.setAttribute("PRODUCT_STATUS_LIST", productStatusDAO.getProductStatusList());
                    }

                    String action = request.getParameter("AdminAction");
                    if (action != null) {
                        if ("CreateProduct".equals(action)) {
                            url = ConstantsKey.CREATE_PRODUCT_PAGE;
                        } else if ("Edit".equals(action)) {
                            url = ConstantsKey.LOAD_PRODUCT_SERVLET;
                        }
                    }
                    
                }

                //Load Products List
                if (loadAll) {

                    //Load Product and Paging
                    ProductsDAO dao = new ProductsDAO();
                    boolean rs = dao.getMinMaxPrice();
                    if (rs) {
                        request.setAttribute("MIN_PRICE", dao.getMinPrice());
                        request.setAttribute("MAX_PRICE", dao.getMaxPrice());
                    }

                    if (request.getParameter("txtSearchValue") == null
                            && request.getAttribute("PRODUCT_LIST") == null) {

                        int total = dao.searchProduct("", dao.getMinPrice(), dao.getMaxPrice(), "%", ConstantsKey.DEFAULT_PAGE, isAdmin);
                        if (total > 0) {

                            if (isAdmin) {
                                url = ConstantsKey.ADMIN_PAGE;
                            } else {
                                url = ConstantsKey.PRODUCT_LIST_PAGE;
                            }
                            request.setAttribute("PRODUCT_LIST", dao.getProductList());

                            int totalPage = dao.getNumberOfProductPage("", dao.getMinPrice(), dao.getMaxPrice(), "%", false);
                            request.setAttribute("CURR_PAGE", ConstantsKey.DEFAULT_PAGE);
                            request.setAttribute("MAX_PAGE", totalPage);
                        }

                    } else { //Search
                        url = ConstantsKey.SEARCH_PRODUCT_SERVLET;
                    }

                }

                //Flag
                session.setAttribute("LOAD_ALL", false);
                session.setAttribute("LOAD_CATEGORY_AND_STATUS", false);

            }
        } catch (SQLException ex) {
            log("LoadDataServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("LoadDataServlet_NamingException:" + ex.getMessage());
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
