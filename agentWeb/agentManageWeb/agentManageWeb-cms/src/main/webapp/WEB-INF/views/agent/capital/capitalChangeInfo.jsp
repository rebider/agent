<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="保证金" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td>代理商编码：</td>
            <td width="180px">${capitalChangeApply.agentId}</td>
            <td>代理商名称：</td>
            <td  width="180px">${capitalChangeApply.agentName}</td>
            <td>缴纳款类型：</td>
            <td>
                <c:forEach var="capitalTypeItem" items="${capitalType}">
                    <c:if test="${capitalTypeItem.dItemvalue== capitalChangeApply.capitalType}">${capitalTypeItem.dItemname}</c:if>
                </c:forEach>
            </td>
            <td>缴纳款金额：</td>
            <td>${capitalChangeApply.capitalAmt}</td>
        </tr>
        <tr>
            <td>操作类型：</td>
            <td>
                <c:forEach items="${operationType}" var="operationTypeItem">
                    <c:if test="${operationTypeItem.key==capitalChangeApply.operationType}">${operationTypeItem.value}</c:if>
                </c:forEach>
            </td>
            <td>抵扣机具欠款金额：</td>
            <td>${capitalChangeApply.machinesDeptAmt}</td>
            <td>操作金额：</td>
            <td>${capitalChangeApply.operationAmt}</td>
            <td bgcolor="red">实际操作金额：</td>
            <td bgcolor="red">${capitalChangeApply.realOperationAmt}</td>
        </tr>
        <tr>
            <td>手续费：</td>
            <td>${capitalChangeApply.serviceCharge}</td>
        </tr>
    </table>
</div>