<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var productagreeGrid;
    var productagreeList;
    $('#productagreediv').click(function(){
    	var rows = productagreeList.datagrid('getSelections');
    	i = rows.length-1;
    	parentProductId = rows[i].parentProductId;
    	//console.info(rows[0]);
        productagreeGrid.datagrid('load', {
            id: parentProductId
        });
    });
    	
    $(function() {
    	productagreeList = $('#productagreeList').datagrid({
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
            }, {
                width : '130',
                title : '创建时间',
                field : 'credateDate'
            },{
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
            } ]]
        });
    
    	productagreeGrid = $('#productagreeGrid').datagrid({
            url : '${path }/productagree/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'agreementId',
            columns : [ [ {
                width : '90',
                title : '所属产品',
                field : 'parentProductId'
            },{
                width : '70',
                title : '协议版本',
                field : 'version'
            },{
                width : '130',
                title : '创建时间',
                field : 'createDate'
            },{
                width : '70',
                title : '模板地址',
                field : 'tempUrl',
                	formatter : function(value, row, index) {
                		s = '<a href="'+value+'" target="_blank">点击查看</a>'
                        return s;
                    }
            },{
                field : 'action',
                title : '操作',
                width : 180,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/productagree/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="agree-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editproductagreeFun(\'{0}\');" >编辑</a>', row.agreementId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/productagree/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="agree-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteproductagreeFun(\'{0}\');" >删除</a>', row.agreementId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/productagree/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="agree-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryproductagreeFun(\'{0}\');" >查看</a>', row.agreementId);
                      </shiro:hasPermission>
                    return str;
              }
            }] ],
            onLoadSuccess:function(data){
                $('.agree-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.agree-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.agree-easyui-linkbutton-query').linkbutton({text:'查看'});
            },
            toolbar : '#productagreeToolbar'
        });
    });
    function addproductagreeFun() {
    	var rows = productagreeList.datagrid('getSelections');
    	if(rows!=undefined&& rows != ''){
    		i = rows.length-1;
    		parentProductId = rows[i].parentProductId;
    	}else{
    		parentProductId = 'null';
    	}
        parent.$.modalDialog({
            title : '添加',
            width : 450,
            height : 250,
            href : '${path }/productagree/addPage?id='+parentProductId,
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productagreeGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#agreeAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteproductagreeFun(id) {
    	
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = productagreeGrid.datagrid('getSelections');
            id = rows[0].agreementId;
        } else {//点击操作里面的删除图标会触发这个
        	productagreeGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前分配？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/productagree/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        productagreeGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editproductagreeFun(id) {
        if (id == undefined) {
            var rows = productagreeGrid.datagrid('getSelections');
            id = rows[0].agreementId;
        } else {
        	productagreeGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 450,
            height : 250,
            href : '${path }/productagree/editPage?id=' + id ,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productagreeGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#agreeEditForm');
                    f.submit();
                }
            } ]
        });
    }
    //查看
    function queryproductagreeFun(id) {
        if (id == undefined) {
            var rows = productagreeGrid.datagrid('getSelections');
            id = rows[0].agreementId;
        } else {
        	productagreeGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 450,
            height : 250,
            href : '${path }/productagree/queryPage?id=' + id

        });
    }
    
    function searchproductagreeFun() {
    	productagreeList.datagrid('load', $.serializeObject($('#searchproductagreeForm')));
    }
    function cleanproductagreeFun() {
        $('#searchproductagreeForm input').val('');
        productagreeList.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
        <form id="searchproductagreeForm">
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
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchproductagreeFun();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanproductagreeFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'配置列表'" >
        <table id="productagreeGrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div id="productagreediv" data-options="region:'west',border:true,title:'产品列表'"  style="width:50%;overflow: hidden; ">
        <table id="productagreeList" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="productagreeToolbar" style="display: none;">
    <shiro:hasPermission name="/productagree/add">
        <a onclick="addproductagreeFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>