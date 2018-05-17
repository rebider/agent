<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var ShareStragegyA;
    $(function() {
    	ShareStragegyA = $('#ShareStragegyA').datagrid({
            url : '${path }/shareStragegy/shareStragegyList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'strategyId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '策略ID',
                field : 'strategyId',
            }, {
                width : '80',
                title : '执行金额起',
                field : 'strategyAmtSta',
            },  {
                width : '80',
                title : '执行金额止',
                field : 'strategyAmtEnd',
            },   {
                width : '80',
                title : '执行时间起',
                field : 'strategyTimeSta',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getHours() + ':' + date.getMinutes();
                }
            },   {
                width : '80',
                title : '执行时间止',
                field : 'strategyTimeEnd',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getHours() + ':' + date.getMinutes();
                }
            }, {
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
            }, {
                width : '90',
                title : '策略录入时间',
                field : 'insertDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            } , {
                width : '90',
                title : '策略更新时间',
                field : 'updateDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '90',
                title : '策略更新人员',
                field : 'updateUser',
            }, {
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
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/shareStragegy/editShareStragegy">
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editShareStragegyFun(\'{0}\');" >编辑</a>', row.strategyId);
                         </shiro:hasPermission>

                         <shiro:hasPermission name="/shareStragegy/deleteShareStragegy">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteShareStragegyFun(\'{0}\');" >删除</a>', row.strategyId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/shareStragegy/editShareStragegy">
                         str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryShareStragegyFun(\'{0}\');" >查看</a>', row.strategyId);
                         </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                 $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
                 $('.product-easyui-linkbutton-query').linkbutton({text:'查看'});
             },
            toolbar : '#shareStragegyToolbar'
        });
    });

    function addShareStragegyFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 320,
            href : '${path }/shareStragegy/addShareStragegyPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = ShareStragegyA;
                    var gr = parent.$.modalDialog.handler.find('#shareStragegyAddForm');
                    gr.submit();
                }
            } ]
        });
    }


    function deleteShareStragegyFun(strategyId) {
        console.log(strategyId);
       if (strategyId == undefined) {//点击右键菜单才会触发这个
           var rows = roleDataGrid.datagrid('getSelections');
           strategyId = rows[0].strategyId;
       } else {//点击操作里面的删除图标会触发这个
    	   ShareStragegyA.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该分润策略信息吗？', function(g) {
           if (g) {
               $.post('${path }/shareStragegy/deleteShareStragegy', {
            	   strategyId : strategyId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       ShareStragegyA.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
   
   function editShareStragegyFun(strategyId) {
           parent.$.modalDialog({
               title : '编辑',
               width : 800,
               height : 320,
               href : '${path }/shareStragegy/editShareStragegyPage?strategyId=' + strategyId,
               buttons : [ {
                   text : '编辑',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = ShareStragegyA;
                       var p = parent.$.modalDialog.handler.find('#shareStragegyEditForm');
                       p.submit();
                   }
               } ]
           });
   }
  //查看
   function queryShareStragegyFun(strategyId) {
           parent.$.modalDialog({
               title : '查看',
               width : 800,
               height : 320,
               href : '${path }/shareStragegy/queryShareStragegyPage?strategyId=' + strategyId,
           });
   }
   
	function searchShareStragegy() {
		ShareStragegyA.datagrid('load', $.serializeObject($('#searchShareStragegyForm')));
	}
	function cleanShareStragegy() {
		$('#searchShareStragegyForm input').val('');
		$("[name='strategyType']").val('');
		ShareStragegyA.datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="ShareStragegyA" data-options="fit:true,border:false"></table>
    </div>
     <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchShareStragegyForm">
			<table>
				<tr>
					<th>策略id:</th>
					<td><input name="strategyId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;</td>
					<th>策略类型:</th>
					<td>
						<select name="strategyType" style="width:140px;height:21px" >
						  <option value="">-请选择-</option>
						  <option value="A">息与费</option>
						  <option value="T">利息</option>
						  <option value="L">罚息</option>
						  <option value="K">利息罚息</option>
						</select>
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchShareStragegy();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanShareStragegy();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="shareStragegyToolbar" style="display: none;">
    <shiro:hasPermission name="/shareStragegy/addShareStragegy">
        <a onclick="addShareStragegyFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加分润策略信息</a>
    </shiro:hasPermission> 
</div>

