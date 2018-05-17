<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var agentStrategy;
    $(function() {
    	agentStrategy = $('#agentStrategy').datagrid({
            url : '${path }/agentStrategy/agentStrategyList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'agentId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '100',
                title : '代理商编号',
                field : 'agentId',
            }, {
                width : '70',
                title : '代理商状态',
                field : 'agentStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A':
                    	return '申请';
                    case 'O':
                    	return '开通';
                    case 'W':
                    	return '暂停';
                    case 'T':
                    	return '终止';
                    case 'B':
                    	return '限制业务';
                    case 'S':
                    	return '限制交易';
                    }
                }
            } , {
                width : '90',
                title : '代理商名称',
                field : 'agentName',
            } , {
                width : '80',
                title : '公司简称',
                field : 'agentCname',
            } , {
                width : '70',
                title : '申请人',
                field : 'appliant',
            }, {
                width : '90',
                title : '申请时间',
                field : 'applyDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '100',
                title : '注册手机',
                field : 'salPhone',
            }, {
                width : '80',
                title : '代理商类型',
                field : 'agentType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'S':
                        return '自营';
                    case 'J':
                        return '加盟';
                    case 'C':
                        return '资方';
                    }
                } 
            },{
                width : '80',
                title : '策略类型',
                field : 'strategyType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A':
                        return '息与费';
                    case 'T':
                        return '利息';
                    case 'L':
                        return '罚息';
                    case 'K':
                        return '利息罚息';
                    }
                }
            }, {
                width : '90',
                title : '单笔分润封顶',
                field : 'shareCap',
            }, {
                width : '70',
                title : '分润比',
                field : 'shareProp',
            },  {
                width : '90',
                title : '策略是否启用',
                field : 'strategyStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'N':
                        return '不启用';
                    case 'Y':
                        return '启用';
                    }
                }
            },{
            	 field : 'action',
                 title : '操作',
                 width : 300,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/agentStrategy/agentBind">
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="bindFun(\'{0}\');" >绑定策略</a>', row.agentId);
                         </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.product-easyui-linkbutton-edit').linkbutton({text:'绑定策略'});
             },
        });
    });
   function bindFun(agentId){
	   /* alert(agentId); */
	   parent.$.modalDialog({
           title : '绑定策略',
           width : 1000,
           height : 420,
           href : '${path }/agentStrategy/agentBind?agentId=' + agentId,
           buttons : [ {
               text : '确定',
               handler : function() {
            	   /* agentStrategy.datagrid('reload'); */
            	   window.location.reload();
            	   /* parent.$.modalDialog.handler.dialog('destroy');  */
            	   /* parent.$.modalDialog.handler.dialog('close');  */
               }
           } ]
       });
   }
   
   function searchAgent() {
	   agentStrategy.datagrid('load', $.serializeObject($('#searchAgentForm')));
	}
	function cleanAgent() {
		$('#searchAgentForm input').val('');
		$("[name='agentStatus']").val('');
		agentStrategy.datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="agentStrategy" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchAgentForm">
			<table>
				<tr>
					<th>代理商编号:</th>
					<td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>代理商状态:</th>
					<td>
						<select name="agentStatus" style="width:140px;height:21px" >
						  <option value="">-请选择-</option>
						  <option value="A">申请</option>
						  <option value="O">开通</option>
						  <option value="W">暂停</option>
						  <option value="T">终止</option>
						  <option value="B">限制业务</option>
						  <option value="S">限制交易</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>申请人:</th>
					<td><input name="appliant" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<th>注册手机:</th>
					<td><input name="salPhone" style="line-height:17px;border:1px solid #ccc"></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchAgent();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanAgent();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

