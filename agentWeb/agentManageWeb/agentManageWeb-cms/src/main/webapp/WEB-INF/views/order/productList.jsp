<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var productList;
    $(function() {
        productList = $('#productList').datagrid({
            url : '${path }/product/productList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '商品编号',
                field : 'proCode'
            },{
                title : '商品名称',
                field : 'proName'
            },{
                title : '机具类型',
                field : 'proType'
            },{
                title : '创建人',
                field : 'cUser'
            },{
                title : '更新人',
                field : 'uUser'
            },{
                title : '状态',
                field : 'proStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '禁用';
                        case 1:
                            return '启用';
                    }
                }
            },{
                title : '创建时间',
                field : 'cTime'
            },{
                title : '更新时间',
                field : 'uTime'
            }, {
                field : 'action',
                title : '操作',
                width : 130,
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/product/productEdit">
                    str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editProduct(\'{0}\');" >编辑</a>', row.id);
                    </shiro:hasPermission>
//                    str += $.formatString('<a href="javascript:void(0)" class="product-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="delProduct(\'{0}\');" >删除</a>', row.id);
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.product-easyui-linkbutton-edit').linkbutton({text:'编辑'});
//                $('.product-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#productToolbar'
        });

    });

   function searchProduct() {
       productList.datagrid('load', $.serializeObject($('#searchProductForm')));
	}
	function cleanProduct() {
		$('#searchProductForm input').val('');
        $("[name='proType']").val('');
        productList.datagrid('load', $.serializeObject($('#searchProductForm')));
	}

    function RefreshCloudHomePageTab() {
        productList.datagrid('reload');
    }

    function addProduct() {
        parent.$.modalDialog({
            title : '添加',
            width : 600,
            height : 300,
            maximizable:true,
            href : '${path }/product/toProductAdd',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productList;
                    var gr = parent.$.modalDialog.handler.find('#productAddForm');
                    gr.submit();
                }
            } ]
        });
    }

    function editProduct(id) {
        parent.$.modalDialog({
            title : '编辑',
            width : 600,
            height : 300,
            maximizable:true,
            href : '${path }/product/toProductEdit?id='+id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = productList;
                    var gr = parent.$.modalDialog.handler.find('#productEditForm');
                    gr.submit();
                }
            } ]
        });
    }

    function delProduct(id) {
        parent.$.messager.confirm('询问', '确认要删除？', function(b) {
            if (b) {
				$.ajax({
					url :"${path}/product/productDel",
					type:'POST',
					data:{
						id:id
					},
					dataType:'json',
					success:function(result){
						if (result.success) {
							productList.datagrid('reload');
							parent.$.messager.alert('提示', result.msg, 'info');
						} else {
							parent.$.messager.alert('错误', result.msg, 'error');
						}
					},
					error:function(data){
						alertMsg("系统异常，请联系管理员！");
					}
				});
            }
        });
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
		<table id="productList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
	   <form id ="searchProductForm">
			<table>
				<tr>
					<th>商品编号:</th>
					<td><input name="proCode" style="line-height:17px;border:1px solid #ccc"></td>
					<th>商品名称:</th>
					<td><input name="proName" style="line-height:17px;border:1px solid #ccc"></td>
					<th>机具类型：</th>
					<td>
						<select name="proType" style="width:140px;height:21px">
							<option value="">  ---请选择---  </option>
							<c:forEach items="${modelType}" var="modelTypeItem">
								<option value="${modelTypeItem.dItemvalue}">${modelTypeItem.dItemname}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProduct();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanProduct();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="productToolbar">
	<shiro:hasPermission name="/product/productAdd">
		<a onclick="addProduct()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加商品</a>
	</shiro:hasPermission>
	<%--<a href="javascript:void(0);" onclick="showProductSelectDialog({data:{platform:'2000'},callBack:function(item,data){console.log(item)}})" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加商品弹框</a>--%>
</div>

