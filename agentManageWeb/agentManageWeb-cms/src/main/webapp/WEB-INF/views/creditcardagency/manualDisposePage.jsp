<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
    .errorMsg{
        margin-top: 15px;
        margin-left: 15px;
        color: red;
        font-size:12px;
        display: none;
    }
</style>
<script type="text/javascript">
    $(function() {
        $('#manualDisposeForm').form({
            url : '${path}/creditcardagency/manualDispose',
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
                var msg = result.msg;
                var code = result.code;
                if(code==0000){
                    parent.$.messager.alert('提示',msg, 'info');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                }else{
                    parent.$.messager.alert('错误',msg, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="manualDisposeForm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="cardId" value="${id}">
            <table class="grid">
                <tr>
                    <td>处理结果</td>
                    <td>
                        <select class="easyui-combobox" name="status" style="width:160px;" data-options="required:true">
                            <option></option>
                            <option value="0">首刷</option>
                            <option value="1">申请成功</option>
                            <option value="3">申请失败</option>
                        </select>
                    </td>
                    <td>金额</td>
                    <td><input name="amount" type="text" placeholder="非申请失败必填" class="easyui-numberbox" style="width:160px;"></td>
                </tr>
            </table>
        </form>
    </div>
</div>