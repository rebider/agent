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
                    <shiro:hasPermission name="/templateApply/daQuAndBusinessAndYuhua">
                        <select name="approvalResult" style="width:160px;height:21px">
                            <option value="pass">通过</option>
                            <option value="back">驳回</option>
                        </select>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/templateApply/andCity">
                        <select name="approvalResult" style="width:160px;height:21px">
                            <option value="pass">通过</option>
                            <option value="reject">撤销</option>
                        </select>
                    </shiro:hasPermission>
                </td>
            </tr>
            <shiro:hasPermission name="/templateApply/andBussiness">
                <tr>
                    <td>下级审批部门</td>
                    <td>
                        <select name="dept" style="width:160px;height:21px" >
                           <option value="">无</option>
                           <option value="yuhua">于华</option>
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
            data.taskId  = $("input[name='taskId']").val();
            data.id = $("input[name='id']").val();
            data.dept  = $("select[name='dept']").length>0?$("select[name='dept']").val():"";
            return data;
        }
    </script>
