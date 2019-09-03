<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    var supplyPromptList;

     $(function() {
        supplyPromptList = $('#SupplyPromptTableList').datagrid({
            url : '/toolsDeduct/getSupplyPromptList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title: 'deductionDate',
                field: 'DEDUCTION_DATE',
                hidden: true
            } ,{
                title: '任务类型',
                field: 'taskType',
                align : 'center',
                width : 200,
                formatter: function (value, row) {
                    return "省区催款";
                }
            } , {
                title: '业务类型',
                field: 'workType',
                align : 'center',
                width : 200,
                formatter: function (value, row) {
                    return "机具欠款";
                }
            } ,{
                title : '代理商名称',
                field : 'AGENT_NAME',
                align : 'center',
                width : 200
            } , {
                title : '代理商唯一编码',
                field : 'AGENT_ID',
                align : 'center',
                width : 180
            },{
                title: '操作',
                field: 'operate',
                align : 'center',
                width : 200,
                formatter: function (value, row) {
                    var str = $.formatString('<a id="supplyButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="toSupplyPage(\'{0}\',\'{1}\');" >补款</a>', row.AGENT_ID,row.DEDUCTION_DATE);
                    return str;
                }
            } ] ],
            onLoadSuccess: function (data) {
                 $('.easyui-linkbutton-add').linkbutton();
            },
            toolbar : '#SupplyPromptToolbar'
        });
    });

    //查询
    function searchPromptList(){
        supplyPromptList.datagrid('load', $.serializeObject($('#SupplyPromptForm')));
    }

    //重置
    function clearPromptList(){
        $('#SupplyPromptForm input').val('');
        supplyPromptList.datagrid('load', {});
    }

    //跳转补款页面
    function toSupplyPage(agentId,deductionDate){
        addTab({
            title : '线下补款/上级代扣申请-'+agentId,
            border : false,
            closable : true,
            fit : true,
            href : '${path }/toolsDeduct/toRecoupAndDeduct?agentId='+agentId+'&deductionDate='+deductionDate
        });
    }
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="dataTable" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="SupplyPromptTableList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height:35px; overflow: hidden;background-color: #fff">
        <form id ="SupplyPromptForm" method="post">
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input name="agentName" id="agentName" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th>代理商唯一编码：</th>
                    <td><input name="agentId" id="agentId" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th></th>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchPromptList();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="clearPromptList();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

