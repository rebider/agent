<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
    var loanApproveDataGrid;
    $(function() {

    	loanApproveDataGrid = $('#loanApproveDataGrid').datagrid({
    		url : '${path }/cLoanApprove/dataGrid', 
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'orderDate',
	        sortOrder : 'desc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '150',
                title : '订单号',
                field : 'orderId',
                sortable : true
            } ,
            {
                width : '90',
                title : '机构号',
                field : 'branchId',
                sortable : true
            } ,
            {
                width : '150',
                title : '唯一客户号',
                field : 'custId',
                sortable : true
            }  ,
            {
                width : '90',
                title : '客户名',
                field : 'custName',
                sortable : true
            } ,
            {
                width : '90',
                title : '客户手机号',
                field : 'custMobile',
                sortable : true
            },
            {
                width : '90',
                title : '子产品编号',
                field : 'subProductId',
                sortable : true
            } ,
            {
                width : '90',
                title : '父产品编号',
                field : 'parentProductId',
                sortable : true
            }  ,
            {
                width : '90',
                title : '申请金额',
                field : 'amt',
                sortable : true
            } ,
            {
                width : '90',
                title : '申请期数',
                field : 'period',
                sortable : true
            } ,
            {
                width : '150',
                title : '申请时间',
                field : 'orderDate',
                sortable : true
            } ,
            {
                width : '150',
                title : '代付卡号',
                field : 'payAccount',
                sortable : true
            } ,
            {
                width : '150',
                title : '代扣卡号',
                field : 'repayAccount',
                sortable : true
            } ,
            {
                width : '90',
                title : '审核状态',
                field : 'status',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '审批通过';
                    case 'E':
                        return '审批拒绝';
                    case 'D':
                        return '审批中';
                    }
                }
            } , 
              { field : 'action',
                    title : '操作',
                    width : 180,
                    formatter : function(value, row, index) {
                        var str = '';
						if(row.status == 'D'){
                            <shiro:hasPermission name="/cLoanApprove/edit_pass">
                                str += $.formatString('<a href="javascript:void(0)" class="ApproveCms-easyui-linkbutton-editPass" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editPassOrderFun(\'{0}\');" >审批通过</a>', row.orderId);
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/cLoanApprove/edit_refuse">
                                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                str += $.formatString('<a href="javascript:void(0)" class="ApproveCms-easyui-linkbutton-editRefuse" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="editRefuseOrderFun(\'{0}\');" >审批拒绝</a>', row.orderId);
                            </shiro:hasPermission>
						}
                        return str;
                    }
            } ] ],
             onLoadSuccess:function(data){
                $('.ApproveCms-easyui-linkbutton-editPass').linkbutton({text:'审批通过'});
                $('.ApproveCms-easyui-linkbutton-editRefuse').linkbutton({text:'审批拒绝'});
            }, 
            toolbar : '#userToolbar'
        });
    });
    //编辑（审批通过）
     function editPassOrderFun(id) {
        if (id == undefined) {
        	alert('该条数据无主键！');
        	return;
        }
        parent.$.modalDialog({
            title : '审批通过',
            width : 500,
            height : 200,
            href : '${path }/cLoanApprove/editPassPage?id=' +id,
            buttons : [ {
                text : '审批通过',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = loanApproveDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#orderEditPassForm');
                    f.submit();
                }
            } ]
        })
    }
    
    //编辑（审批拒绝）
    function editRefuseOrderFun(id) {
        if (id == undefined) {
        	alert('该条数据无主键！');
        	return;
        }
            parent.$.modalDialog({
                title : '审批拒绝',
                width : 500,
                height : 200,
                href : '${path }/cLoanApprove/editRefusePage?id='+id,
                buttons : [ {
                    text : '审批拒绝',
                    handler : function() {
                        parent.$.modalDialog.openner_datagrid = loanApproveDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#orderEditRefuseForm');
                        f.submit();
                    }
                } ]
            });
    }
/*     function searchTransFlow() {
    	loanApproveDataGrid.datagrid('load', $.serializeObject($('#searchProductInfoForm')));
    }
    function cleanTransFlow() {
        $('#searchProductInfoForm input').val('');
        loanApproveDataGrid.datagrid('load', {});
    }  */
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:true,title:'产品信息查询'">
		<table id="loanApproveDataGrid" data-options="fit:true,border:false"></table>
	</div>
</div>
