<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript" src="${staticPath }/static/invoice/${invoice_Result}/socket.io.js" />
<script type="text/javascript" src="${staticPath }/static/invoice/${invoice_Result}/websocket-script.js" />

<script type="text/javascript">

   var pwyWebsocket = new PwyWebSocket({
        name: '${timestamp}'+'${random}',
        onOpen: function () {
                console.log("链接成功！");
        },
        onMessage:function (msg,handlerOk,handerFail) {
            var data = msg.data;
            console.log(data);
            if('${numType}' == 'AG'){
                $.ajax({
                    type: "post",
                    url: '${path }/profit/invoiceDetail/saveInvoiceInfo',
                    data: JSON.stringify(data),
                    contentType: 'application/json; charset=UTF-8',
                    dataType: "json",
                    success: function (data) {
                        if(data.resCode == '1'){
                            handlerOk({'errcode':'0000','data':'','description':'success'});
                            parent.$.messager.alert('提示', '推送成功！', 'ok');
                            searchInfo();
                        }else {
                            handerFail({'errcode':'0020','data':'','description':'导入失败，请于发票管理页面查看原因！'});
                            alertMsg(data.resInfo);
                            searchInfo();
                        }
                    }
                });
            }else if('${numType}' == 'FI'){
                $.ajax({
                    type: "post",
                    url: '${path }/profit/invoiceDetail/finalCheckInvoice',
                    data: JSON.stringify(data),
                    contentType: 'application/json; charset=UTF-8',
                    dataType: "json",
                    success: function (data) {
                        if(data.resCode == '1'){
                            handlerOk({'errcode':'0000','data':data,'description':"success"});
                            parent.$.messager.alert('提示', '推送成功！', 'ok');
                            searchInfo();
                        }else {
                            handerFail({'errcode':'00200','data':'','description':"核票失败，请于发票管理页面查看原因！"});
                            alertMsg(data.resInfo)
                            searchInfo();
                        }
                    }
                });
            }
        },
        onError:function () {
            console.log("链接失败！");
        }
    });

    var time = new Date();
    var year_month;
    if (time.getMonth() == 0) {
        year_month = (time.getFullYear() - 1) + '-12' + '-01';
    } else {
        year_month = time.getFullYear() + '-' + (time.getMonth() >= 10 ? time.getMonth() : 0 + '' + (time.getMonth())) + '-01';
    }

    $('#profitMonth').datebox({
        required: true
    });
    $('#profitMonth').datebox({
        required: true
    });

    $("#profitMonth").datebox({
        formatter: function (data) {
            var date_temp = new Date(data);
            return date_temp.getFullYear() + '' + (date_temp.getMonth() + 1 >= 10 ? date_temp.getMonth() + 1 : ('0' + '' + (date_temp.getMonth() + 1)));
        },
        parser: function (data) {
            if (data.indexOf('-') < 0) {
                data = data.substring(0, 4) + '-' + data.substring(4, data.length);
            }
            var t = Date.parse(data);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });

    $('#profitMonth').datebox('setValue', year_month);

    var profitMonth = $('#profitMonth').datebox('getValue');

    var InvoiceSumGrid;
    $(function () {
        InvoiceSumGrid = $('#InvoiceSumList').datagrid({
            url: '${path }/profit/InvoiceSumController/getInvoiceSumList',
            striped: true,
            rownumbers: true,
            pagination: true,
            queryParams: {'profitMonth': profitMonth},
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: 'id',
                field: 'ID',
                hidden: true
            }, {
                title: '分润月',
                field: 'PROFITMONTH',
                width: 80
            }, {
                title: '机构编码',
                field: 'TOPORGID',
                width: 200
            }, {
                title: '机构名称',
                field: 'TOPORGNAME',
                width: 200
            }, {
                title: '代理商唯一码',
                field: 'AGENTID',
                width: 200
            }, {
                title: '代理商名称',
                field: 'AGENTNAME',
                width: 220
            }, {
                title: '开票公司',
                field: 'INVOICECOMPANY',
                width: 220
            },  {
                title: '代理商税率',
                field: 'TAX',
                width: 100
            }, {
                title: '上月剩余欠票基数',
                field: 'PRELEFTAMT',
                width: 150
            }, {
                title: '本月日返现',
                field: 'DAYBACKAMT',
                width: 130
            }, {
                title: '本月日分润',
                field: 'DAYPROFITAMT',
                width: 130
            }, {
                title: '上月月分润',
                field: 'PREPROFITMONTHAMT',
                width: 130
            }, {
                title: '代下级开票',
                field: 'SUBADDINVOICEAMT',
                width: 130
            }, {
                title: '本月实际到票',
                field: 'ADDINVOICEAMT',
                width: 130
            }, {
                title: '调整金额',
                field: 'ADJUSTAMT',
                width: 100
            }, {
                title: '调整原因',
                field: 'ADJUSTRESON',
                width: 100
            }, {
                title: '本月欠票',
                field: 'OWNINVOICE',
                width: 130
            }, {
                title: '状态',
                field: 'INVOICESTATUS',
                width: 200,
                formatter:function (value) {
                   if(value=='00'){
                       return "冻结"
                   }else if(value=='99'){
                       return "解冻"
                   }
                }
            }, {
                title: '操作',
                field: 'adjustOPR',
                width: 170,
                formatter: function (value, row) {
                    var str = '';
                   <%-- if ('${noEdit}' == 0) {--%>
                        <shiro:hasPermission name="/Financial/authority">
                        str += $.formatString('<a id="adjustAMT" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="adjustInvoiceSumAMT(\'{0}\');" >调整</a>', row.ID);
                        </shiro:hasPermission>
                    str += $.formatString('<a id="examineAdjust" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'icon-search icon-blue\'" onclick="examineAdjust(\'{0}\',\'{1}\');" >查看调整详情</a>', row.ID, row.ADJUSTAMT);
                        <shiro:hasPermission name="/invoiceManage/uploadInvoice">
                        /*if(row.ownInvoice > 0) {
                            str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton" ' +
                                'data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryImage();" >发票上传</a>');
                            str += '&nbsp;&nbsp;&nbsp;';
                        }*/
                        </shiro:hasPermission>
                    //  }
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

    function queryImage() {
        window.open('${content}');
    }

    //调整：终审后不能做调整
    function adjustInvoiceSumAMT(id) {
        parent.$.modalDialog({
            title: '调整金额',
            width: 500,
            height: 280,
            href: '${path}/profit/InvoiceSumController/gotoAdjustInvoiceSumPage?id=' + id,
            buttons: [{
                text: '确定',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = InvoiceSumGrid;
                    var f = parent.$.modalDialog.handler.find('#adjustInvoiceSumAMTForm');
                    f.submit();
                }
            }]
        });
    }

    //查看调整详情
    function examineAdjust(id, adjustAmt) {
        console.info(id);
        console.info(adjustAmt);
        if (adjustAmt == '0') {
            parent.$.messager.alert('提示', '此代理商本月暂无调整事项', 'info');
            return;
        }
        parent.$.modalDialog({
            title: '调整详情',
            width: 340,
            height: 200,
            resizable: "true",
            href: '${path }/profit/InvoiceSumController/examineAdjust?id=' + id
    });
    }

    //查询
    function searchInfo() {
        InvoiceSumGrid.datagrid('load', $.serializeObject($('#InvoiceSumForm')));
    }

    //重置
    function cleanReset() {
        $("#InvoiceSumForm input").val("");
        $('#profitMonth').datebox('setValue', '');
        $('#invoiceStatus').combobox('setValue', '')

        InvoiceSumGrid.datagrid('load', {});
    }
// 导入
    function importInvoiceSum(){
        parent.$.modalDialog({
            title: '导入发票上月剩余基数',
            width: 350,
            height: 200,
            href: '/profit/InvoiceSumController/importInvoiceSumPage',
            buttons: [{
                text: '确定',
                handler: function() {
                    parent.$.modalDialog.openner_dataGrid = InvoiceSumGrid;
                    var f = parent.$.modalDialog.handler.find('#InvoiceSumImForm');
                    f.submit();
                }
            }]
        })
    }
   //导出模板
    function exportInvoiceSum(){
    window.location.href = "${path}/profit/InvoiceSumController/exportTemplate";

    }
</script>

<!--页面布局-->
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="ticketInfo" data-options="region:'west',border:true" style="width: 100%;overflow: hidden;">
        <table id="InvoiceSumList" data-options="fit:true,border:false"></table>
    </div>
    <!--检索条件：北-->
    <div id="" data-options="region:'north',border:false" style="height:60px;overflow: hidden;background-color: #fff;">
        <form id="InvoiceSumForm" method="post" action="${path}/profit/invoiceDetail/dowmloadData">
            <table>
                <tr>
                    <th>代理商名称:</th>
                    <td><input name="agentName" id="agentName"
                               style="line-height: 17px;border: 1px solid #ccc;width: 160px"/></td>
                    <th>代理商唯一码:</th>
                    <td><input name="agentId" id="agentId"
                               style="line-height: 17px;border: 1px solid #ccc;width: 160px"/></td>
                    <th>机构名称:</th>
                    <td><input name="topOrgName" id="topOrgName"
                               style="line-height: 17px;border: 1px solid #ccc;width: 160px"/></td>
                    <th>机构编码:</th>
                    <td><input name="topOrgId" id="topOrgId"
                               style="line-height: 17px;border: 1px solid #ccc;width: 160px"/></td>
                </tr>
                <tr>
                    <th>状态:</th>
                    <td><select id="invoiceStatus" class="easyui-combobox" name="invoiceStatus" style="width:160px;">
                        <option value="">-----------请选择---------</option>
                        <option value="00">冻结</option>
                        <option value="99">解冻</option>

                    </select></td>
                    <th>月份:</th>
                    <td><input id="profitMonth" name="profitMonth" style="width: 160px;"></td>
                    <th>开票公司:</th>
                    <td><input name="invoiceCompany" id="invoiceCompany"
                               style="line-height: 17px;border: 1px solid #ccc;width: 160px"/></td>
                    <th></th>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-search',plain:true" onclick="searchInfo();">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanReset();">重置</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'icon-remove',plain:true" onclick="queryImage();">发票助手</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <shiro:hasPermission name="/Financial/authority">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="importInvoiceSum();">导入</a>
                            <a href='javascript:void(0)' class="easyui-linkbutton" data-options="iconCls:'icon-filter',plain:true" onclick="exportInvoiceSum()">导出模板</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>