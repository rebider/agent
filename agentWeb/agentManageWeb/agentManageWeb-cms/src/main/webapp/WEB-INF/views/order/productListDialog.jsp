<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var productList_Dialog;
    $(function() {
        productList_Dialog = $('#productList_Dialog').datagrid({
            url : '${path }/product/productListDialogList?proType=${data.proType}&proStatus=1',
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
            }
            ]],
			onDblClickRow:function(dataIndex,rowData){
                if(parent.$.modalDialog.handler.par_callBack_options && typeof parent.$.modalDialog.handler.par_callBack_options.callBack == 'function') {
                    parent.$.modalDialog.handler.par_callBack_options.callBack(rowData, parent.$.modalDialog.handler.par_callBack_options.data);
                }
                parent.$.modalDialog.handler.dialog('close');
            }
        });
    });

   function searchProduct() {
       productList_Dialog.datagrid('load', $.serializeObject($('#searchProductDialogForm')));
	}
	function cleanProduct() {
		$('#searchProductDialogForm input').val('');
        productList_Dialog.datagrid('load', {});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true,title:'商品平台列表'"  style="width:100%;overflow: hidden; ">
		<table id="productList_Dialog" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
	   <form id ="searchProductDialogForm">
		    <input type="hidden" name="proType" id="proType" value="<c:if test="data!=nlll">${data.proType}</c:if>">
		    <input type="hidden" name="proStatus" id="proStatus" value="1">
			<table>
				<tr>
					<th>商品编号:</th>
					<td>
						<input name="proCode" style="line-height:17px;border:1px solid #ccc">
					</td>
					<th>商品名称:</th>
					<td><input name="proName" style="line-height:17px;border:1px solid #ccc"></td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchProduct();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanProduct();">清空</a>
                    </td>
				</tr>
			</table>
		</form>
	</div>
</div>