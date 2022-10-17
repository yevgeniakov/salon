<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
      <style>
         table, th, td {
            border: 1px solid black;
         }
      </style>
<body>
<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.my_info"/></title>
</head>



<div class="my_info">

<h3><fmt:message key="label.my_info"/></h3>
<br>

<form action="Controller" method=post>
	<input type="hidden" name="id" value="${sessionScope.user.id}" /> <br>


<div class="mb-3 row">
  <label for="exampleFormControlInput1" class="col-sm-2 col-form-label"><fmt:message key="label.name"/></label>
<div class="col-sm-10">
  <input type="text" class="form-control" name="name" id="exampleFormControlInput1" value="${sessionScope.user.name}">
</div>
</div>

<div class="mb-3 row">
  <label for="exampleFormControlInput1" class="col-sm-2 col-form-label"><fmt:message key="label.surname"/></label>
<div class="col-sm-10">
  <input type="text" class="form-control" name="surname" id="exampleFormControlInput1" value="${sessionScope.user.surname}">
</div>
</div>

<div class="mb-3 row">
  <label for="exampleFormControlInput1" class="col-sm-2 col-form-label"><fmt:message key="label.email"/></label>
<div class="col-sm-10">
  <input type="text" class="form-control" name="email" id="exampleFormControlInput1" value="${sessionScope.user.email}">
</div>
</div>

<div class="mb-3 row">
  <label for="exampleFormControlInput1" class="col-sm-2 col-form-label"><fmt:message key="label.tel"/></label>
<div class="col-sm-10">
  <input type="text" class="form-control" name="tel" id="exampleFormControlInput1"  value="${sessionScope.user.tel}">
</div>
</div>





	<c:if
		test="${sessionScope.user.role == 'HAIRDRESSER'}">
			<c:out value="${sessionScope.user.info}"></c:out>
	</c:if>
				
		<br>
		
		
	<c:if test="${sessionScope.user.role == 'ADMIN'}">
	
		<strong><fmt:message key="label.role"/></strong>
		<br>
		<input type="radio" name="role" value="CLIENT"
			<c:if test="${sessionScope.user.role == 'CLIENT'}">checked</c:if>
			disabled /><fmt:message key="option.roleclient"/>
    <br>
		<input type="radio" name="role" value="HAIRDRESSER"
			<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">checked</c:if>
			disabled /><fmt:message key="option.rolehairdresser"/>
	<br>
		<input type="radio" name="role" value="ADMIN"
			<c:if test="${sessionScope.user.role == 'ADMIN'}">checked</c:if>
			disabled /><fmt:message key="option.roleadmin"/>

</c:if>
	<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">
		<my:getservices master_id="${sessionScope.user.id}" />
		<c:if test="${servicemap != null}">
			
				<strong><fmt:message key="label.master_services"/></strong>
			
			<table class="table table-striped">
				<c:forEach items="${servicemap}" var="entry">
					<tr>
						<td><c:out value="${entry.key.name}" /></td>
						<td><c:out value="${entry.value} hrn." /></td>


					</tr>
				</c:forEach>
			</table>
		</c:if>

	</c:if>
	<input type="hidden" name="isBlocked" value="${sessionScope.user.isBlocked}">
	<input type="hidden" name="info" value="${sessionScope.user.info}">
	<input type="hidden" name="command" value="update_user">
	<p>
	<p>
		<input type="submit" value=<fmt:message key="button.update_data"/>>
</form>
</div>


</body>
</html>
