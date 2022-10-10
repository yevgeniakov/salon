<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my" %> 

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

<h3>Please, provide the information below:</h3>
<br>
<form action="Controller" method=post>
	<p>
		<strong>Login (e-mail): </strong> <br> <input type="email"
			name="email" size="50">
	<p>
	<p>
		<strong>Password: </strong> <br> <input type="password" size="20"
			name="password"> <br>
	<p>
	<p>
		<strong>Repeat password: </strong> <br> <input type="password"
			size="20" name="repeat_password"> <br>
	<p>
	<p>
		<strong>Name: </strong> <br> <input size="50" name="name">
		<br>
	<p>
	<p>
		<strong>Surname: </strong> <br> <input size="50" name="surname">
		<br>
	<p>
	<p>
		<strong>Tel: </strong> <br> <input size="15" name="tel">
		<br>
	<p>
	<p>
		<strong>Role: </strong> <br> <input type="radio" name="role"
			value="CLIENT" />Client <br> <input type="radio" name="role"
			value="HAIRDRESSER" />Hairdresser <br> <input type="radio"
			name="role" value="ADMIN" />Admin <br>
	<p>
	<p>
		<strong>Master info: </strong> <br>
		<textarea rows="10" cols="60" name="info"></textarea>
		
		

<my:getservices master_id = "0" />

<br>
<strong>Service: </strong> <br> 
<c:set var="count" value="0" scope="page" />
	<c:forEach items="${servicelist}" var="item">
	<c:set var="count" value="${count + 1}" scope="page"/>
    	<input name="${'service'}${count}" value="${item.name}">  <input name="sum${count}" size="5"> <br>
  </c:forEach>

		<br> <input type="hidden" name="current_lang" value="en">
		<input type="hidden" name="command" value="create_user">
	<p>
	<p>
		<input type="submit" value="Submit"> <input type="reset"
			value="Reset">
</form>



</body>
</html>
