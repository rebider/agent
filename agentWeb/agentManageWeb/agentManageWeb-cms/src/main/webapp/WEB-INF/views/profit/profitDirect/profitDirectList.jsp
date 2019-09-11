<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript">

    /*日期控件初始时间*/
    //设置默认日期：分润月
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

    $("#DATESTART,#DATEEND").datebox({
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

    $('#DATESTART').datebox('setValue',year_month);
    $('#DATEEND').datebox('setValue',year_month);

    var profitDirectA;
    $(function() {
        var start = $('#DATESTART').datebox('getValue');
        var end = $('#DATEEND').datebox('getValue');

        profitDirectA = $('#directList').datagrid({
            url : '${path }/profitDirect/getList',
            striped : true,
            rownumbers : true,
            pagination : true,
            queryParams:{'DATESTART':start ,'DATEEND':end},
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '代理商编号',
                field : 'AGENT_ID'
            } , {
                title : '代理商名称',
                field : 'AGENT_NAME'
            } , {
                title : '月份',
                field : 'TRANS_MONTH'
            } , {
                title : '上级代理商名称',
                field : 'PARENT_AGENT_NAME'
            }, {
                title : '上级代理商编码',
                field : 'PARENT_AGENT_ID'
            } , {
                title : '一级代理商名称',
                field : 'FRIST_AGENT_NAME'
            },{
                title : '一级代理商唯一码',
                field : 'FRIST_AGENT_PID'
            } , {
                title : '直发平台分润',
                field : 'PROFIT_AMT'
            } , {
                title : '退单补款',
                field : 'SUPPLY_AMT'
            }, {
                title : '退单扣款',
                field : 'BUCKLE_AMT'
            }, {
                title : '涉税前分润汇总',
                field : 'SHOULD_PROFIT'
            }, {
                title : '税款扣除',
                field : 'REAL_TAX_AMT'
            }, {
                title : '实发分润',
                field : 'ACTUAL_PROFIT'
            }, {
                title : '代下级退单扣款',
                field : 'PARENT_BUCKLE'
            }, {
                title : '代下级退单补款',
                field : 'PARENT_SUPPLY'
            },{
                title : '冻结状态',
                field : 'STATUS',
                formatter : function(value, row, index) {
                switch (value) {
                    case "1":
                        return '冻结';
                    case "2":
                        return '未冻结';
                    }
                }
            }, {
                title : '操作',
                field : 'OPERATION',
                formatter:function(value,row,index){
                    var str='<a href="javascript:void;" onclick="examineSupplyDetail(\''+row.AGENT_ID+'\',\''+row.PARENT_SUPPLY+'\',\''+row.TRANS_MONTH+'\')">查看补款明细</a>';
                    str+='&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str+='<a href="javascript:void;" onclick="examineDeductionDetail(\''+row.AGENT_ID+'\',\''+row.PARENT_BUCKLE+'\',\''+row.TRANS_MONTH+'\')">查看扣款明细</a>';
                    return str;
                }
            } ] ],
            toolbar : '#profitDirectToolbar'
        });
    });

    /**
     * 搜索事件
     */
    function searchProfitDirect() {
        profitDirectA.datagrid('load', $.serializeObject($('#ProfitDirectForm')));
    }
    /**
     * 清空事件
     */
    function cleanProfitDirect() {
        $('#ProfitDirectForm input').val('');
        profitDirectA.datagrid('load', {});
    }
    /**
     * 统计事件
     */
    function profitDirectCount(){
        var data=$.serializeObject($('#ProfitDirectForm'));
        var profitCountUrl=encodeURI('/profitDirect/profitCount?AGENT_NAME='+data.AGENT_NAME+'&AGENT_ID='+data.AGENT_ID+'&DATESTART='+data.DATESTART+'&DATEEND='+data.DATEEND+'&FIRST_AGENT_NAME='+data.FIRST_AGENT_NAME+'&FIRST_AGENT_ID='+data.FIRST_AGENT_ID);
        parent.$.modalDialog({
            title : '统计',
            width : 200,
            height : 100,
            href : profitCountUrl
        });
        console.log(data.AGENT_NAME);
    }
    /**
     * 查看补款明细
     */
    function examineSupplyDetail(agentId,parent_supply,trans_month){
        if(!(parent_supply>0)){
            parent.$.messager.alert('提示', '此代理商本月无代下级退单补款', 'info');
            return ;
        }
        parent.$.modalDialog({
            title : '代下级退单补款明细',
            width : 440,
            height : 300,
            href : '${path }/profitDirect/supplyDetail?agentId='+agentId+'&trans_month='+trans_month
        });
    }

    /**
     * 查看扣款明细
     * @param agentId 代理商ID
     * @param parent_buckle 代下级退单扣款总额
     * @param trans_month 分润月
     */
    function examineDeductionDetail(agentId,parent_buckle,trans_month) {
        if(!(parent_buckle>0)){
            parent.$.messager.alert('提示', '此代理商本月无代下级退单扣款', 'info');
            return ;
        }
        trans_month=trans_month.replace("-","");
        parent.$.modalDialog({
            title : '代下级退单扣款明细',
            width : 440,
            height : 300,
            resizable:"flase",
            href : '${path }/profitDirect/deductionDetail?agentId='+agentId+'&trans_month='+trans_month
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="directList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form method="post" action="${path}/profitDirect/exportProfitDirect" id ="ProfitDirectForm"  >
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input name="AGENT_NAME" id="AGENT_NAME" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>代理商编号：</th>
                    <td><input name="AGENT_ID" id="AGENT_ID" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>日期</th>
                    <td><input id="DATESTART" name="DATESTART" /></td>
                    <th>-</th>
                    <td><input id="DATEEND" name="DATEEND"/>
                </tr>
                <tr>
                    <th>一级代理商名称：</th>
                    <td><input name="FIRST_AGENT_NAME" id="FIRST_AGENT_NAME" style="line-height:17px;border:1px solid #ccc"></td>
                    <th>一级代理商唯一码：</th>
                    <td><input name="FIRST_AGENT_ID" id="FIRST_AGENT_ID" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProfitDirect();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanProfitDirect();">清空</a>
                        <shiro:hasPermission name="/profit/profitByFinance/final">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="profitDirectCount();">统计</a>
                        </shiro:hasPermission>
                    </td>
                    <!-- 新增导出-->
                <shiro:hasPermission name="/profit/direct/export">
                    <td>
                        <button type="submit" class="easyui-linkbutton"  data-options="iconCls:'icon-print',plain:true," >导出</button>
                    </td>
                </shiro:hasPermission>
                </tr>
            </table>
        </form>
    </div>
</div>
