<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var monthProfitDetailList;
    $(function() {
        monthProfitDetailList = $('#monthProfitDetailList').datagrid({
            url : '${path }/monthProfit/detailList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100],
            columns : [ [{
                title : '分润日期',
                field : 'profitDate'
            },{
                title : '代理商业务机构号',
                field : 'busNum'
            },{
                title : '代理商编号',
                field : 'agentId'
            },{
                title : '代理商名称',
                field : 'agentName'
            },{
                title : '上级代理商机构号',
                field : 'parentBusNum'
            },{
                title : '上级代理商编号',
                field : 'parentAgentId'
            },{
                title : '平台类型',
                field : 'busCode',
                align:'center',
                width:100,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "100002":
                            return '智能POS';
                        case "100003":
                            return 'POS';
                        case "5000":
                            return '手刷-瑞和宝';
                        case "6000":
                            return '手刷-瑞和宝直发平台';
                        case "4000":
                            return '手刷-瑞众通';
                        case "2000":
                            return '手刷-瑞刷';
                        case "3000":
                            return '手刷-瑞刷活动';
                        case "0001":
                            return '手刷-瑞银信';
                        case "1001":
                            return '手刷-贴牌';
                        case "1111":
                            return '手刷-瑞银信活动';
                    }
                }
            },{
                title : '付款交易额',
                field : 'inTransAmt'
            },{
                title : '出款交易额',
                field : 'outTransAmt'
            },{
                title : '分润金额',
                field : 'profitAmt'
            },{
                title : '付款交易分润比例',
                field : 'inProfitScale'
            },{
                title : '出款交易分润比例',
                field : 'outProfitScale'
            },{
                title : '付款交易分润额',
                field : 'inProfitAmt'
            },{
                title : '出款交易分润额',
                field : 'outProfitAmt'
            },{
                title : 'POS贷记交易额',
                field : 'posCreditAmt'
            },{
                title : '智能POS贷记交易额',
                field : 'iposCreditAmt'
            },{
                title : 'POS奖励交易总额',
                field : 'posRewardAmt'
            },{
                title : '打款公司',
                field : 'payCompany'
            },{
                title : '日结未计税金额',
                field : 'notaxAmt'
            },{
                title : '补差金额',
                field : 'supplyAmt'
            },{
                title : '交易手续费',
                field : 'transFee'
            },{
                title : '财务自编码',
                field : 'unicode'
            },{
                title : '数据来源',
                field : 'sourceInfo'
            } ]]
        });

    });

    function exportProfitDetailData() {
        $('#exportDetailForm').form({
            url : '${path }/monthProfit/export/profit/detail',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#exportDetailForm').submit();
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true,title:'业务平台列表'"  style="width:100%;overflow: hidden; ">
        <table id="monthProfitDetailList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id ="exportDetailForm" method="post">
            <table>
                <tr>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportProfitDetailData();">导出月分润明细</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>