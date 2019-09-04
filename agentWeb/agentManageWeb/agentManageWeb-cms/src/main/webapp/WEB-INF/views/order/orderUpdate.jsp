<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    //活动信息
    var activity_all =${allActivityList};
    //订单json
    var oSubOrdersJson = ${data.oSubOrdersJson};
    //商品活动
    var sSubOrderActivitysJson = ${data.sSubOrderActivitysJson};
    //商品表格
    var buildOrder_product_update ;

    //自定义异常
    function BuildOrderException(message, code){
        this.msg = message;
        this.code = code;
    }

    $(function() {



        //商品信息
        buildOrder_product_update =  $('#buildOrder_product_update').datagrid({
            idField:'proId' , //只要创建数据表格 就必须要加 ifField
            fit:false ,
            width:'100%',
            fitColumns:true ,
            striped: true , //隔行变色特性
            loadMsg: '数据正在加载,请耐心的等待...' ,
            rownumbers:true ,
            columns:[[
                {
                    field:'proId' ,
                    title:'商品代码',
                    width:200
                },{
                    field:'proName' ,
                    title:'商品名称',
                    width:200
                },{
                    field:'proNum' ,
                    title:'商品数量',
                    width:200,
                    editor:'text'
                },{
                    field:'activity' ,
                    title:'活动',
                    width:300,
                    editor:'combobox'
                }, {
                    field: 'action',
                    title: '操作',
                    width: 350,
                    formatter: function (value, row, index) {
                        var str = '';
                        str += $.formatString('<a href="javascript:void(0)" class="orderbuild_deletefromproduces-up-easyui-linkbutton-updateedit" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="deleteFromProducts({0})" >删除</a>',index);
                        return str;
                    }
                }
            ]],
            onLoadSuccess:function(data){

                for (var i in data.rows){
                    buildOrder_product_update.datagrid("beginEdit",i);
                    var tagt =  buildOrder_product_update.datagrid("getEditor",{'index':i,'field':'activity'}).target;
                    var proId = data.rows[i].proId;
                    for(var j=0;j<sSubOrderActivitysJson.length;j++){
                        if( sSubOrderActivitysJson[j].subOrderId == data.rows[i].id ){
                          $(tagt).val(sSubOrderActivitysJson[j].activityId);
                        }
                    }
                }
                $('.orderbuild_deletefromproduces-up-easyui-linkbutton-updateedit').linkbutton({text:'删除'});

            }
        });
        var isFirst = true;
        //平台修改
        $("#update_orderPlatform").combobox({
            url:'/orderbuild/orderAgentPlatformBus?agentId=${data.agent.id}',
            valueField:'ID',
            textField:'FIELDSHOW',
            onChange: function (n,o) {
                if(!isFirst) {
                    var rows = buildOrder_product_update.datagrid("getRows");
                    if (rows.length > 0) {
                        if ($("#update_orderPlatform").attr("oldValue") != n) {
                            parent.$.messager.confirm('询问', '确认修改平台么，商品信息将被清空？', function (b) {
                                if (b) {
                                    //清空商品
                                    platformChangeDelProduct_update();
                                } else {
                                    $("#update_orderPlatform").attr("oldValue", o);
                                    $("#update_orderPlatform").combobox("setValue", o);
                                }
                            });
                        }
                    }
                    $("#update_orderPlatform").attr("oldValue", n);
                    rdbmodDisplay_update();
                }
            },
            onLoadSuccess:function(){
                $("#update_orderPlatform").combobox("setValue","${data.order.busId}");
                isFirst= false;
                rdbmodDisplay_update();
            }
        });


        $.extend($.fn.datagrid.defaults.editors, {
            text: {
                init: function(container, options){
                    var input = $('<input type="text" class="datagrid-editable-input" oninput="update_numChange(this)" >').appendTo(container);
                    return input;
                },
                destroy: function(target){
                    $(target).remove();
                },
                getValue: function(target){
                    return $(target).val();
                },
                setValue: function(target, value){
                    $(target).val(value);
                },
                resize: function(target, width){
                    $(target)._outerWidth(width);
                }
            },
            combobox:{
                init: function(container, options){
                    var proId = $(container).parent().parent().parent().parent().parent().parent().find("td[field='proId']").children().text();
                    var agentId = $('#agentId').val();
                    var update_orderPlatform = $('#update_orderPlatform').combobox('getValue');
                    var activity = [];
                    $.ajaxL({
                        type: "POST",
                        url: basePath+"/activity/queryProductCanActivity",
                        dataType:'json',
                        async:false,
                        data: {agentId:agentId,proId:proId,orderAgentBusifo:(update_orderPlatform==null?"":update_orderPlatform)},
                        beforeSend:function(){
                            progressLoad();
                        },
                        success: function(msg){
                            if(msg && msg.length>0){
                                activity.push('<select style="height:20px;" onchange="update_activityChange(this)">');
                                activity.push('<options>');
                                activity.push('<option value="" price="">--请选择--</option>');
                                for (var i=0;i<msg.length;i++){
                                    activity.push('<option value="'+msg[i].id+'" price="'+msg[i].price+'">');
                                    activity.push(msg[i].activityName+"/活动价"+msg[i].price+"元"+"/原价"+msg[i].originalPrice+"元");
                                    activity.push('</option>');
                                }
                                activity.push('</options>');
                                activity.push('</select>');

                            }else{
                                activity.push('<input type="hidden"/>');
                            }

                        },
                        complete:function(){
                            progressClose();
                        }
                    });
                    var input = $(activity.join('')).appendTo(container);
                    return input;
                },
                destroy: function(target){
                    $(target).remove();
                },
                getValue: function(target){
                    return $(target).val();
                },
                setValue: function(target, value){
                    $(target).val(value);
                },
                resize: function(target, width){
                    $(target)._outerWidth(width);
                }
            }

        });

        buildOrder_product_update.datagrid("loadData",oSubOrdersJson);

        //结算方式控制分期显示
        $("#update_paymentMethod").combobox({
            onChange: function (n,o) {
                switch (n){
                    case 'FKFQ':
                        $.parser.parse($("#shoufu_model"));
                        $.parser.parse($("#order_update_cash_receivables"));
                        $.parser.parse($("#dikou_model"));
                        $("#shoufu_model").css("display","none");
                        $("#order_update_cash_receivables").css("display","none");
                        $("#dikou_model").css("display","none");
                        $("#fenqi_model").css("display","");
                        $.parser.parse($("#fenqi_model"));
                        $("#order_update_cash_receivables #${paylist_model}paylist_src_table").html("");
                        break;
                    case 'FRFQ':
                        $.parser.parse($("#order_update_cash_receivables"));
                        $.parser.parse($("#shoufu_model"));
                        $.parser.parse($("#dikou_model"));
                        $("#shoufu_model").css("display","none");
                        $("#order_update_cash_receivables").css("display","none");
                        $("#dikou_model").css("display","none");
                        $("#fenqi_model").css("display","");
                        $.parser.parse($("#fenqi_model"));
                        $("#order_update_cash_receivables #${paylist_model}paylist_src_table").html("");
                        break;
                    case 'SF1':
                        $("#shoufu_model").css("display","");
                        $("#order_update_cash_receivables").css("display","");
                        $("#fenqi_model").css("display","");
                        $("#dikou_model").css("display","");
                        $.parser.parse($("#shoufu_model"));
                        $.parser.parse($("#order_update_cash_receivables"));
                        $.parser.parse($("#fenqi_model"));
                        $.parser.parse($("#dikou_model"));
                        break;
                    case 'SF2':
                        $("#shoufu_model").css("display","");
                        $("#order_update_cash_receivables").css("display","");
                        $("#fenqi_model").css("display","");
                        $("#dikou_model").css("display","");
                        $.parser.parse($("#shoufu_model"));
                        $.parser.parse($("#order_update_cash_receivables"));
                        $.parser.parse($("#fenqi_model"));
                        $.parser.parse($("#dikou_model"));
                        break;
                    case 'XXDK':
                        $.parser.parse($("#shoufu_model"));
                        $.parser.parse($("#fenqi_model"));
                        $("#shoufu_model").css("display","none");
                        $("#fenqi_model").css("display","none");
                        $("#order_update_cash_receivables").css("display","");
                        $("#dikou_model").css("display","");
                        $.parser.parse($("#order_update_cash_receivables"));
                        $.parser.parse($("#dikou_model"));
                        break;
                    case 'QT':
                        $.parser.parse($("#shoufu_model"));
                        $.parser.parse($("#order_update_cash_receivables"));
                        $.parser.parse($("#fenqi_model"));
                        $("#shoufu_model").css("display","none");
                        $("#order_update_cash_receivables").css("display","none");
                        $("#fenqi_model").css("display","none");
                        $("#dikou_model").css("display","");
                        $.parser.parse($("#dikou_model"));
                        $("#order_update_cash_receivables #${paylist_model}paylist_src_table").html("");
                        break;
                }
                rdbmodDisplay_update();
            },
            onLoadSuccess:function(){
                var val = $(this).combobox('getData');
                if(val.length>0){
                    $(this).combobox("setValue","${data.order.paymentMethod}");
                }
            }
        });



    });

    //
    function platformChangeDelProduct_update(){
        var rows  = buildOrder_product_update.datagrid("getRows");
        if(rows.length>0){
            //清空商品
            for (var i= rows.length -1; i>=0; i--){
                buildOrder_product_update.datagrid("deleteRow",i);
            }
        }

    }

    //数字变更事件
    function update_numChange(t){
        if($(t).val()<=0){
            $(t).val(1);
        }
        update_pan_panel_sumAmount();
    }

    //活动变更
    function update_activityChange(t){
        update_pan_panel_sumAmount();
    }

    //从商品口选择商品
    function selectProduct(item,data){
        //检查是否已经存在
        var rows  = buildOrder_product_update.datagrid("getRows");
        for (var i in rows){

            if(rows[i].proId==item.id){
                info("已存在此商品:"+item.proName);
                return ;
            }

        }

        //添加行
        buildOrder_product_update.datagrid("appendRow",{proId:item.id,proName:item.proName,proPrice:item.proPrice,proNum:1,activity:''});

        rows  = buildOrder_product_update.datagrid("getRows");

        //启用编辑
        for (var i in rows){
            buildOrder_product_update.datagrid("beginEdit",i);
        }

        //更新按钮
        $('.orderbuild_deletefromproduces-up-easyui-linkbutton-updateedit').linkbutton({text:'删除'});

        buildOrder_product_update.datagrid("resize");
        //计算订单金额
        update_pan_panel_sumAmount();

    }
    //获取商品列表中的商品数据
    function getUpdateProductData(id){
        //获取商商品数据
        var rows  = buildOrder_product_update.datagrid("getRows");

        for (var i in rows){

            var ed = buildOrder_product_update.datagrid("getEditor",{index:i,field:'proNum'});

            rows[i].proNum=$(ed.target).val();

            var activity_ed = buildOrder_product_update.datagrid("getEditor",{index:i,field:'activity'});

            rows[i].activity=($(activity_ed.target).val()==null||$(activity_ed.target).val()==undefined ||$(activity_ed.target).val().length==0)?"": $(activity_ed.target).val();

            rows[i].activity_price=$(activity_ed.target).find("option:selected").attr("price");

            if(id && id!=undefined && rows[i].id==id){
                return rows[i];
            }
        }

        return rows;
    }
    //获取数据要存储的商品信息
    function getUpdateDbProductData(){
        //获取商商品数据
        var rows  =getUpdateProductData();
        var arr = [];
        for (var i in rows){
            arr.push({
                id:rows[i].id,
                proId:rows[i].proId,
                proPrice:rows[i].proPrice,
                proRelPrice:(rows[i].activity_price==undefined || rows[i].activity_price=='')?rows[i].proPrice:rows[i].activity_price,
                proNum:rows[i].proNum,
                activity:rows[i].activity,
                sendNum:0
            });
        }
        return arr;
    }
    //删除商品从商品列表中
    function deleteFromProducts(index){
        //删除行数据
        buildOrder_product_update.datagrid("deleteRow",index);

        var rows  = buildOrder_product_update.datagrid("getRows");

        buildOrder_product_update.datagrid("loadData",rows);

        //计算订单金额
        update_pan_panel_sumAmount();
    }


    //控制瑞大宝模块是否显示
    function rdbmodDisplay_update(){
        var update_orderPlatform_data = $("#update_orderPlatform").combobox("getData");
        var update_orderPlatform_value = $("#update_orderPlatform").combobox("getValue");
        var update_paymentMethod_value       = $("#update_paymentMethod").combobox("getValue");
        var isShow = false;
        if(update_orderPlatform_data !=undefined &&  update_orderPlatform_data.length>0){
            for (var i=0;i<update_orderPlatform_data.length;i++){
                if(update_orderPlatform_value==update_orderPlatform_data[i].ID && (update_orderPlatform_data[i].PLATFORM_TYPE=='RDBPOS' || update_orderPlatform_data[i].PLATFORM_TYPE=='RHPOS')){
                    if(update_paymentMethod_value=='SF1' || update_paymentMethod_value=='FRFQ' ){
                        isShow=true;
                    }
                }
            }
        }
        //显示瑞大宝
        if(isShow){
            $("#rdbfr_model_update").css("display","");
        }else{
            $("#rdbfr_model_update").css("display","none");
        }
    }

