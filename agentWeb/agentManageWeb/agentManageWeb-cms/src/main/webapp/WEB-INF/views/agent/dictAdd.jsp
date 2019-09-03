<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        $('#dictAddForm').form({
            url : '${path }/dict/addDict',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) {

                }
                return isValid;

            },
            success : function(result) {
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:true" >
    <div data-options="region:'center',border:false" style="padding: 3px;" >
        <form id="dictAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>字典类型</td>
                    <td><input id="dGroup" name="dGroup" style="width:160px;" class="easyui-validatebox"></td>
                    <td>字典编号</td>
                    <td><input id="dArtifact" name="dArtifact" style="width:160px;" class="easyui-validatebox"></td>
                </tr>
                <tr>
                    <td>字典数值</td>
                    <td><input id="dItemvalue" name="dItemvalue" style="width:160px;" class="easyui-validatebox"></td>
                    <td>字典名称</td>
                    <td><input id="dItemname" name="dItemname" style="width:160px;" class="easyui-validatebox"></td>
                </tr>
                <tr>
                    <td>字典备注</td>
                    <td><input id="dItemnremark" name="dItemnremark" style="width:160px;" class="easyui-validatebox"></td>
                    <td>字典种类</td>
                    <td><input id="dSort" name="dSort" style="width:160px;" class="easyui-validatebox"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
