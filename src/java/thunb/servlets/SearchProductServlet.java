/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thunb.daos.CategoriesDAO;
import thunb.daos.ProductsDAO;
import thunb.dtos.ProductsDTO;
import thunb.dtos.UsersDTO;
import thunb.utilities.ConstantsKey;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "SearchProductServlet", urlPatterns = {"/SearchProductServlet"})
public class SearchProductServlet extends HttpServlet {

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
        String url = ConstantsKey.PRODUCT_LIST_PAGE;

        boolean isAdmin = false;
        HttpSession session = request.getSession(false);
        if (session != null) {
            UsersDTO userCurrent = (UsersDTO) session.getAttribute("LOGIN_USER");
            if (userCurrent != null) {
                if (userCurrent.getRoleID() == ConstantsKey.ADMIN_ROLE) {
                    isAdmin = true;
                    url = ConstantsKey.ADMIN_PAGE;
                }
            }
        }

        try {
            String searchValue = request.getParameter("txtSearchValue");
            String txtMin = request.getParameter("minPrice");
            String txtMax = request.getParameter("maxPrice");
            String txtCategory = request.getParameter("txtCategory");
            String txtPage = request.getParameter("page");
            int page = ConstantsKey.DEFAULT_PAGE;

            if (txtPage != null && !txtPage.trim().isEmpty() && txtPage.matches("\\d+")) {
                page = Integer.parseInt(txtPage);
            }

            if (searchValue != null) {
                CategoriesDAO categoriesDAO = new CategoriesDAO();
                ProductsDAO dao = new ProductsDAO();
                boolean loadedMinMax = dao.getMinMaxPrice();
                if (loadedMinMax) {
                    int categoryID = categoriesDAO.getCategoryIDByName(txtCategory);
                    String category;
                    if (txtCategory != null && txtCategory.trim().isEmpty()) {
                        category = "%";
                    } else {
                        category = String.valueOf(categoryID);
                    }
                    int minPrice = dao.getMinPrice();
                    int maxPrice = dao.getMaxPrice();
                    if (txtMin != null && !txtMin.trim().isEmpty()) {
                        minPrice = Integer.parseInt(txtMin);
                        if (minPrice < dao.getMinPrice()) {
                            minPrice = dao.getMinPrice();
                        }
                    }
                    if (txtMax != null && !txtMax.trim().isEmpty()) {
                        maxPrice = Integer.parseInt(txtMax);
                        if (maxPrice > dao.getMaxPrice()) {
                            maxPrice = dao.getMaxPrice();
                        }
                    }

                    int maxPage = dao.getNumberOfProductPage(searchValue, minPrice, maxPrice, category, isAdmin);
                    if (page > maxPage) {
                        page = maxPage;
                    }
                    if (page < ConstantsKey.DEFAULT_PAGE) {
                        page = ConstantsKey.DEFAULT_PAGE;
                    }
                    int result = dao.searchProduct(searchValue, minPrice, maxPrice, category, page, isAdmin);
                    if (result > 0) {
                        request.setAttribute("CURR_PAGE", page);
                        request.setAttribute("MAX_PAGE", maxPage);
                        List<ProductsDTO> productList = dao.getProductList();
                        request.setAttribute("PRODUCT_LIST", productList);
                    }
                }
            }
        } catch (NamingException ex) {
            log("SearchProductServlet_NamingException:" + ex.getMessage());
        } catch (SQLException ex) {
            log("SearchProductServlet_SQLException:" + ex.getMessage());
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
