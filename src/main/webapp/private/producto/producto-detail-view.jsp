<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="/common/header.jsp"%>

<div class="container">
	<h2>
		<fmt:message key="namempdetail" bundle="${messages}" />
		<c:out value="${p.nombre}" />
	</h2>
	<p>
		<b>Id: <c:out value="${p.id}" /></b>
		<br>
		<fmt:message key="preciompdetail" bundle="${messages}" />
		<c:out value="${p.precio}" />
		€ <br>
		<i><fmt:message key="unidadesmpdetail" bundle="${messages}" /> <c:out value="${p.unidades}" /></i>
	</p>

	<h3>
		<fmt:message key="detalleconsumos" bundle="${messages}" />
	</h3>

	<c:forEach var="consumo" items="${consumos}">
		<p>
			<b><fmt:message key="mp_id" bundle="${messages}" /></b>
			<c:out value="${consumo.idMateriaPrima}" />
			<br>
			<b><fmt:message key="namempdetail" bundle="${messages}" /></b>
			<c:out value="${consumo.nombreMateriaPrima}" />
			<br>
			<b><fmt:message key="preciompdetail" bundle="${messages}" /></b>
			<c:out value="${consumo.precioMateriaPrima}" />
			€ <br>
			<b><fmt:message key="unidadesconsumidas" bundle="${messages}" /></b>
			<c:out value="${consumo.unidades}" />

			<!-- Formulario para eliminar -->
			<form action="ProductoServlet" method="post" onsubmit="return confirm('¿Estás seguro de que deseas eliminar este consumo?');">
				<input type="hidden" name="action" value="delete_consumo" />
				<input type="hidden" name="idProducto" value="${p.id}" />
				<input type="hidden" name="idMateriaPrima" value="${consumo.idMateriaPrima}" />
				<button type="submit" class="delete-button">
					<fmt:message key="borrarconsumo_boton" bundle="${messages}" />
				</button>
			</form>
		</p>
		<hr>
	</c:forEach>

	<form action="ProductoServlet?action=delete" method="post" onsubmit="return confirm('¿Estás seguro de que deseas eliminar este producto?');">
		<input type="hidden" name="id" value="${p.id}" />
		<button type="submit" class="delete-button">
			<fmt:message key="borrarproducto_boton" bundle="${messages}" />
		</button>
	</form>
</div>

<%@include file="/common/footer.jsp"%>
