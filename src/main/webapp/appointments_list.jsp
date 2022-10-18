<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.time.LocalDate"%>

<html>
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}
</style>
<body>
	<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.appointments_list" /></title>
</head>
<c:if test="${param.user_id != null}">
	<c:set var="user_id" scope="request" value="${param.user_id}" />
</c:if>

<h3>
	<fmt:message key="label.appointments_list" />
</h3>
<br>

<form action="Controller" name="formSearch" method="get">
	<strong><fmt:message key="label.date_from" /></strong><input
		type="date" name="datefrom" onchange="document.formSearch.submit();"
		value="${datefrom != null? datefrom : LocalDate.now().plusDays(-7)}">
	<strong><fmt:message key="label.date_to" /></strong><input type="date"
		name="dateto" onchange="document.formSearch.submit();"
		value="${dateto != null? dateto : LocalDate.now().plusDays(7)}">

	<c:if test="${sessionScope.user.role != 'HAIRDRESSER'}">
		<my:getmasters />
		<strong><fmt:message key="label.master" /></strong>
		<select name="master_id" onchange="document.formSearch.submit();"> 
			<option value="null"
				<c:if test="${master_id == null}"> selected </c:if>><fmt:message
					key="option.all" /></option>
			<c:forEach items="${masterlist}" var="item">
				<option value="${item.id}"
					<c:if test="${master_id == item.id}"> selected </c:if>>${item.name}
					${item.surname}</option>
			</c:forEach>
		</select>
	</c:if>


	<my:getservices master_id="0" />

	<strong><fmt:message key="label.service" /></strong> <select
		name="service_id" onchange="document.formSearch.submit();">
		<option value="null"
			<c:if test="${service_id == null}"> selected </c:if>><fmt:message
				key="option.all" /></option>
		<c:forEach items="${servicelist}" var="item">
			<option value="${item.id}"
				<c:if test="${service_id == item.id}"> selected </c:if>>${item.name}</option>
		</c:forEach>
	</select> <strong><fmt:message key="label.status" /></strong> <select
		name="isdone" onchange="document.formSearch.submit();">
		<option value="null" <c:if test="${isDone == null}"> selected </c:if>><fmt:message
				key="option.all" /></option>
		<option value=true
			<c:if test="${isDone != null && isDone}"> selected </c:if>><fmt:message
				key="option.complete" /></option>
		<option value=false
			<c:if test="${isDone != null && !isDone}"> selected </c:if>><fmt:message
				key="option.incomplete" /></option>
	</select> <strong><fmt:message key="label.payment" /></strong> <select
		name="ispaid" onchange="document.formSearch.submit();">
		<option value="null" <c:if test="${isPaid == null}"> selected </c:if>><fmt:message
				key="option.all" /></option>
		<option value=true
			<c:if test="${isPaid != null && isPaid}"> selected </c:if>><fmt:message
				key="option.paid" /></option>
		<option value=false
			<c:if test="${isPaid != null && !isPaid}"> selected </c:if>><fmt:message
				key="option.unpaid" /></option>
	</select> <strong><fmt:message key="label.feedback" /></strong> <select
		name="israting" onchange="document.formSearch.submit();">
		<option value="null"
			<c:if test="${isRating == null}"> selected </c:if>><fmt:message
				key="option.all" /></option>
		<option value=true
			<c:if test="${isRating != null && isRating}"> selected </c:if>><fmt:message
				key="option.feedback_left" /></option>
		<option value=false
			<c:if test="${isRating != null && !isRating}"> selected </c:if>><fmt:message
				key="option.feedback_not_left" /></option>
	</select>


	<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">

		<input type="hidden" name="master_id" value="${sessionScope.user.id}">

	</c:if>

	<c:if test="${sessionScope.user.role == 'CLIENT'}">

		<input type="hidden" name="user_id" value="${sessionScope.user.id}">

	</c:if>


	<c:if test="${sessionScope.user.role == 'ADMIN' && user_id != null}">

		<input type="hidden" name="user_id" value="${user_id}">

	</c:if>



	<input type="hidden" name="command" value="show_appointments_list">
		
	<hr>

	<strong><fmt:message key="label.itemsperpage" /></strong> <input
		name="itemsperpage" onchange="document.formSearch.submit();"
		value="${itemsPerPage == null ? 10 : itemsPerPage }" size=2> <br>
	<input type="hidden" name="page" value="1">

