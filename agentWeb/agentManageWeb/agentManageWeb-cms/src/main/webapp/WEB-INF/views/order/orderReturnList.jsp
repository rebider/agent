<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var orderReturnList;
    $(function() {
        orderReturnList = $('#orderReturnList').datagrid({
            url : '${path }/order/return/orderReturnList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            queryParams: {
                1:1
                <shiro:hasPermission name="/agent/orderReturnList">,orderReturn:"all"</shiro:hasPermission>
            },
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '退货日期',
                field : 'ROTIME'
            },{
                title : '退货单日期',
                field : 'RETURNTIME'
            },{
                title : '退货单编号',
                field : 'ID'
            },{
                title : '原订单编号',
                field : 'ORDER_ID'
            },{
                title : '代理商唯一编码',
                field : 'AG_UNIQ_NUM'
            },{
                title : '代理商名称',
                field : 'AG_NAME'
            },{
                title : '平台',
                field : 'PLATFORM_NAME'
            },{
                title : '机具类型',
                field : 'PRO_TYPE'
            },{
                title : '厂家',
                field : 'VENDER'
            },{
                title : '机型',
                field : 'PRO_MODEL'
            },{
                title : '活动',
                field : 'ACTIVITY_NAME'
            },{
                title : '数量',
                field : 'RETURN_COUNT'
            },{
                title : '单价',
                field : 'ORDER_PRICE'
            },{
                title : '金额',
                field : 'RETURN_PRICE'
            },{
                title : '扣款',
                field : 'CUT_AMO'
            },{
                title : '退货开始sn号',
                field : 'BEGIN_SN'
            },{
                title : '退货结束sn号',
                field : 'END_SN'
            },{
                title : '退货进度',
                field : 'RET_SCHEDULE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 3:
                            return '发货中';
                        case 2:
                            return '待发货';
                        case 1:
                            return '审批中';
                        case 4:
                            return '退款中';
                        case 5:
                            return '完成';
                        case 6:
                            return '审批拒绝';
                        case 7:
                            return '驳回修改';
                        case 8:
                            return '已发货';
                    }
                }
            },{
                title : '创建时间',
                field : 'CTIME'
            },{
                title : '更新时间',
                field : 'UTIME'
            },{
                title : '新老订单',
                field : 'OX_ORDER',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "NE":
                            return '新订单';
                        case "NEW":
                            return '新订单';
                        case "OLD":
                            return '历史订单';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 100,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="activity-easyui-linkbutton-look" data-options="plain:true,iconCls:\'fi-magnifying-glass\'"  onclick="viewAgent(\'{0}\');" >查看</a>&nbsp;&nbsp;&nbsp;&nbsp;', row.ID);
                    return str;
                }
            } ] ],
            onLoadSuccess: function (data) {
                $('.activity-easyui-linkbutton-look').linkbutton({text: '查看'});
            },
            toolbar : '#returnorderToolbar'
        });
    });

    function applyReturn() {
//        parent.$.modalDialog.openner_dataGrid = orderReturnList;
        <%--parent.$.modalDialog({--%>
            <%--title : '申请退货',--%>
            <%--width : '75%',--%>
            <%--height : '60%',--%>
            <%--maximizable:true,--%>
            <%--href : '${path }/order/return/page/create',--%>
            <%--buttons : [ {--%>
                <%--text : '关闭',--%>
                <%--handler : function() {--%>
                    <%--parent.$.modalDialog.handler.dialog('close');--%>
                <%--}--%>
            <%--} ]--%>
        <%--});--%>
        addTab({
            title : '申请退货',
            border : false,
            closable : true,
            fit : true,
            href:'${path }/order/return/page/create'
        });
    }

    /**
	 *历史退货
	 **/
    function historyApplyReturn() {
        addTab({
            title : '历史退货',
            border : false,
            closable : true,
            fit : true,
            href:'${path}/oldorderreturn/apply'
        });
    }

    function viewAgent(id) {
        addTab({
            title : '退货详情',
            border : false,
            closable : true,
            fit : true,
            href:'${path}/order/return/page/viewAgentIndex?returnId='+id
        });
    }

   function searchRefund() {
       orderReturnList.datagrid('load', $.serializeObject($('#searchOrderForm_return')));
	}

	function cleanOrder_return() {
		$('#searchOrderForm_return input').val('');
        $("[name='platform']").val('');
        $("[name='proType']").val('');
        $("[name='vender']").val('');
        $("[name='payMethod']").val('');
        $("[name='retSchedule']").val('');
        orderReturnList.datagrid('load', {});
	}

    /**
	 * 导出退货
     */
    function exportReturn(){
        $('#searchOrderForm_return').form({
            url : '${path }/order/return/exportOrderReturn',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#searchOrderForm_return').submit();
    }

    /**
     * 导出退货单
     */
    function export_thd(){
        $('#searchOrderForm_return').form({
            url : '${path }/order/return/exportTHD',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#searchOrderForm_return').submit();
    }

    /**
     * 导出退转发明细
     */
    function export_returnForwardDetail() {
        $('#searchOrderForm_return').form({
            url: '${path}/order/return/exportRetForDetail',
            onSubmit: function() {
                return $(this).form('validate');
            }
        });
        $('#searchOrderForm_return').submit();
    }


    function applySplitReturn(){
        addTab({
            title : 'SN拆分',
            border : false,
            closable : true,
            fit : true,
            href:'/split/toUploadFileReturnOrderPage?type=returnOrder'
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
		<table id="orderReturnList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 75px; overflow: hidden;background-color: #fff">
	   <form id ="searchOrderForm_return"  method="post"  action="${path}/order/return/exportOrderReturn">
			<table>
				<tr>
					<th>代理商名称:</th>
					<td><input name="agName" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
					<th>退货单号:</th>
					<td><input name="id" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
					<th>活动名称:</th>
					<td><input name="activityName" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
					<th>平台类型:</th>
					<td>
						<select name="platform" style="width: 150px;height:21px">
							<option value="">  ---请选择---  </option>
							<c:forEach items="${platFormList}" var="platFormListItem">
								<option value="${platFormListItem.platformNum}">${platFormListItem.platformName}</option>
							</c:forEach>
						</select>
					</td>
					<th>机型:</th>
					<td><input name="proModel" style="line-height:17px;border:1px solid #ccc;width: 100px;"></td>
				</tr>
				<tr>
					<th>代理商唯一编码:</th>
					<td><input name="agUniqNum" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
					<th>机具类型：</th>
					<td>
						<select name="proType" style="width: 150px;height:21px">
							<option  value="">  ---请选择---  </option>
							<c:forEach items="${modelType}" var="modelTypeItem"  >
								<option value="${modelTypeItem.dItemvalue}">${modelTypeItem.dItemname}</option>
							</c:forEach>
						</select>
					</td>
					<th>厂商：</th>
					<td>
						<select name="vender" style="width:140px;height:21px">
							<option  value="">  ---请选择---  </option>
							<c:forEach items="${manufacturer}" var="manufacturerItem"  >
								<option value="${manufacturerItem.dItemvalue}">${manufacturerItem.dItemname}</option>
							</c:forEach>
						</select>
					</td>
					<th>结算方式:</th>
					<td>
						<select name="payMethod" style="width: 150px;height:21px">
							<option  value="">  ---请选择---  </option>
							<c:forEach items="${settlementType}" var="settlementTypeItem"  >
								<option value="${settlementTypeItem.dItemvalue}">${settlementTypeItem.dItemname}</option>
							</c:forEach>
						</select>
					</td>
					<th>退货状态:</th>
					<td>
						<select name="retSchedule" style="width: 150px;height:21px">
							<option  value="">---请选择---</option>
							<option  value="1">审批中</option>
							<option  value="2">待发货</option>
							<option  value="3">发货中</option>
							<option  value="4">退款中</option>
							<option  value="5">完成</option>
							<option  value="6">审批拒绝</option>
							<option  value="7">驳回修改</option>
							<option  value="8">已发货</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>退货开始~结束时间:</th>
					<td>
						<input name="beginTime" style="line-height:17px;border:1px solid #ccc;width: 100px;" class="easyui-datebox" />
						<input name="endTime"   style="line-height:17px;border:1px solid #ccc;width: 100px;" class="easyui-datebox" />
					</td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchRefund();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanOrder_return();">清空</a>
                    </td>
					<td colspan="7">
						<shiro:hasPermission name="/orderReturn/selforder">
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportReturn();">导出退货</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="/orderReturn/thd_finance">
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="export_thd();">导出退货单</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="/orderReturn/returnForwardDetail">
							<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="export_returnForwardDetail();">导出退转发明细</a>
						</shiro:hasPermission>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="returnorderToolbar">
	<%--<a onclick="applyReturn()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">申请退货</a>--%>
    <%--<shiro:hasPermission name="/orderReturn/historyApplyReturn">--%>
		<%--<a onclick="historyApplyReturn()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">历史退货</a>--%>
	<%--</shiro:hasPermission>--%>
	<shiro:hasPermission name="/split/toUploadFileReturnOrderPage">
		<a onclick="applySplitReturn()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">发起申请</a>
	</shiro:hasPermission>
</div>

