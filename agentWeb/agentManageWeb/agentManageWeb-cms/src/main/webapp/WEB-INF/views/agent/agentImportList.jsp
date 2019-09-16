<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agnet_import_list_ConditionToolbar" style="display: none;">
    <form  method="post" action="" id ="agnet_import_list__ConditionToolbar_searchform" >
        <table>
            <tr>
                <td>处理状态:</td>
                <td>
                    <select name="dealstatus" style="width:140px;height:21px" >
                        <option value="">--请选择--</option>
                        <option value="0">未处理</option>
                        <option value="1">处理中</option>
                        <option value="2">成功</option>
                        <option value="3">失败</option>
                    </select>
                </td>
                <td>
                    <select name="datatype" style="width:140px;height:21px" >
                        <option value="">--请选择--</option>
                        <option value="BASICS">a基础信息a</option>
                        <option value="BUSINESS">a业务信息a</option>
                        <option value="CONTRACT">a合同信息a</option>
                        <option value="PAYMENT">a缴款信息a</option>
                        <option value="BASBUSR">a业务关系a</option>
                        <option value="GATHER">a收款账户a</option>
                        <option value="NETINAPP">a代理商入网开通任务a</option>
                        <option value="BUSAPP">a业务入网开通任务a</option>
                        <option value="OBASE">o订单基础信息o</option>
                        <option value="OGOODS">o订单商品信息o</option>
                        <option value="OLOGISTICS">o订单物流信息o</option>
                        <option value="ORETURN">o订单退货信息o</option>
                        <option value="ORLOGI">o订单退货物流信息o</option>
                    </select>
                </td>
                <td>
                   <input type="text" name="batchcode" style="width:140px;height:21px">
                </td>
                <td>
                    <a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchagnet_import_list_()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanagnet_import_listSearchForm();">清空</a>
                </td>
            </tr>
        </table>
    </form>
    <a id="angetImportFormDialog" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">导入</a>
    <a id="analysisRecode" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-graph-horizontal icon-green'">分析处理</a>
    <a id="importBusRegionInfoExcel" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">导入业务信息更新</a>
    <a id="caAgentList" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">批量工商认证</a>
    <a id="importOrderInfoExcel" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">1:订单无SN导入</a>
    <a id="importPareseOrderData" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">2:解析无SN订单数据</a>
    <a id="importOrderInfoExcelSN" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">3:订单SN导入</a>
    <a id="importPareseOrderDataSN" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">4:解析SN订单数据</a>
    <%--<a id="importPareseOrderLogic" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">5:解析物流信息到redis</a>--%>
    <%--<a id="importPareseOrderReturnData" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'">6:解析处理退货数据</a>--%>
