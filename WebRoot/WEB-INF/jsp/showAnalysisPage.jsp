<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>投票结果分析</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript">
	  let voteId;
	  let groupdata;//chartData
	  $(document).ready(function(){
		  voteId=$("#voteId").val();
		  getSubtitle();
		  showApriTable();
		  $("#submitBtn").click(function(){
			 let value1=$("#subtitleX").find("option:selected").val();
			 let value2=$("#subtitleY").find("option:selected").val();
			 if(value1==value2){
				 alert("无法对两个相同的题目进行分析，请重新选择！");
				 $("#subtitleY").focus();
				 return;
			 }; 
			 showtable(value1,value2);
		  });
		  
	  });
	  function doConfidenceClick(obj){
		  //console.log(obj);
		  let target=$(obj).attr("target");
		  let originStr=$(obj).attr("originStr");
		  		  $.ajax({
		  			  type: "POST",
		  			  url: "showConfidence.do", 
					  data:{"target":target,"originStr":originStr,"id":voteId},
		  			  success:function(data){
		  			  	let allcontent=data.vote.allContent;
						let conArr=data.confidence;
						let targetArr=data.targetName.split("-");
						let targetText;
						for(let k=0;k<allcontent.length;k++){
							if(allcontent[k].contentId==targetArr[0]){
								targetText=allcontent[k].contentText[targetArr[1]];
								break;
							}
						}
						
						let showHtml='<ul class="list-group">';
						for(let i=0;i<conArr.length;i++){
							let valArr=conArr[i].key.split("-");
							for(let k=0;k<allcontent.length;k++){
								if(allcontent[k].contentId==valArr[0]){
									showHtml+='<li class="list-group-item">';
									showHtml+='<span class="badge">'+(conArr[i].value/data.targetValue*100).toFixed(2)+'%</span>';
									showHtml+=targetText;
									showHtml+='  -->  ';
									showHtml+=allcontent[k].contentText[valArr[1]];
									showHtml+='</li>';
									break;
								}
							}

							
							
						}
						showHtml+='</ul>';
						$(".modal-body").html(showHtml);
						
						$('#myModal').modal('show');
		  			  }
		  			  });
		  
	  }
	  
	  function showApriTable(){
		   $.ajax({
		  			  type: "GET",
		  			  url: "showVoteApiMap.do?id="+voteId, 
		  			  success:function(data){
						let apriMap = data.apriMap;
						let allcontent = data.vote.allContent;
						let tableHtml='<table id="anatable" class="table table-bordered" >';
		  				for(let item in apriMap){//1-1 2-1 2-2 
							let nameArr=item.split(" ");
							tableHtml+='<tr >';
							for(let i=0;i<nameArr.length;i++){//1-1 
								if(!(nameArr[i]==null || nameArr[i] == "")){
									let valArr=nameArr[i].split("-");
									for(let k=0;k<allcontent.length;k++){
										if(allcontent[k].contentId==valArr[0]){
											tableHtml+='<td class="btn btn-default btn-lg btn-block" onclick="doConfidenceClick(this);"  target="'+nameArr[i]+'"  originStr="'+item+'"  >'+ allcontent[k].subTitle + allcontent[k].contentText[valArr[1]]+'</td>';
											break;
										}
										
									}
									
								}
							}
							tableHtml+='<td>支持度：'+ (apriMap[item]/data.size*100).toFixed(2)+'%</td>';
							tableHtml+='</tr>';
						}
						tableHtml+='</table>';
						$("#anatable").html(tableHtml);
						
		  			  }
		  			  });
		  
	  }
	  
	  function getSubtitle(){
		  $.ajax({
			  type: "GET",
			  url: "getVoteSubtitle.do?id="+voteId, 
			  success:function(data){
			  	let allContent=data.vote.allContent;
				$("#subtitleX").empty();
				$("#subtitleY").empty();
				let displayNo=1;
				for (let i=0;i<allContent.length;i++) {
					if(allContent[i].contentType.value=="CONTENTS"){
						continue;
					}
					$("#subtitleX").append('<option value="'+allContent[i].contentId+'">'+displayNo+'. '+allContent[i].subTitle+'</option>');
					$("#subtitleY").append('<option value="'+allContent[i].contentId+'">'+displayNo+'. '+allContent[i].subTitle+'</option>');
					displayNo++;
				}
			  }
			  });
	  }
	  
	  function showtable(subtitle1,subtitle2){
		  $.ajax({
			  type: "POST",
			  url: "showVoteTable.do", 
			  data:{"id":voteId,"subtitle1":subtitle1,"subtitle2":subtitle2},
			  success:function(data){
			  	console.log(data);			  	
				let table=data.table;
				let allContent=data.vote.allContent;
				let xTitleId=table[0][0],yTitleId=table[0][2];
				let xTitleArr,yTitleArr;
				//标记数组对应
				for (let i=0;i<allContent.length;i++) {
					if(allContent[i].contentId==xTitleId){
						xTitleArr=allContent[i].contentText;
					}
					if(allContent[i].contentId==yTitleId){
						yTitleArr=allContent[i].contentText;
					}
				} 
				//生成二维数组
				let arr = new Array(xTitleArr.length); //表格有X行
				for(let i = 0;i < arr.length; i++){
					arr[i] = new Array(yTitleArr.length+1); //每行有X列  ---+1多一个小计
					for(let j=0;j<yTitleArr.length+1;j++){
						arr[i][j]=0;
					}
				}
				//table统计到arr
				for(let i=0;i<table.length;i++){
						arr[table[i][1]][table[i][3]]+=table[i][4];
				}
				//小计求和
				for(let i = 0;i < arr.length; i++){	
					let sum=0;
					for(let j=0;j<arr[0].length-1;j++){
						sum+=arr[i][j];
					}
					arr[i][arr[0].length-1]=sum;
				}
				
				groupdata=formatGroupData(arr,yTitleArr,xTitleArr);//生成chart数据
				showChart(groupdata);

				let htmlStr='<table id="datatable" class="table table-hover" >';
				htmlStr+='<tr>';
				htmlStr+='<th>X/Y</th>';
				for(let i=0;i<yTitleArr.length;i++){
					htmlStr+='<th>'+yTitleArr[i]+'</th>';
				}
				htmlStr+='<th>小计</th>';
				htmlStr+='</tr>';
				for(let i = 0;i < arr.length; i++){
					htmlStr+='<tr>';
					htmlStr+='<th scope="row">'+xTitleArr[i]+'</th>';
					for(let j = 0;j < arr[0].length-1; j++){
						if(arr[i][j]==null){
							htmlStr+='<td> 0(0.00%) </td>';
						}
						else{
							htmlStr+='<td>'+arr[i][j]+ '('+ (arr[i][j]/arr[i][arr[0].length-1]*100).toFixed(2) +'%) </td>';
						}
						
					}
					htmlStr+='<td>'+arr[i][arr[0].length-1]+'</td>';
					htmlStr+='</tr>';
				}
				htmlStr+='</table>';
				
				$("#showtable").html(htmlStr);
				
			}
		  });
	  }
	  function formatGroupData(arr,xTitleArr,yTitleArr){
		  let struct,g=new Array;
		  for(let i=0;i<arr.length;i++){
			  struct={
				  name:yTitleArr[i],
				  data:arr[i].slice(0,arr[0].length-1)
			  };
			  g.push(struct);
		  }
		  return {
			  category: xTitleArr,
			  series: g
		  };
	  }
	  function showChart(groupdata){
		  
		  
		  var chart = Highcharts.chart('container', {
			chart: {
				type: 'column'
			},
			title: {
				text: '堆叠柱形图'
			},
			xAxis: {
				categories: groupdata.category
			},
			yAxis: {
				min: 0,
				title: {
					text: '交叉分析'
				},
				stackLabels: {  // 堆叠数据标签
					enabled: true,
					style: {
						fontWeight: 'bold',
						color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
					}
				}
			},
			legend: {
				align: 'right',
				x: -30,
				verticalAlign: 'top',
				y: 25,
				floating: true,
				backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
				borderColor: '#CCC',
				borderWidth: 1,
				shadow: false
			},
			tooltip: {
				formatter: function () {
					return '<b>' + this.x + '</b><br/>' +
						this.series.name + ': ' + this.y + '<br/>' +
						'总量: ' + this.point.stackTotal;
				}
			},
			plotOptions: {
				column: {
					stacking: 'normal',
					dataLabels: {
						enabled: true,
						color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
						style: {
							// 如果不需要数据标签阴影，可以将 textOutline 设置为 'none'
							textOutline: '1px 1px black'
						}
					}
				}
			},
			series: groupdata.series
		});
	  }

  
  </script>
  </head>
  
  <body>
    <h2>关联分析</h2>
   <div id="anatable">
	   
   </div>
  <div>
	  <h2>交叉分析</h2>
	   <select id="subtitleX" class="selectpicker" title="请选择交叉分析的X列">
	   	  <option value="volvo">Volvo</option>
	  	  <option value="saab">Saab</option>
	  	  <option value="opel">Opel</option>
	  	  <option value="audi">Audi</option>
	   </select>
	  <span class="glyphicon glyphicon-remove"></span>
	   <select id="subtitleY" class="selectpicker" title="请选择交叉分析的Y列">
	   	  <option value="volvo">Volvo</option>
	  	  <option value="saab">Saab</option>
	  	  <option value="opel">Opel</option>
	  	  <option value="audi">Audi</option>
	   </select>
	   <button type="button" id="submitBtn" class="btn btn-default" >交叉分析</button>
  </div>
  
 
 <div id="showtable">
	 
 </div>
 <div id="container" style="min-width:400px;height:400px">
	 
 </div>

<!-- 模态框（Modal） -->
 <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					置信度
				</h4>
			</div>
			<div class="modal-body">
				按下 ESC 按钮退出。
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" 
						data-dismiss="modal">关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

 <input type="hidden" id="voteId" value="${param.id}" />
  </body>
  
</html>
