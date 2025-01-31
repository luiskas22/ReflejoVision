<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ page import="com.pinguela.web.util.*"%>

<script>
	$(document).ready(function() {
		$("#nombre").keyup(function() {
			var nombre = $(this).val();
			$.ajax({
				type: "GET",
				url: "http://localhost:8080/ReflejoVisionWebServices/materiaprima?locale=es",
				data: {
					'nombre': nombre										
				},
				<!-- contentType: "" --->
				dataType: "json",
				success: function(results) {
					var htmlResultado = "<ul>";
					for (var i = 0; i< results.length; i++) {
						htmlResultado += "<li>"+results[i].nombre+"</li>";
					}
					htmlResultado += "</ul>";
					$("#results").html(htmlResultado);
				}				
			});
		});
	});
</script>
<form action="${pageContext.request.contextPath}/private/MateriaPrimaServlet"
	method="post" class="search-container">

	<h3>
		<fmt:message key="h1mpsearch" bundle="${messages}" />
	</h3>

	<input type="hidden" name="action" value="search" />

	<!-- Campo: Nombre -->
	<label><fmt:message key="nombrempsearch" bundle="${messages}" /></label>
	<c:forEach var="error" items="${errors.getFieldErrors(Attributes.NOMBRE)}">
		<li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>
	<input id="nombre" type="text" name="nombre" placeholder="Ejemplo: Placa base" />

	<!-- Campo: ID -->
	<label>Id:</label>
	<c:forEach var="error" items="${errors.getFieldErrors(Attributes.ID)}">
		<li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="id" />

	<!-- Campo: Precio Desde -->
	<label><fmt:message key="preciodesdempsearch" bundle="${messages}" /></label>
	<c:forEach var="error" items="${errors.getFieldErrors(Attributes.PRECIODESDE)}">
		<li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="precioDesde" step="0.01" />

	<!-- Campo: Precio Hasta -->
	<label><fmt:message key="preciohastampsearch" bundle="${messages}" /></label>
	<c:forEach var="error" items="${errors.getFieldErrors(Attributes.PRECIOHASTA)}">
		<li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="precioHasta" step="0.01" />

	<!-- Campo: Unidades Desde -->
	<label><fmt:message key="unidadesdesdempsearch" bundle="${messages}" /></label>
	<c:forEach var="error" items="${errors.getFieldErrors(Attributes.UNIDADESDESDE)}">
		<li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="unidadesDesde" />

	<!-- Campo: Unidades Hasta -->
	<label><fmt:message key="unidadeshastampsearch" bundle="${messages}" /></label>
	<c:forEach var="error" items="${errors.getFieldErrors(Attributes.UNIDADESHASTA)}">
		<li class="error-message"><fmt:message key="${error}" bundle="${messages}" /></li>
	</c:forEach>
	<input type="number" name="unidadesHasta" />

	<input type="submit" value="<fmt:message key='botonbuscar' bundle='${messages}' />" />
	
	
</form>


<div class="results-container" id="results">
	<!--  getPage() ? -->
	<c:choose>
		<c:when test="${not empty resultados}">
			<%-- <c:when test="{resultados is not null}"> --%>
			<h3><fmt:message
			key="resultadosmpsearch" bundle="${messages}" /></h3>
			<ul>

				<c:forEach var="mp" items="${resultados}">
					<li><a
						href="<%=request.getContextPath()%>/private/MateriaPrimaServlet?action=detail&id=${mp.id}">
							<c:out value="${mp.nombre}" />
					</a></li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<p><fmt:message
			key="resultadosnoencontrados" bundle="${messages}" /></p>
		</c:otherwise>
	</c:choose>
</div>
<%@include file="/common/footer.jsp"%>

