<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">

    var dataTableInfoList ;

    $(function() {
        dataTableInfoList = $('#agentCardNoviceList').datagrid({
            url : '${path}/agentDebitCard/getNoticeList',
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [[{
                title : '代理商唯一码',
                field : 'ID',
                align : 'center',
                width:140
            },{

                title : '代理商名称',
                field : 'AG_NAME',
                align : 'center',
                width:140
            },{
                title : '审批状态',
                field : 'AG_STATUS',
                align : 'center',
                width:140,
                formatter:function (value, row,index) {
                    return db_options.agStatuss_map[value];
                }
            },{
                title : '收款账户类型',
                field : 'CLO_TYPE',
                align : 'center',
                width:120,
                formatter:function (value, row,index) {
                    console.log(value);
                    switch (value) {
                        case 2:
                            return '对私';
                        case 1:
                            return '对公';
                    }
                }

            },{
                title : '账户验证状态',
                field : 'FLAG',
                align : 'center',
                width:140,
                formatter:function (value, row,index) {
                    switch (value) {
                        case '1':
                            return '已生批';
                        case '0':
                            return '未处理';
                        case '2':
                            return '打款失败-已重试';
                        case '3':
                            return '复合通过';
                        case '4':
                            return '风险拦截';
                        case '5':
                            return '已撤销';
                        case '20':
                            return '银行处理失败';
                        case '7':
                            return '复合中';
                        case '8':
                            return '复合不通过';
                        case '9':
                            return '银行处理成功';
                        case '11':
                            return '打款成功';
                        case '12':
                            return '打款失败';
                    }
                }
            },{
                title : '收款账户名',
                field : 'CLO_REALNAME',
                align : 'center',
                width:140
            },{
                title : '收款账号',
                field : 'CLO_BANK_ACCOUNT',
                align : 'center',
                width:140
            },{
                title : '收款账户总行',
                field : 'CLO_BANK',
                align : 'center',
                width:140
            },{
                title : '收款账户支行',
                field : 'CLO_BANK_BRANCH',
                align : 'center',
                width:140
            },{
                title : '总行联行号',
                field : 'ALL_LINE_NUM',
                align : 'center',
                width:140
            },{
                title : '支行联行号',
                field : 'BRANCH_LINE_NUM',
                align : 'center',
                width:140
            },{
                title : '支付流水id',
                field : 'PAY_ID',
                align : 'center',
                hidden:true
            },{
                title : '收款账户id',
                field : 'AC_ID',
                align : 'center',
                hidden:true
            },{
                title : '操作',
                field : 'aaa',
                align : 'center',
                width:200,
                formatter: function (value, row,index) {
                    var str = '';
                    <shiro:hasPermission name="/agentUpdateApy/agentByidForUpdateColInfoView">
                    str += '&nbsp;&nbsp;&nbsp;&nbsp;';
                    str += $.formatString('<a id="stagButton" href="javascript:void(0)" class="easyui-linkbutton-add" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="applyUpdate(\'{0}\');" >申请修改</a>', row.ID);
                    </shiro:hasPermission>
                    return str;
                }
            }]],
        });
    });

    function setCount(){
        $('#countTotal').html('${countTotal}');
    }
    setCount();


    //申请修改
    function applyUpdate(id){
        if(id == '' || id == undefined){
            alertMsg("系统错误，请重试！");
            return;
        }
        addTab({
            title: '代理商收款信息修改申请' + id,
            border: false,
            closable: true,
            fit: true,
            href: '/agentUpdateApy/agentByidForUpdateColInfoView?id=' + id
        });
    }

    function alertMsg(str) {
        parent.$.messager.alert('提示', str, 'info');
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="dataTableInfo" data-options="region:'west',border:true" style="width: 100%;overflow: hidden;">
        <table id="agentCardNoviceList" data-options="fit:true,border:false"></table>
    </div>
    <div id="cardDataForm1" data-options="region:'north',border:false" style="height:50px;overflow: hidden;">
        <span style="font-size: 20px;text-align:center;width: auto;">特别提示：你有<span id="countTotal" style="font-size: 20px;text-align:center;">0</span> 条代理商结算卡信息需要更新，请及时处理。</span>
    </div>
</div>
