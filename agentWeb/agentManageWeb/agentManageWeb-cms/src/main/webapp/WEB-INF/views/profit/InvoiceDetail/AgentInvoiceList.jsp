<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript" src="${staticPath }/static/invoice/${invoice_Result}/socket.io.js" />
<script type="text/javascript" src="${staticPath }/static/invoice/${invoice_Result}/websocket-script.js" />

<script type="text/javascript">

    var agentMap = new Array();   // 用于存放选中行的id
    var checkIndex = 0 ;  // 选中行数
    var invoiceNumber = 0 ;  // 选中票数累积和
    var agentInvoiceList;

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
                            searchMessage();
                        }else {
                            handerFail({'errcode':'0020','data':'','description':'推送失败，请重新导入发票！'});
                            alertMsg(data.resInfo);
                            searchMessage();
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
                            searchMessage();
                        }else {
                            handerFail({'errcode':'00200','data':'','description':"部分发票信息审核失败！"});
                            alertMsg(data.resInfo);
                            searchMessage();
                        }
                    }
                });
            }
        },
        onError:function () {
            console.log("链接失败！");
        }
    });

    $(function() {
        agentInvoiceList = $('#agentInvoiceList').datagrid({
            url : '${path}/profit/invoiceDetail/getAgentList',
            striped: true,
            rownumbers: true,
            pagination: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [[{
                field : 'id',
                title : 'id',
                checkbox : true,
            },{
                title : '代理商唯一码',
                field : 'agentId',
                align : 'center',
                width:180
            },{
                title : '代理商名称',
                field : 'agentName',
                align : 'center',
                width:180
            }, {
                title : '开票公司',
                field : 'invoiceCompany',
                align : 'center',
                width:180
            },{
                title : '开票日期',
                field : 'invoiceDate',
                align : 'center',
                width:120
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
                title : '商品名称',
                field : 'invoiceItem',
                align : 'center',
                width:140
            },/*{
                title : '单价',
                field : 'unitPrice',
                align : 'center',
                width:140
            },{
                title : '数量',
                field : 'numberSl',
                align : 'center',
                width:140
            },*/{
                    title : '税价合计',
                    field : 'sumAmt',
                    align : 'center',
                    width:140
             },{
                title : '发票税率',
                field : 'tax',
                align : 'center',
                width:140
            },{
                title : '税额',
                field : 'amountTax',
                align : 'center',
                width:140
            },{
                title : '不含税合计',
                field : 'amount',
                align : 'center',
                width:140
            },{
                title : '销售方公司名称',
                field : 'sallerName',
                align : 'center',
                width:180
            },{
                title : '代开发票备注',
                field : 'remark',
                align : 'center',
                width:180
            },{
                title : '发票种类',
                field : 'invoiceType',
                align : 'center',
                width:140,
                formatter: function (value) {
                    switch (value) {
                        case '1':return '普通电子发票';
                        case '2':return '电子发票专票';
                        case '3':return '普通纸质发票';
                        case '4':return '专用纸质发票';
                        case '5':return '普通纸质卷票';
                        case '7':return '通用机打';
                        case '8':return '的士票';
                        case '9':return '火车票';
                        case '10':return '飞机票';
                        case '11':return '其他';
                        case '12':return '机动车';
                        case '13':return '二手车';
                        case '14':return '定额发票';
                        case '15':return '通行费';
                    }

                }

            },{
                 title : '初审结果',
                 field : 'ysResult',
                 align : 'center',
                 width:140,
                 formatter: function (value, row) {
                     if(value == '1'){
                         return "通过";
                     }else{
                         return "未通过";
                     }
                 }
            },{
                title : '初审备注',
                field : 'rev1',
                align : 'center',
                width:180
            },{
                 title : '终审结果',
                 field : 'esResult',
                 align : 'center',
                 width:140,
                 formatter: function (value, row) {
                     if(value == '0'){
                        return "未通过";
                     }else if(value == '1'){
                            return "通过";
                      }else{
                            return "未审核";
                     }
                 }
            },{
                title : '终审时间',
                field : 'esDate',
                align : 'center',
                width:140
            },{
                title : '操作',
                field : 'aaa',
                align : 'center',
                width:200,
                formatter: function (value, row) {
                    var str = '';
                        if(row.esResult == '0' || row.ysResult == '0'){
                            str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton" ' +
                                'data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="deleteInvoice(\'{0}\');" >删除</a>', row.id);
                            str += '&nbsp;&nbsp;&nbsp;';
                        }
                        if((row.ysResult == '1' && '${numType}' == 'AG') || (row.esResult == '0' || '${numType}' == 'FI')){
                            str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton" ' +
                                'data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="addExpress(\'{0}\');" >寄出</a>', row.id);
                            str += '&nbsp;&nbsp;&nbsp;';
                        }
                        <shiro:hasPermission name="/invoiceManage/uploadInvoice">
                        if(row.ysResult == '0' || row.esResult == '0') {
                            str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton" ' +
                                'data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryImage();" >重新上传</a>');
                            str += '&nbsp;&nbsp;&nbsp;';
                        }
                        </shiro:hasPermission>
                    str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton" ' +
                        'data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryExpress(\'{0}\');" >查看物流</a>', row.id);
                    return str;
                }
            }
            ]],
            onUncheck: function(index, row){// 在用户取消勾选一行的时候触发
                onUncheck(index, row);
            },
            onCheck: function(index, row){//勾选一行的时候触发
                onCheck(index,row);
            },
            onCheckAll: function(rows){//选中所有行触发
                onCheckAll(rows);
            },
            onUncheckAll: function(rows){//取消所有行格触发
                onUncheckAll(rows);
            },
            onLoadSuccess: function(data){//加载数据成功,也就是刷新页面或者点击前页后页的时候触发
                onCheckPage(data);
            },
            toolbar : '#invoiceApplyListToolbar'
        });
    });

    //展示二维码
    function queryImage() {
        window.open('${content}');
    }

    //点击选中单元格设置用户ID组
    function onCheck(index, row){
        if(row.ysResult == '1'){
            agentMap.push(row.id);
            checkIndex ++ ;
            invoiceNumber += row.sumAmt ;
            console.info(agentMap);
            setInvoiceSum();
        }

    }
    //点击取消单元格设置用户ID组
    function onUncheck(index, row){
        for (var i = 0; i < agentMap.length; i ++) {
            var objAgentMap = agentMap[i];
            if(objAgentMap == row.id){
                agentMap.splice(i,1);
                checkIndex -- ;
                invoiceNumber -= row.sumAmt ;
            }
        };
        setInvoiceSum();
    }
    //全选
    function onCheckAll(rows){
        if(row.ysResult == '1') {
            var num = rows.length;
            agentMap = new Array();
            checkIndex = num;
            invoiceNumber = 0;
            for (var i = 0; i < num; i++) {
                var row = rows[i];
                agentMap.push(row.id);
                invoiceNumber += row.sumAmt;
            };
            setInvoiceSum();
        }
    }
    //全部取消
    function onUncheckAll(rows){
        agentMap=[];
        checkIndex = 0 ;
        invoiceNumber = 0 ;
        setInvoiceSum();
    }
    //用户点击翻页的时候，设置勾选
    function onCheckPage(data){
        var rows = data.rows;
        var num = agentMap.length;
        for (var i = 0; i < agentMap.length; i++) { // 已选
            for (var j = 0; j < rows.length; j++) {  // 待选
                var agent=agentMap[i];
                var row=rows[j];
                if(row == agent){
                    if(row.ysResult == '1'){
                        var index = agentInvoiceList.datagrid('getRowIndex',row);
                        agentInvoiceList.datagrid('selectRow', index); //根据id选中行
                        agentMap.splice(num,1); // 删除
                    }
                    break;
                }
            }
        }
    }
    // 批量寄出
    function expressByQuery() {
        $("#DialogExpressDiv").show();
        $("#queryUpdate").val("batch");
        $("#DialogExpressDiv").dialog({
            title : '批量寄出操作',
            width : 300,
            height : 260,
            closed : false,
            closable: true,
            cache : false,
            modal : true
        });
    }
    //批量删除
    function deleteByQuery() {
        console.log(agentMap);
        if(agentMap == null || agentMap == ''){
            alertMsg("请选择发票信息")
        }
        $.messager.confirm('继续操作', '确定删除所选中数据数据？',
            function(r){
                if (r){
                    $.ajax({
                        type: "post",
                        url: '${path }/profit/invoiceDetail/deletebyBatchId',
                        data: JSON.stringify(agentMap),
                        dataType: "json",
                        contentType: 'application/json;charset=utf-8',
                        success: function (data) {
                            console.log(data);
                            if(data.resCode == '1'){
                                parent.$.messager.alert('提示', '删除成功！', 'ok');
                                agentInvoiceList.datagrid('reload');
                            }else {
                                alertMsg(data.resInfo);
                                agentInvoiceList.datagrid('reload');
                            }
                        }
                    });
                }
            });
    }
    //单行删除
    function deleteInvoice(id) {
        if(id == null || id == ''){
            parent.$.messager.alert('提示', '系统错误，该条数据删除失败！', 'error');
        }
        $.messager.confirm('继续操作', '确定删除该数据？',
                                            function(r){
                                if (r){
                                    $.ajax({
                                        type: "post",
                                        url: '${path }/profit/invoiceDetail/deletebyId',
                                        data: {id:id},
                                        dataType: "json",
                                        success: function (data) {
                                            if(data.resCode == '1'){
                                                parent.$.messager.alert('提示', '删除成功！', 'ok');
                                                searchMessage();
                                            }else {
                                                alertMsg(data.resInfo);
                                            }
                                        }
                                    });
                                }
        });
    }
    //查询
    function  searchMessage(){
        agentInvoiceList.datagrid('load', $.serializeObject($('#AgentInvoiceListForm')));
    }
    //重置
    function cleanhSearchInfo(){
        $("#AgentInvoiceListForm input").val("");
        $("#AgentInvoiceListForm select").val("");
        agentInvoiceList.datagrid('load',{});
    }
    //寄出
    function addExpress(id) {
        $("#DialogExpressDiv").show();
        $("#onlId").val(id);
        $("#queryUpdate").val("single");
        console.log($("#onlId").val());
        $("#DialogExpressDiv").dialog({
            title : '核票结果操作',
            width : 300,
            height : 260,
            closed : false,
            closable: true,
            cache : false,
            modal : true
        });
    }
    // 寄出--提交
   function submitRemark() {
       var id = $("#onlId").val();
       var expressRemark = $("#expressRemark").val();
       var expressNumber = $("#expressNumber").val();
       var expressCompany = $("#expressCompany").val();
       var expressDate = $("#expressDateT").datebox('getValue');
       if(id == null){
           parent.$.messager.alert('提示', '系统错误！', 'error');
           return;
       }
       if(expressNumber == '' || expressNumber == undefined){
           parent.$.messager.alert('提示', '快递单号不能为空！', 'error');
           return;
       }
       if(expressCompany == '' || expressCompany == undefined){
           parent.$.messager.alert('提示', '快递公司不能为空！', 'error');
           return;
       }
       if(expressDate == '' || expressDate == undefined){
           parent.$.messager.alert('提示', '寄出日期不能为空！', 'error');
           return;
       }
       var data ;
       var numType = $("#numType1").val();
       var queryUpdate = $("#queryUpdate").val();
       if("single" == queryUpdate){   // 单个寄出
           if("AG" == numType){
               data = {id:id,expressRemark:expressRemark,expressNumber:expressNumber,expressCompany:expressCompany,expressDate:expressDate};
           }else{
               data = {id:id,returnReason:expressRemark,returnExpressNumber:expressNumber,returnExpressCompany:expressCompany,returnDate:expressDate};
           }
           $.ajax({
               type: "post",
               url: '${path }/profit/invoiceDetail/updateExpressInfo',
               data: data,
               dataType: "json",
               success: function (data) {
                   console.log("11111");
                   if(data.resCode == '1'){
                       closes(1);
                       parent.$.messager.alert('提示', data.obj, 'ok');
                       agentInvoiceList.datagrid('reload');
                   }else {
                       alertMsg(data.resInfo);
                   }
               }
           });

       }else{
           if("AG" == numType){
               if(agentMap == null || agentMap == ''){
                   alertMsg("请选择初审通过的发票信息！");
                   return;
               }
               var strData = {expressRemark:expressRemark,expressNumber:expressNumber,expressCompany:expressCompany,expressDate:expressDate};
               data = {id:agentMap,invoiceApply:strData};
               console.log(JSON.stringify(data));

           }else{
               var strData = {returnReason:expressRemark,returnExpressNumber:expressNumber,returnExpressCompany:expressCompany,returnDate:expressDate};
               data = {id:agentMap,invoiceApply:strData};

           }
           $.ajax({
               type: "post",
               url: '${path }/profit/invoiceDetail/expressByBatchId',
               data: JSON.stringify(data),
               dataType: "json",
               contentType: 'application/json;charset=utf-8',
               success: function (data) {
                   console.log("11111");
                   if(data.resCode == '1'){
                       closes(1);
                       parent.$.messager.alert('提示', data.obj, 'ok');
                       agentInvoiceList.datagrid('reload');
                   }else {
                       alertMsg(data.resInfo);
                   }
               }
           });
       }

   }
    //弹出框关闭
   function closes(num) {
       if (num==1) {
           $("#expressNumber").val('');
           $("#expressRemark").val('');
           $("#expressCompany").val('');
           $("#expressDateT").datebox('setValue', '');
           $("#DialogExpressDiv").hide();
           $("#DialogExpressDiv").dialog('close');
       }
   }
   // 查看物流弹框
   function queryExpress(id) {
       $("#queryExpressInfo").show();
       $("#queryId").val(id);
       $("#queryExpressInfo").dialog({
           title : '核票结果操作',
           width : 300,
           height : 260,
           closed : false,
           closable: true,
           cache : false,
           modal : true,
           onOpen:function () {
               queryExpreInfo(1);
           }
       });
   }
    //物流信息展示
   function queryExpreInfo(num) {
       var id = $("#queryId").val();
       console.log(id);
       if(id == null || id == undefined){
           alertMsg("系统错误！");
       }
       var numType = $("#numType").val();
       $.ajax({
           type: "post",
           url: '${path }/profit/invoiceDetail/getInvoiceApplyById',
           data: {id:id},
           dataType: "json",
           success: function (data) {
               console.log(data);
               if(data != null){
                   if("AG" == numType){ // 表示代理商查询,
                       if(num == 1){
                           $("#expressNum").val(data.expressNumber);
                           $("#expressCom").val(data.expressCompany);
                           $("#expressDat").val(data.expressDate);
                           $("#expressRem").val(data.expressRemark);
                       }else if( num == 2){
                           $("#expressNum").val(data.returnExpressNumber);
                           $("#expressCom").val(data.returnExpressCompany);
                           $("#expressDat").val(data.returnDate);
                           $("#expressRem").val(data.returnReason);
                       }
                   }else{
                       if(num == 1){
                           $("#expressNum").val(data.returnExpressNumber);
                           $("#expressCom").val(data.returnExpressCompany);
                           $("#expressDat").val(data.returnDate);
                           $("#expressRem").val(data.returnReason);
                       }else if( num == 2){
                           $("#expressNum").val(data.expressNumber);
                           $("#expressCom").val(data.expressCompany);
                           $("#expressDat").val(data.expressDate);
                           $("#expressRem").val(data.expressRemark);
                       }
                   }
               }
           }
       });

   }
   //提示
   function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }
    //更新发票金额
    function setInvoiceSum() {
        document.getElementById("checkIndex").innerText=checkIndex;
        document.getElementById("invoiceNumber1").innerText=invoiceNumber.toFixed(2);
    }

    // 导出
    function exports() {
        $('#AgentInvoiceListForm').form({
            url : '/profit/invoiceDetail/doDownload',
        });
        $('#AgentInvoiceListForm').submit();
    }

