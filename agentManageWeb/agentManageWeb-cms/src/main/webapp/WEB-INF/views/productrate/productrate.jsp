<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var productRateGrid;
    var productrateTree;
    $(function() {
    	productrateTree = $('#productrateTree').tree({
            url : '${path }/productrate/tree',
            parentField : 'parentProductId',
            lines : true,
            onClick : function(node) {
                productRateGrid.datagrid('load', {
                    id: node.id
                });
            }
        });

        productRateGrid = $('#productRateGrid').datagrid({
            url : '${path }/productrate/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'productId',
            sortName : 'productType',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '100',
                title : '息费类型',
                field : 'rateType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'T':
                        return '基准利率';
                    case 'D':
                        return '复利利率';
                    case 'L':
                        return '逾期利率';
                    case 'S':
                        return '服务费';
                    case 'W':
                        return '违约金';
                    case 'K':
                        return '砍头息';
                    case 'F':
                        return '逾期服务费率';
                    case 'X':
                        return '提前还款手续费';
                    }
                }
            },{
                width : '100',
                title : '所属子产品',
                field : 'subProductId'
            },{
                width : '100',
                title : '收息方式',
                field : 'rateMode',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'R':
                        return '提前还款';
                    case 'D':
                        return '每期计息';
                    case 'Q':
                        return '首期计息';
                    case 'L':
                        return '逾期首期';
                    case 'Y':
                        return '逾期每期';
                    case 'K':
                        return '挪减放款金额';
                    case 'U':
                        return '累加贷款本金';
                    }
                }
            },{
                width : '80',
                title : '息费计算类型',
                field : 'rateCaltype',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '日';
                    case 'N':
                        return '年';
                    case 'S':
                        return '期';
                    case 'M':
                        return '一次';
                    }
                }
            },{
                width : '80',
                title : '费率',
                field : 'rate'
            },{
                width : '80',
                title : '固定收取额',
                field : 'amt'
            },  {
                width : '100',
                title : '是否记复利',
                field : 'compound',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '不计复利';
                    case 'D':
                        return '记复利';
                    }
                }
            },{
                width : '80',
                title : '是否有效',
                field : 'status',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'H':
                        return '是';
                    case 'W':
                        return '否';
                    }
                }
            } , {
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/productrate/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="rate-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editProductRateFun(\'{0}\');" >编辑</a>', row.rateId);
                        </shiro:hasPermission>

                        <shiro:hasPermission name="/productrate/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="rate-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteProductRateFun(\'{0}\');" >删除</a>', row.rateId);
                        </shiro:hasPermission>
                        	//查看 
                        <shiro:hasPermission name="/productrate/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="rate-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryProductRateFun(\'{0}\');" >查看</a>', row.rateId);
                   		 </shiro:hasPermission>
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.rate-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.rate-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.rate-easyui-linkbutton-query').linkbutton({text:'查看'});
            },
            toolbar : '#productRateToolbar'
        });
    });
    
    function addProductRateFun() {
    	var rows = productrateTree.tree('getSelected');
    	if(rows!=undefined){
        	productId = rows.id;
    	}
        parent.$.modalDialog({
            title : '添加',
            width : 750,
            height : 400,
            href : '${path }/productrate/addPage?id='+productId,
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productRateGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#rateAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteProductRateFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = productRateGrid.datagrid('getSelections');
            id = rows[0].rateId;
        } else {//点击操作里面的删除图标会触发这个
            productRateGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前息费？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/productrate/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        productRateGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editProductRateFun(id) {
        if (id == undefined) {
            var rows = productRateGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            productRateGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 750,
            height : 400,
            href : '${path }/productrate/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productRateGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#rateEditForm');
                    f.submit();
                }
            } ]
        });
    }
     //查看
    function queryProductRateFun(id) {
        if (id == undefined) {
            var rows = productRateGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            productRateGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '查看',
            width : 750,
            height : 400,
            href : '${path }/productrate/queryPage?id=' + id

        });
    } 
    
    function searchProductRateFun() {
    	//alert($('#searchProductRateForm').val);
        productRateGrid.datagrid('load', $.serializeObject($('#searchProductRateForm')));
    }
    function cleanProductRateFun() {
        $('#searchProductRateForm input').val('');
        productRateGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <!-- <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchProductRateForm">
            <table>
                <tr>
                    <th>息费类型:</th>
                    <td>  
                    <select class="easyui-combobox" name="rateType">
                	<option></option>
					<option value="T">基准利率</option>
					<option value="D">复利利率</option>
					<option value="L">逾期利率</option>
					<option value="S">管理费</option>
					<option value="W">违约金</option>
					<option value="K">砍头息</option>
					</select>
					</td>
					
                    <th>计息周期:</th>
                    <td>
                        <select class="easyui-combobox" name="rateCaltype">
	                	<option></option>
						<option value="D">日</option>
						<option value="Q">周</option>
						<option value="L">月</option>
						<option value="J">季</option>
						<option value="N">年</option>
						<option value="S">期</option>
						<option value="M">一次性</option>
						</select>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProductRateFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanProductRateFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div> -->
    <div data-options="region:'center',border:true,title:'息费列表'" >
        <table id="productRateGrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div data-options="region:'west',border:true,split:false,title:'产品树'"  style="width:220px;overflow: hidden; ">
        <ul id="productrateTree" style="width:160px;margin: 10px 10px 10px 10px"></ul>
    </div>
</div>
<div id="productRateToolbar" style="display: none;">
    <shiro:hasPermission name="/productrate/add">
        <a onclick="addProductRateFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>