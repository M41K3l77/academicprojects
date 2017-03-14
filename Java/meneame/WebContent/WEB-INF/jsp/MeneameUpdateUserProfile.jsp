<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>MENEAME</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheetloginregister.css" />
</head>
<body class="body">
	<header class="header">
		<div class="info"><a class="infohome" href="${pageContext.request.contextPath}/MeneameHomeServlet">MENEAME</a></div>
		<c:choose>
			<c:when test="${empty user.name}">
        	<form class="login" method="GET" action="${pageContext.request.contextPath}/AccessLoginViewServlet"
			accept-charset="utf-8">
				<input class="loginsubmit" type="submit" value="login">
			</form>
			<form class="register" method="GET" action="${pageContext.request.contextPath}/AccessRegisterViewServlet" accept-charset="utf-8">
				<input class="registersubmit" type="submit" value="register">
			</form>
			</c:when>
			<c:otherwise>
        	<form class="logout" method="POST" enctype="application/x-www-form-urlencoded" action="${pageContext.request.contextPath}/LogoutServlet">
        		<input class="logoutsubmit" type="submit" value="logout">
        	</form>
			<div class="userloged">${user.name}</div>
			</c:otherwise>
		</c:choose>
	</header>
	<main class="centralsectionloginregister">
		<!-- LEFT SECTION -->
		<div class="profilesection">
			<div class="boxname">
				Perfil Usuario
			</div>
			<form class="profileuser" method="POST" enctype="application/x-www-form-urlencoded" action="UpdateProfileServlet">
				<label class="usernameprofile">
					user name: ${user.name}
				</label>
				<label class="emailprofile">
					email: ${user.email}
				</label>
				<span class="error">${errorformatoemail}</span>
				<label class="emailprofile">
					nuevo email <input type="email" name="newuseremail" value="${user.email}" maxlength="50" required>
				</label>
				<label class="passwordprofile">
					password: ${user.password}
				</label>
				<label class="passwordprofile">
					nuevo password <input type="password" name="newuserpassword1" value="${user.password}" maxlength="40" required>
				</label>
				<label class="passwordprofile">
					repetir password <input type="password" name="newuserpassword2" value="${user.password}" maxlength="40" required>
				</label>
				<span class="error">${errorpassword}</span>
				<span class="error">${errorformatopassword}</span>
				<c:remove var="errorformatopassword" scope="session"/>
				<c:remove var="errorpassword" scope="session"/>
				<c:remove var="errorformatoemail" scope="session"/>
				<div class="submit">
					<input class="submitbutton" type="submit" value="update user">
				</div>
			</form>
		</div>
	</main>
	<footer class="footer">
		<div class="infofooter">
			<div class="footerelement">Aplicación web basada en <a class="footeritem" href="https://www.meneame.net/">MENEAME</a></div>
			<div class="footerelement">A Complete Guide to <a class="footeritem" href="https://css-tricks.com/snippets/css/a-guide-to-flexbox/">Flexbox</a></div>
		</div>
		<div class="linksfooter">
			<div class="footerelement"><a class="footeritem" href="http://www.unex.es/">UNEX</a></div>
			<div class="footerelement"><a class="footeritem" href="http://www.w3schools.com/">w3schools</a></div>
			<div class="footerelement"><a class="footeritem" href="https://jigsaw.w3.org/css-validator/">W3 css validator</a></div>
			<div class="footerelement"><a class="footeritem" href="https://validator.w3.org/nu/">W3 Html5 validator</a></div>
		</div>
		<div class="w3validated">
			<p>				
				<img style="border:0;width:88px;height:31px" src="${pageContext.request.contextPath}/images/vcss-blue.gif" alt="¡CSS Válido!"/>				
			</p>		
		</div>
	</footer>
</body>
</html>