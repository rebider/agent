<shiro:hasPermission name="/agActivity/approvalBusiness">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="业务信息"  data-options="iconCls:'fi-results'" id="appEditDiv">
    <div class="easyui-tabs">
        <c:if test="${!empty agentBusInfos}">
            <c:forEach items="${agentBusInfos}" var="agentBusInfos">
                <c:if test="${agentBusInfos.busPlatformType=='POS' || agentBusInfos.busPlatformType=='ZPOS' || agentBusInfos.busPlatformType=='ZHPOS' || agentBusInfos.busPlatformType=='SSPOS' || agentBusInfos.busPlatformType=='RJPOS'}">
                    <div title="<c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                <c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">${ablePlatFormItem.platformName}</c:if>
                            </c:forEach>">
                        <table class="grid debitRateGrid">
                            <tr>
                                <td style="display: none"><input name="busId" type="hidden" value="${agentBusInfos.id}"></td>
                                <td width="150px">借记费率下限（%）</td>
                                <td width="160px"><input name="debitRateLower" maxlength="15" type="text" placeholder="请输入"
                                            class="easyui-validatebox" style="width:160px;" value="${agentBusInfos.debitRateLower}"></td>
                                <td width="150px">借记封顶额（元）</td>
                                <td width="160px"><input name="debitCapping" maxlength="15" type="text" placeholder="请输入"
                                                         class="easyui-validatebox"  style="width:160px;" value="${agentBusInfos.debitCapping}"></td>
                                <td width="150px">借记出款费率（%）</td>
                                <td width="160px"><input name="debitAppearRate" maxlength="15" type="text" placeholder="请输入"
                                                         class="easyui-validatebox"  style="width:160px;" value="${agentBusInfos.debitAppearRate}"></td>
                                <td width="150px">贷记费率下限（%）</td>
                                <td width="160px"><input name="creditRateFloor" maxlength="15" type="text" placeholder="请输入"
                                                         class="easyui-validatebox" style="width:160px;" value="${agentBusInfos.creditRateFloor}"></td>
                            </tr>
                            <tr>
                                <td width="150px">贷记费率上限（%）</td>
                                <td width="160px"><input name="creditRateCeiling" maxlength="15" type="text" placeholder="请输入"
                                                        class="easyui-validatebox" style="width:160px;" value="${agentBusInfos.creditRateCeiling}"></td>
                            </tr>
                        </table>
                    </div>
                </c:if>
                <c:if test="${agentBusInfos.busPlatformType=='RDBPOS' || agentBusInfos.busPlatformType=='RHPOS'}">
                    <div title="<c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                <c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">${ablePlatFormItem.platformName}</c:if>
                            </c:forEach>">
                        <table class="grid terminalsLowerGrid">
                            <tr>
                                <td style="display: none"><input name="busId" type="hidden" value="${agentBusInfos.id}"></td>
                                <td width="150px">终端数量下限:</td>
                                <td width="160px">
                                    <input name="terminalsLower" maxlength="15" type="text" placeholder="请输入" data-options="required:true"
                                                         class="easyui-validatebox" style="width:160px;" value="${agentBusInfos.terminalsLower}">
                                </td>
                            </tr>
                        </table>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
</div>
<script type="application/javascript">

    function get_busEditTable_Form() {
        var formDataJson = [];
        $('#appEditDiv .debitRateGrid').each(function(i){
            var data = {};
            data.id = $(this).find("input[name='busId']").val();
            data.debitRateLower = $(this).find("input[name='debitRateLower']").val();
            data.debitCapping = $(this).find("input[name='debitCapping']").val();
            data.debitAppearRate = $(this).find("input[name='debitAppearRate']").val();
            data.creditRateFloor = $(this).find("input[name='creditRateFloor']").val();
            data.creditRateCeiling = $(this).find("input[name='creditRateCeiling']").val();
            formDataJson.push(data);
        });
        return formDataJson;
    }
    function get_terminalsLower_Form() {
        var formDataJson = [];
        $('#appEditDiv .terminalsLowerGrid').each(function(i){
            var data = {};
            data.id = $(this).find("input[name='busId']").val();
            data.terminalsLower = $(this).find("input[name='terminalsLower']").val();
            formDataJson.push(data);
        });
        return formDataJson;
    }
</script>
</shiro:hasPermission>