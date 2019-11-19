<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
	<head>
		<title>MapGeist - Login</title>
		<link rel="stylesheet" href="<c:url value="/resources/styles/styles.css"  />" />
	</head>
	<body>
		<div class="page-container">
			<h3>Login</h3>
			
			<form action="<c:url value="/login" />" method="POST">
				<input type="text" name="username" />
				<input type="password" name="password" />
				<input type="submit" value="Submit "/>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
			
			<c:if test="${error != null}">${error}</c:if>
		</div>
	</body>
</html> 