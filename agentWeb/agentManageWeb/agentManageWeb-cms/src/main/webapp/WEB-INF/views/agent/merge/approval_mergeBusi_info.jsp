<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="合并业务信息" data-options="iconCls:'fi-results'">
    <div class="easyui-tabs">
        <c:if test="${!empty agentMergeBusInfos}">
            <c:forEach items="${agentMergeBusInfos}" var="agentBusInfos">
                <div title="<c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                <c:if test="${ablePlatFormItem.platformNum == agentBusInfos.busPlatform}">${ablePlatFormItem.platformName}</c:if>
                            </c:forEach>">
                    <table class="grid">
                        <tr>
                            <td>被合并代理商编码：</td>
                            <td>${agentBusInfos.subAgentId}</td>
                            <td>被合并代理商名称：</td>
                            <td>${agentBusInfos.subAgentName}</td>
                            <td>合并状态：</td>
                            <td>${agentBusInfos.mergeStatusName}</td>
                        </tr>
                        <tr>
                            <td>业务平台：</td>
                            <td>
                                <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                    <c:if test="${ablePlatFormItem.platformNum == agentBusInfos.busPlatform}">
                                        ${ablePlatFormItem.platformName}
                                        <a href="javascript:void(0)" style="display: none" id="busPlatform">
                                                ${ablePlatFormItem.platformNum}
                                        </a>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>业务平台编号：</td>
                            <td>${agentBusInfos.busNum}</td>
                            <td>业务类型：</td>
                            <td>
                                <c:forEach items="${busType}" var="busTypeItem">
                                    <c:if test="${busTypeItem.dItemvalue == agentBusInfos.busType}">${busTypeItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                            <td>所属上级代理：</td>
                            <td>
                                <agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busParent}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>风险承担所属代理商：</td>
                            <td><agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busRiskParent}"/></td>
                            <td>激活及返现所属代理商：</td>
                            <td><agent:show type="agentBusIdForAgent" busId="${agentBusInfos.busActivationParent}"/></td>
                            <td>业务区域：</td>
                            <td title="<agent:show type="posRegion" busId="${agentBusInfos.busRegion}"/>">
                                <div onclick="showBusRegionFrame({
                                        target: this,
                                        bufData: '${agentBusInfos.busRegion}',
                                        but: false})">查看</div>
                                <input type="hidden" value="${agentBusInfos.busRegion}"/>
                            </td>
                            <td>投诉及风险风控对接邮箱：</td>
                            <td>${agentBusInfos.busRiskEmail}</td>
                        </tr>
                        <tr >
                            <td>业务联系人：</td>
                            <td><desensitization:show type="name" value="${agentBusInfos.busContact}"/></td>
                            <td>业务联系电话：</td>
                            <td><desensitization:show type="mobile" value="${agentBusInfos.busContactMobile}"/></td>
                            <td>分润对接邮箱：</td>
                            <td>${agentBusInfos.busContactEmail}</td>
                            <td>业务对接人：</td>
                            <td><desensitization:show type="name" value="${agentBusInfos.busContactPerson}"/></td>
                        </tr>
                        <tr>
                            <td>是否直发：</td>
                            <td>
                                <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                    <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.busSentDirectly}">${yesOrNoItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                            <td>是否直接返现：</td>
                            <td>
                                <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                    <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.busDirectCashback}">${yesOrNoItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                            <td>是否独立考核：</td>
                            <td>
                                <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                    <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.busIndeAss}">${yesOrNoItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                            <td>是否开具分润发票：</td>
                            <td>
                                <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                    <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.cloInvoice}">${yesOrNoItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                        </tr>
                        <tr >
                            <td>是否要求收据：</td>
                            <td>
                                <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                    <c:if test="${yesOrNoItem.dItemvalue == agentBusInfos.cloReceipt}">${yesOrNoItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                            <td>打款公司：</td>
                            <td>
                                <c:forEach items="${compList}" var="compListItem">
                                    <c:if test="${compListItem.id == agentBusInfos.cloPayCompany}">${compListItem.comName}</c:if>
                                </c:forEach>
                            </td>
                            <td>财务编号：</td>
                            <td>${agentBusInfos.agZbh}</td>
                            <td>使用范围：</td>
                            <td>
                                <c:forEach items="${useScope}" var="useScopeItem">
                                    <c:if test="${useScopeItem.dItemvalue == agentBusInfos.busUseOrgan}">${useScopeItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                        </tr>
                        <tr>
                            <td>平台登陆账号：</td>
                            <td>${agentBusInfos.busLoginNum}</td>
                            <td>业务状态：</td>
                            <td style="color: red">
                                <select id="busStatus" disabled="disabled">
                                    <c:forEach items="${busStatus}" var="busStatusItem">
                                        <option value="${busStatusItem.dItemvalue}"
                                            <c:if test="${busStatusItem.dItemvalue == agentBusInfos.busStatus}">selected="selected"</c:if>>
                                            ${busStatusItem.dItemname}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <c:if test="${agentBusInfos.busPlatformType=='POS' || agentBusInfos.busPlatformType=='ZPOS' || agentBusInfos.busPlatformType=='ZHPOS' || agentBusInfos.busPlatformType=='SSPOS' || agentBusInfos.busPlatformType=='RJPOS'}">
                            <tr>
                                <td>借记费率下限（%）</td>
                                <td>${agentBusInfos.debitRateLower}</td>
                                <td>借记封顶额（元）</td>
                                <td>${agentBusInfos.debitCapping}</td>
                                <td>借记出款费率（%）</td>
                                <td>${agentBusInfos.debitAppearRate}</td>
                                <td>贷记费率下限（%）</td>
                                <td>${agentBusInfos.creditRateFloor}</td>
                            </tr>
                            <tr>
                                <td>贷记费率上限（%）</td>
                                <td>${agentBusInfos.creditRateCeiling}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
<script type="application/javascript">
    function agentQueryBusTreeCallBach(data) {

    }
</script>