</script>
<div>
    <div  class="easyui-panel" title="填写订单" style="background:#fafafa;">
        <%--//填写订单布局--%>
        <div data-options="region:'north',title:'填写订单',split:true,iconCls:'icon-ok'" style="">
            <table style="min-height: 50px;">
                <tr>
                    <td >代理商:</td>
                    <td width="300px">
                        <input id="agName" name="agName" type="text" class="easyui-textbox" data-options="prompt:'请选择代理商'"  readonly="readonly"   value="${data.agent.agName}">
                        <input type="hidden" name="agentId" value="${data.agent.id}" id="agentId" >
                        <input type="hidden" name="orderId" value="${data.order.id}" id="orderId" >
                        <c:if test="${!isagent.isOK()}">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentInfoSelectDialogTier({data:this,callBack:agentSelectOrderBuild})">检索代理商</a>
                            <script type="application/javascript">
                                function agentSelectOrderBuild(item,data){
                                    if(item){
                                        $($(data).parent('td').find('#agName')).textbox('setText',item.agName);
                                        $($(data).parent('td').find('input[name=\'agentId\']')).val(item.id);
                                        $.ajaxL({
                                            type:'POST',
                                            url: basePath+'/orderbuild/orderAgentPlatformBus',
                                            dataType:'json',
                                            contentType:'application/json;charset=UTF-8',
                                            data:{agentId:item.id},
                                            beforeSend : function() {
                                                progressLoad();
                                            },
                                            success:function(data){
                                                //删除商品
                                                platformChangeDelProduct_update();

                                                $('#update_orderPlatform').combobox("clear");
                                                //加载平台数据
                                                $('#update_orderPlatform').combobox({
                                                    url:'/orderbuild/orderAgentPlatformBus?agentId='+item.id,
                                                    valueField:'ID',
                                                    textField:'FIELDSHOW',
                                                    onBeforeLoad:function(){
                                                        progressLoad();
                                                    },
                                                    onLoadSuccess:function(){
                                                        progressClose();
                                                        $('#update_orderPlatform').combobox("setValue");
                                                    }
                                                });
                                            },
                                            complete:function(){
                                                progressClose();
                                            }
                                        });
                                    }
                                }
                            </script>
                        </c:if>
                    </td>
                    <td >业务平台:</td>
                    <td width="300px">
                        <select name="update_orderPlatform" style="width: 100%;" class="easyui-combobox" id="update_orderPlatform">
                            <%--<options>--%>
                                <%--<c:forEach items="${listPlateform}" var="listPlateformItem">--%>
                                <%--<option value="${listPlateformItem.ID}"--%>
                                        <%--<c:if test="${listPlateformItem.ID==data.order.busId}" >--%>
                                            <%--selected="selected"--%>
                                        <%--</c:if>--%>
                                    <%--platformType="${listPlateformItem.PLATFORM_TYPE}"--%>
                                <%-->${listPlateformItem.FIELDSHOW}</option>--%>
                                <%--</c:forEach>--%>
                            <%--</options>--%>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <%--商品布局--%>
    <div name="product_update_panel" id="product_update_panel"  class="easyui-panel"  title="请选择商品并编辑商品数量" style="background:#fafafa;min-height: 100px;" data-options="iconCls:'fi-wrench',tools:'#buildOrder_product_update_tt'">
        <table data-options="fit:true,border:false" id="buildOrder_product_update"></table>
    </div>
    <div id="buildOrder_product_update_tt">
        <a href="javascript:void(0)" class="fi-plus" style="margin-right: 50px;" onclick="showProductSelectDialog_buildOrder_update()" title="选择商品"></a>
        <%--<a href="javascript:void(0)" class="fi-plus" onclick="javascript:getProductData()" title="获取数据"></a>--%>
        <script type="application/javascript">
            function showProductSelectDialog_buildOrder_update(){
                if($('#update_orderPlatform').combobox('getValue')==undefined || $('#update_orderPlatform').combobox('getValue').length==0){
                    info("请选择业务平台");
                    return;
                }
                if($('#agentId').val()==undefined || $('#agentId').val().length==0){
                    info("请选代理商");
                    return;
                }
                showProductSelectDialog({data:{platformBusId:$('#update_orderPlatform').combobox('getValue')},callBack:selectProduct});
            }
        </script>
    </div>

    <%--结算信息布局--%>
    <div class="easyui-panel" title="结算配置" data-options="iconCls:'icon-ok',footer:'#pay_panel_ft'"  name="pay_panel" id="update_pay_panel">
        <input  type="hidden" name="paymentId" id="paymentId" value="${data.oPayment.id}" readonly="readonly"/>
        <table style="width: 100%;border-collapse:separate; border-spacing:0px 10px;">
            <tr>
                <td style="text-align: right;width: 100px;"><label for="oAmo">订单金额:</label></td>
                <td style="width: 200px;"> <input class="easyui-numberbox" type="text" name="oAmo" id="oAmo" value="${data.order.payAmo}" readonly="readonly" style="width:150px;" data-options="min:0,precision:2"/>/元</td>
                <td style="text-align: right;width: 100px;"><label for="update_paymentMethod">结算方式:</label></td>
                <td style="width: 200px;">
                    <select  class="easyui-combobox" name="paymentMethod"  id="update_paymentMethod" style="width:150px;">
                        <c:forEach var="settlementTypeItem" items="${settlementType}" >
                            <option value="${settlementTypeItem.dItemvalue}" >${settlementTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <%--<td style="text-align: right;width: 100px;"><label for="collectCompany">收款公司:</label></td>--%>
                <%--<td style="width: 200px;">--%>
                    <%--<select  class="easyui-combobox" name="collectCompany"  id="collectCompany" style="width:150px;">--%>
                        <%--<c:forEach var="recCompListItem" items="${recCompList}" >--%>
                            <%--<option value="${recCompListItem.id}"--%>
                                    <%--<c:if test="${recCompListItem.id==data.oPayment.collectCompany}">selected="selected"</c:if>--%>
                            <%-->${recCompListItem.comName}</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
                <td colspan="3">
                    &nbsp;
                </td>
            </tr>
            <tr id="fenqi_model">
                <td style="text-align: right;"><label for="downPaymentCount">分期期数:</label></td>
                <td style="width: 200px;">
                    <select class="easyui-combobox" name="downPaymentCount" style="width:150px;" data-options="editable:false" id="downPaymentCount">
                        <c:forEach var="v" begin="1" end="12" step="1">
                            <option value="${v}"
                                    <c:if test="${v==data.oPayment.downPaymentCount}">selected="selected"</c:if>
                            >${v}期</option>
                        </c:forEach>
                    </select>
                </td>
                <td style="text-align: right;"><label for="downPaymentDate">分期开始时间:</label></td>
                <td style="width: 200px;">
                    <input class="easyui-datebox" type="text" name="downPaymentDate" id="downPaymentDate" style="width:150px;" value="<c:if test="${!empty data.oPayment.downPaymentDate}"><fmt:formatDate pattern="yyyy-MM-dd" value="${data.oPayment.downPaymentDate}" /> </c:if>" /></td>
                <td>
                    &nbsp;
                </td>
                <td colspan="2">
                    <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-magnifying-glass'" onclick="alert('开发中');" >分期计划</a>--%>
                </td>

            </tr>

            <tr id="shoufu_model">
                <td style="text-align: right;width: 150px;"><label for="downPayment">首付金额:</label></td>
                <td style="width: 200px;"> <input class="easyui-numberbox" type="text" name="downPayment" style="width:150px;" id="downPayment" value="${data.oPayment.downPayment}" data-options="min:0,precision:2" />/元</td>
                <td colspan="5" >
                    &nbsp;
                </td>
            </tr>

            <tr id="rdbfr_model_update">
                <td style="text-align: right;width: 150px;"><label for="downPayment">分润形式:</label></td>
                <td >
                    <label for="rdbfr_model_rfr">日分润</label><input type="checkbox" name="rdbfr_model_rfr" id="rdbfr_model_rfr" value="1"
                        <c:if test="${!empty data.oPayment_ProfitForm}">
                        <c:forEach items="${data.oPayment_ProfitForm}" var="oPayment_ProfitForm_item" >
                            <c:if test="${oPayment_ProfitForm_item=='1'}">checked="checked"</c:if>
                        </c:forEach>
                        </c:if>
                     />&nbsp;&nbsp;
                    <label for="rdbfr_model_rfx">日返现</label><input type="checkbox" name="rdbfr_model_rfr" id="rdbfr_model_rfx" value="2"
                        <c:if test="${!empty data.oPayment_ProfitForm}">
                        <c:forEach items="${data.oPayment_ProfitForm}" var="oPayment_ProfitForm_item" >
                            <c:if test="${oPayment_ProfitForm_item=='2'}">checked="checked"</c:if>
                        </c:forEach>
                        </c:if>
                    />&nbsp;&nbsp;
                    <label for="rdbfr_model_xjdk">下级代扣</label><input type="checkbox" name="rdbfr_model_rfr" id="rdbfr_model_xjdk" value="3"
                        <c:if test="${!empty data.oPayment_ProfitForm}">
                        <c:forEach items="${data.oPayment_ProfitForm}" var="oPayment_ProfitForm_item" >
                            <c:if test="${oPayment_ProfitForm_item=='3'}">checked="checked"</c:if>
                        </c:forEach>
                        </c:if>
                    />

                </td>
                <td style="text-align: right;">
                    <label for="downPayment"> 存量分润(月分润):</label>
                </td>
                <td colspan="3">
                    <label for="rdbfr_model_yes">是</label><input type="radio" name="rdbfr_mouth" id="rdbfr_model_yes" value="1"
                            <c:if test="${data.oPayment.profitMouth=='1'}">checked="checked"</c:if>
                    />&nbsp;&nbsp;
                    <label for="rdbfr_model_no">否</label><input type="radio" name="rdbfr_mouth" id="rdbfr_model_no" value="0"
                            <c:if test="${data.oPayment.profitMouth=='0'}">checked="checked"</c:if>
                    />
                </td>
            </tr>
            <%--<tr id="shifu_model">--%>
                <%--<td style="text-align: right;width: 150px;"><label for="downPayment">实付金额:</label></td>--%>
                <%--<td style="width: 200px;"> <input class="easyui-numberbox" type="text" name="actualReceipt" style="width:150px;" id="actualReceipt" value="${data.oPayment.actualReceipt}" data-options="min:0,precision:2" />/元</td>--%>
                <%--<td style="text-align: right;width: 150px;"><label for="update_downPaymentUser">打款人:</label></td>--%>
                <%--<td> <input class="easyui-textbox" type="text" name="downPaymentUser" id="update_downPaymentUser" value="${data.oPayment.downPaymentUser}"  style="min-width: 100px;"/></td>--%>
                <%--<td style="text-align: right;"><label for="actualReceiptDate">打款时间:</label></td>--%>
                <%--<td>  <input class="easyui-datebox" type="text" name="actualReceiptDate" id="actualReceiptDate" value="<c:if test="${!empty data.oPayment.actualReceiptDate}"><fmt:formatDate pattern="yyyy-MM-dd" value="${data.oPayment.actualReceiptDate}" /></c:if>" /></td>--%>
            <%--</tr>--%>
            <shiro:hasPermission name="order_apr_Permission_isCloInvoice">
                <%--业务部审核分是否开具发票--%>
                <tr>
                    <td style="text-align: right;width: 100px;">是否开具发票:</td>
                    <td colspan="6">
                        <label><input type="radio" value="0" name="isCloInvoice" <c:if test="${!empty data.oPayment.isCloInvoice}"> <c:if test="${data.oPayment.isCloInvoice == 0}"> checked="checked" </c:if></c:if> />否</label>
                        <label><input type="radio" value="1" name="isCloInvoice" <c:if test="${!empty data.oPayment.isCloInvoice}"> <c:if test="${data.oPayment.isCloInvoice == 1}"> checked="checked" </c:if></c:if> />是</label>
                    </td>
                </tr>
                <tr><td style="text-align: right;width: 100px;"/>
                    <td colspan="6"><label style="color: red;font-size:11px;">1、如果选择开发票，那么要求代理商也同意给我司开分润发票；</label></td></tr>
                <tr><td style="text-align: right;width: 100px;"/>
                    <td colspan="6"><label style="color: red;font-size:11px;">2、只针对代理商打款到我司公户的部分开发票；</label></td></tr>
                <tr><td style="text-align: right;width: 100px;"/>
                    <td colspan="6"><label style="color: red;font-size:11px;">3、我司开具的发票与代理商打款的打款人信息一致，提供的开票信息与打款人不一致时无法开票；</label></td></tr>
                <tr><td style="text-align: right;width: 100px;"/>
                    <td colspan="6"><label style="color: red;font-size:11px;">4、如需开发票，请上传附件时带上开票信息的附件；</label></td></tr>
            </shiro:hasPermission>
            <tr id="danbaodaili_model">
                <td style="text-align: right;width: 150px;"><label for="downPayment">结算价:</label></td>
                <td style="width: 250px;">
                    <input class="easyui-textbox"  name="settlementPriceStr" id="update_settlementPriceStr" disabled="disabled" value="${data.oPayment.settlementPriceStr}" >
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showSettlementTypeSelectDialog({target:this,callBack:function(item,data){
                             $('#update_settlementPriceStr').textbox('setValue',item);
                        }})">结算价</a>
                </td>
                <%--<td style="text-align: right;width: 150px;"><label for="downPayment">担保代理商:</label></td>--%>
                <%--<td style="width: 200px;">--%>
                    <%--<input class="easyui-textbox"  name="guaranteeAgent" id="guaranteeAgent"  value="<agent:show type="agent" busId="${data.oPayment.guaranteeAgent}"/>" >--%>
                    <%--<input type="hidden"  name="guaranteeAgentId" id="guaranteeAgentId" value="${data.oPayment.guaranteeAgent}">--%>
                    <%--&nbsp;&nbsp;&nbsp;&nbsp;--%>
                <%--</td>--%>
                <%--<td colspan="3">--%>
                    <%--<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="showAgentInfoSelectDialog({data:this,callBack:function(item,data){--%>
                            <%--if(item){--%>
                                 <%--$($(data).parent('td').parent('tr').find('#guaranteeAgent')).textbox('setText',item.agName);--%>
                                 <%--$($(data).parent('td').parent('tr').find('#guaranteeAgentId')).val(item.id);--%>
                            <%--}--%>
                        <%--}})">检索代理商</a>--%>
                <%--</td>--%>
            </tr>
            <shiro:hasPermission name="order_apr_Permission_deductionType">
                <%--抵扣信息--%>
                <tr id="dikou_model">
                    <td style="text-align: right;width: 100px;">抵扣信息:</td>
                    <td >
                        <select id="orderupdate_deductionType" name="deductionType" class="easyui-combobox"  >
                            <options>
                                <option value="">--请选择--</option>
                                <c:forEach items="${capitalType}" var="capitalTypeItem">
                                <option value="${capitalTypeItem.dItemvalue}" <c:if test="${capitalTypeItem.dItemvalue == data.oPayment.deductionType}">selected="selected"</c:if> >${capitalTypeItem.dItemname}</option>
                                </c:forEach>
                            </options>
                        </select>
                    </td>
                    <td colspan="2"><label>余额:</label><input  class="easyui-numberbox" id="orderupdate_busniss_capital_all" value="0" readonly="readonly" data-options="min:0,precision:2" />/元</td>
                    <td colspan="2"><label>可用余额:</label><input class="easyui-numberbox" id="orderupdate_busniss_capital_can" value="0" readonly="readonly" data-options="min:0,precision:2" />/元</td>
                    <td><label>抵扣金额:</label><input class="easyui-numberbox" value="<c:if test="${!empty data.oPayment.deductionAmount}">${data.oPayment.deductionAmount}</c:if>" name="deductionAmount" prompt="设置抵扣金额" id="orderupdate_deductionAmount" data-options="min:0,precision:2" />/元</td>
                </tr>
                <script type="application/javascript">



                    $(function(){
                        $("#orderupdate_deductionType").combobox({
                            /**
                             * 抵扣类型变更
                             */
                            onChange: function (n,o) {
                                if(n!=undefined && n.length>0){

                                    if($('#agentId').val()==undefined || $('#agentId').val().length==0){
                                        info("请选代理商");
                                        return;
                                    }

                                    $.ajaxL({
                                        type: "GET",
                                        url: "/orderbuild/queryAgentCanCapital",
                                        dataType:'json',
                                        data:{
                                            agentId:$('#agentId').val(),
                                            type:$("#orderupdate_deductionType").combobox('getValue')
                                        },
                                        beforeSend :function(){
                                            progressLoad();
                                        },
                                        success: function(data){
                                            if(data.status==200){
                                                $("#orderupdate_busniss_capital_all").numberbox('setValue',data.data.all);
                                                $("#orderupdate_busniss_capital_can").numberbox('setValue',data.data.can);
                                            }else{
                                                $("#orderupdate_busniss_capital_all").numberbox('setValue',0);
                                                $("#orderupdate_busniss_capital_can").numberbox('setValue',0);
                                            }
                                        },
                                        complete:function (XMLHttpRequest, textStatus) {
                                            progressClose();
                                        }
                                    });
                                }else{
                                    $("#orderupdate_busniss_capital_all").numberbox('setValue',0);
                                    $("#orderupdate_busniss_capital_can").numberbox('setValue',0);
                                }

                            }
                        });
                    });
                    function getorderupdate_deductionAmount(){
                        var data= {};
                        if($("#orderupdate_deductionAmount").length>0){
                            data.deductionAmount = $("#orderupdate_deductionAmount").numberbox('getValue');
                        }
                        if($("#orderupdate_deductionType").length>0){
                            data.deductionType = $("#orderupdate_deductionType").combobox("getValue");
                        }
                        return data;
                    }
                </script>
            </shiro:hasPermission>
            <tr id="order_update_cash_receivables">
                <td colspan="7">
                    <%@ include file="/commons/order_cash_receivables_edit.jsp"%>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;"><label for="update_buildOrderAttrList">打款截图:</label></td>
                <td colspan="5" id="update_buildOrderAttrList">
                    <c:forEach items="${data.attrs}" var="attrsItem">
                        <span onclick='removeBuildOrderAddattr(this)'>${attrsItem.attName}<input type='hidden' name='buildOrderAttrListFile' value='${attrsItem.id}' /></span>&nbsp;&nbsp;&nbsp;&nbsp;
                    </c:forEach>
                </td>
                <td >
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-multiple'" onclick="multFileUpload(buildOrderAddattr_update);" >添加凭证</a>
                    <script type="application/javascript">
                        function show_fenqi_model(flag,type){
                            if(!flag){
                                $("#fenqi_model").css("display","none");
                            }else{
                                $("#fenqi_model").css("display","");
                            }
                        }
                        //添加附件
                        function buildOrderAddattr_update(data){
                            var jsondata = eval(data);
                            for(var i=0;i<jsondata.length ;i++){
                                $("#update_buildOrderAttrList").append("<span onclick='removeBuildOrderAddattr(this)'>"+jsondata[i].attName+"<input type='hidden' name='buildOrderAttrListFile' value='"+jsondata[i].id+"' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
                            }
                        }
                        //删除附件
                        function removeBuildOrderAddattr(t){
                            parent.$.messager.confirm('询问', '确定删除附件么？', function(b) {
                                if (b) {
                                    $(t).remove();
                                }
                            });
                        }
                        //获取附件信息
                        function getBuildOrderAddattrs(){
                            var attachments = [];
                            var inputs = $("#update_buildOrderAttrList").find("input[name='buildOrderAttrListFile']");
                            for(var i=0;i<inputs.length;i++){
                                var id =  $(inputs[i]).val();
                                attachments.push({id:id});
                            }
                            return attachments;
                        }
                    </script>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;"><label for="remark">备注:</label></td>
                <td colspan="6">
                    <input class="easyui-textbox" data-options="multiline:true,prompt:'1、填写申请奖励信息，申请考核信息；2、填写抵扣费用申请信息；'" value="${data.order.remark}" style="width:80%;height:100px" name="remark" id="remark"/>
                </td>

            </tr>
        </table>
    </div>
    <div id="pay_panel_ft" style="padding:10px;">
        <table style="width: 100%;">
            <tr>
                <td colspan="4" style="margin: auto;text-align: center;">
                    <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'fi-save'" style="width: 100px;" onclick="updateOrder()">保存修改</a>
                </td>
            </tr>
        </table>
        <script type="application/javascript">
            $(function() {
                $('#downPaymentDate').datebox({
                    //显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
                    onShowPanel: function () {
                        //触发click事件弹出月份层
                        span.trigger('click');
                        if (!tds)
                        //延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                            setTimeout(function() {
                                tds = p.find('div.calendar-menu-month-inner td');
                                tds.click(function(e) {
                                    //禁止冒泡执行easyui给月份绑定的事件
                                    e.stopPropagation();
                                    //得到年份
                                    var year = /\d{4}/.exec(span.html())[0] ,
                                        //月份
                                        //之前是这样的month = parseInt($(this).attr('abbr'), 10) + 1;
                                        month = parseInt($(this).attr('abbr'), 10);

                                    //隐藏日期对象
                                    $('#downPaymentDate').datebox('hidePanel')
                                    //设置日期的值
                                        .datebox('setValue', year + '-' + month);
                                });
                            }, 0);
                    },
                    //配置parser，返回选择的日期
                    parser: function (s) {
                        if (!s) return new Date();
                        var arr = s.split('-');
                        return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
                    },
                    //配置formatter，只返回年月 之前是这样的d.getFullYear() + '-' +(d.getMonth());
                    formatter: function (d) {
                        var currentMonth = (d.getMonth()+1);
                        var currentMonthStr = currentMonth < 10 ? ('0' + currentMonth) : (currentMonth + '');
                        return d.getFullYear() + '-' + currentMonthStr;
                    }
                });

                //日期选择对象
                var p = $('#downPaymentDate').datebox('panel'),
                    //日期选择对象中月份
                    tds = false,
                    //显示月份层的触发控件
                    span = p.find('span.calendar-text');
                var curr_time = new Date();

                //设置前当月
                $("#downPaymentDate").datebox("setValue", myformatter(curr_time));
            });

            function myformatter(date) {
                //获取年份
                var y = date.getFullYear();
                //获取月份
                var m = date.getMonth() + 1;
                return y + '-' + m;
            }

            //计算订单金额
            function update_pan_panel_sumAmount(){
                var rows = getUpdateDbProductData();
                var amount = 0;
                for(var i in rows){
                    if(rows[i].proNum && rows[i].proNum >= 0){
                        amount = amount + rows[i].proRelPrice * rows[i].proNum ;
                    }
                }
                $("#oAmo").numberbox('setValue', amount);
            }

            //订单保存
            function updateOrder(){
                try{
                    //产品数据
                    var product_data = getUpdateDbProductData();

                    //订单的ID
                    var orderId = $("#orderId").val();
                    //付款单ID
                    var paymentId = $("#paymentId").val();
                    //代理商
                    var agentId = $("#agentId").val();
                    if(agentId==undefined|| agentId.length==0){
                        throw new BuildOrderException("请指定代理商",1);
                    }
                    //平台信息
                    var update_orderPlatform = $("#update_orderPlatform").combobox("getValue");

                    if(update_orderPlatform==undefined|| update_orderPlatform.length==0){
                        throw new BuildOrderException("请指定业务平台",1);
                    }
                    //订单金额
                    var oAmo = $("#oAmo").textbox("getValue");
                    //备注
                    var remark = $("#remark").textbox("getValue");
                    //收款公司
                    //var collectCompany = $("#collectCompany").combobox("getValue");
                    //支付方式
                    var paymentMethod = $("#update_paymentMethod").combobox("getValue");
                    //首付
                    var downPayment = $("#downPayment").numberbox("getValue");
                    //首付分期
                    var downPaymentCount = $("#downPaymentCount").combobox("getValue");
                    //首付分期日期
                    var downPaymentDate = $("#downPaymentDate").datebox("getValue");
                    //打款时间
                    //var actualReceiptDate = $("#actualReceiptDate").datebox("getValue");
                    //打款人
                    //var downPaymentUser = $("#update_downPaymentUser").textbox("getValue");
                    //实际付款金额
                    //var actualReceipt = $("#actualReceipt").numberbox("getValue");
                    //附件信息
                    var attachments = getBuildOrderAddattrs();
                    //担保代理
                    var guaranteeAgentId = $("#guaranteeAgentId").val();
                    //结算价
                    var settlementPriceStr = $("#update_settlementPriceStr").val();
                    //分润形式
                    var profit_form_update = "";
                    $("#rdbfr_model_update input[name='rdbfr_model_rfr']:checked").each(function(){
                        if(profit_form_update==""){
                            profit_form_update=$(this).val();
                        }else{
                            profit_form_update=profit_form_update+","+$(this).val();
                        }
                    });
                    //存量分润(月分润)
                    var profit_mouth_update = $("#rdbfr_model_update input[type='radio'][name='rdbfr_mouth']:checked").val();
                    //必须选择商品
                    if(product_data.length==0){
                        throw new BuildOrderException("请选择商品",1);
                    }
                    //线下支付列表
                    var payList = [];
                    if(typeof getUpdatePayList =='function'){
                        payList = getUpdatePayList('${paylist_model}');
                        if(paymentMethod=='SF1' || paymentMethod=='SF2'){
                            var money = parseInt("0")
                            for(var i=0;i<payList.length;i++){
                                money+=parseInt(payList[i].amount)
                            }
//                            if(downPayment!=money){
//                                info("打款金额不等于首付金额");
//                                return false;
//                            }
                        }
                    }

                    //结算信息
                    var paymentData = {
                        id:paymentId,
                        agentId:agentId,
                        payAmount:oAmo,
                        payMethod:paymentMethod,
                        downPayment:downPayment,
                        downPaymentCount:downPaymentCount,
                        downPaymentDate:downPaymentDate,
                        remark:remark,
                        //downPaymentUser:downPaymentUser,
                        //actualReceiptDate:actualReceiptDate,
                        //actualReceipt:actualReceipt,
                        //collectCompany:collectCompany,
                        guaranteeAgent:guaranteeAgentId,
                        settlementPriceStr:settlementPriceStr,
                        profitForm:profit_form_update,
                        profitMouth:profit_mouth_update
                    };
                    //抵扣信息
                    if(typeof getorderupdate_deductionAmount=== "function" ){
                        var getorderupdate_deductionAmount_data = getorderupdate_deductionAmount();
                        if(getorderupdate_deductionAmount_data.deductionType!=undefined && getorderupdate_deductionAmount_data.deductionType!='' && getorderupdate_deductionAmount_data.deductionAmount>=0){
                            paymentData.deductionAmount = getorderupdate_deductionAmount_data.deductionAmount;
                            paymentData.deductionType = getorderupdate_deductionAmount_data.deductionType;
                        }
                    }
                    //是否开发票
                    if($("#update_pay_panel").find("input[name='isCloInvoice']:radio:checked").length>0){
                        paymentData.isCloInvoice = $("#update_pay_panel").find("input[name='isCloInvoice']:radio:checked").val();
                    }
                    parent.$.messager.confirm('询问', '确认要添加？', function(b) {
                        if (b) {
                            $.ajaxL({
                                type: "POST",
                                url: basePath+"/order/updateOrderAction",
                                dataType:'json',
                                contentType:'application/json;charset=UTF-8',
                                data: JSON.stringify({
                                    id:orderId,
                                    agentId:agentId,//代理商ID
                                    orderPlatform:update_orderPlatform,//平台值
                                    oAmo:oAmo,
                                    paymentMethod:paymentData.payMethod,
                                    remark:remark,
                                    oCashReceivables:payList,
                                    attachments:attachments,
                                    oSubOrder:product_data,//商品
                                    oPayment:paymentData//支付信息
                                }),
                                beforeSend : function() {
                                    progressLoad();
                                },
                                success: function(msg){

                                    if(msg.status=='200'){
                                            info(msg.msg);
                                            progressClose();
                                            if(orderList!=undefined)
                                                orderList.datagrid('reload');
                                            $('#index_tabs').tabs('close',"代理商订货");
                                            $('#index_tabs').tabs('close',"订单修改");
                                    }else{
                                        info(msg.msg);
                                    }
                                },
                                complete:function (XMLHttpRequest, textStatus) {
                                    progressClose();
                                }
                            });
                        }
                    });
                    //异常统一提示
                }catch (e){
                    if(e.code == 1){
                        info(e.msg);
                    }
                }
            }

        </script>
    </div>
</div>
