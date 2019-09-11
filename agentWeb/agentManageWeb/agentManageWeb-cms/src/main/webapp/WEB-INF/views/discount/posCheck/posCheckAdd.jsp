<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }

    //校验
    function queryAgentCheck(num) {
        var date ;
        if(num == 1){ // 根据AG码进行校验
            var agUniqNum = $("#agentId_posCheck").val();
            if(agUniqNum == '' || agUniqNum == undefined){
                alertMsg("代理商唯一码不能为空");
                return false;
            }
            date = {agentId:agUniqNum};
        }
        if(num == 2){  //根据代理商名称进行校验
            var agentName = $("#agentName_posCheck").val();
            if(agentName == '' || agentName == undefined){
                alertMsg("代理商名称不能为空");
                return false;
            }
            date = {agentName:agentName};
        }
        if(num == 3){ // 根据业务平台编码进行校验
            var busNum = $("#busNum_posCheck").val();
            if(busNum == '' || busNum == undefined){
                alertMsg("业务平台编码不能为空");
                return false;
            }
            date = {busNum:busNum};
        }
        if(num == 4){
            var agUniqNum = $("#agentId_posCheck").val();
            var busPlayform = $("#playformCode_posCheck").val();
            date = {busPlayform:busPlayform,agentId:agUniqNum};
        }
        $.ajax({
            url :"${path}/discount/queryAgentInfo",
            type:'POST',
            data:date,
            dataType:'json',
            success:function(data){
                if(data.resCode == '1'){
                    var jsonObj = data.obj;
                    $("#agentName_posCheck").val(jsonObj[0].AG_NAME);
                    $("#agentId_posCheck").val(jsonObj[0].ID);
                    if(num == 4){
                        $("#busNum_posCheck").val(jsonObj[0].BUS_NUM);
                    }

                    var str;
                    for(var k = 0;k<jsonObj.length;k++){
                        busNum = jsonObj[k].BUS_PLATFORM;
                        for(var i=0;i< db_options.ablePlatForm.length;i++){
                            if (db_options.ablePlatForm[i].platformNum == busNum) {
                                var temp = db_options.ablePlatForm[i].platformName;
                                str += '<option value="'+busNum+'">'+temp+'</option>';
                            }
                        }
                    }
                    $("#playformCode_posCheck").html(str); //设置业务平台
                    busNum = jsonObj[0].BUS_PLATFORM;
                    setCheckType(busNum); //添加分润比例类型
                }else{
                    alertMsg(data.resInfo);
                    $('#posCheckAddForm input').val('');
                    $('#posCheckAddForm select').val('');
                    $('#checkType_posCheck').html('');
                }
            },
            error:function(data){
                alertMsg("获取代理商失败，请联系管理员！");
            }
        });
    }

    //设置分润比例类型
    function setCheckType(busNum){
        var str1 = '<input type="radio" name="checkType" value="0" checked="checked">付款分润比例';
        var str2 = '<input type="radio" name="checkType" value="1">出款分润比例';
        var str3 = '<input type="radio" name="checkType" value="2">2019新商户付款分润比例';

        if("100003" == busNum) {  //大POS
            $("#checkType_posCheck").html(str1+str2+str3);
        }else if("100002" == busNum || "100013" == busNum){ //  智能POS 智慧POS
            $("#checkType_posCheck").html(str1+str2);
        }else { //新增平台
            $("#checkType_posCheck").html(str1);
        }
    }

    function selectCheckType(){
        var busNum = $("#playformCode_posCheck").val();
        queryAgentCheck(4);
        setCheckType(busNum);
    }

    $(function() {
        $('#posCheckAddForm').form({
            url : '${path}/discount/addCheck',
            onSubmit : function() {
                var isValid = $(this).form('validate');
                var busPlayform = $("#playformCode_posCheck").val();
                if(busPlayform == '' || busPlayform == undefined){
                    alertMsg("请进行信息校验！");
                    return false;
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
        editable:false,
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

    //验证分润比例格式
    $.extend($.fn.validatebox.defaults.rules, {
        profitScale: {
            validator: function(value){
                var reg1 = new RegExp(/^[0]\.[0-9]+$/); //0.06正则
                if(reg1.test(value)){
                    return true;
                }
                return false;
            },
            message: '分润比例格式为小数(例:0.06)'
        },
    });

    //复选框点击事件
    function checkboxOnclick(checkbox){
        if ( checkbox.checked == true){
            //设置日期框数值为空，不可用
            $('#checkDateS').datebox('setValue', '');
            $('#checkDateE').datebox('setValue', '');
            $('#checkDateS').datebox({disabled:true,required:false});
            $('#checkDateE').datebox({disabled:true,required:false});
        }else{
            //设置日期框可用
            $('#checkDateS').datebox({disabled:false,required:true});
            $('#checkDateE').datebox({disabled:false,required:true});
        }
    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;">
        <form id="posCheckAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>代理商编码：</td>
                    <td>
                        <input id="agentId_posCheck" name="agentId" class="easyui-validatebox" data-options="required:true" style="width:200px;" <c:if test="${isagent.isOK()}"> value="${isagent.data.id}" readonly="readonly" </c:if> />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentCheck(1);"> </a>
                    </td>
                </tr>
                <tr>
                    <td>代理商名称：</td>
                    <td>
                        <input id="agentName_posCheck" name="agentName" class="easyui-validatebox" data-options="required:true" style="width:200px;">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentCheck(2);"> </a>
                    </td>
                </tr>
                <tr>
                    <td>业务平台编码</td>
                    <td>
                        <input id="busNum_posCheck" name="busNum" class="easyui-validatebox" data-options="required:true" style="width:200px;">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="queryAgentCheck(3);"> </a>
                    </td>
                </tr>
                <tr></tr>
                <tr>
                    <td>业务平台</td>
                    <td>
                        <select id="playformCode_posCheck" name="playformCode"  class="easyui-validatebox" data-options="required:true" style="width:200px;" onchange="selectCheckType()">
                        <option>请选择</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>分润比例类型</td>
                    <td id="checkType_posCheck">
                        <input type="radio" name="checkType" value="0" checked="checked">付款分润比例
                        <input type="radio" name="checkType" value="1">出款分润比例
                        <input type="radio" name="checkType" value="2">2019新商户付款分润比例
                    </td>
                </tr>
                <tr>
                    <td>承诺交易金额(万)</td>
                    <td>
                        <input id="totalAmt_posCheck" name="totalAmt" class="easyui-numberbox" data-options="required:true" style="width:200px;">
                    </td>
                </tr>
                <tr>
                    <td>机具数量(台)</td>
                    <td>
                        <input id="posOrders_posCheck" name="posOrders" class="easyui-numberbox" data-options="required:true" style="width:200px;">
                    </td>
                </tr>
                <tr>
                    <td>分润比例</td>
                    <td>
                        <input id="profitScale_posCheck" name="profitScale"  class="easyui-validatebox" data-options="required:true,validType:'profitScale'" style="width:200px;">
                    </td>
                </tr>
                <tr>
                    <td>收取周期</td>
                    <td>
                        <input id="checkDateS" name="checkDateS" class="easyui-datebox" data-options="required:true" style="width:100px;" >
                        &nbsp;至&nbsp;
                        <input id="checkDateE" name="checkDateE" class="easyui-datebox" data-options="required:true" style="width:100px;" >
                        <input type="checkbox" name="checkboxDate" value="1" onclick="checkboxOnclick(this)">长期
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>