<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="机具扣款调整申请" data-options="iconCls:'fi-results'">
    <table class="grid">
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
            <td>${profitDeduction.agentId}</td>
        </tr>
        <tr>
            <td>本月新增扣款金额：</td>
            <td>${profitDeduction.addDeductionAmt}</td>
        </tr>
        <tr>
            <td>本月扣款总金额：</td>
            <td>${profitDeduction.sumDeductionAmt}</td>
        </tr>
        <tr>
            <td>本月应扣款金额：</td>
            <td>${profitDeduction.mustDeductionAmt}</td>
        </tr>
        <tr>
            <td>申请调整扣款金额：</td>
            <td>${approvalDeduceAmt}</td>
        </tr>
        <tr>
            <td>备注：</td>
            <td>${profitDeduction.remark}</td>
        </tr>

    </table>
</div>

