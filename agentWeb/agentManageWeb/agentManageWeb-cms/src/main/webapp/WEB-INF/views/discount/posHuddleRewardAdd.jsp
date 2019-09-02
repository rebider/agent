<%--suppress ALL --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agName = undefined;
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    //校验代理商
    function queryAgentReward(num) {

        //校验代理商----名称

        var agUniqNum = $("#"+"agentId"+num).val();

        if(agUniqNum=='' || agUniqNum==undefined){
            alertMsg("代理商编码不能为空");
            return false;
        }
        $.ajax({
            url :"${path}/business/queryAgentName",
            type:'POST',
            data:{
                id:agUniqNum
            },
            dataType:'json',
            success:function(data){
                if(data.success){
                    var jsonObj = JSON.parse(data.msg);
                    $("#"+"agentName"+num).val(jsonObj.agName);
                    $('input[name="agentId"+'+num+']').val(jsonObj.id);
                }else{
                    alertMsg(data.msg);
                    $('input[name="agentId"+'+num+']').val("");
                    $("#agentAname").remove();
                }
            },
            error:function(data){
                alertMsg("获取代理商失败，请联系管理员！");
            }
        });
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
            url : '${path }/discount/addReward?type=huddle',
            onSubmit : function() {
            },
            success : function(result) {
                progressClose();
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
                $("#assess_month").html('');
            }
        });
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
    function showHide(num) {
        var agentName =   $('#'+'agentName'+num).val();
        var sum = 1+ parseInt(num);
        if(sum!="20"){
            if(agentName&&agentName!=''){
                $('#showHide'+sum).show();
            }else{
                $.messager.alert("提示","请完善代理商信息后点添加！")
            }
        }else{
            $.messager.alert("提示","最多添加20个代理商！")
        }


    }

    function  hideHide(num){
        $('#'+'agentName'+num).val('');
        $('#'+'agentId'+num).val('');
        $('#showHide'+num).hide();
    }


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="填写申请信息" style=" overflow:scroll;padding: 3px;width:850px;height:350px;">
        <form id="posRewardAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId0" name="agentId0" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('0');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName0" name="agentName0" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                    </td>
                </tr>

                <tr>
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId1" name="agentId1" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('1');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName1" name="agentName1" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('1');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>

                    </td>
                </tr>

                <tr id = "showHide2" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId2" name="agentId2" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('2');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName2" name="agentName2" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('2');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('2');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>


                <tr id = "showHide3" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId3" name="agentId3" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('3');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName3" name="agentName3" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('3');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('3');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide4" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId4" name="agentId4" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('4');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName4" name="agentName4" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('4');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('4');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>


                <tr id = "showHide5" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId5" name="agentId5" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('5');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName5" name="agentName5" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('5');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('5');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide6" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId6" name="agentId6" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('6');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName6" name="agentName6" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('6');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('6');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>


                <tr id = "showHide7" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId7" name="agentId7" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('7');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName7" name="agentName7" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('7');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('7');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide8" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId8" name="agentId8" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('8');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName8" name="agentName8" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('8');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('8');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>


                <tr id = "showHide9" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId9" name="agentId9" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('9');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName9" name="agentName9" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('9');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('9');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>


                <tr id = "showHide10" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId10" name="agentId10" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('10');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName10" name="agentName10" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('10');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('10');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>


                <tr id = "showHide11" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId11" name="agentId11" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('11');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName11" name="agentName11" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('11');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('11');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>



                <tr id = "showHide12" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId12" name="agentId12" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('12');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName12" name="agentName12" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('12');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('12');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>


                <tr id = "showHide13" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId13" name="agentId13" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('13');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName13" name="agentName13" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('13');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('13');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide14" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId14" name="agentId14" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('14');">校验</a>

                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName14" name="agentName14" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('14');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('14');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide15" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId15" name="agentId15" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('15');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName15" name="agentName15" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('15');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('15');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide16" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId16" name="agentId16" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('16');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName16" name="agentName16" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('16');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('16');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide17" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId17" name="agentId17" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('17');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName17" name="agentName17" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('17');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('17');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide18" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId18" name="agentId18" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('18');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName18" name="agentName18" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('18');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('18');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr id = "showHide19" style="display: none">
                    <td>代理商唯一码：</td>
                    <td>
                        <input id="agentId19" name="agentId19" maxlength="30" type="text" placeholder="请输入代理商编码" <c:if test="${isagent.isOK()}">value="${isagent.data.agUniqNum}"</c:if> class="easyui-validatebox" data-options="required:true" style="width:170px;" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentReward('19');">校验</a>
                    </td>

                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName19" name="agentName19" value="" <c:if test="${isagent.isOK()}">value="${isagent.data.agName}"</c:if> class="easyui-validatebox" data-options="required:true" readonly="readonly" style="width:170px;">
                        <a onclick="showHide('19');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="hideHide('19');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>

                <tr>
                    <td>预发周期（起始&结束）：</td>
                    <td colspan="3">
                        <input id="totalConsMonth" name="totalConsMonth" placeholder="起始月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                        &nbsp;至&nbsp;
                        <input id="creditConsMonth" name="creditConsMonth" placeholder="结束月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                    </td>
                </tr>
                <tr>
                    <td>考核月份：</td>
                    <td id="assess_month" colspan="3"></td>
                </tr>
                <tr>
                    <td>机具数量：</td>
                    <td colspan="3" ><input id="machineryNum" name="machineryNum" class="easyui-validatebox" data-options="required:true" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>承诺交易金额（万）：</td>
                    <td colspan="3"><input name="growAmt" class="easyui-validatebox" data-options="required:true" style="width:200px;"></td>
                </tr>
                <tr>
                    <td>奖励标准：</td>
                    <td colspan="3"><input name="rewardScale" class="easyui-validatebox" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" data-options="required:true,min:0,precision:5" style="width:200px;"></td>
                </tr>
            </table>
        </form>
    </div>
</div>