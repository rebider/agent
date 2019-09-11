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
                <select name="approvalResult" style="width:160px;height:21px" >
                    <shiro:hasPermission name="/agentNetIn/approvalPass">
                        <option value="${passDict.dItemvalue}">${passDict.dItemname}</option>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agentNetIn/approvalReject">
                        <option value="${rejectDict.dItemvalue}">${rejectDict.dItemname}</option>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agentNetIn/approvalCancel">
                        <option value="${cancelDict.dItemvalue}">${cancelDict.dItemname}</option>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agentNetIn/approvalBack">
                        <option value="${backDict.dItemvalue}">${backDict.dItemname}</option>
                    </shiro:hasPermission>
                </select>
                <shiro:hasPermission name="netIn_apr_marketPermission">
                    <select name="dept" style="width:160px;height:21px" >
                        <c:forEach items="${netin_market}" var="netin_marketItem"  >
                            <option value="${netin_marketItem.dItemvalue}">${netin_marketItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </shiro:hasPermission>
            </td>
        </tr>
        <input type="hidden" name="taskId" value="${taskId}">
    </table>
</div>
<script>
    function get_netIn_subApproval_FormDataItem() {
        var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.dept  = $("select[name='dept']").val();
        data.taskId  = $("input[name='taskId']").val();
        return data;
    }

</script>
</shiro:hasPermission>