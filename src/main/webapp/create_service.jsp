<%@page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>

<html>
      <style>
         table, th, td {
            border: 1px solid black;
         }
      </style>
<body>
<jsp:include page="header.jsp" />
<head>
<title>Create Service Page</title>
</head>

<h3>Please, provide the information below:</h3>
<br>
<form action="Controller" method=post>


	<strong>Name of service: </strong> <br> <input size="100"
		name="name"> <br>
	<p>

		<strong>Service info: </strong> <br>
		<textarea rows="10" cols="60" name="info">

    </textarea>

		<br> <input type="hidden" name="current_lang" value="en">
		<input type="hidden" name="command" value="create_service">
	<p>
	<p>
		<input type="submit" value="Submit"> <input type="reset"
			value="Reset">
</form>



</body>
</html>
