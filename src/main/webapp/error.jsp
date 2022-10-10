<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %> 
<html>
<body>
<jsp:include page="header.jsp" />
<head>
<title>Error</title>
</head>

<h3>Something went wrong :(</h3>
<br> <h4>${error}</h4>
<br>

</body>
</html>
