<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MENEAME</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css" />
</head>
<body class="body">
	<header class="header">
		<div class="info">
			<a class="infohome" href="${pageContext.request.contextPath}/MeneameHomeServlet">MENEAME</a>
		</div>		
		<c:choose>
			<c:when test="${empty user.name}">
        	<form class="login" method="GET" action="AccessLoginViewServlet"
			accept-charset="utf-8">
				<input class="loginsubmit" type="submit" value="login">
			</form>
			<form class="register" method="GET" action="AccessRegisterViewServlet" accept-charset="utf-8">
				<input class="registersubmit" type="submit" value="register">
			</form>
			</c:when>
			<c:otherwise>
        	<form class="logout" method="POST" enctype="application/x-www-form-urlencoded" action="${pageContext.request.contextPath}/LogoutServlet">
        		<input class="logoutsubmit" type="submit" value="logout">
        	</form>
			<div class="userloged">${user.name}</div>
			<form class="myprofile" method="GET" action="newsrestricted/AccessMyProfileServlet">
        		<input class="myprofilesubmit" type="submit" value="ver perfil">
        	</form>
			</c:otherwise>
		</c:choose>
	</header>
	<nav class="nav">
		<div class="menunoticia">
			<c:if test="${not empty user.name}">
			<form class="listarnoticia" method="GET"
				action="newsrestricted/UserListNoticiasServlet">
				<input class="submitbutton" type="submit"
					value="listar mis noticias">
			</form>
			<form class="listarcomentarios" method="GET"
				action="newsrestricted/UserListComentariosServlet">
				<input class="submitbutton" type="submit"
					value="listar mis comentarios">
			</form>
			<form class="enviarnoticia" method="GET"
				action="newsrestricted/UserCrearHistoriaServlet">
				<input class="submitbutton" type="submit" value="enviar noticia">
			</form>
			</c:if>
			<div class="newsselected">${fn:toUpperCase(category)}</div>
		</div>		
		<div class="navnewstype">
			<form class="filtrarnewstype" method="GET"
				action="MeneameHomeServlet">
				<select class="newstypefiltro" name="filtro">
					<option value="fecha" <c:if test="${filtro eq 'fecha'}"> selected="selected" </c:if> >fecha</option>
					<option value="meneos" <c:if test="${filtro eq 'meneos'}"> selected="selected" </c:if> >meneos</option>
				</select> <input class="filtrarnewstypebutton" type="submit" value="filtrar">
			</form>
			<form class="newstype" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="category" value="todas"> <input
					class="todassubmit" type="submit" value="Todas">
			</form>
			<form class="newstype" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="category" value="actualidad"> <input
					class="actualidadsubmit" type="submit" value="Actualidad">
			</form>
			<form class="newstype" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="category" value="ocio"> <input
					class="ociosubmit" type="submit" value="Ocio">
			</form>
			<form class="newstype" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="category" value="cultura"> <input
					class="culturasubmit" type="submit" value="Cultura">
			</form>
			<form class="newstype" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="category" value="deporte"> <input
					class="deportessubmit" type="submit" value="Deportes">
			</form>
			<form class="newstype" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="category" value="tecnologia"> <input
					class="tecnologiasubmit" type="submit" value="Tecnología">
			</form>
		</div>
	</nav>
	<main class="centralsection">
	<div class="leftsection">
		<!-- LEFT SECTION -->
		<c:forEach var="notice" items="${newsMap}" varStatus="status">
			<div class="noticia">
				<div class="noticiameneo">
					<div class="meneonumeromeneos">${notice.key.hits} meneos</div>
					<form class="meneomenear" method="POST"
						enctype="application/x-www-form-urlencoded"
						action="MeneameHomeMenearServlet">
						<input type="hidden" name="noticeid" value="${notice.key.id}">
						<input class="submitbutton" type="submit" value="menear">
					</form>
				</div>
				<div class="noticiainfo">
					<a class="noticiatitle" href="${notice.key.url}">${notice.key.title}</a>
					<div class="noticiauser">${notice.value.name}</div>
					<div class="noticiatext">${notice.key.text}</div>
					<div class="noticiadetails">
						<a class="noticiadetailsnumerocomentario" href="NoticiaServlet?noticeid=${notice.key.id}">comentarios: ${countCommetNews[status.index]}</a>
						<div class="noticiadetailskarma">karma: ${notice.key.likes}</div>
						<div class="noticiafecha">date: ${notice.key.dateStamp}
							${notice.key.timeStamp}</div>
					</div>
				</div>
			</div>
		</c:forEach>
		<div class="pagination">
			<form class="firstform" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="actualpage" value="${1}">				
				<input class="first" type="submit" value="primera">
			</form>
			<form class="prevform" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="actualpage" value="${actualpage-1}">				
				<input class="prev" type="submit" value="anterior">
			</form>
			<div class="actual">
			${actualpage}
			</div>
			<form class="nextform" method="GET" action="MeneameHomeServlet">
				<input type="hidden" name="actualpage" value="${actualpage+1}">
				<input class="next" type="submit" value="siguiente">
			</form>
		</div>
	</div>
	<aside class="aside">
		<!-- ASIDE -->
		<div class="noticiasmasvotadas">
			MÁS MENEADAS
			<c:forEach var="notice" items="${mostHittedNewsList}">
			<div class="noticiamini">
				<div class="noticiamininummeneos">${notice.hits}</div>
				<a class="noticiaminititulo" href="NoticiaServlet?noticeid=${notice.id}">${notice.title}</a>
			</div>
			</c:forEach>
		</div>
		<div class="noticiasmasvaloradas">
			MÁS POPULARES
			<c:forEach var="notice" items="${mostLikedNewsList}">
			<div class="noticiamini">
				<div class="noticiamininummeneos">${notice.likes}</div>
				<a class="noticiaminititulo" href="NoticiaServlet?noticeid=${notice.id}">${notice.title}</a>
			</div>
			</c:forEach>
		</div>
	</aside>
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
			&nbsp;&nbsp;
			<p>
				<img style="border:0;width:88px;height:31px" src="${pageContext.request.contextPath}/images/vcss-blue.gif" alt="¡CSS Válido!"/>
			</p>
		</div>
	</footer>
</body>
</html>
