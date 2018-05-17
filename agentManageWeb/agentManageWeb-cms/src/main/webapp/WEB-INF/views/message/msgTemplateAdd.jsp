<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#msgTemplateAddForm').form({
            url : '${path }/template/add',
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
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="msgTemplateAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>短信模板编号</td>
                    <td><input name="templateId" type="text" placeholder="请输入短信模板编号" class="easyui-validatebox span2" data-options="required:true" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>短信模板内容</td>
                    <td colspan="3"><textarea name="templateContent" style="width:400px;"></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>