<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript"></script>
<div>
    <c:if test="${not empty data}">
        <div class="easyui-panel" title="订单信息" style="width: 100%;background:#fafafa;" data-options="">
            <form id="queryOrderInfo_model_form">
                <table class="grid">
                    <tbody>
                    <tr>
                        <td style="width: 100px;text-align: right;">代理商:</td><td>${data.agent.agName}</td>
                        <td style="width: 100px;text-align: right;">代理商唯一编码:</td><td>${data.parentInfo.AAGENTID}</td>
                        <td style="width: 100px;text-align: right;">业务平台编码:</td><td>${data.parentInfo.ABUSNUM}</td>
                        <td style="width: 100px;text-align: right;">订单编号:</td><td>${data.order.oNum}</td>
                    </tr>
                    <tr>
                        <td style="width: 100px;text-align: right;">金额:</td><td>${data.order.oAmo}/元</td>
                        <td style="width: 100px;text-align: right;">差价:</td><td>${data.order.incentiveAmo}/元</td>
                        <td style="width: 100px;text-align: right;">应付:</td><td>${data.order.payAmo}/元</td>
                        <td style="width: 100px;text-align: right;">下单日期:</td><td><fmt:formatDate pattern="yyyy-MM-dd" value="${data.order.cTime}"/></td>
                    </tr>
                    <tr>
                        <td style="width: 100px;text-align: right;">状态:</td><td><c:forEach items="${orderStatus}" var="orderStatusItem">
                        <c:if test="${data.order.orderStatus == orderStatusItem.dItemvalue}">${orderStatusItem.dItemname}</c:if></c:forEach></td>
                        <td style="width: 100px;text-align: right;">备注:</td><td colspan="3" style="color: red;">${data.order.remark}</td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <div class="easyui-panel" title="商品信息" style="width: 100%;background:#fafafa;" data-options="">
            <table class="grid" id="queryProductInfo_model_form">
                <tbody>
                <tr>
                    <td style="width: 50px;text-align: center;">编号:</td>
                    <td style="width: 50px;text-align: center;">名称:</td>
                    <td style="width: 50px;text-align: center;">类型:</td>
                    <td style="width: 50px;text-align: center;">单价:</td>
                    <td style="width: 50px;text-align: center;">数量:</td>
                    <td style="width: 50px;text-align: center;">活动:</td>
                    <td style="width: 50px;text-align: center;">可变更活动:</td>
                    <td style="width: 50px;text-align: center;">操作:</td>
                </tr>
                <c:forEach items="${data.oSubOrders}" var="oSubOrdersItem" varStatus="status">
                    <tr>
                        <td style="text-align: center;">${oSubOrdersItem.proCode}</td>
                        <td style="text-align: center;">${oSubOrdersItem.proName}</td>
                        <td style="text-align: center;">${oSubOrdersItem.proType}</td>
                        <td style="text-align: center;">${oSubOrdersItem.proRelPrice}/元</td>
                        <td style="text-align: center;">${oSubOrdersItem.proNum}/件</td>
                        <td style="text-align: center;">
                            <c:forEach items="${data.sSubOrderActivitys}" var="sSubOrderActivitysItem">
                                <c:if test="${sSubOrderActivitysItem.subOrderId==oSubOrdersItem.id}">
                                    ${sSubOrderActivitysItem.activityName}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td style="text-align: center;">
                            <input type="hidden" name="agentId" value="${data.parentInfo.AAGENTID}">
                            <input type="hidden" name="orderId" value="${oSubOrdersItem.orderId}">
                            <input type="hidden" name="id" value="${oSubOrdersItem.id}">

                            <select name="activityName" style="width:200px;height:21px">
                                <c:forEach items="${data.oActivityLists[status.count-1]}" var="oActivityListItem">
                                    <option value="${oActivityListItem.id}"
                                            <c:if test="${sSubOrderActivitysItem.activityId==oActivityListItem.id}">selected="selected"</c:if>>
                                            ${oActivityListItem.activityName}/${oActivityListItem.actCode}
                                            /${oSubOrdersItem.proName}-${oSubOrdersItem.proCode}-${oSubOrdersItem.proType}
                                            /${oActivityListItem.price}元/${oActivityListItem.vender}/${oActivityListItem.proModel}
                                            /${oActivityListItem.posType}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td style="text-align: center;">
                            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 60px;"
                               data-options="iconCls:'fi-save',region:'center'" onclick="saveChangeActivity(this)">保存</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="easyui-panel" title="物流信息" style="width: 100%;background:#fafafa;" data-options="">
            <form id="queryLogisticsInfo_model_form">
                <table class="grid" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;">
                    <tbody>
                    <c:forEach items="${data.oReceiptOrders}" var="oReceiptOrdersItem">
                        <tr>
                            <td>收货单编号:</td><td>${oReceiptOrdersItem.receiptNum}</td>
                            <td>收货人:</td><td>${oReceiptOrdersItem.addrRealname}:${oReceiptOrdersItem.addrMobile}</td>
                            <td>地址:</td><td><agent:show type="region" busId="${oReceiptOrdersItem.addrDistrict}"/>(${oReceiptOrdersItem.addrDetail})</td>
                            <td>备注:</td><td>${oReceiptOrdersItem.remark}</td>
                            <td>
                                <table class="grid" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px;color:lightgrey; border-style:hidden;">
                                    <tbody>
                                    <tr>
                                        <td style="width: 50px;">商品:</td>
                                        <td style="width: 50px;">名称:</td>
                                        <td style="width: 50px;">数量:</td>
                                        <td style="width: 50px;">已排单数:</td>
                                    </tr>
                                    <c:forEach items="${data.oReceiptPros}" var="oReceiptProsItem">
                                        <c:if test="${oReceiptProsItem.receiptId == oReceiptOrdersItem.id}">
                                            <tr>
                                                <td>${oReceiptProsItem.proCode}</td>
                                                <td>${oReceiptProsItem.proName}</td>
                                                <td>${oReceiptProsItem.proNum}/件</td>
                                                <td>${oReceiptProsItem.sendNum}</td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
        <div class="easyui-panel" title="结算信息" style="width: 100%;background:#fafafa;" data-options="">
            <form id="queryPaymentInfo_model_form">
                <table class="grid">
                    <tbody>
                    <tr>
                        <td>支付方式:</td>
                        <td>金额:</td>
                        <td>已付:</td>
                        <td>待付:</td>
                        <td>首付:</td>
                        <td>分期期数:</td>
                        <td>分期日:</td>
                        <td>担保代理:</td>
                        <td>结算价:</td>
                        <td>分润模板:</td>
                        <td>抵扣类型:</td>
                        <td>抵扣金额:</td>
                        <td>是否开具发票:</td>
                    </tr>
                    <tr>
                        <td>
                            <select name="payMethod"  disabled="true" id="paymentType">
                                <c:forEach items="${settlementType}" var="settlementTypeItem">
                                    <option value="${settlementTypeItem.dItemvalue}"
                                            <c:if test="${data.oPayment.payMethod==settlementTypeItem.dItemvalue}">selected="selected"</c:if>
                                            ${settlementTypeItem.dItemname}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>${data.oPayment.payAmount}/元</td>
                        <td>${data.oPayment.realAmount}/元</td>
                        <td>${data.oPayment.outstandingAmount}/元</td>
                        <td>${data.oPayment.downPayment}/元</td>
                        <td>${data.oPayment.downPaymentCount}/期</td>
                        <td>
                            <c:if test="${!empty data.oPayment.downPaymentDate}">
                                <fmt:formatDate pattern="yyyy-MM-dd" value="${data.oPayment.downPaymentDate}" />
                            </c:if>
                        </td>
                        <td><agent:show type="agent" busId="${data.oPayment.guaranteeAgent}"></agent:show></td>
                        <td>${data.oPayment.settlementPriceStr}</td>
                        <td>${data.oPayment.shareTemplet}</td>
                        <td>
                            <c:forEach items="${capitalType}" var="capitalTypeItem">
                                <c:if test="${capitalTypeItem.dItemvalue==data.oPayment.deductionType}">${capitalTypeItem.dItemname}</c:if>
                            </c:forEach>
                        </td>
                        <td>${data.oPayment.deductionAmount}/元</td>
                        <td>
                            <c:choose>
                                <c:when test="${data.oPayment.isCloInvoice==1}">
                                    是
                                </c:when>
                                <c:when test="${data.oPayment.isCloInvoice==0}">
                                    否
                                </c:when>
                                <c:otherwise>
                                    无
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <c:if test="${!empty data.platForm && (data.platForm.platformType=='RDBPOS' || data.platForm.platformType=='RHPOS')}">
                        <c:if test="${data.oPayment.payMethod=='FRFQ' || data.oPayment.payMethod=='SF1'}">
                            <tr>
                                <td>分润形式:</td>
                                <td colspan="2">
                                    <c:forEach items="${data.oPayment_ProfitForm}" var="ProfitForm_type_Item">
                                        <c:if test="${ProfitForm_type_Item==1}">日分润&nbsp;&nbsp;</c:if>
                                        <c:if test="${ProfitForm_type_Item==2}">日返现&nbsp;&nbsp;</c:if>
                                        <c:if test="${ProfitForm_type_Item==3}">下级代扣</c:if>
                                    </c:forEach>
                                </td>
                                <td>存量分润(月分润):</td>
                                <td colspan="9">
                                    <c:if test="${data.oPayment.profitMouth=='1'}">是</c:if>
                                    <c:if test="${data.oPayment.profitMouth=='0'}">否</c:if>
                                </td>
                            </tr>
                        </c:if>
                    </c:if>
                    <tr>
                        <td>附件:</td>
                        <td colspan="12">
                            <c:if test="${!empty data.attrs}">
                                <c:forEach items="${data.attrs}" var="attrsItem">
                                    <a href="${imgPath}${attrsItem.attDbpath}" target="_blank">${attrsItem.attName}</a>
                                </c:forEach>
                            </c:if>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
            <%@ include file="/commons/order_cash_receivables_show.jsp"%>
            <c:if test="${data.oPaymentDetails.size() > 0}">
                <span style="color: red;">分期数据</span>
                <table class="grid">
                    <tbody>
                    <tr>
                        <td>类型:</td>
                        <td>付款金额:</td>
                        <td>实际付款:</td>
                        <td>计划日期(自然月):</td>
                        <td>付款状态:</td>
                    </tr>
                    <c:forEach items="${data.oPaymentDetails}" var="oPaymentDetailsItem">
                        <tr>
                            <td>
                                <c:forEach items="${paymentType}" var="paymentTypeItem">
                                    <c:if test="${oPaymentDetailsItem.payType==paymentTypeItem.dItemvalue}">${paymentTypeItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                            <td>${oPaymentDetailsItem.payAmount}/元</td>
                            <td>
                                <c:if test="${!empty oPaymentDetailsItem.realPayAmount}">
                                    ${oPaymentDetailsItem.realPayAmount}/元
                                </c:if>
                                <c:if test="${empty oPaymentDetailsItem.realPayAmount}">
                                    0/元
                                </c:if>
                            </td>
                            <td>
                                <fmt:formatDate pattern="yyyy-MM-dd" value="${oPaymentDetailsItem.planPayTime}" />
                                <c:if test="${!empty oPaymentDetailsItem.payTime}">
                                    实际付款:<fmt:formatDate pattern="yyyy-MM-dd" value="${oPaymentDetailsItem.payTime}" />
                                </c:if>
                                <c:if test="${!empty oPaymentDetailsItem.srcType}">
                                    (
                                    <c:forEach items="${paymentSrcTypes}" var="paymentSrcTypeItem">
                                        <c:if test="${oPaymentDetailsItem.srcType==paymentSrcTypeItem.dItemvalue}">${paymentSrcTypeItem.dItemname}</c:if>
                                    </c:forEach>
                                    )
                                </c:if>
                            </td>
                            <td>
                                <c:forEach items="${paymentStatus}" var="paymentStatusItem">
                                    <c:if test="${oPaymentDetailsItem.paymentStatus==paymentStatusItem.dItemvalue}">${paymentStatusItem.dItemname}</c:if>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </c:if>
</div>
<script type="application/javascript">
    //获取form数据
    function saveChangeActivity(o) {
        try {
            var oNum = $(o).parent().parent().find("input[name='orderId']").val();
            var subOrderId = $(o).parent().parent().find("input[name='id']").val();
            var activityId = $(o).parent().parent().find("select[name='activityName'] option:selected").val();
            parent.$.messager.confirm('询问', '确认要变更活动？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: basePath+"/orderbuild/orderChangeActivity?oNum="+oNum+"&subOrderId="+subOrderId+"&activityId="+activityId,
                        dataType: 'json',
                        contentType: 'application/json;charset=UTF-8',
                        beforeSend: function() {
                            progressLoad();
                        },
                        success: function(msg) {
                            if (msg.status=='200') {
                                progressClose();
                                info(msg.msg);
                                $('#index_tabs').tabs('close', "订单修改-活动变更");
                                all_orderList.datagrid('reload');
                            } else {
                                progressClose();
                                info(msg.msg);
                            }
                        },
                        complete: function(XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
        } catch(e) {
            //异常统一提示
            if (e.code==1) {
                info(e.msg);
            }
        }
    }


    function get_ProductInfoTable_Form() {
        var formDataJson = [];
        $('#queryProductInfo_model_form').find('tr').each(function(i){
            if (i >= 1) {
                var data = {};
                data.id = $(this).find("input[name='agName']").val();
                data.id = $(this).find("input[name='AAGENTID']").val();
                formDataJson.push(data);
            }
        });
        return formDataJson;
    }

    function get_queryOrderInfo_FormData() {
        return queryOrderData = $.serializeObject($("#queryOrderInfo_model_form"));
    }
</script>
