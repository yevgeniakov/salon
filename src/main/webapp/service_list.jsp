<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>

<body class="d-flex flex-column h-100">
	<jsp:include page="header.jsp" />
<head>

<title><fmt:message key="label.service_list" /></title>
</head>
<h3 align="center">
	<fmt:message key="label.service_list" />
</h3>
<br>
<div class="table-list">
	<table class="table table-striped">
		<tr>
			<th><fmt:message key="label.service_name" /></th>
			<th><fmt:message key="label.info" /></th>
			<th><fmt:message key="label.masters" /></th>
		</tr>
		<c:forEach items="${servicelist}" var="item">
			<tr>
				<td><c:out value="${item.name}" /></td>
				<td><c:out value="${item.info}" /></td>
				<td><a
					href="Controller?command=show_masters_of_service&service_id=${item.id}"><fmt:message
							key="link.show_masters" /></a></td>
			</tr>
		</c:forEach>
	</table>
</div>

<footer class="footer mt-auto py-3 bg-dark">
	<div class="container">
		<span class="text-light"><fmt:message key="label.footer" /></span>
	</div>
</footer>
</body>
</html>
