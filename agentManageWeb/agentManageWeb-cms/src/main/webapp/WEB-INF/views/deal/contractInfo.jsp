<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var contractDataGrid;

    $(function() {

        contractDataGrid = $('#contractDataGrid').datagrid({
            url : '${path }/contract/dataGrid',//dataGrid
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : false,
            idField : 'id',
            sortName : 'accountId',
	        sortOrder : 'asc',
            pageSize : 20,
            checkOnSelect: true, selectOnCheck:true,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                field : 'ck',
                checkbox:true
            }, {
                width : '160',
                title : '合同号',
                field : 'contractId',
                sortable : true
            },
            {
                width : '130',
                title : '订单编号',
                field : 'orderId',
                sortable : true
            }, {
                width : '160',
                title : '唯一客户号',
                field : 'custId'
            } , {
                width : '70',
                title : '客户姓名',
                field : 'custName'
            } ,
            {
                width : '80',
                title : '客户证件类型',
                field : 'custPidType',
                sortable : true ,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'I':
                        return '身份证';
                    case 'T':
                        return '临时身份证';
                    case 'S':
                        return '军官证/士兵证';
                    case 'P':
                        return '护照';
                    case 'L':
                        return '营业执照';
                    case 'O':
                        return '其他有效证件';
                    case 'R':
                        return '户口簿';
                    case 'H':
                        return '港澳居民来往内地通行证';
                    case 'W':
                        return '台湾同胞来往内地通行证';
                    case 'F':
                        return '外国人居留证';
                    case 'Y':
                        return '测试';
                    case 'C':
                        return '警官证';
                    case 'Z':
                        return '烟草专卖号';
                    }
                } 
            } ,
            {
                width : '160',
                title : '客户证件号',
                field : 'custPid',
                sortable : true
            } , {
                width : '60',
                title : '产品编号',
                field : 'subProductId'
            } , {
                width : '70',
                title : '父产品编号',
                field : 'parentProductId'
            } , {
                width : '80',
                title : '产品类型',
                field : 'productType',
                sortable : true ,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '随借随还产品';
                    case 'F':
                        return '分期产品';
                    }
                } 
            } , {
                width : '130',
                title : '合同创建时间',
                field : 'createDate',
                sortable : true
            } , {
                width : '130',
                title : '合同签署时间',
                field : 'contractDate'
            } , {
                width : '70',
                title : '还款类型',
                field : 'repayType',
                sortable : true ,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '等额本金';
                     case'L':
                        return '等额本息';
                    case 'H':
                        return '等本等息';
                    case 'S':
                        return '测试';
                    case 'R':
                        return '按日计息';
                    }
                } 
            } , {
                width : '70',
                title : '放款类型',
                field : 'payType',
                sortable : true ,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '一次性放款';
                     case'D':
                        return '分期放款';
                    }
                }  
            } , {
                width : '80',
                title : '贷款本金',
                field : 'amt'
            } , {
                width : '60',
                title : '贷款期数',
                field : 'period'
            } , {
                width : '70',
                title : '约定还款日',
                field : 'repayDay'
            } , {
                width : '130',
                title : '开始计息日',
                field : 'interestDate'
            } , {
                width : '160',
                title : '代付卡号',
                field : 'payAccount'
            } , {
                width : '160',
                title : '代扣卡号',
                field : 'repayAccount'
            } , {
                width : '60',
                title : '合同状态',
                field : 'contractStatus',
                sortable : true ,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '申请';
                     case'D':
                        return '生效';
                     case'E':
                         return '无效';
                     case'L':
                         return '已结清';
                     case'C':
                         return '超时';
                     case'O':
                         return '正常';
                     case'Y':
                         return '关注';
                     case'F':
                         return '次级';
                     case'K':
                         return '可疑';
                     case'M':
                         return '损失';
                    }
                } 
            },{
                width : '130',
                title : '借款时间',
                field : 'loanDate'
            },{
                width : '130',
                title : '放款时间',
                field : 'payDate'
            }, {
                width : '130',
                title : '结清时间',
                field : 'settledDate'
            }, {
                width : '130',
                title : '合同生效时间',
                field : 'startDate'
            }, {
                width : '130',
                title : '合同终止时间',
                field : 'endDate'
            },{
                width : '130',
                title : '审核时间',
                field : 'operDate'
            }, {
                width : '100',
                title : '每期的周期单位',
                field : 'periodUnit'
            },{
                width : '90',
                title : '客户输入金额',
                field : 'inputAmt'
            }, {
                width : '90',
                title : '最长逾期天数',
                field : 'maxDpd'
            }, {
                field : 'action',
                title : '操作',
                width : 260,
                formatter : function(value, row, index) {
                    var str = '';
                    //开始
                    <shiro:hasPermission name="/contract/query">
                        str += $.formatString('<a href="javascript:void(0)" class="qy-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryCustFun(\'{0}\');" >查看</a>', row.contractId);
                    </shiro:hasPermission>
                    //明细查看开始
                     <shiro:hasPermission name="/contract/searchDetail">
                        str += $.formatString('<a href="javascript:void(0)" class="qy-easyui-linkbutton-detail" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="detailPage(\'{0}\');" >明细查看</a>', row.contractId);
                    </shiro:hasPermission> 
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
           	 $('.qy-easyui-linkbutton-edit').linkbutton({text:'查看'});
           	$('.qy-easyui-linkbutton-detail').linkbutton({text:'明细查看'}); 
           },
            toolbar : '#userToolbar'
        });
    });
    
    
    //合同信息查看
    function queryCustFun(contractId) {
        parent.$.modalDialog({
            title : '详情',
            width : 1000,
            height : 400,
            href : '${path }/contract/editQueryPage?contractId=' + contractId
        });
	}
     //明细查看
    function detailPage(contractId) {
        parent.$.modalDialog({
            title : '详情',
            width : 1000,
            height : 250,
            href : '${path }/contract/detailPage?contractId=' + contractId  
        });
	} 
    
    
    function addUserFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 300,
            href : '${path }/user/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = contractDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function deleteUserFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = contractDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            contractDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/user/delete', {
                    id : id
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        contractDataGrid.datagrid('reload');
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
            var rows = contractDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            contractDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 500,
            height : 300,
            href : '${path }/user/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = contractDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userEditForm');
                    f.submit();
                }
            } ]
        });
    }
    
    function searchContract() {
        contractDataGrid.datagrid('load', $.serializeObject($('#searchContractForm')));
    }
    function cleanContract() {
        $('#searchContractForm input').val('');
        contractDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
        <form id="searchContractForm">
            <table>
                <tr>
                    <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                    <th>订单编号:</th>
                    <td><input name="orderId" placeholder="请输入订单编号"/></td>
                    <th>唯一客户号:</th>
                    <td><input name="custId" placeholder="请输入唯一客户号"/></td>
                </tr>
                <tr>
                    <th>客户姓名:</th>
                    <td><input name="custName" placeholder="请输入客户姓名"/></td>
                    <th>客户证件号:</th>
                    <td><input name="custPid" placeholder="请输入客户证件号:"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchContract();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanContract();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'合同信息查询'" >
        <table id="contractDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
