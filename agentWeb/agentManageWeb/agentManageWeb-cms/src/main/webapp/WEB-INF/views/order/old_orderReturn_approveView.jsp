<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<%--省区大区审批查看界面--%>
<div id="old_order_return" class="easyui-panel" title="退货信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <table class="grid" style="width: 100%">
        <c:forEach items="${old_order_return_info_detail}" var="old_order_return_info_detail_item">
            <tr>
                <td>起始SN:</td>
                <td>${old_order_return_info_detail_item.beginSn}</td>
                <td>结束SN:</td>
                <td>${old_order_return_info_detail_item.endSn}</td>
                <td>退货数量:</td>
                <td>${old_order_return_info_detail_item.returnCount}</td>
                <td>单价:</td>
                <td>${old_order_return_info_detail_item.proPrice}</td>
                <td>退款金额:</td>
                <td>${old_order_return_info_detail_item.returnAmt}</td>
            </tr>
        </c:forEach>
    </table>
</div>