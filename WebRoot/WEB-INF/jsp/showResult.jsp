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
<script language="JavaScript">
function showCharts(tableName,containerName,subTitle) { 
   var data = {
      table: tableName
   };
   var chart = {
      type: 'column'
   };
   var title = {
      text: subTitle   
   };      
   var yAxis = {
      allowDecimals: false,
      title: {
         text: 'Units'
      }
   };
   var tooltip = {
      formatter: function () {
         return '<b>' + this.series.name + '</b><br/>' +
            this.point.y + ' ' + this.point.name.toLowerCase();
      }
   };
   var credits = {
      enabled: false
   };  
      
   var json = {};   
   json.chart = chart; 
   json.title = title; 
   json.data = data;
   json.yAxis = yAxis;
   json.credits = credits;  
   json.tooltip = tooltip;  
   $('#'+containerName).highcharts(json);
}

</script>
  <body>
  	<div class="container-fluid">
  	<form action="saveVote.do" method="post">
  	<div class="row">
	  	<div class="col-md-3 col-md-offset-4 column ">
		  	<h2> 主标题：${vote.mainTitle} </h2>
		  	<h3> 创建人：${vote.voteCreater} 	</h3>
	  	</div>
	  	<div class="col-md-1" >
			 <a href="showAnalysisPage.do?id=${vote.voteID}" class="btn btn-primary btn-lg" role="button" target="_blank">结果分析</a>
	  	</div>
  	</div>
  	 <c:set var="countSub" value="0"></c:set>
  	 <c:set var="tempCount" value="0"></c:set>
  	 
  	 <c:forEach items="${vote.allContent}" var="lists" varStatus="Mainstatus" >
  		<div class="row clearfix">
  		<hr>
  	 	<h4><c:out value="${lists.subTitle}"></c:out><br/></h4>
  	 	
  	 	<c:choose>
 	 		
			<c:when test="${lists.contentType =='CONTENTS'}">
 				<div class="col-md-3 col-md-offset-1 column ">	<a href="showBlankResult.do?id=${vote.voteID}">查看更多</a></div>
			</c:when> 	
  	 		<c:otherwise>
  	 			<div class="col-md-3 col-md-offset-1 column">
  	 			<table class="table table-hover">
  	 			<thead>
     				 <tr>
        			 <th>选项名</th>
        			 <th>得票数</th>
     				 </tr>
  			    </thead>
  			    <tbody>
  	 			<c:forEach items="${lists.contentText}" var="subContent" varStatus="status">
  	 				<tr>
					<c:if test="${subContent !=null}">
 						 <td><c:out value="${subContent}"></c:out></td>
 						 <td> ${countList[tempCount][2] ==null?0:countList[tempCount][2]}<br/></td>
 					</c:if>
 					<c:set var="tempCount" value="${tempCount+1}"></c:set>
 					</tr>
 				</c:forEach>
 				</tbody>
 				</table>
 				</div>
 				<c:set var="tempCount" value="${countSub}"></c:set>
 				<div class="col-md-3  col-md-offset-1 column">
 				<div id="container${Mainstatus.count}" style="width: 550px; height: 400px; margin: 0 auto"></div>
 				</div>
 				<div style="display:none;">
 						<table id="datatable${Mainstatus.count}">
							<thead>
								<tr><th></th>
								<c:forEach items="${lists.contentText}" var="subContent" varStatus="status">
  	 				
								<c:if test="${subContent !=null}">
 									<th><c:out value="${subContent}"></c:out></th>
 								</c:if>			
 								</c:forEach>
								</tr>
							</thead>
							<tbody>
								<tr><th><c:out value="${lists.subTitle}"></c:out></th>
								<c:forEach items="${lists.contentText}" var="subContent" varStatus="status">
  	 				
								<c:if test="${subContent !=null}">
 									<td>${countList[tempCount][2]}</td>
 								</c:if>
 								<c:set var="tempCount" value="${tempCount+1}"></c:set>
 					
 								</c:forEach>
 								
								</tr>

							</tbody>
						</table>
 				</div>

					<c:set var="countSub" value="${tempCount}"></c:set>
					<script language="JavaScript">
						$(function(){
							showCharts('datatable${Mainstatus.count}','container${Mainstatus.count}','${lists.subTitle}');
						});
					</script>				
  	 		</c:otherwise>	 	
  	 	</c:choose>	
  	 	 </div>
  	 </c:forEach>
  	
  	<input type="hidden" value="${vote.voteID}" name="voteID" />

	</form>
	
	</div>
  </body>
  
</html>
