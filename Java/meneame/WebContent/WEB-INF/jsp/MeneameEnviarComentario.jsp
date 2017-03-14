<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MENEAME</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css" />
</head>
<body class="body">
	<header class="header">
		<div class="info"><a class="infohome" href="${pageContext.request.contextPath}/MeneameHomeServlet">MENEAME</a></div>
		<form class="logout" method="POST"
			enctype="application/x-www-form-urlencoded"
			action="${pageContext.request.contextPath}/LogoutServlet">
			<input class="logoutsubmit" type="submit" value="logout">
		</form>
		<div class="userloged">${user.name}</div>
		<form class="myprofile" method="GET" action="AccessMyProfileServlet">
        	<input class="myprofilesubmit" type="submit" value="ver perfil">
        </form>
	</header>
	<nav class="nav">
		<div class="menunoticia">
			<form class="listarnoticia" method="GET" action="UserListNoticiasServlet"><input class="submitbutton" type="submit" value="listar mis noticias"></form>
			<form class="enviarnoticia" method="GET" action="UserCrearHistoriaServlet"><input class="submitbutton" type="submit" value="enviar noticia"></form>
		</div>
	</nav>
	<main class="centralsectionenviarcomentario">
		<!-- LEFT SECTION -->
		<div class="enviarcomentariosection">
			<div class="boxname">
				Enviar comentario
			</div>
			<form class="enviarcomentario" action="EnviarComentarioServlet" method="POST">
				Comentario
				<input type="hidden" name="noticeid" value="${noticeid}">
				<textarea class="comentariouser" rows="8" cols="50" maxlength="400" name="usercomment" value="${usercomment}" placeholder="inserta tu comentario" required></textarea>
				<span class="error">${errorcomentario}</span>
				<c:remove var="errorcomentario" scope="session"/>
				<div class="submit">
					<input class="submitbutton" type="submit" value="enviar comentario">
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
