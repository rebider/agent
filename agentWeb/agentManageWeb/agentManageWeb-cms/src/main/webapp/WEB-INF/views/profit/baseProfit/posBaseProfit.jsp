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
    $('#DATE_START').datebox({
        required:true
    });
    $('#DATE_END').datebox({
        required:true
    });

    $('#DATE_END,#DATE_START').datebox({
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

    $('#DATE_START').datebox('setValue',year_month);
    $('#DATE_END').datebox('setValue',year_month);

    var start = $('#DATE_START').datebox('getValue');
    var end = $('#DATE_END').datebox('getValue');


    var profitPosBase;
    $(function() {
        profitPosBase = $('#SupplyPosBaseList').datagrid({
            url : '${path }/posBaseProfit/getBaseProfitList?type=POS',
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
                title : '刷卡交易量',
                field : 'POS_TRAN_AMT',
                align : 'center',
                width : 120
            }, {
                title : '收单手续费',
                field : 'POS_TRAN_FEE',
                align : 'center',
                width : 120
            }, {
                title : '刷卡分润',
                field : 'POS_PFT_AMT',
                align : 'center',
                width : 120
            }, {
                title : '二维码交易量',
                field : 'QR_TRAN_AMT',
                align : 'center',
                width : 120
            }, {
                title : '二维码手续费',
                field : 'QR_TRAN_FEE',
                align : 'center',
                width : 120
            }, {
                title : '二维码分润',
                field : 'QR_PFT_AMT',
                align : 'center',
                width : 120
            }, {
                title : '补差',
                field : 'SUPPLY_AMT',
                align : 'center',
                width : 120
            }, {
                title : 'POS奖励',
                field : 'POS_REWARD_AMT',
                align : 'center',
                width : 120
            }, {
                title : '付款交易额',
                field : 'IN_TRANS_AMT',
                align : 'center',
                width : 120
            }, {
                title : '付款交易分润额',
                field : 'IN_PROFIT_AMT',
                align : 'center',
                width : 120
            }, {
                title : '出款交易额',
                field : 'OUT_TRANS_AMT',
                align : 'center',
                width : 120
            }, {
                title : '出款交易分润额',
                field : 'OUT_PROFIT_AMT',
                align : 'center',
                width : 120
            }, {
                title : '服务费',
                field : 'SERVER_AMT',
                align : 'center',
                width : 120
            },{
                title : '备注',
                field : 'REMARK',
                align : 'center',
                width : 100
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
    function resetPosBase(){
        $('#AGENT_NAME').val('');
        $("#concludeChild").val("2");
        $('#AGENT_ID').val('');
        $("#BUS_NAME").combobox('setValue',"");
        $("#BUS_CODE").val("");
        $('#DATE_START').datebox('setValue','');
        $('#DATE_END').datebox('setValue','');
        profitPosBase.datagrid('load',{});
    }

    /**
     * 搜索事件
     */
    function searchPosBase() {
        var flage = $("#concludeChild").val();

        if(flage == 1){
           if(($("#AGENT_NAME").val()==null && $("#AGENT_ID").val()==null)||($("#AGENT_NAME").val()=="" && $("#AGENT_ID").val()=="")){
               $.messager.alert("提示","请选择代理商编码或名称")
               return;
           }
            if(($("#DATE_START").datebox('getValue'))!= ($("#DATE_END").datebox('getValue'))){
                $.messager.alert("提示","请将日期确定为一个月查询")
                return;
            }
        }


        profitPosBase.datagrid('load', $.serializeObject($('#ProfitPosBaseeForm')));


    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="contract" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="SupplyPosBaseList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 56px; overflow: hidden;background-color: #fff">
        <form id ="ProfitPosBaseeForm" method="post">
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input id = "AGENT_NAME" name="AGENT_NAME"  style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th>代理商唯一码：</th>
                    <td><input id = "AGENT_ID" name="AGENT_ID" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th>平台码：</th>
                    <td><input id = "BUS_CODE" name="BUS_CODE" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>

                    <th>是否包含下级:</th>
                    <td>
                        <select id="concludeChild" name="concludeChild" style="width: 80px;line-height: 47px">
                            <option value="2">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>

                </tr>
                <tr>
                <th>产品类型：</th>
                    <td>


                        <input id="BUS_NAME" class="easyui-combobox" name="BUS_NAME"
                               data-options="valueField:'PLATFORM_NUM',textField:'PLATFORM_NAME',url:'${path }/posBaseProfit/queryBusNum?type=POS'" />


                        <%--<select id="BUS_NAME" class="easyui-combobox" name="BUS_NAME" style="line-height:17px;border:1px solid #ccc;width:160px;">
                    <option value="">--请选择--</option>
                    <option value="100003">大POS</option>
                    <option value="100002">智能pos</option>
                    <option value="100004">瑞e付</option>
                    <option value="100005">瑞银付</option>
                    <option value="100007">瑞付</option>
                    <option value="100006">瑞享付</option>
                </select>--%>

                </td>
                    <th>日期:</th>
                    <td><input id="DATE_START" name="DATE_START" type="text" style="line-height:17px;border:1px solid #ccc;width:140px;" ></td>
                    <th>-</th>
                    <td><input id="DATE_END" name="DATE_END" type="text" style="line-height:17px;border:1px solid #ccc;width:140px;" ></td>

                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchPosBase();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="resetPosBase();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>