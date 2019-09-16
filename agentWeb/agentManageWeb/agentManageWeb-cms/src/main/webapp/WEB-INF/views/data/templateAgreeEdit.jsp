<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    function saveAgreement() {
        var agentName = $('#agreName').val();
        if (agentName == '' || agentName == undefined) {
            alertMsg("请填写协议名称！");
            return;
        }
        var agreVersion = $('#agreVersion').val();
        if (agreVersion == '' || agreVersion == undefined) {
            alertMsg("请填写协议版本！");
            return;
        }
        var agreType = $('#agreType').val();
        if (agreType == '' || agreType == undefined) {
            alertMsg("请选择协议类型！");
            return;
        }

        parent.$.messager.confirm('询问', '确认要修改？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/tempAgreement/edit",
                    dataType:'json',
                    traditional:true,
                    contentType:"application/x-www-form-urlencoded",
                    data: $('#templateEditForm').serializeArray(),
                    success: function(msg){
                        if (msg.success) {
                            alertMsg("修改成功！");
                            $("#index_tabs").tabs('close','修改协议模板');
                            tempAgreeDataGrid.datagrid('reload');
                        }else {
                            alertMsg("修改异常！");
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }

    function removeAgentColinfoTable_model(t){
        $(t).parent().parent().parent().remove();
    }

    var addAgentColinfoTable_attrDom ;

    //上传窗口
    function addAgentColinfoTable_uploadView(t){
        addAgentColinfoTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addAgentColinfoTable_jxkxUploadFile);
    }

    //附件解析
    function addAgentColinfoTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<jsondata.length ;i++){
            $(addAgentColinfoTable_attrDom).append("<span onclick='removeAgentColinfoTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='hidden' name='attrid' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }
    function removeAgentColinfoTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    $("#agreType").val('${templAgree.agreType}');
    $("#status").val('${templAgree.status}');
</script>
<div class="easyui-panel" title="协议模板信息" data-options="iconCls:'fi-results'">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="templateEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>协议名</td>
                    <td><input id ="id" name="id" type="hidden"  value="${templAgree.id}">
                        <input id ="agreName" name="agreName" value="${templAgree.agreName}"  maxlength="49" type="text" placeholder="请输入协议名" class="easyui-validatebox span2" data-options="required:true" ></td>
                </tr>
                <tr>
                    <td>协议版本</td>
                    <td><input id ="agreVersion" name="agreVersion" value="${templAgree.agreVersion}"  maxlength="10" type="text" placeholder="请输入协议版本" class="easyui-validatebox span2" data-options="required:true" ></td>
                </tr>
                <tr>
                    <td>协议类型</td>
                    <td>
                        <select id ="agreType" name="agreType" style="width:140px;height:21px" >
                            <c:forEach items="${dict}" var="dictItem"  >
                                <option value="${dictItem.dItemvalue}">${dictItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>展示方式</td>
                    <td><input id ="agreViewType" name="agreViewType"  value="${templAgree.agreViewType}"  type="text"  class="easyui-validatebox span2"  ></td>
                </tr>
                <tr>
                    <td>状态</td>
                    <td >
                        <select id ="status" name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto',required:true">
                            <option value="1">生效</option>
                            <option value="2">无效</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>协议内容</td>
                    <td colspan="3"><textarea id ="agreContent" name="agreContent">${templAgree.agreContent}</textarea></td>
                </tr>
                <tr>
                    <td>上传附件</td>
                    <td class="attrInput">
                        <%--<a href="javascript:void(0)" class="addAgentColinfoTabledel-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="removeAgentColinfoTable_model(this)" >删除</a>||--%>
                        <a href="javascript:void(0)" class="addAgentColinfoTableAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addAgentColinfoTable_uploadView(this)" >添加附件</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div style="text-align:center;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save',region:'center'"  onclick="saveAgreement()">保存</a>
</div>