<%@ include file="/WEB-INF/include/head.jspf"%>

<%-- set the locale --%>
<fmt:setLocale value="${param.locale}" scope="session"/>

<%-- load the bundle (by locale) --%>
<fmt:setBundle basename="resources"/>

<%-- set current locale to session --%>
<c:set var="currentLocale" value="${param.locale}" scope="session"/>

<%-- goto back to the settings--%>
<c:if test="${sessionScope.user == null }">
<c:redirect url="index.jsp"/>

</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN' }">
<c:redirect url="admin_page.jsp"/>
</c:if>
<c:if test="${sessionScope.user.role == 'HAIRDRESSER' }">
<c:redirect url="master_page.jsp"/>
</c:if>
<c:if test="${sessionScope.user.role == 'CLIENT' }">
<c:redirect url="client_page.jsp"/>

</c:if>