<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Add New User</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/user" method="post">
		<fieldset>
			<div>
				<label for="id">User ID</label>
				<input type="text" name="id" value="<c:out value="${user.id}" />"
					readonly="readonly" placeholder="User ID" />
			</div>
			<div>
				<label for="firstName">First Name</label>
				<input type="text" name="firstName" value="<c:out value="${user.firstname}" />"
					placeholder="First Name" />
			</div>
			<div>
				<label for="lastName">Last Name</label>
				<input type="text" name="lastName" value="<c:out value="${user.lastname}" />"
					placeholder="Last Name" />
			</div>
			<div>
				<label for="email">Email</label>
				<input type="text" name="email" value="<c:out value="${user.email}" />" placeholder="Email" />
			</div>
			<div>
				<label for="username">Username</label>
				<input type="text" name="username" value="<c:out value="${user.username}" />" placeholder="Username" />
			</div>
			<div>
				<input type="submit" value="Submit" />
			</div>
		</fieldset>
	</form>
</body>
</html>