<shiro:hasPermission name="/agActivity/approvalOpinion">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div class="easyui-panel" title="审批意见" data-options="iconCls:'fi-results'">
        <table class="grid">
            <tr>
                <td>审批意见</td>
                <td>
                    <input class="easyui-textbox" name="approvalOpinion" data-options="multiline:true" value="" style="width:300px;height:100px">
                </td>
            </tr>
            <tr>
                <td>审批结果</td>
                <td>
                    <select name="approvalResult" style="width:160px;height:21px">
                        <shiro:hasPermission name="/capitalChange/approvalPass">
                            <option value="${passDict.dItemvalue}">${passDict.dItemname}</option>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/capitalChange/approvalReject">
                            <option value="${rejectDict.dItemvalue}">${rejectDict.dItemname}</option>
                        </shiro:hasPermission>
                    </select>
                </td>
            </tr>
            <%--<shiro:hasPermission name="/capitalChange/market_approval_param">--%>
                <%--<tr>--%>
                    <%--<td>下级审批部门</td>--%>
                    <%--<td>--%>
                        <%--<select name="dept" style="width:160px;height:21px" >--%>
                            <%--<c:forEach items="${capitalMarket_param}" var="capitalMarket_paramItem">--%>
                                <%--<option value="${capitalMarket_paramItem.dItemvalue}">${capitalMarket_paramItem.dItemname}</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--</shiro:hasPermission>--%>
            <input type="hidden" name="taskId" value="${taskId}">
        </table>
    </div>
    <script>
        function get_common_Approval_Form_capital() {
            var data = {};
            data.approvalOpinion = $("input[name='approvalOpinion']").val();
            data.approvalResult  = $("select[name='approvalResult']").val();
            data.taskId = $("input[name='taskId']").val();
            data.dept = $("select[name='dept']").length>0?$("select[name='dept']").val():"";
            return data;
        }
    </script>
</shiro:hasPermission>