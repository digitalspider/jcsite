<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/jsp/include/taglibs.jsp"%>

<s:layout-render name="/jsp/layout/public.jsp" pageTitle="About">

    <s:layout-component name="contents">
        <section class="tm-section">
            <div class="container-fluid">
                <div class="row tm-2-rows-sm-swap">

                    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-9 col-xl-9 tm-2-rows-sm-down-1">
                        <h3 class="tm-gold-text">About us</h3>
                        <p>Vivamus accumsan blandit ligula. Sed lobortis efficitur sapien</p>
                        <p>Quisque vel sem eu turpis ullamcorper euismod. Praesent quis nisi ac augue luctus viverra. Sed et dui nisi. Fusce vitae dapibus justo. Pellentesque accumsan est ac posuere imperdiet. Curabitur eros mi, lacinia at euismod quis, dapibus vel ligula. Ut sodales erat vitae nunc tempor mollis. Donec tempor lobortis tortor, in feugiat massa facilisis sed. Ut dignissim viverra pretium. In eu justo maximus turpis feugiat finibus scelerisque nec eros.</p>

						<p class="m-b-2">Try it out today, or contact us for more information. </p>
						<a href="login.jsp" class="tm-btn text-uppercase">Sign up</a>
						<a href="contact.jsp" class="tm-btn text-uppercase">Contact Us</a>
                    </div>

                    <jsp:include page="/jsp/include/sidebar.jsp"/>
                </div>
            </div>
        </section>

    </s:layout-component>
</s:layout-render>