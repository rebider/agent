<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        $('#terminalImportFileForm').form({
            url : '${path}/terminal/importTerminal',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                var data = eval("(" + result + ")");
                if(!data.success && data.msg!=undefined){
                    info(data.msg);
                    progressClose();
                    return;
                }else{
                    info("导入成功");
                    progressClose();
                }

                parent.$.modalDialog.handler.dialog('close');
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;">
        <form id="terminalImportFileForm" method="post" enctype="multipart/form-data">
         <%--   <input type="hidden" name="busId" value="${busId}">--%>
            <table class="grid" id="">
                <tr>
                    <td>
                        <input type="file" id="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>