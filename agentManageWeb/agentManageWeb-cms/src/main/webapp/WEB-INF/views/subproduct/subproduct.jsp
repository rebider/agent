<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var subproductGrid;
    var productList1;

    $('#subproductdiv').click(function(){
    	var rows = productList1.datagrid('getSelections');
    	i = rows.length-1;
    	parentProductId = rows[i].parentProductId;
    	//console.info(rows[0]);
        subproductGrid.datagrid('load', {
            id: parentProductId
        });
        subproductGrid.datagrid('unselectAll').datagrid('uncheckAll');
    });
    	
    $(function() {
    	productList1 = $('#productList1').datagrid({
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
                width :	90,
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
                width : 70,
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
    
    	subproductGrid = $('#subproductGrid').datagrid({
            url : '${path }/product/dataGrid_Sub',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'subProductId',
            columns : [ [ {
                width : '80',
                title : '所属产品',
                field : 'parentProductId'
            },{
                width : '80',
                title : '子产品名称',
                field : 'productName'
            },{
                width : '80',
                title : '子产品编号',
                field : 'subProductId'
            },{
            	width : '80',
                title : '期数',
                field : 'period'
            },{
                width : '70',
                title : '每期数值',
                field : 'periodUnit'
            },{
                width : '70',
                title : '名义利率',
                field : 'titularRate'
            },{
                field : 'action',
                title : '操作',
                width : 180,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/product/edit_Sub">
                            str += $.formatString('<a href="javascript:void(0)" class="sub-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editsubproductFun(\'{0}\',\'{1}\');" >编辑</a>', row.parentProductId,row.subProductId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/product/delete_Sub">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="sub-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deletesubproductFun(\'{0}\',\'{1}\');" >删除</a>', row.parentProductId,row.subProductId);
                        </shiro:hasPermission>
                        //查看
                        <shiro:hasPermission name="/product/edit_Sub">
                            str += $.formatString('<a href="javascript:void(0)" class="sub-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="querysubproductFun(\'{0}\',\'{1}\');" >查看</a>', row.parentProductId,row.subProductId);
                        </shiro:hasPermission>
                    return str;
              }
            }] ],
            onLoadSuccess:function(data){
                $('.sub-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.sub-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.sub-easyui-linkbutton-query').linkbutton({text:'查看'});
            },
            toolbar : '#subproductToolbar'
        });
    });
    function addsubproductFun() {
    	var rows = productList1.datagrid('getSelections');
    	if(rows!=undefined && rows != ''){
    		i = rows.length-1;
        	productId = rows[i].parentProductId;
        	//alert(productId);
    	}else{
    		productId = 'null';
    	}
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 250,
            href : '${path }/product/addPage_Sub?id='+productId,
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = subproductGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#subAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deletesubproductFun(parentProductId,subProductId) {
    	
        if (parentProductId == undefined) {//点击右键菜单才会触发这个
            var rows = subproductGrid.datagrid('getSelections');
            parentProductId = rows[0].parentProductId;
            subProductId = rows[0].subProductId;
        } else {//点击操作里面的删除图标会触发这个
        	subproductGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前分配？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/product/delete_Sub', {
                	parentProductId : parentProductId,
                	subProductId : subProductId
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        subproductGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    //编辑页面
    function editsubproductFun(parentProductId,subProductId) {
        if (parentProductId == undefined) {
            var rows = subproductGrid.datagrid('getSelections');
            parentProductId = rows[0].parentProductId;
            subProductId = rows[0].subProductId;
        } else {
        	subproductGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 400,
            height : 250,
            href : '${path }/product/editPage_Sub?subProductId=' + subProductId ,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = subproductGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#subEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    //查看页面
    function querysubproductFun(parentProductId,subProductId) {
        if (parentProductId == undefined) {
            var rows = subproductGrid.datagrid('getSelections');
            parentProductId = rows[0].parentProductId;
            subProductId = rows[0].subProductId;
        } else {
        	subproductGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '查看',
            width : 400,
            height : 250,
            href : '${path }/product/queryPage_Sub?subProductId=' + subProductId 

        });
    }
    
    function searchsubproductFun() {
        productList1.datagrid('load', $.serializeObject($('#searchsubproductForm')));
    }
    function cleansubproductFun() {
        $('#searchsubproductForm input').val('');
        $("[name='productType']").val('');
        productList1.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
        <form id="searchsubproductForm">
            <table>
                <tr>
                    <th>产品编号:</th>
                    <td><input name="parentProductId" placeholder="请输入产品编号"/></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>产品类型:</th>
                    <td>
                        <select style="width:140px;height:21px" name="productType">
	                	<option></option>
						<option value="S">随借随还</option>
						<option value="F">分期</option>
						</select>
					</td>
					<td>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchsubproductFun();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleansubproductFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'子产品列表'" >
        <table id="subproductGrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div id="subproductdiv" data-options="region:'west',border:true,title:'产品列表'"  style="width:48%;overflow: hidden; ">
        <table id="productList1" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="subproductToolbar" style="display: none;">
    <shiro:hasPermission name="/product/add_Sub">
        <a onclick="addsubproductFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>