<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var orderList;
    $(function() {
        orderList = $('#orderList').datagrid({
            url : '${path }/order/orderList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '订单号',
                field : 'O_NUM'
            },{
                title : '代理商唯一编码',
                field : 'AG_UNIQ_NUM'
            },{
				title : '平台',
				field : 'PLATFORM_NAME'
			},{
                title : '平台号',
                field : 'ORDER_PLATFORM'
            },{
                title : '订单编号',
                field : 'ID',
				hidden : true
            },{
                title : '代理商名称',
                field : 'AG_NAME'
            },{
                title : '业务平台编码',
                field : 'BUS_NUM'
            },{
                title : '对接大区',
                field : 'AG_DOC_DISTRICT_NAME'
            },{
                title : '对接省区',
                field : 'AG_DOC_PRO_NAME'
            },{
                title : '对接人',
                field : 'BUS_CONTACT_PERSON'
            },{
                title : '结算方式',
                field : 'PAY_METHOD',
                formatter : function(value, row, index) {
                    return db_options.settlementType_map[value]
                }
            }, {
                title : '支付方式',
                field : 'PAYMENT_METHOD',
                formatter : function(value, row, index) {
                    return db_options.settlementType_map[value]
                }
            },{
                title : '总金额',
                field : 'PAY_AMO',
                formatter : function(value, row, index) {
                    return value.toFixed(2)+'/￥';
                }
            },{
                title : '待付金额',
                field : 'OUTSTANDING_AMOUNT',
                formatter : function(value, row, index) {
                    return  value.toFixed(2)+'/￥';
                }
            },{
                title : '欠款',
                field : 'QK',
                formatter : function(value, row, index) {
					return  (value > 0)? ('<span style="color: red">'+value.toFixed(2)+'/￥</span>') :value.toFixed(2)+'/￥';
                }
            },{
                title : '本月欠款',
                field : 'BYQK',
                formatter : function(value, row, index) {
                    return  (value > 0)? ('<span style="color: red">'+value.toFixed(2)+'/￥</span>') :value.toFixed(2)+'/￥';
                }
            },{
                title : '待配货',
                field : 'DPD',
                hidden:true,
                formatter : function(value, row, index) {
                        return value+"件";
                }
            },{
                title : '审批状态',
                field : 'REVIEW_STATUS',
                formatter : function(value, row, index) {
                    return db_options.agStatusi_map[value]
                }
            },{
                title : '活动',
                field : 'ACTIVITY_NAME'
            },{
                title : '数量',
                field : 'PRO_NUM',
                formatter : function(value, row, index) {
                    return  value.toFixed(0)+'/件';
                }

            },{
                title : '订单日期',
                field : 'O_INURETIME',
			},{
                title : '新老订单',
                field : 'OX_ORDER',
                formatter : function(value, row, index) {
                    switch (value) {
                        case "NE":
                            return '新订单';
                        case "OLD":
                            return '历史订单';
                    }
                }
            },{
                title : '订单状态',
                field : 'ORDER_STATUS',
				hidden : true,
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '新建';
                        case 2:
                            return '生效';
                        case 3:
                            return '锁定状态';
                        case 4:
                            return '失效';
                        case 5:
                            return '已撤销';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 360,
                formatter : function(value, row, index) {
                    var str = '';
                    //查看
                    str += $.formatString('<a href="javascript:void(0)" class="order-easyui-linkbutton-look" data-options="plain:true,iconCls:\'fi-page\'" onclick="orderView(\'{0}\');" >查看</a>', row.ID);
                    if(row.REVIEW_STATUS==1 && row.USER_ID == mainLoginUserId){
                    	str += $.formatString('||<a href="javascript:void(0)" class="order-easyui-linkbutton-delete" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="deleteOrder(\'{0}\');" >删除</a>', row.ID);
                    }
                    <shiro:hasPermission name="/order/orderEidt">
					//新建
					if(row.REVIEW_STATUS==1 && row.USER_ID == mainLoginUserId){
                        str += $.formatString('||<a href="javascript:void(0)" class="order-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-torsos-all\'" onclick="orderViewStartActivity(\'{0}\');" >提交审批</a>', row.ID);
                        str += $.formatString('||<a href="javascript:void(0)" class="order-easyui-linkbutton-update" data-options="plain:true,iconCls:\'fi-pencil\'" onclick="xiugaiAction(\'{0}\',\'{1}\');" >修改</a>', row.ID,row.AGENTID);
//                        str += $.formatString('||<a href="javascript:void(0)" class="order-easyui-linkbutton-revocation" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="revocation(\'{0}\',\'{1}\');" >订单撤销</a>', row.ID);
					}
                    </shiro:hasPermission>
					<shiro:hasPermission name="/order/shipments">
                    //配货
                    if(row.REVIEW_STATUS==3 && row.USER_ID == mainLoginUserId  && row.TOTAL!=row.PRO_NUM){
                        str += $.formatString('||<a href="javascript:void(0)" class="order-easyui-linkbutton-peihuo" data-options="plain:true,iconCls:\'fi-folder-add icon-yellow\'" onclick="peihuo(\'{0}\',\'{1}\');" >配货</a>', row.ID,row.AGENTID);
                    }
                    </shiro:hasPermission>
					<shiro:hasPermission name="/order/sumpplement">
                    //是否欠款
                    if(row.QK!='0'){
                        str += $.formatString('||<a href="javascript:void(0)" class="order-easyui-linkbutton-BK" data-options="plain:true,iconCls:\'fi-yen  icon-yellow\'" onclick="bukuanAction(\'{0}\',\'{1}\');" >补款</a>', row.ID,row.AGENTID);
                    }
					</shiro:hasPermission>
					<shiro:hasPermission name="/order/logistics">
                    //物流
                    if(row.REVIEW_STATUS==3){
                        str += $.formatString('||<a href="javascript:void(0)" class="order-easyui-linkbutton-wuliu" data-options="plain:true,iconCls:\'fi-shopping-cart\'" onclick="wuliu(\'{0}\',\'{1}\');" >物流信息</a>', row.ID,row.AGENTID);
                    }
                    </shiro:hasPermission>
					<%--<shiro:hasPermission name="/order/revocation">--%>
					<%--//订单撤销--%>
					<%--if(row.REVIEW_STATUS==1 || row.REVIEW_STATUS==2 || row.ORDER_STATUS!=5){--%>
						<%--str += $.formatString('||<a href="javascript:void(0)" class="order-easyui-linkbutton-revocation" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="revocation(\'{0}\',\'{1}\');" >订单撤销</a>', row.ID);--%>
					<%--}--%>
                    <%--</shiro:hasPermission>--%>
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.order-easyui-linkbutton-look').linkbutton({text: '查看'});
                $('.order-easyui-linkbutton-delete').linkbutton({text: '删除'});
                $('.order-easyui-linkbutton-edit').linkbutton({text: '提交审批'});
                $('.order-easyui-linkbutton-BK').linkbutton({text: '补款'});
                $('.order-easyui-linkbutton-update').linkbutton({text: '修改'});
                $('.order-easyui-linkbutton-peihuo').linkbutton({text: '配货'});
                $('.order-easyui-linkbutton-wuliu').linkbutton({text: '物流'});
                $('.order-easyui-linkbutton-revocation').linkbutton({text: '订单撤销'});
            },
            toolbar : '#orderToolbar'
        });
    });

    //订单撤销
    function revocation(id) {
        parent.$.messager.confirm('询问', '确认撤销订单？撤销后不可恢复，需重新提交，退款问题线下联系公司财务！', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+" /order/revocationOrder?id="+id,
                    dataType:'json',
                    success: function(msg){
                        info(msg.msg);
                        if(msg.ok){
                            searchOrder();
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                    }
                });
            }
        });
    }

	//刪除
    function deleteOrder(id){
        parent.$.messager.confirm('询问', '确认要删除么？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+" /order/deleteOrder?id="+id,
                    dataType:'json',
                    success: function(msg){
                        info(msg.msg);
                        if(msg.ok){
                            searchOrder();
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                    }
                });
            }
        });
    }

   function searchOrder() {
       orderList.datagrid('load', $.serializeObject($('#searchOrderForm')));
	}

	function cleanOrder() {
		$('#searchOrderForm input').val('');
        $("[name='platform']").val('');
        $("[name='proType']").val('');
        $("[name='proCom']").val('');
        $("[name='payMethod']").val('');
        $("[name='reviewStatus']").val('');
        orderList.datagrid('load', {});
	}

    function RefreshCloudHomePageTab() {
        orderList.datagrid('reload');
    }

    //订单详情
    function orderView(id){
        addTab({
            title : '订单:'+id,
            border : false,
            closable : true,
            fit : true,
            href:'/orderbuild/orderApprView?orderId='+id
        });
	}

	//订单构建
    function buildOrder(){
        addTab({
            title : '代理商订货',
            border : false,
            closable : true,
            fit : true,
            href:'${path}/orderbuild/buildview'
        });
	}

	//提交审批
	function orderViewStartActivity(id){
        parent.$.messager.confirm('询问', '确认要提交审批？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "GET",
                    url: "/orderbuild/startOrderReview",
                    dataType:'json',
                    data: {orderId:id},
                    beforeSend:function(){
                        progressLoad();
					},
                    success: function(msg){
                        if(msg.status==200){
                            orderList.datagrid('load', $.serializeObject($('#searchOrderForm')));
						}
                        info(msg.msg);
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        orderList.datagrid('load', $.serializeObject($('#searchOrderForm')));
                        progressClose();
                    }
                });
            }
        });
	}

    /**
	 * 补款操作
     * @param id
     */
	function bukuanAction(id,agentId){
	    //根据订单信息查询需要补款的付款明细
        $.ajaxL({
            type: "GET",
            url: "/orderbuild/queryOrderForOSupplementPaymentdetail",
            dataType:'json',
            data: {orderId:id,agentId:agentId},
            beforeSend:function(){
                progressLoad();
            },
            success: function(msg){
                console.log(msg);
                if(msg.status==200){
                    addTab({
                        title: '补款审批申请',
                        border: false,
                        closable: true,
                        fit: true,
                        href: "${path}/supplement/supplementAddDialog?srcId="+msg.data.paymentDetails.id+"&pkType=1&remark=OrderNum-"+msg.data.order.oNum+"&agentId="+msg.data.order.agentId+"&payAmount="+msg.data.paymentDetails.payAmount
                    });
                }else{
                    info(msg.msg);
				}
            },
            complete:function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
	}

	function peihuo(id,agentId){
        //根据订单信息查询需要补款的付款明细
        addTab({
            title: '配货',
            border: false,
            closable: true,
            fit: true,
            href: "${path}/order/distributionView?orderId="+id+"&agentId="+agentId
        });


	}

    function wuliu(id,agentId){
        //根据订单信息查询需要补款的付款明细
        parent.$.modalDialog({
            title : '物流',
            width : 900,
            height : 500,
            href :  "${path}/logistics/orderLogisticsDialog?orderId="+id+"&agentId="+agentId
        });
        <%--addTab({--%>
        <%--title: '配货',--%>
        <%--border: false,--%>
        <%--closable: true,--%>
        <%--fit: true,--%>
        <%--href: "${path}/order/distributionView?orderId="+id+"&agentId="+agentId--%>
        <%--});--%>
    }

    function xiugaiAction(id,agentId){
        //根据订单信息查询需要补款的付款明细
        addTab({
            title: '订单修改',
            border: false,
            closable: true,
            fit: true,
            href: "${path}/order/updateOrderView?orderId="+id+"&agentId="+agentId
        });
    }

    function order_myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }

    function order_myparser(s){
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d);
        } else {
            return new Date();
        }
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
		<table id="orderList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 110px; overflow: hidden;background-color: #fff">
	   <form id ="searchOrderForm" action="${path }/order/exportOrder" method="post">
			<table>
				<tr>
					<%--<th>订单编号:</th>--%>
					<%--<td><input name="id" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>--%>
					<th>订单号:</th>
					<td><input name="oNum" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
					<th>代理商名称:</th>
					<td><input name="agName" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
					<th>代理商唯一编码:</th>
					<td><input name="agUniqNum" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
					<th>活动名称:</th>
					<td><input name="activityName" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
				</tr>
				<tr>
					<th>平台类型:</th>
					<td>
						<select name="platform" style="width:140px;height:21px">
							<option value="">  ---请选择---  </option>
							<c:forEach items="${platFormList}" var="platFormListItem">
								<option value="${platFormListItem.platformNum}">${platFormListItem.platformName}</option>
							</c:forEach>
						</select>
					</td>
					<th>机具类型：</th>
					<td>
						<select name="proType" style="width:140px;height:21px">
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
						<select name="payMethod" style="width:140px;height:21px">
							<option  value="">  ---请选择---  </option>
							<c:forEach items="${settlementType}" var="settlementTypeItem"  >
								<option value="${settlementTypeItem.dItemvalue}">${settlementTypeItem.dItemname}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>机型:</th>
					<td><input name="model" style="line-height:17px;border:1px solid #ccc;width: 50px;"></td>
					<th>业务编号:</th>
					<td><input name="busNum" style="line-height:17px;border:1px solid #ccc;width: 150px;"></td>
					<th>对接大区:</th>
					<td>
						<input type="text" id="agDocDistrict"  style="width:60%;" data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="${userOrg.ORGPID}" />">
						<input name="agDocDistrict" type="hidden" value="" id="agDocDistricts"/>
						<a href="javascript:void(0);"
						   onclick="showRegionFrame({target:this,callBack:returnNetInRegion_forordersel},'/region/departmentTree',false)">选择</a>
						<a href="javascript:void(0);" onclick="returnNetInRegion_forordersel_del(this)">清除</a>
						<script type="application/javascript">
                            //地区选择
                            function returnNetInRegion_forordersel(data,options){
                                $(options.target).prev("input").val(data.id);
                                $(options.target).prev("input").prev("input").val(data.text);
                            }
                            function  returnNetInRegion_forordersel_del(t) {
                                var inputs  = $(t).parent("td").find("input");
                                $(inputs).val("");
                                // $("[id='"+id+"']").val('');
                                // $("[id='"+ids+"']").val('');
                            }
						</script>
					</td>
					<th>对接省区:</th>
					<td colspan="3">
						<input type="text" id="agDocPro" style="width:60%;" data-options="required:true" readonly="readonly" value="<agent:show type="dept" busId="" />">
						<input name="agDocPro" type="hidden" value="" id="agDocPros"/>
						<a href="javascript:void(0);"onclick="showRegionFrame({target:this,callBack:returnNetInRegion_forordersel,pid:$(this).parent().prev('td').prev('td').children('input[name=\'agDocDistrict\']').val()},'/region/departmentTree',false)">选择</a>
						<a href="javascript:void(0);" onclick="returnNetInRegion_forordersel_del(this)">清除</a>
					</td>
				</tr>
				<tr>
					<th>下单时间:</th>
					<td>
						<input name="beginTime" style="line-height:17px;border:1px solid #ccc;width: 100px;" class="easyui-datebox" data-options="prompt:'下单开始时间',formatter:order_myformatter,parser:order_myparser,"/>
						<input name="endTime"   style="line-height:17px;border:1px solid #ccc;width: 100px;" class="easyui-datebox" data-options="prompt:'下单结束时间',formatter:order_myformatter,parser:order_myparser,"/>
					</td>
					<th>审批状态：</th>
					<td>
						<select name="reviewStatus" style="width:140px;height:21px">
							<option value="">  ---请选择---  </option>
							<c:forEach items="${agStatusList}" var="agStatusListItem" >
								<option value="${agStatusListItem.dItemvalue}">${agStatusListItem.dItemname}</option>
							</c:forEach>
						</select>
					</td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchOrder();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanOrder();">清空</a>
                    </td>
					<td>
						<shiro:hasPermission name="/order/exportOrder">
							<button type="button" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true," onclick="dc()">订单导出</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/order/exportWdhk">
							<button type="button" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true," onclick="dcwdhk()">导出我的核款</button>
						</shiro:hasPermission>
						<script type="application/javascript">
                            function dc(){
                                $("#searchOrderForm").attr("action","${path }/order/exportOrder");
                                $("#searchOrderForm").submit();
                            }
                            function dcwdhk(){
                              $("#searchOrderForm").attr("action","${path }/order/exportOrder?nuclearUser=1");
                                $("#searchOrderForm").submit();
                            }
						</script>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="orderToolbar">
	<%--订货--%>
	<shiro:hasPermission name="order_buildorder">
	<a onclick="buildOrder()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-folder-add icon-green'">订货</a>
	</shiro:hasPermission>
</div>

