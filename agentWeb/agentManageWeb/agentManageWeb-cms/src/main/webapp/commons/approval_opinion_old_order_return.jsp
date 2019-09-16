<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'" id="old_order_return_approve_panel">
    <table class="grid">
        <tr >
            <td>审批意见</td>
            <td>
                <input class="easyui-textbox" name="approvalOpinion" data-options="multiline:true" value="" style="width:300px;height:100px">
            </td>
        </tr>

        <%--代理商上传物流--%>
            <tr>
                <td>审批结果</td>
                <td>
                    <select name="approvalResult" style="width:160px;height:21px" >
                        <c:choose>
                            <c:when test="${sid=='sid-F315F787-E98B-40FA-A6DC-6A962201075D'}"><option value="${passDict.dItemvalue}">${passDict.dItemname}</option></c:when>
                            <c:when test="${sid=='sid-E33DC3B4-6BC3-4258-982A-B6DB0E1B23B8'}"><option value="${passDict.dItemvalue}">${passDict.dItemname}</option></c:when>
                            <c:when test="${sid=='sid-4528CEA4-998C-4854-827B-1842BBA5DB4B'}"><option value="${passDict.dItemvalue}">${passDict.dItemname}</option></c:when>
                            <c:otherwise>
                                <option value="${passDict.dItemvalue}">${passDict.dItemname}</option>
                                <option value="${rejectDict.dItemvalue}">${rejectDict.dItemname}</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </td>
            </tr>
        <input type="hidden" name="taskId" value="${taskId}">
    </table>
</div>
<script>
    function get_old_order_return_approve_panel_FormDataItem() {
        var data = {};
        data.approvalOpinion = $("#old_order_return_approve_panel input[name='approvalOpinion']").val();
        data.approvalResult  = $("#old_order_return_approve_panel select[name='approvalResult']").val();
        data.taskId  = $("#old_order_return_approve_panel input[name='taskId']").val();
        return data;
    }

</script>
