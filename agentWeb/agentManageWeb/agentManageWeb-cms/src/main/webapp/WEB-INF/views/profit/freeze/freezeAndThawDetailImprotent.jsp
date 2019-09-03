<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    $(function() {
       $('#freezeHistory').datagrid({
            url : '${path}/agentFreeze/getCheckHistoryDate',
            striped : true,
           rownumbers : true,
            queryParams:{
                agentId:"${freezeOperationRecord.agentId}",
                parentAgentId:"${freezeOperationRecord.parentAgentId}",
                freezeBatch:"${freezeOperationRecord.freezeBatch}",
                thawBatch:"${freezeOperationRecord.thawBatch}",
                freezeType:"${freezeOperationRecord.freezeType}"
		   },
            singleSelect : true,
            fit : true,
            idField : 'id',
            columns : [ [

                {
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
                 title : '上级代理商唯一码',
				 field : 'parentAgentId',
				 align : 'center',
                 width : 200
            },{
                    title : '上级代理商名称',
                    field : 'parentAgentName',
                    align : 'center',
                    width : 200
                },{
                    title : '冻结类型',
                    field : 'freezeType',
                    align : 'center',
                    width : 80,
                    formatter : function (value,row,index) {
                        if (value=="00"){
                            return "月份润";
                        } else if (value=="01"){
                            return "日分润";
                        } else if (value=="02"){
                            return "日返现";
                        } else{
                            return "";
                        }
                    }
                },{
                    title : '当前状态',
                    field : 'status',
                    align : 'center',
                    width : 80,
                    formatter : function (value,row,index) {
                        if (value=="0"){
                            return "已冻结";
                        } else if (value=="1"){
                            return "已解冻";
                        }else if(value=="2"){
                            return "解冻申请中";
						}
                    }
                },{
                    title : '金额',
                    field : 'freezeAmt',
                    align : 'center',
                    width : 80
                },{
                    title : '冻结/解冻操作时间',
                    field : 'operationTime',
                    align : 'center',
                    width : 200
                },{
                    title : '申请解冻时间',
                    field : 'thawTime',
                    align : 'center',
                    width : 200
                },{
                    title : '冻结原因',
                    field : 'freezeReason',
                    align : 'center',
                    width : 200
                },{
                    title : '解冻原因',
                    field : 'thawReason',
                    align : 'center',
                    width : 200
                }

			]],
		});
    });
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:''"  style="width:100%;overflow: hidden; ">
		<table id="freezeHistory" data-options="fit:true,border:false"></table>
	</div>
	</div>

	<%--
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
--%>


