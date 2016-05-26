<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://javacloud.com.au/taglib" prefix="jc" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Show All Users</title>
    </head>
    <body>
        <jc:substring input="GOODMORNING" start="1" end="6"/>

        <table>
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Username</th>
                    <th colspan="2">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.id}" escapeXml="false"/></td>
                        <td><c:out value="${user.firstname}" escapeXml="false"/></td>
                        <td><c:out value="${user.lastname}" escapeXml="false"/></td>
                        <td><c:out value="${user.email}" escapeXml="false"/></td>
                        <td><c:out value="${user.username}" escapeXml="false"/></td>
                        <td><a
                            href="<%=request.getContextPath()%>/user?action=edit&id=<c:out value="${user.id }"/>">Update</a></td>
                        <td><a
                            href="<%=request.getContextPath()%>/user?action=delete&id=<c:out value="${user.id }"/>">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p>
            <a href="<%=request.getContextPath()%>/user?action=insert">Add User</a>
        </p>
    </body>
</html>