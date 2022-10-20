<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<body class="d-flex flex-column h-100">
<jsp:include page="header.jsp" />
<head>
    <title>Best Beauty Salon</title>
<link href="css/registration.css" rel="stylesheet">
</head>
<br>
<div class="registration_data">
<h1 align="center">PROJECT DESCRIPTION</h1>

<h3>The task of the final project is to develop a web application that supports the functionality according to the task variant.</h3>

<p>Variants</p>

<p class="lead">
Beauty salon
</p>
	

<p>The system implements the work schedule of beauty salon employees. There are roles: Guest, Client, Administrator, Hairdresser.<br>
The guest can see the catalog of services and the list of hairdressers taking into account sorting:</p>
<ul>
<li> by the name;</li>
<li> by rating</li>
</ul>
<p>can filter:</p>
<ul>
<li> by certain hairdresser;</li>
<li> by services.</li>
</ul>
<p>The client (authorized user) can sign up for a specific service provided by the hairdresser and for a specific time slot.<br>
The administrator can:</p>
<ul>
<li> view customer orders and change the selected time slot;</li>
<li> cancel order;</li>
<li> accept payment for the service.</li>
</ul>
<p>The hairdresser sees his schedule (busy and free time slots) and marks the execution of the order.<br>
After providing services, the Client leaves feedback. The offer to leave feedback comes to the Client's e-mail the day after the service is provided.</p>
</div>

    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>
</body>

</html>