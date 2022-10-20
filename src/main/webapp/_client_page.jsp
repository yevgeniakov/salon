<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<body>
<jsp:include page="header.jsp" />
<head>
    <title>Best Beauty Salon</title>
    <link href='css/jquery.rating.css' type="text/css" rel="stylesheet" />
</head>
<h2><fmt:message key="label.greeting"/><c:if test="${sessionScope.user != null }"><c:out value=", "></c:out><c:out value="${sessionScope.user.name}"></c:out></c:if><c:out value="!"></c:out></h2>

<br>
<a href = "Controller?command=show_master_list"><fmt:message key="menu.master_list"/></a>
<br>
<a href = "Controller?command=show_service_list"><fmt:message key="menu.service_list"/></a>
<br>
<a href = "Controller?command=show_appointments_list"><fmt:message key="menu.my_appointments"/></a>
<h2>Here is some general information</h2>




<h:createstars rating="5.0" inputname="rat"/>




<br>

<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled" checked="checked"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>
<input name="adv1" type="radio" class="star {split:4}" disabled="disabled"/>



<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.0/jquery.min.js'
	type="text/javascript"></script>
<script src='js/jquery.MetaData.js' type="text/javascript"
	></script>
<script src='js/jquery.rating.js' type="text/javascript"
	></script>
</body>
</html>
