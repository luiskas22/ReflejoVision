<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/header-login.jsp"%>

<div class="login-container">

	<img src="${pageContext.request.contextPath}/images/rvLogo.jpeg"
		alt="Login Image" class="login-image" />
	
	<c:forEach var="error" items="${errors.globalErrors}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>

	<form action="${pageContext.request.contextPath}/public/UsuarioServlet"
		method="post">
		<h3>
			<fmt:message key="user_authentication" bundle="${messages}" />
		</h3>

		<input type="hidden" name="action" value="login" /> <label><fmt:message
				key="user2p" bundle="${messages}" /></label> <input type="text"
			name="username" placeholder="llopez" required /> <label><fmt:message
				key="password2p" bundle="${messages}" /></label> <input type="password"
			name="password" placeholder="abc123." required /> <input
			type="checkbox" name="remember-user" checked>
		<fmt:message key="recordar_usuario" bundle="${messages}" />
		</input> <input type="submit"
			value="<fmt:message key="acceso" bundle="${messages}"/>"> <input
			type="reset"
			value="<fmt:message key="limpiar" bundle="${messages}"/>">
	</form>


</div>
<div class="shape shape1"></div>
<div class="shape shape2"></div>
<div class="shape shape3"></div>

<%@include file="/common/footer.jsp"%>
