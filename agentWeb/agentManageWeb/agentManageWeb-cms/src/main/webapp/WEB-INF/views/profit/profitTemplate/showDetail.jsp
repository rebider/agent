<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        if('${applyTemplatePriceList}' != '' && '${applyTemplatePriceList}' != null){

            var orgTemplatePriceList = JSON.parse('${applyTemplatePriceList}');
            var oldOrgTemplateList = JSON.parse('${orgTemplatePriceList}');

            for (var i = 0; i < orgTemplatePriceList.length ; i++) {
                var str = '';
                var tempaltePrice = orgTemplatePriceList[i];
                var price = 0;
                var flag1 = false;
                for(var j = 0;j < oldOrgTemplateList.length;j++){
                    var oldTemplatePrice = oldOrgTemplateList[j];

                    if(oldTemplatePrice.ruleId ==  tempaltePrice.ruleId){
                        price = oldTemplatePrice.rulePrice;
                        if(price == tempaltePrice.rulePrice){
                            flag1 = true;
                        }
                        break;
                    }
                }
                if(flag1){  //表示和原有值一致，不进行提示
                    str = '<tr>' +
                        '<td>' + tempaltePrice.ruleGroupName + '</td>' +
                        '<td>' + tempaltePrice.ruleName + '</td>' +
                        '<td>' + tempaltePrice.ruleTypeName + '</td>' +
                        '<td>' + tempaltePrice.rulePrice+ '</td>' +
                        '</tr>';
                }else{
                    str = '<tr>' +
                        '<td>' + tempaltePrice.ruleGroupName + '</td>' +
                        '<td>' + tempaltePrice.ruleName + '</td>' +
                        '<td>' + tempaltePrice.ruleTypeName + '</td>' +
                        '<td>' + tempaltePrice.rulePrice+ '<span style="color:red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原有信息('+price+')</span>'+'</td>' +
                        '</tr>';
                }

                $("#profit_template_form${templateRecode.id} #priceTable1").append(str);
            }

            //设置分润比例
            var  orgTemplateRateList = JSON.parse('${applyTemplateRateList}');
            var oldTemplateRateList = JSON.parse('${orgTemplateRateList}');

            for (var i = 0; i < orgTemplateRateList.length; i++) {
                var templateRate = orgTemplateRateList[i];
                var rateType = templateRate.rateType;
                var tableId = '';

                if ('98' == rateType) {
                    tableId = "#profit_template_form${templateRecode.id} #98Table1";
                }else if ('99' == rateType) {
                    tableId = "#profit_template_form${templateRecode.id} #99Table1";
                }else if (parseInt(rateType)%2 == 1) {
                    tableId = "#profit_template_form${templateRecode.id} #01Table1";
                } else if (parseInt(rateType)%2 == 0) {
                    tableId = "#profit_template_form${templateRecode.id} #02Table1";
                }

                var oldTemplateRate = '';
                for (var j = 0; j < oldTemplateRateList.length; j++){
                    var rateOld = oldTemplateRateList[j];
                    if(rateOld.rateType == templateRate.rateType && rateOld.seqNo == templateRate.seqNo){
                        oldTemplateRate = rateOld;

                        break;
                    }
                }

                var str = '';

                str = '<tr>'+'<td>' + templateRate.startValue + '</td>';

                if(oldTemplateRate == '' || oldTemplateRate == undefined){
                    str += '<td></td>'
                }else if(oldTemplateRate.startValue == null || oldTemplateRate.startValue == undefined){
                    str += '<td><span style="color:red">原有信息( )</span></td>';
                }else if(templateRate.startValue != oldTemplateRate.startValue){
                    str += '<td><span style="color:red">原有信息(' + oldTemplateRate.startValue + ')</span></td>';
                }else{
                    str += '<td></td>';
                }

                str += '<td>' + templateRate.endValue + '</td>';

                if(oldTemplateRate == '' || oldTemplateRate == undefined){
                    str += '<td></td>'
                }else if(oldTemplateRate.endValue == null || oldTemplateRate.endValue == undefined){
                    str += '<td><span style="color:red">原有信息( )</span></td>';
                }else if(templateRate.endValue != oldTemplateRate.endValue){
                    str += '<td><span style="color:red">原有信息(' + oldTemplateRate.endValue + ')</span></td>';
                }else{
                    str += '<td></td>';
                }

                str += '<td>' + templateRate.rateValue + '</td>';

                if(oldTemplateRate == '' || oldTemplateRate == undefined){
                    str += '<td></td>'
                }else if(oldTemplateRate.rateValue == null || oldTemplateRate.rateValue == undefined){
                    str += '<td><span style="color:red">原有信息( )</span></td>';
                }else if(templateRate.rateValue != oldTemplateRate.rateValue){
                    str += '<td><span style="color:red">原有信息(' + oldTemplateRate.rateValue + ')</span></td>';
                }else{
                str += '<td></td>';
                }

                str += '</tr>';

                $(tableId).append(str);
            }
        }else {
            alertMsg("获取该条记录失败，请联系管理员！")
        }

    });

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
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
                    <input id="dateStart" name="dateStart" readonly="readonly" value="${applyDetail.startMonth}" >
                </td>
            </tr>
            <tr>
                <td>业务平台:</td>
                <td>
                    <input id="busPlatName" name="busPlatName"
                            <c:forEach items="${ablePlatForm}" var="ablePlatFormItem"  >
                                <c:if test="${ablePlatFormItem.platformNum == templateRecode.busPlatform}">
                                    value='${ablePlatFormItem.platformName}'
                                </c:if>
                            </c:forEach>
                           style="width:120px;text-align: center"/>
                </td>
                <td>分润模板名称:</td>
                <td>
                    <input id="templateName1" name="templateName"  value="${applyDetail.templateName}" readonly="readonly" >
                    <c:if test="${applyDetail.oldOrgTemplate.templateName != applyDetail.templateName}">
                        <span style="color: red" >原有信息(${applyDetail.oldOrgTemplate.templateName})</span>
                    </c:if>
                    <%--<span style="color: red">原有信息(${applyDetail.oldOrgTemplate.templateName})</span>--%>
                </td>
                <td>失效时间：</td>
                <td id="endMont">
                    <input id="dateEnd" name="dateEnd" readonly="readonly" value="${applyDetail.endMonth}" >
                </td>
            </tr>
        </table>
    </form>
