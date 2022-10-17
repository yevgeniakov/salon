<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<script src="js/jquery-3.6.1.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>



<head>
<link rel="shortcut icon" href="favicon.ico"/>
</head>
<body>







<c:if test="${currentLocale == null}">
<c:set var="currentLocale" value="en" scope="session"/>
</c:if>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">BestBeauty</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDarkDropdown" aria-controls="navbarNavDarkDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDarkDropdown">

      <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
	<a href="index.jsp"><fmt:message key="link.homepage"/></a>
        </li>
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
          <a class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">

     ${ sessionScope.user != null ? sessionScope.user.name : "Guest" }</a>
          </a>
          <ul class="dropdown-menu dropdown-menu-right dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
            <li><h6 class="dropdown-header"> ${ sessionScope.user != null ? sessionScope.user.name.concat(' ').concat(sessionScope.user.surname) : "Guest" } </h6></li>
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
            <li><a class="dropdown-item" href="registration.jsp"><fmt:message key="link.registration"/></li>
	</c:if>
	      <li><hr class="dropdown-divider"></li>

            <li><a class="dropdown-item" href="#"><fmt:message key="link.help"/></a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>


</body>
</html>
