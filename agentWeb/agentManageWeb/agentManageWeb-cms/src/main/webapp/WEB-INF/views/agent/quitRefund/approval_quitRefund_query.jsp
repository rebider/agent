<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="查看信息">
        <%@ include file="approval_quitRefund_info.jsp" %>
        <div class="easyui-panel" title="申请退款-财务审批打款信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tr>
                    <td width="120px">实际退款金额：</td>
                    <td>${agentQuitRefund.realitySuppDept}</td>
                </tr>
                <tr>
                    <c:if test="${!empty agentQuitRefund.attachments}">
                        <td width="120px">打款凭证：</td>
                        <c:forEach items="${agentQuitRefund.attachments}" var="attachment">
                            <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                            <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                        </c:forEach>
                    </c:if>
                </tr>
            </table>
        </div>
        <%@ include file="/commons/queryAgentBase_model.jsp" %>
        <div class="easyui-panel" title="代理商退出-数据信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tr>
                    <td width="120px">申请退出平台：</td>
                    <td>
                        <c:forEach items="${quitPlatformList}" var="quitPlatform" >
                            <c:if test="${agentQuit.quitPlatform==quitPlatform.key}">
                                ${quitPlatform.value}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td width="120px">减免金额：</td>
                    <td>${agentQuit.subtractAmt}</td>
                    <td width="120px">手刷迁移平台：</td>
                    <td>
                        <c:forEach items="${migrationPlatforms}" var="migrationPlatformsItem">
                            <c:if test="${migrationPlatformsItem.dItemvalue == agentQuit.migrationPlatform}">${migrationPlatformsItem.dItemname}</c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <c:if test="${!empty agentQuit.attachments}">
                        <td width="120px">打款凭证：</td>
                        <c:forEach items="${agentQuit.attachments}" var="attachment">
                            <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                            <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                        </c:forEach>
                    </c:if>
                </tr>
                <tr>
                    <td width="120px">申请备注：</td>
                    <td colspan="9">${agentQuit.remark}</td>
                </tr>
            </table>
        </div>
        <%@ include file="/commons/queryAgentcapital_model.jsp" %>
        <%@ include file="../quit/agentQuitDebt.jsp" %>
        <%@ include file="/commons/order_cash_receivables_show.jsp"%>
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