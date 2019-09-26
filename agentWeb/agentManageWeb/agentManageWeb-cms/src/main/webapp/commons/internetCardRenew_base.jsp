<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="申请信息"  data-options="iconCls:'fi-wrench'">
    <table class="grid">
        <tbody>
        <tr>
            <td width="15%">续费方式</td>
            <td width="35%">
                <c:forEach items="${internetRenewWayList}" var="internetRenewWay">
                    <c:if test="${internetRenewWay.key==oInternetRenew.renewWay}">${internetRenewWay.value}</c:if>
                </c:forEach>
            </td>
            <td width="15%">卡数量</td>
            <td width="35%">${oInternetRenew.renewCardCount}</td>
        </tr>
        <tr>
            <td>轧差金额</td>
            <td>${oInternetRenew.sumOffsetAmt}</td>
            <td>应补款金额</td>
            <td>${oInternetRenew.suppAmt}</td>
        </tr>
        <tr>
            <td>附件：</td>
            <td colspan="3">
            <c:if test="${!empty oInternetRenew.attachmentList}">
                <c:forEach items="${oInternetRenew.attachmentList}" var="attachment">
                    ${attachment.attName}
                    <a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a>
                </c:forEach>
            </c:if>
            </td>
        </tr>
        <tr>
            <td width="80px">申请备注</td>
            <td colspan="3">
                ${oInternetRenew.applyRemark}
            </td>
        </tr>
        </tbody>
    </table>
</div>
