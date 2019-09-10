<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var logisticsA_order;
    $(function() {
        logisticsA_order = $('#logisticsId_order').datagrid({
            url : '${path}/logistics/oLogisticsList?ORDER_ID=${orderId}',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '商品编号',
                field : 'PRO_CODE'
            } , {
                title : '商品名称',
                field : 'PRO_NAME'
            } , {
                title : '发货数量',
                field : 'SENDNUM'
            } , {
                title : '机型',
                field : 'MODEL'
            } , {
                title : '实际发货时间',
                field : 'REAL_SEND_DATE'
            } , {
                title : '收货人姓名',
                field : 'ADDR_REALNAME'
            } , {
                title : '物流公司',
                field : 'LOG_COM'
            } , {
                title : '物流单号',
                field : 'W_NUMBER'
            } , {
                title : '起始SN序列号',
                field : 'SN_BEGIN_NUM'
            } , {
                title : '结束SN序列号',
                field : 'SN_END_NUM'
            } , {
                title : '退货子订单编号',
                field : 'RETURN_ORDER_DETAIL_ID'
            } ] ]
        });
    });

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'订单物流信息'" style="width:100%;overflow: hidden; ">
        <table id="logisticsId_order" data-options="fit:true,border:false"></table>
    </div>
</div>
