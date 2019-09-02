<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var internetCardImportList;
    $(function () {
        internetCardImportList = $('#internetCardImportList').datagrid({
            url: '${path }/internet/internetCardImportList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: 'id',
                field: 'id'
            },{
                title: '批次号',
                field: 'batchNum'
            },{
                title: '导入类型',
                field: 'importType',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "1":
                            return '流量卡卡号';
                        case "2":
                            return '供应商发卡汇总';
                        case "3":
                            return '历史北京总部发卡';
                        case "4":
                            return '退货转发数据';
                        case "5":
                            return '流量卡状态';
                        case "6":
                            return '物联网卡续费信息';
                    }
                }
            },{
                title: '导入状态',
                field: 'importStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '未处理';
                        case 2:
                            return '成功';
                        case 3:
                            return '失败';
                    }
                }
            },{
                title: '错误信息',
                field: 'errorMsg'
            },{
                title: '创建时间',
                field: 'cTime'
            }
            ]],
            toolbar: '#internetCardImportToolbar'
        });

    });

    function searchInternetImportCard() {
        internetCardImportList.datagrid('load', $.serializeObject($('#searchInternetCardImportForm')));
    }

    function cleanInternetImportCard() {
        $('#searchInternetCardImportForm input').val('');
        internetCardImportList.datagrid('load', {});
    }

    function internetCardAdd(){
        parent.$.modalDialog({
            title: '上传文件',
            width: 600,
            height: 400,
            maximizable: true,
            href: '${path }/internet/toInternetCardAdd',
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = internetCardImportList;
                    var gr = parent.$.modalDialog.handler.find('#uploadInternetCardForm');
                    gr.submit();
                }
            }]
        });
    }

    function exportErrorExcel() {
        var batchNum = $("input[name='batchNum']").val();
        var importType = $("#importType").combobox('getValue');
        var importStatus = $("#importStatus").combobox('getValue');
        if(batchNum=='' || batchNum==undefined){
            info("批次号不能为空");
            return;
        }
        if(importType=='' || importType==undefined){
            info("导入类型不能为空");
            return;
        }
        if(importStatus=='' || importStatus==undefined){
            info("导入状态不能为空");
            return;
        }
        if(importStatus!=3){
            info("导入状态只能导出失败");
            return;
        }
        $("#searchInternetCardImportForm").submit();
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="internetCardImportList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchInternetCardImportForm" action="/internet/exportInternetCard">
            <table>
                <tr>
                    <th>批次号:</th>
                    <td><input name="batchNum" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>导入类型:</th>
                    <td>
                        <select class="easyui-combobox" name="importType" id="importType" style="width:160px;height:21px">
                            <option value="">-全部-</option>
                            <c:forEach items="${cardImportTypeList}" var="cardImportType">
                                <option value="${cardImportType.key}">${cardImportType.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>导入状态:</th>
                    <td>
                        <select class="easyui-combobox" name="importStatus"  id="importStatus" style="width:160px;height:21px">
                            <option value="">-全部-</option>
                            <c:forEach items="${importStatusList}" var="importStatus">
                                <option value="${importStatus.key}">${importStatus.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchInternetImportCard();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanInternetImportCard();">清空</a>
                        <shiro:hasPermission name="/internet/exportInternetCard">
                        <a href="javascript:void(0)" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="exportErrorExcel()">导出错误excel</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="internetCardImportToolbar">
    <shiro:hasPermission name="/internet/toInternetCardAdd">
    <a onclick="internetCardAdd()" href="javascript:void(0);" class="easyui-linkbutton"
       data-options="plain:true,iconCls:'fi-plus icon-green'">上传文件</a>
    </shiro:hasPermission>
</div>

