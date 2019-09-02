<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#custEditForm').form({
            url : '${path }/profit/profit_refuse',
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
    <form id="custEditForm" method="post">
        <table class="grid">
            <input name="id" type="hidden" value="${profitDetailM.id}">
            <input name="agentId" type="hidden" value="${profitDetailM.agentId}">
            <tr>
                <td>冻结原因:</td>
                <td><textarea name="remark" cols="20" rows="3"></textarea></td>
            </tr>
        </table>
    </form>
</div>