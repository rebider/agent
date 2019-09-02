<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">

    var agnet_select_dialog_ConditionDataGrid;

    $(function() {

        agnet_select_dialog_ConditionDataGrid = $('#agnet_select_dialog_ConditionDataGrid').datagrid({
            <c:if test="${!empty busPlatform}">
                url : '${path}/abusinfo/agentSelectDialog?busPlatform=${busPlatform}',
            </c:if>
            <c:if test="${empty busPlatform}">
                url : '${path}/abusinfo/agentSelectDialog',
            </c:if>
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [  {
                width:100,
                title : '代理商',
                field : 'AG_NAME',
                sortable : true
            } , {
                width:100,
                title : '代理商唯一编号',
                field : 'AG_UNIQ_NUM',
                sortable : true
            } ,  {
                width:80,
                title : '业务平台',
                field : 'BUS_PLATFORM',
                sortable : true,
                formatter : function(value, row, index) {
                    if(db_options.ablePlatForm)
                        for(var i=0;i< db_options.ablePlatForm.length;i++){
                            if(db_options.ablePlatForm[i].platformNum==row.BUS_PLATFORM){
                                return db_options.ablePlatForm[i].platformName;
                            }
                        }
                    return "";
                }
            } ,{
                width:80,
                title : '业务编号',
                field : 'BUS_NUM',
                sortable : true
            },{
                width:80,
                title : '业务状态',
                field : 'BUS_STATUS',
                sortable : true,
                formatter : function(value, row, index) {

                    return db_options.busStatus_map[value]
                }
            }, {
                width:80,
                title : '业务审批状态',
                field : 'CLO_REVIEW_STATUS',
                sortable : true,
                formatter : function(value, row, index) {
                    return db_options.agStatusi_map[value]
                }
            } , {
                width:80,
                title : '代理商审批状态',
                field : 'AG_STATUS',
                sortable : true,
                formatter : function(value, row, index) {
                    return db_options.agStatuss_map[value]
                }
            }  ] ],
            onLoadSuccess:function(data){

            },
            onDblClickRow:function(dataIndex,rowData){
                parent.$.modalDialog.handler.par_callBack_options.callBack(rowData,parent.$.modalDialog.handler.par_callBack_options.data);
                parent.$.modalDialog.handler.dialog('close');
            },
            toolbar : '#agnet_select_dialog_ConditionToolbar'
        });

    });
    function searchAgent() {
        agnet_select_dialog_ConditionDataGrid.datagrid('load', $.serializeObject($('#agnet_select_dialog_ConditionToolbar_searchform')));
    }

</script>
<div id="agnet_select_dialog_ConditionToolbar" style="display: none;">
    <form  method="post" action="" id ="agnet_select_dialog_ConditionToolbar_searchform">
        <table>
            <tr>
                <td>名称:</td>
                <td>
                    <input id="name" style="line-height:17px;border:1px solid #ccc" name="name">
                    <input id="clo_review_status" style="line-height:17px;border:1px solid #ccc" name="clo_review_status" type="hidden" value="3">
                </td>
                <td>业务平台:</td>
                <td>
                    <select name="busPlatform" style="width:160px;height:21px" >
                        <option value="">--请选择--</option>
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >
                            <option value="${ablePlatFormItem.platformNum}"
                                <c:if test="${!empty busPlatform}">
                                    <c:if test="${ablePlatFormItem.platformNum==busPlatform}">
                                       selected="selected"
                                    </c:if>
                                </c:if>
                            >${ablePlatFormItem.platformName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgent()">查询</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agnet_select_dialog_ConditionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>

<%--例子--%>
<%--<a href="javascript:void(0);" onclick="showAgentSelectDialog({data:'',callBack:returnAgentSele)">选择项</a>--%>
<%--<script  type="application/javascript">--%>
    <%--function returnAgentSele(data,srcData){--%>
        <%--alert(data.AG_NAME);--%>
    <%--}--%>
<%--</script>--%>