<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" isELIgnored="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="agent" uri="/WEB-INF/tld/agent.tld" %>
<%@ taglib prefix="desensitization" uri="/WEB-INF/tld/desensitization.tld" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<c:set var="path" value="${ctxPath}" />
<c:set var="staticPath" value="${ctxPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

    function submitApproval() {

        //审批信息
        var subApprovalTable = (typeof get_subApproval_FormDataItem=== "function")?get_subApproval_FormDataItem():[];
        var  id1 = '${templateRecode.id}';

        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/profit/template/doTaskApproval",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        sid:subApprovalTable.id,
                        orderAprDept:subApprovalTable.dept,
                        pretest:id1
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

    function refreshTabView() {
        refreshTab();
    }
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    function editInfo(){
        var editData = (typeof setData_form_AgentInfo === "function")?setData_form_AgentInfo():[];
        parent.$.messager.confirm('询问', '您确定申请信息修改完毕吗？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: '${path}/profit/template/updateTemplateName',
                    dataType: 'json',
                    data:editData,
                    success: function(data){
                        if (data.resCode == '1') {
                            alertMsg(data.obj);
                        }else{
                            alertMsg(data.resInfo);
                        }
                    }
                });
            }
        });
    }


</script>

<div class="easyui-tabs" id="taskAppTabs">

        <div title="待审信息">
            <c:if test="${resuuult != null}">
                <span style="font-size: 24px;color: red;">${resuuult}</span>
            </c:if>
            <c:if test="${resuuult == null}">
                <shiro:hasPermission name="/templateApply/daQuAndBusinessAndYuhua">
                    <%@ include file="templateApplyInfo.jsp" %>
                </shiro:hasPermission>
                <shiro:hasPermission name="/templateApply/andCity">
                    <%@ include file="editInfo.jsp" %>
                </shiro:hasPermission>
                <%@ include file="approvalResult.jsp" %>
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="submitApproval()">提交</a>
                <shiro:hasPermission name="/templateApply/andCity">
                    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="editInfo()">保存</a>
                </shiro:hasPermission>
            </c:if>
        </div>
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
        <div title="审批流程图">
            <img src="/agActivity/approvalImage?taskId=${taskId}" />
        </div>

</div>