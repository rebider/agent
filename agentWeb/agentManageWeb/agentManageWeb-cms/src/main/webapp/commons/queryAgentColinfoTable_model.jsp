<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="收款账户"  data-options="iconCls:'fi-results'">
    <table class="grid">
        <c:if test="${!empty agentColinfos}">
            <c:forEach items="${agentColinfos}" var="agentColinfos">
                <tr >
                    <shiro:hasPermission name="/colinfo/cloType">
                    <td>收款账户类型</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOTYPE" name="cloType" id="cloType">
                        <select disabled="disabled">
                        <c:forEach items="${colInfoType}" var="colInfoTypeItem">
                            <option value="${colInfoTypeItem.dItemvalue}" <c:if test="${colInfoTypeItem.dItemvalue==agentColinfos.cloType}">selected="selected"</c:if>>${colInfoTypeItem.dItemname}</option>
                        </c:forEach>
                        </select>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/cloRealname">
                    <td>收款账户名</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOREALNAME">
                        ${agentColinfos.cloRealname}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/cloBankAccount">
                    <td>收款账号</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOBANKACCOUNT">
                        <desensitization:show type="card" value="${agentColinfos.cloBankAccount}" jurisdiction="/agent/busBankCardSee"/>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/cloBank">
                    <td>收款开户总行</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOBANK">
                        ${agentColinfos.cloBank}
                    </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <shiro:hasPermission name="/colinfo/bankRegion">
                    <td>开户行地区</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="BANKREGION">
                        <agent:show type="region" busId="${agentColinfos.bankRegion}"/>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/cloBankBranch">
                    <td>收款开户支行</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOBANKBRANCH">
                        ${agentColinfos.cloBankBranch}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/allLineNum">
                    <td>总行联行号</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="ALLLINENUM">
                        ${agentColinfos.allLineNum}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/branchLineNum">
                    <td>支行联行号</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="BRANCHLINENUM">
                        ${agentColinfos.branchLineNum}
                    </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <shiro:hasPermission name="/colinfo/cloTaxPoint">
                    <td>税点</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOTAXPOINT">
                        ${agentColinfos.cloTaxPoint}
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/cloInvoice">
                    <td>是否开具分润发票</td>
                    <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOINVOICE" name="cloInvoice" id="cloInvoice">
                        <select disabled="disabled">
                        <c:forEach items="${yesOrNo}" var="yesOrNoItem" >
                            <option value="${yesOrNoItem.dItemvalue}" <c:if test="${yesOrNoItem.dItemvalue==agentColinfos.cloInvoice}">selected="selected"</c:if>>${yesOrNoItem.dItemname}</option>
                        </c:forEach>
                        </select>
                    </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/remark">
                    <td>备注</td>
                    <td colspan="3" SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="REMARK">
                        ${agentColinfos.remark}
                    </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <shiro:hasPermission name="/colinfo/agLegalCernum">
                        <td>户主证件号</td>
                        <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="AGLEGALCERNUM">
                            <desensitization:show type="card" value="${agentColinfos.agLegalCernum}"/>
                        </td>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/colinfo/cloReviewStatus">
                    <td>审批状态</td>
                    <td SCAN="TRUE" MODEL="AGENTCONTRACTS" MODELID="${agentColinfos.id}" MODELKEY="CLOREVIEWSTATUS">
                        <c:forEach items="${agStatusi}" var="agStatusi">
                            <c:if test="${agStatusi.dItemvalue==agentColinfos.cloReviewStatus}">${agStatusi.dItemname}</c:if>
                        </c:forEach>
                    </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <shiro:hasPermission name="/colinfo/attFiles">
                        <c:if test="${!empty agentColinfos.attachmentList}">
                            <td>附件</td>
                            <td colspan="7">
                                <c:forEach items="${agentColinfos.attachmentList}" var="attachment">
                                    <span SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${attachment.id}" MODELKEY="ATTACHMENT"><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></span>
                                    <span><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></span>
                                </c:forEach>
                            </td>
                        </c:if>
                    </shiro:hasPermission>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</div>
<script>
    function get_agentColinfo() {
        var formDataJson = [];
            var data = {};
            data.cloType= $("#cloType").find('option:selected').val();
            data.cloInvoice= $("#cloInvoice").find('option:selected').val();
            formDataJson.push(data);
        return formDataJson;
    }
</script>