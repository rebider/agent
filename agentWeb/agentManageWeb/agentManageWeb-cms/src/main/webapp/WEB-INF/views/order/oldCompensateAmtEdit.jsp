<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="查看信息">
        <div class="easyui-panel" title="补差价信息"  data-options="iconCls:'fi-results'">
            <form id ="editComPensateForm">
            <table class="grid">
                <tr>
                    <%--<td width="100px">申请补差价类型：</td>--%>
                    <%--<td width="200px" >${oRefundPriceDiff.applyCompName}</td>--%>
                    <%--<td width="100px" >申请补差价金额：</td>--%>
                    <%--<td width="100px" >${oRefundPriceDiff.applyCompAmt}</td>--%>
                    <td>申请备注：</td>
                    <td colspan="9">
                        <input type="text" name="applyRemark" value="${oRefundPriceDiff.applyRemark}">
                    </td>
                    <td style="display: none"><input type="hidden" name="id" value="${oRefundPriceDiff.id}"></td>
                </tr>
                <tr>
                    <td>代理商附件名称</td>
                    <td colspan="5">
                        <span class="attrInput">
                            <c:if test="${!empty oRefundPriceDiff.attachmentList}">
                                <c:forEach items="${oRefundPriceDiff.attachmentList}" var="attachment">
                                     <span>
                                             <a onclick='removeCompenEditFile(this)'>${attachment.attName}</a>
                                             <input type='hidden' value='${attachment.id}' />
                                            <a href="<%=imgPath%>${attachment.attDbpath}" target="_blank" >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                     </span>
                                </c:forEach>
                            </c:if>
                        </span>
                        <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'"
                           style="margin-left: 20px" onclick="compenEdit_AttFile_uploadView(this)">添加附件</a>
                    </td>
                </tr>
                <tr id="bcAmt">
                </tr>
            </table>
            </form>
        </div>
        <div class="easyui-panel" id="DataList" title="活动信息"  data-options="iconCls:'fi-results'">
            <c:forEach items="${oRefundPriceDiff.refundPriceDiffDetailList}" var="refundPriceDiffDetail">
                <table class="grid editGrid">
                    <tr>
                        <td width="60px">SN开始：</td>
                        <td>
                            <input type="text" name="beginSn" value="${refundPriceDiffDetail.beginSn}" readonly="readonly">
                            <input type="hidden" name="diffDetailId" value="${refundPriceDiffDetail.id}">
                        </td>
                        <td width="60px">SN结束：</td>
                        <td><input type="text" name="endSn" value="${refundPriceDiffDetail.endSn}" readonly="readonly"></td>
                        <td width="60px">数量：</td>
                        <td class="changeCountClass">${refundPriceDiffDetail.changeCount}</td>
                        <td width="80px">商品名称：</td>
                        <td>
                            <select name="proId" style="width:160px;height:21px">
                                <c:forEach items="${refundPriceDiffDetail.proMaps}" var="product">
                                    <option value="${product.id}" <c:if test="${product.id==refundPriceDiffDetail.proId}">selected="selected"</c:if> >${product.proName}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td width="60px">活动：</td>
                        <td>
                            <input type="text" name="oldActivityId" value="${refundPriceDiffDetail.activityFront.activityName}" oldActivityId="${refundPriceDiffDetail.activityFront.id}" readonly="readonly">
                        </td>
                        <td width="60px">新活动：</td>
                        <td>
                            <select name="activityRealId" style="width:160px;height:21px">
                            <c:forEach items="${refundPriceDiffDetail.oActivities}" var="activitiy">
                                <option value="${activitiy.id}" <c:if test="${activitiy.id==refundPriceDiffDetail.activityRealId}">selected="selected"</c:if> >${activitiy.activityName}</option>
                            </c:forEach>
                            </select>
                        </td>

                    </tr>
                </table>
            </c:forEach>
        </div>
        <%@ include file="/commons/order_cash_receivables_edit.jsp"%>
    </div>
</div>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="compensateAmtEdit()">保存</a>
</div>
<script>

    $(function () {
        $("select[name='activityRealId']").change(function(){
            console.log(getEditCompensate_FormDataJson());
            $.ajaxL({
                type: "POST",
                    url: "/oldCompensate/calculatePriceDiff",
                    dataType:'json',
                    traditional:true,//这使json格式的字符不会被转码
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                    refundPriceDiffDetailList:getEditCompensate_FormDataJson()
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
    });

    function getEditCompensate_FormDataJson() {
        var editCompensate_FormDataJson = [];
        $('#DataList .editGrid').each(function(i){
            var data = {};
            data.id = $(this).find("input[name='diffDetailId']").val();
            data.beginSn = $(this).find("input[name='beginSn']").val();
            data.endSn = $(this).find("input[name='endSn']").val();
            data.activityRealId = $(this).find("select[name='activityRealId']").find("option:checked").val();
            data.activityFrontId = $(this).find("input[name='oldActivityId']").attr("oldActivityId");
            data.changeCount = $(this).find(".changeCountClass").html();
            data.proId = $(this).find("select[name='proId']").find("option:checked").val();
            data.proName = $(this).find("select[name='proId']").find("option:checked").text();
            editCompensate_FormDataJson.push(data);
        });
        return editCompensate_FormDataJson;
    }



    function compensateAmtEdit() {
        var refundPriceDiffDetailList = getEditCompensate_FormDataJson();
        var updatePayList = getUpdatePayList("${paylist_model}");
        var editComPensateForm = $.serializeObject($("#editComPensateForm"));
        var compenEdit_formFiles = $(".attrInput").find("input");
        var files = [];
        for (var i = 0; i < compenEdit_formFiles.length; i++) {
            files.push($(compenEdit_formFiles[i]).val());
        }

        if(updatePayList.length>0){
            var flag = 0;
            for (var j = 0; j<updatePayList.length; j++){
                var amount = updatePayList[j].amount;
                if(amount=='' || amount==undefined){
                    flag = 1;
                    break;
                }
                if(updatePayList[j].payType=='YHHK'){
                    var payUser = updatePayList[j].payUser;
                    var payTime = updatePayList[j].payTime;
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

        parent.$.messager.confirm('询问', '您确定要修改？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/oldCompensate/compensateAmtEdit",
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        refundPriceDiffDetailList: refundPriceDiffDetailList,
                        oRefundPriceDiff:editComPensateForm,
                        refundPriceDiffFile:files,
                        oCashReceivablesVoList:updatePayList
                    }),
                    success: function (data) {
                        info(data.msg);
                        if(data.status==200){
                            $('#index_tabs').tabs('close',"补差价-修改");
                            refreshRefundPriceDiffList();
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {

                    }
                });
            }
        });
    }

    var compenEdit_AttFile_attrDom;
    //上传窗口
    function compenEdit_AttFile_uploadView(t) {
        compenEdit_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(compenEdit_AttFile__jxkxUploadFile);
    }
    //附件解析
    function compenEdit_AttFile__jxkxUploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(compenEdit_AttFile_attrDom).append("<span ><a onclick='removeCompenEditFile(this)'>" + jsonData[i].attName + "</a><input type='hidden' value='" + jsonData[i].id + "' /><a href='<%=imgPath%>"+jsonData[i].attDbpath+"' data-options='plain:true' target='_blank' >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function removeCompenEditFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }
</script>
