<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var monthProfitListGrid;
    $(function() {
        monthProfitListGrid = $('#monthProfitList').datagrid({
            url : '${path }/monthProfit/list',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            align : 'center',
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100 ],
            columns : [ [{
                title : '分润日期',
                align:'center',
                field : 'profitDate'
            },{
                title : '商户编号',
                align:'center',
                field : 'agentId'
            },{
                title : '上级商户编号',
                align:'center',
                field : 'parentAgentId'
            },{
                title : '付款交易额(pos交易额+二维码交易额+云闪付+银联二维)',
                align:'center',
                field : 'tranAmt'
            },{
                title : '出款交易额(S0+D1)',
                align:'center',
                field : 'payAmt'
            },{
                title : '付款交易分润比例',
                align:'center',
                field : 'tranProfitScale'
            },{
                title : '出款交易分润比例',
                align:'center',
                field : 'payProfitScale'
            },{
                title : '付款交易分润额',
                align:'center',
                field : 'tranProfitAmt'
            },{
                title : '出款交易分润额',
                align:'center',
                field : 'payProfitAmt'
            },{
                title : '瑞银信分润',
                align:'center',
                field : 'ryxProfitAmt'
            },{
                title : '瑞银信活动分润',
                align:'center',
                field : 'ryxHdProfitAmt'
            },{
                title : '贴牌分润',
                align:'center',
                field : 'tpProfitAmt'
            },{
                title : '瑞刷分润',
                align:'center',
                field : 'rsProfitAmt'
            },{
                title : '瑞刷活动分润',
                align:'center',
                field : 'rsHdProfitAmt'
            },{
                title : '瑞和宝分润',
                align:'center',
                field : 'rhbProfitAmt'
            },{
                title : '直发平台分润',
                align:'center',
                field : 'zfProfitAmt'
            },{
                title : 'POS直签补差分润',
                align:'center',
                field : 'posZqSupplyProfitAmt'
            },{
                title : '手刷直签补差分润',
                align:'center',
                field : 'mposZqSupplyProfitAmt'
            },{
                title : '分润汇总',
                align:'center',
                field : 'profitSumAmt'
            },{
                title : 'POS退单应扣款',
                align:'center',
                field : 'posTdMustDeductionAmt'
            },{
                title : 'POS退单实扣款',
                align:'center',
                field : 'posTdRealDeductionAmt'
            },{
                title : '手刷退单应扣',
                align:'center',
                field : 'mposTdMustDeductionAmt'
            },{
                title : '手刷退单实扣',
                align:'center',
                field : 'mposTdRealDeductionAmt'
            },{
                title : '瑞和宝订购应扣总额',
                align:'center',
                field : 'rhbDgMustDeductionAmt'
            },{
                title : '瑞和宝订购实扣',
                align:'center',
                field : 'rhbDgRealDeductionAmt'
            },{
                title : 'POS订购应扣总额',
                align:'center',
                field : 'posDgMustDeductionAmt'
            },{
                title : 'POS订购实扣',
                align:'center',
                field : 'posDgRealDeductionAmt'
            },{
                title : '智能POS订购应扣总额',
                align:'center',
                field : 'zposDgMustDeductionAmt'
            },{
                title : '智能POS订购实扣',
                align:'center',
                field : 'zposTdRealDeductionAmt'
            },{
                title : 'POS考核扣款(新国都、瑞易送)',
                align:'center',
                field : 'posKhDeductionAmt'
            },{
                title : '手刷考核扣款(小蓝牙、MPOS)',
                align:'center',
                field : 'mposKhDeductionAmt'
            },{
                title : '商业保理扣款',
                align:'center',
                field : 'buDeductionAmt'
            },{
                title : '其他扣款',
                align:'center',
                field : 'otherDeductionAmt'
            },{
                title : 'POS退单补款',
                align:'center',
                field : 'posTdSupplyAmt'
            },{
                title : '手刷退单补款',
                align:'center',
                field : 'mposTdSupplyAmt'
            },{
                title : '其他补款',
                align:'center',
                field : 'otherSupplyAmt'
            },{
                title : 'POS奖励',
                align:'center',
                field : 'posRewardAmt'
            },{
                title : 'POS奖励扣款',
                align : 'center',
                field : 'PosRewardDeductionAmt  '
            },{
                title : '扣当月之前税额（包含当月日结分润）',
                align:'center',
                field : 'deductionTaxMonthAgoAmt'
            },{
                title : '扣本月税额',
                align:'center',
                field : 'deductionTaxMonthAmt'
            },{
                title : '补下级税点',
                align:'center',
                field : 'supplyTaxAmt'
            },{
                title : '实发分润',
                align:'center',
                field : 'realProfitAmt'
            },{
                title : '本月分润',
                align:'center',
                field : 'profitMonthAmt'
            },{
                title : '账号',
                align:'center',
                field : 'accountId'
            },{
                title : '户名',
                align:'center',
                field : 'accountName'
            },{
                title : '开户行',
                align:'center',
                field : 'openBankName'
            },{
                title : '邮箱地址',
                align:'center',
                field : 'email'
            },{
                title : '联行号',
                align:'center',
                field : 'bankCode'
            },{
                title : '总行行号',
                align:'center',
                field : 'uTime'
            }/*,{
                title : '备注',
                align:'center',
                field : 'remark'
            }*/,{
                title : '打款状态',
                align:'center',
                field : 'payStatus'
            },{
                title : '分润状态',
                align:'center',
                field : 'status',
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
                title : '备注',
                align:'center',
                field : 'remark'
            },{
                title : '审核记录',
                align:'center',
                field : 'status1',
                formatter : function(value, row, index) {
                    if (row.status=='2' || row.status=='4') {
                        return $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryApproval(\'{0}\');" >查看</a>', row.id);
                    }else{
                        return "";
                    }
                }
            }, {
                field : 'action',
                title : '操作',
                align:'center',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="queryProfitDetail(\'{0},{1},{2}\');" >查看明细</a>', row.agentId, row.profitDate);
                    str += "&nbsp;&nbsp;&nbsp;&nbsp"
                    if(row.status == '0'|| row.status == '1'){
                        str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-social-myspace icon-red\'" onclick="freeze(\'{0}\');" >申请解冻</a>', row.id);
                    }
                    //str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-adjustM" data-options="plain:true,iconCls:\'fi-social-myspace icon-blue\'" onclick="adjustM(\'{0}\');" 调整></a>', row.id);
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.activity-easyui-linkbutton-edit').linkbutton({text:'查看明细'});
                $('.activity-easyui-linkbutton-del').linkbutton({text:'申请解冻'});
                //$('.activity-easyui-linkbutton-adjustM').linkbutton({text:'月调整'});
            }
        });

    });

    function searchData() {
        monthProfitListGrid.datagrid('load', $.serializeObject($('#monthProfitForm')));
    }
    function cleanData() {
        $('#monthProfitForm input').val('');
        monthProfitListGrid.datagrid('load', {});
    }

    /**
     * 查看审批明细
     */
    function queryApproval(id) {
        addTab({
            title: '审批展示'+id,
            border: false,
            closable: true,
            fit: true,
            href: '${path}/thawActivity/gotoTaskApproval?id=' + id
        });
    }

    function exportProfitDat() {
        $('#monthProfitForm').form({
            url : '${path }/monthProfit/export/profit/month',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#monthProfitForm').submit();
    }

    function queryProfitDetail(agentId) {
        addTab({
            title : '月分润明细数据展示',
            border : false,
            closable : true,
            fit : true,
            href:'/monthProfit/detail/page?agentId='+agentId
        });
    }

    function freeze(id) {
        addTab({
            title : '月分润申请解冻',
            border : false,
            closable : true,
            fit : true,
            href : '${path }/monthProfit/freeze/page?id='+id,
        });
    }

    function adjustM(id) {
            parent.$.modalDialog({
                title : '调整',
                width : 500,
                height : 260,
                href : '${path }/monthProfit/toAdjust?id=' + id,
                buttons : [ {
                    text : '确定',
                    handler : function() {
                        parent.$.modalDialog.openner_dataGrid = monthProfitListGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#adjustmEditForm');
                        f.submit();
                    }
                } ]
            });
    }

    $("#profitDateStartss,#profitDateEndss").datebox({
        formatter: function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            return y+'-'+(m<10?('0'+m):m);
        },
        parser: function(s){
            var t = Date.parse(s);
            if (!isNaN(t)){
                return new Date(t);
            } else {
                return new Date();
            }
        }
    })

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="monthProfitList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id ="monthProfitForm" method="post">
            <table>
                <tr>
                    <td>
                        <input id="agName" name="agentName" type="text" class="easyui-textbox" data-options="prompt:'代理商名称'" style="width:180px;">
                        <input id="agentId" name="agentId" type="text" class="easyui-textbox" data-options="prompt:'代理商业务编号'" value="" style="width:180px;">
                    </td>
                    <th>交易日期:</th>
                    <td><input id ="profitDateStartss" name="profitDateStart">&nbsp;至</td>
                    <td><input id ="profitDateEndss" name="profitDateEnd" ></td>
                    <th>分润状态：</th>
                    <td >
                        <select name="status" style="width:140px;height:21px" >
                            <option value=""></option>
                            <option value="1">冻结</option>
                            <option value="2">解冻审批中</option>
                            <option value="3">解冻失败</option>
                            <option value="4">未分润</option>
                            <option value="5">已分润</option>
                            <option value="6">打款失败</option>
                        </select>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchData();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanData();">清空</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportProfitDat();">导出</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

