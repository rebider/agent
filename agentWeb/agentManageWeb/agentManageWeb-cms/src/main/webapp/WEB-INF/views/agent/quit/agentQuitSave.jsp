<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agentQuit_AttFile_attrDom;
    //上传窗口
    function agentQuit_AttFile_uploadView(t) {
        agentQuit_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(agentQuit_AttFile__jxkxUploadFile);
    }
    //附件解析
    function agentQuit_AttFile__jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(agentQuit_AttFile_attrDom).append("<span onclick='removeFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='agentQuitFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function saveAgentQuit(saveFlag) {
        $("#flag").val(saveFlag);
        var payTempletList = JSON.stringify(getPayList('${paylist_model}'));
        $("#payTemplet").val(payTempletList);
        parent.$.messager.confirm('询问', '确认要保存数据？', function(b) {
            if (b) {
                $.ajax({
                    url: "/agentQuit/agentQuitSave",
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    data: $("#agentQuitFrom").serialize(),
                    dataType: 'json',
                    success: function(data) {
                        if (!data.success) {
                            info(data.msg);
                            return;
                        }
                        info(data.msg);
                        $('#index_tabs').tabs('close', "代理商退出-申请");
                        agentQuitList.datagrid('reload');
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        progressClose();
                    },
                    error: function(data) {
                        parent.$.messager.alert('错误','系统异常，请联系管理员！', 'error');
                    }
                });
            }
        });
    }
</script>
<div title="查看信息">
    <%@ include file="/commons/queryAgentBase_model.jsp" %>
    <%@ include file="/commons/queryAttachment_model.jsp" %>
    <%@ include file="/commons/queryAgentContractTable_model.jsp" %>
    <%@ include file="/commons/queryAgentColinfoTable_model.jsp" %>
    <%@ include file="/commons/queryAgentBusi_model.jsp" %>
    <%@ include file="/commons/queryAgentcapital_model.jsp" %>
</div>
<%@ include file="agentQuitDebt.jsp" %>
<form id="agentQuitFrom">
<input type="hidden" name="flag" id="flag">
<input type="hidden" name="payTemplet" id="payTemplet">
<input type="hidden" name="agentId" value="${agent.id}">
<div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tbody>
        <tr>
            <td width="80px">退出平台:</td>
            <td>
                <select name="quitPlatform" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto',required:true">
                    <c:forEach items="${quitPlatformList}" var="quitPlatform"  >
                        <option value="${quitPlatform.key}">${quitPlatform.value}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td width="80px">打款凭证:</td>
            <td>
                <a class="attrInput">
                </a>
                <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit"
                   data-options="plain:true,iconCls:'fi-magnifying-glass'"
                   onclick="agentQuit_AttFile_uploadView(this)">添加附件</a>
            </td>
        </tr>
        <tr>
            <td width="80px">申请备注</td>
            <td><input name="remark" type="text" class="easyui-validatebox"  style="width:500px;" /></td>
        </tr>
        </tbody>
    </table>
</div>
</form>
<%@ include file="/commons/order_cash_receivables.jsp"%>
<div style="text-align:right;padding:10px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 160px;" data-options="iconCls:'icon-ok'" onclick="saveAgentQuit(1)">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 160px;" data-options="iconCls:'icon-ok'" onclick="saveAgentQuit(2)">保存并审核</a>
</div>