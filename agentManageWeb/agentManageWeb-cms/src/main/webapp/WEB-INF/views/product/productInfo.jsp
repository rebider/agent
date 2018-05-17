<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
    var productDataGrid;
    $(function() {

    	productDataGrid = $('#productDataGrid').datagrid({
    		url : '${path }/product/dataGrid',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'parentProductId',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '100',
                title : '产品编号',
                field : 'parentProductId',
                sortable : true
            } ,
            {
                width : '100',
                title : '产品名称',
                field : 'productName',
                sortable : true
            } , {
                field : 'productType',
                title : '产品类型',
                width : 100,
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
                width : 100,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'D':
                        return '循环借';
                    case 'F':
                        return '结清再借';
                    }
                }
            }, {
                field : 'repayType',
                title : '还款方式',
                width : 100,
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
            },{
                field : 'periodType',
                title : '期限类型',
                width : 100,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'R':
                        return '日';
                    case 'Z':
                        return '周';
                    case 'Y':
                        return '月';
                    case 'J':
                        return '季';
                    case 'N':
                        return '年';
                    }
                }
            },  {
                width : '140',
                title : '创建时间',
                field : 'credateDate'
            },  {
                width : '80',
                title : '宽限期',
                field : 'graceDate'
            }, {
                width : '80',
                title : '容差',
                field : 'tolerance'
            },{
                width : '80',
                title : '风险等级',
                field : 'riskLevel'
            },{
            	field : 'status',
                title : '是否启用',
                width : 100,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'Q':
                        return '启用';
                    case 'N':
                        return '不启用';
                    }
                }
            } ] ],
            toolbar : '#userToolbar'
        });
    });
    
    function searchTransFlow() {
    	productDataGrid.datagrid('load', $.serializeObject($('#searchProductInfoForm')));
    }
    function cleanTransFlow() {
        $('#searchProductInfoForm input').val('');
        productDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<!-- <div data-options="region:'north',border:false"
		style="height: 50px; overflow: hidden;background-color: #fff">
		<form id="searchProductInfoForm">
			<table>
				<tr>
					<th>产品编号:</th>
					<td><input name="parentProductId" placeholder="产品编号" /></td>
					<th>产品类型:</th>
					<td><select class="easyui-combobox" name="productType"
						data-options="width:120,height:20,editable:false,panelHeight:'auto'">
							<option></option>
							<option value="S">随借随还</option>
							<option value="F">分期</option>
					</select></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton"
						data-options="iconCls:'fi-magnifying-glass',plain:true"
						onclick="searchTransFlow();">查询</a> <a href="javascript:void(0);"
						class="easyui-linkbutton"
						data-options="iconCls:'fi-x-circle',plain:true"
						onclick="cleanTransFlow();">清空</a></td>
				</tr>
			</table>
		</form>
	</div> -->
	<div data-options="region:'center',border:true,title:'产品信息查询'">
		<table id="productDataGrid" data-options="fit:true,border:false"></table>
	</div>
</div>
