<%--市场部选择业务平台顶级机构--%>
<shiro:hasPermission name="/agActivity/approvalMarketTopOrg">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="市场部审批-顶级机构" data-options="iconCls:'fi-results'">
    <table class="grid" id="market_toporg_TableId_${agent.id}">
        <tr>
            <td>业务平台类型</td>
            <td>顶级机构</td>
        </tr>
        <tr>
            <c:forEach items="${agentBusInfos}" var="agentBusInfosItem">
                <td>
                    <input type="hidden" name="agentbusid" value="${agentBusInfosItem.id}">
                    <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                        <c:if test="${ablePlatFormItem.platformNum==agentBusInfosItem.busPlatform}">${ablePlatFormItem.platformName}</c:if>
                    </c:forEach>
                </td>
                <td>
                    <select name="market_organNum" id="market_organNum" style="width:200px;height:21px" data-options="required:true">
                        <option value="">---请选择---</option>
                        <c:forEach items="${platOrg[agentBusInfosItem.busPlatform]}" var="organListItem">
                            <option value="${organListItem.orgId}"
                                    <c:if test="${organListItem.orgId==platParentOrg[agentBusInfosItem.id]}">selected="selected"</c:if>>
                                    ${organListItem.orgName}</option>
                        </c:forEach>
                    </select>
                </td>
            </c:forEach>
        </tr>
    </table>
</div>
<script type="application/javascript">
    //获取顶级机构审批数据
    function get_market_toporg_TableId_Form() {
        var formDataJson = [];
        $('#market_toporg_TableId_${agent.id} tr').each(function(i){
            if (i >= 1) {
                var data = {};
                data.id = $(this).find("input[name='agentbusid']").val();
                data.organNum = $(this).find("select[name='market_organNum']").val();
                formDataJson.push(data);
            }
        });
        return formDataJson;
    }
</script>
</shiro:hasPermission>