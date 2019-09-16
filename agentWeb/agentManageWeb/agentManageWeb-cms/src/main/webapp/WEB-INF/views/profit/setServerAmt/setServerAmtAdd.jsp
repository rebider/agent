<%--suppress ALL --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var number = 0;

    var time = new Date();
    var year_month;
    if(time.getMonth()==0){
        year_month = (time.getFullYear()-1)+'-12'+'-01';
    }else{
        year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
    }

    $("input[sgin='sgin']").datebox({
        required:true
    });

    $("input[sgin='sgin']").datebox({
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

    $("input[sgin='sgin']").datebox('setValue',year_month);


    var start = $("input[sgin='sgin']").datebox('getValue');
    var end = $("input[sgin='sgin']").datebox('getValue');




    $(function() {
        $('#setServerAmtAddForm').form({
            url : '${path }/SetServerAmtController/getDate',
           /* SetServerAmtController*/
            onSubmit : function() {
                var initTable = $('#init_table');
                $("input[name='coun']").remove();
                var hide_B = $("<input type='text' style='display: none'></th>")
                hide_B.attr("value",number);
                hide_B.attr("name","coun");
                initTable.append(hide_B);
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

    });


    $('#bum_Id').combobox({
        onChange: function (newValue, oldValue) {
            var bumId = $("#bum_Id").combobox("getValue");
            console.info(bumId);
            $.ajax({
                type: "post",
                url: "${path}/SetServerAmtController/queryD",
                data: {bumId: bumId},
                dataType: "json",
                success: function (data) {
                    var addtd1 = $('#addtd1');
                    var addtr1 = $('#addtr1');
                    $('.new_A').remove();
                    for (var i = 0; i < data.length; i++) {
                        var hide_A = $("<input type='text' class='new_A' style='display: none'></th>")
                        hide_A.attr("value",data.length);
                        hide_A.attr("name","size");
                        if(i == 0){
                            var agentName = $("<th class='new_A'>代理商名称：<input type='checkbox'  checked='checked' ></th>")
                            var agentId = $("<input type='text' class='new_A' style='display: none'</td>");
                            agentId.attr('value',data[i].AGENT_ID);
                            agentId.attr('name','agentId'+i);
                            var inputA = $("<td class='new_A'><input type='text'  readonly='readonly' style='line-height:17px;border:1px solid #ccc'/></td>");
                            var bumCode = $("<th class='new_A'>业务平台编码：</th>")
                            var inputB = $("<td class='new_A'><input type='text' readonly='readonly' style='line-height:17px;border:1px solid #ccc'/></td>");
                            agentName.children().attr('value',i);
                            agentName.children().attr('name','isno'+i);
                            inputA.children().attr('value', data[i].AGENT_NAME);
                            inputA.children().attr('name','inputA'+i );
                            inputB.children().attr('value', data[i].BUM_CODE);
                            inputB.children().attr('name', 'inputB'+i);

                            addtd1.after(inputB);
                            addtd1.after(hide_A);
                            addtd1.after(bumCode);
                            addtd1.after(inputA);
                            addtd1.after(agentName);
                            addtd1.after(agentId);
                        }else{
                            var tr1 = $("<tr class='new_A'></tr>");
                            var td1=$("<td></td>");
                            var td2=$("<td></td>");
                            var agentName = $("<th >代理商名称：<input type='checkbox' checked='checked' ></th>")
                            var agentId = $("<input type='text'  class='new_A' style='display: none'/></td>");
                            agentId.attr('value',data[i].AGENT_ID);
                            agentId.attr('name','agentId'+i);
                            var inputA = $("<td><input type='text' readonly='readonly' style='line-height:17px;border:1px solid #ccc'/></td>");
                            var bumCode = $("<th>业务平台编码：</th>")
                            var inputB = $("<td><input type='text' readonly='readonly' style='line-height:17px;border:1px solid #ccc'/></td>");
                            agentName.children().attr('value',i);
                            agentName.children().attr('name','isno'+i);
                            inputA.children().attr('value', data[i].AGENT_NAME);
                            inputA.children().attr('name','inputA'+i );
                            inputB.children().attr('value', data[i].BUM_CODE);
                            inputB.children().attr('name', 'inputB'+i);
                            tr1.append(td1);
                            tr1.append(td2);
                            tr1.append(agentName);
                            tr1.append(inputA);
                            tr1.append(bumCode);
                            tr1.append(inputB);
                            tr1.append(agentId);
                            addtr1.after(tr1);
                        }

                    }
                }
            });
        }
    });


   function checkboxOnclick(checkbox){

       if ( checkbox.checked == true){
           var numb=checkbox.value
           $("#F_MONTH"+numb).datebox("setValue",'');
           $("#E_MONTH"+numb).datebox("setValue",'');
       }

   }
    function subDiv(){
        if(number!=0){
            $('#table'+number).remove();
            $('.new_A'+number).remove();
            number--;
       }
    }

    function addDiv(){

        number++;
        var initTable = $('#init_table');
        var table = $("<table class='grid'></table>");
        table.attr("id","table"+number);
        var tr1 = $("<tr></tr>");
        var serverName = $("<th>费用名称：</th>");
        var  serverNameInput =$('<td><select  class="easyui-combobox"  style="line-height:17px;width:200px;border:1px solid #ccc" ><option value="00">服务费</option></select></td>');
        var CHARGE_TYPE = $("<th>收费类型：</th>");
        var  CHARGE_TYPEInput = $('<td><select  class="easyui-combobox" style="line-height:17px;width:200px;border:1px solid #ccc"  > <option value="00">借记S0</option><option value="01">借记D0</option> <option value="02">贷记S0</option> <option value="03">贷记D0</option> </select> </td>')
        serverNameInput.children().attr("name","SERVER_TYPE"+number);
        CHARGE_TYPEInput.children().attr("name","CHARGE_TYPE"+number);
        tr1.append(serverName);
        tr1.append(serverNameInput);
        tr1.append(CHARGE_TYPE);
        tr1.append(CHARGE_TYPEInput);
        var tr2 = $("<tr></tr>");
        var CHARGE_BASE = $("<th>收费基数：</th>");
        var CHARGE_BASEInput=$('<td> <select  class="easyui-combobox"   style="line-height:17px;width:200px;border:1px solid #ccc"> <option value="00">交易量总和</option> </select> </td>')
        var CHARGE_PROPORTION = $("<th>收取比例：</th>");
        var CHARGE_PROPORTIONInput = $(' <td><input type="text" onkeyup="value=value.replace(/[^\\d{1,}\\.\\d{1,}|\\d{1,}]/g,\'\')"    placeholder="0.003"  style="line-height:17px; width:200px;border:1px solid #ccc"/></td>')
        CHARGE_TYPEInput.children().attr("name","CHARGE_TYPE"+number);
        CHARGE_PROPORTIONInput.children().attr("name","CHARGE_PROPORTION"+number);
        tr2.append(CHARGE_BASE);
        tr2.append(CHARGE_BASEInput);
        tr2.append(CHARGE_PROPORTION);
        tr2.append(CHARGE_PROPORTIONInput);
        var tr3 = $("<tr></tr>");
        var F_MONTH = $("<th>收取周期：</th>");
        var F_MONTHInput = $("<td><input type=\"text\"  sgin=\"sgin\" placeholder=\"起始月份\" data-options=\"required:true\" style=\"width:200px;\"></td>");
        var E_MONTH = $(" <th> &nbsp;至&nbsp;</th>");
        var E_MONTHImput =$('<td><input  type="text" id="E_MONTH0"  sgin="sgin" placeholder="结束月份"  data-options="required:true" style="width:200px;"></td>');
        var long_time=$('<td><input class="long_Time" type=\'checkbox\'>长期：</td>');
        /* var btt =$(' <td style="float:right"></td>');
         var a1=$(' <a onclick="addDiv();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:\'fi-plus icon-green\'"></a>')
         var a2=$(' <a onclick="subDiv();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:\'fi-minus icon-green\'"></a>')*/
       /*  btt.append(a1);
         btt.append(a2);*/

        F_MONTHInput.children().attr("name","F_MONTH"+number);
        F_MONTHInput.children().attr("id","F_MONTH"+number);
        F_MONTHInput.children().attr("class","easyui-datebox");
        E_MONTHImput.children().attr("name","E_MONTH"+number);
        E_MONTHImput.children().attr("id","E_MONTH"+number);
        E_MONTHImput.children().attr("class","easyui-datebox");
        long_time.children().attr("value",number);
        long_time.children().attr("name","longTime"+number);
        long_time.children().attr("onclick","checkboxOnclick(this)");
        $.parser.parse(long_time);
        $.parser.parse(F_MONTHInput);
        $.parser.parse(E_MONTHImput);
        tr3.append(F_MONTH);
        tr3.append(F_MONTHInput);
        tr3.append(E_MONTH);
        tr3.append(E_MONTHImput);
        tr3.append(long_time);
       /* tr3.append(btt);*/
        table.append(tr1);
        table.append(tr2);
        table.append(tr3);
        initTable.after(table);
        var hr = $('<hr/>');
        var br=$('<br/>');
        hr.attr("class","new_A"+number);
        hr.attr("class","new_A"+number);
        initTable.after(hr);
        initTable.after(br);




        var time = new Date();
        var year_month;
        if(time.getMonth()==0){
            year_month = (time.getFullYear()-1)+'-12'+'-01';
        }else{
            year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
        }

        $("input[sgin='sgin']").datebox({
            required:true
        });

        $("input[sgin='sgin']").datebox({
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

        $("input[sgin='sgin']").datebox('setValue',year_month);


        var start = $("input[sgin='sgin']").datebox('getValue');
        var end = $("input[sgin='sgin']").datebox('getValue');
    }
</script>



<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"  style="overflow: hidden;padding: 3px;overflow:auto">
        <form id="setServerAmtAddForm" method="post">
            <table class="grid">
                <tr id="addtr1">
                    <th>业务平台:</th>
                    <td id="addtd1">

                     <input id="bum_Id" class="easyui-combobox" name="bumId"   style="line-height:17px; width:200px;border:1px solid #ccc"
                               data-options="valueField:'BUM_ID',textField:'BUM_NAME',url:'${path}/SetServerAmtController/queryBumId'"/>
                    </td>
                   <%-- <td id="addtd1">
                        &lt;%&ndash;<select id="aaaa" class="easyui-combobox" name="SERVER_TYPE" >
                            <option value="00">服务费</option>
                            <option value="0440">444</option>
                        </select>&ndash;%&gt;
                 &lt;%&ndash;   <span id="mySpan">
                    <th>代理商名称：</th>
                    <td><input type='checkbox' name='isno'></td>
                    <td>
                        <input id="agentName" name="agentName"
                        <c:if test="${isagent.isOK()}"> value="${isagent.data.agName}" </c:if>
                               class="easyui-validatebox" data-options="required:true" readonly="readonly"
                               style="width:200px;">
                    </td>
                    </span>&ndash;%&gt;
                    </td>--%>
                </tr>
            </table>
            <br/>
            <hr/>
            <br/>
            <table class="grid" id="init_table">
                <tr>
                    <th>费用名称：</th>
                    <td>
                        <select id="SERVER_TYPE0" class="easyui-combobox" name="SERVER_TYPE0" style="line-height:17px;width:200px;border:1px solid #ccc" >
                            <option value="00">服务费</option>
                        </select>
                    </td>

                    <th>收费类型：</th>
                    <td>
                        <select id="CHARGE_TYPE0" class="easyui-combobox" name="CHARGE_TYPE0"  style="line-height:17px;width:200px;border:1px solid #ccc"  >
                            <option value="00">借记S0</option>
                            <option value="01">借记D0</option>
                            <option value="02">贷记S0</option>
                            <option value="03">贷记D0</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>收费基数：</th>
                    <td>
                        <select id="CHARGE_BASE0" class="easyui-combobox" name="CHARGE_BASE0"  style="line-height:17px;width:200px;border:1px solid #ccc">
                            <option value="00">交易量总和</option>
                        </select>
                    </td>
                    <th>收取比例：</th>

                    <td>
                        <input type="text" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"   name="CHARGE_PROPORTION0" placeholder="0.003"  style="line-height:17px; width:200px;border:1px solid #ccc"/>
                    </td>

                </tr>


                <tr>
                    <th>收取周期：</th>
                    <td>

                        <input id="F_MONTH0" class="style_box" sgin="sgin" name="F_MONTH0" placeholder="起始月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                    </td>
                    <th> &nbsp;至&nbsp;</th>
                    <td>
                        <input  id="E_MONTH0" class="style_box" sgin="sgin" name="E_MONTH0" placeholder="结束月份" class="easyui-datebox" data-options="required:true" style="width:200px;">
                    </td>
                    <td><input  class="long_Time" type='checkbox' name='longTime0' onclick="checkboxOnclick(this)" value="0">长期：</td>

                    <td style="float:right">
                        <a onclick="addDiv();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'"></a>
                        <a onclick="subDiv();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-minus icon-green'"></a>
                    </td>
                </tr>
            </table>
            <br/>
        </form>
    </div>
</div>