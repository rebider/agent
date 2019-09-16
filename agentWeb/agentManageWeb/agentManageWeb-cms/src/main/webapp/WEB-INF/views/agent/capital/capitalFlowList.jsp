<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var capitalFlowList;
    $(function () {
        capitalFlowList = $('#capitalFlowList').datagrid({
            url: '${path }/capitalFlow/getCapitalFlowList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            queryParams: {
                1:1
                <shiro:hasPermission name="/capitalFlow/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '流水编号',
                field: 'ID'
            },{
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AGENT_NAME'
            },{
                title: '资金类型',
                field: 'C_TYPE',
                formatter: function(value, row, index) {
                        return db_options.capitalType_map[value]
                }
            },{
                title: '资金记录ID',
                field: 'CAPITAL_ID'
            },{
                title: '原数据类型',
                field: 'SRC_TYPE',
                formatter: function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '保证金管理';
                        case 2:
                            return '入网';
                        case 3:
                            return '分润入账';
                        case 4:
                            return '代理商退出';
                        case 5:
                            return '订单';
                    }
                }
            },{
                title: '原数据id',
                field: 'SRC_ID'
            },{
                title: '操作前金额',
                field: 'BEFORE_AMOUNT'
            },{
                title: '资金金额',
                field: 'C_AMOUNT'
            },{
                title: '操作类型',
                field: 'OPERATION_TYPE',
                formatter: function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '出账';
                        case 2:
                            return '入账';
                    }
                }
            },{
                title: '流水状态',
                field: 'FLOW_STATUS',
                formatter: function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '未生效';
                        case 1:
                            return '有效';
                    }
                }
            },{
                title: '备注',
                field: 'REMARK'
            },{
                title: '创建时间',
                field: 'C_TIME'
            },{
                title: '更新时间',
                field: 'U_TIME'
            },{
                title: '创建人',
                field: 'C_USER_NAME'
            },{
                title: '更新人',
                field: 'U_USER_NAME'
            }]],
            toolbar: '#capitalFlowToolbar'
        });
    });

    function searchCapitalFlow() {
        capitalFlowList.datagrid('load', $.serializeObject($('#searchCapitalFlowForm')));
    }

    function cleanCapitalFlow() {
        $('#searchCapitalFlowForm input[name!=dataRole]').val('');
        capitalFlowList.datagrid('load', $.serializeObject($('#searchCapitalFlowForm')));
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'资金流水列表'" style="width:100%;overflow: hidden; ">
        <table id="capitalFlowList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchCapitalFlowForm">
            <table>
                <tr>
                    <th>流水编号:</th>
                    <td>
                        <input name="id" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/capitalFlow/dataRole">
                            <input name="dataRole" type="hidden" value="all">
                        </shiro:hasPermission>
                    </td>
                    <c:if test="${empty agentId}">
                        <th>代理商唯一编码:</th>
                        <td><input name="agentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称:</th>
                        <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                    <th>流水状态:</th>
                    <td>
                        <select class="easyui-combobox" name="flowStatus" style="width:160px;height:21px">
                            <option value=""></option>
                            <option value="1">有效</option>
                            <option value="0">未生效</option>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchCapitalFlow();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanCapitalFlow();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>