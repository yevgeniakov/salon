<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my"%>
<%@ page isELIgnored="false"%>
<html>
      <style>
         table, th, td {
            border: 1px solid black;
         }
      </style>
<body>

		    <a href="index.jsp">Home page</a> | 
    	<c:if test="${sessionScope.user != null}">
    		
    		<a href="my_info.jsp">${sessionScope.user.name} ${sessionScope.user.surname} — ${sessionScope.user.role}</a> | 
    		<a href="Controller?command=logout">Logout</a>
    	</c:if>
    	<c:if test="${sessionScope.user == null}">
    		<a href="login.jsp">Login</a> | 
    		<a href="registration.jsp">Registration</a>
    	</c:if>
 

<hr>
</body>
</html>
