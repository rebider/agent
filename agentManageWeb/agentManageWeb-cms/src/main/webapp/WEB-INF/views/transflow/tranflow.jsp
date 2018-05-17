<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/global.jsp"%>
<script type="text/javascript"> 
    var transAccountDataGrid;
    $(function() {
        transAccountDataGrid = $('#transAccountDataGrid').datagrid({
            url : '${path }/transflow/find',
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
                width : '100',
                title : '流水编号',
                field : 'flowId',
                sortable : true
            } , {
                width : '130',
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
                        return '借记';
                    case 'C':
                        return '贷记';
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
                    case 'G':
                        return '有效';
                    }
                }
            } , {
                width : '150',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } , {
                width : '80',
                title : '客户姓名',
                field : 'custName',
                sortable : true
             } , {
                width : '150',
                title : '交易卡号',
                field : 'account',
                sortable : true
             } , {
                width : '80',
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
                title : '操作',
                width : 80,
                formatter : function(value, row, index) {
                      var str = '';
                        <shiro:hasPermission name="/transflow/status">
                         if (row.accountStatus == 'D') {
                            str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-ok"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="checkTransFlowStatus(\'{0}\');" >状态不一致调账</a>', row.flowId);
                            }
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/transflow/deletion">
                         if (row.accountStatus == 'Q'){
                            str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="checkFlowDeletion(\'{0}\');" >回盘缺失调账</a>', row.flowId);
                            }
                        </shiro:hasPermission>
                        //开始新加查看
                        <shiro:hasPermission name="/transflow/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="trans-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryTransFun(\'{0}\');" >查看</a>', row.flowId);
                       </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({text:'状态不一致调涨'});
                $('.batch-easyui-linkbutton-del').linkbutton({text:'回盘缺失调账'});
                $('.trans-easyui-linkbutton-edit').linkbutton({text:'查看'});
            },
            toolbar : '#transFlowRec'
        });
    });

    
    //对账交易查看
    function queryTransFun(flowId) {
        parent.$.modalDialog({
            title : '详情',
            width : 1200,
            height : 420,
            href : '${path }/transflow/editTransFlowPage?flowId=' + flowId
        });
	}
    function reconciliation () {
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 250,
            href : '${path }/transflow/addPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = transAccountDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#transAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function reconciliationcut(){
         parent.$.modalDialog({
            title : '切换页面',
            width : 400,
            height : 250,
            href : '${path }/transflow/cutTransPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = transAccountDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#cutmodel');
                    f.submit();
                }
            } ]
        });
    }

    function checkTransFlowStatus(jobId) {
        if (jobId == undefined) {//点击右键菜单才会触发这个
            var rows = transAccountDataGrid.datagrid('getSelections');
            jobId = rows[0].jobId;
        } else {//点击操作里面的删除图标会触发这个
            transAccountDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要重启'+jobId+'的跑批？', function(b) {
            if (b) {
                $.post('${path }/transflow/start', {
                    jobId : jobId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        transAccountDataGrid.datagrid('reload');
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
            transAccountDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除'+jobId+'的跑批？', function(b) {
            if (b) {
                $.post('${path }/transflow/delete', {
                    jobId : jobId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        transAccountDataGrid.datagrid('reload');
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
        
       /*  $("#submit_account_trans").click(function () {
        	transAccountDataGrid.datagrid('load', $.serializeObject($('#search_account_form')));
        	$('#transAccountDataGrid').datagrid('load',{
		        flowId:$('#flowId').val(),
		        transType:$('#transType option:selected').val(),
		        accountStatus:$('#accountStatus option:selected').val()
	        });
        }); */
        
        function searchTR() {
        	transAccountDataGrid.datagrid('load', $.serializeObject($('#search_account_form')));
        }
        
        function cleanTR() {
    		$('#search_account_form input').val('');
    		$("[name='transType']").val('');
    		$("[name='accountStatus']").val('');
    		transAccountDataGrid.datagrid('load', {});
    	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="transAccountDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="transFlowRec" style="display: none;">
    <shiro:hasPermission name="/transflow/reconciliation">
        <a onclick="reconciliation ();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">重新对账</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="/transflow/cutFlow">
        <a onclick="reconciliationcut ();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">对账切换</a>
    </shiro:hasPermission>
   <form name="search_account_form" method="post" action="" id="search_account_form">
		<span>流水编号</span>
		<input id="flowId" name="flowId" style="line-height:17px;border:1px solid #ccc">&nbsp&nbsp
		<span>交易状态</span>
		<select id="transType" name="transType" style="width:140px;height:21px">
		      <option value="">-请选择-</option>
		      <option value="Q">申请</option>
		      <option value="O">交易中</option>
		      <option value="S">成功</option>
		      <option value="E">失败</option>
		      <option value="L">超时</option>
		      <option value="T">已重提</option>
		      <option value="F">不确定</option>
		      <option value="D">冲正</option>
        </select>&nbsp&nbsp
        <span>对账状态</span>
        <select id="accountStatus" name="accountStatus" style="width:140px;height:21px">
              <option value="">-请选择-</option>
		      <option value="S">对账成功</option>
		      <option value="E">对账失败</option>
		      <option value="D">状态不一致</option>
		      <option value="Q">回盘文件缺失</option>
		      <option value="W">等待对账</option>
        </select>&nbsp&nbsp
		<span>交易起始时间:</span>
					<td><input name="startTime" id="startTime" class="easyui-datetimebox" placeholder="开始时间"/></td>
					<span>～～    交易结束时间:<span>
					<td><input name="endTime" id="endTime" class="easyui-datetimebox" placeholder="结束时间" /></td>
		<!-- <input class="easyui-datetimebox" id="tRDate" data-options="" style="width:140px"> -->&nbsp&nbsp
		<!-- <a id="submit_account_trans" class="easyui-linkbutton" plain="true">搜索</a> -->
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchTR();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanTR();">清空</a>
	</form>
</div>