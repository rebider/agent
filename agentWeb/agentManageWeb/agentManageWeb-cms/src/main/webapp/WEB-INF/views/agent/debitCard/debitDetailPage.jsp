<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
    <div class="easyui-panel" title="代理商结算卡信息" >
        <table class="grid">
            <c:forEach items="${agentColinfos}" var="agentColinfo">
                <tr>
                    <td>代理商唯一编码</td>
                    <td>${agentInfo.ID}</td>
                    <td>代理商名称</td>
                    <td>${agentInfo.AG_NAME}</td>
                    <td>审批状态</td>
                    <td>
                        <c:forEach items="${agStatuss}" var="aagStatusItem">
                            <c:if test="${aagStatusItem.dItemvalue == agentInfo.AG_STATUS}">${aagStatusItem.dItemname}</c:if>
                        </c:forEach>
                    </td>
                    <td>账户验证状态</td>
                    <td>
                        <c:forEach items="${flagList}" var="flagListItem"  >
                            <c:if test="${flagListItem.key == agentInfo.FLAG}">${flagListItem.value}</c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>收款账户类型</td>
                    <td>
                        <c:forEach items="${colInfoType}" var="colInfoTypeItem">
                            <c:if test="${colInfoTypeItem.dItemvalue==agentColinfo.cloType}">
                                ${colInfoTypeItem.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>收款账户名</td>
                    <td>${agentColinfo.cloRealname}</td>
                    <td>收款账号</td>
                    <td>${agentColinfo.cloBankAccount}</td>
                    <td>收款账户总行</td>
                    <td>${agentColinfo.cloBank}</td>
                </tr>
                <tr>
                    <td>开户行地区</td>
                    <td><agent:show type="region" busId="${agentColinfo.bankRegion}"/></td>
                    <td>收款开户支行</td>
                    <td>${agentColinfo.cloBankBranch}</td>
                    <td>总行联行号</td>
                    <td>${agentColinfo.allLineNum}</td>
                    <td>支行联行号</td>
                    <td>${agentColinfo.branchLineNum}</td>
                </tr>
                <tr>
                    <td>税点</td>
                    <td>${agentColinfo.cloTaxPoint}</td>
                    <td>是否开居分润发票</td>
                    <td>
                        <c:forEach items="${yesOrNo}" var="yesOrNoItem" >
                            <c:if test="${yesOrNoItem.dItemvalue==agentColinfo.cloInvoice}">${yesOrNoItem.dItemname}</c:if>
                        </c:forEach>
                    </td>
                    <td>备注</td>
                    <td colspan="3">${agentColinfo.remark}</td>
                </tr>
                <tr>
                    <td>户主证件号</td>
                    <td>${agentColinfo.agLegalCernum}</td>
                    <td>附件</td>
                    <td colspan="5">
                        <c:if test="${!empty agentColinfo.attachmentList}">
                            <c:forEach items="${agentColinfo.attachmentList}" var="attachment">
                                <span><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></span>
                                <span><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></span>
                            </c:forEach>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
                <tr>
                    <td>出款机构</td>
                    <td colspan="7">
                        <c:if test="${!empty agentBusInfos}">
                            <c:forEach items="${agentBusInfos}" var="agentBusInfo">
                                <span>${agentBusInfo.ORGAN_NUM}</span>
                            </c:forEach>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>顶级机构</td>
                    <td colspan="7">
                        <c:if test="${!empty agentBusInfos}">
                            <c:forEach items="${agentBusInfos}" var="agentBusInfo">
                                <span>${agentBusInfo.FINACE_REMIT_ORGAN} </span><span>,</span>
                            </c:forEach>
                        </c:if>
                    </td>
                </tr>
        </table>
    </div>

