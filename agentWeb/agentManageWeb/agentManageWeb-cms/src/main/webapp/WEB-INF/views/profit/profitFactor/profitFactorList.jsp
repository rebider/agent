<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<script type="text/javascript">

    //设置默认日期
    var time = new Date();
    var year_month;
    if(time.getMonth()==0){
        year_month = (time.getFullYear()-1)+'-12'+'-01';
    }else{
        year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
    }

    $('#dateStart').datebox({
        required:true
    });
    $('#dateEnd').datebox({
        required:true
    });

    $("#dateStart,#dateEnd").datebox({
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

    $('#dateStart').datebox('setValue',year_month);
    $('#dateEnd').datebox('setValue',year_month);

    var start = $('#dateStart').datebox('getValue');
    var end = $('#dateEnd').datebox('getValue');

    var profitFactorA;
    $(function() {
        profitFactorA = $('#factorList').datagrid({
            url : '${path }/profitFactor/getList',
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
                title : '代理商唯一码',
                field : 'AGENT_ID',
                align : 'center',
                width : 200
            } /*, {
                title : '代理商唯一码',
                field : 'AGENT_PID',
                align : 'center',
            } */, {
                title : '代理商名称',
                field : 'AGENT_NAME',
                align : 'center',
                width : 240
            } , {
                title : '上级代理商唯一码',
                field : 'PARENT_AGENT_ID',
                align : 'center',
                width : 240
            } , {
                title : '上级代理商名称',
                field : 'PARENT_AGENT_NAME',
                align : 'center',
                width : 240
            } , {
                title : '月份',
                field : 'FACTOR_MONTH',
                align : 'center',
                width : 120
            } , {
                title : '应还款',
                field : 'TATOL_AMT',
                align : 'center',
                width : 100
            } , {
                title : '已扣款',
                field : 'BUCKLE_AMT',
                align : 'center',
                width : 100
            } , {
                title : '未扣足',
                field : 'SURPLUS_AMT',
                align : 'center',
                width : 100
            } , {
                title : '备注',
                field : 'REMARK',
                align : 'center',
            } , {
                title : '导入时间',
                field : 'FACTOR_DATE',
                align : 'center',
                width : 140
            } ] ],
            toolbar : '#profitFactorToolbar'
        });
    });

    /**
     * 搜索事件
     */
    function searchProfitFactor() {
        profitFactorA.datagrid('load', $.serializeObject($('#ProfitFactorForm')));
    }

    function cleanProfitFactor() {
        $('#ProfitFactorForm input').val('');
        $('#dateStart').datebox('setValue','');
        $('#dateEnd').datebox('setValue','');
        profitFactorA.datagrid('load', {});
    }
    /**
     * 统计事件
     */
    function profitFactorCount(){
        var data=$.serializeObject($('#ProfitFactorForm'));
        var profitCountUrl=encodeURI('/profitFactor/profitCount?AGENT_NAME='+data.AGENT_NAME+'&AGENT_PID='+data.AGENT_PID+'&DATESTART='+data.DATESTART+'&DATEEND='+data.DATEEND);
        parent.$.modalDialog({
            title : '统计',
            width : 200,
            height : 100,
            href : profitCountUrl
        });
        console.log(data);
    }

    // 导入数据
    function importProfitFactor() {
        parent.$.modalDialog({
            title: '导入保理数据',
            width: 300,
            height: 110,
            href: '${path}/profitFactor/importPage',
            buttons: [{
                text: '确定',
                handler: function() {
                    parent.$.modalDialog.openner_dataGrid = profitFactorA;
                    var f = parent.$.modalDialog.handler.find('#importFileForm');
                    f.submit();
                }
            }]
        });
    }

    //清除上月数据
    function resetDataFactor() {
        parent.$.messager.confirm('询问', '确认要清除上月份数据么？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+"/profitFactor/resetDataFactor",
                    dataType: 'json',
                    success: function(msg){
                        if (msg.success) {
                            profitFactorA.datagrid('reload');
                        }
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        profitFactorA.datagrid('reload');
                    }
                });
            }
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="factorList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 32px; overflow: hidden;background-color: #fff">
        <form id ="ProfitFactorForm" method="post">
            <table>
                <tr>
                    <th>代理商名称：</th>
                    <td><input name="AGENT_NAME" id="AGENT_NAME" style="line-height:17px;border:1px solid #ccc;width:140px;"></td>
                    <td>&nbsp;</td>
                    <th>代理商唯一码：</th>
                    <td><input name="AGENT_PID" id="AGENT_PID" style="line-height:17px;border:1px solid #ccc;width:140px;"></td>
                    <td>&nbsp;</td>
                    <th>月份：</th>
                    <td><input id="dateStart" name="DATESTART" type="text" style="width: 100px"/></td>
                    <th>-</th>
                    <td><input id="dateEnd" name="DATEEND" type="text" style="width: 100px"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProfitFactor();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanProfitFactor();">清空</a>
                        <shiro:hasPermission name="/profit/profitByFinance/final">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="profitFactorCount()">统计</a>
                        </shiro:hasPermission>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;
                        <c:if test="${noEdit==0}">
                            <shiro:hasPermission name="/profit/factor/import">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-print',plain:true" onclick="importProfitFactor();">导入</a>
                            </shiro:hasPermission>
                        </c:if>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;
                        <c:if test="${noEdit==0}">
                            <shiro:hasPermission name="/profit/factor/clear">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-print',plain:true" onclick="resetDataFactor();">清除上月份数据</a>
                            </shiro:hasPermission>
                        </c:if>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
