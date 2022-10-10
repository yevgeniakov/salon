<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my"%>
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
<title>My info</title>
</head>


<h3>My info</h3>
<br>
<form action="Controller" method=post>
	<input type="hidden" name="id" value="${sessionScope.user.id}" /> <br>
	<strong>Name:</strong>
	<br> <input type="text" name="name"
		value="${sessionScope.user.name}" /> <br>
	<strong>Surname:</strong>
	<br> <input type="text" name="surname"
		value="${sessionScope.user.surname}" /> <br>
	<strong>email:</strong>
	<br> <input type="text" name="email"
		value="${sessionScope.user.email}" /> <br>
	<strong>tel:</strong>
	<br> <input type="text" name="tel"
		value="${sessionScope.user.tel}" /> <br>
		
	<c:if
		test="${sessionScope.user.role == 'HAIRDRESSER'}">
			<c:out value="${sessionScope.user.info}"></c:out>
	</c:if>
				
		<br>
		
		
	<c:if test="${sessionScope.user.role == 'ADMIN'}">
	
		<strong>Role:</strong>
		<br>
		<input type="radio" name="role" value="CLIENT"
			<c:if test="${sessionScope.user.role == 'CLIENT'}">checked</c:if>
			disabled />Client
    <br>
		<input type="radio" name="role" value="HAIRDRESSER"
			<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">checked</c:if>
			disabled />Hairdresser
	<br>
		<input type="radio" name="role" value="ADMIN"
			<c:if test="${sessionScope.user.role == 'ADMIN'}">checked</c:if>
			disabled />Admin

</c:if>
	<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">
		<my:getservices master_id="${sessionScope.user.id}" />
		<c:if test="${servicemap != null}">
			
				<strong> Master Services: </strong>
			
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

	<input type="hidden" name="command" value="update_user">
	<p>
	<p>
		<input type="submit" value="Update data">
</form>

</body>
</html>
