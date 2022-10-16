<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>

<body>
<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.registration_page"/></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
crossorigin="anonymous">
</head>

<h3><fmt:message key="label.provide_your_data"/></h3>
<br>
<form action="Controller" method=post>
	<p>
		<strong><fmt:message key="label.login"/></strong> <br> <input type="email"
			name="email" size="50">
	<p>
	<p>
		<strong><fmt:message key="label.password"/></strong> <br> <input type="password" size="20"
			name="password"> <br>
	<p>
	<p>
		<strong><fmt:message key="label.repeat_password"/></strong> <br> <input type="password"
			size="20" name="repeat_password"> <br>
	<p>
	<p>
		<strong><fmt:message key="label.name"/></strong> <br> <input size="50" name="name">
		<br>
	<p>
	<p>
		<strong><fmt:message key="label.surname"/></strong> <br> <input size="50" name="surname">
		<br>
	<br> 
		<p>
		<strong><fmt:message key="label.tel"/></strong> <br> <input size="15" name="tel">
		<br>
	<p>
		<input type="hidden" name="current_lang" value="en">
		<input type="hidden" name="role" value="CLIENT">
		<input type="hidden" name="command" value="create_user">
	<p>
	<p>
		<input type="submit" value=<fmt:message key="button.register"/>> <input type="reset"
			value=<fmt:message key="button.reset"/>> <button class="btn btn-primary" type="submit">Button</button>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>
