<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="查看-修改">
        <%@ include file="agentBaseInfoQuery.jsp" %>
        <%@ include file="/commons/editAgentContractTable_model.jsp" %>
    </div>
</div>
<script type="text/javascript">
    function editAgentContractInfo() {
        var queryAgentTable = get_queryAgentBasics_FormData();
        var editAgentContractTable = (typeof get_editAgentContractTable_FormData==="function")?get_editAgentContractTable_FormData():[];
        var queryAgentBase = $("#queryAgentBasics_model_form").form('validate');
        var editAgentContract = $("#editAgentContract_model_form").form('validate');
        if (queryAgentBase && editAgentContract) {
            parent.$.messager.confirm('询问', '确认要修改？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/agentContract/agentConEdit",
                        dataType: 'json',
                        contentType: 'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            agent: queryAgentTable,
                            contractVoList: editAgentContractTable
                        }),
                        beforeSend: function() {
                            progressLoad();
                        },
                        success: function(msg) {
                            if (msg.resCode && msg.resCode=='1') {
                                info(msg.resInfo);
                                if (typeof refreshTabView=='function') {
                                    try {
                                        refreshTabView(queryAgentTable.id);
                                    } catch (e) {

                                    }
                                }
                                $('#index_tabs').tabs('close', "编辑-合同数据");
                                agnet_contract_dataGrid.datagrid('reload');
                            } else {
                                info(msg.resInfo);
                            }
                        },
                        complete: function(XMLHttpRequest, textStatus) {
                            progressClose();
                        },
                        error: function(data) {
                            parent.$.messager.alert('错误', '系统异常，请联系管理员！', 'error');
                        }
                    });
                }
            });
        }else {
            info("请输入必填项！");
        }
    }
</script>
<div style="text-align:right;padding:10px;margin-bottom: 50px;">
    <shiro:hasPermission name="/agentContract/agentConEdit">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="editAgentContractInfo()">保存</a>
    </shiro:hasPermission>
</div>