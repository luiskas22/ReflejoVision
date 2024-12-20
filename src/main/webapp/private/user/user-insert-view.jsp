
<%@include file="/common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp"%>
<%@ page import="com.pinguela.web.util.Attributes"%>

<form action="${pageContext.request.contextPath}/private/UsuarioServlet" method="post" enctype="multipart/form-data">
    <h3>
        <fmt:message key="addUsers" bundle="${messages}" />
    </h3>

    <input type="hidden" name="<%=Actions.ACTION%>" value="ingresar" />

    <!-- Name Field -->
    <label><fmt:message key="namempdetail" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.NOMBRE)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="text" id="<%=Attributes.NOMBRE%>" name="<%=Attributes.NOMBRE%>" placeholder="Ejemplo: Luis"
           value="${param[Attributes.NOMBRE]}" />

    <!-- Username Field -->
    <label>Username</label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.USERNAME)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="text" id="<%=Attributes.USERNAME%>" name="<%=Attributes.USERNAME%>" value="${param[Attributes.USERNAME]}" />

    <!-- Email Field -->
    <label><fmt:message key="correo_user" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.CORREO)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="email" id="<%=Attributes.CORREO%>" name="<%=Attributes.CORREO%>" placeholder="Ejemplo: luis@example.com"
           value="${param[Attributes.CORREO]}" />

    <!-- Password Field -->
    <label><fmt:message key="password" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.PASSWORD)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="password" id="<%=Attributes.PASSWORD%>" name="<%=Attributes.PASSWORD%>" value="${param[Attributes.PASSWORD]}" />

    <!-- Role Field -->
    <label><fmt:message key="rol_user" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.ROL)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <select id="<%=Attributes.ROL%>" name="<%=Attributes.ROL%>">
		<option value="1">Operario</option>
		<option value="2">Administrador</option>
	</select>
    <br>

    <!-- Image Upload Field -->
    <label><fmt:message key="profileImage" bundle="${messages}" /></label>
    <input type="file" name="file" accept="image/*" />
    <br>

    <!-- Submit Button -->
    <input type="submit" value="Registrar Usuario" />
</form>

<%@ include file="/common/footer.jsp"%>
