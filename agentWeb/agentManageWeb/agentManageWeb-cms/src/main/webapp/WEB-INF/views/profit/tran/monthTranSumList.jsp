<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    //设置默认交易月
    var time = new Date();
    var year_month;
    if(time.getMonth()==0){
        year_month = (time.getFullYear()-1)+'-12'+'-01';
    }else{
        year_month = time.getFullYear()+'-'+(time.getMonth()>=10?time.getMonth():0+''+(time.getMonth()))+'-01';
    }

    $("#tranMonth").datebox({
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
    $('#tranMonth').datebox('setValue',year_month);
    var tranCheckDataList;
    $(function() {
        tranCheckDataList=$('#tranCheckList').datagrid({
            url : '${path }/profit/tran/check/getTranCheckData',
            striped : true,
            singleSelect : true,
            rownumbers : true,
            fit : true,
            idField : 'id',
            columns : [ [{
                title : '编号',
                field : 'id',
                align : 'center',
                width : 230
            },{
                title : '交易月份',
                field : 'profitMonth',
                align : 'center',
                width : 100
            },{
                title : '业务平台',
                field : 'platformType',
                align : 'center',
                width : 100
            },{
                title : '业务类型',
                field : 'platformNum',
                align : 'center',
                width : 100
            },{
                title : '技术(交易量)',
                field : 'technologyAmt',
                align : 'center',
                width : 120
            },{
                title : '技术(手续费)',
                field : 'technologyFee',
                align : 'center',
                width : 120
            },{
                title : '清算(交易量)',
                field : 'clearAmt',
                align : 'center',
                width : 120
            },{
                title : '清算(手续费)',
                field : 'clearFee',
                align : 'center',
                width : 120
            },{
                title : '差异(交易量)',
                field : 'differentAmt',
                align : 'center',
                width : 120,
                formatter:function (value,row,index) {
                    if(row.technologyAmt!=null&&row.clearAmt!=null){
                        Math.formatFloat = function(f, digit) {
                            var m = Math.pow(10, digit);
                            return parseInt(f * m, 10) / m;
                        }
                        return Math.formatFloat(row.technologyAmt-row.clearAmt,2);
                    }
                }
            },{
                title : '差异(手续费)',
                field : 'differentFee',
                align : 'center',
                width : 120,
                formatter:function (value,row,index) {
                    if(row.technologyFee!=null&&row.clearFee!=null){
                        Math.formatFloat = function(f, digit) {
                            var m = Math.pow(10, digit);
                            return parseInt(f * m, 10) / m;
                        }
                        return Math.formatFloat(row.technologyFee-row.clearFee,2);
                    }
                }
            }]]
        });
    });

    function searchTranCheckData() {
        tranCheckDataList.datagrid('load', $.serializeObject($('#tranCheckForm')));
    }

    function cleanTranCheckData() {
        $('#tranCheckForm input').val('');
        $("#platFormType").val("00");
        searchTranCheckData();
    }
    function importData (type) {
        $.ajaxL({
            type: "POST",
            url: '${path }/profit/tran/check/importData?type='+type,
            dataType:'json',
            traditional:true,
            contentType:"application/x-www-form-urlencoded",
            beforeSend : function() {
                progressLoad();
            },
            success: function(msg){
                if (msg.success) {
                    alertMsg( msg.msg);
                }else {
                    alertMsg("系统异常，请联系运维人员！");
                }
            },
            complete:function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }
    function alertMsg(msg) {
        parent.$.messager.alert('提示',msg, 'info');
    }
    function synchronizeTranCheckData() {
        $.ajaxL({
            type: "POST",
            url: '${path }/profit/tran/check/synchronizeTranCheckData',
            dataType:'json',
            traditional:true,
            contentType:"application/x-www-form-urlencoded",
            beforeSend : function() {
                progressLoad();
            },
            success: function(msg){
                alertMsg( msg.msg);
            },
            complete:function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="tranCheckList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form method="post"id ="tranCheckForm"  >
            <table>
                <tr>
                    <td>分润月份:</td>
                    <td><input id="tranMonth" name="tranMonth" /></td>
                    <td>业务平台:</td>
                    <td>
                        <select name="platFormType" id="platFormType">
                            <option value="00">请选择</option>
                            <c:forEach items="${platFormTypes}" var="temp">
                                <option value="${temp.num}">${temp.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchTranCheckData();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanTranCheckData();">清空</a>
                    </td>
                    <td>
                        <c:if test="${noEdit==0}">
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="importData(2);">1POS分润数据重新同步</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="importData(4);">2手刷日结分润重新同步</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="importData(5);">3手刷直发平台重新同步</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="importData(6);">4手刷补差数据重新同步</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="importData(3);">5手刷月结分润重新同步</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="importData(7);">6手刷月汇总重算</a>
                            <a href="javascript:void(0);" class="easyui-linkbutton"
                               data-options="iconCls:'icon-print',plain:true" onclick="synchronizeTranCheckData();">7交易量重新同步</a>
                        </c:if>
                    </td>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>


