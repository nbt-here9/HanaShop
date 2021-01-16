/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import thunb.daos.CategoriesDAO;
import thunb.daos.LogsDAO;
import thunb.daos.ProductsDAO;
import thunb.dtos.UsersDTO;
import thunb.errors.ProductErrors;
import thunb.utilities.ConstantsKey;
import thunb.utilities.Utilities;

/**
 *
 * @author Banh Bao
 */
@WebServlet(name = "CreateProductServlet", urlPatterns = {"/CreateProductServlet"})
public class CreateProductServlet extends HttpServlet {

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
        ServletContext context = request.getServletContext();
        List<String> allowExtensions = (List<String>) context.getAttribute("ALLOW_EXTENSIONS");
        //
        boolean rs = false;
        boolean valid = true;
        String url = ConstantsKey.CREATE_PRODUCT_PAGE;
        String adminAction = "CreateProduct";
        ProductErrors error = new ProductErrors();
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                UsersDTO loginUser = (UsersDTO) session.getAttribute("LOGIN_USER");
                if (loginUser != null) {
                    boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
                    if (isMultiPart) {
                        List items;
                        FileItemFactory factory = new DiskFileItemFactory();
                        ServletFileUpload upload = new ServletFileUpload(factory);
                        InputStream is = null;
                        //
                        items = upload.parseRequest(request);
                        //
                        Iterator iter = items.iterator();
                        Hashtable params = new Hashtable();
                        String imageName = null;
                        while (iter.hasNext()) {
                            FileItem item = (FileItem) iter.next();
                            if (item.isFormField()) {
                                params.put(item.getFieldName(), item.getString("UTF-8"));
                            } else {
                                String itemName = item.getName();
                                if (itemName != null && !itemName.trim().isEmpty()) {
                                    imageName = System.currentTimeMillis() + itemName.substring(itemName.lastIndexOf("."));
                                    is = item.getInputStream();
                                }
                            }
                        }
                        //
                        if (imageName != null
                                && !allowExtensions.contains(
                                        imageName.substring(imageName.lastIndexOf(".")).toLowerCase())) {
                            error.setInvalidFileType("File Type Incorrect");
                            valid = false;
                        }
                        //
                        String name = (String) params.get("txtProductName");
                        if (name == null || name.trim().isEmpty()) {
                            error.setEmptyProductName("Product Name Cannot be Empty");
                            valid = false;
                        }
                        //
                        String description = (String) params.get("txtDescription");
                        if (description == null || description.trim().isEmpty()) {
                            error.setEmptyDescription("Product Description cannot be empty");
                            valid = false;
                        }
                        //
                        String categoryName = (String) params.get("txtCategory");
                        if (categoryName == null || categoryName.trim().isEmpty()) {
                            error.setEmptyCategory("You Must Select A Category");
                            valid = false;
                        }
                        //
                        String txtPrice = (String) params.get("txtPrice");
                        if (txtPrice == null
                                || txtPrice.trim().isEmpty()
                                || !txtPrice.matches("\\d+")
                                || Integer.parseInt(txtPrice) < 1000
                                || Integer.parseInt(txtPrice) > Integer.MAX_VALUE) {
                            error.setInvalidPrice("Invalid Price!!");
                            valid = false;
                        }
                        //
                        String txtQuantity = (String) params.get("txtQuantity");
                        if (txtQuantity == null || txtQuantity.trim().isEmpty()
                                || !txtQuantity.matches("\\d++") || Integer.parseInt(txtQuantity) < 0
                                || Integer.parseInt(txtQuantity) > Integer.MAX_VALUE) {
                            error.setInvalidQuantity("Invalid Quantity!");
                            valid = false;
                        }
                        //
                        Timestamp createDate = Utilities.getCurrentTime();
                        //
                        if (valid) {
                            int price = Integer.parseInt(txtPrice);
                            int quantity = Integer.parseInt(txtQuantity);
                            if (imageName != null && is != null) {
                                Utilities.writeImgToServerFile(imageName, is);
                            }
                            //
                            CategoriesDAO categoriesDAO = new CategoriesDAO();
                            ProductsDAO productsDAO = new ProductsDAO();
                            int categoryID = categoriesDAO.getCategoryIDByName(categoryName);
                            if (categoryID >= 0) {
                                rs = productsDAO.createProduct(name, imageName, description, price, quantity, createDate, categoryID);
                                if (rs) {
                                    int latestProductID = productsDAO.getLatestProductID();
                                    if (latestProductID >= 0) {
                                        Utilities.copyImgToContextFolder(context.getRealPath("/resources/imgs"), imageName);
                                        LogsDAO logsDAO = new LogsDAO();
                                        logsDAO.addLogs(latestProductID, loginUser.getUsername(), createDate, "Create");
                                    }
                                    request.setAttribute("CREATE_SUCCESS", "New Product Created Successfully");

                                    adminAction = "Edit" + "&txtProductID=" + latestProductID;

                                } else {
                                    request.setAttribute("CREATE_ERROR", "CREATE FAILED");
                                }
                            }
                        } else {
                            request.setAttribute("ERROR", error);
                        }
                    }
                }

                url = "DispatchServlet"
                        + "?Action=LoadData"
                        + "&AdminAction=" + adminAction;

                session.setAttribute("LOAD_CATEGORY_AND_STATUS", true);
            }
        } catch (FileUploadException ex) {
            log("CreateProductServlet_FileUploadException:" + ex.getMessage());
        } catch (SQLException ex) {
            log("CreateProductServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("CreateProductServlet_NamingException:" + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

            //response.sendRedirect(url);
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
