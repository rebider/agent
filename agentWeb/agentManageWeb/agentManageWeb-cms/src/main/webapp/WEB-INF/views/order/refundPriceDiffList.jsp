<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var refundPriceDiffList;
    $(function() {
        refundPriceDiffList = $('#refundPriceDiffList').datagrid({
            url : '${path }/compensate/refundPriceDiffList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            queryParams: {
                1:1
                <shiro:hasPermission name="/compensate/allData">,dataRole:"all"</shiro:hasPermission>
                <shiro:hasPermission name="/compensate/belowData">,dataRole:"below"</shiro:hasPermission>
            },
            columns : [ [{
                title : '补差价编号',
                field : 'ID'
            },{
                title : '代理商唯一编码',
                field : 'AGENT_ID'
            },{
                title : '代理商名称',
                field : 'AG_NAME'
//            },{
//                title : '申请补差价类型',
//                field : 'APPLY_COMP_NAME'
//            },{
//                title : '申请补差价金额',
//                field : 'APPLY_COMP_AMT'
//            },{
//                title : '实际补差价类型',
//                field : 'REL_COMP_NAME'
//            },{
//                title : '实际补差价金额',
//                field : 'REL_COMP_AMT'
//            },{
//                title : '机具扣除金额',
//                field : 'MACH_OWE_AMT'
            },{
                title : '扣除总金额',
                field : 'DEDUCT_AMT'
            },{
                title : '线下打款金额',
                field : 'BELOW_PAY_AMT'
            },{
                title : '分润抵扣金额',
                field : 'SHARE_DEDUCT_AMT'
            },{
                title : '收款时间',
                field : 'GATHER_TIME'
            },{
                title : '收款金额',
                field : 'GATHER_AMT'
            },{
                title : '申请备注',
                field : 'APPLY_REMARK'
            },{
                title : '审批状态',
                field : 'REVIEW_STATUS',
                formatter : function(value, row, index) {
                    return db_options.agStatusi_map[value]
                }
            },{
                title : '订单类型',
                field : 'ORDER_TYPE',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '新订单';
                        case 2:
                            return '历史订单';
                    }
                }
            },{
                title : '申请时间',
                field : 'S_TIME'
            },{
                title : '更新时间',
                field : 'U_TIME'
            }, {
                field : 'ACTION',
                title : '操作',
                width : 250,
                formatter : function(value, row, index) {
                    var str = '';
                    str += $.formatString('<a href="javascript:void(0)" class="priceDiff-easyui-linkbutton-see" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="seeCompensate(\'{0}\',\'{1}\');" >查看</a>', row.ID,row.REVIEW_STATUS);
					<shiro:hasPermission name="/compensate/refundPriceDiffEdit">
						if(row.REVIEW_STATUS==1)
						str += $.formatString('<a href="javascript:void(0)" class="priceDiff-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editCompensate(\'{0}\');" >编辑</a>', row.ID);
                    </shiro:hasPermission>
					<shiro:hasPermission name="/compensate/refundPriceDiffDel">
						if(row.REVIEW_STATUS==1)
						str += $.formatString('<a href="javascript:void(0)" class="priceDiff-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="delCompensate(\'{0}\');" >删除</a>', row.ID);
                    </shiro:hasPermission>
					<shiro:hasPermission name="/compensate/refundPriceDiffApp">
						if(row.REVIEW_STATUS==1)
						str += $.formatString('<a href="javascript:void(0)" class="priceDiff-easyui-linkbutton-sub" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="subCompensate(\'{0}\',\'{1}\');" >提交审批</a>', row.ID,row.ORDER_TYPE);
					</shiro:hasPermission>
                    return str;
                }
            }
            ]],
            onLoadSuccess:function(data){
                $('.priceDiff-easyui-linkbutton-see').linkbutton({text:'查看'});
                $('.priceDiff-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.priceDiff-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.priceDiff-easyui-linkbutton-sub').linkbutton({text:'提交审批'});
            },
            toolbar : '#compensateToolbar'
        });

    });

   function searchCompensate() {
       refundPriceDiffList.datagrid('load', $.serializeObject($('#searchCompensateForm')));
	}
	function cleanCompensate() {
        $("#applyBeginTime").datebox("setValue","");
        $("#applyEndTime").datebox("setValue","");
        $('#reviewStatus').combobox('clear');
        $("input[name='agentId']").val('');
        $("input[name='agentName']").val('');
        refundPriceDiffList.datagrid('load', $.serializeObject($('#searchCompensateForm')));
	}

    function RefreshCloudHomePageTab() {
        refundPriceDiffList.datagrid('reload');
    }

    function seeCompensate(id,reviewStatus) {
		addTab({
			title : '补差价-查看',
			border : false,
			closable : true,
			fit : true,
			href:'/compensate/refundPriceDiffQuery?id='+id+"&reviewStatus="+reviewStatus
		});
    }

    //订单构建
    <%--function compensateAmt(){--%>
        <%--addTab({--%>
            <%--title : '退补差价',--%>
            <%--border : false,--%>
            <%--closable : true,--%>
            <%--fit : true,--%>
            <%--href:'${path}/compensate/toCompensateAmtAddPage'--%>
        <%--});--%>
    <%--}--%>

    <%--function oldCompensateAmt(){--%>
        <%--addTab({--%>
            <%--title : '退补差价',--%>
            <%--border : false,--%>
            <%--closable : true,--%>
            <%--fit : true,--%>
            <%--href:'${path}/oldCompensate/toCompensateAmtAddPage'--%>
        <%--});--%>
    <%--}--%>

    function editCompensate(id) {
        addTab({
            title : '补差价-修改',
            border : false,
            closable : true,
            fit : true,
            href:'/compensate/toCompensateAmtEditPage?id='+id
        });
    }


    function subCompensate(compId,orderType) {
        if(compId=='' || compId==undefined){
			info("提交审批参数错误");
            return false;
		}
		var reqUrl;
        if(orderType==2){
            reqUrl = "/oldCompensate/startCompensate";
		}else{
            reqUrl = "/compensate/startCompensate";
		}
        parent.$.messager.confirm('询问', '确认要提交审批？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+reqUrl,
                    data:{
                        compId:compId
                    },
                    beforeSend : function() {
                        progressLoad();
                    },
                    success: function(data){
						info(data.msg);
                        refreshRefundPriceDiffList();
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        progressClose();
                    }
                });
            }
        });
    }

    function refreshRefundPriceDiffList() {
        refundPriceDiffList.datagrid('load', $.serializeObject($('#searchCompensateForm')));
    }

    function delCompensate(id) {
        parent.$.messager.confirm('询问', '确认要删除？', function (b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: "/compensate/compensateAmtDel",
                    dataType: 'json',
                    data: {busId: id},
                    success: function (data) {
                        info(data.msg);
                    },
                    complete: function (XMLHttpRequest, textStatus) {
                        refundPriceDiffList.datagrid('reload');
                    }
                });
            }
        });
    }

