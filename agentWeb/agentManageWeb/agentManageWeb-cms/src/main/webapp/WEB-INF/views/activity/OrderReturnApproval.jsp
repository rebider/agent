<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <jsp:include page="/order/return/page/auditView?returnId=${busId}"></jsp:include>
        <%--审批意见--%>
        <%@ include file="/commons/approval_opinion_return.jsp" %>



    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
    </shiro:hasPermission>
    <div title="审批流程图">
        <shiro:hasPermission name="/agActivity/approvalRecordImgSee">
            <img src="/agActivity/approvalImage?taskId=${taskId}"/>
        </shiro:hasPermission>
    </div>
</div>
<script>

    //修改退货单
    function editReturnOrder() {
        addTab({
            title : '修改退货单',
            border : false,
            closable : true,
            fit : true,
            href:'${path}/order/return/page/orderReturnEdit?returnId=${busId}'
        });
    }


    function submitApprovalReturn() {

        var returnApprovalResult = (typeof get_subApproval_FormDataItem_return === "function") ? get_subApproval_FormDataItem_return() : [];

        <c:if test="${sid=='sid-C911F512-9E63-44CC-9E6E-763484FA0E5B'}">
            var plans = ((typeof getPaiDanInfos == "function") ? getPaiDanInfos() : []);
            if(returnApprovalResult.approvalResult=="pass" && (plans==undefined || plans.length<=0)){
                info("请先添加排单信息");
                return;
            }
        </c:if>


        parent.$.messager.confirm('询问', '确认完成任务？', function (b) {
            var returnTime = {};
            var attachments = [];
            if (b) {

                <c:if test="${sid=='sid-4528CEA4-998C-4854-827B-1842BBA5DB4B'}">
                attachments = ((typeof getReturnOrderAddattrs === "function") ? getReturnOrderAddattrs() : []);
                returnTime = ((typeof get_time_return === "function") ? get_time_return() : {});
                </c:if>
                $.ajaxL({
                    type: "POST",
                    url: "/order/return/taskApproval",
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        sid:'${sid}',
                        <c:if test="${sid=='sid-C911F512-9E63-44CC-9E6E-763484FA0E5B'}">
                        plans:JSON.stringify(plans),
                        </c:if>
                        <c:if test="${sid=='sid-4528CEA4-998C-4854-827B-1842BBA5DB4B'}">
                        attachments:attachments,
                        </c:if>
                        approvalOpinion: returnApprovalResult.approvalOpinion,
                        approvalResult: returnApprovalResult.approvalResult,
                        taskId: returnApprovalResult.taskId,
                        <c:if test="${sid=='sid-4528CEA4-998C-4854-827B-1842BBA5DB4B'}">
                        oReturnOrder:returnTime==undefined?{}:returnTime,
                        </c:if>
                        returnId:'${busId}',
                        orderAprDept:returnApprovalResult.dept
                    }),
                    beforeSend: function () {
                        progressLoad();
                    },
                    success: function (msg) {
                        info(msg.resInfo);
                        if (msg.resCode == '1') {
                            $('#index_tabs').tabs('close', "处理任务");
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


    var nindex = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
    function refreshTabView_return() {
        refreshTabIndex(nindex);
    }
</script>
