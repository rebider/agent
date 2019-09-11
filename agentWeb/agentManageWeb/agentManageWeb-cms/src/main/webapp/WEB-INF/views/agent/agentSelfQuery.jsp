<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<div class="easyui-tabs">
    <div title="查看信息">
        <%@ include file="/commons/queryAgentBase_model.jsp" %>
        <%@ include file="/commons/queryAttachment_model.jsp" %>
        <%@ include file="/commons/queryAgentcapital_model.jsp" %>
        <%@ include file="/commons/queryAgentContractTable_model.jsp" %>
        <%@ include file="/commons/queryAgentColinfoTable_model.jsp" %>
        <div class="easyui-panel" title="业务信息"  data-options="iconCls:'fi-results'">
            <div class="easyui-tabs">
                <c:if test="${!empty agentBusInfos}">
                    <c:forEach items="${agentBusInfos}" var="agentBusInfos">
                        <div title="<c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                            <c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">${ablePlatFormItem.platformName}</c:if>
                        </c:forEach>">
                            <table class="grid">
                                <tr >
                                    <td>业务平台</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSPLATFORM">
                                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                                            <c:if test="${ablePlatFormItem.platformNum== agentBusInfos.busPlatform}">
                                                ${ablePlatFormItem.platformName}
                                                <a href="javascript:void(0)" style="display: none" id="busPlatform">
                                                        ${ablePlatFormItem.platformNum}
                                                </a>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>业务平台编号</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSNUM"  width="180px">
                                        <input type="hidden" id="busId" value="${agentBusInfos.id}">
                                        <a href="javascript:void(0)" style="text-decoration:none;color:#333;" id="busNum">${agentBusInfos.busNum}</a>
                                    </td>
                                    <td>业务区域</td>
                                    <td title="<agent:show type="posRegion" busId="${agentBusInfos.busRegion}"/>" SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSREGION" >
                                        <div onclick="showBusRegionFrame({target:this,bufData:'${agentBusInfos.busRegion}',but:false})">查看</div><input type="hidden" value="${agentBusInfos.busRegion}"/>
                                    </td>
                                    <td>投诉及风险风控对接邮箱</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSRISKEMAIL">${agentBusInfos.busRiskEmail}</td>
                                </tr>
                                <tr >
                                    <td>业务联系人</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACT" ><desensitization:show type="name" value="${agentBusInfos.busContact}" jurisdiction="/agent/busNameSee"/></td>
                                    <td>业务联系电话</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="BUSCONTACTMOBILE"><desensitization:show type="mobile" value="${agentBusInfos.busContactMobile}" jurisdiction="/agent/busMobileSee"/></td>
                                    <td>分管协议</td>
                                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentBusInfos.id}" MODELKEY="ASSPROTOCOLID">
                                            ${agentBusInfos.assProtocolMap.PROTOCOL_RULE_REL}
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</div>