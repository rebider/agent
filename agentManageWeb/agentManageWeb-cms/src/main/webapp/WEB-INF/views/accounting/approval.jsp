<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var springDataGrid;
    $(function() {
        springDataGrid = $('#springDataGrid').datagrid({
            url : '${path}/approval/approval',
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
                title : '还款计划编号',
                field : 'planId',
                sortable : true
            },  {
                width : '155',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            },{
                width : '155',
                title : '创建日期',
                field : 'createDate',
                sortable : true
            },{
                width : '60',
                title : '应还本金',
                field : 'amt',
                sortable : true
            },{
                width : '60',
                title : '应还利息',
                field : 'interestAmt',
                sortable : true
            },{
                width : '65',
                title : '应付砍头息',
                field : 'cutInterestAmt',
                sortable : true
            },{
                width : '65',
                title : '应付管理费',
                field : 'expenseAmt',
                sortable : true
            },{
                width : '65',
                title : '应付逾期利息',
                field : 'overdueAmt',
                sortable : true
            },{
                width : '65',
                title : '应付违约金',
                field : 'penaltyAmt',
                sortable : true
            },{
                width : '65',
                title : '减免利息',
                field : 'redInterestAmt',
                sortable : true
            },{
                width : '55',
                title : '减免砍头息',
                field : 'redCutInterestAmt',
                sortable : true
            },{
                width : '65',
                title : '减免管理费',
                field : 'redExpenseAmt',
                sortable : true
            },{
                width : '65',
                title : '减免逾期利息',
                field : 'redOverdueAmt',
                sortable : true
            }, {
                width : '65',
                title : '减免违约金',
                field : 'redPenaltyAmt',
                sortable : true
            }, {
                width : '65',
                title : '调账状态',
                field : 'adjustmentStatus',
                sortable : true
            },{
                field : 'action',
                title : '手工调账',
                width : 120,
                formatter : function(value, row, index) {
                     var str = '';
                        <shiro:hasPermission name="/approval/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-ok"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="editUserFun(\'{0}\');" >新增账务调整</a>', row.pk);
                        </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({text:'调账状态'});
            },
            toolbar : '#roleToolbar'
        });
    });


    function editUserFun(pk) {
        if (pk == undefined) {
            var rows = springDataGrid.datagrid('getSelections');
            pk = rows[0].pk;
        } else {
            springDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '调账状态',
            width : 500,
            height : 800,
            href : '${path}/approval/editPage?pk='+ pk,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = springDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#EditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchFun() {
    	springDataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    function cleanFun() {
        $('#searchForm input').val('');
        springDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                     <th>还款计划编号:</th>
                    <td><input name="planId" placeholder="请输入还款计划编号"/></td>
                    <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                    <td>审批状态</td>
                	<td>   
		                <select name="adjustmentStatus">
			                <option value="Y">通过</option>   
			                <option value="O">拒绝</option>
			                <option value="C" selected = "selected">处理中</option>
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
        <table id="springDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>