<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<jsp:include page="/orderbuild/orderView?orderId=${oPaymentDetail.orderId}"></jsp:include>
<div class="easyui-panel" title="补款详情" data-options="iconCls:'fi-results'">
    <input name="id" value="${id}" type="hidden">
    <table class="grid">
        <tr>
            <td>付款方式类型</td>
            <td>${oPaymentDetail.payType}</td>
            <td>付款金额</td>
            <td>${oPaymentDetail.payAmount}元</td>
            <td>付款分期笔数</td>
            <td>${oPaymentDetail.planNum}笔</td>
        </tr>
        <tr>
            <td>计划付款时间</td>
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${oPaymentDetail.planPayTime}"/></td>
            <td>创建时间</td>
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${oPaymentDetail.cDate}"/></td>
        </tr>
        <tr>
            <td colspan="6">打款截图</td>
        </tr>
        <c:forEach items="${attachment}" var="attachment">
            <tr>
                <td>${attachment.attName}</td>
                <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true"
                       target="_blank">查看附件</a></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${attachment.cTime}"/></td>
            </tr>
        </c:forEach>

    </table>
</div>
<script>
    function get_suppDetail() {
        var data = {};
        data.supplementId = $("input[name='id']").val();
        return data;
    }

</script>