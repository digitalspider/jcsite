<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://javacloud.com.au/taglib" prefix="jc" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Show All Pages</title>
    </head>
    <body>
        <jc:substring input="GOODMORNING" start="1" end="6"/>

        <table>
            <thead>
                <tr>
                    <th>Page ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Content</th>
                    <th>URL</th>
                    <th>Type</th>
                    <th>Tags</th>
                    <th>Status</th>
                    <th>Author Id</th>
                    <th>Parent Id</th>
                    <th colspan="2">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pages}" var="page">
                    <tr>
                        <td><c:out value="${page.id}" /></td>
                        <td><c:out value="${page.title}" escapeXml="false"/></td>
                        <td><c:out value="${page.description}" escapeXml="false"/></td>
                        <td><c:out value="${page.content}" escapeXml="false"/></td>
                        <td><c:out value="${page.url}" escapeXml="false"/></td>
                        <td><c:out value="${page.type}" /></td>
                        <td><c:out value="${page.tags}" /></td>
                        <td><c:out value="${page.status}" /></td>
                        <td><c:out value="${page.authorId}" /></td>
                        <td><c:out value="${page.parentId}" /></td>

                        <td><a
                            href="<%=request.getContextPath()%>/page/edit/<c:out value="${page.id }"/>">Update</a></td>
                        <td><a
                            href="<%=request.getContextPath()%>/page/delete/<c:out value="${page.id }"/>">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p>
            <a href="<%=request.getContextPath()%>/page/insert">Add Page</a>
        </p>
    </body>
</html>