<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.luis.reflejovision.model.*"%>
<%@ page import="com.pinguela.web.util.*"%>

<c:choose>
	<c:when test="${usuario == null}">
		<div class="user-info">
			<a href="${pageContext.request.contextPath}/public/user/login.jsp"><fmt:message
					key="authenticate" bundle="${messages}" /></a>
		</div>
	</c:when>
	<c:otherwise>
		<div class="user-info">
			<a
				href="${pageContext.request.contextPath}/private/UsuarioServlet?action=detail&id=${usuario.id}"
				class="user-name"> ${usuario.nombre} </a>
		</div>

		<br>
		<br>

		<div class="home-link">
			<a href="${pageContext.request.contextPath}${Views.HOME}">
				<img src="${pageContext.request.contextPath}/images/rvLogo.jpeg" alt="ReflejoVision Home" class="home-logo" />
			</a>
		</div>

		<div class="top-right-container">
			<a href="${pageContext.request.contextPath}/private/UsuarioServlet?action=logout">
				<button class="logout-button">
					<fmt:message key="logout" bundle="${messages}" />
				</button>
			</a>
		</div>

		<jsp:include page="/common/menu.jsp" />
	</c:otherwise>
</c:choose>
