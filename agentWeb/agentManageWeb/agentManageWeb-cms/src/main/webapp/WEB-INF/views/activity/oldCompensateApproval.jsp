<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs">
    <div title="待审信息">
        <div class="easyui-panel" title="补差价信息"  data-options="iconCls:'fi-results'">
            <table class="grid">
                <%--<tr>--%>
                    <%--<td>申请补差价类型：</td>--%>
                    <%--<td>${oRefundPriceDiff.applyCompName}</td>--%>
                    <%--<td>申请补差价金额：</td>--%>
                    <%--<td>${oRefundPriceDiff.applyCompAmt}</td>--%>
                    <%--<td>实际补差价类型：</td>--%>
                    <%--<td>${oRefundPriceDiff.relCompName}</td>--%>
                    <%--<td>实际补差价金额：</td>--%>
                    <%--<td>${oRefundPriceDiff.relCompAmt}</td>--%>
                <%--</tr>--%>
                <tr>
                    <td >申请备注：</td>
                    <td colspan="9">${oRefundPriceDiff.applyRemark}</td>
                </tr>
                <tr>
                    <c:if test="${!empty oRefundPriceDiff.attachmentList}">
                        <td>代理商附件名称：</td>
                        <c:forEach items="${oRefundPriceDiff.attachmentList}" var="attachment">
                            <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">${attachment.attName}</a></td>
                            <td><a href="<%=imgPath%>${attachment.attDbpath}" class="easyui-linkbutton" data-options="plain:true" target="_blank" >查看附件</a></td>
                        </c:forEach>
                    </c:if>
                </tr>
            </table>
        </div>
        <div class="easyui-panel" title="活动信息"  data-options="iconCls:'fi-results'">
            <table class="grid bcjData">
                <c:forEach items="${oRefundPriceDiff.refundPriceDiffDetailList}" var="refundPriceDiffDetail">
                    <tr>
                        <td width="80px">代理商编号：</td>
                        <td width="240px">${refundPriceDiffDetail.agentId}</td>
                        <td width="80px">SN开始：</td>
                        <td width="80px">${refundPriceDiffDetail.beginSn}</td>
                        <td width="80px">SN结束：</td>
                        <td width="80px">${refundPriceDiffDetail.endSn}</td>
                        <td width="60px">数量：</td>
                        <td width="60px">${refundPriceDiffDetail.changeCount}</td>
                        <td width="60px">商品：</td>
                        <td width="60px">${refundPriceDiffDetail.proName}</td>
                        <td width="60px">旧活动：</td>
                        <td width="60px">${refundPriceDiffDetail.activityFront.activityName}</td>
                        <td width="60px">新活动：</td>
                        <td width="60px">${refundPriceDiffDetail.activityName}</td>
                    </tr>
                    <shiro:hasPermission name="/oldCom/busWrite">
                    <tr class="busWriteClass">
                        <td width="80px">订单号：</td>
                        <td width="100px">
                            <input name="id" type="hidden" value="${refundPriceDiffDetail.id}">
                            <input name="orderId" maxlength="32" type="text" placeholder="请输入"
                                   class="easyui-validatebox" data-options="required:true" style="width:160px;" value="">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true"
                               onclick="searchOldCompensate(this);">查询</a>
                        </td>
                        <td>商品名称：</td>
                        <td>
                            <select name="productId" style="width: 125px">

                            </select>
                        </td>
                        <td>活动名称：</td>
                        <td id="queryAct"></td>
                    </tr>
                    </shiro:hasPermission>
                </c:forEach>
            </table>
        </div>
        <%--业务扣款--%>
        <%@ include file="/commons/orderRefundPriceDiff_bus_model.jsp" %>
        <%@ include file="/commons/order_cash_receivables_show.jsp"%>
        <shiro:hasPermission name="/order/withholdSee">
            <%--财务查看扣款--%>
            <%@ include file="/commons/orderRefundPriceDiff_financeSee_model.jsp" %>
            <c:if test="${not empty paylist}">
                <%--财务核款实际到账时间--%>
                <%@ include file="/commons/order_cash_receivables_approve.jsp"%>
            </c:if>
        </shiro:hasPermission>
        <%--财务查看扣款--%>
        <%@ include file="/commons/orderRefundPriceDiff_finance_model.jsp" %>
        <%--审批意见--%>
        <%@ include file="/commons/approval_opinion_compensate.jsp" %>
        <shiro:hasPermission name="/order/compensateEdit">
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="editCompensateApproval('${agentBusId}')" >修改</a>
        </shiro:hasPermission>
        <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="submitCompensateApproval()" >提交</a>
    </div>
    <shiro:hasPermission name="/agActivity/approvalRecordSee">
    <div title="审批记录">
        <%@ include file="/commons/approval_record.jsp" %>
    </div>
    </shiro:hasPermission>
    <div title="审批流程图">
        <shiro:hasPermission name="/agActivity/approvalRecordImgSee">
            <img src="/agActivity/approvalImage?taskId=${taskId}" />
        </shiro:hasPermission>
    </div>
