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
<link href="css/registration.css" rel="stylesheet">
<title><fmt:message key="label.create_user"/></title>
</head>

<h3><fmt:message key="label.provide_user_data"/></h3>
<br>
<form action="Controller" id="registration_form" method=post>

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

		<strong><fmt:message key="label.role"/></strong> <br> 
		<input type="radio" name="role" id="select_role" onchange="change_master_options()" value="CLIENT" checked /><fmt:message key="option.roleclient"/> <br>
		<input type="radio" name="role" id="select_role" onchange="change_master_options()" value="HAIRDRESSER" /><fmt:message key="option.rolehairdresser"/> <br>
		<input type="radio" name="role" id="select_role" onchange="change_master_options()" value="ADMIN" /><fmt:message key="option.roleadmin"/> <br>
<div id="master_options" style="display: none;">
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
</div>
		<br> 
		<input type="hidden" name="command" value="create_user">
		<input type="submit" value=<fmt:message key="button.create_user"/>> 
		<input type="reset" value=<fmt:message key="button.reset"/>>
</form>

<script src="${pageContext.request.contextPath}/js/create_user.js"></script>
</body>
</html>
