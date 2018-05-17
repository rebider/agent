<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var ruleStrategyDataGrid;
    var ruleStrategyLeftDataGrid;
    $(function() {
        ruleStrategyDataGrid = $('#ruleStrategyDataGrid').datagrid({
            url : '${path }/strategy/strategyList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            // sortName : 'jobTime',
            // sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '50',
                title : '编号',
                field : 'id',
                sortable : true
            }, {
                width : '100',
                title : '名称',
                field : 'name',
                sortable : true
            } , {
                width : '70',
                title : '类型',
                field : 'typeName',
                sortable : true
            } , {
                width : '70',
                title : '值',
                field : 'value',
                sortable : true
            }, {
                field : 'action',
                title : '操作',
                width : 150,
                formatter : function(value, row, index) {
                    var str = '';
                    <%--<shiro:hasPermission name="/springbatch/start">--%>
                    str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-add"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="addStrategy(\'{0}\');" >添加</a>', row.id);
                    <%--</shiro:hasPermission>--%>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({text:'修改'});
            },
            toolbar : '#strategyToolbar'
        });
        strategyRuleList();

    });
    function strategyRuleList(){
        ruleStrategyLeftDataGrid= $('#ruleStrategyLeftDataGrid').datagrid({
            url : '${path }/rule/ruleStrategyList?ruleId=${cRule.id}',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            // sortName : 'jobTime',
            // sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '50',
                title : '编号',
                field : 'id',
                sortable : true
            }, {
                width : '100',
                title : '名称',
                field : 'name',
                sortable : true
            } , {
                width : '70',
                title : '类型',
                field : 'typeName',
                sortable : true
            } , {
                width : '70',
                title : '值',
                field : 'value',
                sortable : true
            },{
                field : 'action',
                title : '操作',
                width : 150,
                formatter : function(value, row, index) {
                    var str = '';
                    <%--<shiro:hasPermission name="/springbatch/start">--%>
                    str += $.formatString('<a href="javascript:void(0)" class="batch-easyui-linkbutton-add"  data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="deleteStrategy(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.batch-easyui-linkbutton-ok').linkbutton({text:'修改'});
            },
            toolbar : '#ruleStrategyToolbar'
        });
    }
    function addStrategy(strategyId) {
        if (strategyId == undefined) {//点击右键菜单才会触发这个
            var rows = ruleStrategyDataGrid.datagrid('getSelections');
            strategyId = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            ruleStrategyDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要添加到'+strategyId+'的条件？', function(b) {
            if (b) {
                $.post('${path }/rule/addStrategy', {
                    strategyId : strategyId,
                    ruleId : ${cRule.id}
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
//                        ruleStrategyDataGrid.datagrid('reload');
                        strategyRuleList();
                    }
                }, 'JSON');
            }
        });
    }

    function deleteStrategy(strategyId) {
        if (strategyId == undefined) {//点击右键菜单才会触发这个
            var rows = ruleStrategyDataGrid.datagrid('getSelections');
            strategyId = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            ruleStrategyDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要添加到'+strategyId+'的条件？', function(b) {
            if (b) {
                $.post('${path }/rule/deleteStrategy', {
                    strategyId : strategyId,
                    ruleId : ${cRule.id}
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
//                        ruleStrategyLeftDataGrid.datagrid('reload');
                        strategyRuleList();
                    }
                }, 'JSON');
            }
        });
    }

</script>
<div class="easyui-layout" style="width:800px;height:650px;">
    <%--<div data-options="region:'north'" style="height:10px"></div>--%>
    <%--<div data-options="region:'south',split:true" style="height:10px;"></div>--%>
    <%--<div data-options="region:'east',split:true" title="East" style="width:100px;"></div>--%>
    <div data-options="region:'west',split:true" title="选中" style="width:400px;">
        <table id="ruleStrategyLeftDataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'center',title:'备选',iconCls:'icon-ok'">
        <table id="ruleStrategyDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>