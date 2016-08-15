<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="tm-header">
    <div class="container-fluid">
        <div class="tm-header-inner">
            <a href="index.jsp" class="navbar-brand tm-site-name">JCloud</a>

            <!-- navbar -->
            <nav class="navbar tm-main-nav">

                <button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#tmNavbar">
                    &#9776;
                </button>

                <div class="collapse navbar-toggleable-sm" id="tmNavbar">
                    <ul class="nav navbar-nav">
                        <li class="nav-item">
                            <a href="about.jsp" class="nav-link">Services</a>
                        </li>
                        <li class="nav-item">
                            <a href="http://blog.jcloud.com.au" class="nav-link">Blog</a>
                        </li>
                        <li class="nav-item">
                            <a href="contact.jsp" class="nav-link">Contact</a>
                        </li>
                        <li class="nav-item">
                        	<c:set var="user" scope="session" value='<%= request.getSession().getAttribute("user")%>'/>
                        	<c:choose>
                        		<c:when test="${user == null}">
                            		<a href="login.jsp" class="nav-link">Login</a>
                            	</c:when>
                            	<c:otherwise>
                            		<s:form id="logoutForm" method="post" action="/login.action">
                            			<s:submit id="submit" name="logout" value="Logout" class="tm-btn text-uppercase" />
                            		</s:form>
                            	</c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </div>

            </nav>

        </div>
    </div>
</div>
