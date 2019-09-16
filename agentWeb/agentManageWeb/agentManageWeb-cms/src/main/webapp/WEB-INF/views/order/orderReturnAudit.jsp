<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>

<div>
    <div class="easyui-panel" title="售后信息" style="background:#fafafa;"
         data-options="iconCls:'fi-results',tools:'#return_model_tools'">
        <div>
            <table class="grid" style="width: 100%">
                <thead>
                <tr>
                    <td><b>退货明细编号</b></td>
                    <td><b>订单编号</b></td>
                    <td><b>商品</b></td>
                    <td><b>采购单价</b></td>
                    <td><b>退货数量</b></td>
                    <td><b>终端开始SN</b></td>
                    <td><b>终端结束SN</b></td>
                    <td><b>厂家</b></td>
                    <td><b>机型</b></td>
                    <td><b>操作</b></td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${returnDetails}" var="returnDetails">
                    <tr>
                        <td>${returnDetails.id}</td>
                        <td>${returnDetails.orderId}</td>
                        <td>${returnDetails.proName}</td>
                        <td>${returnDetails.orderPrice}</td>
                        <td>${returnDetails.returnCount}</td>
                        <td>${returnDetails.beginSn}</td>
                        <td>${returnDetails.endSn}</td>
                        <td>${returnDetails.proCom}</td>
                        <td>${returnDetails.proType}</td>
                        <shiro:hasPermission name="refund_apr_permission_addkk">
                        <c:if test="${sid=='sid-C911F512-9E63-44CC-9E6E-763484FA0E5B'}">
                        <td><b><a href="#" onclick="openPlanerListPage('${returnDetails.id}')">添加排单</a>&nbsp;&nbsp;&nbsp;&nbsp;</b></td>
                        </c:if>
                        </shiro:hasPermission>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div>
            <table class="grid" style="width: 100%;">
                <tr>
                    <td colspan="4">退发票： <input disabled style="margin-left: 20px" type="radio" name="retInvoice" value="1" <c:if test="${returnOrder.retInvoice==1}">checked</c:if>>是 <input
                            disabled style="margin-left: 20px" type="radio" name="retInvoice" value="0" <c:if test="${returnOrder.retInvoice==0}">checked</c:if>>否
                    </td>
                </tr>
                <tr style="display: none;">
                    <td colspan="4">退收据： <input disabled style="margin-left: 20px" type="radio" name="retReceipt" value="1" <c:if test="${returnOrder.retReceipt==1}">checked</c:if>>是 <input
                            disabled style="margin-left: 20px" type="radio" name="retReceipt" value="0" <c:if test="${returnOrder.retReceipt==0}">checked</c:if>>否
                    </td>
                </tr>
                <tr>
                    <td>申请备注：${returnOrder.applyRemark}</td>
                </tr>
                <shiro:hasPermission name="refund_apr_permission_addkk">
                    <c:if test="${sid=='sid-C911F512-9E63-44CC-9E6E-763484FA0E5B'}">
                        <tr>
                            <td>
                                <select class="easyui-combobox" style="width: 200px" id="select_cut">
                                    <c:forEach items="${cType}" var="cType">
                                        <option value="${cType.dItemvalue}">${cType.dItemname}</option>
                                    </c:forEach>
                                </select> &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="addCut()">添加款项</a>
                            </td>
                        </tr>
                        <div>
                        <tr>
                            <td id="td_cut">
                                <c:forEach items="${deductCapitals}" var="deductCapitals">
                                            <c:forEach items="${cType}" var="cType">
                                                <c:if test="${deductCapitals.cType == cType.dItemvalue}">
                                                    <div><span style="width: 100px;display:inline-block;margin-top: 10px">${cType.dItemname}：</span>${deductCapitals.cAmount} 元 &nbsp;&nbsp;<a id="a_${deductCapitals.id}" href="#" onclick="delCut('${deductCapitals.id}')">删除</a></div>
                                                </c:if>
                                            </c:forEach>
                                </c:forEach>
                            </td>
                        </tr>
                        </div>
                        <tr><td></td></tr>
                    </c:if>
                </shiro:hasPermission>
                <tr>
                    <td>退货扣款总金额：<span id="cutAmo" name="cutAmo">${returnOrder.cutAmo}</span> 元
                        &nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${sid!='sid-C911F512-9E63-44CC-9E6E-763484FA0E5B'}"><a href="javascript:cutDetail()">查看明细</a></c:if>
                    </td>
                </tr>
                <tr>
                    <td>退货机具总金额：${returnOrder.goodsReturnAmo} 元</td>
                </tr>
                <tr>
                    <td>应退款总金额：<span id="returnAmo"
                                      name="returnAmo">${returnOrder.returnAmo}</span>
                        元
                    </td>
                </tr>

                <c:if test="${sid=='sid-4528CEA4-998C-4854-827B-1842BBA5DB4B'}">
                    <c:choose>
                        <c:when test="${haveAdjusted}"> <!--如果 -->
                            <tr>
                                <td>
                                    抵扣欠款金额：${oAccountAdjusts.adjust.adjustAmount} 元
                                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:adjustD()">查看明细</a>
                                </td>
                            </tr>
                            <tr>
                                <td>线下退款金额：
                                    <c:if test="${not empty oAccountAdjusts.refund}">
                                        ${oAccountAdjusts.refund.refundAmount}
                                    </c:if>
                                    <c:if test="${empty oAccountAdjusts.refund}">
                                        0
                                    </c:if>
                                    元
                                </td>

                            </tr>
                        </c:when>

                        <c:otherwise>  <!--否则 -->
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td><b>机具欠款抵扣信息</b></td>
                            </tr>
                            <tr>
                                <td>可抵扣机具欠款金额：${takeAmt} 元
                                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:takoutAmt()">查看明细</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    付款计划变更
                                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:fkjh()">点击预览</a>
                                </td>
                            </tr>
                            <tr>
                                <td>线下退款金额：
                                    <c:if test="${not empty refund}">
                                        ${refund.refundAmount}
                                    </c:if>
                                    <c:if test="${empty refund}">
                                        0
                                    </c:if> 元
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a id="a_zxqkdk" href="#" class="easyui-linkbutton" data-options="" style="width:200px;"  onclick="doPayPlan()">执行退款方案</a>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>

                        </c:otherwise>

                    </c:choose>

                </c:if>
                </tbody>
            </table>
            <%@ include file="/commons/invoice_show_common.jsp"%>
            <c:if test="${sid=='sid-4528CEA4-998C-4854-827B-1842BBA5DB4B'}">
                <table>
                    <tr>
                        <td>退款日期:
                            <input style="border:1px solid #ccc" name="time" class="easyui-datebox" editable="false">
                        </td>
                        <td>退款人:
                            <input style="border:1px solid #ccc" name="refundPeople" type="text" value="">
                        </td>
                        <td id="returnOrderAttrList"></td>
                        <td>上传打款凭证：&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0)" class="easyui-linkbutton"
                               data-options="plain:true,iconCls:'fi-page-multiple'"
                               onclick="multFileUpload(returnOrderAttach);">添加附件</a>
                            <script type="application/javascript">
                                //添加附件
                                function returnOrderAttach(data) {
                                    var jsondata = eval(data);
                                    for (var i = 0; i < jsondata.length; i++) {
                                        $("#returnOrderAttrList").append("<span onclick='removeBuildOrderAddattr(this)'>" + jsondata[i].attName + "<input type='hidden' name='returnOrderAttrListFile' value='" + jsondata[i].id + "' /></span>&nbsp;&nbsp;&nbsp;&nbsp;");
                                    }
                                }

                                //删除附件
                                function removeBuildOrderAddattr(t) {
                                    parent.$.messager.confirm('询问', '确定删除附件么？', function (b) {
                                        if (b) {
                                            $(t).remove();
                                        }
                                    });
                                }

                                //获取附件信息
                                function getReturnOrderAddattrs() {
                                    var attachments = [];
                                    var inputs = $("#returnOrderAttrList").find("input[name='returnOrderAttrListFile']");
                                    for (var i = 0; i < inputs.length; i++) {
                                        var id = $(inputs[i]).val();
                                        attachments.push(id);
                                    }
                                    return attachments;
                                }
                            </script>
                        </td>
                    </tr>
                </table>
            </c:if>
        </div>
    </div>

    <shiro:hasPermission name="refund_apr_permission_addkk">
        <c:if test="${sid=='sid-C911F512-9E63-44CC-9E6E-763484FA0E5B'}">
            <%--<table class="grid" style="width: 100%;margin-top: 30px">--%>
                <%--<tr>--%>
                    <%--&lt;%&ndash;<td><b><a href="#" onclick="openPlanerListPage()">添加排单</a>&nbsp;&nbsp;&nbsp;&nbsp;</b>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
                <%--</tr>--%>
            <%--</table>--%>
            <div class="easyui-layout" data-options="fit:false,border:false" style="height: 200px">
                <div data-options="region:'center',fit:false,border:false">
                    <table id="paidan" data-options="fit:false,border:false"></table>
                </div>
            </div>
        </c:if>
    </shiro:hasPermission>


    <div id="dd_kk_${returnOrder.id}" class="easyui-dialog" title="扣款明细" style="width:800px;height:500px;"
         data-options="iconCls:'icon-save',resizable:true,closed:true">
        <table class="grid" style="width: 100%;margin-top: 20px">
            <tr>
                <td><b>扣款类型</b></td>
                <td><b>扣款金额</b></td>
            </tr>
            <c:forEach items="${deductCapitals}" var="deductCapitals">
                <tr>
                    <td>
                        <c:forEach items="${cType}" var="cType">
                            <c:if test="${deductCapitals.cType == cType.dItemvalue}">
                                ${cType.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${deductCapitals.cAmount} 元</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <shiro:hasPermission name="refund_apr_permission_paidan">
    <div id="div_paidan">
        <c:if test="${sid=='sid-F315F787-E98B-40FA-A6DC-6A962201075D'}">
            <table class="grid" style="width: 100%;margin-top: 20px">
                <tr>
                    <td><a href="#" onclick="exportData_refund()">导出退货排单信息</a></td>
                </tr>
            </table>
        </c:if>

        <c:if test="${sid=='sid-F315F787-E98B-40FA-A6DC-6A962201075D' || sid=='sid-E33DC3B4-6BC3-4258-982A-B6DB0E1B23B8'}">
        <%--已排单列表--%>
        <div class="easyui-layout" data-options="fit:false,border:false" style="height: 200px">
            <div data-options="region:'center',fit:false,border:false">
                <table class="grid" data-options="fit:false,border:false">
                    <tr>
                        <td><b>排单信息</b></td>
                    </tr>
                </table>
                <table id="paidan_complate_b" data-options="fit:false,border:false">
                </table>
            </div>
        </div>
        </c:if>
    </div>
    </shiro:hasPermission>

    <shiro:hasPermission name="refund_apr_permission_wuliu">
    <div id="div_wuliu">
        <c:if test="${sid=='sid-F315F787-E98B-40FA-A6DC-6A962201075D'}">
            <table class="grid" style="width: 100%;margin-top: 20px">
                <tr>
                    <td><a id="importOLogisticsFun" href="#">导入退货物流信息</a></td>
                </tr>
            </table>
            <div id="oLogistics_return_notifyWin" class="easyui-window" title="通知信息详情" closed="true" style="width:1000px;height:300px;" data-options="iconCls:'icon-save',modal:true"></div>
        </c:if>


        <c:if test="${sid=='sid-F315F787-E98B-40FA-A6DC-6A962201075D' || sid=='sid-E33DC3B4-6BC3-4258-982A-B6DB0E1B23B8'}">
        <%--物流信息--%>
        <div class="easyui-layout" data-options="fit:false,border:false" style="height: 200px">
            <div data-options="region:'center',fit:false,border:false">
                <table class="grid" data-options="fit:false,border:false">
                    <tr>
                        <td><b>物流信息</b></td>
                    </tr>
                </table>
                <table id="wuliu_b" data-options="fit:false,border:false">
                </table>
            </div>
        </div>
        </c:if>
    </div>
    </shiro:hasPermission>


    <div id="dd_qk_${returnOrder.id}" class="easyui-dialog" title="抵扣机具欠款明细" style="width:800px;height:500px;"
         data-options="iconCls:'icon-save',resizable:true,closed:true">
        <table class="grid" style="width: 100%;margin-top: 20px">
            <tr>
                <td><b>订单编号</b></td>
                <td><b>应付款金额</b></td>
                <td><b>已付款金额</b></td>
                <td><b>待付金额</b></td>
                <td><b>可抵扣金额</b></td>
            </tr>
            <c:forEach items="${takeoutList}" var="takeoutList">
                <tr>
                    <td>${takeoutList.orderId}</td>
                    <td>${takeoutList.payment.payAmount} 元</td>
                    <td>${takeoutList.payment.realAmount} 元</td>
                    <td>${takeoutList.payment.outstandingAmount} 元</td>
                    <td>${takeoutList.payAmt} 元</td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div id="dd_jh_${returnOrder.id}" class="easyui-dialog" title="付款计划更新" style="width:800px;height:500px;"
         data-options="iconCls:'icon-save',resizable:true,closed:true">
        <table class="grid" style="width: 100%;margin-top: 20px">

            <tr>
                <td colspan="6" align="center"><h3>原付款计划：</h3></td>
            </tr>

            <tr>
                <td><b>订单号</b></td>
                <td><b>付款类型</b></td>
                <td><b>期数</b></td>
                <td><b>金额</b></td>
                <td><b>支付状态</b></td>
                <td><b>支付方式</b></td>
            </tr>

            <c:forEach items="${planNows}" var="planNows">
                <tr>
                    <td>${planNows.orderId}</td>
                    <td>
                        <c:forEach items="${paymentType}" var="paymentType">
                        <c:if test="${planNows.payType == paymentType.dItemvalue}">
                            ${paymentType.dItemname}
                        </c:if>
                        </c:forEach>
                    </td>
                    <td>${planNows.planNum}</td>
                    <td>${planNows.payAmount}</td>
                    <td>
                        <c:forEach items="${paymentStatus}" var="paymentStatus">
                            <c:if test="${planNows.paymentStatus == paymentStatus.dItemvalue}">
                                ${paymentStatus.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${planNows.srcType}</td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="6" align="center"><h3>更新后付款计划：</h3></td>
            </tr>
            <tr>
                <td><b>订单号</b></td>
                <td><b>付款类型</b></td>
                <td><b>期数</b></td>
                <td><b>金额</b></td>
                <td><b>支付状态</b></td>
                <td><b>支付方式</b></td>
            </tr>
            <c:forEach items="${planNows_complate}" var="planNows_complate">
                <tr>
                    <td>${planNows_complate.orderId}</td>
                    <td>
                        <c:forEach items="${paymentType}" var="paymentType">
                            <c:if test="${planNows_complate.payType == paymentType.dItemvalue}">
                                ${paymentType.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${planNows_complate.planNum}</td>
                    <td>${planNows_complate.payAmount}</td>
                    <td>
                        <c:forEach items="${paymentStatus}" var="paymentStatus">
                            <c:if test="${planNows_complate.paymentStatus == paymentStatus.dItemvalue}">
                                ${paymentStatus.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${planNows_complate.srcType}</td>
                </tr>
            </c:forEach>
            <c:forEach items="${planNews}" var="planNews">
                <tr>
                    <td>${planNews.orderId}</td>
                    <td>
                        <c:forEach items="${paymentType}" var="paymentType">
                            <c:if test="${planNews.payType == paymentType.dItemvalue}">
                                ${paymentType.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${planNews.planNum}</td>
                    <td>${planNews.payAmount}</td>
                    <td>
                        <c:forEach items="${paymentStatus}" var="paymentStatus">
                            <c:if test="${planNews.paymentStatus == paymentStatus.dItemvalue}">
                                ${paymentStatus.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach items="${paymentSrcType}" var="paymentSrcType">
                            <c:if test="${planNews.srcType == paymentSrcType.dItemvalue}">
                                ${paymentSrcType.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>


    <div id="dd_tz_${returnOrder.id}" class="easyui-dialog" title="抵扣欠款明细" style="width:800px;height:500px;"
         data-options="iconCls:'icon-save',resizable:true,closed:true">
        <table class="grid" style="width: 100%;margin-top: 20px">
            <tr>
                <td>订单编号</td>
                <td>付款单编号</td>
                <td>抵充金额</td>
                <%--<td>付款类型</td>--%>
                <td>原分期批次号</td>
                <td>新分期批次号</td>
            </tr>


                    <c:forEach items="${oAccountAdjusts.details}" var="details">
            <tr>
                    <td>${details.orderId}</td>
                    <%--<td>
                        <c:forEach items="${settlementType}" var="paymentType">
                            <c:if test="${details.payType == settlementType.dItemvalue}">
                                ${settlementType.dItemname}
                            </c:if>
                        </c:forEach>
                    </td>--%>
                    <td>${details.paymentDetailId}</td>
                    <td>${details.takeOutAmount}</td>
                    <td>${details.batchCodeOld}</td>
                    <td>${details.batchCodeNew}</td>
            </tr>
                    </c:forEach>



        </table>
    </div>

</div>

<form id="receipPlanQueryForm" method="post">
    <input type="hidden" name="returnId" value="${returnOrder.id}">
</form>

<script type="text/javascript">

    var cuts = [];
    var returnId = '${returnOrder.id}';
    var totalAmt =${returnOrder.goodsReturnAmo};

    //扣款明细对话框
    function cutDetail() {
        $('#dd_kk_${returnOrder.id}').dialog('open');
    }

    //可抵扣欠款对话框
    function takoutAmt() {
        $('#dd_qk_${returnOrder.id}').dialog('open');
    }

    //付款计划变更对话框
    function fkjh() {
        $('#dd_jh_${returnOrder.id}').dialog('open');
    }

    //调账明细对话框
    function adjustD() {
        $('#dd_tz_${returnOrder.id}').dialog('open');
    }

    function addCut() {

        var text = $('#select_cut').combobox('getText');
        var id = $('#select_cut').combobox('getValue');

        if($("#a_"+id).length > 0){
            return;
        }

        $("#td_cut").append('<div><span style="width: 100px;display:inline-block;margin-top: 10px">' + text + '：</span><input data-options="precision:2" type="text" name="cut" id="' + id + '">&nbsp;&nbsp;&nbsp;&nbsp;<a id="a_' + id + '" href="#" onclick="saveCutAmt(\'' + id + '\')">保存</a></div>')
    }


    //保存扣款款项
    function saveCutAmt(id) {
        var amt = $("#" + id).val();

        if(amt==null || amt=="" || amt==undefined){
            parent.$.messager.alert('错误', '金额不能为空', 'error');
            return;
        }

        $.ajax({
            url: "${path}/order/return/saveCut",
            type: 'POST',
            data: {
                returnId: returnId,
                ctype: id,
                amt: $("#" + id).val()
            },
            dataType: 'json',
            success: function (result) {
                if (result.resCode == '1') {
                    $("#cutAmo").text(result.obj.cutAmo);
                    $("#returnAmo").text(result.obj.returnAmo);
                    $("#a_" + id).after("<a id=\"a_"+result.obj.cutId+"\" href=\"#\" onclick=\"delCut('"+result.obj.cutId+"')\">删除</a>");
                    $("#a_" + id).hide();

                    parent.$.messager.alert('信息', '添加款项成功', 'success');
                } else {
                    parent.$.messager.alert('错误', result.resInfo, 'error');
                }
            }
        });
    }

    //删除扣款款项
    function delCut(cutId){
        $.ajax({
            url: "${path}/order/return/delCut",
            type: 'POST',
            data: {
                returnId: returnId,
                cutId: cutId
            },
            dataType: 'json',
            success: function (result) {
                if (result.resCode == '1') {
                    $("#cutAmo").text(result.obj.cutAmo);
                    $("#returnAmo").text(result.obj.returnAmo);
                    $("#a_"+cutId).parent().remove();
                    parent.$.messager.alert('信息', '删除款项成功', 'success');
                } else {
                    parent.$.messager.alert('错误', result.resInfo, 'error');
                }
            }
        });

    }


    //申请按钮
    $(function () {
        $('#form_sq').form({
            url: '${path }/order/return/apply',
            onSubmit: function () {
                var isValid = $(this).form('validate');
                if (!isValid) {

                }
                return isValid;

            },
            success: function (result) {
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });

    $(function () {
        //代理商表格
        $('#paidan_complate_b').datagrid({
            rownumbers: true,
            striped: true,
            pagination: false,
            iconCls: 'icon-edit',
            singleSelect: true,
            editors: $.fn.datagrid.defaults.editors,
            idField: 'id',
            columns: [[{
                title: '订单编号',
                field: 'ORDER_ID',
                sortable: true,
                width: 120
            }, {
                title: '子订单编号',
                field: 'RECEIPT_NUM',
                sortable: true,
                width: 120
            }, {
                title: '代理商ID',
                field: 'AGENT_ID',
                sortable: true,
                width: 120
            }, {
                title: '收货姓名',
                field: 'ADDR_REALNAME',
                sortable: true,
                width: 120
            }, {
                title: '商品名称',
                field: 'PRO_NAME',
                sortable: true,
                width: 120
            }, {
                title: '订单名称',
                field: 'ORDER_ID',
                sortable: true,
                width: 120
            }, {
                title: '订货量',
                field: 'PRO_NUM',
                sortable: true,
                width: 120
            }, {
                title: '已排量',
                field: 'SEND_NUM',
                sortable: true,
                width: 120
            }, {
                title: '厂家',
                field: 'proCom',
                sortable: true,
                editor: "text",
                width: 120
            }, {
                title: '机型',
                field: 'model',
                sortable: true,
                editor: "text",
                width: 80
            }, {
                title: '数量',
                field: 'planProNum',
                sortable: true,
                editor: "numberbox",
                width: 80
            }]],
            onLoadSuccess: function (data) {
            },
            onDblClickRow: function (rowIndex, rowData) {
            },
            onBeforeEdit: function (index, row) {
            },
            onAfterEdit: function (index, row) {
            },
            onCancelEdit: function (index, row) {
            }
        });
    });

    var paidan;
    $(function () {
        //代理商表格
        paidan = $('#paidan').datagrid({
            rownumbers: true,
            striped: true,
            pagination: false,
            iconCls: 'icon-edit',
            singleSelect: true,
            editors: $.fn.datagrid.defaults.editors,
            idField: 'id',
            columns: [[{
                title: '退货子订单编号',
                field: 'O_RETURN_ORDER_DETAIL_ID',
                sortable: true,
                width: 120
            }, {
                title: '订单编号',
                field: 'ORDER_ID',
                sortable: true,
                width: 120
            }, {
                title: '子订单编号',
                field: 'RECEIPT_NUM',
                sortable: true,
                width: 120
            }, {
                title: '代理商ID',
                field: 'AGENT_ID',
                sortable: true,
                width: 120
            }, {
                title: '收货姓名',
                field: 'ADDR_REALNAME',
                sortable: true,
                width: 120
            }, {
                title: '商品名称',
                field: 'PRO_NAME',
                sortable: true,
                width: 120
            }, {
                title: '订单名称',
                field: 'ORDER_ID',
                sortable: true,
                width: 120
            }, {
                title: '订货量',
                field: 'PRO_NUM',
                sortable: true,
                width: 120
            }, {
                title: '已排量',
                field: 'SEND_NUM',
                sortable: true,
                width: 120
            }, {
                title: '数量',
                field: 'planProNum',
                sortable: true,
                editor: "numberbox",
                width: 80
            },{
                title : '活动名称',
                field : 'ACTIVITY_NAME'
            } , {
                title: '操作',
                field: 'opt',
                width: 80,
                formatter : function(value, row, index) {
                    var str = '';
                    str += '<a href="javascript:void(0)" class="" data-options="plain:true,iconCls:\'fi-magnifying-glass\'" onclick="deleteRw('+index+');">删除</a>';
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                for (var i in data.rows) {
                    paidan.datagrid("beginEdit", i);
                }
            },
            onDblClickRow: function (rowIndex, rowData) {
                paidan.datagrid("beginEdit", rowIndex);
            },
            onBeforeEdit: function (index, row) {
                row.editing = true;
            },
            onAfterEdit: function (index, row) {
                row.editing = false;
            },
            onCancelEdit: function (index, row) {
                row.editing = false;
            }
        });
    });


    function deleteRw(i) {
        paidan.datagrid("deleteRow", i);
    }


    //打开排单查询对话框
    function openPlanerListPage(returnDetailsId) {
        parent.$.modalDialog.openner_dataGrid = paidan;
        parent.$.modalDialog({
            title: '排单查询',
            width : '60%',
            height : '80%',
            maximizable: true,
            href: '${path }/order/return/page/planerList?returnDetailsId='+returnDetailsId,
            buttons: [{
                text: '关闭',
                handler: function () {
                    parent.$.modalDialog.handler.dialog('close');
                }
            }]
        });
    }


    //获取排单数据
    function getPaiDanInfos() {
        var rows = $("#paidan").datagrid("getRows");
        var plans = [];
        for (var i = 0; i < rows.length; i++) {
            var oneplan = {};
            //alert(JSON.stringify(rows[i]));
            var index = $('#paidan').datagrid('getRowIndex', rows[i]);
            var proNum = rows.proNum;
            var sendNum = rows.sendNum;
            paidan.datagrid("beginEdit", index);

            //厂家与机型使用退货单厂家
            //            var proCom = $('#paidan').datagrid("getEditor", {index: index, field: 'proCom'});
            //            var model = $('#paidan').datagrid("getEditor", {index: index, field: 'model'});
            var planProNum = $('#paidan').datagrid("getEditor", {index: index, field: 'planProNum'});


            //厂家与机型使用退货单厂家
            //            if (proCom == undefined) {
            //                info("请填写厂家");
            //                return [];
            //            }
            //            if (model == undefined) {
            //                info("请填写型号");
            //                return [];
            //            }
            if (planProNum == undefined) {
                info("请填写数量");
                return [];
            }
            //厂家与机型使用退货单厂家
            //            var proComVal = $(proCom.target).val();
            //            var modelVal = $(model.target).val();
            var planProNumVal = $(planProNum.target).val();
            //厂家与机型使用退货单厂家
            //            if (proComVal == '' || proComVal == undefined) {
            //                info("请填写厂家");
            //                return false;
            //            }
            //            if (modelVal == '' || modelVal == undefined) {
            //                info("请填写型号");
            //                return false;
            //            }
            if (planProNumVal == '' || planProNumVal == undefined) {
                info("请填写数量");
                return false;
            }

            oneplan.receiptId = rows[i].RECEIPT_ID;
            oneplan.orderId = rows[i].ORDERID;
//            oneplan.proCom = proComVal;
//            oneplan.model = modelVal;
            oneplan.planProNum = planProNumVal;
            oneplan.receiptProId = rows[i].RECEIPT_PRO_ID;
            oneplan.O_RETURN_ORDER_DETAIL_ID = rows[i].O_RETURN_ORDER_DETAIL_ID;
            plans.push(oneplan);
        }
        return plans;
    }



    //已排单查询
    var receipPlanDataGrid_b;
    $(function () {
        receipPlanDataGrid_b = $('#paidan_complate_b').datagrid({
            url: '${path }/receiptPlanReturn/list?returnId=' + returnId,
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '退货子订单编号',
                field: 'RETURN_ORDER_DETAIL_ID'
            },{
                title: '排单编号',
                field: 'PLAN_NUM'
            }, {
                title: '排单状态',
                field: 'PLAN_ORDER_STATUS',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 3:
                            return '未发货';
                        case 2:
                            return '已发货';
                        case 1:
                            return '已排单';
                    }
                }
            }, {
                title: '订单编号',
                field: 'ORDER_ID',
            }, {
                title: '商品编号',
                field: 'PRO_CODE'
            }, {
                title: '商品名称',
                field: 'PRO_NAME'
            }, {
                title: '商品数量',
                field: 'PRO_NUM'
            }, {
                title: '已排单数量',
                field: 'SEND_NUM'
            }, {
                title: '订货厂家',
                field: 'PRO_COM'
            }, {
                title: '订货数量',
                field: 'PLAN_PRO_NUM'
            }, {
                title: '发货数量',
                field: 'SEND_PRO_NUM'
            }, {
                title: '机型',
                field: 'MODEL'
            }, {
                title: '收货人姓名',
                field: 'ADDR_REALNAME'
            }, {
                title: '收货人联系电话',
                field: 'ADDR_MOBILE'
            }, {
                title: '省',
                field: 'NAME'
            }, {
                title: '市',
                field: 'CITY'
            }, {
                title: '区',
                field: 'DISTRICT'
            }, {
                title: '详细地址',
                field: 'ADDR_DETAIL'
            }, {
                title: '地址备注',
                field: 'REMARK'
            }, {
                title: '邮编',
                field: 'ZIP_CODE'
            }, {
                title: '快递备注',
                field: 'EXPRESS_REMARK'
            }, {
                title: '排单创建时间',
                field: 'C_DATE'
            }]]
        });
    });

    //导出排单
    function exportData_refund() {
        $('#receipPlanQueryForm').form({
            url: '${path}/receiptPlanReturn/export',
            onSubmit: function () {
                return $(this).form('validate');
            }
        });
        $('#receipPlanQueryForm').submit();
    }


    // 导入物流信息
    $("#importOLogisticsFun").click(function () {
        parent.$.modalDialog({
            title: '导入物流信息',
            width: 300,
            height: 110,
            href: "${path}/order/importPage",
            buttons: [{
                text: '确定',
                handler: function () {
                    var fun = parent.$.modalDialog.handler.find('#logisticsImportFileForm');
                    fun.submit();
                }
            }]
        });
    });


    //执行扣款计划
    function doPayPlan() {
        $.ajaxL({
            type: "POST",
            url: "${path}/order/return/doPayPlan",
            dataType: 'json',
            data: {
                returnId: returnId
            },
            beforeSend: function () {
                progressLoad();
            },
            success: function (result) {
                if (result.success) {
                    info(result.msg);
                    $("#a_zxqkdk").hide();
                } else {
                    err(result.msg);
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                progressClose();
            }
        });
    }

    //物流信息
    var logisticsRefund_b;
    $(function () {
        logisticsRefund_b = $('#wuliu_b').datagrid({
            url: '${path }/logistics/oLogisticsListRefund?returnId=' + returnId,
            striped: true,
            rownumbers: true,
            pagination: true,
            singleSelect: true,
            fit: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
            columns: [[{
                title: '排单编号',
                field: 'PLAN_NUM'
            }, {
                title: '排单状态',
                field: 'PLAN_ORDER_STATUS',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 3:
                            return '未发货';
                        case 2:
                            return '已发货';
                        case 1:
                            return '已排单';
                    }
                }
            }, {
                title: '订单编号',
                field: 'ORDER_ID',
            }, {
                title: '商品编号',
                field: 'PRO_CODE'
            }, {
                title: '商品名称',
                field: 'PRO_NAME'
            }, {
                title: '商品数量',
                field: 'PRO_NUM'
            }, {
                title: '已排单数量',
                field: 'SEND_NUM'
            }, {
                title: '订货厂家',
                field: 'PRO_COM'
            }, {
                title: '订货数量',
                field: 'PLAN_PRO_NUM'
            }, {
                title: '发货数量',
                field: 'SEND_PRO_NUM'
            }, {
                title: '机型',
                field: 'MODEL'
            }, {
                title: '实际发货时间',
                field: 'REAL_SEND_DATE'
            }, {
                title: '收货人姓名',
                field: 'ADDR_REALNAME'
            }, {
                title: '退货子订单编号',
                field: 'RETURN_ORDER_DETAIL_ID'
            }, {
                title: '物流公司',
                field: 'LOG_COM'
            }, {
                title: '物流类型',
                field: 'LOG_TYPE',
                formatter: function (value, row, index) {
                    switch (value) {
                        case "2":
                            return '退货物流';
                        case "1":
                            return '发货物流';
                    }
                }
            }, {
                title: '物流单号',
                field: 'W_NUMBER'
            }, {
                title: '起始SN序列号',
                field: 'SN_BEGIN_NUM'
            }, {
                title: '结束SN序列号',
                field: 'SN_END_NUM'
            }]],
            toolbar: '#logisticsToolbar'
        });
    });

    function get_time_return() {
        var data = {};
        data.refundpeople = $("input[name='refundPeople']").val();
        data.refundtime = $("input[name='time']").val();
        return data;
    }
</script>
