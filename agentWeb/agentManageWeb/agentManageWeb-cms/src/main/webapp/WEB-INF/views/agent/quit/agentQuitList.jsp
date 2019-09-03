<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agentQuitList;
    $(function () {
        agentQuitList = $('#agentQuitList').datagrid({
            url: '${path }/agentQuit/getAgentQuitList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            queryParams: {
                1:1
                <shiro:hasPermission name="/agentQuit/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '申请编号',
                field: 'ID'
            },{
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AGENT_NAME'
            },{
                title: '退出平台ids',
                field: 'QUIT_BUS_ID',
                hidden:true
            },{
                title: '申请退出平台',
                field: 'QUIT_PLATFORM',
                formatter : function(value, row, index) {
                    switch (value) {
                        case '1':
                            return 'POS';
                        case '2':
                            return '手刷';
                        case '3':
                            return 'POS+手刷';
                    }
                }
            },{
                title: '缴款项欠款',
                field: 'CAPITAL_DEBT'
            },{
                title: '订单欠款',
                field: 'ORDER_DEBT'
            },{
                title: '分润欠款',
                field: 'PROFIT_DEBT'
            },{
                title: '总欠款',
                field: 'AGENT_DEPT'
            },{
                title: '操作方式',
                field: 'SUPP_TYPE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case '1':
                            return '代理商打款';
                        case '2':
                            return '公司打款';
                        case '3':
                            return '无';
                    }
                }
            },{
                title: '操作金额',
                field: 'SUPP_DEPT'
            },{
                title: '实际退款金额',
                field: 'REALITY_SUPP_DEPT'
            },{
                title: '欠票',
                field: 'AGENT_OWE_TICKET'
            },{
                title: '补缴欠票',
                field: 'SUPP_TICKET'
            },{
                title: '减免金额',
                field: 'SUBTRACT_AMT'
            },{
                title: '迁移平台',
                field: 'MIGRATION_PLATFORM',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "1":
                            return '瑞银信自营平台';
                    }
                }
            },{
                title: '是否上传解除合同',
                field: 'CONTRACT_STATUS',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '是';
                        case 0:
                            return '否';
                    }
                }
            },{
                title: '是否可以申请退款',
                field: 'APP_REFUND',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '是';
                        case 0:
                            return '否';
                    }
                }
            },{
                title: '是否已退款',
                field: 'REFUND_AMT_STATUS',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '是';
                        case 0:
                            return '否';
                    }
                }
            },{
                title: '申请退款期限',
                field: 'REFUND_AMT_DEADLINE'
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
                title: '申请备注',
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
            },{
                field: 'action',
                title: '操作',
                width: 300,
                formatter: function (value, row, index) {
                    var str = '';
                        str += $.formatString('<a href="javascript:void(0)" class="quit-easyui-linkbutton-query" data-options="plain:true,iconCls:\'icon-tip\'" onclick="agentQuitQuery(\'{0}\',\'{1}\');" >查看</a>', row.ID, row.CLO_REVIEW_STATUS);
                    if (row.CLO_REVIEW_STATUS==1) {
                        <shiro:hasPermission name="/agentQuit/sub_app">
                        str += $.formatString('<a href="javascript:void(0)" class="quit-easyui-linkbutton-start" data-options="plain:true,iconCls:\'icon-ok\'" onclick="quitStartActivity(\'{0}\');" >提交审批</a>', row.ID);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/agentQuit/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="quit-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="agentQuitEdit(\'{0}\',1);" >修改</a>', row.ID);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/agentQuit/del">
                        str += $.formatString('<a href="javascript:void(0)" class="quit-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-clear\'" onclick="agentQuitDel(\'{0}\');" >删除</a>', row.ID);
                        </shiro:hasPermission>
                    }
                    if (row.CLO_REVIEW_STATUS==3) {
                        <shiro:hasPermission name="/agentQuit/quitUploadContract">
                        str += $.formatString('<a href="javascript:void(0)" class="quit-easyui-linkbutton-upload" data-options="plain:true,iconCls:\'icon-ok\'" onclick="quitUploadContract(\'{0}\');" >上传解除合同</a>', row.ID);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/agentQuit/applyQuitRefund">
                        str += $.formatString('<a href="javascript:void(0)" class="quit-easyui-linkbutton-apply" data-options="plain:true,iconCls:\'icon-ok\'" onclick="applyQuitRefund(\'{0}\');" >申请退款</a>', row.ID);
                        </shiro:hasPermission>
                    }
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.quit-easyui-linkbutton-query').linkbutton({text: '查看'});
                $('.quit-easyui-linkbutton-start').linkbutton({text: '提交审批'});
                $('.quit-easyui-linkbutton-edit').linkbutton({text: '修改'});
                $('.quit-easyui-linkbutton-del').linkbutton({text: '删除'});
                $('.quit-easyui-linkbutton-upload').linkbutton({text: '上传解除合同'});
                $('.quit-easyui-linkbutton-apply').linkbutton({text: '申请退款'});
            },
            toolbar: '#agentQuitToolbar'
        });

        $("#agentAgentQuitDialog").click(function () {
            addTab({
                title: '代理商退出-申请',
                border: false,
                closable: true,
                fit: true,
                href: '/agentQuit/toAgentQuitSave'
            });
        });
    });

    function quitUploadContract(id) {
        addTab({
            title: '代理商退出-上传解除合同',
            border: false,
            closable: true,
            fit: true,
            href: '/agentQuit/toAgentQuitUpload?id=' + id
        });
    }

    function quitStartActivity(id) {
        parent.$.messager.confirm('询问', '确认提交审批？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentQuit/quitStartActivity",
                    dataType: 'json',
                    data: {busId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agentQuitList.datagrid('reload');
                    }
                });
            }
        });
    }

    function agentQuitQuery(id, cloReviewStatus) {
        addTab({
            title: '代理商退出-查看',
            border: false,
            closable: true,
            fit: true,
            href: '/agentQuit/toAgentQuitQuery?id=' + id + "&cloReviewStatus=" + cloReviewStatus
        });
    }

    function agentQuitEdit(id,index) {
        addTab({
            title: '代理商退出-修改',
            border: false,
            closable: true,
            fit: true,
            href: '/agentQuit/toAgentQuitEdit?busId='+id+"&index="+index
        });
    }


    function agentQuitDel(id) {
        parent.$.messager.confirm('询问', '确认要删除？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentQuit/agentQuitDelete",
                    dataType: 'json',
                    data: {busId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agentQuitList.datagrid('reload');
                    }
                });
            }
        });
    }


    function applyQuitRefund(id) {
        parent.$.messager.confirm('询问', '确认要申请退款？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/quitRefund/applyQuitRefund",
                    dataType: 'json',
                    data: {quitId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agentQuitList.datagrid('reload');
                    }
                });
            }
        });
    }

    function searchAgentQuit() {
        agentQuitList.datagrid('load', $.serializeObject($('#searchAgentQuitForm')));
    }

    function cleanAgentQuit() {
        $('#searchAgentQuitForm input').val('');
        agentQuitList.datagrid('load', $.serializeObject($('#searchAgentQuitForm')));
    }

