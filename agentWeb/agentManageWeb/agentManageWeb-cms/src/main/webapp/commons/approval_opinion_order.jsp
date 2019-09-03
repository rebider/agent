<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tr >
            <td>审批意见</td>
            <td colspan="2">
                <input class="easyui-textbox" name="approvalOpinion" data-options="multiline:true" value="" style="width:300px;height:100px">
            </td>
        </tr>
        <tr>
            <td>审批结果</td>
            <td>
                <select name="approvalResult" style="width:160px;height:21px" >
                    <shiro:hasPermission name="/order/approvalPass">
                        <option value="${passDict.dItemvalue}">${passDict.dItemname}</option>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/order/approvalReject">
                        <option value="${rejectDict.dItemvalue}">${rejectDict.dItemname}</option>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/order/approvalBack">
                        <option value="${backDict.dItemvalue}">${backDict.dItemname}</option>
                    </shiro:hasPermission>
                </select>
            </td>
            <td>
                <shiro:hasPermission name="order_apr_busnissPermission">
                    <select name="dept" style="width:160px;height:21px" >
                        <c:forEach items="${order_apr_busniss}" var="order_apr_busnissItem"  >
                            <option value="${order_apr_busnissItem.dItemvalue}">${order_apr_busnissItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </shiro:hasPermission>
                <shiro:hasPermission name="order_apr_marketPermission">
                    <select name="dept" style="width:160px;height:21px" >
                        <c:forEach items="${order_apr_market}" var="order_apr_marketItem"  >
                            <option value="${order_apr_marketItem.dItemvalue}">${order_apr_marketItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </shiro:hasPermission>
            </td>
        </tr>
        <input type="hidden" name="taskId" value="${taskId}">
    </table>
</div>
<script>
    function get_subApproval_FormDataItem_order() {
        var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.taskId  = $("input[name='taskId']").val();
        data.dept  = $("select[name='dept']").length>0?$("select[name='dept']").val():"";
        return data;
    }
</script>
