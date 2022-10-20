<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>

<body>
	<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.registration_page" /></title>
<link href="css/registration.css" rel="stylesheet">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<br>
<div class="registration_data">
	<h3>
		<fmt:message key="label.provide_your_data" />
	</h3>
	<br>

	<form action="Controller" id="registration_form"
		name="registration_form" method=post>
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

		<input type="hidden" name="current_lang" value="en"> <input
			type="hidden" name="role" value="CLIENT"> <input
			type="hidden" name="command" value="create_user">
		<div class="btn-group registration-btns">
			<button class="w-50 btn btn-lg btn-primary" type="submit">
				<fmt:message key="button.register" />
			</button>
			<button class="w-50 btn btn-lg btn-primary" type="reset">
				<fmt:message key="button.reset" />
			</button>
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/js/registration.js"></script>
</body>
</html>
