<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<%--<%@ include file="views/order/addressManageList.jsp" %>--%>
<script type="text/javascript">
    $(function(){

        refreshView();


    });

    //刷新订单配货数据
    function refreshView(){
        $.ajaxL({
            type: "GET",
            url: "/order/distributionViewData",
            dataType:'json',
            data: {orderId:'${orderId}',agentId:'${agentId}'},
            beforeSend:function(){
                progressLoad();
            },
            success: function(msg){
                console.log(msg.data);
                if(msg.status==200){
                    renderProduct(msg.data);
                    renderRecieveOrder(msg.data);
                }else{
                    info(msg.msg);
                }
            },
            complete:function (XMLHttpRequest, textStatus) {
                progressClose();
                $("select").change();
            }
        });
    }

    //渲染商品结合
    function renderProduct(all){
        var sub_produc =  renderProductItem(all,all.sub);
        $("#dgdsp").empty();
        $("#dgdsp").append(sub_produc);
    }

    //渲染单个商品
    function renderProductItem(all,productItemList){
        var ar = [];
        ar.push('<table class="grid">');
        ar.push(renderProductItemRow(all,'',true));
        $.each(productItemList,function(i,n){
            ar.push(renderProductItemRow(all,n,false));
        });
        ar.push('</table>');
        return ar.join('');
    }
    //渲染单个商品行数据
    function renderProductItemRow(all,data,head){
        var ar = [];
        if(head){
            ar.push('<tr>');
//            ar.push('<td>');ar.push('订购单ID');ar.push('</td>');
            ar.push('<td>');ar.push('商品名');ar.push('</td>');
            ar.push('<td>');ar.push('编号');ar.push('</td>');
            ar.push('<td>');ar.push('型号');ar.push('</td>');
            ar.push('<td>');ar.push('数量');ar.push('</td>');
            ar.push('<td>');ar.push('已配');ar.push('</td>');
            ar.push('<td>');ar.push('地址');ar.push('</td>');
            ar.push('<td>');ar.push('');ar.push('</td>');
            ar.push('<td>');ar.push('待配货');ar.push('</td>');
            ar.push('<td>');ar.push('操作');ar.push('</td>');
            ar.push('</tr>');
            return ar.join('');
        }
        ar.push('<tr>');
//        ar.push('<td>');ar.push(data.ID);ar.push('</td>');
        ar.push('<td>');ar.push(data.PRO_NAME);ar.push('</td>');
        ar.push('<td>');ar.push(data.PRO_CODE);ar.push('</td>');
        ar.push('<td>');ar.push(data.PRO_TYPE);ar.push('</td>');
        ar.push('<td>');ar.push(data.PRO_NUM);ar.push('</td>');
        ar.push('<td>');ar.push(data.HAVEPHCOUNT);ar.push('</td>');
        ar.push('<td>');
        ar.push('<select style="width: 200px;"  onchange="subOrderAddressChange(this,\''+data.ID+'\')">');
        $.each(all.address,function(i,n){
            ar.push('<option value="'+n.id+'">');
            ar.push(n.addrRealname);
            ar.push(n.addrMobile);
            ar.push(n.addrProvinceString);
            ar.push(n.addrCityString);
            ar.push(n.addrDistrictString);
            ar.push(n.addrDetail);
            ar.push('</option>');
        });
        ar.push('</select>');
        ar.push('</td>');
        ar.push('<td>');
        ar.push('<a href="javascript:void(0);" class="easyui-linkbutton"\n' +
            '                           data-options="iconCls:\'fi-magnifying-glass\',plain:true" onclick="addressAdd();">');
        ar.push('添加地址');
        ar.push('</a>');
        ar.push('</td>');
        ar.push('<td>');
        ar.push('<input type="text" style="width: 50px;" value="'+(data.PRO_NUM-data.HAVEPHCOUNT)+'" oninput="subOrderNumChange(this,\''+data.ID+'\')" />');
        ar.push('</td>');

        ar.push('<td>');
        if((data.PRO_NUM-data.HAVEPHCOUNT)>0){
            ar.push('<input type="button" value="保存" ' +
                'id="'+data.ID+'" ' +
                'PRO_ID="'+data.PRO_ID+'" ' +
                'AGENT_ID="'+data.AGENT_ID+'" ' +
                'ORDER_ID="'+data.ORDER_ID+'" ' +
                'ADDRESS_ID="" ' +
                'SEND_NUM="'+(data.PRO_NUM-data.HAVEPHCOUNT)+'"' +
                'onclick="peihuoAction(this)"/>');
        }
        ar.push('</td>');

        ar.push('</tr>');
        return ar.join('');
    }

    //订单商品地址下拉编辑
    function subOrderAddressChange(t,id){
        $("#"+id).attr("ADDRESS_ID",$(t).val());
    }

    function subOrderNumChange(t,id){
        $("#"+id).attr("SEND_NUM",$(t).val());
    }

    //添加地址
    function addressAdd() {
        parent.$.modalDialog({
            title : '地址添加',
            width : 800,
            height : 300,
            maximizable:true,
            href : '${path}/address/addressManageAddView?flag='+1,
            buttons : [ {
                text : '确定',
                handler : function() {
//                    parent.$.modalDialog.openner_dataGrid = addressManageList;
                    var gr = parent.$.modalDialog.handler.find('#addressAddForm');
                    gr.submit();
                }
            } ]
        });
    }


    //渲染收货地址
    function renderRecieveOrder(all){
        var recive_produc =  renderRecieveOrderItem(all,all.peiHuo);
        $("#yphsp").empty();
        $("#yphsp").append(recive_produc);
    }
    //渲染收货地址商品
    function renderRecieveOrderItem(all,peiHuo){
        var ar = [];
        ar.push('<table class="grid">');
        ar.push(renderRecieveOrderItemRow(all,'',true));
        $.each(peiHuo,function(i,n){
            ar.push(renderRecieveOrderItemRow(all,n,false));
        });
        ar.push('</table>');
        return ar.join('');
    }
    //渲染收货地址商品行
    function renderRecieveOrderItemRow(all,data,head) {

        var ar = [];
        if (head) {
            ar.push('<tr>');
            ar.push('<td title="">');ar.push('地址');ar.push('</td>');
            ar.push('<td>');ar.push('商品');ar.push('</td>');
            ar.push('<td>');ar.push('编号');ar.push('</td>');
            ar.push('<td>');ar.push('状态');ar.push('</td>');
            ar.push('<td>');ar.push('数量');ar.push('</td>');
            ar.push('<td>');ar.push('操作');ar.push('</td>');
            ar.push('</tr>');
            return ar.join('');
        }
        var address = data.ADDR_REALNAME + ":" + data.ADDR_MOBILE + ":" + data.ADDR_PROVINCE_STRING + data.ADDR_CITY_STRING + data.ADDR_DISTRICT_STRING + data.ADDR_DETAIL;
        ar.push('<tr>');
        ar.push('<td>');
        ar.push(address);
        ar.push('</td>');
        ar.push('<td>');
        ar.push(data.PRO_NAME);
        ar.push('</td>');
        ar.push('<td>');
        ar.push(data.PRO_CODE);
        ar.push('</td>');
        ar.push('<td>');
        ar.push(formardRECEIPT_PRO_STATUS(data.RECEIPT_PRO_STATUS));
        ar.push('</td>');
        ar.push('<td>');
        ar.push('<input type="text" style="width: 50px;"  value="' + (data.PRO_NUM_COUNT) + '" id="input_RECEIPT_PRO_'+data.RECEIPT_PRO_ID+'" oninput="reciveOrderNumChange(this,\'' + data.RECEIPT_PRO_ID + '\')" />');
        ar.push('</td>');
        ar.push('<td>');
        if ((data.RECEIPT_PRO_STATUS) == 0 || (data.RECEIPT_PRO_STATUS) == 1) {
            ar.push('<input type="button" value="修改" id="' + data.RECEIPT_PRO_ID + '" RECEIPT_NUM="' + data.RECEIPT_NUM + '" onclick="updatePeihuoAction(this)"/>');
            ar.push('&nbsp;||&nbsp;<input type="button" value="申请发货" id="' + data.RECEIPT_PRO_ID + '" RECEIPT_NUM="' + data.RECEIPT_NUM + '" onclick="sureSendAction(this)"/>');
            ar.push('&nbsp;||&nbsp;<input type="button" value="删除" id="' + data.RECEIPT_PRO_ID + '"  RECEIPT_NUM="' + data.RECEIPT_NUM + '" onclick="deletePeihuoAction(this)"/>');

        }
        ar.push('</td>');
        ar.push('</tr>');
        return ar.join('');
    }

    //格式化状态
    function formardRECEIPT_PRO_STATUS(RECEIPT_PRO_STATUS) {
        switch (RECEIPT_PRO_STATUS) {
            case 0:
                return '暂存';
            case 1:
                return '待排单';
            case 2:
                return '已排单';
        }
    }

    //配货地址商品变更监听
    function reciveOrderNumChange(t, id) {
        $("#"+id).attr("RECEIPT_NUM", $(t).val());
    }


    /**
     * 解析保存配货商品按钮数据
     * @param t
     * @returns {{orderId: (*|jQuery), agentId: (*|jQuery), addressId: (*|jQuery), agentId: (*|jQuery), proId: (*|jQuery), sendNum: (*|jQuery)}}
     */
    function peihuoButton(t) {
        return {
            orderId: $(t).attr("ORDER_ID"),
            agentId: $(t).attr("AGENT_ID"),
            addressId: $(t).attr("ADDRESS_ID"),
            proId: $(t).attr("PRO_ID"),
            sendNum: $(t).attr("SEND_NUM")
        };
    }

    /**
     * 配货操作
     * @param t
     */
    function peihuoAction(t) {
        var data = peihuoButton(t);
        $.ajaxL({
            type: "GET",
            url: "/order/peihuoAction",
            dataType: 'json',
            data: data,
            beforeSend: function () {
                progressLoad();
            },
            success: function (msg) {
                console.log(msg.data);
                if (msg.status == 200) {
                    refreshView();
                } else {
                    info(msg.msg);
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }


    function getUpdatePeihuoActionData(t) {
        var id = $(t).attr("id");
        return {
            id: id,
            proNum: $("#input_RECEIPT_PRO_"+id).val()
        };

    }

    // 更新配货信息
    function updatePeihuoAction(t) {
        var data = getUpdatePeihuoActionData(t);
        $.ajaxL({
            type: "GET",
            url: "/order/updatepeihuoActionItem",
            dataType: 'json',
            data: data,
            beforeSend: function () {
                progressLoad();
            },
            success: function (msg) {
                if (msg.status == 200) {
                    refreshView();
                } else {
                    info(msg.msg);
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }

    //确定发货
    function sureSendAction(t) {
        var data = getUpdatePeihuoActionData(t);
        $.ajaxL({
            type: "GET",
            url: "/order/sureSendAction",
            dataType: 'json',
            data: data,
            beforeSend: function () {
                progressLoad();
            },
            success: function (msg) {
                if (msg.status == 200) {
                    refreshView();
                } else {
                    info(msg.msg);
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }


    //确定发货全部
    function sureSendActionALL(orderId,agentId) {
        $.ajaxL({
            type: "GET",
            url: "/order/sureSendActionAll",
            dataType: 'json',
            data: {orderId:orderId,agentId:agentId},
            beforeSend: function () {
                progressLoad();
            },
            success: function (msg) {
                if (msg.status == 200) {
                    refreshView();
                } else {
                    info(msg.msg);
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }

    //删除
    function deletePeihuoAction(t) {
        //删除之前先去查询是否已经在排单了
        var data = getUpdatePeihuoActionData(t);
        $.ajaxL({
            type: "GET",
            url: "/order/deletePeihuoAction",
            dataType: 'json',
            data: data,
            beforeSend: function () {
                progressLoad();
            },
            success: function (msg) {
                if (msg.status == 200) {
                    refreshView();
                } else {
                    info(msg.msg);
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }

</script>
<div>
    <div id="dgdsp" class="easyui-panel" title="订购单商品" style="width:100%;min-height:100px;background:#fafafa;"
         data-options="iconCls:'icon-save',">
    </div>
    <div id="yphsp" class="easyui-panel" title="已配货商品" style="width:100%;min-height:100px;background:#fafafa;"
         data-options="iconCls:'icon-save',footer:'#yphspFooter'," >
    </div>
    <div id="yphspFooter" style="text-align: right;">
        <a onclick="sureSendActionALL('${orderId}','${agentId}')" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-folder-add icon-red'">申请发货</a>
    </div>
</div>
