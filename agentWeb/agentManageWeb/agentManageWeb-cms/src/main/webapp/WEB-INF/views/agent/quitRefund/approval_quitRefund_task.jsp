<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息">
        <%@ include file="approval_quitRefund_info.jsp" %>
        <%@ include file="/commons/queryAgentBase_model.jsp" %>
        <div class="easyui-panel" title="代理商退出-数据信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tr>
                    <td width="120px">申请退出平台：</td>
                    <td>
                        <c:forEach items="${quitPlatformList}" var="quitPlatform" >
                            <c:if test="${agentQuit.quitPlatform==quitPlatform.key}">
                                ${quitPlatform.value}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td width="120px">减免金额：</td>
                    <td>${agentQuit.subtractAmt}</td>
                    <td width="120px">手刷迁移平台：</td>
                    <td>
                        <c:forEach items="${migrationPlatforms}" var="migrationPlatformsItem">
                            <c:if test="${migrationPlatformsItem.dItemvalue == agentQuit.migrationPlatform}">${migrationPlatformsItem.dItemname}</c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <c:if test="${!empty agentQuit.attachments}">
                        <td width="120px">打款凭证：</td>
                        <c:forEach items="${agentQuit.attachments}" var="attachment">
                            <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                            <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                        </c:forEach>
                    </c:if>
                </tr>
                <tr>
                    <td width="120px">申请备注：</td>
                    <td colspan="9">${agentQuit.remark}</td>
                </tr>
            </table>
        </div>
        <%@ include file="/commons/queryAgentcapital_model.jsp" %>
        <%@ include file="../quit/agentQuitDebt.jsp" %>
        <%@ include file="/commons/order_cash_receivables_show.jsp"%>

        <shiro:hasPermission name="/quitRefund/quitRefund_finance_model">
            <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
                <table class="grid">
                    <tr>
                        <td width="100px"><font color="red">实际打款金额：</font></td>
                        <td><input id="realSuppDept" name="realitySuppDept" data-options="required:true"
                                   class="easyui-numberbox" type="text" style="line-height:17px;border:1px solid #ccc"></td>
                    </tr>
                </table>
                <form id="quitRefund_AttFile_attrDom_form">
                    <table class="grid">
                        <tr>
                            <td width="100px"><font color="red">上传打款凭证：</font></td>
                            <td colspan="2">
                                <a class="attrInput" colspan="4"></a>
                                <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit"
                                   data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="quitRefund_AttFile_uploadView(this)">添加附件</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </shiro:hasPermission>
        <%--审批意见--%>
        <%@ include file="approval_quitRefund_opinion.jsp" %>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'icon-ok'" onclick="submitQuitRefundApproval()">提交</a>
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
    var quitRefund_AttFile_attrDom;
    //上传窗口
    function quitRefund_AttFile_uploadView(t) {
        quitRefund_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(quitRefund_AttFile_jxkxUploadFile);
    }
    //附件解析
    function quitRefund_AttFile_jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(quitRefund_AttFile_attrDom).append("<span onclick='removeFile(this)'>" + jsonData[i].attName
                + "<input type='hidden' name='quitRefundFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }
    //删除附件
    function removeFile(t) {
        parent.$.messager.confirm('询问', '确定删除附件么？', function (b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    function submitQuitRefundApproval() {
        var subApprovalTable = (typeof get_common_approval_form_quitRefund === "function")?get_common_approval_form_quitRefund():{};
        var realitySuppDept = "";//实际打款金额
        var quitRefundFile = "";
        var uploadQuitRefundFiles = [];//上传打款凭证
        <shiro:hasPermission name="/quitRefund/quitRefund_finance_model">
        realitySuppDept = $("#realSuppDept").numberbox('getValue');
        var files =  $("#quitRefund_AttFile_attrDom_form").find("input[name='quitRefundFile']");
        for (var j = 0; j < files.length; j++) {
            uploadQuitRefundFiles.push($(files[j]).val());
        }
        </shiro:hasPermission>
        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/quitRefund/quitRefundTaskApproval",
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion: subApprovalTable.approvalOpinion,
                        approvalResult: subApprovalTable.approvalResult,
                        taskId: subApprovalTable.taskId,
                        dept: subApprovalTable.dept,
                        agentBusId: "${busId}",
                        realitySuppDept: realitySuppDept,
                        quitRefundFile: uploadQuitRefundFiles
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
</script>