<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agentQuitUpload_AttFile_attrDom;
    //上传窗口
    function agentQuitUpload_AttFile_uploadView(t) {
        agentQuitUpload_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(agentQuit_AttFile_AQRTC_UploadFile);
    }
    //附件解析
    function agentQuit_AttFile_AQRTC_UploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(agentQuitUpload_AttFile_attrDom).append("<span onclick='removeQuitUploadFile(this)'>" + jsonData[i].attName
                + "<input type='hidden' name='agentQuitFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }
    //删除合同
    function removeQuitUploadFile(t) {
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).parent().remove();
            }
        });
    }

    function agentQuitUpRtc() {
        parent.$.messager.confirm('询问', '确认要保存数据？', function(b) {
            if (b) {
                $.ajax({
                    url: "/agentQuit/agentQuitUploadRtc",
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    data: $("#agentQuitUploadFrom").serialize(),
                    dataType: 'json',
                    success: function(data) {
                        if (!data.success) {
                            info(data.msg);
                            return;
                        }
                        info(data.msg);
                        $('#index_tabs').tabs('close', "代理商退出-上传解除合同");
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
<form id="agentQuitUploadFrom">
    <input type="hidden" class="easyui-textbox" id="id" name="id" value="${agentQuit.id}">
    <div class="easyui-panel" title="解除合同信息" data-options="iconCls:'fi-results'">
        <table class="grid">
            <tbody>
            <tr>
                <td width="120px">解除合同：</td>
                <td colspan="5">
                    <span class="attrInput">
                        <c:if test="${!empty attachments}">
                            <c:forEach items="${attachments}" var="attachment">
                                 <span>
                                     <a onclick='removeQuitUploadFile(this)'>${attachment.attName}</a>
                                     <input type='hidden' name='agentQuitFile' value='${attachment.id}' />
                                     <a href="<%=imgPath%>${attachment.attDbpath}" target="_blank" >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                 </span>
                            </c:forEach>
                        </c:if>
                    </span>
                    <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" style="margin-left: 20px"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="agentQuitUpload_AttFile_uploadView(this)">添加附件</a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
<div style="text-align:right;padding:10px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 160px;" data-options="iconCls:'icon-ok'" onclick="agentQuitUpRtc()">保存</a>
</div>