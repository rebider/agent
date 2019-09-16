<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="easyui-panel" title="填写申请退出信息（申请退出会关联业务平台进行数据的调整！！！）" data-options="iconCls:'fi-results'">
    <form id="applyBaseData">
        <table class="grid">
            <tr>
                <td>申请退出平台</td>
                <td colspan="7">
                    <select class="easyui-combobox" name="applyPlat" id="applyPlat" style="width:160px;height:21px">
                        <c:forEach items="${contentMap}" var="contentMapItem">
                            <option value="${contentMapItem.key}">${contentMapItem.value}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <%--<td>保证金信息：</td>--%>
                <c:forEach items="${capitalList}" var="capitalListItem">
                    <td>${capitalListItem.cType}</td>
                    <td >${capitalListItem.cAmount}/元</td>
                </c:forEach>
                <td hidden>代理商Id<input name="agentId" id="agentId" type="text" value="${agentId}"/></td>
            </tr>
            <tr>
                <td>欠票</td>
                <td>
                    <input class="easyui-numberbox" name="debtBill" id="debtBill" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true,validType:['length[1,20]','Money']" value="0" readonly="readonly">/元</td>
                <td>补缴欠票</td>
                <td><input class="easyui-numberbox" name="supplyDebtBill" id="supplyDebtBill" type="text" class="easyui-validatebox" style="width:120px;"
                           placeholder="请输入金额" data-options="required:true,validType:'length[1,30]'" value="">/元</td>
            </tr>
            <tr>
                <td>欠款</td>
                <td>
                    <%--<select class="easyui-numberbox" name="debtAmt" id="debtAmt" style="width:160px;height:21px">--%>
                        <%--<c:forEach items="${debtAmtList}" var="debtAmtListItem">--%>
                            <%--<td>${debtAmtListItem.debtAmt}/元</td>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                    <input class="easyui-numberbox" name="debtAmt" id="debtAmt" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true,validType:['length[1,20]','Money']" value="0">/元
                </td>
                </td>
                <td>补缴欠款</td>
                <td><input class="easyui-numberbox" name="supplyDebtAmt" id="supplyDebtAmt" type="text" class="easyui-validatebox" style="width:120px;"
                           placeholder="请输入金额" data-options="required:true,validType:['length[1,20]','Money']" value="">/元</td>
            </tr>
            <tr>
                <td>补款方式</td>
                <td>
                    <select class="easyui-combobox" name="supplyType" id="supplyType" style="width:160px;height:21px">
                        <c:forEach items="${resultMap}" var="resultMapItem">
                            <option value="${resultMapItem.key}">${resultMapItem.value}</option>
                        </c:forEach>
                    </select>
                </td>
                </td>
                <td>收款公司</td>
                <td>
                    <select class="easyui-combobox" name="payCompany" id="payCompany" style="width:160px;height:21px">
                        <c:forEach var="payCompListItem" items="${payCompList}">
                            <option value="${payCompListItem.id}">${payCompListItem.comName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
<%--添加附件--%>
<div class="easyui-panel" title="添加附件" data-options="iconCls:'fi-results'">
    <form id="AgentBase_AttFile_model_form">
        <table class="grid">
            <tr>
                <td class="attrInput" colspan="4">
                </td>
                <td colspan="2">
                    <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" style="cursor: pointer;"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="AgentBase_AttFile_model_form_uploadView(this,ATT_DATA_TYPE_STATIC.HTJCTZ.key)">合同解除通知</a>
                    ||
                    <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" style="cursor: pointer;"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="AgentBase_AttFile_model_form_uploadView(this,ATT_DATA_TYPE_STATIC.DKXX.key)">打款信息</a>
                    ||
                    <a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" style="cursor: pointer;"
                       data-options="plain:true,iconCls:'fi-magnifying-glass'"
                       onclick="AgentBase_AttFile_model_form_uploadView(this,ATT_DATA_TYPE_STATIC.KPXX.key)">开票信息</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<%@ include file="/commons/validate.jsp" %>
<shiro:hasPermission name="/agent/enterbutton" >
    <div style="text-align:right;padding:5px;margin-bottom: 50px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveAgentEnterIn()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveAndaudit()">保存并审核</a>
    </div>
</shiro:hasPermission>
<script type="text/javascript">
    var AgentBase_AttFile_model_form_attrDom;
    //上传窗口
    function AgentBase_AttFile_model_form_uploadView(t,type) {
        AgentBase_AttFile_model_form_attrDom = $(t).parent().parent().find(".attrInput");
        multFileUpload(AgentBase_AttFile_model_form_jxkxUploadFile,type);
    }

    //附件解析
    function AgentBase_AttFile_model_form_jxkxUploadFile(data) {
        var jsondata = eval(data);
        for (var i = 0; i < jsondata.length; i++) {
            $(AgentBase_AttFile_model_form_attrDom).append("<span onclick='removeFile(this)'>" + jsondata[i].attName + "<input type='hidden' name='agentBaseTableFile' value='" + jsondata[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function removeFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    //保存
    function saveAgentEnterIn() {
        var baseData = $.serializeObject($("#applyBaseData"));
        var agentId = $('#agentId').val();//代理商Id
        var applyPlat = $('#applyPlat').combobox("getText");//申请退出平台
//        var debtBill = $('#debtBill').textbox("getValue");//欠票
        var supplyDebtBill = $('#supplyDebtBill').textbox("getValue");//补缴欠票
        var debtAmt = $('#debtAmt').textbox("getValue");//欠款
        var supplyDebtAmt = $('#supplyDebtAmt').textbox("getValue");//补缴欠款
        var supplyType = $('#supplyType').combobox("getText");//补款方式
        var payCompany = $('#payCompany').combobox("getText");//收款公司
        if (applyPlat == '' || applyPlat == undefined) {
            info("请选择申请退出平台！");
            return;
        }
        if (supplyType == '' || supplyType == undefined) {
            info("请选择补款方式！");
            return;
        }
        if (payCompany == '' || payCompany == undefined) {
            info("请选择收款公司！");
            return;
        }
        alert(agentId+"/"+applyPlat+"/"+supplyDebtBill+"/"+debtAmt+"/"+supplyDebtAmt+"/"+supplyType+"/"+payCompany)

        var AgentBase_AttFile_model_formFiles = $("#AgentBase_AttFile_model_form").find("input");
        var files = [];
        for (var i = 0; i < AgentBase_AttFile_model_formFiles.length; i++) {
            files.push($(AgentBase_AttFile_model_formFiles[i]).val());
        }
        var agentBase = $("#applyBaseData").form('validate');
        if(files.length < 3){
            info("请上传合同解除通知、打款信息、开票信息附件件！");
            return;
        }

        if (agentBase) {
            parent.$.messager.confirm('询问', '确认要添加？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/pAgentQuit/addQuit",
                        dataType: 'json',
                        traditional: true, //这使json格式的字符不会被转码
                        data: {
                            agent: baseData,
                            agentId: agentId,
                            applyPlat: applyPlat,
                            supplyDebtBill: supplyDebtBill,
                            debtAmt: debtAmt,
                            supplyDebtAmt: supplyDebtAmt,
                            supplyType: supplyType,
                            payCompany: payCompany,
                            agentTableFile: files
                        },
                        beforeSend: function () {
                            progressLoad();
                        },
                        success: function (msg) {
                            info(msg.resInfo);
                            if (msg.resCode == '1') {
                                $('#index_tabs').tabs('close', "代理商操作-退出申请");
                                quitApplyList.datagrid('reload');
                            }
                        },
                        complete: function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
        } else {
            info("请输入必填项");
        }
    }

    //保存并审核
    function industrialAuth() {
        var applyPlat = $('#applyPlat').val();
        if (applyPlat == '' || applyPlat == undefined) {
            info("请选择申请退出平台！");
            return;
        }

        $.ajax({
            url: "${path}//pAgentQuit/addQuit",
            type: 'POST',
            data: {
                agentBusinfoName: applyPlat
            },
            dataType:'json',
            success:function(data){
                $("input[name='debtBill']").val(dataObj.frName);  //欠票
                $("input[name='supplyDebtBill']").val(dataObj.address); //补缴欠票
                $("input[name='debtAmt']").val(dataObj.operateScope); //欠款
                $("input[name='supplyDebtAmt']").val(dataObj.creditCode); //补缴欠款
                $("input[name='supplyType']").val(dataObj.operateScope); //补款方式
                $("input[name='payCompany']").val(dataObj.creditCode); //打款公司

                if(dataObj.frName!='' && dataObj.frName!=undefined){
                    $("input[name='debtBill']").removeClass("validatebox-invalid");
                    $("input[name='debtBill']").textbox({readonly:true});
                }
                if(dataObj.address!='' && dataObj.address!=undefined){
                    $("input[name='supplyDebtBill']").removeClass("validatebox-invalid");
                    $("input[name='supplyDebtBill']").textbox({readonly:true});
                }
                if(dataObj.operateScope!='' && dataObj.operateScope!=undefined){
                    $("input[name='debtAmt']").removeClass("validatebox-invalid");
                    $("input[name='debtAmt']").textbox({readonly:true});
                }
                if(dataObj.creditCode!='' && dataObj.creditCode!=undefined){
                    $("input[name='supplyDebtAmt']").removeClass("validatebox-invalid");
                    $("input[name='supplyDebtAmt']").textbox({readonly:true});
                }
                if(dataObj.operateScope!='' && dataObj.operateScope!=undefined){
                    $("input[name='supplyType']").removeClass("validatebox-invalid");
                    $("input[name='supplyType']").textbox({readonly:true});
                }
                if(dataObj.creditCode!='' && dataObj.creditCode!=undefined){
                    $("input[name='payCompany']").removeClass("validatebox-invalid");
                    $("input[name='payCompany']").textbox({readonly:true});
                }
                $("input[name='applyPlat']").textbox({readonly:true});
                $("#applyBaseData .datebox :text").attr("disabled","true");

            },
            error:function(data){
                info("服务器异常，请联系管理员！");
            }
        });
    }
</script>