<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
        $('#adjustForm').form({
            url : '${path }/finance/adjust',
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
        
        $("#rateType").val('${cRepayDetail.rateType}');
    });
</script>
<div style="padding: 3px;">
    <form id="adjustForm" method="post">
        <table class="grid">
                <tr>
                    <td style="width:140px;">合同号</td>
                    <td><input name="contractId" value="${cRepayPlan.contractId}" type="text" readonly="true" class="easyui-textbox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>客户号</td>
                    <td><input name="custId" value="${cRepayPlan.custId}" type="text" readonly="true" class="easyui-textbox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>调整金额</td>
                    <td><input name="amount" type="text" class="easyui-textbox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>   
                    <td>还款计划ID</td>
                    <td><input name="planId" value="${cRepayDetail.planId}" type="text" readonly="true" class="easyui-textbox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>		
               		<td>借据ID</td>
                    <td><input name="loanId" value="${cRepayPlan.loanId}" type="text" readonly="true" class="easyui-textbox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>   
                    <td>交易码</td>
                    <td>
		                <select class="easyui-combobox" id="transCode" name="transCode" data-options="required:true" style="width:160px;">
			                <option></option>
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
						</select>
				    </td>
				</tr>
                <tr>   
                    <td>调账人</td>
                    <td><input name=operId value="${shiroUser.name}" readonly="true" type="text" class="easyui-textbox" data-options="required:true" style="width:160px;"></td>
             	</tr>
                <tr>	
             		<td>调整费用类型</td>
                    <td>
		                <select class="easyui-combobox" id="rateType" name="rateType" readonly="true" data-options="required:true"style="width:160px;">
			                <option></option>
							<option value="A">本金</option>
							<option value="T">基准利率</option>
							<option value="D">复利利率</option>
							<option value="L">逾期利率</option>
							<option value="S">服务费</option>
							<option value="W">违约金</option>
							<option value="K">砍头息</option>
							<option value="F">逾期服务费率</option>
							<option value="X">提前还款手续费</option>
							<option value="Z">逾期管理费</option>
						</select>
				    </td>
               </tr>
        </table>
    </form>
</div>
