<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<%--查看界面--%>
<div id="old_order_return_bus_edit" class="easyui-panel" title="退货信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <input hidden="hidden" value="${old_order_return_info.id}" id="old_order_return_bus_edit_returnId" />
    <table class="grid" style="width: 100%;text-align: center;" id="old_order_return_bus_edit_table" >
        <tr>
            <td >退货子订单</td>
            <td >开始SN</td>
            <td >结束SN</td>
            <td >数量</td>
            <td >订单号</td>
            <td >商品</td>
            <td >采购单价</td>
        </tr>
        <c:forEach items="${old_order_return_info_detail}" var="old_order_return_info_detail_item">
        <tr id="${old_order_return_info_detail_item.id}" return_count="${old_order_return_info_detail_item.returnCount}" tr_return_info="${old_order_return_info.id}">
            <td >${old_order_return_info_detail_item.id}</td>
            <td >${old_order_return_info_detail_item.beginSn}</td>
            <td >${old_order_return_info_detail_item.endSn}</td>
            <td >${old_order_return_info_detail_item.returnCount}</td>
            <td >
                <input value="${old_order_return_info_detail_item.orderId}" name="orderid" style="width: 80px;" id="${old_order_return_info_detail_item.id}_orderid"/>
            </td>
            <td >
                <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_productid" name="productid">
                    <c:forEach items="${products}" var="productsItem">
                        <option value="${productsItem.id}" <c:if test="${productsItem.id==old_order_return_info_detail_item.proId}" >selected="selected"</c:if> >${productsItem.proName}</option>
                    </c:forEach>
                </select>
            </td>
            <td >
                <input value="${old_order_return_info_detail_item.proPrice}" style="width: 50px;" id="${old_order_return_info_detail_item.id}_proprice" name="proprice"/>
            </td>
            </c:forEach>
    </table>
    <script type="application/javascript">
        //===========================================已排单查询
        var returnId = "${old_order_return_info.id}";
        var old_order_return_bus_paidan_complate_table = $('#old_order_return_bus_paidan_complate_table').datagrid({
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
                title: '本次排单数量',
                field: 'PLAN_PRO_NUM'
            }, {
                title: '订货厂家',
                field: 'PRO_COM'
            },  {
                title: '机型',
                field: 'MODEL'
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
                field: 'RECEIPT_STATUS',
                hidden:true
            }, {
                title: '排单创建时间',
                field: 'C_DATE',
                hidden:true
            }, {
                title: '退货子订单编号',
                field: 'RETURN_ORDER_DETAIL_ID'
            }]]
        });
        //===========================================物流查询
        var  old_order_return_bus_wuliu_table = $('#old_order_return_bus_wuliu_table').datagrid({
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
                title: '实际发货时间',
                field: 'REAL_SEND_DATE'
            }, {
                title: '收货人姓名',
                field: 'ADDR_REALNAME'
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
        //扣款明细对话框
        function cutDetail() {
            $('#dd_kk_${returnOrder.id}').dialog('open');
        }

        //可抵扣欠款对话框
        function takoutAmt() {
            $('#dd_qk_${returnOrder.id}').dialog('open');
        }

        //付款计划变更对话框
        function fkjh() {
            $('#dd_jh_${returnOrder.id}').dialog('open');
        }

        //调账明细对话框
        function adjustD() {
            $('#dd_tz_${returnOrder.id}').dialog('open');
        }
    </script>
</div>
<div id="old_order_return_bus_paidan_complate" class="easyui-panel" title="排单信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <div class="easyui-layout" data-options="fit:false,border:false" style="height: 150px">
        <div data-options="region:'center',fit:false,border:false">
            <table id="old_order_return_bus_paidan_complate_table" data-options="fit:false,border:false">
            </table>
        </div>
    </div>
</div>
<div id="old_order_return_bus_wuliu_table_panal" class="easyui-panel" title="物流信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <div class="easyui-layout" data-options="fit:false,border:false" style="height: 150px">
        <div data-options="region:'center',fit:false,border:false">
            <table id="old_order_return_bus_wuliu_table" data-options="fit:false,border:false">
            </table>
        </div>
    </div>
</div>
<div id="${old_order_return_info.id}_old_order_return_amt" class="easyui-panel" title="退款信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <table class="grid" style="width: 100%">
        <tr>
            <td style="width: 100px;">退货单总金额：</td>
            <td style="width: 60px;"><input type="text" name="returnAmo" value="${old_order_return_info.returnAmo}" readonly="readonly" /></td>
            <td style="width: 20px;">元</td>
            <td>(机具金额-扣款金额)</td>
        </tr>
        <tr>
            <td style="width: 100px;">扣款金额：</td>
            <td style="width: 60px;"><input type="text" name="cutAmt" value="${old_order_return_info.cutAmo}" readonly="readonly" /></td>
            <td style="width: 20px;">元</td>
            <td>
                <select name="cType" style="width:160px;height:21px" disabled="disabled">
                    <c:forEach items="${cType}" var="cType">
                        <option value="${cType.dItemvalue}">${cType.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td style="width: 100px;">机具金额：</td>
            <td style="width: 60px;"><input type="text" readonly="readonly" name="goodsReturnAmo" value="${old_order_return_info.goodsReturnAmo}" /></td>
            <td style="width: 20px;">元</td>
            <td>(机具台数*机具单价)</td>
        </tr>
        <tr>
            <td style="width: 100px;">可抵扣机具欠款：</td>
            <td style="width: 60px;"><input type="text" readonly="readonly" name="takeAmt" value="${takeAmt}" /></td>
            <td style="width: 20px;">元</td>
            <td>
                <c:if test="${takeAmt>0}">
                    <input type="button" value="执行退款方案" id="doOldOrderReturnPayPlan_button" onclick="doOldOrderReturnPayPlan()" />
                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:takoutAmt()">可抵扣欠款</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:fkjh()">付款计划变更</a>
                </c:if>
            </td>
        </tr>
        <tr>
            <td style="width: 100px;">已抵扣欠款：</td>
            <td style="width: 60px;">
                <input type="text" readonly="readonly" name="takeAmt" value="${old_order_return_info.takeOutAmo}" />
            </td>
            <td style="width: 20px;">元</td>
            <td>
            </td>
        </tr>
        <tr>
            <td style="width: 100px;">线下打款金额：</td>
            <td style="width: 60px;">
                <c:if test="${old_order_return_info.takeOutAmo==0}">
                <input type="text" readonly="readonly" name="takeAmt" value="${old_order_return_info.returnAmo-takeAmt}" />
                </c:if>
                <c:if test="${old_order_return_info.takeOutAmo>0}">
                    <input type="text" readonly="readonly" name="takeAmt" value="${old_order_return_info.relReturnAmo}" />
                </c:if>
            </td>
            <td style="width: 20px;">元</td>
            <td>


            </td>
        </tr>
        <tr>
            <td style="width: 100px;">上传附件：</td>
            <td style="width: 60px;"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-multiple'" onclick="multFileUpload(old_orderReturnAddattr);" >添加凭证</a></td>
            <td style="width: 20px;"></td>
            <td>
                <div id="old_orderReturnAddattr_list_${old_order_return_info.id}"></div>
                <script type="application/javascript">
                    //添加附件
                    function old_orderReturnAddattr(data){
                        var jsondata = eval(data);
                        for(var i=0;i<jsondata.length ;i++){
                            $("#old_orderReturnAddattr_list_"+returnId).append("<span onclick='removeold_orderReturnAddattr(this)'>"+jsondata[i].attName+"<input type='hidden' name='old_orderReturnAddattr_list_item' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
                        }
                    }
                    //删除附件
                    function removeold_orderReturnAddattr(t){
                        parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
                            if (b) {
                                $(t).remove();
                            }
                        });
                    }
                    //获取附件信息
                    function getold_orderReturnAddattr(returnID){
                        var attachments = [];
                        var inputs = $("#old_orderReturnAddattr_list_"+returnID).find("input[name='old_orderReturnAddattr_list_item']");
                        for(var i=0;i<inputs.length;i++){
                            var id =  $(inputs[i]).val();
                            attachments.push(id);
                        }
                        return attachments;
                    }
                    //执行扣款计划
                    function doOldOrderReturnPayPlan() {
                        parent.$.messager.confirm('询问', '确定执行抵扣么？', function (b) {
                            if (b) {
                                $.ajaxL({
                                    type: "POST",
                                    url: "${path}/order/return/doPayPlan",
                                    dataType: 'json',
                                    data: {
                                        returnId: returnId
                                    },
                                    beforeSend: function () {
                                        progressLoad();
                                    },
                                    success: function (result) {
                                        if (result.success) {
                                            info(result.msg);

                                            $("#doOldOrderReturnPayPlan_button").hide();
                                        } else {
                                            err(result.msg);
                                        }
                                    },
                                    complete: function (XMLHttpRequest, textStatus) {
                                        progressClose();
                                    }
                                });
                            }
                        });

                    }
                </script>
            </td>
        </tr>
    </table>
</div>




<div id="dd_qk_${returnOrder.id}" class="easyui-dialog" title="抵扣机具欠款明细" style="width:800px;height:500px;"
     data-options="iconCls:'icon-save',resizable:true,closed:true">
    <table class="grid" style="width: 100%;margin-top: 20px">
        <tr>
            <td><b>订单编号</b></td>
            <td><b>应付款金额</b></td>
            <td><b>已付款金额</b></td>
            <td><b>待付金额</b></td>
            <td><b>可抵扣金额</b></td>
        </tr>
        <c:forEach items="${takeoutList}" var="takeoutList">
            <tr>
                <td>${takeoutList.orderId}</td>
                <td>${takeoutList.payment.payAmount} 元</td>
                <td>${takeoutList.payment.realAmount} 元</td>
                <td>${takeoutList.payment.outstandingAmount} 元</td>
                <td>${takeoutList.payAmt} 元</td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="dd_jh_${returnOrder.id}" class="easyui-dialog" title="付款计划更新" style="width:800px;height:500px;"
     data-options="iconCls:'icon-save',resizable:true,closed:true">
    <table class="grid" style="width: 100%;margin-top: 20px">

        <tr>
            <td colspan="6" align="center"><h3>原付款计划：</h3></td>
        </tr>

        <tr>
            <td><b>订单号</b></td>
            <td><b>付款类型</b></td>
            <td><b>期数</b></td>
            <td><b>金额</b></td>
            <td><b>支付状态</b></td>
            <td><b>支付方式</b></td>
        </tr>

        <c:forEach items="${planNows}" var="planNows">
            <tr>
                <td>${planNows.orderId}</td>
                <td>
                    <c:forEach items="${paymentType}" var="paymentType">
                        <c:if test="${planNows.payType == paymentType.dItemvalue}">
                            ${paymentType.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${planNows.planNum}</td>
                <td>${planNows.payAmount}</td>
                <td>
                    <c:forEach items="${paymentStatus}" var="paymentStatus">
                        <c:if test="${planNows.paymentStatus == paymentStatus.dItemvalue}">
                            ${paymentStatus.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${planNows.srcType}</td>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="6" align="center"><h3>更新后付款计划：</h3></td>
        </tr>
        <tr>
            <td><b>订单号</b></td>
            <td><b>付款类型</b></td>
            <td><b>期数</b></td>
            <td><b>金额</b></td>
            <td><b>支付状态</b></td>
            <td><b>支付方式</b></td>
        </tr>
        <c:forEach items="${planNows_complate}" var="planNows_complate">
            <tr>
                <td>${planNows_complate.orderId}</td>
                <td>
                    <c:forEach items="${paymentType}" var="paymentType">
                        <c:if test="${planNows_complate.payType == paymentType.dItemvalue}">
                            ${paymentType.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${planNows_complate.planNum}</td>
                <td>${planNows_complate.payAmount}</td>
                <td>
                    <c:forEach items="${paymentStatus}" var="paymentStatus">
                        <c:if test="${planNows_complate.paymentStatus == paymentStatus.dItemvalue}">
                            ${paymentStatus.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${planNows_complate.srcType}</td>
            </tr>
        </c:forEach>
        <c:forEach items="${planNews}" var="planNews">
            <tr>
                <td>${planNews.orderId}</td>
                <td>
                    <c:forEach items="${paymentType}" var="paymentType">
                        <c:if test="${planNews.payType == paymentType.dItemvalue}">
                            ${paymentType.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${planNews.planNum}</td>
                <td>${planNews.payAmount}</td>
                <td>
                    <c:forEach items="${paymentStatus}" var="paymentStatus">
                        <c:if test="${planNews.paymentStatus == paymentStatus.dItemvalue}">
                            ${paymentStatus.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach items="${paymentSrcType}" var="paymentSrcType">
                        <c:if test="${planNews.srcType == paymentSrcType.dItemvalue}">
                            ${paymentSrcType.dItemname}
                        </c:if>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>


<div id="dd_tz_${returnOrder.id}" class="easyui-dialog" title="抵扣欠款明细" style="width:800px;height:500px;"
     data-options="iconCls:'icon-save',resizable:true,closed:true">
    <table class="grid" style="width: 100%;margin-top: 20px">
        <tr>
            <td>订单编号</td>
            <td>付款单编号</td>
            <td>抵充金额</td>
            <%--<td>付款类型</td>--%>
            <td>原分期批次号</td>
            <td>新分期批次号</td>
        </tr>


        <c:forEach items="${oAccountAdjusts.details}" var="details">
            <tr>
                <td>${details.orderId}</td>
                    <%--<td>
                        <c:forEach items="${settlementType}" var="paymentType">
                            <c:if test="${details.payType == settlementType.dItemvalue}">
                                ${settlementType.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>--%>
                <td>${details.paymentDetailId}</td>
                <td>${details.takeOutAmount}</td>
                <td>${details.batchCodeOld}</td>
                <td>${details.batchCodeNew}</td>
            </tr>
        </c:forEach>
    </table>
</div>