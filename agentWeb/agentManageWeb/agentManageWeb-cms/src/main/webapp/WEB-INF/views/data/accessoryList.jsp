<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="accessory_list" style="display: none;">
    <form method="post" action="${path}/accessory/selectList" id="agent_accessory">
        <table>
            <tr>
                <td>附件名称:</td>
                <td><input style="border:1px solid #ccc" name="attName" type="text"></td>
                <td>业务类型:</td>
                <td>
                    <select name="busType" style="width:160px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${accessoryList}" var="accessoryList">
                            <option value="${accessoryList.dItemnremark}">${accessoryList.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                       onclick="searchAccessory_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAccessory();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="accessory_data" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var accessory_data;
    $(function () {
        //代理商表格
        accessory_data = $('#accessory_data').datagrid({
            url: '${path}/accessory/selectList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 15,
            pageList: [15, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '编号',
                field: 'ID',
                sortable: true
            }, {
                title: '附件名称',
                field: 'ATT_NAME',
                sortable: true
            }, {
                title: '数据库路径',
                field: 'ATT_DBPATH',
                sortable: true
            }, {
                title: '业务类型',
                field: 'BUS_TYPE',
                sortable: true
            }, {
                title: '创建时间',
                field: 'C_TIME',
            }, {
                field: 'action',
                title: '操作',
                width: 80,
                formatter: function (value, row, index) {
                    var str = '';
                     str += $.formatString('<a href="<%=imgPath%>'+row.ATT_DBPATH+'"  class="acc_easyui-linkbutton" data-options="plain:true,iconCls:\'icon-print\'" target="_blank">下载附件</a>', row.ATT_DBPATH);
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.acc_easyui-linkbutton').linkbutton({text: '下载附件'});
            },
            onDblClickRow: function (dataIndex, rowData) {
            },
            toolbar: '#accessory_list'
        });
    });

    /**
     * 搜索事件
     */
    function searchAccessory_list() {
        accessory_data.datagrid('load', $.serializeObject($('#agent_accessory')));
    }

    /**
     * 清空事件
     */
    function cleanAccessory() {
        $('#agent_accessory input').val('');
        $("[name='busType']").val('');
        accessory_data.datagrid('load', {});
    }
</script>
