<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    function saveUnfreeAtt() {
        var remark = $('#remark').val();
        if (remark == '' || remark == undefined) {
            return;
        }
        parent.$.messager.confirm('询问', '您确定要对该代理商进行分润解冻？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/monthProfit/unfreeze",
                    dataType:'json',
                    traditional:true,
                    contentType:"application/x-www-form-urlencoded",
                    data: $('#profitFreezeForm').serializeArray(),
                    beforeSend : function() {
                        progressLoad();
                    },
                    success: function(msg){
                        if (msg.success) {
                            alertMsg("申请解冻已提交！");
                        }else {
                            alertMsg(msg.msg);
                        }
                        $("#index_tabs").tabs('close','月分润申请解冻');
                        monthProfitListGrid.datagrid('reload');
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
            $(addAgentColinfoTable_attrDom).append("<span onclick='removeAgentColinfoTable_jxkxUploadFile(this)'>"+jsondata[i].attName+"<input type='text' hidden name='attachInfo' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
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
<div class="easyui-panel" title="月分润申请解冻" data-options="iconCls:'fi-results'">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="profitFreezeForm" method="post">
            <table class="grid">
                <input type="hidden" name="profitId" value="${profitMonth.id}">
                <input type="hidden" name="freezeStatus" value="3">
                <tr>
                    <td>代理商编号：</td>
                    <td><input type="hidden" name="agentId" value="${profitMonth.agentId}" readonly="true" >${profitMonth.agentId}</td>
                </tr>
                <tr>
                    <td>上级商户编号：</td>
                    <td><input type="hidden" name="parentAgentId" value="${profitMonth.parentAgentId}" readonly="true" >${profitMonth.parentAgentId}</td>
                </tr>
                <tr>
                    <td>代理商名称：</td>
                    <td><input type="hidden" name="agentName" value="${profitMonth.agentName}" readonly="true" >${profitMonth.agentName}</td>
                </tr>
                <tr>
                    <td>解冻月份：</td>
                    <td><input type="hidden" name="profitDate" value="${profitMonth.profitDate}" readonly="true" >${profitMonth.profitDate}</td>
                </tr>
                <tr>
                    <td>解冻分润(单位元)：</td>
                    <td><input type="hidden" name="payProfit" value="${profitMonth.realProfitAmt}" readonly="true" >${profitMonth.realProfitAmt}</td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td>
                        <textarea style="width: 390px" id="remark"  name="remark" placeholder="请输入申请解冻原因" class="easyui-validatebox" data-options="required:true">${profitMonth.remark}</textarea></td>
                    </td>
                </tr>
                <tr>
                    <td>上传附件：</td>
                    <td class="attrInput">
                        <a href="javascript:void(0)" class="addAgentColinfoTableAttr-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="addAgentColinfoTable_uploadView(this)" >添加附件</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div style="text-align:center;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save',region:'center'"  onclick="saveUnfreeAtt()">申请解冻</a>
</div>