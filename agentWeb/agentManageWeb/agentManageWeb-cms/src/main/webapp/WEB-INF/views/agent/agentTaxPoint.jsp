<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agentTaxPoint_list_ConditionToolbar" style="display: none;">
    <form  method="post" action="" id ="agentTaxPoint_list_ConditionToolbar_searchform" >
        <table>
            <tr>
                <td>代理商唯一编码:</td>
                <td><input name="agUniqNum" style="line-height:17px;border:1px solid #ccc" type="text"></td>
                <td>代理商名称:</td>
                <td><input  style="border:1px solid #ccc" name="agName" type="text"></td>
                <td>对接大区:</td>
                <td>
                    <input type="text"  class="easyui-validatebox"  style="width:80%;"  readonly="readonly" />
                    <input name="agDocDistrict" type="hidden" value=""/>
                    <a href="javascript:void(0);" onclick="showRegionFrame({target:this,callBack:returnSelectForSearchBaseRegion},'/region/departmentTree',false)">选择</a>
                </td>
                <td>
                    <a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchagentTaxPoint_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentListSearchForm();">清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agentTaxPoint_list_ConditionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var agentTaxPoint_list_ConditionDataGrid;



    $(function() {


        //代理商表格
        agentTaxPoint_list_ConditionDataGrid = $('#agentTaxPoint_list_ConditionDataGrid').datagrid({
            url : '${path}/agentManage/agentList',
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
                title : '代理商唯一编码',
                field : 'agUniqNum',
                sortable : true
            } , {
                title : '代理商',
                field : 'agName',
                sortable : true
            }  , {
                title : '税点',
                field : 'cloTaxPoint',
                sortable : true,
                editor:'text'
            } , {
                title : '对接省区',
                field : 'agDocProTemp',
                sortable : true
            } , {
                title : '对接大区',
                field : 'agDocDistrictTemp',
                sortable : true
            } , {
                title : '入网时间',
                field : 'cIncomTime',
                sortable : true
            } , {
                title : '入网状态',
                field : 'cIncomStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    return db_options.agentInStatus_map[value]
                }
            } ,{
                title : '代理商状态',
                field : 'agStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    return db_options.agStatuss_map[value]
                }
            },{
                title : '备注',
                field : 'agRemark',
                sortable : true
            },{
                field : 'action',
                title : '操作',
                width : 350,
                formatter : function(value, row, index) {

                    var str = '';

                    str += $.formatString('<a href="javascript:void(0)" class="agentTaxPoint_list-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="agentQuery(\'{0}\',\'{1}\');" >查看</a>', row.id,row.agStatus);

                    str += "&nbsp;&nbsp;||&nbsp;&nbsp;"+$.formatString('<a href="javascript:void(0)" class="agentTaxPoint_edit-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editrow({0});" >编辑</a>', index);

                    str += "&nbsp;&nbsp;||&nbsp;&nbsp;"+$.formatString('<a href="javascript:void(0)" class="agentTaxPoint_cancel-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="cancelrow({0});" >取消</a>', index);

                    str += "&nbsp;&nbsp;||&nbsp;&nbsp;"+$.formatString('<a href="javascript:void(0)" class="agentTaxPoint_save-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="saverow({0});" >保存</a>', index);

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
            toolbar : '#agentTaxPoint_list_ConditionToolbar'
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



    function getRowIndex(target){
        var tr = $(target).closest('tr.datagrid-row');
        return parseInt(tr.attr('datagrid-row-index'));
    }

    function editrow(index){
        agentTaxPoint_list_ConditionDataGrid.datagrid('beginEdit', index);
    }
    function saverow(index){
        var rows = agentTaxPoint_list_ConditionDataGrid.datagrid('getRows');    // get current page rows
        var row = rows[index];    // your row data
        $.messager.confirm('确定','你确定对'+row.agName+"税点"+row.cloTaxPoint+"做变动么",function(r){
            if (r){
                agentTaxPoint_list_ConditionDataGrid.datagrid('endEdit', index);
            }
        });
    }
    function cancelrow(index){
        agentTaxPoint_list_ConditionDataGrid.datagrid('cancelEdit', index);
    }

    function saveRowAction(row){
        $.ajaxL({
            type: "POST",
            url: "/agentManage/updateAgentTaxPoint",
            dataType:'json',
            data: {id:row.id,p:row.cloTaxPoint},
            success: function(msg){
                info(msg.resInfo);
                if(msg.resCode=='0'){
                    agentTaxPoint_list_ConditionDataGrid.datagrid('reload');
                }
            },
            complete:function (XMLHttpRequest, textStatus) {

            }
        });
    }




    /**
     * 搜索事件
     */
    function searchagentTaxPoint_list() {
        agentTaxPoint_list_ConditionDataGrid.datagrid('load', $.serializeObject($('#agentTaxPoint_list_ConditionToolbar_searchform')));
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
        $('#agentTaxPoint_list_ConditionToolbar_searchform input').val('');
        $("[name='agStatus']").val('');
        agentTaxPoint_list_ConditionDataGrid.datagrid('load', {});
    }

    function onAgentTaxPointClickCell(index,field,value){

    }

    function onAgentTaxPointAfterEdit(index, row, changes){

    }


</script>
