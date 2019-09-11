<shiro:hasPermission name="/agActivity/approvalOpinion">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div class="easyui-panel" title="审批意见" data-options="iconCls:'fi-results'">
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
                        <shiro:hasPermission name="/agentQuit/approvalPass">
                            <option value="${passDict.dItemvalue}">${passDict.dItemname}</option>
                        </shiro:hasPermission>
                        <%--<shiro:hasPermission name="/agentQuit/approvalReject">--%>
                        <c:if test="${sid=='sid-5C0AE792-7539-46D2-86BA-243B6A42D9FE'}">
                            <option value="${rejectDict.dItemvalue}">${rejectDict.dItemname}</option>
                        </c:if>
                        <%--</shiro:hasPermission>--%>
                        <shiro:hasPermission name="/agentQuit/approvalBack">
                            <option value="${backDict.dItemvalue}">${backDict.dItemname}</option>
                        </shiro:hasPermission>
                    </select>
                </td>
            </tr>
            <shiro:hasPermission name="/agentQuit/quit_approval_param">
                <tr>
                    <td>下级审批部门</td>
                    <td>
                        <select name="dept" style="width:160px;height:21px" >
                            <c:forEach items="${quit_param}" var="merge_paramItem">
                                <option value="${merge_paramItem.dItemvalue}">${merge_paramItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </shiro:hasPermission>
            <input type="hidden" name="taskId" value="${taskId}">
        </table>
    </div>
    <script>
        function get_common_Approval_Form_quit() {
            var data = {};
            data.approvalOpinion = $("input[name='approvalOpinion']").val();
            data.approvalResult  = $("select[name='approvalResult']").val();
            data.taskId = $("input[name='taskId']").val();
            data.dept = $("select[name='dept']").length>0?$("select[name='dept']").val():"";
            return data;
        }
    </script>
</shiro:hasPermission>