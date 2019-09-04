<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#conditionUpdateForm').form({
            url : '${path }/condition/updateCondition',
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
        $("#typeId").combobox({
            url : "/condition/typeList",//返回json数据的url
            valueField : "type",//这个id和你返回json里面的id对应
            textField : "typeName" //这个text和你返回json里面的text对应
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="conditionUpdateForm" method="post">
            <table class="grid">
                <tr>
                    <td style="width:150px">名称</td>
                    <td >
                        <input name="id" id="id" type="hidden" value="${cCondition.id}"/>
                        <input name="name" id="name" value="${cCondition.name}" class="easyui-validatebox" data-options="required:true" style="width:150px"/>
                    </td>
                </tr>
                <tr>
                    <td>关键词</td>
                    <td><input class="easyui-validatebox" name="conditionKey" value="${cCondition.conditionKey}" data-options="required:true"  style="width:150px">  </td>
                </tr>
                <tr>
                    <td>类型</td>
                    <td >
                        <input id="typeId" name="type" value="${cCondition.type}" data-options="required:true" >
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>