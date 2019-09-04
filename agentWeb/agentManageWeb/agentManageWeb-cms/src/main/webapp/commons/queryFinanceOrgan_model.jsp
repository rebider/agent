<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="财务-出款机构" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr>
            <td>业务平台类型</td>
            <td>出款机构</td>
        </tr>
        <tr>
            <c:forEach items="${agentBusInfos}" var="agentBusInfos">
                <td>
                    <input type="hidden" name="agentbusPlatForm" value="${agentBusInfos.busPlatform}">
                    <input type="hidden" name="agentbusid" value="${agentBusInfos.id}">
                    <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                        <c:if test="${ablePlatFormItem.platformNum==agentBusInfos.busPlatform}">${ablePlatFormItem.platformName}</c:if>
                    </c:forEach>
                </td>
                <td>
                    <agent:busInfo type="BUS_ORG" busId="${agentVo.finaceRemitOrgan}"/>
                </td>
            </c:forEach>
        </tr>
    </table>
</div>
<script>

</script>