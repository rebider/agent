<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var ShareDetailA;
    $(function() {
    	ShareDetailA = $('#ShareDetailA').datagrid({
            url : '${path }/shareDetail/shareDetailList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'flowId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
            	width : '80',
                title : '分润明细ID',
                field : 'shareDetailId',
            }, {
            	width : '100',
                title : '流水ID',
                field : 'flowId',
            }, {
                width : '90',
                title : '所属代理商',
                field : 'agentId',
            }, {
                width : '70',
                title : '交易类型',
                field : 'transCode',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'A003':
                        return '还款';
                    case 'A011':
                        return '提前还款';
                    
                    }
                }
            }, {
                width : '80',
                title : '交易时间',
                field : 'transDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            } , {
                width : '60',
                title : '本金',
                field : 'loanAmt',
            }, {
                width : '90',
                title : '有效分润金额',
                field : 'shareValidAmt',
            }, {
                width : '80',
                title : '分润成本',
                field : 'shareCost',
            }, {
                width : '80',
                title : '分润比',
                field : 'shareScale',
            }, {
                width : '80',
                title : '分润金额',
                field : 'shareAmt',
            }, {
                width : '80',
                title : '分润参考金额',
                field : 'shareDemoAmt',
            }, {
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                        /*  <shiro:hasPermission name="/shareDetail/editShareDetail">
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editShareDetailFun(\'{0}\');" >编辑</a>', row.shareDetailId);
                         </shiro:hasPermission>
 */
                         <shiro:hasPermission name="/shareDetail/deleteShareDetail">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteShareDetailFun(\'{0}\');" >删除</a>', row.shareDetailId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/shareDetail/editShareDetail">
                         str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryShareDetailFun(\'{0}\');" >查看</a>', row.shareDetailId);
                         </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
               /*   $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'}); */
                 $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
                 $('.product-easyui-linkbutton-query').linkbutton({text:'查看'});
             },
            toolbar : '#shareDetailToolbar'
        });
    });

    function addShareDetailFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 420,
            href : '${path }/shareDetail/addShareDetailPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = ShareDetailA;
                    var gr = parent.$.modalDialog.handler.find('#shareDetailAddForm');
                    gr.submit();
                }
            } ]
        });
    }


    function deleteShareDetailFun(shareDetailId) {
        console.log(shareDetailId);
       if (shareDetailId == undefined) {//点击右键菜单才会触发这个
           var rows = roleDataGrid.datagrid('getSelections');
           shareDetailId = rows[0].shareDetailId;
       } else {//点击操作里面的删除图标会触发这个
    	   ShareDetailA.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该分润明细信息吗？', function(g) {
           if (g) {
               $.post('${path }/shareDetail/deleteShareDetail', {
            	   shareDetailId : shareDetailId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       ShareDetailA.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
   
   function editShareDetailFun(shareDetailId) {
           parent.$.modalDialog({
               title : '编辑',
               width : 800,
               height : 420,
               href : '${path }/shareDetail/editShareDetailPage?shareDetailId=' + shareDetailId,
               buttons : [ {
                   text : '编辑',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = ShareDetailA;
                       var p = parent.$.modalDialog.handler.find('#shareDetailEditForm');
                       p.submit();
                   }
               } ]
           });
   }
  //查看
   function queryShareDetailFun(shareDetailId) {
           parent.$.modalDialog({
               title : '查看',
               width : 800,
               height : 420,
               href : '${path }/shareDetail/queryShareDetailPage?shareDetailId=' + shareDetailId,
           });
   }
  
   	function searchShareDetail() {
   		ShareDetailA.datagrid('load', $.serializeObject($('#searchShareDetailForm')));
	}
	function cleanShareDetail() {
		$('#searchShareDetailForm input').val('');
		$("[name='transCode']").val('');
		ShareDetailA.datagrid('load', {});
	}
   
   
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="ShareDetailA" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchShareDetailForm">
			<table>
				<tr>
					<th>分润明细ID:</th>
					<td><input name="shareDetailId" style="line-height:17px;border:1px solid #ccc"></td>
					<th>交易类型:</th>
					<td>
						<select name="transCode" style="width:140px;height:21px" >
						  <option value="">-请选择-</option>
						  <option value="A003">还款</option>
						  <option value="A011">提前还款</option>
						</select>
					</td>
					<th>流水ID:</th>
					<td><input name="flowId" style="line-height:17px;border:1px solid #ccc"></td>
				</tr>
				<tr>
					<th>所属代理商:</th>
					<td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
					<th>开始时间:</th>
					<td><input name="startTime" class="easyui-datebox" placeholder="开始时间" /></td>
					<th>～～    结束时间:</th>
					<td><input name="endTime" class="easyui-datebox" placeholder="结束时间" /></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchShareDetail();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanShareDetail();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="shareDetailToolbar" style="display: none;">
    <shiro:hasPermission name="/shareDetail/addShareDetail">
        <a onclick="addShareDetailFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加分润明细信息</a>
    </shiro:hasPermission> 
</div>

