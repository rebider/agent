<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="查看信息">
        <%@ include file="/commons/queryAgentBase_model.jsp" %>
        <%@ include file="/commons/queryAttachment_model.jsp" %>
        <%@ include file="/commons/queryAgentContractTable_model.jsp" %>
        <%@ include file="/commons/queryAgentColinfoTable_model.jsp" %>
        <%@ include file="/commons/queryAgentBusi_model.jsp" %>
        <%@ include file="/commons/queryAgentcapital_model.jsp" %>
        <%@ include file="agentQuitDebt.jsp" %>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tbody>
                <tr>
                    <td width="80px">退出平台:</td>
                    <td>
                        <c:forEach items="${quitPlatformList}" var="quitPlatform"  >
                            <c:if test="${agentQuit.quitPlatform==quitPlatform.key}">
                                ${quitPlatform.value}
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <c:if test="${!empty agentQuit.attachments}">
                        <td>打款凭证：</td>
                        <c:forEach items="${agentQuit.attachments}" var="attachment">
                            <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                            <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                        </c:forEach>
                    </c:if>
                </tr>
                <tr>
                    <td >申请备注：</td>
                    <td colspan="9">${agentQuit.remark}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <%@ include file="/commons/order_cash_receivables_show.jsp"%>
        <shiro:hasPermission name="/agentQuit/quit_fiane_model">
        <%@ include file="/commons/order_cash_receivables_approve.jsp"%>
        </shiro:hasPermission>
        <div class="easyui-panel" title="信息填写" data-options="iconCls:'fi-results'">
            <form id="messageWrite">
            <table class="grid">
                <shiro:hasPermission name="/agentQuit/market_messageWrite">
                <tr>
                    <td>退款期限</td>
                    <td>
                        <select name="refundAmtDeadline" style="width:160px;height:21px" >
                            <c:forEach var="deadline" items="${deadlineList}">
                                <option value="${deadline.dItemvalue}">${deadline.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>迁移至：</td>
                    <td>
                        <select name="migrationPlatform" style="width:160px;height:21px" >
                            <c:forEach var="migrationPlatform" items="${migrationPlatforms}">
                                <option value="${migrationPlatform.dItemvalue}">${migrationPlatform.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                </shiro:hasPermission>
                <shiro:hasPermission name="/agentQuit/yuhua_messageWrite">
                <tr>
                    <td>减免金额</td>
                    <td>
                        <input name="subtractAmt" type="text" class="easyui-textbox"  value="0">
                    </td>
                </tr>
                </shiro:hasPermission>
                <shiro:hasPermission name="/agentQuit/fiane_messageWrite">
                <tr>
                    <td>是否可以申请退款</td>
                    <td>
                        <select name="appRefund" style="width:160px;height:21px" id="">
                            <c:forEach items="${yesOrNo}" var="yesOrNoItem">
                                <option value="${yesOrNoItem.dItemvalue}">${yesOrNoItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                </shiro:hasPermission>
            </table>
            </form>
        </div>
        <%@ include file="agentQuitApprovalOpinion.jsp" %>
        <c:if test="${sid=='sid-5C0AE792-7539-46D2-86BA-243B6A42D9FE'}">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'icon-mini-edit'" onclick="editAgentQuitApproval(2)">点击修改</a>
        </c:if>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'icon-ok'" onclick="submitAgentQuitApproval()">提交</a>
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
    function submitAgentQuitApproval() {

        var subApprovalTable = (typeof get_common_Approval_Form_quit === "function")?get_common_Approval_Form_quit():{};
        var oCashReceivablesVoList = (typeof getApproveCashPayListRealRecTime === "function")?getApproveCashPayListRealRecTime("${paylist_model}"):[];
        var agentQuit = $.serializeObject($("#messageWrite"));

        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/agentQuit/quitTaskApproval",
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion: subApprovalTable.approvalOpinion,
                        approvalResult: subApprovalTable.approvalResult,
                        taskId: subApprovalTable.taskId,
                        dept: subApprovalTable.dept,
                        agentBusId: "${busId}",
                        oCashReceivablesVoList:oCashReceivablesVoList,
                        sid:"${sid}",
                        agentQuit:agentQuit
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

    function editAgentQuitApproval(index) {
        addTab({
            title: '代理商退出-修改',
            border: false,
            closable: true,
            fit: true,
            href: '/agentQuit/toAgentQuitEdit?busId=${busId}&index='+index
        });
    }

</script>