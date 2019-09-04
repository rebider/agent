<%--suppress ALL --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    $(function(){
        $('#adjustForm').form({
            url : '${path }/deductTaxDetail/doAdjust',
            onSubmit : function() {
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('提示', result.msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="调整" style="overflow: hidden;padding: 3px;">
        <form id="adjustForm" method="post">
            <input type="hidden" id="deductTaxID" name="id" value="${id}">
            <table class="grid">
                <tr>
                    <td>调整金额：</td>
                    <td>
                        <input id="adjustAmt" name="adjustAmt" maxlength="30" type="text" placeholder="请输入调整金额" precision="2" class="easyui-numberbox" data-options="required:true" style="width:200px;" />
                    </td>
                </tr>
                <tr>
                    <td>调整原因：</td>
                    <td>
                        <input id="adjustReson" name="adjustReson" class="easyui-textbox" data-options="required:true,multiline:true" style="width:200px;height: 120px;">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>