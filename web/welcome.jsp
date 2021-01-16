<%-- 
    Document   : welcome
    Created on : Jan 5, 2021, 8:01:04 PM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="resources/css/welcome.css">
        <title>Hana Shop</title>
    </head>
    <body>
        <div>

            <div class="col-12 text-center white-text wow fadeIn">
                <h1 class="mb-4">
                    <strong>HANA SHOP</strong>
                </h1>

                <form action="DispatchServlet">
                    <c:if test="${empty sessionScope.LOGIN_USER}">
                        <input type="submit" class="btn btn-outline-danger" value="Sign in" name="Action" />
                    </c:if>
                </form>


                <form action="DispatchServlet?Action=LoadData">
                    <c:if test="${sessionScope.LOGIN_USER.roleID ne 1}">
                        <c:set var="LOAD_ALL" value="${true}" scope="session"/>
                        <c:set var="LOAD_CATEGORY_AND_STATUS" value="${true}" scope="session"/>
                        <input type="submit" class="btn btn-outline-danger" value="Go Shopping!!!" name="" />
                    </c:if>

                    <c:if test="${sessionScope.LOGIN_USER.roleID eq 1}">
                         <c:set var="LOAD_ALL" value="${true}" scope="session"/>
                        <c:set var="LOAD_CATEGORY_AND_STATUS" value="${true}" scope="session"/>
                        <input type="submit" class="btn btn-outline-danger" value="Management Products" name="" />
                    </c:if>
                </form>


            </div>

        </div>
    </body>
</html>
