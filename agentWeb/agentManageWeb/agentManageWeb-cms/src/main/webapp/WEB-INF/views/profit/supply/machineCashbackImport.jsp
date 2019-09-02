<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        $('#machineSupplyForm').form({
            url : '${path}/profitSupply/importFile',
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
                profitSupplyB.datagrid('reload');
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;">
        <form id="machineSupplyForm" method="post" enctype="multipart/form-data">
            <table class="grid" id="">
                <tr>
                    <td>
                        <input type="file" id="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                    <td style="display: none">标识:<input id="sign" name="sign" value="02"></td>
                </tr>
            </table>
        </form>
    </div>
</div>