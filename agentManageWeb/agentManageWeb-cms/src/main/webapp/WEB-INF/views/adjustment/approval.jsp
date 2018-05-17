<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var approvalDate;
    $(function() {
        approvalDate = $('#approvalDate').datagrid({
            url : '${path}/approval/find',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'jobId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '150',
                title : 'ID',
                field : 'pk',
                sortable : true
            },{
                width : '150',
                title : '交易流水号',
                field : 'flowId',
                sortable : true
            },  {
                width : '155',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            },{
                width : '120',
                title : '创建日期',
                field : 'createDate',
                sortable : true
            },{
                width : '120',
                title : '调整时间',
                field : 'adjustmentTime',
                sortable : true
            },{
                width : '60',
                title : '调整金额',
                field : 'amount',
                sortable : true
            },{
                width : '60',
                title : '资金流向',
                field : 'moneyFlow',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '增加';
                    case 'C':
                        return '减少';
                    }
                }
            },{
                width : '65',
                title : '交易类型',
                field : 'transCode',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A001':
                        return '借款合同申请';
                    case 'B001':
                        return '合同撤销';
                    case 'A002':
                        return '放款申请';
                    case 'B002':
                        return '放款冲正';
                    case 'A003':
                        return '还款';
                    case 'B003':
                        return '还款冲正';
                    case 'A004':
                        return '本金调整';
                    case 'A005':
                        return '利息调整';
                    case 'A006':
                        return '砍头息调整';
                    case 'A007':
                        return '管理费调整';
                    case 'A008':
                        return '逾期利息调整';
                    case 'A009':
                        return '违约金调整';
                    case 'B004':
                        return '息费调整冲正';
                    case 'A010':
                        return '溢交款转出';
                    case 'B010':
                        return '溢交款转出冲正';
                    case 'A011':
                        return '提前还款';
                    case 'C010':
                        return '利息计提';
                    }
                }
            } ,{
                width : '65',
                title : '状态',
                field : 'adjustmentStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'C':
                        return '处理中';
                    case 'Y':
                        return '通过';
                    case 'O':
                        return '拒绝';
                    }
                }
            },{
                width : '50',
                title : '申请人',
                field : 'applicant',
                sortable : true
            },{
                width : '50',
                title : '审批人',
                field : 'approver',
                sortable : true
            },{
                width : '120',
                title : '审批时间',
                field : 'approvalTime',
                sortable : true
            },{
                field : 'action',
                title : '手工调账',
                width : 120,
                formatter : function(value, row, index) {
                     var str = '';
                        <shiro:hasPermission name="/approval/edit">
                        if (row.adjustmentStatus == 'C'){
                            str += $.formatString('<a href="javascript:void(0)" id ="action" class="batch-easyui-linkbutton-ok" data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="editPkFun(\'{0}\');" >审批</a>', row.pk);
                        }
                        </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({approval:'审批'});
            },
            toolbar : '#approvalroleToolbar'
        });
    });
    

    function editPkFun(pk) {
        if (pk == undefined) {
            var rows = approvalDate.datagrid('getSelections');
            pk = rows[0].pk;
        } else {
            approvalDate.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '调账审批明细',
            width : 500,
            height : 600,
            href : '${path}/approval/editPage?pk='+ pk,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = approvalDate;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#approvalEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchFun() {
    	approvalDate.datagrid('load', $.serializeObject($('#examineSearchForm')));
    }
    function cleanFun() {
        $('#examineSearchForm input').val('');
        approvalDate.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="examineSearchForm">
            <table>
                <tr>
                     <th>交易流水:</th>
                    <td><input name="flowId" placeholder="请输入还款计划编号"/></td>
                    <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                    <td>审批状态</td>
                	<td>   
		                <select name="adjustmentStatus">
		                    <option value="">请选择状态</option>  
			                <option value="Y">通过</option>   
			                <option value="O">拒绝</option>
			                <option value="C">处理中</option>
		             	</select>
		            </td>  
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'账务调整列表'" >
        <table id="approvalDate" data-options="fit:true,border:false"></table>
    </div>
</div>