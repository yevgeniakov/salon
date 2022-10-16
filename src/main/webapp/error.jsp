<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %> 
<html>

<body>
<jsp:include page="header.jsp" />
<link rel="stylesheet" href="css/my.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link  rel="stylesheet"  href="https://cdn.jsdelivr.net/gh/lipis/flag-icons@6.6.6/css/flag-icons.min.css"/>

<script src="js/bootstrap.bundle.min.js"></script>
<head>
<title>Error</title>

</head>

      <!-- Fixed navbar -->
      <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#">BestBeauty</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item">
              <a class="nav-link disabled" href="#">Disabled</a>
            </li>
          </ul>
        </div>
<div class="dropdown langsel">
<select class="selectpicker" data-width="fit">
    <option data-content='<span class="flag-icon flag-icon-us"></span> English'>English</option>
  <option  data-content='<span class="flag-icon flag-icon-mx"></span> Español'>Español</option>
</select>
</div>

<div>
<i class="flag flag-us"></i>
</div>

<div class="dropdown lang_selector">
    <a class="dropdown-toggle" href="#" id="Dropdown" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
        <i class="flag-united-kingdom flag m-0"></i>
    </a>

    <ul class="dropdown-menu" aria-labelledby="Dropdown">
        <li>
            <a class="dropdown-item" href="#"><i class="flag-united-kingdom flag"></i>English <i class="fa fa-check text-success ms-2"></i></a>
        </li>
        <li><hr class="dropdown-divider" /></li>
        <li>
            <a class="dropdown-item" href="#"><i class="flag-poland flag"></i>Polski</a>
        </li>
        <li>
            <a class="dropdown-item" href="#"><i class="flag-china flag"></i>中文</a>
        </li>
        <li>
            <a class="dropdown-item" href="#"><i class="flag-japan flag"></i>日本語</a>
        </li>
        <li>
            <a class="dropdown-item" href="#"><i class="flag-germany flag"></i>Deutsch</a>
        </li>
        <li>
            <a class="dropdown-item" href="#"><i class="flag-france flag"></i>Français</a>
        </li>
        <li>
            <a class="dropdown-item" href="#"><i class="flag-spain flag"></i>Español</a>
        </li>
        <li>
            <a class="dropdown-item" href="#"><i class="flag-russia flag"></i>Русский</a>
        </li>
        <li>
            <a class="dropdown-item" href="#"><i class="flag-portugal flag"></i>Português</a>
        </li>
    </ul>
</div>
<div class="dropdown">
  <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
    Guest
  </button>
  <ul class="dropdown-menu">
    <li><a class="dropdown-item" href="login.jsp">Login</a></li>
    <li><a class="dropdown-item" href="registration.jsp">Registration</a></li>
  </ul>
</div>
      </nav>
    </header>



<h3>Something went wrong :(</h3>
<br> <h4>${error}</h4>
<br>

</body>
</html>
