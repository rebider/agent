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
        $('#uploadBankNotifyForm').form({
            url : '${path}/creditcardagency/analysisBankNotifyExcel',
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
                if(msg=='noFileError'){
                    parent.$.messager.alert('错误',"请选择上传文件", 'error');
                    return false;
                }else if(msg=='noCount'){
                    parent.$.messager.alert('错误',"文件有误请重新上传", 'error');
                    return false;
                }else if(msg=='batchRepeate'){
                    parent.$.messager.alert('错误',"系统繁忙请稍后再试", 'error');
                    return false;
                }else if(msg==0000){
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                }else{
                    $(".errorMsg").show();
                    $.each(result,function(i,item){
                        $(".errorText").append("第"+item.errorIndex+"条记录 "+item.errorMsg+"<br>")
                    });
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="uploadBankNotifyForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>
                        <input type="file" id="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' />
                    </td>
                </tr>
            </table>
            <div class="errorMsg">
                <p>错误列表：</p>
                <div class="errorText">
                </div>
            </div>
        </form>
    </div>
</div>