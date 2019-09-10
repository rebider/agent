<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    $(function() {
       $('#posHuddleList').datagrid({
            url : '${path }/discount/posHuddleDetailList?huddleCode=${huddleCode}',
            striped : true,
            rownumbers : true,
            singleSelect : true,
            fit : true,
            idField : 'id',

            columns : [ [{
				title : '代理商唯一码',
				field : 'posAgentId',
                align : 'center',
                width : 200
			},{
                title : '代理商名称',
                field : 'posAgentName',
                align : 'center',
                width : 200
            }]],
		});
    });
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:''"  style="width:100%;overflow: hidden; ">
		<table id="posHuddleList" data-options="fit:true,border:false"></table>
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


