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
<title><fmt:message key="label.appointment_info"/></title>
</head>

<h3><fmt:message key="label.appointment_info"/></h3>
<br>
<form action="change_appointment.jsp" method="get">
	<strong><fmt:message key="label.date"/></strong><br>
	
	<c:out value="${appointment.date}"></c:out>
	<br> <br>

	<strong><fmt:message key="label.time"/></strong><br>
	
	<my:timeslotdisp timeslot="${appointment.timeslot}" currentLang="${sessionScope.user.currentLang}"/>
	
	<br>
	<h:appkeyinputs/>
	<c:if test="${sessionScope.user.role == 'ADMIN' && !appointment.isDone}">
	<input type="submit" name="submit" value=<fmt:message key="button.change_time"/>>
	</c:if>
	
	</form>
	<strong><fmt:message key="label.master"/></strong>
	<br>
	<c:if test="${sessionScope.user.id == appointment.master.id}">
	<a href="my_info.jsp" >${appointment.master.name} ${appointment.master.surname}</a>
	</c:if>
	<c:if test="${sessionScope.user.id != appointment.master.id}">
	<a href="Controller?command=show_user_info&id=${appointment.master.id}">${appointment.master.name} ${appointment.master.surname}</a>
	</c:if>

<br><br>
	<strong><fmt:message key="label.client"/></strong>
	<br>
	<c:if test="${sessionScope.user.id == appointment.user.id}">
	<a href="my_info.jsp" >${appointment.user.name} ${appointment.user.surname}</a>
	</c:if>
	<c:if test="${sessionScope.user.id != appointment.user.id}">
	<a href="Controller?command=show_user_info&id=${appointment.user.id}">${appointment.user.name} ${appointment.user.surname} </a>
	</c:if>
<br><br>
	<strong><fmt:message key="label.service"/></strong>
	<br>
<c:out value="${appointment.service.name}"></c:out>
<br><br>

	<strong><fmt:message key="label.sum"/></strong>
	<br>
	<c:out value="${appointment.sum} hrn."></c:out>
<br><br>
	<fmt:message key="label.complete" var="lcomplete" />
	<fmt:message key="label.incomplete" var="lincomplete" />
	<c:out value="${appointment.isDone? lcomplete : lincomplete}"></c:out>
	<br>
	
	<c:if test="${sessionScope.user.id == appointment.master.id}">
	<form action = "Controller" method = "post">
	<h:appkeyinputs/>
	<input type="hidden" name="command" value="set_complete_appointment">
	<fmt:message key="button.set_complete" var="bcomplete" />
	<fmt:message key="button.set_incomplete" var="bincomplete" />
	<input type="submit" name="submit" value="${appointment.isDone? bincomplete : bcomplete}">
	</form>
	<br><br>
	</c:if>
	
	<fmt:message key="label.paid" var="lpaid" />
	<fmt:message key="label.unpaid" var="lunpaid" />	
	<c:out value="${appointment.isPaid? lpaid : lunpaid}"></c:out>
	<br>
	<c:if test="${sessionScope.user.role == 'ADMIN'}">
	<form action = "Controller" method = "post">
	<h:appkeyinputs/>
	<input type="hidden" name="command" value="set_pay_appointment">
	<fmt:message key="button.set_paid" var="bpaid" />
	<fmt:message key="button.set_unpaid" var="bunpaid" />
	<input type="submit" name="submit" value="${appointment.isPaid? bunpaid : bpaid}">
	</form>
	<br><br>
	</c:if>
	
	
	
	<c:if test="${appointment.rating != 0}">
	<strong><fmt:message key="label.rating"/></strong>
	<c:out value="${appointment.rating}"></c:out>
	<br><br>
	<strong><fmt:message key="label.feedback"/></strong>
	<c:out value="${appointment.feedback}"></c:out>
	</c:if>
	<br><br>
	<c:if test="${sessionScope.user.id == appointment.user.id && appointment.isDone}">
	<form action = "leave_feedback.jsp" method = "get">
	<h:appkeyinputs/>
	<fmt:message key="button.leave_feedback" var="bleavef" />
	<fmt:message key="button.change_feedback" var="bchangef" />
	<input type="submit" name="submit" value="${appointment.rating == 0 ? bleavef : bchangef }">
	</form>
	</c:if>
	<c:if test="${sessionScope.user.role == 'ADMIN' && !(appointment.isDone)}">
					<form action="Controller" method="post">
						<h:appkeyinputs/>
						<input type="hidden" name=command value="delete_appointment" /> 
						<input type="submit" name="submit" value=<fmt:message key="button.delete_appointment"/> />
					</form>
				</c:if>
</body>
</html>
