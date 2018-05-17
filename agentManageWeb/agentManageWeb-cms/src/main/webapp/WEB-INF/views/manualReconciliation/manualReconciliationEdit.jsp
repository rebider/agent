<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
        $('#manualReconciliationEditForm').form({
            url : '${path }/manualReconciliation/editManualReconciliation',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        
        $("#transCode").val('${CTransFlow.transCode}');
    });
</script>
<div style="padding: 3px;">
    <form id="manualReconciliationEditForm" method="post">
        <table class="grid">
                 <tr>
                    <td>流水ID</td>
                    <td><input name="flowId" value="${CTransFlow.flowId}" type="text" class="easyui-textbox" readonly="true"  data-options="required:true" style="width:160px;"></td>
                    <td>合同编号</td>
                    <td><input name="contractId" value="${CTransFlow.contractId}" type="text" class="easyui-textbox" readonly="true" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>交易类型</td>
                    <td>
		                <select class="easyui-combobox" id="transCode" name="transCode" readonly="true" data-options="required:true" style="width:160px;">
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
							<option value="C007">服务费计提</option>
							<option value="C008">提前手续费计提</option>
							<option value="C009">逾期管理费计提</option>
							<option value="C010">利息计提</option>
							<option value="C011">罚息计提</option> 
							<option value="C099">未知成分计提</option>  
							<option value="Z001">正常利息转逾期</option> 
							<option value="Z002">正常本金转逾期</option>
						</select>
				    </td>
                    <td>调整金额</td>
                    <td><input name="amount" value="${CTransFlow.amount}" type="text" class="easyui-textbox" readonly="true" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                	<td>交易时间</td>
                    <td><input name="transTime" value="${CTransFlow.transTime}" type="text" class="easyui-textbox" readonly="true" data-options="required:true" style="width:160px;"></td>
                    <td>调账类型</td>
                    <td>
		                <select class="easyui-combobox" data-options="required:true" name="adjustmentStatus" style="width:160px;" >
			                <option></option>
			                <option value="R">失败合同转成功</option>
			                <option value="W">成功合同转失败</option>
			                <option value="M">还款</option>
						</select>
	                </td>
                </tr>
                <tr>
                    <td>备注</td>
                     <td>
		               <textarea data-options="required:true" name="remarks" cols="50" rows="10" placeholder="请输入备注"></textarea> 
	                </td>
                </tr>
        </table>
    </form>
</div>
