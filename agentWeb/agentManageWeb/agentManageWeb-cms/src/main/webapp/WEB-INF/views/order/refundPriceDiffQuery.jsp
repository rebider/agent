<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<div class="easyui-tabs">
    <div title="查看信息">
        <%@ include file="/commons/orderQueryRefundPriceDiff_model.jsp" %>
        <%--财务查看扣款--%>
        <%@ include file="/commons/orderRefundPriceDiff_financeSee_model.jsp" %>
        <%@ include file="/commons/order_cash_receivables_show.jsp"%>
        <div class="easyui-panel" title="财务处理结果" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
            <form id="refundPriceDiff_model_form">
            </form>
        </div>
        <div id="refundPriceDiff_model_templet">
            <table class="grid">
                <tbody>
                    <tr>
                        <td>收款时间：</td>
                        <td>
                            <input type="text" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${oRefundPriceDiff.gatherTime}"/>" disabled input-class="easyui-validatebox"  style="width:160px;"  value="${deductCapital.cAmount}" />
                        </td>
                        <td>收据金额：</td>
                        <td>
                            <input type="text" value="${oRefundPriceDiff.gatherAmt}" disabled input-class="easyui-validatebox"  style="width:160px;"  value="${deductCapital.cAmount}" />
                        </td>
                        <%--<td>扣款金额：</td>--%>
                        <%--<td>--%>
                            <%--<input type="text" value="${oRefundPriceDiff.deductAmt}" disabled input-class="easyui-validatebox"  style="width:160px;"  value="${deductCapital.cAmount}" />--%>
                        <%--</td>--%>
                        <%--<td>可抵扣机具欠款金额：</td>--%>
                        <%--<td>--%>
                            <%--<input type="text" value="${oRefundPriceDiff.machOweAmt}" disabled input-class="easyui-validatebox"  style="width:160px;"  value="${deductCapital.cAmount}" />--%>
                        <%--</td>--%>
                    </tr>
                    <tr>
                        <c:if test="${!empty oRefundPriceDiff.attachmentFianceList}">
                            <td>财务打款附件：</td>
                            <c:forEach items="${oRefundPriceDiff.attachmentFianceList}" var="attachment">
                                <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                                <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                            </c:forEach>
                        </c:if>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    </shiro:hasPermission>
    <c:if test="${reviewStatus=='2'}">
        <div title="审批流程图">
            <img src="/agActivity/approvalActImage?busId=${busIdImg}&busType=${busTypeImg}" />
        </div>
    </c:if>
</div>