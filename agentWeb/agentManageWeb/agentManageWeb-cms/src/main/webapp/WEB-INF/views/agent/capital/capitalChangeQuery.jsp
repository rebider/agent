<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="查看信息">
        <div class="easyui-panel" title="代理商信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tbody>
                <tr>
                    <td width="200px">代理商编码</td>
                    <td width="200px">代理商名称</td>
                    <td width="200px">欠票</td>
                    <td width="200px">欠款</td>
                </tr>
                <tr>
                    <td width="200px">${capitalChangeApply.agentId}</td>
                    <td width="200px">${capitalChangeApply.agentName}</td>
                    <td width="200px">${subAgentOweTicket}</td>
                    <td width="200px">${sumDebt}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <%@ include file="capitalChangeInfo.jsp" %>
        <%@ include file="capitalChangeReceipt.jsp" %>
        <%--<%@ include file="/commons/order_cash_receivables_show.jsp"%>--%>
        <div class="easyui-panel" title="打款凭证" data-options="iconCls:'fi-results'">
            <table class="grid">
                <%--<tr>--%>
                    <%--<td width="120px">打款凭证：</td>--%>
                    <%--<c:forEach items="${capitalChangeApply.attachments}" var="attachment">--%>
                        <%--<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>--%>
                        <%--<td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank">查看附件</a></td>--%>
                    <%--</c:forEach>--%>
                <%--</tr>--%>
                <tr>
                    <td width="120px">申请备注：</td>
                    <td colspan="9">${capitalChangeApply.remark}</td>
                </tr>
            </table>
        </div>
        <div class="easyui-panel" title="财务打款凭证" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tr>
                    <td width="80px">打款时间：</td>
                    <td width="180px"><fmt:formatDate value="${capitalChangeApply.remitTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td width="80px">打款人：</td>
                    <td width="180px">${capitalChangeApply.remitPerson}</td>
                    <td width="80px">打款金额：</td>
                    <td>${capitalChangeApply.remitAmt}</td>
                </tr>
                <tr>
                    <td width="120px">打款凭证：</td>
                    <c:forEach items="${capitalChangeApply.financeAttachments}" var="financeAttachment">
                        <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${financeAttachment.attName}</a></td>
                        <td><a href="<%=imgPath%>${financeAttachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank">查看附件</a></td>
                    </c:forEach>
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