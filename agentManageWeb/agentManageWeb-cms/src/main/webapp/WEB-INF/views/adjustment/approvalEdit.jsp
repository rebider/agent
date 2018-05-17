<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
         $('#approvalEditForm').form({
            url : '${path}/approval/edit',
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
                    parent.$.modalDialog.handler.dialog('close');
                 	parent.$.messager.alert('提示', result.msg, 'info');
                    productTreeGrid.treegrid('reload'); 
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        /* $("#moneyFlow").val('${tranAdjustment.moneyFlow}'); */
        $("#TransCode").val('${tranAdjustment.transCode}');//交易类型
        $("#TransType").val('${cTransFlow.transType}');//原交易状态
        $("#adjustmentState").val('${tranAdjustment.adjustmentState}');//调整状态
    }); 
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="approvalEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>调账记录编号</td>
                    <td>
                    <input type="hidden" name="transCode" value="${tranAdjustment.transCode}">
                    <input type="hidden" name="adjustmentState" value="${tranAdjustment.adjustmentState}">
                    <input name="pk" type="text"  class="easyui-validatebox"  value="${tranAdjustment.pk}" readonly></td>
                </tr>
                <tr>
                    <td>交易流水号</td>
                     <td><input name="flowId" type="text"  class="easyui-validatebox"  value="${tranAdjustment.flowId}" readonly></td>
                </tr>
                <tr>
                    <td>合同编号</td>
                     <td><input name="contractId" type="text"  class="easyui-validatebox"  value="${tranAdjustment.contractId}" readonly></td>
                </tr>
                <tr>
                    <td>创建时间</td>
                     <td><input name="createDate" type="text"  class="easyui-validatebox"  value="${createDateZ}" readonly></td>
                </tr>
                <tr>
                    <td>调整时间</td>
                     <td><input name="adjustmentTime" type="text"  class="easyui-validatebox"  value="${adjustmentTimeZ}" readonly></td>
                </tr>
                <tr>
                    <td>调整金额</td>
                    <td><input name="amount" type="text" class="easyui-validatebox"  value="${tranAdjustment.amount}" readonly></td>
                </tr>
                <tr>
                    <td>原交易状态</td>
                    <td>
                    <select id="TransType" name= "TransType" class="easyui-combobox" style="width:280px;" data-options="required:true" disabled="disabled">
					<option value="Q">申请</option>
					<option value="O">交易中</option>
					<option value="S">成功</option>
					<option value="E">失败</option>
					<option value="L">超时</option>
					<option value="T">已重提</option>
					<option value="F">不确定</option>
					<option value="D">冲正</option>
					<option value="H">挂起</option>
					</select>
                    </td>
                </tr>
                <tr>
                    <td>调整状态</td>
                    <td>
                    <select id="adjustmentState" name="adjustmentState" class="easyui-combobox" style="width:280px;" data-options="required:true" disabled="disabled">
					<option value="Q">申请</option>
					<option value="O">交易中</option>
					<option value="S">成功</option>
					<option value="E">失败</option>
					<option value="L">超时</option>
					</select>
                   </td>
                </tr>
                <tr>
                    <td>交易类型</td>
                    <td>
                    <select id="TransCode" name = "TransCode" class="easyui-combobox" style="width:280px;" data-options="required:true" disabled="disabled">
					<option value="A001">借款合同申请</option>
					<option value="B001">合同撤销</option>
					<option value="A002">放款申请</option>
					<option value="B002">放款冲正</option>
					<option value="A003">还款</option>
					<option value="A011">提前还款</option>
					<option value="B003">还款冲正</option>
					<option value="C010">利息计提</option>
				    </select>
                   </td>
                </tr>
           		<tr>
                    <td>备注</td>
                    <td>
                    <textarea name="remarks" rows="4" cols="20">${tranAdjustment.remarks}</textarea>
                    </td>
                </tr> 
                <tr>
                	<td>审批状态</td>
                	<td>   
	                <select name="adjustmentStatus">   
	                <option value="Y">通过</option>   
	                <option value="O">拒绝</option>   
	             	</select>   
		            </td>   
                </tr> 
            </table>
        </form>
    </div>
</div>