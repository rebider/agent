<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="代理商信息" data-options="iconCls:'fi-results'">
    <form id="queryAgentBasics_model_form">
        <table class="grid">
            <tr>
                <td>代理商唯一编码</td>
                <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="ID">
                    <input type="hidden" name="id" value="${agent.id}">
                    ${agent.id}
                </td>
                <td>代理商名称</td>
                <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGNAME">
                    <input type="hidden" name="agName" value="${agent.agName}">
                    ${agent.agName}
                </td>
                <td>对接省区</td>
                <td colspan="3" SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGDOCPRO">
                    <input type="hidden" name="agDocPro" value="${agent.agDocPro}">
                    <agent:show type="dept" busId="${agent.agDocPro}" />
                </td>
                <td>审批状态</td>
                <td SCAN="TRUE" MODEL="AGENT" MODELID="${agent.id}" MODELKEY="AGSTATUSS">
                    <input type="hidden" name="agStatus" value="${agent.agStatus}">
                    <c:forEach items="${agStatuss}" var="agStatussItem">
                        <c:if test="${agStatussItem.dItemvalue==agent.agStatus}">${agStatussItem.dItemname}</c:if>
                    </c:forEach>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    //获取form数据
    function get_queryAgentBasics_FormData(){
        return quertBaseData = $.serializeObject($("#queryAgentBasics_model_form"));
    }
</script>
