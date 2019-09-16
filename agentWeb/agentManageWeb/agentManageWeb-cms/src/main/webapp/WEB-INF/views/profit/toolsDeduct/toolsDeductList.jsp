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
    $('#deductionDateStart').datebox({
        required:true
    });
    $('#deductionDateEnd').datebox({
        required:true
    });

    $('#deductionDateStart,#deductionDateEnd').datebox({
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
    $('#deductionDateStart').datebox('setValue',year_month);
    $('#deductionDateEnd').datebox('setValue',year_month);




    var start = $('#deductionDateStart').datebox('getValue');
    var end = $('#deductionDateEnd').datebox('getValue');


    var toolsDeductListGrid;
    $(function() {
        toolsDeductListGrid = $('#toolsDeductList').datagrid({
            url : '${path }/toolsDeduct/list',
            striped : true,
            queryParams:{'deductionDateStart':start ,'deductionDateEnd':end},
            pagination : true,
            rownumbers : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100],
            columns : [ [{
                title : '扣款月份',
                field : 'deductionDate',
                align:'center',
                width:150
            },{
                title : '代理商名称',
                field : 'agentName',
                align:'center',
                width:250
            },{
                title : '代理商唯一码',
                field : 'agentPid',
                align:'center',
                width:250
            },{
                title : '上级代理商名称',
                field : 'parentAgentName',
                align:'center',
                width:250
            },{
                title : '上级代理商唯一码',
                field : 'parentAgentId',
                align:'center',
                width:250
            },{
                title : '机具类型',
                field : 'deductionDesc',
                align:'center',
                width:150,
                formatter : function(value, row, index) {
                    for(var i=0;i< db_options.ablePlatForm.length;i++){
                        if (db_options.ablePlatForm[i].platformNum == value) {
                            var temp = db_options.ablePlatForm[i].platformName;
                            return temp;
                        }
                    }
                }
            },{
                title : '本月应扣',
                field : 'mustDeductionAmt',
                align:'center',
                width:150
            },{
                title : '本月实扣',
                field : 'actualDeductionAmt',
                align:'center',
                width:150
            },{
                title : '未扣足',
                field : 'notDeductionAmt',
                align:'center',
                width:150
            },{
                title : '关联代理商扣款',
                field : 'rev1',
                align : 'center',
                width : 150,
                formatter : function (value, row, index) {
                    if(value==null){
                        return '0';
                    }
                }
            },{
                title : '线下补款',
                field : 'rev2',
                align : 'center',
                width : 150,
                formatter : function (value, row, index) {
                    if(value==null){
                        return '0';
                    }
                }
            },{
                title : '上级代扣',
                field : 'rev3',
                align : 'center',
                width : 150,
                formatter : function (value, row, index) {
                    if(value==null){
                        return '0';
                    }
                }
            },{
                title : '审批状态',
                field : 'stagingStatus',
                align:'center',
                width:150,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "0":
                            return '未扣款';
                        case "1":
                            return '未审核';
                        case "2":
                            return '审批中';
                        case "3":
                            return '审批通过，未扣款';
                        case "4":
                            return '审批未通过，正常扣款';
                        case "5":
                            return '已扣款';
                        case "00":
                            return '省区补款审批中';
                        case "01":
                            return '省区补款审批失败';
                        case "02":
                            return '省区补款审批成功';
                    }
                }
            }, {
                field : 'action',
                title : '操作',
                width : 500,
                formatter : function(value, row, index) {
                    var str = '';
                   <shiro:hasPermission name="tools_deductions_adjust">
                    if(row.stagingStatus == '0'|| row.stagingStatus == '1'|| row.stagingStatus == '4'){
                        str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-edit-tools" data-options="plain:true,iconCls:\'fi-social-myspace icon-red\'" onclick="applyAdjustment(\'{0}\');" >申请扣款调整</a>', row.id);
                        str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-edit-tools" data-options="plain:true,iconCls:\'fi-social-myspace icon-blue\'" onclick="applyRecoupAndDeduct(\'{0}\',\'{1}\');" >补款</a>', row.agentPid,row.deductionDate);
                        str += "&nbsp;&nbsp;&nbsp;&nbsp"
                    }
                    </shiro:hasPermission>
                    if((row.stagingStatus == '3'|| row.stagingStatus == '5')&& row.sumDeductionAmt > row.mustDeductionAmt){
                        if(row.sumDeductionAmt != row.mustDeductionAmt){
                            str += $.formatString('<a href="javascript:void(0)"  class="easyui-linkbutton-add-tools" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="queryDetail(\'{0}\');" >调整部分扣款明细</a>', row.id);
                        }
                    }
                   /* if(row.stagingStatus == '5' ){
                        str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-deduct-tools" data-options="plain:true,iconCls:\'fi-magnifying-glass\'"  onclick="deductDetail(\'{0}\');">扣款明细</a>', row.id);
                        str += "&nbsp;&nbsp;&nbsp;&nbsp"
                    }*/
                    if(row.stagingStatus == '2'|| row.stagingStatus == '3'|| row.stagingStatus == '4'){
                        str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-query-tools" data-options="plain:true,iconCls:\'fi-magnifying-glass\'"  onclick="showActivity(\'{0}\');">查看审批进度</a>', row.id);
                        str += "&nbsp;&nbsp;&nbsp;&nbsp"
                    }
                    if(row.stagingStatus == '5' && row.sumDeductionAmt > row.mustDeductionAmt){
                        str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-query-tools" data-options="plain:true,iconCls:\'fi-magnifying-glass\'"  onclick="showActivity(\'{0}\');">查看审批进度</a>', row.id);
                        str += "&nbsp;&nbsp;&nbsp;&nbsp"
                    }
                    //if(row.stagingStatus == '5') {
                        str += $.formatString('<a href="javascript:void(0)"  class="easyui-linkbutton-detail" data-options="plain:true,iconCls:\'icon-search\'"  onclick="showRevDetail(\'{0}\');">关联扣款明细</a>', row.id );
                   // }
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.activity-easyui-linkbutton-edit-tools').linkbutton({});
                $('.easyui-linkbutton-query-tools').linkbutton({text:'查看审批进度'});
                $('.easyui-linkbutton-add-tools').linkbutton({text:'调整部分扣款明细'});
                $('.easyui-linkbutton-detail').linkbutton({text:'关联扣款明细'});

            }
        });
    });

    //查看关联代理商扣款明细（别人替其扣）
    function showRevDetail(id){
        addTab({
            title : '关联扣款明细-'+id,
            border : false,
            closable : true,
            fit : true,
            href : '${path }/toolsDeduct/toRevpage?id='+id
        });
    }

    function showActivity(id) {
        addTab({
            title : '机具扣款申请调整审批进度',
            border : false,
            closable : true,
            fit : true,
            href : '/toolsActivity/toApplyDetail?id='+id
        });
    }

    function applyAdjustment(id) {
        parent.$.modalDialog({
            title : '申请调整',
            width : 400,
            height : 420,
            href : '${path }/toolsDeduct/editPage?id='+id,
            buttons : [ {
                text : '申请',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = toolsDeductListGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#toolsDeductEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function applyRecoupAndDeduct(agentPid,deductionDate) {
        addTab({
            title : '线下补款/上级代扣申请-'+agentPid,
            border : false,
            closable : true,
            fit : true,
            href : '${path }/toolsDeduct/toRecoupAndDeduct?agentId='+agentPid+'&deductionDate='+deductionDate
        });
    }

    function queryDetail(id) {
        parent.$.modalDialog({
            title : '调整部分下月扣款明细',
            width : 400,
            height : 200,
            href : '${path }/toolsDeduct/detail/page?id='+id
        });
    }

    function deductDetail(id) {
        parent.$.modalDialog({
            title : '扣款明细',
            width : 1200,
            height : 400,
            href : '${path }/toolsDeduct/deduct/detail?id='+id
        });
    }

    function searchToolsData() {
        toolsDeductListGrid.datagrid('load', $.serializeObject($('#toolsDeductForm')));
    }
    function cleanToolsData() {
        $('#toolsDeductForm input').val('');
        $('#toolsDeductForm select').val('');
        toolsDeductListGrid.datagrid('load', {});
    }

    //展示机具类型
    function displayPlatForm(){
        var str = "<option value=''>--请选择--</option>";
        for(var i=0;i< db_options.ablePlatForm.length;i++){
            str += "<option value="+db_options.ablePlatForm[i].platformNum+">"+db_options.ablePlatForm[i].platformName+"</option>";
        }
        $("#deductionDesc").html(str);
    }

    displayPlatForm();

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="toolsDeductList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id ="toolsDeductForm" method="post">
            <table>
                <tr>
                    <th>代理商名称:</th>
                    <td><input id="agName" name="agentName" type="text" class="easyui-textbox" style="width:140px;"></td>
                    <th>代理商唯一码:</th>
                    <td><input id="agentId" name="agentId" type="text" class="easyui-textbox" value="" ></td>
                    <th>机具类型:</th>
                    <td>
                        <select id="deductionDesc" name="deductionDesc" style="width:140px;"></select>
                    </td>
                    <th>月份:</th>
                    <td><input id ="deductionDateStart" name="deductionDateStart" style="width: 140px">-</td>
                    <td><input id="deductionDateEnd" name="deductionDateEnd"  style="width: 140px"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchToolsData();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanToolsData();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

