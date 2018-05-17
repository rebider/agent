<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var loanQueryDataGrid;
$(function() {

	loanQueryDataGrid = $('#loanQueryDataGrid').datagrid({
		url : '${path}/credit/loanQueryDataGrid',
        fit : true,
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        idField : 'orderId',
        sortName : 'orderDate',
        sortOrder : 'asc',
        pageSize : 20,
        pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
        columns : [ [ {
            width : '150',
            title : '申请件编号',
            field : 'orderId',
            sortable : true
        } ,
        {
            width : '80',
            title : '客户名',
            field : 'custName',
            sortable : true
        }  ,
        {
            width : '120',
            title : '移动电话',
            field : 'custMobile',
            sortable : true
        } ,
        {
            width : '120',
            title : '产品代码',
            field : 'parentProductId'
        } ,
        {
            width : '80',
            title : '申请金额',
            field : 'amt',
            sortable : true
        } ,
        {
            width : '120',
            title : '申请状态',
            field : 'status',
            sortable : true,
            formatter : function(value, row, index){
            	switch (value) {
            	case '1':
            		return '审批通过';
            	case '2':
            		return '审批拒绝';
            	case '3':
            		return '审批通过已通知账务放款';
            	}
            }
        } ,
        {
            width : '120',
            title : '收款账户',
            field : 'payAccount',
            sortable : true
        } ,
        
        {
            width : '120',
            title : '还款账户',
            field : 'repayAccount',
            sortable : true
        } ,
        {
            width : '120',
            title : '申请时间',
            field : 'orderDate',
            sortable : true
        } ,
        {
            width : '420',
            title : '申请结果',
            field : 'auditResult',
            sortable : true
        }
        ] ],
        toolbar : '#userToolbar'
    });
});

function searchLoanQuery() {
	loanQueryDataGrid.datagrid('load', $.serializeObject($('#loanQueryForm')));
}
function cleanLoanQuery() {
    $('#loanQueryForm input').val('');
    loanQueryDataGrid.datagrid('load', {});
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="overflow: hidden;background-color: #fff">
    	<form id="loanQueryForm">
			<table>
					<tr>
					<th>申请件编号:</th>
					<td><input style="line-height:17px;border:1px solid #ccc" name="orderId"/></td>
					<th>客户名:</th>
					<td><input style="line-height:17px;border:1px solid #ccc" name="custName"/></td>
					<th>移动电话:</th>
					<td><input style="line-height:17px;border:1px solid #ccc" name="custMobile"/></td>
					</tr>
					<tr>
					<th>产品代码:</th>
					<td><input style="line-height:17px;border:1px solid #ccc" name="parentProductId"/>
					<th>审核状态:</th>
					<td>
					<select id="status" name="status" style="width:140px;height:21px">
					      <option value="">-请选择-</option>
			              <option value="1">审批通过</option>
			              <option value="2">审批拒绝</option>
			              <option value="3">审批通过已通知账务放款</option>
			        </select>
			        <td></td>
					</td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchLoanQuery();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="cleanLoanQuery();">清空</a></td></td>
					</tr>
			</table>
			
		</form>
    </div>
    <div data-options="region:'center',border:true,title:'查询结果'">
        <table id="loanQueryDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
