<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<%--省区大区审批查看界面--%>
<div id="old_order_return_bus_edit" class="easyui-panel" title="退货信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <input hidden="hidden" value="${old_order_return_info.id}" id="old_order_return_bus_edit_returnId" />
    <table class="grid" style="width: 100%;text-align: center;" id="old_order_return_bus_edit_table" >
        <tr>
            <td >退货子订单</td>
            <td >开始SN</td>
            <td >结束SN</td>
            <td >数量</td>
            <td >订单号</td>
            <td >商品</td>
            <td >业务平台</td>
            <td >活动</td>
            <td >机具类型</td>
            <td >采购单价</td>
            <td >厂家</td>
            <td >型号</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${old_order_return_info_detail}" var="old_order_return_info_detail_item">
            <tr id="${old_order_return_info_detail_item.id}" return_count="${old_order_return_info_detail_item.returnCount}" tr_return_info="${old_order_return_info.id}">
                <td >${old_order_return_info_detail_item.id}</td>
                <td >${old_order_return_info_detail_item.beginSn}</td>
                <td >${old_order_return_info_detail_item.endSn}</td>
                <td >${old_order_return_info_detail_item.returnCount}</td>
                <td >
                    <input value="${old_order_return_info_detail_item.orderId}" name="orderid" style="width: 80px;" id="${old_order_return_info_detail_item.id}_orderid"/>
                </td>
                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_productid" name="productid" disabled="disabled">
                        <c:forEach items="${products}" var="productsItem">
                            <option value="${productsItem.id}"
                                    <%--<c:if test="${productsItem.id==old_order_return_info_detail_item.proId}" >selected="selected"</c:if> --%>
                                    <c:if test="${not empty  old_order_return_info_detail_item.act && !(old_order_return_info_detail_item.act eq  null )
                                    && old_order_return_info_detail_item.act.productId==productsItem.id}">
                                        selected="selected"
                                    </c:if>
                            >${productsItem.proName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_platform" name="platform" disabled="disabled">
                        <c:forEach items="${ablePlatForm}" var="ablePlatFormItem">
                            <option value="${ablePlatFormItem.platformNum}"
                            <c:if test="${not empty  old_order_return_info_detail_item.act && !(old_order_return_info_detail_item.act eq  null )
                                        && old_order_return_info_detail_item.act.platform==ablePlatFormItem.platformNum}">
                                selected="selected"
                            </c:if>
                            >${ablePlatFormItem.platformName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td >
                    <c:if test="${ empty   old_order_return_info_detail_item.act || old_order_return_info_detail_item.act eq  null }">
                        <input type="text" value=""   style="width: 80px;" id="${old_order_return_info_detail_item.id}_activity" readonly="readonly"/>
                        <input type="hidden" value=""   style="width: 80px;" id="${old_order_return_info_detail_item.id}_activity_hidden" name="activity"/>
                    </c:if>
                    <c:if test="${not empty  old_order_return_info_detail_item.act && !(old_order_return_info_detail_item.act eq  null )}">
                        <input type="text" value="${old_order_return_info_detail_item.act.activityName}"   style="width: 80px;" id="${old_order_return_info_detail_item.id}_activity" readonly="readonly"/>
                        <input type="hidden" value="${old_order_return_info_detail_item.act.id}"   style="width: 80px;" id="${old_order_return_info_detail_item.id}_activity_hidden" name="activity"/>
                    </c:if>
                </td>
                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_modeltype" name="modeltype" disabled="disabled">
                        <c:forEach items="${modelType}" var="modelTypeItem">
                            <option value="${modelTypeItem.dItemvalue}"
                                <c:if test="${not empty  old_order_return_info_detail_item.act
                                && !(old_order_return_info_detail_item.act eq  null )
                                && (old_order_return_info_detail_item.act.proType == modelTypeItem.dItemvalue )
                                }">
                                 selected="selected"
                                </c:if>
                            >${modelTypeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td >
                    <input value="${old_order_return_info_detail_item.proPrice}" style="width: 50px;" id="${old_order_return_info_detail_item.id}_proprice" name="proprice" readonly="readonly"/>
                </td>

                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_manufacturer" name="manufacturer" disabled="disabled">
                        <c:forEach items="${manufacturer}" var="manufacturerItem">
                            <option value="${manufacturerItem.dItemvalue}"
                                    <c:if test="${not empty  old_order_return_info_detail_item.act
                                    && !(old_order_return_info_detail_item.act eq  null )
                                    && (old_order_return_info_detail_item.act.vender == manufacturerItem.dItemvalue )
                                    }">
                                    selected="selected"
                                    </c:if>
                            >${manufacturerItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td >
                    <select style="width: 80px;" id="${old_order_return_info_detail_item.id}_promode" name="promode" disabled="disabled">
                        <c:forEach items="${proMode}" var="proModeItem">
                            <option value="${proModeItem.dItemvalue}"
                                    <c:if test="${not empty  old_order_return_info_detail_item.act
                                    && !(old_order_return_info_detail_item.act eq  null )
                                    && (old_order_return_info_detail_item.act.proModel == proModeItem.dItemvalue )
                                    }">
                                        selected="selected"
                                    </c:if>
                            >${proModeItem.dItemname}</option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    <%--<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2',plain:true" onclick="old_order_return_bus_edit_loadData('${old_order_return_info_detail_item.id}')">加载数据</a>--%>
                    <a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2',plain:true" onclick="openOldOrderReturnPlanerListPage('${old_order_return_info_detail_item.id}')">排单</a>
                    <%--<input type="button" value="加载数据" onclick="old_order_return_bus_edit_loadData('${old_order_return_info_detail_item.id}')" />--%>
                    <%--<input type="button" value="排单" onclick="old_order_return_bus_edit_loadData('${old_order_return_info_detail_item.id}')" />--%>

                </td>
            </tr>
        </c:forEach>
    </table>
    <div style="padding:5px 20px 5px 5px;text-align: right;">
        <table class="grid">
            <tr>
                <td style="color: red;text-align: left;">1:请先补全历史订单信息,2:进行退货机具排单</td>
                <td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="old_order_return_bus_edit_loadData_Save('${old_order_return_info.id}')">保存历史订单配置</a></td>
            </tr>
        </table>
    </div>
    <script type="application/javascript">

        /**
         * 加载历史订单sn退货配置项
         * @param id
         */
        function old_order_return_bus_edit_loadData(id){
            var _orderid   =  $("#"+id+"_orderid").val();
            var _productid = $("#"+id+"_productid option:selected").val();
            $.ajaxL({
                type: "POST",
                url: "/oldorderreturn/loadOldOrderReturnDetailInfo",
                dataType: 'json',
                data: {
                    orderId:_orderid,
                    proId:_productid
                },
                beforeSend: function () {
                    progressLoad();
                },
                success: function (msg) {
                    if(msg.status!=200){
                        info(msg.msg);
                    }else{
                        $("#"+id+"_platform").val(msg.data.order.orderPlatform);
                        $("#"+id+"_activity").val(msg.data.oSubOrderActivity.activityName);
                        $("#"+id+"_activity_hidden").val(msg.data.oSubOrderActivity.activityId);
                        $("#"+id+"_modeltype").val(msg.data.subOrder.proType);
                        $("#"+id+"_proprice").val(msg.data.oSubOrderActivity.price);
                        $("#"+id+"_manufacturer").val(msg.data.oSubOrderActivity.vender);
                        $("#"+id+"_promode").val(msg.data.oSubOrderActivity.proModel);
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                    progressClose();
                }
            });
        }


        /**
         * 加载数据配置保存
         * @param returnId
         */
        function old_order_return_bus_edit_loadData_Save(returnId){
           var data =  old_order_return_bus_edit_loadData_Save_getData(returnId);
            $.ajaxL({
                type: "POST",
                url: "/oldorderreturn/completOldOrderReturnDetailInfo",
                dataType: 'json',
                contentType:'application/json;charset=UTF-8',
                data: JSON.stringify(data),
                beforeSend: function () {
                    progressLoad();
                },
                success: function (msg) {
                    if(msg.status!=200){
                        info(msg.msg);
                    }else{
                        $("#"+returnId+"_old_order_return_amt").find("input[name='returnAmo']").val(msg.data.oReturnOrder.returnAmo);
                        $("#"+returnId+"_old_order_return_amt").find("input[name='goodsReturnAmo']").val(msg.data.oReturnOrder.goodsReturnAmo);
                        info(msg.msg);
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                    progressClose();
                }
            });
        }
        /**
         * 解析html进行数据组装
         */
        function old_order_return_bus_edit_loadData_Save_getData(returnId){
            var dataArr = [];
            $("#old_order_return_bus_edit_table tr[tr_return_info='"+returnId+"']").each(function(index,elm){
                 dataArr.push({
                     returnid:returnId,
                     returndetailid: $(elm).attr("id"),
                     orderid:   $(elm).find("input[name='orderid']").val(),
                     productid: $(elm).find("select[name='productid']").val(),
                     platform:  $(elm).find("select[name='platform']").val(),
                     activity:  $(elm).find("input[name='activity']").val(),
                     modeltype: $(elm).find("select[name='modeltype']").val(),
                     proprice:  $(elm).find("input[name='proprice']").val(),
                     manufacturer: $(elm).find("select[name='manufacturer']").val(),
                     promode:   $(elm).find("select[name='promode']").val()
                 });
            });
            return dataArr;
        }

        /**
         * 打开排单查询对话框
         */
        function openOldOrderReturnPlanerListPage(returnDetailsId) {

            //检查是否进行了订单数据保存
            $.ajaxL({
                type: "POST",
                url: "/oldorderreturn/checkReturnOrderOrderIdIsCompplet",
                dataType: 'json',
                data: {returnDetailsId:returnDetailsId},
                beforeSend: function () {
                    progressLoad();
                },
                success: function (msg) {

                    if(msg.status!=200){
                        info(msg.msg);
                    }else{
                        parent.$.modalDialog({
                            title: '排单查询',
                            width : '60%',
                            height : '80%',
                            maximizable: true,
                            href: '${path }/oldorderreturn/page/planerList?returnDetailsId='+returnDetailsId,
                            buttons: [{
                                text: '关闭',
                                handler: function () {
                                    parent.$.modalDialog.handler.dialog('close');
                                }
                            }]
                        });
                    }

                },
                complete: function (XMLHttpRequest, textStatus) {
                    progressClose();
                }
            });


        }

        /**
         * 排单列表插入界面
         * @param rowData
         * @param returnDetailsId
         */
        function do_old_order_plan(rowData,returnDetailsId) {
            var returnCount = parseInt($("#"+returnDetailsId).attr("return_count"));
            var arrhtml = [];
            arrhtml.push("<tr type='old_order_plan_row'>");
            arrhtml.push("<td type='old_order_plan_row_returnDetailsId'>");arrhtml.push(returnDetailsId);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_ORDER_ID'>");arrhtml.push(rowData.ORDER_ID);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_RECEIPT_NUM' style='display: none;' v='"+rowData.RECEIPT_PRO_ID+"'>");arrhtml.push(rowData.RECEIPT_NUM);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_AGENT_ID'>");arrhtml.push(rowData.AGENT_ID);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_AG_NAME'>");arrhtml.push(rowData.AG_NAME);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_ADDR_REALNAME'>");arrhtml.push(rowData.ADDR_REALNAME);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_PRO_NAME'>");arrhtml.push(rowData.PRO_NAME);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_PRO_NUM'>");arrhtml.push(rowData.PRO_NUM);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_SEND_NUM'>");arrhtml.push(rowData.SEND_NUM);arrhtml.push("</td>");
            arrhtml.push("<td type='old_order_plan_row_RETURNCOUNT'>");arrhtml.push("<input type='text' value='"+(returnCount>rowData.SEND_NUM?rowData.SEND_NUM:returnCount)+"' name='returnCount'/>");arrhtml.push("</td>");
            arrhtml.push("<td>");arrhtml.push("<a onclick='javascript:$(this).parent().parent().remove();'>删除</a>");arrhtml.push("</td>");
            arrhtml.push("</tr>");
            $("#${old_order_return_info.id}_old_order_return_plan_table tbody").append(arrhtml.join(""));
        }

        /**
         * 获取历史订单排单数据
         */
        function getOldOrderPlanData(returnId){
               var arr = [];
            $("#"+returnId+"_old_order_return_plan_table").find("tr[type='old_order_plan_row']").each(function(index,elm){
                arr.push({
                    O_RETURN_ORDER_DETAIL_ID:$(elm).find("td[type='old_order_plan_row_returnDetailsId']").html(),
                    receiptProId:$(elm).find("td[type='old_order_plan_row_RECEIPT_NUM']").attr("v"),
                    orderId:$(elm).find("td[type='old_order_plan_row_ORDER_ID']").html(),
                    receiptId:$(elm).find("td[type='old_order_plan_row_RECEIPT_NUM']").html(),
                    planProNum:$(elm).find("input[name='returnCount']").val()
                });
            });
            console.log(arr);
            return arr;
        }

        function getOldOrderDeductCapitalList(returnId){
            var arr = [];
            var cutAmt = $("#"+returnId+"_old_order_return_amt").find("input[name='cutAmt']").val();
            var cType = $("#"+returnId+"_old_order_return_amt").find("select[name='cType']").val();
            arr.push({
                cType:cType,
                cAmount:cutAmt
            });
            console.log(arr);
            return arr;
        }

        function _old_order_return_next(returnId){
            return $("#"+returnId+"_old_order_return_next").find("select[name='dept']").val();
        }

        function compute(cutAmt) {
            //机具金额
            var tools_amt = document.getElementById("goodsReturnAmo").value;
            //扣款金额
            var deduct_amt = cutAmt.value;
            //计算总金额
            document.getElementById("returnAmo").value = parseFloat(tools_amt) - parseInt(deduct_amt);
        }
    </script>