</div>
<script>
    var kcFlag = 0;
    function submitCompensateApproval() {
        var subApprovalTable = (typeof get_approval_For_compenstate === "function")?get_approval_For_compenstate():{};
        var refundPriceDiffTable = (typeof get_addRefundPriceDiffTable_FormData === "function")?get_addRefundPriceDiffTable_FormData():[];
        var oRefundPriceDiffJson = (typeof get_finance_check_Table_FormDataItem === "function")?get_finance_check_Table_FormDataItem():{};
        var oRefundPriceDiffFileJson = (typeof get_finance_check_Table_files === "function")?get_finance_check_Table_files():[];
        var oCashReceivablesVoList = (typeof getApproveCashPayListRealRecTime === "function")?getApproveCashPayListRealRecTime("${paylist_model}"):[];

        if(refundPriceDiffTable!=[]){
            var applyCompAmt = "${oRefundPriceDiff.applyCompAmt}";
            var kcSumAmt = 0;
            console.info(refundPriceDiffTable);
            for(var i=0;i<refundPriceDiffTable.length;i++){
                var cAmountStr = (refundPriceDiffTable[i].cAmount);
                kcSumAmt = (parseFloat(kcSumAmt))+(parseFloat(cAmountStr));
            }
            if(parseFloat(kcSumAmt)>parseFloat(applyCompAmt)){
                info("扣除金额不能大于申请金额");
                return false;
            }
        }

        if((typeof get_finance_check_Table_FormDataItem === "function")){
            if("${oRefundPriceDiff.applyCompType}"=='1'){
                var gatherTimeStr = oRefundPriceDiffJson.gatherTimeStr;
                var gatherAmt = oRefundPriceDiffJson.gatherAmt;
                if(gatherTimeStr=='' || gatherTimeStr==undefined){
                    info("请填写收款时间");
                    return;
                }
                if(gatherAmt=='' || gatherAmt==undefined){
                    info("请填写收款金额");
                    return;
                }
            }
        }
        var formDataJson = [];
        <shiro:hasPermission name="/oldCom/busWrite">
        $(".bcjData .busWriteClass").each(function(){
            var data = {};
            data.id = $(this).find("input[name='id']").val();
            data.orderId = $(this).find("input[name='orderId']").val();
            data.subOrderId =$(this).find("select[name='productId']").find("option:checked").val();
            formDataJson.push(data);
        });
        </shiro:hasPermission>
        parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/compensate/taskApproval",
                    dataType:'json',
                    contentType:'application/json;charset=UTF-8',
                    data: JSON.stringify({
                        approvalOpinion:subApprovalTable.approvalOpinion,
                        approvalResult:subApprovalTable.approvalResult,
                        taskId:subApprovalTable.taskId,
                        deductCapitalList:refundPriceDiffTable,
                        oRefundPriceDiffVo:oRefundPriceDiffJson,
                        refundPriceDiffFinanceFile:oRefundPriceDiffFileJson,
                        agentBusId:"${agentBusId}",
                        oCashReceivablesVoList:oCashReceivablesVoList,
                        flag:kcFlag,
                        refundPriceDiffDetailList:formDataJson
                    }),
                    beforeSend:function(){
                        progressLoad();
                    },
                    success: function(msg){
                        info(msg.resInfo);
                        if(msg.resCode=='1'){
                            $('#index_tabs').tabs('close',"处理任务");
                            activityDataGrid.datagrid('reload');
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }

    function editCompensateApproval(agentBusId) {
        addTab({
            title : '补差价-修改',
            border : false,
            closable : true,
            fit : true,
            href:'/compensate/toCompensateAmtEditPage?id='+agentBusId
        });
    }

    function searchOldCompensate(o) {
       var orderId = $(o).parent().find("input[name='orderId']").val();
       if(orderId=='' || orderId==undefined){
           info("请填写订单号");
           return false;
       }
        $.ajaxL({
            type: "POST",
            url: "/oldCompensate/queryOrder",
            dataType:'json',
            data: {
                orderId: orderId
            },
            beforeSend:function(){
                progressLoad();
            },
            success: function(result){
                if(result.status!=200){
                    info(result.msg);
                    return false;
                }
                var str = '<option value="">---请选择---</option>';
                $.each(result.data,function(n,b) {
                    str+='<option value="'+b.id+'">'+b.proName+'</option>';
                });
                var product = $(o).parent().parent().find("select[name='productId']");
                product.html(str);

                product.change(function(){
                    var subOrderId = $(this).val();
                    $.ajaxL({
                        type: "POST",
                        url: "/oldCompensate/queryActivity",
                        dataType:'json',
                        traditional:true,//这使json格式的字符不会被转码
                        data: {
                            subOrderId: subOrderId
                        },
                        beforeSend : function() {
                            progressLoad();
                        },
                        success: function(result){
                            if(result.status!=200){
                                info(result.msg);
                                return false;
                            }
                            $(o).parent().parent().find("#queryAct").html(result.data.activityName);
                        },
                        complete:function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                });

            },
            complete:function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }
</script>
