<shiro:hasPermission name="/agActivity/approvalBusiness">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'">
    <c:if test="${!empty agentBusInfos}">
        <div class="easyui-tabs" id="busAppEditDiv">
            <c:forEach items="${agentBusInfos}" var="agentBusInfo">
                <div title="
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >
                            <c:if test="${agentBusInfo.busPlatform==ablePlatFormItem.platformNum}">
                                ${ablePlatFormItem.platformName}
                            </c:if>
                         </c:forEach>
                    ">
                    <table class="grid">
                        <tr>
                            <td style="width: 20%">
                                业务所属上级
                                <input type="hidden" name="busPlatform" value="${agentBusInfo.busPlatform}">
                            </td>
                            <td>
                                <input type="hidden" name="busId" value="${agentBusInfo.id}">
                                <input type="hidden" name="busParent" value="${agentBusInfo.busParent}">
                                <input type="text" value="<agent:show type="agentBusIdForAgent" busId="${agentBusInfo.busParent}"/>">
                                <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                                    target:this,
                                    urlpar:'?busPlatform='+$(this).parent().parent().find('input[name=\'busPlatform\']').val()},callBack:returnAppAgentSele})">选择</a>
                            </td>
                        </tr>
                        <tr>
                            <td>业务风险所属上级</td>
                            <td>
                                <input type="hidden" name="busRiskParent" value="${agentBusInfo.busRiskParent}">
                                <input type="text" value="<agent:show type="agentBusIdForAgent" busId="${agentBusInfo.busRiskParent}"/>">
                                <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                                    target:this,
                                    urlpar:'?busPlatform='+$(this).parent().parent().prev().find('input[name=\'busPlatform\']').val()},callBack:returnAppAgentSele})">选择</a>
                            </td>
                        </tr>
                        <tr>
                            <td>激活及返现业务所属上级</td>
                            <td>
                                <input type="hidden" name="busActivationParent" value="${agentBusInfo.busActivationParent}">
                                <input type="text" value="<agent:show type="agentBusIdForAgent" busId="${agentBusInfo.busActivationParent}"/>">
                                <a href="javascript:void(0);" onclick="showAgentSelectDialog({data:{
                                    target:this,
                                    urlpar:'?busPlatform='+$(this).parent().parent().prev().prev().find('input[name=\'busPlatform\']').val()},callBack:returnAppAgentSele})">选择</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>
<script>

    function returnAppAgentSele(data,srcData){
        $(srcData.target).prev("input").val(data.AG_NAME);
        $(srcData.target).prev("input").prev("input").val(data.ID);
    }

    function get_busEditTable_FormDataItem() {
        var formDataJson = [];
        $('#busAppEditDiv .grid').each(function(i){
            var data = {};
            data.id = $(this).find("input[name='busId']").val();
            data.busParent = $(this).find("input[name='busParent']").val();
            data.busRiskParent = $(this).find("input[name='busRiskParent']").val();
            data.busActivationParent = $(this).find("input[name='busActivationParent']").val();
            formDataJson.push(data);
        });
        return formDataJson;
    }
</script>
</shiro:hasPermission>