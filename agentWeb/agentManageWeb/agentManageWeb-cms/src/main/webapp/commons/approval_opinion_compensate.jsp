<shiro:hasPermission name="/agActivity/approvalOpinion">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr >
            <td>审批意见</td>
            <td>
                <input class="easyui-textbox" name="approvalOpinion" data-options="multiline:true" value="" style="width:300px;height:100px">
            </td>
        </tr>
        <tr>
            <td>审批结果</td>
            <td>
                <shiro:hasPermission name="/order/approvalAllResult">
                    <select name="approvalResult" style="width:160px;height:21px" >
                        <c:forEach items="${approvalType}" var="appTypeItem"  >
                            <option value="${appTypeItem.dItemvalue}">${appTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </shiro:hasPermission>
                <shiro:hasPermission name="/order/approvalPassResult">
                    <select name="approvalResult" style="width:160px;height:21px" >
                        <c:forEach items="${approvalPassType}" var="appPassTypeItem"  >
                            <option value="${appPassTypeItem.dItemvalue}">${appPassTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </shiro:hasPermission>
            </td>
        </tr>
        <input type="hidden" name="taskId" value="${taskId}">
    </table>
</div>
<script>
    function get_approval_For_compenstate() {
        var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.taskId  = $("input[name='taskId']").val();
        return data;
    }

</script>
</shiro:hasPermission>