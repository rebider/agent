<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<!--代理商端欠票维护页面-->
<script type="text/javascript">



    var agentInvoiceDetailList;

    $(function() {
        agentInvoiceDetailList = $('#agentInvoiceDetailList').datagrid({
            url : '${path}/profit/invoiceDetail/getAgentInvoiceDetailList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title: 'id',
                field: 'id',
                hidden: true
            }, {
                title: '月份',
                field: 'profitMonth',
                width: 80
            }, {
                title: '代理商唯一码',
                field: 'agentId',
                width: 200

            },{
                title: '代理商名称',
                field: 'agentName',
                width: 220
            },{
                title: '上月剩余欠票基数',
                field: 'preLeftAmt',
                width: 150
            },{
                title: '本月日返现',
                field: 'dayBackAmt',
                width: 130
            }, {
                title: '本月日分润',
                field: 'dayProfitAmt',
                width: 130
            }, {
                title: '上月月分润',
                field: 'preProfitMonthAmt',
                width: 130
            }, {
                title: '上月瑞银信保理款',
                field: 'preProfitMonthBlAmt',
                width: 140
            },  {
                title: '本月实际到票',
                field: 'addInvoiceAmt',
                width: 130
            }, {
                title: '调整金额',
                field: 'adjustAmt',
                width: 130
            }, {
                title: '本月欠票',
                field: 'ownInvoice',
                width: 130
            }, {
                title: '状态',
                field: 'invoiceStatus',
                width: 130,
                formatter: function (value, row) {
                    if(row.ownInvoice > 0){
                        return "冻结";
                    }
                    return "正常";
                }
            }
            ]],
            onLoadSuccess: function (data) {
                $('.easyui-linkbutton-query').linkbutton();
                $('.easyui-linkbutton-add').linkbutton();
            },
            toolbar: '#dataFormToolbar'
        });
    });

    $("#profitMonth").datebox({
        required:true,
        formatter:function(data){
            var date_temp=new Date(data);
            return date_temp.getFullYear()+''+(date_temp.getMonth()+1>=10?date_temp.getMonth()+1:('0'+''+(date_temp.getMonth()+1)));
        },
        parser:function(data) {
            if(data.indexOf('-')<0){
                data=data.substring(0,4)+'-'+data.substring(4,data.length);
            }
            var t = Date.parse(data);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });

    //查询
    function searchInfo(){
        agentInvoiceDetailList.datagrid('load', $.serializeObject($('#InvoiceListGrid')));
    }

    //清空
    function cleanhSearchInfo(){
        $('#profitMonth').datebox('setValue','');
        agentInvoiceDetailList.datagrid('load',{});
    }


</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="ticketInfo" data-options="region:'west',border:true" style="width: 100%;overflow: hidden;">
        <table id="agentInvoiceDetailList" data-options="fit:true,border:false"></table>
    </div>
    <!--检索条件：北-->
    <div id="" data-options="region:'north',border:false" style="height:37px;overflow: hidden;background-color: #fff;">
        <form id="InvoiceListGrid" method="post">
            <table>
                <tr>
                    <th>代理商名称:</th>
                    <td><input name="agentName" id="agentName"  class="easyui-textbox"
                               data-options="editable:false" style="width: 160px" value='${agentName}'/></td>
                    <th>代理商唯一码:</th>
                    <td><input name="agentId" id="agentId"  class="easyui-textbox"
                               data-options="editable:false" style="width: 160px" value="${agentId}"/></td>
                    <th>月份:</th>
                    <td><input  id="profitMonth" name="profitMonth" style="width: 160px;"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-search',plain:true" onclick="searchInfo();">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanhSearchInfo();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>