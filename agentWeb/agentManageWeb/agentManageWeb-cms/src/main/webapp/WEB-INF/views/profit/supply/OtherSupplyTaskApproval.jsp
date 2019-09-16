<%--标签 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" isELIgnored="false" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="agent" uri="/WEB-INF/tld/agent.tld" %>
<%@ taglib prefix="desensitization" uri="/WEB-INF/tld/desensitization.tld" %>
<%--ctxPath--%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%--项目路径 --%>
<c:set var="path" value="${ctxPath}" />
<%--静态文件目录 --%>
<c:set var="staticPath" value="${ctxPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script>
    function submitApproval() {
        var subApprovalTable = (typeof get_subApproval_FormDataItem=== "function")?get_subApproval_FormDataItem():[];
        var busEditTable = (typeof get_busEditTable_FormDataItem=== "function")?get_busEditTable_FormDataItem():[];
        var addAgentAccountTable = (typeof get_addAgentAccountTable_FormDataItem=== "function")?get_addAgentAccountTable_FormDataItem():[];

        var busInfoVoList = [];
        if(busEditTable!=''){
            busInfoVoList = busEditTable;
        }
        console.log(subApprovalTable.approvalOpinion);
        console.log(subApprovalTable.approvalResult);
        console.log(subApprovalTable.taskId);
        console.log(subApprovalTable.dept);
        console.log(subApprovalTable.addAgentAccountTable);
        console.log(subApprovalTable.busInfoVoList);
        console.log(subApprovalTable.id);

        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/profit/application/doTaskApproval",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        orderAprDept:subApprovalTable.dept,
                        sid:subApprovalTable.id,
                        agentColinfoRelList:addAgentAccountTable,
                        busInfoVoList:busInfoVoList
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

    /**
     *驳回-----修改信息  ///get_FormData
     */
    function editInfo(){
        //var editData = (typeof get_FormData=== "function")?get_FormData():[];
        parent.$.messager.confirm('询问', '您确定申请信息填写完毕吗？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/profit/application/editOtherDeductionInfo",
                    dataType:'json',
                    traditional:true,
                    contentType:"application/x-www-form-urlencoded",
                    data: $('#otherSupplyEditFrom').serializeArray(),
                    beforeSend : function() {
                        progressLoad();
                    },
                    success: function(data){
                        if (data.success) {
                            info("其他补款申请信息修改成功！");
                        }else {
                            info(data.msg);
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
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <!--核对信息-->
        <%--<shiro:hasPermission name="/other/otherDedudcApprovalPage">--%>
        <%@ include file="OtherSupplyInfo.jsp" %>
        <%--</shiro:hasPermission>--%>
        <!--修改信息-->
        <shiro:hasPermission name="/other/EditOtherDeducInfo">
            <%@ include file="EditOtherSupplyInfo.jsp" %>
        </shiro:hasPermission>
        <!--审批意见-->
        <%@ include file="approvalResult.jsp" %>
        <!--提交-->
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="submitApproval()">提交</a>
        <shiro:hasPermission name="/other/EditOtherDeducInfo">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="editInfo()">保存</a>
        </shiro:hasPermission>
    </div>
    <%--<shiro:hasPermission name="/agActivity/approvalRecordSee">--%>
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    <%--</shiro:hasPermission>--%>
    <div title="审批流程图">
        <%--<shiro:hasPermission name="/agActivity/approvalRecordImgSee">--%>
        <img src="/agActivity/approvalImage?taskId=${taskId}" />
        <%--</shiro:hasPermission>&ndash;%&gt;--%>
    </div>
</div>

