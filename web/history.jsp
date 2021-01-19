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
        <link rel="stylesheet" type="text/css" href="resources/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="resources/css/productslist.css">
        <title>History</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/header/header.jsp" flush="true"/>

        <c:if test="${sessionScope.LOGIN_USER.roleID eq 0}">

            <c:if test="${(requestScope.NO_RESULT) && ( (not empty param.txtFromDate && not empty param.txtToDate) || (not empty param.txtSearchValue) )}">
                <div class="text-center text-muted">
                    <font style="color: red">No result matched this search value!</font><br/>
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.HISTORY}">
                <h1 class="text-center text-muted">Your Shopping history</h1><br>

                <form style="width: 500px; margin: 0 auto" action="DispatchServlet" method="POST">
                    <div class="text-center text-muted">
                        <div class="row mb-4">
                            <div class="col-md-6 form-group mb-0">
                                <label>From:</label>
                                <input class="form-control" type="datetime-local"name="txtFromDate" min="2018-06-07T00:00" step="1"
                                       value="${param.txtFromDate}"/><br/>
                            </div>                            
                            <div class="col-md-6 form-group mb-0">
                                <label>To:</label>
                                <input class="form-control" type="datetime-local" name="txtToDate" min="2018-06-07T00:00" step="1"
                                       value="${param.txtToDate}" /><br/>

                            </div>
                        </div>  
                        <c:if test="${not empty requestScope.ERROR}">
                            <div class="text-center text-muted">
                                <font style="color: red">${requestScope.ERROR}</font><br/>
                            </div>
                        </c:if>
                    </div>



                    <div class="text-center text-muted">
                        <input style="width: 220px" type="text" name="txtSearchValue" value="${param.txtSearchValue}"/>
                    </div>
                    <br>
                    <div class="text-center text-muted">
                        <input class="btn btn-info" type="submit" name="Action" value="Search History"/>
                    </div>



                </form>


                <c:forEach var="order" items="${sessionScope.HISTORY}">



                    <div style="padding: 0 100px; margin-top: 50px">
                        <h4 class="login-heading mb-4">Your order: ${order.orderID}</h4>  
                        <h5 class="login-heading mb-4" style=" margin-left: auto;">Date: ${order.orderDate}</h5>  

                        <table class="table table-hover table-warning">
                            <thead class="thead-light">
                                <tr>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>

                                <tr>
                                    <td>
                                        <c:forEach var="productName" items="${order.productNameList}">
                                            ${productName} <br>
                                        </c:forEach>
                                    </td>


                                    <td>
                                        <c:forEach var="detail" items="${order.detailList}">
                                            ${detail.quantity} <br>
                                        </c:forEach>
                                    </td>

                                    <td>
                                        <c:forEach var="detail" items="${order.detailList}">
                                            ${detail.total} (VND)<br>
                                        </c:forEach>
                                    </td>

                                </tr>

                                <tr style="border-bottom:2px solid #ffffff; border-top:2px solid #ffffff; font-weight: bold;">
                                    <td colspan="2">Total bill: </td>
                                    <td>${order.total} (VND)</td>
                                </tr>

                                <tr>
                                    <td colspan="3">
                                        <strong>Customer Name:</strong> ${order.customerName} <br>
                                        <strong>Customer Phone number:</strong> ${order.customerPhone} <br>
                                        <strong>Customer Address:</strong> ${order.customerAddress} <br>
                                        <strong>Payment:</strong> <c:if test="${order.paymentMethod eq 0}">Cash payment upon delivery</c:if> <br>
                                        </td>
                                    </tr>


                                </tbody>
                            </table>    

                        </div>

                        <br>
                </c:forEach>



            </c:if>


        </c:if>



        <c:if test="${sessionScope.LOGIN_USER.roleID ne 0}">
            <h1 class="text-center">You do not have access this function! <br>
                <a href="DispatchServlet">Go Back!</a>
            </h1>
        </c:if>
    </body>
</html>
