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
    $('#PROFIT_POS_DATE_START').datebox({
        required:true
    });
    $('#PROFIT_POS_DATE_END').datebox({
        required:true
    });

    $('#PROFIT_POS_DATE_START,#PROFIT_POS_DATE_END').datebox({
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
    $('#PROFIT_POS_DATE_START').datebox('setValue',year_month);
    $('#PROFIT_POS_DATE_END').datebox('setValue',year_month);

    var start = $('#PROFIT_POS_DATE_START').datebox('getValue');
    var end = $('#PROFIT_POS_DATE_END').datebox('getValue');

    var posRewardDetailListA;
    $(function() {
        posRewardDetailListA = $('#posRewardDetailList').datagrid({
            url : '${path }/postRewardDetail/rewardDetailList',
            striped : true,
            rownumbers : true,
            pagination : true,
            queryParams:{'PROFIT_POS_DATE_START':start ,'PROFIT_POS_DATE_END':end},
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [
                [ {
                title : '月份',
                field : 'PROFIT_POS_DATE'
            } , {
                title : '代理商唯一码',
                field : 'POS_AGENT_ID'
            } , {
                title : '代理商名称',
                field : 'POS_AGENT_NAME'
            }, {
                title : '上级代理商唯一码',
                field : 'PARENT_AGENT_ID'
            } , {
                title : '上级代理商名称',
                field : 'PARENT_AGENT_NAME'
            } ,{
                title : '机构类型',
                field : 'POS_MECHANISM_TYPE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "1":
                            return '二代直签直发';
                        case "2":
                            return '机构';
                        case "3":
                            return '机构一代';
                        case "4":
                            return '机构直签';
                        case "5":
                            return '一代X';
                        case "6":
                            return '标准一代';
                        case "7":
                            return '二代直签';
                        case "8":
                            return '直签不直发';
                    }
                }
            },
            {
                title : '机构编码',
                field : 'POS_MECHANISM_ID'
            } , {
                title : '对比月交易量',
                field : 'POS_COMPARE_COUNT'
            } , {
                title : '当月交易总量',
                field : 'POS_CURRENT_COUNT'
            } , {
                title : '对比月贷记交易量',
                field : 'POS_COMPARE_LOAN_COUNT'
            } , {
                title : '当月贷记交易总量',
                field : 'POS_CURRENT_LOAN_COUNT'
            }, {
                title : '奖励交易金额',
                field : 'POS_AMT'
            }, {
                title : '奖励标准',
                field : 'POS_STANDARD'
            }, {
                title : '本级奖励',
                field : 'POS_OWN_REWARD'
            }, {
                title : '下级奖励',
                field : 'POS_DOWN_REWARD'
            }, {
                title : '奖励分润',
                field : 'POS_REAWRD_PROFIT'
            }, {
                    title : '备注',
                    field : 'POS_REMARK',
                    formatter : function(value, row, index) {
                        switch (value) {
                            case "TY_Template":
                                return '通用模板';
                            case "TS_Template":
                                return '特殊模板';
                            default : "无奖励";
                        }
                    }
                }] ],
            toolbar : '#posRewardDetailToolbar'
        });
    });

    /**
     * 搜索事件
     */
    function searchProfitDirect() {
        posRewardDetailListA.datagrid('load', $.serializeObject($('#posRewardDetailListAForm')));
    }
    /**
     * 清空事件
     */
    function cleanProfitDirect() {
        $('#posRewardDetailListAForm input').val('');
        $('#PROFIT_POS_DATE_START').datebox('setValue','');
        $('#PROFIT_POS_DATE_END').datebox('setValue','');
        posRewardDetailListA.datagrid('load', {});
    }
    /**
     * 统计事件
     */
    function posRewardCount(){
        var data=$.serializeObject($('#posRewardDetailListAForm'));
        var profitCountUrl=encodeURI('/postRewardDetail/profitCount?POS_AGENT_ID='+data.POS_AGENT_ID+'&POS_AGENT_NAME='+data.POS_AGENT_NAME+'&PROFIT_POS_DATE_START='+data.PROFIT_POS_DATE_START+'&PROFIT_POS_DATE_END='+data.PROFIT_POS_DATE_END+'&POS_MECHANISM_TYPE='+data.POS_MECHANISM_TYPE+'&POS_REMARK='+data.POS_REMARK);
        parent.$.modalDialog({
            title : '统计',
            width : 200,
            height : 100,
            href : profitCountUrl
        });
        console.log(data);
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="posRewardDetailList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form method="post" action="${path}/profitDirect/exportProfitDirect" id ="posRewardDetailListAForm"  >
            <table>
                <tr>
                    <th>代理商名称:</th>
                    <td><input name="POS_AGENT_NAME" id="POS_AGENT_NAME" style="line-height:17px;border:1px solid #ccc;width:120px;"></td>
                    <td>&nbsp;</td>
                    <th>代理商唯一码:</th>
                    <td><input name="POS_AGENT_ID" id="POS_AGENT_ID" style="line-height:17px;border:1px solid #ccc;width:120px;"></td>
                    <td>&nbsp;</td>
                    <th>分润月份:</th>
                    <td><input id = "PROFIT_POS_DATE_START" name="PROFIT_POS_DATE_START"  style="line-height:17px;border:1px solid #ccc;width:160px;" type="text"></td>
                    <th>-</th>
                    <td><input id = "PROFIT_POS_DATE_END" name="PROFIT_POS_DATE_END"  style="line-height:17px;border:1px solid #ccc;width:160px;" type="text"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProfitDirect();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanProfitDirect();">清空</a>
                        <shiro:hasPermission name="/profit/profitByFinance/final">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="posRewardCount()">统计</a>
                        </shiro:hasPermission>
                    </td>
                    <!-- 新增导出-->
                    <shiro:hasPermission name="/profit/direct/export">
                        <td>
                            <button type="submit" class="easyui-linkbutton"  data-options="iconCls:'icon-print',plain:true," >导出</button>
                        </td>
                    </shiro:hasPermission>
                </tr>
                <tr>
                    <th>机构类型:</th>
                    <td>
                        <select name="POS_MECHANISM_TYPE" id="POS_MECHANISM_TYPE" style="line-height:17px;border:1px solid #ccc;width:120px;">
                            <option value="">---请选择---</option>
                            <option value="1">二代直签直发</option>
                            <option value="2">机构</option>
                            <option value="3">机构一代</option>
                            <option value="4">机构直签</option>
                            <option value="5">一代X</option>
                            <option value="6">标准一代</option>
                            <option value="7">二代直签</option>
                            <option value="8">直签不直发</option>
                        </select>
                    </td>
                    <td>&nbsp;</td>
                    <th>备注类型:</th>
                    <td>
                        <select name="POS_REMARK" id="POS_REMARK" style="line-height:17px;border:1px solid #ccc;width:120px;">
                            <option value="">---请选择---</option>
                            <option value="TY_Template">通用模板</option>
                            <option value="TS_Template">特殊模板</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
