<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/commons/global.jsp" %>
<%@ include file="/commons/angetJs.jsp" %>
<div class="easyui-tabs" id="taskAppTabs">
    <div title="待审信息">
        <jsp:include page="/orderbuild/orderView?orderId=${busId}"></jsp:include>
        <%--订单审批业务信息--%>
        <%@ include file="/commons/orderApproveBusInfo.jsp" %>
        <%--审批意见--%>
        <%@ include file="/commons/approval_opinion_order.jsp" %>
        <c:if test="${data.order.userId==MainLoginUserId}">
            <shiro:hasPermission name="order_apr_Permission_update">
                <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-pencil'"  onclick="xiugaiAction_approve('${busId}','${data.agent.id}')">修改</a>
            </shiro:hasPermission>
        </c:if>

<c:choose >
    <%--财务部选项控制--%>
    <c:when  test="${!empty userOrgCode && !empty userOrgCode.ORGANIZATIONCODE && userOrgCode.ORGANIZATIONCODE=='finance'}">
        <%--有保证金抵扣，发货人员审批--%>
        <%--北京收款 北京财务审批--%>
        <%--深圳收款 深圳财务审批--%>
        <c:choose >
            <%--有保证金抵扣，发货人员审批--%>
            <c:when test="${!empty oPayment.deductionAmount && oPayment.deductionAmount > 0}">
                <shiro:hasRole name="财务发货人员">
                    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="subApproval_FormDataItem_order()">提交</a>
                </shiro:hasRole>
            </c:when>
            <%--北京收款 北京财务审批--%>
            <c:when test="${!empty oPayment.collectCompany && oPayment.collectCompany == '8'}">
                <shiro:hasRole name="北京财务">
                    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="subApproval_FormDataItem_order()">提交</a>
                </shiro:hasRole>
            </c:when>
            <%--深圳收款 深圳财务审批--%>
            <c:when test="${!empty oPayment.collectCompany && oPayment.collectCompany == '7'}">
                <shiro:hasRole name="深圳财务">
                    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="subApproval_FormDataItem_order()">提交</a>
                </shiro:hasRole>
            </c:when>
            <%--发货人员审批--%>
            <c:otherwise>
                <shiro:hasRole name="财务发货人员">
                    <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="subApproval_FormDataItem_order()">提交</a>
                </shiro:hasRole>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <%--其他部门权限--%>
            <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 200px;" data-options="iconCls:'fi-save'"  onclick="subApproval_FormDataItem_order()">提交</a>
    </c:otherwise>
</c:choose>


    </div>
        <div title="审批记录">
            <%@ include file="/commons/approval_record.jsp" %>
        </div>
    <div title="审批流程图">
            <img src="/agActivity/approvalImage?taskId=${taskId}" />
    </div>
</div>
<script>
    function subApproval_FormDataItem_order() {
        var subApprovalTable = (typeof get_subApproval_FormDataItem_order === "function")?get_subApproval_FormDataItem_order():{};
        var subApprovalOrderTableBusInfo = (typeof get_subApproval_FormDataItem_order_busInfo === "function")?get_subApproval_FormDataItem_order_busInfo():{};
        var oCashReceivablesVoList = (typeof getApproveCashPayListRealRecTime === "function")?getApproveCashPayListRealRecTime("${paylist_model}"):[];
        var payMent= (typeof get_payMethod=== "function")?get_payMethod():{};
        var subFlag = true;
        if(subFlag){
            parent.$.messager.confirm('询问', '确认完成任务？', function(b) {
                if (b) {
                    $.ajaxL({
                        type: "POST",
                        url: "/orderbuild/taskApproval",
                        dataType:'json',
                        traditional:true,//这使json格式的字符不会被转码
                        contentType:'application/json;charset=UTF-8',
                        data: JSON.stringify({
                            approvalOpinion:subApprovalTable.approvalOpinion,
                            approvalResult:subApprovalTable.approvalResult,
                            taskId:subApprovalTable.taskId,
                            orderAprDept:subApprovalTable.dept,
                            oPayment:subApprovalOrderTableBusInfo,
                            oCashReceivablesVoList:oCashReceivablesVoList,
                            payMethod:payMent.payMethod
                        }),
                        beforeSend:function(){
                            progressLoad();
                        },
                        success: function(msg){
                            info(msg.msg);
                            if(msg.status==200){
                                $('#index_tabs').tabs('close',"处理任务");
                                activityDataGrid.datagrid('reload');
                            }
                        },
                        complete:function (XMLHttpRequest, textStatus) {
                            progressClose();
                        }
                    });
                }
            });
        }
    }

    function refreshTabView() {
        refreshTab();
    }

    //修改订单
    function xiugaiAction_approve(id,agentId){
        //根据订单信息查询需要补款的付款明细
        addTab({
            title: '订单修改',
            border: false,
            closable: true,
            fit: true,
            href: "${path}/order/updateOrderView?orderId="+id+"&agentId="+agentId
        });
    }
</script>
