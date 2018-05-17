<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var agentBind;
    var strategy;
    $(function() {
    	agentBind = $('#agentBind').datagrid({
            url : '${path }/agentStrategy/agentBindList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'strategyId',
            pageSize : 20,
            queryParams: {
            	agentId: $('#agentId').val()
        	},
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '70',
                title : '策略ID',
                field : 'strategyId',
            }, {
                width : '80',
                title : '策略类型',
                field : 'strategyType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A':
                        return '息与费';
                    case 'T':
                        return '利息';
                    case 'L':
                        return '罚息';
                    case 'K':
                        return '利息罚息';
                    }
                }
            }, {
                width : '90',
                title : '单笔分润封顶',
                field : 'shareCap',
            }, {
                width : '50',
                title : '分润比',
                field : 'shareProp',
            }, {
                width : '80',
                title : '策略是否启用',
                field : 'strategyStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'N':
                        return '不启用';
                    case 'Y':
                        return '启用';
                    }
                }
            },{
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteFun(\'{0}\');" >删除</a>', row.strategyId);
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
             },
        });
    	
    	
    	strategy = $('#strategy').datagrid({
            url : '${path }/agentStrategy/agentNotBindList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'strategyId',
            pageSize : 20,
            queryParams: {
            	agentId: $('#agentId').val()
        	},
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '70',
                title : '策略ID',
                field : 'strategyId',
            }, {
                width : '90',
                title : '执行金额起',
                field : 'strategyAmtSta',
            }, {
                width : '90',
                title : '执行金额止',
                field : 'strategyAmtEnd',
            }, {
                width : '90',
                title : '执行时间起',
                field : 'strategyTimeSta',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '90',
                title : '执行时间止',
                field : 'strategyTimeEnd',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '80',
                title : '策略类型',
                field : 'strategyType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A':
                        return '息与费';
                    case 'T':
                        return '利息';
                    case 'L':
                        return '罚息';
                    case 'K':
                        return '利息罚息';
                    }
                }
            }, {
                width : '90',
                title : '单笔分润封顶',
                field : 'shareCap',
            }, {
                width : '50',
                title : '分润比',
                field : 'shareProp',
            }, {
                width : '90',
                title : '策略录入时间',
                field : 'insertDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            } , {
                width : '90',
                title : '策略更新时间',
                field : 'updateDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '90',
                title : '策略更新人员',
                field : 'updateUser',
            }, {
                width : '80',
                title : '策略是否启用',
                field : 'strategyStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'N':
                        return '不启用';
                    case 'Y':
                        return '启用';
                    }
                }
            },{
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                     str += $.formatString('<a href="javascript:void(0)" class="agent-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="bindFun(\'{0}\');" >绑定</a>', row.strategyId);
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.agent-easyui-linkbutton-edit').linkbutton({text:'绑定'});
             },
        });
    	
    });
    
    function deleteFun(strategyId) {
        console.log(strategyId);
       if (strategyId == undefined) {//点击右键菜单才会触发这个
           var rows = roleDataGrid.datagrid('getSelections');
           strategyId = rows[0].strategyId;
       } else {//点击操作里面的删除图标会触发这个
    	   agentBind.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该代理商分润策略？', function(g) {
           if (g) {
               $.post('${path }/agentStrategy/delete', {
            	   id : strategyId,
            	   agentId: $('#agentId').val()
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       agentBind.datagrid('reload');
                       strategy.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
    
    function bindFun(strategyId) {
        console.log(strategyId);
       if (strategyId == undefined) {//点击右键菜单才会触发这个
           var rows = roleDataGrid.datagrid('getSelections');
           strategyId = rows[0].strategyId;
       } else {
    	   agentBind.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要绑定该代理商分润策略？', function(g) {
           if (g) {
               $.post('${path }/agentStrategy/bind', {
            	   id : strategyId,
            	   agentId: $('#agentId').val()
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       agentBind.datagrid('reload');
                       strategy.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
</script>
<input value="${Cagent}" type="hidden" id="agentId"/>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west',border:false"  style="overflow: hidden;border: 3px;width:500px;">
        <table id="agentBind"></table>
    </div>
    <div data-options="region:'center',border:false"  style="overflow: hidden;border: 3px;width:500px;">
        <table id="strategy"></table>
    </div>
</div>