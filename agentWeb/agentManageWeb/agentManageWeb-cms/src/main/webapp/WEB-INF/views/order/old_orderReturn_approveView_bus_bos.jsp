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
            <td >业务平台</td>
            <td >活动</td>
            <td >机具类型</td>
            <td >采购单价</td>
            <td >厂家</td>
            <td >型号</td>
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
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_productid" name="productid" disabled="disabled">
                        <c:forEach items="${products}" var="productsItem">
                            <option value="${productsItem.id}"
                                <%--<c:if test="${productsItem.id==old_order_return_info_detail_item.proId}" >selected="selected"</c:if> --%>
                                    <c:if test="${not empty  old_order_return_info_detail_item.act && !(old_order_return_info_detail_item.act eq  null )
                                    && old_order_return_info_detail_item.act.productId==productsItem.id}">
                                        selected="selected"
                                    </c:if>
                            >${productsItem.proName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_platform" name="platform" disabled="disabled">
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                            <option value="${ablePlatFormItem.platformNum}"
                                    <c:if test="${not empty  old_order_return_info_detail_item.act && !(old_order_return_info_detail_item.act eq  null )
                                        && old_order_return_info_detail_item.act.platform==ablePlatFormItem.platformNum}">
                                        selected="selected"
                                    </c:if>
                            >${ablePlatFormItem.platformName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td >
                    <c:if test="${ empty   old_order_return_info_detail_item.act || old_order_return_info_detail_item.act eq  null }">
                        <input type="text" value=""   style="width: 80px;" id="${old_order_return_info_detail_item.id}_activity" readonly="readonly"/>
                        <input type="hidden" value=""   style="width: 80px;" id="${old_order_return_info_detail_item.id}_activity_hidden" name="activity"/>
                    </c:if>
                    <c:if test="${not empty  old_order_return_info_detail_item.act && !(old_order_return_info_detail_item.act eq  null )}">
                        <input type="text" value="${old_order_return_info_detail_item.act.activityName}"   style="width: 80px;" id="${old_order_return_info_detail_item.id}_activity" readonly="readonly"/>
                        <input type="hidden" value="${old_order_return_info_detail_item.act.id}"   style="width: 80px;" id="${old_order_return_info_detail_item.id}_activity_hidden" name="activity"/>
                    </c:if>
                </td>
                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_modeltype" name="modeltype" disabled="disabled">
                        <c:forEach items="${modelType}" var="modelTypeItem">
                            <option value="${modelTypeItem.dItemvalue}"
                                    <c:if test="${not empty  old_order_return_info_detail_item.act
                                && !(old_order_return_info_detail_item.act eq  null )
                                && (old_order_return_info_detail_item.act.proType == modelTypeItem.dItemvalue )
                                }">
                                        selected="selected"
                                    </c:if>
                            >${modelTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td >
                    <input value="${old_order_return_info_detail_item.proPrice}" style="width: 50px;" id="${old_order_return_info_detail_item.id}_proprice" name="proprice" readonly="readonly"/>
                </td>

                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_manufacturer" name="manufacturer" disabled="disabled">
                        <c:forEach items="${manufacturer}" var="manufacturerItem">
                            <option value="${manufacturerItem.dItemvalue}"
                                    <c:if test="${not empty  old_order_return_info_detail_item.act
                                    && !(old_order_return_info_detail_item.act eq  null )
                                    && (old_order_return_info_detail_item.act.vender == manufacturerItem.dItemvalue )
                                    }">
                                        selected="selected"
                                    </c:if>
                            >${manufacturerItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_promode" name="promode" disabled="disabled">
                        <c:forEach items="${proMode}" var="proModeItem">
                            <option value="${proModeItem.dItemvalue}"
                                    <c:if test="${not empty  old_order_return_info_detail_item.act
                                    && !(old_order_return_info_detail_item.act eq  null )
                                    && (old_order_return_info_detail_item.act.proModel == proModeItem.dItemvalue )
                                    }">
                                        selected="selected"
                                    </c:if>
                            >${proModeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
        </c:forEach>
    </table>
    <script type="application/javascript">
        //已排单查询
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


    </script>
</div>
<div id="old_order_return_bus_paidan_complate" class="easyui-panel" title="排单信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <div class="easyui-layout" data-options="fit:false,border:false" style="height: 200px">
        <div data-options="region:'center',fit:false,border:false">
            <table id="old_order_return_bus_paidan_complate_table" data-options="fit:false,border:false">
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
    </table>
</div>

