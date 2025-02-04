<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.pinguela.web.util.*"%>

<form action="${pageContext.request.contextPath}/private/ProductoServlet" method="post">

    <h3><fmt:message key="h1producto" bundle="${messages}" /></h3>

    <input type="hidden" name="action" value="search" />

    <label><fmt:message key="nombrempsearch" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.NOMBRE)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="text" name="nombre" placeholder="Ejemplo: Placa base" />

    <label>Id:</label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.ID)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="number" name="id" />

    <label><fmt:message key="preciodesdempsearch" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.PRECIODESDE)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="number" name="precioDesde" step="0.01" />

    <label><fmt:message key="preciohastampsearch" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.PRECIOHASTA)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="number" name="precioHasta" step="0.01" />

    <label><fmt:message key="unidadesdesdempsearch" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.UNIDADESDESDE)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="number" name="unidadesDesde" />

    <label><fmt:message key="unidadeshastampsearch" bundle="${messages}" /></label>
    <c:forEach var="error" items="${errors.getFieldErrors(Attributes.UNIDADESHASTA)}">
        <li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
    </c:forEach>
    <input type="number" name="unidadesHasta" />

    <input type="submit" value="<fmt:message key='botonbuscar' bundle='${messages}' />" />
</form>

<div class="results-container">
    <c:choose>
        <c:when test="${not empty resultados}">
            <h3><fmt:message key="resultadosmpsearch" bundle="${messages}" /></h3>
            <ul>
                <c:forEach var="p" items="${resultados}">
                    <li>
                        <a href="<%=request.getContextPath()%>/private/ProductoServlet?action=detail&id=${p.id}">
                            <c:out value="${p.nombre}" />
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
