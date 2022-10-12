<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my" %> 
<%@ page isELIgnored="false"%>

<html>
      <style>
         table, th, td {
            border: 1px solid black;
         }
      </style>
<body>
<jsp:include page="header.jsp" />
<head>
<title>Create Appointment</title>
</head>

<hr>
<h3>Please, check you data and select the service:</h3>

<br>
<form action="Controller" method=post>
	<p>
		<strong>Name: </strong> <br> <input name="name"
			value="${sessionScope.user.name}" size="50" disabled>
	<p>
		<strong>Surname: </strong> <br> <input size="50" name="surname"
			value="${sessionScope.user.surname}" disabled> <br>
	<p>
	
	<my:getservices master_id = "${param.master_id }" />
		<strong>Service: </strong> <br> 
		<select name="service_id">
			<c:forEach items="${servicemap}" var="entry">
				<option value="${entry.key.id}">${entry.key.name} - ${entry.value} hrn.</option>
			</c:forEach>
		</select> <br> 
		<input type="hidden" name="user_id" value="${sessionScope.user.id}">
		<input type="hidden" name="date" value="${param.date}">
		<input type="hidden" name="master_id" value="${param.master_id}">
		<input type="hidden" name="timeslot" value="${param.timeslot}">
			<input type="hidden" name="command" value="create_appointment">
	<p>
	<p>
		<input type="submit" value="Submit"> 
</form>



</body>
</html>
