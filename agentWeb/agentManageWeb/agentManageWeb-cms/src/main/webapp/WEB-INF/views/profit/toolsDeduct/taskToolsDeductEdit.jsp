<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-panel" title="机具扣款调整申请修改" data-options="iconCls:'fi-results'">
    <form id="taskToolsDeductEditFrom" method="post">
        <table class="grid">
            <input type="hidden" name="id" value="${profitDeduction.id}">
            <input type="hidden" name="detailId" value="${profitStagingDetail.id}">
            <input type="hidden" name="stagingStatus" value="${profitDeduction.stagingStatus}">
            <tr>
                <td>扣款月份：</td>
                <td>${profitDeduction.deductionDate}</td>
            </tr>
            <tr>
                <td>代理商名称：</td>
                <td>${profitDeduction.agentName}</td>
            </tr>
            <tr>
                <td>代理商编号：</td>
                <td>${profitDeduction.agentPid}</td>
            </tr>
            <tr>
                <td>本月新增扣款金额：</td>
                <td>${profitDeduction.addDeductionAmt}</td>
            </tr>
            <tr>
                <td>本月扣款总金额：</td>
                <td><input type="hidden" name="sumDeductionAmt" value="${profitDeduction.sumDeductionAmt}">${profitDeduction.sumDeductionAmt}</td>
            </tr>
            <tr>
                <td>本月应扣款金额：</td>
                <td>${profitDeduction.mustDeductionAmt}</td>
            </tr>
            <tr>
                <td>申请调整扣款：</td>
                <td>
                    <c:choose>
                        <c:when test="${profitDeduction.stagingStatus == 5}">${profitDeduction.mustDeductionAmt}</c:when>
                        <c:otherwise>
                            <input type="text" name="mustDeductionAmt" value="${approvalDeduceAmt}" class="easyui-numberbox"  placeholder="请输入申请机具扣款金额" class="easyui-validatebox" data-options="required:true,min:0.01,precision:2">
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <c:if test="${profitDeduction.stagingStatus == 5}">
                <tr>
                    <td>扣款状态：</td>
                    <td>已扣款</td>
                </tr>
            </c:if>
            <tr>
                <td>备注：</td>
                <td>
                    <c:choose>
                        <c:when test="${profitDeduction.stagingStatus == 5}">${profitDeduction.remark}</c:when>
                        <c:otherwise>
                        <textarea style="width: 390px" id="remark"  name="remark" placeholder="请输入申请解冻原因" class="easyui-validatebox" data-options="required:true">${profitDeduction.remark}</textarea></td>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
    </form>
</div>