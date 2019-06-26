<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>查看投票</title>
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
  	 <c:forEach items="${pageList}" var="lists" >
  	 	<a target="_blank" class="list-group-item" href="<c:out value="showResult.do?id=${lists[0]}"></c:out>"><c:out value="${lists[1]}"></c:out></a>
 	
  	 </c:forEach>
	<%@include file="pagination.jsp" %>
	
  </body>
  
</html>
