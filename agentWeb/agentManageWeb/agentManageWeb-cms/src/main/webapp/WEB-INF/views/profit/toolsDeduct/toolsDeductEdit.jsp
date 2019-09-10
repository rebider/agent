<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#toolsDeductEditForm').form({
            url : '${path }/toolsDeduct/edit',
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
                    parent.$.messager.alert('成功', result.msg, 'ok');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="toolsDeductEditForm" method="post">
            <table class="grid">
                <input type="hidden" name="id" value="${profitDeduction.id}">
                <input type="hidden" name="deductionDate" value="${profitDeduction.deductionDate}">
                <input type="hidden" name="agentName" value="${profitDeduction.agentName}">
                <input type="hidden" name="agentId" value="${profitDeduction.agentId}">
                <%--<input type="hidden" name="sourceId" value="${profitDeduction.sourceId}">--%> <!--分期明细-->
                <input type="hidden" name="sumDeductionAmt" value="${profitDeduction.sumDeductionAmt}">
                <input type="hidden" name="deductionDesc" value="${profitDeduction.deductionDesc}">  <!--平台-->
                <tr>
                    <td>扣款月份：</td>
                    <td>${profitDeduction.deductionDate}</td>
                </tr>
                <tr>
                    <td>代理商名称：</td>
                    <td>${profitDeduction.agentName}</td>
                </tr>
                <tr>
                    <td>代理商编号：</td>
                    <td>${profitDeduction.agentPid}</td>
                </tr>
                <tr>
                    <td>本月扣款总金额：</td>
                    <td>${profitDeduction.sumDeductionAmt}</td>
                </tr>
                <tr>
                    <td>本月应扣款金额：</td>
                    <td>${profitDeduction.mustDeductionAmt}</td>
                </tr>
                <tr>
                    <td>申请调整扣款金额：</td>
                    <td><input type="text" name="mustDeductionAmt" value="${profitDeduction.mustDeductionAmt}" class="easyui-numberbox"  placeholder="请输入申请机具扣款金额" class="easyui-validatebox" data-options="required:true,min:0.01,precision:2"></td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td><textarea style="height: 100px;width: 250px" id="remark"  name="remark" >${profitDeduction.remark}</textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>