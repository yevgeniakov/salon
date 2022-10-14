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
    <title><fmt:message key="label.service_list"/></title>
</head>

<h3><fmt:message key="label.service_list"/></h3>
<br>

<table>
<tr>
<th><fmt:message key="label.service_name"/></th><th><fmt:message key="label.info"/></th><th><fmt:message key="label.masters"/></th>
</tr>
  <c:forEach items="${servicelist}" var="item">
    <tr>
      <td><c:out value="${item.name}" /></td>
      <td><c:out value="${item.info}" /></td>
      <td><a href = "Controller?command=show_masters_of_service&service_id=${item.id}"><fmt:message key="link.show_masters"/></a></td>
    </tr>
  </c:forEach>
</table>





</body>
</html>
