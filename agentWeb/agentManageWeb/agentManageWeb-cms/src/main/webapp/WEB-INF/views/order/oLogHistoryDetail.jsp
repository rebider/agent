<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var logisticsHistory;
    $(function() {
        logisticsHistory = $('#logisticsHistoryId').datagrid({
            url: '${path }/logistics/oLogHistoryDetailList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            pageList: [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns: [ [ {
                title: '订单ID',
                field: 'ORDER_ID'
            },{
                title: '物流ID',
                field: 'LOGISTICS_ID'
            },{
                title: '商品ID',
                field: 'PRO_ID'
            },{
                title: '活动ID',
                field: 'ACTIVITY_ID'
            },{
                title: '代理商唯一编码',
                field: 'AGENT_ID'
            },{
                title: '操作单ID',
                field: 'OPT_ID'
            },{
                title: '退货单ID',
                field: 'RETURN_ORDER_ID'
            },{
                title: '创建人',
                field: 'C_USER'
            },{
                title: '创建人',
                field: 'C_USER'
            },{
                title: '创建人',
                field: 'C_USER'
            },{
                title: '商品名称',
                field: 'PRO_NAME'
            },{
                title: '商品结算价',
                field: 'SETTLEMENT_PRICE'
            },{
                title: '活动名称',
                field: 'ACTIVITY_NAME'
            },{
                title: 'SN码',
                field: 'SN_NUM'
            },{
                title: '操作类型',
                field: 'OPT_TYPE'
            },{
                title: '终端号',
                field: 'TERMINALID'
            },{
                title: '秘钥',
                field: 'TERMINALID_KEY'
            },{
                title: '序列',
                field: 'TERMINALID_SEQ'
            },{
                title: '业务平台编号',
                field: 'BUS_PRO_CODE'
            },{
                title: '业务平台名称',
                field: 'BUS_PRO_NAME'
            },{
                title: '终端批次号',
                field: 'TERM_BATCHCODE'
            },{
                title: '终端批次名称',
                field: 'TERM_BATCHNAME'
            },{
                title: '终端类型编号',
                field: 'TERMTYPE'
            },{
                title: '状态',
                field: 'STATUS',
                formatter: function(value, row, index) {
                    switch(row.STATUS){
                        case 0:
                            return "库存";
                        case 1:
                            return "发货";
                        case 2:
                            return "退货";
                    }
                    return "";
                }
            },{
                title: '记录状态',
                field: 'RECORD_STATUS',
                formatter: function(value, row, index) {
                    switch(row.RECORD_STATUS){
                        case 0:
                            return "删除";
                        case 1:
                            return "有效";
                        case 2:
                            return "锁定";
                        case 3:
                            return "历史";
                    }
                    return "";
                }
            },{
                title: '终端类型',
                field: 'TERMINALID_TYPE',
                formatter: function(value, row, index) {
                    if(value == 'MPOS') {
                        return "手刷";
                    }else if(value == 'POS') {
                        return "POS";
                    }
                    return "";
                }
            },{
                title: '发送状态',
                field: 'SEND_STATUS',
                formatter: function(value, row, index) {
                    switch(row.SEND_STATUS){
                        case 0:
                            return "未联动";
                        case 1:
                            return "已联动";
                        case 2:
                            return "联动失败";
                        case 3:
                            return "联动业务系统处理中";
                    }
                    return "";
                }
            },{
                title: '创建人',
                field: 'C_USER'
            },{
                title: '创建时间',
                field: 'C_TIME'
            } ] ],
            toolbar: '#logisticsToolbar'
        });
    });

    /**
     * 搜索事件
     */
    function searchOLogisticsHistory() {
        logisticsDetail.datagrid('load', $.serializeObject($('#logisticsHistoryForm')));
    }

    /**
     * 清空事件
     */
    function cleanOLogisticsHistory() {
        $('#logisticsHistoryForm input').val('');
        logisticsDetail.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%; overflow:hidden;">
        <table id="logisticsHistoryId" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id ="logisticsHistoryForm" method="post">
            <table>
                <tr>
                    <th>订单ID:</th>
                    <td><input name="orderId" style="line-height:20px;border:1px solid #ccc;width: 100px;"></td>
                    <th>SN码:</th>
                    <td><input name="snNum" style="line-height:20px;border:1px solid #ccc;width: 100px;"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchOLogisticsHistory();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanOLogisticsHistory();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>