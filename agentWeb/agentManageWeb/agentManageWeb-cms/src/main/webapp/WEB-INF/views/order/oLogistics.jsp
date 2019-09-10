<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<script type="text/javascript">
    var logisticsA;
    $(function() {
        logisticsA = $('#logisticsId').datagrid({
            url : '${path }/logistics/oLogisticsList',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            fit : true,
            idField : 'id',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                title : '订单编号',
                field : 'ORDER_ID'
            },{
                title : '代理商唯一编码',
                field : 'AG_UNIQ_NUM'
            },{
                title : '平台',
                field : 'PLATFORM_NAME'
            },{
                title : '代理商名称',
                field : 'AG_NAME'
            },{
                title : '商品编号',
                field : 'PRO_CODE'
            },{
                title : '商品名称',
                field : 'PRO_NAME'
            },{
                title : '订货数量',
                field : 'PRO_NUM'
            },{
                title : '已排单总数量',
                field : 'SEND_NUM'
            },{
                title : '订货厂家',
                field : 'PRO_COM'
            },{
                title : '排单数量',
                field : 'PLAN_PRO_NUM'
            },{
                title : '发货数量',
                field : 'O_LOGISTICS_SEND_NUM'
            },{
                title : '单价',
                field : 'PRO_PRICE'
            },{
                title : '金额',
                field : 'PROPRICE'
            },{
                title : '机型',
                field : 'MODEL'
            },{
                title : '机具类型',
                field : 'PRO_TYPE'
            },{
                title : '活动',
                field : 'ACTIVITY_NAME'
            },{
                title : '发货日期',
                field : 'O_LOGISTICS_SEND_DATE'
            },{
                title : '退货子订单编号',
                field : 'RETURN_ORDER_DETAIL_ID'
            },{
                title : '物流公司',
                field : 'LOG_COM'
            },{
                title : '物流类型',
                field : 'LOG_TYPE',
                hidden:true,
                formatter : function(value, row, index) {
                    switch (value) {
                        case "2":
                            return '退货物流';
                        case "1":
                            return '发货物流';
                    }
                }
            },{
                title : '物流单号',
                field : 'W_NUMBER'
            },{
                title : '起始SN序列号',
                field : 'SN_BEGIN_NUM'
            },{
                title : '结束SN序列号',
                field : 'SN_END_NUM'
            },{
                title : '收货人',
                field : 'ADDR_REALNAME'
            },{
                title : '收货地址',
                field : 'ADDR_DETAIL'
            },{
                title : '收货人联系方式',
                field : 'ADDR_MOBILE'
            },{
                title : '订单日期',
                field : 'TIME'
            },{
                title : '发货日期',
                field : 'SENDTIME'
            },{
                title : '联动状态',
                field : 'SEND_STATUS',
                formatter : function(value, row, index) {
                    switch (value) {
                        case -1:
                            return '无需联动';
                        case 0:
                            return '未联动业务系统';
                        case 1:
                            return '联动业务系统成功';
                        case 2:
                            return '联动业务系统失败';
                        case 3:
                            return '部分联动失败';
                        case 4:
                            return '生成明细失败';
                        case 5:
                            return '生成明细中';
                        case 6:
                            return '生成明细成功';
                        case 7:
                            return '联动业务系统处理中';
                    }
                }
            },{
                title : '联动结果',
                field : 'SEND_MSG'
            },{
                field : 'action',
                title : '操作',
                width : 300,
                formatter : function(value, row, index) {
                    var str = '';
                    //查看
                    if(row.SEND_STATUS==2){
                        str += $.formatString('<a href="javascript:void(0)" class="order-easyui-linkbutton-delete" data-options="plain:true,iconCls:\'fi-trash icon-blue\'" onclick="sendToBusSystem(\'{0}\');" >重新发送到业务系统</a>', row.O_LOGISTICS_ID);
                    }
                    return str;
                }
            }] ],
            toolbar : '#logisticsToolbar'
        });
    });

    /**
     * 搜索事件
     */
    function searchOLogistics() {
        logisticsA.datagrid('load', $.serializeObject($('#logisticsForm')));
    }

    function cleanOLogistics() {
        $('#logisticsForm input').val('');
        $("[name='platform']").val('');
        $("[name='proType']").val('');
        $("[name='proCom']").val('');
        $("[name='payMethod']").val('');
        $("[name='sendStatus']").val('');
        logisticsA.datagrid('load', {});
    }

    // 导出数据
    function exportOLogisticsFun(){
        $('#logisticsForm').form({
            url : '${path }/logistics/exportOLogistics',
            onSubmit : function() {
                return $(this).form('validate');
            }
        });
        $('#logisticsForm').submit();
    }

    // 导入数据
    $("#importOLogisticsFun").click(function(){
        parent.$.modalDialog({
            title : '导入物流信息',
            width : 300,
            height : 110,
            href : "/logistics/importPage",
            buttons : [ {
                text : '确定',
                handler : function() {
                    var fun = parent.$.modalDialog.handler.find('#logisticsImportFileForm');
                    fun.submit();
                }
            } ]
        });
    });

    // 导入sn码
    $("#importOLogisticsSn").click(function(){
        parent.$.modalDialog({
            title : '导入SN码',
            width : 300,
            height : 110,
            href : "/logistics/importSn",
            buttons : [ {
                text : '确定',
                handler : function() {
                    var fun = parent.$.modalDialog.handler.find('#SnImportFileForm');
                    fun.submit();
                }
            } ]
        });
    });
    var tag = 0;
    function sendToBusSystem(id){
        if(tag==0){
            $.ajaxL({
                type: "POST",
                url: "/logistics/sendToBusSystem",
                dataType: 'json',
                data: {id: id},
                beforeSend:function(){
                    tag = 1;
                    progressLoad();
                },
                success: function (msg) {
                    info(msg.msg);
                },
                complete: function (XMLHttpRequest, textStatus) {
                    tag = 0;
                    progressClose();
                    agentData_list_ConditionDataGrid.datagrid('reload');
                }
            });
        }

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
    <div id="" data-options="region:'west',border:true" style="width:100%;overflow: hidden; ">
        <table id="logisticsId" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'north',border:false" style="height: 150px; overflow: hidden;background-color: #fff">
        <form id ="logisticsForm" method="post">
            <table>
                <tr>
                    <th>排单编号：</th>
                    <td><input name="PLAN_NUM" id="PLAN_NUM" style="line-height:17px;border:1px solid #ccc;width:160px;"></td>
                    <th>订单编号：</th>
                    <td><input name="ORDER_ID" id="ORDER_ID" style="line-height:17px;border:1px solid #ccc;width:160px;"></td>
                    <th>商品名称:</th>
                    <td>
                        <input name="proName" style="line-height:17px;border:1px solid #ccc;width: 110px;">
                    </td>
                    <th>活动名称:</th>
                    <td><input name="activityName" style="line-height:17px;border:1px solid #ccc;width: 100px;"></td>
                <tr>
                <tr>
                    <th>代理商名称:</th>
                    <td>
                        <input name="agName" style="line-height:17px;border:1px solid #ccc;width: 160px;">
                    </td>
                    <th>代理商唯一编码:</th>
                    <td><input name="agUniqNum" style="line-height:17px;border:1px solid #ccc;width: 160px;"></td>
                    <th>机具类型：</th>
                    <td>
                        <select name="proType" style="width:110px;height:17px">
                            <option  value="">  ---请选择---  </option>
                            <c:forEach items="${modelType}" var="modelTypeItem"  >
                                <option value="${modelTypeItem.dItemvalue}">${modelTypeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>厂商：</th>
                    <td>
                        <select name="proCom" style="width:110px;height:17px">
                            <option  value="">  ---请选择---  </option>
                            <c:forEach items="${manufacturer}" var="manufacturerItem"  >
                                <option value="${manufacturerItem.dItemvalue}">${manufacturerItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                </tr>
                    <th>结算方式:</th>
                    <td>
                        <select name="payMethod" style="width:110px;height:17px">
                            <option  value="">  ---请选择---  </option>
                            <c:forEach items="${settlementType}" var="settlementTypeItem"  >
                                <option value="${settlementTypeItem.dItemvalue}">${settlementTypeItem.dItemname}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>平台类型:</th>
                    <td>
                        <select name="platform" style="width:110px;height:17px">
                            <option value="">  ---请选择---  </option>
                            <c:forEach items="${platFormList}" var="platFormListItem">
                                <option value="${platFormListItem.platformNum}">${platFormListItem.platformName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <th>机型:</th>
                    <td><input name="model" style="line-height:17px;border:1px solid #ccc;width: 110px;"></td>
                    <th>下单开始~结束时间:</th>
                    <td>
                        <input name="beginTime" style="line-height:17px;border:1px solid #ccc;width: 100px;" class="easyui-datebox"/>
                        <input name="endTime"   style="line-height:17px;border:1px solid #ccc;width: 100px;" class="easyui-datebox" />
                    </td>

                </tr>
                <tr>
                    <th>下发状态:</th>
                    <td>
                        <select name="sendStatus" style="width:110px;height:17px">
                            <option value="">  ---请选择---  </option>
                            <option value="0">未联动业务系统</option>
                            <option value="1">联动业务系统成功</option>
                            <option value="2">联动业务系统失败</option>
                        </select>
                    </td>
                    <th>发货日期:</th>
                    <td>
                        <input name="fhBeginTime" style="line-height:17px;border:1px solid #ccc;width: 90px;" class="easyui-datebox"/>
                        <input name="fhEndTime"   style="line-height:17px;border:1px solid #ccc;width: 90px;" class="easyui-datebox" />
                    </td>
                    <th>SN序列号:</th>
                    <td><input name="snNum" style="line-height:17px;border:1px solid #ccc;width: 110px;"></td>
                    <td colspan="2">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchOLogistics();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanOLogistics();">清空</a>
                        <shiro:hasPermission name="/oLogistics/toLeadLog">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" onclick="exportOLogisticsFun();">导出物流信息</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/oLogistics/importLog">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-archive icon-green'" id="importOLogisticsFun">导入物流信息</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="/oLogistics/importSn">
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true" id="importOLogisticsSn">导入sn码</a>
                        </shiro:hasPermission>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="oLogistics_notifyWin" class="easyui-window" title="物流信息详情" closed="true" style="width:1000px;height:300px;" data-options="iconCls:'icon-save',modal:true"></div>
