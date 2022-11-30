<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.time.LocalDate"%>
<html>

<body class="d-flex flex-column h-100">
	<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.appointment_info" /></title>
    <link href='css/jquery.rating.css' type="text/css" rel="stylesheet" />
    <link href="css/forms.css" rel="stylesheet">
</head>
<div class="appointment-info">
<h3>
	<fmt:message key="label.appointment_info" />
</h3>
<br>
<div class="card text-center" style="width: 18rem;">
  <div class="card-body">
<form action="change_appointment.jsp" method="get">
	<strong><fmt:message key="label.date" /></strong><br>

	<c:out value="${appointment.date}"></c:out>
	<br> <br> <strong><fmt:message key="label.time" /></strong><br>

	<my:timeslotdisp timeslot="${appointment.timeslot}"
		currentLang="${sessionScope.currentLocale}" />

	<br>
	<h:appkeyinputs />
	<c:if
		test="${sessionScope.user.role == 'ADMIN' && !appointment.isDone}">
		<br>
				<button class="w-70 btn btn-primary btn-sm text-nowrap" type="submit">
				<fmt:message key="button.change_time" />
			</button>
	</c:if>

</form>
</div>
</div>



<c:if test="${sessionScope.user.id == appointment.master.id}">
<div class="card text-center" style="width: 18rem;">
  <div class="card-body">
<strong><fmt:message key="label.master" /> </strong>
	<a href="my_info.jsp">${appointment.master.name}
		${appointment.master.surname}</a>
		</div>
		</div>
</c:if>

<c:if test="${sessionScope.user.id != appointment.master.id}">
<div class="card text-center" style="width: 18rem;">

  <div class="card-body">
<strong><fmt:message key="label.master" /> </strong>
	<a href="Controller?command=show_user_info&id=${appointment.master.id}">${appointment.master.name}
		${appointment.master.surname}</a>
		</div>
		</div>
</c:if>

<c:if test="${sessionScope.user.id == appointment.user.id}">
<div class="card text-center" style="width: 18rem;">
  <div class="card-body">
<strong><fmt:message key="label.client" /></strong>
	<a href="my_info.jsp">${appointment.user.name}
		${appointment.user.surname}</a>
		</div>
		</div>
</c:if>
<c:if test="${sessionScope.user.id != appointment.user.id}">
<div class="card text-center" style="width: 18rem;">
  <div class="card-body">
<strong><fmt:message key="label.client" /></strong>
	<a href="Controller?command=show_user_info&id=${appointment.user.id}">${appointment.user.name}
		${appointment.user.surname} </a>
		</div>
		</div>
</c:if>
<div class="card text-center" style="width: 18rem;">
  <div class="card-body">
<strong><fmt:message key="label.service" /></strong>
<c:out value="${appointment.service.name}"></c:out>
</div>
</div>
<div class="card text-center" style="width: 18rem;">
<h5 class="card-title">
<fmt:message key="label.sum" /></h5>
  <div class="card-body">
<c:out value="${appointment.sum} " /> <fmt:message key="label.hrn" />

</div>
</div>
<fmt:message key="label.complete" var="lcomplete" />
<fmt:message key="label.incomplete" var="lincomplete" />
<div class="card text-center" style="width: 18rem;">
<c:out value="${appointment.isDone? lcomplete : lincomplete}"></c:out>
<br>

<c:if test="${sessionScope.user.id == appointment.master.id}">
<fmt:message key="confirmation.complete_appointment" var="conf_complete" />
		<fmt:message key="confirmation.incomplete_appointment" var="conf_incomplete" />
	<form action="Controller" method="post" data-confirm="${appointment.isDone? conf_incomplete : conf_complete}">
		<h:appkeyinputs />
		<input type="hidden" name="command" value="set_complete_appointment">
		<fmt:message key="button.set_complete" var="bcomplete" />
		<fmt:message key="button.set_incomplete" var="bincomplete" />
		<button class= "${appointment.isDone? 'btn btn-danger' : 'btn btn-success'} btn-sm text-nowrap" type="submit" name="submit">${appointment.isDone? bincomplete : bcomplete}</button>

	</form>

</c:if>
</div>
<fmt:message key="label.paid" var="lpaid" />
<fmt:message key="label.unpaid" var="lunpaid" />
<div class="card text-center" style="width: 18rem;">
<c:out value="${appointment.isPaid? lpaid : lunpaid}"></c:out>
<br>
<c:if test="${sessionScope.user.role == 'ADMIN'}">
		<fmt:message key="confirmation.pay_appointment" var="conf_paid" />
		<fmt:message key="confirmation.unpay_appointment" var="conf_unpaid" />
	<form action="Controller" method="post" data-confirm="${appointment.isPaid? conf_unpaid : conf_paid}">
		<h:appkeyinputs />
		<input type="hidden" name="command" value="set_pay_appointment">
		<fmt:message key="button.set_paid" var="bpaid" />
		<fmt:message key="button.set_unpaid" var="bunpaid" />
		<button class= "${appointment.isPaid? 'btn btn-danger' : 'btn btn-success'} btn-sm text-nowrap" type="submit" name="submit">${appointment.isPaid? bunpaid : bpaid}</button>
	</form>

</c:if>
</div>


<c:if test="${appointment.rating != 0}">
	<strong><fmt:message key="label.rating" /></strong>
	<br>
	<h:createstars rating="${appointment.rating}" inputname="rat1"/>
	<br>
	<br>
	<strong><fmt:message key="label.feedback" /></strong>
	<c:out value="${appointment.feedback}"></c:out><br>
</c:if>


<c:if
	test="${sessionScope.user.id == appointment.user.id && appointment.isDone}">
	<form action="leave_feedback.jsp" method="get">
	<br>
		<h:appkeyinputs />
		<fmt:message key="button.leave_feedback" var="bleavef" />
		<fmt:message key="button.change_feedback" var="bchangef" />
		
		<button class= "${appointment.rating == 0 ? 'btn btn-primary' : 'btn btn-warning'} text-nowrap" type="submit" name="submit">${appointment.rating == 0 ? bleavef : bchangef}</button>

	</form>
</c:if>
<c:if
	test="${sessionScope.user.role == 'ADMIN' && !(appointment.isDone)}">
	<br>
	<form action="Controller" method="post" data-confirm=<fmt:message key="confirmation.delete_appointment" />>
		<h:appkeyinputs />
		<input type="hidden" name=command value="delete_appointment" /> 
		
		<button type="submit" class="btn btn-danger text-nowrap" name="submit"><fmt:message key="button.delete_appointment"/></button>
			</form>
</c:if>
</div>
    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light"><fmt:message key="label.footer" /></span>
      </div>
    </footer>
<script src="${pageContext.request.contextPath}/js/form_confirmation.js"></script>
<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js'
	type="text/javascript"></script>
<script src='js/jquery.MetaData.js' type="text/javascript"
	></script>
<script src='js/jquery.rating.js' type="text/javascript"
	></script>
</body>
</html>
