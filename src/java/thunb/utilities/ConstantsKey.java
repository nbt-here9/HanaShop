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

    //Google
     public static String GOOGLE_CLIENT_ID = "596654673778-ie4v7opm6tudd91qv6pmfnnqvmp8fhov.apps.googleusercontent.com";
  public static String GOOGLE_CLIENT_SECRET = "ypPJw12uC_VBOJpzWluATcEW";
  public static String GOOGLE_REDIRECT_URI = "http://localhost:8084/SE140803_HanaShop/LoginGoogleServlet";
  public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
  public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
  public static String GOOGLE_GRANT_TYPE = "authorization_code";
    
    
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
    public static final String UPDATE_STATUS_PRODUCT_SERVLET = "UpdateStatusProductServlet";
    public static final String LOGIN_GOOGLE_SERVLET = "LoginGoogleServlet";
    public static final String VIEW_HISTORY_SERVLET = "ViewHistoryServlet";

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
    public static final String SIGN_UP_PAGE = "signup.jsp";
    public static final String HISTORY_PAGE = "history.jsp";

}
