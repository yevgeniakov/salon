<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my" %> 
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

<c:set var="newDate" scope="request" value="${param.newDate == null ? param.date : param.newDate}"/>
<script type = "text/javascript">

            function Redirect(newDate) {
            	const queryString = window.location.search;
            	const urlParams = new URLSearchParams(queryString);
               window.location = "change_appointment.jsp?date=" + urlParams.get('date') + "&master_id=" + urlParams.get('master_id') + "&timeslot=" + urlParams.get('timeslot') + "&newDate=" + newDate;
            }
         
    </script>
<title>Change Appointment Time</title>
</head>

<hr>
<h3>Please, select free date and time:</h3>

<br>
<form action="Controller" method="post">
<strong>Date:</strong>

	<br> <input onchange='Redirect(this.value)' type="date" name="newdate" value="${newDate == null ? appointment.date : newDate}" />
	<br><br>
 
	
	<my:getfreeslots master_id="${param.master_id}" date="${newDate}"/>
	
	<strong>Time: </strong> 
	<br>
	
	<select name="newtimeslot">
		
		<c:forEach items="${freetimeslots}" var="item">
			<option value="${item}"><my:timeslotdisp timeslot = "${item}" currentLang="${sessionScope.user.currentLang}"/></option>
		</c:forEach>
	</select> 
	<input type="hidden" name="master_id" value="${param.master_id}">
	<input type="hidden" name="date" value="${param.date}">
	<input type="hidden" name="timeslot" value="${param.timeslot}">
	<input type="hidden" name=command value="set_time_appointment">
	<br><br>
	<input type="submit" name="submit" value="Set new time">
</form>



</body>
</html>
