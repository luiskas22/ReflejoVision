<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.pinguela.web.util.*"%>

<%@include file="/common/header.jsp"%>

<div class="container">
   <div id="image-container">
        <img alt="profile image" src="${pageContext.request.contextPath}/ImageServlet?action=profileImage&usuarioId=${u.id}&imageName=g1.jpg" />
    </div>
    <form action="${pageContext.request.contextPath}/private/UsuarioServlet?action=uploadImage" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${u.id}">
        <input type="file" name="file">
        <input type="submit" value="Subir foto">
    </form>
        
    <h2>
        Username:
        <c:out value="${u.username}" />
    </h2>
    <p>
        <i>Id: <c:out value="${u.id}" /></i>
    </p>
    <p>
        <b><fmt:message key="namempdetail" bundle="${messages}" /> <c:out value="${u.nombre}" /></b>
    </p>
    <p>
        <i><fmt:message key="correo_user" bundle="${messages}" /> <c:out value="${u.correo}" /></i>
    </p>
    
    <!-- Role Display -->
    <p>
        <i><fmt:message key="rol_user" bundle="${messages}" /> 
            <c:choose>
                <c:when test="${u.rol == 1}">Operario</c:when>
                <c:when test="${u.rol == 2}">Administrador</c:when>
                <c:otherwise>Unknown Role</c:otherwise>
            </c:choose>
        </i>
    </p>
    
    <form action="UsuarioServlet?action=delete" method="post" onsubmit="return confirm('¿Estás seguro de que deseas eliminar este usuario?');">
        <input type="hidden" name="id" value="${u.id}" />
        <button type="submit" class="delete-button"><fmt:message key="borrarusuario_boton" bundle="${messages}" /> </button>
    </form>
</div>

<%@include file="/common/footer.jsp"%>
