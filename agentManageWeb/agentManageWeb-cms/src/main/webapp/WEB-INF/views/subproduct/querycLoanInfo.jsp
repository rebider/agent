<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var cloanDetailGrid;
    var cloanList;
    $('#loandiv').click(function(){
    	var rows = cloanList.datagrid('getSelections');
    	i = rows.length-1;
    	loanId = rows[i].loanId;
    	//console.info(rows[0]);
        cloanDetailGrid.datagrid('load', {
            id: loanId
        });
        cloanDetailGrid.datagrid('unselectAll').datagrid('uncheckAll');
    });
    	
    $(function() {
    	cloanList = $('#cloanList').datagrid({
            url : '${path }/transFlow/querycLoanList',
            idField : 'loanId',
            fit : true,
            fitColumns : false,
            border : false,
            frozenColumns : [ [ {
                title : '借据编号',
                field : 'loanId',
                width : 60
            } ] ],
            columns : [ [ 
            {
                field : 'contractId',
                title : '合同编号',
                width : 120
            },
            {
                field : 'branchId',
                title : '机构号',
                width : 120
            },
            {
                field : 'custId',
                title : '唯一客户号',
                width : 120
            },
            {
                field : 'custMobile',
                title : '客户手机号',
                width : 120
            },
            {
                field : 'subProductId',
                title : '子产品编号',
                width : 120
            },
            {
                field : 'parentProductId',
                title : '父产品编号',
                width : 120
            },
            {
                field : 'loanDate',
                title : '借款时间',
                width : 120
            },
            {
                field : 'payDate',
                title : '放款时间',
                width : 120
            },
            {
                field : 'period',
                title : '借款期数',
                width : 120
            },
            {
                field : 'interestAmt',
                title : '总已还金额',
                width : 120
            },
            {
                field : 'returnAmt',
                title : '总已还金额',
                width : 120
            },
            {
                field : 'overdueAmt',
                title : '发生金额',
                width : 120
            },
            
            {
                field : 'isSettled',
                title : '是否已结清',
                width : 100,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'O':
                        return '未结清';
                    case 'Y':
                        return '已结清';
                    }
                }
            },
            {
                field : 'overdueAmt',
                title : '结清时间',
                width : 120
            },
            {
                field : 'isPlan',
                title : '是否已生成还款计划',
                width : 100,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'O':
                        return '未生成';
                    case 'Y':
                        return '已生成';
                    }
                }
            },
            {
                field : 'planDate',
                title : '还款计划生成时间',
                width : 120
            },
            {
                field : 'planId',
                title : '还款计划编号',
                width : 120
            },
            {
                field : 'planPayDate',
                title : '计划放款时间',
                width : 120
            },
            {
                field : 'tolerance',
                title : '容差',
                width : 120
            },
            {
                field : 'amt',
                title : '贷款本金',
                width : 120
            },
            {
                field : 'remainAmt',
                title : '剩余本金',
                width : 120
            },
            ]]
        });
    
    	cloanDetailGrid = $('#cloanDetailGrid').datagrid({
            url : '${path }/transFlow/detailGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'loanId',
            columns : [ [ {
                width : '80',
                title : '借款编号',
                field : 'loanId'
            },
            {
                width : '100',
                title : '息费ID',
                field : 'loanRateId'
            },
            {
                field : 'rateType',
                title : '成分类型',
                width : 100,
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
                width : '80',
                title : '应还息费',
                field : 'interestRate'
            },
            {
                width : '80',
                title : '已还息费',
                field : 'returnRate'
            }] ]
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:true,title:'子产品列表'" >
        <table id="cloanDetailGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="loandiv" data-options="region:'west',border:true,title:'产品列表'"  style="width:48%;overflow: hidden; ">
        <table id="cloanList" data-options="fit:true,border:false"></table>
    </div>
</div>
 --%>