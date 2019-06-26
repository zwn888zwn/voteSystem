<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册页面</title>
    
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

   <h1>用户注册：</h1>

   <div class="row" >
      <div class="col-md-6 col-md-offset-3" >

       <form action="" method="post"  role="form">
    	<p id="showResult" ></p>
    	  <div class="form-group">
   			 <label for="name">用户名：</label>
   			 <input type="text" id="id" name="id" class="form-control"  placeholder="请输入用户名"/><input id="check"  class="btn btn-default" type="button" value="检查账号"/><br/> 
				</div>
			 <div class="form-group">
				 <label for="name">密码：</label>
				 <input type="password" name="password"  class="form-control"  placeholder="请输入密码"/>
			 </div>
			 <div class="form-group">
				 <label for="name">重复输入密码：</label>
				 <input type="password" name="password1"  class="form-control"  placeholder="请重复输入密码"/>
				</div>
			 <div class="form-group">
				 <label for="name">姓名：</label>
				 <input type="text" name="username"  class="form-control"  placeholder="请输入姓名"/>
				</div>
			 <div class="form-group">
				 <label for="name">性别：</label>
				   <label class="radio-inline">
						<input type="radio" name="sex" value="0" checked="checked"/>	男				
					</label>
					<label class="radio-inline">
						<input type="radio" name="sex" value="1"/>				女	
					</label>
			</div>

			 <div class="form-group">
				 <label for="name">年龄：</label>
				 <input type="text" name="age" class="form-control"  placeholder="请输入年龄"/>
				</div>
			 <div class="form-group">
				 <label for="name">QQ：</label>
				 <input type="text" name="qq" class="form-control"  placeholder="请输入QQ"/>
				</div>
				
    	<input type="submit" class="btn btn-primary" value="注册"/> <input type="reset" class="btn btn-warning" value="重置" />
    </form>

   		</div>
	</div>
  
   
  </body>
  <script type="text/javascript">
  	$(function(){
  		$("#check").click(function(){
  			var id=$("#id").val();
  			$.ajax({
  				url:'registe/check.do',
  				data:{ "id":id},	
  				success:function(data){
  					var obj=eval('('+data+')');
  					var results=obj.result;
  					if(results == 0){
  						$("#showResult").css("color","green");
  						$("#showResult").text('该账号可以使用');
  					}
  					else{
  						$("#showResult").css("color","red");
  						$("#showResult").text("该账号已被注册!!!");
  					}
  				},
  				error:function(){
					alert('错误!');
				}
  				
  			
  			});
  		});
  	
  	});
  </script>
</html>
