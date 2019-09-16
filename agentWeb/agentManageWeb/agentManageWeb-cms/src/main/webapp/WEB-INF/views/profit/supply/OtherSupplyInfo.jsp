<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="其他补款申请信息" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td width="200px">代理商唯一码：</td>
            <td>${pCityApplicationDetail.agentId}</td>
        </tr>
        <tr>
            <td width="200px">代理商名称：</td>
            <td>${pCityApplicationDetail.agentName}</td>
        </tr>
        <tr>
            <td width="200px">上级代理商唯一码：</td>
            <td>${pCityApplicationDetail.parentAgentId}</td>
        </tr>
        <tr>
            <td width="200px">上级代理商名称：</td>
            <td>${pCityApplicationDetail.parentAgentName}</td>
        </tr>
        <tr>
            <td width="200px">补款类型：</td>
            <td>${pCityApplicationDetail.deductionRemark}</td>
        </tr>
        <tr>
            <td width="200px">补款金额：</td>
            <td>${pCityApplicationDetail.applicationAmt}</td>
        </tr>
    </table>
</div>

