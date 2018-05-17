<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var doneTaskDataGrid;
$(function() {
	doneTaskDataGrid = $('#doneTaskDataGrid').datagrid({
		url : '${path}/credit/doneTaskDataGrid',
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
        columns : [ [ {
            width : '180',
            title : '案件编号',
            field : 'caseId',
            sortable : true
        } ,
        {
            width : '100',
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
        } ,
        {
            width : '180',
            title : '客户编号',
            field : 'custId',
            sortable : true
        } ,
        {
            width : '100',
            title : '客户名',
            field : 'custName',
            sortable : true
        } ,
        {
            width : '180',
            title : '证件号码',
            field : 'custPid',
            sortable : true
        } ,
        {
            width : '100',
            title : '产品代码',
            field : 'parentProductId'
        },
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
            width : '100',
            title : '申请状态',
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
        }  ,
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
        } ,
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
        },{
        	width : '80',
            title : '操作',
            field : 'action',
            sortable : true,
            formatter : function(value, row, index) {
            	var str = '';
            	str += $.formatString('<a href="javascript:void(0);" class="batch-easyui-linkbutton-detail" data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="auditFun(\'{0}\');">详情</a>',row.caseId);
            	return str;
            }
        }] ],
        onLoadSuccess:function(data){
            $('.batch-easyui-linkbutton-detail').linkbutton({text:'详情'});
        },
        toolbar : '#userToolbar'
    });
});

function auditFun(caseId){
	parent.$.modalDialog({
		title : '审核',
		width : 900,
		height : 600,
		href : '${path}/credit/caseInfo?caseId='+caseId
	})
	return false;
}


function searchTransFlow() {
	doneTaskDataGrid.datagrid('load', $.serializeObject($('#doneTaskForm')));
}
function cleanTask() {
    $('#doneTaskForm input').val('');
    doneTaskDataGrid.datagrid('load', {});
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="overflow: hidden;background-color: #fff">
    	<form  id="doneTaskForm" action="">
			<table>
				<tr>
					<th>案件编号:</th>
	    			<td><input style="line-height:17px;border:1px solid #ccc" name="caseId"/></td>
	    			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>客户名:</th>
	    			<td><input style="line-height:17px;border:1px solid #ccc" name="custName"/></td>
	    			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>证件号码:</th>
	    			<td><input style="line-height:17px;border:1px solid #ccc" name="custPid"/></td>
					
				</tr>
				<tr>
					<th>移动电话:</th>
					<td><input style="line-height:17px;border:1px solid #ccc" name="custMobile"/></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>申请时间：</th>
					<td><input class="easyui-datetimebox" name="startDate" style="width:150px"></td>
					<td>至</td>
					<td><input class="easyui-datetimebox" name="endDate" style="width:150px"></td>
						<td>
						<a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-magnifying-glass',plain:true"
						onclick="searchTransFlow();">查询</a> <a href="javascript:void(0);"
						class="easyui-linkbutton"
						data-options="iconCls:'fi-x-circle',plain:true"
						onclick="cleanTask();">清空</a></td>
				</tr>
			</table>
		</form>
    </div>
    <div data-options="region:'center',border:true,title:'查询结果'">
        <table id="doneTaskDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
