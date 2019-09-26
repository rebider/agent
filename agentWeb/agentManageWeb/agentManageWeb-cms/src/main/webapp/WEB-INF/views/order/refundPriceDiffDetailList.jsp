<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var refundPriceDiffDetailList;
    $(function() {
        refundPriceDiffDetailList = $('#refundPriceDiffDetailList').datagrid({
            url : '${path}/compensate/refundPriceDiffDetailList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '编号',
                field : 'id'
            },{
                title : '代理商ID',
                field : 'agentId'
            },{
                title : '前活动id',
                field : 'activityFrontId'
            },{
                title : '后活动id',
                field : 'activityRealId'
            },{
                title : '补差申请编号',
                field : 'refundPriceDiffId'
            },{
                title : '商品id',
                field : 'proId'
            },{
                title : '商品名称',
                field : 'proName'
            },{
                title : '变更数量',
                field : 'changeCount'
            },{
                title : '活动名称',
                field : 'activityName'
            },{
                title : '前价格',
                field : 'frontPrice'
            },{
                title : '价格',
                field : 'price'
            },{
                title : '开始SN',
                field : 'beginSn'
            },{
                title : '结束SN',
                field : 'endSn'
            },{
                title : '订单类型',
                field : 'orderType',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '新订单';
                        case 2:
                            return '历史订单';
                    }
                }
            },{
                title : '创建时间',
                field : 'cTime'
            },{
                title : '更新时间',
                field : 'uTime'
            },{
                title : '下发状态',
                field : 'sendStatus',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 0:
                            return '未下发';
                        case 1:
                            return '下发成功';
                        case 2:
                            return '下发失败';
                    }
                }
            },{
                title : '联动结果',
                field : 'sendMsg'
            }, {
                field : 'ACTION',
                title : '操作',
                width : 250,
                formatter : function(value, row, index) {
                    var str = '';
                    if(row.sendStatus==2)
                    str += $.formatString('<a href="javascript:void(0)" class="priceDiffDetail-easyui-linkbutton" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="manualSendBus(\'{0}\');" >重新发送业务系统</a>', row.id);
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.priceDiffDetail-easyui-linkbutton').linkbutton({text:'重新发送业务系统'});
            }
        });

    });

    function searchCompensateDetail() {
       refundPriceDiffDetailList.datagrid('load', $.serializeObject($('#searchCompensateDetailForm')));
	}

	function cleanCompensateDetail() {
        $("#searchCompensateDetailForm").find("input").val('');
        refundPriceDiffDetailList.datagrid('load', $.serializeObject($('#searchCompensateDetailForm')));
	}

    function manualSendBus(id) {
        parent.$.messager.confirm('询问', '确认要手动通知？', function(b) {
            if (b) {
                $.ajaxL({
                    url: "${path}/compensate/manualDispose",
                    type: 'POST',
                    dataType:'json',
                    data: {
                        busId: id
                    },
                    beforeSend:function(){
                        progressLoad();
                    },
                    success:function(result){
                        info(result.msg);
                    },
                    complete:function(){
                        progressClose();
                    }
                });
            }
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
		<table id="refundPriceDiffDetailList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 40px; overflow: hidden;background-color: #fff">
	   <form id ="searchCompensateDetailForm">
			<table>
				<tr>
					<th>代理商编号:</th>
					<td><input style="border:1px solid #ccc" name="agentId" type="text"></td>
					<th>代理商名称:</th>
					<td><input style="border:1px solid #ccc" name="agentName" type="text"></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchCompensateDetail();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanCompensateDetail();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>