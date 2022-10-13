<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<body>
<jsp:include page="header.jsp" />
<head>
    <title>Best Beauty Salon</title>
</head>
<h2><fmt:message key="label.greeting"/>${sessionScope.user.name == null? 'guest' : sessionScope.user.name}! </h2>

<a href = "create_user.jsp"><fmt:message key="menu.create_user"/></a>
<br>
<a href = "Controller?command=show_user_list"><fmt:message key="menu.user_list"/></a>
<br>
<a href = "create_service.jsp"><fmt:message key="menu.create_service"/></a>
<br>
<a href = "Controller?command=show_master_list"><fmt:message key="menu.master_list"/></a>
<br>
<a href = "Controller?command=show_service_list"><fmt:message key="menu.service_list"/></a>
<br>
<a href = "Controller?command=show_appointments_list"><fmt:message key="menu.appointments_list"/></a>




<h2>Here is some general information</h2>


</body>
</html>