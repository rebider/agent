<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var productTreeGrid;
    $(function() { 
    	productTreeGrid = $('#productTreeGrid').treegrid({
            url : '${path }/product/treeGrid',
            idField : 'parentProductId',
            treeField : 'productName',
            parentField : 'parentProductId',
            fit : true,
            fitColumns : false,
            border : false,
            frozenColumns : [ [ {
                title : '产品编号',
                field : 'parentProductId',
                width : 60
                //hidden : true
            } ] ],
            columns : [ [ {
                field : 'productName',
                title : '产品名称',
                width : 130
            },{
                field : 'productType',
                title : '产品类型',
                width : 110,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '随借随还';
                    case 'F':
                        return '分期';
                    }
                }
            }, {
                field : 'loanType',
                title : '借款类型',
                width : 90,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '循环借';
                    case 'F':
                        return '结清再借';
                    }
                }
            }, {
                field : 'repayType',
                title : '还款方式',
                width : 90,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '等额本金';
                    case 'L':
                        return '等额本息';
                    case 'H':
                        return '等本等息';
                    case 'R':
                        return '按日计息';
                    }
                }
            },{
                field : 'periodType',
                title : '期限类型',
                width : 80,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'R':
                        return '日';
                    case 'Z':
                        return '周';
                    case 'Y':
                        return '月';
                    case 'J':
                        return '季';
                    case 'N':
                        return '年';
                    }
                }
            },  {
                width : '140',
                title : '创建时间',
                field : 'credateDate'
            },  {
                width : '60',
                title : '宽限期',
                field : 'graceDate'
            }, {
                width : '60',
                title : '容差',
                field : 'tolerance'
            },{
                width : '60',
                title : '风险等级',
                field : 'riskLevel'
            },{
            	field : 'status',
                title : '是否启用',
                width : 80,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Q':
                        return '启用';
                    case 'N':
                        return '不启用';
                    }
                }
            } , {
                field : 'action',
                title : '操作',
                width : 210,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/product/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editProductFun(\'{0}\');" >编辑</a>', row.parentProductId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/product/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteProductFun(\'{0}\');" >删除</a>', row.parentProductId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/product/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-search" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryProductFun(\'{0}\');" >查看</a>', row.parentProductId);
                    </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.product-easyui-linkbutton-search').linkbutton({text:'查看'});
            },
            toolbar : '#proToolbar'
        });
    });
    
    function editProductFun(id) {
        if (id != undefined) {
        	productTreeGrid.treegrid('select', id);
        }
        
        var node = productTreeGrid.treegrid('getSelected');
        //alert(node.statusType);
        if (node) {
            parent.$.modalDialog({
                title : '编辑',
                width : 750,
                height : 450,
                href : '${path }/product/editPage?id=' + node.parentProductId,
                buttons : [ {
                    text : '保存',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = productTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#productEditForm');
                        f.submit();
                    }
                } ]
            });
        }
    }
    //查看页面
    function queryProductFun(id) {
        if (id != undefined) {
        	productTreeGrid.treegrid('select', id);
        }
        
        var node = productTreeGrid.treegrid('getSelected');
        //alert(node.statusType);
        if (node) {
            parent.$.modalDialog({
                title : '查看',
                width : 750,
                height : 450,
                href : '${path }/product/searchPage?id=' + node.parentProductId

            });
        }
    }
    
    function deleteProductFun(id) {
        if (id != undefined) {
        	productTreeGrid.treegrid('select', id);
        }
        var node = productTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除当前产品？删除当前资源会连同子产品一起删除!', function(b) {
                if (b) {
                    progressLoad();
                    $.post('${path }/product/delete', {
                        id : node.parentProductId
                    }, function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            productTreeGrid.treegrid('reload');
                        }else{
                            parent.$.messager.alert('提示', result.msg, 'info');
                        }
                        progressClose();
                    }, 'JSON');
                }
            });
        }
    }
    
    function addProductFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 750,
            height : 450,
            href : '${path }/product/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = productTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#productAddForm');
                    f.submit();
                }
            } ]
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"  style="overflow: hidden;">
        <table id="productTreeGrid"></table>
    </div>
    <div id="proToolbar" style="display: none;">
        <shiro:hasPermission name="/product/add">
            <a onclick="addProductFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
        </shiro:hasPermission>
    </div>
</div>