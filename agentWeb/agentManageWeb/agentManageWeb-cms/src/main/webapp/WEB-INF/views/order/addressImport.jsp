<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        $('#addressImportFileForm').form({
            url : '${path}/address/importExcel',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                eval("var data = "+result+";");
                info(data.resInfo);
                progressClose();
                parent.$.modalDialog.handler.dialog('close');
                addressManageList.datagrid('reload');
            }
        });
    });
</script>
<%--<input type="hidden" value="${callBack}" id="callBack">--%>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="addressImportFileForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>
                        <input type="file" id="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>