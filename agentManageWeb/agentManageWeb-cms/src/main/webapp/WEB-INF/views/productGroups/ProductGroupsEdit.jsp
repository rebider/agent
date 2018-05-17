<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
     $(function() {
        $('#productGroupEditForm').form({
            url : '${path }/product/editProductGroups',
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
        $("#productGroupType").val('${cProductGroup.productGroupType}');
    });
</script>
<div style="padding: 3px;">
    <form id="productGroupEditForm" method="post">
        <table class="grid">
         	<tr>
                <td>产品组编码</td>
                <td>
                	<input name="groupId" type="hidden"  value="${cProductGroup.groupId}">
                	<input name="productGroupId" type="text" value="${cProductGroup.productGroupId}"  class="easyui-validatebox" data-options="required:true" style="width:160px;">
                </td>
            </tr>
            <tr>
                <td>产品组名称</td>
                <td><input name="productName" type="text" value="${cProductGroup.productName}"  class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
            </tr>
            <tr>
                    <td>产品组类型</td>
                    <td>
                    <select class="easyui-combobox" id="productGroupType" name="productGroupType" style="width:160px;" data-options="required:true">
						<option value="N">正常</option>
						<option value="A">优惠活动</option>
						<option value="O">其他</option>
					</select>
					</td>
                </tr>
        </table>
    </form>
</div>
