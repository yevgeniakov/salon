<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %> 
<html>
      <style>
         table, th, td {
            border: 1px solid black;
         }
      </style>
<body>

<jsp:include page="header.jsp" />
<head>
    <title>Service List</title>
</head>

<h3>Service list</h3>
<br>

<table>
<tr>
<th>id</th><th>Name</th><th>Info</th><th>Masters</th>
</tr>
  <c:forEach items="${servicelist}" var="item">
    <tr>
      <td><c:out value="${item.id}" /></td>
      <td><c:out value="${item.name}" /></td>
      <td><c:out value="${item.info}" /></td>
      <td><a href = "Controller?command=show_masters_of_service&service_id=${item.id}"> Show hairdressers for this service </a></td>
    </tr>
  </c:forEach>
</table>





</body>
</html>
