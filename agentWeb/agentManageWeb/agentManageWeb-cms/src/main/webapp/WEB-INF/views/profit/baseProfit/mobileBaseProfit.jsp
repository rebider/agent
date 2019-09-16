<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript">


    var time = new Date();
    var year_month;
    if(time.getMonth()==0){
        year_month = (time.getFullYear()-1)+'-12'+'-01';
    }else{
        year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
    }
    $('#DATE_Mobile_START').datebox({
        required:true
    });
    $('#DATE_Mobile_END').datebox({
        required:true
    });

    $('#DATE_Mobile_END,#DATE_Mobile_START').datebox({
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

    $('#DATE_Mobile_START').datebox('setValue',year_month);
    $('#DATE_Mobile_END').datebox('setValue',year_month);

    var start = $('#DATE_Mobile_START').datebox('getValue');
    var end = $('#DATE_Mobile_END').datebox('getValue');


    var profitMobileBase;
    $(function() {

        profitMobileBase = $('#SupplyMobileBaseList').datagrid({
            url : '${path }/posBaseProfit/getBaseProfitList?type=MPOS',
            striped : true,
            rownumbers : true,
            pagination : true,
            queryParams:{"DATE_START":start,"DATE_END":end},
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
                field : 'PROFIT_DATE',
                align : 'center',
                width : 120
            } , {
                title : '上级代理商唯一码',
                field : 'PARENT_AGENT_ID',
                align : 'center',
                width : 200
            }, {
                    title : '上级代理商名称',
                    field : 'PARENT_AGENT_NAME',
                    align : 'center',
                    width : 200
                }, {
                    title : '代理商唯一码',
                    field : 'AGENT_ID',
                    align : 'center',
                    width : 200
                } , {
                    title : '代理商名称',
                    field : 'AGENT_NAME',
                    align : 'center',
                    width : 200
                } , {
                    title : '平台码',
                    field : 'BUS_CODE',
                    align : 'center',
                    width : 120
                }, {
                title : '产品类型',
                field : 'BUS_NAME',
                align : 'center',
                width : 120,

                formatter : function(value, row, index) {
                    for(var i=0;i< db_options.ablePlatForm.length;i++){
                        if (db_options.ablePlatForm[i].platformNum == value) {
                            var temp = db_options.ablePlatForm[i].platformName;
                                return temp;
                        }
                    }
                }
               /* formatter: function(value,row,index){
                   if(value=="100002"){
                         return "智能POS";
                    }else if(value=="100003"){
                        return "大POS";
                    }else if(value=="100004"){
                        return "瑞e付";
                    }else if(value=="100005"){
                        return "瑞银付";
                    }else if(value=="100006"){
                        return "瑞享送";
                    }else if(value=="100007"){
                         return "瑞付";
                     }
                }*/

            }, {
                title : '交易量',
                field : 'IN_TRANS_AMT',
                align : 'center',
                width : 120
            }, {
                title : '手续费',
                field : 'TRANS_FEE',
                align : 'center',
                width : 120
            }, {
                title : '服务费',
                field : 'SERVER_AMT',
                align : 'center',
                width : 120
            }, {
                title : '分润',
                field : 'PROFIT_AMT',
                align : 'center',
                width : 120
            },{
                title : '补差',
                field : 'SUPPLY_AMT',
                align : 'center',
                width : 120
            },{
                title : '备注',
                field : 'REMARK',
                align : 'center',
                width : 400
            }] ],
            onLoadSuccess: function (data) {
                $('.easyui-linkbutton-query').linkbutton();
            },
            toolbar : '#ProfitSupplyToolbar'
        });

    })
    /**
     * 重置
     */
    function resetMobileBase(){
        $('#AGENT_NAME_Mobile').val('');
        $("#concludeChild_Mobile").val("2");
        $('#AGENT_ID_Mobile').val('');
        $("#BUS_NAME_Mobile").combobox('setValue',"");
        $("#BUS_CODE_Mobile").val("");
        $('#DATE_Mobile_START').datebox('setValue','');
        $('#DATE_Mobile_END').datebox('setValue','');
        profitMobileBase.datagrid('load',{});
    }

    /**
     * 搜索事件
     */
    function searchMobileBase() {
        var flage = $("#concludeChild_Mobile").val();

        if(flage == 1){
           if(($("#AGENT_NAME_Mobile").val()==null && $("#AGENT_ID_Mobile").val()==null)||($("#AGENT_NAME_Mobile").val()=="" && $("#AGENT_ID_Mobile").val()=="")){
               $.messager.alert("提示","请选择代理商编码或名称")
               return;
           }
            if(($("#DATE_Mobile_START").datebox('getValue'))!= ($("#DATE_Mobile_END").datebox('getValue'))){
                $.messager.alert("提示","请将日期确定为一个月查询")
                return;
            }
        }


        profitMobileBase.datagrid('load', $.serializeObject($('#profitMobileBaseFrom')));


    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="contract" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="SupplyMobileBaseList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 56px; overflow: hidden;background-color: #fff">
        <form id ="profitMobileBaseFrom" method="post">
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input id = "AGENT_NAME_Mobile" name="AGENT_NAME"  style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th>代理商唯一码：</th>
                    <td><input id = "AGENT_ID_Mobile" name="AGENT_ID" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th>平台码：</th>
                    <td><input id = "BUS_CODE_Mobile" name="BUS_CODE" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>

                    <th>是否包含下级:</th>
                    <td>
                        <select id="concludeChild_Mobile" name="concludeChild" style="width: 80px;line-height: 47px">
                            <option value="2">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>

                </tr>
                <tr>
                <th>产品类型：</th>
                <td>

                    <input id="BUS_NAME_Mobile" class="easyui-combobox" name="BUS_NAME"
                           data-options="valueField:'PLATFORM_NUM',textField:'PLATFORM_NAME',url:'${path }/posBaseProfit/queryBusNum?type=MPOS'" />


                <%--    <select id="BUS_NAME" class="easyui-combobox" name="BUS_NAME" style="line-height:17px;border:1px solid #ccc;width:160px;">
                    <option value="">--请选择--</option>
                    <option value="0001">瑞银信</option>
                    <option value="2000">瑞刷</option>
                    <option value="1001">贴牌</option>
                    <option value="5000">瑞和宝</option>
                    <option value="1111">瑞银信活动</option>
                    <option value="3000">瑞刷活动</option>
                    <option value="6000">瑞和宝直发平台</option>
                    <option value="4000">瑞众通</option>
                </select>--%>
                </td>
                    <th>日期:</th>
                    <td><input id="DATE_Mobile_START" name="DATE_START" type="text" style="line-height:17px;border:1px solid #ccc;width:140px;" ></td>
                    <th>-</th>
                    <td><input id="DATE_Mobile_END" name="DATE_END" type="text" style="line-height:17px;border:1px solid #ccc;width:140px;" ></td>

                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchMobileBase();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="resetMobileBase();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>