<%-- 
    Document   : error
    Created on : Jan 5, 2021, 7:34:15 PM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>


        <c:if test="${not empty requestScope.ERR_MESS}">
            <h1>${requestScope.ERR_MESS}</h1>
        </c:if>

        <c:if test="${empty requestScope.ERR_MESS}">
            <h1>Something is wrong</h1>
        </c:if>

        <a href="DispatchServlet">Go Back!</a>
    </body>
</html>
