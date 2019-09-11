<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var agent_orderList;
    $(function() {
        agent_orderList = $('#agent_orderList').datagrid({
            url : '${path }/order/agentOrderList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'ID',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [{
                title : '平台',
                field : 'PLATFORM_NAME'
            },{
                title : '代理商',
                field : 'AG_NAME'
            },{
                title : '订单号',
                field : 'O_NUM'
            },{
                title : '业务平台编码',
                field : 'BUS_NUM'
            },{
                title : '支付方式',
                field : 'PAYMENT_METHOD',
                formatter : function(value, row, index) {
                    return db_options.settlementType_map[value]
                }
            },{
                title : '应付金额',
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
                title : '待配货',
                field : 'DPD',
                formatter : function(value, row, index) {
                    return value+"件";
                }
            },{
                title : '活动',
                field : 'ACTIVITY_NAME'
            },{
                title : '审批状态',
                field : 'REVIEW_STATUS',
                formatter : function(value, row, index) {
                    return db_options.agStatusi_map[value]
                }
            },{
                title : '创建时间',
                field : 'C_TIME'
            },{
                title : '订单日期',
                field : 'O_INURETIME'
            },{
                field : 'action',
                title : '操作',
                width : 300,
                formatter : function(value, row, index) {
                    var str = '';
                    //查看
                    str += $.formatString('<a href="javascript:void(0)" class="agentorder-easyui-linkbutton-look" data-options="plain:true,iconCls:\'fi-page\'" onclick="orderView(\'{0}\');" >查看</a>', row.ID);
                    if(row.REVIEW_STATUS==1 && row.USER_ID == mainLoginUserId){
                        str += $.formatString('<a href="javascript:void(0)" class="agentorder-easyui-linkbutton-delete" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="agentDeleteOrder(\'{0}\');" >删除</a>', row.ID);
                    }
                    <shiro:hasPermission name="/order/agentOrderEidt">
                    //新建
                    if(row.REVIEW_STATUS==1 && row.USER_ID == mainLoginUserId){
                        str += $.formatString('||<a href="javascript:void(0)" class="agentorder-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-torsos-all\'" onclick="orderViewStartActivity(\'{0}\');" >提交审批</a>', row.ID);
                        str += $.formatString('||<a href="javascript:void(0)" class="agentorder-easyui-linkbutton-update" data-options="plain:true,iconCls:\'fi-pencil\'" onclick="xiugaiAction(\'{0}\',\'{1}\');" >修改</a>', row.ID,row.AGENTID);
                    }
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/order/agentShipments">
                    //配货
                    if(row.REVIEW_STATUS==3 && row.TOTAL!=row.PRO_NUM && row.USER_ID == mainLoginUserId){
                        str += $.formatString('||<a href="javascript:void(0)" class="agentorder-easyui-linkbutton-peihuo" data-options="plain:true,iconCls:\'fi-folder-add icon-yellow\'" onclick="peihuo(\'{0}\',\'{1}\');" >配货</a>', row.ID,row.AGENTID);
                    }
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/order/agentSumpplement">
                    //是否欠款
                    if(row.QK!='0'){
                        str += $.formatString('||<a href="javascript:void(0)" class="agentorder-easyui-linkbutton-BK" data-options="plain:true,iconCls:\'fi-yen  icon-yellow\'" onclick="bukuanAction(\'{0}\',\'{1}\');" >补款</a>', row.ID,row.AGENTID);
                    }
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/order/agentLogistics">
                    //物流
                    if(row.REVIEW_STATUS==3){
                        str += $.formatString('||<a href="javascript:void(0)" class="agentorder-easyui-linkbutton-wuliu" data-options="plain:true,iconCls:\'fi-shopping-cart\'" onclick="wuliu(\'{0}\',\'{1}\');" >物流信息</a>', row.ID,row.AGENTID);
                    }
                    </shiro:hasPermission>
                    return str;
                }
            },{
                title : '订单编号',
                field : 'ID',
                hidden:'true'
            }]],
            onLoadSuccess: function (data) {
                $('.agentorder-easyui-linkbutton-look').linkbutton({text: '查看'});
                $('.agentorder-easyui-linkbutton-edit').linkbutton({text: '提交审批'});
                $('.agentorder-easyui-linkbutton-BK').linkbutton({text: '补款'});
                $('.agentorder-easyui-linkbutton-update').linkbutton({text: '修改'});
                $('.agentorder-easyui-linkbutton-peihuo').linkbutton({text: '配货'});
                $('.agentorder-easyui-linkbutton-wuliu').linkbutton({text: '物流'});
                $('.agentorder-easyui-linkbutton-delete').linkbutton({text: '删除'});
            },
            toolbar : '#agentorderToolbar'
        });
    });

    function agent_searchOrder() {
        agent_orderList.datagrid('load', $.serializeObject($('#agent_searchOrderForm')));
    }

    function agent_cleanOrder() {
        $('#agent_searchOrderForm input').val('');
        agent_orderList.datagrid('load', {});
    }

    function RefreshCloudHomePageTab() {
        agent_orderList.datagrid('reload');
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
                            agent_orderList.datagrid('load', $.serializeObject($('#agent_searchOrderForm')));
                        }
                        info(msg.msg);
                    },
                    complete:function (XMLHttpRequest, textStatus) {
                        agent_orderList.datagrid('load', $.serializeObject($('#agent_searchOrderForm')));
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

    function all_order_myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }

    function all_order_myparser(s){
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

    //刪除
    function agentDeleteOrder(id){
        parent.$.messager.confirm('询问', '确认要删除么？', function(b) {
            if (b) {
                $.ajaxL({
                    type: "POST",
                    url: basePath+" /order/deleteOrder?id="+id,
                    dataType:'json',
                    success: function(msg){
                        info(msg.msg);
                        if(msg.ok){
                            agent_searchOrder();
                        }
                    },
                    complete:function (XMLHttpRequest, textStatus) {

                    }
                });
            }
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="" data-options="region:'west',border:true"  style="width:100%;overflow: hidden; ">
        <table id="agent_orderList" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 35px; overflow: hidden;background-color: #fff">
        <form id ="agent_searchOrderForm">
            <table>
                <tr>
                    <th>订单号:</th>
                    <td><input name="oNum" style="line-height:17px;border:1px solid #ccc;width: 80px;"></td>
                    <th>代理商名称:</th>
                    <td><input name="agName" style="line-height:17px;border:1px solid #ccc;width: 80px;"></td>
                    <th>下单时间:</th>
                    <td>
                        <input name="beginTime" style="line-height:17px;border:1px solid #ccc" class="easyui-datebox" data-options="prompt:'下单开始时间',formatter:all_order_myformatter,parser:all_order_myparser,"/>
                        <input name="endTime"   style="line-height:17px;border:1px solid #ccc" class="easyui-datebox" data-options="prompt:'下单结束时间',formatter:all_order_myformatter,parser:all_order_myparser,"/>
                    </td>
                    <td>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="agent_searchOrder();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="agent_cleanOrder();">清空</a>
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                    <td>
                        <shiro:hasPermission name="/orderall/exportOrderAgent">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportOrderFun();">导出订单信息</a>
                        </shiro:hasPermission>
                        </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="agentorderToolbar">
    <%--订货--%>
    <shiro:hasPermission name="order_buildorder">
        <a onclick="buildOrder()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-folder-add icon-green'">订货</a>
    </shiro:hasPermission>
</div>

