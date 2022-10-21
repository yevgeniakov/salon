<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.time.LocalDate"%>


<html>
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery-3.6.1.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>



<head>

<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<c:if test="${currentLocale == null}">
<c:set var="currentLocale" value="en" scope="session"/>
</c:if>


<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">

  <div class="container-fluid">
    <a class="navbar-brand h1" href="index.jsp">BestBeauty</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDarkDropdown" aria-controls="navbarNavDarkDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse white_font" id="navbarNavDarkDropdown">

      <ul class="navbar-nav ml-auto">

	<c:if test="${sessionScope.user == null}">
            <li class="nav-item  m_list">
		<a class="nav-link active" href="Controller?command=show_master_list"><fmt:message key="menu.master_list"/></a>
    	    </li>
            <li class="nav-item m_list">
		<a class="nav-link active" href="Controller?command=show_service_list"><fmt:message key="menu.service_list"/></a>
    	    </li>

        </c:if>

	<c:if test="${sessionScope.user.role == 'CLIENT'}">
            <li class="nav-item  m_list">
		<a class="nav-link active" href="Controller?command=show_master_list"><fmt:message key="menu.master_list"/></a>
    	    </li>
            <li class="nav-item m_list">
		<a class="nav-link active" href="Controller?command=show_service_list"><fmt:message key="menu.service_list"/></a>
    	    </li>
            <li class="nav-item m_list">
		<a class="nav-link active" href="Controller?command=show_appointments_list"><fmt:message key="menu.my_appointments"/></a>
    	    </li>

        </c:if>

	<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">
            <li class="nav-item  m_list">
		<a class="nav-link active" href="Controller?command=show_master_schedule&id=${sessionScope.user.id}&date=${LocalDate.now()}"><fmt:message key="menu.view_schedule"/></a>
    	    </li>
            <li class="nav-item m_list">
		<a class="nav-link active" href="Controller?command=show_appointments_list"><fmt:message key="menu.appointments_list"/></a>
    	    </li>

        </c:if>

	<c:if test="${sessionScope.user.role == 'ADMIN'}">
	<li class="nav-item dropdown">
    		  <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
    	        <fmt:message key="label.users"/>
        	  </a>
        	    <ul class="dropdown-menu">
        		    <li><a class="dropdown-item" href="create_user.jsp"><fmt:message key="label.create_user"/></a></li>
        		    <li><a class="dropdown-item" href="Controller?command=show_user_list"><fmt:message key="label.user_list"/></a></li>
        		    <li><a class="dropdown-item" href="Controller?command=show_master_list"><fmt:message key="label.master_list"/></a></li>
        	    </ul>
        </li>
	<li class="nav-item dropdown">
    		  <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
    	        <fmt:message key="label.services"/>
        	  </a>
        	    <ul class="dropdown-menu">
        		    <li><a class="dropdown-item" href="create_service.jsp"><fmt:message key="menu.create_service"/></a></li>
        		    <li><a class="dropdown-item" href="Controller?command=show_service_list"><fmt:message key="label.service_list"/></a></li>
        	    </ul>
        </li>
	<li class="nav-item dropdown">
    		  <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
    	        <fmt:message key="label.appointments"/>
        	  </a>
        	    <ul class="dropdown-menu">
        		    <li><a class="dropdown-item" href="Controller?command=show_appointments_list"><fmt:message key="label.appointments_list"/></a></li>
        	    </ul>
        </li>

        </c:if>

      </ul>

      <ul class="navbar-nav ms-auto">
<li class="nav-item lang_switch">
<form  action="Controller" name="formlocale" method="post">
	<select name="locale" class="form-select" onchange="document.formlocale.submit();">
	    <c:forEach items="${applicationScope.locales}" var="locale">
		<c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
		<option value="${locale.key}" ${selected}>${locale.value}</option>
	    </c:forEach>
	</select>
	<input type="hidden" name="command" value="change_locale">	

</form>
</li>

        <li class="nav-item dropdown dropstart">
        <fmt:message key="label.guest" var="labelGuest"/>
          <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">

     ${ sessionScope.user != null ? sessionScope.user.name : labelGuest }</a>
         
          <ul class="dropdown-menu dropdown-menu-right dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
            <li><h6 class="dropdown-header"> ${ sessionScope.user != null ? sessionScope.user.name.concat(' ').concat(sessionScope.user.surname) : labelGuest } </h6></li>
	<c:if test="${sessionScope.user != null}">
        <li><h6 class="dropdown-header"> --==${sessionScope.user.role}==-- </h6></li>
	</c:if>
            <li><hr class="dropdown-divider"></li>
	<c:if test="${sessionScope.user != null}">
            <li><a class="dropdown-item" href="my_info.jsp"><fmt:message key="link.profile"/></a></li>
            <li><a class="dropdown-item" href="Controller?command=logout"><fmt:message key="link.logout"/></a></li>
	</c:if>



	<c:if test="${sessionScope.user == null}">
            <li><a class="dropdown-item" href="login.jsp"><fmt:message key="link.login"/></a></li>
            <li><a class="dropdown-item" href="registration.jsp"><fmt:message key="link.registration"/></a></li>
	</c:if>
	      <li><hr class="dropdown-divider"></li>

            <li><a class="dropdown-item" href="help.jsp"><fmt:message key="link.help"/></a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>


</body>
</html>
