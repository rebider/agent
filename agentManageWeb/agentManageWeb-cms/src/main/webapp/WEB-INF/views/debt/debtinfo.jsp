<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var loanDetailDataGrid;//借据明细
    var debtDataGrid;
    $(function() {
        debtDataGrid = $('#debtDataGrid').datagrid({
            url : '${path }/debt/dataGrid',
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
            columns : [ [{
                field : 'ck',
                checkbox:true
            }, {
                width : '80',
                title : '借款编号',
                field : 'loanId',
                sortable : true
            },
            {
                width : '100',
                title : '合同编号',
                field : 'contractId',
                sortable : true
            } ,
            {
                width : '80',
                title : '机构编号',
                field : 'branchId',
                sortable : true
            } ,  {
                width : '80',
                title : '唯一客户号',
                field : 'custId',
                sortable : true
            }, {
                width : '80',
                title : '客户手机号',
                field : 'custMobile'
            } , {
                width : '50',
                title : '产品编号',
                field : 'subProductId'
            }, {
                width : '50',
                title : '父产品编号',
                field : 'parentProductId'
            }  ,  
            {
                width : '80',
                title : '借款时间',
                field : 'loanDate',
                sortable : true
            }, {
                width : '100',
                title : '放款时间',
                field : 'payDate'
            } , {
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
            }] ],
            selectOnCheck: true,
            checkOnSelect: true,
            toolbar : '#userToolbar'
        });
        
     
    });
    
   	//借据明细
    function searchLoanDetail( ){
    	 var checkedItems = $('#debtDataGrid').datagrid('getChecked');
    	 var names = [];
    	    $.each(checkedItems, function(index, item){
    	        names.push(item.loanId);
    	    });                
    	    loanDetailDataGrid = $('#loanDetailDataGrid').datagrid({
             url : '${path }/transFlow/loandataGrid?loanId='+names,
             fit : true,
             striped : true,
             rownumbers : true,
             pagination : true,
             singleSelect : false,
             idField : 'id',
             sortName : 'loanId',
 	        sortOrder : 'asc',
             pageSize : 20,
             checkOnSelect: true, selectOnCheck: true,
             pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
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
             } ] ],
             toolbar : '#userToolbar'
         });
    }
    
    
    
    
  
    function searchDebt() {
        debtDataGrid.datagrid('load', $.serializeObject($('#searchDebtForm')));
    }
    function cleanDebt() {
        $('#searchDebtForm input').val('');
        debtDataGrid.datagrid('load', {});
    }
</script>

	<div class="easyui-layout" style="width:1300px;height:500px;">
		<div data-options="region:'north',border:false" style="height: 50px; overflow: hidden;background-color: #fff">
        <form id="searchDebtForm">
            <table>
                <tr>
                    <th>借款编号:</th>
                    <td><input name="loanId" placeholder="请输入借款用户编号"/></td>
                     <th>合同编号:</th>
                    <td><input name="contractId" placeholder="请输入合同编号"/></td>
                   <!--  <th>时间:</th> -->
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchDebt();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanDebt();">清空</a>
                    </td>
                    <td>
                       <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchLoanDetail();">借据明细查看</a>
                    </td>
                </tr>
            </table>
        </form>
        </div>
		<!-- <div data-options="region:'south',split:true" style="height:50px;"></div> -->
		<div data-options="region:'east',split:true" title="借据明细" style="width:650px;">
		     <table id="loanDetailDataGrid" data-options="fit:true,border:false, rownumbers:true,singleSelect:true,url:'datagrid_data1.json'"></table>
		</div>
		<div data-options="region:'west',split:true" title="借据信息" style="width:650px;">
		   <table id="debtDataGrid" data-options="fit:true,border:false, rownumbers:true,singleSelect:true,url:'datagrid_data1.json'"></table>
		   <div style="margin: 10px 0;">
			  <span>Selection Mode: </span> <select
				onchange="$('#debtDataGrid').datagrid({singleSelect:(this.value==0)})">
				<option value="0">Single Row</option>
				<option value="1">Multiple Rows</option>
			    </select><br /> SelectOnCheck: <input type="checkbox" checked
				onchange="$('#debtDataGrid').datagrid({selectOnCheck:$(this).is(':checked')})"><br />
			    CheckOnSelect: <input type="checkbox" checked
				onchange="$('#debtDataGrid').datagrid({checkOnSelect:$(this).is(':checked')})">
		    </div>
	    </div>
	    
		
	</div>
 
</body>
</html>