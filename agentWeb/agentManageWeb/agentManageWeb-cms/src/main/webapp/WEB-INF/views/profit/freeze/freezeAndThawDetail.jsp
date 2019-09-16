
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script>
    var freezeAgentListAA;
    $(function () {
        freezeAgentListAA=$("#freezeAgentDetailList").datagrid({
            url : '${path }/agentFreeze/getFreezeDetailList',
            striped : true,
            rownumbers : true,
            pagination : true,
            fit : true,
            idField : 'id',
            pageSize : 50,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [[
                {
                    title: 'id',
                    field: 'id',
                    hidden: true
                },{
                    title : '代理商唯一码',
                    field : 'AGENT_ID',
                    align : 'center',
                    width : 200
                },{
                    title : '代理商名称',
                    field : 'AGENT_NAME',
                    align : 'center',
                    width : 200
                },{
                    title : '上级代理商唯一码',
                    field : 'PARENT_AGENT_ID',
                    align : 'center',
                    width : 200
                },{
                    title : '上级代理商名称',
                    field : 'PARENT_AGENT_NAME',
                    align : 'center',
                    width : 200
                },{
                    title : '冻结类型',
                    field : 'FREEZE_TYPE',
                    align : 'center',
                    width : '200',
                    formatter : function (value,row,index) {
                        if (value=="00"){
                            return "月份润";
                        } else if (value=="01"){
                            return "日分润";
                        } else if (value=="02"){
                            return "日返现";
                        } else{
                            return "";
                        }
                    }
                },{
                    title : '当前状态',
                    field : 'STATUS',
                    align : 'center',
                    width : 200,
                    formatter : function (value,row,index) {
                        if (value=="0"){
                            return "已解冻";
                        } else if (value=="1"){
                            return "已冻结";
                        }else{
                            return "解冻申请中";
                        }
                    }
                },{
                    title : '冻结金额',
                    field : 'FREEZE_AMT',
                    align : 'center',
                    width : 120
                },{
                    title : '解冻金额',
                    field : 'RELIEVE_AMT',
                    align : 'center',
                    width : 120
                },{
                    title : '冻结时间',
                    field : 'FREEZE_TIME',
                    align : 'center',
                    width : 120
                },{
                    title : '冻结理由',
                    field : 'FREEZE_REASON',
                    align : 'center',
                    width : 120
                },{
                    title : '解冻时间',
                    field : 'RELIEVE_TIME',
                    align : 'center',
                    width : 120
                },{
                    title : '解冻理由',
                    field : 'RELIEVE_REASON',
                    align : 'center',
                    width : 120
                },{
                    title : '冻结批次号',
                    field : 'FREEZE_OPERATION_BATCH',
                    align : 'center',
                    width : 200,
                    hidden: true
                },{
                    title : '解冻批次号',
                    field : 'RELIEVE_OPERATION_BATCH',
                    align : 'center',
                    width : 200,
                    hidden: true
                },{
                    title : '操作',
                    field : 'action',
                    align : 'center',
                    width : 240,
                    formatter: function(value,row,index){
                        var str = '';
                        str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-add"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="checkHistory(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\')">查看历史</a>',row.FREEZE_OPERATION_BATCH,row.RELIEVE_OPERATION_BATCH,row.AGENT_ID,row.PARENT_AGENT_ID,row.FREEZE_TYPE);
                        if (row.RELIEVE_OPERATION_BATCH){
                            str += $.formatString('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="batch-easyui-linkbutton-add"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="checkActivate(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\')">查看审批流</a>',row.FREEZE_OPERATION_BATCH,row.RELIEVE_OPERATION_BATCH,row.AGENT_ID,row.PARENT_AGENT_ID,row.FREEZE_TYPE);
                        }
                        return str;
                    }


                    }
            ]]
        });
    });

    function  searchIsNoFreeze(){
        if("true"==$("#isNo").combobox("getValue")){
            var AGENT_NAME = $("#AGENT_NAME").textbox("getValue");
            var AGENT_ID = $("#AGENT_ID").textbox("getValue");
            if((""==AGENT_NAME || AGENT_NAME == null)&&(""==AGENT_ID || AGENT_ID == null)){
                $.messager.alert("提示框","若要查询下级请输入代理商名称或者唯一码");
                return;
            }
        }
        freezeAgentListAA.datagrid('load', $.serializeObject($("#searchDetailFreezeForm")));
    }
    function   cleanFreezeDetail(){
        $("#AGENT_NAME").textbox('setValue','');
        $("#AGENT_ID").textbox('setValue','');
        $("#FREEZE_TYPE").combobox('setValue','');
        $("#PARENT_AGENT_NAME").textbox('setValue','');
        $("#PARENT_AGENT_ID").textbox('setValue','');
        $("#isNo").combobox('setValue','false');
        $("#STATUS").combobox('setValue','');
    }

    function checkHistory(FREEZE_OPERATION_BATCH,RELIEVE_OPERATION_BATCH,AGENT_ID,PARENT_AGENT_ID,FREEZE_TYPE){

        parent.$.modalDialog({
            title: '冻结历史查询',
            width: 1200,
            height: 400,
            closed: false,
            cache: false,
            href: '${path}/agentFreeze/getCheckHistory?FREEZE_OPERATION_BATCH='+FREEZE_OPERATION_BATCH+"&RELIEVE_OPERATION_BATCH="+RELIEVE_OPERATION_BATCH+"&AGENT_ID="+AGENT_ID+"&PARENT_AGENT_ID="+PARENT_AGENT_ID+"&FREEZE_TYPE="+FREEZE_TYPE,
            modal: true,
            resizable:"true"
        });

        <%--$.ajax({--%>
            <%--type: "post",--%>
            <%--url: '${path }/agentFreeze/getCheckHistory',--%>
            <%--data: {"FREEZE_OPERATION_BATCH":FREEZE_OPERATION_BATCH,"RELIEVE_OPERATION_BATCH":RELIEVE_OPERATION_BATCH,"AGENT_ID":AGENT_ID,"PARENT_AGENT_ID":PARENT_AGENT_ID,"FREEZE_TYPE":FREEZE_TYPE},--%>
            <%--dataType: "json",--%>
            <%--contentType: 'application/json;charset=utf-8',--%>
            <%--success: function (data) {--%>
                <%--info(data.msg);--%>
                <%--$("#DialogFreezeDiv").dialog('close');--%>
                <%--$("#DialogFreezeDiv").hide();--%>
                <%--$("#DialogFreeze").val('');--%>
                <%--freezeAgentList.datagrid('reload');--%>
            <%--}--%>
        <%--});--%>
    }

    function checkActivate(FREEZE_OPERATION_BATCH,RELIEVE_OPERATION_BATCH,AGENT_ID,PARENT_AGENT_ID,FREEZE_TYPE){
        addTab({
            title : '奖励考核申请审批进度',
            border : false,
            closable : true,
            fit : true,
            href : '/agentFreeze/examineDetail?busId='+RELIEVE_OPERATION_BATCH+'&busType=thawAgentByCity'
        });

    }
