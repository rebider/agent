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
        $('#uploadInternetCardForm').form({
            url : '${path}/internet/analysisFile',
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
                var data = eval("(" + result + ")");
                if(!data.success){
                    info(data.msg);
                }else{
                    parent.$.modalDialog.handler.dialog('close');
                    internetCardImportList.datagrid('reload');
                    info("导入处理中,批次号:"+data.msg+",可根据批次号查询。");
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="uploadInternetCardForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td width="150px">导入类型</td>
                    <td>
                        <select name="importType" style="width:120px;height:21px">
                            <c:forEach items="${cardImportType}" var="cardImport" >
                                <option value="${cardImport.key}">${cardImport.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>导入文件</td>
                    <td>
                        <input type="file" id="file" name="file" class="form-control" style='width: 160px;float: left;' />
                    </td>
                </tr>
                <tr>
                    <td>供应商发卡汇总模板</td>
                    <td><a href="/static/template/InternetCardModel1.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a></td>
                </tr>
                <tr>
                    <td>历史北京总部发卡模板</td>
                    <td><a href="/static/template/InternetCardModel2.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a></td>
                </tr>
                <tr>
                    <td>流量卡卡号模板</td>
                    <td><a href="/static/template/InternetCardModel3.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a></td>
                </tr>
                <tr>
                    <td>流量卡状态模板</td>
                    <td><a href="/static/template/InternetCardModel4.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a></td>
                </tr>
                <tr>
                    <td>退货转发数据模板</td>
                    <td><a href="/static/template/InternetCardModel5.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a></td>
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