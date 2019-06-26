<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>创建投票</title>
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
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-2 column" ></div>
			<div class="col-md-8 column" style="margin-top:20px;">
				
				<div>
					<form id="myform" action="submit.do" method="post" class="form-horizontal" role="form">
						<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">主标题</label><div class="col-sm-10"><input type="text" value="${mainTitles}" id="mainTitle"
							name="mainTitle"  class="form-control" placeholder="请输入主标题" /></div>
							</div>
					</form>
				</div>
				<div class="row">
					<c:forEach items="${voteContentlist}" var="mainlist"
						varStatus="stats">
						<div id="myAlert" class="alert alert-success">


						<a href="delete.do?listIndex=${stats.index}" class="close" data-dismiss="alert">&times;</a>
  	 		 <strong><c:out value="${mainlist.subTitle}" /> </strong>
				<c:out value="${mainlist.contentType}" />
						<c:forEach items="${mainlist.contentText}" var="subContent">
							<c:out value="${subContent}" />
						</c:forEach>

						</div>
					</c:forEach>
					<br />

				</div>
				<div>
					<form id="myform1" action="add.do" method="post" class="form-horizontal" role="form">
					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">子标题 </label><div class="col-sm-10"><input type="text" class="form-control" placeholder="请输入子标题" name="subTitle" /></div></div>
					<div class="form-group">	<label for="firstname" class="col-sm-2 control-label">类型</label> <div class="col-sm-10"> <select class="form-control" name="contentType">
							<option selected="selected" value="RADIO">单选</option>
							<option value="CHECKBOX">多选</option>
							<option value="CONTENTS">填空</option>
						</select><br /></div>
					</div>
					<div class="form-group">
						<div id="myContent">
							<label for="firstname" class="col-sm-2 control-label">选项1</label><div class="col-sm-10" ><input name="contentText[0]" type="text" class="form-control"/></div>
						</div>
					</div>
						<br />
					<div class="form-group">
						
							<div class="btn-group btn-group-justified">
								<div class="btn-group" > <input class="btn  btn-info" type="button" value="增加选项" id="addBtn" /></div>
								<div class="btn-group"><input type="button" id="delBtn" value="删除选项" class="btn btn-danger" /></div> 
							    <div  class="btn-group"><input type="button" id="submitAdd" value="保存子投票" class="btn btn-primary" /></div> 
							</div>
						
					</div>
					<div class="form-group row" >
						<div class="col-dm-2"></div>
						<div class="col-dm-10">
							
							<div class="col-dm-5"><input type="button" class="btn btn-success btn-lg btn-block" value="保存投票" id="submitbtn" /> </div> 
						
						</div>
						<input type="hidden" name="mainTitle1" id="mainTitle1" />
					</div>	
					</form>
				</div>
			</div>
			<div class="col-md-2 column"></div>
		</div>
	</div>
</body>
<script>
  	$(function(){
  	
  	 var num=1;
  	 	$("#addBtn").click(function(){
  	 		$("#myContent").append('<label for="firstname" class="col-sm-2 control-label">选项'+(num+1)+'</label><div class="col-sm-10" ><input name="contentText['+num+']" type="text" class="form-control"/></div>');
  	 		//$("#myContent").append('选项'+num+':'+'<input type="text" name="contentText['+num+']" /><br/>');
  	 		num++;
  	 	});
  	 $("#delBtn").click(function(){
  	 if(num>1){
  	   	$("#myContent").find("div").last().remove();
  	 	$("#myContent").find("label").last().remove();
  	 	num--;
  	 }

  	 });
  	 	
  	 //
  	 $("#submitbtn").click(function(){
  	 	submitAll();
  	 });
  	 $("#submitAdd").click(function(){
  	 	$("#mainTitle1").val($("#mainTitle").val());
  	 	submitAdd();
  	 });
  	});
  	function submitAdd(){
  		$("#myform1").submit();
  	}
  	function submitAll(){
  		$("#myform").submit();
  	}
  </script>
</html>
