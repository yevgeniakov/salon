<%@ include file="/WEB-INF/include/head.jspf"%>


<html>
<body>
<jsp:include page="header.jsp" />
<head>
    <title>Best Beauty Salon</title>

<link rel="shortcut icon" href="favicon.ico"/>

</head>
<h2><fmt:message key="label.greeting"/><c:if test="${sessionScope.user != null }"><c:out value=", "></c:out><c:out value="${sessionScope.user.name}"></c:out></c:if><c:out value="!"></c:out></h2>


<br>
<a href = "Controller?command=show_master_list"><fmt:message key="menu.master_list"/></a>
<br>
<a href = "Controller?command=show_service_list"><fmt:message key="menu.service_list"/></a>
<hr>

<h2>Here is some general information</h2>


</body>
</html>
