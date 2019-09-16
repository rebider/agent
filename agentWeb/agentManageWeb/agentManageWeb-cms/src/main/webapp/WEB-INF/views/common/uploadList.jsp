<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        $('#multiFileForm').form({
            url : '${path}/multiFile/uploadFile',
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
                stepping(result);
                parent.$.modalDialog.handler.dialog('close');
            }
        });
    });
</script>
<%--<input type="hidden" value="${callBack}" id="callBack">--%>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="multiFileForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>
                        <input type="file" id="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                        <input type="hidden" id="attDataType" name="attDataType" class="form-control" style='width: 200px;margin-left: 20px;float: left;' value="${attDataType}"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>