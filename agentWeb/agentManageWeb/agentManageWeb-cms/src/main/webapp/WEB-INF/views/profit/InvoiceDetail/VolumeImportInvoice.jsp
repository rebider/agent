<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<!--批量导入发票-->
<script type="text/javascript">

    //批量导入 提交
    $(function() {
        $('#volumeImportInvoiceForm').form({
            url : '${path }/profit/invoiceDetail/importInvoiceData',
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
                if (result.success == '1') {
                    parent.$.messager.alert('成功', result.obj, 'ok');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                }else {
                    parent.$.messager.alert('错误', result.resInfo, 'error');
                }
            }
        });
    });


    //下载模板
    function downLoadTemplate() {
        location.href = '${path}/profit/invoiceDetail/dowmloadModel';
       // parent.$.messager.alert('提示', "此模板不能使用excel公式整合数据", 'ok');
    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="volumeImportInvoiceForm" method="post" enctype="multipart/form-data">
            <div>
                <input id="file" name="file" type="file" style='width: 160px;'>
            </div>
        </form>
        <div>
            <a onclick="downLoadTemplate();" href="javascript:void(0);" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'fi-plus icon-green'">下载模板</a>
        </div>
    </div>

</div>