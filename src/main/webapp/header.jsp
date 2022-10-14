<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<style>
table, th, td {
	border: 1px solid black;
}
</style>
<body>
<c:if test="${currentLocale == null}">
<c:set var="currentLocale" value="en" scope="session"/>
</c:if>
		<a href="index.jsp"><fmt:message key="link.homepage"/></a> |
		<c:if test="${sessionScope.user != null}">

			<a href="my_info.jsp">${sessionScope.user.name}
				${sessionScope.user.surname} — ${sessionScope.user.role}</a> |
			<a href="Controller?command=logout"><fmt:message key="link.logout"/></a>
			

		</c:if>
		<c:if test="${sessionScope.user == null}">
			<a href="login.jsp"><fmt:message key="link.login"/></a> | 
    		<a href="registration.jsp"><fmt:message key="link.registration"/></a>
		</c:if>

<form action="change_locale.jsp" name="formlocale" method="post">
		<fmt:message key="label.set_locale"/>:
		<select name="locale" onchange="document.formlocale.submit();">
			<c:forEach items="${applicationScope.locales}" var="locale">
				<c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
				<option value="${locale.key}" ${selected}>${locale.value}</option>
			</c:forEach>
		</select>
			
	</form>

	<hr>
</body>
</html>
