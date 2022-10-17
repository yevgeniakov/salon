<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.time.LocalDate"%>

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

<h3><fmt:message key="label.user_info"/> - ${showuser.name} ${showuser.surname} </h3>
<h4>${showuser.role}</h4>
<c:if test="${showuser.role == 'HAIRDRESSER'}">
<fmt:message key="label.rating" var="val" />
 <c:out value="${val}: ${showuser.rating }"></c:out>
</c:if>

<br>
<form action="Controller" method=post>
	<input type="hidden" name="id" value="${showuser.id}" /> <br>
	<strong><fmt:message key="label.name"/></strong>
	<br> <input type="text" name="name" value="${showuser.name}"
		<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if> />
	<br>
	<strong><fmt:message key="label.surname"/></strong>
	<br> <input type="text" name="surname" value="${showuser.surname}"
		<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if> />
	<br>
	<strong><fmt:message key="label.email"/></strong>
	<br> <input type="text" name="email" value="${showuser.email}"
		<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if> />
	<br>
	<strong><fmt:message key="label.tel"/></strong>
	<br> <input type="text" name="tel" value="${showuser.tel}"
		<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if> />
	<br>
	
	<c:if
		test="${showuser.role == 'HAIRDRESSER' && sessionScope.user.role != 'ADMIN'}">
			<c:out value="${showuser.info}"></c:out><br>
	</c:if>
	
	<c:if
		test="${showuser.role == 'HAIRDRESSER' && sessionScope.user.role == 'ADMIN'}">
		<textarea rows="10" cols="60" name="info">${showuser.info}</textarea><br>
			
	</c:if>
	
	<c:if test="${showuser.role == 'ADMIN'}">
		<strong><fmt:message key="label.role"/></strong>
		<br>
		<input type="radio" name="role" value="CLIENT"
			<c:if test="${showuser.role == 'CLIENT'}">checked</c:if> disabled /><fmt:message key="option.roleclient"/>
    <br>
		<input type="radio" name="role" value="HAIRDRESSER"
			<c:if test="${showuser.role == 'HAIRDRESSER'}">checked</c:if>
			disabled /><fmt:message key="option.rolehairdresser"/>
	<br>
		<input type="radio" name="role" value="ADMIN"
			<c:if test="${showuser.role == 'ADMIN'}">checked</c:if> disabled /><fmt:message key="option.roleadmin"/>

</c:if>
	<c:if
		test="${sessionScope.user.role == 'ADMIN' && showuser.role == 'HAIRDRESSER'}">
		<my:getabsentservices master_id="${showuser.id}" />

		<strong><fmt:message key="label.add_service"/></strong>
		<br>
		<c:set var="count" value="0" scope="page" />
		<c:forEach items="${serviceabsentlist}" var="item">
			<c:set var="count" value="${count + 1}" scope="page" />
			<input name="${'service'}${count}" value="${item.name}" size="40">
			<input name="sum${count}" size="5"> <fmt:message key="label.hrn"/>
			<br>
		</c:forEach>


	</c:if>

	<c:if
		test="${sessionScope.user.role == 'ADMIN' && showuser.isBlocked }">
		<strong><fmt:message key="label.status_blocked"/></strong>


	</c:if>


	<input type="hidden" name="command" value="update_user">
	<input type="hidden" name="isBlocked" value="${showuser.isBlocked}">
		
	
		<c:if test="${sessionScope.user.role == 'ADMIN'}">
			<input type="submit" value=<fmt:message key="button.update_data"/>>
		</c:if>
		
</form>

<c:if test="${services != null && showuser.role == 'HAIRDRESSER'}">
		
			<strong><fmt:message key="label.master_services"/></strong>

		<table>
			<c:forEach items="${services}" var="entry">
				<tr>
					<td><c:out value="${entry.key.name}" /></td>
					<td><c:out value="${entry.value}"/></td>

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
		href="Controller?command=show_master_schedule&id=${showuser.id}&date=${LocalDate.now().plusDays(1)}">
		<fmt:message key="link.view_schedule"/> </a>
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN'}">

	<form action="Controller" method=post>
		<input type="hidden" name="command" value="set_user_block"> <input
			type="hidden" name="isBlocked"
			value="${showuser.isBlocked? 'false' : 'true'}"> <input
			type="hidden" name="id" value="${showuser.id}"> <input
			type="submit"
			<fmt:message key="button.block_user" var="block" />
			<fmt:message key="button.unblock_user" var="unblock" />

			value="${showuser.isBlocked? unblock : block}">

	</form>
</c:if>

	
<c:if
	test="${sessionScope.user != null &&sessionScope.user.role == 'ADMIN' && showuser.role == 'CLIENT'}">
	<br>
<a href = "Controller?command=show_appointments_list&user_id=${showuser.id}"><fmt:message key="link.user_appointments"/></a>
</c:if>
</body>
</html>
