<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var productDataGridD;
    var productAndGroup;
    $(function() {
    	 $('#groupanddivs').click(function(){
    	    	var rows = productDataGridD.datagrid('getSelections');
    	    	if(null==rows){
    	    		return;
    	    	}
    	    	i = rows.length-1;
    	    	productGroupId = rows[i].productGroupId;
    	    	//console.info(rows[0]);
    	        productAndGroup.datagrid('load', {
    	            id: productGroupId
    	        });
    	    });
    	 
        productDataGridD = $('#productDataGridD').datagrid({
            url : '${path }/product/productGroupsList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'groupId',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '130',
                title : '产品组编码',
                field : 'productGroupId',
            }, {
                width : '100',
                title : '产品组名称',
                field : 'productName',
            } ,  {
                width : '100',
                title : '产品组类型',
                field : 'productGroupType',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 'N':
                        return '正常';
                    case 'A':
                        return '优惠活动';
                    case 'O':
                        return '其他';
                    }
                }
            } , {
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/product/edit">
                             str += $.formatString('<a href="javascript:void(0)" class="group-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editProductGroupsFun(\'{0}\');" >编辑</a>', row.groupId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/product/delete">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="group-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteProductGroupsFun(\'{0}\');" >删除</a>', row.productGroupId);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/product/edit">
                         str += $.formatString('<a href="javascript:void(0)" class="group-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryProductGroupsFun(\'{0}\');" >查看</a>', row.groupId);
                     	</shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.group-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                 $('.group-easyui-linkbutton-del').linkbutton({text:'删除'});
                 $('.group-easyui-linkbutton-query').linkbutton({text:'查看'});
             },
            toolbar : '#productGroupbar'
        });
        
        productAndGroup = $('#productAndGroup').datagrid({
            url : '${path }/product/dataGrid_Link',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '130',
                title : '产品组编码',
                field : 'parentProductGroup',
            }, {
                width : '100',
                title : '产品编号',
                field : 'parentProductId',
            }, {
                width : '100',
                title : '创建时间',
                field : 'createTime',
            }, {
                width : '100',
                title : '更新时间',
                field : 'updateTime',
            }, {
            	 field : 'action',
                 title : '操作',
                 width : 180,
                 formatter : function(value, row, index) {
                     var str = '';
                         <shiro:hasPermission name="/product/edit_Link">
                             str += $.formatString('<a href="javascript:void(0)" class="link-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editProductLinkFun(\'{0}\');" >编辑</a>', row.id);
                         </shiro:hasPermission>
                         <shiro:hasPermission name="/product/delete_Link">
                             str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                             str += $.formatString('<a href="javascript:void(0)" class="link-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteProductLinkFun(\'{0}\');" >删除</a>', row.id);
                         </shiro:hasPermission>
                         //查看
                         <shiro:hasPermission name="/product/edit_Link">
                         str += $.formatString('<a href="javascript:void(0)" class="link-easyui-linkbutton-query" data-options="plain:true,iconCls:\'fi-magnifying-glass icon-blue\'" onclick="queryProductLinkFun(\'{0}\');" >查看</a>', row.id);
                     </shiro:hasPermission>
                     return str;
                 }
             } ] ],
             onLoadSuccess:function(data){
                 $('.link-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                 $('.link-easyui-linkbutton-del').linkbutton({text:'删除'});
                 $('.link-easyui-linkbutton-query').linkbutton({text:'查看'});
             },
            toolbar : '#productLinkbar'
        });
    });

    function addProductGroupsFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 450,
            height : 250,
            href : '${path }/product/addProductGroupsPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productDataGridD;
                    var gr = parent.$.modalDialog.handler.find('#productGroupsAddForm');
                    gr.submit();
                    
                }
            } ]
        });
    }

    function addProductLinkFun() {
    	var rows = productDataGridD.datagrid('getSelections');
    	if(rows!=undefined && rows != ''){
    		i = rows.length-1;
    		productGroupId = rows[i].productGroupId;
        	//alert(productId);
    	}else{
    		productGroupId = 'null';
    	}
        parent.$.modalDialog({
            title : '添加',
            width : 450,
            height : 250,
            href : '${path }/product/addPage_Link?id='+productGroupId,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productAndGroup;
                    var gr = parent.$.modalDialog.handler.find('#productLinkAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    function deleteProductGroupsFun(productGroupId) {
        console.log(productGroupId);
       if (productGroupId == undefined) {//点击右键菜单才会触发这个
           var rows = productDataGridD.datagrid('getSelections');
           productGroupId = rows[0].productGroupId;
       } else {//点击操作里面的删除图标会触发这个
    	   productDataGridD.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该产品组吗？', function(g) {
           if (g) {
               $.post('${path }/product/deleteProductGroups', {
            	   productGroupId : productGroupId
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       productDataGridD.datagrid('reload');
                       productAndGroup.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
    
    function deleteProductLinkFun(id) {
        //console.log(groupId);
       if (id == undefined) {//点击右键菜单才会触发这个
           var rows = productAndGroup.datagrid('getSelections');
           id = rows[0].id;
       } else {//点击操作里面的删除图标会触发这个
    	   productAndGroup.datagrid('unselectAll').datagrid('uncheckAll');
       }
       parent.$.messager.confirm('询问', '您是否要删除该关系吗？', function(g) {
           if (g) {
               $.post('${path }/product/delete_Link', {
            	   id : id
               }, function(result) {
                   if (result.success) {
                       parent.$.messager.alert('提示', result.msg, 'info');
                       productAndGroup.datagrid('reload');
                   }
               }, 'JSON');
           }
       });
   }
   
   function editProductGroupsFun(groupId) {
           parent.$.modalDialog({
               title : '编辑',
               width : 500,
               height : 420,
               href : '${path }/product/editProductGroupsPage?groupId=' + groupId,
               buttons : [ {
                   text : '编辑',
                   handler : function() {
                       parent.$.modalDialog.openner_dataGrid = productDataGridD;
                       var p = parent.$.modalDialog.handler.find('#productGroupEditForm');
                       p.submit();
                   }
               } ]
           });
   }
   //查看
   function queryProductGroupsFun(groupId) {
           parent.$.modalDialog({
               title : '详情',
               width : 400,
               height : 200,
               href : '${path }/product/queryProductGroupsPage?groupId=' + groupId
           });
   }
   
   function editProductLinkFun(id) {
       parent.$.modalDialog({
           title : '编辑',
           width : 500,
           height : 420,
           href : '${path }/product/editPage_Link?id=' + id,
           buttons : [ {
               text : '编辑',
               handler : function() {
                   parent.$.modalDialog.openner_dataGrid = productAndGroup;
                   var p = parent.$.modalDialog.handler.find('#productLinkEditForm');
                   p.submit();
               }
           } ]
       });
	}
   //子查看
   function queryProductLinkFun(id) {
       parent.$.modalDialog({
           title : '详情',
           width : 500,
           height : 420,
           href : '${path }/product/queryPage_Link?id=' + id

       });
       

	}
    
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="groupanddivs" data-options="region:'west',border:true,split:false,title:'产品组'"  style="width:45%;overflow:hidden;">
        <table id="productDataGridD" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'center',border:true,title:'产品组关联产品信息'" >
        <table id="productAndGroup" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="productGroupbar" style="display: none;">
    <shiro:hasPermission name="/product/addProductGroups">
        <a onclick="addProductGroupsFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加产品组</a>
    </shiro:hasPermission> 
</div>
<div id="productLinkbar" style="display: none;">
    <shiro:hasPermission name="/product/add_Link">
        <a onclick="addProductLinkFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加关系</a>
    </shiro:hasPermission> 
</div>
