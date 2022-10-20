<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.time.LocalDate"%>
<html>

<body class="d-flex flex-column h-100">
<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.master_list"/></title>
</head>

<div class="sort-controller">
<c:if test="${param.service_id != null}">
<c:set var="user_id" scope="request" value="${param.service_id}"/>
</c:if>

<br>
<strong><fmt:message key="label.sortby"/></strong>
<form action="Controller" name="formSearch" method="get">
<select name="sort" class="form-select" onchange="document.formSearch.submit();">
		<option value="surname" <c:if test="${sort == 'surname' || sort == null}"> selected </c:if>><fmt:message key="option.surname"/></option>
		<option value="rating" <c:if test="${sort == 'rating'}"> selected </c:if>><fmt:message key="option.rating"/></option>
		
	</select>
	<input type="hidden" name="service_id" value="${service_id}">
	<input type="hidden" name="command" value="${service_id == null? 'show_master_list' : 'show_masters_of_service' }">
</form>
</div>


<div class="table-list">
<br>
<c:if test="${service == null}">
<h3 align="center"><fmt:message key="label.master_list"/></h3>

	<table class="table table-striped">
<tr>
<th><fmt:message key="label.name"/></th><th><fmt:message key="label.tel"/></th><th><fmt:message key="label.short_info"/></th><th><fmt:message key="label.rating"/></th><c:if test="${sessionScope.user != null}"><th><fmt:message key="label.view_schedule"/></th></c:if>
</tr>
		<c:forEach items="${userlist}" var="item">
			<tr>
				<td><a href="Controller?command=show_user_info&id=${item.id}"><c:out value="${item.name} ${item.surname}" /></a></td>
				<td><c:out value="${item.tel}" /></td>
				<td><c:out value="${item.info}" /></td>
				<td><c:out value="${item.rating}" /></td>
				<c:if test="${sessionScope.user != null}">
					<td><a
						href="Controller?command=show_master_schedule&id=${item.id}&date=${LocalDate.now().plusDays(1)}">
							<fmt:message key="link.view_schedule"/> </a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</c:if>

<c:if test="${service != null}">

	<h3 align="center"><fmt:message key="label.masters_who"/> ${service.name} </h3>
	<br>
	<table class="table table-striped">
<tr>

<th><fmt:message key="label.name"/></th><th><fmt:message key="label.tel"/></th><th><fmt:message key="label.short_info"/></th><th><fmt:message key="label.rating"/></th><th><fmt:message key="label.price"/></th><c:if test="${sessionScope.user != null}"><th><fmt:message key="label.view_schedule"/></th></c:if>

</tr>	

		<c:forEach items="${masters}" var="entry">
			<tr>
				<td><a href="Controller?command=show_user_info&id=${entry.key.id}"><c:out value="${entry.key.name} ${entry.key.surname}" /></a></td>
				<td><c:out value="${entry.key.tel}" /></td>
				<td><c:out value="${entry.key.info}" /></td>
				<td><c:out value="${entry.key.rating}" /></td>
				<td><c:out value="${entry.value} hrn." /></td>

				<c:if test="${sessionScope.user != null}">
					<td><a
						href="Controller?command=show_master_schedule&id=${entry.key.id}&date=${LocalDate.now().plusDays(1)}">
							<fmt:message key="label.view_schedule"/> </a></td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</c:if>

</div>

    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>

</body>
</html>
