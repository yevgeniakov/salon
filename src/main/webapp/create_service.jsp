<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>

<body class="d-flex flex-column h-100">
<jsp:include page="header.jsp" />
<head>
<title>Create Service Page</title>
<link href="css/registration.css" rel="stylesheet">
</head>
<div class="create-service">
<h3><fmt:message key="label.create_service"/></h3>
<br>
<form action="Controller" method=post>
	<strong><fmt:message key="label.name_of_service"/></strong> <br>
	<input class="form-control" name="name" required> <br>
		<strong><fmt:message key="label.service_info"/></strong> <br>
		<textarea class="form-control" rows="10" cols="60" name="info" required>

    </textarea>

		<br> <input type="hidden" name="current_lang" value="en">
		<input type="hidden" name="command" value="create_service">

		<div class="btn-group registration-btns" align="center">
			<button class="w-100 btn btn-lg btn-primary text-nowrap" type="submit">
				<fmt:message key="button.create_service" />
			</button>
			<button class="w-100 btn btn-lg btn-secondary text-nowrap" type="reset">
				<fmt:message key="button.reset" />
			</button>
		</div>
</form>


</div>

    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>
</body>
</html>
