<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="填写信息" data-options="iconCls:'fi-results'">
    <form id="internetRenew_model_form">
        <input type="hidden" name="iccidNumIds">
        <table class="grid">
            <tbody>
            <tr>
                <td>续费方式</td>
                <td>
                    <select name="renewWay" id="renewWay" style="width:160px;height:21px">
                        <c:forEach items="${internetRenewWayList}" var="internetRenewWay">
                            <option value="${internetRenewWay.key}">${internetRenewWay.value}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>卡数量</td>
                <td>
                    <input name="renewCardCount" id="renewCardCount" type="text"  class="easyui-validatebox"  style="width:160px;"  data-options="required:true" disabled/>
                </td>
            </tr>
            <tr>
                <td>轧差金额</td>
                <td>
                    <input name="sumOffsetAmt" type="text"  class="easyui-validatebox"  style="width:160px;"  disabled/>
                </td>
                <td>应补款金额</td>
                <td>
                    <input name="suppAmt" type="text"  class="easyui-validatebox"  style="width:160px;"  disabled/>
                </td>
            </tr>
            <tr>
                <td width="80px">附件</td>
                <td colspan="3">
                    <a class="attrInput">
                    </a>
                    <a href="javascript:void(0)"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="renew_AttFile_uploadView(this)">添加附件</a>
                </td>
            </tr>
            <tr>
                <td width="80px">申请备注</td>
                <td colspan="3"><input name="applyRemark" type="text" class="easyui-validatebox"  style="width:100%" /></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<%@ include file="/commons/order_cash_receivables.jsp"%>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveTerminalTransfer()">保存并审核</a>
</div>
<script type="text/javascript">

    $(function () {
        var iccidNumIds = sessionStorage.getItem("iccidNumIds");
        $("input[name='iccidNumIds']").val(iccidNumIds);
        var arr = iccidNumIds.split(",");
        var renewCardCount = arr.length;
        $("#renewCardCount").val(arr.length);
        var renewWay = $("#internetRenew_model_form").find("select[name='renewWay'] option:selected").val();
        var sumOffsetAmt = "0";
        var suppAmt = "0";
        if(renewWay.indexOf("GC") >= 0 ) {
            sumOffsetAmt = "${offsetAmt}"*renewCardCount
        }
        suppAmt = "${cardAmt}"*renewCardCount;
        $("#internetRenew_model_form").find("input[name='sumOffsetAmt']").val(sumOffsetAmt);
        $("#internetRenew_model_form").find("input[name='suppAmt']").val(suppAmt);

        $("#renewWay").change(function(){
            var renewWay =  $(this).val();
            var renewCardCount = $(this).parent().parent().find("#renewCardCount").val();
            if(renewCardCount=='' || renewCardCount==undefined){
                renewCardCount = 0;
            }
            var sumOffsetAmt = "0";
            var suppAmt = "0";
            if(renewWay.indexOf("GC") >= 0 ) {
                sumOffsetAmt = "${offsetAmt}"*renewCardCount
            }
            suppAmt = "${cardAmt}"*renewCardCount;
            $("#internetRenew_model_form").find("input[name='sumOffsetAmt']").val(sumOffsetAmt);
            $("#internetRenew_model_form").find("input[name='suppAmt']").val(suppAmt);
        });

    });


    var renew_AttFile_attrDom;
    //上传窗口
    function renew_AttFile_uploadView(t) {
        renew_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(renew_AttFile_UploadFile);
    }
    //附件解析
    function renew_AttFile_UploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(renew_AttFile_attrDom).append("<span onclick='renew_removeFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='files' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function renew_removeFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }


    function saveTerminalTransfer() {

        var internetRenewModelForm = $("#internetRenew_model_form").form('validate');
        var oCashReceivablesVoList = (typeof getPayList=== "function")?getPayList(${paylist_model}):[];

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
                info("请填写打款记录必填项！");
                return;
            }
        }

        if (internetRenewModelForm) {
            parent.$.messager.confirm('询问', '确认要保存？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/internetRenew/internetRenewSave",
                        dataType:'json',
                        traditional:true,//这使json格式的字符不会被转码
                        contentType:'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            internetRenew:$.serializeObject($("#internetRenew_model_form")),
                            oCashReceivablesVoList:oCashReceivablesVoList
                        }),
                        beforeSend : function() {
                            progressLoad();
                        },
                        success: function(data){
                            if(data.status!=200){
                                info(data.msg);
                                return;
                            }
                            info(data.msg);
                            $('#index_tabs').tabs('close',"物联网卡_续费");
                            internetCardList.datagrid('reload');
                        },
                        complete:function (XMLHttpRequest, textStatus) {
                            progressClose();
                        },
                        error:function () {
                            progressClose();
                        }
                    });
                }
            });
        } else {
            info("请输入必填项")
        }
    }

</script>
