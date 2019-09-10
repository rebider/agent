<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="代理商基本信息"  data-options="iconCls:'fi-results'">
    <table class="grid" style="table-layout: fixed">
        <tr>
            <shiro:hasPermission name="/base/agName">
            <td>代理商名称</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGNAME" >${agent.agName}</td>
            <c:if test="${agent.agName != preAgent.agName}">
                <td style="color: red">(原有信息:${preAgent.agName})</td>
            </c:if>
            <c:if test="${agent.agName == preAgent.agName}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agNature">
            <td>公司性质</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGNATURE">
                <c:forEach items="${agNatureType}" var="agNatureTypeItem">
                    <c:if test="${agNatureTypeItem.dItemvalue==agent.agNature}">${agNatureTypeItem.dItemname}</c:if>
                </c:forEach>
            </td>
            <c:if test="${agent.agNature != preAgent.agNature}">
                <td style="color: red">(原有信息:
                    <c:forEach items="${agNatureType}" var="agNatureTypeItem">
                        <c:if test="${agNatureTypeItem.dItemvalue==preAgent.agNature}">${agNatureTypeItem.dItemname}</c:if>
                    </c:forEach>)
                </td>
            </c:if>
            <c:if test="${agent.agNature == preAgent.agNature}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agCapital">
            <td>注册资本</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGCAPITAL">
                ${agent.agCapital}/万元
            </td>
            <c:if test="${agent.agCapital != preAgent.agCapital}">
                <td style="color: red">(原有信息:${preAgent.agCapital}/万元)</td>
            </c:if>
            <c:if test="${agent.agCapital == preAgent.agCapital}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agBusLic">
            <td>营业执照</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGBUSLIC">
                ${agent.agBusLic}
            </td>
            <c:if test="${agent.agBusLic != preAgent.agBusLic}">
                <td style="color: red">(原有信息:${preAgent.agBusLic})</td>
            </c:if>
            <c:if test="${agent.agBusLic == preAgent.agBusLic}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
        </tr>
        <tr>
            <shiro:hasPermission name="/base/agBusLicb">
            <td>营业执照开始时间</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGBUSLICB">
                <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.agBusLicb}" />
            </td>
            <c:if test="${agent.agBusLicb != preAgent.agBusLicb}">
                <td style="color: red">(原有信息:<fmt:formatDate pattern="yyyy-MM-dd" value="${preAgent.agBusLicb}" />)</td>
            </c:if>
            <c:if test="${agent.agBusLicb == preAgent.agBusLicb}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agBusLice">
            <td>营业执照到期日</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGBUSLICE">
                <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.agBusLice}" />
            </td>
            <c:if test="${agent.agBusLice != preAgent.agBusLice}">
                <td style="color: red">(原有信息:<fmt:formatDate pattern="yyyy-MM-dd" value="${preAgent.agBusLice}" />)</td>
            </c:if>
            <c:if test="${agent.agBusLice == preAgent.agBusLice}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agHead">
            <td>负责人</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGHEAD">
                <desensitization:show type="name" value="${agent.agHead}" jurisdiction="/agent/baseNameSee"/>
            </td>
            <c:if test="${agent.agHead != preAgent.agHead}">
                <td style="color: red">(原有信息:
                    <desensitization:show type="name" value="${preAgent.agHead}" jurisdiction="/agent/baseNameSee"/>)
                </td>
            </c:if>
            <c:if test="${agent.agHead == preAgent.agHead}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agHeadMobile">
            <td>负责人联系电话</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGHEADMOBILE">
                <desensitization:show type="mobile" value="${agent.agHeadMobile}" jurisdiction="/agent/baseMobileSee"/>
            </td>
            <c:if test="${agent.agHeadMobile != preAgent.agHeadMobile}">
                <td style="color: red">(原有信息:<desensitization:show type="mobile" value="${preAgent.agHeadMobile}" jurisdiction="/agent/baseMobileSee"/>)</td>
            </c:if>
            <c:if test="${agent.agHeadMobile == preAgent.agHeadMobile}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
        </tr>
        <tr>
            <shiro:hasPermission name="/base/agLegalCertype">
            <td>法人证件类型</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="CERTTYPE">
                <c:forEach items="${certType}" var="certTypeItem">
                    <c:if test="${certTypeItem.dItemvalue== agent.agLegalCertype}">${certTypeItem.dItemname}</c:if>
                </c:forEach>
            </td>
            <c:if test="${agent.agLegalCertype != preAgent.agLegalCertype}">
                <td style="color: red">(原有信息:
                    <c:forEach items="${certType}" var="certTypeItem">
                        <c:if test="${certTypeItem.dItemvalue== preAgent.agLegalCertype}">${certTypeItem.dItemname}</c:if>
                    </c:forEach>)
                </td>
            </c:if>
            <c:if test="${agent.agLegalCertype == preAgent.agLegalCertype}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agLegalCernum">
            <td>法人证件号</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGLEGALCERNUM">
                <desensitization:show type="card" value="${agent.agLegalCernum}" jurisdiction="/agent/baseCardSee"/>
            </td>
            <c:if test="${agent.agLegalCernum != preAgent.agLegalCernum}">
                <td style="color: red">(原有信息:<desensitization:show type="card" value="${preAgent.agLegalCernum}" jurisdiction="/agent/baseCardSee"/>)</td>
            </c:if>
            <c:if test="${agent.agLegalCernum == preAgent.agLegalCernum}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agLegal">
            <td>法人姓名</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGLEGAL">
                <desensitization:show type="name" value="${agent.agLegal}" jurisdiction="/agent/baseNameSee"/>
            </td>
            <c:if test="${agent.agLegal != preAgent.agLegal}">
                <td style="color: red">(原有信息:
                    <desensitization:show type="name" value="${preAgent.agLegal}" jurisdiction="/agent/baseNameSee"/>)
                </td>
            </c:if>
            <c:if test="${agent.agLegal == preAgent.agLegal}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agLegalMobile">
            <td>法人联系电话</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGLEGALMOBILE">
                <desensitization:show type="mobile" value="${agent.agLegalMobile}" jurisdiction="/agent/baseMobileSee"/>
            </td>
            <c:if test="${agent.agLegalMobile != preAgent.agLegalMobile}">
                <td style="color: red">(原有信息:<desensitization:show type="mobile" value="${preAgent.agLegalMobile}" jurisdiction="/agent/baseMobileSee"/>)</td>
            </c:if>
            <c:if test="${agent.agLegalMobile == preAgent.agLegalMobile}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
        </tr>
        <tr>
            <shiro:hasPermission name="/base/agRegArea">
            <td>注册区域</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.agRegArea}" MODELKEY="AGREGAREA">
                <agent:show type="region" busId="${agent.agRegArea}" />
            </td>
            <c:if test="${agent.agRegArea != preAgent.agRegArea}">
                <td style="color: red">(原有信息:<agent:show type="region" busId="${preAgent.agRegArea}" />)</td>
            </c:if>
            <c:if test="${agent.agRegArea == preAgent.agRegArea}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agRegAdd">
            <td>注册地址</td>
            <td colspan="5" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGREGADD">
                <desensitization:show type="address" value="${agent.agRegAdd}" jurisdiction="/agent/baseAddressSee"/>
            </td>
            <c:if test="${agent.agRegAdd != preAgent.agRegAdd}">
                <td style="color: red">(原有信息:<desensitization:show type="address" value="${preAgent.agRegAdd}" jurisdiction="/agent/baseAddressSee"/>)</td>
            </c:if>
            <c:if test="${agent.agRegAdd == preAgent.agRegAdd}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
        </tr>
        <tr>
            <%--<td>税点</td>--%>
            <%--<td>${agent.cloTaxPoint}</td>--%>
            <shiro:hasPermission name="/base/agDocDistrict">
            <td>业务对接大区</td>
            <td colspan="3" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGDOCDISTRICT">
                <agent:show type="dept" busId="${agent.agDocDistrict}" />
            </td>
            <c:if test="${agent.agDocDistrict != preAgent.agDocDistrict}">
                <td style="color: red">(原有信息:<agent:show type="dept" busId="${preAgent.agDocDistrict}" />)</td>
            </c:if>
            <c:if test="${agent.agDocDistrict == preAgent.agDocDistrict}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agDocPro">
            <td>业务对接省区</td>
            <td colspan="3" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGDOCPRO">
                <agent:show type="dept" busId="${agent.agDocPro}" />
            </td>
            <c:if test="${agent.agDocPro != preAgent.agDocPro}">
                <td style="color: red">(原有信息:
                    <agent:show type="dept" busId="${preAgent.agDocPro}" />)
                </td>
            </c:if>
            <c:if test="${agent.agDocPro == preAgent.agDocPro}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
        </tr>
        <tr>
            <shiro:hasPermission name="/base/agBusScope">
            <td>营业范围</td>
            <td title="${agent.agBusScope}" colspan="2" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGBUSSCOPE" style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" >
                    ${agent.agBusScope}
            </td>
            <c:if test="${agent.agBusScope != preAgent.agBusScope}">
                <td style="color: red">(原有信息:${preAgent.agBusScope})</td>
            </c:if>
            <c:if test="${agent.agBusScope == preAgent.agBusScope}">
                <td></td>
            </c:if>
            </shiro:hasPermission>

            <c:if test="${agent.reportStatus=='1'}">
            <shiro:hasPermission name="/base/reportStatus">
            <td>报备状态</td>
            <td  SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="REPORTSTATUS">
                    <c:forEach items="${reportStatusList}" var="reportStatusItem">
                        <c:if test="${reportStatusItem.dItemvalue==agent.reportStatus}">${reportStatusItem.dItemname}</c:if>
                    </c:forEach>
            </td>
            <c:if test="${agent.reportStatus != preAgent.reportStatus}">
                <td style="color: red">(原有信息:
                    <c:forEach items="${reportStatusList}" var="reportStatusItem">
                        <c:if test="${reportStatusItem.dItemvalue==preAgent.reportStatus}">${reportStatusItem.dItemname}</c:if>
                    </c:forEach>)
                </td>
            </c:if>
            <c:if test="${agent.reportStatus == preAgent.reportStatus}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/reportTime">
            <td>报备时间</td>
            <td  SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="REPORTTIME">
                    <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.reportTime}" />
            </td>
            <c:if test="${agent.reportTime != preAgent.reportTime}">
                <td style="color: red">(原有信息:
                    <fmt:formatDate pattern="yyyy-MM-dd" value="${preAgent.reportTime}" />)
                </td>
            </c:if>
            <c:if test="${agent.reportTime == preAgent.reportTime}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
            </c:if>
        </tr>

        <tr>
            <shiro:hasPermission name="/base/busRiskEmail">
                <td>投诉及风险风控对接邮箱</td>
                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agent.id}" MODELKEY="BUSRISKEMAIL">${agent.busRiskEmail}</td>
                <c:if test="${agent.busRiskEmail != preAgent.busRiskEmail}">
                    <td style="color: red">(原有信息:${preAgent.busRiskEmail})</td>
                </c:if>
                <c:if test="${agent.busRiskEmail == preAgent.busRiskEmail}">
                    <td></td>
                </c:if>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/busContactEmail">
                <td>分润对接邮箱</td>
                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agent.id}" MODELKEY="BUSCONTACTEMAIL">${agent.busContactEmail}</td>
                <c:if test="${agent.busContactEmail != preAgent.busContactEmail}">
                    <td style="color: red">(原有信息:${preAgent.busContactEmail})</td>
                </c:if>
                <c:if test="${agent.busContactEmail == preAgent.busContactEmail}">
                    <td></td>
                </c:if>
            </shiro:hasPermission>
        </tr>

        <tr>
            <shiro:hasPermission name="/base/agRemark">
            <td>备注</td>
            <td colspan="7" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGREMARK">
                ${agent.agRemark}
            </td>
            <c:if test="${agent.agRemark != preAgent.agRemark && !(empty preAgent.agRemark && agent.agRemark=='')}">
                <td style="color: red">(原有信息:${preAgent.agRemark})</td>
            </c:if>
            <c:if test="${agent.agRemark == preAgent.agRemark or (empty preAgent.agRemark && agent.agRemark=='')}">
                <td></td>
            </c:if>
            </shiro:hasPermission>
        </tr>
    </table>
</div>
