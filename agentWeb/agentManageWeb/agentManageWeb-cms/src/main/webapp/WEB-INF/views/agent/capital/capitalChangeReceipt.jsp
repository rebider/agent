<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="收款账户" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td>收款账户类型：</td>
            <td>
                <c:forEach var="colInfoTypeItem" items="${colInfoType}">
                    <c:if test="${colInfoTypeItem.dItemvalue==capitalChangeApply.cloType}">${colInfoTypeItem.dItemname}</c:if>
                </c:forEach>
            </td>
            <td>收款账户名：</td>
            <td>${capitalChangeApply.cloRealname}</td>
            <td>收款开户总行：</td>
            <td>${capitalChangeApply.cloBank}</td>
            <td>收款开户支行：</td>
            <td>${capitalChangeApply.cloBankBranch}</td>
        </tr>
        <tr>
            <td>收款账号：</td>
            <td><desensitization:show type="name" value="${capitalChangeApply.cloBankAccount}"/></td>
            <td>总行联行号：</td>
            <td>${capitalChangeApply.allLineNum}</td>
            <td>支行联行号：</td>
            <td>${capitalChangeApply.branchLineNum}</td>
            <td>开户行地区：</td>
            <td>${capitalChangeApply.bankRegion}</td>
        </tr>
    </table>
</div>