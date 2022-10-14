<%@ include file="/WEB-INF/include/head.jspf"%>

<%-- set the locale --%>
<fmt:setLocale value="${param.locale}" scope="session"/>

<%-- load the bundle (by locale) --%>
<fmt:setBundle basename="resources"/>

<%-- set current locale to session --%>
<c:set var="currentLocale" value="${param.locale}" scope="session"/>

<%-- goto back to the settings--%>
<c:if test="${sessionScope.user == null }">
<jsp:forward page="index.jsp"/>
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN' }">
<jsp:forward page="admin_page.jsp"/>
</c:if>
<c:if test="${sessionScope.user.role == 'HAIRDRESSER' }">
<jsp:forward page="master_page.jsp"/>
</c:if>
<c:if test="${sessionScope.user.role == 'CLIENT' }">
<jsp:forward page="client_page.jsp"/>
</c:if>