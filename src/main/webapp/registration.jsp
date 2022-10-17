<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>

<body>
<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.registration_page"/></title>
<link href="css/registration.css" rel="stylesheet">

</head>
<div class="registration_data">
<h3><fmt:message key="label.provide_your_data"/></h3>
<br>
<form action="Controller" id="registration_form" name="registration_form" method=post>
		<strong><fmt:message key="label.login"/></strong> <br>
		<input id="email" name="email" required ><br>
		<span id="error_email" class="error" aria-live="polite"></span><br>
		<strong><fmt:message key="label.password"/></strong> <br>
		<input type="password" id="password" name="password" required> <br>
		<span id="error_password" class="error" aria-live="polite"></span><br>
		<strong><fmt:message key="label.repeat_password"/></strong> <br>
		<input type="password" id="repeat_password" name="repeat_password" required><br>
		<span id="error_repeat_password" class="error" aria-live="polite"></span><br>
		<strong><fmt:message key="label.name"/></strong> <br>
		<input name="name" id= "name" required> <br>
		<span id="error_firstname" class="error" aria-live="polite"></span><br>
		<strong><fmt:message key="label.surname"/></strong> <br>
		<input name="surname" id="surname" required> <br>
		<span id="error_surname" class="error" aria-live="polite"></span><br>
		<strong><fmt:message key="label.tel"/></strong> <br>
		<input name="tel" id="tel" required> <br>

		<input type="hidden" name="current_lang" value="en">
		<input type="hidden" name="role" value="CLIENT">
		<input type="hidden" name="command" value="create_user">
		<br>
		<input type="submit" value=<fmt:message key="button.register"/>>
		<input type="reset"	value=<fmt:message key="button.reset"/>> 
</form>
</div>
<script src="${pageContext.request.contextPath}/js/registration.js"></script>
</body>
</html>
