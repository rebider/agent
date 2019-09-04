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
                    <shiro:hasPermission name="/agActivity/approvalAllResult">
                        <select name="approvalResult" style="width:160px;height:21px" >
                                <option value="pass">通过</option>
                                <option value="reject">拒绝</option>
                        </select>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/agActivity/approvalPassResult">
                        <select name="approvalResult" style="width:160px;height:21px" >
                            <option value="pass">通过</option>
                            <option value="reject">拒绝</option>
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
</shiro:hasPermission>