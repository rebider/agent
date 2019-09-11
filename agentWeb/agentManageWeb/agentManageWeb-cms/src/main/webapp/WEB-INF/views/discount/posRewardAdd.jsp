<%--suppress ALL --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agName = undefined;
    var map;
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }
    //校验代理商
    function queryAgentReward(temp) {
        var date ;
        var agUniqNum = $("#agentId").val();
        var agentName = $("#agentName").val();
        var busNum = $("#busNum").val();
        switch (temp) {
            case 1:{      // 根据AG码进行校验
                if(agUniqNum == '' || agUniqNum == undefined){
                    alertMsg("代理商唯一码不能为空");
                    return false;
                }
                date = {agentId:agUniqNum};
                break;
            }case 2:{
                if(agentName == '' || agentName == undefined){
                    alertMsg("代理商名称不能为空");
                    return false;
                }
                date = {agentName:agentName};
                break;
            }case 3:{
                if(busNum == '' || busNum == undefined){
                    alertMsg("业务平台编码不能为空");
                    return false;
                }
                date = {busNum:busNum};
                break;
            }
        }
        var temppp=temp;
        $.ajax({
            url :"${path}/discount/queryAgentInfo",
            type:'POST',
            data:date,
            dataType:'json',
            success:function(data){
                console.log('1');
                //先清空form表单
                $('#posRewardAddForm input[type!=radio]').val('');
                $('#posRewardAddForm select').val('');
                $('#platFormType').html('<option value="0">请选择</option>');
                $('#rewardType').html('<option value="0">请选择</option>');
                map={};
                if(data.resCode == '1'){
                    var jsonObj = data.obj;
                    $("#agentName").val(jsonObj[0].AG_NAME);
                    $("#agentId").val(jsonObj[0].ID);
                    var str='<option value="0">请选择</option>';
                    if(temppp==3){
                        $('#busNum').val(busNum);
                        str='';
                    }
                    for(var k = 0;k<jsonObj.length;k++){
                        busNum = jsonObj[k].BUS_PLATFORM;
                        for(var i=0;i< db_options.ablePlatForm.length;i++){
                            if (db_options.ablePlatForm[i].platformType=='POS'&& db_options.ablePlatForm[i].platformNum == busNum) {
                                map[busNum]=jsonObj[k].BUS_NUM;
                                var temp = db_options.ablePlatForm[i].platformName;
                                str += '<option value="'+busNum+'">'+temp+'</option>';
                            }
                        }
                    }
                    $("#platFormType").html(str); //设置业务平台
                    if ($('#platFormType').val()=='100003'){// 如果查出来的业务平台为大POS则设置奖励类型
                        var rewardTypeHtml='<option value="存量2奖励">存量2奖励</option>';
                        rewardTypeHtml+='<option value="2019新商户奖励">2019新商户奖励</option>';
                        $("#rewardType").html(rewardTypeHtml);
                        $('#containOtherPos').attr("checked",true);

                    }else if ($('#platFormType').val()!='0'){
                        var rewardTypeHtml='<option value="新品牌奖励">新品牌奖励</option>';
                        $("#rewardType").html(rewardTypeHtml);
                    }
                }else{
                    alertMsg(data.resInfo);
                    $('#posRewardAddForm input[type!=radio]').val('');
                    $('#posRewardAddForm select').val('');
                    $('#platFormType').html('<option value="0">请选择</option>');
                    $('#rewardType').html('<option value="0">请选择</option>');
                }
            },
            error:function(data){
                alertMsg("获取代理商失败，请联系管理员！");
            }
        });
    }

    function selectPlatFormType() {
        var platFormType=$('#platFormType').val();
        $('#busNum').val(map[platFormType]);
        if (platFormType=='100003'){
            var rewardTypeHtml='<option value="存量2奖励">存量2奖励</option>';
            rewardTypeHtml+='<option value="2019新商户奖励">2019新商户奖励</option>';
            $("#rewardType").html(rewardTypeHtml);

            $('#containOtherPos').attr("checked",true);
        }else if (platFormType!='0'){
            var rewardTypeHtml='<option value="新品牌奖励">新品牌奖励</option>';
            $("#rewardType").html(rewardTypeHtml);
        }
    }

    function no_assess(){
        if($(this).is(":checked")){
            $('[name="assess_month"]').each(function (index, domEle) {
                $(domEle).removeAttr("checked");
           });
        }
    }

    function do_checked(){
        if($(this).is(":checked")) {
            $("#no_assess").removeAttr("checked");
        }
    }

    $(function() {
        $('#posRewardAddForm').form({
            url : '${path }/discount/addReward',
            onSubmit : function() {

                var agentId=$("#agentId").val();
                var agentName=$("#agentName").val();
                var busNum=$("#busNum").val();
                if (agentId==''||agentName==''||busNum==''){
                    parent.$.messager.alert('错误', '请输入代理商相关信息', 'error');
                    return false;
                }
                var platFormType=$('#platFormType').val();
                if (platFormType=='0'){
                    parent.$.messager.alert('错误', '请选择业务平台', 'error');
                    return false;
                }
                var totalConsMonth=$('#totalConsMonth').datebox('getValue');
                var creditConsMonth=$('#creditConsMonth').datebox('getValue');
                if (totalConsMonth == '' || creditConsMonth == '') {
                    parent.$.messager.alert('错误', '请填写考核周期', 'error');
                    return false;
                }
                var rewardType=$('#rewardType').val();
                if (rewardType=='0'){
                    parent.$.messager.alert('错误', '请选择奖励类型', 'error');
                    return false;
                }
                var promiseAmt=$('#promiseAmt').numberbox('getValue');
                var machineryNum=$('#machineryNum').numberbox('getValue');
                var rewardScale=$('#rewardScale').numberbox('getValue');
                if (promiseAmt==''||machineryNum==''||rewardScale=='') {
                    parent.$.messager.alert('错误', '请填写考核与奖励标准', 'error');
                    return false;
                }
            },
            success : function(result) {
                console.log(result);
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });

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
                /*$("#assess_month").html('');*/
            }
        });
        $('#creditConsMonth').datebox({
            onSelect: function(date){
                var start_month=$('#totalConsMonth').datebox('getValue');
                start_month=start_month.substr(start_month.length-2,2);
                var end_month=$('#creditConsMonth').datebox('getValue')
                end_month=end_month.substr(end_month.length-2,2);

                if ((start_month=='01'&&end_month=='03') ||(start_month=='04'&&end_month=='06')||(start_month=='07'&&end_month=='09')||(start_month=='10'&&end_month=='12')){
                    $('#accountingCycle').html('<input type="radio" name="accountingCycle" value="0" id="byMonthProfit" checked/><label for="byMonthProfit">月度核算</label><input type="radio" name="accountingCycle" value="1" id="byQuarterProfit"/><label for="byQuarterProfit">季度核算</label>')
                }else{
                    $('#accountingCycle').html('<input type="radio" name="accountingCycle" value="0" id="byMonthProfit" checked/><label for="byMonthProfit">月度核算</label>')
                }
            }
        });
    });
    //限制预发周期开始月份为六个月内
    $('#totalConsMonth').datebox().datebox('calendar').calendar({
        validator : function(date){
            var now = new Date();
            var now_month=now.getMonth();
            var end_month=now_month+5>12?now_month-7:now_month+5;
            var end_year=end_month<now_month?now.getFullYear()+1:now.getFullYear();
            var d1 = new Date(now.getFullYear(),now_month,1);
            var d2 = new Date(end_year,end_month,1)-1;
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
    });

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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="填写申请信息" style="overflow: hidden;padding: 3px;">
        <form id="posRewardAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>代理商编码:</td>
                    <td>
                        <input id="agentId" name="agentId" <c:if test="${isagent.isOK()}">value="${isagent.data.id}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:200px;" />
                        <shiro:hasPermission name="/discount/addHuddleReward">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward(1);"></a>
                        </shiro:hasPermission>
                    </td>
                </tr>
                <tr>
                    <td>代理商名称:</td>
                    <td>
                        <input id="agentName" name="agentName" <c:if test="${isagent.isOK()}"> value="${isagent.data.agName}" </c:if> class="easyui-validatebox" data-options="required:true" style="width:200px;">
                        <shiro:hasPermission name="/discount/addHuddleReward">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward(2);"></a>
                        </shiro:hasPermission>
                    </td>
                </tr>
                <tr>
                    <td>业务平台编码:</td>
                    <td>
                        <input id="busNum" name="busNum" class="easyui-validatebox" data-options="required:true"  style="width:200px;">
                        <shiro:hasPermission name="/discount/addHuddleReward">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward(3);"></a>
                        </shiro:hasPermission>
                    </td>
                </tr>
                <tr>
                    <td>业务平台:</td>
                    <td>
                        <select id="platFormType" name="busPlatform" onchange="selectPlatFormType()" style="width:200px;height: 20px;">
                            <option value="0">请选择</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>考核周期:</td>
                    <td>
                        <input id="totalConsMonth" name="totalConsMonth" placeholder="起始月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                        &nbsp;至&nbsp;
                        <input id="creditConsMonth" name="creditConsMonth" placeholder="结束月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                    </td>
                </tr>
                <tr>
                    <td>奖励类型:</td>
                    <td>
                        <select name="rewardType" id="rewardType"style="width:200px;height: 20px;">
                            <option value="0">请选择</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>是否包含其他Pos平台：</td>
                    <td>
                        <input type="radio" name="iscontainotherpos" value="1" id="containOtherPos"/><label for="containOtherPos">是</label>
                        <input type="radio" name="iscontainotherpos" value="0" id="notContainOtherPos" checked/><label for="notContainOtherPos">否</label>
                    </td>
                </tr>
                <%--<tr>
                    <td>考核月份:</td>
                    <td id="assess_month"></td>
                </tr>--%>
                <tr>
                    <td>承诺交易金额（万）:</td>
                    <td><input id="promiseAmt" name="growAmt" class="easyui-numberbox" data-options="min:0,precision:2,required:true" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>机具数量（台）:</td>
                    <td><input id="machineryNum" name="machineryNum" class="easyui-numberbox" data-options="min:0,precision:2,required:true" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>奖励标准:</td>
                    <td><input id="rewardScale" name="rewardScale" class="easyui-numberbox" data-options="required:true,min:0,max:0.099,precision:3" style="width:200px;">%</td>
                </tr>
                <tr>
                    <td>考核方式:</td>
                    <td>
                        <input type="radio" name="assessWay" value="1" id="allMonthProfit"/><label for="allMonthProfit">累计交易量考核</label>
                        <input type="radio" name="assessWay" value="0" id="singleMonthProfit" checked/><label for="singleMonthProfit">单月交易量考核</label>
                    </td>
                </tr>
                <tr>
                    <td>奖励核算周期:</td>
                    <td id="accountingCycle">
                        <input type="radio" name="accountingCycle" value="0" id="byMonthProfit" checked/><label for="byMonthProfit">月度核算</label>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>