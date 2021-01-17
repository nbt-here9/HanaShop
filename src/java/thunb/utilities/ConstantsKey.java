/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunb.utilities;

/**
 *
 * @author Banh Bao
 */
public class ConstantsKey {

    //Properties
    public static final int ADMIN_ROLE = 1;
    public static final int USER_ROLE = 0;
    public static final int PRODUCT_PER_PAGE = 20;
    public static final int DEFAULT_PAGE = 1;
    public static final int ORDERID_LENGTH = 10;
    public static final int COD = 0;

    //Resources
    //Servlets
    public static final String SEARCH_PRODUCT_SERVLET = "SearchProductServlet";
    public static final String ADD_TO_CART_SERVLET = "AddToCartServlet";
//    public static final String VIEW_CART_SERVLET = "ViewCartServlet";
    public static final String REMOVE_FROM_CART_SERVLET = "RemoveFromCartServlet";
    public static final String UPDATE_CART_SERVLET = "UpdateCartServlet";
    public static final String LOGIN_SERVLET = "LoginServlet";
    public static final String LOGOUT_SERVLET = "LogoutServlet";
//    public static final String SEARCH_PRODUCT_ADMIN_SERVLET = "SearchProductAdminServlet";
    public static final String LOAD_PRODUCT_SERVLET = "LoadProductServlet";
//    public static final String CART_ITEM_PRICE_UPDATE_SERVLET = "CartItemPriceUpdateServlet";
    public static final String LOAD_FOR_CHECKOUT_SERVLET = "LoadForCheckOutServlet";
//    public static final String CREATE_PRODUCT_SERVLET = "CreateProductServlet";
    public static final String LOAD_DATA_SERVLET = "LoadDataServlet";
    public static final String CREATE_CATEGORY_SERVLET = "CategoryCreateServlet";
    public static final String CHECK_OUT_SERVLET = "CheckOutServlet";

    //Pages
    public static final String WELCOME_PAGE = "welcome.jsp";
    public static final String ERROR_PAGE = "error.jsp";
    public static final String PRODUCT_LIST_PAGE = "productlist.jsp";
    public static final String VIEW_CART_PAGE = "viewcart.jsp";
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String ADMIN_PAGE = "adminpage.jsp";
    public static final String UPDATE_PRODUCT_PAGE = "updateproduct.jsp";
    public static final String CHECK_OUT_PAGE = "checkout.jsp";
    public static final String CREATE_PRODUCT_PAGE = "createproduct.jsp";

}
