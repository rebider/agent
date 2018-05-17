<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var repayPlanInfoDataGrid;
    var debtDataGrid;
    $(function() {

        debtDataGrid = $('#debtDataGrid').datagrid({
            url : '${path }/debt/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect :false,
            idField : 'id',
            sortName : 'loanId',
	        sortOrder : 'asc',
            pageSize : 20,
            checkOnSelect: true, selectOnCheck:true,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                field : 'ck',
                checkbox:true
            }, {
                width : '150',
                title : '借款编号',
                field : 'loanId',
                sortable : true
            },
            {
                width : '150',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } ,{
                width : '150',
                title : '唯一客户号',
                field : 'custId',
                sortable : true
            }, {
                width : '60',
                title : '产品编号',
                field : 'subProductId'
            }, {
                width : '130',
                title : '借款时间',
                field : 'loanDate',
                sortable : true
            } , {
                width : '60',
                title : '借款期数',
                field : 'period'
            } , {
                width : '60',
                title : '贷款本金',
                field : 'amt'
            } , {
                width : '60',
                title : '剩余本金',
                field : 'remainAmt'
            } , {
                width : '130',
                title : '放款时间',
                field : 'payDate'
            } , {
                width : '60',
                title : '借款金额',
                field : 'amt'
            } , {
              	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                     <shiro:hasPermission name="/debt/edit">
                     str += $.formatString('<a href="javascript:void(0)" class="debt-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="editProductGroupsFun(\'{0}\');" >查看</a>', row.loanId);
                    </shiro:hasPermission>
                     return str;
                 } 
                } ] ],
                onLoadSuccess:function(data){
                	$('.debt-easyui-linkbutton-edit').linkbutton({text:'查看'});
                },       
                });
        
     
    });
  	//还款计划查看
    function queryRepayPlanFun(planId) {
        parent.$.modalDialog({
            title : '详情',
            width : 520,
            height : 360,
            href : '${path }/repayPlan/editRepayPlanPage?planId=' + planId
        });
    }
    //借据编辑
    function editProductGroupsFun(loanId) {
        parent.$.modalDialog({
            title : '详情',
            width : 520,
            height : 360,
            href : '${path }/debt/editProductGroupsPage?loanId=' + loanId
        });
}
    //借款信息表
    $('#debtDiv').click(function(){
    	 searchRepayPlan( );
    }); 
    function searchRepayPlan( ){
    	 var checkedItems = $('#debtDataGrid').datagrid('getChecked');
    	 var names = [];
    	    $.each(checkedItems, function(index, item){
    	        names.push(item.contractId);
    	    });                
    /* 	    console.log(names.join(","));
           $.messager.alert('contractId',names+"",'info'); */
			//还款计划明细
            repayPlanInfoDataGrid = $('#repayPlanInfoDataGrid').datagrid({
             url : '${path }/repayPlan/dataGrid?contractId='+names,
             fit : true,
             striped : true,
             rownumbers : true,
             pagination : true,
             singleSelect : false,
             idField : 'id',
             sortName : 'loanId',
 	        sortOrder : 'asc',
             pageSize : 20,
             checkOnSelect: true, selectOnCheck: true,
             pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],

	columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				width : '150',
				title : '还款编号',
				field : 'planId',
				sortable : true
			}, {
				width : '150',
				title : '合同编号',
				field : 'contractId',
				sortable : true
			}, {
				width : '60',
				title : '产品编号',
				field : 'subProductId',
				sortable : true
			}, {
				width : '70',
				title : '父产品编号',
				field : 'parentProductId'
			}, {
				width : '150',
				title : '客户唯一编号',
				field : 'custId',
				sortable : true
			}, {
				width : '80',
				title : '还款计划类型',
				field : 'planType',
				sortable : true,
				formatter : function(value, row, index) {
                    switch (value) {
                    case 'K' :
                        return '账分';
                    case 'H' :
                        return '账汇';
                    case 'R' :
                        return '无还款计划';
                    case 'Z' :
                        return '借款时生成';
                    case 'Y' :
                        return '账单日生成';
                    }
                }
			}, {
				width : '130',
				title : '创建日期',
				field : 'createDate',
				sortable : true
			}, {
				width : '60',
				title : '当前期数',
				field : 'period',
				sortable : true
			}, {
				width : '50',
				title : '总期数',
				field : 'maxPeriod',
				sortable : true
			}, {
				width : '80',
				title : '借据编号',
				field : 'loanId',
				sortable : true
			}, {
				width : '130',
				title : '还款日',
				field : 'repayDate',
				sortable : true
			}, {
				width : '50',
				title : '宽限期(天)',
				field : 'gracePeriod',
				sortable : true
			},{
				width : '80',
				title : '最低还款额',
				field : 'leaseAmt',
				sortable : true
			},{
				width : '60',
				title : '逾期天数',
				field : 'dpd',
				sortable : true
			}, {
				width : '60',
				title : '计划状态',
				field : 'planStatus',
				sortable : true,
				formatter : function(value, row, index) {
                    switch (value) {
                    case 'S' :
                        return '有效';
                    case 'D' :
                        return '暂停';
                    case 'L' :
                        return '无效';
                    }
                }
			}, {
				width : '60',
				title : '还款状态',
				field : 'repayStatus',
				sortable : true,
				formatter : function(value, row, index) {
                    switch (value) {
                    case 'T' :
                        return '未结清';
                    case 'L' :
                        return '提前结清';
                    case 'U' :
                        return '已结清';
                    }
                }
			}, {
				width : '80',
				title : '还清时间',
				field : 'settledDate',
				sortable : true
			}, {
				width : '130',
				title : '应还总额',
				field : 'interestAmt',
				sortable : true
			}, {
				width : '100',
				title : '已还总额',
				field : 'returnAmt',
				sortable : true
			}, {
				width : '200',
				title : '贷款本金',
				field : 'amt',
				sortable : true
			}, {
              	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                     <shiro:hasPermission name="/repayPlan/edit">
                     str += $.formatString('<a href="javascript:void(0)" class="repay-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryRepayPlanFun(\'{0}\');" >查看</a>', row.planId);
                    </shiro:hasPermission>
                     return str;
                 } 
                } ] ],
                onLoadSuccess:function(data){
                	$('.repay-easyui-linkbutton-edit').linkbutton({text:'查看'});
                },       
                });    
            
	}


    
	function searchDebt() {
		debtDataGrid.datagrid('load', $.serializeObject($('#searchDebtForm')));
	}
	function cleanDebt() {
		$('#searchDebtForm input').val('');
		debtDataGrid.datagrid('load', {});
	}
