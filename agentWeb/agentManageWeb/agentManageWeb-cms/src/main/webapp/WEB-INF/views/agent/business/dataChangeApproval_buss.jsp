<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息" id="dataChangeApprovalNew">
        <%@ include file="queryAgentBase_model_buss.jsp" %>
        <%@ include file="queryAttachment_model_buss.jsp" %>
        <%@ include file="queryAgentBusi_model_sp.jsp" %>
        <c:if test="${dataType=='DC_Agent'}">
            <shiro:hasPermission name="/agActivity/dataChangeAppFinance">
                <%@ include file="/commons/approval_finance.jsp" %>
                <%@ include file="/commons/approval_finance_organ.jsp" %>
            </shiro:hasPermission>
            <shiro:hasPermission name="/agActivity/dataChangeAppBus">
                <%@ include file="/commons/approval_edit_debit.jsp" %>
                <%@ include file="/commons/queryFinance_model.jsp" %>
            </shiro:hasPermission>
        </c:if>
        <%--市场部审批顶级机构--%>
        <%@ include file="/commons/approval_market_toporg.jsp" %>
        <%@ include file="/commons/approval_opinion.jsp" %>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="submitAgentDataUpdateApproval()" >提交</a>
    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
    </shiro:hasPermission>
    <div title="审批流程图">
        <shiro:hasPermission name="/agActivity/approvalRecordImgSee">
            <img src="/agActivity/approvalImage?taskId=${taskId}" />
        </shiro:hasPermission>
    </div>
</div>
<script>
    function submitAgentDataUpdateApproval() {
        var subApprovalTable = (typeof get_subApproval_FormDataItem === "function")?get_subApproval_FormDataItem():{};
        var payCompanyTable = (typeof get_payCompanyTable_FormDataItem === "function")?get_payCompanyTable_FormDataItem():[];
        var busEditTable = (typeof get_busEditTable_FormDataItem=== "function")?get_busEditTable_FormDataItem():[];
        var appTableForm = (typeof get_addAppTable_FormDataItem=== "function")?get_addAppTable_FormDataItem():[];
        var busEditDebitForm = (typeof get_busEditTable_Form=== "function")?get_busEditTable_Form():[];
        var remitOrganTableForm = (typeof get_remitOrganTable_Form === "function")?get_remitOrganTable_Form():[];

        var busInfoVoList = [];
        if(payCompanyTable!=''){
            busInfoVoList = payCompanyTable;
        }else if(busEditTable!=''){
            busInfoVoList = busEditTable;
        }
        var debt = "";
        var oweTicket = "";
        if(appTableForm!=''){
            debt = appTableForm.debt;
            oweTicket = appTableForm.oweTicket;
        }
        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/dataChangeReq/taskApproval",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        busInfoVoList:busInfoVoList,
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        agentBusId:"${busId}",
                        debt:debt,
                        oweTicket:oweTicket,
                        editDebitList:busEditDebitForm,
                        orgTypeList:remitOrganTableForm
                    }),
                    beforeSend:function(){
                        progressLoad();
                    },
                    success: function(msg){
                        info(msg.resInfo);
                        if(msg.resCode=='1'){
                            $('#index_tabs').tabs('close',"处理任务");
                            activityDataGrid.datagrid('reload');
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }
</script>
