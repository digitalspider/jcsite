<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User <c:out value="${user.id}" /></title>
</head>
<body>
    <a href="<%=request.getContextPath()%>/user/list">List</a>
    <div>
        <label for="id">Student ID</label>
        <div><c:out value="${user.id}" /></div>
    </div>
    <div>
        <label for="firstName">First Name</label>
        <div><c:out value="${user.firstname}" /></div>
    </div>
    <div>
        <label for="lastName">Last Name</label>
        <div><c:out value="${user.lastname}" /></div>
    </div>
    <div>
        <label for="course">Course</label>
        <div><c:out value="${user.email}" /></div>
    </div>
    <div>
        <label for="year">Year</label>
        <div><c:out value="${user.username}" /></div>
    </div>
</body>
</html>