<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<body class="d-flex flex-column h-100">
<jsp:include page="header.jsp" />
<head>
<title><fmt:message key="label.error"/></title>
</head>
<div align="center">
<img src="${pageContext.request.contextPath}/images/error.png"/> <h3><fmt:message key="label.error_message"/></h3>
<br> <h4>${error}</h4>
</div>

    <footer class="footer mt-auto py-3 bg-dark">
      <div class="container">
        <span class="text-light">Beauty Salon © 2022</span>
      </div>
    </footer>
</body>
</html>
