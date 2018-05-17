<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
     $(function() {
        $('#shareSummaryEditForm').form({
            url : '${path }/shareSummary/editShareSummary',
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
    });
</script>
<div style="padding: 3px;">
    <form id="shareSummaryEditForm" method="post">
    <input type="hidden" name="tempID" value="${CshareSummary.shareSummaryId}">
        <table class="grid">
               <%-- <tr>
	                <td>分润汇总id</td>
	                <td><input id="shareSummaryId" name="shareSummaryId" value="${CshareSummary.shareSummaryId}" style="width:160px;" class="easyui-validatebox" data-options="required:true"></td>
                </tr> --%>
                 <tr>
                    <td>所属代理商</td>
                    <td><input name="agentId" value="${CshareSummary.agentId}" type="text" placeholder="请输入" class="easyui-validatebox"  data-options="required:true" style="width:160px;"></td>
                    <td>交易所属日期</td>
                    <td><input name="transTime" value="${CshareSummary.transTime}" type="text" placeholder="请输入" class="easyui-datebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>汇总日期</td>
                    <td><input name="summaryTime" value="${CshareSummary.summaryTime}" type="text" class="easyui-datebox" data-options="required:true" style="width:160px;"></td>
                    <td>交易总额</td>
                    <td><input name="summaryAmt" value="${CshareSummary.summaryAmt}" type="text" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>本金总额</td>
                    <td><input name="summaryLoanAmt" value="${CshareSummary.summaryLoanAmt}" type="text" class="easyui-validatebox" style="width:160px;"></td>
                    <td>息与费总额</td>
                    <td><input name="summaryAllRate" value="${CshareSummary.summaryAllRate}" type="text" placeholder="请输入" class="easyui-validatebox" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>利息总额</td>
                    <td><input name="summaryRate" value="${CshareSummary.summaryRate}" type="text" placeholder="请输入"  style="width:160px;"></td>
                    <td>罚息总额</td>
                    <td><input name="summaryPenalty" value="${CshareSummary.summaryPenalty}" type="text" placeholder="请输入" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>有效分润总额</td>
                    <td><input name="summaryValidAmt" value="${CshareSummary.summaryValidAmt}" type="text" placeholder="请输入" style="width:160px;"></td>
                    <td>分润金额</td>
                    <td><input name="summaryShareAmt" value="${CshareSummary.summaryShareAmt}" type="text" placeholder="请输入"  style="width:160px;"></td>
                </tr>
                <tr>
                    <td>分润参考总额</td>
                    <td><input name="summaryDemoAmt" value="${CshareSummary.summaryDemoAmt}" type="text" style="width:160px;"></td>
                </tr>
        </table>
    </form>
</div>
