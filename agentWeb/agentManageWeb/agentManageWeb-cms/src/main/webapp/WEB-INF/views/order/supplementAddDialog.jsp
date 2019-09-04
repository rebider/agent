<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="添加补款" data-options="iconCls:'fi-results'">
    <form id="supplementAdd">
        <input name="amount" type="hidden" value="${oPayment.outstandingAmount}" id="outstandingAmount"/>
        <input name="srcId" type="hidden" value="${oSupplement.srcId}"/>

        <input name="agentId" type="hidden" value="${oSupplement.agentId}"/>

        <table class="grid">
            <tr>
                <td>补款:</td>
                <td>
                    <select name="pkType" style="width:200px;height:21px" disabled="true" id="pkType" edi>
                        <c:forEach items="${PkTypeList}" var="PkTypeListItem">
                            <c:if test="${PkTypeListItem.dItemnremark==oSupplement.pkType}">
                                <option value="${PkTypeListItem.dItemnremark}">${PkTypeListItem.dItemname}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
                <td>补款说明:</td>
                <td colspan="3"><input name="remark" type="text" class="easyui-validatebox" style="width:230px;"
                                       value="${oSupplement.remark}"/></td>

            </tr>
            <tr>
                <td>付款方式:</td>
                <td>
                    <select name="payMethod" style="width:100px;height:21px">
                        <c:forEach items="${PayList}" var="PayList">
                            <option value="${PayList.dItemnremark}">${PayList.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>应付金额:</td>
                <td><input name="money" type="text" class="easyui-numberbox" style="width:120px;"
                           value="${payMount}" readonly="readonly"
                           data-options="min:0,precision:2"/>元
                    <input name="orderId" type="hidden" value="${orderId}">
                </td>
            </tr>
            <tr>
                <td>实际付款金额:</td>
                <td><input name="payAmount" type="text" class="easyui-numberbox" style="width:120px;"
                           value="${payMount}"
                           data-options="min:0,precision:2"/>元
                    <input name="orderId" type="hidden" value="${orderId}">
                </td>
                <td>当前期数:</td>
                <td>第${oPaymentDetail.planNum}期</td>
            </tr>
            <tr>
                <td >订单备注:</td>
                <td colspan="3">${order.remark}</td>
            </tr>
        </table>
    </form>
</div>
    <%@ include file="/commons/order_cash_receivables.jsp" %>
<div class="easyui-panel" title="上传打款截图" data-options="iconCls:'fi-results'">
    <form id="supplement_AttFile_model_form">
        <table class="grid">
            <tr>
                <td class="attrInput" colspan="4">
                </td>
                <td colspan="2">
                    <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="supplement_AttFile_model_form_uploadView(this)">上传打款截图</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"
       onclick="saveSupplementDialog()">保存并审核</a>
</div>
<script type="text/javascript">
    var supplement_AttFile_model_form_attrDom;

    //上传窗口
    function supplement_AttFile_model_form_uploadView(t) {
        supplement_AttFile_model_form_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(supplement_AttFile_model_form_jxkxUploadFile);
    }

    //附件解析
    function supplement_AttFile_model_form_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for (var i = 0; i < jsondata.length; i++) {
            $(supplement_AttFile_model_form_attrDom).append("<span onclick='removeFile(this)'>" + jsondata[i].attName + "<input type='hidden' name='agentBaseTableFile' value='" + jsondata[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function removeFile(t) {
        parent.$.messager.confirm('询问', '确定删除附件么？', function (b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    //获取form数据
    function saveSupplementDialog() {
        $("#pkType").attr("disabled", false);
        var supplement = $.serializeObject($("#supplementAdd"));
        var supplementValidate = $("#supplementAdd").form('validate');
        var supplement_model_formFiles = $("#supplement_AttFile_model_form").find("input");
        var files = [];
        var oCashReceivablesVos =(typeof getPayList=== "function")?getPayList(${paylist_model}):[];
        for (var i = 0; i < supplement_model_formFiles.length; i++) {
            files.push($(supplement_model_formFiles[i]).val());
        }
        if (supplementValidate) {
            parent.$.messager.confirm('询问', '确认要添加？', function (b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/supplement/selectBySrcId",
                        dataType: 'json',
                        traditional: true,//这使json格式的字符不会被转码
                        contentType: 'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            supplement: supplement,
                            agentTableFile: files,
                            oCashReceivablesVos:oCashReceivablesVos
                        }),
                        beforeSend: function () {
                            progressLoad();
                        },
                        success: function (msg) {
                            if (msg.resCode == '1') {

                                $.ajaxL({
                                    type: "POST",
                                    url: "/supplement/supplementSaveAndaudit",
                                    dataType: 'json',
                                    traditional: true,//这使json格式的字符不会被转码
                                    contentType: 'application/json;charset=UTF-8',
                                    data: JSON.stringify({
                                        supplement: supplement,
                                        agentTableFile: files,
                                        oCashReceivablesVos:oCashReceivablesVos
                                    }),
                                    beforeSend: function () {
                                        progressLoad();
                                    },
                                    success: function (msg) {
                                        info(msg.resInfo);
                                        var orderId = $("#supplementAdd").find("input[name='orderId']").val();
                                        if (msg.resCode == '1') {
                                            $('#index_tabs').tabs('close', "补款审批申请");
                                          /*  supplement_data.datagrid('reload');
                                            if (orderList != undefined) {
                                                orderList.datagrid('reload');
                                            }*/
                                            refreshTab('订单:'+orderId);
                                        }
                                    },
                                    complete: function (XMLHttpRequest, textStatus) {
                                        progressClose();
                                    }
                                });

                            } else if (msg.resCode == '0') {
                                info("该订单未激活");
                            }
                        },
                        complete: function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
        }
    }
</script>