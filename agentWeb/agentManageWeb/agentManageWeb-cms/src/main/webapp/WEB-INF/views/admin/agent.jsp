<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var agentDataGrid;
    $(function() {
        agentDataGrid = $('#agentDataGrid').datagrid({
            url : '${path }/agent/find',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'flowId',
           // sortName : 'jobTime',
           // sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {   
                width : '70',
                title : '客户姓名',
                field : 'custName',
                sortable : true
            } , {
                width : '180',
                title : '交易时间',
                field : 'transDate',
                sortable : true
            } , {
                width : '190',
                title : '流水编号',
                field : 'flowId',
                sortable : true
            } , {
                width : '190',
                title : '借据编号',
                field : 'loanId',
                sortable : true
            } , {
                width : '190',
                title : '交易卡号',
                field : 'account',
                sortable : true
            } , {
                width : '190',
                title : '合同编号',
                field : 'contractId',
            } , {
                width : '100',
                title : '交易金额',
                field : 'amount',
                sortable : true
            } , {
                width : '150',
                title : '交易状态',
                field : 'transType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'E':
                        return '交易失败';
                    }
                }
            } , {
                width : '80',
                title : '交易方式',
                field : 'payType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'LJ':
                        return '联机代付';
                    case 'PK':
                        return '批量代付';
                    case 'XF':
                        return '消费';
                    case 'DK':
                        return '主动代扣';
                    case 'PJ':
                        return '批扣';
                    case 'SK':
                        return '刷卡';
                    case 'KZ':
                        return '快捷支付';
                    case 'WX':
                        return '微信';
                    case 'ZB':
                        return '支付宝';
                    case 'XX':
                        return '线下';
                    case 'TZ':
                        return '调账';
                    }
                }
            }, {
                field : 'action',
                title : '操作',
                width : 150,
                formatter : function(value, row, index) {
                      console.log(11);
                      var str = '';
                        <shiro:hasPermission name="/agent/loanAgain">
                            str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-ok"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="LoanBack(\'{0}\');" >放款重提</a>', row.flowId);
                        </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({text:'放款重提'});
            },
            toolbar : '#agentToolbar'
        });
    });

    function LoanBack(flowId) {
        if (flowId == undefined) {//点击右键菜单才会触发这个
            var rows = agentDataGrid.datagrid('getSelections');
            flowId = rows[0].flowId;
        } else {//点击操作里面的删除图标会触发这个
            agentDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要重提'+flowId+'的放款？', function(b) {
            if (b) {
                $.post('${path }/agent/loanAgain', {
                    flowId : flowId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        
                        agentDataGrid.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }
        
        $("#submit_search_agent").click(function () {
        $('#agentDataGrid').datagrid('load',{
        custName:$('#custName').val(),
        flowId:$('#flowId').val(),
        contractId:$('#contractId').val(),
        loanId:$('#loanId').val(),
        payType:$('#payType option:selected').val(),
        transDate:$('#transDate').datetimebox("getValue")
        });
        });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="agentDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="agentToolbar" style="display: none;">
   <form name="searchform" method="post" action="" id ="searchform">
		<span>客户姓名:</span>
		<input id="custName" style="line-height:17px;border:1px solid #ccc">&nbsp&nbsp
		<span>流水编号:</span>
		<input id="flowId" style="line-height:17px;border:1px solid #ccc">&nbsp&nbsp
		<span>交易时间:</span>
		<input class="easyui-datetimebox" id="transDate" data-options=""  style="width:160px">&nbsp&nbsp</br>
		<span>合同编号:</span>
		<input id="contractId" style="line-height:17px;border:1px solid #ccc">&nbsp&nbsp
		<span>借据编号:</span>
		<input id="loanId" style="line-height:17px;border:1px solid #ccc">&nbsp&nbsp
		<span>交易方式:</span>
		<select id="payType" style="width:160px;height:21px">
              <option value="联机代付">联机代付</option>
              <option value="批量代付">批量代付</option>
              <option value="消费">消费</option>
              <option value="主动代扣">主动代扣</option>
              <option value="批扣">批扣</option>
              <option value="刷卡">刷卡</option>
              <option value="快捷支付">快捷支付</option>
              <option value="微信">微信</option>
              <option value="支付宝">支付宝</option>
              <option value="线下">线下</option>
              <option value="调帐">调帐</option>
        </select>
		<a id="submit_search_agent" class="easyui-linkbutton" plain="true" >搜索</a>
	</form>
</div>
