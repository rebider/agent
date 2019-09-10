<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div id="plannerListToolbar_return" style="display: none;">
	<form  method="post" action="" id ="plannerList_searchform_return" >
		<table>
			<tr>
				<td>订单编号:</td>
				<td><input name="orderId" style="line-height:17px;border:1px solid #ccc" type="text"></td>
				<td>订单子编号:</td>
				<td><input  style="border:1px solid #ccc" name="receiptNum" type="text"></td>
				<td>收货人姓名:</td>
				<td><input  style="border:1px solid #ccc" name="addrRealname" type="text"></td>
				<td>
					<a  class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchPlannerList_return()">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanPlannerList_return();">清空</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div  class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',fit:true,border:false">
		<table id="plannerList_return" data-options="fit:true,border:false"></table>
	</div>
</div>
<script type="text/javascript">
    var plannerList_return;
    $(function() {
        //代理商表格
        plannerList_return = $('#plannerList_return').datagrid({
            url : '${path}/planner/plannerList?returnDetailsId=${returnDetailsId}',
            rownumbers : true,
            striped : true,
            pagination : true,
            iconCls:'icon-edit',
            singleSelect : true,
            editors:$.fn.datagrid.defaults.editors,
            idField : 'id',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '订单编号',
                field : 'ORDER_ID',
                sortable : true
            } , {
                title : '子订单编号',
                field : 'RECEIPT_NUM',
                sortable : true
            },{
                title : '代理商ID',
                field : 'AGENT_ID',
                sortable : true
            },{
                title : '代理商',
                field : 'AG_NAME',
                sortable : true
            } ,{
                title : '收货姓名',
                field : 'ADDR_REALNAME',
                sortable : true
            } ,{
                title : '商品名称',
                field : 'PRO_NAME',
                sortable : true
            }  ,{
                title : '订货量',
                field : 'PRO_NUM',
                sortable : true
            }  ,{
                title : '已排量',
                field : 'SEND_NUM',
                sortable : true
            },{
                title : '活动名称',
                field : 'ACTIVITY_NAME'
            }  /*,{
                field : 'action',
                title : '操作',
                width : 100,
                formatter : function(value, row, index) {
                    var str = '';
                    str += '<a href="javascript:void(0)" class="plannerList-look-easyui-linkbutton-add-return" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="selectRw();">选择</a>';
                    return str;
                }
            }*/  ] ],
            onLoadSuccess:function(data){
                //$('.plannerList-look-easyui-linkbutton-add-return').linkbutton({text:'选择'});
                //for (var i in data.rows){
                //    plannerList_return.datagrid("beginEdit",i);
                //}
            },
            onDblClickRow: function (rowIndex, rowData) {
                selectRw(rowData);
            },
            onBeforeEdit:function(index,row){
                row.editing = true;
            },
            onAfterEdit:function(index,row){
                row.editing = false;
//                saveRowAction(row);
            },
            onCancelEdit:function(index,row){
                row.editing = false;
            },
            toolbar : '#plannerListToolbar_return'
        });
    });

    /**
     * 搜索事件
     */
    function searchPlannerList_return() {
        plannerList_return.datagrid('load', $.serializeObject($('#plannerList_searchform_return')));
    }

    function cleanPlannerList_return() {
        $('#plannerList_searchform_return input').val('');
        plannerList_return.datagrid('load', {});
    }

    function selectRw(rowData) {
        if (rowData){
            console.log(rowData);
            //alert('Item ID:'+JSON.stringify(rowData));
			console.log(parent.$.modalDialog.openner_dataGrid);
            parent.$.modalDialog.openner_dataGrid.datagrid('insertRow',{//在右边插入新行。
                index : 0,
                row : rowData
            });
            parent.$.modalDialog.handler.dialog('close');
        }
    }
</script>
