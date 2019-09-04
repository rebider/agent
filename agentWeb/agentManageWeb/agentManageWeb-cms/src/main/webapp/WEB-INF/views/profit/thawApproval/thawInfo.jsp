<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="代理商分润解冻" data-options="iconCls:'fi-results'">
    <table class="grid">
            <tr>
                <td>代理商编号</td>
                <td>${thaw.agentId}</td>
                <td>代理商名称</td>
                <td>${thaw.agentName}</td>
            </tr>
            <tr>
                <td>解冻月份：</td>
                <td>${thaw.profitDate}</td>
                <td>解冻分润(单位元)</td>
                <td>${thaw.payProfit}</td>
            </tr>
        <tr>
            <td>备注：</td>
            <td>
                ${thaw.remark}
            </td>
            <td>附件信息：</td>
            <td>
                <a href="<%=imgPath%>${attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" > ${attName}</a>
            </td>
        </tr>

    </table>
</div>

