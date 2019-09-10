<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#stagingForm').form({
            url : '${path }/profit/staging/add',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.messager.alert('成功', result.msg, 'ok');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
        $('#stagAmt').val(($('#sumAmt').val()/2).toFixed(2)) ;
    });

    $('#stagCount').combobox({
        onSelect:function(newValue){
            var stagAmt = $('#sumAmt').val()/newValue.value;
             $('#stagAmt').val(stagAmt.toFixed(2)) ;
        }
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="stagingForm" method="post">
            <table class="grid">
                <tr>
                    <input name="stagType" type="text" hidden="true"  value="${stagType}">
                    <input name="sourceId" type="text" hidden="true" value="${profitDeduction.id}">
                    <td>退单金额</td>
                    <td><input id="sumAmt" name="sumAmt" type="text"  readonly="true" value="${profitDeduction.addDeductionAmt}"></td>
                </tr>
                <tr>
                    <td>期数</td>
                    <td >
                        <select id="stagCount" name="stagCount"  class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>每期金额</td>
                    <td><input id ="stagAmt" name="stagAmt" type="text" readonly value=""></td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="3"><textarea name="remark" width=100></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>