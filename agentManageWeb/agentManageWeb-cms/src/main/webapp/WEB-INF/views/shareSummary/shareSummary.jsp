<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var ShareSummaryA;
    $(function() {
    	ShareSummaryA = $('#ShareSummaryA').datagrid({
            url : '${path }/shareSummary/shareSummaryList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'shareSummaryId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '100',
                title : '分润汇总ID',
                field : 'shareSummaryId',
            }, {
                width : '80',
                title : '所属代理商',
                field : 'agentId',
            }, {
                width : '100',
                title : '交易所属日期',
                field : 'transDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '100',
                title : '汇总日期',
                field : 'summaryDate',
                formatter :function(value, row, index){
                	var date = new Date(value);
                    return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
                }
            }, {
                width : '80',
                title : '交易总额',
                field : 'summaryAmt',
            } , {
                width : '90',
                title : '有效分润总额',
                field : 'summaryValidAmt',
            }, {
                width : '70',
                title : '分润金额',
                field : 'summaryShareAmt',
            }, {
                width : '90',
                title : '分润参考总额',
                field : 'summaryDemoAmt',
            }, {
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                         /* <shiro:hasPermission name="/shareSummary/editShareSummary">
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editShareSummaryFun(\'{0}\');" >编辑</a>', row.shareSummaryId);
                         </shiro:hasPermission> */

                         <shiro:hasPermission name="/shareSummary/deleteShareSummary">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteShareSummaryFun(\'{0}\');" >删除</a>', row.shareSummaryId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/shareSummary/editShareSummary">
                         str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryShareSummaryFun(\'{0}\');" >查看</a>', row.shareSummaryId);
                         </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 /* $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'}); */
                 $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
                 $('.product-easyui-linkbutton-query').linkbutton({text:'查看'});
             },
            toolbar : '#shareSummaryToolbar'
        });
    });

    function addShareSummaryFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 800,
            height : 320,
            href : '${path }/shareSummary/addShareSummaryPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = ShareSummaryA;
                    var gr = parent.$.modalDialog.handler.find('#shareSummaryAddForm');
                    gr.submit();
                }
            } ]
        });
    }


    function deleteShareSummaryFun(shareSummaryId) {
        console.log(shareSummaryId);
       if (shareSummaryId == undefined) {//点击右键菜单才会触发这个
           var rows = roleDataGrid.datagrid('getSelections');
           shareSummaryId = rows[0].shareSummaryId;
       } else {//点击操作里面的删除图标会触发这个
    	   ShareSummaryA.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该分润汇总信息吗？', function(g) {
           if (g) {
               $.post('${path }/shareSummary/deleteShareSummary', {
            	   shareSummaryId : shareSummaryId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       ShareSummaryA.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
   
   function editShareSummaryFun(shareSummaryId) {
           parent.$.modalDialog({
               title : '编辑',
               width : 800,
               height : 420,
               href : '${path }/shareSummary/editShareSummaryPage?shareSummaryId=' + shareSummaryId,
               buttons : [ {
                   text : '编辑',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = ShareSummaryA;
                       var p = parent.$.modalDialog.handler.find('#shareSummaryEditForm');
                       p.submit();
                   }
               } ]
           });
   }
  //查看
   function queryShareSummaryFun(shareSummaryId) {
           parent.$.modalDialog({
               title : '查看',
               width : 800,
               height : 420,
               href : '${path }/shareSummary/queryShareSummaryPage?shareSummaryId=' + shareSummaryId,
           });
   }
   
  
   	function searchShareSummary() {
	   ShareSummaryA.datagrid('load', $.serializeObject($('#searchShareSummaryForm')));
	}
	function cleanShareSummary() {
		$('#searchShareSummaryForm input').val('');
		ShareSummaryA.datagrid('load', {});
	}
   
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <table id="ShareSummaryA" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
	   <form id ="searchShareSummaryForm">
			<table>
				<tr>
					<th>分润汇总id:</th>
					<td><input name="shareSummaryId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>&nbsp;&nbsp;&nbsp;</td>
					<th>所属代理商:</th>
					<td><input name="agentId" style="line-height:17px;border:1px solid #ccc"></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchShareSummary();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanShareSummary();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="shareSummaryToolbar" style="display: none;">
    <shiro:hasPermission name="/shareSummary/addShareSummary">
        <a onclick="addShareSummaryFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加分润汇总信息</a>
    </shiro:hasPermission> 
</div>

