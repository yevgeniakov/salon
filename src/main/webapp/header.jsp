<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my"%>
<%@ page isELIgnored="false"%>
<html>

<link rel="stylesheet" href="css/my.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

<body>
	<div id="header">
		<a href="index.jsp" img src="images/home.png" class="home" >Home page</a>
<i class="flag flag-us"></i>
	<div id="enter">
		<c:if test="${sessionScope.user != null}">

			<a href="my_info.jsp">${sessionScope.user.name}
				${sessionScope.user.surname} — ${sessionScope.user.role}</a> |
			<a href="Controller?command=logout">Logout</a>
			

		</c:if>
		<c:if test="${sessionScope.user == null}">
			<a href="login.jsp">Login</a> | 
    		<a href="registration.jsp">Registration</a>
		</c:if>
	</div>
		
	</div>

	<hr>
</body>
</html>
