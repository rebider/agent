<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <shiro:hasPermission name="/thaw/approvalUpdate">
            <%@ include file="editStagInfo.jsp" %>
        </shiro:hasPermission>
        <shiro:hasPermission name="/thaw/approvalUpdateQuery">
            <%@ include file="stagInfo.jsp" %>
            <%--审批意见--%>
        </shiro:hasPermission>
        <shiro:hasPermission name="/thaw/approvalUpdate">
            <div style="display: none">
        </shiro:hasPermission>
        <%@ include file="approval_opinion.jsp" %>
        <shiro:hasPermission name="/thaw/approvalUpdate">
            </div>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="editStaging()" >保存</a>
        </shiro:hasPermission>
           <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="submitApproval()">提交</a>
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

    function editStaging(){
        parent.$.messager.confirm('询问', '您确定对分期进行调整吗？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/profit/staging/edit",
                    dataType:'json',
                    traditional:true,
                    contentType:"application/x-www-form-urlencoded",
                    data: $('#stagingForm').serializeArray(),
                    beforeSend : function() {
                        progressLoad();
                    },
                    success: function(msg){
                        if (msg.success) {
                            alertMsg("编辑成功！");
                        }else {
                            alertMsg("系统异常，请联系运维人员！");
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }

    function submitApproval() {
        var addAgentAccountTable = (typeof get_addAgentAccountTable_FormDataItem=== "function")?get_addAgentAccountTable_FormDataItem():[];
        var subApprovalTable = (typeof get_subApproval_FormDataItem=== "function")?get_subApproval_FormDataItem():[];
        var payCompanyTable = (typeof get_payCompanyTable_FormDataItem=== "function")?get_payCompanyTable_FormDataItem():[];
        var busEditTable = (typeof get_busEditTable_FormDataItem=== "function")?get_busEditTable_FormDataItem():[];


        var busInfoVoList = [];
        if(payCompanyTable!=''){
            busInfoVoList = payCompanyTable;
        }else if(busEditTable!=''){
            busInfoVoList = busEditTable;
        }
        var subFlag = false;
        if(payCompanyTable!=''){
            $.each( payCompanyTable, function(index, content) {
                if(content.cloPayCompany==''){
                    info("请分配打款公司！");
                    return false;
                }
                subFlag = true;
            });
        }else{
            subFlag = true;
        }
        if(subFlag){
            parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/toolsActivity/taskApproval",
                        dataType:'json',
                        contentType:'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            agentColinfoRelList:addAgentAccountTable,
                            busInfoVoList:busInfoVoList,
                            approvalOpinion:subApprovalTable.approvalOpinion,
                            approvalResult:subApprovalTable.approvalResult,
                            orderAprDept:subApprovalTable.dept,
                            taskId:subApprovalTable.taskId
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
    }

    function refreshTabView() {
        refreshTab();
    }
</script>
