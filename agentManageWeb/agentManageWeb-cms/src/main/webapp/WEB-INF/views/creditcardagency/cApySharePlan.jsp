<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var cardList;
    $(function() {
        cardList = $('#cardList').datagrid({
            url : '${path }/creditcard/queryMaid',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '创建时间',
                field : 'CREATEDATE'
            },  {
                title : '分润年月',
                field : 'SHARE_DATE'

            }, {
                title : '合作商名称',
                field : 'AGENT_NAME'

            }, {
                title : '分润金额',
                field : 'SHARE_AMOUNT'

            }, {
                title : '实际分润时间',
                field : 'REAL_SHARE_DATE'

            }, {
                title : '代付卡号',
                field : 'PAY_CARD'

            }, {
                title : '代付银行',
                field : 'PAY_BANK'

            }, {
                title : '代付状态',
                field : 'PAY_STATE'

            }, {
                title : '代付订单号',
                field : 'PAY_ORDER_ID'

            }, {
                title : '状态',
                field : 'STATUS'

            },

            ] ],
            toolbar : '#agentRuleToolbar'
        });
    });

    function searchAgent() {
        cardList.datagrid('load', $.serializeObject($('#searchcardForm')));
    }
    function cleanAgent() {
        $('#searchcardForm input').val('');
        cardList.datagrid('load', {});
    }


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="cardList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id ="searchcardForm">
            <table>
                <tr>
                    <th>合作商名称:</th>
                    <td><input style="line-height:17px;border:1px solid #ccc" name="name"/></td>
                    <th>分润年月:</th>
                    <td><input class="easyui-datetimebox" style="line-height:17px;border:1px solid #ccc" name="time"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgent();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgent();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>


