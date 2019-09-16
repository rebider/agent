<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function () {
        $('#reportEditForm').form({
            url: '${path}/agentEnter/reportUpdate',
            onSubmit: function () {
                var isValid = $(this).form('validate');
                if (!isValid) {
                }
                return isValid;
            },
            success: function (result) {
                result = $.parseJSON(result);
                if (result.status==200) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            },

        });
    });

</script>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'center',border:false" style="padding: 3px;">
        <form id="reportEditForm" method="post">
            <input type="hidden" name="id" value="${id}">
            <table class="grid">
                <tr style="height:21px">
                    <td>报备状态:</td>
                    <td>
                        <select name="reportStatus" style="width:150px;height:21px" >
                            <option value="">---请选择----</option>
                            <c:forEach items="${reportStatusList}" var="reportStatusItem"  >
                                <option value="${reportStatusItem.dItemvalue}" <c:if test="${reportStatusItem.dItemvalue== agent.reportStatus}">selected</c:if>>${reportStatusItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>报备时间:</td>
                    <td><input class="easyui-datetimebox" name="time" data-options="required:true"
                               style="width:150px"
                               value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm:ss' value='${agent.reportTime}' />">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>