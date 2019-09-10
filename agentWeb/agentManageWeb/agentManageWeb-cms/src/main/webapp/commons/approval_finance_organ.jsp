<%--入网申请修改财务审批--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="财务审批-出款机构" data-options="iconCls:'fi-results'">
    <table class="grid" id="finaceTableId">
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
                    <select name="finaceRemitOrgan" id="finaceRemitOrgan" style="width:200px;height:21px" data-options="required:true">
                        <option value="">---请选择---</option>
                        <c:forEach items="${allFinceOrganList}" var="organListItem">
                            <option value="${organListItem.orgId}"
                                    <c:if test="${organListItem.orgId==agentBusInfos.finaceRemitOrgan}">selected="selected"</c:if>>
                                    ${organListItem.orgName}</option>
                        </c:forEach>
                    </select>
                </td>
            </c:forEach>
            <td>
                <a href="javascript:void(0);" onclick="selectColinfos('${agent.id}')">查看</a>
            </td>
        </tr>
    </table>
</div>
<script type="application/javascript">
    function get_remitOrganTable_Form() {
        var formDataJson = [];
        $('#finaceTableId tr').each(function(i){
            if (i >= 1) {
                var data = {};
                data.id = $(this).find("input[name='agentbusid']").val();
                data.finaceRemitOrgan = $(this).find("select[name='finaceRemitOrgan']").val();
                formDataJson.push(data);
            }
        });
        return formDataJson;
    }

    function selectColinfos(agentId) {
        addTab({
            title: '代理商操作-查看' + agentId,
            border: false,
            closable: true,
            fit: true,
            href: '/agentEnter/selectColinfos?id='+ agentId
        });
    }
</script>
