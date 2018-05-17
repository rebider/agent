<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var historyTransFlowDataGrid;
    var historytransAllocDataGrid;
    
    $('#historyallocdiv').click(function(){
    	var rows = historyTransFlowDataGrid.datagrid('getSelected');
    	//i = rows.length-1;
    	//loanId = rows[i].loanId;
    	flowId = rows.flowId;
    	//console.info(rows[0]);
        historytransAllocDataGrid.datagrid('load', {
            id: flowId
        });
        historyTransFlowDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    });
    
    $(function() {

        historyTransFlowDataGrid = $('#historyTransFlowDataGrid').datagrid({
            url : '${path }/transFlow/dataHistoryGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'flowId',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ 
            {
                width : '100',
                title : '交易流水号',
                field : 'flowId',
                sortable : true
            } ,
            {
                width : '150',
                title : '订单编号',
                field : 'orderId',
                sortable : true
            }  ,
            {
                width : '150',
                title : '借据编号',
                field : 'loanId',
                sortable : true
            } ,
            {
                width : '150',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } ,
            {
                width : '100',
                title : '交易类型',
                field : 'transCode',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'T001':
                        return '(调账)合同撤销';
                    case 'T002':
                        return '(调账)合同生效';
                    case 'T003':
                        return '(调账)人工还款';
                    case 'T004':
                        return '利息冲销';
                    case 'T005':
                        return '服务费冲销';
                    case 'T006':
                        return '罚息冲销';
                    case 'T007':
                        return '逾期管理费冲销';
                    case 'T008':
                        return '未知费用冲销';
                    case 'A002':
                        return '放款申请';
                    case 'B002':
                        return '放款冲正';
                    case 'A003':
                        return '还款';
                    case 'A004':
                        return '溢缴款还款';
                    case 'A005':
                        return '溢缴款转出';
                    case 'A011':
                        return '提前还款';
                    case 'U001':
                        return '利息调增';
                    case 'U002':
                        return '罚息调增';
                    case 'U003':
                        return '服务费调增';
                    case 'U004':
                        return '提前手续费调增';
                    case 'U005':
                        return '逾期管理费调增';
                    case 'DW01':
                        return '未还利息减免';
                    case 'DY01':
                        return '已还利息减免';
                    case 'DW02':
                        return '未还罚息减免';
                    case 'DY02':
                        return '已还罚息减免';
                    case 'DW03':
                        return '未还服务费减免';
                    case 'DY03':
                        return '已还服务费减免';
                    case 'DW04':
                        return '未还提前手续费减免';
                    case 'DY04':
                        return '已还提前手续费减免';
                    case 'DW05':
                        return '未还逾期管理费减免';
                    case 'DY05':
                        return '已还逾期管理费减免';
                    case 'C007':
                        return '服务费计提';    
                    case 'C008':
                        return '提前手续费计提';
                    case 'C009':
                        return '逾期管理费计提';
                    case 'C010':
                        return '利息计提';
                    case 'C011':
                        return '罚息计提';
                    case 'C099':
                        return '未知成分计提';
                    case 'Z001':
                        return '正常利息转逾期';
                    case 'Z002':
                        return '正常本金转逾期';
                    }
                } 
            }  ,
            {
                width : '100',
                title : '息费类型',
                field : 'rateType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'T':
                        return '基准利率';
                     case 'D':
                        return '复利利率';
                     case 'L':
                        return '逾期利率';
                     case 'S':
                        return '服务费';
                     case 'W':
                        return '违约金';
                     case 'K':
                        return '砍头息';
                     case 'F':
                        return '逾期服务费率';
                     case 'X':
                        return '提前还款手续费';
                    }
                } 
            },
            {
                width : '100',
                title : '出款/还款交易方式',
                field : 'payType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                     case 'LJ':
                        return '联机代付';
                     case 'PK':
                        return '批量代付';
                     case 'XF':
                        return '消费';
                     case 'DK':
                        return '主动代扣';
                     case 'PJ':
                        return '批扣';
                     case 'SK':
                        return '刷卡';
                     case 'KZ':
                        return '快捷支付';
                     case 'WX':
                        return '微信';
                     case 'ZB':
                        return '支付宝';
                     case 'XX':
                        return '线下';
                     case 'XT':
                         return '系统';
                     case 'TZ':
                        return '调账';
                    }
                } 
            },
            {
                width : '130',
                title : '交易时间',
                field : 'transDate',
                sortable : true
            } ,
            {
                width : '80',
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
            },
            {
                width : '80',
                title : '交易金额',
                field : 'amount',
                sortable : true
            },
            {
                width : '80',
                title : '产品类型',
                field : 'productType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '随借随还产品';
                    case 'F':
                        return '分期产品';
                    }
                } 
            },
            {
                width : '80',
                title : '期限类型',
                field : 'periodType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'R':
                        return '日';
                    case 'Z':
                        return '周';
                    case 'Y':
                        return '月';
                    case 'J':
                        return '季';
                    case 'N':
                        return '年';
                    }
                } 
            },
            {
                width : '80',
                title : '子产品名称',
                field : 'productName',
                sortable : true
            } ,
            {
                width : '80',
                title : '客户姓名',
                field : 'custName',
                sortable : true
            }  , {
                width : '100',
                title : '客户手机号',
                field : 'mobileNo'
            }, 
            {
                width : '80',
                title : '操作人',
                field : 'operId',
                sortable : true
            } ,  {
                width : '150',
                title : '交易卡号',
                field : 'account',
                sortable : true
            },{
                width : '80',
                title : '银行名称',
                field : 'bankName'
            }, {
                width : '80',
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
            },{
                 	 field : 'action',
                     title : '操作',
                     width : 180,
                     formatter : function(value, row, index) {
                         var str = '';
                         <shiro:hasPermission name="/transFlow/edit">
                         str += $.formatString('<a href="javascript:void(0)" class="trans-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryHistoryTransFlowFun(\'{0}\');" >查看</a>', row.flowId);
                        </shiro:hasPermission>
                         return str;
                     } 
                    }
            
            ]],
            onLoadSuccess:function(data){
            	$('.trans-easyui-linkbutton-edit').linkbutton({text:'查看'});
            }, 
            toolbar : '#userHistoryToolbar'
        });
        historytransAllocDataGrid = $('#historytransAllocDataGrid').datagrid({
            url : '${path }/transFlow/histransAllocDataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'loanId',
            columns : [ [ {
                width : '100',
                title : '流水号',
                field : 'flowId'
            },
            {
                width : '80',
                title : '分配序号',
                field : 'allocNum'
            },
            {
                width : '150',
                title : '贷款编号',
                field : 'contractId'
            },
            {
                field : 'allocType',
                title : '分配类型',
                width : 80,
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
                field : 'planId'
            },
            {
                field : 'feeType',
                title : '费用类型',
                width : 80,
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
                field : 'amount'
            },
            {
                field : 'processType',
                title : '处理方式',
                width : 80,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'V':
                        return '正常分配';
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
                width : '130',
                title : '处理时间',
                field : 'updateDate'
            },
            {
                field : 'allocStatus',
                title : '分配状态',
                width : 80,
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
            }] ],
            toolbar : '#subproductToolbar'
        });
    });
    
    
      //历史订单交易查看
    function queryHistoryTransFlowFun(flowId) {
        parent.$.modalDialog({
            title : '详情',
            width : 660,
            height : 400,
            href : '${path }/transFlow/editHistoryTransFlowPage?flowId=' + flowId
        });
	}
    
    function searchHistoryTransFlow() {
        historyTransFlowDataGrid.datagrid('load', $.serializeObject($('#searchTransHistoryFlowForm')));
    }
    function cleanHistoryTransFlow() {
        $('#searchTransHistoryFlowForm input').val('');
        historyTransFlowDataGrid.datagrid('load', {});
    }
    $('#rateAddPid').combotree({
        url : '${path }/productrate/tree',
        parentField : 'subProductId',
        lines : true,
        panelHeight : 'auto',
        value : <%=request.getParameter("id")%>
    });
    
    function exportUserHistoryFun(){
    	$.ajax({
    		url:'${path }/transFlow/exportUserHistory',
    		data:$("#searchTransHistoryFlowForm").serialize(),
    		type:'GET',
    		dateType:'json',
    		success:function(data){
   				parent.$.messager.alert('提示', data, 'info');
    		}
    	})
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 110px; overflow: hidden;background-color: #fff">
        <form id="searchTransHistoryFlowForm" action="${path }/transFlow/exportUserHistory" method="post">
			<table>
				<tr>
					<th>合同编号:</th>
					<td><input name="contractId" placeholder="请输入合同编号" /></td>
					<th>持卡人姓名:</th>
					<td><input name="custName" placeholder="请输入持卡人姓名" /></td>
					<th>移动电话:</th>
					<td><input name="mobileNo" placeholder="请输入移动电话" /></td>
					<th>交易流水号:</th>
					<td><input name="flowId" placeholder="请输入交易流水号" /></td>
				</tr>
				<tr>
					<th>出款/还款交易方式:</th>
					<td>	
                    <select  class="easyui-combobox" name="payType" data-options="width:140,height:20,editable:false,panelHeight:'auto'">
                      <option></option>
                      <option value="LJ">联机代付</option> 
                      <option value="PK">批量代付</option> 
                      <option value="XF">消费</option>  
                      <option value="DK">主动代扣</option>
                      <option value="PJ">批扣</option>
                      <option value="SK">刷卡</option>
                      <option value="KZ">快捷支付</option>
                      <option value="WX">微信</option>
                      <option value="ZB">支付宝</option>
                      <option value="XX">线下</option>
                      <option value="XT">系统</option>
                      <option value="TZ">调账</option>
                    </select></td>
                    <th>交易状态:</th>
				   <td>	
                    <select  class="easyui-combobox" name="transType" data-options="width:140,height:20,editable:false,panelHeight:'auto'">
                      <option></option>
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
                    </select></td>
                    <th>订单起始时间:</th>
					<td><input name="startTime" class="easyui-datetimebox" placeholder="开始时间"/></td>
					<th>～～    订单结束时间:</th>
					<td><input name="endTime" class="easyui-datetimebox" placeholder="结束时间" /></td>
                </tr>
                <tr>
                	<th>交易类型:</th>
					<td>	
                    <select  class="easyui-combobox" name="transCode" data-options="width:140,height:20,editable:false,panelHeight:'auto'">
                      	<option></option>
                      	<option value="T001">(调账)合同撤销</option>
		                <option value="T002">(调账)合同生效</option>
		                <option value="T003">(调账)人工还款</option>
		                <option value="T004">利息冲销</option>
		                <option value="T005">服务费冲销</option>
		                <option value="T006">罚息冲销</option>
		                <option value="T007">逾期管理费冲销</option>
		                <option value="T008">未知费用冲销</option>
						<option value="A002">放款申请</option>
						<option value="B002">放款冲正</option>
						<option value="A003">还款</option>
						<option value="A004">溢缴款还款</option>
						<option value="A005">溢缴款转出</option>
						<option value="A011">提前还款</option>
						<option value="DW01">未还利息减免</option>
						<option value="DY01">已还利息减免</option>
						<option value="DW02">未还罚息减免</option>
						<option value="DY02">已还罚息减免</option>
						<option value="DW03">未还服务费减免</option>
						<option value="DY03">已还服务费减免</option>
						<option value="DW04">未还提前手续费减免</option>
						<option value="DY04">已还提前手续费减免</option>
						<option value="DW05">未还逾期管理费减免</option>
						<option value="DY05">已还逾期管理费减免</option>
						<option value="C007">服务费计提</option>
						<option value="C008">提前手续费计提</option>
						<option value="C009">逾期管理费计提</option>
						<option value="C010">利息计提</option>
						<option value="C011">罚息计提</option> 
						<option value="C099">未知成分计提</option>  
						<option value="Z001">正常利息转逾期</option> 
						<option value="Z002">正常本金转逾期</option> 
                    </select></td>   
                    <th>交易起始金额:</th>
					<td><input name="startAmount" placeholder="交易起始金额"/></td>
					<th>～～    交易结束金额:</th>
					<td><input name="endAmount" placeholder="交易结束金额" /></td> 
					<th>子产品名称:</th>
                    <td colspan="3"><select id="rateAddPid" name="subProductId" class="easyui-combobox" data-options="width:140,height:20,editable:false,panelHeight:'auto'"></select>
                    <td>
						<a href="javascript:void(0);"
						class="easyui-linkbutton"
						data-options="iconCls:'fi-magnifying-glass',plain:true"
						onclick="searchHistoryTransFlow();">查询</a> <a href="javascript:void(0);"
						class="easyui-linkbutton"
						data-options="iconCls:'fi-x-circle',plain:true"
						onclick="cleanHistoryTransFlow();">清空</a></td>
				</tr>
				<tr>
					<td>
						<button type="submit">导出历史订单流水</button>
					</td>
				</tr>
			</table>
		</form>
    </div>
     <div  data-options="region:'center',border:true,title:'还款分配明细列表'" >
        <table id="historytransAllocDataGrid" data-options="fit:true,border:false"></table>
    </div> 
    <div id="historyallocdiv" data-options="region:'west',border:true,title:'历史交易流水查询'"  style="width:48%;overflow: hidden; ">
        <table id="historyTransFlowDataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="userHistoryToolbar" style="display: none;">
   		<!-- <a onclick="exportUserHistoryFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-upload icon-green'">导出历史订单流水</a> -->
	</div>
</div>
