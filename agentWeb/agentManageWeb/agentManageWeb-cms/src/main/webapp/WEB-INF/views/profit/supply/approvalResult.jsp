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
                    <!--其他审批人-->
                    <shiro:hasPermission name="/agActivity/approval_oa">
                        <select name="approvalResult" style="width:160px;height:21px" >
                            <c:forEach items="${approvalType}" var="appTypeItem"  >${approvalType}
                                <c:if test="${appTypeItem.dItemvalue  !='reject'}">
                                    <option value="${appTypeItem.dItemvalue}">${appTypeItem.dItemname}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                       <%-- <select name="approvalResult" style="width:160px;height:21px">
                            <option value="pass">通过</option>
                            <option value="back">驳回</option>
                                &lt;%&ndash;<option value="reject">拒绝</option>&ndash;%&gt;
                        </select>--%>
                    </shiro:hasPermission>
                    <!--驳回修改---省区可提交或者撤销申请 -->
                    <shiro:hasPermission name="/agActivity/approvalOpinion_city">
                        <select name="approvalResult" style="width:160px;height:21px" >
                            <c:forEach items="${approvalType}" var="appTypeItem"  >${approvalType}
                                <c:if test="${appTypeItem.dItemvalue  !='back'}">
                                    <option value="${appTypeItem.dItemvalue}">${appTypeItem.dItemname}</option>
                                </c:if>
                            </c:forEach>
                        </select>

                       <%-- <select name="approvalResult" style="width:160px;height:21px">
                                &lt;%&ndash;
                                <option value="back">驳回</option>&ndash;%&gt;
                            <option value="pass">通过</option>
                            <option value="reject">撤销</option>
                        </select>--%>
                    </shiro:hasPermission>
                </td>
            </tr>
            <shiro:hasPermission name="/profit/otherDeduction/apply">
                <tr>
                    <td>下级审批部门</td>
                    <td>
                        <select name="dept" style="width:160px;height:21px" >
                            <c:forEach items="${tools_apr_busniss}" var="tools_apr_busnissItem"  >
                                <option value="${tools_apr_busnissItem.dItemvalue}">${tools_apr_busnissItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </shiro:hasPermission>
            <input type="hidden" name="taskId" value="${taskId}">
            <input type="hidden" name="id" value="${id}">
        </table>
    </div>
    <script>
        function get_subApproval_FormDataItem() {
            var data = {};
            data.approvalOpinion = $("input[name='approvalOpinion']").val();
            data.approvalResult  = $("select[name='approvalResult']").val();
            data.id = $("input[name='id']").val();
            data.taskId  = $("input[name='taskId']").val();
            data.dept  = $("select[name='dept']").length>0?$("select[name='dept']").val():"";
            return data;
        }
    </script>
</shiro:hasPermission>
