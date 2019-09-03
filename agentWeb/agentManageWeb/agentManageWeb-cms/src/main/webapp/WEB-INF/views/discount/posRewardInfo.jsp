<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="POS奖励申请信息" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td width="200px">代理商唯一码：</td>
            <td>${posReward.agentId}</td>
        </tr>
        <tr>
            <td width="200px">代理商名称：</td>
            <td>${posReward.agentName}</td>
        </tr>
        <tr>
            <td width="200px">业务平台编码：</td>
            <td>${posReward.busNum}</td>
        </tr>
        <tr>
            <td width="200px">业务平台：</td>
            <td id="busPlatform">${posReward.busPlatform}</td>
        </tr>
        <tr>
            <td width="200px">考核周期：</td>
            <td>${posReward.appraisalCycle}</td>
        </tr>
        <tr>
            <td width="200px">考核类型：</td>
            <td>${posReward.rewardType}</td>
        </tr>
        <tr>
            <td width="200px">是否包含其他pos平台：</td>
            <td id="iscontainotherpos">${posReward.iscontainotherpos}</td>
        </tr>
        <tr>
            <td width="200px">考核方式：</td>
            <td id="assessWay">${posReward.assessWay}</td>
        </tr>
        <tr>
            <td width="200px">奖励核算周期：</td>
            <td id="accountingCycle">${posReward.accountingCycle}</td>
        </tr>
        <%--<tr>
            <td width="200px">预发周期（起始）：</td>
            <td>${posReward.totalConsMonth}</td>
        </tr>
        <tr>
            <td width="200px">预发周期（结束）：</td>
            <td>${posReward.creditConsMonth}</td>
        </tr>--%>
        <tr>
            <td width="200px">机具数量：</td>
            <td>${posReward.machineryNum}</td>
        </tr>
        <%--<tr>
            <td width="200px">考核月份：</td>
            <td>${posReward.totalEndMonth}</td>
        </tr>--%>
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
<script>
    $(function () {
        var accountingCycle=$('#accountingCycle').html();
        if (accountingCycle=='0'){
            $('#accountingCycle').html('月度核算');
        } else{
            $('#accountingCycle').html('季度核算');
        }
        var assessWay=$('#assessWay').html()
        if (assessWay=='0'){
            $('#assessWay').html('单月交易量考核');
        } else{
            $('#assessWay').html('累计交易量考核');
        }
        var iscontainotherpos=$('#iscontainotherpos').html();
        if (iscontainotherpos=='0'){
            $('#iscontainotherpos').html('不包含');
        } else{
            $('#iscontainotherpos').html('包含');
        }
        var busPlatform=$('td#busPlatform').html();
        var strTemp='';
        for(var i=0;i< db_options.ablePlatForm.length;i++){
            if (db_options.ablePlatForm[i].platformType=='POS'&& db_options.ablePlatForm[i].platformNum == busPlatform) {
                strTemp = db_options.ablePlatForm[i].platformName;
                break;
            }
        }
        $('td#busPlatform').html(strTemp);
    });
</script>

