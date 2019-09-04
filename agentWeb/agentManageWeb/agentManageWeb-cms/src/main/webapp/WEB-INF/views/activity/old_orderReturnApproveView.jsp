<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="历史订单退货信息" id="old_orderReturn_ApprovalNew">
        <%--省区大区审批界面--%>
        <c:if test="${sid=='sid-32130601-AB69-4F02-80A6-E62BDF8D5CBC'}">
            <jsp:include page="/oldorderreturn/approveDataEditView?returnId=${busId}"></jsp:include>
        </c:if>
        <c:if test="${sid=='sid-B0DE20B2-FE81-4057-B397-9517A1B9990B'}">
            <jsp:include page="/oldorderreturn/approveDataEditView?returnId=${busId}"></jsp:include>
        </c:if>
        <c:if test="${sid=='sid-2EA7796E-4176-4E89-A17C-90ED82D86A1C'}">
            <jsp:include page="/oldorderreturn/approveDataEditView?returnId=${busId}"></jsp:include>
        </c:if>


        <%--业务审批界面，填写信息，并排单--%>
        <c:if test="${sid=='sid-C911F512-9E63-44CC-9E6E-763484FA0E5B'}">
        <jsp:include page="/oldorderreturn/approveDataBusEditView?returnId=${busId}"></jsp:include>
        </c:if>

        <%--业务BOS于华审批界面--%>
        <c:if test="${sid=='sid-AAA289E7-8658-45EA-9025-B2DAA864D3D6'}">
        <jsp:include page="/oldorderreturn/approveDataBusBosView?returnId=${busId}"></jsp:include>
        </c:if>
        <c:if test="${sid=='sid-B0CA5D5D-09FC-420C-8288-88595205699B'}">
        <jsp:include page="/oldorderreturn/approveDataBusBosView?returnId=${busId}"></jsp:include>
        </c:if>
        <%--财务核实--%>
        <c:if test="${sid=='sid-ECD4C817-99C4-4F9B-92A2-748BB554D673'}">
            <jsp:include page="/oldorderreturn/approveDataBusBosView?returnId=${busId}"></jsp:include>
        </c:if>
        <%--代理商上传物流--%>
        <c:if test="${sid=='sid-F315F787-E98B-40FA-A6DC-6A962201075D'}">
        <jsp:include page="/oldorderreturn/approveDataAgentLgView?returnId=${busId}"></jsp:include>
        </c:if>
        <%--业务核实--%>
        <c:if test="${sid=='sid-E33DC3B4-6BC3-4258-982A-B6DB0E1B23B8'}">
        <jsp:include page="/oldorderreturn/approveDataBusCheckView?returnId=${busId}"></jsp:include>
        </c:if>
        <%--财务核实并打款--%>
        <c:if test="${sid=='sid-4528CEA4-998C-4854-827B-1842BBA5DB4B'}">
        <jsp:include page="/oldorderreturn/approveDataFinCheckView?returnId=${busId}"></jsp:include>
        </c:if>
        <%--审批选项--%>
        <%@ include file="/commons/approval_opinion_old_order_return.jsp" %>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="sumit_old_order_return_approve()" >提交</a>
    </div>
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
    <div title="审批流程图">
            <img src="/agActivity/approvalImage?taskId=${taskId}" />
    </div>
</div>
<script>
    var returnId_approve = "${busId}";
    $(function(){

    });
    function sumit_old_order_return_approve(){
        //审批意见 审批选项，审批任务
        //财务打款附件信息
        var getold_orderReturnAddattr_files =(typeof getold_orderReturnAddattr === "function") ?  getold_orderReturnAddattr(returnId_approve) : [];
        //下一个审批人
        var old_order_return_next = (typeof _old_order_return_next === "function") ? _old_order_return_next(returnId_approve) : "";
        //排单
        var oldOrderPlanData = (typeof getOldOrderPlanData === "function") ? getOldOrderPlanData(returnId_approve) : [];
        //抵扣信息
        var oldOrderDeductCapitalList = (typeof getOldOrderDeductCapitalList === "function") ? getOldOrderDeductCapitalList(returnId_approve) : [];
        //审批意见等信息
        var returnApprovalResult = (typeof get_old_order_return_approve_panel_FormDataItem === "function") ? get_old_order_return_approve_panel_FormDataItem() : {};
        parent.$.messager.confirm('询问', '确认完成任务？', function (b) {
            var returnTime = {};
            var attachments = [];
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/oldorderreturn/approveOldOrderReturnTask",
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        sid:'${sid}',
                        approvalOpinion: returnApprovalResult.approvalOpinion,
                        approvalResult: returnApprovalResult.approvalResult,
                        taskId: returnApprovalResult.taskId,
                        returnId:returnId_approve,
                        plans:oldOrderPlanData.length>0?JSON.stringify(oldOrderPlanData):'',
                        deductCapitalList:oldOrderDeductCapitalList,
                        dept:old_order_return_next,
                        attachments:getold_orderReturnAddattr_files
                    }),
                    beforeSend: function () {
                        progressLoad();
                    },
                    success: function (msg) {
                        info(msg.msg);
                        if (msg.status == 200) {
                                $('#index_tabs').tabs('close',"处理任务");
                                activityDataGrid.datagrid('reload');
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }
</script>
