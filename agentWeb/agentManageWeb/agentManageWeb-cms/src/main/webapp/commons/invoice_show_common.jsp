<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="发票信息"  data-options="iconCls:'fi-wrench'">
    <table class="grid">
        <c:forEach items="${invoiceList}" var="invoice">
        <tr class="invoiceClass">
            <td>开票公司：</td>
            <td>${invoice.invoiceCompany}</td>
            <td>开票项目：</td>
            <td>${invoice.invoiceProject}</td>
            <td>金额：</td>
            <td>${invoice.invoiceAmt}</td>
            <td>发票号：</td>
            <td>${invoice.invoiceNum}</td>
            <td>发票代码：</td>
            <td>${invoice.invoiceCode}</td>
        </tr>
        <tr class="invoiceClass">
            <td>快递单号：</td>
            <td>${invoice.expressNum}</td>
            <td>快递公司：</td>
            <td>${invoice.expressComp}</td>
            <td>寄出时间：</td>
            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${invoice.sendTime}" /></td>
        </tr>
        <tr class="invoiceClass">
            <c:if test="${!empty invoice.attachments}">
                <td>打款凭证：</td>
                <c:forEach items="${invoice.attachments}" var="attachment">
                    <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                    <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                </c:forEach>
            </c:if>
        </tr>
        </c:forEach>
    </table>
</div>
