<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>

<body class="d-flex flex-column h-100">
	<jsp:include page="header.jsp" />
<head>

<link href='css/jquery.rating.css' type="text/css" rel="stylesheet" />
<link href="css/registration.css" rel="stylesheet">
<title><fmt:message key="label.leave_feedback" /></title>
</head>
<div class="leave-feedback">
<h3>
	<fmt:message key="label.provide_feedback" />
</h3>
<br>

<form action="Controller" method=post id="feedback_form">

	<strong><fmt:message key="label.your_rating" /></strong> <br>
	<c:forEach var="i" begin="1" end="5">

		<input type="radio" class="star" name="rating" id="rating" value="${i}" />
	</c:forEach>
	<input type="hidden" name="master_id" value="${param.master_id}">
	<input type="hidden" name="date" value="${param.date}"> <input
		type="hidden" name="timeslot" value="${param.timeslot}"> <input
		type="hidden" name="command" value="leave_feedback"><br>


	<strong><fmt:message key="label.your_feedback" /></strong> <br>
	<textarea class="form-control" rows="10" cols="60" name="feedback"></textarea>
<br>
	<button class="w-70 btn btn-lg btn-primary text-nowrap" type="submit">
		<fmt:message key="button.set_feedback" />
	</button>

</form>
</div>
    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>
    
<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js'
	type="text/javascript"></script>
<script src='js/jquery.MetaData.js' type="text/javascript"
	></script>
<script src='js/jquery.rating.js' type="text/javascript"
	></script>
	<script src='js/leave_feedback.js' type="text/javascript"
	></script>
</body>
</html>
