<%-- 
    Document   : viewcart
    Created on : Jan 9, 2021, 4:56:14 PM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View your cart</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
        <link rel="stylesheet" href="https://unpkg.com/bootstrap-material-design@4.1.1/dist/css/bootstrap-material-design.min.css" integrity="sha384-wXznGJNEXNG1NFsbm0ugrLFMQPWswR3lds2VeinahP8N0zJw9VWSopbjv2x7WCvX" crossorigin="anonymous">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons">
        <link rel="stylesheet" type="text/css" href="resources/css/viewcart.css">
    </head>
    <body>
        <c:if test="${sessionScope.LOAD_PRICE}">
            <c:redirect url="DispatchServlet?Action=LoadData"/>
        </c:if>
        
        <jsp:include page="WEB-INF/header/header.jsp" flush="true"/>

        <c:if test="${sessionScope.LOGIN_USER.roleID ne 1}">

            <h1 class="text-center mb-4 mt-4">Your Cart</h1>
            <c:if test="${not empty sessionScope.CART.items}">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 ml-auto mr-auto">
                            <h4><small>List of Product In Cart</small></h4>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th class="text-center">#</th>
                                            <th>Product Name</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th class="text-right">Total(VND)</th>
                                            <th class="text-right">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${sessionScope.CART.items}" varStatus="counter">
                                        <form id="updateForm${counter.count}" action="DispatchServlet" method="POST">
                                            <input type="hidden" name="txtProductID" value="${item.key}"/>
                                            <input type="hidden" name="Action" value="Update Cart"/>
                                        </form>
                                        <form id="removeForm${counter.count}" action="DispatchServlet" method="POST">
                                            <input type="hidden" name="txtProductID" value="${item.key}"/>
                                            <input type="hidden" name="Action" value="Remove From Cart"/>
                                        </form>
                                        <tr>
                                            <td class="text-center">
                                                ${counter.count}
                                            </td>
                                            <td>${item.value.itemName}</td>
                                            <td>${item.value.price} VND</td>
                                            <td>
                                                <input type="number" form="updateForm${counter.count}" required="true" min="1" max="${Integer.MAX_VALUE}" 
                                                       name="txtQuantity" value="${item.value.quantity}"/><br/>
                                                <c:if test="${requestScope.ERROR.productID eq item.key}">
                                                    <c:if test="${not empty requestScope.ERROR.outOfStockErr}">
                                                        <font style="color: red">
                                                        ${requestScope.ERROR.outOfStockErr}<br/>
                                                        Only ${requestScope.ERROR.quantityLeft} products left.
                                                        </font>
                                                    </c:if>
                                                </c:if>
                                            </td>
                                            <td class="text-right">${item.value.total()} VND</td>
                                            <td class="td-actions text-right">
                                                <button type="submit" form="updateForm${counter.count}" rel="tooltip" class="btn btn-info btn-just-icon btn-sm" 
                                                        data-original-title="Update" form="updateForm" title="Update" name="btAction" value="Update">
                                                    <i class="material-icons">edit</i>
                                                </button>
                                                <button type="submit" form="removeForm${counter.count}" onclick="return confirm('Are you sure to remove this product ?')" 
                                                        rel="tooltip" class="btn btn-danger btn-just-icon btn-sm" data-original-title="Remove" title="Remove">
                                                    <i class="material-icons">close</i>
                                                </button>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <tr class="text-right">
                                        <td colspan="4"></td>
                                        <td><strong>${sessionScope.CART.total()} VND</strong></td>
                                        <td class="text-center">
                                            <form action="DispatchServlet" method="POST">
                                                <input class="btn btn-warning btn-block" type="submit" value="Check out" name="Action" style="text-decoration: none; font-weight: bold; color: #ffffff;  background-color: #17a2b8;"/>
                                            </form>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${empty sessionScope.CART.items}">
                        <h3 class="text-center text-muted">Your Cart is empty
                            <form action="DispatchServlet">
                                <input type="submit" class="btn btn btn-info" value="Go Shopping!!!" name="Action" />
                            </form>
                        </h3>

                    </c:if>


                    <script>
                        $(document).ready(function () {
                            $('body').bootstrapMaterialDesign();
                            $('[data-toggle="tooltip"], [rel="tooltip"]').tooltip();
                        });
                    </script>

                </c:if>  


                <c:if test="${sessionScope.LOGIN_USER.roleID eq 1}">
                    <h1 class="text-center">You do not have access this function! <br>
                        <a href="DispatchServlet">Go Back!</a>
                    </h1>
                </c:if>
                </body>
                </html>
