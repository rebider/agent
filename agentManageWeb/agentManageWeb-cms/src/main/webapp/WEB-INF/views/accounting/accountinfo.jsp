<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var accountDataGrid;

    $(function() {

        accountDataGrid = $('#accountDataGrid').datagrid({
            url : '${path }/account/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'accountId',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '借款账户编号',
                field : 'accountId',
                sortable : true
            },
            {
                width : '50',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } ,
            {
                width : '80',
                title : '机构号',
                field : 'branchId',
                sortable : true
            } ,  {
                width : '80',
                title : '唯一客户号',
                field : 'custId',
                sortable : true
            }, {
                width : '50',
                title : '客户手机号',
                field : 'custMobile'
            } , {
                width : '50',
                title : '产品编号',
                field : 'productId'
            } ,
            {
                width : '50',
                title : '父产品编号',
                field : 'parentProductId',
                sortable : true
            } ,
            {
                width : '80',
                title : '总额度',
                field : 'maxLimit',
                sortable : true
            } ,  {
                width : '80',
                title : '已用额度',
                field : 'usedLimit',
                sortable : true
            }, {
                width : '50',
                title : '最后评估时间',
                field : 'sumAmt'
            } , {
                width : '50',
                title : '累计放款本金',
                field : 'payAmt'
            } , {
                width : '50',
                title : '累计还款本金',
                field : 'returnAmt'
            } , {
                width : '50',
                title : '已还期数',
                field : 'returnPeriod'
            } , {
                width : '50',
                title : '放款期数',
                field : 'payPeriod'
            } , {
                width : '50',
                title : '已放款期数',
                field : 'paidPeriod'
            } , {
                width : '50',
                title : '应收利息值',
                field : 'interestAmt'
            } , {
                width : '50',
                title : '已还利息',
                field : 'returnInterest'
            } , {
                width : '50',
                title : '应收砍头息',
                field : 'cutInterestAmt'
            } , {
                width : '50',
                title : '已收砍头息',
                field : 'returnCutInterest'
            } , {
                width : '50',
                title : '应收逾期利息',
                field : 'overdueAmt'
            } , {
                width : '50',
                title : '已收逾期利息',
                field : 'returnOverdue'
            } , {
                width : '50',
                title : '应收管理费值',
                field : 'expenseAmt'
            } , {
                width : '50',
                title : '已收管理费',
                field : 'returnExpense'
            } , {
                width : '50',
                title : '违约金',
                field : 'penaltyAmt'
            }, {
                width : '50',
                title : '已收违约金',
                field : 'returnPenalty'
            }, {
                width : '80',
                title : '上次最大额度',
                field : '是否已结清',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '正常';
                    case 1:
                        return '停用';
                    }
                }
            } /* , {
                field : 'action',
                title : '操作',
                width : 260,
                formatter : function(value, row, index) {
                    var str = '';
                      <shiro:hasPermission name="/user/edit">
                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-audit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editUserFun(\'{0}\');" >到期审核</a>', row.id);
                       </shiro:hasPermission>
                        <shiro:hasPermission name="/user/edit">
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-agree" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editUserFun(\'{0}\');" >同意</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/user/delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-refuse" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteUserFun(\'{0}\');" >拒绝</a>', row.id);
                        </shiro:hasPermission>
                    return str;
                }
            } */] ],
           /*  onLoadSuccess:function(data){
            	 $('.user-easyui-linkbutton-audit').linkbutton({text:'到期审核'});
                $('.user-easyui-linkbutton-agree').linkbutton({text:'同意'});
                $('.user-easyui-linkbutton-refuse').linkbutton({text:'拒绝'});
            }, */
            toolbar : '#userToolbar'
        });
    });
    
    
    function addUserFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 300,
            href : '${path }/user/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = accountDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteUserFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = accountDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            accountDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/user/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        accountDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editUserFun(id) {
        if (id == undefined) {
            var rows = accountDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            accountDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 500,
            height : 300,
            href : '${path }/user/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = accountDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchAccount() {
        accountDataGrid.datagrid('load', $.serializeObject($('#searchAccountForm')));
    }
    function cleanAccount() {
        $('#searchAccountForm input').val('');
        accountDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchAccountForm">
            <table>
                <tr>
                    <th>账户编号:</th>
                    <td><input name="accountId" placeholder="请输入账户编号"/></td>
                     <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                   <!--  <th>时间:</th> -->
                    <td>
                       <!--  <input name="createdateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至
                        <input  name="createdateEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /> -->
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAccount();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAccount();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'账户信息查询'" >
    <!--    <table>
         <tr><table id="accountDataGrid" data-options="fit:true,border:false"></table></tr>
         <tr>获取信用计划</tr>
         <tr><table id="accountDataGrid" data-options="fit:true,border:false"></table></tr>
       </table> -->
        <table id="accountDataGrid" data-options="fit:true,border:false"></table>
     <!--    <table id="accountDataGrid2" data-options="fit:true,border:false"></table> -->
      
    </div>
    
	<!--  <div>获取信用计划</div>
    <div data-options="region:'center',border:true,title:'授信到期审核'" >
        <table id="accountDataGrid" data-options="fit:true,border:false"></table>
    </div> -->
</div>
