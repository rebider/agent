<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息" id="dataChangeApprovalNew">
        <%@ include file="/commons/queryAgentBase_model_Colinfo.jsp" %>
        <%@ include file="/commons/queryAttachment_model.jsp" %>
        <%@ include file="/commons/taskAgentcapital.jsp" %>
        <%@ include file="/commons/queryAgentContractTable_model.jsp" %>
        <%@ include file="/commons/queryAgentColinfoTable_model_Colinfo.jsp" %>
        <%@ include file="/commons/queryAgentBusi_model.jsp" %>
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
        <%--&lt;%&ndash;业务审批&ndash;%&gt;--%>
        <%--<%@ include file="/commons/approval_business.jsp" %>--%>
        <%--&lt;%&ndash;财务审批&ndash;%&gt;--%>
        <%--<%@ include file="/commons/approval_account.jsp" %>--%>
        <%--审批意见--%>
        <%@ include file="/commons/approval_opinion_cancel.jsp" %>
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
<%--<div title="现有信息" id="dataChangeApprovalOld" style="display: none">
    &lt;%&ndash;<iframe src="/agentEnter/agentInfo?id=${agent.id}" frameborder="0" scrolling="no"  onload="setIframeHeight(this)"></iframe>&ndash;%&gt;
    <jsp:include page="/agentEnter/agentInfo?id=${agent.id}" flush="true"/>
</div>--%>
<script>
    //审批标红
    $(function(){

      /*  //待比对信息
        var newScanArray = $("#dataChangeApprovalNew").find("td[SCAN='TRUE']");
        //遍历待比对
        $.each(newScanArray,function(i,item){
            var model = $(item).attr("MODEL");
            var modelid = $(item).attr("MODELID");
            //检查是否存在模块
            if(modelid && modelid!=null  && modelid!=undefined  && modelid.length>0){
                var modelkey = $(item).attr("MODELKEY");
                var html = $(item).text();
                if(html.length>0){
                    html = html.trim();
                }
                var old = $("#dataChangeApprovalOld").find("td[SCAN='TRUE'][MODEL='"+model+"'][MODELID='"+modelid+"'][MODELKEY='"+modelkey+"']");
                if(old.length>0){
                    var oldHtml = $(old[0]).text();
                    $(item).after('<td colspan="1" rowspan="1"></td>');
                    if(oldHtml.length>0){
                        oldHtml = oldHtml.trim();
                    }
                    if(html!=oldHtml){
                        var nextElm = $(item).next();
                        nextElm.html('(原信息:'+oldHtml+')');
                        $(item).css("background-color","#1FE4C4");
                    }
                }else{
                    $(item).css("background-color","#FFE4C4");
                }
            }else{
                $(item).css("background-color","#FFE411");
            }
        });*/
    });

    function submitAgentDataUpdateApproval() {
        var addAgentAccountTable = (typeof get_addAgentAccountTable_FormDataItem === "function")?get_addAgentAccountTable_FormDataItem():[];
        var subApprovalTable = (typeof get_subApproval_FormDataItem === "function")?get_subApproval_FormDataItem():{};
        var payCompanyTable = (typeof get_payCompanyTable_FormDataItem === "function")?get_payCompanyTable_FormDataItem():[];
        var busEditTable = (typeof get_busEditTable_FormDataItem=== "function")?get_busEditTable_FormDataItem():[];
        var appTableForm = (typeof get_addAppTable_FormDataItem=== "function")?get_addAppTable_FormDataItem():[];
        var agentCapital = (typeof get_agentCapitalTask=== "function")?get_agentCapitalTask():[];
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
                        agentColinfoRelList:addAgentAccountTable,
                        busInfoVoList:busInfoVoList,
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        agentBusId:"${busId}",
                        debt:debt,
                        oweTicket:oweTicket,
                        capitalVoList:agentCapital,
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
