<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    
    <title>My JSP 'success.jsp' starting page</title>
    
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
<div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
<script language="JavaScript">
$(document).ready(function() { 
   var data = {
      table: 'datatable'
   };
   var chart = {
      type: 'column'
   };
   var title = {
      text: '从网页中的 HTML 表格中读取数据'   
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
   $('#container').highcharts(json);
});

</script>
<table id="datatable">
<thead>
<tr><th></th><th>Jane</th><th>John</th><th>贼好吃</th></tr>
</thead>
<tbody>
<tr><th>关于XXX的投票</th><td>3</td><td>4</td><td>5</td></tr>

</tbody>
</table>
  </body>
</html>
