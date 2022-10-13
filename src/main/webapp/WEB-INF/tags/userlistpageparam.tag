<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<input type="hidden" name="command" value="show_user_list">
<input type="hidden" name= "sort" value="${sort}">
<input type="hidden" name= "sortorder" value="${sortOrder}">	
<input type="hidden" name= "isblocked" value="${isBlocked == null? 'null' : isBlocked}"> 	
<input type="hidden" name="searchvalue" value="${searchValue == null ? '': searchValue }">

<input type="hidden" name="itemsperpage" value="${itemsPerPage}">
