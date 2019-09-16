<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息">
        <%@ include file="approval_merge_info.jsp" %>
        <%@ include file="approval_mergeBusi_info.jsp" %>
        <shiro:hasPermission name="/agentMerge/merge_mergeType_model">
                <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
                    <form id="agentMergeFrom_model">
                        <table class="grid">
                            <tbody>
                            <tr>
                                <td style="width:160px;"><font color="red">合并类型：</font></td>
                                <td>
                                    <select name="mergeType" id="mergeType" style="width:180px;" class="easyui-combobox" data-options="required:true">
                                        <c:forEach var="mergeType" items="${mergeType}">
                                            <option value="${mergeType.key}">${mergeType.value}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
        </shiro:hasPermission>
        <%@ include file="/commons/order_cash_receivables_show.jsp"%>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tr>
                    <td >申请备注：</td>
                    <td colspan="9">${agentMerge.remark}</td>
                </tr>
                <tr>
                    <c:if test="${!empty agentMerge.attachments}">
                        <td>打款凭证：</td>
                        <c:forEach items="${agentMerge.attachments}" var="attachment">
                            <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                            <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                        </c:forEach>
                    </c:if>
                </tr>
            </table>
        </div>
        <shiro:hasPermission name="/agentMerge/merge_finance_model">
        <%@ include file="/commons/order_cash_receivables_approve.jsp"%>
        </shiro:hasPermission>
        <%--审批意见--%>
        <%@ include file="approval_opinion_common.jsp" %>
        <shiro:hasPermission name="/agentMerge/merge_edit_model">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'icon-mini-edit'" onclick="editAgentMergeApproval()">点击修改</a>
        </shiro:hasPermission>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'icon-ok'" onclick="submitAgentMergeApproval()">提交</a>
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
    function submitAgentMergeApproval() {
        var mergeType = "";
        <shiro:hasPermission name="/agentMerge/merge_mergeType_model">
            mergeType = $("#mergeType").combobox("getValue");
        </shiro:hasPermission>
        var subApprovalTable = (typeof get_common_Approval_Form_merge === "function")?get_common_Approval_Form_merge():{};
        var oCashReceivablesVoList = (typeof getApproveCashPayListRealRecTime === "function")?getApproveCashPayListRealRecTime("${paylist_model}"):[];

        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentMerge/mergeTaskApproval",
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion: subApprovalTable.approvalOpinion,
                        approvalResult: subApprovalTable.approvalResult,
                        taskId: subApprovalTable.taskId,
                        dept: subApprovalTable.dept,
                        agentBusId: "${busId}",
                        mergeType: mergeType,
                        oCashReceivablesVoList:oCashReceivablesVoList,
                        sid:"${sid}"
                    }),
                    beforeSend: function() {
                        progressLoad();
                    },
                    success: function(msg) {
                        info(msg.resInfo);
                        if (msg.resCode=='1') {
                            $('#index_tabs').tabs('close', "处理任务");
                            activityDataGrid.datagrid('reload');
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }

    function editAgentMergeApproval() {
        addTab({
            title: '代理商合并-修改',
            border: false,
            closable: true,
            fit: true,
            href: '/agentMerge/toAgentMergeEdit?busId=${busId}&proIns=${proIns}'
        });
    }
</script>