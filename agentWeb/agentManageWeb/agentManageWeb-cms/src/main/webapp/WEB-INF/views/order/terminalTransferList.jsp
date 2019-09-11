<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var terminalTransferList;
    $(function () {
        terminalTransferList = $('#terminalTransferList').datagrid({
            url: '${path }/terminal/terminalTransferList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            queryParams: {
                1:1
                <shiro:hasPermission name="/terminalTransfer/dataRole">,dataRole:"all"</shiro:hasPermission>
            },
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '申请编号',
                field: 'ID'
            },{
                title: '申请备注',
                field: 'REMARK'
            },{
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            },{
                title: '代理商名称',
                field: 'AG_NAME'
            },{
                title: '审核状态',
                field: 'REVIEW_STATUS',
                formatter : function(value, row, index) {
                    return db_options.agStatusi_map[value]
                }
            },{
                title: '创建时间',
                field: 'C_TIME'
            }, {
                title: '更新时间',
                field: 'U_TIME'
            }, {
                title: '创建人',
                field: 'C_USER_NAME'
            }, {
                title: '更新人',
                field: 'U_USER_NAME'
            }, {
                field: 'action',
                title: '操作',
                width: 240,
                formatter: function (value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="terminal-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="terminalQuery(\'{0}\',\'{1}\');" >查看</a>', row.ID,row.REVIEW_STATUS);
                    if(row.REVIEW_STATUS==1){
                        <shiro:hasPermission name="/terminal/terminalStartActivity">
                            str += $.formatString('<a href="javascript:void(0)" class="terminal-easyui-linkbutton-start" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="terminalStartActivity(\'{0}\');" >提交审批</a>', row.ID);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/terminal/terminalEdit">
                            str += $.formatString('<a href="javascript:void(0)" class="terminal-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="terminalEdit(\'{0}\');" >修改</a>', row.ID);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/terminal/terminalDel">
                            str += $.formatString('<a href="javascript:void(0)" class="terminal-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="terminalDel(\'{0}\');" >删除</a>', row.ID);
                        </shiro:hasPermission>
                    }
                    return str;
                }
            }
            ]],
            onLoadSuccess: function (data) {
                $('.terminal-easyui-linkbutton-query').linkbutton({text: '查看'});
                $('.terminal-easyui-linkbutton-start').linkbutton({text: '提交审批'});
                $('.terminal-easyui-linkbutton-edit').linkbutton({text: '修改'});
                $('.terminal-easyui-linkbutton-del').linkbutton({text: '删除'});
            },
            toolbar: '#terminalTransferToolbar'
        });

        $("#terminalTransferFormDialog").click(function () {
            addTab({
                title: '终端划拨-申请',
                border: false,
                closable: true,
                fit: true,
                href: '/terminal/toTerminalTransferSave'
            });
        });

    });

    function searchTerminalTransfer() {
        terminalTransferList.datagrid('load', $.serializeObject($('#searchTerminalTransferForm')));
    }

    function cleanTerminalTransfer() {
        $('#searchTerminalTransferForm input').not("input[name='dataRole']").val('');
        terminalTransferList.datagrid('load', $.serializeObject($('#searchTerminalTransferForm')));
    }

    function terminalQuery(id,reviewStatus) {
        addTab({
            title: '终端划拨-查看',
            border: false,
            closable: true,
            fit: true,
            href: '/terminal/toTerminalQuery?id='+id+"&reviewStatus="+reviewStatus
        });
    }

    function terminalEdit(id) {
        addTab({
            title: '终端划拨-修改',
            border: false,
            closable: true,
            fit: true,
            href: '/terminal/toTerminalEdit?busId='+id
        });
    }

    function terminalStartActivity(id) {
        parent.$.messager.confirm('询问', '确认要提交审批？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/terminal/startActivity",
                    dataType: 'json',
                    data: {busId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        terminalTransferList.datagrid('reload');
                    }
                });
            }
        });
    }

    function terminalDel(id) {
        parent.$.messager.confirm('询问', '确认要删除？警告：会删除所有明细！', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/terminal/terminalDel",
                    dataType: 'json',
                    data: {busId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        terminalTransferList.datagrid('reload');
                    }
                });
            }
        });
    }

    function refTransferReload() {
        terminalTransferList.datagrid('reload');
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="terminalTransferList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchTerminalTransferForm">
            <table>
                <tr>
                    <th>申请编号:</th>
                    <td>
                        <input name="id" style="line-height:17px;border:1px solid #ccc">
                        <shiro:hasPermission name="/terminalTransfer/dataRole">
                            <input name="dataRole" type="hidden" value="all">
                        </shiro:hasPermission>
                    </td>
                    <c:if test="${empty agentId}">
                        <th>代理商唯一编码:</th>
                        <td><input name="agentId" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                        <th>代理商名称:</th>
                        <td><input name="agName" style="line-height:17px;border:1px solid #ccc"></td>
                    </c:if>
                    <th>审批状态:</th>
                    <td>
                        <select class="easyui-combobox" name="reviewStatus"  style="width:160px;height:21px">
                            <option value="">--全部--</option>
                            <c:forEach var="agStatus" items="${agStatusList}">
                                <option value="${agStatus.dItemvalue}">${agStatus.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchTerminalTransfer();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanTerminalTransfer();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="terminalTransferToolbar">
    <shiro:hasPermission name="/terminal/approvalApply">
        <a id="terminalTransferFormDialog" href="javascript:void(0);" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'fi-plus icon-green'">申请</a>
    </shiro:hasPermission>
</div>

