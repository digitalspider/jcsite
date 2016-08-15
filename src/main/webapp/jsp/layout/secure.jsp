<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>

<!DOCTYPE html>

<s:layout-definition>
<html>
    <head>
        <title>JCloud ${pageTitle}</title>
        <jsp:include page="/jsp/include/htmlhead.jsp"/>
    </head>

    <body>
		<s:layout-component name="header">
            <jsp:include page="/jsp/include/header.jsp"/>
        </s:layout-component>

        <s:layout-component name="contents"/>

        <s:layout-component name="footer">
            <jsp:include page="/jsp/include/footer.jsp"/>
        </s:layout-component>
    </body>
</html>
</s:layout-definition>