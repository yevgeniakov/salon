<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>

<body class="d-flex flex-column h-100">
<jsp:include page="header.jsp" />
<head>
    <link href="css/registration.css" rel="stylesheet">
<c:set var="newDate" scope="request" value="${param.newDate == null ? param.date : param.newDate}"/>
<script type = "text/javascript">

            function Redirect(newDate) {
            	const queryString = window.location.search;
            	const urlParams = new URLSearchParams(queryString);
               window.location = "change_appointment.jsp?date=" + urlParams.get('date') + "&master_id=" + urlParams.get('master_id') + "&timeslot=" + urlParams.get('timeslot') + "&newDate=" + newDate;
            }
         
    </script>
<title><fmt:message key="label.change_appointment_time"/></title>
</head>

<div class="change-appointment">
<h3><fmt:message key="label.select_date_time"/></h3>

<br>
<form action="Controller" method="post">
<strong><fmt:message key="label.date"/></strong>

	<br> <input class="form-control" onchange='Redirect(this.value)' type="date" name="newdate" value="${newDate == null ? appointment.date : newDate}" />
	
	<my:getfreeslots master_id="${param.master_id}" date="${newDate}"/>
	
	<strong><fmt:message key="label.time"/></strong> 
	<br>
	
	<select class="form-select" name="newtimeslot">
		
		<c:forEach items="${freetimeslots}" var="item">
			<option value="${item}"><my:timeslotdisp timeslot = "${item}" currentLang="${sessionScope.currentLocale}"/></option>
		</c:forEach>
	</select> 
	<br>
	<input type="hidden" name="master_id" value="${param.master_id}">
	<input type="hidden" name="date" value="${param.date}">
	<input type="hidden" name="timeslot" value="${param.timeslot}">
	<input type="hidden" name=command value="set_time_appointment">

<button class= "btn btn-primary text-nowrap" type="submit" name="submit"><fmt:message key="button.set_new_time"/></button>

</form>
</div>
    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>

</body>
</html>
