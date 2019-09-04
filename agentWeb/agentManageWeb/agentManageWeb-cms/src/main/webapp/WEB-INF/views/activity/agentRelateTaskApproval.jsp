<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息">
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <form id="add_TerminalTransfer_model_form">
                <table class="grid">
                    <tbody>
                    <tr>
                        <td width="150px">代理商基本信息</td>
                        <td>
                            <div>
                                <hr>
                                <table>
                                    <tr>
                                        <td>代理商名称:</td><td>${agentRelate.agentName}</td>
                                        <td>代理商唯一码:</td><td>${agentRelate.agentId}</td>
                                        <td>业务类型:</td><td><input type="text" name="busPlatform" value="${agentRelate.busPlatform}"></td>
                                     </tr>
                                </table>
                                <span style="color: red;font-size: 13px;">提示：关联的子公司顺序为扣款的依次顺序</span>
                                    <table>
                                        <c:forEach  items="${detailList}" var="detail">
                                        <tr>
                                            <td>代理商名称:</td><td>${detail.agentName}</td>
                                            <td>代理商唯一码:</td><td>${detail.agentId}</td>
                                            <td>业务类型:</td><td><input type="text" name="busPlatform" value="${agentRelate.busPlatform}"></td>
                                        </tr>
                                        </c:forEach>
                                    </table>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            开始执行时间（分润归属月份）
                        </td>
                        <td>
                            <input type="text" value="${agentRelate.startMonth}">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <%--审批意见--%>
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
                                <option value="${passDict.dItemvalue}">${passDict.dItemname}</option>
                                <option value="${rejectDict.dItemvalue}">${rejectDict.dItemname}</option>
                        </select>
                    </td>
                </tr>
                <input type="hidden" name="taskId" value="${taskId}">
            </table>
        </div>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="submitAgentBusApproval()" >提交</a>
    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
    </shiro:hasPermission>
    <div title="审批流程图">
        <shiro:hasPermission name="/agActivity/approvalRecordImgSee">
            <img src="/agActivity/approvalImage?taskId=${taskId}" />
        </shiro:hasPermission>
    </div>
</div>
<script>
    $(function() {
        console.log('0-0');
        var busPlatform=$("[name='busPlatform']:first").val();
        for(var i=0;i< db_options.ablePlatForm.length;i++){
            if (db_options.ablePlatForm[i].platformNum == busPlatform) {
                var temp = db_options.ablePlatForm[i].platformName;
               /* if (temp.indexOf('手刷') >= 0) {
                    $("[name='busPlatform']").val('手刷');
                } else {*/
                $("[name='busPlatform']").val(temp);
                /*}*/
            }
        }
    });
    function relate_get_common_Approval_Form() {
        var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.taskId  = $("input[name='taskId']").val();
        return data;
    }
    function submitAgentBusApproval() {
        var subApprovalTable = (typeof relate_get_common_Approval_Form === "function")?relate_get_common_Approval_Form():{};
        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentRelate/agentRelateTaskApproval",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        agentBusId:"${busId}",
                        orderAprDept:subApprovalTable.dept,
                    }),
                    beforeSend:function(){
                        progressLoad();
                    },
                    success: function(msg){
                        info(msg.resInfo);
                        if(msg.resCode=='1'){
                            $('#index_tabs').tabs('close',"处理任务");
                            activityDataGrid.datagrid('reload');
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }
</script>

