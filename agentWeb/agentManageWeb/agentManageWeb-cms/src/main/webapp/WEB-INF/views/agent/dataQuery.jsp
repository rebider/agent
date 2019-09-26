<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agent_data_ConditionToolbar" style="display: none;">
    <form  id="agentData_list_ConditionToolbar_searchform">
        <table>
            <tr>
                <td>数据类型:</td>
                <td>
                    <select name="dataType" style="width:140px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${dataChangeType}" var="dataChangeTypeItem">
                            <option value="${dataChangeTypeItem.dItemvalue}">${dataChangeTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>

                <td>申请状态:</td>
                <td>
                    <select name="appyStatus" style="width:140px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${agStatusi}" var="agStatusiItem">
                            <option value="${agStatusiItem.dItemvalue}">${agStatusiItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <th>代理商名称:</th>
                <td><input name="agName"  style="width:140px;height:21px"></td>
                <td><input type="text" class="form-control" style="display:none"></td>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                       onclick="searchagnetData_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentDataListSearchForm();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agentData_list_ConditionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var agentData_list_ConditionDataGrid;
    $(function () {
        //代理商表格
        agentData_list_ConditionDataGrid = $('#agentData_list_ConditionDataGrid').datagrid({
            url: '${path}/dataChangeReq/queryData',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: 'ID',
                field: 'ID'
            },{
                title: '代理商名称',
                field: 'AG_NAME',
                sortable: true
            }, {
                title: '数据类型',
                field: 'DATA_TYPE',
                sortable: true,
                formatter: function (value, row, index) {
                    return db_options.data_change_type_map[value];
                }
            }, {
                title: '申请状态',
                field: 'APPY_STATUS',
                sortable: true,
                formatter: function (value, row, index) {
                    return db_options.agStatusi_map[value]
                }
            }, {
                title: '创建时间',
                field: 'C_TIME',
                sortable: true
            }, {
                title: '更新时间',
                field: 'C_UPDATE',
                sortable: true
            }, {
                field: 'action',
                title: '操作',
                width: 250,
                formatter: function (value, row, index) {

                    var str = '';

                    str += $.formatString('<a href="javascript:void(0)" class="agentdata-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="agentDataQuery(\'{0}\');" >查看</a>', row.ID);
                    if(row.APPY_STATUS==1){
                        str += $.formatString('<a href="javascript:void(0)" class="agentdata-sp-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="enterData(\'{0}\',\'{1}\');" >提交审批</a>', row.ID, row.C_USER);
                        str += $.formatString('<a href="javascript:void(0)" class="agentdata-sp-easyui-linkbutton-delete" data-options="plain:true,iconCls:\'icon-clear\'" onclick="agentDataDelete(\'{0}\',\'{1}\');" >删除</a>', row.ID, row.C_USER);
                    }

                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.agentdata-look-easyui-linkbutton-edit').linkbutton({text: '查看'});
                $('.agentdata-sp-easyui-linkbutton-edit').linkbutton({text: '提交审批'});
                $('.agentdata-sp-easyui-linkbutton-delete').linkbutton({text: '删除'});
            },
            onDblClickRow: function (dataIndex, rowData) {
            },
            toolbar: '#agent_data_ConditionToolbar'
        });
    });

    /**
     * 搜索事件
     */
    function searchagnetData_list() {
        agentData_list_ConditionDataGrid.datagrid('load', $.serializeObject($('#agentData_list_ConditionToolbar_searchform')));
    }

    function agentDataQuery(ID) {
        addTab({
            title: '数据修改申请-查看' + ID,
            border: false,
            closable: true,
            fit: true,
            href: '/dataChangeReq/selectById?id=' + ID
        });
    }


    function enterData(id, userId) {
        parent.$.messager.confirm('询问', '确认提交审批吗？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/dataChangeReq/startData",
                    dataType: 'json',
                    data: {id: id, userId: userId},
                    success: function (msg) {
                        info(msg.resInfo);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agentData_list_ConditionDataGrid.datagrid('reload');
                    }
                });
            }
        });
    }


    function agentDataDelete(id, userId) {
        parent.$.messager.confirm('询问', '确认删除数据吗？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/dataChangeReq/deleteDataChange",
                    dataType: 'json',
                    data: {
                        id: id,
                        userId: userId
                    },
                    success: function(msg) {
                        info(msg.resInfo);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        agentData_list_ConditionDataGrid.datagrid('reload');
                    }
                });
            }
        });
    }


    function cleanAgentDataListSearchForm() {
        $('#agentData_list_ConditionToolbar_searchform input').val('');
        $("[name='dataType']").val('');
        $("[name='appyStatus']").val('');
        agentData_list_ConditionDataGrid.datagrid('load', {});
    }
</script>
