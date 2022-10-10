<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="WEB-INF/mylib.tld" prefix="my"%>
<%@page import="java.time.LocalDate"%>
<%@ page isELIgnored="false"%>
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
<title>Appointments list</title>
</head>
<c:if test="${param.user_id != null}">
<c:set var="user_id" scope="request" value="${param.user_id}"/>
</c:if>

<h3>Appointments list</h3>
<br>

<form action="Controller" method="get">
	<strong>Date from: </strong><input type="date" name="datefrom" value= "${datefrom != null? datefrom : LocalDate.now().plusDays(-7)}">
	<strong>Date to: </strong><input type="date" name="dateto" value="${dateto != null? dateto : LocalDate.now().plusDays(7)}">

	<c:if test="${sessionScope.user.role != 'HAIRDRESSER'}">  
	<my:getmasters />
	<strong>Master:</strong> <select name="master_id">
		<option value="null" <c:if test="${master_id == null}"> selected </c:if>>All</option>
		<c:forEach items="${masterlist}" var="item">
			<option value="${item.id}"<c:if test="${master_id == item.id}"> selected </c:if>>${item.name} ${item.surname}</option>
		</c:forEach>
	</select> 
	</c:if>
	
	
	<my:getservices master_id="0" />
	
	<strong>Service: </strong> <select name="service_id">
		<option value="null" <c:if test="${service_id == null}"> selected </c:if>>All</option>
		<c:forEach items="${servicelist}" var="item">
			<option value="${item.id}"<c:if test="${service_id == item.id}"> selected </c:if>>${item.name}</option>
		</c:forEach>
	</select> 
	
	<strong>Status: </strong> 
	<select name="isdone">
		<option value="null"<c:if test="${isDone == null}"> selected </c:if>>All</option>
		<option value=true <c:if test="${isDone != null && isDone}"> selected </c:if>>Complete</option>
		<option value=false <c:if test="${isDone != null && !isDone}"> selected </c:if>>Incomplete</option>
	</select> 
	
	<strong>Payment: </strong> 
	<select name="ispaid">
		<option value="null" <c:if test="${isPaid == null}"> selected </c:if>>All</option>
		<option value=true <c:if test="${isPaid != null && isPaid}"> selected </c:if>>Paid</option>
		<option value=false <c:if test="${isPaid != null && !isPaid}"> selected </c:if>>Unpaid</option>
 	</select>
 	
 	<strong>Feedback: </strong> 
	<select name="israting">
		<option value="null"<c:if test="${isRating == null}"> selected </c:if>>All</option>
		<option value=true <c:if test="${isRating != null && isRating}"> selected </c:if>>Feedback left</option>
		<option value=false <c:if test="${isRating != null && !isRating}"> selected </c:if>>Feedback not left</option>
	</select> 


		<c:if test="${sessionScope.user.role == 'HAIRDRESSER'}">

			<input type="hidden" name="master_id" value="${sessionScope.user.id}">

		</c:if>

		<c:if test="${sessionScope.user.role == 'CLIENT'}">

			<input type="hidden" name="user_id" value="${sessionScope.user.id}">

		</c:if>
		
	
		<c:if test="${sessionScope.user.role == 'ADMIN' && user_id != null}">

			<input type="hidden" name="user_id" value="${user_id}">

		</c:if>



		<input type="hidden" name="command" value="show_appointments_list">
		<input type="submit" value="Search">
		<input type="reset" value="Reset">
<hr>		
		
		<strong>Items per page: </strong>
<input name="itemsperpage" value="${itemsPerPage == null ? 10 : itemsPerPage }" size=2>
<br>		
<input type="hidden" name="page" value="${page == null ? 1 : page }">			
	
</form>

<hr>
<br>
<br>


<table>

<tr>
<th>Date</th><th>Time</th><th>Master</th><th>Client</th><th>Service</th><th>Sum</th><th>Status</th><th>Payment</th><th>Rating</th><th>Info</th><th>...</th>
</tr>

  <c:forEach items="${appointmentsList}" var="item">
    <tr>
   	  <td><c:out value="${item.date}" /></td>
      <td><c:out value="${item.timeslot}" /></td>
      <td><c:out value="${item.master.name} ${item.master.surname}" /></td>
      <td><c:out value="${item.user.name} ${item.user.surname}" /></td>
      <td><c:out value="${item.service.name}" /></td>
      <td><c:out value="${item.sum} hrn." /></td>
      
      <td><c:out value="${item.isDone? 'Complete' : 'Incomplete'}"></c:out></td>
      <td><c:out value="${item.isPaid? 'Paid' : 'Unpaid'}"></c:out></td>
      <td><c:out value="${item.rating == 0? ' ' : item.rating}"></c:out></td>
      <td><a href = "Controller?command=show_appointment_info&master_id=${item.master.id}&date=${item.date}&timeslot=${item.timeslot}">Appointment info</a></td>
       <td><c:if test="${item.user != null && sessionScope.user.role == 'ADMIN' && !(item.isDone)}">
					<form action="Controller" method="post">
						<input type="hidden" name=master_id value="${item.master.id}" />
						<input type="hidden" name=date value="${item.date}" /> <input
							type="hidden" name=timeslot value="${item.timeslot}" /> <input
							type="hidden" name=command value="delete_appointment" /> <input
							type="submit" name="submit" value="X" />
					</form>
				</c:if></td>
    
    </tr>
  </c:forEach>
