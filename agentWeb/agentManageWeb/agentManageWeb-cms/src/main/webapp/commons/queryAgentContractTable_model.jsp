<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="合同信息"  data-options="iconCls:'fi-results'">
    <table class="grid">
        <c:if test="${!empty agentContracts}">
            <c:forEach items="${agentContracts}" var="agentContracts" >
                <tr >
                    <shiro:hasPermission name="/contract/contType">
                    <td>合同类型</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${agentContracts.id}" MODELKEY="CONTTYPE">
                        <c:forEach items="${contractType}" var="contractTypeItem">
                            <c:if test="${contractTypeItem.dItemvalue==agentContracts.contType}">${contractTypeItem.dItemname}</c:if>
                        </c:forEach>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/contract/contNum">
                    <td>合同号</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${agentContracts.id}" MODELKEY="CONTNUM">
                        ${agentContracts.contNum}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/contract/contDate">
                    <td>合同签约时间</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${agentContracts.id}" MODELKEY="CONTDATE">
                        <fmt:formatDate pattern="yyyy-MM-dd" value="${agentContracts.contDate}" />
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/contract/contEndDate">
                    <td>合同到期时间</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${agentContracts.id}" MODELKEY="CONTENDDATE">
                        <fmt:formatDate pattern="yyyy-MM-dd" value="${agentContracts.contEndDate}" />
                    </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <shiro:hasPermission name="/contract/appendAgree">
                    <td>是否附加协议</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${agentContracts.id}" MODELKEY="APPENDAGREE">
                        <c:forEach items="${yesOrNo}" var="yesOrNoItem"  >
                            <c:if test="${yesOrNoItem.dItemvalue == agentContracts.appendAgree}">${yesOrNoItem.dItemname}</c:if>
                        </c:forEach>
                    </td>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/contract/protocolRuleRel">
                    <td>分管协议</td>
                    <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agentContracts.id}" MODELKEY="APPENDAGREE">
                            ${agentContracts.assProtocolMap.PROTOCOL_RULE_REL}
                    </td>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/contract/remark">
                    <td>备注</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${agentContracts.id}" MODELKEY="REMARK">
                        ${agentContracts.remark}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/contract/cloReviewStatus">
                    <td>审批状态</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${agentContracts.id}" MODELKEY="CLOREVIEWSTATUS">
                        <c:forEach items="${agStatusi}" var="agStatusi">
                            <c:if test="${agStatusi.dItemvalue==agentContracts.cloReviewStatus}">${agStatusi.dItemname}</c:if>
                        </c:forEach>
                    </td>
                    </shiro:hasPermission>
                </tr>
                <shiro:hasPermission name="/contract/attFiles">
                    <c:if test="${!empty agentContracts.attachmentList}">
                        <tr>
                            <td colspan="8">
                            <c:forEach items="${agentContracts.attachmentList}" var="attachment">

                                    <span SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${attachment.id}" MODELKEY="ATTACHMENT"><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></span>
                                    <span><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看合同信息</a></span>

                            </c:forEach>
                            </td>
                        </tr>
                    </c:if>
                </shiro:hasPermission>
            </c:forEach>
        </c:if>
    </table>
</div>