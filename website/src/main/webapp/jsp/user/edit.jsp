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
				<label for="firstname">First Name</label>
				<input type="text" name="firstname" value="<c:out value="${user.firstname}" />"
					placeholder="First Name" />
			</div>
			<div>
				<label for="lastname">Last Name</label>
				<input type="text" name="lastname" value="<c:out value="${user.lastname}" />"
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
				<label for="password">Password</label>
				<input type="password" name="password" value="<c:out value="${user.password}" />" placeholder="Password" />
			</div>
			<div>
				<label for="url">Url</label>
				<input type="text" name="url" value="<c:out value="${user.url}" />" placeholder="Url" />
			</div>
			<div>
				<label for="mobile">Mobile</label>
				<input type="text" name="mobile" value="<c:out value="${user.mobile}" />" placeholder="Mobile" />
			</div>
			<div>
				<label for="type">Type</label>
				<input type="text" name="type" value="<c:out value="${user.type}" />" placeholder="Type" />
			</div>
			<div>
				<label for="tags">Tags</label>
				<input type="text" name="tags" value="<c:out value="${user.tags}" />" placeholder="Tags" />
			</div>
			<div>
				<label for="status">Status</label>
				<input type="text" name="status" value="<c:out value="${user.status}" />" placeholder="Status" />
			</div>
			<div>
				<input type="submit" value="Submit" />
			</div>
		</fieldset>
	</form>
</body>
</html>