<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<body>
<jsp:include page="header.jsp" />
<head>
    <title>Best Beauty Salon</title>
</head>
<h2><fmt:message key="label.greeting"/>${sessionScope.user.name == null? 'guest' : sessionScope.user.name}! </h2>

<br>
<a href = "Controller?command=show_master_list"><fmt:message key="menu.master_list"/></a>
<br>
<a href = "Controller?command=show_service_list"><fmt:message key="menu.service_list"/></a>
<br>
<a href = "Controller?command=show_appointments_list"><fmt:message key="menu.my_appointments"/></a>
<h2>Here is some general information</h2>


</body>
</html>
