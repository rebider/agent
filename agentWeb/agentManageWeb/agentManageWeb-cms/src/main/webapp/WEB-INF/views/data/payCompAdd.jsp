<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#payCompAddForm').form({
            url : '${path }/paycomp/add',
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
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="payCompAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>打款公司名称</td>
                    <td>
                        <input name="status"  maxlength="24" type="hidden"  class="easyui-validatebox span2" data-options="required:true" value="1">
                        <input name="comName"  maxlength="24" type="text" placeholder="请输入打款公司名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3"><textarea name="remark"></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>