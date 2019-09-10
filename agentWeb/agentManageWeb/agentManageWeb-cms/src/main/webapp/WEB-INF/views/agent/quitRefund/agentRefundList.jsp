<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agentRefundList;
    $(function () {
        agentRefundList = $('#agentRefundList').datagrid({
            url: '${path }/quitRefund/getQuitRefundList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            queryParams: {
                1:1
                <shiro:hasPermission name="/quitRefund/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '申请编号',
                field: 'ID'
            },{
                title: '申请退出ID',
                field: 'QUIT_ID'
            },{
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AGENT_NAME'
            },{
                title: '实际退款金额',
                field: 'REALITY_SUPP_DEPT'
            },{
                title: '申请退款金额',
                field: 'REFUND_AMT'
            },{
                title: '审批通过时间',
                field: 'APPROVE_TIME'
            },{
                title: '审核状态',
                field: 'CLO_REVIEW_STATUS',
                formatter : function(value, row, index) {
                    return db_options.agStatusi_map[value]
                }
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
            },{
                field: 'action',
                title: '操作',
                width: 100,
                formatter: function (value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="refund-easyui-linkbutton-query" data-options="plain:true,iconCls:\'icon-tip\'" onclick="quitRefundQuery(\'{0}\',\'{1}\');" >查看</a>', row.ID, row.CLO_REVIEW_STATUS);
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.refund-easyui-linkbutton-query').linkbutton({text: '查看'});
            },
            toolbar: '#agentRefundToolbar'
        });
    });

    function quitRefundQuery(id, cloReviewStatus) {
        addTab({
            title: '代理商退出申请退款-查看',
            border: false,
            closable: true,
            fit: true,
            href: '/quitRefund/toQuitRefundQuery?id=' + id + "&cloReviewStatus=" + cloReviewStatus
        });
    }

    function searchAgentQuit() {
        agentRefundList.datagrid('load', $.serializeObject($('#searchAgentRefundForm')));
    }

    function cleanAgentQuit() {
        $('#searchAgentRefundForm input').val('');
        agentRefundList.datagrid('load', $.serializeObject($('#searchAgentRefundForm')));
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'退款列表列表'" style="width:100%;overflow: hidden; ">
        <table id="agentRefundList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id="searchAgentRefundForm">
            <table>
                <tr>
                    <th>退出编号：</th>
                    <td>
                        <input name="quitId" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/quitRefund/dataRole">
                            <input name="dataRole" type="hidden" value="all">
                        </shiro:hasPermission>
                    </td>
                    <c:if test="${empty agentId}">
                        <th>代理商唯一编码：</th>
                        <td><input name="agentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称：</th>
                        <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                    <th>审批状态：</th>
                    <td>
                        <select class="easyui-combobox" name="cloReviewStatus" style="width:160px;height:21px">
                            <option value=""></option>
                            <c:forEach var="agStatus" items="${agStatusList}">
                                <option value="${agStatus.dItemvalue}">${agStatus.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>审批时间：</th>
                    <td><input name="approveTimeStart" class="easyui-datetimebox" style="line-height:17px;border:1px solid #ccc;width:160px;" value=""></td>
                    <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至</th>
                    <td><input name="approveTimeEnd" class="easyui-datetimebox" style="line-height:17px;border:1px solid #ccc;width:160px;" value=""></td>
                    <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgentQuit();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentQuit();">清空</a>
                    </td><td/><th/>
                </tr>
            </table>
        </form>
    </div>
</div>