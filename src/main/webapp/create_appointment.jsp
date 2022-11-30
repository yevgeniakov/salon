<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>

<body class="d-flex flex-column h-100">
<jsp:include page="header.jsp" />
<head>
<link href="css/forms.css" rel="stylesheet">
<title><fmt:message key="label.create_appointment"/></title>
</head>

<div class="create-appointment">
<h3><fmt:message key="label.create_appointment_provide"/></h3>

<br>
<form action="Controller" method=post>

		<strong><fmt:message key="label.name"/></strong> <br> <input class="form-control" name="name"
			value="${sessionScope.user.name}" size="50" disabled>

		<strong><fmt:message key="label.surname"/></strong> <br> <input class="form-control" size="50" name="surname"
			value="${sessionScope.user.surname}" disabled> <br>

	
	<my:getservices master_id = "${param.master_id }" />
		<strong><fmt:message key="label.service"/></strong> <br> 
		<select class="form-select" name="service_id">
			<c:forEach items="${servicemap}" var="entry">
				<option value="${entry.key.id}">${entry.key.name} - ${entry.value} <fmt:message key="label.hrn"/></option>
			</c:forEach>
		</select> <br> 
		<input type="hidden" name="user_id" value="${sessionScope.user.id}">
		<input type="hidden" name="date" value="${param.date}">
		<input type="hidden" name="master_id" value="${param.master_id}">
		<input type="hidden" name="timeslot" value="${param.timeslot}">
			<input type="hidden" name="command" value="create_appointment">
		<button type="submit" class="btn btn-primary text-nowrap" name="submit"><fmt:message key="button.create_appointment"/></button>
		
</form>
</div>

    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light"><fmt:message key="label.footer" /></span>
      </div>
    </footer>
</body>
</html>
