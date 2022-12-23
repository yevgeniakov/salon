<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.time.LocalDate"%>
<html>

<body class="d-flex flex-column h-100">
	<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.master_schedule" /></title>
<link href="css/main.css" rel="stylesheet">
<link href='css/jquery.rating.css' type="text/css" rel="stylesheet" />
</head>

<h3 align="center">
	<fmt:message key="label.master_schedule" />
	- <a href="Controller?command=show_user_info&id=${master.id}">${master.name}
		${master.surname}</a>
</h3>
<br>
<div class="table-list">
	<div class="col-sm-1">
		<form method=get name="dateForm">
			<input type="hidden" name="command" value="show_master_schedule">
			<input type="hidden" name="id" value="${param.id}"> <input
				class="form-control" type="date" name=date value="${param.date}"
				onchange="document.dateForm.submit();">
		</form>
	</div>
	<hr>
	<table class="table table-striped">
		<tr>
			<th><fmt:message key="label.time" /></th>
			<th><fmt:message key="label.master" /></th>
			<th><fmt:message key="label.info" /></th>
			<th><fmt:message key="label.client" /></th>
			<th><fmt:message key="label.service" /></th>
			<th><fmt:message key="label.sum" /></th>
			<th><fmt:message key="label.isdone" /></th>
			<th><fmt:message key="label.ispaid" /></th>
			<th><fmt:message key="label.rating" /></th>
		</tr>
		<c:set var="i" value="0"></c:set>
		<c:forEach items="${schedule}" var="item">
			<tr>
				<td><my:timeslotdisp timeslot="${item.timeslot}"
						currentLang="${sessionScope.currentLocale}" /></td>
				<td><c:if test="${item.master != null}">
						<c:out value="${item.master.name} ${item.master.surname}" />
					</c:if></td>
				<td colspan="${item.user != null ? 0 :7 }" align="center"><c:if
						test="${item.user == null && sessionScope.user.role == 'CLIENT' && param.date > LocalDate.now()}">
						<a
							href="create_appointment.jsp?master_id=${item.master.id}&date=${date}&timeslot=${item.timeslot}"><fmt:message
								key="link.create_appointment" /></a>
					</c:if> <c:if
						test="${item.user != null && (sessionScope.user.role == 'ADMIN' || sessionScope.user.id == item.user.id || sessionScope.user.id == item.master.id)}">
						<a
							href="Controller?command=show_appointment_info&master_id=${item.master.id}&date=${date}&timeslot=${item.timeslot}"><fmt:message
								key="link.appointment_info" /></a>
					</c:if> <c:if
						test="${item.user != null && sessionScope.user.role != 'ADMIN' && sessionScope.user.id != item.user.id && sessionScope.user.id != item.master.id}">
						<fmt:message key="label.time_is_occupied" />
					</c:if></td>
				<c:if test="${item.user != null}">
					<td><c:if test="${item.user != null}">
							<c:out value="${item.user.name} ${item.user.surname}" />
						</c:if></td>
					<td><c:if test="${item.service != null}">
							<c:out value="${item.service.name}" />
						</c:if></td>
					<td><c:if test="${item.sum != null && item.sum != 0}">
							<c:out value="${item.sum}" />
							<fmt:message key="label.hrn" />
						</c:if></td>
					<fmt:message key="label.complete" var="lcomplete" />
					<fmt:message key="label.incomplete" var="lincomplete" />
					<fmt:message key="label.paid" var="lpaid" />
					<fmt:message key="label.unpaid" var="lunpaid" />
					<td><c:if test="${item.user != null}">
							<c:out value="${item.isDone? lcomplete : lincomplete}"></c:out>
						</c:if></td>
					<td><c:if test="${item.user != null}">
							<c:out value="${item.isPaid? lpaid : lunpaid}"></c:out>
						</c:if></td>
					<td align="center"><c:if test="${item.rating != 0}">
							<c:set var="i" value="${i + 1}"></c:set>
							<h:createstars rating="${item.rating}" inputname="${'rat'}${i}" />
						</c:if> <c:out value="${item.rating == 0? '-' : ''}"></c:out></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</div>

<footer class="footer mt-auto py-3 bg-dark">
	<div class="container">
		<span class="text-light"><fmt:message key="label.footer" /></span>
	</div>
</footer>

<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js'
	type="text/javascript"></script>
<script src='js/jquery.MetaData.js' type="text/javascript"></script>
<script src='js/jquery.rating.js' type="text/javascript"></script>
</body>
</html>
