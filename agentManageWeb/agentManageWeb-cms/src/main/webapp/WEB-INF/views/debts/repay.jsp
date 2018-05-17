<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
	var repayPlanDetaildataGrid;
	var repayPlanDataGrid;

/*     $('#repaydiv').click(function(){
    	//repayPlanDataGrid.datagrid('unselectAll');
    	var rows = repayPlanDataGrid.datagrid('getSelected');
    	//i = rows.length-1;s
    	planId = rows.planId;
    	//console.info(rows[0]);
        repayPlanDetaildataGrid.datagrid('load', {
            id: planId
        });
        repayPlanDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }); */
    $('#repaydiv').click(function(){
    	var rows = repayPlanDataGrid.datagrid('getSelections');
    	i = rows.length-1;
    	planId = rows[i].planId;
        repayPlanDetaildataGrid.datagrid('load', {
            id: planId
        });
    });
    $(function() {
    	repayPlanDataGrid = $('#repayPlanDataGrid').datagrid({
    		url : '${path }/repayPlan/repaydataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect :false,
            idField : 'id',
            sortName : 'planId',
	        sortOrder : 'asc',
            pageSize : 20,
            checkOnSelect: true, selectOnCheck:true,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '150',
                title : '还款计划编号',
                field : 'planId',
                sortable : true
            },
            {
                width : '150',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } ,
            {
                width : '80',
                title : '子产品编号',
                field : 'subProductId',
                sortable : true
            }  ,
            {
                width : '80',
                title : '父产品编号',
                field : 'parentProductId',
                sortable : true
            } ,  {
                width : '150',
                title : '唯一客户号',
                field : 'custId',
                sortable : true
            },
            {
                width : '80',
                title : '还款计划类型',
                field : 'planType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'K':
                        return '账分';
                    case 'H':
                        return '账汇';
                    case 'R':
                        return '无还款计划';
                    case 'Z':
                        return '借款时生成';
                    case 'Y':
                        return '账单日生成';
                    }
                }
            },
            {
                width : '150',
                title : '借据编号',
                field : 'loanId',
                sortable : true
            },
            {
                width : '130',
                title : '创建日期',
                field : 'createDate',
                sortable : true
            },
            {
                width : '80',
                title : '当前期数',
                field : 'period',
                sortable : true
            },
            {
                width : '80',
                title : '总期数',
                field : 'maxPeriod',
                sortable : true
            },
            {
                width : '130',
                title : '账单日',
                field : 'statementDate',
                sortable : true
            },
            {
                width : '130',
                title : '还款日',
                field : 'repayDate',
                sortable : true
            },
            {
                width : '80',
                title : '宽限期',
                field : 'gracePeriod',
                sortable : true
            },
            {
                width : '80',
                title : '最低还款额',
                field : 'leaseAmt',
                sortable : true
            },
            {
                width : '80',
                title : '逾期天数',
                field : 'dpd',
                sortable : true
            },
            {
                width : '100',
                title : '计划状态',
                field : 'planStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '有效';
                    case 'D':
                        return '暂停';
                    case 'C':
                        return '超时';
                    case 'A':
                        return '申请';
                    case 'L':
                        return '无效';
                    case 'H':
                        return '挂起';
                    }
                }
            },
            {
                width : '100',
                title : '还款状态',
                field : 'repayStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'T':
                        return '未结清';
                    case 'L':
                        return '提前结清';
                    case 'U':
                        return '已结清';
                    case 'G':
                        return '虚拟结清';
                    case 'O':
                        return '已逾期';
                    }
                }
            },
            {
                width : '80',
                title : '还清时间',
                field : 'settledDate',
                sortable : true
            },
            {
                width : '80',
                title : '参考应还',
                field : 'interestAmt',
                sortable : true
            },
            {
                width : '80',
                title : '已还总额',
                field : 'returnAmt',
                sortable : true
            },
            {
                width : '80',
                title : '贷款本金',
                field : 'returnAmt',
                sortable : true
            },
            {
                width : '80',
                title : '容差',
                field : 'tolerance',
                sortable : true
            },
            {
                width : '80',
                title : '剩余本金',
                field : 'remainAmt',
                sortable : true
            },
            {
                width : '80',
                title : '发生',
                field : 'overdueAmt',
                sortable : true
            },
            {
                width : '100',
                title : '批量状态锁定码',
                field : 'batchStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'C':
                        return '正常计息锁定';
                    case 'D':
                        return '正常计息锁定释放';
                    case 'E':
                        return '逾期计息锁定';
                    case 'Y':
                        return '逾期计息锁定释放';
                    case 'O':
                        return '购买锁定';
                    case 'P':
                        return '购买锁定释放';
                    }
                }
            },
            {
                width : '100',
                title : '是否计息',
                field : 'batchStatus',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Y':
                        return '是';
                    case 'N':
                        return '否';
                    }
                }
            },
            {
              	 field : 'action',
                  title : '操作',
                  width : 180,
                  formatter : function(value, row, index) {
                      var str = '';
                      <shiro:hasPermission name="/repayPlan/editRepayPlan">
                      str += $.formatString('<a href="javascript:void(0)" class="order-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryForRepayPlan(\'{0}\');" >查看</a>', row.planId);
                     </shiro:hasPermission>
                      return str;
                  } 
             }
    	 ]],
    	onLoadSuccess:function(data){
           	$('.order-easyui-linkbutton-edit').linkbutton({text:'查看'});
        }, 
    });
    repayPlanDetaildataGrid = $('#repayPlanDetaildataGrid').datagrid({
        url : '${path }/repayPlan/repayplanDetaildataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'planId',
        columns : [ [ 
			{
                width : '80',
                title : '还款计划编号',
                field : 'planId'
            },
            {
                width : '100',
                title : '还款计划明细ID',
                field : 'palnDetailId'
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
                    case 'Z':
                        return '逾期管理费';
                    }
                }
            },
            {
           	 field : 'status',
                title : '状态',
                width : 100,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Y':
                        return '正常';
                    case 'O':
                        return '冻结';
                    case 'Q':
                        return '启用';
                    case 'N':
                        return '不启用';
                    case 'H':
                        return '有效';
                    case 'W':
                        return '无效';
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
            },
            {
                width : '100',
                title : '计提金额',
                field : 'addRateAmt'
            } ,{
           	 field : 'action',
             title : '操作',
             width : 240,
             formatter : function(value, row, index) {
                 var str = '';
                     <shiro:hasPermission name="/finance/adjust">
                     str += $.formatString('<a href="javascript:void(0)" class="link-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="addAdjust(\'{0}\',\'{1}\');" >费用调整</a>', row.planId,row.palnDetailId);
                     </shiro:hasPermission>
                 return str;
             }
         } ] ],
         onLoadSuccess:function(data){
        	 $('.link-easyui-linkbutton-edit').linkbutton({text:'费用调整'});
         },
            toolbar : '#subproductToolbar'
     });
   });
    
    function addAdjust(planId,palnDetailId) {
    	$.ajax({ 
            url: "${path }/finance/isRepayPlan",
            type: "post",
            data:{'planId':planId},
            dataType: "json",
            success: function(data) {
                if (!data) { 
                	parent.$.messager.alert('提示', '该还款计划不支持调账！', 'info');
                } else {
                	parent.$.modalDialog({
                        title : '费用调整',
                        width : 420,
                        height : 350,
                        href : '${path }/finance/addAdjustPage?planId=' + planId  + "&&palnDetailId=" + palnDetailId ,
                        buttons : [ {
                            text : '确定',
                            handler : function() {
                                parent.$.modalDialog.openner_dataGrid = repayPlanDetaildataGrid;
                                var p = parent.$.modalDialog.handler.find('#adjustForm');
                                p.submit();
                            }
                        } ]
                    });
                }  
            }   
        });
	}
    
    function searchRepayPlan() {
    	repayPlanDataGrid.datagrid('load', $.serializeObject($('#searchPlanForm')));
    }
    function cleanRepayPlan() {
        $('#searchPlanForm input').val('');
        repayPlanDataGrid.datagrid('load', {});
    }
    
    function queryForRepayPlan(planId) {
        parent.$.modalDialog({
            title : '详情',
            width : 1200,
            height : 320,
            href : '${path }/repayPlan/editRepayPlanPageT?planId=' + planId
        });
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
        <form id="searchPlanForm">
            <table>
                <tr>
                    <th>还款计划编号:</th>
                    <td><input name="planId" placeholder="请输入还款计划编号"/></td>
                     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <th>客户号:</th>
                    <td><input name="custId" placeholder="请输入客户号"/></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRepayPlan();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanRepayPlan();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    
    <div data-options="region:'center',border:true,title:'还款计划明细列表'" >
        <table id="repayPlanDetaildataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="repaydiv" data-options="region:'west',border:true,title:'还款计划列表'"  style="width:48%;overflow: hidden; ">
        <table id="repayPlanDataGrid" data-options="fit:true,border:false"></table>
    </div>.
</div>

