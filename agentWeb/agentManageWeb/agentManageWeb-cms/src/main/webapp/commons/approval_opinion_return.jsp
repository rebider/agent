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
        <tr>
            <td>审批结果</td>
            <td>
                <shiro:hasPermission name="/return/approvalAllResult">
                    <select name="approvalResult" style="width:160px;height:21px" >
                        <c:forEach items="${approvalType}" var="appTypeItem"  >
                            <option value="${appTypeItem.dItemvalue}">${appTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </shiro:hasPermission>
                <shiro:hasPermission name="/return/approvalPassResult">
                    <select name="approvalResult" style="width:160px;height:21px" >
                        <c:forEach items="${approvalPassType}" var="appPassTypeItem"  >
                            <option value="${appPassTypeItem.dItemvalue}">${appPassTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </shiro:hasPermission>
            </td>
        </tr>
        <c:if test="${sid=='sid-C911F512-9E63-44CC-9E6E-763484FA0E5B'}">
            <tr>
                <td>下级审批部门</td>
                <td>
                    <select name="dept" style="width:160px;height:21px" >
                        <c:forEach items="${orderReturn_param}" var="orderReturn_paramItem">
                            <option value="${orderReturn_paramItem.dItemvalue}">${orderReturn_paramItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </c:if>
        <tr>
            <td></td>
            <td>
                <c:if test="${sid=='sid-5C0AE792-7539-46D2-86BA-243B6A42D9FE'}">
                    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;margin: 20px 20px 20px 0px" onclick="editReturnOrder()">修改</a>
                </c:if>
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;margin: 20px 0px 20px 0px" data-options="iconCls:'fi-save'"
                   onclick="submitApprovalReturn()">提交</a>
            </td>

        </tr>
        <input type="hidden" name="taskId" value="${taskId}">
    </table>
</div>
<script>
    function get_subApproval_FormDataItem_return() {
        var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.taskId  = $("input[name='taskId']").val();
        data.dept  = $("select[name='dept']").length>0?$("select[name='dept']").val():"";
        return data;
    }
</script>
