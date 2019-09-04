<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<style>
    .editTable {
        border-color: #999;
        border-style: solid;
        border-width: 0 0 1px 1px;
        border-spacing: 0;
        border-collapse: collapse;
        font-size: 14px;
        width: 700px;
    }

    .editTable td,th {
        border-color: #999;
        border-style: solid;
        margin: 0;
        padding: 0px;
        border-width: 1px 1px 0 0;
    }

</style>
<script type="text/javascript">

    //提交保存
    function saveAndApply() {
        var orgId = $("#orgId").val();
        if('' == orgId || null == orgId){
            alertMsg("业务平台编码不能为空！");
            return;
        }
        var templateName = $("#templateName1").val();
        if('' == templateName || null == templateName){
            alertMsg("模板名称不能为空！");
            return;
        }

        var agentId = $("#agentId1").val();
        if('' == agentId || null == agentId){
            alertMsg("代理商唯一码不能为空，请先对业务平台编码进行校验");
            return;
        }
        var agentName = $("#agentName1").val();
        var busPlatform = $("#busPlatform").val();
        var startMonth = $('#dateStart').datebox('getValue');
        var endMonth = $('#dateEnd').datebox('getValue');
        if('' == startMonth || null == startMonth){
            alertMsg("生效日期不能为空！");
            return;
        }
        if('' == endMonth || null == endMonth){
            alertMsg("失效日期不能为空！");
            return;
        }
        var orgTemplatePriceList = new Array();
        var re = false;
        //规则值
        $("#tbodyPrice").find("tr").each(function(){
            var tdArr = $(this).children();
            var ruleId = tdArr.eq(0).find("input").val();
            var ruleType = tdArr.eq(1).find("input").val();
            var rulePrice = tdArr.eq(3).find("input").val();
            if(rulePrice == '' || rulePrice == null){
                alertMsg("规则值不能为空！");
                re = true;
                return false;
            }

            var map1 = {ruleId:ruleId,ruleType:ruleType,rulePrice:rulePrice};
            orgTemplatePriceList.push(map1);
        });

        if(re){
            return false;
        }

        //分润比例
        var orgTemplateRateList = new Array();
        $("#fk").find("tr").each(function(){
            var tdArr = $(this).children();
            var map2 = {rateType:tdArr.eq(0).find("input").eq(0).val(),seqNo:tdArr.eq(0).find("input").eq(1).val(),startValue:tdArr.eq(0).find("input").eq(2).val(),
                endValue:tdArr.eq(1).find("input").val(),rateValue:tdArr.eq(2).find("input").val()};
            orgTemplateRateList.push(map2);
        });

        $("#tx").find("tr").each(function(){
            var tdArr = $(this).children();
            var map2 = {rateType:tdArr.eq(0).find("input").eq(0).val(),seqNo:tdArr.eq(0).find("input").eq(1).val(),startValue:tdArr.eq(0).find("input").eq(2).val(),
                endValue:tdArr.eq(1).find("input").val(),rateValue:tdArr.eq(2).find("input").val()};
            orgTemplateRateList.push(map2);
        });
        $("#xsh").find("tr").each(function(){
            var tdArr = $(this).children();
            var map2 = {rateType:tdArr.eq(0).find("input").eq(0).val(),seqNo:tdArr.eq(0).find("input").eq(1).val(),startValue:tdArr.eq(0).find("input").eq(2).val(),
                endValue:tdArr.eq(1).find("input").val(),rateValue:tdArr.eq(2).find("input").val()};
            orgTemplateRateList.push(map2);
        });
        $("#fw").find("tr").each(function(){
            var tdArr = $(this).children();
            var map2 = {rateType:tdArr.eq(0).find("input").eq(0).val(),seqNo:tdArr.eq(0).find("input").eq(1).val(),startValue:tdArr.eq(0).find("input").eq(2).val(),
                endValue:tdArr.eq(1).find("input").val(),rateValue:tdArr.eq(2).find("input").val()};
            orgTemplateRateList.push(map2);
        });

        for (var k = 0;k < orgTemplateRateList.length ; k++){
            var rateLi = orgTemplateRateList[k];
            var sv = rateLi.startValue;
            var ev = rateLi.endValue;
            var rv = rateLi.rateValue;

            if(sv == null || sv == ''){
                alertMsg("初始值不能为空!");
                return;
            }
            if(ev == null || ev == ''){
                alertMsg("结束不能为空!");
                return;
            }
            if(rv == null || rv == ''){
                alertMsg("分润比例不能为空!");
                return;
            }
        }

        var data = {orgId:orgId,agentName:agentName,agentId:agentId,busPlatform:busPlatform,startMonth:startMonth,endMonth:endMonth
        ,templateName:templateName,orgTemplatePriceList:JSON.stringify(orgTemplatePriceList),orgTemplateRateList:JSON.stringify(orgTemplateRateList)};


        $.ajaxL({
            type: "POST",
            url: '${path}/profit/template/saveAndApply',
            dataType: 'json',
            data:data,
            success: function(data){
                if (data.resCode == '1') {
                    alertMsg(data.obj);
                    $('#index_tabs').tabs('close', "模板申请");
                    templatApplyList.datagrid('load',{});
                }else{
                    alertMsg(data.resInfo);
                }
            }
        });


    }
    //验证业务平台编码信息
    function checkAgentInfo() {
        var busNum = $("#orgId").val();
        if('' == busNum || busNum == null){
            alertMsg("业务平台编码不能为空！");
            return;
        }
        $(".priceTBody").html('');
        document.getElementById("ttttttype").style.display="none";

        $.ajaxL({
            type: "POST",
            url: '${path}/profit/template/checkBusNum',
            dataType: 'json',
            data:{busNum:busNum},
            success: function(data){
                if (data.resCode == '1') {
                    setVvv(data.obj);
                }else{
                    alertMsg(data.resInfo);
                }
            }
        });
    }

    //设置值
    function setVvv(data) {

        //清除原有值
        $(".priceTBody").html('');
        document.getElementById("ttttttype").style.display="none";

        var agentInfo = data.agentInfo;
        var templateNow = data.templateNow;
        $("#agentName1").val(agentInfo.AG_NAME);  //代理商名称
        $("#agentId1").val(agentInfo.AGENT_ID);  //唯一码
        $("#busPlatform").val(agentInfo.BUS_PLATFORM); //业务平台
        $("#busPlatName").val(agentInfo.PLATFORM_NAME); //平台名称
        $("#templateName1").val(templateNow.templateName);//模板名称
        if(null != templateNow.startMonth && '' != templateNow.startMonth){
            $('#dateStart').datebox('setValue',templateNow.startMonth );
        }
        if(null != templateNow.endMonth && '' != templateNow.endMonth){
            $('#dateEnd').datebox('setValue', templateNow.endMonth);
        }

        console.log(templateNow.type);
        if('0' == templateNow.type){ //默认模板
            document.getElementById("ttttttype").style.display="inline";
        }else if('1' == templateNow.type){
            document.getElementById("ttttttype").style.display="none";
        }

        //分润模板信息
        //结算价
        var orgTemplatePriceList = templateNow.orgTemplatePriceList;

        for (var i = 0; i < orgTemplatePriceList.length; i++) {
            var str = '';
            var tempaltePrice = orgTemplatePriceList[i];
            str = '<tr>' +
                '<td>' + tempaltePrice.ruleGroupName +
                '<input type="hidden" name="ruleId" value="' + tempaltePrice.ruleId + '">' +
                '</td>' +
                '<td>' + tempaltePrice.ruleName +
                '<input type="hidden" name="ruleType" value="' + tempaltePrice.ruleType + '">' +
                '</td>' +
                '<td>' + tempaltePrice.ruleTypeName + '</td>' +
                '<td>' +
                '<input name="rulePrice" class="easyui-validatebox"  required="required"  value="' + tempaltePrice.rulePrice + '"><span style="color: red">*</span>' +
                '</td>' +
                '</tr>';
            $("#priceTable").append(str);
        }

        //分润比例
        var orgTemplateRateList = templateNow.orgTemplateRateList;
        for (var i = 0; i < orgTemplateRateList.length; i++) {
            var templateRate = orgTemplateRateList[i];
            var rateType = templateRate.rateType;
            var tableId = '';
            if ('98' == rateType) {
                tableId = "#98Table";
            } else if ('99' == rateType) {
                tableId = "#99Table";
            }else if (parseInt(rateType)%2 == 1) {
                tableId = "#01Table";
            } else if (parseInt(rateType)%2 == 0) {
                tableId = "#02Table";
            }
            var str = '<tr>' +
                '<td>' +
                '<input type="hidden" name="rateType" value="' + templateRate.rateType + '">' +
                '<input type="hidden" name="seqNo" value="' + templateRate.seqNo + '">' +
                '<input  name="startValue" style="width: 200px;" value="' + templateRate.startValue + '">' +
                '</td>' +
                '<td>' +
                '<input name="endValue" style="width: 200px;" value="' + templateRate.endValue + '">' +
                '</td>' +
                '<td>' +
                '<input  name="rateValue" style="width: 200px;" value="' + templateRate.rateValue + '">' +
                '</td>' +
                '<td>'+
                '<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  onclick="delete001(this)"><span style="color: #2130ee">删除</span></a>'+
                '</td>'+
                '</tr>';
            $(tableId).append(str);
        }

    }

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+''+(m<10?('0'+m):m);
    }

    function myparser(data){
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

    function addFk() {
        var seqNum = 0;
        var rateType = '';
        $("#fk").find("tr").each(function(){
            var tdArr = $(this).children();
            var seqNo = tdArr.eq(0).find("input").eq(1).val();
            rateType = tdArr.eq(0).find("input").eq(0).val();
            console.log(seqNo);
           if(seqNum <=  seqNo){
               seqNum = parseInt(seqNo) + 1;
           }
        });
        console.log(seqNum);
        var str = '<tr>' +
            '<td>' +
            '<input type="hidden" name="'+rateType+'" value="01">' +
            '<input type="hidden" name="seqNo" value="'+seqNum+'">' +
            '<input  name="startValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>' +
            '<input name="endValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>' +
            '<input  name="rateValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>'+
            '<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  onclick="delete001(this)"><span style="color: #2130ee">删除</span></a>'+
            '</td>'+
            '</tr>';
        $("#fk").append(str);
    }

    function addTx() {
        var seqNum = 0;
        var rateType = '';
        $("#tx").find("tr").each(function(){
            var tdArr = $(this).children();
            var seqNo = tdArr.eq(0).find("input").eq(1).val();
            rateType = tdArr.eq(0).find("input").eq(0).val();
            if(seqNum <=  seqNo){
                seqNum = parseInt(seqNo) + 1;
            }
        });
        console.log(seqNum);
        var str = '<tr>' +
            '<td>' +
            '<input type="hidden" name="'+rateType+'" value="02">' +
            '<input type="hidden" name="seqNo" value="'+seqNum+'">' +
            '<input  name="startValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>' +
            '<input name="endValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>' +
            '<input  name="rateValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>'+
            '<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  onclick="delete001(this)"><span style="color: #2130ee">删除</span></a>'+
            '</td>'+
            '</tr>';
        $("#tx").append(str);
    }

    function addXsh() {
        var seqNum = 0;
        var rateType = '';
        $("#xsh").find("tr").each(function(){
            var tdArr = $(this).children();
            var seqNo = tdArr.eq(0).find("input").eq(1).val();
            rateType = tdArr.eq(0).find("input").eq(0).val();
            if(seqNum <=  seqNo){
                seqNum = parseInt(seqNo) + 1;
            }
        });
        console.log(seqNum);
        var str = '<tr>' +
            '<td>' +
            '<input type="hidden" name="'+rateType+'" value="98">' +
            '<input type="hidden" name="seqNo" value="'+seqNum+'">' +
            '<input  name="startValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>' +
            '<input name="endValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>' +
            '<input  name="rateValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>'+
            '<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true"  onclick="delete001(this)"><span style="color: #2130ee">删除</span></a>'+
            '</td>'+
            '</tr>';
        $("#xsh").append(str);
    }

    function addFw() {
        var seqNum = 0;
        var rateType = '';
        $("#fw").find("tr").each(function(){
            var tdArr = $(this).children();
            var seqNo = tdArr.eq(0).find("input").eq(1).val();
            rateType = tdArr.eq(0).find("input").eq(0).val();
            if(seqNum <=  seqNo){
                seqNum = parseInt(seqNo) + 1;
            }
        });
        console.log(seqNum);
        var str = '<tr>' +
            '<td>' +
            '<input type="hidden" name="'+rateType+'" value="99">' +
            '<input type="hidden" name="seqNo" value="' + seqNum + '">' +
            '<input  name="startValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>' +
            '<input name="endValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>' +
            '<input  name="rateValue" class="easyui-numberbox" style="width: 140px;"/><span style="color:red">*</span>' +
            '</td>' +
            '<td>'+
            '<a href="javascript:void(0)"  class="easyui-linkbutton" data-options="plain:true"  onclick="delete001(this)"><span style="color: #2130ee">删除</span></a>'+
            '</td>'+
            '</tr>';
        $("#fw").append(str);
    }

    //删除本行
    function delete001(obj) {
        $(obj).parent().parent().remove();
    }

</script>
<div class="easyui-panel" title="代理商信息" data-options="iconCls:'fi-results'">
    <form id="agentInfo_form">
        <table>
            <tr>
                <td>业务平台编码：</td>
                <td>
                    <input id="orgId" name="busNum" style="width:120px;"/>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="checkAgentInfo()"></a>
                </td>
                <td>代理商名称:</td>
                <td>
                    <input id="agentName1" name="agentName"  readonly="readonly" style="width:200px;">
                </td>
                <td>代理商唯一编码:</td>
                <td>
                    <input id="agentId1" name="agentId"  readonly="readonly" style="width:200px;">
                </td>
                <td>生效时间:</td>
                <td>
                    <input id="dateStart" name="dateStart" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" required="required" >
                </td>
                <td>失效时间：</td>
                <td>
                    <input id="dateEnd" name="dateEnd" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" required="required">
                </td>
            </tr>
            <tr>
                <td>业务平台:</td>
                <td>
                    <input id="busPlatform" name="busPlatform" type="hidden" />
                    <input id="busPlatName" name="busPlatName"  style="width:120px;"/>
                </td>
                <td>分润模板名称:</td>
                <td>
                    <input id="templateName1" name="templateName"  <%--readonly="readonly"--%> style="width:120px;">
                    <span style="display:none;color: red" id="ttttttype">默认模板</span>
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-panel" title="分润模板信息" data-options="iconCls:'fi-results'" style="display:block">
    <form id="profit_template_form">
        <div>
            <table id="priceTable" class="editTable" style="width:700px;padding-top: 17px;overflow:hidden;overflow-y:auto;" border="1" cellspacing="0" cellpadding="0">
                <thead>
                    <tr>
                        <th style="text-align: center;">分润类型</th>
                        <th style="text-align: center;">交易类型</th>
                        <th style="text-align: center;">分润规则</th>
                        <th style="text-align: center;">规则值</th>
                    </tr>
                </thead>
                <tbody class="priceTBody" id="tbodyPrice">
                <tr>
                    <td style="text-align: left;" rowspan="4">标准类</td>
                    <td style="text-align: left;">借记非封顶</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">借记封顶</td>
                    <td style="text-align: left;">按结算笔数</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">贷记T1</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">贷记S0</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;" rowspan="4">优惠类</td>
                    <td style="text-align: left;">借记非封顶</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">借记封顶</td>
                    <td style="text-align: left;">按结算笔数</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">贷记T1</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">贷记S0</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">云闪付</td>
                    <td style="text-align: left;">1000(含)以下</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;" rowspan="2">微信</td>
                    <td style="text-align: left;">T1交易</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">T0交易</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;" rowspan="2">支付宝</td>
                    <td style="text-align: left;">T1交易</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: left;">T0交易</td>
                    <td style="text-align: left;">按结算金额</td>
                    <td style="text-align: center;">
                        <input class="easyui-numberbox"  data-options="required:true" precision="6" max="1" /><span style="color: red">*</span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div>
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">付款交易分润比例:</span>
                <a href="javascript:void(0)"
                                                class="easyui-linkbutton" data-options="plain:true"  onclick="addFk()"><span style="color: #2130ee">增加一行</span></a>
            </div>
            <div>
                <table id="01Table" class="editTable" border="1" cellspacing="0">
                    <thead>
                        <tr>
                            <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                            <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                            <td style="text-align: center;width: 200px;">分润比例</td>
                            <td style="text-align: center;width: 200px;">操作</td>
                        </tr>
                    </thead>
                    <tbody class="priceTBody" id="fk">

                    </tbody>
                </table>
            </div>
        </div>
        <div>
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">提现交易分润比例:</span>
                <a href="javascript:void(0);"
                   class="easyui-linkbutton" data-options="plain:true" onclick="addTx()"><span style="color: #2130ee">增加一行</span></a>
            </div>
            <div>
                <table id="02Table" class="editTable" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">日均提现金额初始值(元)</td>
                        <td style="text-align: center;width: 200px;">日均提现金额结束值(元)</td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 200px;">操作</td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="tx">
                    </tbody>
                </table>
            </div>
        </div>
        <div>
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">2019后新商户付款交易分润比例:</span>
                <span style="color: #00b7ee"><a href="javascript:void(0);"
                                                class="easyui-linkbutton" data-options="plain:true" onclick="addXsh()"><span style="color: #2130ee">增加一行</span></a></span>
            </div>
            <div>
                <table id="98Table" class="editTable" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                        <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 200px;">操作</td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="xsh">

                    </tbody>
                </table>
            </div>
        </div>
        <div style="display:none;">
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">服务费-POS服务费:</span>
                <span style="color: #00b7ee"><a href="javascript:void(0);"
                                                class="easyui-linkbutton" data-options="plain:true" onclick="addFw()"><span style="color: #2130ee">增加一行</span></a></span>
            </div>
            <div>
                <table id="99Table" class="editTable" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                        <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 200px;">操作</td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="fw">

                    </tbody>
                </table>
            </div>
        </div>
        <div>
            <div style="padding-top: 80px;padding-bottom: 50px;text-align: center;" >
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'" onclick="saveAndApply()">提交</a>
            </div>
        </div>
    </form>
</div>
