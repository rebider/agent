<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var capitalSummaryDataGrid;
    $(function () {
        capitalSummaryDataGrid = $('#capitalSummaryDataGrid').datagrid({
            url: '${path }/capitalSummary/getCapitalSummaryList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 15,
            queryParams: {
                1:1
                <shiro:hasPermission name="/capitalSummary/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            pageList: [15, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AG_NAME'
            },{
                title: '上级代理商唯一编码',
                field: 'PARENT_AGENT_ID'
            },{
                title: '上级代理商名称',
                field: 'PARENT_AGENT_NAME'
            },{
                title: '费用类型',
                field: 'C_TYPE',
                formatter: function(value, row, index) {
                    return db_options.capitalType_map[value];
                }
            },{
                title: '总金额',
                field: 'SUM_AMT'
            },{
                title: '可用金额',
                field: 'KY_AMT'
            },{
                title: '冻结金额',
                field: 'SUM_FREEZE_AMT'
            }, {
                field: 'action',
                title: '操作',
                width: 100,
                formatter: function (value, row, index) {
                    var str = '';
                    if(row.KY_AMT>0 && row.C_TYPE!='FUWUFEI' && row.C_TYPE!='REIHEBAOFWF'){
                        <shiro:hasPermission name="/capitalSummary/cz">
                            str += $.formatString('<a href="javascript:void(0)" class="capital-look-easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="operation(\'{0}\',\'{1}\',\'{2}\');" >操作</a>', row.AGENT_ID,row.C_TYPE,row.KY_AMT);
                        </shiro:hasPermission>
                    }
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.capital-look-easyui-linkbutton-add').linkbutton({text: '操作'});
            },
            toolbar: '#capitalSummaryToolbar'
        });
    });

    /**
     * 搜索事件
     */
    function searchCapitalSummary() {
        capitalSummaryDataGrid.datagrid('load', $.serializeObject($('#capitalSummaryForm')));
    }

    /**
     * 清空事件
     */
    function cleanCapitalSummary() {
        $('#capitalSummaryForm input[name!=dataRole]').val('');
        capitalSummaryDataGrid.datagrid('load', $.serializeObject($('#capitalSummaryForm')));
    }

    /**
     * 导出数据
     */
    function exportCapitalSummary() {
        $('#capitalForm').form({
            url: '${path }/capitalSummary/exportCapitalSummary',
            onSubmit: function() {
                return $(this).form('validate');
            }
        });
        $('#capitalForm').submit();
    }

    function operation(agentId,cType,amount) {
        addTab({
            title: '保证金-操作',
            border: false,
            closable: true,
            fit: true,
            href: '/capitalChange/toCapitalChangeAdd?agentId='+agentId+"&cType="+cType+"&amount="+amount
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'汇总列表'" style="width:100%;overflow: hidden; ">
        <table id="capitalSummaryDataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form method="post" id ="capitalSummaryForm">
            <table>
                <tr>
                    <th>代理商唯一编码：</th>
                    <td>
                        <input name="ID" type="text" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/capitalSummary/dataRole">
                            <input name="dataRole" type="hidden" value="all">
                        </shiro:hasPermission>
                    </td>
                    <th>代理商名称：</th>
                    <td><input name="AG_NAME" style="line-height:17px;border:1px solid #ccc"></td>
                    <td><a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchCapitalSummary()">查询</a></td>
                    <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanCapitalSummary();">清空</a>&nbsp;&nbsp;</td>
                    <%--<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportCapitalSummary();">导出数据</a></td>--%>
                </tr>
            </table>
        </form>
    </div>
</div>
