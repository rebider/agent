<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#EditForm').form({
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
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        
        $("#roleEditStatus").val('${role.status}');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="EditForm" method="post">
            <table class="grid">
                <tr>
                    <td>还款计划编号</td>
                    <td>
                    <input name="pk" type="hidden"  value="${cRepayAdjustment.pk}"><!-- pk -->
                    <input name="loanId" type="hidden"  value="${cRepayAdjustment.loanId}"><!-- 贷款编号 -->
                    <input name="planId" type="text"  class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.planId}" readonly></td>
                </tr>
                <tr>
                    <td>合同编号</td>
                     <td><input name="contractId" type="text"  class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.contractId}" readonly></td>
                </tr>
                <tr>
                    <td>客户号</td>
                     <td><input name="custId" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.custId}" readonly></td>
                </tr>
                <tr>
                    <td>应还本金</td>
                    <td><input name="amt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.amt}" readonly></td>
                </tr>
                <tr>
                    <td>应还利息</td>
                    <td><input name="interestAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.interestAmt}" readonly></td>
                </tr>
                <tr>
                    <td>应付砍头息</td>
                    <td><input name="cutInterestAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.cutInterestAmt}" readonly></td>
                </tr>
                <tr>
                    <td>应付管理费</td>
                    <td><input name="expenseAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.expenseAmt}" readonly></td>
                </tr>
               <tr>
                    <td>应付逾期利息</td>
                    <td><input name="overdueAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.overdueAmt}" readonly></td>
                </tr>
                <tr>
                    <td>应付违约金</td>
                    <td><input name="penaltyAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.penaltyAmt}" readonly></td>
                </tr>
                
                <tr>
                    <td>减免利息</td>
                    <td><input name="redInterestAmt" type="text"  class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.redInterestAmt}" readonly></td>
                </tr>
                <tr>
                    <td>减免砍头息</td>
                    <td><input name="redCutInterestAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.redCutInterestAmt}" readonly></td>
                </tr>
                <tr>
                    <td>减免管理费</td>
                    <td><input name="redExpenseAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.redExpenseAmt}" readonly></td>
                </tr>
                <tr>
                    <td>减免逾期利息</td>
                    <td><input name="redOverdueAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.redOverdueAmt}" readonly></td>
                </tr>
                <tr>
                    <td>减免违约金</td>
                    <td><input name="redPenaltyAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayAdjustment.penaltyAmt}" redPenaltyAmt></td>
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