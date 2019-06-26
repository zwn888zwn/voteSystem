<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线投票系统</title>
<link rel="stylesheet" href="css/loginReset.css" />
<link rel="stylesheet" href="css/loginStyle.css" />
<!--[if IE 8]>
<style>
.focus{
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(
        src='imgs/bg-denglu.jpg',
        sizingMethod='scale');
    -ms-filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(
                src='imgs/bg-denglu.jpg',
                sizingMethod='scale')";
}
.login_area{
    background:#000;
    opacity:0.6;  
	filter:alpha(opacity=60); 
}
</style>
<![endif]-->

</head>
  
  <body>
    <div class="wrap">
	<div class="header">
    	<div class="header_top">
        	<div class="header_l">
            	<div class="logo"> <!-- <img src="imgs/logo-denglu.png" /> --></div>
            </div>
            <div class="header_r">
            	<!-- <img src="imgs/bg-denglu2.png" /> -->
            </div>
        </div>
    </div>
    <div class="focus">
    	<div class="login_box">
        	<div class="login_area">
            	<div class="form_box">
                   <form action="login.do" method="post" >
                   		<h4 >${tips}</h4>
                        <h2>请登录</h2>
                        <div class="user"><input type="text" placeholder="用户名" name="username" /></div>
                        <div class="user"><input type="password" placeholder="密码" name="password" /></div>
                        <input type="submit" value="登录"/>
                        <p ><a href="registe.do" target="_blank">注册用户</a></p>
                   </form>
               </div> 
            </div>
        </div>
    </div>
    <div class="footer">
        <ul>
        	<li>登录说明：</li>
        	<li>1.用户名为学号。</li>
            <li>2.初始密码为身份证后6位。</li>
            <li>3.登录后请尽快修改。</li>
        </ul>
    </div>
</div>
  </body>
</html>
