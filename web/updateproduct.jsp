<%-- 
    Document   : updateproduct
    Created on : Jan 12, 2021, 5:23:34 PM
    Author     : Banh Bao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product</title>
    </head>
    <body>
        <c:if test="${ (sessionScope.LOAD_ALL) or (sessionScope.LOAD_CATEGORY_AND_STATUS) }">
            <c:set var="LOAD_ALL" value="${false}" scope="session"/>

            <c:url var="urlRewriting" value="DispatchServlet">
                <c:param name="Action" value="LoadData"/>
                <c:param name="AdminAction" value="${param.Action}"/>
                <c:param name="txtProductID" value="${param.txtProductID}"/>
            </c:url>
            <c:redirect url="${pageScope.urlRewriting}"/>
        </c:if>
        <jsp:include page="WEB-INF/header/header.jsp" flush="true"/>

        <c:if test="${sessionScope.LOGIN_USER.roleID eq 1}">

            <c:if test="${not empty requestScope.CREATE_SUCCESS}">
                <div class="alert alert-success alert-dismissible mt-3 mb-3 ml-3 mr-3">${requestScope.CREATE_SUCCESS}</div>
            </c:if>
                
            <c:if test="${not empty requestScope.UPDATE_SUCCESS}">
                <div class="alert alert-success alert-dismissible mt-3 mb-3 ml-3 mr-3">Your Product Has been Update Successfully!</div>
            </c:if>
                
            <c:if test="${not empty requestScope.UPDATE_FAILED}">
                <div class="alert alert-danger alert-dismissible mt-3 mb-3 ml-3 mr-3">Update Product Failed!</div>
            </c:if>
                
            <h1 class="text-center text-muted mt-3"> Update Product</h1>
            <c:if test="${not empty requestScope.LOADED_PRODUCT}">
                <div class="container">
                    <div class="col-md-6 mx-auto border border-secondary bg-light pt-4 pb-4 pl-4 pr-4">
                        <form action="UpdateProductServlet" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="txtProductID" value="${requestScope.LOADED_PRODUCT.productID}"/>
                            <div class="form-group mb-0">
                                <label>Product Name</label>
                                <input class="form-control" type="text" name="txtProductName" value="${requestScope.LOADED_PRODUCT.productName}"/><br/>
                                <c:if test="${not empty requestScope.ERROR.emptyProductName}">
                                    <font style="color: red">${requestScope.ERROR.emptyProductName}</font><br/>
                                </c:if>
                            </div>
                            <div class="form-group mb-0">
                                <label>Description</label>
                                <input class="form-control" type="text" name="txtDescription" value="${requestScope.LOADED_PRODUCT.description}"/><br/>
                                <c:if test="${not empty requestScope.ERROR.emptyDescription}">
                                    <font style="color: red">${requestScope.ERROR.emptyDescription}</font><br/>
                                </c:if>
                            </div>

                            <div class="row">
                                <div class="col-md-6 form-group mb-0">
                                    <label>Quantity</label>
                                    <input class="form-control" type="number"  name="txtQuantity" value="${requestScope.LOADED_PRODUCT.quantity}" max="${Integer.MAX_VALUE}" min="0"/><br/>
                                    <c:if test="${not empty requestScope.ERROR.invalidQuantity}">
                                        <font style="color: red">${requestScope.ERROR.invalidQuantity}</font><br/>
                                    </c:if>    
                                </div>                                                  
                                <div class="col-md-6 form-group mb-0">
                                    <label>Price(VND)</label>
                                    <input class="form-control" type="number" name="txtPrice" value="${requestScope.LOADED_PRODUCT.price}" step="1000" max="${Float.MAX_VALUE}" min="0"/>
                                    <c:if test="${not empty requestScope.ERROR.invalidPrice}">
                                        <font style="color: red">${requestScope.ERROR.invalidPrice}</font><br/>
                                    </c:if>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <label>Category</label>
                                    <select class="form-control" name="txtCategory">
                                        <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                                            <option 
                                                <c:if test="${requestScope.LOADED_PRODUCT.categoryID eq category.categoryID}">
                                                    selected="true"
                                                </c:if>>
                                                ${category.categoryName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <c:if test="${not empty requestScope.ERROR.emptyCategory}">
                                        <font style="color: red">${requestScope.ERROR.emptyCategory}</font><br/>
                                    </c:if>
                                </div>
                                <div class="col-md-6 form-group">
                                    <label>Status</label>
                                    <select class="form-control" name="txtStatus">
                                        <c:forEach var="status" items="${requestScope.PRODUCT_STATUS_LIST}">
                                            <option 
                                                <c:if test="${requestScope.LOADED_PRODUCT.statusID eq status.statusID}">
                                                    selected="true"
                                                </c:if>>
                                                ${pageScope.status.statusName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <c:if test="${not empty requestScope.ERROR.emptyStatus}">
                                        <font style="color: red">${requestScope.ERROR.emptyStatus}</font><br/>
                                    </c:if>
                                </div>
                            </div>
                            <label>Image</label>
                            <div class="input-group mt-2">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Upload</span>
                                </div>
                                <div class="custom-file">
                                    <input type="file" name="image" accept="image/gif,image/jpeg,image/png" class="custom-file-input" id="inputGroupFile01">
                                    <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                                </div>
                                <c:if test="${not empty requestScope.ERROR.invalidFileType}">
                                    <font style="color: red">${requestScope.ERROR.invalidFileType}</font><br/>
                                </c:if>
                            </div>
                            <div class="justify-content-between text-center pt-2 img-container img-thumbnail">
                                <img id="img" class="img-fluid" src="resources/imgs/${requestScope.LOADED_PRODUCT.image}" alt="">
                            </div>
                            <div class="row pt-3">
                                <input class="col-md-6 btn btn-info btn-block mx-auto" type="submit" name="Action" value="Update"/>
                            </div>
                        </form>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty requestScope.LOADED_PRODUCT}">
                <h1 class="text-center text-muted alert alert-danger">The Requested Products Does not exist!!!</h1>
            </c:if>
            <script>
                $('#inputGroupFile01').on('change', function () {
                    //get the file name
                    var fileName = $(this).val();
                    fileName = fileName.replace('C:\\fakepath\\', "");
                    //replace the "Choose a file" label
                    $(this).next('.custom-file-label').html(fileName);
                    readURL(this);
                });
                function readURL(input) {
                    if (input.files && input.files[0]) {
                        var reader = new FileReader();

                        reader.onload = function (e) {
                            $('img').attr('src', e.target.result);
                        };

                        reader.readAsDataURL(input.files[0]);
                    }
                }
            </script>
        </c:if>


        <c:if test="${sessionScope.LOGIN_USER.roleID ne 1}">
            <h1 class="text-center">You do not have access this function! <br>
                <a href="DispatchServlet">Go Back!</a>
            </h1>
        </c:if>
    </body>
</html>