</script>

<!--物流展示-->
<div id="queryExpressInfo" style="display: none">
    <input type="hidden" id="queryId" value="">
    <input type="hidden" id="numType" value="${numType}">
    <div style="width: 300px;overflow: hidden;line-height: 17px;" align="center">
        <input type="button" value="我寄出的" onclick="queryExpreInfo(1);">
        <input type="button" value="我收到的" onclick="queryExpreInfo(2);">
    </div>
    <div>
        <table>
            <tr>
                <th>快递单号:</th>
                <td><input id = "expressNum" style="width:150px ; " value="" ></td>
            </tr>
            <tr><th>快递公司:</th>
                <td ><input id = "expressCom" style="width:150px ; " value="" ></td>
            </tr>
            <tr>
                <th>寄出时间:</th>
                <td><input id = "expressDat" style="width:150px ; " value="" ></td>
            </tr>
            <tr>
                <th>备注：</th>
                <td><input id = "expressRem" style="width:150px ; "  value=""></td>
            </tr>
        </table>
    </div>
</div>
<!--快递信息填写-->
<div id="DialogExpressDiv" style="display: none">
    <table>
        <input id="onlId" name="id" type="hidden" value=""/>
        <input type="hidden" id="numType1" value="${numType}"/>
        <input id="queryUpdate" type="hidden" value=""/>
        <tr>
            <th>快递单号:</th>
            <td><input id="expressNumber" name="expressNumber" style="width:150px ; " class="easyui-validatebox" required="true" > </input></td>
        </tr>
        <tr>
            <th>快递公司:</th>
            <td><input id="expressCompany" name="expressCompany" style="width:150px ; "  class="easyui-validatebox" required="true"> </input></td>
        </tr>
        <tr>
            <th>寄出时间:</th>
            <td><input id="expressDateT" name="expressDate" class="easyui-datebox" required="required" data-options="editable:false" style="width:150px ;"  /></td>
        </tr>
        <tr>
            <th>备注:</th>
            <td><textarea id="expressRemark" name="expressRemark" style="width:150px ;height: 50px"> </textarea></td>
        </tr>
        <tr>
            <th> </th>
                <td align="right">
                    <a href="javascript:void(0);" onclick="submitRemark()"
                       class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a>
                </td>
        </tr>
    </table>
