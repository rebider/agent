<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">

    //限制预发周期开始月份为六个月内



    //限制预发周期开始月份为六个月内
    $('#totalConsMonth').datebox({
        onSelect: function(date){
            var total_date=date;
            $('#creditConsMonth').datebox().datebox('calendar').calendar({
                validator : function(date){
                    var now = new Date();
                    var now_month=now.getMonth();
                    var end_month=now_month+5>12?now_month-7:now_month+5;
                    var end_year=end_month<now_month?now.getFullYear()+1:now.getFullYear();
                    var d1 = new Date(total_date.getFullYear(),total_date.getMonth()+1,1);
                    var d2 = new Date(end_year,end_month+1,1)-1;
                    return date>=d1&&date<=d2;
                }
            });
            $("#assess_month").html('');
        }
    });

    //拼接
    $('#creditConsMonth').datebox({
        onSelect: function(date){
            var start_month=$('#totalConsMonth').datebox('getValue');
            if(start_month!=''){
                var str='';
                var end_month=date.getFullYear()+'-'+((date.getMonth()+1)>=10?(date.getMonth()+1):'0'+(date.getMonth()+1));
                if(start_month==end_month){
                    str='<input type="checkbox" name="assess_month" value="'+start_month+'" checked/>'+start_month;
                }else{
                    var start_year=start_month.substring(0,4);
                    var end_year=end_month.substring(0,4);
                    var start_month_temp = parseInt(start_month.substring(5));
                    var end_month_temp = parseInt(end_month.substring(5));
                    if(end_year!=start_year){
                        for (var i=start_month_temp;i<=12;i++){
                            str+='<input type="checkbox" name="assess_month" value="'+start_year+'-'+(i>=10?i:'0'+i)+'"/>'+start_year+'-'+((i)>=10?i:'0'+i);
                        }
                        for (var i=1;i<=end_month_temp;i++){
                            str+='<input type="checkbox" name="assess_month" value="'+end_year+'-'+(i>=10?i:'0'+i)+'"/>'+end_year+'-'+((i)>=10?i:'0'+i);
                        }
                    }else{
                        for (var i=start_month_temp;i<=end_month_temp;i++){
                            str+='<input type="checkbox" name="assess_month" value="'+start_year+'-'+(i>=10?i:'0'+i)+'"/>'+start_year+'-'+((i)>=10?i:'0'+i);
                        }
                    }
                }

                str+='<input id="no_assess" type="checkbox" name="no_assess" value="no_assess"/>不考核';

                $("#assess_month").html(str);
                $("#no_assess").bind('click',no_assess);
                $('[name="assess_month"]').bind('click',do_checked);
            }
        }
    });
   /* $('#totalConsMonth').datebox().datebox('calendar').calendar({
        validator : function(date){
            var now = new Date();
            var now_month=now.getMonth();
            var end_month=now_month+5>12?now_month-7:now_month+5;
            var end_year=end_month<now_month?now.getFullYear()+1:now.getFullYear();
            var d1 = new Date(now.getFullYear(),now_month,1);
            var d2 = new Date(end_year,end_month+1,1)-1;
            return date>=d1&&date<=d2;
        }
    });
    //限制预发周期结束月份为六个月内
    $('#creditConsMonth').datebox().datebox('calendar').calendar({
        validator : function(date){
            var now = new Date();
            var now_month=now.getMonth();
            var end_month=now_month+5>12?now_month-7:now_month+5;
            var end_year=end_month<now_month?now.getFullYear()+1:now.getFullYear();
            var d1 = new Date(now.getFullYear(),now_month,1);
            var d2 = new Date(end_year,end_month+1,1)-1;
            return date>=d1&&date<=d2;
        }
    });*/

    /*$('#totalConsMonth').datebox().datebox('calendar').calendar({
        validator : function(date){
            var now = new Date();
            var d1 = new Date(now.getFullYear(),now.getMonth()-1,now.getDate());
            return d1 <= date;
        }
    });
    $('#creditConsMonth').datebox().datebox('calendar').calendar({
        validator : function(date){
            var now = new Date();
            var d1 = new Date(now.getFullYear(),now.getMonth()-1,now.getDate());
            return d1 <= date;
        }
    });*/

    $("#totalConsMonth, #creditConsMonth").datebox({
        required : true,
        formatter: function(date){
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            return y + "-" + (m<10?('0'+m):m);
        },
        parser: function(s){
            var t = Date.parse(s);
            if (!isNaN(t)){
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });

    /*$("#totalEndMonth").datebox({
        required : true,
        formatter: function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y + "-" + (m<10?('0'+m):m);
        },
        parser: function(s){
            var t = Date.parse(s);
            if (!isNaN(t)){
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });*/
</script>
<div class="easyui-panel" title="POS奖励考核申请修改" data-options="iconCls:'fi-results'">
    <form id="posRewardEditFrom" method="post">
        <table class="grid">
            <input type="hidden" name="id" value="${posReward.id}">
            <tr>
                <td>代理商唯一码：</td>
                <td>${posReward.agentId}</td>
            </tr>
            <tr>
                <td>代理商名称：</td>>
                <td>${posReward.agentName}</td
            </tr>
            <tr>
                <td>预发周期（起始&结束）：</td>
                <td>
                    <input id="totalConsMonth" name="totalConsMonth" <%--value="${posReward.totalConsMonth}"--%> placeholder="起始月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                    &nbsp;至&nbsp;
                    <input id="creditConsMonth" name="creditConsMonth" <%--value="${posReward.creditConsMonth}"--%> placeholder="结束月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                </td>
            </tr>
            <%--<tr>
                <td>考核月份</td>
                <td><input id="totalEndMonth" name="totalEndMonth" value="${posReward.totalEndMonth}" tyle="width:300px;" placeholder="请选择考核月份" class="easyui-validatebox" data-options="required:true"></td>
            </tr>--%>
            <tr>
                <td>考核月份：</td>
               <%-- <td>${posReward.totalEndMonth}</td--%>
                <td id="assess_month"></td>
            </tr>
            <tr>
                <td>机具数量：</td>
                <td><input id="machineryNum" name="machineryNum" class="easyui-validatebox" data-options="required:true" style="width:200px;"></td>
            </tr>
            <tr>
                <td>承诺交易金额（万）：</td>
                <td><input name="growAmt" value="${posReward.growAmt}" data-options="required:true"></td>
            </tr>
            <tr>
                <td>奖励标准：</td>
                <td><input name="rewardScale" value="${posReward.rewardScale}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" data-options="required:true,min:0,precision:4"></td>
            </tr>
        </table>
    </form>
</div>
