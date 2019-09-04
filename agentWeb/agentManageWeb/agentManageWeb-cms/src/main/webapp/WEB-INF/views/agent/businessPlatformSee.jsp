<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<div class="easyui-tabs">
    <div title="查看信息">
        <%@ include file="/commons/queryAgentBase_model.jsp" %>
        <%@ include file="/commons/queryAgentcapital_model.jsp" %>
        <%@ include file="/commons/queryAgentContractTable_model.jsp" %>
        <%@ include file="/commons/queryAgentColinfoTable_model.jsp" %>
        <%@ include file="/commons/queryAgentBusi_model.jsp" %>
        <%@ include file="/commons/queryAttachment_model.jsp" %>
    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    </shiro:hasPermission>
    <c:if test="${cloReviewStatus=='2'}">
        <div title="审批流程图">
            <img src="/agActivity/approvalActImage?busId=${busIdImg}&busType=${busTypeImg}" />
        </div>
    </c:if>
</div>