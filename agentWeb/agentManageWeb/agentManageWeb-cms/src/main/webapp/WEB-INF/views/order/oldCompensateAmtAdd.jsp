<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<style>
    .rigth-text{
        text-align:center;
    }
</style>
<script type="text/javascript">

    function uploadFile() {
        var f = document.getElementById('file');
        f.click();
    }

    function oldAnalysisFile() {
        var formData = new FormData();
        formData.append("file", $('#uploadForm')[0]);
        formData.append("orderType", "${orderType}");
        formData.append("rKey",  "${rKey}");
        $.ajax({
            url: '/oldCompensate/analysisFile',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(data) {
            $(".showGird").html("");
            var jsonObj =  eval("(" + data + ")");
            if(!jsonObj.success && jsonObj.success!=undefined){
                info(jsonObj.msg);
                return;
            }
            $.each(jsonObj,function(n,item) {
                var orderStr =
                "<tr>" +
                "<td class='rigth-text'>sn开始:</td><td><input type='text' name='beginSn' value='"+item.snBegin+"' disabled></td>" +
                "<td class='rigth-text'>sn结束:</td><td><input type='text' name='endSn' value='"+item.snEnd+"' disabled></td>"+
                "<td class='rigth-text'>数量:</td><td><input type='text' name='proNum' value='"+item.count+"' disabled></td>"+
                "<td class='rigth-text'>商品:</td>"+
                "<td width='50px'>"+
                "<select style='width:140px;height:21px' name='proId'>"+
                "<option value=''>--请选择--</option>";
                $.each(item.proMaps,function(n,a) {
                    orderStr+="<option value='"+a.id+"'>"+a.proName+"</option>";
                });
                orderStr+="</select></td>" +
                "<td class='rigth-text'>活动:</td>"+
                "<td><input type='text' name='oldActivityId' oldActivityId='"+item.activity.id+"' value='"+item.activity.activityName+"' disabled></td>"+
                "<td class='rigth-text'>新活动:</td>"+
                "<td width='50px'>"+
                "<select style='width:140px;height:21px' name='activityRealId'>"+
                "<option value=''>--请选择--</option>";
                $.each(item.changeActivitys,function(n,b) {
                    orderStr+="<option value='"+b.id+"'>"+b.activityName+"</option>";
                });
                orderStr+="</select></td>" +
                "</tr>";
                $(".showGird").append(orderStr);
            });


            $("select[name='activityRealId']").change(function(){
                $.ajaxL({
                    type: "POST",
                    url: "/oldCompensate/calculatePriceDiff",
                    dataType:'json',
                    traditional:true,//这使json格式的字符不会被转码
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        refundPriceDiffDetailList:getFromList()
                    }),
                    beforeSend : function() {
                        progressLoad();
                    },
                    success: function(msg){
                        $("#bcAmt").html("<p style='margin-left: 15px' id='bxjAmt'>补差金额："+msg+"元</p>");
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });

            });
            $("#file").val("");
        }).fail(function(res) {
            info("系统异常，请联系管理员！"+res);
        });
    }

    function getFromList() {
        var compenTable_FormDataJson = [];
        $(".showGird").find("tr").each(function(){
            var data = {};
            data.activityRealId = $(this).find("select[name='activityRealId']").find("option:checked").val();
            data.activityFrontId = $(this).find("input[name='oldActivityId']").attr("oldActivityId");
            data.changeCount = $(this).find("input[name='proNum']").val();
            data.beginSn = $(this).find("input[name='beginSn']").val();
            data.endSn = $(this).find("input[name='endSn']").val();
            data.proId = $(this).find("select[name='proId']").find("option:checked").val();
            data.proName = $(this).find("select[name='proId']").find("option:checked").text();
            data.agentId = "${agentId}";
            compenTable_FormDataJson.push(data);
        });
        return compenTable_FormDataJson;
    }

    $(function () {
        $("#file").change(function(){
            oldAnalysisFile();
        });
        if('${rKey}'!=''){
            oldAnalysisFile();
        }
    });

    var oldCompenstate_AttFile_attrDom;
    //上传窗口
    function oldCompenstate_AttFile_uploadView(t) {
        oldCompenstate_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(oldCompenstate_AttFile__jxkxUploadFile);
    }
    //附件解析
    function oldCompenstate_AttFile__jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(oldCompenstate_AttFile_attrDom).append("<span onclick='oldRemoveFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='compenstateFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function oldRemoveFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    function oldCompensateAmtSave(flag) {
        var refundPriceDiffDetailList = getFromList();
        var oCashReceivablesVoList = (typeof getPayList=== "function")?getPayList(${paylist_model}):[];
        var compenstate_formFiles = $(".attrInput").find("input");
        var files = [];
        for (var i = 0; i < compenstate_formFiles.length; i++) {
            files.push($(compenstate_formFiles[i]).val());
        }
        var bxjAmt = $("#bxjAmt").html();
        if(bxjAmt=='' || bxjAmt==undefined){
            info("补差价金额错误");
            return;
        }
        if(bxjAmt.match("-")){
            if(oCashReceivablesVoList.length==0){
                info("请填写打款记录！");
                return;
            }
        }

        if(oCashReceivablesVoList.length>0){
            var flag = 0;
            for (var j = 0; j<oCashReceivablesVoList.length; j++){
                var amount = oCashReceivablesVoList[j].amount;
                if(amount=='' || amount==undefined){
                    flag = 1;
                    break;
                }
                if(oCashReceivablesVoList[j].payType=='YHHK'){
                    var payUser = oCashReceivablesVoList[j].payUser;
                    var payTime = oCashReceivablesVoList[j].payTime;
                    if(payUser=='' || payUser==undefined){
                        flag = 1;
                        break;
                    }
                    if(payTime=='' || payTime==undefined){
                        flag = 1;
                        break;
                    }
                }
            }
            if(flag==1){
                info("请填写必填项！");
                return;
            }
        }
        parent.$.messager.confirm('询问', '确认提交审批？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/oldCompensate/compensateAmtSave",
                    dataType:'json',
                    traditional:true,//这使json格式的字符不会被转码
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        refundPriceDiffDetailList:refundPriceDiffDetailList,
                        refundPriceDiffFile:files,
                        oRefundPriceDiff:$.serializeObject($("#comFromId")),
                        oCashReceivablesVoList:oCashReceivablesVoList,
                        flag:flag
                    }),
                    beforeSend : function() {
                        progressLoad();
                    },
                    success: function(data){
                        info(data.msg);
                        if(data.status==200){
                            $('#index_tabs').tabs('close',"申请-老订单");
                            refreshRefundPriceDiffList();
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

<div class="easyui-panel" data-options="iconCls:'fi-results'" >
    <form id="uploadForm" enctype="multipart/form-data">
        <input style="display:none" type="file" id="file" name="file" />
    </form>
    <a href="javascript:void(0);" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" onclick="uploadFile();">上传SN号</a>
    <%--<a href="/static/template/oldCompensateAmt.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a>--%>
</div>

<div class="easyui-panel" title="订单信息" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
    <form id="com_model_form">
        <table class="grid showGird">
        </table>
    </form>
</div>
<div class="easyui-panel" title="补差价信息" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
    <div id="bcAmt">
    </div>
    <div>
        <div id="comReceivablesId">
            <%@ include file="/commons/order_cash_receivables.jsp"%>
            <p style='margin-left: 15px'>
                打款凭证:
                <a class="attrInput">
                </a>
                <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit"
                   data-options="plain:true,iconCls:'fi-magnifying-glass'"
                   onclick="oldCompenstate_AttFile_uploadView(this)">添加附件</a>
            </p>
        </div>
        <form id="comFromId">
        <input type="hidden" name="agentId" value="${agentId}">
        <p style='margin-left: 15px'>
            申请备注:
            <input name="applyRemark"  maxlength="100" type="text"  class="easyui-validatebox span2" style="width: 500px;">
        </p>
        </form>
    </div>
</div>

<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="oldCompensateAmtSave(1)">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="oldCompensateAmtSave(2)">保存并审核</a>
</div>

