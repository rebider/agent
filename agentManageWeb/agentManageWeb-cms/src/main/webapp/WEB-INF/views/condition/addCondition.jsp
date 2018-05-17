<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#conditionAddForm').form({
            url : '${path }/condition/addCondition',
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
        <form id="conditionAddForm" method="post">
            <table class="grid">
                <tr>
                    <td style="width:150px">名称</td>
                    <td >
                        <input name="name" id="name" class="easyui-validatebox" data-options="required:true" style="width:150px"/>
                    </td>
                </tr>
                 <tr>
                    <td>关键词</td>
                    <td><input class="easyui-validatebox" name="conditionKey" data-options="required:true"  style="width:150px">  </td>
                </tr>
                <tr>
                    <td>类型</td>
                    <td >
                        <select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto' ">
                            <option value="D">date型</option>
                            <option value="I">int型</option>
                            <option value="S">string型</option>
                            <option value="B">boolean型</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>