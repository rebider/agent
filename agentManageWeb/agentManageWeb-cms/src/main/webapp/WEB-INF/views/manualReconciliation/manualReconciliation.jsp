<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var ManualReconciliationA;
    $(function() {
    	ManualReconciliationA = $('#ManualReconciliationA').datagrid({
            url : '${path }/manualReconciliation/manualReconciliationList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'flowId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '100',
                title : '流水ID',
                field : 'flowId',
            }, {
                width : '80',
                title : '交易类型',
                field : 'transCode',
                sortable : true,
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
                    case 'A011':
                        return '提前还款';
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
                    case 'C010':
                        return '利息计提';
                    case 'C011':
                        return '罚息计提';
                    }
                } 
            }, {
                width : '100',
                title : '交易时间',
                field : 'transDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '160',
                title : '合同编号',
                field : 'contractId',
            } , {
                width : '90',
                title : '客户姓名',
                field : 'custName',
            }, {
                width : '70',
                title : '交易状态',
                field : 'transType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Q':
                        return '申请';
                     case 'O':
                        return '交易中';
                    case 'S':
                        return '成功';
                    case 'E':
                        return '失败';
                    case 'L':
                        return '超时';
                    case 'T':
                        return '已重提';    
                    case 'F':
                        return '不确定';
                    case 'D':
                        return '冲正';
                    case 'H':
                        return '挂起';
                    case 'G':
                        return '有效';
                    }
                } 
            }, {
                width : '90',
                title : '放款金额',
                field : 'amount',
            }, {
                width : '90',
                title : '借贷标记',
                field : 'transDirection',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '借记';
                   	case 'C':
                        return '贷记';
                    }
                } 
            }, {
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/manualReconciliation/editManualReconciliation">
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editManualReconciliationFun(\'{0}\');" >编辑</a>', row.flowId);
                         </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.product-easyui-linkbutton-edit').linkbutton({text:'人工调账'});
             },
            toolbar : '#manualReconciliationToolbar'
        });
    });

   function editManualReconciliationFun(flowId) {
           parent.$.modalDialog({
               title : '人工调整',
               width : 800,
               height : 420,
               href : '${path }/manualReconciliation/editManualReconciliationPage?flowId=' + flowId,
               buttons : [ {
                   text : '确认',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = ManualReconciliationA;
                       var p = parent.$.modalDialog.handler.find('#manualReconciliationEditForm');
                       p.submit();
                   }
               } ]
           });
   }
  
   	function searchManualReconciliation() {
   		ManualReconciliationA.datagrid('load', $.serializeObject($('#searchManualReconciliationForm')));
	}
	function cleanManualReconciliation() {
		$('#searchManualReconciliationForm input').val('');
		$("[name='transCode']").val('');
		$("[name='transType']").val('');
		$("[name='transDirection']").val('');
		ManualReconciliationA.datagrid('load', {});
	}
   
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="ManualReconciliationA" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchManualReconciliationForm">
			<table>
					<tr>
					
					<th>流水ID:</th>
					<td><input name="flowId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>合同编号:</th>
					<td><input name="contractId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>交易类型:</th>
					<td><select name="transCode" style="width:140px;height:21px" >
						    <option value="">-请选择-</option>
						    <option value="A001">借款合同申请</option>
							<option value="B001">合同撤销</option>
							<option value="A002">放款申请</option>
							<option value="B002">放款冲正</option>
							<option value="A003">还款</option>
							<option value="A011">提前还款</option>
							<option value="B003">还款冲正</option>
							<option value="A004">本金调整</option>
							<option value="A005">利息调整</option>
							<option value="A006">砍头息调整</option>
							<option value="A007">管理费调整</option>
							<option value="A008">逾期利息调整</option>
							<option value="A009">违约金调整</option>
							<option value="B004">息费调整冲正</option>
							<option value="A010">溢交款转出</option>
							<option value="B010">溢交款转出冲正</option>
							<option value="C010">利息计提</option>
							<option value="C011">罚息计提</option>
						</select>
					</td>
				</tr>
				
				<tr>
				<th>交易状态:</th>
				<td><select name="transType" style="width:140px;height:21px" >
						  <option value="">-请选择-</option>
						  <option value="Q">申请</option>  
	                      <option value="O">交易中</option>  
	                      <option value="S">成功</option>  
	                      <option value="E">失败</option>  
	                      <option value="L">超时</option> 
	                      <option value="T">已重提</option>
	                      <option value="F">不确定</option>
	                      <option value="D">冲正</option>
	                      <option value="H">挂起</option>
	                      <option value="G">有效</option>
						</select>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>借贷标记:</th>
					<td>
						<select name="transDirection" style="width:140px;height:21px" >
						  <option value="">-请选择-</option>
						  <option value="D">借记</option>  
	                      <option value="C">贷记</option>  
						</select>
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th></th>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchManualReconciliation();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanManualReconciliation();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="manualReconciliationToolbar" style="display: none;">
</div>

