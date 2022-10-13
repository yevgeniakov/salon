<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<body>
<jsp:include page="header.jsp" />
<head>
    <title><fmt:message key="label.login_page"/></title>
</head>

<h3><fmt:message key="label.provide_your_data"/></h3>

<form action="Controller" method=post>
    <p><strong><fmt:message key="label.login"/></strong>
    <br>
    <input type="email" name="email" size="50">
    <p><p><strong><fmt:message key="label.password"/></strong>
    <br>
    <input type="password" size="20" name="password">
    <p><p>
    <input type="submit" value=<fmt:message key="button.login"/>>
    <input type="reset" value=<fmt:message key="button.reset"/>>
    <input type="hidden" name="command" value = "login">
</form>



</body>
</html>
