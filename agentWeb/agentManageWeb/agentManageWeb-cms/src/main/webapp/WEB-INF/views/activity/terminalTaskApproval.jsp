<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息">
        <shiro:hasPermission name="/terminal/queryTerminalTransfer_model">
            <%@ include file="/commons/queryTerminalTransfer_model.jsp" %>
        </shiro:hasPermission>
        <shiro:hasPermission name="/terminal/queryTerminalTransfer_bus_model">
            <%@ include file="/commons/queryTerminalTransfer_bus_model.jsp" %>
        </shiro:hasPermission>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <form id="add_TerminalTransfer_model_form">
                <table class="grid">
                    <tbody>
                    <tr>
                        <td width="80px">附件</td>
                        <td>
                            <c:if test="${!empty terminalTransfer.attachments}">
                                <c:forEach items="${terminalTransfer.attachments}" var="attachment">
                                     <span>
                                         <a>${attachment.attName}</a>
                                         <a href="<%=imgPath%>${attachment.attDbpath}" target="_blank" >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                     </span>
                                </c:forEach>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td width="80px">申请备注</td>
                        <td>${terminalTransfer.remark}</td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <%--审批意见--%>
        <%@ include file="/commons/approval_opinion_common.jsp" %>
        <shiro:hasPermission name="/terminal/TerminalTransfer_edit_model">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-rss'"  onclick="editAgentBusApproval()" >修改</a>
        </shiro:hasPermission>
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

    function submitAgentBusApproval() {
        //获取所有的选中复选框中的值
            var obj = document.getElementsByName("isNo");
            var check_val = [];
            for(k in obj){
                if(obj[k].checked)
                    check_val.push(obj[k].value);
            }
            console.info(check_val);

        var subApprovalTable = (typeof get_common_Approval_Form === "function")?get_common_Approval_Form():{};
        var terminalTransfer = (typeof terminal_Transfer_bus_model === "function")?terminal_Transfer_bus_model():"";

        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/terminal/terminalTaskApproval",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        sid:terminalTransfer!=""?"cw":"",   //财务审批
                        agentBusId:"${busId}",
                        terminalTransferDetailID:check_val
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

    function editAgentBusApproval() {
        addTab({
            title: '终端划拨-修改',
            border: false,
            closable: true,
            fit: true,
            href: '/terminal/toTerminalEdit?busId=${busId}'
        });
    }

</script>
