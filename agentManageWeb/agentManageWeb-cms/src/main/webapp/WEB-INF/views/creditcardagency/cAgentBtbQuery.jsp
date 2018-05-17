<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        $("#agentIdNum").val('${CagentBtb.agentId}');   // 代理商编号
        $("#cAgentIdNum").val('${CagentBtb.cAgentId}');   // 子集合作商编号
        $("#businessCode").val('${CagentBtb.businessCode}');   // 业务关系
        $("#status").val('${CagentBtb.status}');  // 状态
    });
</script>

<div style="padding: 3px;">
    <form id="cAgentBtbQueryForm" method="post">
        <input type="hidden" name="id" value="${CagentBtb.id}">
        <table class="grid">
            <tr>
                <td>业务关系编号</td>
                <td><input id="id" value="${CagentBtb.id}" name="id"  class="easyui-validatebox" style="width:160px;" disabled="true"></td>
                <td>代理商编号</td>
                <td>
                    <select class="easyui-combobox" id="agentIdNum" name="agentId" style="width:160px;" disabled="true">
                        <option></option>
                        <c:forEach var="cagent" items="${cagentList}">
                            <option value="${cagent.agentId}">${cagent.agentName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>子集合作商编号</td>
                <td>
                    <select class="easyui-combobox" id="cAgentIdNum" name="cAgentId" style="width:160px;" disabled="true">
                        <option></option>
                        <c:forEach var="cagent" items="${cagentList}">
                            <option value="${cagent.agentId}">${cagent.agentName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>业务关系</td>
                <td>
                    <select class="easyui-combobox" id="businessCode" name="businessCode" disabled="true" style="width:160px;">
                        <option></option>
                        <option value="CREDIT_APY">代办信用卡</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>创建时间</td>
                <td><input name="createtime" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${CagentBtb.createtime}' />" type="text" placeholder="请输入" disabled="true" style="width:160px;"></td>
                <td>状态</td>
                <td>
                    <select class="easyui-combobox" id="status" name="status" disabled="true" style="width:160px;">
                        <option></option>
                        <option value="0">删除</option>
                        <option value="1">正常</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
