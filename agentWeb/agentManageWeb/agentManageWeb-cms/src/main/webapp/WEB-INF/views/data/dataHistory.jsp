<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agnet_list_datahistory" style="display: none;">
    <form method="post" action="${path}/dataHistory/selectAll" id="agent_dataHistory">
        <table>
            <tr>
                <td>数据类型:</td>
                <td>
                    <select name="dataType" style="width:160px;height:21px">
                        <option value="">--请选择--</option>
                        <c:forEach items="${dataList}" var="dataList">
                            <option value="${dataList.dItemnremark}">${dataList.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>数据ID:</td>
                <td><input style="border:1px solid #ccc" name="dataId" ></td>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                       onclick="searchData_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanData();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agnet_history" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="dataWin" class="easyui-window" title="具体内容" closed="true" style="width:600px;height:200px;"
     data-options="iconCls:'icon-save',modal:true"/>
<script type="text/javascript">
    var agnet_history;
    $(function () {
        //代理商表格
        agnet_history = $('#agnet_history').datagrid({
            url: '${path}/dataHistory/selectAll',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            idField: 'id',
            pageSize: 15,
            pageList: [15, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '编号',
                field: 'id',
                sortable: true
            }, {
                title: '数据ID',
                field: 'dataId'
            }, {
                title: '数据类型',
                field: 'dataTypeName',
            }, {
                title: '数据内容',
                field: 'dataCotent',
                width: 200
            }, {
                title: '创建用户',
                field: 'cUserName'
            }, {
                title: '创建时间',
                field: 'cTime'
            },{
                field : 'action',
                title : '操作',
                width : 120,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-ok"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="showListHistory(\'{0}\',\'{1}\')" >查看</a>', row.dataId,row.dataType);
                    return str;
                }
            } ]],
            onDblClickCell: function (rowIndex, field, value) {
                if (field == 'dataCotent') {
                    $('#dataWin').html("");
                    $('#dataWin').html("详情：" + value + "<br>");
                    $('#dataWin').window('open');
                }

            },
            toolbar: '#agnet_list_datahistory'
        });
    });

    /**
     * 搜索事件
     */
    function searchData_list() {
        agnet_history.datagrid('load', $.serializeObject($('#agent_dataHistory')));
    }

    /**
     * 查看历史
     */
    function showListHistory(dataId,dataType){
        addTab({
            title : '历史查看',
            border : false,
            closable : true,
            fit : true,
            href:'dataHistory/selectHistory?dataId='+dataId+'&dataType='+dataType
        });
    }
     /**
     * 清空事件
     */
    function cleanData() {
        $('#agent_dataHistory input').val('');
        $("[name='dataType']").val('');
        agnet_history.datagrid('load', {});
    }
</script>