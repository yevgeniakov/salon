<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my"%>
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
<title>User info</title>
</head>

<h3>Appointment info </h3>
<br>
<form action="change_appointment.jsp" method="get">
	<strong>Date:</strong><br>
	
	<c:out value="${appointment.date}"></c:out>
	<br> <br>

	<strong>Time:</strong><br>
	
	<my:timeslotdisp timeslot="${appointment.timeslot}" currentLang="${sessionScope.user.currentLang}"/>
	
	<br>
	<h:appkeyinputs/>
	<c:if test="${sessionScope.user.role == 'ADMIN' && !appointment.isDone}">
	<input type="submit" name="submit" value="Change time">
	</c:if>
	
	</form>
	<strong>Master:</strong>
	<br>
	<c:if test="${sessionScope.user.id == appointment.master.id}">
	<a href="my_info.jsp" >${appointment.master.name} ${appointment.master.surname}</a>
	</c:if>
	<c:if test="${sessionScope.user.id != appointment.master.id}">
	<a href="Controller?command=show_user_info&id=${appointment.master.id}">${appointment.master.name} ${appointment.master.surname}</a>
	</c:if>

<br><br>
	<strong>User:</strong>
	<br>
	<c:if test="${sessionScope.user.id == appointment.user.id}">
	<a href="my_info.jsp" >${appointment.user.name} ${appointment.user.surname}</a>
	</c:if>
	<c:if test="${sessionScope.user.id != appointment.user.id}">
	<a href="Controller?command=show_user_info&id=${appointment.user.id}">${appointment.user.name} ${appointment.user.surname} </a>
	</c:if>
<br><br>
	<strong>Service:</strong>
	<br>
<c:out value="${appointment.service.name}"></c:out>
<br><br>

	<strong>Sum:</strong>
	<br>
	<c:out value="${appointment.sum} hrn."></c:out>
<br><br>
	
	<c:out value="${appointment.isDone? 'Complete' : 'Incomplete'}"></c:out>
	<br>
	
	<c:if test="${sessionScope.user.id == appointment.master.id}">
	<form action = "Controller" method = "post">
	<h:appkeyinputs/>
	<input type="hidden" name="command" value="set_complete_appointment">
	<input type="submit" name="submit" value="${appointment.isDone? 'Set incomplete' : 'Set complete'}">
	</form>
	<br><br>
	</c:if>
	
	
	<c:out value="${appointment.isPaid? 'Paid' : 'Unpaid'}"></c:out>
	<br>
	<c:if test="${sessionScope.user.role == 'ADMIN'}">
	<form action = "Controller" method = "post">
	<h:appkeyinputs/>
	<input type="hidden" name="command" value="set_pay_appointment">
	<input type="submit" name="submit" value="${appointment.isPaid? 'Set unpaid' : 'Set paid'}">
	</form>
	<br><br>
	</c:if>
	
	
	
	<c:if test="${appointment.rating != 0}">
	<strong>Rating: </strong>
	<c:out value="${appointment.rating}"></c:out>
	<br><br>
	<strong>Feedback:</strong>
	<c:out value="${appointment.feedback}"></c:out>
	</c:if>
	<br><br>
	<c:if test="${sessionScope.user.id == appointment.user.id && appointment.isDone}">
	<form action = "leave_feedback.jsp" method = "get">
	<h:appkeyinputs/>
	<input type="submit" name="submit" value="${appointment.rating == 0 ? 'Leave feedback' : 'Change feedback' }">
	</form>
	</c:if>
	<c:if test="${sessionScope.user.role == 'ADMIN' && !(appointment.isDone)}">
					<form action="Controller" method="post">
						<h:appkeyinputs/>
						<input type="hidden" name=command value="delete_appointment" /> 
						<input type="submit" name="submit" value="Delete appiontment" />
					</form>
				</c:if>
</body>
</html>
