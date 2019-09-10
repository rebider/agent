<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="查看信息">
        <%@ include file="approval_merge_info.jsp" %>
        <%@ include file="approval_mergeBusi_info.jsp" %>
        <%@ include file="/commons/order_cash_receivables_show.jsp"%>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tr>
                    <td >申请备注：</td>
                    <td colspan="9">${agentMerge.remark}</td>
                </tr>
                <tr>
                    <c:if test="${!empty agentMerge.attachments}">
                        <td>打款凭证：</td>
                        <c:forEach items="${agentMerge.attachments}" var="attachment">
                            <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                            <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                        </c:forEach>
                    </c:if>
                </tr>
            </table>
        </div>
    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
    </shiro:hasPermission>
    <c:if test="${cloReviewStatus=='2'}">
        <div title="审批流程图">
            <img src="/agActivity/approvalActImage?busId=${busIdImg}&busType=${busTypeImg}"/>
        </div>
    </c:if>
</div>