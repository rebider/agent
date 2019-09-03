<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="查看信息">
        <div class="easyui-panel" title="代理商信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <tbody>
                <tr>
                    <td width="200px">代理商编码</td>
                    <td width="200px">代理商名称</td>
                    <td width="200px">欠票</td>
                    <td width="200px">欠款</td>
                </tr>
                <tr>
                    <td width="200px">${capitalChangeApply.agentId}</td>
                    <td width="200px">${capitalChangeApply.agentName}</td>
                    <td width="200px">${subAgentOweTicket}</td>
                    <td width="200px">${sumDebt}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <%@ include file="capitalChangeInfo.jsp" %>
        <c:if test="${capitalChangeApply.operationType==2}">
            <%@ include file="capitalChangeReceipt.jsp" %>
        </c:if>
        <%--<%@ include file="/commons/order_cash_receivables_show.jsp"%>--%>
        <%--<shiro:hasPermission name="/capitalChange/capital_fiane_model">--%>
            <%--<%@ include file="/commons/order_cash_receivables_approve.jsp"%>--%>
        <%--</shiro:hasPermission>--%>
        <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
            <table class="grid">
                <%--<tr>--%>
                    <%--<td width="120px">打款凭证：</td>--%>
                    <%--<c:forEach items="${capitalChangeApply.attachments}" var="attachment">--%>
                        <%--<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>--%>
                        <%--<td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank">查看附件</a></td>--%>
                    <%--</c:forEach>--%>
                <%--</tr>--%>
                <tr>
                    <td width="120px">申请备注：</td>
                    <td colspan="9">${capitalChangeApply.remark}</td>
                </tr>
            </table>
        </div>
        <shiro:hasPermission name="/capitalChange/capital_fiane_model">
        <div class="easyui-panel" title="财务上传打款截图" data-options="iconCls:'fi-results'">
            <table class="grid" id="cwdk">
                <tbody>
                <tr>
                    <td width="80px">打款时间：</td>
                    <td width="100px">
                        <input name="remitTimeStr"   style="line-height:17px;border:1px solid #ccc;width: 150px;" class="easyui-datetimebox"/>
                    </td>
                    <td width="80px">打款人：</td>
                    <td width="100px">
                        <input name="remitPerson"   style="line-height:17px;border:1px solid #ccc;width: 150px;" class="easyui-validatebox"/>
                    </td>
                    <td width="80px">打款金额：</td>
                    <td>
                        <input name="remitAmt"   style="line-height:17px;border:1px solid #ccc;width: 150px;" class="easyui-validatebox"/>
                    </td>
                </tr>
                <tr>
                    <td width="80px">打款凭证：</td>
                    <td>
                        <a class="attrInput"></a>
                        <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'"
                           onclick="capitalFina_AttFile_uploadView(this)">添加附件</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        </shiro:hasPermission>
        <%@ include file="capitalChangeApprovalOpinion.jsp" %>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;"
           data-options="iconCls:'icon-ok'" onclick="submitCapitalChangeApproval()">提交</a>
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
    function submitCapitalChangeApproval() {

        var subApprovalTable = (typeof get_common_Approval_Form_capital === "function")?get_common_Approval_Form_capital():{};
        var oCashReceivablesVoList = (typeof getApproveCashPayListRealRecTime === "function")?getApproveCashPayListRealRecTime("${paylist_model}"):[];

        var files =  $("#cwdk").find(".attrInput").find("input[name='capitalChangeFinaFiles']");
        var capitalFinaFileTemp = [];
        for(var j=0;j<files.length;j++){
            capitalFinaFileTemp.push($(files[j]).val());
        }
        var remitTimeStr = $("input[name='remitTimeStr']").val();
        var remitPerson = $("input[name='remitPerson']").val();
        var remitAmt = $("input[name='remitAmt']").val();

        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/capitalChange/capitalChangeTaskApproval",
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion: subApprovalTable.approvalOpinion,
                        approvalResult: subApprovalTable.approvalResult,
                        taskId: subApprovalTable.taskId,
                        dept: subApprovalTable.dept,
                        agentBusId: "${busId}",
                        oCashReceivablesVoList: oCashReceivablesVoList,
                        sid: "${sid}",
                        capitalChangeFinaFiles:capitalFinaFileTemp,
                        remitTimeStr:remitTimeStr,
                        remitPerson:remitPerson,
                        remitAmt:remitAmt
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


    var capitalFina_AttFile_attrDom;
    //上传窗口
    function capitalFina_AttFile_uploadView(t) {
        capitalFina_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(capitalFina_AttFile__jxkxUploadFile);
    }
    //附件解析
    function capitalFina_AttFile__jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(capitalFina_AttFile_attrDom).append("<span onclick='removeFile(this)'>" + jsonData[i].attName
                + "<input type='hidden' name='capitalChangeFinaFiles' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
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

</script>