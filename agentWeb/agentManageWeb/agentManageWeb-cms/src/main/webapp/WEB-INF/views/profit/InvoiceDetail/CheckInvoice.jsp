<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<style type="text/css">
    .backInfo {
        display:none;
    }
</style>

<script type="text/javascript">


    //提交
    $(function() {
        $('#resultForm').form({
            url : '${path }/profit/invoiceDetail/checkResult',
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success == '1') {
                    parent.$.messager.alert('成功', result.obj, 'ok');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                }else {
                    parent.$.messager.alert('提示', result.resInfo, 'error');
                }
            }
        });
    });

    //单选框被选中事件
    $("#shResult1").click(function(){
        $(".backInfo").css("display","none");
    });
    $("#shResult2").click(function(){
        $(".backInfo").css("display","block");
    });

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <form id="resultForm" method="post">
            <input type="hidden" name="id" value="${id}">
            <table>
                <tr>
                    <th>核票结果：</th>
                    <td>
                        <label><input id="shResult1" name="shResult" type="radio" value="1" />通过</label>
                        <label><input id="shResult2"  name="shResult" type="radio" value="2" />退回</label>
                    </td>
                </tr>
            </table>
            <table class="backInfo">
                <tr>
                    <th>退回原因：</th>
                    <td>
                        <input  name="returnReason" data-options="multiline:true" style="width:200px;">
                    </td>
                </tr>
                <tr>
                    <th>快递单号：</th>
                    <td>
                        <input name="returnExpressNumber" style="width:200px;">
                    </td>
                </tr>
                <tr>
                    <th>快递公司：</th>
                    <td>
                        <input name="returnExpressCompany" style="width:200px;" >
                    </td>
                </tr>
                <tr>
                    <th>寄出时间：</th>
                    <td>
                        <input class= "easyui-datebox" name="returnDate" data-options="editable:false"   style="width:200px;">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>