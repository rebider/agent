<%--
  Created by IntelliJ IDEA.
  User: renshenghao
  Date: 2019/4/22
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script>

   /* var agentMap={};*/
    var agentMap = new Array();
    var freezeAgentList;
    var statusTemp=$("#statusTemp").val();
    $(function () {
        console.info(statusTemp);
        freezeAgentList=$("#freezeAgentList").datagrid({
            url : '${path }/agentFreeze/getFreezeList',
            striped : true,
            pagination : true,
            rownumbers : true,
            queryParams : {"statusTemp":statusTemp,"isQuerySubordinate":false},
            fit : true,
            pageSize : 50,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [[
                {
                    field : 'id',
                    title : 'id',
                    checkbox : true,
                },{
                    title : '代理商唯一码',
                    field : 'agentId',
                    align : 'center',
                    width : 200
                },{
                    title : '代理商名称',
                    field : 'agentName',
                    align : 'center',
                    width : 200
                },{
                    title : '上级代理商唯一码',
                    field : 'parentAgentId',
                    align : 'center',
                    width : 200
                },{
                    title : '上级代理商名称',
                    field : 'parentAgentName',
                    align : 'center',
                    width : 200
                },{
                    title : '冻结类型',
                    field : 'freezeType',
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
                    title : '冻结金额',
                    field : 'rev2',
                    align : 'center',
                    width : 120,
                    formatter : function (value,row,index) {
                        if(value==null){
                            return 0 ;
                        }else {
                            return value;
                        }
                    }
                },{
                    title : '当前状态',
                    field : 'status',
                    align : 'center',
                    width : 200,
                    formatter : function (value,row,index) {
                        if (value=="0"){
                            return "正常";
                        } else if (value=="1"){
                            return "已冻结";
                        } else{
                            return "";
                        }
                    }
                }
            ]],
            onUncheck: function(index, row){//在用户取消勾选一行的时候触发
                onUncheck(index, row);
            },
            onCheck: function(index, row){//勾选一行的时候触发
                onCheck(index,row);
            },
            onCheckAll: function(rows){//选中所有行触发
                onCheckAll(rows);
            },
            onUncheckAll: function(rows){//取消所有行格触发
                onUncheckAll(rows);
            },
            onLoadSuccess: function(data){//加载数据成功,也就是刷新页面或者点击前页后页的时候触发
                onCheckPage(data);
            }
        });
    });

    //点击选中单元格设置用户ID组
    function onCheck(index, row){
        var obj=new Object();
        obj["agentId"]=row.agentId;
        obj["agentName"]=row.agentName;
        obj["parentAgentId"]=row.parentAgentId;
        obj["parentAgentName"]=row.parentAgentName;
        obj["freezeAmt"]=row.rev2;
        obj["freezeType"]=row.freezeType;
        // agentMap[row.agentId+row.parentAgentId+row.freezeType]=obj
            agentMap.push(obj);
        console.info(agentMap);
    }
    //点击取消单元格设置用户ID组
    function onUncheck(index, row){
      /*  var obj=new Object();
        obj["agentId"]=row.agentId;
        obj["agentName"]=row.agentName;
        obj["parentAgentId"]=row.parentAgentId;
        obj["parentAgentName"]=row.parentAgentName;
        obj["freezeAmt"]=row.freezeAmt;
        obj["freezeType"]=row.freezeType;
       // delete agentMap[row.agentId+row.parentAgentId+row.freezeType];*/
        for (var i = 0; i < agentMap.length; i ++) {
           var objAgentMap = agentMap[i];
           if(objAgentMap.agentId==row.agentId&&objAgentMap.parentAgentId==row.parentAgentId&&objAgentMap.freezeType==row.freezeType){
               agentMap.splice(i,1);
           }
        };

        console.info(agentMap);
    }
    //点击全取消设置用户ID组
    function onUncheckAll(rows){
        agentMap=[];
       /* var num=rows.length;
        for (var i = 0; i < num; i ++) {
            var row=rows[i];
            delete agentMap[row.agentId+row.parentAgentId+row.freezeType];
        };*/
        console.info(agentMap);
    }
    ////点击全选设置用户ID组
    function onCheckAll(rows){
        var num=rows.length;
        for(var j = 0;j<agentMap.length;j++){
            var agent =agentMap[j];
            for (var i = 0; i < num; i ++) {
                var row = rows[i];
                if(row.agentId==agent.agentId&&row.parentAgentId==agent.parentAgentId&&row.freezeType==agent.freezeType){
                    agentMap.splice(j,1);
                    j--;
                    break;
                }
            }
        }
            for (var i = 0; i < num; i ++) {
                var row=rows[i];
                var obj=new Object();
                obj["agentId"]=row.agentId;
                obj["agentName"]=row.agentName;
                obj["parentAgentId"]=row.parentAgentId;
                obj["parentAgentName"]=row.parentAgentName;
                obj["freezeAmt"]=row.rev2;
                obj["freezeType"]=row.freezeType;
                agentMap.push(obj);
                /* agentMap[row.agentId+row.parentAgentId+row.freezeType]=obj*/
            };


        console.info(agentMap);
    }
    //用户点击翻页的时候，设置勾选
    function onCheckPage(data){
        var rows = data.rows;
        var num = agentMap.length;
        for (var i = 0; i < agentMap.length; i++) {
            for (var j = 0; j < rows.length; j++) {
               var agent=agentMap[i];  var row=rows[j];
                if(row.agentId==agent.agentId&&row.parentAgentId==agent.parentAgentId&&row.freezeType==agent.freezeType){
                    var index = freezeAgentList.datagrid('getRowIndex',row);
                    freezeAgentList.datagrid('selectRow', index); //根据id选中行
                    agentMap.splice(num,1);
                    break;
                }
            }
        }
    }

  /*  function getArrCheckKey(){
        var arrCheckKey=[];//角色用户数组
        for(var key in agentMap){
            arrCheckKey.push(key)
        }
        return arrCheckKey;
    }*/

    function FreezeApplyDFor(temp){
        if (temp==1){
            $("#DialogFreezeDiv").show();
            $("#DialogFreezeDiv").dialog({
                title : '分润冻结',
                width : 400,
                height : 200,
                closed : false,
                closable: true,
                cache : false,
                modal : true
            });
        }else{
            $("#DialogThawDiv").show();
            $("#DialogThawDiv").dialog({
                title : '解冻申请',
                width : 400,
                height : 200,
                closed : false,
                closable: true,
                cache : false,
                modal : true
            });
        }
    }

    function closes(temp){
        console.info("取消");
        if (temp==1){
            $("#DialogFreezeDiv").dialog('close');
            $("#DialogFreezeDiv").hide();
            $("#DialogFreeze").val('');
        } else{
            $("#DialogThawDiv").dialog('close');
            $("#DialogThawDiv").hide();
            $("#DialogThaw").val('');
        }
    }
    function submitRemark(){
        var dialogFreeze = $("#DialogFreeze").val();

        if (''==dialogFreeze){
            $.messager.alert("提示框",'请输入冻结原因');
            return;
        }
        for (var i = 0; i < agentMap.length; i ++) {
          var obj = agentMap[i]
            obj["freezeReason"]=dialogFreeze;
        };
        $("#DialogFreezeDiv").dialog('close');
        $.ajax({
            type: "post",
            url: '${path }/agentFreeze/getFreezeDate',
            data: JSON.stringify(agentMap),
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            success: function (data) {
                info(data.msg);

                $("#DialogFreezeDiv").hide();
                $("#DialogFreeze").val('');
                freezeAgentList.datagrid('reload');
            }
        });
    }

    //查询
    function searchFreezeAgent(){
        agentMap=new Array();
        if ($("#isQuerySubordinate").combobox('getValue')=="true"){
            if($("#agentId").textbox('getValue')==""&&$("#agentName").textbox('getValue')==""){
                parent.$.messager.alert('提示', '请填写查询条件以显示其下级！', 'error');
                return ;
            }
        }
        freezeAgentList.datagrid('load', $.serializeObject($('#searchFreezeForm')));
    }
    //清空
    function cleanFreezeForm(){
        agentMap=new Array();
        $("#agentName").textbox('setValue','');
        $("#agentId").textbox('setValue','');
        $("#parentAgentName").textbox('setValue','');
        $("#parentAgentId").textbox('setValue','');
        $("#statusTemp").combobox('setValue',statusTemp);
        $('#isQuerySubordinate').combobox('setValue','false');
        freezeAgentList.datagrid('load', $.serializeObject($('#searchFreezeForm')));
    }
    //批量解冻
    function thawAgent(){
        var temp=$("#statusTemp").combobox('getValue');
        if(temp!='1'){
            parent.$.messager.alert('提示', '正常代理商无需解冻！', 'error');
            return ;
        }
        var length=agentMap.length;
        if (length<1){
            parent.$.messager.alert('提示', '请选择要解冻的代理商！', 'error');
            return ;
        }
        FreezeApplyDFor(2);
    }
    function doThawAgent() {
        var DialogThaw = $("#DialogThaw").val();
        console.info(DialogThaw);
        if (''==DialogThaw){
            $.messager.alert("提示框",'请输入解冻原因');
            return;
        }
        for (var i = 0; i < agentMap.length; i ++) {
            var obj = agentMap[i]
            obj["thawReason"]=DialogThaw;
        };
        parent.$.messager.confirm('询问', '确认解冻？', function(b) {
            if (b){
                $.ajax({
                    url:"${path }/agentFreeze/thawAgent",
                    type: 'post',
                    data:JSON.stringify(agentMap),
                    dataType: "json",
                    contentType: 'application/json;charset=utf-8',
                    success:function (msg) {
                        info(msg.resInfo);
                        closes(2);
                        cleanFreezeForm();
                    }
                });
            }
        });
    }

