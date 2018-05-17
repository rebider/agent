<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
   /*  var advancerepayPlanDataGrid; */
    var advanceLoanDataGrid;
   /*  $('#advanceLoanDiv').click(function(){
    	var rows = advanceLoanDataGrid.datagrid('getSelections');
    	i = rows.length-1;
    	flowId = rows[i].flowId;
    	//console.info(rows[0]);
        repayPlanDataGrid.datagrid('load', {
            id: flowId
        });
    });  */
    
    
    //提前还款计划信息查询
   
        $(function() {
        advanceLoanDataGrid = $('#advanceLoanDataGrid').datagrid({
            url : '${path }/repayPlan/repaydataGrid',
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
            columns : [ [ /* {
                          field : 'ck',
                          checkbox:true
                      }, */{
                          width : '160',
                          title : '还款计划编号',
                          field : 'planId',
                          sortable : true
                      }, {
                          width : '160',
                          title : '唯一客户号',
                          field : 'custId',
                          sortable : true
                      }, {
                          width : '160',
                          title : '合同编号',
                          field : 'contractId',
                          sortable : true
                      } , {
                          width : '80',
                          title : '产品编号',
                          field : 'subProductId',
                          sortable : true
                      },    {
                      width : '80',
                      title : '父产品编号',
                      field : 'parentProductId'
                  } , {
                      width : '80',
                      title : '机构号',
                      field : 'branchId',
                      sortable : true
                  } , {
                      width : '160',
                      title : '借据编号',
                      field : 'loanId',
                      sortable : true
                  } , {
                      width : '80',
                      title : '借款期数',
                      field : 'period',
                      sortable : true
                  },  {
                      width : '80',
                      title : '贷款本金',
                      field : 'amt',
                      sortable : true
                  } , {
                      width : '80',
                      title : '总应还金额',
                      field : 'interestAmt',
                      sortable : true
                  },  {
                      width : '80',
                      title : '发生',
                      field : 'overdueAmt',
                      sortable : true
                  } ,  {
                      width : '80',
                      title : '总已还金额',
                      field : 'returnAmt',
                      sortable : true
                  },  {
                      width : '80',
                      title : '逾期天数',
                      field : 'dpd',
                      sortable : true
                  },   {
                      width : '80',
                      title : '结清时间',
                      field : 'settledDate',
                      sortable : true
                  }, /*{
                      width : '80',
                      title : '还款账户类型',
                      field : 'repayAccountType',
                      sortable : true,
                      formatter : function(value, row, index) {
                          switch (value) {
                          case 'S':
                              return '银行卡';
                          }
                      } 
                  }, {
                      width : '80',
                      title : '还款账户号',
                      field : 'repayAccount',
                      sortable : true
                  } , {
                      width : '80',
                      title : '还款账户名',
                      field : 'repayAccountName',
                      sortable : true
                  } , {
                      width : '80',
                      title : '是否逾期',
                      field : 'overDue',
                      sortable : true
                  } , {
                      width : '80',
                      title : '银行编号',
                      field : 'bankId',
                      sortable : true
                  } ,  {
                      width : '80',
                      title : '银行名称',
                      field : 'bankName',
                      sortable : true
                  }, {
                      width : '80',
                      title : '省编号',
                      field : 'provinceId',
                      sortable : true
                  }, {
                      width : '80',
                      title : '城市编号',
                      field : 'cityId',
                      sortable : true
                  } , {
                      width : '80',
                      title : '客户身份证号',
                      field : 'custPid',
                      sortable : true
                  },
                       {
                          width : '100',
                          title : '手机号',
                          field : 'mobile',
                          sortable : true,
                          formatter : function(value, row, index) {
                              switch (value) {
                              case 0:
                                  return '正常';
                              case 1:
                                  return '停用';
                              }
                          }
            } 
                  , */{
                   	 field : 'action',
                      title : '操作',
                      width : 180,
                      formatter : function(value, row, index) {
                          var str = '';
                          <shiro:hasPermission name="/repayPlan/editBefore">
                          str += $.formatString('<a href="javascript:void(0)" class="query-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryBeforeRepayFun(\'{0}\');" >查看</a>', row.planId);
                         </shiro:hasPermission>
                          return str;
                      } 
                     }
                  ] ],
                  onLoadSuccess:function(data){
                  	$('.query-easyui-linkbutton-edit').linkbutton({text:'查看'});
                  }, 
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar : '#userToolbar'
        });
        
     
    });
    //提前还款查看
    function queryBeforeRepayFun(planId) {
        parent.$.modalDialog({
            title : '详情',
            width :1200,
            height : 360,
            href : '${path }/repayPlan/editBeforeRepayPlanPage?planId=' + planId
        });
	}
    function searchAdvanceLoan() {
        advanceLoanDataGrid.datagrid('load', $.serializeObject($('#searchAdvanceLoanForm')));
    }
    function cleanAdvanceLoan() {
        $('#searchAdvanceLoanForm input').val('');
        advanceLoanDataGrid.datagrid('load', {});
    }
</script>

<!-- 	<h2>账户信息查询</h2> -->
	<div class="easyui-layout" style="width:1300px;height:500px;">
		<div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
        <form id="searchAdvanceLoanForm">
            <table>
                <tr>
                   	 <th>还款计划编号:</th>
                    	<td><input name="planId" placeholder="请输入还款计划编号"/></td>
                     <th>唯一客户号:</th>
                    	<td><input name="custId" placeholder="请输入唯一客户号"/></td>
                     <th>合同编号:</th>
                    	<td><input name="contractId" placeholder="请输入合同编号"/></td>
                   <!--  <th>时间:</th> -->
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAdvanceLoan();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAdvanceLoan();">清空</a>
                       <!--  <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRepayPlan();">获取信用计划</a> -->
                    </td>
                </tr>
            </table>
        </form>
        </div>
		<div data-options="region:'south',split:true" style="height:50px;"></div>
		<!-- <div data-options="region:'east',split:true" title="还款计划" style="width:650px;">
		     <table id="repayPlanDataGrid" data-options="fit:true,border:false, rownumbers:true,singleSelect:true,url:'datagrid_data1.json'"></table>
		</div> -->
		<div id="advanceLoanDiv" data-options="region:'west',split:true" title="提前还款信息" style="width:1300px;">
		   <table id="advanceLoanDataGrid" data-options="fit:true,border:false, rownumbers:true,singleSelect:true,url:'datagrid_data1.json'"></table>
	    </div>
		<!-- <div id="searchRepayPlan" data-options="region:'center',title:'',iconCls:'icon-ok'">
		   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRepayPlan();">获取信用计划</a>
		</div> -->
	</div>
 
</body>
</html>