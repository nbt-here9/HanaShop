<%-- 
    Document   : history
    Created on : Jan 18, 2021, 7:50:08 AM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/header/header.jsp" flush="true"/>

        <c:if test="${sessionScope.LOGIN_USER.roleID eq 0}">

            <h1 class="text-center text-muted">Your Shopping history</h1>
            
            
            

        </c:if>



        <c:if test="${sessionScope.LOGIN_USER.roleID ne 0}">
            <h1 class="text-center">You do not have access this function! <br>
                <a href="DispatchServlet">Go Back!</a>
            </h1>
        </c:if>
    </body>
</html>
