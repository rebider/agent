<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="合并数据信息" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td hidden>申请编号：</td>
            <td hidden>${agentMerge.id}</td>
            <td>主代理商编码：</td>
            <td>${agentMerge.mainAgentId}</td>
            <td>主代理商名称：</td>
            <td>${agentMerge.mainAgentName}</td>
            <td>创建时间：</td>
            <td><fmt:formatDate value="${agentMerge.cTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>更新时间：</td>
            <td><fmt:formatDate value="${agentMerge.uTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <td>副代理商编码：</td>
            <td>${agentMerge.subAgentId}</td>
            <td>副代理商名称：</td>
            <td>${agentMerge.subAgentName}</td>
            <td>副代理商欠款：</td>
            <td>${agentMerge.subAgentDebt}</td>
            <td>副代理商欠票：</td>
            <td>${agentMerge.subAgentOweTicket}</td>
        </tr>
        <tr>
            <td>合并类型：</td>
            <td>
                <c:forEach var="mergeType" items="${mergeType}">
                    <c:if test="${mergeType.key==agentMerge.mergeType}">${mergeType.value}</c:if>
                </c:forEach>
            </td>
            <td>补缴类型：</td>
            <td>
                <c:forEach var="suppType" items="${suppType}">
                    <c:if test="${suppType.key==agentMerge.suppType}">${suppType.value}</c:if>
                </c:forEach>
            </td>
            <td>补缴代理商编号：</td>
            <td>${agentMerge.suppAgentId}</td>
            <td>补缴代理商名称：</td>
            <td>${agentMerge.suppAgentName}</td>
        </tr>
    </table>
</div>