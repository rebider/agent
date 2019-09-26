<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="代理商基本信息"  data-options="iconCls:'fi-results'">
    <form id="agentBasics">
    <table class="grid" style="table-layout: fixed">
        <tr>
            <shiro:hasPermission name="/base/agName">
            <td>代理商名称</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGNAME" >${agent.agName}</td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agNature">
            <td>公司性质</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGNATURE">
                <c:forEach items="${agNatureType}" var="agNatureTypeItem">
                    <c:if test="${agNatureTypeItem.dItemvalue==agent.agNature}">${agNatureTypeItem.dItemname}</c:if>
                </c:forEach>
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agCapital">
            <td>注册资本</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGCAPITAL">
                ${agent.agCapital}/万元
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agBusLic">
            <td>营业执照</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGBUSLIC">
                ${agent.agBusLic}
            </td>
            </shiro:hasPermission>
        </tr>
        <tr>
            <shiro:hasPermission name="/base/agBusLicb">
            <td>营业执照开始时间</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGBUSLICB">
                <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.agBusLicb}" />
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agBusLice">
            <td>营业执照到期日</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGBUSLICE">
                <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.agBusLice}" />
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agHead">
            <td>负责人</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGHEAD">
                <desensitization:show type="name" value="${agent.agHead}" jurisdiction="/agent/baseNameSee"/>
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agHeadMobile">
            <td>负责人联系电话</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGHEADMOBILE">
                <desensitization:show type="mobile" value="${agent.agHeadMobile}" jurisdiction="/agent/baseMobileSee"/>
            </td>
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
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agLegalCernum">
            <td>法人证件号</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGLEGALCERNUM">
                <desensitization:show type="card" value="${agent.agLegalCernum}" jurisdiction="/agent/baseCardSee"/>
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agLegal">
            <td>法人姓名</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGLEGAL">
                <desensitization:show type="name" value="${agent.agLegal}" jurisdiction="/agent/baseNameSee"/>
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agLegalMobile">
            <td>法人联系电话</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGLEGALMOBILE">
                <desensitization:show type="mobile" value="${agent.agLegalMobile}" jurisdiction="/agent/baseMobileSee"/>
            </td>
            </shiro:hasPermission>
        </tr>
        <tr>
            <shiro:hasPermission name="/base/agRegArea">
            <td>注册区域</td>
            <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.agRegArea}" MODELKEY="AGREGAREA">
                <agent:show type="region" busId="${agent.agRegArea}" />
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agRegAdd">
            <td>注册地址</td>
            <td colspan="5" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGREGADD">
                <desensitization:show type="address" value="${agent.agRegAdd}" jurisdiction="/agent/baseAddressSee"/>
            </td>
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
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/agDocPro">
            <td>业务对接省区</td>
            <td colspan="3" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGDOCPRO">
                <agent:show type="dept" busId="${agent.agDocPro}" />
            </td>
            </shiro:hasPermission>
        </tr>
        <tr>
            <shiro:hasPermission name="/base/agBusScope">
            <td>营业范围</td>
            <td title="${agent.agBusScope}" colspan="2" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGBUSSCOPE" style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;" >
                    ${agent.agBusScope}
            </td>
            </shiro:hasPermission>

            <c:if test="${agent.reportStatus=='1'}">
            <shiro:hasPermission name="/base/reportStatus">
            <td>报备状态</td>
            <td  SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="REPORTSTATUS">
                    <c:forEach items="${reportStatusList}" var="reportStatusItem">
                        <c:if test="${reportStatusItem.dItemvalue==agent.reportStatus}">${reportStatusItem.dItemname}</c:if>
                    </c:forEach>
            </td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/reportTime">
            <td>报备时间</td>
            <td  SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="REPORTTIME">
                    <fmt:formatDate pattern="yyyy-MM-dd" value="${agent.reportTime}" />
            </td>
            </shiro:hasPermission>
            </c:if>
        </tr>

        <tr>
            <shiro:hasPermission name="/base/busRiskEmail">
                <td>投诉及风险风控对接邮箱</td>
                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agent.id}" MODELKEY="BUSRISKEMAIL">${agent.busRiskEmail}</td>
            </shiro:hasPermission>
            <shiro:hasPermission name="/base/busContactEmail">
                <td>分润对接邮箱</td>
                <td SCAN="TRUE" MODEL="AGENTBUSINFOS" MODELID="${agent.id}" MODELKEY="BUSCONTACTEMAIL">${agent.busContactEmail}</td>
            </shiro:hasPermission>
        </tr>

        <tr>
            <shiro:hasPermission name="/base/agRemark">
            <td>备注</td>
            <td colspan="7" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGREMARK">
                ${agent.agRemark}
            </td>
            </shiro:hasPermission>
        </tr>
    </table>
    </form>
</div>
<script type="text/javascript">

    //获取form数据
    function get_editAgentBasics(){
        return baseData = $.serializeObject($("#agentBasics"));
    }
</script>