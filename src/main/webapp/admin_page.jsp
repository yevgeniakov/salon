<%@page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %> 



<html>
<body>
<jsp:include page="header.jsp" />
<head>
    <title>Best Beauty Salon</title>
</head>
<h2>Welcome to our best beauty salon, ${sessionScope.user.name == null? 'guest' : sessionScope.user.name}! </h2>

<a href = "create_user.jsp"> Create user </a>
<br>
<a href = "Controller?command=show_user_list"> User List </a>
<br>
<a href = "create_service.jsp"> Create service </a>
<br>
<a href = "Controller?command=show_master_list"> Master List </a>
<br>
<a href = "Controller?command=show_service_list"> Service List </a>
<br>
<a href = "Controller?command=show_appointments_list"> Appointments list </a>




<h2>Here is some general information</h2>


</body>
</html>