<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>

<s:layout-render name="/jsp/layout/public.jsp" pageTitle="Login">
    <s:layout-component name="contents">

        <section class="tm-section">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">

                        <form id="loginForm" method="post" action="j_security_check" class="tm-login-form">
                        	<% if (request.getQueryString()!=null && request.getQueryString().contains("failed=1")) { %>
                        		<div class="alert alert-danger">Invalid login</div>
                        	<% } %>
                            <div class="h4">Login</div>
                            <div class="form-group">
                                <input type="text" id="username" name="j_username" class="form-control" placeholder="Username" required="true" value="${actionBean.username}"/>
                            </div>
                            <div class="form-group">
                                <input type="password" id="password" name="j_password" class="form-control" placeholder="Password" required="true"/>
                            </div>
                            <input type="submit" id="login" name="login" value="login" class="tm-btn text-uppercase" />
                        </form>
						<br/>
						<s:link href="/reset.jsp">Forgot your password</s:link>
                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">
                        <s:form id="registerForm" method="post" action="/login.action" class="tm-login-form">
                            <div class="h4">Register</div>
                            <div class="form-group">
                                <s:errors field="firstname"/>
                                <input type="text" id="firstname" name="firstname" class="form-control" placeholder="First Name" required="true" value="${actionBean.firstname}"/>
                            </div>
                            <div class="form-group">
                            	<s:errors field="lastname"/>
                                <input type="text" id="lastname" name="lastname" class="form-control" placeholder="Last Name" required="true" value="${actionBean.lastname}"/>
                            </div>
                            <div class="form-group">
                            	<s:errors field="email"/>
                                <input type="email" id="email" name="email" class="form-control" placeholder="Email" required="true" value="${actionBean.email}"/>
                            </div>
                            <div class="form-group">
                            	<s:errors field="newusername"/>
                                <input type="text" id="newusername" name="newusername" class="form-control" placeholder="Username" required="true" value="${actionBean.newusername}"/>
                            </div>
                            <div class="form-group">
                            	<s:errors field="newpassword"/>
                                <input type="password" id="newpassword" name="newpassword" class="form-control" placeholder="Password" required="true"/>
                            </div>
                            <s:submit id="register" name="register" value="register" class="tm-btn text-uppercase"/>
                        </s:form>
                    </div>

                </div> <!-- row -->

            </div>
        </section>

    </s:layout-component>
    <s:layout-component name="footer">

        <!-- load JS files -->
        <script src="js/jquery-1.11.3.min.js"></script>             <!-- jQuery (https://jquery.com/download/) -->
        <script src="https://www.atlasestateagents.co.uk/javascript/tether.min.js"></script> <!-- Tether for Bootstrap, http://stackoverflow.com/questions/34567939/how-to-fix-the-error-error-bootstrap-tooltips-require-tether-http-github-h -->
        <script src="js/bootstrap.min.js"></script>                 <!-- Bootstrap (http://v4-alpha.getbootstrap.com/) -->

        <script src="js/md5.js"></script> <%-- http://www.myersdaily.org/joseph/javascript/md5-text.html --%>
        <script type="text/javascript">
            function encodeAndSubmit(event) {
                event.preventDefault();
                if ($("#password").val().length>0) {
                    $("#password").val(md5($("#password").val()))
                }
                if ($("#newpassword").val().length>0) {
                    $("#newpassword").val(md5($("#newpassword").val()))
                }
                this.name = $(".submit").attr("name");
                this.submit();
            }
           // $('#loginForm').submit(encodeAndSubmit);
           // $('#registerForm').submit(encodeAndSubmit);
        </script>

    </s:layout-component>
</s:layout-render>