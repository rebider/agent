<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var StageDataGridD;
    $(function() {
    	StageDataGridD = $('#StageDataGridD').datagrid({
            url : '${path }/stage/stageList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'parentProductId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '130',
                title : '父产品编号',
                field : 'parentProductId',
            }, {
                width : '130',
                title : '子产品编号',
                field : 'subProductId',
            } , {
                width : '130',
                title : '逾期天数',
                field : 'overdueDays',
            } ,{
                width : '130',
                title : '五级分类',
                field : 'cStageType',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'O':
                        return '正常';
                    case 'Y':
                        return '关注';
                    case 'F':
                        return '次级';
                    case 'K':
                        return '可疑';
                    case 'S':
                        return '损失';
                    }
                }
            } ,{
            	 field : 'action',
                 title : '操作',
                 width : 200,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/stage/editStage">
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editStageFun(\'{0}\');" >编辑</a>', row.subProductId);
                         </shiro:hasPermission>

                         <shiro:hasPermission name="/stage/deleteStage">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteStageFun(\'{0}\');" >删除</a>', row.subProductId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/stage/editStage">
                         str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryStageFun(\'{0}\');" >查看</a>', row.subProductId);
                         </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                 $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
                 $('.product-easyui-linkbutton-query').linkbutton({text:'查看'});
             },
            toolbar : '#stageToolbar'
        });
    });

    function addstageFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 400,
            height : 220,
            href : '${path }/stage/addStagePage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = StageDataGridD;
                    var gr = parent.$.modalDialog.handler.find('#stageAddForm');
                    gr.submit();
                }
            } ]
        });
    }


    function deleteStageFun(subProductId) {
        console.log(subProductId);
       if (subProductId == undefined) {//点击右键菜单才会触发这个
           var rows = roleDataGrid.datagrid('getSelections');
           subProductId = rows[0].subProductId;
       } else {//点击操作里面的删除图标会触发这个
    	   StageDataGridD.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该五级分类吗？', function(g) {
           if (g) {
               $.post('${path }/stage/deleteStage', {
            	   subProductId : subProductId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       StageDataGridD.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
   
   function editStageFun(subProductId) {
           parent.$.modalDialog({
               title : '编辑',
               width : 400,
               height : 220,
               href : '${path }/stage/editStagePage?subProductId=' + subProductId,
               buttons : [ {
                   text : '编辑',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = StageDataGridD;
                       var p = parent.$.modalDialog.handler.find('#stageEditForm');
                       p.submit();
                   }
               } ]
           });
   }
  //查看
   function queryStageFun(subProductId) {
           parent.$.modalDialog({
               title : '查看',
               width : 400,
               height : 220,
               href : '${path }/stage/queryStagePage?subProductId=' + subProductId
           })
   }
   
   
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="StageDataGridD" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="stageToolbar" style="display: none;">
    <shiro:hasPermission name="/stage/addStage">
        <a onclick="addstageFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加五级分类</a>
    </shiro:hasPermission> 
</div>

