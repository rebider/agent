<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#cAgentBtbEditForm').form({
            url : '${path }/creditCardCAgentBtb/editCAgentBtb',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;

                /*var agentId = $('#agentIdNum').combobox('getValue');
                var cAgentId = $('#cAgentIdNum').combobox('getValue');
                if (agentId == cAgentId) {
                    parent.$.messager.alert('提示', "代理商和子集合作商不能为同一个", 'info');
                    return false;
                }*/
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');   // 之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });

        $("#agentIdNum").val('${CagentBtb.agentId}');   // 代理商编号
        $("#cAgentIdNum").val('${CagentBtb.cAgentId}');   // 子集合作商编号
        $("#businessCode").val('${CagentBtb.businessCode}');   // 业务关系
    });
</script>
<div style="padding: 3px;">
    <form id="cAgentBtbEditForm" method="post">
        <input type="hidden" id="id" name="id" value="${CagentBtb.id}">
        <table class="grid">
            <tr>
                <td>代理商编号</td>
                <td>
                    <select class="easyui-combobox" id="agentIdNum" name="agentId" style="width:160px;" data-options="required:true">
                        <option></option>
                        <c:forEach var="cagent" items="${cagentList}">
                            <option value="${cagent.agentId}">${cagent.agentName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>子集合作商编号</td>
                <td>
                    <select class="easyui-combobox" id="cAgentIdNum" name="cAgentId" style="width:160px;" data-options="required:true">
                        <option></option>
                        <c:forEach var="cagent" items="${cagentList}">
                            <option value="${cagent.agentId}">${cagent.agentName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>业务关系</td>
                <td>
                    <select class="easyui-combobox" id="businessCode" name="businessCode" style="width:160px;" data-options="required:true"
                        <option></option>
                        <option value="CREDIT_APY">代办信用卡</option>
                    </select>
                </td>
                <%--<td>创建时间</td>
                <td><input id="createtime" name="createtime" value="${CagentBtb.createtime}" type="text" class="easyui-datetimebox" style="width:160px;"></td>
                <td>状态</td>
                <td>
                    <select class="easyui-combobox" id="status" name="status" value="${CagentBtb.status}" style="width:160px;">
                        <option></option>
                        <option value="1" selected="selected">正常</option>
                        <option value="0">删除</option>
                    </select>
                </td>
            </tr>--%>
        </table>
    </form>
</div>
