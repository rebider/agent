<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var AdjustA;
    $(function() {
    	AdjustA = $('#AdjustA').datagrid({
            url : '${path }/finance/adjustList',
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
                width : '150',
                title : '交易流水号',
                field : 'flowId',
                sortable : true
            } ,
            {
                width : '170',
                title : '借据编号',
                field : 'loanId',
                sortable : true
            } ,
            {
                width : '170',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } ,
            {
                width : '170',
                title : '计划编号',
                field : 'planId',
                sortable : true
            } ,
            {
                width : '80',
                title : '交易金额',
                field : 'amount',
                sortable : true
            },
            {
                width : '130',
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
                    case 'A002':
                        return '放款申请';
                    case 'B002':
                        return '放款冲正';
                    case 'A003':
                        return '还款';
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
                    }
                } 
            }  ,
            {
                width : '130',
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
                width : '130',
                title : '交易时间',
                field : 'transDate',
                sortable : true
            } ,
            {
                width : '80',
                title : '客户号',
                field : 'custId',
                sortable : true
            } , 
            {
                width : '80',
                title : '操作人',
                field : 'operId',
                sortable : true
            } , {
                width : '130',
                title : '操作时间',
                field : 'operDate',
                sortable : true
            } ] ],
            toolbar : '#adjustToolbar'
        });
    });

    function searchAdjust() {
    	AdjustA.datagrid('load', $.serializeObject($('#searchAdjustForm')));
	}
	function cleanAdjust() {
		$('#searchAdjustForm input').val('');
		$("[name='transCode']").val('');
		$("[name='rateType']").val('');
		AdjustA.datagrid('load', {});
	}
   
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="AdjustA" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchAdjustForm">
			<table>
				<tr>
					<th>借据编号:</th>
					<td><input name="loanId" style="line-height:17px;border:1px solid #ccc"></td>
					<th>合同编号:</th>
					<td><input name="contractId" style="line-height:17px;border:1px solid #ccc"></td>
					<th>计划编号:</th>
					<td><input name="planId" style="line-height:17px;border:1px solid #ccc"></td>
				</tr>
				<tr>
					<th>交易类型</th>    
	                <td><select id="transCode" name="transCode" style="width:140px;height:21px">
	                <option></option>
				    <option value="T001">(调账)合同撤销</option>
	                <option value="T002">(调账)合同生效</option>
	                <option value="T003">(调账)人工还款</option>
	                <option value="T004">利息冲销</option>
	                <option value="T005">服务费冲销</option>
	                <option value="T006">罚息冲销</option>
	                <option value="T007">逾期管理费冲销</option>
					<option value="U001">利息调增</option>
					<option value="U002">罚息调增</option>
					<option value="U003">服务费调增</option>
					<option value="U004">提前手续费调增</option>
					<option value="U005">逾期管理费调增</option>
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
					</select></td>
					<th>息费类型</th>
	                <td><select id="rateType" name="rateType" style="width:140px;height:21px">
	                <option></option>
					<option value="T">基准利率</option>
					<option value="D">复利利率</option>
					<option value="L">逾期利率</option>
					<option value="S">服务费</option>
					<option value="W">违约金</option>
					<option value="K">砍头息</option>
					<option value="F">逾期服务费率</option>
					<option value="X">提前还款手续费</option>
					<option value="Z">逾期管理费</option>
					</select></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAdjust();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAdjust();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

