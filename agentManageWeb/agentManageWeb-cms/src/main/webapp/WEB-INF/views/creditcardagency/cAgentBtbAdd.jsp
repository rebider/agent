<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>

<script type="text/javascript">
    $(function() {
        $('#cAgentBtbAddForm').form({
            url : '${path }/creditCardCAgentBtb/addCAgentBtb',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (!isValid) {

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
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>

<div class="easyui-layout" data-options="fit:true,border:true" >
    <div data-options="region:'center',border:false" style="padding: 3px;" >
        <form id="cAgentBtbAddForm" method="post">
            <table class="grid">
                <!-- <tr>
	                <td>业务关系编号</td>
	                <td><input id="id" name="id" style="width:160px;" class="easyui-validatebox" data-options="required:true"></td>
                </tr> -->
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
                        <select class="easyui-combobox" name="businessCode" style="width:160px;" data-options="required:true">
                            <option></option>
                            <option value="CREDIT_APY">代办信用卡</option>
                        </select>
                    </td>
                    <%--<td>创建时间</td>
                    <td><input name="createtime" type="text" class="easyui-datetimebox" style="width:160px;"></td>--%>
                    <%--<td>状态</td>
                    <td>
                        <select class="easyui-combobox" name="status" style="width:160px;" data-options="required:true">
                            <option></option>
                            <option value="1" selected="selected">正常</option>
                            <option value="0">删除</option>
                        </select>
                    </td>--%>
                </tr>
            </table>
        </form>
    </div>
</div>