</script>

	<div class="easyui-layout" style="width:1300px;height:500px;">
		<div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
        <form id="searchDebtForm">
            <table>
                <tr>
                    <th>借款编号:</th>
                    <td><input name="loanId" placeholder="请输入借款用户编号"/></td>
                    <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                    <th>唯一客户号:</th>
                    <td><input name="custId" placeholder="请输入合同编号"/></td>
                    <th>客户手机号:</th>
                    <td><input name="custMobile" placeholder="请输入合同编号"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDebt();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanDebt();">清空</a>
                    </td>
                    <td>
                       <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRepayPlan();">获取信用计划</a>
                    </td>
                </tr>
            </table>
        </form>
        </div>
        
		<div data-options="region:'east',split:true" title="还款计划" style="width:650px;">
		     <table id="repayPlanInfoDataGrid" data-options="fit:true,border:false, rownumbers:true,singleSelect:true,url:'datagrid_data1.json'"></table>
		</div>
		
		<div id="debtDiv" data-options="region:'west',split:true" title="借据信息" style="width:650px;">
		   <table id="debtDataGrid" data-options="fit:true,border:false, rownumbers:true,singleSelect:true,url:'datagrid_data1.json'"></table>
		   <div style="margin: 10px 0;">
			  <span>Selection Mode: </span> <select
				onchange="$('#debtDataGrid').datagrid({singleSelect:(this.value==0)})">
				<option value="0">Single Row</option>
				<option value="1">Multiple Rows</option>
			    </select><br /> SelectOnCheck: <input type="checkbox" checked
				onchange="$('#debtDataGrid').datagrid({selectOnCheck:$(this).is(':checked')})"><br />
			    CheckOnSelect: <input type="checkbox" checked
				onchange="$('#debtDataGrid').datagrid({checkOnSelect:$(this).is(':checked')})">
		    </div>
	    </div>
	</div>
 
</body>
</html>