<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>


<html>
<body class="d-flex flex-column h-100">
<head>

<link href="css/login.css" rel="stylesheet">
<title><fmt:message key="label.login_page" /></title>
</head>
<div>
	<jsp:include page="header.jsp" />

</div>
<div class="l_form form-signin w-100 m-auto">
	<form action="Controller" method=post>
		<h3 class="h3 mb-3 fw-normal">
			<fmt:message key="label.provide_your_data" />
		</h3>
		<div class="form-floating">
			<input type="email" class="form-control" id="floatingInput"
				placeholder="name@example.com" name="email"> <label
				for="floatingInput">Email address</label>
		</div>
		<div class="form-floating">
			<input type="password" class="form-control" id="floatingPassword"
				placeholder="Password" name="password"> <label
				for="floatingPassword"><fmt:message key="label.password" /></label>
		</div>
		<div class="checkbox mb-3"></div>
		<button class="w-100 btn btn-lg btn-primary" type="submit">
			<fmt:message key="button.login" />
		</button>
		<input type="hidden" name="command" value="login">
	</form>
</div>

<footer class="footer mt-auto py-3 bg-dark">
	<div class="container">
		<span class="text-light"><fmt:message key="label.footer" /></span>
	</div>
</footer>
</body>
</html>
