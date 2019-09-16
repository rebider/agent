<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-panel" title="代理商信息" data-options="iconCls:'fi-results'">
    <table class="grid">
        <tbody>
        <tr>
            <td width="200px">代理商编码</td>
            <td width="200px">代理商名称</td>
            <td width="200px">机具欠款</td>
            <td width="200px">欠票</td>
        </tr>
        <tr>
            <td>${agentId}</td>
            <td>${agentName}</td>
            <td>${sumDebt}</td>
            <td>${subAgentOweTicket}</td>
        </tr>
        </tbody>
    </table>
</div>
<form id="capitalChangeDataForm">
    <div class="easyui-panel" title="信息填写" data-options="iconCls:'fi-results'">
        <input type="hidden" name="flag" id="flag">
        <input type="hidden" name="agentId" id="agentId" value="${agentId}">
        <input type="hidden" name="payTemplet" id="payTemplet">
        <table class="grid">
            <tr>
                <td>费用类型</td>
                <td style="width:180px">
                    <input type="text" class="easyui-validatebox" style="width:120px;" value="${cTypeName}" disabled>
                    <input name="capitalType" type="hidden" value="${ccType}">
                </td>
                <td>剩余金额</td>
                <td>
                    <input type="text" class="easyui-validatebox" style="width:120px;" value="${amount}" disabled>
                    <input name="capitalAmt" type="hidden" value="${amount}">
                </td>
                <td>操作类型</td>
                <td>
                    <select name="operationType" style="width:160px;height:21px">
                        <c:forEach var="operationType" items="${operationTypeList}">
                            <option value="${operationType.key}">${operationType.value}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>处理金额</td>
                <td><input name="operationAmt" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true"  value=""></td>
            </tr>
            <tr>
                <td>抵扣机具欠款金额</td>
                <td style="width:180px">
                    <input name="machinesDeptAmt" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true" value="0" readonly="readonly">
                </td>
                <td>手续费</td>
                <td style="width:180px">
                    <input name="serviceCharge" type="text" class="easyui-validatebox" style="width:120px;"
                           data-options="required:true" value="0">
                </td>
            </tr>
        </table>
    </div>
    <div class="easyui-panel" title="收款账户" data-options="iconCls:'fi-results'" style="display: none" id="gatherAccountId">
        <table class="grid">
            <tr>
                <td>收款账户类型</td>
                <td>
                    <select name="cloType" style="width:160px;height:21px">
                        <c:forEach items="${colInfoType}" var="colInfoTypeItem">
                            <option value="${colInfoTypeItem.dItemvalue}" <c:if test="${colInfoTypeItem.dItemvalue==agentColinfos.cloType}">
                                selected="selected"</c:if>>${colInfoTypeItem.dItemname}
                            </option>
                        </c:forEach>
                    </select>
                </td>
                <td>收款账户名</td>
                <td><input name="cloRealname" type="text" value="${agentColinfos.cloRealname}" class="easyui-validatebox"/></td>
                <td>收款开户总行</td>
                <td><input name="cloBank" type="text" value="${agentColinfos.cloBank}" class="easyui-validatebox"/></td>
                <td>收款开户支行</td>
                <td><input name="cloBankBranch" type="text" value="${agentColinfos.cloBankBranch}" class="easyui-validatebox"/></td>
            </tr>
            <tr>
                <td>收款账号</td>
                <td><input name="cloBankAccount" type="text" value="${agentColinfos.cloBankAccount}" class="easyui-validatebox"/></td>
                <td>总行联行号</td>
                <td><input name="allLineNum" type="text" value="${agentColinfos.allLineNum}" class="easyui-validatebox"/></td>
                <td>支行联行号</td>
                <td><input name="branchLineNum" type="text" value="${agentColinfos.branchLineNum}" class="easyui-validatebox"/></td>
                <td>开户行地区</td>
                <td><input name="bankRegion" type="text" value="<agent:show type="region" busId="${agentColinfos.bankRegion}"/>" class="easyui-validatebox"/></td>
            </tr>
        </table>
    </div>
    <div class="easyui-panel" title="申请信息" data-options="iconCls:'fi-results'">
        <table class="grid">
            <tbody>
            <%--<tr>--%>
                <%--<td width="80px">打款凭证</td>--%>
                <%--<td>--%>
                    <%--<a class="attrInput"></a>--%>
                    <%--<a href="javascript:void(0)" class="busck-easyui-linkbutton-edit" data-options="plain:true,iconCls:'fi-magnifying-glass'"--%>
                       <%--onclick="capitalChange_AttFile_uploadView(this)">添加附件</a>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td width="80px">申请备注</td>
                <td>
                    <input name="remark" type="text" class="easyui-validatebox" style="width:500px;"/>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</form>
<%--<%@ include file="/commons/order_cash_receivables.jsp"%>--%>
<div style="text-align:right;padding:5px;margin-bottom: 50px;">
    <%--<a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveCapitalChange(1)">保存</a>--%>
    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="saveCapitalChange(2)">提交审核</a>
</div>
<script type="text/javascript">

    $(function () {
        $("select[name='operationType']").change(function(){
            var value = $(this).find("option:selected").val();
            if(value==1){
                $("input[name='machinesDeptAmt']").val(0);
                $("input[name='machinesDeptAmt']").attr("readonly","readonly");
                $("#gatherAccountId").hide();
            }else if(value==2){
                $("input[name='machinesDeptAmt']").removeAttr("readonly");
                $("#gatherAccountId").show();
            }
        });
    });

//    var capitalChange_AttFile_attrDom;
//    //上传窗口
//    function capitalChange_AttFile_uploadView(t) {
//        capitalChange_AttFile_attrDom = $(t).parent().find(".attrInput");
//        multFileUpload(capitalChange_AttFile__jxkxUploadFile);
//    }
//    //附件解析
//    function capitalChange_AttFile__jxkxUploadFile(data) {
//        var jsonData = eval(data);
//        for (var i = 0; i < jsonData.length; i++) {
//            $(capitalChange_AttFile_attrDom).append("<span onclick='removeFile(this)'>" + jsonData[i].attName
//                + "<input type='hidden' name='capitalChangeFiles' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
//        }
//    }
//    //删除附件
//    function removeFile(t) {
//        parent.$.messager.confirm('询问', '确定删除附件么？', function (b) {
//            if (b) {
//                $(t).remove();
//            }
//        });
//    }

    function saveCapitalChange(saveFlag) {
        $("#capitalChangeDataForm").find("#flag").val(saveFlag);
        <%--var payTempletList = JSON.stringify(getPayList('${paylist_model}'));--%>
//        $("#payTemplet").val(payTempletList);
        var capitalChangeForm = $("#capitalChangeDataForm").form('validate');
        if (!capitalChangeForm) {
            info("请填写必填项");
            return;
        }
        parent.$.messager.confirm('询问', '确认要保存数据？', function(b) {
            if (b) {
                $.ajax({
                    url: "/capitalChange/capitalChangeAdd",
                    type: 'POST',
                    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                    data: $("#capitalChangeDataForm").serialize(),
                    dataType: 'json',
                    success: function(data) {
                        if (!data.success) {
                            info(data.msg);
                            return;
                        }
                        info(data.msg);
                        $('#index_tabs').tabs('close', "保证金-操作");
                        capitalSummaryDataGrid.datagrid('reload');
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
