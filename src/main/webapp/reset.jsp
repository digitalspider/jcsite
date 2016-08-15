<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>

<s:layout-render name="/jsp/layout/public.jsp" pageTitle="Reset">
    <s:layout-component name="contents">

        <section class="tm-section">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 col-xl-6">

                        <s:form id="resetForm" method="post" action="/login.action"  class="tm-login-form">
                            <div class="h4">Password</div>
                            <div class="form-group">
                                <input type="password" id="password" name="password" class="form-control" placeholder="Password"  required/>
                            </div>
                            <s:submit id="submit" name="reset" value="Reset" class="tm-btn text-uppercase" />
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
                this.name = $(".submit").attr("name");
                this.submit();
            }
           // $('#resetForm').submit(encodeAndSubmit);
        </script>

    </s:layout-component>
</s:layout-render>