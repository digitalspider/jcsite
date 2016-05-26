<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Add New Page</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/page" method="post">
		<fieldset>
			<div>
				<label for="id">Page ID</label>
				<input type="text" name="id" value="<c:out value="${page.id}" />"
					readonly="readonly" placeholder="Page ID" />
			</div>
			<div>
				<label for="title">Title</label>
				<input type="text" name="title" value="<c:out value="${page.title}" />" placeholder="Title" />
			</div>
			<div>
				<label for="description">Description</label>
				<input type="text" name="description" value="<c:out value="${page.description}" />" placeholder="Description" />
			</div>
			<div>
				<label for="content">Content</label>
				<input type="text" name="content" value="<c:out value="${page.content}" />" placeholder="Content" />
			</div>
			<div>
				<label for="url">URL</label>
				<input type="text" name="url" value="<c:out value="${page.url}" />" placeholder="Url" />
			</div>
			<div>
				<input type="submit" value="Submit" />
			</div>
		</fieldset>
	</form>
</body>
</html>