<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
        <div class="easyui-panel" title="售后信息" style="background:#fafafa;"
             data-options="iconCls:'fi-results',tools:'#Agentcapital_model_tools'">
            <div>
                <table class="grid" style="width: 100%">
                    <thead>
                    <tr>
                        <td><b>订单编号</b></td>
                        <td><b>商品</b></td>
                        <td><b>采购单价</b></td>
                        <td><b>退货数量</b></td>
                        <td><b>终端开始SN</b></td>
                        <td><b>终端结束SN</b></td>
                        <td><b>厂家</b></td>
                        <td><b>机型</b></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${returnDetails}" var="returnDetails">
                        <tr>
                            <td>${returnDetails.orderId}</td>
                            <td>${returnDetails.proName}</td>
                            <td>${returnDetails.orderPrice}</td>
                            <td>${returnDetails.returnCount}</td>
                            <td>${returnDetails.beginSn}</td>
                            <td>${returnDetails.endSn}</td>
                            <td>${returnDetails.proCom}</td>
                            <td>${returnDetails.proType}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div>
                <table class="grid" style="width: 100%;margin-top: 20px">
                    <tr>
                        <td colspan="4">退发票： <input disabled style="margin-left: 20px" type="radio" name="retInvoice" value="1" <c:if test="${returnOrder.retInvoice==1}">checked</c:if>>是 <input
                                disabled style="margin-left: 20px" type="radio" name="retInvoice" value="0" <c:if test="${returnOrder.retInvoice==0}">checked</c:if>>否
                        </td>
                    </tr>
                    <tr style="display: none;">
                        <td colspan="4">退收据： <input disabled style="margin-left: 20px" type="radio" name="retReceipt" value="1" <c:if test="${returnOrder.retReceipt==1}">checked</c:if>>是 <input
                                disabled style="margin-left: 20px" type="radio" name="retReceipt" value="0" <c:if test="${returnOrder.retReceipt==0}">checked</c:if>>否
                        </td>
                    </tr>
                    <tr>
                        <td>申请备注：${returnOrder.applyRemark}</td>
                    </tr>
                    <tr>
                        <td>退货扣款总金额：<span id="cutAmo" name="cutAmo">${returnOrder.cutAmo}</span> 元
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:cutDetail()">查看明细</a>
                        </td>
                    </tr>
                    <tr>
                        <td>退货机具总金额：${returnOrder.goodsReturnAmo} 元</td>
                    </tr>
                    <tr>
                        <td>应退款总金额：<span id="returnAmo"
                                         name="returnAmo">${returnOrder.returnAmo}</span>
                            元
                        </td>
                    </tr>
                    <tr>
                        <td>
                            抵扣欠款金额：${returnOrder.takeOutAmo} 元
                            &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:adjustD()">查看明细</a>
                        </td>
                    </tr>
                    <tr>
                        <td>线下退款金额：${returnOrder.relReturnAmo} 元
                        </td>
                    </tr>
                    </tbody>
                </table>
                <%@ include file="/commons/invoice_show_common.jsp"%>
            </div>
        </div>


            <%--已排单列表--%>
            <div class="easyui-layout" data-options="fit:false,border:false" style="height: 200px">
                <div data-options="region:'center',fit:false,border:false">
                    <table class="grid" data-options="fit:false,border:false">
                        <tr>
                            <td><b>排单信息</b></td>
                        </tr>
                    </table>
                    <table id="paidan_complate" data-options="fit:false,border:false">
                    </table>
                </div>
            </div>

        <%--物流信息--%>
        <div class="easyui-layout" data-options="fit:false,border:false" style="height: 200px">
            <div data-options="region:'center',fit:false,border:false">
                <table class="grid" data-options="fit:false,border:false">
                    <tr>
                        <td><b>物流信息</b></td>
                    </tr>
                </table>
                <table id="wuliu" data-options="fit:false,border:false">
                </table>
            </div>
        </div>

