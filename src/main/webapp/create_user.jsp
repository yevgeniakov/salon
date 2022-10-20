<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>

<body class="d-flex flex-column h-100">
	<jsp:include page="header.jsp" />
<head>
<link href="css/registration.css" rel="stylesheet">
<title><fmt:message key="label.create_user" /></title>
</head>

<div class="registration_data">
	<h3>
		<fmt:message key="label.provide_user_data" />
	</h3>
	<br>
	<form action="Controller" id="registration_form" method=post>

		<div class="mb-3 row">
			<label for="email" class="col-sm-2 col-form-label"><fmt:message
					key="label.login" /></label>
			<div class="col-sm-10">
				<input id="email" class="form-control" name="email" required>
				<span id="error_email" class="error" aria-live="polite"></span>
			</div>
		</div>

		<div class="mb-3 row">
			<label for="password" class="col-sm-2 col-form-label"><fmt:message
					key="label.password" /></label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="password"
					name="password" required> <span id="error_password"
					class="error" aria-live="polite"></span>
			</div>
		</div>
		<div class="mb-3 row">
			<label for="repeat_password" class="col-sm-2 col-form-label"><fmt:message
					key="label.repeat_password" /></label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="repeat_password"
					name="repeat_password" required> <span
					id="error_repeat_password" class="error" aria-live="polite"></span>
			</div>
		</div>
		<div class="mb-3 row">
			<label for="name" class="col-sm-2 col-form-label"><fmt:message
					key="label.name" /></label>
			<div class="col-sm-10">
				<input name="name" class="form-control" id="name" required>
				<span id="error_firstname" class="error" aria-live="polite"></span>
			</div>
		</div>
		<div class="mb-3 row">
			<label for="surname" class="col-sm-2 col-form-label"><fmt:message
					key="label.surname" /></label>
			<div class="col-sm-10">
				<input name="surname" class="form-control" id="surname" required>
				<span id="error_surname" class="error" aria-live="polite"></span>
			</div>
		</div>
		<div class="mb-3 row">
			<label for="tel" class="col-sm-2 col-form-label"><fmt:message
					key="label.tel" /></label>
			<div class="col-sm-10">
				<input name="tel" class="form-control" id="tel" required> <span
					id="error_tel" class="error" aria-live="polite"></span>
			</div>
		</div>

		<strong><fmt:message key="label.role" /></strong> <br> <input
			class="form-check-input" type="radio" name="role" id="select_role"
			onchange="change_master_options()" value="CLIENT" checked />
		<fmt:message key="option.roleclient" />
		<br> <input class="form-check-input" type="radio" name="role"
			id="select_role" onchange="change_master_options()"
			value="HAIRDRESSER" />
		<fmt:message key="option.rolehairdresser" />
		<br> <input class="form-check-input" type="radio" name="role"
			id="select_role" onchange="change_master_options()" value="ADMIN" />
		<fmt:message key="option.roleadmin" />
		<br>
		<div id="master_options" style="display: none;">
			<strong><fmt:message key="label.master_info" /></strong> <br>
			<textarea class="form-control" rows="10" cols="60" name="info"></textarea>

			<my:getservices master_id="0" />

			<br> <strong><fmt:message key="label.service" /></strong> <br>
			<div class="row">
				<c:set var="count" value="0" scope="page" />
				<c:forEach items="${servicelist}" var="item">
					<c:set var="count" value="${count + 1}" scope="page" />
					<div class="col-8">
						<input class="form-control" name="${'service'}${count}"
							value="${item.name}" size="40">
					</div>
					<div class="col-2">
						<input class="form-control" name="sum${count}" size="5">
					</div>
					<div class="col">
						<fmt:message key="label.hrn" />
					</div>
					<div class="w-100"></div>

				</c:forEach>
			</div>
		</div>
		<br> <input type="hidden" name="command" value="create_user">


		<div class="btn-group registration-btns">
			<button class="w-100 btn btn-lg btn-primary text-nowrap" type="submit">
				<fmt:message key="button.create_user" />
			</button>
			<button class="w-100 btn btn-lg btn-primary text-nowrap" type="reset">
				<fmt:message key="button.reset" />
			</button>
		</div>

	</form>
</div>

    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>
<script src="${pageContext.request.contextPath}/js/create_user.js"></script>
</body>
</html>
