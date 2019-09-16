<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    $(function () {
       var agentId=$('#supplyAgentId').val();
       if(agentId==''){
           parent.$.messager.alert('提示','无补款代理商信息！', 'error');
           var temp=$('#supplyAgentId').attr('pageTemp');
           $('#index_tabs').tabs('close', "线下补款/上级代扣申请-"+temp);
       }
    });

   $("#dedutionNextMonthRadio").click(function () {
       $(".verification").each(function(){
          $(this).textbox('setValue','');
       });
       $('#paymentAccountType').combobox('setValue', 'bj');
       $("#paymentDate").datebox('setValue','');
       $('#annexFile').filebox('setValue','');
   });

    function doApplyRecoupAndDeduct() {
        console.info("rsh");
        var myForm;
        var isSupply=$('#isSupply').is(":checked");
        if (!isSupply){
            var supplyAndDeductRemark=$('#supplyAndDeductRemark').textbox('getValue')
            if(supplyAndDeductRemark==''){
                parent.$.messager.alert('提示', '此说明不能为空！', 'error');
                return ;
            }
            myForm=new FormData();
            myForm.append('supplyAndDeductRemark',supplyAndDeductRemark);

        }else{
            if($('#annexFile').filebox('getValue')==''){
                parent.$.messager.alert('提示', '附件不能为空！', 'error');
                return ;
            }
            if($('#paymentDate').datebox('getValue')==''){
                parent.$.messager.alert('提示', '打款日期不能为空！', 'error');
                return ;
            }
            $('.verification').each(function () {
               if($(this).val()==''){
                   var effect=$(this).attr('effect');
                   parent.$.messager.alert('提示', effect+'不能为空！', 'error');
                   $(this).focus();
                   return false;
               }
            });
            myForm=new FormData($('#offlineSupply')[0]);
        }
        myForm.append('isSupply',isSupply);
        var P_DEDUCTION_ID=new Array();
        var PROFIT_DATE=new Array();
        var AGENT_NAME=new Array();
        var AGENT_ID=new Array();
        var PARENT_AGENT_NAME=new Array();
        var PARENT_AGENT_ID=new Array();
        var BUS_CODE=new Array();
        var C_INCOM_TIME=new Array();
        var MUST_DEDUCTION_AMT=new Array();
        var PROFIT_AMT=new Array();
        var repaymentCycle=new Array();
        var REMIT_AMT=new Array();
        var isChecked= new Array();
        $('[name="P_DEDUCTION_ID"]').each(function(){
            P_DEDUCTION_ID.push($(this).val());
        });
        $('[name="PROFIT_DATE"]').each(function(){
            PROFIT_DATE.push($(this).html());
        });
        $('[name="AGENT_NAME"]').each(function(){
            AGENT_NAME.push($(this).html());
        });
        $('[name="AGENT_ID"]').each(function(){
            AGENT_ID.push($(this).html());
        });
        $('[name="PARENT_AGENT_NAME"]').each(function(){
            if (""==$(this).html()){
                PARENT_AGENT_NAME.push("null");
            }else{
                PARENT_AGENT_NAME.push($(this).html());
            }
        });
        $('[name="PARENT_AGENT_ID"]').each(function(){
            if (""==$(this).html()){
                PARENT_AGENT_ID.push("null");
            }else{
                PARENT_AGENT_ID.push($(this).html());
            }
        });
        $('[name="BUS_CODE"]').each(function(){
            BUS_CODE.push($(this).html());
        });
        $('[name="C_INCOM_TIME"]').each(function(){
            C_INCOM_TIME.push($(this).html());
        });
        $('[name="MUST_DEDUCTION_AMT"]').each(function(){
            MUST_DEDUCTION_AMT.push($(this).html());
        });
        $('[name="PROFIT_AMT"]').each(function(){
            PROFIT_AMT.push($(this).html());
        });
        $('[name="repaymentCycle"]').each(function(){
            repaymentCycle.push($(this).html());
        });
        $('[name="REMIT_AMT"]').each(function(){
            REMIT_AMT.push($(this).val());
        });
        $('[name="isDeductParentAgent"]').each(function(){
            isChecked.push($(this).is(":checked"));
        });

        myForm.append('P_DEDUCTION_ID',P_DEDUCTION_ID);
        myForm.append('PROFIT_DATE',PROFIT_DATE);
        myForm.append('AGENT_NAME',AGENT_NAME);
        myForm.append('AGENT_ID',AGENT_ID);
        myForm.append('PARENT_AGENT_NAME',PARENT_AGENT_NAME);
        myForm.append('PARENT_AGENT_ID',PARENT_AGENT_ID);
        myForm.append('BUS_CODE',BUS_CODE);
        myForm.append('C_INCOM_TIME',C_INCOM_TIME);
        myForm.append('MUST_DEDUCTION_AMT',MUST_DEDUCTION_AMT);
        myForm.append('PROFIT_AMT',PROFIT_AMT);
        myForm.append('repaymentCycle',repaymentCycle);
        myForm.append('REMIT_AMT',REMIT_AMT);
        myForm.append('isDeductParentAgent',isChecked);


        $.ajax({
            url:"${path }/toolsDeduct/addRecoupAndDeduct",
            type: 'post',
            data:myForm,
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                var result=$.parseJSON(data);
                if (result.success) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    }

    $('#paymentDate').datebox().datebox('calendar').calendar({
        validator : function(date){
            var now = new Date();
            return date<=now;
        }
    });
    $('#paymentDate').datebox({
        parser:function(data) {
            var t = Date.parse(data);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });
</script>
<style type="text/css">
    #addRecoupAndDeduct_table td{
        text-align: center;
    }
    #supplyAndDeductForm *:not(input),#nextMonthDeduction *:not(input){
        font-size: 16px;
    }
</style>
<input type="hidden" id="supplyAgentId" pageTemp="${agentId}" value="${profitDatas[0].AGENT_ID}">
<div class="easyui-panel" data-options="fit:true,border:false">
    <div data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="addRecoupAndDeduct_table" class="grid" data-options="fit:true,border:false">
            <tr>
                <td>月份</td>
                <td>代理商名称</td>
                <td>代理商唯一码</td>
                <td>上级代理商名称</td>
                <td>上级代理商唯一码</td>
                <td>机具类型</td>
                <td>入网时间</td>
                <td>机具欠款总额</td>
                <td>上月月分润</td>
                <td>预计还款周期</td>
                <td>线下补款</td>
                <td>上级代扣</td>
            </tr>
            <c:forEach items="${profitDatas}" var="profitData">
                <tr>
                    <td>
                        <input name="P_DEDUCTION_ID" type="hidden" value="${profitData.ID}">
                        <span name="PROFIT_DATE">${profitData.PROFIT_DATE}</span>
                    </td>
                    <td><span name="AGENT_NAME">${profitData.AG_NAME}</span></td>
                    <td><span name="AGENT_ID">${profitData.AGENT_ID}</span></td>
                    <td><span name="PARENT_AGENT_NAME">${profitData.PARENT_AGENT_NAME}</span></td>
                    <td><span name="PARENT_AGENT_ID">${profitData.PARENT_AGENT_ID}</span></td>
                    <td><span name="BUS_CODE">${profitData.BUS_CODE}</span></td>
                    <td><span name="C_INCOM_TIME">${profitData.C_INCOM_TIME}</span></td>
                    <td><span name="MUST_DEDUCTION_AMT">${profitData.MUST_DEDUCTION_AMT}</span></td>
                    <td><span name="PROFIT_AMT">${profitData.PROFIT_AMT}</span></td>
                    <td><span name="repaymentCycle">${profitData.repaymentCycle}</span></td>
                    <td>
                        <input id="${profitData.AGENT_ID}_REMIT_AMT" style="width: 100px" name="REMIT_AMT" class="easyui-numberbox" type="text" data-options="min:0,precision:2,max:${profitData.MUST_DEDUCTION_AMT}" value="0">
                    </td>
                    <td>
                        <c:if test="${profitData.PARENT_AGENT_ID != null && profitData.hasParentAgent==null}">
                            <input name="isDeductParentAgent" type="checkbox" style="width: 20px;height: 20px"/>
                        </c:if>
                        <c:if test="${profitData.PARENT_AGENT_ID != null && profitData.hasParentAgent!=null}">
                            <input name="isDeductParentAgent" disabled type="checkbox" style="width: 20px;height: 20px"/>
                        </c:if>
                        <c:if test="${profitData.PARENT_AGENT_ID == null }">
                            <input name="isDeductParentAgent" disabled type="checkbox" style="width: 20px;height: 20px"/>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div style="margin-top: 50px" id="supplyAndDeductForm">
        <div>
            <input type="radio" id="isSupply" name="payWay" value="isSupply"><label for="isSupply">补款</label>
        </div>
        <hr>
        <div>
            <form id="offlineSupply" method="post" enctype="multipart/form-data">
                <table>
                    <tr><td><h2>线下补款</h2></td></tr>
                    <tr>
                        <td>收款账户类型</td>
                        <td>
                            <select id="paymentAccountType" effect="收款账户类型" name="paymentAccountType" class="easyui-combobox verification" style="width:200px;">
                                <option value="bj">北京</option>
                                <option value="sz">深圳</option>
                            </select>
                        </td>
                        <td>付款账户名</td>
                        <td>
                            <input id="paymentAccountName" effect="付款账户名" name="paymentAccountName" class="easyui-textbox verification" style="width:200px">
                        </td>
                        <td>付款账号</td>
                        <td>
                            <input id="paymentAccount" effect="付款账号" name="paymentAccount" class="easyui-textbox verification" style="width:200px">
                        </td>
                    </tr>
                    <tr>
                        <td>付款开户行</td>
                        <td>
                            <input id="paymentBank" effect="付款开户行" name="paymentBank" class="easyui-textbox verification" style="width:200px">
                        </td>
                        <td>缴纳款项</td>
                        <td>
                            <input id="payAmt" effect="缴纳款项" name="payAmt" class="easyui-textbox verification" style="width:200px">
                        </td>
                        <td>附件</td>
                        <td>
                            <input id="annexFile" name="annexFile" class="easyui-filebox" data-options="prompt:'请选择...',buttonText:'选择'" style="width:200px">
                        </td>
                    </tr>
                    <tr>
                        <td>打款日期</td>
                        <td>
                            <input id="paymentDate" name="paymentDate" type= "text" class= "easyui-datebox" required ="required">
                        </td>
                    </tr>
                </table>
                <hr>
            </form>
        </div>
        <div id="nextMonthDeduction">
            <input type="radio" name="payWay" id="dedutionNextMonthRadio" checked> <label for="dedutionNextMonthRadio">申请下月扣款</label>
            <br>
            <div>
                <input id="supplyAndDeductRemark" name="supplyAndDeductRemark" class="easyui-textbox" data-options="multiline:true,prompt:'申请说明'" style="width:800px;height:100px">
            </div>
        </div>
    </div>
    <div style="text-align: center">
        <button id="submitRecoup" onclick="doApplyRecoupAndDeduct()" style="width: 140px;height: 25px;margin: auto">提交</button>
    </div>
</div>