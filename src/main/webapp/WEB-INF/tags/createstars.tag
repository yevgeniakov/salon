<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="rating" required="true"%>
<%@ attribute name="inputname" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="intRating" value="${(rating/0.25).intValue() }" scope="page" />
<c:forEach var="i" begin="1" end="${intRating - 1}">
	<input name="${inputname}" type="radio" class="star {split:4}"
		disabled="disabled" />
</c:forEach>
<input name="${inputname}" type="radio" class="star {split:4}"
	disabled="disabled" checked="checked" />
<c:forEach var="i" begin="1" end="${20- intRating}">
	<input name="${inputname}" type="radio" class="star {split:4}"
		disabled="disabled" />
</c:forEach>