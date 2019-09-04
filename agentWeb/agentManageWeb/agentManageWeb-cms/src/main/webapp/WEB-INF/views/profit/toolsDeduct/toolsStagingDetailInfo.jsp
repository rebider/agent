<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="调整部分下月扣款明细" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td>下月扣款月份：</td>
            <td>${profitStagingDetail.deductionDate}</td>
        </tr>
        <tr>
            <td>下月应扣款金额：</td>
            <td>${profitStagingDetail.mustAmt}</td>
        </tr>
        <tr>
            <td>备注：</td>
            <td>${profitStagingDetail.remark}</td>
        </tr>
    </table>
</div>

