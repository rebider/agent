<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agent_contract_toolbar" style="display: none;">
    <form id="searchContractForm">
        <table>
            <tr>
                <th>代理商唯一编码:</th>
                <td><input name="agentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                <th>代理商名称:</th>
                <td><input name="agName" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                <th>审批状态:</th>
                <td>
                    <select name="agStatus" style="width:140px;height:21px">
                        <option value="">  --请选择--  </option>
                        <c:forEach items="${agStatuss}" var="agStatussItem">
                            <option value="${agStatussItem.dItemvalue}">${agStatussItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <th>合同类型:</th>
                <td>
                    <select name="contType" style="width:140px;height:21px">
                        <option value="">  --请选择--  </option>
                        <c:forEach items="${contractType}" var="contractTypeItem">
                            <option value="${contractTypeItem.dItemvalue}">${contractTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <th>合同号:</th>
                <td><input name="contNum" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                <th>合同签约时间:</th>
                <td>
                    <input name="contDateStart" type="text" class="easyui-datebox" style="width:90px;">
                    <input name="contDateEnd" type="text" class="easyui-datebox" style="width:90px;">
                </td>
                <th>合同到期时间:</th>
                <td>
                    <input name="contEndDateStart" type="text" class="easyui-datebox" style="width:90px;">
                    <input name="contEndDateEnd" type="text" class="easyui-datebox" style="width:90px;">
                </td><th></th>
                <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchContract();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanContract();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agnet_contract_dataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var agnet_contract_dataGrid;
    $(function() {
        agnet_contract_dataGrid = $('#agnet_contract_dataGrid').datagrid({
            url: '${path}/agentContract/getAgentConList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AG_NAME'
            },{
                title: '审批状态',
                field: 'AG_STATUS',
                formatter: function (value, row, index) {
                    return db_options.agStatuss_map[value];
                }
            },{
                title: '合同类型',
                field: 'CONT_TYPE',
                formatter: function(value, row, index) {
                    return db_options.contractType_map[value];
                }
            },{
                title: '合同号',
                field: 'CONT_NUM'
            },{
                title: '合同签约时间',
                field: 'CONT_DATE'
            },{
                title: '合同到期时间',
                field: 'CONT_END_DATE'
            },{
                title: '是否附加协议',
                field: 'APPEND_AGREE',
                formatter: function(value, row, index) {
                    return db_options.yesOrNo_map[value];
                }
            },{
                title: '备注',
                field: 'REMARK'
            },{
                field: 'action',
                title: '操作',
                width: 180,
                formatter: function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/agentContract/agentConQuery">
                        str += $.formatString('<a href="javascript:void(0)" class="agentCon-look-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="agentConQuery(\'{0}\');" >查看详情</a>', row.AGENT_ID);
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agentContract/agentConEditPage">
                        str += $.formatString('<a href="javascript:void(0)" class="editCon-update-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="agentConEdit(\'{0}\');" >修改合同</a>', row.AGENT_ID);
                    </shiro:hasPermission>
                    return str;
                }
            }]],
            onLoadSuccess: function(data) {
                $('.agentCon-look-easyui-linkbutton-query').linkbutton({text: '查看详情'});
                $('.editCon-update-easyui-linkbutton-edit').linkbutton({text: '修改合同'});
            },
            onDblClickRow: function (dataIndex, rowData) {
            },
            toolbar: '#agent_contract_toolbar'
        });
    });

    function agentConQuery(AGENT_ID) {
        addTab({
            title: '查看-合同数据'+"-"+AGENT_ID,
            border: false,
            closable: true,
            fit: true,
            href: '/agentContract/agentConQuery?agentId='+AGENT_ID
        });
    }

    function agentConEdit(AGENT_ID) {
        addTab({
            title: '编辑-合同数据'+"-"+AGENT_ID,
            border: false,
            closable: true,
            fit: true,
            href: '/agentContract/agentConEditPage?agentId='+AGENT_ID
        });
    }

    function searchContract() {
        agnet_contract_dataGrid.datagrid('load', $.serializeObject($('#searchContractForm')));
    }

    function cleanContract() {
        $('#searchContractForm input').val('');
        $("[name='agStatus']").val('');
        $("[name='contType']").val('');
        agnet_contract_dataGrid.datagrid('load', $.serializeObject($('#searchContractForm')));
    }
</script>