<%-- 
    Document   : checkout
    Created on : Jan 12, 2021, 10:40:12 PM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="resources/css/checkout.css">
       <title>Check Out</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/header/header.jsp" flush="true"/>
        
        <c:if test="${not empty sessionScope.CART}">
            <section class="payment-form dark pt-4">
                <div class="container">
                    <form action="checkout" method="POST">
                        <div class="products">
                            <h3 class="title">Checkout</h3>
                            <c:forEach var="item" items="${sessionScope.CART.items}" varStatus="counter">
                                <div class="item">
                                    <span class="price">${item.value.total()} VND</span>
                                    <p class="item-name">${item.value.itemName}</p>
                                    <p class="item-description">Quantity: ${item.value.quantity}, Price:${item.value.price} VND</p>
                                </div>
                            </c:forEach>
                            <div class="total">Total<span class="price">${sessionScope.CART.total()} VND</span></div>
                        </div>                    
                        <div class="card-details">
                            <h3 class="title">Customer Details</h3>
                            <div class="row">
                                <div class="form-group col-sm-7">
                                    <label for="card-holder">Customer Name</label>
                                    <input id="card-holder" type="text" name="txtName" 
                                           required="true" maxlength="200" value="${sessionScope.LOGIN_USER.name}" 
                                           class="form-control" placeholder="Customer Name" aria-label="Customer Name" aria-describedby="basic-addon1">
                                    <c:if test="${not empty requestScope.ERRORS.emptyNameErr}">
                                        <font style="color: red">${requestScope.ERRORS.emptyNameErr}</font>
                                    </c:if>
                                </div>
                                <div class="form-group col-sm-5">
                                    <label for="phone-number">Phone Number</label>
                                    <div class="input-group expiration-date">
                                        <input type="text" name="txtPhone" required="true" id="phone-number" 
                                               maxlength="12" value="${sessionScope.LOGIN_USER.phone}" class="form-control"
                                               placeholder="Phone Number" aria-label="Phone Number" aria-describedby="basic-addon1"/>
                                        <c:if test="${not empty requestScope.ERRORS.invalidPhoneNumErr}">
                                            <font style="color: red">${requestScope.ERRORS.invalidPhoneNumErr}</font>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="form-group col-sm-8">
                                    <label for="card-number">Customer Address</label>
                                    <input name="txtAddress" required="true" maxlength="200" value="${sessionScope.LOGIN_USER.address}"
                                           id="card-number" type="text" class="form-control" placeholder="Customer Address"
                                           aria-label="Customer Address" aria-describedby="basic-addon1"/>
                                    <c:if test="${not empty requestScope.ERRORS.emptyAddressErr}">
                                        <font style="color: red">${requestScope.ERRORS.emptyAddressErr}</font>
                                    </c:if>
                                </div>
                                <div class="form-group col-sm-4 ">
                                    <label for="paymentMethod">Payment Method</label>
                                    <div id="paymentMethod" class="justify-content-between ">
                                        <c:forEach var="payment" items="${requestScope.PAYMENT_LIST}">
                                            <input type="radio" id="method" name="txtPaymentMethod" value="${payment.methodType}"/>
                                            <label for="method">${payment.methodName}</label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <button type="submit" name="Action" value="Check Out" class="btn btn-info btn-block">Proceed</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </c:if>
        <c:if test="${empty sessionScope.CART}">
            <c:redirect url="DispatchServlet"></c:redirect>
        </c:if>
    </body>
</html>
