<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript">
    var InvoiceFinaceList;

    $(function() {
        InvoiceFinaceList = $('#InvoiceFinaceListTable').datagrid({
            url : '${path}/profit/invoiceDetail/getInvoiceFinaceList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [[{
                title : '代理商名称',
                field : 'agentName',
                align : 'center',
                width:140
            },{
                title : '代理商唯一码',
                field : 'agentId',
                align : 'center',
                width:140
            },{
                title : '开票公司',
                field : 'invoiceCompany',
                align : 'center',
                width:140
            },{
                title : '开票日期',
                field : 'invoiceDate',
                align : 'center',
                width:140
            },{
                title : '发票号',
                field : 'invoiceNumber',
                align : 'center',
                width:140
            },{
                title : '发票代码',
                field : 'invoiceCode',
                align : 'center',
                width:140
            },{
                title : '开票项目',
                field : 'invoiceItem',
                align : 'center',
                width:140
            },{
                title : '单价',
                field : 'unitPrice',
                align : 'center',
                width:140
            },{
                title : '数量',
                field : 'numberSl',
                align : 'center',
                width:140
            },{
                title : '税前金额',
                field : 'amountBeforeTax',
                align : 'center',
                width:140
            },{
                title : '税点',
                field : 'tax',
                align : 'center',
                width:140
            },{
                title : '税额',
                field : 'amountTax',
                align : 'center',
                width:140
            },{
                title : '税价合计',
                field : 'sumAmt',
                align : 'center',
                width:140
            },{
                title : '寄出时间',
                field : 'expressDate',
                align : 'center',
                width:140

            },{
                title : '快递单号',
                field : 'expressNumber',
                align : 'center',
                width:140
            },{
                title : '快递公司',
                field : 'expressCompany',
                align : 'center',
                width:140
            },{
                title : '发票',
                field : 'filename',
                align : 'center',
                width:140,
                formatter: function (value, row) {
                    var str = '';
                    str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryApproval(\'{0}\');" >'+row.filename+'</a>', row.id);
                    return str;
                }
            },{
                title : '审核人',
                field : 'shName',
                align : 'center',
                width:140
            },{
                title : '审核时间',
                field : 'shDate',
                align : 'center',
                width:140
            },{
                title : '审核结果',
                field : 'shResult',
                align : 'center',
                width:140,
                formatter: function (value, row) {
                    var str = '';
                    if(value == '0'){
                        str = "审核中";
                    }else if(value == '1'){
                        str = "通过";
                    }else if(value == '2'){
                        str = "退回";
                    }
                    return str;
                }
            },{
                title : '问题发票寄出时间',
                field : 'returnDate',
                align : 'center',
                width:140
            },{
                title : '快递单号',
                field : 'returnExpressNumber',
                align : 'center',
                width:140
            },{
                title : '快递公司',
                field : 'returnExpressCompany',
                align : 'center',
                width:140
            }
            ]],
            toolbar : '#invoiceApplyListToolbar'
        });
    });

    //查看附件
    function queryApproval(id) {
        if(id == null){
            alertMsg("下载失败，请重试！");
            return;
        }
        location.href = '${path }/profit/invoiceDetail/show?id='+id;
    }

    //查询
    function  searchMessage(){
        InvoiceFinaceList.datagrid('load', $.serializeObject($('#InvoiceFinaceForm')));
    }

    //重置
    function cleanhSearchInfo(){
        $("#InvoiceFinaceForm input").val("");
        InvoiceFinaceList.datagrid('load',{});
    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="invoiceInfo" data-options="region:'west',border:true" style="width: 100%;overflow: hidden;">
        <table id="InvoiceFinaceListTable" data-options="fit:true,border:false"></table>
    </div>
    <div id="aaa" data-options="region:'north',border:false" style="height:30px;overflow: hidden;">
        <form id="InvoiceFinaceForm">
            <table>
                <th>代理商名称:</th>
                <td>
                    <input name="agentName" id="agentName" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                </td>
                <th>代理商唯一码:</th>
                <td>
                    <input name="agentId" id="agentId" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                </td>
                <th>快递单号:</th>
                <td>
                    <input name="expressNumber" id="expressNumber" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                </td>
                <th>寄出时间:</th>
                <td>
                    <input class= "easyui-datebox" name="expressDate" id="expressDate" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/>
                </td>
                <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',plain:true" onclick="searchMessage();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',plain:true" onclick="cleanhSearchInfo();">重置</a>
                </td>
            </table>
        </form>
    </div>
</div>

