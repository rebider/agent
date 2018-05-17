<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
var spillDataGrid;
    $(function() {
    	spillDataGrid = $('#spillDataGrid').datagrid({
    		url : '${path }/repayPlan/spilldataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect :false,
            idField : 'id',
            sortName : 'flowId',
	        sortOrder : 'asc',
            pageSize : 20,
            checkOnSelect: true, selectOnCheck:true,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '100',
                title : '流水ID',
                field : 'flowId',
                sortable : true
            },
            {
                width : '80',
                title : '分配序号',
                field : 'allocNum',
                sortable : true
            } ,
            {
                width : '150',
                title : '贷款编号',
                field : 'loanId',
                sortable : true
            }  ,
            {
                width : '150',
                title : '合同编号',
                field : 'contractId',  
                sortable : true
            } , 
            {
                width : '80',
                title : '分配类型',
                field : 'allocType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'O':
                        return '还款计划';
                    case 'S':
                        return '随借随还借据';
                    case 'F':
                        return '分期借据';
                    }
                }
            },
            
            {
                width : '150',
                title : '还款计划ID',
                field : 'planId',
                sortable : true
            },
            {
                width : '150',
                title : '客户号',
                field : 'custId',
                sortable : true
            },
            {
                width : '80',
                title : '分配类型',
                field : 'feeType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'O':
                        return '上期未还清';
                    case 'Y':
                        return '本金';
                    case 'V':
                        return '利息';
                    case 'B':
                        return '砍头息';
                    case 'E':
                        return '服务费';
                    case 'P':
                        return '逾期罚息';
                    case 'W':
                        return '违约金';
                    case 'G':
                        return '溢缴款';
                    case 'S':
                        return '单边账';
                    case 'X':
                        return '提前还款手续费';
                    case 'Z':
                        return '逾期管理费';
                    }
                }
            },
            {
                width : '80',
                title : '分配金额',
                field : 'amount',
                sortable : true
            },
            {
                width : '100',
                title : '处理方式',
                field : 'processType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A':
                        return '调账转入';
                    case 'B':
                        return '已冲正';
                    case 'E':
                        return '已转出处理(溢交款)';
                    case 'P':
                        return '已分配(溢交款)';
                    case 'S':
                        return '单边账';
                    }
                }
            },
            
            
            
            {
                width : '150',
                title : '处理时间',
                field : 'updateDate',
                sortable : true
            },
            {
                width : '80',
                title : '分配状态',
                field : 'allocStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '正常分配';
                    case 'Y':
                        return '逾期分配';
                    case 'L':
                        return '临时分配';
                    }
                }
            },
            
            {
           	 field : 'action',
             title : '操作',
             width : 180,
             /* formatter : function(value, row, index) {
                 var str = '';
                     <shiro:hasPermission name="/finance/adjust">
                     str += $.formatString('<a href="javascript:void(0)" class="link-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="addAdjust(\'{0}\',\'{1}\');" >费用调整</a>', row.planId,row.palnDetailId);
                     </shiro:hasPermission>
                 return str;
             } */
         } ] ],
         /* onLoadSuccess:function(data){
        	 $('.link-easyui-linkbutton-edit').linkbutton({text:'费用调整'});
         },
            toolbar : '#subproductToolbar' */
     });
   });
	function searchSpillPlan() {
        spillDataGrid.datagrid('load', $.serializeObject($('#searchSpillForm')));
	}
	function cleanSpillPlan() {
		$('#searchSpillForm input').val('');
        spillDataGrid.datagrid('load', {});
	}

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
		<form id="searchSpillForm">
			<table>
				<tr>
					<th>流水号:</th>
					<td><input name="flowId" placeholder="请输入还款计划编号"/></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>合同编号:</th>
					<td><input name="contractId" placeholder="请输入合同编号"/></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>客户号:</th>
					<td><input name="custId" placeholder="请输入客户号"/></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>还款计划编号:</th>
					<td><input name="planId" placeholder="请输入还款计划编号"/></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchSpillPlan();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanSpillPlan();">清空</a>
					</td>

				</tr>
			</table>
		</form>
	</div>
   <div data-options="region:'center',border:true,title:'溢缴款查询'">
		<table id="spillDataGrid" data-options="fit:true,border:false"></table>
	</div>
</div>

