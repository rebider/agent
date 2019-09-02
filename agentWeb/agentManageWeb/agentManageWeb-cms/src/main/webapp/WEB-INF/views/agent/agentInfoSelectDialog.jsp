<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="agnet_info_select_dialog_ConditionToolbar" style="display: none;">
    <form   id ="agnet_info_select_dialog_searchform" >
        <table>
            <tr>
                <td>代理商唯一编号:</td>
                <td><input name="agUniqNum" style="line-height:17px;border:1px solid #ccc" type="text"></td>
                <td>代理商名称:</td>
                <td><input  style="border:1px solid #ccc" name="agName" type="text"></td>
                <td>
                    <a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="search_agnet_info_select_dialog_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanagnet_info_select_dialogSearchForm();">清空</a>
                </td>
            </tr>
        </table>
    </form>

</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agnet_info_select_dialog" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var agnet_info_select_dialog;
    $(function() {
        //代理商表格
        agnet_info_select_dialog = $('#agnet_info_select_dialog').datagrid({
            url : '${path}/abusinfo/agentInfoSelectDialog',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 15,
            pageList : [ 15, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
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
            toolbar : '#agnet_info_select_dialog_ConditionToolbar'
        });

    });
    /**
     * 搜索事件
     */
    function search_agnet_info_select_dialog_list() {
        agnet_info_select_dialog.datagrid('load', $.serializeObject($('#agnet_info_select_dialog_searchform')));
    }

    /**
     * 清空搜索条件
     */
    function cleanagnet_info_select_dialogSearchForm() {
        $('#agnet_info_select_dialog_searchform input').val('');
        $("#agnet_info_select_dialog_searchform [name='agStatus']").val('');
        agnet_info_select_dialog.datagrid('load', {});
    }




</script>


<%--例子--%>
<%--<a href="javascript:void(0);" onclick="showAgentInfoSelectDialog({data:'',callBack:function(item,data){console.log(item)}})">选择代理商</a>--%>
<%--<script  type="application/javascript">--%>
<%--function returnAgentSele(data,srcData){--%>
<%--alert(data.AG_NAME);--%>
<%--}--%>
<%--</script>--%>