//    function showAgentCityQuitDialog(options) {
//        parent.$.modalDialog({
//            title: '代理商选择',
//            width: 800,
//            height: 500,
//            href: '/abusinfo/agentMainSelectDialogView'
//        });
//        parent.$.modalDialog.handler.par_callBack_options = options;
//    }

    function showAgentCityQuit(item, data) {
        if (item) {
//            info(item.agName);
//            info(item.id);
            addTab({
                title: '代理商退出-申请',
                border: false,
                closable: true,
                fit: true,
                href: '/agentQuit/toAgentQuitSave?agentId='+item.id
            });
        }
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'退出列表'" style="width:100%;overflow: hidden; ">
        <table id="agentQuitList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchAgentQuitForm">
            <table>
                <tr>
                    <th>申请编号:</th>
                    <td>
                        <input name="id" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/agentQuit/dataRole">
                            <input name="dataRole" type="hidden" value="all">
                        </shiro:hasPermission>
                    </td>
                    <c:if test="${empty agentId}">
                        <th>代理商唯一编码:</th>
                        <td><input name="agentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称:</th>
                        <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                    <th>审批状态:</th>
                    <td>
                        <select class="easyui-combobox" name="cloReviewStatus" style="width:160px;height:21px">
                            <option value=""></option>
                            <c:forEach var="agStatus" items="${agStatusList}">
                                <option value="${agStatus.dItemvalue}">${agStatus.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgentQuit();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentQuit();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="agentQuitToolbar">
    <shiro:hasPermission name="/agentQuit/approvalAgentApply">
        <a id="agentAgentQuitDialog" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">代理商申请</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="/agentQuit/approvalCityApply">
        <a id="agentCityQuitDialog" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" onclick="agentInfoSelectDialogViewTierPass({data:this,callBack:showAgentCityQuit})">省区申请</a>
    </shiro:hasPermission>
</div>