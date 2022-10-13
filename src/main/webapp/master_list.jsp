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
<title><fmt:message key="label.master_list"/></title>
</head>
<c:if test="${param.service_id != null}">
<c:set var="user_id" scope="request" value="${param.service_id}"/>
</c:if>

<br>
<strong><fmt:message key="label.sortby"/></strong>
<form action="Controller" method="get">
<select name="sort">
		<option value="surname" <c:if test="${sort == 'surname' || sort == null}"> selected </c:if>><fmt:message key="option.surname"/></option>
		<option value="rating" <c:if test="${sort == 'rating'}"> selected </c:if>><fmt:message key="option.rating"/></option>
		
	</select>
	<input type="hidden" name="service_id" value="${service_id}">
	<input type="hidden" name="command" value="${service_id == null? 'show_master_list' : 'show_masters_of_service' }">
	<input type = "submit" value=<fmt:message key="button.apply"/>> 
</form>

<br>
<c:if test="${service == null}">
<strong><fmt:message key="label.master_list"/></strong>
	<table>
<tr>
<th><fmt:message key="label.name"/></th><th><fmt:message key="label.tel"/></th><th><fmt:message key="label.short_info"/></th><th><fmt:message key="label.rating"/></th><th><fmt:message key="label.info"/></th><c:if test="${sessionScope.user != null}"><th><fmt:message key="label.view_schedule"/></th></c:if>
</tr>
		<c:forEach items="${userlist}" var="item">
			<tr>
				<td><c:out value="${item.name} ${item.surname}" /></td>
				<td><c:out value="${item.tel}" /></td>
				<td><c:out value="${item.info}" /></td>
				<td><c:out value="${item.rating}" /></td>
				<td><a href="Controller?command=show_user_info&id=${item.id}">
						<fmt:message key="label.master_info"/> </a></td>
				<c:if test="${sessionScope.user != null}">
					<td><a
						href="Controller?command=show_master_schedule&id=${item.id}&date=${LocalDate.now()}">
							<fmt:message key="link.view_schedule"/> </a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</c:if>

<c:if test="${service != null}">

	<strong><fmt:message key="label.masters_who"/> "${service.name}" </strong>
	<br>
	<table>
<tr>

<td><fmt:message key="label.name"/></td><td><fmt:message key="label.tel"/></td><td><fmt:message key="label.short_info"/></td><td><fmt:message key="label.rating"/></td><td><fmt:message key="label.price"/></td><td><fmt:message key="label.info"/></td><c:if test="${sessionScope.user != null}"><td><fmt:message key="label.view_schedule"/></td></c:if>

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
						<fmt:message key="label.master_info"/> </a></td>
				<c:if test="${sessionScope.user != null}">
					<td><a
						href="Controller?command=show_master_schedule&id=${entry.key.id}&date=${LocalDate.now()}">
							<fmt:message key="label.view_schedule"/> </a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</c:if>




</body>
</html>
