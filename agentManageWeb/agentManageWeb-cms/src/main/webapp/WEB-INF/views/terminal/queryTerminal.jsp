<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
        $('#terminalEditForm').form({
            url : '${path }/terminal/editTerminal',
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
    <form id="terminalEditForm" method="post">
        <table class="grid">
         	<tr>
                <td style="width:150px;">终端编号</td>
                <td>
                	<input name="terminalId" type="hidden"  value="${cterminal.terminalId}">
                	<input name="terminalCode" type="text" value="${cterminal.terminalCode}"  class="class="easyui-textbox"" data-options="required:true, disabled:true" style="width:160px;">
                </td>
            </tr>
            <tr>
                <td>终端名称</td>
                <td><input name="terminalName" type="text" value="${cterminal.terminalName}"  class="class="easyui-textbox"" data-options="required:true, disabled:true" style="width:160px;"></td>
            </tr>
            <tr>
                <td>授权码</td>
                <td><input name="terminalAuth" type="text" value="${cterminal.terminalAuth}"  class="class="easyui-textbox"" data-options="required:true, disabled:true" style="width:160px;"></td>
            </tr>
        </table>
    </form>
</div>
