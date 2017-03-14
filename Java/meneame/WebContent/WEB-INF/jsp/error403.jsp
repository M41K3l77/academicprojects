<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MENEAME</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css" />
</head>
<body class="error">
	<div class="text403">3. HTTP ERROR 403 (FORBIDDEN) This error is
		similar to the 401 error, but note the difference between unauthorized
		and forbidden. In this case no login opportunity was available. This
		can for example happen if you try to access a (forbidden) directory on
		a website.</div>
		<img src="${pageContext.request.contextPath}/images/403.jpg" alt="Error 403" />
</body>
</html>