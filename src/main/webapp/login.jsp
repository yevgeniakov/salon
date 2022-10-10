<%@page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %> 
<html>
<body>
<jsp:include page="header.jsp" />



<head>
    <title>Login Page</title>
</head>

<h3>Hello, please log in:</h3>
<br>
<form action="Controller" method=post>
    <p><strong>Login (e-mail): </strong>
    <br>
    <input type="email" name="email" size="50">
    <p><p><strong>Password: </strong>
    <br>
    <input type="password" size="20" name="password">
    <p><p>
    <input type="submit" value="Submit">
    <input type="reset" value="Reset">
    <input type="hidden" name="command" value = "login">
</form>



</body>
</html>
