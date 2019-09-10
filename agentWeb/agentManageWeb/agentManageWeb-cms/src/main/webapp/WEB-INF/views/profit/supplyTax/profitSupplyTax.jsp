<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript">
    //定义变量记录直签直发之间切换(temp=1 为直签，temp=2为直发)
    var temp=1;

    var time = new Date();
    var year_month;
    if(time.getMonth()==0){
        year_month = (time.getFullYear()-1)+'-12'+'-01';
    }else{
        year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
    }
    $('#DATESTART').datebox({
        required:true
    });
    $('#DATEEND').datebox({
        required:true
    });

    $('#DATEEND,#DATESTART').datebox({
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

    $('#DATESTART').datebox('setValue',year_month);
    $('#DATEEND').datebox('setValue',year_month);

    var start = $('#DATESTART').datebox('getValue');
    var end = $('#DATEEND').datebox('getValue');


    var profitSupplyA;
    $(function() {
        profitSupplyA = $('#SupplyTList').datagrid({
            url : '${path }/profitSupplyTaxController/getProfitSupplyTaxList?sign=01',
            striped : true,
            rownumbers : true,
            queryParams:{"DATESTART":start,"DATEEND":end},
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title: 'id',
                field: 'ID',
                hidden: true
            } , {
                title : '分润月份',
                field : 'SUPPLY_TAX_DATE',
                align : 'center',
                width : 180
            } , {
                title : '代理商唯一码',
                field : 'SUPPLY_TAX_AGENT_ID',
                align : 'center',
                width : 200
            }, {
                    title : '代理商名称',
                    field : 'SUPPLY_TAX_AGENT_NAME',
                    align : 'center',
                    width : 180
                } , {
                    title : '代理商类型',
                    field : 'SUPPLY_TAX_TYPE',
                    align : 'center',
                    width : 180,
                formatter: function (value, row) {
                    if (value=="1") {
                        return "二代直签直发";
                    }else if(value=="2"){
                        return "机构";
                    }else if(value=="3"){
                        return "机构一代";
                    }else if(value=="5"){
                        return "一代X";
                    }else if(value=="6"){
                        return "标准一代";
                    }else if(value=="8"){
                        return "直签不直发"
                    }
                }
                } , {
                    title : '下级代理商唯一码',
                    field : 'SUPPLY_TAX_SUB_ID',
                    align : 'center',
                    width : 120
                } , {
                    title : '下级代理商名称',
                    field : 'SUPPLY_TAX_SUB_NAME',
                    align : 'center',
                    width : 120
                } , {
                    title : '补税金额',
                    field : 'SUPPLY_TAX_AMT',
                    align : 'center',
                    width : 120
                } ] ],
            onLoadSuccess: function (data) {
                $('.easyui-linkbutton-query').linkbutton();
            },
            toolbar : '#ProfitSupplyToolbar'
        });

    })
    /**
     * 重置
     */
    function resetTax(){
        $('#SUPPLY_TAX_AGENT_NAME').val('');
        $("#concludeChild").val("2");
        $('#DATESTART').datebox('setValue','');
        $('#DATEEND').datebox('setValue','');
        $('#SUPPLY_TAX_AGENT_ID').val('');
        profitSupplyA.datagrid('load',{});
    }

    /**
     * 搜索事件
     */
    function searchTaxProfitSupply() {
        var flage = $("#concludeChild").val();

        if(flage == 1){
           if(($("#SUPPLY_TAX_AGENT_NAME").val()==null && $("#SUPPLY_TAX_AGENT_ID").val()==null)||($("#SUPPLY_TAX_AGENT_NAME").val()=="" && $("#SUPPLY_TAX_AGENT_ID").val()=="")){
               $.messager.alert("提示","请选择代理商编码或名称")
               return;
           }
            if(($("#DATESTART").datebox('getValue'))!= ($("#DATEEND").datebox('getValue'))){
               /* if((($("#DATESTART").datebox('getValue')==null)&&($("#DATEEND").datebox('getValue')==null))||(($("#DATESTART").datebox('getValue')=='')&&($("#DATEEND").datebox('getValue')==''))){
                    $.messager.alert("提示","月份不能为空")
                    return;
                }*/
                $.messager.alert("提示","请将日期确定为一个月查询")
                return;
            }

        }
        profitSupplyA.datagrid('load', $.serializeObject($('#ProfitSupplyForm')));
    }

    /**
     * 统计事件
     */
    function taxSupplyProfitCount() {
        var flage = $("#concludeChild").val();

        if(flage == 1){
            if(($("#SUPPLY_TAX_AGENT_NAME").val()==null && $("#SUPPLY_TAX_AGENT_ID").val()==null)||($("#SUPPLY_TAX_AGENT_NAME").val()=="" && $("#SUPPLY_TAX_AGENT_ID").val()=="")){
                $.messager.alert("提示","请选择代理商编码或名称")
                return;
            }
            if(($("#DATESTART").datebox('getValue'))!= ($("#DATEEND").datebox('getValue'))){
                /* if((($("#DATESTART").datebox('getValue')==null)&&($("#DATEEND").datebox('getValue')==null))||(($("#DATESTART").datebox('getValue')=='')&&($("#DATEEND").datebox('getValue')==''))){
                     $.messager.alert("提示","月份不能为空")
                     return;
                 }*/
                $.messager.alert("提示","请将日期确定为一个月查询")
                return;
            }

        }
        var data=$.serializeObject($('#ProfitSupplyForm'));
        var profitCountUrl=encodeURI('/profitSupplyTaxController/profitCount?SUPPLY_TAX_AGENT_ID='+data.SUPPLY_TAX_AGENT_ID+'&SUPPLY_TAX_AGENT_NAME='+data.SUPPLY_TAX_AGENT_NAME+'&DATESTART='+data.DATESTART+'&DATEEND='+data.DATEEND+'&flage='+flage+'&temp='+temp);
        parent.$.modalDialog({
            title : '统计',
            width : 200,
            height : 100,
            href : profitCountUrl
        });
        console.log(encodeURI);
    }
    function contract() {
        temp=1;
        $('#isno').show();
        $('#concludeChild').show();
       $('#SupplyTList').datagrid({
            url : '${path }/profitSupplyTaxController/getProfitSupplyTaxList?sign=01',
            striped : true,
           queryParams:{"DATESTART":start,"DATEEND":end},
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title: 'id',
                field: 'ID',
                hidden: true
            } , {
                title : '分润月份',
                field : 'SUPPLY_TAX_DATE',
                align : 'center',
                width : 180
            } , {
                title : '代理商唯一码',
                field : 'SUPPLY_TAX_AGENT_ID',
                align : 'center',
                width : 200
            }, {
                title : '代理商名称',
                field : 'SUPPLY_TAX_AGENT_NAME',
                align : 'center',
                width : 180
            } , {
                title : '代理商类型',
                field : 'SUPPLY_TAX_TYPE',
                align : 'center',
                width : 180,
                formatter: function (value, row) {
                    if (value=="1") {
                        return "二代直签直发";
                    }else if(value=="2"){
                        return "机构";
                    }else if(value=="3"){
                        return "机构一代";
                    }else if(value=="5"){
                        return "一代X";
                    }else if(value=="6"){
                        return "标准一代";
                    }else if(value=="8"){
                        return "直签不直发"
                    }
                }
            } , {
                title : '下级代理商唯一码',
                field : 'SUPPLY_TAX_SUB_ID',
                align : 'center',
                width : 120
            } , {
                title : '下级代理商名称',
                field : 'SUPPLY_TAX_SUB_NAME',
                align : 'center',
                width : 120
            } , {
                title : '补税金额',
                field : 'SUPPLY_TAX_AMT',
                align : 'center',
                width : 120
            } ] ],
            onLoadSuccess: function (data) {
                $('.easyui-linkbutton-query').linkbutton();
            },
            toolbar : '#ProfitSupplyToolbar'
        });
    }
    function sendOut() {
        temp=2;
        $('#isno').hide();
        $('#concludeChild').hide();
        var startDate =  $('#DATESTART').datebox('getValue');
        var endDate =  $('#DATEEND').datebox('getValue');
        $('#SupplyTList').datagrid({
            url : '${path }/profitSupplyTaxController/getProfitSupplyTaxList?sign=02',
            striped : true,
            queryParams:{"DATESTART":startDate,"DATEEND":endDate},
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title: 'id',
                field: 'ID',
                hidden: true
            } , {
                title : '分润月份',
                field : 'SUPPLY_TAX_DATE',
                align : 'center',
                width : 180
            } , {
                title : '代理商唯一码',
                field : 'SUPPLY_TAX_AGENT_ID',
                align : 'center',
                width : 200
            }, {
                title : '代理商名称',
                field : 'SUPPLY_TAX_AGENT_NAME',
                align : 'center',
                width : 180
            } , {
                title : '代理商类型',
                field : 'SUPPLY_TAX_TYPE',
                align : 'center',
                width : 180,
                formatter: function (value, row) {
                    if (value=="1") {
                        return "二代直签直发";
                    }else if(value=="2"){
                        return "机构";
                    }else if(value=="3"){
                        return "机构一代";
                    }else if(value=="5"){
                        return "一代X";
                    }else if(value=="6"){
                        return "标准一代";
                    }else if(value=="8"){
                        return "直签不直发"
                    }
                }
            } ,  {
                title : '补税金额',
                field : 'SUPPLY_TAX_AMT',
                align : 'center',
                width : 120
            } ] ],
            onLoadSuccess: function (data) {
                $('.easyui-linkbutton-query').linkbutton();

            },
            toolbar : '#ProfitSupplyToolbar'
        })
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="contract" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="SupplyTList" data-options="fit:true,border:false"></table>
        <table id='sendOut" data-options="fit:true,border:false" '></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 56px; overflow: hidden;background-color: #fff">
        <form id ="ProfitSupplyForm" method="post">
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input id = "SUPPLY_TAX_AGENT_NAME" name="SUPPLY_TAX_AGENT_NAME"  style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th>代理商唯一码：</th>
                    <td><input id = "SUPPLY_TAX_AGENT_ID" name="SUPPLY_TAX_AGENT_ID" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th>日期</th>
                    <td><input id="DATESTART" name="DATESTART" type="text" style="line-height:17px;border:1px solid #ccc;width:140px;" ></td>
                    <th>-</th>
                    <td><input id="DATEEND" name="DATEEND" type="text" style="line-height:17px;border:1px solid #ccc;width:140px;" ></td>
                    <th id ='isno'>是否包含下级:</th>
                    <td>
                        <select id="concludeChild" name="concludeChild" style="width: 80px;line-height: 47px">
                            <option value="2">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchTaxProfitSupply();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="resetTax();">清空</a>
                        <shiro:hasPermission name="/profitSupplyTaxController/SupplyTaxPage/finance">
                            <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="taxSupplyProfitCount()">统计</a>
                        </shiro:hasPermission>
                    </td>
                </tr>

            </table>
        </form>

       <%-- <div id="tt" class="easyui-tabs">
            <div title="直签" style="padding:20px;display:none;" onclick="contract()">
                alert('tab1');
            </div>
            <div title="直发"  style="overflow:auto;padding:20px;display:none;">
                tab2
            </div>
        </div>--%>

        <input type="button" value="直签" onclick="contract()">&nbsp&nbsp&nbsp<input type="button" value="直发" onclick="sendOut()">
    </div>
</div>