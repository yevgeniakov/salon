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
<title><fmt:message key="label.my_info"/></title>
</head>


<h3><fmt:message key="label.my_info"/></h3>
<br>
<form action="Controller" method=post>
	<input type="hidden" name="id" value="${sessionScope.user.id}" /> <br>
	<strong><fmt:message key="label.name"/></strong>
	<br> <input type="text" name="name"
		value="${sessionScope.user.name}" /> <br>
	<strong><fmt:message key="label.surname"/></strong>
	<br> <input type="text" name="surname"
		value="${sessionScope.user.surname}" /> <br>
	<strong><fmt:message key="label.email"/></strong>
	<br> <input type="text" name="email"
		value="${sessionScope.user.email}" /> <br>
	<strong><fmt:message key="label.tel"/></strong>
	<br> <input type="text" name="tel"
		value="${sessionScope.user.tel}" /> <br>
		
	<c:if
		test="${sessionScope.user.role == 'HAIRDRESSER'}">
			<c:out value="${sessionScope.user.info}"></c:out>
	</c:if>
				
		<br>
		
		
	<c:if test="${sessionScope.user.role == 'ADMIN'}">
	
		<strong><fmt:message key="label.role"/></strong>
		<br>
		<input type="radio" name="role" value="CLIENT"
			<c:if test="${sessionScope.user.role == 'CLIENT'}">checked</c:if>
			disabled /><fmt:message key="option.roleclient"/>
    <br>
		<input type="radio" name="role" value="HAIRDRESSER"
			<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">checked</c:if>
			disabled /><fmt:message key="option.rolehairdresser"/>
	<br>
		<input type="radio" name="role" value="ADMIN"
			<c:if test="${sessionScope.user.role == 'ADMIN'}">checked</c:if>
			disabled /><fmt:message key="option.roleadmin"/>

</c:if>
	<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">
		<my:getservices master_id="${sessionScope.user.id}" />
		<c:if test="${servicemap != null}">
			
				<strong><fmt:message key="label.master_services"/></strong>
			
			<table>
				<c:forEach items="${servicemap}" var="entry">
					<tr>
						<td><c:out value="${entry.key.name}" /></td>
						<td><c:out value="${entry.value} hrn." /></td>


					</tr>
				</c:forEach>
			</table>
		</c:if>

	</c:if>
	<input type="hidden" name="isBlocked" value="${sessionScope.user.isBlocked}">
	<input type="hidden" name="info" value="${sessionScope.user.info}">
	<input type="hidden" name="command" value="update_user">
	<p>
	<p>
		<input type="submit" value=<fmt:message key="button.update_data"/>>
</form>

</body>
</html>
