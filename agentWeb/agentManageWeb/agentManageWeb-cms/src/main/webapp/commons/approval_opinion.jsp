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
                <shiro:hasPermission name="/agActivity/approvalAllResult">
                    <select name="approvalResult" style="width:160px;height:21px" >
                        <%--<c:forEach items="${approvalType}" var="appTypeItem"  >
                            <c:if test="${appTypeItem.dItemvalue != 'cancel'}">
                            <option value="${appTypeItem.dItemvalue}">${appTypeItem.dItemname}</option>
                            </c:if>
                        </c:forEach>--%>
                            <shiro:hasPermission name="/agActivity/approvalPass">
                                <option value="${passDict.dItemvalue}">${passDict.dItemname}</option>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/agActivity/approvalReject">
                                <option value="${rejectDict.dItemvalue}">${rejectDict.dItemname}</option>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/agActivity/approvalCancel">
                                <option value="${cancelDict.dItemvalue}">${cancelDict.dItemname}</option>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/agActivity/approvalBack">
                                <option value="${backDict.dItemvalue}">${backDict.dItemname}</option>
                            </shiro:hasPermission>
                    </select>
                </shiro:hasPermission>
                <shiro:hasPermission name="/agActivity/approvalPassResult">
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
    function get_subApproval_FormDataItem() {
        var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.taskId  = $("input[name='taskId']").val();
        return data;
    }

</script>
