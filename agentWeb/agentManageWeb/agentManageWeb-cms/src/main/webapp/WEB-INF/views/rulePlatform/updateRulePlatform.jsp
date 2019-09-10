<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#rulePlatformUpdateForm').form({
            url : '${path }/rulePlatform/updateRulePlatform',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
            },
            success : function(result) {
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
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="rulePlatformUpdateForm" method="post">
            <table class="grid">
                <tr>
                    <td style="width:150px">名称</td>
                    <td >
                        <input name="id" id="id" type="hidden" value="${cRulePlatform.id}"/>
                        <input name="name" id="name" value="${cRulePlatform.name}" class="easyui-validatebox" data-options="required:true" style="width:150px"/>
                    </td>
                </tr>
                <tr>
                    <td>平台编码</td>
                    <td><input id="code" name="code" value="${cRulePlatform.code}" class="easyui-validatebox" data-options="required:true" style="width:150px"></td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td >
                        <input name="department" id="department" value="${cRulePlatform.department}" class="easyui-validatebox" data-options="required:true" style="width:150px"/>
                    </td>
                </tr>
                <tr>
                    <td>小组</td>
                    <td >
                        <input name="mgroup" id="mgroup" value="${cRulePlatform.mgroup}"  class="easyui-validatebox" data-options="required:true" style="width:150px"/>
                    </td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td >
                        <input name="description" id="description" value="${cRulePlatform.mgroup}" style="width:150px" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>