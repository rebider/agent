	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<%@ include file="/commons/global.jsp" %>
	
	<script type="text/javascript">
	    var splitDataGrid;
	    $(function() {
	    	splitDataGrid = $('#splitDataGrid').datagrid({
	            url : '${path }/cSplitRules/dataGrid',
	            striped : true,
	            rownumbers : true,
	            pagination : true,
	            singleSelect : true,
	            idField : 'splitId',
	            sortName : 'createTime',
	            sortOrder : 'asc',
	            pageSize : 20,
	            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
	            frozenColumns : [ [ 
	            {
	                width : '90',
	                title : '拆分编号',
	                field : 'splitId',
	                sortable : true
	            }, {
	                width : '90',
	                title : '拆分类型',
	                field : 'splitType',
	                sortable : true,
	                formatter : function(value, row, index) {
	                    switch (value) {
	                    case 'Y':
	                        return '逾期';
	                    case 'O':
	                        return '其他';
	                    }
	                }
	            },{
	                width : '90',
	                title : '拆分笔数',
	                field : 'splitCount',
	                sortable : true
	            },{
	                width : '90',
	                title : '拆分比例',
	                field : 'splitArray',
	                sortable : true
	            },{
	                width : '90',
	                title : '金额起始',
	                field : 'amtStart',
	                sortable : true
	            },{
	                width : '90',
	                title : '金额终止',
	                field : 'amtEnd',
	                sortable : true
	            },{
	                width : '130',
	                title : '创建时间',
	                field : 'createTime',
	                sortable : true
	            },
	            {
	                width : '80',
	                title : '是否有效',
	                field : 'status',
	                sortable : true,
	                formatter : function(value, row, index) {
	                    switch (value) {
	                    case 'Y':
	                        return '有效';
	                    case 'N':
	                        return '无效';
	                    }
	                }
	            } , {
	            	field : 'action',
	                title : '操作',
	                width : 200,
	                formatter : function(value, row, index) {
	                    var str = '';
	                        <shiro:hasPermission name="/cSplitRules/edit">
	                            str += $.formatString('<a href="javascript:void(0)" class="splitRules-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editSplitRulesFun(\'{0}\');" >编辑</a>', row.splitId);
	                        </shiro:hasPermission>
	                        <shiro:hasPermission name="/cSplitRules/delete">
	                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
	                            str += $.formatString('<a href="javascript:void(0)" class="splitRules-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deletesplitRulesFun(\'{0}\');" >删除</a>', row.splitId);
	                        </shiro:hasPermission>
	                        <shiro:hasPermission name="/cSplitRules/edit">
	                     	    str += $.formatString('<a href="javascript:void(0)" class="splitRules-easyui-linkbutton-search" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="querySplitRulesRateFun(\'{0}\');" >查看</a>', row.splitId);
	                   		</shiro:hasPermission>
	                    return str;
	                }
	            } 
	            ] ] ,
	            onLoadSuccess:function(data){
	                $('.splitRules-easyui-linkbutton-edit').linkbutton({text:'编辑'});
	                $('.splitRules-easyui-linkbutton-del').linkbutton({text:'删除'});
	                $('.splitRules-easyui-linkbutton-search').linkbutton({text:'查看'});
	            }, 
	    	toolbar : '#splitRulesToolbar'
	        });
	    });
		
	    //规则添加
	    function addproductallocFun() {
	        parent.$.modalDialog({
	            title : '添加',
	            width : 400,
	            height : 300,
	            href : '${path }/cSplitRules/addPage',
	            buttons : [ {
	                text : '确定',
	                handler : function() {
	                    parent.$.modalDialog.openner_dataGrid = splitDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
	                    var f = parent.$.modalDialog.handler.find('#splitRulesAddForm');
	                    f.submit();
	                }
	            } ]
	        });
	    }
	    
	    //规则编辑
	    function editSplitRulesFun(id) {
        if (id == undefined) {
            var rows = splitDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
        	splitDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
    	 parent.$.modalDialog({
                title : '编辑',
                width : 450,
                height : 300,
                href : '${path }/cSplitRules/editPage?id=' + id,
                buttons : [ {
                    text : '確定',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = splitDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#splitRulesEditForm');
                        f.submit();
                    }
                } ]
            });
	    }
	
	    //刪除規則
	    function deletesplitRulesFun(id) {
	        if (id == undefined) {//点击右键菜单才会触发这个
	            var rows = splitDataGrid.datagrid('getSelections');
	            id = rows[0].splitId;
	        } else {//点击操作里面的删除图标会触发这个
	        	splitDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	        }
	        parent.$.messager.confirm('询问', '您是否要删除当前规则？', function(b) {
	            if (b) {
	                progressLoad();
	                $.post('${path }/cSplitRules/delete', {
	                    id : id
	                }, function(result) {
	                    if (result.success) {
	                        parent.$.messager.alert('提示', result.msg, 'info');
	                        splitDataGrid.datagrid('reload');
	                    } else {
	                        parent.$.messager.alert('错误', result.msg, 'error');
	                    }
	                    progressClose();
	                }, 'JSON');
	            }
	        });
	    }
	    //規則查看
	    function querySplitRulesRateFun(id) {
	        if (id == undefined) {
	            var rows = splitDataGrid.datagrid('getSelections');
	            id = rows[0].id;
	        } else {
	        	splitDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
	        }
	        parent.$.modalDialog({
	            title : '查看',
	            width : 450,
	            height : 320,
	            href : '${path }/cSplitRules/queryPage?id=' + id
	        });
	    } 
	</script>
	<div class="easyui-layout" data-options="fit:true,border:false">
	    <div data-options="region:'center',fit:true,border:false">
	        <table id="splitDataGrid" data-options="fit:true,border:false"></table>
	    </div>
	    <!-- 添加 -->
	    <div id="splitRulesToolbar" style="display: none;">
	    <shiro:hasPermission name="/cSplitRules/add">
	        <a onclick="addproductallocFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
	    </shiro:hasPermission>
	</div>
	</div>