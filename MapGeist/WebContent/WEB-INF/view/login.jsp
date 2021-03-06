<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
	<head>
		<title>MapGeist - Login</title>
		<link rel="stylesheet" href="<c:url value="/resources/styles/styles.css"  />" />
		<link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico"/>" />
		
	</head>
	<body class="flex-body body__center body__login">
		<div class="login--bg"></div>
		<img class="mapgeist--logo" src="<c:url value="/resources/images/logo.svg" />" />
		<div class="login">
			<h3 class="login--header">Moderator Login</h3>
			
			<form action="<c:url value="/login" />" method="POST" class="login--form">
				<div class="login--form-items">
					<input type="text" name="username" placeholder="Username" class="login--form-item" />
					<input type="password" name="password" placeholder="Password" class="login--form-item" />
				</div>
				<input type="submit" value="Log in" class="btn btn__fullwidth" />
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
			
			<c:if test="${error != null}">
				<div class="login--error">
					${error}
				</div>
			</c:if>
			
			<c:if test="${param.logout ne null}">
				<div class="login--logout-msg">
					Successfully logged out!
				</div>
			</c:if>
		</div>
	</body>
</html> 