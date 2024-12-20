<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.pinguela.web.util.*"%>


<form
	action="${pageContext.request.contextPath}/private/MateriaPrimaServlet"
	method="post" class="purchase-container">
	<h3>
		<fmt:message key="comprar_mp" bundle="${messages}" />
	</h3>
	<input type="hidden" name="action" value="comprar" /> 
	
	
	<label><fmt:message
			key="nombremp_insert" bundle="${messages}" /> EN</label>
	<c:forEach var="error"
		items="${errors.getFieldErrors(Attributes.NOMBRE)}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>
	<input type="text" name="nombre_en" placeholder="Ejemplo: Placa base" />


	<label><fmt:message key="nombremp_insert" bundle="${messages}" />
		ES</label>
	<c:forEach var="error"
		items="${errors.getFieldErrors(Attributes.NOMBRE)}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>
	<input type="text" name="nombre_es" placeholder="Ejemplo: Placa base" />

	<label><fmt:message key="nombremp_insert" bundle="${messages}" />
		GL</label>
	<c:forEach var="error"
		items="${errors.getFieldErrors(Attributes.NOMBRE)}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>
	<input type="text" name="nombre_gl" placeholder="Ejemplo: Placa base" />

	<label><fmt:message key="preciompdetail" bundle="${messages}" /></label>
	<c:forEach var="error"
		items="${errors.getFieldErrors(Attributes.PRECIO)}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="precio" step="0.01" /> <label><fmt:message
			key="unidadesmpdetail" bundle="${messages}" /> </label>
	<c:forEach var="error"
		items="${errors.getFieldErrors(Attributes.UNIDADES)}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="unidades" /> <label><fmt:message
			key="unidadmedidamp_insert" bundle="${messages}" /> </label>
	<c:forEach var="error"
		items="${errors.getFieldErrors(Attributes.UNIDADMEDIDA)}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="unidadMedida" /> <input type="submit"
		value="<fmt:message key="comprar_boton" bundle="${messages}" />" />
</form>

<%@include file="/common/footer.jsp"%>
