<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Master list</title>
</head>
<c:if test="${param.service_id != null}">
<c:set var="user_id" scope="request" value="${param.service_id}"/>
</c:if>

<br>
<div class="sort-controller">
<strong>Sort by: </strong>
<form action="Controller" method="get">
<div id="bl1">
<select name="sort" class="form-select">
		<option value="surname" <c:if test="${sort == 'surname' || sort == null}"> selected </c:if>>surname</option>
		<option value="rating" <c:if test="${sort == 'rating'}"> selected </c:if>>rating</option>
		
	</select>
</div>
<div id="bl2" class="btn-appl">
	<input type="hidden" name="service_id" value="${service_id}">
	<input type="hidden" name="command" value="${service_id == null? 'show_master_list' : 'show_masters_of_service' }">
	<input type = "submit" class="btn btn-primary" value="Apply"> 
</div>
</form>

</div>

<br>
<div class="list-masters">
<c:if test="${service == null}">
<strong>List of masters </strong>

<div class="table-list">
	<table class="table table-striped">
<tr>
<th>Name</th><th>Tel</th><th>Short info</th><th>Rating</th><th>Info</th><c:if test="${sessionScope.user != null}"><th>View schedule</th></c:if>
</tr>
		<c:forEach items="${userlist}" var="item">
			<tr>
				<td><c:out value="${item.name} ${item.surname}" /></td>
				<td><c:out value="${item.tel}" /></td>
				<td><c:out value="${item.info}" /></td>
				<td><c:out value="${item.rating}" /></td>
				<td><a href="Controller?command=show_user_info&id=${item.id}">
						Master Info </a></td>
				<c:if test="${sessionScope.user != null}">
					<td><a
						href="Controller?command=show_master_schedule&id=${item.id}&date=${LocalDate.now()}">
							View schedule </a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</c:if>

<c:if test="${service != null}">

	<strong>List of masters doing "${service.name}" </strong>
	<br>
	<table class="table table-striped">
<tr>

<td>Name</td><td>Tel</td><td>Short info</td><td>Rating</td><td>Price</td><td>Info</td><c:if test="${sessionScope.user != null}"><td>View schedule</td></c:if>

</tr>	

		<c:forEach items="${masters}" var="entry">
			<tr>
				<td><c:out value="${entry.key.name} ${entry.key.surname}" /></td>
				<td><c:out value="${entry.key.tel}" /></td>
				<td><c:out value="${entry.key.info}" /></td>
				<td><c:out value="${entry.key.rating}" /></td>
				<td><c:out value="${entry.value} hrn." /></td>
				<td><a
					href="Controller?command=show_user_info&id=${entry.key.id}">
						Master Info </a></td>
				<c:if test="${sessionScope.user != null}">
					<td><a
						href="Controller?command=show_master_schedule&id=${entry.key.id}&date=${LocalDate.now()}">
							View schedule </a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</c:if>
</div>
</div>


</body>
</html>
