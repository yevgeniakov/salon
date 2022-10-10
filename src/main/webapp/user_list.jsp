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
    <title>User list</title>
</head>

<h3>User list</h3>

<br>
<strong>Sort by: </strong>
<form action="Controller" method="get">
<select name="sort">
		<option value="id" <c:if test="${sort == 'id' || sort == null}"> selected </c:if>>id</option>
		<option value="surname" <c:if test="${sort == 'surname'}"> selected </c:if>>surname</option>
		
		
	</select>
	
	<select name="sortorder">
		<option value="asc" <c:if test="${sortorder == 'asc' || sortorder == null}"> selected </c:if>>ascending</option>
		<option value="desc" <c:if test="${sortorder == 'desc'}"> selected </c:if>>descending</option>
		
		
	</select>
	
	<strong>Status: </strong> 
	<select name="isblocked">
		<option value="null"<c:if test="${isBlocked == null}"> selected </c:if>>All</option>
		<option value=true <c:if test="${isBlocked != null && isBlocked}"> selected </c:if>>Blocked</option>
		<option value=false <c:if test="${isBlocked != null && !isBlocked}"> selected </c:if>>Active</option>
	</select> 
	
	<strong>Search by value: </strong> 
	
		<input name="searchvalue" value="${searchValue == null ? '': searchValue }">
		<input type = "submit" value="Apply"> 
<br>
<hr>
<strong>Items per page: </strong>
<input name="itemsperpage" value="${itemsPerPage == null ? 10 : itemsPerPage }" size=2>
<br>		
<input type="hidden" name="page" value="${page == null ? 1 : page }">			
	
	
	<input type="hidden" name="command" value="user_list">
	
</form>
<table>
<tr>
<th>id</th><th>Name</th><th>Email</th><th>Tel</th><th>Role</th><th>Status</th><th>User page</th>
</tr>

  <c:forEach items="${userlist}" var="item">
    <tr>
      <td><c:out value="${item.id}" /></td>
      <td><c:out value="${item.name} ${item.surname}" /></td>
      <td><c:out value="${item.email}" /></td>
      <td><c:out value="${item.tel}" /></td>
      <td><c:out value="${item.role}" /></td>
      <td><c:out value="${item.isBlocked? 'BLOCKED' : 'ACTIVE'}"/></td>
      <td><a href = "Controller?command=show_user_info&id=${item.id}"> User Info </a></td>
    </tr>
  </c:forEach>
</table>

<br>

<table border="1" >
        <tr>
            <c:forEach begin="1" end="${pagesTotal}" var="i">
                <c:choose>
                    <c:when test="${page eq i}">
                        ${i}
                    </c:when>
                    <c:otherwise>
                        <a href="Controller?command=user_list&sort=${sort}&sortorder=${sortOrder}&isblocked=${isBlocked}&searchvalue=${searchValue}&itemsperpage=${itemsPerPage}&page=${i}"> ${i} </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
  <c:if test="${page != 1}">
 <a href="Controller?command=user_list&sort=${sort}&sortorder=${sortOrder}&isblocked=${isBlocked}&searchvalue=${searchValue}&itemsperpage=${itemsPerPage}&page=${page - 1}">prev</a>
  
       
    </c:if>  
    <c:if test="${page lt pagesTotal}">
        <a href="Controller?command=user_list&sort=${sort}&sortorder=${sortOrder}&isblocked=${isBlocked}&searchvalue=${searchValue}&itemsperpage=${itemsPerPage}&page=${page + 1}">next></a>
    </c:if>


</body>
</html>