</div>
<div class="easyui-panel" title="分润模板信息" data-options="iconCls:'fi-results'" style="display:block;overflow: hidden;" style="line-height: 17px;">
    <form id="profit_template_form${templateRecode.id}">
        <div style="line-height: 17px;margin-top: 17px;" >
            <span>模板名称：&nbsp;&nbsp; &nbsp; &nbsp;    ${applyDetail.templateName}</span>
        </div>
        <div style="line-height: 17px;margin-top: 17px;" >
            <table id="priceTable1" class="editTable" style="width:700px;padding-top: 17px;overflow:hidden;overflow-y:auto;" border="1" cellspacing="0" cellpadding="0">
                <thead id="theadPrice">
                <tr>
                    <th style="text-align: center;">分润类型</th>
                    <th style="text-align: center;">交易类型</th>
                    <th style="text-align: center;">分润规则</th>
                    <th style="text-align: center;">规则值</th>
                    <span></span>
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
                <table id="01Table1" class="editTable" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                        <td style="text-align: center;width: 200px;"></td>
                        <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                        <td style="text-align: center;width: 200px;"></td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 200px;"></td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="fk">

                    </tbody>
                </table>
            </div>
        </div>
        <div style="line-height: 17px;margin-top: 17px;" id="hiddentx">
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">提现交易分润比例:</span>
            </div>
            <div>
                <table id="02Table1" class="editTable" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">日均提现金额初始值(元)</td>
                        <td style="text-align: center;width: 200px;"></td>
                        <td style="text-align: center;width: 200px;">日均提现金额结束值(元)</td>
                        <td style="text-align: center;width: 200px;"></td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 200px;"></td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="tx">
                    </tbody>
                </table>
            </div>
        </div>
        <div style="line-height: 17px;margin-top: 17px;" id="hidden98">
            <div style="padding-top: 17px;">
                <span style="font-size: 14px">2019后新商户付款交易分润比例:</span>
            </div>
            <div>
                <table id="98Table1" class="editTable" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                        <td style="text-align: center;width: 200px;"></td>
                        <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                        <td style="text-align: center;width: 200px;"></td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 200px;"></td>
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
                <table id="99Table1" class="editTable" border="1" cellspacing="0">
                    <thead>
                    <tr>
                        <td style="text-align: center;width: 200px;">月交易总额初始值(元)</td>
                        <td style="text-align: center;width: 200px;"></td>
                        <td style="text-align: center;width: 200px;">月交易总额结束值(元)</td>
                        <td style="text-align: center;width: 200px;"></td>
                        <td style="text-align: center;width: 200px;">分润比例</td>
                        <td style="text-align: center;width: 200px;"></td>
                    </tr>
                    </thead>
                    <tbody class="priceTBody" id="fw">

                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>
