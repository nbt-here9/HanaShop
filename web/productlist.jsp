<%-- 
    Document   : home
    Created on : Jan 5, 2021, 5:58:14 PM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hana Shop</title>
        <link rel="stylesheet" type="text/css" href="resources/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="resources/css/productslist.css">
    </head>
    <body>

        <c:if test="${ (sessionScope.LOAD_ALL) or (sessionScope.LOAD_CATEGORY_AND_STATUS) }">
            <c:redirect url="DispatchServlet?Action=LoadData"/>
        </c:if>
        <jsp:include page="WEB-INF/header/header.jsp" flush="true"/>

        <h2 class="text-center mb-3 mt-3">Product List</h2>
        <!--Form search-->
        <form style="width: 500px; margin: 0 auto" action="DispatchServlet?Action=LoadData" method="POST">

            <div class="d-flex justify-content-between">
                <input type="text" name="minPrice" id="minPrice" readonly="true" />
                <input class="text-right" type="text" name="maxPrice" id="maxPrice" readonly="true" />
            </div>
            <div id="slider-range"></div> 
            <br>
            <div class="d-flex justify-content-between">
                <input style="width: 220px" type="text" name="txtSearchValue" value="${param.txtSearchValue}"/>
                <select style="width: 180px" name="txtCategory">
                    <option></option>
                    <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                        <option 
                            <c:if test="${param.txtCategory eq category.categoryName}">
                                selected="true"
                            </c:if>>
                            ${category.categoryName}
                        </option>
                    </c:forEach>
                </select>
                <input class="btn btn-info" type="submit" name="" value="Search"/>
            </div>

        </form>
        <!-------------------------------------------->     
        <!--Product card-->
        <c:if test="${not empty requestScope.PRODUCT_LIST}">
            <div class="mt-5 container">
                <div class="row">
                    <c:forEach var="product" items="${requestScope.PRODUCT_LIST}">
                        <div class="col-md-3">
                            <div class="card card-custom bg-white border-white">
                                <div class="card-custom-img" style="background-image: url(resources/imgs/${product.image})"></div>
                                <div class="card-body" style="overflow-y: auto">
                                    <h4 class="card-title">${product.productName}</h4>
                                    <p class="card-text">${product.description}</p>
                                </div>
                                <div class="card-footer" style="background: inherit; border-color: inherit;">
                                    <div class="justify-content-between d-flex">
                                        <p class="card-text">${product.price} VND</p>
                                        <p class="card-text">In Stock: ${product.quantity}</p>
                                    </div>
                                    <form action="DispatchServlet" method="POST">
                                        <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}"/>
                                        <input type="hidden" name="minPrice" value="${param.minPrice}"/>
                                        <input type="hidden" name="maxPrice" value="${param.maxPrice}"/>
                                        <input type="hidden" name="txtCategory" value="${param.txtCategory}"/>
                                        <input type="hidden" name="page" value="${param.page}"/>
                                        <input type="hidden" name="txtProductID" value="${product.productID}"/>
                                        <input type="submit" name="Action" class="btn btn-info" value="Add To Cart"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!---->

            <c:set var="LOAD_ALL" value="${true}" scope="session"/>
            <c:set var="LOAD_CATEGORY_AND_STATUS" value="${true}" scope="session"/>
            <c:set var="LOAD_PRICE" value="${true}" scope="session"/>

            <ul class="mt-2 justify-content-center pagination pagination-lg text-center">
                <c:forEach begin="1" end="${requestScope.MAX_PAGE}" varStatus="counter">
                    <c:url var="urlRewriting" value="DispatchServlet">
                        <c:param name="Action" value="LoadData"/>
                        <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                        <c:param name="txtCategory" value="${param.txtCategory}"/>
                        <c:if test="${not empty param.minPrice}"><c:param name="minPrice" value="${param.minPrice}"/></c:if>
                        <c:if test="${empty param.minPrice}"><c:param name="minPrice" value="0"/></c:if>
                        <c:if test="${not empty param.maxPrice}"><c:param name="minPrice" value="${param.maxPrice}"/></c:if>
                        <c:if test="${empty param.maxPrice}"><c:param name="minPrice" value="${requestScope.MAX_PRICE}"/></c:if>
                        <c:param name="page" value="${counter.count}"/>
                    </c:url>
                    <li class="page-item <c:if test="${counter.count==requestScope.CURR_PAGE}">active</c:if>">                        
                        <a class="page-link" href="${pageScope.urlRewriting}">
                            ${counter.count}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty requestScope.PRODUCT_LIST}">
            <h3 class="text-center mt-4 text-muted">No Product Found!</h3>
            <a href="DispatchServlet">Go Back!</a>
        </c:if>
        <script>
            var minPrice = "${param.minPrice}";
            var maxPrice = "${param.maxPrice}";
            if (minPrice === null)
                minPrice = 0;
            if (maxPrice === null || maxPrice.trim() === "")
                maxPrice =${requestScope.MAX_PRICE};

            $(function () {
                $("#slider-range").slider({
                    range: true,
                    min: 0,
                    max: ${requestScope.MAX_PRICE},
                    values: [minPrice, maxPrice],
                    step: 1000,
                    slide: function (event, ui) {
                        $("#minPrice").val(ui.values[0]);
                        $("#maxPrice").val(ui.values[1]);
                    }
                }
                );
                $("#minPrice").val($("#slider-range").slider("values", 0));
                $("#maxPrice").val($("#slider-range").slider("values", 1));
            });
        </script>    
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    </body>
</html>
