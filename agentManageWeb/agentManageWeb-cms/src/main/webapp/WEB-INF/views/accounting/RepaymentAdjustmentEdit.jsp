<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#EditForm').form({
            url : '${path}/Repayment/edit',
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
                    <input name="loanId" type="hidden"  value="${cRepayPlan.loanId}"><!-- 贷款编号 -->
                    <input name="planId" type="text"  class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.planId}" readonly></td>
                </tr>
                <tr>
                    <td>合同编号</td>
                     <td><input name="contractId" type="text"  class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.contractId}" readonly></td>
                </tr>
                <tr>
                    <td>客户号</td>
                     <td><input name="custId" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.custId}" readonly></td>
                </tr>
                <tr>
                    <td>应还本金</td>
                    <td><input name="amt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.amt}" readonly></td>
                </tr>
                <tr>
                    <td>应还利息</td>
                    <td><input name="interestAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.interestAmt}" readonly></td>
                </tr>
                <tr>
                    <td>应付砍头息</td>
                    <td><input name="cutInterestAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.cutInterestAmt}" readonly></td>
                </tr>
                <tr>
                    <td>应付管理费</td>
                    <td><input name="expenseAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.expenseAmt}" readonly></td>
                </tr>
               <tr>
                    <td>应付逾期利息</td>
                    <td><input name="overdueAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.overdueAmt}" readonly></td>
                </tr>
                <tr>
                    <td>应付违约金</td>
                    <td><input name="penaltyAmt" type="text" class="easyui-validatebox" data-options="required:true" value="${cRepayPlan.penaltyAmt}" readonly></td>
                </tr>
                
                
                <tr>
                    <td>减免利息</td>
                    <td><input name="redInterestAmt" type="text" placeholder="请输入减免利息" class="easyui-validatebox" ></td>
                </tr>
                <tr>
                    <td>减免砍头息</td>
                    <td><input name="redCutInterestAmt" type="text" placeholder="请输入减免砍头息" class="easyui-validatebox" ></td>
                </tr>
                <tr>
                    <td>减免管理费</td>
                    <td><input name="redExpenseAmt" type="text" placeholder="请输入减免管理费" class="easyui-validatebox"></td>
                </tr>
                <tr>
                    <td>减免逾期利息</td>
                    <td><input name="redOverdueAmt" type="text" placeholder="请输入减免逾期利息" class="easyui-validatebox"></td>
                </tr>
                <tr>
                    <td>减免违约金</td>
                    <td><input name="redPenaltyAmt" type="text" placeholder="请输入减免违约金" class="easyui-validatebox"></td>
                </tr>
            </table>
        </form>
    </div>
</div>