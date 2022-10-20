<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.time.LocalDate"%>

<html>

<body class="d-flex flex-column h-100">
	<jsp:include page="header.jsp" />
<head>
<link href='css/jquery.rating.css' type="text/css" rel="stylesheet" />
<title><fmt:message key="label.appointments_list" /></title>
</head>
<c:if test="${param.user_id != null}">
	<c:set var="user_id" scope="request" value="${param.user_id}" />
</c:if>

<h3 align="center">
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
		<c:set var="i" value="0"></c:set>
		<c:forEach items="${appointmentsList}" var="item">
			<tr>

				<td><c:out value="${item.date}" /></td>
				<td><my:timeslotdisp timeslot="${item.timeslot}"
						currentLang="${sessionScope.currentLocale}" /></td>
				<td><a
					href="Controller?command=show_user_info&id=${item.master.id}"><c:out
							value="${item.master.name} ${item.master.surname}" /></a></td>
				<td><a
					href="Controller?command=show_user_info&id=${item.user.id}"><c:out
							value="${item.user.name} ${item.user.surname}" /></a></td>
				<td><c:out value="${item.service.name}" /></td>
				<td><c:out value="${item.sum} " /> <fmt:message
						key="label.hrn" /></td>
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
				<td><a
					href="Controller?command=show_appointment_info&master_id=${item.master.id}&date=${item.date}&timeslot=${item.timeslot}"><fmt:message
							key="link.appointment_info" /></a></td>
				<td><c:if
						test="${item.user != null && sessionScope.user.role == 'ADMIN' && !(item.isDone)}">
						<form action="Controller" method="post"
							data-confirm=<fmt:message key="confirmation.delete_appointment" />>
							<input type="hidden" name=master_id value="${item.master.id}" />
							<input type="hidden" name=date value="${item.date}" /> <input
								type="hidden" name=timeslot value="${item.timeslot}" /> <input
								type="hidden" name=command value="delete_appointment" /> 
								<button type="submit" class="btn btn-danger text-nowrap" name="submit">X</button>
						</form>
					</c:if></td>

			</tr>
		</c:forEach>
	</table>
</div>

<br>

<c:set var="master_id_param"
		value="null"></c:set>
		<c:set var="user_id_param"
		value="null"></c:set>
<c:if test="${sessionScope.user.role != 'HAIRDRESSER'}">
	<c:set var="master_id_param"
		value="${master_id == null? 'null' : master_id}"></c:set>
</c:if>
<c:if test="${sessionScope.user.role == 'CLIENT'}">
	<c:set var="user_id_param" value="${sessionScope.user.id}"></c:set>
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN' && user_id != null}">
	<c:set var="user_id_param" value="${user_id}"></c:set>
</c:if>

<nav aria-label="Page navigation">
	<ul class="pagination">
		<li class="page-item ${page != 1? '' : 'disabled' }"><a
			class="page-link"
			href="Controller?command=show_appointments_list&datefrom=${datefrom}&dateto=${dateto}&service_id=${service_id == null? 'null' : service_id}&master_id=${master_id_param}&user_id=${user_id_param}&isdone=${isDone == null? 'null' : isDone}&ispaid=${isPaid == null? 'null' : isPaid}&israting=${isRating == null? 'null' : isRating}&itemsperpage=${itemsPerPage}&page=${page - 1}
">&laquo;</a>
		</li>
		<c:forEach begin="1" end="${pagesTotal}" var="i">
			<li class="page-item ${page eq i? 'active' : '' }"><a
				class="page-link"
				href="Controller?command=show_appointments_list&datefrom=${datefrom}&dateto=${dateto}&service_id=${service_id == null? 'null' : service_id}&master_id=${master_id_param}&user_id=${user_id_param}&isdone=${isDone == null? 'null' : isDone}&ispaid=${isPaid == null? 'null' : isPaid}&israting=${isRating == null? 'null' : isRating}&itemsperpage=${itemsPerPage}&page=${i}
">${i}</a>
			</li>
		</c:forEach>
		<li class="page-item ${page lt pagesTotal? '' : 'disabled' }"><a
			class="page-link"
			href="Controller?command=show_appointments_list&datefrom=${datefrom}&dateto=${dateto}&service_id=${service_id == null? 'null' : service_id}&master_id=${master_id_param}&user_id=${user_id_param}&isdone=${isDone == null? 'null' : isDone}&ispaid=${isPaid == null? 'null' : isPaid}&israting=${isRating == null? 'null' : isRating}&itemsperpage=${itemsPerPage}&page=${page + 1}">&raquo;</a>
		</li>
	</ul>
</nav>



<footer class="footer mt-auto py-3 bg-dark">
	<div class="container">
		<span class="text-light">Beauty Salon © 2022</span>
	</div>
</footer>
<script src="${pageContext.request.contextPath}/js/form_confirmation.js"></script>
<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js'
	type="text/javascript"></script>
<script src='js/jquery.MetaData.js' type="text/javascript"></script>
<script src='js/jquery.rating.js' type="text/javascript"></script>
</body>
</html>