<div id="dd_kk_${returnOrder.id}" class="easyui-dialog" title="扣款明细" style="width:800px;height:500px;"
     data-options="iconCls:'icon-save',resizable:true,closed:true">
    <table class="grid" style="width: 100%;margin-top: 20px">
        <tr>
            <td><b>扣款类型</b></td>
            <td><b>扣款金额</b></td>
        </tr>
        <c:forEach items="${deductCapitals}" var="deductCapitals">
            <tr>
                <td>
                    <c:forEach items="${cType}" var="cType">
                        <c:if test="${deductCapitals.cType == cType.dItemvalue}">
                            ${cType.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${deductCapitals.cAmount} 元</td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="dd_qk_${returnOrder.id}" class="easyui-dialog" title="欠款明细" style="width:800px;height:500px;"
     data-options="iconCls:'icon-save',resizable:true,closed:true">
    <table class="grid" style="width: 100%;margin-top: 20px">
        <tr>
            <td><b>订单编号</b></td>
            <td><b>欠款金额</b></td>
        </tr>
        <c:forEach items="${takeoutList}" var="takeoutList">
            <tr>
                <td>${takeoutList.orderId}</td>
                <td>${takeoutList.payAmt} 元</td>
            </tr>
        </c:forEach>
    </table>
</div>


<div id="dd_tz_${returnOrder.id}" class="easyui-dialog" title="抵扣欠款明细" style="width:800px;height:500px;"
     data-options="iconCls:'icon-save',resizable:true,closed:true">
    <table class="grid" style="width: 100%;margin-top: 20px">
        <tr>
            <td>订单编号</td>
            <%--<td>付款类型</td>--%>
            <td>付款单编号</td>
            <td>抵充金额</td>
            <td>原分期批次号</td>
            <td>新分期批次号</td>
        </tr>

        <c:forEach items="${oAccountAdjusts.details}" var="details">
            <tr>
                <td>${details.orderId}</td>
                <%--<td>--%>
                    <%--<c:forEach items="${paymentType}" var="paymentType">--%>
                        <%--<c:if test="${details.payType == paymentType.dItemvalue}">--%>
                            <%--${paymentType.dItemname}--%>
                        <%--</c:if>--%>
                    <%--</c:forEach>--%>
                <%--</td>--%>
                <td>${details.paymentDetailId}</td>
                <td>${details.takeOutAmount}</td>
                <td>${details.batchCodeOld}</td>
                <td>${details.batchCodeNew}</td>
            </tr>
        </c:forEach>

    </table>
</div>



<form id="receipPlanQueryForm" method="post">
    <input type="hidden" name="returnId" value="${returnOrder.id}">
</form>

<script type="text/javascript">

    var returnId = '${returnOrder.id}';


    //扣款明细对话框
    function cutDetail() {
        $('#dd_kk_${returnOrder.id}').dialog('open');
    }

    //调账明细对话框
    function adjustD() {
        $('#dd_tz_${returnOrder.id}').dialog('open');
    }

    //已排单查询
    var receipPlanDataGrid_a;
    $(function () {
        receipPlanDataGrid_a = $('#paidan_complate').datagrid({
            url: '${path }/receiptPlanReturn/list?returnId=' + returnId,
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '排单编号',
                field: 'PLAN_NUM'
            }, {
                title: '排单状态',
                field: 'PLAN_ORDER_STATUS',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 3:
                            return '未发货';
                        case 2:
                            return '已发货';
                        case 1:
                            return '已排单';
                    }
                }
            }, {
                title: '订单编号',
                field: 'ORDER_ID',
            }, {
                title: '商品编号',
                field: 'PRO_CODE'
            }, {
                title: '商品名称',
                field: 'PRO_NAME'
            }, {
                title: '商品数量',
                field: 'PRO_NUM'
            }, {
                title: '已排单数量',
                field: 'SEND_NUM'
            }, {
                title: '订货厂家',
                field: 'PRO_COM'
            }, {
                title: '订货数量',
                field: 'PLAN_PRO_NUM'
            }, {
                title: '发货数量',
                field: 'SEND_PRO_NUM'
            }, {
                title: '机型',
                field: 'MODEL'
            }, {
                title: '计划发货日期',
                field: 'SEND_DATE'
            }, {
                title: '实际发货时间',
                field: 'REAL_SEND_DATE'
            }, {
                title: '收货人姓名',
                field: 'ADDR_REALNAME'
            }, {
                title: '收货人联系电话',
                field: 'ADDR_MOBILE'
            }, {
                title: '省',
                field: 'NAME'
            }, {
                title: '市',
                field: 'CITY'
            }, {
                title: '区',
                field: 'DISTRICT'
            }, {
                title: '详细地址',
                field: 'ADDR_DETAIL'
            }, {
                title: '地址备注',
                field: 'REMARK'
            }, {
                title: '邮编',
                field: 'ZIP_CODE'
            }, {
                title: '快递备注',
                field: 'EXPRESS_REMARK'
            }, {
                title: '收货单状态',
                field: 'RECEIPT_STATUS'
            }, {
                title: '排单创建时间',
                field: 'C_DATE'
            }, {
                title: '退货子订单编号',
                field: 'RETURN_ORDER_DETAIL_ID'
            }]]
        });
    });


    //物流信息
    var logisticsRefund_a;
    $(function () {
        logisticsRefund_a = $('#wuliu').datagrid({
            url: '${path }/logistics/oLogisticsListRefund?returnId=' + returnId,
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '排单编号',
                field: 'PLAN_NUM'
            }, {
                title: '排单状态',
                field: 'PLAN_ORDER_STATUS',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 3:
                            return '未发货';
                        case 2:
                            return '已发货';
                        case 1:
                            return '已排单';
                    }
                }
            }, {
                title: '订单编号',
                field: 'ORDER_ID',
            }, {
                title: '商品编号',
                field: 'PRO_CODE'
            }, {
                title: '商品名称',
                field: 'PRO_NAME'
            }, {
                title: '商品数量',
                field: 'PRO_NUM'
            }, {
                title: '已排单数量',
                field: 'SEND_NUM'
            }, {
                title: '订货厂家',
                field: 'PRO_COM'
            }, {
                title: '订货数量',
                field: 'PLAN_PRO_NUM'
            }, {
                title: '发货数量',
                field: 'SEND_PRO_NUM'
            }, {
                title: '机型',
                field: 'MODEL'
            }, {
                title: '计划发货日期',
                field: 'SEND_DATE'
            }, {
                title: '实际发货时间',
                field: 'REAL_SEND_DATE'
            }, {
                title: '收货人姓名',
                field: 'ADDR_REALNAME'
            }, {
                title: '收货单状态',
                field: 'RECEIPT_STATUS'
            }, {
                title: '退货子订单编号',
                field: 'RETURN_ORDER_DETAIL_ID'
            }, {
                title: '物流公司',
                field: 'LOG_COM'
            }, {
                title: '物流类型',
                field: 'LOG_TYPE',
                formatter: function (value, row, index) {
                    switch (value) {
                        case "2":
                            return '退货物流';
                        case "1":
                            return '发货物流';
                    }
                }
            }, {
                title: '物流单号',
                field: 'W_NUMBER'
            }, {
                title: '起始SN序列号',
                field: 'SN_BEGIN_NUM'
            }, {
                title: '结束SN序列号',
                field: 'SN_END_NUM'
            }]],
            toolbar: '#logisticsToolbar'
        });
    });



</script>
