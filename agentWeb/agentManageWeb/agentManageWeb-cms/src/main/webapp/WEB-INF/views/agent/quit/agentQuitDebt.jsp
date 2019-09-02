<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="代理商欠款信息"  data-options="iconCls:'fi-results'">
    <table class="grid" style="table-layout: fixed">
        <tr>
            <td>欠票</td>
            <td>${subAgentOweTicket}</td>
            <td>补缴欠票</td>
            <td>${subAgentOweTicket}</td>
        </tr>
        <tr>
            <td>欠款</td>
            <td>${subAgentDebt}</td>
            <td>订单欠款</td>
            <td>${orderDebt}</td>
            <td>缴纳款欠款</td>
            <td>${capitalDebt}</td>
            <td>分润欠款</td>
            <td>${profitDebt}</td>
        </tr>
        <tr>
            <td>剩余保证金</td>
            <td>${capitalSumAmt}</td>
            <td>操作方式</td>
            <td>${suppMap.suppType}</td>
            <td>操作金额</td>
            <td>${suppMap.suppDept}</td>
            <td>实际退款金额</td>
            <td>${suppMap.realitySuppDept}</td>
            <td>减免金额</td>
            <td>${suppMap.subtractAmt}</td>
        </tr>
    </table>
</div>