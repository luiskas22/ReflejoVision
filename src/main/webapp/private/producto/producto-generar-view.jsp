<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.pinguela.web.util.*"%>

<form action="${pageContext.request.contextPath}/private/ProductoServlet"
	method="post">

	<h3>
		<fmt:message key="generarproducto" bundle="${messages}" />
	</h3>

	<input type="hidden" name="action" value="generar" /> <label><fmt:message
			key="nombreproducto" bundle="${messages}" /></label>
	<c:forEach var="error"
		items="${errors.getFieldErrors(Attributes.NOMBRE)}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>
	<input type="text" name="nombre" placeholder="Ejemplo: Placa base" />
	<label><fmt:message key="preciompdetail" bundle="${messages}" />
	</label>
	<c:forEach var="error"
		items="${errors.getFieldErrors(Attributes.PRECIO)}">
		<li class="error-message"><fmt:message key="${error}"
				bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="precio" step="0.01" />

	<h3>
		<fmt:message key="raw_material" bundle="${messages}" />
	</h3>
	<div id="materias-primas-container">
		<div class="materia-prima">
			<label>Id:</label> <input type="number" name="idMateriaPrima1" /> <label><fmt:message
					key="namempdetail" bundle="${messages}" /></label> <input type="text"
				name="nombreMateriaPrima1" /> <label><fmt:message
					key="unidadesmpdetail" bundle="${messages}" /></label> <input
				type="number" name="unidades1" />
		</div>
	</div>

	<hr>

	<div id="materias-primas-container">
		<div class="materia-prima">
			<label>Id:</label> <input type="number" name="idMateriaPrima2" /> <label><fmt:message
					key="namempdetail" bundle="${messages}" /></label> <input type="text"
				name="nombreMateriaPrima2" /> <label><fmt:message
					key="unidadesmpdetail" bundle="${messages}" /></label> <input
				type="number" name="unidades2" />
		</div>
	</div>

	<hr>

	<div id="materias-primas-container">
		<div class="materia-prima">
			<label>Id:</label> <input type="number" name="idMateriaPrima3" /> <label><fmt:message
					key="namempdetail" bundle="${messages}" /></label> <input type="text"
				name="nombreMateriaPrima3" /> <label><fmt:message
					key="unidadesmpdetail" bundle="${messages}" /></label> <input
				type="number" name="unidades3" />
		</div>
	</div>

	<br> <br> <input type="submit"
		value=<fmt:message
					key="generar_boton" bundle="${messages}" /> />
</form>

<%@include file="/common/footer.jsp"%>

