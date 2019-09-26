<shiro:hasPermission name="/order/withholdSee">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="easyui-panel" title="财务审批款项" data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
    <form id="refundPriceDiff_model_form">
    </form>
</div>
<div id="refundPriceDiff_finance_model_templet">
    <table class="grid" id="finance_model_templet">
        <tbody>
            <%--<c:if test="${oRefundPriceDiff.applyCompType=='1'}">--%>
                <tr>
                    <td>核款时间：</td>
                    <td>
                        <input name="gatherTimeStr" type="text" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${system_now_date}" />"  class="easyui-datetimebox"  style="width:160px;" >
                    </td>
                    <td>收款金额：</td>
                    <td>
                        <input name="gatherAmt" type="text" maxlength="11" precision="2" class="easyui-numberbox"  style="width:160px;" />
                    </td>
                </tr>
            <%--</c:if>--%>
            <%--<c:if test="${oRefundPriceDiff.applyCompType=='2'}">--%>
                <%--<tr>--%>
                    <%--<td colspan="4">扣款金额：--%>
                        <%--<a id="kkAmt">${oRefundPriceDiff.deductAmt}</a>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td colspan="4">可抵扣机具欠款金额：--%>
                        <%--<a id="jjqkAmt">${machineDebtAmt}</a>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td colspan="4">实际退款金额：--%>
                        <%--<a id="sjtkAmt">--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${fn:contains(oRefundPriceDiff.relCompAmt-machineDebtAmt,'-')==true}">--%>
                                    <%--0--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--${oRefundPriceDiff.relCompAmt-machineDebtAmt}--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                        <%--</a>--%>
                        <%--<c:if test="${machineDebtAmt!=0}">--%>
                            <%--<a href="javascript:void(0)" class="easyui-linkbutton" style="width: 120px;margin-left: 50px" data-options="iconCls:'fi-save'"  onclick="executeDeductAmt()">执行欠款抵扣</a>--%>
                        <%--</c:if>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<c:if test="${machineDebtAmt==0}">--%>
                    <%--<tr>--%>
                        <%--<td colspan="4">--%>
                            <%--打款凭证：--%>
                            <%--<a class="attrInput">--%>
                            <%--</a>--%>
                            <%--<a href="javascript:void(0)" class="busck-easyui-linkbutton-edit"--%>
                               <%--data-options="plain:true,iconCls:'fi-magnifying-glass'"--%>
                               <%--onclick="compenstate_finance_AttFile(this)">添加附件</a>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</c:if>--%>
            <%--</c:if>--%>
        </tbody>
    </table>
</div>
<script>
    var com_finance_AttFile_attrDom;
    //上传窗口
    function compenstate_finance_AttFile(t) {
        com_finance_AttFile_attrDom = $(t).parent().find(".attrInput");
        multFileUpload(comp_finance_AttFile_UploadFile);
    }
    //附件解析
    function comp_finance_AttFile_UploadFile(data) {
        var jsonData = eval(data);
        for (var i = 0; i < jsonData.length; i++) {
            $(com_finance_AttFile_attrDom).append("<span onclick='removeFinanceFile(this)'>" + jsonData[i].attName + "<input type='hidden' name='compenstateFinanceFile' value='" + jsonData[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
        }
    }

    function removeFinanceFile(t){
        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
            if (b) {
                $(t).remove();
            }
        });
    }

    //解析打个table
    function get_finance_check_Table_FormDataItem(){
        var data = {};
        data.gatherTimeStr = $("#finance_model_templet").find("input[name='gatherTimeStr']").val();
        data.machOweAmt = $("#finance_model_templet").find("#jjqkAmt").html();
        data.gatherAmt = $("#finance_model_templet").find("input[name='gatherAmt']").val();
        return data;
    }

    function get_finance_check_Table_files() {
        var data = [];
        var files = $("#finance_model_templet").find(".attrInput").find("input[name='compenstateFinanceFile']");
        for(var j=0;j<files.length;j++){
            data.push($(files[j]).val());
        }
        return data;
    }


    function executeDeductAmt() {
        var adjustAmt = "${oRefundPriceDiff.relCompAmt}";
        if(adjustAmt=='' || adjustAmt==undefined){
            info("扣款金额有误!");
            return false;
        }
        parent.$.messager.confirm('询问', '确认要执行抵扣？', function(b) {
            if (b) {
                $.ajax({
                    url :"${path}/compensate/adjust",
                    type:'POST',
                    data:{
                        isRealAdjust:true,
                        adjustAmt:adjustAmt,
                        busId:"${agentBusId}"
                    },
                    dataType:'json',
                    success:function(result){
                        info(result.msg);
                        if(result.success)
                        kcFlag = 1;  //扣款成功标记
                    },
                    error:function(data){
                        info("系统异常，请联系管理员！");
                    }
                });
            }
        });
    }
</script>
</shiro:hasPermission>