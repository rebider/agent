<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var profitMergeList;
    $(function() {
        profitMergeList = $('#profitMergeList').datagrid({
            url : '${path }/profitAgentMerge/getProfitAgentMergeList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '合并编号',
                field : 'ID'
            },{
                title : '主代理商唯一号',
                field : 'MAIN_AGENT_ID',
                align : 'center',
                width : 200
            },{
                title : '主代理商名称',
                field : 'MAIN_AGENT_NAME',
                align : 'center',
                width : 200
            },{
                title : '主代理商负责人',
                field : 'MAIN_HEAD',
                align : 'center',
                width : 130
            },{
                title : '主代理商负责人联系电话',
                field : 'MAIN_HEAD_MOBILE',
                align : 'center',
                width : 150
            },{
                title : '附代理商唯一号',
                field : 'SUB_AGENT_ID',
                align : 'center',
                width : 200
            },{
                title : '附代理商名称',
                field : 'SUB_AGENT_NAME',
                align : 'center',
                width : 200
            },{
                title : '附代理商负责人',
                field : 'SUBN_HEAD',
                align : 'center',
                width : 130
            },{
                title : '附代理商负责人联系电话',
                field : 'SUB_HEAD_MOBILE',
                align : 'center',
                width : 150
            },{
                title : '合并时间',
                field : 'MERGE_DATE',
                align : 'center',
                width : 130
            },{
                title : '合并状态',
                field : 'MERGE_STATUS',
                align : 'center',
                width : 130,
                formatter: function (value, row, index) {
                    if (db_options.agStatuss)
                        for (var i = 0; i < db_options.agStatuss.length; i++) {
                            if (db_options.agStatuss[i].dItemvalue == row.MERGE_STATUS) {
                                return db_options.agStatuss[i].dItemname;
                            }
                        }
                    return "";
                }
            },{
                field : 'action',
                title : '操作',
                width : 230,
                formatter : function(value, row) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-query-merge" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="showMerge(\'{0}\');" >查看数据详情</a>', row.ID);
                    str += "&nbsp;&nbsp;"
                    if(row.MERGE_STATUS == 'Approved') {
                        str += $.formatString('<a href="javascript:void(0)" class="easyui-linkbutton-update-merge" data-options="plain:true,iconCls:\'fi-pencil\'" onclick="updateMerge(\'{0}\');" >更改代理商名称</a>', row.ID);
                    }
					return str;
                }
            }]],
            onLoadSuccess:function(data){
                $('.easyui-linkbutton-query-merge').linkbutton();
                $('.easyui-linkbutton-update-merge').linkbutton();
            },
            toolbar : '#profitMergeToolbar'
        });
    });

    //代理商合并申请进度查询
    function showMerge(id,agStatus) {
        addTab({
            title : '代理商合并申请审批进度',
            border : false,
            closable : true,
            fit : true,
            href : '/pAgentMergeActivity/mergeTaskApproval?id='+id
        });
    }

    /**
     * 搜索事件
     */
    function searchProfitMerge() {
        profitMergeList.datagrid('load', $.serializeObject($('#profitMergeForm')));
    }

    /**
     * 清空事件
     */
    function cleanProfitMerge() {
        $('#profitMergeForm input').val('');
        profitMergeList.datagrid('load', {});
    }

    //代理商合并申请
    function buildOrder(){
        addTab({
            title : '代理商合并申请',
            border : false,
            closable : true,
            fit : true,
            href:'${path}/profitAgentMerge/buildAgentMerge'
        });
    }

    //更改代理商名称
    function updateMerge(ID) {
        parent.$.messager.confirm('询问', '是否确认更改代理商名称?', function(b) {
            if (b) {
                $.post('${path }/profitAgentMerge/updateAgentName', {
                    id : ID
                }, function(result) {
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        meGroupDataGrid.datagrid('reload');
                    }else{
                        parent.$.messager.alert('提示', result.msg, 'info');
                    }
                }, 'JSON');
            }
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
		<table id="profitMergeList" data-options="fit:true,border:false"></table>
	</div>
	<div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
		<form method="post" id ="profitMergeForm"  >
			<table>
				<tr>
					<th>代理商唯一码:</th>
					<td><input name="MAIN_AGENT_ID" style="line-height:17px;border:1px solid #ccc"></td>
					<th>代理商名称:</th>
					<td><input name="MAIN_AGENT_NAME" style="line-height:17px;border:1px solid #ccc"></td>
					<td>
						<a class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProfitMerge()">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanProfitMerge();">清空</a>
					</td>
				</tr>
			</table>
		</form>
		<table>
			<shiro:hasPermission name="order_buildorder">
				<a onclick="buildOrder();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">代理商合并申请</a>
			</shiro:hasPermission>
		</table>
	</div>
</div>