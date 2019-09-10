<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="税点调整申请信息" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td width="200px">代理商唯一码：</td>
            <td>${taxAdjust.agentId}</td>
        </tr>
        <tr>
            <td width="200px">代理商名称：</td>
            <td>${taxAdjust.agentName}</td>
        </tr>
        <tr>
            <td width="200px">原税点：</td>
            <td>${taxAdjust.taxOld}</td>
        </tr>
        <tr>
            <td width="200px">申请税点：</td>
            <td>${taxAdjust.taxIng}</td>
        </tr>
    </table>
</div>

