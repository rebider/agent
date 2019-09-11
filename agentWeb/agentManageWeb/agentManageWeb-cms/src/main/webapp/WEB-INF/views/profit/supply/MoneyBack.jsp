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
    $('#SUPPLY_D_START').datebox({
        required:true
    });
    $('#SUPPLY_D_END').datebox({
        required:true
    });

    $('#SUPPLY_D_START,#SUPPLY_D_END').datebox({
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
    $('#SUPPLY_D_START').datebox('setValue',year_month);
    $('#SUPPLY_D_END').datebox('setValue',year_month);

    var start = $('#SUPPLY_D_START').datebox('getValue');
    var end = $('#SUPPLY_D_END').datebox('getValue');


    var profitSupplyC;
    $(function() {
        profitSupplyC = $('#moneyList').datagrid({
            url : '${path }/profitSupply/moneyList?sign=01',
            striped : true,
            rownumbers : true,
            queryParams:{'SUPPLY_DATE_START':start ,'SUPPLY_DATE_END':end},
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
                title : '代理商唯一码',
                field : 'AGENT_ID',
                align : 'center',
                width : 180
            } , {
                title : '代理商名称',
                field : 'AGENT_NAME',
                align : 'center',
                width : 200
            }
            , {
                title : '上级代理商编号',
                field : 'PARENT_AGENT_ID',
                align : 'center',
                width : 180
            } , {
                title : '上级代理商名称',
                field : 'PARENT_AGENT_NAME',
                align : 'center',
                width : 180
            } , {
                title : '月份',
                field : 'SUPPLY_DATE',
                align : 'center',
                width : 120
            } , {
                title : '补款类型',
                field : 'SUPPLY_TYPE',
                align : 'center',
                width : 120
            } , {
                title : '补款金额',
                field : 'SUPPLY_AMT',
                align : 'center',
                width : 120
            } , {
                    field: 'action',
                    title: '操作',
                    width: 300,
                    formatter: function (value, row) {
                        var str = '';
                            str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="queryMoneyDetail(\'{0}\');" >查看明细</a>', row.ID);
                        return str;
                    }
                } ] ],
            onLoadSuccess: function (data) {
                $('.easyui-linkbutton-query').linkbutton();
            },
            toolbar : '#ProfitSupplyToolbar'
        });

    });

    /**
     * 查看补款明细
     */
    function queryMoneyDetail(ID) {
        addTab({
            title: '补款明细数据查看' + ID,
            border: false,
            closable: true,
            fit: true,
            href: '${path}/profit/settleErr/gotoProfitSettleErrList?type=2&sourceId='+ID
        });
    }

    /**
     * 搜索事件
     */
    function searchProfitMoney() {

        profitSupplyC.datagrid('load', $.serializeObject($('#ProfitMoneyForm')));
    }

    function cleanProfitMoney() {
        $('#ProfitMoneyForm input').val('');
        $('#SUPPLY_D_START').datebox('setValue','');
        $('#SUPPLY_D_END').datebox('setValue','');
        profitSupplyC.datagrid('load', {});
    }
    /**
     * 统计事件
     */
    function profitMoneyCount(){
        var data=$.serializeObject($('#ProfitMoneyForm'));
        var profitCountUrl=encodeURI('/profitSupply/profitCount?AGENT_NAME='+data.AGENT_NAME+'&AGENT_ID='+data.AGENT_ID+'&BUS_TYPE='+data.BUS_TYPE+'&SUPPLY_DATE_START='+data.SUPPLY_DATE_START+'&SUPPLY_DATE_END='+data.SUPPLY_DATE_END+'&BUS_BIG_TYPE=01');
        parent.$.modalDialog({
            title : '统计',
            width : 200,
            height : 100,
            href : profitCountUrl
        });
        console.log(data);
    }

    // 清除上月数据
    function resetMoneyData(){
        parent.$.messager.confirm('询问', '确认要清除上月份数据么？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+"/profitSupply/resetData",
                    dataType:'json',
                    success: function(msg){
                        if (msg.success) {
                            profitSupplyC.datagrid('reload');
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        profitSupplyC.datagrid('reload');
                    }
                });
            }
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="moneyList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 56px; overflow: hidden;background-color: #fff">
        <form id ="ProfitMoneyForm" method="post">
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input name="AGENT_NAME" id="AGENT_NAME" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>
                    <th>代理商唯一码：</th>
                    <td><input name="AGENT_ID" id="AGENT_ID" style="line-height:17px;border:1px solid #ccc;width:160px;">&nbsp;&nbsp;&nbsp;</td>


                    <th></th>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProfitMoney();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanProfitMoney();">清空</a>
                        <shiro:hasPermission name="/profit/profitByFinance/final">
                            <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="profitMoneyCount()">统计</a>
                        </shiro:hasPermission>
                    </td>



                    <th></th>
                    <td><input name="BUS_BIG_TYPE" id="BUS_BIG_TYPE" value="01" style="display: none"></td>

                </tr>
                <tr>
                    <th>补款类型：</th>
                    <td><select id="BUS_TYPE" class="easyui-combobox" name="BUS_TYPE" style="width:160px;">
                        <option value="">----请选择-----------</option>
                        <option value="01">手刷退款补款</option>
                        <option value="02">POS退单补款</option>
                    </select> &nbsp;&nbsp;&nbsp;</td>

                    <th>月份：</th>
                    <td><input id = "SUPPLY_D_START" name="SUPPLY_DATE_START"  type="text" style="line-height:17px;border:1px solid #ccc;width:160px;" ></td>
                    <th>-</th>
                    <td><input id = "SUPPLY_D_END" name="SUPPLY_DATE_END" type="text" style="line-height:17px;border:1px solid #ccc;width:160px;" ></td>
                   <%-- <td>&nbsp;&nbsp;&nbsp;
                        <c:if test="${noEdit==0}">
                            <shiro:hasPermission name="/profit/supply/clear">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-print',plain:true" onclick="resetMoneyData();">清除上月份数据</a>
                            </shiro:hasPermission>
                        </c:if>
                    </td>--%>
                </tr>
            </table>
        </form>
    </div>
</div>
