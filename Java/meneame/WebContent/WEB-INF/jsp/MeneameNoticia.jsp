<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
			<form class="listarnoticia" method="GET" action="newsrestricted/UserListNoticiasServlet"><input class="submitbutton" type="submit" value="listar mis noticias"></form>
			<form class="enviarnoticia" method="GET" action="newsrestricted/UserCrearHistoriaServlet"><input class="submitbutton" type="submit" value="enviar noticia"></form>
			</c:if>
			<div class="newsselected">${fn:toUpperCase(notice.category)}</div>
		</div>
	</nav>
	<main class="centralsection">
	<div class="leftsection">
		<!-- LEFT SECTION -->
		<div class="noticia">
			<div class="noticiameneo">
				<div class="meneonumeromeneos">${notice.hits} meneos</div>
				<form class="meneomenear" method="POST"
					enctype="application/x-www-form-urlencoded"
					action="NoticiaMenearServlet">
					<input type="hidden" name="noticeid" value="${notice.id}">
					<input class="submitbutton" type="submit" value="menear">
				</form>
			</div>
			<div class="noticiainfo">
				<a class="noticiatitle" href="${notice.url}">${notice.title}</a>
				<div class="noticiauser">${usernews}</div>
				<div class="noticiatext">${notice.text}</div>
				<div class="noticiadetails">
					<a class="noticiadetailsnumerocomentario"
						href="NoticiaServlet?noticeid=${notice.id}">comentarios: ${countCommetNews}</a>
					<div class="noticiadetailskarma">karma: ${notice.likes}</div>
					<div class="noticiafecha">date: ${notice.dateStamp}
							${notice.timeStamp}</div>
				</div>
				<c:if test="${not empty user.name}">
				<div class="noticiaedition">
					<form class="comentariocreate" method="GET"
						action="newsrestricted/UserCrearComentarioServlet">
						<input type="hidden" name="noticeid" value="${notice.id}">
						<input class="comentariocreatebutton" type="submit"
							value="crear comentario">
					</form>
				</div>
				</c:if>
			</div>
		</div>
 		<div class="comentarios">
		<c:forEach var="comment" items="${comments}" varStatus="status">
			<div class="comentario">
				<div class="comentarioinfo">
					<div class="comentarionumber">#${status.index+1}</div>
					<div class="comentariotext">${comment.text}</div>
				</div>
				<div class="comentariodetails">
					<c:if test="${not empty user.name}">
					<form class="comentariovote" method="POST"
						enctype="application/x-www-form-urlencoded"
						action="newsrestricted/MeneameNoticiaVotarComentarioServlet">
						<input type="hidden" name="commentid" value="${comment.id}">
						<input type="hidden" name="commentnoticeid" value="${comment.news}">
						<select class="votarcomentario" name="voto">
							<option value="-3">-3</option>
							<option value="-2">-2</option>
							<option value="-1">-1</option>
							<option value="1" selected="selected">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
						</select> <input class="comentariovotebutton" type="submit"
							value="votar comentario">
					</form>
					</c:if>
					<!-- <div class="comentarionumerovotos">num votos: 45</div> -->
					<div class="comentariokarma">karma: ${comment.likes}</div>
					<div class="comentariofecha">date: ${comment.dateStamp} ${comment.timeStamp}</div>
					<div class="comentariouser">${userNamescommentList[status.index]}</div>
				</div>
			</div>
		</c:forEach>
 		</div>
		<div class="pagination">
			<form class="prevform" method="GET" action="NoticiaServlet">
				<input type="hidden" name="noticeid" value="${notice.id}">
				<input type="hidden" name="actualpagecomment" value="${actualpagecomment-1}">				
				<input class="prev" type="submit" value="anterior">
			</form>
			<div class="actual">
			${actualpagecomment}
			</div>
			<form class="nextform" method="GET" action="NoticiaServlet">
				<input type="hidden" name="noticeid" value="${notice.id}">
				<input type="hidden" name="actualpagecomment" value="${actualpagecomment+1}">
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
			<img style="border:0;width:88px;height:31px" src="${pageContext.request.contextPath}/images/vcss-blue.gif" alt="¡CSS Válido!"/>
		</div>
	</footer>
</body>
</html>