</script>
<%--<a href="javascript:void(0)" class="batch-easyui-linkbutton-add"  data-options="plain:true,iconCls:'fi-check icon-green'" onclick="checkHistory()" >查看历史</a>--%>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="TABLE" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="freezeAgentDetailList" data-options="fit:true,border:false"></table>
    </div>
    <div id="formList" data-options="region:'north',border:false" style="height:90px; overflow: hidden;background-color: #fff">
        <form  method="post" id ="searchDetailFreezeForm" >
            <table>
                <tr>
                    <td>代理商名称</td>
                    <td><input id="AGENT_NAME" name="AGENT_NAME" type="text" class="easyui-textbox" style="width:200px;"></td>
                    <td>代理商唯一码</td>
                    <td><input id="AGENT_ID" name="AGENT_ID" type="text" class="easyui-textbox" style="width:200px;"></td>
                    <td>冻结类型</td>
                    <td>
                        <select name="FREEZE_TYPE" id="FREEZE_TYPE" class="easyui-combobox" style="width:200px;">
                            <option value="">-----------请选择---------</option>
                                <option value="00">月份润</option>
                                <option value="01">日分润</option>
                                <option value="02">日返现</option>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchIsNoFreeze();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanFreezeDetail();">清空</a>
                    </td>
                </tr>
                <tr>
                    <td>上级代理商名称</td>
                    <td><input id="PARENT_AGENT_NAME" name="PARENT_AGENT_NAME" type="text" class="easyui-textbox" style="width:200px;"></td>
                    <td>上级代理商唯一码</td>
                    <td><input id="PARENT_AGENT_ID" name="PARENT_AGENT_ID" type="text" class="easyui-textbox" style="width:200px;"></td>
                    <td>是否包含下级</td>
                    <td>
                        <select name="isNo" id="isNo" class="easyui-combobox" style="width:200px;">
                            <option value="false">否</option>
                            <option value="true">是</option>
                        </select>
                    </td>
                </tr>
                <tr>

                    <td>当前状态</td>
                    <td>
                        <select name="STATUS" id="STATUS" class="easyui-combobox" style="width:200px;">
                            <option value="">-----------请选择---------</option>
                            <option value="1">已冻结</option>
                            <option value="0">已解冻</option>
                            <option value="2">解冻中</option>
                        </select>
                    </td>

                </tr>
            </table>
        </form>
    </div>
</div>