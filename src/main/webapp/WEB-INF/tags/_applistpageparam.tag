<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input type="hidden" name="command" value="show_appointments_list">
<input type="hidden" name= "datefrom" value="${datefrom}">
<input type="hidden" name= "dateto" value="${dateto}">	
<c:if test="${sessionScope.user.role != 'HAIRDRESSER'}">	
<input type="hidden" name= "master_id" value="${master_id == null? 'null' : master_id}">
<c:set var="master_id_param" value="${master_id == null? 'null' : master_id}"></c:set>
</c:if>	

<input type="hidden" name= "service_id" value="${service_id == null? 'null' : service_id}"> 	
<input type="hidden" name= "isdone" value="${isDone == null? 'null' : isDone}"> 
<input type="hidden" name= "ispaid" value="${isPaid == null? 'null' : isPaid}"> 	
<input type="hidden" name= "israting" value="${isRating == null? 'null' : isRating}">

<c:if test="${sessionScope.user.role == 'CLIENT'}">
<input type="hidden" name="user_id" value="${sessionScope.user.id}">
<c:set var="user_id_param" value="${sessionScope.user.id}"></c:set>
</c:if>
<c:if test="${sessionScope.user.role == 'ADMIN' && user_id != null}">
<input type="hidden" name="user_id" value="${user_id}">
<c:set var="user_id_param" value="${user_id}"></c:set>
</c:if>
<input type="hidden" name="itemsperpage" value="${itemsPerPage}">
