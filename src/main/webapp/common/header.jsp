<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.luis.reflejovision.model.*"%>
<%@ page import="java.util.List"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<fmt:setLocale value="${sessionScope['locale']}" />
<fmt:setBundle basename="i18n.Messages" var="messages" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/styles.css'/>" />
    <link rel="icon" href="<c:url value='/images/rvLogo.ico'/>" type="image/x-icon">
<script src="${pageContext.request.contextPath}/js/jquery/jquery-3.7.1.min.js"></script>


<title>Reflejo Vision</title>
</head>
<body>
	<div class="language-selector">
		<!-- Language selector buttons -->
		<c:url var="galician" value="/public/UsuarioServlet">
			<c:param name="${Parameters.ACTION}" value="${Actions.CHANGE_LOCALE}" />
			<c:param name="${Parameters.LOCALE}" value="${Parameters.GALEGO}" />
			<c:param name="${Parameters.CALLBACK_URL}"
				value="${requestScope.callbackURL}" />
		</c:url>
		<a href="${galician}"
			class="${sessionScope['locale'] == 'gl_ES' ? 'active' : ''}"> <fmt:message
				key="galician" bundle="${messages}" />
		</a>

		<c:url var="spanish" value="/public/UsuarioServlet">
			<c:param name="${Parameters.ACTION}" value="${Actions.CHANGE_LOCALE}" />
			<c:param name="${Parameters.LOCALE}" value="${Parameters.SPANISH}" />
			<c:param name="${Parameters.CALLBACK_URL}"
				value="${requestScope.callbackURL}" />
		</c:url>
		<a href="${spanish}"
			class="${sessionScope['locale'] == 'es' ? 'active' : ''}"> <fmt:message
				key="spanish" bundle="${messages}" />
		</a>

		<c:url var="english" value="/public/UsuarioServlet">
			<c:param name="${Parameters.ACTION}" value="${Actions.CHANGE_LOCALE}" />
			<c:param name="${Parameters.LOCALE}" value="${Parameters.ENGLISH}" />
			<c:param name="${Parameters.CALLBACK_URL}"
				value="${requestScope.callbackURL}" />
		</c:url>
		<a href="${english}"
			class="${sessionScope['locale'] == 'en' ? 'active' : ''}"> <fmt:message
				key="english" bundle="${messages}" />
		</a>
	</div>

	<%@include file="/common/user-menu.jsp"%>
	<%@include file="/common/footer.jsp"%>