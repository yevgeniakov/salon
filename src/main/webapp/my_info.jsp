<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>

<body class="d-flex flex-column h-100">
	<jsp:include page="header.jsp" />
<head>
<link href="css/forms.css" rel="stylesheet">
<link href='css/jquery.rating.css' type="text/css" rel="stylesheet" />
<title><fmt:message key="label.my_info" /></title>
</head>
<div class="user-info">
	<h3>
		<fmt:message key="label.my_info" />
	</h3>
	<br>
	<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">
		<fmt:message key="label.rating" var="val" />
		<c:out value="${val}: ${sessionScope.user.rating}"></c:out>
		<c:if test="${sessionScope.user.rating !=0}">
			<br>
			<h:createstars rating="${sessionScope.user.rating}"
				inputname="${'rat'}${sessionScope.user.id}" />
			<br>
		</c:if>
	</c:if>
	<form action="Controller" method=post id="profile_form">
		<input type="hidden" name="id" value="${sessionScope.user.id}" /> <br>
		<div class="mb-3 row">
			<label for="name" class="col-sm-2 col-form-label"><fmt:message
					key="label.name" /></label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="name" id="name"
					value="${sessionScope.user.name}"> <span
					id="error_firstname" class="error" aria-live="polite"></span>
			</div>
		</div>
		<div class="mb-3 row">
			<label for="surname" class="col-sm-2 col-form-label"><fmt:message
					key="label.surname" /></label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="surname" id="surname"
					value="${sessionScope.user.surname}"> <span
					id="error_surname" class="error" aria-live="polite"></span>
			</div>
		</div>
		<div class="mb-3 row">
			<label for="email" class="col-sm-2 col-form-label"><fmt:message
					key="label.email" /></label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="email" id="email"
					value="${sessionScope.user.email}"> <span id="error_email"
					class="error" aria-live="polite"></span>
			</div>
		</div>
		<div class="mb-3 row">
			<label for="tel" class="col-sm-2 col-form-label"><fmt:message
					key="label.tel" /></label>
			<div class="col-sm-10">
				<input type="text" class="form-control" name="tel" id="tel"
					value="${sessionScope.user.tel}"> <span id="error_tel"
					class="error" aria-live="polite"></span>
			</div>
		</div>
		<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">
			<label><fmt:message key="label.master_info" /></label>
			<br>
			<span class="border border-info">${sessionScope.user.info}</span>
			<br>
		</c:if>
		<br>
		<c:if test="${sessionScope.user.role == 'ADMIN'}">
			<strong><fmt:message key="label.role" /></strong>
			<br>
			<input class="form-check-input" type="radio" name="role"
				value="CLIENT"
				<c:if test="${sessionScope.user.role == 'CLIENT'}">checked</c:if>
				disabled />
			<fmt:message key="option.roleclient" />
			<br>
			<input class="form-check-input" type="radio" name="role"
				value="HAIRDRESSER"
				<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">checked</c:if>
				disabled />
			<fmt:message key="option.rolehairdresser" />
			<br>
			<input class="form-check-input" type="radio" name="role"
				value="ADMIN"
				<c:if test="${sessionScope.user.role == 'ADMIN'}">checked</c:if>
				disabled />
			<fmt:message key="option.roleadmin" />
			<br>
		</c:if>
		<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">
			<my:getservices master_id="${sessionScope.user.id}" />
			<c:if test="${servicemap != null}">
				<strong><fmt:message key="label.master_services" /></strong>
				<table class="table table-striped">
					<c:forEach items="${servicemap}" var="entry">
						<tr>
							<td><c:out value="${entry.key.name}" /></td>
							<td><c:out value="${entry.value} " /> <fmt:message
									key="label.hrn" /></td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</c:if>
		<input type="hidden" name="isBlocked"
			value="${sessionScope.user.isBlocked}"> <input type="hidden"
			name="info" value="${sessionScope.user.info}"> <input
			type="hidden" name="command" value="update_user"> <br>
		<button type="submit" class="btn btn-primary">
			<fmt:message key="button.update_data" />
		</button>
	</form>
</div>
<footer class="footer mt-auto py-3 bg-dark">
	<div class="container">
		<span class="text-light"><fmt:message key="label.footer" /></span>
	</div>
</footer>
<script src="${pageContext.request.contextPath}/js/info.js"></script>
<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js'
	type="text/javascript"></script>
<script src='js/jquery.MetaData.js' type="text/javascript"></script>
<script src='js/jquery.rating.js' type="text/javascript"></script>
</body>
</html>
