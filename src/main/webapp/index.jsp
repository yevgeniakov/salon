<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ page contentType="text/html; charset=UTF-8" %>


<html>
<body>
<jsp:include page="header.jsp" />
<head>
    <title>Best Beauty Salon</title>
<link href="css/qq.css" rel="stylesheet">
</head>

<h1 class="text-center mb-4"><fmt:message key="label.greeting"/><c:if test="${sessionScope.user != null }"><c:out value=", "></c:out><c:out value="${sessionScope.user.name}"></c:out></c:if><c:out value="!"></c:out></h1>
<section class="image-grid">
  <div class="container-xxl">
    <div class="row gy-4">
      <div class="col-12 col-sm-6 col-md-4">
        <figure>
          <a class="d-block" href="">
            <img width="1920" height="1280" src="${pageContext.request.contextPath}/images/1.jpg" class="img-fluid" alt=<fmt:message key="photo.caption1"/> data-caption=<fmt:message key="photo.caption1"/>>
          </a>
        </figure>
      </div>
      <div class="col-12 col-sm-6 col-md-4">
        <figure>
          <a class="d-block" href="">
            <img width="1920" height="1280" src="${pageContext.request.contextPath}/images/2.jpg" class="img-fluid" alt=<fmt:message key="photo.caption2"/> data-caption=<fmt:message key="photo.caption2"/>>
          </a>
        </figure>
      </div>
      <div class="col-12 col-sm-6 col-md-4">
        <figure>
          <a class="d-block" href="">
            <img width="1920" height="1280" src="${pageContext.request.contextPath}/images/3.jpg" class="img-fluid" alt=<fmt:message key="photo.caption3"/> data-caption=<fmt:message key="photo.caption3"/>>
          </a>
        </figure>
      </div>
      <div class="col-12 col-sm-6 col-md-4">
        <figure>
          <a class="d-block" href="">
            <img width="1920" height="1280" src="${pageContext.request.contextPath}/images/4.jpg" class="img-fluid" alt=<fmt:message key="photo.caption4"/> data-caption=<fmt:message key="photo.caption4"/>>
          </a>
        </figure>
      </div>
      <div class="col-12 col-sm-6 col-md-4">
        <figure>
          <a class="d-block" href="">
            <img width="1920" height="1280" src="${pageContext.request.contextPath}/images/5.jpg" class="img-fluid" alt=<fmt:message key="photo.caption5"/> data-caption=<fmt:message key="photo.caption5"/>>
          </a>
        </figure>
      </div>
      <div class="col-12 col-sm-6 col-md-4">
        <figure>
          <a class="d-block" href="">
            <img width="1920" height="1280" src="${pageContext.request.contextPath}/images/6.jpg" class="img-fluid" alt=<fmt:message key="photo.caption6"/> data-caption=<fmt:message key="photo.caption6"/>>
          </a>
        </figure>
      </div>
     </div>
  </div>
</section>

<div class="modal lightbox-modal" id="lightbox-modal" tabindex="-1">
  <div class="modal-dialog modal-fullscreen">
    <div class="modal-content">
      <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
      <div class="modal-body">
        <div class="container-fluid p-0">
          <!-- JS content here -->
        </div>
      </div>
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath}/js/qq.js"></script>
</body>

</html>