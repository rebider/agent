<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="补差价信息"  data-options="iconCls:'fi-results'">
    <table class="grid">
        <%--<tr>--%>
            <%--<td>申请补差价类型：</td>--%>
            <%--<td>${oRefundPriceDiff.applyCompName}</td>--%>
            <%--<td>申请补差价金额：</td>--%>
            <%--<td>${oRefundPriceDiff.applyCompAmt}</td>--%>
            <%--<td>实际补差价类型：</td>--%>
            <%--<td>${oRefundPriceDiff.relCompName}</td>--%>
            <%--<td>实际补差价金额：</td>--%>
            <%--<td>${oRefundPriceDiff.relCompAmt}</td>--%>
        <%--</tr>--%>
        <tr>
            <td >申请备注：</td>
            <td colspan="9">${oRefundPriceDiff.applyRemark}</td>
        </tr>
        <tr>
            <c:if test="${!empty oRefundPriceDiff.attachmentList}">
                <td>代理商附件名称：</td>
                <c:forEach items="${oRefundPriceDiff.attachmentList}" var="attachment">
                    <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                    <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                </c:forEach>
            </c:if>
        </tr>
    </table>
</div>
<div class="easyui-panel" title="活动信息"  data-options="iconCls:'fi-results'">
    <table class="grid">
        <c:forEach items="${oRefundPriceDiff.refundPriceDiffDetailList}" var="refundPriceDiffDetail">
            <tr>
                <td style="color: red" colspan="10">变更前信息：</td>
            </tr>
            <tr>
                <td width="70px">订单编号：</td>
                <td width="100px">${refundPriceDiffDetail.orderId}</td>
                <td width="80px">代理商编号：</td>
                <td width="100px">${refundPriceDiffDetail.agentId}</td>
                <td width="80px">SN开始：</td>
                <td>${refundPriceDiffDetail.beginSn}</td>
                <td width="80px">SN结束：</td>
                <td>${refundPriceDiffDetail.endSn}</td>
            </tr>
            <tr>
                <td width="80px">商品名称：</td>
                <td width="120px">${refundPriceDiffDetail.proName}</td>
                <td>价格：</td>
                <td>${refundPriceDiffDetail.refundPriceDiffDetailMap!=null?refundPriceDiffDetail.refundPriceDiffDetailMap.SETTLEMENT_PRICE:refundPriceDiffDetail.price}</td>
                <td>数量：</td>
                <td>${refundPriceDiffDetail.changeCount}</td>
                <td >活动名称：</td>
                <td>${refundPriceDiffDetail.orderType==2?refundPriceDiffDetail.activityFront.activityName:refundPriceDiffDetail.refundPriceDiffDetailMap.ACTIVITY_NAME}</td>
            </tr>
            <tr>
                <td style="color: red" colspan="10">变更后信息：</td>
            </tr>
            <tr>
                <td>商品名称：</td>
                <td>${refundPriceDiffDetail.frontProName}</td>
                <td>价格：</td>
                <td>${refundPriceDiffDetail.price}</td>
                <td>数量：</td>
                <td>${refundPriceDiffDetail.changeCount}</td>
                <td>活动名称：</td>
                <td>${refundPriceDiffDetail.activityName}</td>
            </tr>
        </c:forEach>
    </table>
</div>