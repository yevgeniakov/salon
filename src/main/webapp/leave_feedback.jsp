<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>Leave feedback</title>
</head>

<h3>Please, leave feedback for your appointment:</h3>
<br>

<form action="Controller" method=post>

	<strong>Your rating: </strong> <br>
	<c:forEach var="i" begin="1" end="5">
		<input type="radio" name="rating" value="${i}" />${i} <br> 
		</c:forEach>
		<br> <input type="hidden" name="master_id" value="${param.master_id}">
		<br> <input type="hidden" name="date" value="${param.date}">
		<br> <input type="hidden" name="timeslot" value="${param.timeslot}">
		<input type="hidden" name="command" value="leave_feedback">
	

		<strong>Your feedback: </strong> <br>
		<textarea rows="10" cols="60" name="feedback">

    </textarea>
    
	<p>
	<p>
		<input type="submit" value="Leave feedback"> <input type="reset"
			value="Reset">
</form>



</body>
</html>
