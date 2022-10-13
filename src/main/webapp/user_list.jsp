<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
      <style>
         table, th, td {
            border: 1px solid black;
         }
      </style>
<body>
<jsp:include page="header.jsp" />

<head>
    <title><fmt:message key="label.user_list"/></title>
</head>

<h3><fmt:message key="user_list_jsp.label.title"/></h3>

<br>
<strong><fmt:message key="label.sortby"/></strong>
<form action="Controller" method="get">
<select name="sort">
		<option value="id" <c:if test="${sort == 'id' || sort == null}"> selected </c:if>><fmt:message key="label.id"/></option>
		<option value="surname" <c:if test="${sort == 'surname'}"> selected </c:if>><fmt:message key="label.surname"/></option>
		
		
	</select>
	
	<select name="sortorder">
		<option value="asc" <c:if test="${sortorder == 'asc' || sortorder == null}"> selected </c:if>><fmt:message key="option.ascending"/></option>
		<option value="desc" <c:if test="${sortorder == 'desc'}"> selected </c:if>><fmt:message key="option.descending"/></option>
		
		
	</select>
	
	<strong><fmt:message key="label.status"/></strong> 
	<select name="isblocked">
		<option value="null"<c:if test="${isBlocked == null}"> selected </c:if>><fmt:message key="option.all"/></option>
		<option value=true <c:if test="${isBlocked != null && isBlocked}"> selected </c:if>><fmt:message key="table.cell.status_blocked"/></option>
		<option value=false <c:if test="${isBlocked != null && !isBlocked}"> selected </c:if>><fmt:message key="table.cell.status_active"/></option>
	</select> 
	
	<strong><fmt:message key="label.searchbyvalue"/></strong> 
	
		<input name="searchvalue" value="${searchValue == null ? '': searchValue }">
		<input type = "submit" value=<fmt:message key="button.apply"/>> 
<br>
<hr>
<strong><fmt:message key="label.itemsperpage"/></strong>
<input name="itemsperpage" value="${itemsPerPage == null ? 10 : itemsPerPage }" size=2>
<br>		
<input type="hidden" name="page" value="1">			
	
	
	<input type="hidden" name="command" value="show_user_list">
	
</form>
<table>
<tr>
<th><fmt:message key="label.id"/></th><th><fmt:message key="label.name"/></th><th><fmt:message key="label.email"/></th><th><fmt:message key="label.tel"/></th><th><fmt:message key="label.role"/></th><th><fmt:message key="label.status"/></th><th><fmt:message key="label.user_page"/></th>
</tr>

  <c:forEach items="${userlist}" var="item">
    <tr>
      <td><c:out value="${item.id}" /></td>
      <td><c:out value="${item.name} ${item.surname}" /></td>
      <td><c:out value="${item.email}" /></td>
      <td><c:out value="${item.tel}" /></td>
      <td><c:out value="${item.role}" /></td>
      <td><c:out value="${item.isBlocked? 'BLOCKED' : 'ACTIVE'}"/></td>
      <td><a href = "Controller?command=show_user_info&id=${item.id}"><fmt:message key="label.user_info"/></a></td>
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
                        <a href="Controller?command=show_user_list&sort=${sort}&sortorder=${sortOrder}&isblocked=${isBlocked}&searchvalue=${searchValue}&itemsperpage=${itemsPerPage}&page=${i}"> ${i} </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
  <c:if test="${page != 1}">
 <a href="Controller?command=show_user_list&sort=${sort}&sortorder=${sortOrder}&isblocked=${isBlocked}&searchvalue=${searchValue}&itemsperpage=${itemsPerPage}&page=${page - 1}"><fmt:message key="link.prev"/></a>
  
       
    </c:if>  
    <c:if test="${page lt pagesTotal}">
        <form action="Controller" method="get">
 <h:userlistpageparam/>
<input type="hidden" name="page" value="${page + 1}">

		<input type="submit" value=">>">
		
</form>
        
        
      
    </c:if>


</body>
</html>
