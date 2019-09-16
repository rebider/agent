<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var capitalDataGrid;
    $(function () {
        capitalDataGrid = $('#capitalDataGrid').datagrid({
            url: '${path }/capitalSummary/getCapitalList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            queryParams: {
                1:1
                <shiro:hasPermission name="/capital/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            columns: [[{
                title: '资金ID',
                field: 'ID'
            },{
                title: '代理商唯一编码',
                field: 'C_AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AG_NAME'
            },{
                title: '资金类型',
                field: 'C_TYPE',
                formatter: function(value, row, index) {
                    return db_options.capitalType_map[value];
                }
            },{
                title: '资金金额',
                field: 'C_AMOUNT'
            },{
                title: '可用金额',
                field: 'C_FQ_IN_AMOUNT'
            },{
                title: '冻结金额',
                field: 'FREEZE_AMT'
            },{
                title: '打款人',
                field: 'C_PAYUSER'
            },{
                title: '打款时间',
                field: 'C_PAYTIME',
                formatter:function(value,row,index){
                    if(value==null){
                        return "";
                    }
                    var date = new Date(value);
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    var d = date.getDate();
                    return y + '-' +m + '-' + d;
                }
            },{
                title: '分期笔数',
                field: 'C_FQ_COUNT'
            },{
                title: '打款方式',
                field: 'C_PAY_TYPE',
                formatter: function(value, row, index) {
                    return db_options.payType_map[value]
                }
            },{
                title: '收款地方',
                field: 'C_IN_COM'
            },{
                title: '备注',
                field: 'REMARK'
            },{
                title: '创建时间',
                field: 'C_TIME'
            },{
                title: '更新时间',
                field: 'C_UTIME'
            },{
                title: '操作人',
                field: 'C_USER_NAME'
            }]],
            toolbar: '#capitalToolbar'
        });
    });

    /**
     * 搜索事件
     */
    function searchCapital() {
        capitalDataGrid.datagrid('load', $.serializeObject($('#capitalForm')));
    }

    /**
     * 清空事件
     */
    function cleanCapital() {
        $('#capitalForm input[name!=dataRole]').val('');
        capitalDataGrid.datagrid('load', $.serializeObject($('#capitalForm')));
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'缴纳款记录列表'" style="width:100%;overflow: hidden; ">
        <table id="capitalDataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form method="post" id ="capitalForm">
            <table>
                <tr>
                    <th>资金ID：</th>
                    <td>
                        <input name="id" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/capital/dataRole">
                            <input name="dataRole" type="hidden" value="all">
                        </shiro:hasPermission>
                    </td>
                    <c:if test="${empty agentId}">
                        <th>代理商唯一编码：</th>
                        <td><input name="cAgentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                    <td><a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchCapital()">查询</a></td>
                    <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanCapital();">清空</a>&nbsp;&nbsp;</td>
                </tr>
            </table>
        </form>
    </div>
</div>
