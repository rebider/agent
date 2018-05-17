<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var terminalDataGridD;
    var terminalProDataGridD;
    $(function() {
    	 $('#groupanddiv').click(function(){
    	    	var rows = terminalDataGridD.datagrid('getSelections');
    	    	if(null==rows){
    	    		return;
    	    	}
    	    	i = rows.length-1;
    	    	terminalId = rows[i].terminalId;
    	    	//console.info(rows[0]);
    	        terminalProDataGridD.datagrid('load', {
    	        	terminalId: terminalId
    	        });
    	    });
    	 
    	 terminalDataGridD = $('#terminalDataGridD').datagrid({
            url : '${path }/terminal/terminalList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'terminalId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '终端ID',
                field : 'terminalId',
            }, {
                width : '80',
                title : '终端编号',
                field : 'terminalCode',
            } ,  {
                width : '80',
                title : '终端名称',
                field : 'terminalName'
            } , {
                width : '80',
                title : '授权码',
                field : 'terminalAuth'
            } ,{
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/terminal/editTerminal">
                             str += $.formatString('<a href="javascript:void(0)" class="group-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editTerminalFun(\'{0}\');" >编辑</a>', row.terminalId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/terminal/deleteTerminal">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="group-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteTerminalFun(\'{0}\');" >删除</a>', row.terminalId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/terminal/editTerminal">
                         str += $.formatString('<a href="javascript:void(0)" class="group-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryTerminalFun(\'{0}\');" >查看</a>', row.terminalId);
                     	</shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.group-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                 $('.group-easyui-linkbutton-del').linkbutton({text:'删除'});
                 $('.group-easyui-linkbutton-query').linkbutton({text:'查看'});
             },
            toolbar : '#terminalbar'
        });
        
    	 terminalProDataGridD = $('#terminalProDataGridD').datagrid({
            url : '${path }/terminal/terminalPro_Link',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'terminalProductId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '130',
                title : '终端产品关联ID',
                field : 'terminalProductId',
            }, {
                width : '100',
                title : '终端ID',
                field : 'terminalId',
            }, {
                width : '100',
                title : '产品ID',
                field : 'parentProductId',
            }, {
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/terminal/editTerminalPro">
                             str += $.formatString('<a href="javascript:void(0)" class="link-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editTerminalProFun(\'{0}\');" >编辑</a>', row.terminalProductId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/terminal/deleteTerminalPro">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="link-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteTerminalProFun(\'{0}\');" >删除</a>', row.terminalProductId);
                         </shiro:hasPermission>
                         //查看
                         <shiro:hasPermission name="/terminal/editTerminalPro">
                         str += $.formatString('<a href="javascript:void(0)" class="link-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryTerminalProFun(\'{0}\');" >查看</a>', row.terminalProductId);
                     </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.link-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                 $('.link-easyui-linkbutton-del').linkbutton({text:'删除'});
                 $('.link-easyui-linkbutton-query').linkbutton({text:'查看'});
             },
            toolbar : '#terminalLinkbar'
        });
    });

    function addTerminalFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 220,
            href : '${path }/terminal/addTerminalPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = terminalDataGridD;
                    var gr = parent.$.modalDialog.handler.find('#terminalAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    function addterminalProLinkFun() {
    	var rows = terminalDataGridD.datagrid('getSelections');
    	if(rows!=undefined && rows != ''){
    		i = rows.length-1;
    		terminalId = rows[i].terminalId;
        	//alert(terminalId);
    	}else{
    		terminalId = 'null';
    	}
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 220,
            href : '${path }/terminal/addTerminal_Link?terminalId='+terminalId,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = terminalProDataGridD;
                    var gr = parent.$.modalDialog.handler.find('#terminalLinkAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    function deleteTerminalFun(terminalId) {
        console.log(terminalId);
       if (terminalId == undefined) {//点击右键菜单才会触发这个
           var rows = terminalDataGridD.datagrid('getSelections');
           terminalId = rows[0].terminalId;
       } else {//点击操作里面的删除图标会触发这个
    	   terminalDataGridD.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该产品组吗？', function(g) {
           if (g) {
               $.post('${path }/terminal/deleteTerminal', {
            	   terminalId : terminalId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       terminalDataGridD.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
    
    function deleteTerminalProFun(terminalProductId) {
        //console.log(groupId);
       if (terminalProductId == undefined) {//点击右键菜单才会触发这个
           var rows = terminalProDataGridD.datagrid('getSelections');
           terminalProductId = rows[0].terminalProductId;
       } else {//点击操作里面的删除图标会触发这个
    	   terminalProDataGridD.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该关系吗？', function(g) {
           if (g) {
               $.post('${path }/terminal/deleteTerminalPro', {
            	   terminalProductId : terminalProductId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       terminalProDataGridD.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
   
   function editTerminalFun(terminalId) {
           parent.$.modalDialog({
               title : '编辑',
               width : 400,
               height : 220,
               href : '${path }/terminal/editTerminalFun?terminalId=' + terminalId,
               buttons : [ {
                   text : '编辑',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = terminalDataGridD;
                       var p = parent.$.modalDialog.handler.find('#terminalEditForm');
                       p.submit();
                   }
               } ]
           });
   }
   //查看
   function queryTerminalFun(terminalId) {
           parent.$.modalDialog({
               title : '详情',
               width : 400,
               height : 220,
               href : '${path }/terminal/queryTerminalFun?terminalId=' + terminalId
           });
   }
   
   function editTerminalProFun(terminalProductId) {
       parent.$.modalDialog({
           title : '编辑',
           width : 400,
           height : 220,
           href : '${path }/terminal/editTerminalProFun?terminalProductId=' + terminalProductId,
           buttons : [ {
               text : '编辑',
               handler : function() {
                   parent.$.modalDialog.openner_dataGrid = terminalProDataGridD;
                   var p = parent.$.modalDialog.handler.find('#terminalLinkEditForm');
                   p.submit();
               }
           } ]
       });
	}
   //子查看
   function queryTerminalProFun(terminalProductId) {
       parent.$.modalDialog({
           title : '详情',
           width : 400,
           height : 220,
           href : '${path }/terminal/queryTerminalProFun?terminalProductId=' + terminalProductId

       });
       

	}
    
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="groupanddiv" data-options="region:'west',border:true,split:false,title:'终端组'"  style="width:45%;overflow:hidden;">
        <table id="terminalDataGridD" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'center',border:true,title:'终端产品关联信息'" >
        <table id="terminalProDataGridD" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="terminalbar" style="display: none;">
    <shiro:hasPermission name="/terminal/addTerminal">
        <a onclick="addTerminalFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加终端组</a>
    </shiro:hasPermission> 
</div>
<div id="terminalLinkbar" style="display: none;">
    <shiro:hasPermission name="/terminal/add_Link">
        <a onclick="addterminalProLinkFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加关系</a>
    </shiro:hasPermission> 
</div>
