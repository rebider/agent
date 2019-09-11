<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <shiro:hasPermission name="/thaw/approvalUpdate">
        <%@ include file="editThawInfo.jsp" %>
        </shiro:hasPermission>
        <shiro:hasPermission name="/thaw/approvalUpdateQuery">
            <%@ include file="thawInfo.jsp" %>
        </shiro:hasPermission>
        <shiro:hasPermission name="/thaw/approvalUpdate">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;align-content: center" onclick="editThaw()" >保存修改信息</a>
        </shiro:hasPermission>
        <%--审批意见--%>
        <%@ include file="approval_opinion.jsp" %>
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

    function editThaw(){
            var remark = $('#remark').val();
            if (remark == '' || remark == undefined) {
                return;
            }
            parent.$.messager.confirm('询问', '您确定对该代理商分润解冻编辑吗？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/monthProfit/editUnfreeze",
                        dataType:'json',
                        traditional:true,
                        contentType:"application/x-www-form-urlencoded",
                        data: $('#profitFreezeForm').serializeArray(),
                        beforeSend : function() {
                            progressLoad();
                        },
                        success: function(msg){
                            if (msg.success) {
                                alertMsg("编辑成功！");
                                $("#fileInput").text(fileName);
                                $("#delFile").remove();
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

            parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/toolsActivity/taskApproval",
                        dataType:'json',
                        contentType:'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            approvalOpinion:subApprovalTable.approvalOpinion,
                            approvalResult:subApprovalTable.approvalResult,
                            taskId:subApprovalTable.taskId,
                            orderAprDept:subApprovalTable.dept,
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
</script>
