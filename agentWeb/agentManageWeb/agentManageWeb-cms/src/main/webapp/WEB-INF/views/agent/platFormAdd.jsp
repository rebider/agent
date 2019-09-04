<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        $('#platFormAddForm').form({
            url : '${path }/platForm/addPlatForm',
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
        <form id="platFormAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>平台号</td>
                    <td><input id="platformNum" name="platformNum" style="width:160px;" class="easyui-validatebox"></td>
                    <td>平台名称</td>
                    <td><input id="platformName" name="platformName" style="width:160px;" class="easyui-validatebox"></td>
                </tr>
                <tr>
                    <td>平台类型</td>
                    <td><input id="platformType" name="platformType" style="width:160px;" class="easyui-validatebox"></td>
                    <td>平台名称前缀</td>
                    <td><input id="posanameprefix" name="posanameprefix" style="width:160px;" class="easyui-validatebox"></td>
                </tr>
                <tr>
                    <td>POS代理商业务类型</td>
                    <td><input id="posbusitype" name="posbusitype" style="width:160px;" class="easyui-validatebox"></td>
                    <td>业务平台编号</td>
                    <td><input id="busplatform" name="busplatform" style="width:160px;" class="easyui-validatebox"></td>
                </tr>
                <tr>
                    <td>业务平台网址</td>
                    <td><input id="platformUrl" name="platformUrl" style="width:160px;" class="easyui-validatebox"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
