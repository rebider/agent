<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#payCompEditForm').form({
            url : '${path }/paycomp/edit',
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

    $("#editStatus").val('${payComp.status}');

</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="payCompEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>打款公司名称</td>
                    <td><input name="id" type="hidden"  value="${payComp.id}">
                        <input name="comName" value="${payComp.comName}" maxlength="24" type="text" placeholder="请输入打款公司名称" class="easyui-validatebox span2" data-options="required:true" ></td>
                </tr>
                <tr>
                    <td>状态</td>
                    <td >
                        <select id = "editStatus" name="status"  class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto',required:true">
                            <option value="1">生效</option>
                            <option value="2">无效</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3"><textarea name="remark" >${payComp.remark}</textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>