//    function showCompensateDialog(options) {
//        parent.$.modalDialog({
//            title: '代理商选择',
//            width: 800,
//            height: 500,
//            href: '/abusinfo/agentInfoSelectDialogViewTier'
//        });
//        parent.$.modalDialog.handler.par_callBack_options = options;
//    }
//
//    function showCompensate(item, data) {
//        if (item) {
//            addTab({
//                title: '退补差价',
//                border: false,
//                closable: true,
//                fit: true,
//                href: '/oldCompensate/toCompensateAmtAddPage?agentId='+item.id
//            });
//        }
//    }

    function applySplit(){
        addTab({
            title : 'SN拆分',
            border : false,
            closable : true,
            fit : true,
            href:'/split/toUploadFileCompensatePage?type=compensation'
        });
    }

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
		<table id="refundPriceDiffList" data-options="fit:true,border:false"></table>
	</div>
    <div data-options="region:'north',border:false" style="height: 60px; overflow: hidden;background-color: #fff">
	   <form id ="searchCompensateForm">
		   <shiro:hasPermission name="/compensate/allData">
			   <input name="dataRole" type="hidden" value="all">
		   </shiro:hasPermission>
		   <shiro:hasPermission name="/compensate/belowData">
			   <input name="dataRole" type="hidden" value="below">
		   </shiro:hasPermission>
			<table>
				<tr>
					<th>申请开始时间:</th>
					<td>
						<input class="easyui-datetimebox" name="applyBeginTime" id="applyBeginTime" style="width:150px">
					</td>
					<th>申请结束时间:</th>
					<td>
						<input class="easyui-datetimebox" name="applyEndTime" id="applyEndTime" style="width:150px">
					</td>
					<th>审核状态:</th>
					<td>
						<select  class="easyui-combobox" name="reviewStatus" id="reviewStatus" style="width:150px;">
							<option value="" >--全部--</option>
							<c:forEach var="status" items="${agStatusi}" >
								<option value="${status.dItemvalue}" >${status.dItemname}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>代理商唯一编码:</th>
					<td><input style="border:1px solid #ccc" name="agentId" type="text"></td>
					<th>代理商名称:</th>
					<td><input style="border:1px solid #ccc" name="agentName" type="text"></td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchCompensate();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanCompensate();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="compensateToolbar">
	<shiro:hasPermission name="/split/toUploadFileCompensatePage">
		<a onclick="applySplit()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">发起申请</a>
	</shiro:hasPermission>
</div>

