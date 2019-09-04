<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $('#checkDateS').datebox().datebox('calendar').calendar({
        validator : function(date){
            var now = new Date();
            var d1 = new Date(now.getFullYear(),now.getMonth(),now.getDate());
            return d1 <= date;
        }
    });
    $('#checkDateE').datebox().datebox('calendar').calendar({
        validator : function(date){
            var now = new Date();
            var d1 = new Date(now.getFullYear(),now.getMonth(),now.getDate());
            return d1 <= date;
        }
    });
    $("#checkDateS,#checkDateE").datebox({
        required:true,
        formatter: function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+"-"+(m<10?('0'+m):m);
        },
        parser: function(s){
            var t = Date.parse(s);
            if (!isNaN(t)){
                return new Date(t);
            } else {
                return new Date();
            }
        }
    })
</script>
<div class="easyui-panel" title="分润比例考核申请修改" data-options="iconCls:'fi-results'">
    <form id="posCheckEditFrom" method="post">
        <table class="grid">
            <input type="hidden" name="id" value="${posCheck.id}">
            <tr>
                <td>代理商唯一码：</td>
                <td>${posCheck.agentId}</td>
            </tr>
            <tr>
                <td>代理商名称：</td>
                <td>${posCheck.agentName}</td>
            </tr>
            <tr>
                <td>考核日期（起始&截止）：</td>
                <td>
                    <input id="checkDateS" name="checkDateS" value="${posCheck.checkDateS}" placeholder="起始日期" class="easyui-datebox" data-options="required:true" style="width:200px;">
                    &nbsp;至&nbsp;
                    <input id="checkDateE" name="checkDateE" value="${posCheck.checkDateE}" placeholder="截止日期" class="easyui-datebox" data-options="required:true" style="width:200px;">
                </td>
            </tr>
            <tr>
                <td>交易总额（万）：</td>
                <td><input name="totalAmt" value="${posCheck.totalAmt}" data-options="required:true"></td>
            </tr>
            <tr>
                <td>机具订货量：</td>
                <td><input name="posOrders" value="${posCheck.posOrders}" data-options="required:true"></td>
            </tr>
            <tr>
                <td>分润比例：</td>
                <td><input name="profitScale" value="${posCheck.profitScale}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" data-options="required:true,min:0,precision:4"></td>
            </tr>
        </table>
    </form>
</div>