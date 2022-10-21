<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.time.LocalDate"%>

<html>

<body class="d-flex flex-column h-100">
<jsp:include page="header.jsp" />
<head>
<link href="css/registration.css" rel="stylesheet">
<link href='css/jquery.rating.css' type="text/css" rel="stylesheet" />
<title>User info</title>
</head>
<div class="user-info">

<h3><fmt:message key="label.user_info"/> - ${showuser.name} ${showuser.surname} </h3>
<h4>${showuser.role}</h4>
<c:if test="${showuser.role == 'HAIRDRESSER'}">
<fmt:message key="label.rating" var="val" />
 <c:out value="${val}: ${showuser.rating}"></c:out>
 <c:if test="${showuser.rating !=0}">
 <br>
<h:createstars rating="${showuser.rating}" inputname="${'rat'}${showuser.id}"/>
<br>
</c:if>
</c:if>

<c:if
	test="${sessionScope.user != null &&sessionScope.user.role == 'ADMIN' && showuser.role == 'CLIENT'}">
	<br>
<a href = "Controller?command=show_appointments_list&user_id=${showuser.id}"><fmt:message key="link.user_appointments"/></a>
</c:if>

<c:if
	test="${sessionScope.user != null && showuser.role == 'HAIRDRESSER'}">
	<br>
	<a
		href="Controller?command=show_master_schedule&id=${showuser.id}&date=${LocalDate.now().plusDays(1)}">
		<fmt:message key="link.view_schedule"/> </a>
</c:if>

<br>
<form action="Controller" method=post id="profile_form">
	<input type="hidden" name="id" value="${showuser.id}" /> <br>


<div class="mb-3 row">
  <label for="name" class="col-sm-2 col-form-label"><fmt:message key="label.name"/></label>
<div class="col-sm-10">
  <input type="text" class="form-control" name="name" id="name" value="${showuser.name}"<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if>>
  <span id="error_firstname" class="error" aria-live="polite"></span>
</div>
</div>

<div class="mb-3 row">
  <label for="surname" class="col-sm-2 col-form-label"><fmt:message key="label.surname"/></label>
<div class="col-sm-10">
  <input type="text" class="form-control" name="surname" id="surname" value="${showuser.surname}"<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if>>
    <span id="error_surname" class="error" aria-live="polite"></span>
</div>
</div>

<div class="mb-3 row">
  <label for="email" class="col-sm-2 col-form-label"><fmt:message key="label.email"/></label>
<div class="col-sm-10">
  <input type="text" class="form-control" name="email" id="email" value="${showuser.email}"<c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if>>
    <span id="error_email" class="error" aria-live="polite"></span>
</div>
</div>

<div class="mb-3 row">
  <label for="tel" class="col-sm-2 col-form-label"><fmt:message key="label.tel"/></label>
<div class="col-sm-10">
  <input type="text" class="form-control" name="tel" id="tel"  value="${showuser.tel}" <c:if test="${sessionScope.user.role != 'ADMIN'}">readonly</c:if>>
    <span id="error_tel" class="error" aria-live="polite"></span>
</div>
</div>
	

	<c:if
		test="${showuser.role == 'HAIRDRESSER' && sessionScope.user.role != 'ADMIN'}">
			<label><fmt:message key="label.master_info"/></label><br>
		<span class="border border-info">${showuser.info}</span><br>
	</c:if>
	
	<c:if
		test="${showuser.role == 'HAIRDRESSER' && sessionScope.user.role == 'ADMIN'}">
		<textarea class="form-control" rows="10" cols="60" name="info">${showuser.info}</textarea><br>
			
	</c:if>
	
	<c:if test="${showuser.role == 'ADMIN'}">
		<strong><fmt:message key="label.role"/></strong>
		<br>
		<input class="form-check-input" type="radio" name="role" value="CLIENT"
			<c:if test="${showuser.role == 'CLIENT'}">checked</c:if> disabled /><fmt:message key="option.roleclient"/>
    <br>
		<input class="form-check-input" type="radio" name="role" value="HAIRDRESSER"
			<c:if test="${showuser.role == 'HAIRDRESSER'}">checked</c:if>
			disabled /><fmt:message key="option.rolehairdresser"/>
	<br>
		<input class="form-check-input" type="radio" name="role" value="ADMIN"
			<c:if test="${showuser.role == 'ADMIN'}">checked</c:if> disabled /><fmt:message key="option.roleadmin"/>
