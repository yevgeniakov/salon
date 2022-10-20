<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<body class="d-flex flex-column h-100">



<head>
<link href="css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title><fmt:message key="label.login_page"/></title>
</head>
<style>
.form-signin {
  max-width: 330px;
  padding: 15px;
}

.form-signin .form-floating:focus-within {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

</style>







<div>
 <jsp:include page="header.jsp" />

</div>

<div class="l_form form-signin w-100 m-auto">

 <form  action="Controller" method=post>

    <h3 class="h3 mb-3 fw-normal"><fmt:message key="label.provide_your_data"/></h3>
    <div class="form-floating">
      <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" name="email">
      <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
      <label for="floatingPassword"><fmt:message key="label.password"/></label>
    </div>

    <div class="checkbox mb-3">
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="button.login"/></button>
    <input type="hidden" name="command" value = "login">
  </form>

</div>

    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>


</body>
</html>