</table>

<br>
 <c:if test="${page != 1}">
 <form action="Controller" method="get">
<input type="hidden" name="command" value="show_appointments_list">
<input type="hidden" name= "datefrom" value="${datefrom}">
<input type="hidden" name= "dateto" value="${dateto}">	
<c:if test="${sessionScope.user.role != 'HAIRDRESSER'}">	
<input type="hidden" name= "master_id" value="${master_id == null? 'null' : master_id}">
</c:if>	
<input type="hidden" name= "service_id" value="${service_id == null? 'null' : service_id}"> 	
<input type="hidden" name= "isdone" value="${isDone == null? 'null' : isDone}"> 
<input type="hidden" name= "ispaid" value="${isPaid == null? 'null' : isPaid}"> 	
<input type="hidden" name= "israting" value="${isRating == null? 'null' : isRating}">

<c:if test="${sessionScope.user.role == 'CLIENT'}">
<input type="hidden" name="user_id" value="${sessionScope.user.id}">
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN' && user_id != null}">
<input type="hidden" name="user_id" value="${user_id}">
</c:if>
<input type="hidden" name="itemsperpage" value="${itemsPerPage}">
<input type="hidden" name="page" value="${page - 1}">

		<input type="submit" value="<<">
		
</form>
         
    </c:if>  

<table >
        <tr>
        
            <c:forEach begin="1" end="${pagesTotal}" var="i">
                <c:choose>
                    <c:when test="${page eq i}">
                        ${i}
                    </c:when>
                    <c:otherwise>
<form action="Controller" method="get">
<input type="hidden" name="command" value="show_appointments_list">
<input type="hidden" name= "datefrom" value="${datefrom}">
<input type="hidden" name= "dateto" value="${dateto}">	
<c:if test="${sessionScope.user.role != 'HAIRDRESSER'}">	
<input type="hidden" name= "master_id" value="${master_id == null? 'null' : master_id}">
</c:if>	
<input type="hidden" name= "service_id" value="${service_id == null? 'null' : service_id}"> 	
<input type="hidden" name= "isdone" value="${isDone == null? 'null' : isDone}"> 
<input type="hidden" name= "ispaid" value="${isPaid == null? 'null' : isPaid}"> 	
<input type="hidden" name= "israting" value="${isRating == null? 'null' : isRating}">

<c:if test="${sessionScope.user.role == 'CLIENT'}">
<input type="hidden" name="user_id" value="${sessionScope.user.id}">
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN' && user_id != null}">
<input type="hidden" name="user_id" value="${user_id}">
</c:if>
<input type="hidden" name="itemsperpage" value="${itemsPerPage}">
<input type="hidden" name="page" value="${i}">

		<input type="submit" value="${i}">
		
</form>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            
        </tr>
    </table>
 
    <c:if test="${page lt pagesTotal}">
    <br>
<form action="Controller" method="get">
<input type="hidden" name="command" value="show_appointments_list">
<input type="hidden" name= "datefrom" value="${datefrom}">
<input type="hidden" name= "dateto" value="${dateto}">	
<c:if test="${sessionScope.user.role != 'HAIRDRESSER'}">	
<input type="hidden" name= "master_id" value="${master_id == null? 'null' : master_id}">
</c:if>	
<input type="hidden" name= "service_id" value="${service_id == null? 'null' : service_id}"> 	
<input type="hidden" name= "isdone" value="${isDone == null? 'null' : isDone}"> 
<input type="hidden" name= "ispaid" value="${isPaid == null? 'null' : isPaid}"> 	
<input type="hidden" name= "israting" value="${isRating == null? 'null' : isRating}">

<c:if test="${sessionScope.user.role == 'CLIENT'}">
<input type="hidden" name="user_id" value="${sessionScope.user.id}">
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN' && user_id != null}">
<input type="hidden" name="user_id" value="${user_id}">
</c:if>
<input type="hidden" name="itemsperpage" value="${itemsPerPage}">
<input type="hidden" name="page" value="${page + 1}">

		<input type="submit" value=">>">
		
</form>
    </c:if>



</body>
</html>
