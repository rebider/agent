<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="打款记录"  data-options="iconCls:'fi-wrench'">
<table class="grid">
    <c:if test="${not empty paylist}">
    <tr>
        <td style="text-align: center;">打款方式:</td>
        <td style="text-align: center;">打款金额:</td>
        <td style="text-align: center;">付款状态:</td>
        <td style="text-align: center;">审核状态:</td>
        <td style="text-align: center;">收款公司:</td>
        <td style="text-align: center;">打款人:</td>
        <td style="text-align: center;">打款时间:</td>
        <td style="text-align: center;">核款人:</td>
        <td style="text-align: center;">核款时间:</td>
        <td style="text-align: center;">实际到账时间:</td>
    </tr>
    </c:if>
    <c:forEach items="${paylist}" var="paylistItem">
    <tr>
        <td style="text-align: center;"><c:forEach items="${payTypeSelect}" var="payTypes"><c:if test="${paylistItem.payType==payTypes.key}">${payTypes.value}</c:if></c:forEach></td>
        <td style="text-align: center;">${paylistItem.amount}/元</td>
        <td style="text-align: center;"><agent:show type="PaymentStatus" busId="${paylistItem.payStatus}" /></td>
        <td style="text-align: center;"><agent:show type="approve" busId="${paylistItem.reviewStatus}" /></td>
        <c:if test="${paylistItem.payType=='YHHK'}">
            <td style="text-align: center;"> <c:forEach var="recCompListItem" items="${recCompList}" > <c:if test="${recCompListItem.id==paylistItem.collectCompany}"> ${recCompListItem.comName} </c:if> </c:forEach></td>
            <td style="text-align: center;">${paylistItem.payUser}</td>
            <td style="text-align: center;"> <fmt:formatDate pattern="yyyy-MM-dd" value="${paylistItem.payTime}" /></td>
            <td style="text-align: center;"><agent:show type="user" busId="${paylistItem.checkUser}" /></td>
            <td style="text-align: center;"> <fmt:formatDate pattern="yyyy-MM-dd" value="${paylistItem.checkDate}" /></td>
            <td style="text-align: center;"> <fmt:formatDate pattern="yyyy-MM-dd" value="${paylistItem.realRecTime}" /></td>
        </c:if>
    </tr>
    </c:forEach>
</table>
</div>
