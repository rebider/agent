<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息">
        <%@ include file="/commons/orderQueryRefundPriceDiff_model.jsp" %>
        <%--业务扣款--%>
        <%@ include file="/commons/orderRefundPriceDiff_bus_model.jsp" %>
        <%@ include file="/commons/order_cash_receivables_show.jsp"%>
        <shiro:hasPermission name="/order/withholdSee">
            <%--财务查看扣款--%>
            <%@ include file="/commons/orderRefundPriceDiff_financeSee_model.jsp" %>
            <c:if test="${not empty paylist}">
                <%--财务核款实际到账时间--%>
                <%@ include file="/commons/order_cash_receivables_approve.jsp"%>
            </c:if>
        </shiro:hasPermission>
        <%--财务查看扣款--%>
        <%@ include file="/commons/orderRefundPriceDiff_finance_model.jsp" %>
        <%--审批意见--%>
        <%@ include file="/commons/approval_opinion_compensate.jsp" %>
        <shiro:hasPermission name="/order/compensateEdit">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="editCompensateApproval('${agentBusId}')" >修改</a>
        </shiro:hasPermission>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="submitCompensateApproval()" >提交</a>
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
    var kcFlag = 0;
    function submitCompensateApproval() {
        var subApprovalTable = (typeof get_approval_For_compenstate === "function")?get_approval_For_compenstate():{};
        var refundPriceDiffTable = (typeof get_addRefundPriceDiffTable_FormData === "function")?get_addRefundPriceDiffTable_FormData():[];
        var oRefundPriceDiffJson = (typeof get_finance_check_Table_FormDataItem === "function")?get_finance_check_Table_FormDataItem():{};
        var oRefundPriceDiffFileJson = (typeof get_finance_check_Table_files === "function")?get_finance_check_Table_files():[];
        var oCashReceivablesVoList = (typeof getApproveCashPayListRealRecTime === "function")?getApproveCashPayListRealRecTime("${paylist_model}"):[];

        if(refundPriceDiffTable!=[]){
            var applyCompAmt = "${oRefundPriceDiff.applyCompAmt}";
            var kcSumAmt = 0;
            console.info(refundPriceDiffTable);
            for(var i=0;i<refundPriceDiffTable.length;i++){
                var cAmountStr = (refundPriceDiffTable[i].cAmount);
                kcSumAmt = (parseFloat(kcSumAmt))+(parseFloat(cAmountStr));
            }
            if(parseFloat(kcSumAmt)>parseFloat(applyCompAmt)){
                info("扣除金额不能大于申请金额");
                return false;
            }
        }

        if((typeof get_finance_check_Table_FormDataItem === "function")){
            if("${oRefundPriceDiff.applyCompType}"=='1'){
                var gatherTimeStr = oRefundPriceDiffJson.gatherTimeStr;
                var gatherAmt = oRefundPriceDiffJson.gatherAmt;
                if(gatherTimeStr=='' || gatherTimeStr==undefined){
                    info("请填写收款时间");
                    return;
                }
                if(gatherAmt=='' || gatherAmt==undefined){
                    info("请填写收款金额");
                    return;
                }
            }
        }

        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/compensate/taskApproval",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        deductCapitalList:refundPriceDiffTable,
                        oRefundPriceDiffVo:oRefundPriceDiffJson,
                        refundPriceDiffFinanceFile:oRefundPriceDiffFileJson,
                        agentBusId:"${agentBusId}",
                        oCashReceivablesVoList:oCashReceivablesVoList,
                        flag:kcFlag
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

    function editCompensateApproval(agentBusId) {
        addTab({
            title : '补差价-修改',
            border : false,
            closable : true,
            fit : true,
            href:'/compensate/toCompensateAmtEditPage?id='+agentBusId
        });
    }
</script>