</div>
<!--数据表格展示-->
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="invoiceInfo" data-options="region:'west',border:true" style="width: 100%;overflow: hidden;">
        <table id="agentInvoiceList" data-options="fit:true,border:false"></table>
    </div>
    <div id="aaa" data-options="region:'north',border:false" style="height:58px;overflow: hidden;">
        <div>
            <form id="AgentInvoiceListForm">
                <table>
                    <tr>
                        <th>开票公司:</th>
                        <td>
                            <input name="invoiceCompany" id="invoiceCompany" style="line-height: 17px;border: 1px solid #ccc;width: 120px"/>
                        </td>
                        <th>发票号:</th>
                        <td>
                            <input name="invoiceNumber" id="invoiceNumber" style="line-height: 17px;border: 1px solid #ccc;width: 120px"/>
                        </td>
                        <th>发票代码：</th>
                        <td>
                            <input name="invoiceCode" id="invoiceCode" style="line-height: 17px;border: 1px solid #ccc;width: 120px"/>
                        </td>
                        <th>终审结果:</th>
                        <td>
                            <select id="esResult" name="esResult" style="line-height: 17px;border: 1px solid #ccc;width: 80px">
                                <option value="">请选择</option>
                                <option value="0">未通过</option>
                                <option value="1">通过</option>
                            </select>
                        </td>
                        <td>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-search',plain:true" onclick="searchMessage();">查询</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanhSearchInfo();">重置</a>
                            <%--<a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-remove',plain:true" onclick="deleteByQuery();">批量删除</a>--%>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-remove',plain:true" onclick="queryImage();">发票助手</a>
                            <shiro:hasPermission name="/profit/invoiceDetail/doDownload">
                                  <a href="javascript:void(0);" class="easyui-linkbutton"
                                      data-options="iconCls:'icon-remove',plain:true" onclick="exports();">导出</a>
                            </shiro:hasPermission>
                        </td>
                    </tr>
                    <tr>
                        <shiro:hasPermission name="/finance/invoiceCollect">
                            <th>代理商名称:</th>
                            <td>
                                <input name="agentName" id="agentName" style="line-height: 17px;border: 1px solid #ccc;width: 120px"/>
                            </td>
                            <th>代理商唯一码:</th>
                            <td>
                                <input name="agentId" id="agentId" style="line-height: 17px;border: 1px solid #ccc;width: 120px"/>
                            </td>
                        </shiro:hasPermission>
                        <th></th><td></td><th></th><td></td><th></th><td></td><th></th><td></td><th></th><td></td><td></td>
                        <td>
                            <div  style="text-align:right">
                                <span>共选中</span><span id="checkIndex">0</span><span>张,总金额:</span><span id="invoiceNumber1">0</span>
                                <span><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="expressByQuery();">批量寄出</a></span>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>






