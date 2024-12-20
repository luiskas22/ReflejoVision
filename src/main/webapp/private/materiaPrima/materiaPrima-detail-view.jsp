<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="/common/header.jsp"%>

<div class="container">
	<h2>
		<fmt:message key="namempdetail" bundle="${messages}" />
		<c:out value="${mp.nombre}" />
	</h2>
	<p>
		<b>Id: <c:out value="${mp.id}" /></b>
	</p>

	<!-- Formulario de edición -->
	<form action="MateriaPrimaServlet?action=update" method="post">
		<input type="hidden" name="id"
			value="${(empty param.id) ? mp.id : param.id}" />

		<!-- Campo Nombre EN -->
		<div>
			<label for="nombre_en"> <fmt:message key="namempdetail"
					bundle="${messages}" /> EN
			</label>

			<!-- Error para 'nombre_en' -->
			<c:forEach var="error" items="${errors.getFieldErrors('nombre')}">
				<li class="error-message"><fmt:message key="${error}"
						bundle="${messages}" /></li>
			</c:forEach>

			<!-- Buscar traducción EN -->
			<c:set var="nombre_en"
				value="${empty traducciones[0] ? '' : traducciones[0].nombre}" />
			<input type="text" id="nombre_en" name="nombre_en"
				value="${(empty param.nombre_en) ? nombre_en : param.nombre_en}"
				required />
		</div>

		<!-- Campo Nombre ES -->
		<div>
			<label for="nombre_es"> <fmt:message key="namempdetail"
					bundle="${messages}" /> ES
			</label>

			<!-- Error para 'nombre_es' -->
			<c:forEach var="error" items="${errors.getFieldErrors('nombre')}">
				<li class="error-message"><fmt:message key="${error}"
						bundle="${messages}" /></li>
			</c:forEach>

			<!-- Buscar traducción ES -->
			<c:set var="nombre_es"
				value="${empty traducciones[1] ? '' : traducciones[1].nombre}" />
			<input type="text" id="nombre_es" name="nombre_es"
				value="${(empty param.nombre_es) ? nombre_es : param.nombre_es}"
				required />
		</div>

		<!-- Campo Nombre GL -->
		<div>
			<label for="nombre_gl"> <fmt:message key="namempdetail"
					bundle="${messages}" /> GL
			</label>

			<!-- Error para 'nombre_gl' -->
			<c:forEach var="error" items="${errors.getFieldErrors('nombre')}">
				<li class="error-message"><fmt:message key="${error}"
						bundle="${messages}" /></li>
			</c:forEach>

			<!-- Buscar traducción GL -->
			<c:set var="nombre_gl"
				value="${empty traducciones[2] ? '' : traducciones[2].nombre}" />
			<input type="text" id="nombre_gl" name="nombre_gl"
				value="${(empty param.nombre_gl) ? nombre_gl : param.nombre_gl}"
				required />
		</div>



		<!-- Campo Precio -->
		<div>
			<label for="precio"> <fmt:message key="preciompdetail"
					bundle="${messages}" /></label>
			<c:forEach var="error" items="${errors.getFieldErrors('precio')}">
				<li class="error-message"><fmt:message key="${error}"
						bundle="${messages}" /></li>
			</c:forEach>
			<input type="text" step="0.01" id="precio" name="precio"
				value="${(empty param.precio) ? mp.precio : param.precio}" required />
		</div>

		<!-- Campo Unidades -->
		<div>
			<label for="unidades"> <fmt:message key="unidadesmpdetail"
					bundle="${messages}" /></label>
			<c:forEach var="error" items="${errors.getFieldErrors('unidades')}">
				<li class="error-message"><fmt:message key="${error}"
						bundle="${messages}" /></li>
			</c:forEach>
			<input type="text" id="unidades" name="unidades"
				value="${(empty param.unidades) ? mp.unidades : param.unidades}"
				required />
		</div>

		<!-- Campo Unidad Medida -->
		<div>
			<label for="unidadMedida"> <fmt:message
					key="unidadmedidamp_insert" bundle="${messages}" />
			</label>
			<c:forEach var="error"
				items="${errors.getFieldErrors('unidadMedida')}">
				<li class="error-message"><fmt:message key="${error}"
						bundle="${messages}" /></li>
			</c:forEach>

			<c:set var="unidadMedida"
				value="${(empty param.unidadMedida) ? mp.idUnidadMedida : param.unidadMedida}" />
			<select id="unidadMedida" name="unidadMedida">
				<option value="1" ${unidadMedida == 1 ? "selected" : ""}>KG</option>
				<option value="2" ${unidadMedida == 2 ? "selected" : ""}>CM</option>
				<option value="3" ${unidadMedida == 3 ? "selected" : ""}>
					<fmt:message key="unidadesmpdetail" bundle="${messages}" />
				</option>
			</select>
		</div>

		<!-- Botón Guardar Cambios -->
		<button type="submit" class="update-button">
			<fmt:message key="guardarCambiosmpdetail" bundle="${messages}" />
		</button>
	</form>

	<!-- Botón para eliminar -->
	<form action="MateriaPrimaServlet?action=delete" method="post"
		onsubmit="return confirm('¿Estás seguro de que deseas eliminar esta materia prima?');">
		<input type="hidden" name="id" value="${mp.id}" />
		<button type="submit" class="delete-button">
			<fmt:message key="borrarmpdetail" bundle="${messages}" />
		</button>
	</form>
</div>

<%@include file="/common/footer.jsp"%>
