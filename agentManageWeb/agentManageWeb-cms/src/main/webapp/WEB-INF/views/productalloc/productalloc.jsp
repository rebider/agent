<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var productallocGrid;
    var productList;

    $('#productdiv').click(function(){
    	var rows = productList.datagrid('getSelections');
    	i = rows.length-1;
    	parentProductId = rows[i].parentProductId;
    	//console.info(rows[0]);
        productallocGrid.datagrid('load', {
            id: parentProductId
        });
    });
    	
    $(function() {
    	productList = $('#productList').datagrid({
            url : '${path }/product/selectGrid',
            idField : 'parentProductId',
            fit : true,
            fitColumns : false,
            border : false,
            frozenColumns : [ [ {
                title : '产品编号',
                field : 'parentProductId',
                width : 70
            } ] ],
            columns : [ [ {
                field : 'productName',
                title : '产品名称',
                width : 90
            },{
                field : 'productType',
                title : '产品类型',
                width : 90,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '随借随还';
                    case 'F':
                        return '分期';
                    }
                }
            },{
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
            },{
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
                width : '130',
                title : '创建时间',
                field : 'credateDate'
            }, {
            	field : 'status',
                title : '是否启用',
                width : 60,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Q':
                        return '启用';
                    case 'N':
                        return '不启用';
                    }
                }
            }]]
        });
    
        productallocGrid = $('#productallocGrid').datagrid({
            url : '${path }/productalloc/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'allocId',
            columns : [ [ {
                width : '100',
                title : '所属产品',
                field : 'parentProductId'
            },{
                width : '100',
                title : '分配类型',
                field : 'feeType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'O':
                        return '上期未还清';
                    case 'Y':
                        return '本金';
                    case 'V':
                        return '利息';
                    case 'B':
                        return '砍头息';
                    case 'E':
                        return '服务费';
                    case 'P':
                        return '逾期罚息';
                    case 'W':
                        return '违约金';
                    case 'G':
                        return '溢缴款';
                    case 'S':
                        return '单边账';
                    case 'X':
                        return '提前还款手续费';
                    case 'Z':
                        return '逾期管理费';
                    }
                }
            },{
                width : '80',
                title : '排序类型',
                field : 'allocStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '正常分配';
                    case 'Y':
                        return '逾期分配';
                    case 'L':
                        return '临时分配';
                    }
                }
            },{
                width : '70',
                title : '排序',
                field : 'allocNum'
            },{
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/productalloc/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="alloc-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editproductallocFun(\'{0}\');" >编辑</a>', row.allocId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/productalloc/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="alloc-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteproductallocFun(\'{0}\');" >删除</a>', row.allocId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/productalloc/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="alloc-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryproductallocFun(\'{0}\');" >查看</a>', row.allocId);
                        </shiro:hasPermission>
                    return str;
              }
            }] ],
            onLoadSuccess:function(data){
                $('.alloc-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.alloc-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.alloc-easyui-linkbutton-query').linkbutton({text:'查看'});
            },
            toolbar : '#productallocToolbar'
        });
    });
    function addproductallocFun() {
    	var rows = productList.datagrid('getSelections');
    	if(rows!=undefined && rows != ''){
    		i = rows.length-1;
    		parentProductId = rows[i].parentProductId;
        	//alert(productId);
    	}else{
    		parentProductId = 'null';
    	}
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 250,
            href : '${path }/productalloc/addPage?id='+parentProductId,
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productallocGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#allocAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteproductallocFun(id) {
    	
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = productallocGrid.datagrid('getSelections');
            id = rows[0].allocId;
        } else {//点击操作里面的删除图标会触发这个
            productallocGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前分配？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/productalloc/delete', {
                	allocId : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        productallocGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editproductallocFun(id) {
        if (id == undefined) {
            var rows = productallocGrid.datagrid('getSelections');
            id = rows[0].allocId;
        } else {
            productallocGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 400,
            height : 250,
            href : '${path }/productalloc/editPage?allocId=' + id ,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productallocGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#allocEditForm');
                    f.submit();
                }
            } ]
        });
    }
    //查看
    function queryproductallocFun(id) {
        if (id == undefined) {
            var rows = productallocGrid.datagrid('getSelections');
            id = rows[0].allocId;
        } else {
            productallocGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 400,
            height : 250,
            href : '${path }/productalloc/queryPage?allocId=' + id 

        });
    }
    
    function searchproductallocFun() {
        productList.datagrid('load', $.serializeObject($('#searchproductallocForm')));
    }
    function cleanproductallocFun() {
        $('#searchproductallocForm input').val('');
        productList.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
        <form id="searchproductallocForm">
            <table>
                <tr>
                    <th>产品编号:</th>
                    <td><input name="parentProductId" placeholder="请输入产品编号"/></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>产品类型:</th>
                    <td>
                        <select class="easyui-combobox" name="productType">
	                	<option></option>
						<option value="S">随借随还</option>
						<option value="F">分期</option>
						</select>
					</td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchproductallocFun();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanproductallocFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'配置列表'" >
        <table id="productallocGrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div id="productdiv" data-options="region:'west',border:true,title:'产品列表'"  style="width:52%;overflow: hidden; ">
        <table id="productList" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="productallocToolbar" style="display: none;">
    <shiro:hasPermission name="/productalloc/add">
        <a onclick="addproductallocFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>