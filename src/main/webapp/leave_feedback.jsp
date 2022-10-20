<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<style>
table, th, td {
	border: 1px solid black;
}
</style>
<body>
	<jsp:include page="header.jsp" />
<head>

<link href='css/jquery.rating.css' type="text/css" rel="stylesheet" />
<title><fmt:message key="label.leave_feedback" /></title>
</head>

<h3>
	<fmt:message key="label.provide_feedback" />
</h3>
<br>

<form action="Controller" method=post>

	<strong><fmt:message key="label.your_rating" /></strong> <br>
	<c:forEach var="i" begin="1" end="5">

		<input type="radio" class="star" name="rating" value="${i}" />
	</c:forEach>
	<input type="hidden" name="master_id" value="${param.master_id}">
	<input type="hidden" name="date" value="${param.date}"> <input
		type="hidden" name="timeslot" value="${param.timeslot}"> <input
		type="hidden" name="command" value="leave_feedback"><br>


	<strong><fmt:message key="label.your_feedback" /></strong> <br>
	<textarea rows="10" cols="60" name="feedback"></textarea>

	<p>
	<p>
		<input type="submit" value=<fmt:message key="button.set_feedback"/>>
		<input type="reset" value=<fmt:message key="button.reset"/>>
</form>


<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js'
	type="text/javascript"></script>
<script src='js/jquery.MetaData.js' type="text/javascript"
	></script>
<script src='js/jquery.rating.js' type="text/javascript"
	></script>
</body>
</html>