</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agnet_import_list__ConditionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">

    var agnet_import_list__ConditionDataGrid;
    $(function() {
        //代理商表格
        agnet_import_list__ConditionDataGrid = $('#agnet_import_list__ConditionDataGrid').datagrid({
            url : '${path}/agImport/list',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : 'ID',
                field : 'id',
                sortable : true
            },{
                title : '代理商唯一编码',
                field : 'dataid',
                sortable : true
            },{
                title : '类型',
                field : 'datatype',
                sortable : true
            } , {
                title : '批次码',
                field : 'batchcode',
                sortable : true
            } ,{
                title : '创建时间',
                field : 'cTime',
                sortable : true
            }, {
                title : '处理时间',
                field : 'dealTime',
                sortable : true
            } , {
                title : '处理状态',
                field : 'dealstatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch(row.dealstatus){
                        case 0:
                            return "待处理";
                        case 1:
                            return "处理中";
                        case 2:
                            return "成功";
                        case 3:
                            return "失败";
                    }
                    return "";
                }
            } , {
                title : '处理结果',
                field : 'dealmsg',
                sortable : true
            } ,{
                field : 'action',
                title : '操作',
                width : 350,
                formatter : function(value, row, index) {

                    var str = '';

                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
            },
            onDblClickRow:function(dataIndex,rowData){
            },
            toolbar : '#agnet_import_list_ConditionToolbar'
        });


        //代理商入网form
        $("#angetImportFormDialog").click(function(){
            parent.$.modalDialog({
                title : '代理商导入',
                width : 300,
                height : 500,
                href : "/agImport/importView",
                buttons : [ {
                    text : '确定',
                    handler : function() {
                        var f = parent.$.modalDialog.handler.find('#agentImportFileForm');
                        f.submit();
                    }
                } ]
            });
        });


        //代理商导入businforegion
        $("#importBusRegionInfoExcel").click(function(){
            parent.$.modalDialog({
                title : '代理商导入业务信息更改',
                width : 300,
                height : 500,
                href : "/agImport/importView",
                buttons : [ {
                    text : '确定',
                    handler : function() {
                        var f = parent.$.modalDialog.handler.find('#importBusRegionInfoExcel');
                        f.submit();
                    }
                } ]
            });
        });


        $("#analysisRecode").click(function(){
            $.ajaxL({
                type: "POST",
                url: "/agImport/analysisRecode",
                dataType:'json',
                beforeSend:function(){
                    progressLoad();
                },
                success: function(msg){
                    info(msg.resInfo);
                },
                complete:function (XMLHttpRequest, textStatus) {
                    progressClose();
                    agnet_import_list__ConditionDataGrid.datagrid('reload');
                }
            });
        });


        $("#caAgentList").click(function(){
            $.ajaxL({
                type: "POST",
                url: "/agImport/caAgentList",
                dataType:'json',
                beforeSend:function(){
                    progressLoad();
                },
                success: function(msg){
                    info(msg.resInfo);
                },
                complete:function (XMLHttpRequest, textStatus) {
                    progressClose();
                }
            });
        });


        //老订单导入无SN
        $("#importOrderInfoExcel").click(function(){
            parent.$.modalDialog({
                title : '订单无SN导入',
                width : 300,
                height : 500,
                href : "/agImport/importView",
                buttons : [ {
                    text : '确定',
                    handler : function() {
                        var f = parent.$.modalDialog.handler.find('#importOrderExcel');
                        f.submit();
                    }
                } ]
            });
        });

        //订单无SN解析
        $("#importPareseOrderData").click(function(){
            $.ajaxL({
                type: "POST",
                url: "/agImport/importPareseOrderData",
                dataType: 'json',
                beforeSend: function(){
                    progressLoad();
                },
                success: function(msg){
                    info(msg.resInfo);
                },
                complete: function (XMLHttpRequest, textStatus) {
                    progressClose();
                    agnet_import_list__ConditionDataGrid.datagrid('reload');
                }
            });
        });


        //老订单导入含有SN
        $("#importOrderInfoExcelSN").click(function(){
            parent.$.modalDialog({
                title : '订单SN导入',
                width : 300,
                height : 500,
                href : "/agImport/importView",
                buttons : [ {
                    text : '确定',
                    handler : function() {
                        var f = parent.$.modalDialog.handler.find('#importOrderExcelSN');
                        f.submit();
                    }
                } ]
            });
        });

        //订单含有SN解析
        $("#importPareseOrderDataSN").click(function(){
            $.ajaxL({
                type: "POST",
                url: "/agImport/importPareseOrderDataSN",
                dataType: 'json',
                beforeSend: function(){
                    progressLoad();
                },
                success: function(msg){
                    info(msg.resInfo);
                },
                complete: function (XMLHttpRequest, textStatus) {
                    progressClose();
                    agnet_import_list__ConditionDataGrid.datagrid('reload');
                }
            });
        });


        $("#importPareseOrderLogic").click(function(){
            $.ajaxL({
                type: "POST",
                url: "/agImport/pareseOrderLogic",
                dataType:'json',
                beforeSend:function(){
                    progressLoad();
                },
                success: function(msg){
                    info(msg.resInfo);
                },
                complete:function (XMLHttpRequest, textStatus) {
                    progressClose();
                    agnet_import_list__ConditionDataGrid.datagrid('reload');
                }
            });
        });


        $("#importPareseOrderReturnData").click(function(){
            $.ajaxL({
                type: "POST",
                url: "/agImport/importPareseOrderReturnData",
                dataType:'json',
                beforeSend:function(){
                    progressLoad();
                },
                success: function(msg){
                    info(msg.resInfo);
                },
                complete:function (XMLHttpRequest, textStatus) {
                    progressClose();
                    agnet_import_list__ConditionDataGrid.datagrid('reload');
                }
            });
        });
    });


    /**
     * 搜索事件
     */
    function searchagnet_import_list_() {
        agnet_import_list__ConditionDataGrid.datagrid('load', $.serializeObject($('#agnet_import_list__ConditionToolbar_searchform')));
    }

    function cleanagnet_import_listSearchForm() {
        $("[name='datatype']").val('');
        $("[name='dealstatus']").val('');
        agnet_import_list__ConditionDataGrid.datagrid('load', {});
    }
</script>
