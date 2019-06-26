<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线投票系统</title>
<link rel="stylesheet" type="text/css" href="css/indexstyle.css" />
</head>
  
  <body>
<div class="wallpaper">
	<!--头部内容开始-->
    <div id="jt-top">
        <div class="jt-logo">
            <a href="#"><img src="imgs/logo.png"></a>
        </div>
    
        <div class="jt-edit">
        	<span>${userSession.username}，欢迎登录</span>
        	
            <ul>
                <li class="js_li"><a href="logout.do" class="jl_a"><i class='dataedu-icon'>&#xe6d6;</i></a></li>
            </ul>
        </div>
        
    </div>
    <!--头部内容结束-->

    <div class="jt-user">
                <div class="jt-middle">
                    <div class="user-info">
                        <div class="pic"><img src="imgs/pic.jpg"/></div>
                    </div>
                    <div class="jm-info">
                        <h1>${userSession.username}</h1>
                        <p>${userSession.id}</p>
                        <p>${userSession.authority ==1?'管理员':'普通用户'}</p>
                    </div>
                    <ul>
                        <li>
                            <span class="span_block">
                                <!-- <h5></h5>
                                <p></p> -->
                                <h5>${joinCount}</h5>
                                <p>参与投票数</p>
                            </span>
                        </li>
                        <li>
                            <span class="span_block">
                                <h5>${createCount} </h5>
                                <p>发起投票数</p>
                            </span>
                        </li>
                        <li>
                            <span class="span_block">
                             <!--    <h5></h5>
                                <p></p> -->
                                
                            </span>
                        </li>
                        <li>
                            <span class="span_block">
                                
                            </span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="content">
            	<div class="middle">
            	
                        	<div class="m1_left">
                            	<span class="cont_title">最新投票<!--  <i><a href="javascript:;">更多</a></i>--></span>
                                <ul class="m1_right_ul">
                                	<c:forEach items="${newestVoteList}" var="arr">
                                		<li><span class="arr"><img src="imgs/arr.png" /></span><span class="time">${fn:substring(arr[2],0,10)}</span><a href="showVote/showContent.do?id=${arr[0]}" target="_blank">${arr[1]}</a></li>
                                	</c:forEach>
                                </ul>
                            </div>
                            <div class="m1_right">
                            	<span class="cont_title">热门投票<!--<i><a href="javascript:;">更多</a></i>--></span>
                            	<ul class="m1_right_ul">
                                   <c:forEach items="${hotVoteList}" var="arr">
                                		<li><span class="arr"><img src="imgs/arr.png" /></span><span class="time">${fn:substring(arr[2],0,10)}</span><a href="showVote/showContent.do?id=${arr[0]}" target="_blank">${arr[1]}</a></li>
                                	</c:forEach>
                                </ul>
                            </div>
                        
            	</div>
            	<div class="middle">
                	<div class="middle_left">
                    	
                        <div class="left_m2">
                        	<span class="cont_title">日程表</span>
                            <div class="data">
                            <!-- 日历 -->
            <div id="CalendarMain" class="border_style">
                <div id="title"> 
                    <a class="selectBtn month" href="javascript:" onClick="CalendarHandler.CalculateLastMonthDays();"><</a><a class="selectBtn selectYear" href="javascript:" onClick="CalendarHandler.CreateSelectYear(CalendarHandler.showYearStart);">2014年</a><a class="selectBtn selectMonth" onClick="CalendarHandler.CreateSelectMonth()">6月</a> <a class="selectBtn nextMonth" href="javascript:" onClick="CalendarHandler.CalculateNextMonthDays();">></a><a class="selectBtn currentDay" href="javascript:" onClick="CalendarHandler.CreateCurrentCalendar(0,0,0);">今天</a>
                </div>
                <div id="context">
                    <div class="week">
                        <h3> 一 </h3>
                        <h3> 二 </h3>
                        <h3> 三 </h3>
                        <h3> 四 </h3>
                        <h3> 五 </h3>
                        <h3> 六 </h3>
                        <h3> 日 </h3>
                    </div>
                    <div id="center">
                        <div id="centerMain">
                            <div id="selectYearDiv"></div>
                            <div id="centerCalendarMain">
                                <div id="Container"></div>
                            </div>
                            <div id="selectMonthDiv"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 日历 -->
                            </div>
                        </div>
                    </div>
                    <div class="middle_right">
                    	
                        <div class="right_m2">
                        	<span class="cont_title">应用系统<!--<i><a href="javascript:;">更多</a></i>--></span>
                            <ul class="m2_ul">
								<a href="createVote/index.do" target="_blank">
                            	<li>
                                	<span><img src="imgs/icon-spxt.png" /></span>
                                    <p>发起投票</p>
                                </li>
								</a>
								<a href="showVote/showAll.do" target="_blank">
                                <li>
                                	<span><img src="imgs/icon-bgxt.png" /></span>
                                    <p>查看投票</p>
                                </li>
                                </a>
                                <a href="showVote/showAllResult.do" target="_blank">
                                <li>
                                	<span><img src="imgs/icon-tsgxt.png" /></span>
                                    <p>显示结果</p>
                                </li>
                                </a>
                                <a href="manageVote/show.do" target="_blank">
                                <li class="no_right">   
                                	<span><img src="imgs/icon-jwxt.png" /></span>
                                    <p>管理投票</p>
                                </li>
                                </a>

                               
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

    <!--页脚开始-->
    <div id="jt-foot">
        <div class="jf-1000">
            <p class="jf-top">版权所有 Copyright 2012 All rights reserved</p>
            <ul>
            	
            </ul>
        </div>
    </div>
    <!--页脚结束-->
</div>
  </body>
</html>
<script src="js/main.js"></script>
<script src="js/owl.carousel.min.js"></script>