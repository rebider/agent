<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="收款账户"  data-options="iconCls:'fi-results'">
    <table class="grid">
        <c:if test="${!empty agentColinfos}">
            <c:forEach items="${agentColinfos}" var="agentColinfos">
                <c:if test="${agentColinfos.id == ''}">
                    <tr>
                        <td colspan="8" style="font-size: 18px;font-weight: bold;text-align: center;font-family: 'Blackadder ITC'">[新增收款账户]</td>
                    </tr>
                    <tr>
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
                </c:if>
                <c:forEach items="${preAgentColinfos}" var="preAgentColinfos">
                    <c:if test="${agentColinfos.id != '' && agentColinfos.id == preAgentColinfos.id}">
                        <tr>
                            <shiro:hasPermission name="/colinfo/cloType">
                                <td>收款账户类型</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOTYPE" name="cloType" id="cloType">
                                    <select disabled="disabled">
                                        <c:forEach items="${colInfoType}" var="colInfoTypeItem">
                                            <option value="${colInfoTypeItem.dItemvalue}" <c:if test="${colInfoTypeItem.dItemvalue==agentColinfos.cloType}">selected="selected"</c:if>>${colInfoTypeItem.dItemname}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <c:if test="${agentColinfos.cloType != preAgentColinfos.cloType}">
                                    <td style="color: red">(原有信息:
                                        <select disabled="disabled">
                                            <c:forEach items="${colInfoType}" var="colInfoTypeItem">
                                                <option value="${colInfoTypeItem.dItemvalue}" <c:if test="${colInfoTypeItem.dItemvalue==preAgentColinfos.cloType}">selected="selected"</c:if>>${colInfoTypeItem.dItemname}</option>
                                            </c:forEach>
                                        </select>)
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.cloType == preAgentColinfos.cloType}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/colinfo/cloRealname">
                                <td>收款账户名</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOREALNAME">
                                        ${agentColinfos.cloRealname}
                                </td>
                                <c:if test="${agentColinfos.cloRealname != preAgentColinfos.cloRealname}">
                                    <td style="color: red">(原有信息:
                                        ${preAgentColinfos.cloRealname})
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.cloRealname == preAgentColinfos.cloRealname}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/colinfo/cloBankAccount">
                                <td>收款账号</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOBANKACCOUNT">
                                    <desensitization:show type="card" value="${agentColinfos.cloBankAccount}" jurisdiction="/agent/busBankCardSee"/>
                                </td>
                                <c:if test="${agentColinfos.cloBankAccount != preAgentColinfos.cloBankAccount}">
                                    <td style="color: red">(原有信息:
                                        <desensitization:show type="card" value="${preAgentColinfos.cloBankAccount}" jurisdiction="/agent/busBankCardSee"/>)
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.cloBankAccount == preAgentColinfos.cloBankAccount}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/colinfo/cloBank">
                                <td>收款开户总行</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOBANK">
                                        ${agentColinfos.cloBank}
                                </td>
                                <c:if test="${agentColinfos.cloBank != preAgentColinfos.cloBank}">
                                    <td style="color: red">(原有信息:
                                        ${preAgentColinfos.cloBank})
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.cloBank == preAgentColinfos.cloBank}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                        </tr>
                        <tr>
                            <shiro:hasPermission name="/colinfo/bankRegion">
                                <td>开户行地区</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="BANKREGION">
                                    <agent:show type="region" busId="${agentColinfos.bankRegion}"/>
                                </td>
                                <c:if test="${agentColinfos.bankRegion != preAgentColinfos.bankRegion}">
                                    <td style="color: red">(原有信息:
                                        <agent:show type="region" busId="${preAgentColinfos.bankRegion}"/>)
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.bankRegion == preAgentColinfos.bankRegion}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/colinfo/cloBankBranch">
                                <td>收款开户支行</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOBANKBRANCH">
                                        ${agentColinfos.cloBankBranch}
                                </td>
                                <c:if test="${agentColinfos.cloBankBranch != preAgentColinfos.cloBankBranch}">
                                    <td style="color: red">(原有信息:
                                        ${preAgentColinfos.cloBankBranch})
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.cloBankBranch == preAgentColinfos.cloBankBranch}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/colinfo/allLineNum">
                                <td>总行联行号</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="ALLLINENUM">
                                        ${agentColinfos.allLineNum}
                                </td>
                                <c:if test="${agentColinfos.allLineNum != preAgentColinfos.allLineNum}">
                                    <td style="color: red">(原有信息:
                                        ${preAgentColinfos.allLineNum})
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.allLineNum == preAgentColinfos.allLineNum}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/colinfo/branchLineNum">
                                <td>支行联行号</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="BRANCHLINENUM">
                                        ${agentColinfos.branchLineNum}
                                </td>
                                <c:if test="${agentColinfos.branchLineNum != preAgentColinfos.branchLineNum}">
                                    <td style="color: red">(原有信息:
                                        ${preAgentColinfos.branchLineNum})
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.branchLineNum == preAgentColinfos.branchLineNum}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                        </tr>
                        <tr>
                            <shiro:hasPermission name="/colinfo/cloTaxPoint">
                                <td>税点</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="CLOTAXPOINT">
                                        ${agentColinfos.cloTaxPoint}
                                </td>
                                <c:if test="${agentColinfos.cloTaxPoint != preAgentColinfos.cloTaxPoint}">
                                    <td style="color: red">(原有信息:
                                        ${preAgentColinfos.cloTaxPoint})
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.cloTaxPoint == preAgentColinfos.cloTaxPoint}">
                                    <td></td>
                                </c:if>
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
                                <c:if test="${agentColinfos.cloInvoice != preAgentColinfos.cloInvoice}">
                                    <td style="color: red">(原有信息:
                                        <select disabled="disabled">
                                            <c:forEach items="${yesOrNo}" var="yesOrNoItem" >
                                                <option value="${yesOrNoItem.dItemvalue}" <c:if test="${yesOrNoItem.dItemvalue==preAgentColinfos.cloInvoice}">selected="selected"</c:if>>${yesOrNoItem.dItemname}</option>
                                            </c:forEach>
                                        </select>)
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.cloInvoice == preAgentColinfos.cloInvoice}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/colinfo/remark">
                                <td>备注</td>
                                <td colspan="3" SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="REMARK">
                                        ${agentColinfos.remark}
                                </td>
                                <c:if test="${agentColinfos.remark != preAgentColinfos.remark && !(empty preAgentColinfos.remark && agentColinfos.remark=='')}">
                                    <td style="color: red">(原有信息:
                                        ${preAgentColinfos.remark})
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.remark == preAgentColinfos.remark or (empty agentColinfos.remark && agentColinfos.remark=='')}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
                        </tr>
                        <tr>
                            <shiro:hasPermission name="/colinfo/agLegalCernum">
                                <td>户主证件号</td>
                                <td SCAN="TRUE" MODEL="AGENTCOLINFOS" MODELID="${agentColinfos.id}" MODELKEY="AGLEGALCERNUM">
                                    <desensitization:show type="card" value="${agentColinfos.agLegalCernum}"/>
                                </td>
                                <c:if test="${agentColinfos.agLegalCernum != preAgentColinfos.agLegalCernum}">
                                    <td style="color: red">(原有信息:
                                        <desensitization:show type="card" value="${preAgentColinfos.agLegalCernum}"/>)
                                    </td>
                                </c:if>
                                <c:if test="${agentColinfos.agLegalCernum == preAgentColinfos.agLegalCernum}">
                                    <td></td>
                                </c:if>
                            </shiro:hasPermission>
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
                    </c:if>
                </c:forEach>
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