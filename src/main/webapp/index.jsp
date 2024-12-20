<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/js/manual.js"></script>

<%@ include file="/common/header.jsp"%>

<div class="container">
	<c:choose>
		<c:when test="${not empty sessionScope.usuario}">
			<!-- Title with arrow -->
			<h1 id="manualHeader">
				User Manual <span id="toggleArrow">▼</span>
			</h1>

			<!-- Hidden content -->
			<div id="manualContent">
				<h2>1. Introduction</h2>
				<p>This web application is designed to streamline factory
					management, enabling users to efficiently manage raw materials,
					products, and users. It offers an intuitive and functional
					interface to optimize processes.</p>

				<h2>2. System Requirements</h2>
				<ul>
					<li>An updated web browser (Google Chrome, Mozilla Firefox,
						Microsoft Edge, or similar).</li>
					<li>Internet connection.</li>
					<li>Access credentials provided by the administrator.</li>
				</ul>

				<h2>3. Login</h2>
				<p>
					1. Go to the login page at the URL
					<code>/user/login.jsp</code>
					.
				</p>
				<p>2. Enter your username and password in the corresponding
					fields.</p>
				<p>3. Click the "Login" button.</p>
				<p>4. If the credentials are correct, you will be redirected to
					the application's main dashboard.</p>
				<p>
					<strong>Note:</strong> If you forget your password, contact the
					administrator to reset it.
				</p>

				<h2>4. Application Navigation</h2>
				<p>The application features a main navigation menu divided into
					the following sections:</p>

				<h3>Raw Materials</h3>
				<p>In this section, you can:</p>
				<ul>
					<li>Buy raw materials.</li>
					<li>Search for raw materials based on specific criteria.</li>
				</ul>

				<h3>Products</h3>
				<p>This section allows you to:</p>
				<ul>
					<li>Create new products and associate raw materials.</li>
					<li>View the list of existing products and their consumption.</li>
				</ul>

				<h3>Users</h3>
				<p>Available options:</p>
				<ul>
					<li>Manage system users.</li>
					<li>View information about roles and permissions.</li>
				</ul>

				<h3>Raw Materials Search</h3>
				<p>1. Go to the "Raw Materials" section from the main menu.</p>
				<p>2. Fill in the filters in the search form according to your
					needs.</p>
				<p>3. Click "Search" to view the results.</p>
				<p>4. The information will be displayed in an organized table.</p>

				<h3>Product Creation</h3>
				<p>1. Access the "Products" module.</p>
				<p>2. Click "Create Product".</p>
				<p>3. Fill in the required fields (product name, description,
					etc.).</p>
				<p>4. Select the raw materials and specify the required units.</p>
				<p>5. Save the changes to register the new product.</p>

				<h3>User Management</h3>
				<p>1. From the "Users" menu, access the "User Management"
					option.</p>
				<p>You can:</p>
				<ul>
					<li>Create new users.</li>
					<li>Modify existing information.</li>
					<li>Delete users who no longer need access.</li>
				</ul>

				<hr>
				<p>
					<strong>© 2024 Factory Management Application. All rights
						reserved.</strong>
				</p>
			</div>
		</c:when>
		<c:otherwise>

			<h1>
				<fmt:message key="welcome" bundle="${messages}" />
			</h1>
		</c:otherwise>

	</c:choose>
</div>

<%@ include file="/common/footer.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/manual.css">
