<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<style>
table, th, td {
	border: 1px solid black;
}
</style>
<body>
	<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.user_list" /></title>
</head>

<h3>
	<fmt:message key="label.user_list" />
</h3>

<br>
<strong><fmt:message key="label.sortby" /></strong>
<form action="Controller" name="formSearch" method="get">
	<select name="sort" onchange="document.formSearch.submit();">
		<option value="id"
			<c:if test="${sort == 'id' || sort == null}"> selected </c:if>><fmt:message
				key="label.id" /></option>
		<option value="surname"
			<c:if test="${sort == 'surname'}"> selected </c:if>><fmt:message
				key="option.surname" /></option>


	</select> <select name="sortorder" onchange="document.formSearch.submit();">
		<option value="asc"
			<c:if test="${sortorder == 'asc' || sortorder == null}"> selected </c:if>><fmt:message
				key="option.ascending" /></option>
		<option value="desc"
			<c:if test="${sortorder == 'desc'}"> selected </c:if>><fmt:message
				key="option.descending" /></option>


	</select> 
	
	<strong><fmt:message key="label.role" /></strong>
	<select name="role" onchange="document.formSearch.submit();">
		<option value="null"
			<c:if test="${role == null}"> selected </c:if>><fmt:message
				key="option.all" /></option>
		<option value=CLIENT
			<c:if test="${role != null && role == 'CLIENT'}"> selected </c:if>><fmt:message
				key="option.roleclient" /></option>
		<option value=HAIRDRESSER
			<c:if test="${role != null && role == 'HAIRDRESSER'}"> selected </c:if>><fmt:message
				key="option.rolehairdresser" /></option>

	</select>
	
	<strong><fmt:message key="label.status" /></strong> <select
		name="isblocked" onchange="document.formSearch.submit();">
		<option value="null"
			<c:if test="${isBlocked == null}"> selected </c:if>><fmt:message
				key="option.all" /></option>
		<option value=true
			<c:if test="${isBlocked != null && isBlocked}"> selected </c:if>><fmt:message
				key="option.blocked" /></option>
		<option value=false
			<c:if test="${isBlocked != null && !isBlocked}"> selected </c:if>><fmt:message
				key="option.active" /></option>
	</select> <strong><fmt:message key="label.searchbyvalue" /></strong> <input
		name="searchvalue" onchange="document.formSearch.submit();" value="${searchValue == null ? '': searchValue }">
	
	<hr>
	<strong><fmt:message key="label.itemsperpage" /></strong> <input
		name="itemsperpage" onchange="document.formSearch.submit();"
		value="${itemsPerPage == null ? 10 : itemsPerPage }" size=2> <br>
	<input type="hidden" name="page" value="1"> <input
		type="hidden" name="command" value="show_user_list">

</form>
<table>
	<tr>
		<th><fmt:message key="label.id" /></th>
		<th><fmt:message key="label.name" /></th>
		<th><fmt:message key="label.email" /></th>
		<th><fmt:message key="label.tel" /></th>
		<th><fmt:message key="label.role" /></th>
		<th><fmt:message key="label.status" /></th>

	</tr>

	<c:forEach items="${userlist}" var="item">
		<tr>
			<td><c:out value="${item.id}" /></td>
			<td><a href="Controller?command=show_user_info&id=${item.id}"><c:out value="${item.name} ${item.surname}" /></a></td>
			<td><c:out value="${item.email}" /></td>
			<td><c:out value="${item.tel}" /></td>
			<td><c:out value="${item.role}" /></td>
			<td><c:out value="${item.isBlocked? 'BLOCKED' : 'ACTIVE'}" /></td>

		</tr>
	</c:forEach>
</table>

<br>

<table border="1">
	<tr>
		<c:if test="${page != 1}">
			<form action="Controller" method="get">
				<h:userlistpageparam />
				<input type="hidden" name="page" value="${page - 1}"> <input
					type="submit" value="<<">

			</form>


		</c:if>

		<c:forEach begin="1" end="${pagesTotal}" var="i">
			<c:choose>
				<c:when test="${page eq i}">
                        ${i}
                    </c:when>
				<c:otherwise>
					<form action="Controller" method="get">
						<h:userlistpageparam />
						<input type="hidden" name="page" value="${i}"> <input
							type="submit" value="${i}">

					</form>
				</c:otherwise>
			</c:choose>
		</c:forEach>


		<c:if test="${page lt pagesTotal}">
			<form action="Controller" method="get">
				<h:userlistpageparam />
				<input type="hidden" name="page" value="${page + 1}"> <input
					type="submit" value=">>">

			</form>
		</c:if>
	</tr>  
</table>






</body>
</html>
