<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pinguela.web.util.*"%>
<%@include file="/common/header.jsp"%>

<form action="${pageContext.request.contextPath}/private/UsuarioServlet" method="post" class="user-search-form">

    <h3><fmt:message key="buscarusuarios" bundle="${messages}" /></h3>

    <input type="hidden" name="action" value="search" />

    <label>Username:</label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.USERNAME)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="text" name="username" placeholder="user1" />

    <label>Id:</label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.ID)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="number" name="id" />

    <label><fmt:message key="namempdetail" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.NOMBRE)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="text" name="nombre" />

    <label><fmt:message key="rol_user" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.ROL)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="text" name="rol" />

    <label><fmt:message key="correo_user" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.CORREO)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="text" name="correo" />

    <input type="submit" value="<fmt:message key='botonbuscar' bundle='${messages}' />" />
</form>

<div class="results-container">
    <c:choose>
        <c:when test="${not empty resultados}">
            <h3><fmt:message key="resultadosmpsearch" bundle="${messages}" /></h3>
            <ul>
                <c:forEach var="u" items="${resultados}">
                    <li>
                        <a href="<%=request.getContextPath()%>/private/UsuarioServlet?action=detail&id=${u.id}">
                            <c:out value="${u.nombre}" />
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="resultadosnoencontrados" bundle="${messages}" /></p>
        </c:otherwise>
    </c:choose>
</div>

<%@include file="/common/footer.jsp"%>
