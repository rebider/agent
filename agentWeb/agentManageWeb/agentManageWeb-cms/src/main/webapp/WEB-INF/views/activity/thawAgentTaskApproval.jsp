<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2019/4/25
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息">
        <div class="easyui-panel" title="分润信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <shiro:hasPermission name="/agentFreeze/cityUpdateOperationAuthority"><%--省区权限--%>
                    <input type="hidden" value="${dept}" id="cityDept"><%--传userId到前端--%>
                </shiro:hasPermission>
                <tr>
                    <td>代理商唯一码</td>
                    <td>代理商名称</td>
                    <td>上级代理商唯一码</td>
                    <td>上级代理商名称</td>
                    <td>当前状态</td>
                    <td>分润类型</td>
                    <td>分润金额</td>
                    <td>申请时间</td>
                    <shiro:hasPermission name="/agentFreeze/updateOperationAuthority"><%--修改操作权限--%>
                        <td>操作</td>
                    </shiro:hasPermission>
                </tr>
                <c:forEach items="${freezeAgents}" var="freezeAgent">
                    <tr>
                        <td>${freezeAgent.agentId}</td>
                        <td>${freezeAgent.agentName}</td>
                        <td>${freezeAgent.parentAgentId}</td>
                        <td>${freezeAgent.parentAgentName}</td>
                        <td>已冻结</td>
                        <td><span name="freezeType">${freezeAgent.freezeType}</span></td>
                        <td>${freezeAgent.rev1}</td><%--查询时将冻结金额写入该字段--%>
                        <td>${freezeAgent.rev2}</td><%--查询的时候将申请时间写入该字段--%>
                        <shiro:hasPermission name="/agentFreeze/updateOperationAuthority">
                            <td>
                                <a href="javascript:void(0);" id="${freezeAgent.id}" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="deleteThawOperation('${freezeAgent.id}');">删除</a>
                            </td>
                        </shiro:hasPermission>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
                <table class="grid">
                    <tr>
                        <td>申请人</td>
                        <td>归属省区</td>
                        <td>申请时间</td>
                        <td>申请类型</td>
                        <td>原因</td>
                    </tr>
                    <tr>
                        <td>${thawOperator.user}</td>
                        <td>${thawOperator.city}</td>
                        <td>${thawOperator.time}</td>
                        <td><%--${thawOperator.type}--%>申请解冻</td>
                        <td>${thawOperator.reason}</td>
                    </tr>
                </table>
        </div>
        <div class="easyui-panel" title="审批" data-options="iconCls:'fi-results'">
            <form id="approvalForm" enctype="multipart/form-data">
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
                                <option value="pass">通过</option>
                                <shiro:lacksPermission name="/agentFreeze/updateOperationAuthority">
                                    <option value="back">退回</option>
                                    <option value="reject">拒绝</option>
                                </shiro:lacksPermission>
                                <shiro:hasPermission name="/agentFreeze/updateOperationAuthority">
                                    <option value="reject">退出</option>
                                </shiro:hasPermission>
                            </select>
                        </td>
                    </tr>
                    <input type="hidden" name="taskId" value="${taskId}">
                </table>
            </form>
        </div>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="submitThawAgent()" >提交</a>
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
    var freezeAgentIds=new Array();
    function thaw_get_common_Approval_Form() {
        var data = {};
        data.approvalOpinion = $("input[name='approvalOpinion']").val();
        data.approvalResult  = $("select[name='approvalResult']").val();
        data.taskId  = $("input[name='taskId']").val();
        data.citySupplyId = $("#citySupplyId").val();
        data.dept=$("#cityDept").length>0?$("#cityDept").val():"";//判断有没有省区的userId 有则取其值 无则取空
        data.freezeAgentIds=freezeAgentIds;
        return data;
    }
    function submitThawAgent() {
        var subApprovalTable = (typeof thaw_get_common_Approval_Form === "function")?thaw_get_common_Approval_Form():{};
        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentFreeze/thawTaskApproval",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        dept:subApprovalTable.dept,
                        agentBusId:"${busId}",
                        agentTableFile:freezeAgentIds    //将删除的解冻代理商传给后台
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
    function deleteThawOperation(freezeAgentId) {
        console.info(freezeAgentId);

        freezeAgentIds.push(freezeAgentId);//将freezeAgentId添加到删除列表

        /*删除后按钮应该失效*/
        $("#"+freezeAgentId).linkbutton('disable');

    }
</script>
