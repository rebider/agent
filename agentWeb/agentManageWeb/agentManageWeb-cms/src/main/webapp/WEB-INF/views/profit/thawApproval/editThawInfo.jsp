<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript">
    var addAgentColinfoTable_attrDom ;
    var fileName;
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }
    //上传窗口
    function addAgentColinfoTable_uploadView(t){
        var length = $('[name=attachInfo]').length;
        if (length>0) {
            alertMsg("只能上传一个文件！");
            return;
        }
        addAgentColinfoTable_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(addAgentColinfoTable_jxkxUploadFile);
    }

    //附件解析
    function addAgentColinfoTable_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for(var i=0;i<1 ;i++){
            fileName = jsondata[i].attName;
            $(addAgentColinfoTable_attrDom).append("<span id='delFile' onclick='removeAgentColinfoTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='text' hidden name='attachInfo' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }

    }
    function removeAgentColinfoTable_jxkxUploadFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }

</script>
<div class="easyui-panel" title="代理商分润解冻" data-options="iconCls:'fi-results'">
    <form id="profitFreezeForm" method="post">
        <input type="hidden" name="id" value="${thaw.id}">
        <table class="grid">
                <tr>
                    <td>代理商编号</td>
                    <td>${thaw.agentId}</td>
                    <td>代理商名称</td>
                    <td>${thaw.agentName}</td>
                </tr>
                <tr>
                    <td>解冻月份：</td>
                    <td>${thaw.profitDate}</td>
                    <td>解冻分润(单位元)</td>
                    <td>${thaw.payProfit}</td>
                </tr>
            <tr>
                <td>备注：</td>
                <td>
                    <textarea style="width: 390px" id="remark"  name="remark" placeholder="请输入申请解冻原因" class="easyui-validatebox" data-options="required:true">${thaw.remark}</textarea></td>
                </td>
            </tr>
            <tr>
                <td>上传附件：</td>
                <td class="attrInput">
                    <a id="fileInput" href="javascript:void(0)" class="addAgentColinfoTableAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addAgentColinfoTable_uploadView(this)" >
                        <c:if test="${attName !=''}">新增</c:if>${attName}

                    </a>
                </td>
            </tr>

        </table>
    </form>
</div>

