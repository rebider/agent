<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="申请退款-数据信息" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td hidden>申请编号：</td>
            <td hidden>${agentQuitRefund.id}</td>
            <td>申请退出ID：</td>
            <td>${agentQuitRefund.quitId}</td>
            <td>代理商编码：</td>
            <td>${agentQuitRefund.agentId}</td>
            <td>代理商名称：</td>
            <td>${agentQuitRefund.agentName}</td>
            <td>实际退款金额：</td>
            <td>${agentQuitRefund.realitySuppDept}</td>
            <td>申请退款金额：</td>
            <td>${agentQuitRefund.refundAmt}</td>
        </tr>
        <tr>
            <td>审批通过时间：</td>
            <td><fmt:formatDate value="${agentQuitRefund.approveTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>创建时间：</td>
            <td><fmt:formatDate value="${agentQuitRefund.cTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>更新时间：</td>
            <td><fmt:formatDate value="${agentQuitRefund.uTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        </tr>
    </table>
</div>