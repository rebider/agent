<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agent_data_ConditionToolbar" style="display: none;">
    <form  id="agentDataAll_list_ConditionToolbar_searchform" method="post">
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
                <td>审批完成时间:</td>
                <td>
                    <input class="easyui-datebox" name="finishTime" id="finishTime" style="width:150px">
                </td>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                       onclick="searchagnetData_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentDataListSearchForm();">清空</a>
                </td>
                <shiro:hasPermission name="/dataChangeReq/exportData">
                <td>
                    <a href='javascript:void(0)' class="easyui-linkbutton" data-options="iconCls:'icon-filter',plain:true" onclick="exportData()">导出</a>
                </td>
                </shiro:hasPermission>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agentDataAll_list_ConditionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var agentDataAll_list_ConditionDataGrid;
    $(function () {
        //代理商表格
        agentDataAll_list_ConditionDataGrid = $('#agentDataAll_list_ConditionDataGrid').datagrid({
            url: '${path}/dataChangeReq/queryDataAll',
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
                    if(row.APPY_STATUS==1)
                    str += $.formatString('<a href="javascript:void(0)" class="agentdata-sp-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="enterData(\'{0}\',\'{1}\');" >提交审批</a>', row.ID, row.C_USER);

                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.agentdata-look-easyui-linkbutton-edit').linkbutton({text: '查看'});
                $('.agentdata-sp-easyui-linkbutton-edit').linkbutton({text: '提交审批'});
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
        agentDataAll_list_ConditionDataGrid.datagrid('load', $.serializeObject($('#agentDataAll_list_ConditionToolbar_searchform')));
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
                        agentDataAll_list_ConditionDataGrid.datagrid('reload');
                    }
                });
            }
        });
    }


    function cleanAgentDataListSearchForm() {
        $('#agentDataAll_list_ConditionToolbar_searchform input').val('');
        $("[name='dataType']").val('');
        $("[name='appyStatus']").val('');
        agentDataAll_list_ConditionDataGrid.datagrid('load', {});
    }


    //导出数据
    function exportData() {
        var dataType = $("select[name='dataType']").val();
        if(dataType==''){
            info("请选择数据类型");
            return false;
        }
        if(dataType=='DC_Colinfo'){
            $('#agentDataAll_list_ConditionToolbar_searchform').form({
                url: '${path }/dataChangeReq/exportData',
                onSubmit: function() {
                    return $(this).form('validate');
                }
            });
            $('#agentDataAll_list_ConditionToolbar_searchform').submit();
        }else{
            info("暂无该导出类型")
        }
    }
</script>
