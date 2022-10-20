<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>

<body class="d-flex flex-column h-100">
	<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.user_list" /></title>
</head>
<br>
<h3 align="center">
	<fmt:message key="label.user_list" />
</h3>

<br>


<nav class="navbar bg-light">
  <div class="container-fluid">
<form action="Controller" name="formSearch" method="get">

<div class="row">
<div class="col-sm"><p class="text-center"><fmt:message key="label.sortby"/></p></div>
<div class="col-sm"><p class="text-center"><fmt:message key="label.role" /></p></div>
<div class="col-sm"><p class="text-center"><fmt:message key="label.status"/></p></div>
<div class="col-sm"><p class="text-center"><fmt:message key="label.searchbyvalue" /></p></div>
<div class="w-100"></div>
<div class="col-sm row">
<div class="col-sm">
	<select name="sort" class="form-select" onchange="document.formSearch.submit();">
		<option value="id"
			<c:if test="${sort == 'id' || sort == null}"> selected </c:if>><fmt:message
				key="label.id" /></option>
		<option value="surname"
			<c:if test="${sort == 'surname'}"> selected </c:if>><fmt:message
				key="option.surname" /></option>
	</select> 
</div>
<div class="col-sm">
	<select name="sortorder" class="form-select" onchange="document.formSearch.submit();">
		<option value="asc"
			<c:if test="${sortorder == 'asc' || sortorder == null}"> selected </c:if>><fmt:message
				key="option.ascending" /></option>
		<option value="desc"
			<c:if test="${sortorder == 'desc'}"> selected </c:if>><fmt:message
				key="option.descending" /></option>
	</select>
</div>
</div>
<div class="col-sm">
	<select name="role" class="form-select" onchange="document.formSearch.submit();">
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

</div>
<div class="col-sm">
<select
		name="isblocked" class="form-select" onchange="document.formSearch.submit();">
		<option value="null"
			<c:if test="${isBlocked == null}"> selected </c:if>><fmt:message
				key="option.all" /></option>
		<option value=true
			<c:if test="${isBlocked != null && isBlocked}"> selected </c:if>><fmt:message
				key="option.blocked" /></option>
		<option value=false
			<c:if test="${isBlocked != null && !isBlocked}"> selected </c:if>><fmt:message
				key="option.active" /></option>
	</select>
</div>

<div class="col-sm">
<input	class="search_field1" name="searchvalue" onchange="document.formSearch.submit();" value="${searchValue == null ? '': searchValue }">
	<input type="hidden" name="page" value="1"> <input
		type="hidden" name="command" value="show_user_list">

</div>
<div class="w-100"></div>
<div class="col-sm"><fmt:message key="label.itemsperpage" /> <input	name="itemsperpage" onchange="document.formSearch.submit();"
		value="${itemsPerPage == null ? 10 : itemsPerPage }" size=2> <br>

	<input type="hidden" name="page" value="1"> <input
		type="hidden" name="command" value="show_user_list">
</div>

</div>
    </form>
</div>

</nav>

<!--
<strong><fmt:message key="label.sortby" /></strong>
<form action="Controller" name="formSearch" method="get">
	<select name="sort" class="form-select" onchange="document.formSearch.submit();">
		<option value="id"
			<c:if test="${sort == 'id' || sort == null}"> selected </c:if>><fmt:message
				key="label.id" /></option>
		<option value="surname"
			<c:if test="${sort == 'surname'}"> selected </c:if>><fmt:message
				key="option.surname" /></option>


	</select> 
	<select name="sortorder" class="form-select" onchange="document.formSearch.submit();">
		<option value="asc"
			<c:if test="${sortorder == 'asc' || sortorder == null}"> selected </c:if>><fmt:message
				key="option.ascending" /></option>
		<option value="desc"
			<c:if test="${sortorder == 'desc'}"> selected </c:if>><fmt:message
				key="option.descending" /></option>


	</select> 
	
	<strong><fmt:message key="label.role" /></strong>
	<select name="role" class="form-select" onchange="document.formSearch.submit();">
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
		name="isblocked" class="form-select" onchange="document.formSearch.submit();">
		<option value="null"
			<c:if test="${isBlocked == null}"> selected </c:if>><fmt:message
				key="option.all" /></option>
		<option value=true
			<c:if test="${isBlocked != null && isBlocked}"> selected </c:if>><fmt:message
				key="option.blocked" /></option>
		<option value=false
			<c:if test="${isBlocked != null && !isBlocked}"> selected </c:if>><fmt:message
				key="option.active" /></option>
	</select> 
<strong><fmt:message key="label.searchbyvalue" /></strong> <input
		name="searchvalue" onchange="document.formSearch.submit();" value="${searchValue == null ? '': searchValue }">
	
	<hr>
	<strong><fmt:message key="label.itemsperpage" /></strong> <input
		name="itemsperpage" onchange="document.formSearch.submit();"
		value="${itemsPerPage == null ? 10 : itemsPerPage }" size=2> <br>

	<input type="hidden" name="page" value="1"> <input
		type="hidden" name="command" value="show_user_list">

</form> -->
<table class="table table-striped">
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


<nav aria-label="Page navigation">
	<ul class="pagination">
		<li class="page-item ${page != 1? '' : 'disabled' }"><a
			class="page-link"
			href="Controller?command=show_user_list&sort=${sort}&sortorder=${sortorder}&isblocked=${isBlocked == null? 'null' : isBlocked}&searchvalue=${searchValue == null ? '': searchValue}&itemsperpage=${itemsPerPage}&page=${page - 1}
">&laquo;</a>
		</li>
		<c:forEach begin="1" end="${pagesTotal}" var="i">
			<li class="page-item ${page eq i? 'active' : '' }"><a
				class="page-link"
				href="Controller?command=show_user_list&sort=${sort}&sortorder=${sortorder}&isblocked=${isBlocked == null? 'null' : isBlocked}&searchvalue=${searchValue == null ? '': searchValue}&itemsperpage=${itemsPerPage}&page=${i}
">${i}</a>
			</li>
		</c:forEach>
		<li class="page-item ${page lt pagesTotal? '' : 'disabled' }"><a
			class="page-link"
			href="Controller?command=show_user_list&sort=${sort}&sortorder=${sortorder}&isblocked=${isBlocked == null? 'null' : isBlocked}&searchvalue=${searchValue == null ? '': searchValue}&itemsperpage=${itemsPerPage}&page=${page + 1}
">&raquo;</a>
		</li>
	</ul>
</nav>


<footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>

</body>
</html>
