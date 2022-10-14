<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
      <style>
         table, th, td {
            border: 1px solid black;
         }
      </style>
<body>
<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.registration_page"/></title>
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
			value=<fmt:message key="button.reset"/>>
</form>



</body>
</html>
