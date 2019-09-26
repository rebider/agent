<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="profitByFinance_list_ConditionToolbar" style="display: none;">
    <form  method="post" action="" id ="profitByFinance_list_ConditionToolbar_searchform" >
        <table>
            <tr>
                <th>代理商名称</th>
                <td><input id="agentName" name="agentName" type="text" class="easyui-textbox"  value="" style="width:180px;"></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <th>代理商唯一编码</th>
                <td><input id="agentId" name="agentId" type="text" class="easyui-textbox"  value="" style="width:180px;"></td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <th>月份:</th>
                <td><input id="profitDateStart" name="profitDateStart"></td><th>-</th>
                <td><input id="profitDateEnd" name="profitDateEnd"></td>
                <td><input type="checkbox" name="chekbox" id="chekbox" value="1" />是否查看下级</td>
                <td>&nbsp;&nbsp;&nbsp;</td>
                <td>
                    <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchprofitByFinance_list()">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgentListSearchForm();">清空</a>
                    <shiro:hasPermission name="/profit/profitByFinance/final">
                        <a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="profitCount()">统计</a>
                    </shiro:hasPermission>
                <td>&nbsp;&nbsp;&nbsp;</td>
                    <shiro:hasPermission name="/profit/profitByFinance/final">
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-check',plain:true" onclick="initPosRewordDeatil();">核算POS奖励</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/profit/profitByFinance/final">
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-check',plain:true" onclick="commitFirst();">初审</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/profit/profitByFinance/final">
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-check',plain:true" onclick="commitFinal();">终审</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/profit/profitByFinance/paymoney">
                        <a href="javascript:void(0);" class="easyui-linkbutton"
                           data-options="iconCls:'fi-check',plain:true" onclick="payMoney();">出款</a>
                    </shiro:hasPermission>&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-check',plain:true"
                       onclick="exportByFinance();">导出数据</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="profitByFinance_list_ConditionDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var profitByFinance_list_ConditionDataGrid;
    $(function() {
        //代理商表格
        profitByFinance_list_ConditionDataGrid = $('#profitByFinance_list_ConditionDataGrid').datagrid({
            url : '${path}/profit/profitFList',
            rownumbers : true,
            striped : true,
            pagination : true,
            iconCls:'icon-edit',
            singleSelect : true,
            editors:$.fn.datagrid.defaults.editors,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title: 'id',
                field: 'ID',
                hidden: true
            },{
                    title : '分润月份',
                    field : 'PROFIT_DATE',
                    align : 'center'

        },{
                title : '代理商唯一编码',
                field : 'AGENT_ID',
                align : 'center'
            },{
                title : '代理商名称',
                field : 'AGENT_NAME',
                align : 'center'
            },{
                title : '上级代理商唯一码',
                field : 'PARENT_AGENT_ID',
                align : 'center'
            },{
                title : '上级代理商名称',
                field : 'PARENT_AGENT_NAME',
                align : 'center'
            },{
                title : '实发分润',
                field : 'REAL_PROFIT_AMT',
                align : 'center'
            },{
                title : '基础分润合计',
                field : 'PROFIT_SUM_AMT',
                align : 'center',
                styler : function(value,row,index){
                    return 'background-color:#ADD8E6;';
                }
            },{
                title : 'POS分润',
                field : 'SUM_POS',
                align : 'center'
            },{
                title : 'POS直签补差分润',
                field : 'POS_ZQ_SUPPLY_PROFIT_AMT',
                align : 'center'
            },{
                title : 'POS奖励',
                field : 'POS_REWARD_AMT',
                align : 'center'
            },{
                title : '手刷分润',
                field : 'SUM_ZF',
                align : 'center'
            },{
                title : '手刷直签补差分润',
                field : 'MPOS_ZQ_SUPPLY_PROFIT_AMT',
                align : 'center'
            },{
                title : '分润补款合计',
                field : 'PROFITSUPPLYSUM',
                align : 'center',
                styler : function(value,row,index){
                    return 'background-color:#ADD8E6;';
                }
            },{
                title : 'POS退单补款',
                field : 'POS_TD_SUPPLY_AMT',
                align : 'center'
            },{
                title : '手刷退单补款',
                field : 'MPOS_TD_SUPPLY_AMT',
                align : 'center'
            },{
                title : '机具返现',
                field : 'TOOLS_RETURN_AMT',
                align : 'center'
            },{
                title : '其他补款',
                field : 'OTHER_SUPPLY_AMT',
                align : 'center'
            },{
                title : '分润扣款合计',
                field : 'PROFITCHARGESUM',
                align : 'center',
                styler : function(value,row,index){
                    return 'background-color:#ADD8E6;';
                }
            },{
                title : 'POS退单实扣款',
                field : 'POS_TD_REAL_DEDUCTION_AMT',
                align : 'center'
            },{
                title : '手刷退单实扣',
                field : 'MPOS_TD_REAL_DEDUCTION_AMT',
                align : 'center'
            },{
                title : '考核扣款',
                field : 'KHDEDUCTIONAMT',
                align : 'center'
            },{
                title : '智能POS预发分润扣款',
                field : 'ZNPOS_PROFIT_AMT',
                align : 'center'
            },{
                title : '机具扣款',
                field : 'TOOLSHARGEAMT',
                align : 'center'
            },{
                title : '保理扣款',
                field : 'BU_DEDUCTION_AMT',
                align : 'center'
            },{
                title : '服务费',
                field : 'SERVER_AMT_SUM',
                align : 'center'
            },{
                title : '其它扣款',
                field : 'OTHER_DEDUCTION_AMT',
                align : 'center'
            },{
                title : '涉税事项前应发分润汇总',
                field : 'SSSXQYFFRHZ',
                align : 'center'
            },{
                title : '税款扣除',
                field : 'TAXPAYDEDUCT',
                align : 'center'
            },{
                title : '补税点差',
                field : 'SUPPLY_TAX_AMT',
                align : 'center'
            },{
                title : '收款户名',
                field : 'ACCOUNT_NAME',
                align : 'center'
            },{
                title : '打款公司',
                field : 'accountName',
                align : 'center',
                hidden : true
            },{
                title : '分润状态',
                field : 'STATUS',
                align : 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "0":
                            return '正常';
                        case "1":
                            return '冻结';
                        case "2":
                            return '解冻审批中';
                        case "3":
                            return '解冻审批失败,未分润';
                        case "4":
                            return '未分润';
                        case "5":
                            return '已分润';
                        case "6":
                            return '打款失败';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 350,
                formatter : function(value, row, index) {
                    var str = '';
                  //  str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryProfitDetail(\'{0},{1},{2}\');" >查看明细</a>', row.agentId, row.profitDate);
                    <shiro:hasPermission name="/profit/finance/frozen">
                   if(row.STATUS == '0'|| row.STATUS == '4'|| row.status == '6'){
                    str += /*"&nbsp;&nbsp;||&nbsp;&nbsp;"+*/$.formatString('<a href="javascript:void(0)" class="profitByFinance_save-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="frozen(\'{0}\',\'{1}\');" >冻结</a>',row.ID,row.STATUS);
                    }
                    </shiro:hasPermission>

                    <shiro:hasPermission name="/profit/finance/frozen">
                    /*str += /!*"&nbsp;&nbsp;||&nbsp;&nbsp;"+*!/$.formatString('<a href="javascript:void(0)" class="profitByFinance_save-look-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="frozen(\'{0}\',\'{1}\');" >解冻</a>',row.id,row.status);*/
                    if( row.STATUS == '1' || row.STATUS == '3') {
                         str += /*"&nbsp;&nbsp;||&nbsp;&nbsp;"+*/$.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-social-myspace icon-red\'" onclick="freeze(\'{0}\');" >解冻</a>', row.ID);

                    }
                    </shiro:hasPermission>

                    <c:if test="${noEdit==0}">
                        <shiro:hasPermission name="/profit/month/Finance">
                            if(row.STATUS != '5') {
                                str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-adjustAmt" data-options="plain:true,iconCls:\'fi-social-myspace icon-blue\'" onclick="adjustM(\'{0}\');" 调整></a>', row.ID);
                            }
                        </shiro:hasPermission>
                    </c:if>
                        str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-assureAmt" data-options="plain:true,iconCls:\'icon-search\'" onclick="showRevDetail(\'{0}\');">担保代扣明细</a>', row.ID);

                    return str;
                }
            }]],
            onLoadSuccess:function(data){
                $('.activity-easyui-linkbutton-adjustAmt').linkbutton({text:'调整'});
                $('.activity-easyui-linkbutton-assureAmt').linkbutton({text:'担保代扣明细'});
            },
            onDblClickRow:function(dataIndex,rowData){
            },
            onBeforeEdit:function(index,row){
                row.editing = true;
            },
            onAfterEdit:function(index,row){
                row.editing = false;
                saveRowAction(row);
            },
            onCancelEdit:function(index,row){
                row.editing = false;
            },
            toolbar : '#profitByFinance_list_ConditionToolbar'
        });

        //代理商入网form
        $("#angetEnterInFormDialog").click(function(){
            addTab({
                title : '代理商操作-新签代理商',
                border : false,
                closable : true,
                fit : true,
                href:'/agentEnter/agentForm'
            });
        });
    });

    function adjustM(id) {
        parent.$.modalDialog({
            title : '调整',
            width : 500,
            height : 260,
            href : '${path }/profit/toAmtAdjust?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = profitByFinance_list_ConditionDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#adjustmEditForm');
                    f.submit();
                }
            } ]
        });
    }


    //查看担保代扣明细
    function showRevDetail(id){
        addTab({
            title : '担保扣款明细-'+id,
            border : false,
            closable : true,
            fit : true,
            href : '/monthProfit/toRevpage?id='+id
        });
    }

    function queryProfitDetail(agentId) {
        addTab({
            title : '明细数据展示',
            border : false,
            closable : true,
            fit : true,
            href:'/monthProfit/detail/page?agentId='+agentId
        });
    }

    function getRowIndex(target){
        var tr = $(target).closest('tr.datagrid-row');
        return parseInt(tr.attr('datagrid-row-index'));
    }

    function editrow(index){
        profitByFinance_list_ConditionDataGrid.datagrid('beginEdit', index);
    }
    function saverow(index){
        var rows = profitByFinance_list_ConditionDataGrid.datagrid('getRows');    // get current page rows
        var row = rows[index];    // your row data
        $.messager.confirm('确定','你确定冻结['+row.agentName+"]的分润么",function(r){
            profitByFinance_list_ConditionDataGrid.datagrid('endEdit', index);
        });
    }
    function cancelrow(index){
        profitByFinance_list_ConditionDataGrid.datagrid('cancelEdit', index);
    }

    /**
     * 出款
     **/
    function payMoney() {
        parent.$.messager.confirm('询问', '确认要进行出款吗？', function(b) {
            if (b) {
                $.ajax({
                    url :"${path}/monthProfit/payMoney",
                    type:'POST',
                    dataType:'json',
                    beforeSend :function() {
                        progressLoad();
                    },
                    success:function(result){
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                    },
                    error:function(data){
                        alertMsg("系统异常，请联系管理员！");
                    },
                    complete:function(){
                        searchprofitByFinance_list();
                        progressClose();
                    }
                });
            }
        });
    }

    /**
     * 终审
     **/
    function commitFinal() {
        parent.$.messager.confirm('询问', '确认要提交终审，后续不能再进行任务修改操作？', function(b) {
            if (b) {
                $.ajax({
                    url :"${path}/monthProfit/commitFinal",
                    type:'POST',
                    dataType:'json',
                    beforeSend :function() {
                        progressLoad();
                    },
                    success:function(result){
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                    },
                    error:function(data){
                        parent.$.messager.alert('错误','系统异常，请联系管理员！', 'error');
                    },
                    complete:function(){
                        searchprofitByFinance_list();
                        progressClose();
                    }
                });
            }
        });
    }

    /**
     * 终审
     **/
    function commitFirst() {
        parent.$.messager.confirm('询问', '确认要提交初审？', function(b) {
            if (b) {

                $.ajax({
                    url :"${path}/monthProfit/commitFirst",
                    type:'POST',
                    dataType:'json',
                    beforeSend :function() {
                        progressLoad();
                    },
                    success:function(result){
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                        progressClose();
                    },
                    error:function(data){
                        parent.$.messager.alert('错误','系统异常，请联系管理员！', 'error');
                    },
                    complete:function(){
                        searchprofitByFinance_list();
                        progressClose();
                    }
                });
            }
        });
    }

    /**
     * pOS奖励核算，初始化
     **/
    function initPosRewordDeatil() {
        parent.$.messager.confirm('询问', '请在初审操作前进行POS奖励的核算', function(b) {
            if (b) {

                $.ajax({
                    url :"${path}/monthProfit/initPosRowardDetail",
                    type:'POST',
                    dataType:'json',
                    beforeSend :function() {
                        progressLoad();
                    },
                    success:function(result){
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                        } else {
                            parent.$.messager.alert('错误', result.msg, 'error');
                        }
                        progressClose();
                    },
                    error:function(data){
                        parent.$.messager.alert('错误','系统异常，请联系管理员！', 'error');
                    },
                    complete:function(){
                        searchprofitByFinance_list();
                        progressClose();
                    }
                });
            }
        });
    }

    function frozen(id,status) {

        if(status=="1" ){
            parent.$.messager.alert('提示', "该状态为已冻结!", 'info');
            renturn;
        }
        if(status=="5"){
            parent.$.messager.alert('提示', "该状态为已分润!", 'info');
            renturn;
        }
        if(status != "1" || status != "5"){
            parent.$.modalDialog({
                title : '冻结',
                width : 400,
                height : 220,
                href : '${path }/profit/frozenProfit?id=' + id,
                buttons : [ {
                    text : '确定',
                    handler : function() {
                        parent.$.modalDialog.openner_dataGrid = profitByFinance_list_ConditionDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#custEditForm');
                        f.submit();
                    }
                } ]
            });
        }
    }

    //日期查询
    $("#profitDateStart,#profitDateEnd").datebox({
        required: true,
        formatter: function (date) {
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            return y +""+ (m < 10 ? ('0' + m) : m);
        },
        parser: function (s) {
        }
    })

    /**
     * 搜索事件
     */
    function searchprofitByFinance_list() {
        var flage = $("#chekbox").is(":checked");

        if(flage){
            if(($("#agentId").val()==null && $("#agentName").val()==null)||($("#agentId").val()=="" && $("#agentName").val()=="")){
                $.messager.alert("提示","请选择代理商唯一码或名称")
                return;
            }

            if(($("#profitDateStart").datebox('getValue'))!= ($("#profitDateEnd").datebox('getValue'))){
                $.messager.alert("提示","请将日期确定为一个月查询")
                return;
            }
        }
        profitByFinance_list_ConditionDataGrid.datagrid('load', $.serializeObject($('#profitByFinance_list_ConditionToolbar_searchform')));
    }

    function cleanAgentListSearchForm() {
        $('#profitByFinance_list_ConditionToolbar_searchform input:not("#chekbox")').val('');
        $("[name='agStatus']").val('');
        profitByFinance_list_ConditionDataGrid.datagrid('load', {});
    }
    /**
     * 统计事件
     */
    function profitCount(){
        var flag = $("#chekbox").is(":checked");
        var checkbox=0;
        if(flag){
            checkbox=$("#chekbox").val();
            if(($("#agentId").val()==null && $("#agentName").val()==null)||($("#agentId").val()=="" && $("#agentName").val()=="")){
                $.messager.alert("提示","请选择代理商唯一码或名称")
                return;
            }
        }
        var data=$.serializeObject($('#profitByFinance_list_ConditionToolbar_searchform'));
        var profitCountUrl=encodeURI('/profit/profitCount?agentId='+data.agentId+'&agentName='+data.agentName+'&profitDateEnd='+data.profitDateEnd+'&profitDateStart='+data.profitDateStart+'&checkbox='+checkbox);
        parent.$.modalDialog({
            title : '统计',
            width : 200,
            height : 100,
            href : profitCountUrl
        });
        console.log(data);
    }

    /**
     * 导出数据
     **/
    function exportByFinance() {
        $('#profitByFinance_list_ConditionToolbar_searchform').form({
            url : '${path }/profit/exportByF',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#profitByFinance_list_ConditionToolbar_searchform').submit();
    }

    /**
     * 解冻申请
     * @param id
     */
    function freeze(id) {

        addTab({
            title : '月分润申请解冻',
            border : false,
            closable : true,
            fit : true,
            href : '${path }/monthProfit/freeze/page?id='+id,
        });
    }
</script>
