<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<!--代理商发票录入界面-->
<script type="text/javascript">

    //提交信息
    $(function(){
        $("#EntryInvoiceForm").form({
            url:"${path}/profit/invoiceDetail/submitInvoiceInformation",
            onSubmit : function() {
                var isValid = $(this).form('validate');
                if (isValid) {
                    parent.$('#dispatchBtn').attr('disabled',"true");
                }
                return isValid;
            },
            success : function(result) {
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });

    /*$('#file').filebox({
        buttonText: '选择文件',
        buttonAlign: 'right'
    });*/

    //验证税点
    $.extend($.fn.validatebox.defaults.rules, {
        tax: {
            validator: function(value){
                console.log(value);
              //  var reg = new RegExp(/^[0-9]%$/); //百分比正则
                var reg1 = new RegExp(/^[0]\.[0-9]{1,2}$/); //0.06正则
                if(reg1.test(value)){
                    return true;
                }
                return false;
            },
            message: '税点格式2位小数（例：0.06）'
        },
        invoiceNumber:{
            validator: function(value){
                console.log(value);
                var reg = new RegExp(/^[0-9]*$/);
                if(reg.test(value)){
                    return true;
                }
                return false;
            },
            message: '发票号只能用数字表示'
        },
        invoiceCode:{
            validator: function(value){
                console.log(value);
                var reg = new RegExp(/^[0-9]*$/);
                if(reg.test(value)){
                    return true;
                }
                return false;
            },
            message: '发票代码只能用数字表示'
        },
        //税额
        amountTax:{
            validator: function(value){
                console.log(value);
                var reg = new RegExp(/^[0-9]*\.[0-9]{1,2}$/);
                if(reg.test(value)){
                    return true;
                }
                return false;
            },
            message: '税额需保留2位小数'
        },
        //税价总计
        sumAmt:{
            validator: function(value){
                console.log(value);
                var reg = new RegExp(/^[0-9]*\.[0-9]{1,2}$/);
                if(reg.test(value)){
                    return true;
                }
                return false;
            },
            message: '税价总计需保留2位小数'
        },

    });

    $('#invoiceDate,#expressDate').datebox({
        editable:false
    })


    //计算税前金额 = 单价*数量
    $("#unitPrice,#numberSl").numberbox({
        "onChange":function(){
            var num = $("#numberSl").val();
            if(num == ''){
                $('#amountBeforeTax').val(0);
            }
            var price = $("#unitPrice").val();
            if(price == ''){
                $('#amountBeforeTax').val(0);
            }
            var amountBeforeTax= (price*num).toFixed(2);
            $("#amountBeforeTax").textbox("setValue", amountBeforeTax);

            var tax = $("#tax").val();
            if(tax == ''){
                return;
            }
            var amountTax = (tax*amountBeforeTax).toFixed(2);
            $('#amountTax').val(amountTax);

            $("#sumAmt").val((Number(amountTax)+Number(amountBeforeTax)).toFixed(2));

        }
    });

    //计算税额
    function computerAmountTax(){
        var tax = $("#tax").val();
        var num = $("#numberSl").val();
        var price = $("#unitPrice").val();
        var amountBeforeTax = (price*num).toFixed(2);
        $('#amountTax').val((tax*amountBeforeTax).toFixed(2));
        var amountTax = $("#amountTax").val();
        var amountBeforeTax = $("#amountBeforeTax").val();
        $("#sumAmt").val((Number(amountTax)+Number(amountBeforeTax)).toFixed(2));
    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="EntryInvoiceForm" method="post" enctype="multipart/form-data">
            <table>
                    <tr>
                        <th>开票公司:</th>
                        <td>
                            <input id="invoiceCompany"  name="invoiceCompany" class="easyui-textbox" data-options="editable:false" value="${agentName}" style="width:200px;">
                        </td>
                        <th>当前欠票:</th>
                        <td>
                            <input id="currentInvoice"  name="currentInvoice" class="easyui-textbox" data-options="editable:false" value="${ownInvoice}" style="width:200px;">
                        </td>
                    <tr>
                        <th>发票号:</th>
                        <td>
                            <input id="invoiceNumber" name="invoiceNumber" class="easyui-validatebox" data-options="required:true,validType:'invoiceNumber'" style="width:200px;">
                        </td>
                        <th>开票日期:</th>
                        <td>
                            <input id="invoiceDate" name="invoiceDate" class="easyui-datebox" data-options="required:true" style="width:200px;">
                        </td>
                    <tr>
                        <th>开票项目:</th>
                        <td>
                            <input id="invoiceItem" name="invoiceItem" class="easyui-validatebox" data-options="required:true" style="width:200px;">
                        </td>
                        <th>发票代码:</th>
                        <td>
                            <input id="invoiceCode" name="invoiceCode" class="easyui-validatebox"
                                   data-options="required:true,validType:'invoiceCode'" style="width:200px;">
                        </td>
                    <tr>
                        <th>数量:</th>
                        <td>
                            <input class="input easyui-numberbox" id="numberSl" name="numberSl" data-options="required:true"  precision="0" style="width:200px;" />
                        </td>
                        <th>单价:</th>
                        <td>
                            <input class="input easyui-numberbox" id="unitPrice" name="unitPrice" data-options="required:true"  precision="2" style="width:200px;"  />
                        </td>
                    <tr>
                        <th>税前金额:</th>
                        <td>
                            <input id="amountBeforeTax" name="amountBeforeTax" class="easyui-textbox" data-options="required:true,editable:false" style="width:200px;">
                        </td>
                        <th>税点:</th>
                        <td>
                            <input id="tax" name="tax" class="easyui-validatebox" data-options="required:true,validType:'tax'"  onchange="computerAmountTax()" style="width:200px;">
                        </td>
                    <tr>
                        <th>税额:</th>
                        <td>
                            <input id="amountTax" name="amountTax" class="easyui-validatebox" data-options="required:true,validType:'amountTax'" style="width:200px;">
                        </td>
                        <th>税价合计:</th>
                        <td>
                            <input id="sumAmt" name="sumAmt" class="easyui-validatebox" data-options="required:true,validType:'sumAmt'" style="width:200px;">
                        </td>
                    <tr>
                        <th>快递单号:</th>
                        <td>
                            <input id="expressNumber" name="expressNumber" class="easyui-validatebox" data-options="required:true" style="width:200px;">
                        </td>
                        <th>快递公司:</th>
                        <td>
                            <input id="expressCompany" name="expressCompany" class="easyui-validatebox" data-options="required:true" style="width:200px;">
                        </td>
                    <tr>
                        <th>发票上传:</th>
                        <td>
                            <input id="file" name="file" class="easyui-validatebox" type="file" data-options="required:true" style="width:200px;">
                        </td>
                        <th>寄出时间:</th>
                        <td>
                            <input id="expressDate" name="expressDate" class="easyui-datebox" data-options="required:true" style="width:200px;">
                        </td>
                    </tr>
            </table>
        </form>
    </div>
</div>