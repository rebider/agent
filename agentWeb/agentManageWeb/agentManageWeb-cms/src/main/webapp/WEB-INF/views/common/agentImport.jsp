<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        //模板数据导入
        $('#agentImportFileForm').form({
            url : '${path}'+'/agImport/importExcel',
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
                parent.$.modalDialog.handler.dialog('close');
            }
        });

        //业务数据更新
        $('#importBusRegionInfoExcel').form({
            url : '${path}'+'/agImport/importBusRegionInfoExcel',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                console.log(result);
                progressClose();
                parent.$.modalDialog.handler.dialog('close');
            }
        });

        //订单无SN导入
        $('#importOrderExcel').form({
            url : '${path}'+'/agImport/importOrderExcel',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                console.log(result);
                progressClose();
                parent.$.modalDialog.handler.dialog('close');
            }
        });

        //订单SN导入
        $('#importOrderExcelSN').form({
            url : '${path}'+'/agImport/importOrderExcelSN',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                console.log(result);
                progressClose();
                parent.$.modalDialog.handler.dialog('close');
            }
        });
    });
</script>
<%--<input type="hidden" value="${callBack}" id="callBack">--%>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="agentImportFileForm" method="post" action="/agImport/importExcel"  enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>
                        代理商模板导入
                        <input type="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                </tr>
            </table>
        </form>

        <form id="importBusRegionInfoExcel" method="post" action="/agImport/importBusRegionInfoExcel" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>
                        代理商业务导入更改
                        <input type="file"  name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                </tr>
            </table>
        </form>

        <form id="importOrderExcel" method="post" action="/agImport/importOrderExcel" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>
                        <div>订单无SN导入</div>
                        <input type="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                </tr>
            </table>
        </form>

        <form id="importOrderExcelSN" method="post" action="/agImport/importOrderExcelSN" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td>
                        <div>订单SN导入</div>
                        <input type="file" name="file" class="form-control" style='width: 200px;margin-left: 20px;float: left;' multiple="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>