<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var profitList;
    $(function() {
        profitList = $('#profitList').datagrid({
            url : '${path }/profit/profitDList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [[{
                title : '代理商名称',
                field : 'AGENTNAME',
                align : 'center',
				width:140
            },{
				title : '代理商编号',
				field : 'AGENTPID',
                align : 'center',
                width:140
			},{
                title : '代理商唯一码',
                field : 'AGENTID',
                align : 'center',
                width : 180
            }/*,{
                title : '交易时间',
                field : 'TRANSDATE',
                align : 'center',
                width : 100
            }*/,{
                title : '出款时间',
                field : 'REMITDATE',
                align : 'center',
                width : 100
            },{
                title : '补款金额',
                field : 'REDOMONEY',
                align : 'center',
                width : 100
            },{
                title : '返现金额',
                field : 'RETURNMONEY',
                align : 'center',
                width : 100
            },{
                title : '本日应发分润',
                field : 'TOTALPROFIT',
                align : 'center',
                width : 100
            },{
                title : '本日实发分润',
                field : 'REALMONEY',
                align : 'center',
                width : 100
            },{
                title : '冻结分润金额',
                field : 'FROZENMONEY',
                align : 'center',
                width : 100
            },{
                title : '打款成功金额',
                field : 'SUCCESSMONEY',
                align : 'center',
                width : 100
            },{
                title : '打款失败金额',
                field : 'FAILMONEY',
                align : 'center',
                width : 100
            },{
                title : '打款公司',
                field : 'COMNAME',
                align : 'center',
                width : 180

            }]],
			toolbar : '#profitToolbar'
        });
    });

   function searchprofit() {
       profitList.datagrid('load', $.serializeObject($('#searchprofitForm')));
	}
	function cleanprofit() {
		$('#searchprofitForm input').val('');
        profitList.datagrid('load', {});
	}

    function RefreshCloudHomePageTab() {
        profitList.datagrid('reload');
    }
    $("#startDate,#endDate").datebox({
        required : true,
        formatter: function(date){
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            return y +""+ (m<10?('0'+m):m)+""+(d<10?('0'+d):d);
        },
        parser: function(s){
        }
    })

</script>
<div class="easyui-layout" data-options="fit:true,bprofit:false">
	<div id="" data-options="region:'west',bprofit:true"  style="width:100%;overflow: hidden; ">
		<table id="profitList" data-options="fit:true,bprofit:false"></table>
	</div>
    <div data-options="region:'north',bprofit:false" style="height: 40px; overflow: hidden;background-color: #fff">
	   <form  method="post" action="${path}/profit/exportProfitD" id ="searchprofitForm" >
			<table>
				<tr>
					<th>代理商名称</th>
					<td><input id="agentName" name="agentName" type="text" class="easyui-textbox" style="width:160px;"></td><td>
					<th>代理商唯一码</th>
					<td><input id="agentId" name="agentId" type="text" class="easyui-textbox" value="" style="width:160px;"></td>
					<th>出款时间:</th>
					<td>
						<input id="startDate" name="startDate" style="width: 120px;" class="easyui-datebox" data-options="required:true">
						&nbsp;- &nbsp;<input id="endDate" name="endDate" style="width: 120px;"  class="easyui-datebox" data-options="required:true">
					</td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchprofit();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanprofit();">清空</a>
                    </td>
					<shiro:hasPermission name="/profit/day/export">
					<td>
						<button type="submit" class="easyui-linkbutton"  data-options="iconCls:'icon-print',plain:true," >导出</button>
					</td>
					</shiro:hasPermission>
				</tr>
			</table>
		</form>
	</div>
</div>


