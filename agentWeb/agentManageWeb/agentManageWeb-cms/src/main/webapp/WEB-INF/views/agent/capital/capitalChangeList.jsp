<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var capitalChangeList;
    $(function () {
        capitalChangeList = $('#capitalChangeList').datagrid({
            url: '${path }/capitalChange/getCapitalChangeList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            queryParams: {
                1:1
                <shiro:hasPermission name="/capitalChange/dataRole">,dataRole:"all"</shiro:hasPermission>
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
                title: '缴纳款类型',
                field: 'CAPITAL_TYPE',
                formatter: function(value, row, index) {
                        return db_options.capitalType_map[value]
                }
            },{
                title: '缴纳款金额',
                field: 'CAPITAL_AMT'
            },{
                title: '机具欠款金额',
                field: 'MACHINES_DEPT_AMT'
            },{
                title: '操作类型',
                field: 'OPERATION_TYPE',
                formatter: function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '扣款';
                        case 2:
                            return '退款';
                    }
                }
            },{
                title: '操作金额',
                field: 'OPERATION_AMT'
            },{
                title: '实际操作金额',
                field: 'REAL_OPERATION_AMT'
            },{
                title: '手续费',
                field: 'SERVICE_CHARGE'
            },{
                title: '审核状态',
                field: 'CLO_REVIEW_STATUS',
                formatter: function(value, row, index) {
                    return db_options.agStatusi_map[value];
                }
            },{
                title: '收款账户类型',
                field: 'CLO_TYPE',
                formatter: function(value, row, index) {
                    return db_options.colInfoType_map[value]
                }
            },{
                title: '收款账户名',
                field: 'CLO_REALNAME'
            },{
                title: '收款开户行',
                field: 'CLO_BANK'
            },{
                title: '收款开户行支行',
                field: 'CLO_BANK_BRANCH'
            },{
                title: '收款账号',
                field: 'CLO_BANK_ACCOUNT'
            },{
                title: '总行联行号',
                field: 'ALL_LINE_NUM'
            },{
                title: '支行联行号',
                field: 'BRANCH_LINE_NUM'
            },{
                title: '开户行地区',
                field: 'BANK_REGION'
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
                width: 280,
                formatter: function (value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="change-easyui-linkbutton-query" data-options="plain:true,iconCls:\'icon-tip\'" onclick="capitalChangeQuery(\'{0}\',\'{1}\');" >查看</a>', row.ID, row.CLO_REVIEW_STATUS);
                    <%--if (row.CLO_REVIEW_STATUS==1) {--%>
                        <%--<shiro:hasPermission name="/capitalChange/start">--%>
                        <%--str += $.formatString('<a href="javascript:void(0)" class="change-easyui-linkbutton-start" data-options="plain:true,iconCls:\'icon-ok\'" onclick="capitalStartActivity(\'{0}\');" >提交审批</a>', row.ID);--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/capitalChange/edit">--%>
                        <%--str += $.formatString('<a href="javascript:void(0)" class="change-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="capitalChangeEdit(\'{0}\');" >修改</a>', row.ID);--%>
                        <%--</shiro:hasPermission>--%>
                        <%--<shiro:hasPermission name="/capitalChange/delete">--%>
                        <%--str += $.formatString('<a href="javascript:void(0)" class="change-easyui-linkbutton-delete" data-options="plain:true,iconCls:\'icon-clear\'" onclick="capitalChangeDelete(\'{0}\');" >删除</a>', row.ID);--%>
                        <%--</shiro:hasPermission>--%>
                    <%--}--%>
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.change-easyui-linkbutton-query').linkbutton({text: '查看'});
                $('.change-easyui-linkbutton-start').linkbutton({text: '提交审批'});
                $('.change-easyui-linkbutton-edit').linkbutton({text: '修改'});
                $('.change-easyui-linkbutton-delete').linkbutton({text: '删除'});
            },
            toolbar: '#capitalChangeToolbar'
        });

        $("#agentCapitalChangeDialog").click(function() {
            addTab({
                title: '保证金-申请',
                border: false,
                closable: true,
                fit: true,
                href: '/capitalChange/tocapitalChangeSave'
            });
        });
    });

    function capitalStartActivity(id) {
        parent.$.messager.confirm('询问', '确认提交审批？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/capitalChange/capitalStartActivity",
                    dataType: 'json',
                    data: {busId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        capitalChangeList.datagrid('reload');
                    }
                });
            }
        });
    }

    function capitalChangeQuery(id, cloReviewStatus) {
        addTab({
            title: '保证金-查看',
            border: false,
            closable: true,
            fit: true,
            href: '/capitalChange/toCapitalChangeQuery?id=' + id + "&cloReviewStatus=" + cloReviewStatus
        });
    }

//    function capitalChangeEdit(id) {
//        addTab({
//            title: '保证金-修改',
//            border: false,
//            closable: true,
//            fit: true,
//            href: '/capitalChange/toCapitalChangeEdit?busId='+id
//        });
//    }

//    function capitalChangeDelete(id) {
//        parent.$.messager.confirm('询问', '确认要删除？', function (b) {
//            if (b) {
//                $.ajaxL({
//                    type: "POST",
//                    url: "/capitalChange/capitalChangeDelete",
//                    dataType: 'json',
//                    data: {busId: id},
//                    success: function (data) {
//                        info(data.msg);
//                    },
//                    complete: function (XMLHttpRequest, textStatus) {
//                        capitalChangeList.datagrid('reload');
//                    }
//                });
//            }
//        });
//    }

    function searchCapitalChange() {
        capitalChangeList.datagrid('load', $.serializeObject($('#searchCapitalChangeForm')));
    }

    function cleanCapitalChange() {
        $('#searchCapitalChangeForm input[name!=dataRole]').val('');
        capitalChangeList.datagrid('load', $.serializeObject($('#searchCapitalChangeForm')));
    }

    function showAgentCityDialog(options) {
        parent.$.modalDialog({
            title: '代理商选择',
            width: 800,
            height: 500,
            href: '/abusinfo/agentMainSelectDialogView'
        });
        parent.$.modalDialog.handler.par_callBack_options = options;
    }

    function showAgentCity(item, data) {
        if (item) {
            addTab({
                title: '保证金-申请',
                border: false,
                closable: true,
                fit: true,
                href: '/capitalChange/toCapitalChangeSave?agentId=' + item.id
            });
        }
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'保证金列表'" style="width:100%;overflow: hidden; ">
        <table id="capitalChangeList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchCapitalChangeForm">
            <table>
                <tr>
                    <th>申请编号:</th>
                    <td>
                        <input name="id" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/capitalChange/dataRole">
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
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchCapitalChange();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanCapitalChange();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="capitalChangeToolbar">
    <shiro:hasPermission name="/capitalChange/approvalAgentApply">
        <a id="agentCapitalChangeDialog" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">代理商申请</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="/capitalChange/approvalCityApply">
        <a id="cityCapitalChangeDialog" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" onclick="showAgentCityDialog({data:this,callBack:showAgentCity})">省区申请</a>
    </shiro:hasPermission>
</div>