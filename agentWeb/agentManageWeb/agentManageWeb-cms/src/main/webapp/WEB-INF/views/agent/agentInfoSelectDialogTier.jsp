<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agent_dialog_tier_ConditionToolbar" style="display: none;">
    <form id ="agnet_info_tier_dialog_searchform" >
        <table>
            <tr>
                <td>代理商唯一编号:</td>
                <td>
                    <input name="agUniqNum" style="line-height:17px;border:1px solid #ccc" type="text">
                    <input name="dataType" id="dataType" type="hidden" value="${dataType}">
                </td>
                <td>代理商名称:</td>
                <td><input  style="border:1px solid #ccc" name="agName" type="text"></td>
                <td>
                    <a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="search_agnet_tier_dialog_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanagnet_tier_dialogSearchForm();">清空</a>
                </td>
            </tr>
        </table>
    </form>

</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agnet_tier_select_dialog" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var agnet_tier_select_dialog;
    $(function() {
        //代理商表格
        agnet_tier_select_dialog = $('#agnet_tier_select_dialog').datagrid({
            url : '${path}/abusinfo/agentInfoSelectDialogTier',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 15,
            pageList : [ 15, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            queryParams: {
                dataType:"${dataType}"
            },
            columns : [ [{
                title : '代理商',
                field : 'agName',
                sortable : true
            } , {
                title : '代理商唯一编号',
                field : 'agUniqNum',
                sortable : true
            }  ,{
                title : '公司负责人',
                field : 'agHead',
                sortable : true
            }  , {
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
                title : '编号',
                field : 'id',
                sortable : true
            }] ],
            onLoadSuccess:function(data){
            },
            onDblClickRow:function(dataIndex,rowData){
                if(parent.$.modalDialog.handler.par_callBack_options && typeof parent.$.modalDialog.handler.par_callBack_options.callBack == 'function') {
                    parent.$.modalDialog.handler.par_callBack_options.callBack(rowData, parent.$.modalDialog.handler.par_callBack_options.data);
                }
                parent.$.modalDialog.handler.dialog('close');
            },
            toolbar : '#agent_dialog_tier_ConditionToolbar'
        });

    });
    /**
     * 搜索事件
     */
    function search_agnet_tier_dialog_list() {
        agnet_tier_select_dialog.datagrid('load', $.serializeObject($('#agnet_info_tier_dialog_searchform')));
    }

    /**
     * 清空搜索条件
     */
    function cleanagnet_tier_dialogSearchForm() {
        $('#agnet_info_tier_dialog_searchform input').not("#dataType").val('');
        agnet_tier_select_dialog.datagrid('load', $.serializeObject($('#agnet_info_tier_dialog_searchform')));
    }
</script>