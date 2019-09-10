<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#importFileForm').form({
            url : '${path}/profitFactor/importFile',
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
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                    parent.$.modalDialog.handler.dialog('close');
                }else {
                    parent.$.messager.alert('错误', result.resInfo, 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;">
        <form id="importFileForm" method="post" enctype="multipart/form-data">
            <table class="grid" id="">
                <tr>
                    <td>
                        <input type="file" id="file" name="file"  style="width: 200px;"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>