<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var paymentList;
    $(function () {
        paymentList = $('#paymentList').datagrid({
            url: '${path }/colinfoPayment/paymentList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '流水号',
                field: 'balanceLs'
            }, {
                title: '收款账户ID',
                field: 'colinfoId'
            }, {
                title: '代理商唯一码',
                field: 'merchId'
            }, {
                title: '代理商名称',
                field: 'merchName'
            }, {
                title: '交易日期',
                field: 'tranDate'
            }, {
                title: '交易金额',
                field: 'balanceAmt'
            }, {
                title: '卡号',
                field: 'balanceRcvAcc'
            }, {
                title: '户名',
                field: 'balanceRcvBank'
            }, {
                title: '银行名称',
                field: 'balanceRcvName'
            }, {
                title: '联行号',
                field: 'balanceRcvCode'
            }, {
                title: '账户类型',
                field: 'balanceRcvType',
                formatter : function(value, row, index) {
                    switch (value) {
                        case '0':
                            return '对私';
                        case '2':
                            return '对公';
                    }
                }
            }, {
                title: '录入时间',
                field: 'inputTime'
            }, {
                title: '出款失败描述',
                field: 'errDesc'
            }, {
                title: '结算状态',
                field: 'flag'
            }, {
                title: '交易时间',
                field: 'payDate'
            }, {
                title: '创建时间',
                field: 'createTime'
            }
            ]],
            onLoadSuccess: function (data) {
            }
        });

    });

    function searchColifoPayment() {
        paymentList.datagrid('load', $.serializeObject($('#searchColinPaymentForm')));
    }

    function cleanColifoPayment() {
        $('#searchColinPaymentForm input').val('');
        paymentList.datagrid('load', {});
    }


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="paymentList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 55px; overflow: hidden;background-color: #fff">
        <form id="searchColinPaymentForm">
            <table>
                <tr>
                    <th>流水号:</th>
                    <td>
                        <input name="balanceLs" style="line-height:17px;border:1px solid #ccc">
                    </td>
                    <th>收款账户编号:</th>
                    <td>
                        <input name="colinfoId" style="line-height:17px;border:1px solid #ccc">
                    </td>
                    <th>代理商唯一码:</th>
                    <td>
                        <input name="merchId" style="line-height:17px;border:1px solid #ccc">
                    </td>
                    <th>代理商名称:</th>
                    <td>
                        <input name="merchName" style="line-height:17px;border:1px solid #ccc">
                    </td>
                </tr>
                <tr>
                    <th>结算状态:</th>
                    <td>
                        <select name="flag" style="width:140px;height:21px">
                            <option  value="">---请选择---</option>
                            <c:forEach items="${flagList}" var="flagListItem"  >
                                <option value="${flagListItem.key}">${flagListItem.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchColifoPayment();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanColifoPayment();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

