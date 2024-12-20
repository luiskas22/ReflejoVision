<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/menu.css'/>" />
<fmt:setLocale value="${sessionScope['locale']}"/>
<fmt:setBundle basename="i18n.Messages" var="messages"/>  



<nav class="horizontal-menu">
    <ul>
        <li><a href="#"><fmt:message key="raw_material" bundle="${messages}"/></a>
            <ul class="submenu">
                <li><a href="${pageContext.request.contextPath}/private/materiaPrima/materiaPrima-search-view.jsp"><fmt:message
			key="inventario_mp" bundle="${messages}" /></a></li>
                <li><a href="${pageContext.request.contextPath}/private/materiaPrima/materiaPrima-insert-view.jsp"><fmt:message
			key="compra_mp" bundle="${messages}" /></a></li>
            </ul>
        </li>
        <li><a href="#"><fmt:message key="productos" bundle="${messages}"/></a>
            <ul class="submenu">
                <li><a href="${pageContext.request.contextPath}/private/producto/producto-search-view.jsp"><fmt:message key="inventario_p" bundle="${messages}"/></a></li>
                <li><a href="${pageContext.request.contextPath}/private/producto/producto-generar-view.jsp"><fmt:message key="consumos" bundle="${messages}"/></a></li>
                <li><a href="${pageContext.request.contextPath}/private/producto/producto-crear-view.jsp"><fmt:message key="fabricacion_p" bundle="${messages}"/></a></li>
            </ul>
        </li>
        <li><a href="#"><fmt:message key="usuarios" bundle="${messages}"/></a>
            <ul class="submenu">
                <li><a href="${pageContext.request.contextPath}/private/user/user-insert-view.jsp"><fmt:message key="ingresar_usuarios" bundle="${messages}"/></a></li>
                <li><a href="${pageContext.request.contextPath}/private/user/user-search-view.jsp"><fmt:message key="consultar_usuarios" bundle="${messages}"/></a></li>
            </ul>
        </li>
    </ul>
</nav>
