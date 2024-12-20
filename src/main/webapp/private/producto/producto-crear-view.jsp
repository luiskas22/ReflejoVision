<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.pinguela.web.util.*"%>

<form action="${pageContext.request.contextPath}/private/ProductoServlet"
    method="post">
    <h3>Crear Producto</h3>

    <input type="hidden" name="action" value="crear" /> 

    <!-- Nombre -->
    <label><fmt:message key="namempdetail" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.NOMBRE)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="text" name="nombre" placeholder="Ejemplo: Espejo retrovisor x" />

    <!-- ID -->
    <label><fmt:message key="producto_id" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.ID)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="number" name="id" />

    <!-- Unidades -->
    <label><fmt:message key="unidades_producir" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.UNIDADES)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="number" name="unidades" />

    <input type="submit" value="Crear" />
</form>

<%@include file="/common/footer.jsp"%>
