<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var loanDetailList;//subproductGrid
    var loanList;//productList1
    /* $('#loandiv').click(function(){
    	var rows = loanList.datagrid('getSelected');
    	//i = rows.length-1;
    	//loanId = rows[i].loanId;
    	loanId = rows.loanId;
    	//console.info(rows[0]);
        loanDetailList.datagrid('load', {
            id: loanId
        });
        loanList.datagrid('unselectAll').datagrid('uncheckAll');
    }); */
    $('#loandiv').click(function(){
    	var rows = loanList.datagrid('getSelections');
    	i = rows.length-1;
    	loanId = rows[i].loanId;
        loanDetailList.datagrid('load', {
            id: loanId
        });
    });
    $(function() {
    	loanList = $('#loanList').datagrid({
    		url : '${path }/debt/loandataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect :false,
            idField : 'id',
            sortName : 'loanId',
	        sortOrder : 'asc',
            pageSize : 20,
            checkOnSelect: true, selectOnCheck:true,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '150',
                title : '借款编号',
                field : 'loanId',
                sortable : true
            },
            {
                width : '150',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } ,
            {
                width : '70',
                title : '机构编号',
                field : 'branchId',
                sortable : true
            } ,  {
                width : '150',
                title : '唯一客户号',
                field : 'custId',
                sortable : true
            }, {
                width : '100',
                title : '客户手机号',
                field : 'custMobile'
            } , {
                width : '70',
                title : '产品编号',
                field : 'subProductId'
            }, {
                width : '70',
                title : '父产品编号',
                field : 'parentProductId'
            }  ,  
            {
                width : '130',
                title : '借款时间',
                field : 'loanDate',
                sortable : true
            }, {
                width : '130',
                title : '放款时间',
                field : 'payDate'
            },
            {
                width : '80',
                title : '借款期数',
                field : 'period'
            },
            {
                width : '80',
                title : '贷款金额',
                field : 'amt'
            },
            {
           	 field : 'action',
               title : '操作',
               width : 180,
               formatter : function(value, row, index) {
                   var str = '';
                   <shiro:hasPermission name="/debt/editLoan">
                   str += $.formatString('<a href="javascript:void(0)" class="order-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryForLoan(\'{0}\');" >查看</a>', row.loanId);
                  </shiro:hasPermission>
                   <shiro:hasPermission name="/cSplitRules/edit">
                   str += $.formatString('<a href="javascript:void(0)" class="splitRules-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editSplitRulesFun(\'{0}\');" >编辑</a>', row.splitId);
                   </shiro:hasPermission>
                   return str;
               } 
              }
            ]],
            onLoadSuccess:function(data){
               	$('.order-easyui-linkbutton-edit').linkbutton({text:'查看'});
               }, 
        });
    
    	loanDetailList = $('#loanDetailList').datagrid({
            url : '${path }/transFlow/loanDetaildataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'loanId',
            columns : [ [ {
                width : '150',
                title : '借款编号',
                field : 'loanId'
            },
            {
                width : '150',
                title : '息费ID',
                field : 'loanRateId'
            },
            {
                field : 'rateType',
                title : '成分类型',
                width : 90,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A':
                        return '本金';
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
            },
            {
                width : '90',
                title : '应还息费',
                field : 'interestRate'
            },
            {
                width : '90',
                title : '已还息费',
                field : 'returnRate'
            } ] ],
            toolbar : '#subproductToolbar'
        });
    });
    
    
    function queryForLoan(loanId) {
        parent.$.modalDialog({
            title : '详情',
            width : 1000,
            height : 320,
            href : '${path}/debt/editLoanPage?loanId=' + loanId
        });
	}
    function searchDebt() {
    	loanList.datagrid('load', $.serializeObject($('#searchDebtForm')));
    }
    function cleanDebt() {
        $('#searchDebtForm input').val('');
        loanList.datagrid('load', {});
    }
    
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 70px; overflow: hidden;background-color: #fff">
        <form id="searchDebtForm">
            <table>
                <tr>
                    <th>借款编号:</th>
                    <td><input name="loanId" placeholder="请输入借款用户编号"/></td>
                    <th></th>
                     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                </tr>
                <tr>
              	 <th>客户手机号:</th>
                 <td><input name="custMobile" placeholder="请输入客户手机号"/></td>
                  <th></th>
                 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                  <th>客户号:</th>
                    <td><input name="custId" placeholder="请输入客户号"/></td>
                 <th>
                  <td>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDebt();">查询</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanDebt();">清空</a>
                  </td>
                 </th>	  
                </tr>
            </table>
        </form>
    </div>
    
    <div data-options="region:'center',border:true,title:'借据明细列表'" >
        <table id="loanDetailList" data-options="fit:true,border:false"></table>
    </div>
    <div id="loandiv" data-options="region:'west',border:true,title:'借据列表'"  style="width:48%;overflow: hidden; ">
        <table id="loanList" data-options="fit:true,border:false"></table>
    </div>
</div>
