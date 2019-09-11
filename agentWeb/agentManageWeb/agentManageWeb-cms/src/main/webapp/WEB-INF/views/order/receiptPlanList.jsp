<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var receipPlanDataGrid;
    $(function () {
        receipPlanDataGrid = $('#receipPlanListId').datagrid({
            url : '${path }/receiptPlan/list',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns : [ [ {
                title : '排单编号',
                field : 'PLAN_NUM'
            },{
                title : '排单日期',
                field : 'C_DATE'
            },{
                title : '订单编号',
                field : 'ORDER_ID'
            },{
                title : '订单日期',
                field : 'TIME'
            },{
                title : '业务平台编码',
                field : 'BUS_NUM'
            },{
                title : '代理商唯一编码',
                field : 'AG_UNIQ_NUM'
            },{
                title : '代理商名称',
                field : 'AG_NAME'
            },{
                title : '商品名称',
                field : 'PRO_NAME'
            },{
                title : '平台',
                field : 'PLATFORM_NAME'
            },{
                title : '机具类型',
                field : 'PRO_TYPE'
            },{
                title : '活动',
                field : 'ACTIVITY_NAME'
            },{
                title : '配货数量',
                field : 'PRO_NUM'
            },{
                title : '订货厂家',
                field : 'PRO_COM'
            },{
                title : '机型',
                field : 'MODEL'
            },{
                title : '已排单总数量',
                field : 'SEND_NUM'
            },{
                title : '排单数量',
                field : 'PLAN_PRO_NUM'
            },{
                title : '剩余未排单数量',
                field : 'RESIDUE'
            },{
                title : '收货人',
                field : 'ADDR_REALNAME'
            },{
                title : '省',
                field : 'NAME'
            },{
                title : '市',
                field : 'CITY'
            },{
                title : '区',
                field : 'DISTRICT'
            },{
                title : '收货人地址',
                field : 'ADDR_DETAIL'
            },{
                title : '收货人联系方式',
                field : 'ADDR_MOBILE'
            },{
                title : '邮编',
                field : 'ZIP_CODE'
            },{
                title : '地址备注',
                field : 'REMARK'
            },{
                title : '退货子订单编号',
                field : 'RETURN_ORDER_DETAIL_ID'
            },{
                title : '排单状态',
                field : 'PLAN_ORDER_STATUS',
                formatter : function(value, row, index) {
                    switch (value) {
                        case 1:
                            return '已排单';
                        case 2:
                            return '已发货';
                        case 3:
                            return '未发货';
                        case 4:
                            return '发货中';
                    }
                }
            },{
                field : 'action',
                title : '操作',
                width : 160,
                formatter : function(value, row, index) {
                    var str = '';
                    <shiro:hasPermission name="/receiptPlan/revocationPlanner">
                        if (row.PLAN_ORDER_STATUS==1) {
                            str += $.formatString('<a href="javascript:void(0)" class="receiptPlan-easyui-linkbutton-revoke" data-options="plain:true,iconCls:\'icon-undo\'" onclick="revokePlanner(\'{0}\',\'{1}\');">排单撤回</a>', row.PLAN_NUM,row.ORDER_ID);
                        }
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/receiptPlan/startShipping">
                        if (row.PLAN_ORDER_STATUS==1) {
                            str += $.formatString('<a href="javascript:void(0)" class="receiptPlan-easyui-linkbutton-shipping" data-options="plain:true,iconCls:\'icon-ok\'" onclick="shippingPlanner(\'{0}\',\'{1}\');">开始发货</a>', row.PLAN_NUM,row.ORDER_ID);
                        }
                    </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess: function (data) {
                $('.receiptPlan-easyui-linkbutton-revoke').linkbutton({text: '排单撤回'});
                $('.receiptPlan-easyui-linkbutton-shipping').linkbutton({text: '开始发货'});
            },
            toolbar : '#receiptPlanToolbar'
        });
    });

    function revokePlanner(PLAN_NUM, ORDER_ID) {
        parent.$.messager.confirm('询问', '确认撤回排单？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+"/receiptPlan/revocationPlanner?planNum="+PLAN_NUM+"&orderId="+ORDER_ID,
                    dataType: 'json',
                    success: function(msg) {
                        if (msg.ok) {
                            info(msg.msg);
                            searchPlan();
                        }
                    },
                    complete: function(XMLHttpRequest, textStatus) {
                    }
                });
            }
        });
    }

    function shippingPlanner(PLAN_NUM, ORDER_ID) {
        parent.$.messager.confirm('询问', '确认开始发货？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+"/receiptPlan/startShipping?planNum="+PLAN_NUM+"&orderId="+ORDER_ID,
                    dataType: 'json',
                    success: function(msg) {
                        info(msg.msg);
                        if (msg.ok) {
                            searchPlan();
                        }
                    },
                    complete: function(XMLHttpRequest, textStatus) {
                    }
                });
            }
        });
    }

    function searchPlan() {
        receipPlanDataGrid.datagrid('load', $.serializeObject($('#receipPlanQueryForm')));
    }

    function cleanPlan() {
        $('#receipPlanQueryForm input').val('');
        $("[name='PLAN_ORDER_STATUS']").val('');
        $("[name='proType']").val('');
        $("[name='proCom']").val('');
        $("[name='payMethod']").val('');
        $("[name='platform']").val('');

        receipPlanDataGrid.datagrid('load', {});
    }

    function exportData() {
        $('#receipPlanQueryForm').form({
            url : '${path }/receiptPlan/export',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#receipPlanQueryForm').submit();
    }

    //导出历史排单记录
    function exportHistory() {
        $('#receipPlanQueryForm').form({
            url : '${path }/receiptPlan/exportHistory',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#receipPlanQueryForm').submit();
    }

    function toPlannerList() {
        addTab({
            title : '排单',
            border : false,
            closable : true,
            fit : true,
            href:'${path}/planner/toPlannerList'
        });
    }
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="receipPlanListId" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 84px; overflow: hidden;background-color: #fff">
        <form id ="receipPlanQueryForm" method="post">
            <table>
                <tr>
                    <th width="80px">排单编号：</th>
                    <td><input name="PLAN_NUM" style="line-height:17px;border:1px solid #ccc;width:160px;"></td>
                    <th>排单状态：</th>
                    <td>
                        <select name="PLAN_ORDER_STATUS" style="width:100px;height:21px">
                            <option value="">--请选择--</option>
                            <option value="1">已排单</option>
                            <option value="2">已发货</option>
                            <option value="3">未发货</option>
                            <option value="4">发货中</option>
                        </select>
                    </td>
                    <th>订单编号：</th>
                    <td><input name="ORDER_ID" style="line-height:17px;border:1px solid #ccc;width:100px;"></td>
                    <th>活动名称:</th>
                    <td><input name="activityName" style="line-height:17px;border:1px solid #ccc;width: 100px;"></td>
                    <th>平台类型:</th>
                    <td>
                        <select name="platform" style="width:100px;height:17px">
                            <option value="">  ---请选择---  </option>
                            <c:forEach items="${platFormList}" var="platFormListItem">
                                <option value="${platFormListItem.platformNum}">${platFormListItem.platformName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>代理商唯一码:</th>
                    <td><input name="agUniqNum" style="line-height:17px;border:1px solid #ccc;width: 160px;"></td>
                    <th>机具类型：</th>
                    <td>
                        <select name="proType" style="width:100px;height:17px">
                            <option  value="">  ---请选择---  </option>
                            <c:forEach items="${modelType}" var="modelTypeItem"  >
                                <option value="${modelTypeItem.dItemvalue}">${modelTypeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>厂商：</th>
                    <td>
                        <select name="proCom" style="width:100px;height:17px">
                            <option  value="">  ---请选择---  </option>
                            <c:forEach items="${manufacturer}" var="manufacturerItem"  >
                                <option value="${manufacturerItem.dItemvalue}">${manufacturerItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>结算方式:</th>
                    <td>
                        <select name="payMethod" style="width:100px;height:17px">
                            <option  value="">  ---请选择---  </option>
                            <c:forEach items="${settlementType}" var="settlementTypeItem"  >
                                <option value="${settlementTypeItem.dItemvalue}">${settlementTypeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>机型:</th>
                    <td><input name="model" style="line-height:17px;border:1px solid #ccc;width: 100px;"></td>
                </tr>
                <tr>

                    <th>排单开始:</th>
                    <td>
                        <input name="beginTime" style="line-height:17px;border:1px solid #ccc;width: 100px;" class="easyui-datebox"/>
                    </td>
                    <th>结束时间:</th>
                    <td>
                        <input name="endTime"   style="line-height:17px;border:1px solid #ccc;width: 100px;" class="easyui-datebox" />
                    </td>
                    <th>代理商名称:</th>
                    <td><input name="agName" style="line-height:17px;border:1px solid #ccc;width: 160px;"></td>

                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchPlan();">查询</a>
                    </td>
                    <td>
                         <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanPlan();">清空</a>
                    </td>
                    <td>
                        <shiro:hasPermission name="/planner/toLeadPlanner">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportData();">导出排单信息</a>
                        </shiro:hasPermission>
                    </td>
                    <td>
                        <shiro:hasPermission name="/planner/toHistory">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportHistory();">导出历史排单记录</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="receiptPlanToolbar">
    <shiro:hasPermission name="/planner/plannerAdd">
        <a onclick="toPlannerList()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">排单</a>
    </shiro:hasPermission>
</div>
