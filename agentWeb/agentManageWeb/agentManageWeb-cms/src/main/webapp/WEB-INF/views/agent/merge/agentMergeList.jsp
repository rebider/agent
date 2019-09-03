<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agentMergeList;
    $(function () {
        agentMergeList = $('#agentMergeList').datagrid({
            url: '${path }/agentMerge/getAgentMergeList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            queryParams: {
                1:1
                <shiro:hasPermission name="/agentMerge/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '申请编号',
                field: 'ID'
            },{
                title: '主代理商唯一编码',
                field: 'MAIN_AGENT_ID'
            },{
                title: '主代理商名称',
                field: 'MAIN_AGENT_NAME'
            },{
                title: '副代理商唯一编码',
                field: 'SUB_AGENT_ID'
            },{
                title: '副代理商名称',
                field: 'SUB_AGENT_NAME'
            },{
                title: '副代理商欠款',
                field: 'SUB_AGENT_DEBT'
            },{
                title: '副代理商欠票',
                field: 'SUB_AGENT_OWE_TICKET'
            },{
                title: '合并类型',
                field: 'MERGE_TYPE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '业务平台合并';
                        case 2:
                            return '业务平台改名称';
                    }
                }
            },{
                title: '补缴类型',
                field: 'SUPP_TYPE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '线下补款';
                        case 2:
                            return '代理商代扣';
                    }
                }
            },{
                title: '补缴代理商唯一编号',
                field: 'SUPP_AGENT_ID'
            },{
                title: '补缴代理商名称',
                field: 'SUPP_AGENT_NAME'
            },{
                title: '审核状态',
                field: 'CLO_REVIEW_STATUS',
                formatter : function(value, row, index) {
                    return db_options.agStatusi_map[value];
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
                width: 300,
                formatter: function (value, row, index) {
                    var str = '';
                        str += $.formatString('<a href="javascript:void(0)" class="merge-easyui-linkbutton-query" data-options="plain:true,iconCls:\'icon-tip\'" onclick="agentMergeQuery(\'{0}\',\'{1}\');" >查看</a>', row.ID, row.CLO_REVIEW_STATUS);
                    if (row.CLO_REVIEW_STATUS==1) {
                        <shiro:hasPermission name="/agentMerge/mergeStartActivity">
                            str += $.formatString('<a href="javascript:void(0)" class="merge-easyui-linkbutton-start" data-options="plain:true,iconCls:\'icon-ok\'" onclick="mergeStartActivity(\'{0}\');" >提交审批</a>', row.ID);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/agentMerge/agentMergeEdit">
                            str += $.formatString('<a href="javascript:void(0)" class="merge-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-edit\'" onclick="agentMergeEdit(\'{0}\');" >修改</a>', row.ID);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/agentMerge/agentMergeDel">
                            str += $.formatString('<a href="javascript:void(0)" class="merge-easyui-linkbutton-del" data-options="plain:true,iconCls:\'icon-clear\'" onclick="agentMergeDel(\'{0}\');" >删除</a>', row.ID);
                        </shiro:hasPermission>
                    }
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.merge-easyui-linkbutton-query').linkbutton({text: '查看'});
                $('.merge-easyui-linkbutton-start').linkbutton({text: '提交审批'});
                $('.merge-easyui-linkbutton-edit').linkbutton({text: '修改'});
                $('.merge-easyui-linkbutton-del').linkbutton({text: '删除'});
            },
            toolbar: '#agentMergeToolbar'
        });

        $("#agentMergeFormDialog").click(function () {
            addTab({
                title: '代理商合并-申请',
                border: false,
                closable: true,
                fit: true,
                href: '/agentMerge/toAgentMergeSave'
            });
        });
    });

    function agentMergeQuery(id, cloReviewStatus) {
        addTab({
            title: '代理商合并-查看',
            border: false,
            closable: true,
            fit: true,
            href: '/agentMerge/toAgentMergeQuery?id=' + id + "&cloReviewStatus=" + cloReviewStatus + "&isZpos=true&isZpos=all"
        });
    }

    function mergeStartActivity(id) {
        parent.$.messager.confirm('询问', '确认提交审批？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentMerge/mergeStartActivity",
                    dataType: 'json',
                    data: {busId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agentMergeList.datagrid('reload');
                    }
                });
            }
        });
    }

    function agentMergeEdit(id) {
        addTab({
            title: '代理商合并-修改',
            border: false,
            closable: true,
            fit: true,
            href: '/agentMerge/toAgentMergeEdit?busId=' + id
        });
    }

    function agentMergeDel(id) {
        parent.$.messager.confirm('询问', '确认要删除？警告：会删除合并业务！', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentMerge/agentMergeDelete",
                    dataType: 'json',
                    data: {busId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agentMergeList.datagrid('reload');
                    }
                });
            }
        });
    }

    function searchAgentMerge() {
        agentMergeList.datagrid('load', $.serializeObject($('#searchAgentMergeForm')));
    }

    function cleanAgentMerge() {
        $('#searchAgentMergeForm input').val('');
        agentMergeList.datagrid('load', $.serializeObject($('#searchAgentMergeForm')));
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'合并列表'" style="width:100%;overflow: hidden; ">
        <table id="agentMergeList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchAgentMergeForm">
            <table>
                <tr>
                    <th>申请编号:</th>
                    <td>
                        <input name="id" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/agentMerge/dataRole">
                            <input name="dataRole" type="hidden" value="all">
                        </shiro:hasPermission>
                    </td>
                    <c:if test="${empty agentId}">
                        <th>主代理商唯一编码:</th>
                        <td><input name="mainAgentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>主代理商名称:</th>
                        <td><input name="mainAgentName" style="line-height:17px;border:1px solid #ccc"></td>
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
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgentMerge();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentMerge();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="agentMergeToolbar">
    <shiro:hasPermission name="/agentMerge/approvalApplyMerge">
        <a id="agentMergeFormDialog" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">申请</a>
    </shiro:hasPermission>
</div>
