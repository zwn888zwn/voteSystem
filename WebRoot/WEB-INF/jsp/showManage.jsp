<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="include.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>投票管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script src="${appContextPath}/ligerUI/js/core/base.js" type="text/javascript"></script> 
    <link  rel="stylesheet" href="${appContextPath}/LigerUi/ligerUI/skins/Aqua/css/ligerui-all.css"  type="text/css" /> 
    <script src="${appContextPath}/LigerUi/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/json2.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/ligerUI/js/plugins/ligerCheckBox.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/ligerUI/js/plugins/ligerDateEditor.js" type="text/javascript"></script>
    <script src="${appContextPath}/LigerUi/ligerUI/js/plugins/ligerSpinner.js" type="text/javascript"></script>


    <script type="text/javascript">

        var statusData = [{ status: 1, text: '开启' }, { status: 0, text: '关闭'}];
        $(f_initGrid);
        var manager, g;
      
        function f_initGrid()
        {
            g = manager = $("#maingrid").ligerGrid({
                columns: [
                { display: '主键', name: 'voteID', width: 50, type: 'int', frozen: true },
                { display: '名字', name: 'mainTitle'
                   
                },
              
                { display: '创建人', name: 'voteCreater',width:150 },
                { display: '创建时间', name: 'date.time' ,width:150,
                	render:function(item){
						
                		//alert(JSON.stringify());
                	    var date=new Date(item.date.time);
                	    
                		return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
                	}
                },
                { display: '状态', width: 50, name: 'status', type: 'int',isSort: false,
                    editor: { type: 'select', data: statusData, valueField: 'status' },
                    render: function (item)
                    {
                        if (parseInt(item.status) == 1) return '开启';
                        return '关闭';
                    }
                },
                { display: '操作', isSort: false, width: 120, render: function (rowdata, rowindex, value)
                {
                    var h = "";
                    if (!rowdata._editing)
                    {
                        h += "<a href='javascript:beginEdit(" + rowindex + ")'>修改</a> ";
                        h += "<a href='javascript:deleteRow(" + rowindex + ")'>删除</a> "; 
                       
                    }
                    else
                    {
                        h += "<a href='javascript:endEdit(" + rowindex + ")'>提交</a> ";
                        h += "<a href='javascript:cancelEdit(" + rowindex + ")'>取消</a> "; 
                    }
                    return h;
                }
                }
                ],
                onSelectRow: function (rowdata, rowindex)
                {
                    $("#txtrowindex").val(rowindex);
                },
                enabledEdit: true,clickToEdit:false, isScroll: false,
                dataAction:"local", 
                url:'get.do',
                width: '100%'
            });   
        }
        function beginEdit(rowid) { 
            manager.beginEdit(rowid);
        }
        function cancelEdit(rowid) { 
            manager.cancelEdit(rowid);
        }
        function endEdit(rowid)
        {
        	
            manager.endEdit(rowid);
            var row = manager.getSelectedRow();
                $.ajax({
		    	url:'update.do',
		    	type:'post',
		    	data:{"id":row.voteID,"status":row.status},
		    	async: false,
		    	success:function(result){
		    	},
		    	error:function(){
		    	}
		  		});
        }

        function deleteRow(rowid)
        {
            if (confirm('确定删除?'))
            {
                       
                var row = manager.getSelectedRow();
                $.ajax({
		    	url:'delete.do',
		    	type:'post',
		    	data:{"id":row.voteID},
		    	async: false,
		    	success:function(result){
		    	},
		    	error:function(){
		    	}
		  		});
		  		
		  		manager.deleteRow(rowid);    
            }
        }
        var newrowid = 100;
        function addNewRow()
        {
            manager.addEditRow();
        } 
         
        function getSelected()
        { 
            var row = manager.getSelectedRow();
            if (!row) { alert('请选择行'); return; }
            alert(JSON.stringify(row));
        }
        function getData()
        { 
            var data = manager.getData();
            alert(JSON.stringify(data));
        } 
        
        
    </script>
  </head>
  
  <body  style="padding:10px">  
 <div class="l-clear"></div>
 	<h2 style="text-align:center;">后台投票管理页面</h2>
    <div id="maingrid" style="margin-top:20px"></div> <br />
       <br />
   <a class="l-button" style="width:120px" onclick="getSelected()">获取选中的值(选择行)</a>
 
   <br />
   <a class="l-button" style="width:120px" onclick="getData()">获取当前的值</a>
  <div style="display:none;">
  <!-- g data total ttt -->
</div>
</body>
  
</html>