</div>
<div id="${old_order_return_info.id}_old_order_return_plan" class="easyui-panel" title="排单列表" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <table class="grid" style="width: 100%;text-align: center;" id="${old_order_return_info.id}_old_order_return_plan_table">
        <tr >
            <td>退货子订单</td>
            <td>订单编号</td>
            <td style='display: none;'>收货单编号</td>
            <td>代理商编号</td>
            <td>代理商</td>
            <td>收货人</td>
            <td>商品名称</td>
            <td>订货量</td>
            <td>已排单</td>
            <td>数量</td>
            <td>操作</td>
        </tr>
    </table>
</div>
<div id="${old_order_return_info.id}_old_order_return_amt" class="easyui-panel" title="退款信息" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <table class="grid" style="width: 100%">
        <tr>
            <td style="width: 100px;">退货单总金额：</td>
            <td style="width: 60px;">
                <input type="text" id="returnAmo" name="returnAmo" value="${old_order_return_info.returnAmo}" readonly="readonly"/>
            </td>
            <td style="width: 20px;">元</td>
            <td>(机具金额-扣款金额)</td>
        </tr>
        <tr>
            <td style="width: 100px;">扣款金额：</td>
            <td style="width: 60px;">
                <input type="text" onkeypress="return(event.keyCode>=48 && event.keyCode<=57)" onblur="compute(this)" id="cutAmt" name="cutAmt" value="${old_order_return_info.cutAmo}" />
            </td>
            <td style="width: 20px;">元</td>
            <td>
                <select name="cType" style="width:160px;height:21px" >
                <c:forEach items="${cType}" var="cType">
                    <option value="${cType.dItemvalue}">${cType.dItemname}</option>
                </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td style="width: 100px;">机具金额：</td>
            <td style="width: 60px;">
                <input type="text" id="goodsReturnAmo" name="goodsReturnAmo" value="${old_order_return_info.goodsReturnAmo}" readonly="readonly"/>
            </td>
            <td style="width: 20px;">元</td>
            <td>(机具台数*机具单价)</td>
        </tr>
    </table>
</div>
<div id="${old_order_return_info.id}_old_order_return_next" class="easyui-panel" title="下级审批" style="background:#fafafa;" data-options="iconCls:'fi-results'">
    <table class="grid" style="width: 100%">
        <tr>
            <td>下级审批部门</td>
            <td>
                <select name="dept" style="width:160px;height:21px" >
                    <c:forEach items="${orderReturn_param}" var="orderReturn_paramItem">
                        <option value="${orderReturn_paramItem.dItemvalue}">${orderReturn_paramItem.dItemname}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
</div>

