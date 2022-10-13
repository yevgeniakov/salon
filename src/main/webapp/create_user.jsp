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
<title>Create User Page</title>
</head>

<h3><fmt:message key="label.provide_user_data"/></h3>
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
	<p>
	<p>
		<strong><fmt:message key="label.tel"/></strong> <br> <input size="15" name="tel">
		<br>
	<p>
	<p>
		<strong><fmt:message key="label.role"/></strong> <br> <input type="radio" name="role"
			value="CLIENT" checked /><fmt:message key="option.roleclient"/> <br> <input type="radio" name="role"
			value="HAIRDRESSER" /><fmt:message key="option.rolehairdresser"/> <br> <input type="radio"
			name="role" value="ADMIN" /><fmt:message key="option.roleadmin"/> <br>
	<p>
	<p>
		<strong><fmt:message key="label.master_info"/></strong> <br>
		<textarea rows="10" cols="60" name="info"></textarea>
		
		

<my:getservices master_id = "0" />

<br>
<strong><fmt:message key="label.service"/></strong> <br> 
<c:set var="count" value="0" scope="page" />
	<c:forEach items="${servicelist}" var="item">
	<c:set var="count" value="${count + 1}" scope="page"/>
    	<input name="${'service'}${count}" value="${item.name}" size="40">  <input name="sum${count}" size="5"> <fmt:message key="label.hrn"/> <br>
  </c:forEach>

		<br> <input type="hidden" name="current_lang" value="en">
		<input type="hidden" name="command" value="create_user">
	<p>
	<p>
		<input type="submit" value=<fmt:message key="button.create_user"/>> <input type="reset"
			value=<fmt:message key="button.reset"/>>
</form>



</body>
</html>
