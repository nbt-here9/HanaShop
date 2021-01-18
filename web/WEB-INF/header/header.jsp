<%-- 
    Document   : header
    Created on : Jan 5, 2021, 5:54:52 PM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id"
              content="${applicationScope.GOOGLE_CLIENT_ID}">
    </head>
    <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #17a2b8">
        <a class="navbar-brand" style="color: #ffffff">Hana Shop</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <form action="DispatchServlet" style="width: 1100px;">

            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item 
                        <c:if test="${requestScope.NEXT_PAGE eq 'Home'}">active</c:if>">
                            <a class="nav-link">
                            <input class="btn btn-outline-light" type="submit" value="Home" name="Action" />
                        </a>
                    </li>
                    <c:if test="${sessionScope.LOGIN_USER.roleID ne 1}">
                        <li class="nav-item <c:if test="${requestScope.NEXT_PAGE eq 'Your cart'}">active</c:if>">
                                <a class="nav-link">
                                    <input class="btn btn-outline-light" type="submit" value="Your cart<c:if test="${not empty sessionScope.CART}">(${sessionScope.CART.numOfProductInCart})</c:if>" name="Action" />
                                </a>
                            </li>
                    </c:if>

                    
                    <c:if test="${sessionScope.LOGIN_USER.roleID eq 0}">
                        <li class="nav-item <c:if test="${requestScope.NEXT_PAGE=='history.jsp'}">active</c:if>">
                                <a class="nav-link">
                                    <input class="btn btn-outline-light" type="submit" value="History" name="Action" />
                                </a>
                            </li>
                    </c:if>
                    

                </ul>
                <span class="navbar-text">
                    <ul class="navbar-nav mr-auto">
                        <c:if test="${empty sessionScope.LOGIN_USER}">
                             <%--
                            <li class="nav-item <c:if test="${requestScope.NEXT_PAGE=='createaccount.jsp'}">active</c:if>">
                                    <a class="nav-link">
                                        <input class="btn btn-outline-light" type="submit" value="Sign up" name="Action" />
                                    </a>
                                </li>
                                --%>
                                <li class="nav-item <c:if test="${requestScope.NEXT_PAGE=='login.jsp'}">active</c:if>">
                                    <a class="nav-link">
                                        <input class="btn btn-outline-light" type="submit" value="Sign in" name="Action" />
                                    </a>
                                </li>
                        </c:if>
                        <c:if test="${not empty sessionScope.LOGIN_USER}">

                            <span class="nav-item" style="padding-right: 10px;">
                                Welcome,<font style="color: #ffffff;">${sessionScope.LOGIN_USER.name  }</font>
                            </span>



                            <li class="nav-item">
                                <input class="btn btn-outline-light" type="submit" value="Sign out" name="Action" />
                            </li>
                        </c:if>
                    </ul>
                </span>
            </div>
        </form>
    </nav>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" crossorigin="anonymous"></script>

</header>
</html>

