<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/global.jsp"%>
<script type="text/javascript"> 
    var adjustmentDataGrid;
    $(function() {
        adjustmentDataGrid = $('#adjustmentDataGrid').datagrid({
            url : '${path }/adjustment/find',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'flowId',
            sortName : 'transDate',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '180',
                title : '流水编号',
                field : 'flowId',
                sortable : true
            } , {
                width : '180',
                title : '交易日期',
                field : 'transDate',
                sortable : true
            } , {
                width : '80',
                title : '借还状态',
                field : 'transDirection',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '借款';
                    case 'C':
                        return '还款';
                    }
                }
            } , {
                width : '80',
                title : '交易状态',
                field : 'transType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Q':
                        return '申请';
                    case 'O':
                        return '交易中';
                    case 'S':
                        return '成功';
                    case 'E':
                        return '失败';
                    case 'L':
                        return '超时';
                    case 'T':
                        return '已重提';
                    case 'F':
                        return '不确定';
                    case 'D':
                        return '冲正';
                    case 'H':
                        return '挂起';
                    }
                }
            } , {
                width : '180',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } , {
                width : '80',
                title : '客户姓名',
                field : 'custName',
                sortable : true
             } , {
                width : '100',
                title : '交易金额(元)',
                field : 'amount',
                sortable : true
             } , {
                width : '130',
                title : '交易类型',
                field : 'transCode',                     
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A001':
                        return '借款合同申请';
                    case 'B001':
                        return '合同撤销';
                    case 'A002':
                        return '放款申请';
                    case 'B002':
                        return '放款冲正';
                    case 'A003':
                        return '还款';
                    case 'B003':
                        return '还款冲正';
                    case 'A004':
                        return '本金调整';
                    case 'A005':
                        return '利息调整';
                    case 'A006':
                        return '砍头息调整';
                    case 'A007':
                        return '管理费调整';
                    case 'A008':
                        return '逾期利息调整';
                    case 'A009':
                        return '违约金调整';
                    case 'B004':
                        return '息费调整冲正';
                    case 'A010':
                        return '溢交款转出';
                    case 'B010':
                        return '溢交款转出冲正';
                    case 'A011':
                        return '提前还款';
                    case 'C010':
                        return '利息计提';
                    }
                }
            } ,  {
                width : '80',
                title : '对账状态',
                field : 'accountStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '对账成功';
                    case 'E':
                        return '对账失败';
                    case 'D':
                        return '状态不一致';
                    case 'Q':
                        return '回盘文件缺失';
                    case 'W':
                        return '等待对账';
                    }
                }
            }, {
                field : 'action',
                title : '手工调账',
                width : 120,
                formatter : function(value, row, index) {
                     var str = '';
                        <shiro:hasPermission name="/adjustment/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-ok"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="editRlanIdFun(\'{0}\');" >新增账务调整</a>', row.flowId);
                        </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({Repayment:'新增调账'});
            },
            toolbar : '#repaymentToolbar'
        });
    });
    
    
    function editRlanIdFun(flowId) {
        if (flowId == undefined) {
            var rows = adjustmentDataGrid.datagrid('getSelections');
            flowId = rows[0].flowId;
        } else {
        	adjustmentDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '新增账务调整',
            width : 500,
            height : 600,
            href : '${path}/adjustment/editPage?flowId=' + flowId,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = adjustmentDataGrid; //因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#repaymentEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function reconciliation () {
        parent.$.modalDialog({
            title : '新增交易调账',
            width : 400,
            height : 550,
            href : '${path }/adjustment/addPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = adjustmentDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#repaymentAddEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function checkTransFlowStatus(jobId) {
        if (jobId == undefined) {//点击右键菜单才会触发这个
            var rows = adjustmentDataGrid.datagrid('getSelections');
            jobId = rows[0].jobId;
        } else {//点击操作里面的删除图标会触发这个
            adjustmentDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要重启'+jobId+'的跑批？', function(b) {
            if (b) {
                $.post('${path }/transflow/start', {
                    jobId : jobId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        adjustmentDataGrid.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }
    
      function checkFlowDeletion(jobId) {
         console.log(jobId);
        if (jobId == undefined) {//点击右键菜单才会触发这个
            var rows = roleDataGrid.datagrid('getSelections');
            jobId = rows[0].jobId;
        } else {//点击操作里面的删除图标会触发这个
            adjustmentDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除'+jobId+'的跑批？', function(b) {
            if (b) {
                $.post('${path }/transflow/delete', {
                    jobId : jobId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        adjustmentDataGrid.datagrid('reload');
                    }
                }, 'JSON');
            }
        });
    }
    
    //将表单数据转为json
        function form2Json(id) {
 
            var arr = $("#" + id).serializeArray();
            var jsonStr = "";
 
            jsonStr += '{';
            for (var i = 0; i < arr.length; i++) {
                jsonStr += '"' + arr[i].name + '":"' + arr[i].value + '",';
            }
            jsonStr = jsonStr.substring(0, (jsonStr.length - 1));
            jsonStr += '}';
 
            var json = JSON.parse(jsonStr);
            console.log(json);
            return json;
        }
        
        $("#submit_account_trans").click(function () {
        	$('#adjustmentDataGrid').datagrid('load',{
		        flowId:$('#flowId').val(),
		        contractId:$('#contractId').val(),
		        tRDate:$('#tRDate').datetimebox("getValue"),
		        transType:$('#transType option:selected').val(),
		        transCode:$('#transCode option:selected').val()
	        });
        });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="adjustmentDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="repaymentToolbar" style="display: none;">
    <shiro:hasPermission name="/adjustment/addPage">
        <a onclick="reconciliation ();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">新增交易调账</a>
    </shiro:hasPermission>
   <form name="search_account_form" method="post" action="" id="search_account_form">
		<span>流水编号</span>
		<input id="flowId" style="line-height:17px;border:1px solid #ccc">&nbsp&nbsp
		<span>合同编号</span>
		<input id="contractId" style="line-height:17px;border:1px solid #ccc">&nbsp&nbsp
		<span>交易类型</span>
        <select id="transCode" style="width:140px;height:21px">
              <option value="">-请选择-</option>
		      <option value="A002">放款</option>
		      <option value="A003">还款</option>
		      <option value="A011">提前还款</option>
		      <option value="C010">计提</option>
        </select>&nbsp&nbsp
		<span>交易状态</span>
		<select id="transType" style="width:140px;height:21px">
		      <option value="">-请选择-</option>
		      <option value="Q">申请</option>
		      <option value="O">交易中</option>
		      <option value="S">成功</option>
		      <option value="E">失败</option>
		      <option value="L">超时</option>
		      <option value="T">已重提</option>
		      <option value="F">不确定</option>
		      <option value="D">冲正</option>
		      <option value="H">挂起</option>
        </select>&nbsp&nbsp
		<span>交易时间:</span>
		<input class="easyui-datetimebox" id="tRDate" data-options="" style="width:140px">&nbsp&nbsp
		<a id="submit_account_trans" class="easyui-linkbutton" plain="true">搜索</a>
	</form>
</div>