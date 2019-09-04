<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    var time = new Date();
    var year_month;
    if(time.getMonth()==0){
        year_month = (time.getFullYear()-1)+'-12'+'-01';
    }else{
        year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
    }

    $('#dateStart').datebox({
        required:true
    });
    $('#dateEnd').datebox({
        required:true
    });

    $("#dateStart,#dateEnd").datebox({
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

    $('#dateStart').datebox('setValue',year_month);
    $('#dateEnd').datebox('setValue',year_month);

    var start = $('#dateStart').datebox('getValue');
    var end = $('#dateEnd').datebox('getValue');

    var InvoiceListGrid;
    $(function() {
        InvoiceListGrid = $('#InvoiceList').datagrid({
            url : '${path }/profit/invoiceDetail/getInvoiceDetailList',
            striped : true,
            rownumbers : true,
            pagination : true,
            queryParams:{'dateStart':start ,'dateEnd':end},
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
                title: '代理商名称',
                field: 'agentName',
                width: 220
            },{
                title: '代理商唯一码',
                field: 'agentId',
                width: 200
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
            }, {
                title: '调整金额',
                field: 'adjustAmt',
                width: 100
            }, {
                title: '本月实际到票',
                field: 'addInvoiceAmt',
                width: 130
            }, {
                title: '本月欠票',
                field: 'ownInvoice',
                width: 130
            }, {
                title: '备注',
                field: 'adjustReson',
                width: 130,
            }, {
                title: '操作',
                field: 'adjustOPR',
                width: 170,
                formatter: function (value, row) {
                    var str = '';
                    if ('${noEdit}'==0) {
                        str += $.formatString('<a id="adjustAMT" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="adjustAMT(\'{0}\');" >调整</a>', row.id);
                        str += $.formatString('|<a id="examineAdjust" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'icon-search icon-blue\'" onclick="examineAdjust(\'{0}\',\'{1}\',\'{2}\');" >查看调整详情</a>', row.agentId, row.profitMonth,row.adjustAmt);
                    }
                    return str;
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

    //导入欠票：终审后不能进行调整
    function exportInfo(){
            parent.$.modalDialog({
                title: '导入',
                width: 500,
                height: 100,
                href: '${path}/profit/invoiceDetail/gotoExportPage',
                buttons: [{
                    text: '确定',
                    handler: function () {
                        parent.$.modalDialog.openner_dataGrid = InvoiceListGrid;
                        var f = parent.$.modalDialog.handler.find('#importForm');
                        f.submit();
                    }
                }]
            });
    }


    //调整：终审后不能做调整
    function adjustAMT(id){
        parent.$.modalDialog({
            title: '调整金额',
            width: 500,
            height: 400,
            href: '${path}/profit/invoiceDetail/gotoAdjustPage?id='+id,
            buttons: [{
                text: '确定',
                handler: function() {
                    parent.$.modalDialog.openner_dataGrid = InvoiceListGrid;
                    var f = parent.$.modalDialog.handler.find('#adjustAMTForm');
                    f.submit();
                }
            }]
        });
    }

    //查看调整详情
    function examineAdjust(agentId,profitMonth,adjustAmt){
        if (adjustAmt=='0') {
            parent.$.messager.alert('提示', '此代理商本月暂无调整事项', 'info');
            return ;
        }
        parent.$.modalDialog({
            title : agentId+'的调整详情',
            width : 340,
            height : 200,
            resizable:"true",
            href : '${path }/profit/invoiceDetail/examineAdjust?agentId='+agentId+'&profitMonth='+profitMonth
        });
    }

    //导出
    function downloadDataList() {
        $('#InvoiceListGrid').form({
            url : '${path}/profit/invoiceDetail/dowmloadData',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#InvoiceListGrid').submit();
    }

    //查询
    function searchInfo(){
        InvoiceListGrid.datagrid('load', $.serializeObject($('#InvoiceListGrid')));
    }

    //清空 concludeChild
    function cleanhSearchInfo(){
        $("#InvoiceListGrid input").val("");
        $('#dateStart').datebox('setValue','');
        $('#dateEnd').datebox('setValue','');
        $("#InvoiceListGrid select").val("2");
        InvoiceListGrid.datagrid('load',{});
    }
    /**
     * 统计事件
     */
    function invoiceCount(){
        var data=$.serializeObject($('#InvoiceListGrid'));
        var profitCountUrl=encodeURI('/profit/invoiceDetail/profitCount?agentId='+data.agentId+'&agentName='+data.agentName+'&concludeChild='+data.concludeChild+'&dateStart='+data.dateStart+'&dateEnd='+data.dateEnd);
        parent.$.modalDialog({
            title : '统计',
            width : 200,
            height : 100,
            href : profitCountUrl
        });
        console.log(data);
    }

    //对月份进行格式化

</script>


<!--页面布局-->
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="ticketInfo" data-options="region:'west',border:true" style="width: 100%;overflow: hidden;">
        <table id="InvoiceList" data-options="fit:true,border:false"></table>
    </div>
    <!--检索条件：北-->
    <div id="" data-options="region:'north',border:false" style="height:60px;overflow: hidden;background-color: #fff;">
       <form id="InvoiceListGrid" method="post" action="${path}/profit/invoiceDetail/dowmloadData">
            <table>
                <tr>
                    <th>代理商名称:</th>
                    <td><input name="agentName" id="agentName" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/></td>
                    <th>代理商唯一码:</th>
                    <td><input name="agentId" id="agentId" style="line-height: 17px;border: 1px solid #ccc;width: 160px"/></td>


                    <th></th>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-search',plain:true" onclick="searchInfo();">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanhSearchInfo();">清空</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="invoiceCount()">统计</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-print',plain:true," onclick="downloadDataList();">导出</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <shiro:hasPermission name="/profit/invoiceDetail/export">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="exportInfo();">导入</a>
                        </shiro:hasPermission>
                    </td>



                </tr>
                <tr>
                    <th>是否包含下级:</th>
                    <td>
                        <select id="concludeChild" name="concludeChild" style="line-height: 17px;border: 1px solid #ccc;width: 160px">
                            <option value="2">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                    <th>月份:</th>
                    <td><input  id="dateStart" name="dateStart" style="width: 160px;"></td>
                    <th>-</th>
                    <td><input id="dateEnd" name="dateEnd"  style="width: 160px;" /></td>
                </tr>
            </table>
        </form>
    </div>
</div>