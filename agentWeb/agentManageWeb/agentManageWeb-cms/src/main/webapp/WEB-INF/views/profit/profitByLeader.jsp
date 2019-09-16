<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="profitByLeader_list_ConditionToolbar" style="display: none;">
    <form  method="post" action="" id ="profitByLeader_list_ConditionToolbar_searchform" >
        <table>
            <tr>
                <th>代理商名称：</th>
                <td>
                    <input id="agName" name="agentName" type="text" class="easyui-textbox" data-options="prompt:'代理商名称'" style="width:180px;">
                </td>
                <th>代理商编号：</th>
                <td>
                <input id="agentId" name="agentId" type="text" class="easyui-textbox" data-options="prompt:'代理商编号'" value="" style="width:180px;">
                <%-- <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentInfoSelectDialog({data:this,callBack:function(item,data){
                         if(item){
                              $($(data).parent('td').find('#agName')).textbox('setText',item.agName);
                              $($(data).parent('td').find('#agentId')).textbox('setText',item.id);
                         }
                     }})">检索代理商</a>--%>
                <td>
                    <a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchprofitByLeader_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentListSearchForm();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="profitByLeader_list_ConditionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var profitByLeader_list_ConditionDataGrid;



    $(function() {


        //代理商表格
        profitByLeader_list_ConditionDataGrid = $('#profitByLeader_list_ConditionDataGrid').datagrid({
            url : '${path}/profit/profitLList',
            rownumbers : true,
            striped : true,
            pagination : true,
            iconCls:'icon-edit',
            singleSelect : true,
            editors:$.fn.datagrid.defaults.editors,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '代理商唯一编号',
                field : 'agentId',
                sortable : true
            } ,  {
                title : '代理商名称',
                field : 'agentName',
                sortable : true
            }  ,{
                title : '分润月份',
                field : 'profitDate',
                sortable : true
            } , {
                title : 'POS分润',
                field : 'transProfitPos',
                sortable : true
            } , {
                title : '手刷分润',
                field : 'transProfitMpos',
                sortable : true
            } , {
                title : 'POS直签补差分润',
                field : 'transSupplyProfitPos',
                sortable : true
            } , {
                title : '手刷直签补差分润',
                field : 'transSupplyProfitMpos',
                sortable : true
            } ,{
                title : 'POS奖励',
                field : 'posReward',
                sortable : true
            },{
                title : '分润扣款',
                field : 'profitDeduction',
                sortable : true
            },{
                title : '分润补款',
                field : 'profitSupply',
                sortable : true
            },{
                title : '扣税',
                field : 'taxDeduction',
                sortable : true
            },{
                title : '实发分润',
                field : 'payProfit',
                sortable : true
            },{
                title : '分润状态',
                field : 'status',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "0":
                            return '正常';
                        case "1":
                            return '冻结';
                        case "2":
                            return '解冻中';
                        case "3":
                            return '解冻失败';
                        case "4":
                            return '未分润';
                        case "5":
                            return '已分润';
                        case "6":
                            return '打款失败';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 350,
                formatter : function(value, row, index) {

                    var str = '';

                    str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryProfitDetail(\'{0},{1}\');" >明细</a>', row.agentId, row.id);

                    return str;
                }
            }  ] ],
            onLoadSuccess:function(data){

            },
            onDblClickRow:function(dataIndex,rowData){
            },
            onBeforeEdit:function(index,row){
                row.editing = true;
            },
            onAfterEdit:function(index,row){
                row.editing = false;
                saveRowAction(row);
            },
            onCancelEdit:function(index,row){
                row.editing = false;
            },
            toolbar : '#profitByLeader_list_ConditionToolbar'
        });


        //代理商入网form
        $("#angetEnterInFormDialog").click(function(){
            addTab({
                title : '代理商操作-新签代理商',
                border : false,
                closable : true,
                fit : true,
                href:'/agentEnter/agentForm'
            });
        });


    });

    function queryProfitDetail(agentId) {
        addTab({
            title : '明细数据展示',
            border : false,
            closable : true,
            fit : true,
            href:'/monthProfit/detail/page?agentId='+agentId
        });
    }

    function getRowIndex(target){
        var tr = $(target).closest('tr.datagrid-row');
        return parseInt(tr.attr('datagrid-row-index'));
    }

    function editrow(index){
        profitByLeader_list_ConditionDataGrid.datagrid('beginEdit', index);
    }
    function saverow(index){
        var rows = profitByLeader_list_ConditionDataGrid.datagrid('getRows');    // get current page rows
        var row = rows[index];    // your row data
        $.messager.confirm('确定','你确定对代理商['+row.agentName+"]的机具扣款"+row.taxDeduction+"做变动么",function(r){
            if (r){
                profitByLeader_list_ConditionDataGrid.datagrid('endEdit', index);
            }
        });
    }
    function cancelrow(index){
        profitByLeader_list_ConditionDataGrid.datagrid('cancelEdit', index);
    }

    function saveRowAction(row){
        $.ajaxL({
            type: "POST",
            url: "/profit/updatePosBack",
            dataType:'json',
            data: {id:row.agentId,p:row.taxDeduction},
            success: function(msg){
                info(msg.resInfo);
                if(msg.resCode=='0'){
                    profitByLeader_list_ConditionDataGrid.datagrid('reload');
                }
            },
            complete:function (XMLHttpRequest, textStatus) {

            }
        });
    }




    /**
     * 搜索事件
     */
    function searchprofitByLeader_list() {
        profitByLeader_list_ConditionDataGrid.datagrid('load', $.serializeObject($('#profitByLeader_list_ConditionToolbar_searchform')));
    }


    function agentQuery(id,agStatus) {
        addTab({
            title : '代理商操作-查看'+id,
            border : false,
            closable : true,
            fit : true,
            href:'/agentEnter/agentQuery?id='+id+"&agStatus="+agStatus
        });
    }



    //地区选择
    function returnSelectForSearchBaseRegion(data,options){
        $(options.target).prev("input").val(data.id);
        $(options.target).prev("input").prev("input").val(data.text);
    }

    function cleanAgentListSearchForm() {
        $('#profitByLeader_list_ConditionToolbar_searchform input').val('');
        $("[name='agStatus']").val('');
        profitByLeader_list_ConditionDataGrid.datagrid('load', {});
    }

    function onprofitByLeaderClickCell(index,field,value){

    }

    function onprofitByLeaderAfterEdit(index, row, changes){

    }


</script>
