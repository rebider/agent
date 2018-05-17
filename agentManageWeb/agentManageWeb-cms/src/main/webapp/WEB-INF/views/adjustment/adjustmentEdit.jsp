<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">    
    $(function() {
        $('#repaymentEditForm').form({
            url : '${path}/adjustment/edit',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) { 
                }
                return isValid;
            },
            success : function(result) {
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
     	$("#transDirection").val('${cTransFlow.transDirection}');//借还状态
        $("#TransCode").val('${cTransFlow.transCode}');//交易类型
        $("#TransType").val('${cTransFlow.transType}');//原交易状态
    });
    
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="repaymentEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>流水ID</td>
                    <td>
                    <input type="hidden" name="transCode" value="${cTransFlow.transCode}">
                    <input type="hidden" name="transType" value="${cTransFlow.transType}">
                    <input id="flowId" name="flowId" type="text"  class="easyui-validatebox" data-options="required:true" value="${cTransFlow.flowId}" readonly>
                    </td>
                </tr>
                 <tr>
                    <td>交易类型</td>
                    <td>
                    <select id="TransCode" name="transCode" class="easyui-combobox"  
                    style="width:280px;" data-options="required:true" disabled="disabled">
					<option></option>
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
                    <td>原交易金额</td>
                    <td><input name="transDate" type="text" class="easyui-validatebox" data-options="required:true" value="${cTransFlow.amount}" readonly></td>
                </tr>
                <tr>
                    <td>原交易时间</td>
                    <td><input name="date" type="text" class="easyui-validatebox" data-options="required:true" value="${date}" readonly></td>
                </tr>
                <tr>
                    <td>借还状态</td>
                    <td>
                    <select id="transDirection" name="transDirection" class="easyui-combobox" 
                    style="width:280px;" data-options="required:true" disabled="disabled">
               		<option></option>
					<option value="C">贷记</option>
					<option value="D">借记</option>
					</select>
                    </td>
                </tr>
                <tr>
                    <td>交易状态</td>
                    <td>
                     <select id="TransType" name="transType" class="easyui-combobox" 
                    style="width:280px;" data-options="required:true" disabled="disabled">
					<option></option>
					<option value="Q">申请</option>
					<option value="S">成功</option>
					<option value="E">失败</option>
					<option value="L">超时</option>
					<option value="T">已重提</option>
					<option value="O">不确定</option>
					<option value="F">冲正</option>
					<option value="D">挂起</option>
					<option value="H">交易中</option>
					</select>
                    </td>
                </tr>
                <!-- =========添加========= -->
               <tr>
                    <td>资金流向</td>
                    <td>   
	                <select id="moneyFlow" name="moneyFlow" class="easyui-validatebox" data-options="required:true">   
                	<option></option> 
	                <option value="D">借记增加</option>
	                <option value="C">贷记减少</option>      
	             	</select>   
		            </td> 
                </tr>
                <tr>
                    <td>调整交易时间</td>
                    <td><input class="easyui-datetimebox" id="adjustmentTime" name="adjustmentTime"   style="width:150px"></td>
                </tr>
                <tr>
                    <td>调整金额</td>
                    <td><input id="amount" name="amount" type="text" placeholder="请输入调账金额"  class="easyui-validatebox" data-options="required:true"><span id="tip"></span></td>
                </tr>
                <tr>
                    <td>调整状态</td>
                     <td>   
		                <select  class="easyui-validatebox" id="adjustmentState" name="adjustmentState" data-options="required:true">   
	                	<option></option> 
		                <option value="S">成功</option>
		                <option value="E">失败</option>      
		             	</select>  
		            </td> 
                </tr>
                <tr>
                    <td>备注</td>
                    <td>
                    <textarea name="remarks" rows="4" cols="20" placeholder="请输入备注"></textarea>
                    <span id="tip"></span></td>
                </tr> 
            </table>
        </form>
    </div>
</div>