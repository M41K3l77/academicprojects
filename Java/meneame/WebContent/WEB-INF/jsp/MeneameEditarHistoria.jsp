<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MENEAME</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/stylesheet.css" />
</head>
<body class="body">
	<header class="header">
		<div class="info">
			<a class="infohome"
				href="${pageContext.request.contextPath}/MeneameHomeServlet">MENEAME</a>
		</div>
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
			<form class="listarnoticia" method="GET"
				action="UserListNoticiasServlet">
				<input class="submitbutton" type="submit"
					value="listar mis noticias">
			</form>
			<form class="enviarnoticia" method="GET"
				action="UserCrearHistoriaServlet">
				<input class="submitbutton" type="submit" value="enviar noticia">
			</form>
			<div class="newsselected">${fn:toUpperCase(category)}</div>
		</div>
	</nav>
	<main class="centralsectionenviarhistoria"> <!-- LEFT SECTION -->
	<div class="editarhistoriasection">
		<div class="boxname">EDITAR NOTICIA</div>
		<form class="noticiaeditarhistoria" method="POST"
			enctype="application/x-www-form-urlencoded"
			action="HistoriaEditadaServlet">
			<input type="hidden" name="noticeid" value="${noticeid}">
			<div class="urlnoticia">
				Url de la noticia <input type="url" name="url" value="${url}"
					required>
			</div>
			<span class="error">${errorurl}</span>
			<div class="titulonoticia">
				Título de la noticia <input type="text" maxlength="150" name="title"
					value="${title}" required>
			</div>
			<span class="error">${errortitulo}</span>
			<div class="descripcionnoticia">
				Descripción de la noticia
				<textarea class="descripcionnoticiauser" maxlength="400" name="text"
					value="${text}" required>${text}</textarea>
			</div>
			<span class="error">${errordescripcion}</span>
			<div class="categorianoticia">
				Categoría de la noticia
				<div class="categorybox">
					<input type="radio" name="category" value="actualidad"
						<c:if test="${category eq 'actualidad'}"> checked </c:if>>
					Actualidad <input type="radio" name="category" value="ocio"
						<c:if test="${category eq 'ocio'}"> checked </c:if>> Ocio
					<input type="radio" name="category" value="cultura"
						<c:if test="${category eq 'cultura'}"> checked </c:if>>
					Cultura <input type="radio" name="category" value="deporte"
						<c:if test="${category eq 'deporte'}"> checked </c:if>>
					Deporte <input type="radio" name="category" value="tecnologia"
						<c:if test="${category eq 'tecnologia'}"> checked </c:if>>
					Tecnología
				</div>
			</div>
			<span class="error">${errorcategory}</span>
			<c:remove var="errorurl" scope="session"/>
			<c:remove var="errortitulo" scope="session"/>
			<c:remove var="errordescripcion" scope="session"/>
			<c:remove var="errorcategory" scope="session"/>
			<div class="submit">
				<input class="submitbutton" type="submit" value="Editar historia">
			</div>
		</form>
	</div>
	</main>
	<footer class="footer">
		<div class="infofooter">
			<div class="footerelement">
				Aplicación web basada en <a class="footeritem"
					href="https://www.meneame.net/">MENEAME</a>
			</div>
			<div class="footerelement">
				A Complete Guide to <a class="footeritem"
					href="https://css-tricks.com/snippets/css/a-guide-to-flexbox/">Flexbox</a>
			</div>
		</div>
		<div class="linksfooter">
			<div class="footerelement">
				<a class="footeritem" href="http://www.unex.es/">UNEX</a>
			</div>
			<div class="footerelement">
				<a class="footeritem" href="http://www.w3schools.com/">w3schools</a>
			</div>
			<div class="footerelement">
				<a class="footeritem" href="https://jigsaw.w3.org/css-validator/">W3
					css validator</a>
			</div>
			<div class="footerelement">
				<a class="footeritem" href="https://validator.w3.org/nu/">W3
					Html5 validator</a>
			</div>
		</div>
		<div class="w3validated">
			<p>				
			<img style="border:0;width:88px;height:31px" src="${pageContext.request.contextPath}/images/vcss-blue.gif" alt="¡CSS Válido!"/>				
			</p>
		</div>
	</footer>
</body>
</html>