</form>

<hr>
<br>
<br>



<div class="table-list">
<table class="table table-striped">

	<tr>
		<th><fmt:message key="label.date" /></th>
		<th><fmt:message key="label.time" /></th>
		<th><fmt:message key="label.master" /></th>
		<th><fmt:message key="label.client" /></th>
		<th><fmt:message key="label.service" /></th>
		<th><fmt:message key="label.sum" /></th>
		<th><fmt:message key="label.status" /></th>
		<th><fmt:message key="label.payment" /></th>
		<th><fmt:message key="label.rating" /></th>
		<th><fmt:message key="label.info" /></th>
		<th>...</th>
	</tr>

	<c:forEach items="${appointmentsList}" var="item">
		<tr>
			<td><c:out value="${item.date}" /></td>
			<td><my:timeslotdisp timeslot="${item.timeslot}"
					currentLang="${sessionScope.currentLocale}" /></td>
			<td><a href="Controller?command=show_user_info&id=${item.master.id}"><c:out value="${item.master.name} ${item.master.surname}" /></a></td>
			<td><a href="Controller?command=show_user_info&id=${item.user.id}"><c:out value="${item.user.name} ${item.user.surname}" /></a></td>
			<td><c:out value="${item.service.name}" /></td>
			<td><c:out value="${item.sum} " />
				<fmt:message key="label.hrn" /></td>
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
			<td align="center"><c:out
					value="${item.rating == 0? '-' : item.rating}"></c:out></td>
			<td><a
				href="Controller?command=show_appointment_info&master_id=${item.master.id}&date=${item.date}&timeslot=${item.timeslot}"><fmt:message
						key="link.appointment_info" /></a></td>
			<td><c:if
					test="${item.user != null && sessionScope.user.role == 'ADMIN' && !(item.isDone)}">
					<form action="Controller" method="post" data-confirm=<fmt:message key="confirmation.delete_appointment" />>
						<input type="hidden" name=master_id value="${item.master.id}" />
						<input type="hidden" name=date value="${item.date}" /> <input
							type="hidden" name=timeslot value="${item.timeslot}" /> <input
							type="hidden" name=command value="delete_appointment" /> <input
							type="submit" name="submit" value="X" />
					</form>
				</c:if></td>

		</tr>
	</c:forEach>
</table>
</div>

<br>
<div class="table-list">
<table class="table table-striped">
	<tr>
<c:if test="${page != 1}">
	<form action="Controller" method="get">
		<h:applistpageparam />
		<input type="hidden" name="page" value="${page - 1}"> <input
			type="submit" value="<<">

	</form>

</c:if>



		<c:forEach begin="1" end="${pagesTotal}" var="i">
			<c:choose>
				<c:when test="${page eq i}">
                        ${i}
                    </c:when>
				<c:otherwise>
					<form action="Controller" method="get">
						<h:applistpageparam />
						<input type="hidden" name="page" value="${i}"> <input
							type="submit" value="${i}">

					</form>
				</c:otherwise>
			</c:choose>
		</c:forEach>



<c:if test="${page lt pagesTotal}">

	<form action="Controller" method="get">
		<h:applistpageparam />
		<input type="hidden" name="page" value="${page + 1}"> <input
			type="submit" value=">>">

	</form>
</c:if>

	</tr>
</table>
</div>
<script src="${pageContext.request.contextPath}/js/form_confirmation.js"></script>
</body>
</html>
