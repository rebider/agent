<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="POS抱团奖励申请信息" data-options="iconCls:'fi-results'">

    <table class="grid">

        <c:forEach items="${AllAgent}" var="term">
            <tr>
                <td width="200px">代理商：</td>
                <td>${term}</td>
            </tr> 　
        </c:forEach>
      <%--  <tr>
            <td width="200px">代理商唯一码：</td>
            <td>${AllId}</td>
        </tr>
        <tr>
            <td width="200px">代理商名称：</td>
            <td>${AllName}</td>
        </tr>--%>
        <tr>
            <td width="200px">预发周期（起始）：</td>
            <td>${posReward.totalConsMonth}</td>
        </tr>
        <tr>
            <td width="200px">预发周期（结束）：</td>
            <td>${posReward.creditConsMonth}</td>
        </tr>
        <tr>
            <td width="200px">机具数量：</td>
            <td>${posReward.machineryNum}</td>
        </tr>
        <tr>
            <td width="200px">考核月份：</td>
            <td>${posReward.totalEndMonth}</td>
        </tr>
        <tr>
            <td width="200px">承诺交易金额（万）：</td>
            <td>${posReward.growAmt}</td>
        </tr>
        <tr>
            <td width="200px">奖励比例标准：</td>
            <td>${posReward.rewardScale}</td>
        </tr>
    </table>
</div>

