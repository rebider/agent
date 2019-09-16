<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var posTaxList;
    $(function() {
        posTaxList = $('#posTaxList').datagrid({
            url : '${path }/discount/posTaxList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
				title : '代理商唯一码',
				field : 'agentId',
                align : 'center',
                width : 200
			},{
                title : '代理商名称',
                field : 'agentName',
                align : 'center',
                width : 200
            },{
                title : '申请税点',
                field : 'taxIng',
                align : 'center',
                width : 130
            },{
                title : '原税点',
                field : 'taxOld',
                align : 'center',
                width : 130
            },{
                title : '分润月份',
                field : 'profitMonth',
                align : 'center',
                width : 130
            },{
                title : '生效日期',
                field : 'validDate',
                align : 'center',
                width : 130
            },{
                title : '调整状态',
                field : 'taxStatus',
                align : 'center',
            	width : 130,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "9":
                            return '新建';
                        case "0":
                            return '申请中';
                        case "1":
                            return '生效';
                        case "2":
                            return '无效';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                    if(row.taxStatus == '0'|| row.taxStatus == '1'){
                        var str = $.formatString('<a href="javascript:void(0)" class="tax-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="showActivity(\'{0}\');">查看审批进度</a>', row.id);
                    }
                    return str;
                }
            }]],
            onLoadSuccess:function(data){
                $('.tax-easyui-linkbutton-query').linkbutton({text:'查看审批进度'});
            },
            toolbar : '#taxToolbar'
        });

    });

    function showActivity(id) {
        addTab({
            title : '税点调整申请审批进度',
            border : false,
            closable : true,
            fit : true,
            href : '/posTaxActivity/gotoTaskApproval?id='+id
        });
    }

    //申请调整form
    $("#posTaxDialog").click(function(){
        addTab({
            title : '优惠政策-税点调整申请',
            border : false,
            closable : true,
            fit : true,
            href:'/discount/posTaxForm'
        });
    });

   function searchtax_list() {
       posTaxList.datagrid('load', $.serializeObject($('#searchtaxForm')));
	}
	function cleantax_list() {
		$('#searchtaxForm input').val('');
        posTaxList.datagrid('load', {});
	}

    function RefreshCloudHomePageTab() {
        posTaxList.datagrid('reload');
    }


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:''"  style="width:100%;overflow: hidden; ">
		<table id="posTaxList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
	   <form  method="post" action="${path}/tax/exporttaxD" id ="searchtaxForm" >
		   <table>
			   <tr>
				   <th>代理商名称:</th>
				   <td><input name="agentName" style="line-height:17px;border:1px solid #ccc"></td>
				   <th>代理商唯一码:</th>
				   <td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
				   <td>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchtax_list();">查询</a>
					   <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleantax_list();">清空</a>
				   </td>
			   </tr>
		   </table>
		</form>
	</div>
	<div id="taxToolbar">
		<c:if test="${noEdit==0}">
			<shiro:hasPermission name="/discount/taxbutton" >
				<a id="posTaxDialog" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">申请调整</a>
			</shiro:hasPermission>
		</c:if>
	</div>
</div>


