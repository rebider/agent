<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="退单分期" data-options="iconCls:'fi-results'">
    <table class="grid">
            <tr>
                <td>代理商编号</td>
                <td>${agentId}</td>
                <td>代理商名称</td>
                <td>${agentName}</td>
            </tr>
            <tr>
                <td>总金额</td>
                <td>${staging.sumAmt}</td>
                <td>期数</td>
                <td>${staging.stagCount}</td>
            </tr>
            <tr>
                <td>每期金额</td>
                <td>${staging.stagAmt}</td>
                <td>备注</td>
                <td>${staging.remark}</td>
            </tr>
    </table>
</div>

