<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    .edit1Table {
        border-color: #999;
        border-style: solid;
        border-width: 0 0 1px 1px;
        border-spacing: 0;
        border-collapse: collapse;
        font-size: 14px;
        width: 1000px;
    }

    .edit1Table td,th {
        border-color: #999;
        border-style: solid;
        margin: 0;
        padding: 0px;
        border-width: 1px 1px 0 0;
    }

</style>
<script type="text/javascript">

    function setData_form_AgentInfo() {

        var newName = $("#templateName1").val();
        if('' == newName || null == newName){
            alertMsg("模板名称不能为空！");
            return;
        }

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

        // 结算价
        var applyTemplatePriceList = new Array();

        var re = false;
        //规则值
        $("#tbodyPrice").find("tr").each(function(){
            var tdArr = $(this).children();
            var ruleId = tdArr.eq(0).find("input").eq(0).val();
            var ruleType = tdArr.eq(0).find("input").eq(1).val();
            var rulePrice = tdArr.eq(3).find("input").val();
            if(rulePrice == '' || rulePrice == null){
                alertMsg("规则值不能为空！");
                re = true;
                return false;
            }
            var map1 = {ruleId:ruleId,ruleType:ruleType,rulePrice:rulePrice};
            applyTemplatePriceList.push(map1);
        });

        if(re){
            return false;
        }

        //分润比例
        var orgTemplateRateList = new Array();
        $("#fk").find("tr").each(function(){
            var tdArr = $(this).children();
            var map2 = {rateType:tdArr.eq(0).find("input").eq(0).val(),seqNo:tdArr.eq(0).find("input").eq(1).val(),startValue:tdArr.eq(0).find("input").eq(2).val(),
                endValue:tdArr.eq(2).find("input").val(),rateValue:tdArr.eq(4).find("input").val()};
            orgTemplateRateList.push(map2);
        });

        $("#tx").find("tr").each(function(){
            var tdArr = $(this).children();
            var map2 = {rateType:tdArr.eq(0).find("input").eq(0).val(),seqNo:tdArr.eq(0).find("input").eq(1).val(),startValue:tdArr.eq(0).find("input").eq(2).val(),
                endValue:tdArr.eq(2).find("input").val(),rateValue:tdArr.eq(4).find("input").val()};
            orgTemplateRateList.push(map2);
        });
        $("#xsh").find("tr").each(function(){
            var tdArr = $(this).children();
            var map2 = {rateType:tdArr.eq(0).find("input").eq(0).val(),seqNo:tdArr.eq(0).find("input").eq(1).val(),startValue:tdArr.eq(0).find("input").eq(2).val(),
                endValue:tdArr.eq(2).find("input").val(),rateValue:tdArr.eq(4).find("input").val()};
            orgTemplateRateList.push(map2);
        });
        $("#fw").find("tr").each(function(){
            var tdArr = $(this).children();
            var map2 = {rateType:tdArr.eq(0).find("input").eq(0).val(),seqNo:tdArr.eq(0).find("input").eq(1).val(),startValue:tdArr.eq(0).find("input").eq(2).val(),
                endValue:tdArr.eq(2).find("input").val(),rateValue:tdArr.eq(4).find("input").val()};
            orgTemplateRateList.push(map2);
        });

        var data = {startMonth:startMonth,endMonth:endMonth,applyId:'${templateRecode.templateId}',id:'${templateRecode.id}',busNum:'${templateRecode.busNum}',newName:newName,applyDetail:'${applyDetail}',
            orgTemplatePriceList:JSON.stringify(applyTemplatePriceList),orgTemplateRateList:JSON.stringify(orgTemplateRateList)};
        return data;
    }

    $(function () {

        var applyDetail1 = '${applyDetail}';

        var oldStartMonth = '${oldStartMon}' ;
        if('${applyDetail.startMonth}' != oldStartMonth){
            var str = '<span style="color:red">原有信息（'+oldStartMonth+'）</span>';
            $("#startMont").append(str);
        }
        var oldEndMonth = '${oldEndMon}' ;
        if('${applyDetail.endMonth}' != oldEndMonth){
            var str = '<span style="color:red">原有信息（'+oldEndMonth+'）</span>';
            $("#endMont").append(str);
        }

        // 设置结算价
        var orgTemplatePriceList = JSON.parse('${applyTemplatePriceList}');

        var oldOrgTemplateList = JSON.parse('${orgTemplatePriceList}');
        for (var i = 0; i < orgTemplatePriceList.length ; i++) {
            var str = '';
            var tempaltePrice = orgTemplatePriceList[i];
            var price = 0;
            for(var j = 0;j < oldOrgTemplateList.length;j++){
                var oldTemplatePrice = oldOrgTemplateList[j];
                if(oldTemplatePrice.ruleId ==  tempaltePrice.ruleId){
                    price = oldTemplatePrice.rulePrice;
                    break;
                }
            }
            str = '<tr>' +
                '<td><input type="hidden" name="ruleId" value="' + tempaltePrice.ruleId + '">' +
                '<input type="hidden" name="ruleType" value="' + tempaltePrice.ruleType + '">' +
                '<span>' + tempaltePrice.ruleGroupName + '</span>' +
                '</td>' +
                '<td>' + tempaltePrice.ruleName + '</td>' +
                '<td>' + tempaltePrice.ruleTypeName + '</td>' +
                '<td><input type="text"  name="rulePrice" class="easyui-validatebox"  required="required"  value=" ' + tempaltePrice.rulePrice+ '"/><span style="color: red">*</span></td>';
            if(price == tempaltePrice.rulePrice){
                str += '<td ></td >';
            }else{
                str += '<td><span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原有信息('+price+')</span></td>';
            }
            str += '</tr>';
            $("#priceTable").append(str);
        };

        //设置分润比例
        var  orgTemplateRateList = JSON.parse('${applyTemplateRateList}');
        var oldTemplateRateList = JSON.parse('${orgTemplateRateList}');

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

            var oldTemplateRate = '';
            for (var j = 0; j < oldTemplateRateList.length; j++){
                var rateOld = oldTemplateRateList[j];
                if(rateOld.rateType == templateRate.rateType && rateOld.seqNo == templateRate.seqNo){
                    oldTemplateRate = rateOld;
                    break;
                }
            }

            var str = '<tr>' +
                '<td>'+
                '<input type="hidden" name="rateType" value="' + templateRate.rateType + '">' +
                '<input type="hidden" name="seqNo" value="' + templateRate.seqNo + '">' +
                '<span><input name="startValue"  value="' + templateRate.startValue + '"></span><span style="color: red">*</span></td>';

            if(oldTemplateRate == '' || oldTemplateRate == undefined){
                str += '<td></td>'
            }else if(oldTemplateRate.startValue == null || oldTemplateRate.startValue == undefined){
                str += '<td><span style="color:red">原有信息( )</span></td>';
            }else if(templateRate.startValue == oldTemplateRate.startValue){
                str += '<td></td>';
            }else{
                str += '<td><span style="color:red">原有信息(' + oldTemplateRate.startValue + ')</span></td>';
            }
            str += '<td><span><input name="endValue" value="' + templateRate.endValue + '"></span><span style="color: red">*</span></td>';

            if(oldTemplateRate == '' || oldTemplateRate == undefined){
                str += '<td></td>'
            }else if(oldTemplateRate.endValue == null || oldTemplateRate.endValue == undefined){
                str += '<td><span style="color:red">原有信息( )</span></td>';
            }else if(templateRate.endValue == oldTemplateRate.endValue){
                str += '<td></td>';
            }else{
                str += '<td><span style="color:red">原有信息(' + oldTemplateRate.endValue + ')</span></td>';
            }

            str += '<td><span><input  name="rateValue" value="' + templateRate.rateValue + '"></span><span style="color: red">*</span></td>';

            if(oldTemplateRate == '' || oldTemplateRate == undefined){
                str += '<td></td>'
            }else if(oldTemplateRate.rateValue == null || oldTemplateRate.rateValue == undefined){
                str += '<td><span style="color:red">原有信息( )</span></td>';
            }else if(templateRate.rateValue == oldTemplateRate.rateValue){
                str += '<td></td>';
            }else {
                str += '<td><span style="color:red">原有信息(' + oldTemplateRate.rateValue + ')</span></td>';
            }
             str +=  '</tr>';
            $(tableId).append(str);
        }
    });


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



