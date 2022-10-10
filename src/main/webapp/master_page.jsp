<%@page contentType="text/html; charset=UTF-8"%>
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
<title>Best Beauty Salon</title>
</head>
<h2>Welcome to our best beauty salon, ${sessionScope.user.name == null? 'guest' : sessionScope.user.name}!
</h2>

<br>
<a href = "Controller?command=show_appointments_list"> My appointments </a>
<br>
<a href="Controller?command=show_master_schedule&id=${sessionScope.user.id}&date=${LocalDate.now()}">
	View schedule </a>


<h2>Here is some general information</h2>


</body>
</html>
