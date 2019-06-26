<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<ul class="pagination ">
  	 <c:choose>
    	 <c:when test="${nowPage-1>=1}">
     		<li><a href="${pageLink}?currentPage=${nowPage-1}">&laquo;</a></li>
    	 </c:when>
     	<c:otherwise>
     	<li class="disabled"><a href="#">&laquo;</a></li>
     	</c:otherwise>
     </c:choose>

    <li class="active"><a href="#"> <c:out value="当前第  ${nowPage}  页"></c:out></a></li>
     <c:choose>
    	 <c:when test="${nowPage+1 <= pageSize}">
     		<li><a href="${pageLink}?currentPage=${nowPage+1}">&raquo;</a></li>
    	 </c:when>
     	<c:otherwise>
     <li class="disabled"><a href="#">&raquo;</a></li>
     	</c:otherwise>
     </c:choose>
     </ul>
  </body>
  
</html>
