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
  <div class="container">
	<div class="row clearfix">
	<div class="col-md-3 column">
	</div>
	<div class="col-md-4 column">
		
  	<form action="saveVote.do" method="post" role="form" class="form-horizontal">
  	<h2> 主标题：${vote.mainTitle} </h2>
  	<h3> 创建人：${vote.voteCreater} 	</h3>
  	 <c:forEach items="${vote.allContent}" var="lists" varStatus="status" >
  	 
  			 
  	 	 <label for="name"><c:out value="${status.count}"></c:out>、<c:out value="${lists.subTitle}"></c:out></label>
  	 	<c:choose>
			<c:when test="${lists.contentType=='RADIO'}">
				<div class="radio">
				<c:forEach items="${lists.contentText}" var="subContent" varStatus="substatus" >
					<label>
					<c:if test="${subContent !=null}">
 					<input type="radio" value="${substatus.index}" name="${lists.contentId}"/><c:out value="${subContent}"></c:out>
 					</c:if>
 					</label>
 				</c:forEach>
 				</div>
			</c:when>  	 
			<c:when test="${lists.contentType =='CHECKBOX'}">
				<div class="checkbox">
				<c:forEach items="${lists.contentText}" var="subContent" varStatus="substatus">
					<label>
					<c:if test="${subContent !=null}">
 					<input type="checkbox" value="${substatus.index}" name="${lists.contentId}"/><c:out value="${subContent}"></c:out>
 					</c:if>
 					</label>
 				</c:forEach>
 				</div>
			</c:when> 
			<c:when test="${lists.contentType =='CONTENTS'}">
					<br />
 					<textarea name="${lists.contentId}" class="form-control"></textarea>

			</c:when> 	
  	 		<c:otherwise>未知格式</c:otherwise>
  	 	
  	 	</c:choose>
 		
 	
  	 </c:forEach>
  	<input type="hidden" value="${vote.voteID}" name="voteID" />
	
	<input type="submit" class="btn btn-default btn-lg" value="提交"/>

	</form>
	</div>
	<div class="col-md-4 column">
	</div>
	</div>
</div>
  </body>
  
</html>
