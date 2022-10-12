<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my"%>
<%@ page isELIgnored="false" %> 
<html>
      <style>
         table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
         }
      </style>
<body>
<jsp:include page="header.jsp" />


<head>
    <title>Master Schedule </title>
</head>

<h3>Master Schedule - ${master.name} ${master.surname}</h3>
<br>
<form method=get name="dateForm">
<input type="hidden" name="command" value="show_master_schedule">
<input type="hidden" name="id" value="${param.id}">
<input type="date" name=date value="${param.date}" onchange="document.dateForm.submit();">

</form>

<hr>

<table>
<tr>
<th>Time</th><th>Master</th><th>Info</th><th>User</th><th>Service</th><th>Sum</th><th>isDone</th><th>isPaid</th><th>Rating</th>
</tr>
  <c:forEach items="${schedule}" var="item">
    <tr>
      <td><my:timeslotdisp timeslot = "${item.timeslot}" currentLang="${sessionScope.user.currentLang}"/></td>
      <td><c:if test="${item.master != null}"><c:out value="${item.master.name} ${item.master.surname}" /></c:if></td>
      <td colspan="${item.user != null ? 0 :7 }" align="center">
      <c:if test="${item.user == null && sessionScope.user.role == 'CLIENT'}">
      <a href = "create_appointment.jsp?master_id=${item.master.id}&date=${date}&timeslot=${item.timeslot}">Create appointment</a>
      </c:if>
      <c:if test="${item.user != null && (sessionScope.user.role == 'ADMIN' || sessionScope.user.id == item.user.id || sessionScope.user.id == item.master.id)}">
      <a href = "Controller?command=show_appointment_info&master_id=${item.master.id}&date=${date}&timeslot=${item.timeslot}">Appointment info</a>
      </c:if>
      <c:if test="${item.user != null && sessionScope.user.role != 'ADMIN' && sessionScope.user.id != item.user.id && sessionScope.user.id != item.master.id}">
      <c:out value = "Time is occupied"></c:out>
      </c:if>
      </td>
      <c:if test="${item.user != null}">
      <td ><c:if test="${item.user != null}"><c:out value="${item.user.name} ${item.user.surname}" /></c:if></td>
      
      <td ><c:if test="${item.service != null}"><c:out value="${item.service.name}" /></c:if></td>
      <td><c:if test="${item.sum != null && item.sum != 0}"><c:out value="${item.sum} hrn." /></c:if></td>
      
     
      
   	  <td><c:if test="${item.user != null}"><c:out value="${item.isDone? 'Complete' : 'Incomplete'}"></c:out></c:if></td>
      <td><c:if test="${item.user != null}"><c:out value="${item.isPaid? 'Paid' : 'Unpaid'}"></c:out></c:if></td>
      <td align="center"><c:out value="${item.rating == 0? '-' : item.rating}"></c:out></td>
</c:if>
      
      
    </tr>
  </c:forEach>
</table>





</body>
</html>
