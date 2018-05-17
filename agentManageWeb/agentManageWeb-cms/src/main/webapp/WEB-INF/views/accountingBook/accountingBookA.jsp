<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#accountingBookAForm').form({
            url : '${path }/finance/accounting',
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
<div class="easyui-layout" data-options="fit:true,border:true" >
    <div data-options="region:'center',border:false" style="padding: 3px;" >
        <form id="accountingBookAForm" method="post">
            <table class="grid">
                <tr>
                    <td>秘钥</td>
                    <td><input name="custPid" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
                <tr>
                    <td>日期</td>
                    <td><input name="custId" type="text" placeholder="请输入" class="easyui-validatebox" data-options="required:true" style="width:160px;"></td>
                </tr>
            </table>
        </form>
    </div>
</div>