</script>
<div class="easyui-panel" title="代理商信息" data-options="iconCls:'fi-results'" style="line-height: 17px;">
    <form id="agentInfo_form">
        <table>
            <tr>
                <td>业务平台编码：</td>
                <td>
                    <input id="orgId" value="${templateRecode.busNum}" name="busNum" style="width:120px;" readonly="readonly"/>
                </td>
                <td>代理商名称:</td>
                <td>
                    <input id="agentName1" value="${templateRecode.agentName}" name="agentName"  readonly="readonly" style="width:200px;">
                </td>
                <td>代理商唯一编码:</td>
                <td>
                    <input id="agentId1" value="${templateRecode.agentId}" name="agentId"  readonly="readonly" style="width:200px;">
                </td>
                <td>生效时间:</td>
                <td id="startMont">
                    <input id="dateStart" name="dateStart" value="${applyDetail.startMonth}" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" >
                </td>

            </tr>
            <tr>
                <td>业务平台:</td>
                <td>
                    <input id="busPlatName" name="busPlatName" readonly="readonly"
                            <c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >
                                <c:if test="${ablePlatFormItem.platformNum == templateRecode.busPlatform}">
                                    value='${ablePlatFormItem.platformName}'
                                </c:if>
                            </c:forEach>
                           style="width:120px;text-align: center"/>
                </td>
                <td>分润模板名称:</td>
                <td>
                    <input id="templateName1" name="templateName"  value="${applyDetail.templateName}"  >
                    <c:if test="${applyDetail.oldOrgTemplate.templateName != applyDetail.templateName}">
                        <span style="color: red" >原有信息(${applyDetail.oldOrgTemplate.templateName})</span>
                    </c:if>
                </td>
                <td>失效时间：</td>
                <td id="endMont">
                    <input id="dateEnd" name="dateEnd"  value="${applyDetail.endMonth}"  class="easyui-datebox" data-options="formatter:myformatter,parser:myparser">
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-panel" title="分润模板信息" data-options="iconCls:'fi-results'" style="display:block;overflow: hidden;" style="line-height: 17px;">
    <form id="profit_template_form">
        <div style="line-height: 17px;margin-top: 17px;" >
            <table id="priceTable" class="editTable" style="width:700px;padding-top: 17px;overflow:hidden;overflow-y:auto;" border="1" cellspacing="0" cellpadding="0">
                <thead id="theadPrice">
                <tr>
                    <th style="text-align: center;">分润类型</th>
                    <th style="text-align: center;">交易类型</th>
                    <th style="text-align: center;">分润规则</th>
                    <th style="text-align: center;">规则值</th>
                    <th></th>

                </tr>
                </thead>
                <tbody class="priceTBody" id="tbodyPrice">

                </tbody>
            </table>
        </div>
        <div style="line-height: 17px;margin-top: 17px;" >
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">付款交易分润比例:</span>
            </div>
            <div>
                <table id="01Table" class="edit1Table" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                        <td style="text-align: center;width: 400px;"></td>
                        <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                        <td style="text-align: center;width: 400px;"></td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 400px;"></td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="fk">

                    </tbody>
                </table>
            </div>
        </div>
        <div style="line-height: 17px;margin-top: 17px;" >
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">提现交易分润比例:</span>
            </div>
            <div>
                <table id="02Table" class="edit1Table" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">日均提现金额初始值(元)</td>
                        <td style="text-align: center;width: 400px;"></td>
                        <td style="text-align: center;width: 200px;">日均提现金额结束值(元)</td>
                        <td style="text-align: center;width: 400px;"></td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 400px;"></td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="tx">
                    </tbody>
                </table>
            </div>
        </div>
        <div style="line-height: 17px;margin-top: 17px;" >
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">2019后新商户付款交易分润比例:</span>
            </div>
            <div>
                <table id="98Table" class="edit1Table" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                        <td style="text-align: center;width: 400px;"></td>
                        <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                        <td style="text-align: center;width: 400px;"></td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 400px;"></td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="xsh">

                    </tbody>
                </table>
            </div>
        </div>
        <div style="line-height: 17px;margin-top: 17px;display: none;" >
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">服务费-POS服务费:</span>
            </div>
            <div>
                <table id="99Table" class="edit1Table" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                        <td style="text-align: center;width: 400px;"></td>
                        <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                        <td style="text-align: center;width: 400px;"></td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 400px;"></td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="fw">

                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>
