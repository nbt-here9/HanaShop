<%-- 
    Document   : login
    Created on : Jan 5, 2021, 7:44:17 PM
    Author     : Banh Bao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="resources/css/login.css">
    </head>
    <body class="bg-gradient-primary">
        <jsp:include page="WEB-INF/header/header.jsp" flush="true"/>
        <div class="container-fluid">
            <div class="row no-gutter">
                <div class="d-none d-md-flex col-md-4 col-lg-6 bg-image"></div>
                <div class="col-md-8 col-lg-6">
                    <div class="login d-flex align-items-center py-5">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-9 col-lg-8 mx-auto">
                                    <h3 class="login-heading mb-4">Welcome!</h3>

                                    <form action="DispatchServlet" method="POST">
                                        <div class="form-label-group">
                                            <input type="text" id="inputEmail" class="form-control"
                                                   placeholder="Username" name="txtUsername" value="${param.txtUsername}" 
                                                   maxlength="70" required autofocus>
                                            <label for="inputEmail">Username</label>
                                        </div>
                                        <div class="form-label-group">
                                            <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="txtPassword" maxlength="50" required>
                                            <label for="inputPassword">Password</label>
                                        </div>
                                        <c:if test="${not empty requestScope.LOGIN_ERROR}">
                                            <font style="color: red">${requestScope.LOGIN_ERROR}</font>
                                        </c:if>
                                        <input class="btn btn-lg btn-info btn-block btn-login text-uppercase font-weight-bold mb-2" type="submit" value="Login" name="Action" />
                                        <hr class="my-4">
                                        <div class="d-flex justify-content-between">
                                            <div id="my-signin2" class="mr-auto ml-auto"></div>
                                        </div>

                                        <a class="loginBtn loginBtn--google" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/SE140803_HanaShop/LoginGoogleServlet&response_type=code
                                           &client_id=596654673778-ie4v7opm6tudd91qv6pmfnnqvmp8fhov.apps.googleusercontent.com&approval_prompt=force">Login With Google</a> 
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
