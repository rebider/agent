<shiro:hasPermission name="/agActivity/approvalOpinion">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'">
    <table class="grid">
        <shiro:hasPermission name="yes_approval_opinion">
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
                        <c:forEach items="${approvalType}" var="appTypeItem"  >${approvalType}
                            <c:if test="${appTypeItem.dItemvalue != 'reject'}">
                              <option value="${appTypeItem.dItemvalue}">${appTypeItem.dItemname}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </shiro:hasPermission>
        <shiro:hasPermission name="not_approval_opinion">
            <select name="approvalResult" style="width:160px;height:21px;display: none" >
                <option value="pass">通过</option>
            </select>
        </shiro:hasPermission>
        <shiro:hasPermission name="tools_apr_busnissPermission">
            <tr>
                <td>下级审批部门</td>
                <td>
                    <select name="dept" style="width:160px;height:21px" >
                        <c:forEach items="${POS_APR_BUSNISS}" var="tools_apr_busnissItem"  >
                            <option value="${tools_apr_busnissItem.dItemvalue}">${tools_apr_busnissItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </shiro:hasPermission>
        <input type="hidden" name="taskId" value="${taskId}">
    </table>
</div>
<script>
    function get_subApproval_FormDataItem() {
        var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.taskId  = $("input[name='taskId']").val();
        data.dept  = $("select[name='dept']").length>0?$("select[name='dept']").val():"";
        return data;
    }
</script>
</shiro:hasPermission>