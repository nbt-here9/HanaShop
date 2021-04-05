<%-- 
    Document   : adminpage
    Created on : Jan 10, 2021, 10:55:04 PM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">
        <link rel="stylesheet" type="text/css" href="resources/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="resources/css/productslist.css">
    </head>
    <body>
        <c:if test="${ (sessionScope.LOAD_ALL) or (sessionScope.LOAD_CATEGORY_AND_STATUS) }">
            <c:redirect url="DispatchServlet?Action=LoadData"/>
        </c:if>
        <jsp:include page="WEB-INF/header/header.jsp" flush="true"/>

        <c:if test="${not empty requestScope.REMOVE_SUCCESS}">
            <div class="alert alert-success alert-dismissible mt-3 mb-3 ml-3 mr-3">${requestScope.REMOVE_SUCCESS}</div>
        </c:if>

        <c:if test="${sessionScope.LOGIN_USER.roleID eq 1}">
            <h1 class="text-center text-muted">Product Management</h1>

            <form style="width: 500px; margin: 0 auto" action="DispatchServlet?Action=LoadData" method="POST" class="text-center"> 

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
                <br>

                <a href="DispatchServlet?Action=CreateProduct" class="btn btn-warning">Create Product</a>

            </form>

            <div style="padding: 0 40px; margin-top: 20px">
                <c:if test="${not empty requestScope.PRODUCT_LIST}">
                    <table class="table table-hover table-warning">
                        <thead class="thead-light">
                            <tr>
                                <th>Product Name</th>
                                <th>Description</th>
                                <th>Price</th>
                                <th>Quantity</th>
                                <th>Image</th>
                                <th>Create Date</th>
                                <th>Status</th>
                                <th>Edit</th>
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach var="product" items="${requestScope.PRODUCT_LIST}">
                            <form action="DispatchServlet" id="RemoveProduct" method="POST"></form>
                            <form action="DispatchServlet" method="POST">
                                <tr>
                                    <td>${product.productName}</td>
                                    <td>${product.description}</td>
                                    <td>${product.price}VND</td>
                                    <td>${product.quantity}</td>
                                    <td>
                                        <c:if test="${not empty product.image}">
                                            <img src="resources/imgs/${product.image}" height="200" width="200" alt="No Image"/>
                                        </c:if>
                                    </td>
                                    <td>${product.createDate}</td>
                                    <td>
                                        <c:if test="${product.statusID eq 1}">Active</c:if>
                                        <c:if test="${product.statusID eq 0}">Inactive</c:if>
                                        </td>  
                                   
                                    <td class="td-actions text-right">
                                        <input type="hidden" name="txtProductID" value="${product.productID}" />
                                        <button type="submit" class="btn btn-success btn-just-icon btn-sm" 
                                                data-original-title="Update"  title="Update" name="Action" value="Edit">
                                            <i class="material-icons">edit</i>
                                        </button>

                                        <c:if test="${product.statusID eq 1}">
                                            <input type="hidden" name="txtProductID" value="${product.productID}" form="RemoveProduct"/>
                                            <button type="submit" onclick="return confirm('Are you sure to remove this product ?')" 
                                                    form="RemoveProduct"
                                                    class="btn btn-danger btn-just-icon btn-sm" data-original-title="Remove" title="Remove" 
                                                    name="Action" value="Remove">
                                                <i class="material-icons">close</i>
                                            </button>
                                        </c:if>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>

                        </tbody>
                    </table>    

                    <c:set var="LOAD_ALL" value="${true}" scope="session"/>
                    <c:set var="LOAD_CATEGORY_AND_STATUS" value="${true}" scope="session"/>

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
            </div>
        </c:if>

        <c:if test="${empty requestScope.PRODUCT_LIST}">
            <h3 class="text-center mt-4 text-muted">No Product Found!</h3>
            <a href="DispatchServlet">Go Back!</a>
        </c:if>

        <c:if test="${sessionScope.LOGIN_USER.roleID ne 1}">
            <h1 class="text-center">You do not have access this function! <br>
                <a href="DispatchServlet">Go Back!</a>
            </h1>
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