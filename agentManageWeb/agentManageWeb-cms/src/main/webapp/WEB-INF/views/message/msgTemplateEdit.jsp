<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#msgTemplateEditForm').form({
            url : '${path}/template/edit',
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
                	/* alert("aaa"); */
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        
       /*  $("#roleEditStatus").val('${role.status}'); */
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="msgTemplateEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>短信模板编号</td>
                    <td><input id="templateId" name="templateId" type="text" style="width:200px;" value="${cMessageTemplate.templateId}"></td>
                </tr>
               
                <tr>
                    <td>短信模板内容</td>
                    <td colspan="3"><textarea name="templateContent" style="width:400px;">${cMessageTemplate.templateContent}</textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>