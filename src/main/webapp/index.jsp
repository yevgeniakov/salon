<%@page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %> 



<html>
<body>
<jsp:include page="header.jsp" />
<head>
    <title>Best Beauty Salon</title>
</head>
<h2>Welcome to our best beauty salon, ${sessionScope.user.name == null? 'guest' : sessionScope.user.name}! </h2>


<br>
<a href = "Controller?command=show_master_list"> Master List </a>
<br>
<a href = "Controller?command=show_service_list"> Service List </a>
<hr>

<h2>Here is some general information</h2>


</body>
</html>
