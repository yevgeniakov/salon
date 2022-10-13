<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
      <style>
         table, th, td {
            border: 1px solid black;
         }
      </style>
<body>
<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.leave_feedback"/></title>
</head>

<h3><fmt:message key="label.provide_feedback"/></h3>
<br>

<form action="Controller" method=post>

	<strong><fmt:message key="label.your_rating"/></strong> <br>
	<c:forEach var="i" begin="1" end="5">
		<input type="radio" name="rating" value="${i}" />${i} <br> 
		</c:forEach>
		<br> <input type="hidden" name="master_id" value="${param.master_id}">
		<br> <input type="hidden" name="date" value="${param.date}">
		<br> <input type="hidden" name="timeslot" value="${param.timeslot}">
		<input type="hidden" name="command" value="leave_feedback">
	

		<strong><fmt:message key="label.your_feedback"/></strong> <br>
		<textarea rows="10" cols="60" name="feedback" ></textarea>
    
	<p>
	<p>
		<input type="submit" value=<fmt:message key="button.set_feedback"/>> <input type="reset"
			value=<fmt:message key="button.reset"/>>
</form>



</body>
</html>
