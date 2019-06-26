    <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
    <%  
    String path = request.getContextPath();  
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
    %>  
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
    <html>  
     <head>  
       <base href="<%=basePath%>">  
     </head>  
      <body>  
          <div  style="text-align:center">  
               jfreechart _3D柱状图 <br><br>  
              点击显示柱状图<a href="getColumnChart.do">getMajorChart</a>  
             <br><br>  
             <img src="${chartURL}"  width=600 height=400  border=0  color=gray />  
          </div>  
            
      </body>  
    </html>  