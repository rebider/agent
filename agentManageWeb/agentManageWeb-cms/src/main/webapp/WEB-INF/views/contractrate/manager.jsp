<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var contractRateGrid;
    var contractGrid;
    $('#contractdiv').click(function(){
    	var rows = contractGrid.datagrid('getSelections');
    	i = rows.length-1;
    	contractId = rows[i].contractId;
    	//console.info(rows[0]);
        contractRateGrid.datagrid('load', {
            id: contractId
        });
    });
    
    $(function() {
    	contractGrid = $('#contractGrid').datagrid({
                url : '${path }/contractrate/treeGrid',
                idField : 'contractId',
                fit : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                singleSelect : true,
                pageSize : 20,
                pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
                frozenColumns : [ [ {
                    title : '合同编号',
                    field : 'contractId',
                    width : 150
                } ] ],
                columns : [ [ {
                    field : 'amt',
                    title : '贷款本金',
                    width : 80
                },  {
                    width : '80',
                    title : '客户姓名',
                    field : 'custName'
                }, {
                	field : 'custPid',
                    title : '客户证件号',
                    width : 150
                }, {
                	field : 'createDate',
                    title : '合同创建时间',
                    width : 130,
                    sortable : true
                } , {
                	field : 'contractDate',
                    title : '合同签署时间',
                    width : 130
                } ]]
            });

        contractRateGrid = $('#contractRateGrid').datagrid({
            url : '${path }/contractrate/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'rateId',
            sortName : 'status',
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
                width : '100',
                title : '息费计算类型',
                field : 'rateCaltype',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '日';
                    case 'Q':
                        return '周';
                    case 'L':
                        return '月';
                    case 'J':
                        return '季';
                    case 'N':
                        return '年';
                    case 'S':
                        return '期';
                    case 'M':
                        return '次';
                    }
                }
            },{
                width : '100',
                title : '执行费率',
                field : 'rate'
            },{
                width : '100',
                title : '执行固定收取金额',
                field : 'amt'
            },  {
                width : '80',
                title : '是否记复利',
                field : 'comPoundType',
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
                field : 'statusType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'H':
                        return '有效';
                    case 'W':
                        return '无效';
                    }
                }
            } , {
                field : 'action',
                title : '操作',
                width : 60,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="/contractrate/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="crate-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editcontractRateFun(\'{0}\');" >编辑</a>', row.rateId);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/contractrate/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="crate-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deletecontractRateFun(\'{0}\');" >删除</a>', row.rateId);
                        </shiro:hasPermission>
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.crate-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.crate-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#contractRateToolbar'
        });
    });
    
    function addcontractRateFun() {
    	var rows = contractGrid.datagrid('getSelections');
    	if(rows!=undefined&& rows != ''){
    		i = rows.length-1;
        	contractId = rows[i].contractId;
    	}else{
    		alert("请至少选择一条合同进行操作！");
    		return;
    	}
        parent.$.modalDialog({
            title : '添加',
            width : 750,
            height : 400,
            href : '${path }/contractrate/addPage?id='+contractId,
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = contractRateGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#crateAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deletecontractRateFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = contractRateGrid.datagrid('getSelections');
            id = rows[0].rateId;
        } else {//点击操作里面的删除图标会触发这个
            contractRateGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前息费？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/contractrate/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        contractRateGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editcontractRateFun(id) {
        if (id == undefined) {
            var rows = contractRateGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            contractRateGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 750,
            height : 400,
            href : '${path }/contractrate/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = contractRateGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#crateEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchcontractRateFun() {
    	//alert($('#searchcontractRateForm').val);
        contractGrid.datagrid('load', $.serializeObject($('#searchcontractRateForm')));
    }
    function cleancontractRateFun() {
        $('#searchcontractRateForm input').val('');
        contractGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
        <form id="searchcontractRateForm">
            <table>
                <tr>
                    <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <th>客户姓名:</th>
                    <td><input name="custName" placeholder="请输入客户姓名"/></td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <!-- <th>订单编号:</th>
                    <td><input name="orderId" placeholder="请输入流水编号"/></td> -->
                    <th>客户证件号:</th>
                    <td><input name="custPid" placeholder="请输入客户证件号"/></td>
                    <th><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchcontractRateFun();">查询</a>
                	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleancontractRateFun();">清空</a>
                	</th>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'息费列表'" >
        <table id="contractRateGrid" data-options="fit:true,border:false"></table>
    </div>
    
    <div id="contractdiv" data-options="region:'west',border:true,title:'合同列表'"  style="width:50%;overflow: hidden; ">
        <ul id="contractGrid" style="width:160px;margin: 10px 10px 10px 10px"></ul>
    </div>
</div>
<div id="contractRateToolbar" style="display: none;">
    <shiro:hasPermission name="/contractrate/add">
        <a onclick="addcontractRateFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </shiro:hasPermission>
</div>