<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var listTask = {};
var listTaskDataGrid;
$(function() {
	
	$.post('${path }/credit/gerUserRole', {
    }, function(result) {
         //parent.$.messager.alert('提示', result, 'info');
         
         if(result.indexOf("xscs")!=-1 && result.indexOf("xszs")==-1){
        	 $("#div_taskStage").append("<input type='radio' name='taskStage' value='1' checked>初审任务");
        	 $("#div_taskButton").show();
         }else if(result.indexOf("xscs")==-1 && result.indexOf("xszs")!=-1){
        	 $("#div_taskStage").html("<input type='radio' name='taskStage' value='2' checked>终审任务");
        	 $("#div_taskButton").show();
         }else if(result.indexOf("xscs")!=-1 && result.indexOf("xszs")!=-1){
        	 $("#div_taskStage").html("<input type='radio' name='taskStage' value='1' checked>初审任务<input type='radio' name='taskStage' value='2'>终审任务");
        	 $("#div_taskButton").show();
         }else{
        	 $("#div_taskButton").hide();
         }
         
         listTaskDataGrid = $('#listTaskDataGrid').datagrid({
     		url : '${path}/credit/listTaskDataGrid',
             fit : true,
             striped : true,
             rownumbers : true,
             pagination : true,
             singleSelect : true,
             idField : 'caseId',
             sortName : 'createDate',
             sortOrder : 'asc',
             pageSize : 20,
             pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
             queryParams: {  
             	taskStage:$("[name='taskStage']:checked").val()
              },
             frozenColumns:[[
                     		{
             	width : '80',
                 title : '操作',
                 field : 'action',
                 sortable : true,
                 formatter : function(value, row, index) {
                 	if(row.auditStatue == '0' || row.auditStatue == '1'){
                        var ss ='审核';
                        <shiro:hasPermission name="/credit/bl">
                        ss ='补录';
                        </shiro:hasPermission>
     	            	var str = '';
     	            	str += $.formatString('<a href="javascript:void(0);" class="batch-easyui-linkbutton-ok" data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="auditFun(\'{0}\');">'+ss+'</a>',row.caseId);
     	            	return str;
                 	}
                 }
             }
                     	]],
             columns : [ [ {
                 width : '180',
                 title : '案件编号',
                 field : 'caseId',
                 sortable : true
             } ,
             {
            	    width : '80',
                 title : '任务阶段',
                 field : 'taskStage',
                 sortable : true,
                 formatter : function(value, row, index) {
                 	switch (value) {
                 	case '1':
                 		return '初审';
                 	case '2':
                 		return '终审';
                 	}
                 }
             }  ,
             {
                 width : '180',
                 title : '客户编号',
                 field : 'custId',
                 sortable : true
             }  ,
             {
                 width : '80',
                 title : '客户名',
                 field : 'custName',
                 sortable : true
             } ,
             {
                 width : '130',
                 title : '证件号码',
                 field : 'custPid',
                 sortable : true
             } ,
             {
                 width : '120',
                 title : '产品代码',
                 field : 'parentProductId'
             } ,
             {
                 width : '100',
                 title : '系统评分',
                 field : 'sysPoint',
                 sortable : true
             } ,
             {
                 width : '100',
                 title : '系统建议额度',
                 field : 'sysLimit',
                 sortable : true
             } ,
             {
                 width : '100',
                 title : '人工授信额度',
                 field : 'creditLimit',
                 sortable : true
             },
             {
                 width : '100',
                 title : '移动电话',
                 field : 'custMobile',
                 sortable : true
             } ,
             {
                 width : '60',
                 title : '审核状态',
                 field : 'auditStatue',
                 sortable : true,
                 formatter : function(value, row, index) {
                     switch (value) {
                     case '0':
                         return '进件待审批';
                      case '1':
                         return '初审通过';
                     case '2':
                         return '初审拒绝';
                     case '3':
                         return '终审通过';
                     case '4':
                         return '终审拒绝';                  
                     }
                 }
             } ,
             {
                 width : '100',
                 title : '初审员',
                 field : 'firstAuditUsername',
                 sortable : true
             },{
             	width : '100',
                 title : '终审员',
                 field : 'lastAuditUsername',
                 sortable : true
             }  ,
             {
                 width : '150',
                 title : '申请时间',
                 field : 'createDate',
                 sortable : true
             },{
             	width : '150',
                 title : '初审时间',
                 field : 'firstAuditDate',
                 sortable : true
             },{
             	width : '150',
                 title : '终审时间',
                 field : 'lastAuditDate',
                 sortable : true
             }] ],
             onLoadSuccess:function(data){
                 var ss ='审核';
                 <shiro:hasPermission name="/credit/bl">
                 ss ='补录';
                 </shiro:hasPermission>
                 $('.batch-easyui-linkbutton-ok').linkbutton({text:ss});
             }
         });
         
    }, 'JSON');

	
});

