<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<style>
    .rigth-text{
        text-align:center;
    }
</style>
<script type="text/javascript">

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    function uploadFile() {
        var f = document.getElementById('file');
        f.click();
    }

    function analysisFile() {
        var formData = new FormData();
        formData.append("file", $('#uploadForm')[0]);
        formData.append("orderType", "${orderType}");
        formData.append("rKey",  "${rKey}");
        $.ajax({
            url: '/compensate/analysisFile',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(data) {
            $(".showGird").html("");
            $(".bcjGird").html("");
            var jsonObj =  eval("(" + data + ")");
            if(!jsonObj.success && jsonObj.success!=undefined){
                info(jsonObj.msg);
                return;
            }
            var sumPrice = 0;
            $.each(jsonObj,function(n,item) {
                sumPrice = parseInt(sumPrice)+parseInt(item.SUM_PROPRICE);
                var orderStr =
                "<tr>" +
                "<input type='hidden' name='id' value='"+item.id+"'>"+
                "<input type='hidden' name='beginSn' value='"+item.MIN_SN+"'>"+
                "<input type='hidden' name='endSn' value='"+item.MAX_SN+"'>"+
                "<input type='hidden' name='proId' value='"+item.PRO_ID+"'>"+
                "<input type='hidden' name='proName' value='"+item.PRO_NAME+"'>"+
                "<input type='hidden' name='agentId' value='"+item.AGENT_ID+"'>"+
                "<input type='hidden' name='orderId' value='"+item.ORDER_ID+"'>"+
                "<td class='rigth-text'>商品:</td>"+
                "<td width=\"50px\">"+
                "<select style=\"width:140px;height:21px\" disabled name=\"proNameSelect\">"+
                "<option >"+item.PRO_NAME+"</option></select></td>"+
                "<td class='rigth-text'>单价:</td><td><input type='text' name='proPrice' value='"+item.SETTLEMENT_PRICE+"' disabled></td>" +
                "<td class='rigth-text'>订货数量:</td><td><input type='text' name='proNum' value='"+item.PRO_NUM+"' disabled></td>";

                var actStr = queryAllActivity(orderStr,item.ACTIVITY_ID,item.PRO_ID,item.AGENT_ID,item.ORDER_ID);
                $(".bcjGird").append(actStr);
                orderStr+="<td class='rigth-text'>活动信息:</td>"+
                "<td>"+
                "<select style=\"width:140px;height:21px\" disabled>"+
                "<option value='"+(item.ACTIVITY_ID!=undefined?item.ACTIVITY_ID:'')+"'>"+(item.ACTIVITY_NAME!=undefined?item.ACTIVITY_NAME:'')+"</option>"+
                "</select></td></tr>";
                $(".showGird").append(orderStr);
                var aa = "<tr><td class='rigth-text'>sn开始:</td><td>"+item.MIN_SN+"</td>"+
                "<td class='rigth-text'>sn结束:</td><td>"+item.MAX_SN+"</td>"+
                "<td class='rigth-text'>代理商编号:</td><td>"+item.AGENT_ID+"</td>"+
                "</tr>";
                $(".showGird").append(aa);
            });
            $("#com_model_form").append("<p style=\"margin-left: 30px\">总金额："+sumPrice+"</p>");

//            $("select[name='activityRealId']").change(function(){
//                var price = $(this).find("option:checked").attr("price");
//                $(this).parent().parent().find("input[name='proPrice']").val(price);
//                $.ajaxL({
//                    type: "POST",
//                    url: "/compensate/calculatePriceDiff",
//                    dataType:'json',
//                    traditional:true,//这使json格式的字符不会被转码
//                    contentType:'application/json;charset=UTF-8',
//                    data: JSON.stringify({
//                        refundPriceDiffDetailList:getFromList()
//                    }),
//                    beforeSend : function() {
//                        progressLoad();
//                    },
//                    success: function(msg){
//                        $("#bcAmt").html("<p style='margin-left: 15px' id='bxjAmt'>补差金额："+msg+"元</p>");
//                    },
//                    complete:function (XMLHttpRequest, textStatus) {
//                        progressClose();
//                    }
//                });
//            });
            $("select[name='activityRealId']").change(function(){
                var price = $(this).find("option:checked").attr("price");
                var productName = $(this).find("option:checked").attr("productName");
                $(this).parent().parent().find("select[name='proNameSelect']").html("<option>"+productName+"</option>");
                $(this).parent().parent().find("input[name='proPrice']").val(price);
            });

            $("#file").val("");
        }).fail(function(res) {
            info("系统异常，请联系管理员！"+res);
        });
    }

    function getFromList() {
        var compenTable_FormDataJson = [];
        $(".bcjGird").find("tr").each(function(){
            var data = {};
            data.activityRealId = $(this).find("select[name='activityRealId']").find("option:checked").val();
            data.activityFrontId = $(this).find("select[name='activityRealId']").attr("oldActivityId");
            data.price = $(this).find("input[name='proPrice']").val();
            data.changeCount = $(this).find("input[name='proNum']").val();
            data.subOrderId = $(this).find("input[name='id']").val();
            data.beginSn = $(this).find("input[name='beginSn']").val();
            data.endSn = $(this).find("input[name='endSn']").val();
            data.proId = $(this).find("input[name='proId']").val();
            data.proName = $(this).find("input[name='proName']").val();
            data.agentId = $(this).find("input[name='agentId']").val();
            data.orderId = $(this).find("input[name='orderId']").val();
            compenTable_FormDataJson.push(data);
        });
        return compenTable_FormDataJson;
    }

    function queryAllActivity(orderStr,oldActivityId,proId,agentId,orderId){
        $.ajax({
            url :"${path}/activity/queryProductCanActivity",
            type:'POST',
            async: false,
            data: {agentId:agentId,proId:proId,oldActivityId:oldActivityId,orderId:orderId},
            dataType:'json',
            success:function(data){
                orderStr+="<td class='rigth-text'>活动信息:</td><td>"+
                "<select style=\"width:140px;height:21px\" name='activityRealId' oldActivityId='"+oldActivityId+"'>";
                orderStr+="<option value=''>--请选择--</option>";
                $.each(data,function(n,item) {
                    orderStr+="<option value='"+item.id+"' price='"+item.price+"' productName='"+item.productName+"'>"+item.activityName+"</option>";
                });
                orderStr+="</select></td></tr>";
            },
            error:function(data){
                alertMsg("系统异常，请联系管理员！");
            }
        });
        return orderStr;
    }

    $(function () {
        $("#file").change(function(){
            analysisFile();
        });
        if('${rKey}'!=''){
            analysisFile();
        }
    });

    var compenstate_AttFile_attrDom;
    //上传窗口
    function compenstate_AttFile_uploadView(t) {
        compenstate_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(compenstate_AttFile__jxkxUploadFile);
    }
    //附件解析
    function compenstate_AttFile__jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(compenstate_AttFile_attrDom).append("<span onclick='removeFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='compenstateFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function removeFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    function compensateAmtSave(flag) {
        var refundPriceDiffDetailList = getFromList();
        var oCashReceivablesVoList = (typeof getPayList=== "function")?getPayList(${paylist_model}):[];
        var compenstate_formFiles = $(".attrInput").find("input");
        var files = [];
        for (var i = 0; i < compenstate_formFiles.length; i++) {
            files.push($(compenstate_formFiles[i]).val());
        }
//        var bxjAmt = $("#bxjAmt").html();
//        if(bxjAmt=='' || bxjAmt==undefined){
//            info("提交失败")
//            return;
//        }
//        if(bxjAmt.match("-")){
//            if(oCashReceivablesVoList.length==0){
//                info("请填写打款记录！");
//                return;
//            }
//        }

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
                    url: "/compensate/compensateAmtSave",
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
                            $('#index_tabs').tabs('close',"申请-新订单");
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
    <%--<a href="/static/template/compensateAmt.xlsx" style="margin:5px 5px 5px 5px" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'" >下载模板</a>--%>
</div>

<div class="easyui-panel" title="订单信息" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
    <form id="com_model_form">
        <table class="grid showGird">
        </table>
    </form>
</div>
<div class="easyui-panel" title="补差价信息" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
    <table class="grid bcjGird">
    </table>
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
                   onclick="compenstate_AttFile_uploadView(this)">添加附件</a>
            </p>
        </div>
        <form id="comFromId">
        <p style='margin-left: 15px'>
            申请备注:
            <input name="applyRemark"  maxlength="100" type="text"  class="easyui-validatebox span2" style="width: 500px;">
        </p>
        </form>
    </div>
</div>

<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="compensateAmtSave(1)">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="compensateAmtSave(2)">保存并审核</a>
</div>