</script>
<div id="DialogFreezeDiv" style="display: none">
    <table>
        <tr>
            <th>冻结原因:</th>
            <td><textarea id="DialogFreeze" name="DialogFreeze" style="width:280px ; height:100px ;" > </textarea></td>
        </tr>

        <tr>
            <td style="color:red">*此为必填项*</td>
            <th align="right">
                <a href="javascript:void(0);" onclick="submitRemark()"
                 class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0);" onclick="closes(1)"
                   class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
            </th>
        </tr>
</table>
</div>

<div id="DialogThawDiv" style="display: none">
    <table>
        <tr>
            <th>解冻原因:</th>
            <td><textarea id="DialogThaw" name="DialogThaw" style="width:280px ; height:100px ;" > </textarea></td>
        </tr>

        <tr>
            <td>*此为必填项*</td>
            <th align="right">
                <a href="javascript:void(0);" onclick="doThawAgent()"
                 class="easyui-linkbutton" data-options="iconCls:'icon-save'">提交</a>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="javascript:void(0);" onclick="closes(2)"
                   class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">取消</a>
            </th>
        </tr>
    </table>
</div>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="TABLE" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="freezeAgentList" data-options="fit:true,border:false"></table>
    </div>
    <div id="formList" data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form  method="post" id ="searchFreezeForm" >
            <table>
                <tr>
                    <td>代理商名称</td>
                    <td><input id="agentName" name="agentName" type="text" class="easyui-textbox" style="width:200px;"></td>
                    <td>代理商唯一码</td>
                    <td><input id="agentId" name="agentId" type="text" class="easyui-textbox" style="width:200px;"></td>
                    <td>当前状态</td>
                    <td>
                        <select name="statusTemp" id="statusTemp" class="easyui-combobox" style="width:200px;">
                            <shiro:hasPermission name="/agentFreeze/approvalFreeze">
                                <option value="0">正常</option>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/agentFreeze/approvalThaw">
                                <option value="1">已冻结</option>
                            </shiro:hasPermission>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchFreezeAgent();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanFreezeForm();">清空</a>
                    </td>
                </tr>
                <tr>
                    <td>上级代理商名称</td>
                    <td><input id="parentAgentName" name="parentAgentName" type="text" class="easyui-textbox" style="width:200px;"></td>
                    <td>上级代理商唯一码</td>
                    <td><input id="parentAgentId" name="parentAgentId" type="text" class="easyui-textbox" style="width:200px;"></td>
                    <td>是否包含下级</td>
                    <td>
                        <select name="isQuerySubordinate" id="isQuerySubordinate" class="easyui-combobox" style="width:200px;">
                            <option value="false">否</option>
                            <option value="true">是</option>
                        </select>
                    </td>
                    <td>
                        <shiro:hasPermission name="/agentFreeze/approvalFreeze">
                           <%-- <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="FreezeApplyDFor(1);">冻结申请</a>--%>
                            <input type="button" value="冻结申请" onclick="FreezeApplyDFor(1);">
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/agentFreeze/approvalThaw">
                           <%-- <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-social-rdio',plain:true" onclick="thawAgent();">解冻申请</a>--%>
                            <input type="button" value="解冻申请" onclick="thawAgent();">
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
