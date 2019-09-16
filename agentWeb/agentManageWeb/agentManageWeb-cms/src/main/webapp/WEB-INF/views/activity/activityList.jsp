<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var activityDataGrid;
    $(function() {
        activityDataGrid = $('#activityDataGrid').datagrid({
            url : '${path }/activity/activityList.htmls',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
           // sortName : 'jobTime',
           // sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '编号',
                field : 'id',
                sortable : true
            }, {
                title : '任务名称',
                field : 'name',
                sortable : true,
                formatter : function(value, row, index) {
                    if ("ASSESS_DEDUCTION"==row.busType) {
                        if("其他扣款分期调整信息修改"==row.name){
                            return "考核扣款分期信息修改";
                        }else{
                            return "考核扣款分期";
                        }
                    }else{
                        return row.name;
                    }
                }
            } , {
                title : '业务类型',
                field : 'busType',
                sortable : true,
                formatter : function(value, row, index) {

                    if(db_options.busActRelBustype)
                        console.log(db_options.busActRelBustype[11].dItemvalue);
                        for(var i=0;i< db_options.busActRelBustype.length;i++){
                            if(db_options.busActRelBustype[i].dItemvalue==row.busType){
                                return db_options.busActRelBustype[i].dItemname;
                            }
                        }
                    return "";
                }
            }  , {
                title : '业务数据',
                field : 'busData',
                sortable : true
            }, {
                title : '代理商唯一编码',
                field : 'agentId',
                sortable : true
            }, {
                title : '代理商名称',
                field : 'agName',
                sortable : true
            },{
                title : '对接大区',
                field : 'agDocDistrictName',
                sortable : true
            },{
                title : '对接省区',
                field : 'agDocProName',
                sortable : true
            }, {
                title : '提交时间',
                field : 'submitTime',
                sortable : true
            }, {
                title : '处理时间',
                field : 'createTime',
                sortable : true
            }, {
                field : 'action',
                title : '操作',
                width : 120,
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/agActivity/taskApproval">
                    str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-ok"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="updateconditionValue(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\',\'{5}\');" >任务处理</a>', row.id,row.procInstId,row.busType,row.busId,row.busData,row.sid);
                    </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({text:'任务处理'});
            },
            toolbar: '#jbpm_activity_list_ConditionToolbar'
        });
    });


    function updateconditionValue(taskid,proIns,busType,busId,busData,sid) {
        var url  ;
        if(busType=='Agent')
           url  = $.formatString('/agActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='Business')
            url  = $.formatString('/agActivity/agentBusTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='DC_Agent')
            url  = $.formatString('/agActivity/dataChangerUpdateAprroval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='DC_Colinfo')
            url  = $.formatString('/agActivity/dataChangerUpdateAprroval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='STAGING')
            url  = $.formatString('/stagActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='OTHER_DEDUCTION')
            url  = $.formatString('/otherDeductionActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='THAW')
            url  = $.formatString('/thawActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='TOOLS')
            url  = $.formatString('/toolsActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='TOOL_SUPPLY')
            url  = $.formatString('/toolsActivity/toToolSupplyTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='PkType')
            url  = $.formatString('/supplement/queryById?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='ORDER')
            url  = $.formatString('/orderbuild/approvalOrderView?taskId={0}&proIns={1}&busType={2}&busId={3}&sid={4}',taskid,proIns,busType,busId,sid);
        else if(busType=='COMPENSATE')
            url  = $.formatString('/compensate/approvalCompensateView?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='refund')
            url  = $.formatString('/order/return/approvalView?taskId={0}&proIns={1}&busType={2}&busId={3}&sid={4}',taskid,proIns,busType,busId,sid);
        else if(busType=='POSREWARD')
            url  = $.formatString('/rewardActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='POSHUDDLEREWARD')
            url  = $.formatString('/rewardActivity/toHuddleTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='POSCHECK')
            url  = $.formatString('/checkActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='POSTAX')
            url  = $.formatString('/posTaxActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='QUIT')
            url  = $.formatString('/agentQuit/toAgentQuitApproval?taskId={0}&proIns={1}&busType={2}&busId={3}&sid={4}',taskid,proIns,busType,busId,sid);
        else if(busType=='MERGE')
            url  = $.formatString('/agentMerge/toAgentMergeApproval?taskId={0}&proIns={1}&busType={2}&busId={3}&sid={4}',taskid,proIns,busType,busId,sid);
        else if(busType=='agentTerminal')
            url  = $.formatString('/terminal/toTerminalTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='agentRelate')
            url  = $.formatString('/agentRelate/toAgentRelateTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='agentQuitRefund')
            url  = $.formatString('/quitRefund/toQuitRefundApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='capitalChange')
            url  = $.formatString('/capitalChange/toCapitalChangeApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='ASSESS_DEDUCTION')
             url  = $.formatString('/otherDeductionActivity/toTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='agentQuitRefund')
             url  = $.formatString('/quitRefund/toQuitRefundApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='capitalChange')
             url  = $.formatString('/capitalChange/toCapitalChangeApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='CityApplyDeduction')
            url  = $.formatString('/profit/application/toOtherDeductApprovalView?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='CityApplySupply')
            url  = $.formatString('/profitSupply/toOtherSupplyApprovalView?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='INVOICEAPPLY')
            url  = $.formatString('/profit/invoiceDetail/toInvoiceApprovalView?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='thawAgentByCity')
            url  = $.formatString('/agentFreeze/toThawAgentTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='thawAgentByBusiness')
            url  = $.formatString('/agentFreeze/toThawAgentTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='cardRenew')
            url  = $.formatString('/internetRenew/toInternetCardRenewApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        else if(busType=='profitTempalteApply')
            url  = $.formatString('/profit/template/toThawAgentTaskApproval?taskId={0}&proIns={1}&busType={2}&busId={3}',taskid,proIns,busType,busId);
        addTab({
            title : "处理任务",
            border : false,
            closable : true,
            fit : true,
            iconCls : 'fi-database',
            href:url
        });

    }

    function jbpm_activity_list_ConditionToolbar_search(){
        activityDataGrid.datagrid('load', $.serializeObject($('#jbpm_activity_list_ConditionToolbar')));
    }

    function jbpm_activity_list_ConditionToolbar_clean() {
        $('#jbpm_activity_list_ConditionToolbar input').val('');
        $('#jbpm_activity_list_ConditionToolbar').find("select[name='busActRelBusType']").val("");
        activityDataGrid.datagrid('load',$.serializeObject($('#jbpm_activity_list_ConditionToolbar')));
    }

</script>
<form id="jbpm_activity_list_ConditionToolbar" style="display: none;">
        <table>
            <tr>
                <td>类型:</td>
                <td>
                    <select name="busActRelBusType" style="width:140px;height:21px" id="jbpm_activity_list_ConditionToolbar_BusActRelBusType">
                        <option value="">--请选择--</option>
                        <c:forEach items="${BusActRelBusType}" var="BusActRelBusTypeItem">
                            <option value="${BusActRelBusTypeItem.key}">${BusActRelBusTypeItem.value}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>代理商唯一编码:</td>
                <td>
                    <input style="border:1px solid #ccc" name="agentId" type="text" id="jbpm_activity_list_ConditionToolbar_AgentId">
                </td>
                <td>代理商名称:</td>
                <td>
                    <input style="border:1px solid #ccc" name="agentName" type="text" id="jbpm_activity_list_ConditionToolbar_AgentName">
                </td>
                <td>提交时间:</td>
                <td>
                    <input  class="easyui-datetimebox"  name="subBegin" type="text" id="jbpm_activity_list_ConditionToolbar_sub_start">--
                    <input  class="easyui-datetimebox"  name="subEnd" type="text" id="jbpm_activity_list_ConditionToolbar_sub_end">
                </td>

                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                       onclick="jbpm_activity_list_ConditionToolbar_search()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'fi-x-circle',plain:true"
                       onclick="jbpm_activity_list_ConditionToolbar_clean();">清空</a>
                </td>
            </tr>
        </table>
</form>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="activityDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
