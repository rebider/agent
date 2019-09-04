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
        //导出排单
        function old_exportData_refund() {
            $('#old_receipPlanQueryForm').form({
                url: '${path}/receiptPlanReturn/export',
                onSubmit: function () {
                    return $(this).form('validate');
                }
            });
            $('#old_receipPlanQueryForm').submit();
        }
        //代理商导入物流信息
        function old_importData_refund() {
            parent.$.modalDialog({
                title: '导入物流信息',
                width: 300,
                height: 110,
                href: "${path}/oldorderreturn/agentUploadLogicView",
                buttons: [{
                    text: '确定',
                    handler: function () {
                        var fun = parent.$.modalDialog.handler.find('#old_logisticsImportFileForm');
                        fun.submit();
                    }
                }]
            });
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
<div style="padding:5px 20px 5px 5px;text-align: right;">
    <table class="grid" style="width: 100%;text-align: center;">
        <tr>
            <td >
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="old_exportData_refund()" >1:导出排单</a>
                <form id="old_receipPlanQueryForm" method="post">
                    <input type="hidden" name="returnId" value="${old_order_return_info.id}" >
                </form>
            </td>
            <td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="old_importData_refund()">2:导入物流</a></td>
            <td>发货流程:请先导出公司物流排单，并填写物流信息以及SN相关信息并上传</td>
        </tr>
    </table>
    <div id="oLogistics_return_notifyWin" class="easyui-window" title="通知信息详情" closed="true" style="width:1000px;height:300px;" data-options="iconCls:'icon-save',modal:true"></div>
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
    </table>
</div>
