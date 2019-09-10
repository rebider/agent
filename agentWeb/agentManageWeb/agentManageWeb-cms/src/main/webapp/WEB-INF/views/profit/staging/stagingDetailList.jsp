<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var profitStagingDetailList;
    $(function() {
        profitStagingDetailList = $('#profitStagingDetailList${sourceId}').datagrid({
            url : '${path }/profit/staging/getStagingDetailList?sourceId=${sourceId}',
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
                title : '分润月份',
                field : 'deductionDate',
                width : 80
            },{
                title : '期数',
                field : 'currentStagCount',
                width : 130
            },{
                title : '应扣金额',
                field : 'mustAmt',
                width : 130
            },{
                title : '实扣金额',
                field : 'realAmt',
                width : 130
            } ,{
                title : '状态',
                field : 'status',
                width : 130,
                formatter : function(value, row) {
                    if (value=='0') {
                        return '未扣款';
					}else {
                        return '已扣款';
                    }
                }
            }
            ]]
        });

    });


</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:'业务平台列表'"  style="width:100%;overflow: hidden; ">
		<table id="profitStagingDetailList${sourceId}" data-options="fit:true,border:false"></table>
	</div>
</div>


