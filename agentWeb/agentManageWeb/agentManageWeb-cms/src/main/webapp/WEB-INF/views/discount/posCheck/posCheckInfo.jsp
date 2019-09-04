<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="分润比例考核申请信息" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td width="200px">代理商唯一码：</td>
            <td>${posCheck.AGENT_ID}</td>
        </tr>
        <tr>
            <td width="200px">代理商名称：</td>
            <td>${posCheck.AGENT_NAME}</td>
        </tr>
        <tr>
            <td width="200px">业务平台编码：</td>
            <td>${posCheck.BUS_NUM}</td>
        </tr>
        <tr>
            <td width="200px">业务平台：</td>
            <td>${posCheck.PLAYFORM_CODE_NAME}</td>
        </tr>
        <tr>
            <td width="200px">分润比例类型：</td>
            <td>${posCheck.CHECK_TYPE_POS}</td>
        </tr>
        <tr>
            <td width="200px">承诺交易金额(万)：</td>
            <td>${posCheck.TOTAL_AMT}</td>
        </tr>
        <tr>
            <td width="200px">机具数量(台)：</td>
            <td>${posCheck.POS_ORDERS}</td>
        </tr>
        <tr>
            <td width="200px">分润比例：</td>
            <td>${posCheck.PROFIT_SCALE}</td>
        </tr>
        <tr>
            <td width="200px">考核周期</td>
            <td>${posCheck.CHECK_DATE_S}--${posCheck.CHECK_DATE_END}</td>
        </tr>
    </table>
</div>

