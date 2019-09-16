<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#adjustmEditForm').form({
            url : '${path }/monthProfit/doProfitAdjust',
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
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                parent.$.modalDialog.handler.dialog('close');
            }
        });
    });
</script>
<div style="padding: 3px;">
    <form id="adjustmEditForm" method="post">
        <table class="grid">
            <input name="id" type="hidden"  value="${profitAdjust.id}">
            <input name="parentAgentId" type="hidden"  value="${profitAdjust.parentAgentId}">
            <input name="agentId" type="hidden"  value="${profitAdjust.agentId}">
            <tr>
                <td width="130px">代理商名称</td>
                <td width="130px"><input name="agentName" type="text"  value="${profitAdjust.agentName}" readonly="readonly"></td>
            </tr>
            <tr>
                <td width="130px">月份</td>
                <td width="130px"><input name="profitDate" type="text"  value="${profitAdjust.profitDate}" readonly="readonly"></td>
            </tr>
            <tr>
                <td>调整类型:</td>
                <td>
                    <select name="adjustType" id="adjustType"  width="100px">
                        <option value="1">补款</option>
                        <option value="2">扣款</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>调整金额:</td>
                <td><input name="adjustAmt" type="text" class="easyui-numberbox" precision="2" data-options="required: true"></td>
            </tr>
            <tr>
                <td>调整内容:</td>
                <td><textarea name="adjustContent" cols="20" rows="3" data-options="required: true"></textarea></td>
            </tr>
        </table>
    </form>
</div>