function auditFun(caseId){
	parent.$.modalDialog.openner_dataGrid = listTaskDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
	parent.$.modalDialog({
		title : '审核',
		width : 900,
		height : 600,
		maximizable:true,
		href : '${path}/credit/caseInfo?caseId='+caseId
	})
	return false;
}

function searchListTask() {
	listTaskDataGrid.datagrid('load', $.serializeObject($('#listTaskForm')));
}

listTask.getParam = function(){
	var param = {
			caseId : $("input[name='caseId']").val(),
			custName : $("input[name='custName']").val(),
			custPid : $("input[name='custPid']").val(),
			custMobile : $("input[name='custMobile']").val(),
			startDate : $("input[name='startDate']").val(),
			endDate : $("input[name='endDate']").val()
	}
	return param;
}

function exportListTask(){
	var form=$("<form>");//定义一个表单
	form.attr("style","display:none");
 	form.attr("target","_blank");
 	form.attr("method","post");
 	form.attr("action",'${path}/credit/export');
 	
 	var input1=$("<input>");
 	input1.attr("type","hidden");
	input1.attr("name","json");
 	input1.attr("value",JSON.stringify(listTask.getParam()));
 	
 	$("body").append(form);
 	form.append(input1);
 	form.submit();
}

function receiveListTask(){
	
	var taskStage = $('input:radio[name="taskStage"]:checked').val();
	
	$.post('${path }/credit/getTask', {
		taskStage : taskStage
    }, function(result) {
        if (result.code=="00") {
            parent.$.messager.alert('提示', "领取任务成功！", 'info');
            listTaskDataGrid.datagrid('reload');
        } else {
            parent.$.messager.alert('提示', result.msg, 'info');
        }
    }, 'JSON');
 	
}

function cleanListTask() {
    $('#listTaskForm input').val('');
    listTaskDataGrid.datagrid('load', {taskStage:$("[name='taskStage']:checked").val()});
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="overflow: hidden;background-color: #fff;margin-top:10px;margin-left:10px">
    	<form id="listTaskForm">
			<!-- <table>
				<tr>
					<th>案件编号:</th>
					<td><input class="easyui-textbox" type="text" name="caseId"/></td>
					<th>客户名:</th>
					<td><input class="easyui-textbox" type="text" name="custName"/></td>
				</tr>
				<tr>
					<th>移动电话:</th>
					<td><input class="easyui-textbox" type="text" name="custMobile"/></td>
					<th>证件号码:</th>
					<td><input class="easyui-textbox" type="text" name="custPid"/></td>
					<th>申请时间：</th>
					<td><input class="easyui-datetimebox" name="startDate" style="width:150px">至 
						<input class="easyui-datetimebox" name="endDate" style="width:150px"><a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchListTask();">查询</a><a href="javascript:void(0);"
						class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanListTask();">清空</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-x-circle',plain:true" onclick="exportListTask();">导出</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<td id="div_taskStage"></td>
					<td ><a id="div_taskButton" style="display:none" href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-plus icon-green',plain:true" onclick="receiveListTask();">领取任务</a></td>
						
				</tr>
			</table> -->
			<span>案件编号：</span> 
			<input class="easyui-textbox" type="text" name="caseId" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <span>客户名称：</span> 
		    <input class="easyui-textbox" type="text" name="custName" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <span>移动电话：</span> 
		    <input class="easyui-textbox" type="text" name="custMobile" /> <br>
			<span>证件号码：</span>
			<input class="easyui-textbox" type="text" name="custPid"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span>申请时间：</span>
			<input class="easyui-datetimebox" name="startDate"
				style="width: 150px"> &nbsp;至 &nbsp; <input class="easyui-datetimebox"
				name="endDate" style="width: 150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" 
				onclick="searchListTask();">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true"
				onclick="cleanListTask();">清空</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true"
				onclick="exportListTask();">导出</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</form>
		<div>
			<span id="div_taskStage"></span> <a id="div_taskButton"
				href="javascript:void(0);" class="easyui-linkbutton"
				data-options="iconCls:'fi-plus icon-green',plain:true"
				onclick="receiveListTask();">领取任务</a>
		</div>
    </div>
    <div data-options="region:'center',border:true,title:'查询结果'">
        <table id="listTaskDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
