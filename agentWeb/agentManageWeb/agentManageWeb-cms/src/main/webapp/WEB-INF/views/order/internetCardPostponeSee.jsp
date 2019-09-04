<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var logisticsA_order;
    $(function() {
        logisticsA_order = $('#logisticsId_order').datagrid({
            url : '${path}/internet/internetCardPostponeSee?iccid=${internetCardPostpone.iccid}',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '延期编号',
                field : 'id'
            },{
                title : 'iccid',
                field : 'iccid'
            },{
                title : '代理商编码',
                field : 'agentId'
            },{
                title : '代理商名称',
                field : 'agentName'
            },{
                title : '商户编号',
                field : 'merId'
            },{
                title : '商户名称',
                field : 'merName'
            },{
                title : '订单号',
                field : 'orderId'
            },{
                title : '开户时间',
                field : 'openAccountTime'
            },{
                title : '到期时间',
                field : 'expireTime'
            },{
                title : '延期后到期时间',
                field : 'postponeExpireTime'
            },{
                title : '延期时间',
                field : 'postponeTime',
                formatter : function(value, row, index) {
                    return  value+'个月';
                }
            },{
                title : '创建时间',
                field : 'cTime'
            },{
                title : '操作人',
                field : 'cUser'
            }
            ] ]
        });
    });

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'订单物流信息'" style="width:100%;overflow: hidden; ">
        <table id="logisticsId_order" data-options="fit:true,border:false"></table>
    </div>
</div>
