<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var AgentA;
    $(function() {
    	AgentA = $('#AgentA').datagrid({
            url : '${path }/agent/agentList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'agentId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
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
                width : '90',
                title : '代理商推广码',
                field : 'agentCode',
            } ,{
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
                width : '90',
                title : '注册手机',
                field : 'salPhone',
            }, {
                width : '40',
                title : '城市',
                field : 'area',
            }, {
                width : '70',
                title : '注册资金',
                field : 'regcapAmt',
            } , {
                width : '80',
                title : '开户名',
                field : 'actName',
            }, {
                width : '70',
                title : '开户行',
                field : 'actBank',
            }, {
                width : '80',
                title : '开户账号',
                field : 'actAccount',
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
            	 field : 'action',
                 title : '操作',
                 width : 240,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/agent/editAgent">
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editAgentFun(\'{0}\');" >编辑</a>', row.agentId);
                         </shiro:hasPermission>

                         <shiro:hasPermission name="/agent/deleteAgent">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteAgentFun(\'{0}\');" >删除</a>', row.agentId);
                         </shiro:hasPermission>
                        /*  <shiro:hasPermission name="/agent/editAgent">
                         str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryAgentFun(\'{0}\');" >查看</a>', row.agentId);
                         </shiro:hasPermission> */
                         <shiro:hasPermission name="/agent/addAgentUser">
                         str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-login" data-options="plain:true,iconCls:\'fi-plus icon-green\'" onclick="addAgentUserFun(\'{0}\');" >代理商授权登录</a>', row.agentId);
                         </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                 $('.product-easyui-linkbutton-login').linkbutton({text:'代理商授权登录'});
                 $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
                /*  $('.product-easyui-linkbutton-query').linkbutton({text:'查看'}); */
             },
            toolbar : '#agentToolbar'
        });
    });

    function addAgentFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 420,
            maximizable:true,
            href : '${path }/agent/addAgentPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = AgentA;
                    var gr = parent.$.modalDialog.handler.find('#agentAddForm');
                    gr.submit();
                }
            } ]
        });
    }
    
    function addAgentUserFun(agentId) {
        parent.$.modalDialog({
            title : '代理商授权',
            width : 800,
            height : 420,
            maximizable:true,
            href : '${path }/agent/agentUserPage?agentId=' + agentId,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = AgentA;
                    var gr = parent.$.modalDialog.handler.find('#agentUserForm');
                    gr.submit();
                }
            } ]
        });
    }


    function deleteAgentFun(agentId) {
        console.log(agentId);
       if (agentId == undefined) {//点击右键菜单才会触发这个
           var rows = roleDataGrid.datagrid('getSelections');
           agentId = rows[0].agentId;
       } else {//点击操作里面的删除图标会触发这个
    	   AgentA.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该代理商信息吗？', function(g) {
           if (g) {
               $.post('${path }/agent/deleteAgent', {
            	   agentId : agentId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       AgentA.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
   
   function editAgentFun(agentId) {
           parent.$.modalDialog({
               title : '编辑',
               width : 800,
               height : 420,
               href : '${path }/agent/editAgentPage?agentId=' + agentId,
               buttons : [ {
                   text : '编辑',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = AgentA;
                       var p = parent.$.modalDialog.handler.find('#agentUserForm');
                       p.submit();
                   }
               } ]
           });
   }
  //查看
   function queryAgentFun(agentId) {
           parent.$.modalDialog({
               title : '查看',
               width : 800,
               height : 420,
               href : '${path }/agent/queryAgentPage?agentId=' + agentId,
           });
   }
   
   	function searchAgent() {
		AgentA.datagrid('load', $.serializeObject($('#searchAgentForm')));
	}
	function cleanAgent() {
		$('#searchAgentForm input').val('');
		$("[name='agentStatus']").val('');
		AgentA.datagrid('load', {});
	}
   
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="AgentA" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchAgentForm">
			<table>
				<tr>
					<th>代理商编号:</th>
					<td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;</td>
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
					<td>&nbsp;&nbsp;&nbsp;</td>
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
<div id="agentToolbar" style="display: none;">
    <shiro:hasPermission name="/agent/addAgent">
        <a onclick="addAgentFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加代理商信息</a>
    </shiro:hasPermission> 
</div>

