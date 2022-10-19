<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<body>
<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.error"/></title>
</head>
<div align="center">
<img src="${pageContext.request.contextPath}/images/error.png"/> <h3><fmt:message key="label.error_message"/></h3>
<br> <h4>${error}</h4>
</div>

</body>
</html>
