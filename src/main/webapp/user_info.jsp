<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my"%>
<%@page import="java.time.LocalDate"%>
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
<title>User info</title>
</head>

<h3>User info - ${showuser.name} ${showuser.surname} </h3>
<h4>${showuser.role}</h4>
<c:if test="${showuser.role == 'HAIRDRESSER'}">
<c:out value="rating: ${showuser.rating }"></c:out>
</c:if>

<br>
<form action="Controller" method=post>
	<input type="hidden" name="id" value="${showuser.id}" /> <br>
	<strong>Name:</strong>
	<br> <input type="text" name="name" value="${showuser.name}"
		<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if> />
	<br>
	<strong>Surname:</strong>
	<br> <input type="text" name="surname" value="${showuser.surname}"
		<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if> />
	<br>
	<strong>email:</strong>
	<br> <input type="text" name="email" value="${showuser.email}"
		<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if> />
	<br>
	<strong>tel:</strong>
	<br> <input type="text" name="tel" value="${showuser.tel}"
		<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if> />
	<br>
	
	<c:if
		test="${showuser.role == 'HAIRDRESSER'}">
			<c:out value="${showuser.info}"></c:out>
	</c:if>
	<c:if test="${showuser.role == 'ADMIN'}">
		<strong>Role:</strong>
		<br>
		<input type="radio" name="role" value="CLIENT"
			<c:if test="${showuser.role == 'CLIENT'}">checked</c:if> disabled />Client
    <br>
		<input type="radio" name="role" value="HAIRDRESSER"
			<c:if test="${showuser.role == 'HAIRDRESSER'}">checked</c:if>
			disabled />Hairdresser
	<br>
		<input type="radio" name="role" value="ADMIN"
			<c:if test="${showuser.role == 'ADMIN'}">checked</c:if> disabled />Admin

</c:if>
	<c:if
		test="${sessionScope.user.role == 'ADMIN' && showuser.role == 'HAIRDRESSER'}">
		<my:getabsentservices master_id="${showuser.id}" />

		<strong>Add service: </strong>
		<br>
		<c:set var="count" value="0" scope="page" />
		<c:forEach items="${serviceabsentlist}" var="item">
			<c:set var="count" value="${count + 1}" scope="page" />
			<input name="${'service'}${count}" value="${item.name}">
			<input name="sum${count}" size="5">
			<br>
		</c:forEach>


	</c:if>

	<c:if
		test="${sessionScope.user.role == 'ADMIN' && showuser.isBlocked }">
		<strong>BLOCKED</strong>


	</c:if>


	<input type="hidden" name="command" value="update_user"> <input
		type="hidden" name="isBlocked" value="${showuser.isBlocked}">
	<p>
	<p>
		<c:if test="${sessionScope.user.role == 'ADMIN'}">
			<input type="submit" value="Update data">
		</c:if>
</form>

<c:if test="${services != null && showuser.role == 'HAIRDRESSER'}">
		
			<strong> Master Services: </strong>
		

		<table>
			<c:forEach items="${services}" var="entry">
				<tr>
					<td><c:out value="${entry.key.name}" /></td>
					<td><c:out value="${entry.value} hrn." /></td>

					<c:if test="${sessionScope.user.role == 'ADMIN'}">
						<td>
							<form action="Controller" method="post">
								<input type="hidden" name=service_id value="${entry.key.id}" />
								<input type="hidden" name=master_id value="${showuser.id}" /> 
								<input type="hidden" name=command value="delete_service_from_master" />
								<input type="submit" name="submit" value="X" />
							</form>
						</td>
					</c:if>


				</tr>
			</c:forEach>
		</table>


	</c:if>

<c:if
	test="${sessionScope.user != null && showuser.role == 'HAIRDRESSER'}">
	<a
		href="Controller?command=show_master_schedule&id=${showuser.id}&date=${LocalDate.now()}">
		View schedule </a>
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN'}">
	<form action="Controller" method=post>
		<input type="hidden" name="command" value="set_user_block"> <input
			type="hidden" name="isBlocked"
			value="${showuser.isBlocked? 'false' : 'true'}"> <input
			type="hidden" name="id" value="${showuser.id}"> <input
			type="submit"
			value="${showuser.isBlocked? 'UNBLOCK user' : 'BLOCK user'}">

	</form>
</c:if>

	
<c:if
	test="${sessionScope.user != null &&sessionScope.user.role == 'ADMIN' && showuser.role == 'CLIENT'}">
	<br>
<a href = "appointments_list.jsp?user_id=${showuser.id}"> User appointments </a>
</c:if>
</body>
</html>
