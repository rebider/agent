<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var deductionDetailList;
    $(function() {
        deductionDetailList = $('#deductionDetailList${sourceId}').datagrid({
            url : '${path }/profit/other/deduction/getDeductionDetailList?sourceId=${sourceId}',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
				title : 'id',
				field : 'id',
				hidden:true
			},{
                title : '扣款月份',
                field : 'deductionDate',
                width : 80
            } ,{
                title : '金额',
                field : 'mustAmt',
                width : 130
            },{
                title : '扣款类型',
                field : 'remark',
                width : 130
            }
            ]]
        });

    });


</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:'业务平台列表'"  style="width:100%;overflow: hidden; ">
		<table id="deductionDetailList${sourceId}" data-options="fit:true,border:false"></table>
	</div>
</div>


