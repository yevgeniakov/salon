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
<title>Create Service Page</title>
</head>

<h3><fmt:message key="label.appointments_list"/></h3>
<br>
<form action="Controller" method=post>
	<strong><fmt:message key="label.name_of_service"/></strong> <br> <input name="name" required> <br>
	<p>

		<strong><fmt:message key="label.service_info"/></strong> <br>
		<textarea rows="10" cols="60" name="info" required>

    </textarea>

		<br> <input type="hidden" name="current_lang" value="en">
		<input type="hidden" name="command" value="create_service">
	<p>
	<p>
		<input type="submit" value=<fmt:message key="button.create_service"/>> <input type="reset"
			value=<fmt:message key="button.reset"/>>
</form>



</body>
</html>
