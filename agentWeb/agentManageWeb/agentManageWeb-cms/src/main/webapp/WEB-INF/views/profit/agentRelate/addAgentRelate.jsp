<%--suppress ALL --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var add_index=1;
    var minus_index=0;

    $('#executeMonth').datebox({
        required:true
    });
    $("#executeMonth").datebox({
        required:true,
        formatter:function(data){
            var date_temp=new Date(data);
            return date_temp.getFullYear()+''+(date_temp.getMonth()+1>=10?date_temp.getMonth()+1:('0'+''+(date_temp.getMonth()+1)));
        },
        parser:function(data) {
            if(data.indexOf('-')<0){
                data=data.substring(0,4)+'-'+data.substring(4,data.length);
            }
            var t = Date.parse(data);
            if (!isNaN(t)) {
                return new Date(t);
            } else {
                return new Date();
            }
        }
    });
    $('#executeMonth').datebox().datebox('calendar').calendar({
        validator : function(date){
            var temp = new Date();
            temp.setDate(1);
            temp.setMonth(temp.getMonth()-1,0);
            return date>=temp;
        }
    });
    function addAgentForRelate() {
        $('#table-relate'+add_index).show();
        if(add_index!=4)
            add_index++;
        if (minus_index!=4){
            minus_index++;
        }
    }
    function delAgentForRelate() {
        $('#agentName'+minus_index).val('');
        $('#agentId'+minus_index).val('');
        $('#type-0'+minus_index).val('');
        $('#table-relate'+minus_index).hide();
        if (add_index!=1)
            add_index--;
        if(minus_index!=0)
            minus_index--;
    }
    function agentBusPlatformChange(){
        $('#table-relate1 input').val('');
        $('#table-relate2 input').val('');
        $('#table-relate3 input').val('');
        $('#table-relate4 input').val('');
        $('#executeMonth').val('');
        $('#remark').val('');
    }
    function clearPrimaryAgent(){
        add_index=1;
        minus_index=0;
        $('#addAgentRelateForm input').val('');
        $('#table-relate1').hide();
        $('#table-relate2').hide();
        $('#table-relate3').hide();
        $('#table-relate4').hide();
        $('#agentName-span').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
        $('#agentId-span').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
        $('#agentType-span').html('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
    }
    function searchPrimaryAgent(){
        var primaryAgentId=$('#primaryAgentId').val();
        if(primaryAgentId==''){
            parent.$.messager.alert('提示', '请填写要查询的代理商唯一码。', 'error');
            return ;
        }
        clearPrimaryAgent();
        $.ajax({
            url:'${path }/agentRelate/searchPrimaryAgent',
            type:'post',
            data:{"agentId":primaryAgentId},
            datatype:'json',
            success:function(data){
                var result=JSON.parse(data);
                if(result.isSuccess){
                    $('#primaryAgentName').val(result.agentName);
                    $('#primaryAgentId').val(result.agentId);

                    $('#agentId-span').html(result.agentId);
                    $('#agentName-span').html(result.agentName);
                    if(result.msg){
                        parent.$.messager.alert('提示', result.msg, 'error');
                        return ;
                    }
                    var busPlatform=result.busPlatform;
                    var str='<select id="primaryAgentBusPlatform" onchange="agentBusPlatformChange()" class="easyui-combobox" name="agentBusPlatform" style="width:140px; height: 24px;font-size: 14px">' +
                        '<option value="0">请选择</option> ';
                    var brushTemp=false;
                    if(db_options.ablePlatForm)
                        for (var j=0;j<busPlatform.length;j++) {
                            for(var i=0;i< db_options.ablePlatForm.length;i++){
                                if (db_options.ablePlatForm[i].platformNum == busPlatform[j]) {
                                    var temp = db_options.ablePlatForm[i].platformName;
                                    /*if (temp.indexOf('手刷') >= 0) {
                                        temp = '手刷';
                                        if (!brushTemp) {
                                            str += '<option value="00">' + temp + '</option>';
                                            $('#primaryAgentType').val(busPlatform[j]);
                                            console.log($('#primaryAgentType').val());
                                            brushTemp = true;
                                        }
                                    } else {*/
                                    str += '<option value="' + busPlatform[j] + '">' + temp + '</option>';
                                    /*}*/
                                }
                            }
                        }

                    str+='</select>';
                    $('#agentType-span').html(str);
                    addAgentForRelate();
                }else{
                    parent.$.messager.alert('提示', '未找到相应代理商信息。', 'error');
                }
            }
        });
    }
    function searchRelateAgent(index){
        //清楚相关元素值
        $('#agentName'+index).val('');
        $('#type-0'+index).val('');
        var agentId=$('#agentId'+index).val();
        var primaryAgentId=$('#primaryAgentId').val();
        if(agentId==''){
            parent.$.messager.alert('提示', '请填写要查询的代理商唯一码。', 'error');
            return ;
        }
        if(agentId==primaryAgentId){
            $('#agentId'+index).val('');
            parent.$.messager.alert('提示', '与主代理商重复，请重新选择代理商。', 'error');
            return ;
        }
        //取得主代理商所选平台类型
        var primaryAgentType=$('#primaryAgentBusPlatform').val();
        if(primaryAgentType=='0'){
            parent.$.messager.alert('提示', '请选择主代理商的平台类型', 'error');
            return ;
        }
        switch (index) {
            case 2:{
                var agentId_1=$('#agentId1').val();
                if(agentId==agentId_1){
                    $('#agentId'+index).val('');
                    parent.$.messager.alert('提示', '与已填代理商重复，请重新选择代理商。', 'error');
                    return ;
                }
                break;
            }case 3:{
                var agentId_1=$('#agentId1').val();
                var agentId_2=$('#agentId2').val();
                if(agentId==agentId_1||agentId==agentId_2){
                    $('#agentId'+index).val('');
                    parent.$.messager.alert('提示', '与已填代理商重复，请重新选择代理商。', 'error');
                    return ;
                }
                break;
            }case 4:{
                var agentId_1=$('#agentId1').val();
                var agentId_2=$('#agentId2').val();
                var agentId_3=$('#agentId3').val();
                if(agentId==agentId_1||agentId==agentId_2||agentId==agentId_3){
                    $('#agentId'+index).val('');
                    parent.$.messager.alert('提示', '与已填代理商重复，请重新选择代理商。', 'error');
                    return ;
                }
            }
        }
        $.ajax({
            url:'${path }/agentRelate/searchPrimaryAgent',
            type:'post',
            data:{"agentId":agentId},
            datatype:'json',
            success:function(data){
                var result=JSON.parse(data);
                if(result.isSuccess){
                    if(result.msg){
                        parent.$.messager.alert('提示', result.msg, 'error');
                        return ;
                    }
                    $('#agentName'+index).val(result.agentName);
                    var busPlatform=result.busPlatform;
                    /*if(primaryAgentType=='00'){
                        if(db_options.ablePlatForm)
                            for (var j=0;j<busPlatform.length;j++) {
                                for(var i=0;i< db_options.ablePlatForm.length;i++){
                                    if (db_options.ablePlatForm[i].platformNum == busPlatform[j]) {
                                        var temp = db_options.ablePlatForm[i].platformName;
                                        if (temp.indexOf('手刷') >= 0) {
                                           $('#type-0'+index).val('手刷');
                                        }
                                    }
                                }
                            }
                    }else{*/
                    for (var j=0;j<busPlatform.length;j++) {
                        if(primaryAgentType==busPlatform[j]){
                            $('#type-0'+index).val($('#primaryAgentBusPlatform option:selected').text());
                        }
                    }
                    /*}*/
                    //查询完成的关联代理商平台类型
                    var type_temp=$('#type-0'+index).val();
                    if(type_temp==''){
                        $('#agentId'+index).val('');
                        $('#agentName'+index).val('');
                        parent.$.messager.alert('提示', '此代理商与主代理商无相同业务类型,不能关联。', 'error');
                        return ;
                    }
                }else{
                    $('#agentId'+index).val('');
                    parent.$.messager.alert('提示', '未查询到代理商信息，请重新输入。', 'error');
                }
            }
        });

    }
    function submitAddAgentRalate(){
        /*判断已设定关联代理商*/
        var type01=$('#type-01').val()
        var agentId1=$('#agentId1').val()
        if(agentId1==''){
            parent.$.messager.alert('提示', '请选择关联代理商。', 'error');
            return ;
        }
        if (type01==''){
            parent.$.messager.alert('提示', '请选择关联代理商。', 'error');
            return ;
        }
        var primaryAgentType=$('#primaryAgentBusPlatform').val();
        if(primaryAgentType=='00'){
            var str_temp=$('#primaryAgentType').val();
            $('#primaryAgentBusPlatform option:selected').val(str_temp);
        }
        var executeMonth=$('#executeMonth').datebox('getValue');
        if(executeMonth==''){
            parent.$.messager.alert('提示', '请填写开始执行时间。', 'error');
            return;
        }
        var data_temp=$.serializeObject($('#addAgentRelateForm'));
        var data_str=JSON.stringify(data_temp);
        $.ajax({
            url:'${path }/agentRelate/addAgentRalate',
            type:'post',
            data:{"data":data_str},
            datatype:'json',
            success:function(data){
                var result=JSON.parse(data);
                if(result.isSuccess){
                    parent.$.messager.alert('提示', '提交成功，进入审批流程。', 'success');
                    $('#index_tabs').tabs('close',"添加关联代理商");
                    agentRelateList.datagrid('reload');
                }else{
                    parent.$.messager.alert('提示', result.msg, 'error');
                }
            }
        });
    }
</script>
<div style="margin-left: 30px;margin-top: 10px;margin-right: 30px">
    <form id="addAgentRelateForm" method="post">
        <table>
            <tr>
                <th style="font-size: 14px">代理商名称:</th>
                <td>
                    <input id="primaryAgentName" name="agentName" readonly type="text" class="easyui-validatebox"style="width: 200px; line-height:17px;border:1px solid #ccc"/>
                </td>
                <th style="font-size: 14px">代理商唯一码:</th>
                <td>
                    <input id="primaryAgentId" name="agentId" maxlength="25" type="text" class="easyui-validatebox" style="width: 200px; line-height:17px;border:1px solid #ccc"/>
                </td>
                <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="clearPrimaryAgent()">清空</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchPrimaryAgent()">查询</a>
                </td>
            </tr>
        </table>
        <div style="font-size: 17px;font-style: initial">
            <br/><strong>代理商基本信息</strong>
            <hr>
        </div>
        <table>
            <tr>
                <td>
                    <strong style="font-size: 16px">代理商名称:</strong>&nbsp;&nbsp;
                    <span id="agentName-span" style="font-size: 14px">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </span>
                </td>
                <td>
                    <strong style="font-size: 16px">代理商唯一码:</strong>&nbsp;&nbsp;
                    <span id="agentId-span" style="font-size: 14px">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </span>
                </td>
                <td>
                    <input type="hidden" id="primaryAgentType">
                    <strong style="font-size: 16px">业务平台:</strong>&nbsp;&nbsp;
                    <span id="agentType-span" style="font-size: 14px;display: inline-block">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    </span>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <br><br><br>
                    <span style="font-size:13px;color: red">提示：关联的子公司顺序为扣款的依次顺序</span>
                </td>
            </tr>
            <tr>
                <td>
                    <a onclick="addAgentForRelate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">关联代理商</a>
                    <a onclick="delAgentForRelate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-red'">删除代理商</a>
                </td>
            </tr>
        </table>
        <div id="table-relate1" style="display: none">
            <hr>
            <table>
                <tr>
                    <td>代理商名称 <input id="agentName1" name="agentName1" readonly maxlength="30" type="text" class="easyui-validatebox" style="line-height:17px;border:1px solid #ccc"/></td>
                    <td>代理商唯一码 <input id="agentId1" name="agentId1" maxlength="30" type="text" class="easyui-validatebox" style="line-height:17px;border:1px solid #ccc"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRelateAgent(1)">代理商检索</a>
                    </td>
                    <td>业务平台</td>
                    <td><input id="type-01" type="text" value=""readonly></td>
                </tr>
                <tr>
                    <td><br><br><br></td>
                </tr>
            </table>
        </div>
        <div id="table-relate2" style="display: none">
            <hr>
            <table>
                <tr>
                    <td>代理商名称 <input id="agentName2" name="agentName2" readonly maxlength="30" type="text" class="easyui-validatebox" style="line-height:17px;border:1px solid #ccc"/></td>
                    <td>代理商唯一码 <input id="agentId2" name="agentId2" maxlength="30" type="text" class="easyui-validatebox" style="line-height:17px;border:1px solid #ccc"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRelateAgent(2)">代理商检索</a>
                    </td>
                    <td>业务平台</td>
                    <td><input id="type-02" type="text" value=""readonly></td>
                </tr>
                <tr>
                    <td><br><br><br></td>
                </tr>
            </table>
        </div>
        <div id="table-relate3" style="display: none">
            <hr>
            <table>
                <tr>
                    <td>代理商名称 <input id="agentName3" name="agentName3" readonly maxlength="30" type="text" class="easyui-validatebox" style="line-height:17px;border:1px solid #ccc"/></td>
                    <td>代理商唯一码 <input id="agentId3" name="agentId3" maxlength="30" type="text" class="easyui-validatebox" style="line-height:17px;border:1px solid #ccc"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRelateAgent(3)">代理商检索</a>
                    </td>
                    <td>业务平台</td>
                    <td><input id="type-03" type="text" value=""readonly></td>
                </tr>
                <tr>
                    <td><br><br><br></td>
                </tr>
            </table>
        </div>
        <div id="table-relate4" style="display: none">
            <hr>
            <table>
                <tr>
                    <td>代理商名称 <input id="agentName4" name="agentName4" readonly maxlength="30" type="text" class="easyui-validatebox" style="line-height:17px;border:1px solid #ccc"/></td>
                    <td>代理商唯一码 <input id="agentId4" name="agentId4" maxlength="30" type="text" class="easyui-validatebox" style="line-height:17px;border:1px solid #ccc"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRelateAgent(4)">代理商检索</a>
                    </td>
                    <td>业务平台</td>
                    <td><input id="type-04" type="text" value=""readonly></td>
                </tr>
                <tr>
                    <td><br><br><br></td>
                </tr>
            </table>
        </div>
        <hr>
        <table>
            <tr>
                <td>开始执行时间（分润归属月份）</td>
                <td><input id="executeMonth" name="executeMonth"/></td>
            </tr>
            <tr>
                <td>备注：</td>
                <td>
                    <textarea id="remark" name="remark"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitAddAgentRalate()">提交</a>
                </td>
            </tr>
        </table>
    </form>
</div>
