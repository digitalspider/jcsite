<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <title>jcloud Login</title>
        <jsp:include page="jsp/include/htmlhead.jsp"/>
    </head>

    <body>
        <jsp:include page="jsp/include/header.jsp"/>

        <section class="tm-section">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">

                        <form method="post" action="<%=request.getContextPath() %>/jc/login/login"  class="tm-login-form">
                            <div class="h4">Login</div>
                            <div class="form-group">
                                <input type="text" id="j_username" name="j_username" class="form-control" placeholder="Username"  required/>
                            </div>
                            <div class="form-group">
                                <input type="password" id="j_password" name="j_password" class="form-control" placeholder="Password"  required/>
                            </div>
                            <input type="submit" value="Login" class="tm-btn text-uppercase" />
                        </form>
                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">
                        <form method="post" action="<%=request.getContextPath() %>/jc/user" class="tm-login-form">
                            <div class="h4">Register</div>
                            <div class="form-group">
                                <input type="text" id="login_firstname" name="login_firstname" class="form-control" placeholder="First Name"  required/>
                            </div>
                            <div class="form-group">
                                <input type="text" id="login_lastname" name="login_lastname" class="form-control" placeholder="Last Name"  required/>
                            </div>
                            <div class="form-group">
                                <input type="email" id="login_email" name="login_email" class="form-control" placeholder="Email"  required/>
                            </div>
                            <div class="form-group">
                                <input type="username" id="login_username" name="login_username" class="form-control" placeholder="Username"  required/>
                            </div>
                            <div class="form-group">
                                <input type="password" id="login_password" name="login_password" class="form-control" placeholder="Password"  required/>
                            </div>
                            <input type="submit" value="Register" class="tm-btn text-uppercase"/>
                        </form>
                    </div>

                </div> <!-- row -->

            </div>
        </section>

        <!-- load JS files -->
        <script src="js/jquery-1.11.3.min.js"></script>             <!-- jQuery (https://jquery.com/download/) -->
        <script src="https://www.atlasestateagents.co.uk/javascript/tether.min.js"></script> <!-- Tether for Bootstrap, http://stackoverflow.com/questions/34567939/how-to-fix-the-error-error-bootstrap-tooltips-require-tether-http-github-h --> 
        <script src="js/bootstrap.min.js"></script>                 <!-- Bootstrap (http://v4-alpha.getbootstrap.com/) -->
       
</body>
</html>