<br>
</c:if>
	<c:if
		test="${sessionScope.user.role == 'ADMIN' && showuser.role == 'HAIRDRESSER'}">
		<my:getabsentservices master_id="${showuser.id}" />

		<strong><fmt:message key="label.add_service"/></strong>
		<br>
		<div class="row">
		<c:set var="count" value="0" scope="page" />
		<c:forEach items="${serviceabsentlist}" var="item">
			<c:set var="count" value="${count + 1}" scope="page" />
			<div class="col-8"><input class="form-control" name="${'service'}${count}" value="${item.name}"></div>
			<div class="col-2"><input class="form-control" name="sum${count}" size="5"></div> <div class="col"><fmt:message key="label.hrn"/></div>
			<div class="w-100"></div>
			
		</c:forEach>
		</div>


	</c:if>

	<c:if
		test="${sessionScope.user.role == 'ADMIN' && showuser.isBlocked }">
		<strong><fmt:message key="label.status_blocked"/></strong>
<br>

	</c:if>


	<input type="hidden" name="command" value="update_user">
	<input type="hidden" name="isBlocked" value="${showuser.isBlocked}">
		
	
		<c:if test="${sessionScope.user.role == 'ADMIN'}">
		<br>
			<button type="submit" class="btn btn-primary text-nowrap"><fmt:message key="button.update_data"/></button> 
		</c:if>
		
</form>
<c:if test="${sessionScope.user.role == 'ADMIN'}">
<fmt:message key="confirmation.block_user" var="conf_block" />
<fmt:message key="confirmation.unblock_user" var="conf_unblock" />

	<form action="Controller" method=post data-confirm="${showuser.isBlocked? conf_unblock : conf_block }">
		<input type="hidden" name="command" value="set_user_block"> <input
			type="hidden" name="isBlocked"
			value="${showuser.isBlocked? 'false' : 'true'}">
			<input type="hidden" name="id" value="${showuser.id}"> 
			<fmt:message key="button.block_user" var="block" />
			<fmt:message key="button.unblock_user" var="unblock" />
			<button type="submit" class="${showuser.isBlocked? 'btn btn-success' : 'btn btn-danger'}">${showuser.isBlocked? unblock : block}</button> 
			

			

	</form>
</c:if>

<c:if test="${services != null && showuser.role == 'HAIRDRESSER'}">
		
			<strong><fmt:message key="label.master_services"/></strong>

		<table class="table table-striped">
			<c:forEach items="${services}" var="entry">
				<tr>
					<td><c:out value="${entry.key.name}" /></td>
					<td><c:out value="${entry.value}"/> <fmt:message key="label.hrn"/></td>

					<c:if test="${sessionScope.user.role == 'ADMIN'}">
						<td align="center">
							<form action="Controller" method="post" data-confirm=<fmt:message key="confirmation.delete_service"/>>
								<input type="hidden" name=service_id value="${entry.key.id}" />
								<input type="hidden" name=master_id value="${showuser.id}" /> 
								<input type="hidden" name=command value="delete_service_from_master" />
								<button type="submit" class="btn btn-danger text-nowrap" name="submit">X</button>
							</form>
						</td>
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
<script src="${pageContext.request.contextPath}/js/form_confirmation.js"></script>
<script src="${pageContext.request.contextPath}/js/info.js"></script>
<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js'
	type="text/javascript"></script>
<script src='js/jquery.MetaData.js' type="text/javascript"
	></script>
<script src='js/jquery.rating.js' type="text/javascript"
	></script>
</body>
</html>
