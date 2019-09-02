<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agnet_list_ConditionToolbar" style="display: none;">
    <form method="post" action="${path}/agentEnter/exportAgent" id="agnet_list_ConditionToolbar_searchform">
        <table>
            <tr>
                <td>代理商唯一编码:</td>
                <td>
                    <input name="agUniqNum" style="line-height:17px;border:1px solid #ccc" type="text">
                    <shiro:hasPermission name="/agent/selfAgentRecord">
                        <input name="flag" type="hidden" value="1">
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agent/agentIsZpos">
                        <input name="isZpos" type="hidden" value="true">
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agent/agentIsZposAll">
                        <input name="isZpos" type="hidden" value="all">
                    </shiro:hasPermission>
                </td>
                <td>代理商名称:</td>
                <td><input style="border:1px solid #ccc" name="agName" type="text"></td>
                <td>业务平台:</td>
                <td>
                    <select name="busPlatform" style="width:140px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${platFormList}" var="platFormListItem">
                            <option value="${platFormListItem.platformNum}">${platFormListItem.platformName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>业务平台编号:</td>
                <td><input style="border:1px solid #ccc" name="busNum" type="text"></td>
            </tr>
            <tr>
                <td>审批状态:</td>
                <td>
                    <select name="agStatus" style="width:140px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${agStatusList}" var="aagStatusItem">
                            <option value="${aagStatusItem.dItemvalue}">${aagStatusItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>审批通过时间:</td>
                <td><input style="border:1px solid #ccc" name="time" class="easyui-datebox" editable="false"></td>
                <td>对接大区:</td>
                <td>
                    <input type="text" class="easyui-validatebox" style="width:80%;" readonly="readonly"/>
                    <input name="agDocDistrict" type="hidden" value=""/>
                    <a href="javascript:void(0);"
                       onclick="showRegionFrame({target:this,callBack:returnSelectForSearchBaseRegion},'/region/departmentTree',false)">选择</a>
                </td>


                <td>报备状态:</td>
                <td>
                    <select name="reportStatus" style="width:100px;height:21px" >
                        <option value="">--请选择--</option>
                        <c:forEach items="${reportStatusList}" var="reportStatusItem">
                            <option value="${reportStatusItem.dItemvalue}">${reportStatusItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchagnet_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentListSearchForm();">清空</a>
                </td>
                <td>
                    <shiro:hasPermission name="/agent/selfagent">
                        <button type="submit" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true,">导出</button>
                    </shiro:hasPermission>
                </td>
            </tr>
        </table>
    </form>
    <shiro:hasPermission name="/agentEnter/agentForm">
        <a id="angetEnterInFormDialog" href="javascript:void(0);"
           class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">代理商入网</a>
    </shiro:hasPermission>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agnet_list_ConditionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var agnet_list_ConditionDataGrid;
    $(function () {
        //代理商表格
        agnet_list_ConditionDataGrid = $('#agnet_list_ConditionDataGrid').datagrid({
            url: '${path}/agentEnter/agentManageList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'ID',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '代理商ID',
                field: 'ID',
                hidden: 'true'
            }, {
                title: '代理商唯一编码',
                field: 'AG_UNIQ_NUM'
            }, {
                title: '代理商名称',
                field: 'AG_NAME'
            }, {
                title: '负责人',
                field: 'AG_HEAD',
                formatter: function (value, row, index) {
                    var phone;
                    if(null!=row.AG_HEAD &&''!=row.AG_HEAD){
                        if (row.AG_HEAD.length >= 2) {
                            phone = row.AG_HEAD.substr(0, 1) + '**';
                        }
                    }
                    return phone;
                }
            }, {
                title: '负责人联系电话',
                field: 'AG_HEAD_MOBILE',
                formatter: function (value, row, index) {
                    var phone;
                    if (null != row.AG_HEAD_MOBILE && '' != row.AG_HEAD_MOBILE) {
                        if (row.AG_HEAD_MOBILE.length > 7) {
                            phone = row.AG_HEAD_MOBILE.substr(0, 3) + '****' + row.AG_HEAD_MOBILE.substr(7);
                        } else if (row.AG_HEAD_MOBILE.length <= 7) {
                            var mobile = value.substring(row.AG_HEAD_MOBILE.length - 4);
                            var flag = "";
                            for (var i = 0; i < row.AG_HEAD_MOBILE.length - 4; i++) {
                                flag = "*" + flag;
                            }
                            phone = flag + mobile;
                        }
                    }
                    else if (null == row.AG_HEAD_MOBILE || '' == row.AG_HEAD_MOBILE) {
                        phone = "无";
                    }
                    return phone;
                }
            }, {
                title: '注册地址',
                field: 'AG_REG_ADD',
                formatter: function (value, row, index) {
                    var phone;
                    if(null!=row.AG_REG_ADD &&''!=row.AG_REG_ADD){
                        if (row.AG_REG_ADD.length >= 2) {
                            phone = row.AG_REG_ADD.substr(0, 5) + '**';
                        }else{
                            phone = row.AG_REG_ADD
                        }
                    }
                    return phone;
                }
            }, {
                title : '入网状态',
                field : 'C_INCOM_STATUS',
                sortable : true,
                formatter : function(value, row, index) {
                    return db_options.agentInStatus_map[value]
                }
            }, {
                title: '审批状态',
                field: 'AG_STATUS',
                formatter: function (value, row, index) {
                    return db_options.agStatuss_map[value]
                }
            }, {
                title: '备注',
                field: 'AG_REMARK',
                hidden: 'true'
            }, {
                title: '创建时间',
                field: 'C_TIME'
            }, {
                title: '审批通过时间',
                field: 'C_INCOM_TIME'
            }, {
                title: '对接大区',
                field: 'AG_DOC_DISTRICT'
            }, {
                title: '对接省区',
                field: 'AG_DOC_PRO'
            }, {
                title: '代理商冻结解冻状态',
                field: 'FREESTATUS',
//                hidden: true,
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '冻结';
                        case 1:
                            return '正常';
                        default:
                            return '正常';
                    }
                }
            }, {
                    title: '报备状态',
                    field: 'REPORT_STATUS',
                    formatter: function (value, row, index) {
                        return db_options.reportStatus_map[value]
                    }
                }, {
                title: '报备时间',
                field: 'REPORT_TIME'
            }, {
                field: 'action',
                title: '操作',
                width: 500,
                formatter: function (value, row, index) {
                    var str = '';

                    <shiro:hasPermission name="/agentEnter/agentQuery">
                        str += $.formatString('<a href="javascript:void(0)" class="agentinlist-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="agentQuery(\'{0}\',\'{1}\');" >查看</a>', row.ID, row.AG_STATUS);
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/agActivity/startAg">
                        if (row.AG_STATUS != 'Approved' && row.AG_STATUS != 'Approving' && row.AG_STATUS != 'Refuse')
                            str += $.formatString('<a href="javascript:void(0)" class="agentinlist-sp-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="enterIn(\'{0}\');" >提交审批</a>', row.ID);
                    </shiro:hasPermission>

                    if (row.AG_STATUS == 'Approved' && row.C_INCOM_STATUS=='1') {
//                        str += $.formatString('<a href="javascript:void(0)" class="agentinlist-xq-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="a(\'{0}\');" >新签业务</a>', row.id);
                        <%--<shiro:hasPermission name="/agent/baseinfoappy">--%>
                            <%--str += $.formatString('<a href="javascript:void(0)" class="agentinlist-sjxgsq-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="dataChangeAppy(\'{0}\',\'agent\');" >申请修改</a>', row.ID);--%>
                        <%--</shiro:hasPermission>--%>
                        <shiro:hasPermission name="/agent/colinfoappy">
                            str += $.formatString('<a href="javascript:void(0)" class="agentinlist-zhsq-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="dataChangeAppy(\'{0}\',\'agentColInfo\');" >申请收款账户修改</a>', row.ID);
                        </shiro:hasPermission>
                    }

                    <shiro:hasPermission name="/agentEnter/editinfo">
                        if (row.AG_STATUS == 'Create' )
                            str += $.formatString('<a href="javascript:void(0)" class="agentinlist-up-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="agentEdit(\'{0}\');" >修改</a>', row.ID);
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/agentEnter/toEditCompany/agentEnter/editCompany">
                        if (row.AG_STATUS == 'Approved') {
                            str += $.formatString('<a href="javascript:void(0)" class="company-easyui-linkbutton-editDkgs" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editCompany(\'{0}\');" >修改打款公司</a>', row.ID);
                        }
                    </shiro:hasPermission>

                    <%--<shiro:hasPermission name="/agentEnter/queryMergeBus">--%>
                        if (row.AG_STATUS == 'Approved') {
                            str += $.formatString('<a href="javascript:void(0)" class="agentBus-look-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="agentBusQuery(\'{0}\');" >查看合并业务</a>', row.ID);
                        }
                    <%--</shiro:hasPermission>--%>

                    <shiro:hasPermission name="/agentEnter/agentUnfreeze">
                        if(row.FREESTATUS == 0) {
                            str += $.formatString('<a href="javascript:void(0)" class="agentinlist-unfreeze-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'icon-ok\'" onclick="agentUnfreeze(\'{0}\');" >解冻</a>', row.ID);
                        }
                    </shiro:hasPermission>

                   <shiro:hasPermission name="/agentEnter/toReportEdit">
                    str += $.formatString('<a href="javascript:void(0)" class="agentinlist-report-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="reportEdit(\'{0}\');" >修改报备</a>', row.ID);
                    </shiro:hasPermission>
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.agentinlist-look-easyui-linkbutton-edit').linkbutton({text: '查看'});
                $('.agentinlist-sp-easyui-linkbutton-edit').linkbutton({text: '提交审批'});
//                    $('.agentinlist-xq-easyui-linkbutton-edit').linkbutton({text:'新签业务'});
                $('.agentinlist-up-easyui-linkbutton-edit').linkbutton({text: '修改'});
//                $('.agentinlist-sjxgsq-easyui-linkbutton-edit').linkbutton({text: '申请修改'});
                $('.agentinlist-zhsq-easyui-linkbutton-edit').linkbutton({text: '申请收款账户修改'});
                $('.agentBus-look-easyui-linkbutton-query').linkbutton({text: '查看合并业务'});
                $('.agentinlist-unfreeze-easyui-linkbutton-edit').linkbutton({text: '解冻'});
                $('.company-easyui-linkbutton-editDkgs').linkbutton({text: '修改打款公司'});
                $('.agentinlist-report-easyui-linkbutton-edit').linkbutton({text: '修改报备'});
            },
            onDblClickRow: function (dataIndex, rowData) {
            },
            toolbar: '#agnet_list_ConditionToolbar'
        });

        //代理商入网form
        $("#angetEnterInFormDialog").click(function () {
            addTab({
                title: '代理商操作-新签代理商',
                border: false,
                closable: true,
                fit: true,
                href: '/agentEnter/agentForm'
            });
        });
    });

    /**
     * 搜索事件
     */
    function searchagnet_list() {
        agnet_list_ConditionDataGrid.datagrid('load', $.serializeObject($('#agnet_list_ConditionToolbar_searchform')));
    }

    function editCompany(id) {
        parent.$.modalDialog({
            title : '修改打款公司',
            width : 350,
            height : 220,
            maximizable:true,
            href : '${path }/agentEnter/toEditCompany?id='+id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = agnet_list_ConditionDataGrid;
                    var gr = parent.$.modalDialog.handler.find('#businessPlatCompany');
                    gr.submit();
                }
            } ]
        });
    }

    function enterIn(id) {
        parent.$.messager.confirm('询问', '确认要提交审批？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agActivity/startAg",
                    dataType: 'json',
                    data: {agentId: id},
                    success: function (msg) {
                        info(msg.resInfo);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agnet_list_ConditionDataGrid.datagrid('reload');
                    }
                });
            }
        });
    }

    function agentQuery(id, agStatus) {
        addTab({
            title: '代理商操作-查看' + id,
            border: false,
            closable: true,
            fit: true,
            href: '/agentEnter/agentQuery?id=' + id + "&agStatus=" + agStatus +"<shiro:hasPermission name="/agent/agentIsZpos">&isZpos=true</shiro:hasPermission><shiro:hasPermission name="/agent/agentIsZposAll">&isZpos=all</shiro:hasPermission>"
        });
    }

    function agentEdit(id) {
        addTab({
            title: '代理商信息修改',
            border: false,
            closable: true,
            fit: true,
            href: '/agentEnter/agentByid?id=' + id
        });
    }

    //地区选择
    function returnSelectForSearchBaseRegion(data, options) {
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    function cleanAgentListSearchForm() {
        $('#agnet_list_ConditionToolbar_searchform input').not("input[name='flag']").not("input[name='isZpos']").val('');
        $("[name='agStatus']").val('');
        $("[name='busPlatform']").val('');
        $("[name='reportStatus']").val('');
        agnet_list_ConditionDataGrid.datagrid('load', $.serializeObject($('#agnet_list_ConditionToolbar_searchform')));
    }

    //数据修改申请
    function dataChangeAppy(id, type) {
        if (type == 'agent') {
            addTab({
                title: '代理商信息修改申请' + id,
                border: false,
                closable: true,
                fit: true,
                href: '/agentUpdateApy/agentBaseUpdateApyView?id=' + id
            });
        }
        if (type == 'agentColInfo') {
            addTab({
                title: '代理商收款信息修改申请' + id,
                border: false,
                closable: true,
                fit: true,
                href: '/agentUpdateApy/agentByidForUpdateColInfoView?id=' + id
            });
        }
    }

    function agentQuitApply(id, agStatus) {
        addTab({
            title: '代理商退出申请' + id,
            border: false,
            closable: true,
            fit: true,
            href: '/agentEnter/agentQuitApply?id=' + id + "&agStatus=" + agStatus
        });
    }

    function agentBusQuery(id) {
        addTab({
            title: '代理商查看-合并业务' + id,
            border: false,
            closable: true,
            fit: true,
            href: '/agentEnter/agentBusQuery?id=' + id
        });
    }

    function agentUnfreeze(id) {
        parent.$.messager.confirm('询问', '确认要解冻此代理商？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentEnter/agentUnfreeze",
                    dataType: 'json',
                    data: {id: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agnet_list_ConditionDataGrid.datagrid('reload');
                    }
                });
            }
        });
    }





    function reportEdit(id) {
        parent.$.modalDialog({
            title : '修改报备',
            width : 350,
            height : 220,
            maximizable:true,
            href : '${path }/agentEnter/toReportEdit?id='+id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = agnet_list_ConditionDataGrid;
                    var gr = parent.$.modalDialog.handler.find('#reportEditForm');
                    gr.submit();
                }
            } ]
        });
    }

</script>
